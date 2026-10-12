package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
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
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody0;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody1;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody2;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/// The builder of the members of a class being declared (§6.5), branded
/// with `Self`: what [TypedClassBuilder] — of a class that extends `Object` —
/// and [TypedSubclassBuilder] — of a class with another superclass — have in
/// common, which is everything but what depends on the superclass. The
/// fields, the definitions of the declared members and the escape hatch are
/// here; the declarations of the methods are those of [Declarations].
///
/// See [TypedClassBuilder] for how members are declared and defined.
///
/// @param <Self> the brand of the class being declared
public abstract sealed class MemberBuilder<Self> extends Declarations<Self>
        permits TypedClassBuilder, TypedSubclassBuilder {
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");

    private final TargetClasspath target;
    private final String described;
    private final FinalClassToken<Self> self;
    private final Superclass superclass;
    private final SignatureRegistry registry;
    private final ClassDeclaration<Self> owner;
    private List<Slot> slots = List.of();
    private Declaration declaration = new Declaration.Open();

    MemberBuilder(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        this.superclass = superclass;
        this.target = declared.target();
        this.described = declared.described();
        this.self = declared.self();
        this.registry = new SignatureRegistry(described, inherited, declared.bounded());
        this.owner = declared;
    }

    /// The scope of the types a member mentions: of an instance member, or of a `static` one (JLS 8.1.2).
    private TypeScope scope(InvocableKind kind, String where) {
        return switch (kind) {
            case CONSTRUCTOR, INSTANCE_METHOD -> owner.scope(where);
            case STATIC_METHOD -> owner.staticScope(where);
        };
    }

    /// Holds the types of the signature of a member to its scope, where it is declared.
    private void requireInScope(Invocable member) {
        TypeScope scope = scope(member.kind(), "the signature of " + member);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(scope::require);
    }

    /// The fact of the class being declared: a [FinalClassToken], usable as
    /// the type of expressions, variables and members of the class. Its
    /// [FinalClassToken#methods()] is known once the declaration completes.
    ///
    /// @return the self token
    public final FinalClassToken<Self> self() {
        return self;
    }

    final MethodTable methodTable() {
        return switch (declaration) {
            case Declaration.Open _ ->
                throw new IllegalStateException("the methods of " + described + " are known only once its"
                        + " declaration is complete: the class is still being declared");
            case Declaration.Complete(var methods) -> methods;
        };
    }

    @Override
    final MemberBuilder<Self> builder() {
        return this;
    }

    @Override
    final List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` field with an initializer. The initializer
    /// is not in the scope of any body, so it cannot use `this` or a variable,
    /// nor throw a checked exception.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the initializer can throw a checked exception
    public <T> FieldRef<Self, T> field(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        FieldRef<Self, T> field = UnsafeFacts.field(self, name, type);
        declareField(name, type, Set.of(Modifier.PUBLIC, Modifier.FINAL), initializer);
        return field;
    }

    /// Declares a `public` field, not `final`, with an initializer.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the initializer can throw a checked exception
    public <T> MutableFieldRef<Self, T> mutableField(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        MutableFieldRef<Self, T> field = UnsafeFacts.mutableField(self, name, type);
        declareField(name, type, Set.of(Modifier.PUBLIC), initializer);
        return field;
    }

    private void declareField(String name, TypeToken<?> type, Set<Modifier> modifiers, Expr<?> initializer) {
        requireOpen("field " + name + " of " + described);
        owner.scope("the type of field " + name).require(type);
        registry.field(name);
        ScopeCheck.standalone(initializer.node(), "initializer of field " + name);
        Exceptions.initializer(initializer.node(), "initializer of field " + name);
        given(() -> new FieldDecl(
                name,
                type.typeRef(),
                List.of(),
                modifiers,
                Optional.of(new Lowering(
                                target,
                                new Lowering.Place.InClass(self, owner.scope("the initializer of field " + name)),
                                Optional.empty())
                        .lowerInitializer(initializer.node(), type)),
                Optional.empty()));
    }

    // ------------------------------------------------------------------
    // Definitions: each throws IllegalStateException if the member is
    // defined already, or is not a member declared by this builder
    // ------------------------------------------------------------------

    /// Defines a declared method; the body gets `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    public final <R> void define(MethodRef0<Self, R> method, InstanceBody0<Self, Body<R>, R> body) {
        Slot.Declared slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        define(slot, member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    public final <R, A1> void define(MethodRef1<Self, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        Slot.Declared slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public final <R, A1, A2> void define(
            MethodRef2<Self, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        Slot.Declared slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        define(slot, member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    /// Defines a declared `void` method; the body gets `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    public final void define(VoidMethodRef0<Self> method, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Declared slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        define(slot, member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    public final <A1> void define(VoidMethodRef1<Self, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Declared slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public final <A1, A2> void define(
            VoidMethodRef2<Self, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Declared slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        define(slot, member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    /// Defines a declared static method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    /// @param <R> the result type
    public final <R> void define(StaticMethodRef0<R> method, Function<Body<R>, Terminated<R>> body) {
        Slot.Declared slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), declares(method));
        define(slot, member(slot, root, List.of(), body));
    }

    /// Defines a declared static method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    public final <R, A1> void define(StaticMethodRef1<R, A1> method, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        Slot.Declared slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, member(slot, root, List.of(p1), b -> body.apply(b, p1)));
    }

    /// Defines a declared static `void` method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    public final void define(VoidStaticMethodRef0 method, Function<VoidBody, Terminated<Void>> body) {
        Slot.Declared slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), declares(method));
        define(slot, member(slot, root, List.of(), body));
    }

    /// Defines a declared static `void` method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <A1> the parameter type
    public final <A1> void define(
            VoidStaticMethodRef1<A1> method, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        Slot.Declared slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        define(slot, member(slot, root, List.of(p1), b -> body.apply(b, p1)));
    }

    /// Defines a declared constructor; the body gets `this`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block and `this`
    final void defineConstructor(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        define(slot, member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared constructor; the body gets `this` and the parameter.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    final <A1> void defineConstructor(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        define(slot, member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared constructor; the body gets `this` and the parameters.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    final <A1, A2> void defineConstructor(
            CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        define(slot, member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    // ------------------------------------------------------------------
    // What a class has of its superclass
    // ------------------------------------------------------------------

    /// Defines a declared constructor that begins with `super(...)`.
    final <S> void defineWithSuper(CtorRef0<Self> ctor, TypedSubclassBuilder.CtorBody0<Self, S> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        Supplier<ClassMember> member = member(slot, root, List.of(), b -> body.build(site));
        requireCalled(site, ctor);
        define(slot, member);
    }

    /// Defines a declared constructor with a parameter that begins with `super(...)`.
    final <S, A1> void defineWithSuper(CtorRef1<Self, A1> ctor, TypedSubclassBuilder.CtorBody1<Self, S, A1> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Supplier<ClassMember> member = member(slot, root, List.of(p1), b -> body.build(site, p1));
        requireCalled(site, ctor);
        define(slot, member);
    }

    /// Defines a declared constructor with two parameters that begins with `super(...)`.
    final <S, A1, A2> void defineWithSuper(
            CtorRef2<Self, A1, A2> ctor, TypedSubclassBuilder.CtorBody2<Self, S, A1, A2> body) {
        Slot.Declared slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", declares(ctor));
        SuperSite<Self, S> site = new SuperSite<>(superclass, root, thisOf(root));
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Supplier<ClassMember> member = member(slot, root, List.of(p1, p2), b -> body.build(site, p1, p2));
        requireCalled(site, ctor);
        define(slot, member);
    }

    private void requireCalled(SuperSite<Self, ?> site, Invocable ctor) {
        if (!site.called()) {
            throw new IllegalStateException("the body of " + ctor + " of " + described + " does not begin with"
                    + " super(...): end it by ctor.super_(constructor, arguments, rest)");
        }
    }

    /// The fact of a `protected` member of a class this one is a subclass of.
    final <F extends MemberFact> F opened(Protected<?, F> held) {
        if (!self.isSubclassOf(held.owner().erasure())) {
            throw new IllegalArgumentException(described + " cannot reach " + held + ": it is no subclass of "
                    + held.owner() + ", and a protected member is reached by a subclass alone (JLS 6.6.2)");
        }
        return UnsafeFacts.open(held);
    }

    /// `super.method(args)`, on `this` of this class.
    final Node superCall(This<Self> receiver, Invocable method, List<Node.Operand> args) {
        if (receiver.type() != self || !(receiver.node() instanceof Node.This(var owner))) {
            throw new IllegalArgumentException("super." + method.name() + "(...) is called on this of " + described
                    + ", got this of " + receiver.type());
        }
        Inheritance.Group group = superclass.member(described, method);
        Inheritance.Entry member = group.stands();
        switch (group.standing()) {
            case DEFAULT, CONCRETE, FINAL -> {}
            case ABSTRACT ->
                throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                        + " calls no body: " + member + " is abstract in " + superclass.token() + " (JLS 15.12.3)");
            case STATIC ->
                throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                        + " calls an instance method, and " + member + " is static in " + superclass.token());
        }
        switch (member.method().visibility()) {
            case PUBLIC, PROTECTED -> {}
            case PACKAGE ->
                throw new IllegalArgumentException("super." + method.name() + "(...) of " + described
                        + " cannot call " + member + ": the method has package access and the class is in its"
                        + " package — whether the class reaches it depends on the packages of the classes"
                        + " between, which the typed layer does not tell");
        }
        return new Node.SuperCall(owner, superclass.token(), method, args);
    }

    // ------------------------------------------------------------------
    // Escape hatch
    // ------------------------------------------------------------------

    /// Adds a raw, untyped member from [Unsafe#member(me.supcheg.javafile.model.ClassMember)].
    /// It is not checked against the signatures of the typed members.
    ///
    /// @param member the member
    /// @return this builder
    public MemberBuilder<Self> add(UnsafeMember member) {
        requireOpen("an unsafe member of " + described);
        given(member::member);
        return this;
    }

    // ------------------------------------------------------------------
    // Completion
    // ------------------------------------------------------------------

    /// Completes the declaration: every declared member must be defined.
    ///
    /// The members are lowered only now, when the method table of [#self()]
    /// is complete: lowering consults it to pin the overload a call of a
    /// method of this class names, and a body may call a method whose
    /// overloads are declared after it.
    ///
    /// @return the members, in declaration order
    /// @throws IllegalStateException if a declared member is not defined
    final List<ClassMember> complete() {
        List<String> undefined = slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Declared declared -> Stream.of(declared.fact().toString());
                    case Slot.Defined _, Slot.Given _ -> Stream.<String>empty();
                })
                .toList();
        if (!undefined.isEmpty()) {
            throw new IllegalStateException("the declaration of " + described
                    + " is complete, but these declared members are never defined: "
                    + String.join(", ", undefined) + "; define each declared member exactly once");
        }
        registry.requireComplete();
        declaration = new Declaration.Complete(registry.table());
        return slots.stream()
                .map(slot -> switch (slot) {
                    case Slot.Defined(var _, var member) -> member.get();
                    case Slot.Given(var member) -> member.get();
                    case Slot.Declared declared ->
                        throw new IllegalStateException(declared.fact() + " was held defined, and is not");
                })
                .toList();
    }

    // ------------------------------------------------------------------

    private void requireOpen(String what) {
        switch (declaration) {
            case Declaration.Open _ -> Scopes.requireNoneOpen(what);
            case Declaration.Complete _ ->
                throw new IllegalStateException("cannot declare or define " + what + ": the declaration of "
                        + described + " is already complete; a class declares and defines its members in the"
                        + " specification its declaration was given, while that runs");
        }
    }

    /// Declares a method or constructor of the class.
    ///
    /// @param member its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `member`
    <F extends Invocable> F declare(F member, List<ClassToken<? extends Throwable>> thrown) {
        String what = member.toString();
        requireOpen(what);
        requireInScope(member);
        registry.member(member);
        declared(new Slot.Declared(member, thrown, Slot.Kind.DECLARES));
        return member;
    }

    /// Declares a method of the class that overrides the methods of its signature the class
    /// inherits; rendered with `@Override`.
    ///
    /// @param own its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `own`
    /// @throws IllegalArgumentException if the class inherits no such method, or cannot override it
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown) {
        String what = own.toString();
        requireOpen(what);
        requireInScope(own);
        // What a result is a subtype of is told by its metamodel, which has to hold on the target.
        own.resultType().ifPresent(target::verify);
        registry.override(own);
        declared(new Slot.Declared(own, thrown, Slot.Kind.OVERRIDES));
        return own;
    }

    /// A member that is no method and no constructor — a field, an unsafe member: it is there whole.
    private void given(Supplier<ClassMember> member) {
        declared(new Slot.Given(member));
    }

    private void declared(Slot slot) {
        slots = Stream.concat(slots.stream(), Stream.of(slot)).toList();
    }

    /// The declared member gets its body.
    private void define(Slot.Declared declared, Supplier<ClassMember> member) {
        slots = slots.stream()
                .map(slot -> slot == declared ? new Slot.Defined(declared, member) : slot)
                .toList();
    }

    /// The slot of a member that is declared and not defined yet. A member is known by its fact
    /// itself: two facts that tell the same are two members.
    private Slot.Declared definable(Invocable member) {
        String what = member.toString();
        requireOpen(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Declared declared when declared.fact() == member -> Stream.of(declared);
                    case Slot.Defined(var declared, var _)
                    when declared.fact() == member ->
                        throw new IllegalStateException(what + " of " + described
                                + " is already defined; each declared member is defined exactly once");
                    case Slot.Declared _, Slot.Defined _, Slot.Given _ -> Stream.<Slot.Declared>empty();
                })
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot define " + what
                        + ": it was not declared by the builder of " + described
                        + "; define only the members declared through its declare* methods"));
    }

    private This<Self> thisOf(Block<?, ?> root) {
        return new This<>(root, self);
    }

    /// Builds `root` — the root of its scope tree — from `bodyFn`, and
    /// returns the lowering of the member it is the body of, run on
    /// [#complete()].
    private <R, B extends Block<R, B>> Supplier<ClassMember> member(
            Slot.Declared slot, B root, List<Var<?>> params, Function<? super B, Terminated<R>> bodyFn) {
        Terminated<R> terminated = Scopes.within(root, () -> bodyFn.apply(root));
        Block.requireIssuedBy(terminated, root);
        return () -> lower(slot, root, params);
    }

    private ClassMember lower(Slot.Declared slot, Block<?, ?> root, List<Var<?>> params) {
        Invocable fact = slot.fact();
        Lowering lowering = new Lowering(
                target, new Lowering.Place.InClass(self, scope(fact.kind(), "the body of " + fact)), fact.resultType());
        List<Param> coreParams = params.stream()
                .map(param ->
                        new Param(lowering.declareUpfront(param), param.type().typeRef()))
                .toList();
        CodeBody code = lowering.lowerBlock(root.instrs());
        List<ClassOrInterfaceTypeRef> throwsClause = slot.thrown().stream()
                .map(type -> lowering.named(type).typeRef())
                .toList();
        List<AnnotationUse> annotations =
                switch (slot.kind()) {
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

    /// The `throws` clause of a member, as the scope of its body.
    private static ExceptionScope.Declares declares(Invocable member) {
        return new ExceptionScope.Declares(
                switch (member.kind()) {
                    case CONSTRUCTOR -> ExceptionScope.Boundary.CONSTRUCTOR;
                    case INSTANCE_METHOD, STATIC_METHOD -> ExceptionScope.Boundary.METHOD;
                },
                ExceptionType.ofAll(member.traits().throwsTypes()));
    }

    /// Whether the class is still being declared.
    private sealed interface Declaration {
        /// Members are being declared and defined.
        record Open() implements Declaration {}

        /// The declaration is complete.
        ///
        /// @param methods the methods of the class
        record Complete(MethodTable methods) implements Declaration {}
    }

    /// A member of the class, in the order it was declared.
    private sealed interface Slot {

        /// Whether a method overrides those the class inherits: rendered with `@Override`.
        enum Kind {
            /// A method or constructor declared by its name.
            DECLARES,
            /// A method that overrides.
            OVERRIDES
        }

        /// A method or constructor that is declared and has no body yet.
        ///
        /// @param fact its fact
        /// @param thrown the exception classes of its `throws` clause
        /// @param kind whether it overrides
        record Declared(Invocable fact, List<ClassToken<? extends Throwable>> thrown, Kind kind) implements Slot {
            public Declared {
                thrown = List.copyOf(thrown);
            }
        }

        /// A method or constructor with its body.
        ///
        /// @param declared the member as it was declared
        /// @param member its lowering, run when the declaration completes
        record Defined(Declared declared, Supplier<ClassMember> member) implements Slot {}

        /// A member that is there whole from the first: a field, an unsafe member.
        ///
        /// @param member its lowering, run when the declaration completes
        record Given(Supplier<ClassMember> member) implements Slot {}
    }
}
