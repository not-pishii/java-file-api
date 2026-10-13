package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.typed.MemberBuilder.Member;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The bodies of the members of a class being declared (§6.5), branded
/// with `Self`: the stage after its declarations ([MemberBuilder]), where
/// each declared method and constructor is defined and each field given its
/// initializer. What [TypedClassBodies] — of a class that extends `Object` —
/// and [TypedSubclassBodies] — of a class with another superclass — have in
/// common, which is everything but the bodies of the constructors.
///
/// **A body is judged where it is given.** When this stage begins every
/// member of the class is declared: its methods are known, and the names of
/// its fields. So the body a `define` is given is built and lowered by that
/// call, and whatever is wrong with it — a `protected` member reached where
/// it cannot be, an overload no argument tells from the one meant, a type
/// parameter out of its scope, a name the file cannot write — is rejected by
/// that call, in the generator, with the stack of the line that gave the
/// body.
///
/// **Every declared member is defined exactly once.** A second definition is
/// rejected where it is given. A member declared and never defined is
/// rejected when the declaration of the class completes — nothing earlier
/// can tell that no definition is to come — and the rejection names the line
/// that declared it.
///
/// **`this` is a parameter.** The body of an instance method or constructor
/// receives `this` as a [This] HOAS parameter, next to its block; a static
/// method body does not. `this` is in scope in that body only — lambdas in it
/// included — and using it anywhere else, e.g. smuggled into the body of
/// another member or a field initializer, is rejected when the statement is
/// built (§6.2).
///
/// **`super.m()` and `protected` members.** A method the superclass has with
/// a body is called by [#superCall] on `this`; a `protected` member of the
/// superclass or of a class above it is reached by its fact, taken out of its
/// wrapper by [#protected_] — the type of this stage says the class is a
/// subclass of `S`.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class MemberBodies<Self, S> permits TypedClassBodies, TypedSubclassBodies {
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");

    private final ClassDeclaration<Self> owner;
    private final TargetClasspath target;
    private final String described;
    private final FinalClassToken<Self> self;
    private final Superclass superclass;
    private final Function<List<ClassMember>, JavaFile> written;
    private List<Slot> slots;

    /// What a class declared, as its bodies take it.
    ///
    /// @param owner the declaration of the class
    /// @param superclass its superclass
    /// @param members its members, in the order they were declared
    /// @param <Self> the brand of the class
    record Declared<Self>(
            ClassDeclaration<Self> owner,
            Superclass superclass,
            List<Member> members,
            Function<List<ClassMember>, JavaFile> written) {
        Declared {
            members = List.copyOf(members);
        }
    }

    /// @throws IllegalStateException if a type the declarations of the class name cannot be written in its file
    MemberBodies(Declared<Self> declared) {
        this.owner = declared.owner();
        this.target = owner.target();
        this.described = owner.described();
        this.self = owner.self();
        this.superclass = declared.superclass();
        this.written = declared.written();
        this.slots = declared.members().stream()
                .<Slot>map(member -> switch (member) {
                    case Member.Callable callable -> new Slot.Awaiting(callable);
                    case Member.Field field -> new Slot.Blank(field);
                    case Member.Given given -> new Slot.Defined(given, given.member());
                })
                .toList();
        requireWritable(slots, "the members");
    }

    /// The fact of the class being declared, see [MemberBuilder#self()]. Its
    /// [FinalClassToken#methods()] is known: the declarations of the class have ended.
    ///
    /// @return the self token
    public final FinalClassToken<Self> self() {
        return self;
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Gives a declared field its initializer. The initializer is not in the scope of any body,
    /// so it cannot use `this` or a variable, nor throw a checked exception.
    ///
    /// @param field the declared field
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @throws IllegalStateException if the field has its initializer already or was not declared by
    ///     this class, if the initializer can throw a checked exception, or cannot be lowered or written
    ///     (see [#define(MethodRef0, InstanceBody0)])
    public final <T> void initialize(FieldRef<Self, T> field, Expr<? extends T> initializer) {
        String name = field.name();
        Slot.Blank slot = initializable(field);
        ScopeCheck.standalone(initializer.node(), "initializer of field " + name);
        Exceptions.initializer(initializer.node(), "initializer of field " + name);
        defined(
                slot,
                new FieldDecl(
                        name,
                        field.type().typeRef(),
                        List.of(),
                        slot.declared().modifiers(),
                        Optional.of(new Lowering(
                                        target,
                                        new Lowering.Place.InClass(
                                                self, owner.scope("the initializer of field " + name)),
                                        Optional.empty())
                                .lowerInitializer(initializer.node(), field.type())),
                        Optional.empty()),
                "the initializer of field " + name);
    }

    // ------------------------------------------------------------------
    // Definitions
    // ------------------------------------------------------------------

    /// Defines a declared method; the body gets `this`.
    ///
    /// The body is built and lowered by this call: what is wrong with it is rejected here.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    /// @throws IllegalStateException if the method is defined already or was not declared by this
    ///     class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names
    ///     a type parameter of the class out of its scope, or a type the file of the class cannot write
    /// @throws TargetClasspathMismatchException if a metamodel the body uses does not hold on the target classpath
    /// @throws FactLookupException if a member the body uses has an overload on the target classpath
    ///     that no argument tells it from
    public final <R> void define(MethodRef0<Self, R> method, InstanceBody0<Self, Body<R>, R> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        define(slot, root, List.of(), b -> body.build(b, self));
    }

    /// Defines a declared method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <R, A1> void define(MethodRef1<Self, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, root, List.of(p1), b -> body.build(b, self, p1));
    }

    /// Defines a declared method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <R, A1, A2> void define(
            MethodRef2<Self, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        define(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2));
    }

    /// Defines a declared `void` method; the body gets `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final void define(VoidMethodRef0<Self> method, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        define(slot, root, List.of(), b -> body.build(b, self));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <A1> void define(VoidMethodRef1<Self, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, root, List.of(p1), b -> body.build(b, self, p1));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <A1, A2> void define(
            VoidMethodRef2<Self, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        define(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2));
    }

    /// Defines a declared static method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    /// @param <R> the result type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <R> void define(StaticMethodRef0<R> method, Function<Body<R>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), declares(method));
        define(slot, root, List.of(), body);
    }

    /// Defines a declared static method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <R, A1> void define(StaticMethodRef1<R, A1> method, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, root, List.of(p1), b -> body.apply(b, p1));
    }

    /// Defines a declared static `void` method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final void define(VoidStaticMethodRef0 method, Function<VoidBody, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), declares(method));
        define(slot, root, List.of(), body);
    }

    /// Defines a declared static `void` method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as [#define(MethodRef0, InstanceBody0)]
    public final <A1> void define(
            VoidStaticMethodRef1<A1> method, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, root, List.of(p1), b -> body.apply(b, p1));
    }

    /// Defines a declared constructor; the body gets `this`.
    final void defineConstructor(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        define(slot, root, List.of(), b -> body.build(b, self));
    }

    /// Defines a declared constructor; the body gets `this` and the parameter.
    final <A1> void defineConstructor(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        define(slot, root, List.of(p1), b -> body.build(b, self, p1));
    }

    /// Defines a declared constructor; the body gets `this` and the parameters.
    final <A1, A2> void defineConstructor(
            CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        define(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2));
    }

    // ------------------------------------------------------------------
    // What a class has of its superclass
    // ------------------------------------------------------------------

    /// Defines a declared constructor that begins with `super(...)`.
    final void defineWithSuper(CtorRef0<Self> ctor, TypedSubclassBodies.CtorBody0<Self, S> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        built(root, b -> body.build(site));
        requireCalled(site, ctor);
        defined(slot, root, List.of());
    }

    /// Defines a declared constructor with a parameter that begins with `super(...)`.
    final <A1> void defineWithSuper(CtorRef1<Self, A1> ctor, TypedSubclassBodies.CtorBody1<Self, S, A1> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        Var<A1> p1 = Var.param(ctor.param1(), root);
        built(root, b -> body.build(site, p1));
        requireCalled(site, ctor);
        defined(slot, root, List.of(p1));
    }

    /// Defines a declared constructor with two parameters that begins with `super(...)`.
    final <A1, A2> void defineWithSuper(
            CtorRef2<Self, A1, A2> ctor, TypedSubclassBodies.CtorBody2<Self, S, A1, A2> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        built(root, b -> body.build(site, p1, p2));
        requireCalled(site, ctor);
        defined(slot, root, List.of(p1, p2));
    }

    private void requireCalled(SuperSite<Self, ?> site, Invocable ctor) {
        if (!site.called()) {
            throw new IllegalStateException("the body of " + ctor + " of " + described + " does not begin with"
                    + " super(...): end it by ctor.super_(constructor, arguments, rest)");
        }
    }

    /// The fact of a `protected` member of the superclass or of a class above it, taken out of
    /// its wrapper.
    ///
    /// The fact is of the member as its owner declares it. A method of it is called on `this` or
    /// on another value of the class being declared, and a field read on `this` — in the body of
    /// this class alone, which lowering holds every use to (JLS 6.6.2): the fact is a value, and a
    /// generator may carry it anywhere.
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if the class is no subclass of the owner of the member on the
    ///     target classpath
    public final <F extends MemberFact> F protected_(Protected<? super S, F> held) {
        return MemberBuilder.opened(self, described, held);
    }

    /// `super.method()`: the method as the superclass has it, whatever the class being declared
    /// overrides it with. `self` is `this` of the body the call stands in; the method is one of
    /// the superclass or of a class above it that has a body there.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param <R> the result type
    /// @return the call
    /// @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no
    ///     such method with a body on the target classpath
    public final <R> Invocation<R> superCall(This<Self> self, MethodRef0<? super S, R> method) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments()), method.result());
    }

    /// `super.method(a1)`, see [#superCall(This, MethodRef0)].
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the argument
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    public final <R, A1> Invocation<R> superCall(
            This<Self> self, MethodRef1<? super S, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1)), method.result());
    }

    /// `super.method(a1, a2)`, see [#superCall(This, MethodRef0)].
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    public final <R, A1, A2> Invocation<R> superCall(
            This<Self> self, MethodRef2<? super S, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2)), method.result());
    }

    /// `super.method()` of a `void` method, see [#superCall(This, MethodRef0)].
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    public final VoidInvocation superCall(This<Self> self, VoidMethodRef0<? super S> method) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments()));
    }

    /// `super.method(a1)` of a `void` method, see [#superCall(This, MethodRef0)].
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the argument
    /// @param <A1> the parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    public final <A1> VoidInvocation superCall(
            This<Self> self, VoidMethodRef1<? super S, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1)));
    }

    /// `super.method(a1, a2)` of a `void` method, see [#superCall(This, MethodRef0)].
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    public final <A1, A2> VoidInvocation superCall(
            This<Self> self, VoidMethodRef2<? super S, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2)));
    }

    /// `super.method(args)`, on `this` of this class.
    private Node superCallNode(This<Self> receiver, Invocable method, List<Node.Operand> args) {
        if (receiver.type() != self) {
            throw new IllegalArgumentException("super." + method.name() + "(...) is called on this of " + described
                    + ", got this of " + receiver.type());
        }
        Inheritance.Group group = superclass.member(described, method);
        Inheritance.Entry member = group.stands();
        return switch (group.standing()) {
            case DEFAULT, CONCRETE, FINAL ->
                switch (member.method().visibility()) {
                    case PUBLIC, PROTECTED -> new Node.SuperCall(receiver.body(), superclass.token(), method, args);
                    case PACKAGE ->
                        throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                                + " cannot call " + member + ": the method has package access and the class is in"
                                + " its package — whether the class reaches it depends on the packages of the"
                                + " classes between, which the typed layer does not tell");
                };
            case ABSTRACT ->
                throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                        + " calls no body: " + member + " is abstract in " + superclass.token() + " (JLS 15.12.3)");
            case STATIC ->
                throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                        + " calls an instance method, and " + member + " is static in " + superclass.token());
        };
    }

    // ------------------------------------------------------------------
    // Completion
    // ------------------------------------------------------------------

    /// Completes the declaration: every declared member must be defined.
    ///
    /// @return the members, in declaration order
    /// @throws IllegalStateException if a declared member is not defined: its message tells where each
    ///     such member was declared, and its cause is of the stack of the first
    final List<ClassMember> complete() {
        List<Undefined> undefined = slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting(var declared) ->
                        Stream.of(new Undefined(declared.fact().toString(), declared.site()));
                    case Slot.Blank(var declared) ->
                        Stream.of(new Undefined("field " + declared.fact(), declared.site()));
                    case Slot.Defined _ -> Stream.<Undefined>empty();
                })
                .toList();
        if (!undefined.isEmpty()) {
            IllegalStateException rejection = new IllegalStateException(
                    "the declaration of " + described + " is complete, but these declared members are never"
                            + " defined: "
                            + undefined.stream()
                                    .map(member -> member.what() + " (declared at " + member.site() + ")")
                                    .collect(Collectors.joining(", "))
                            + "; define each declared member exactly once, and give each field its initializer",
                    undefined.getFirst().site().asCause(undefined.getFirst().what() + " was declared"));
            undefined.stream()
                    .skip(1)
                    .forEach(member -> rejection.addSuppressed(member.site().asCause(member.what() + " was declared")));
            throw rejection;
        }
        return members(slots);
    }

    /// A member that was declared and has no definition.
    private record Undefined(String what, Site site) {}

    // ------------------------------------------------------------------

    /// The members of the class as far as they are there: a field without its initializer takes
    /// its name already, a method or constructor without its body is not there yet.
    private static List<ClassMember> members(List<Slot> slots) {
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting _ -> Stream.<ClassMember>empty();
                    case Slot.Blank(var declared) ->
                        Stream.of(new FieldDecl(
                                declared.fact().name(),
                                declared.fact().type().typeRef(),
                                List.of(),
                                declared.modifiers(),
                                Optional.empty(),
                                Optional.empty()));
                    case Slot.Defined(var _, var member) -> Stream.of(member);
                })
                .toList();
    }

    /// Holds the class, as far as it is there, to what a file can write: the renderer writes a type
    /// a field, a variable, a member type or a type parameter of the file hides by its qualified
    /// name, and refuses the file where that name begins with such a name too (JLS 6.4.2) — when it
    /// renders. The class is rendered here, with what `added` brought, so the call that brought a
    /// name the file cannot write is the one rejected; a class whose last member is defined is one
    /// that renders.
    ///
    /// @throws IllegalStateException if a type the class names cannot be written in its file
    private void requireWritable(List<Slot> with, String added) {
        try {
            written.apply(members(with)).render();
        } catch (IllegalStateException unwritable) {
            throw new IllegalStateException(
                    added + " of " + described + " cannot be written: " + unwritable.getMessage()
                            + ". The names are those of the fields the class declares and inherits, of the"
                            + " variables of its bodies, of the member types it inherits and of its type"
                            + " parameters: a field of the class can be given another name, a name a supertype"
                            + " takes cannot — a type it hides is then none the class can mention",
                    unwritable);
        }
    }

    /// The slot of a method or constructor that is declared and not defined yet. A member is known
    /// by its fact itself: two facts that tell the same are two members.
    private Slot.Awaiting definable(Invocable member) {
        String what = member.toString();
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting awaiting when awaiting.declared().fact() == member -> Stream.of(awaiting);
                    case Slot.Defined(Member.Callable declared, var _)
                    when declared.fact() == member -> throw alreadyDefined(what);
                    case Slot _ -> Stream.<Slot.Awaiting>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    /// The slot of a field that is declared and has no initializer yet.
    private Slot.Blank initializable(FieldRef<?, ?> field) {
        String what = "field " + field;
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Blank blank when blank.declared().fact() == field -> Stream.of(blank);
                    case Slot.Defined(Member.Field declared, var _)
                    when declared.fact() == field -> throw alreadyDefined(what);
                    case Slot _ -> Stream.<Slot.Blank>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    private IllegalStateException alreadyDefined(String what) {
        return new IllegalStateException(
                what + " of " + described + " is already defined; each declared member is defined exactly once");
    }

    private IllegalStateException undeclared(String what) {
        return new IllegalStateException("cannot define " + what + ": it was not declared by the builder of "
                + described + "; define only the members declared through its declare* methods");
    }

    private This<Self> thisOf(Block<?, ?> root) {
        return new This<>(root, self);
    }

    /// Builds `root` — the root of its scope tree — from `bodyFn`.
    private static <R, B extends Block<R, B>> void built(B root, Function<? super B, Terminated<R>> bodyFn) {
        Terminated<R> terminated = Scopes.within(root, () -> bodyFn.apply(root));
        Block.requireIssuedBy(terminated, root);
    }

    /// The declared member gets the body `bodyFn` builds: built and lowered here.
    private <R, B extends Block<R, B>> void define(
            Slot.Awaiting slot, B root, List<Var<?>> params, Function<? super B, Terminated<R>> bodyFn) {
        built(root, bodyFn);
        defined(slot, root, params);
    }

    /// The declared member gets the body built in `root`: lowered here, against all the class declared.
    private void defined(Slot.Awaiting slot, Block<?, ?> root, List<Var<?>> params) {
        defined(slot, lower(slot.declared(), root, params), "the " + root.path());
    }

    /// The declared member is there whole.
    private void defined(Slot.Open from, ClassMember member, String what) {
        List<Slot> with = slots.stream()
                .map(slot -> slot == from ? new Slot.Defined(from.declared(), member) : slot)
                .toList();
        requireWritable(with, what);
        slots = with;
    }

    private ClassMember lower(Member.Callable declared, Block<?, ?> root, List<Var<?>> params) {
        Invocable fact = declared.fact();
        Lowering lowering = new Lowering(target, new Lowering.Place.InClass(self, declared.scope()), fact.resultType());
        List<Param> named = params.stream()
                .map(param ->
                        new Param(lowering.declareUpfront(param), param.type().typeRef()))
                .toList();
        List<Param> coreParams =
                switch (declared.arity()) {
                    case FIXED -> named;
                    case VARIABLE ->
                        Stream.concat(named.stream().limit(named.size() - 1L), Stream.of(variable(named.getLast())))
                                .toList();
                };
        CodeBody code = lowering.lowerBlock(root.instrs());
        List<ClassOrInterfaceTypeRef> throwsClause = declared.thrown().stream()
                .<ClassOrInterfaceTypeRef>map(type -> lowering.named(type).typeRef())
                .toList();
        List<AnnotationUse> annotations =
                switch (declared.kind()) {
                    case OVERRIDES -> List.of(new AnnotationUse(OVERRIDE, List.of()));
                    case DECLARES -> List.of();
                };
        return switch (fact.kind()) {
            case CONSTRUCTOR ->
                new ConstructorDecl(
                        List.of(), Set.of(Modifier.PUBLIC), coreParams, code, throwsClause, Optional.empty());
            case INSTANCE_METHOD ->
                new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        annotations,
                        Set.of(Modifier.PUBLIC),
                        List.of(),
                        coreParams,
                        code,
                        throwsClause,
                        Optional.empty());
            case STATIC_METHOD ->
                new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        coreParams,
                        code,
                        throwsClause,
                        Optional.empty());
        };
    }

    /// The last parameter of a method of variable arity, `T... v`: its type is the array `T[]` to
    /// the body, and is written by its component.
    private static Param variable(Param last) {
        return switch (last.type()) {
            case ArrayTypeRef array -> new Param(last.name(), array.component(), last.annotations(), true);
            case PrimitiveTypeRef _, ClassOrInterfaceTypeRef _ ->
                throw new IllegalStateException(
                        "the last parameter of a method of variable arity is an array, got " + last.type());
        };
    }

    /// The `throws` clause of a member, as the scope of its body.
    private static ExceptionScope.Declares declares(Invocable member) {
        return new ExceptionScope.Declares(
                switch (member.kind()) {
                    case CONSTRUCTOR -> ExceptionScope.Boundary.CONSTRUCTOR;
                    case INSTANCE_METHOD, STATIC_METHOD -> ExceptionScope.Boundary.METHOD;
                },
                ExceptionType.ofAll(member.traits().throwsTypes()));
    }

    /// A member of the class, in the order it was declared.
    private sealed interface Slot {
        /// A slot that waits for what defines it.
        sealed interface Open extends Slot {
            /// The member as it is declared.
            Member declared();
        }

        /// A method or constructor that has no body yet.
        ///
        /// @param declared the member as it was declared
        record Awaiting(Member.Callable declared) implements Open {}

        /// A field that has no initializer yet.
        ///
        /// @param declared the field as it was declared
        record Blank(Member.Field declared) implements Open {}

        /// A member that is there whole.
        ///
        /// @param declared the member as it was declared
        /// @param member the member as it is rendered
        record Defined(Member declared, ClassMember member) implements Slot {}
    }

    // ------------------------------------------------------------------
    // Bodies of instance members (§6.5 HOAS: the block, `this`, the parameters)
    // ------------------------------------------------------------------

    /// The body of an instance method or constructor without parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    @FunctionalInterface
    public interface InstanceBody0<Self, B, R> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self);
    }

    /// The body of an instance method or constructor with one parameter.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the parameter type
    @FunctionalInterface
    public interface InstanceBody1<Self, B, R, A1> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self, Var<A1> p1);
    }

    /// The body of an instance method or constructor with two parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    @FunctionalInterface
    public interface InstanceBody2<Self, B, R, A1, A2> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the first parameter
        /// @param p2 the second parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2);
    }
}
