package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.ConstructorInvocation;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody3;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody4;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The bodies of the members of a type being declared (§6.5), branded with
/// `Self`: the stage [TypeDeclarationSupport#bodies] gives. Every declared
/// member is defined here, and each body is lowered where it is defined,
/// against all the members the type declared.
///
/// The forms reach arity 4 for the kinds of §6.5; the forms of the class
/// builder reach arity 12 (`DeclarationArities`, `MemberBodyArities`).
///
/// @param <Self> the brand of the type being declared
/// @param <M> the core member type of the kind
abstract class TypedTypeBodies<Self, M> {
    private final TypeDeclarationSupport<Self> owner;
    private final me.supcheg.javafile.facts.DeclaredToken<Self> self;
    private List<Slot> slots;

    /// What a type declared, as its bodies take it.
    ///
    /// @param self the token of the type
    /// @param inherited what the type inherits
    /// @param members its members, in the order they were declared
    /// @param methods the methods of the type, asked once they are known
    record Declared(
            me.supcheg.javafile.facts.DeclaredToken<?> self,
            Inheritance inherited,
            List<Member> members,
            java.util.function.Supplier<me.supcheg.javafile.facts.MethodTableTemplate> methods) {
        Declared {
            members = List.copyOf(members);
        }
    }

    TypedTypeBodies(TypeDeclarationSupport<Self> owner, Declared declared) {
        this.owner = owner;
        this.self = owner.self();
        this.slots = declared.members().stream()
                .<Slot>map(member -> switch (member) {
                    case Member.Callable callable ->
                        callable.kind() == Member.Kind.ABSTRACT
                                ? new Slot.Defined(callable, abstractMethod(callable))
                                : new Slot.Awaiting(callable);
                    case Member.Field field -> new Slot.Blank(field);
                    case Member.Constant constant -> new Slot.BlankConstant(constant);
                    case Member.StaticField staticField -> new Slot.BlankStatic(staticField);
                })
                .toList();
    }

    final TypeDeclarationSupport<Self> owner() {
        return owner;
    }

    final me.supcheg.javafile.facts.DeclaredToken<Self> self() {
        return self;
    }

    // ------------------------------------------------------------------
    // Definitions
    // ------------------------------------------------------------------

    private Slot.Awaiting definable(Invocable member) {
        String what = member.toString();
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting awaiting when awaiting.declared().fact() == member -> Stream.of(awaiting);
                    case Slot.Awaiting _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.Blank _, Slot.BlankConstant _, Slot.BlankStatic _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.Defined defined
                    when defined.declared() instanceof Member.Callable callable && callable.fact() == member ->
                        throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.Awaiting>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    private Slot.Blank initializable(FieldRef<?, ?> field) {
        String what = "field " + field;
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Blank blank when blank.declared().fact() == field -> Stream.of(blank);
                    case Slot.Blank _ -> Stream.<Slot.Blank>empty();
                    case Slot.Awaiting _, Slot.BlankConstant _, Slot.BlankStatic _ -> Stream.<Slot.Blank>empty();
                    case Slot.Defined defined
                    when defined.declared() instanceof Member.Field declared && declared.fact() == field ->
                        throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.Blank>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    /// The slot of a constant or of a static field that is declared and has no initializer yet.
    private Slot.AwaitingInitializer initializable(StaticFieldRef<?> field) {
        String what = "field " + field;
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.BlankConstant blank
                    when blank.declared().fact() == field -> Stream.<Slot.AwaitingInitializer>of(blank);
                    case Slot.BlankStatic blank
                    when blank.declared().fact() == field -> Stream.<Slot.AwaitingInitializer>of(blank);
                    case Slot.BlankConstant _, Slot.BlankStatic _ -> Stream.<Slot.AwaitingInitializer>empty();
                    case Slot.Awaiting _, Slot.Blank _ -> Stream.<Slot.AwaitingInitializer>empty();
                    case Slot.Defined defined
                    when defined.declared() instanceof Member.Constant constant && constant.fact() == field ->
                        throw alreadyDefined(what);
                    case Slot.Defined defined
                    when defined.declared() instanceof Member.StaticField staticField && staticField.fact() == field ->
                        throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.AwaitingInitializer>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    private IllegalStateException alreadyDefined(String what) {
        return new IllegalStateException(what + " of " + owner.described()
                + " is already defined; each declared member is defined" + " exactly once");
    }

    private IllegalStateException undeclared(String what) {
        return new IllegalStateException("cannot define " + what + ": it was not declared by the builder of "
                + owner.described() + "; define only the members declared through its declare* methods");
    }

    /// Gives a declared field its initializer.
    ///
    /// @param field the declared field
    /// @param initializer the initializer
    /// @param <T> the field type
    public final <T> void initialize(FieldRef<Self, T> field, Expr<? extends T> initializer) {
        String what = "field " + field.name();
        Tokens.requireNoBoxing(initializer.type(), field.type(), "the initializer of " + what);
        Slot.Blank slot = initializable(field);
        ScopeCheck.standalone(initializer.node(), "initializer of " + what);
        Exceptions.initializer(initializer.node(), "initializer of " + what);
        Lowering lowering = new Lowering(
                owner.target(),
                new Lowering.Place.InClass(self, owner.scope("the initializer of " + what), owner.bounded()),
                java.util.Optional.empty());
        VariableInitializer lowered = lowering.lowerInitializer(initializer.node(), field.type());
        defined(slot, field(slot.declared(), lowered), "the initializer of " + what);
    }

    /// Gives a declared constant or static field its initializer. A constant of an interface must be
    /// initialized by a constant expression (JLS 9.3.1); a static field of a record or of an enum need not be,
    /// and is no constant variable to the typed layer whatever its initializer: its fact carries no value, so a
    /// read of it is no constant expression (JLS 4.12.4).
    ///
    /// The initializer is static: it is in no body's scope and in the scope of no type parameter, so it can
    /// neither use `this` nor a variable, nor throw a checked exception, and it reads only the static fields the
    /// type declares before the one it initializes (JLS 8.3.3).
    ///
    /// @param field the declared constant or static field
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @throws IllegalStateException if the field has its initializer already or was not declared by this
    ///     type, or if the initializer can throw a checked exception
    /// @throws IllegalArgumentException if the initializer of a constant is no constant expression, or reads a
    ///     static field of the type declared at or after the field it initializes (JLS 8.3.3)
    public final <T> void initialize(StaticFieldRef<T> field, Expr<? extends T> initializer) {
        Slot.AwaitingInitializer slot = initializable(field);
        String what = slot instanceof Slot.BlankConstant ? "constant " + field.name() : "static field " + field.name();
        Tokens.requireNoBoxing(initializer.type(), field.type(), "the initializer of " + what);
        ScopeCheck.standalone(initializer.node(), "initializer of " + what);
        Exceptions.initializer(initializer.node(), "initializer of " + what);
        requireConstant(slot, initializer.node());
        VariableInitializer lowered = lower(slot, initializer, field.type());
        defined(
                slot,
                switch (slot) {
                    case Slot.BlankConstant constant -> constant(constant.declared(), lowered);
                    case Slot.BlankStatic staticField -> staticField(staticField.declared(), lowered);
                },
                "the initializer of " + what);
    }

    /// The initializer of a constant or of a static field, lowered: one of a static field reads only the
    /// fields of the type declared before the field it initializes (JLS 8.3.3).
    private VariableInitializer lower(Slot.AwaitingInitializer slot, Expr<?> initializer, TypeToken<?> type) {
        return switch (slot) {
            case Slot.BlankConstant constant ->
                new Lowering(
                                owner.target(),
                                new Lowering.Place.InClass(
                                        self,
                                        owner.staticScope("the initializer of constant "
                                                + constant.declared().fact().name()),
                                        owner.bounded()),
                                java.util.Optional.empty())
                        .lowerInitializer(initializer.node(), type);
            case Slot.BlankStatic staticField ->
                Lowering.staticInitializer(
                                owner.target(),
                                new Lowering.Place.InClass(
                                        self,
                                        owner.staticScope("the initializer of static field "
                                                + staticField.declared().fact().name()),
                                        owner.bounded()),
                                staticField.declared().fact(),
                                declaredFrom(staticField.declared().fact()))
                        .lowerInitializer(initializer.node(), type);
        };
    }

    /// The static fields of the type that the initializer of `field` may not read: `field` itself and each
    /// declared after it, in the order of the declarations (JLS 8.3.3).
    private List<StaticFieldRef<?>> declaredFrom(StaticFieldRef<?> field) {
        List<StaticFieldRef<?>> declared = slots.stream()
                .map(Slot::declared)
                .flatMap(member -> member instanceof Member.StaticField staticField
                        ? Stream.<StaticFieldRef<?>>of(staticField.fact())
                        : Stream.<StaticFieldRef<?>>empty())
                .toList();
        return declared.subList(declared.indexOf(field), declared.size());
    }

    /// Whether an initializer of a constant has to be a constant expression (JLS 9.3.1): an interface
    /// constant does, a static field of a record or enum need not.
    boolean requireConstantExpression() {
        return false;
    }

    private void requireConstant(Slot.AwaitingInitializer slot, Node node) {
        if (slot instanceof Slot.BlankConstant constant
                && requireConstantExpression()
                && Constants.fold(node) instanceof Constants.Folded.None) {
            throw new IllegalArgumentException(
                    "the initializer of constant " + constant.declared().fact() + " of "
                            + owner.described() + " is not a constant expression: a constant of an interface is"
                            + " initialized by one (JLS 9.3.1)");
        }
    }

    // ------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------

    public final <R> void define(MethodRef0<Self, R> method, InstanceBody0<Self, Body<R>, R> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), Start.IMPLICIT, b -> body.build(b, self));
    }

    public final <R, A1> void define(MethodRef1<Self, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        defineIn(slot, root, List.of(p1), Start.IMPLICIT, b -> body.build(b, self, p1));
    }

    public final <R, A1, A2> void define(
            MethodRef2<Self, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), Start.IMPLICIT, b -> body.build(b, self, p1, p2));
    }

    public final <R, A1, A2, A3> void define(
            MethodRef3<Self, R, A1, A2, A3> method, InstanceBody3<Self, Body<R>, R, A1, A2, A3> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), Start.IMPLICIT, b -> body.build(b, self, p1, p2, p3));
    }

    public final <R, A1, A2, A3, A4> void define(
            MethodRef4<Self, R, A1, A2, A3, A4> method, InstanceBody4<Self, Body<R>, R, A1, A2, A3, A4> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), Start.IMPLICIT, b -> body.build(b, self, p1, p2, p3, p4));
    }

    public final void define(VoidMethodRef0<Self> method, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), Start.IMPLICIT, b -> body.build(b, self));
    }

    public final <A1> void define(VoidMethodRef1<Self, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        defineIn(slot, root, List.of(p1), Start.IMPLICIT, b -> body.build(b, self, p1));
    }

    public final <A1, A2> void define(
            VoidMethodRef2<Self, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), Start.IMPLICIT, b -> body.build(b, self, p1, p2));
    }

    public final <A1, A2, A3> void define(
            VoidMethodRef3<Self, A1, A2, A3> method, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), Start.IMPLICIT, b -> body.build(b, self, p1, p2, p3));
    }

    public final <A1, A2, A3, A4> void define(
            VoidMethodRef4<Self, A1, A2, A3, A4> method, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), Start.IMPLICIT, b -> body.build(b, self, p1, p2, p3, p4));
    }

    public final <R> void define(StaticMethodRef0<R> method, Function<Body<R>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        defineIn(slot, root, List.of(), Start.IMPLICIT, body);
    }

    public final <R, A1> void define(StaticMethodRef1<R, A1> method, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        defineIn(slot, root, List.of(p1), Start.IMPLICIT, b -> body.apply(b, p1));
    }

    public final <R, A1, A2> void define(
            StaticMethodRef2<R, A1, A2> method, Function3<Body<R>, Var<A1>, Var<A2>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), Start.IMPLICIT, b -> body.apply(b, p1, p2));
    }

    public final <R, A1, A2, A3> void define(
            StaticMethodRef3<R, A1, A2, A3> method, Function4<Body<R>, Var<A1>, Var<A2>, Var<A3>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), Start.IMPLICIT, b -> body.apply(b, p1, p2, p3));
    }

    public final <R, A1, A2, A3, A4> void define(
            StaticMethodRef4<R, A1, A2, A3, A4> method,
            Function5<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Terminated<R>> body) {
        Slot.Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), Start.IMPLICIT, b -> body.apply(b, p1, p2, p3, p4));
    }

    public final void define(VoidStaticMethodRef0 method, Function<VoidBody, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        defineIn(slot, root, List.of(), Start.IMPLICIT, body);
    }

    public final <A1> void define(
            VoidStaticMethodRef1<A1> method, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        defineIn(slot, root, List.of(p1), Start.IMPLICIT, b -> body.apply(b, p1));
    }

    public final <A1, A2> void define(
            VoidStaticMethodRef2<A1, A2> method, Function3<VoidBody, Var<A1>, Var<A2>, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), Start.IMPLICIT, b -> body.apply(b, p1, p2));
    }

    public final <A1, A2, A3> void define(
            VoidStaticMethodRef3<A1, A2, A3> method,
            Function4<VoidBody, Var<A1>, Var<A2>, Var<A3>, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), Start.IMPLICIT, b -> body.apply(b, p1, p2, p3));
    }

    public final <A1, A2, A3, A4> void define(
            VoidStaticMethodRef4<A1, A2, A3, A4> method,
            Function5<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Terminated<Void>> body) {
        Slot.Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), Start.IMPLICIT, b -> body.apply(b, p1, p2, p3, p4));
    }

    // ------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------

    public final void define(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        defineConstructor(ctor, Start.IMPLICIT, body);
    }

    public final <A1> void define(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        defineConstructor(ctor, Start.IMPLICIT, body);
    }

    public final <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        defineConstructor(ctor, Start.IMPLICIT, body);
    }

    public final <A1, A2, A3> void define(
            CtorRef3<Self, A1, A2, A3> ctor, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        defineConstructor(ctor, Start.IMPLICIT, body);
    }

    public final <A1, A2, A3, A4> void define(
            CtorRef4<Self, A1, A2, A3, A4> ctor, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        defineConstructor(ctor, Start.IMPLICIT, body);
    }

    public final void defineDelegating(
            CtorRef0<Self> ctor, List<? extends Expr<?>> args, InstanceBody0<Self, VoidBody, Void> body) {
        defineConstructor(ctor, Start.delegating(args), body);
    }

    public final <A1> void defineDelegating(
            CtorRef1<Self, A1> ctor, List<? extends Expr<?>> args, InstanceBody1<Self, VoidBody, Void, A1> body) {
        defineConstructor(ctor, Start.delegating(args), body);
    }

    public final <A1, A2> void defineDelegating(
            CtorRef2<Self, A1, A2> ctor,
            List<? extends Expr<?>> args,
            InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        defineConstructor(ctor, Start.delegating(args), body);
    }

    public final <A1, A2, A3> void defineDelegating(
            CtorRef3<Self, A1, A2, A3> ctor,
            List<? extends Expr<?>> args,
            InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        defineConstructor(ctor, Start.delegating(args), body);
    }

    public final <A1, A2, A3, A4> void defineDelegating(
            CtorRef4<Self, A1, A2, A3, A4> ctor,
            List<? extends Expr<?>> args,
            InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        defineConstructor(ctor, Start.delegating(args), body);
    }

    private <R> void defineConstructor(Invocable ctor, Start start, InstanceBody0<Self, VoidBody, Void> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), start, b -> body.build(b, self));
    }

    private <R, A1> void defineConstructor(Invocable ctor, Start start, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(param(ctor, 0), root);
        defineIn(slot, root, List.of(p1), start, b -> body.build(b, self, p1));
    }

    private <R, A1, A2> void defineConstructor(
            Invocable ctor, Start start, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(param(ctor, 0), root);
        Var<A2> p2 = Var.param(param(ctor, 1), root);
        defineIn(slot, root, List.of(p1, p2), start, b -> body.build(b, self, p1, p2));
    }

    private <R, A1, A2, A3> void defineConstructor(
            Invocable ctor, Start start, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(param(ctor, 0), root);
        Var<A2> p2 = Var.param(param(ctor, 1), root);
        Var<A3> p3 = Var.param(param(ctor, 2), root);
        defineIn(slot, root, List.of(p1, p2, p3), start, b -> body.build(b, self, p1, p2, p3));
    }

    private <R, A1, A2, A3, A4> void defineConstructor(
            Invocable ctor, Start start, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        Slot.Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(param(ctor, 0), root);
        Var<A2> p2 = Var.param(param(ctor, 1), root);
        Var<A3> p3 = Var.param(param(ctor, 2), root);
        Var<A4> p4 = Var.param(param(ctor, 3), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), start, b -> body.build(b, self, p1, p2, p3, p4));
    }

    @SuppressWarnings("unchecked")
    private static <T> TypeToken<T> param(Invocable member, int index) {
        return (TypeToken<T>) member.params().get(index);
    }

    /// The last parameter of a declared member, of `type`.
    private static <T> Var<T> lastParameter(TypeToken<T> type, Block<?, ?> root) {
        return new Var<>(type, Var.Role.Fixed.PARAMETER, root);
    }

    // ------------------------------------------------------------------
    // Lowering
    // ------------------------------------------------------------------

    private This<Self> thisOf(Block<?, ?> root) {
        return new This<>(root, self);
    }

    private <R, B extends Block<R, B>> void defineIn(
            Slot.Awaiting slot, B root, List<Var<?>> params, Start start, Function<? super B, Terminated<R>> bodyFn) {
        MemberBodies.built(root, bodyFn);
        defined(slot, lower(slot.declared(), root, params, start), "the " + root.path());
    }

    private M lower(Member.Callable declared, Block<?, ?> root, List<Var<?>> params, Start start) {
        Invocable fact = declared.fact();
        Lowering lowering = new Lowering(
                owner.target(), new Lowering.Place.InClass(self, declared.scope(), owner.bounded()), fact.resultType());
        List<Param> named = IntStream.range(0, params.size())
                .mapToObj(index -> {
                    Var<?> param = params.get(index);
                    String name = compactParameterName(declared, index);
                    String declaredName =
                            name != null ? lowering.declareUpfront(param, name) : lowering.declareUpfront(param);
                    List<me.supcheg.javafile.annotation.AnnotationUse> annotations =
                            index < declared.parameterAnnotations().size()
                                    ? declared.parameterAnnotations().get(index).stream()
                                            .map(me.supcheg.javafile.facts.AnnotationRef::toUse)
                                            .toList()
                                    : List.of();
                    return new Param(declaredName, param.type().typeRef(), annotations);
                })
                .toList();
        List<Param> coreParams = named;
        List<me.supcheg.javafile.annotation.AnnotationUse> annotations = Stream.of(
                        declared.annotations().stream().map(me.supcheg.javafile.facts.AnnotationRef::toUse),
                        declared.overrides()
                                ? Stream.of(new me.supcheg.javafile.annotation.AnnotationUse(
                                        java.lang.constant.ClassDesc.of("java.lang", "Override"), List.of()))
                                : Stream.<me.supcheg.javafile.annotation.AnnotationUse>empty())
                .flatMap(Function.identity())
                .toList();
        List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> throwsClause = List.of();
        return switch (declared.kind()) {
            case CONSTRUCTOR, CANONICAL, COMPACT -> {
                ConstructorBody body =
                        switch (start) {
                            case Start.Implicit _ -> lowering.lowerConstructor(root.instrs());
                            case Start.This(var args) ->
                                new ConstructorBody.Explicit(
                                        CodeBody.EMPTY,
                                        new ConstructorInvocation.This(args.stream()
                                                .map(arg -> lowering.lowerExpr(arg.node()))
                                                .toList()),
                                        lowering.lowerBlock(root.instrs()));
                        };
                yield constructor(declared, body, coreParams, throwsClause, annotations);
            }
            case METHOD, ABSTRACT, DEFAULT, STATIC, PRIVATE, PRIVATE_STATIC, ACCESSOR -> {
                CodeBody code = lowering.lowerBlock(root.instrs());
                yield method(declared, code, coreParams, throwsClause, annotations);
            }
        };
    }

    /// The name of a parameter of a compact constructor, if the declaration gives one: the component
    /// name, which the compact constructor does not write but its body names.
    String compactParameterName(Member.Callable declared, int index) {
        return null;
    }

    private M abstractMethod(Member.Callable declared) {
        List<Param> params = IntStream.range(0, declared.fact().params().size())
                .mapToObj(index -> new Param(
                        "v" + index, declared.fact().params().get(index).typeRef()))
                .toList();
        List<me.supcheg.javafile.annotation.AnnotationUse> annotations = List.of();
        List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> throwsClause = List.of();
        return abstractMethod(declared, params, throwsClause, annotations);
    }

    // ------------------------------------------------------------------
    // Completion
    // ------------------------------------------------------------------

    /// Completes the declaration: every declared member must be defined.
    ///
    /// @return the members, in declaration order
    /// @throws IllegalStateException if a declared member is not defined
    final List<M> complete() {
        List<Undefined> undefined = slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting(var declared) ->
                        Stream.of(new Undefined(declared.fact().toString(), declared.site()));
                    case Slot.Blank(var declared) ->
                        Stream.of(new Undefined("field " + declared.fact(), declared.site()));
                    case Slot.BlankConstant(var declared) ->
                        Stream.of(new Undefined("constant " + declared.fact(), declared.site()));
                    case Slot.BlankStatic(var declared) ->
                        Stream.of(new Undefined("field " + declared.fact(), declared.site()));
                    case Slot.Defined _ -> Stream.<Undefined>empty();
                })
                .toList();
        if (!undefined.isEmpty()) {
            IllegalStateException rejection = new IllegalStateException(
                    "the declaration of " + owner.described() + " is complete, but these declared members are"
                            + " never defined: "
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
        @SuppressWarnings("unchecked")
        List<M> members = Stream.concat(
                        slots.stream().map(slot -> (M) ((Slot.Defined) slot).member()), extraMembers().stream())
                .toList();
        return members;
    }

    /// The members the type has without a declaration of its own, after those declared: the accessors of
    /// the components of a record.
    List<M> extraMembers() {
        return List.of();
    }

    private void defined(Slot.Open from, M member, String what) {
        List<Slot> with = slots.stream()
                .map(slot -> slot == from ? new Slot.Defined(from.declared(), member) : slot)
                .toList();
        slots = with;
    }

    private record Undefined(String what, Site site) {}

    /// How the body of a constructor begins.
    private sealed interface Start {
        Start IMPLICIT = new Implicit();

        record Implicit() implements Start {}

        record This(List<Expr<?>> args) implements Start {
            public This(List<Expr<?>> args) {
                this.args = List.copyOf(args);
            }
        }

        static Start delegating(List<? extends Expr<?>> args) {
            @SuppressWarnings("unchecked")
            List<Expr<?>> copied = (List<Expr<?>>) (List<?>) List.copyOf(args);
            return new This(copied);
        }
    }

    /// A member of the type, in the order it was declared.
    sealed interface Slot {
        /// The member as it was declared.
        Member declared();

        /// A slot that waits for what defines it.
        sealed interface Open extends Slot {}

        /// A slot of a constant or a static field, which waits for its initializer.
        sealed interface AwaitingInitializer extends Open {}

        record Awaiting(Member.Callable declared) implements Open {}

        record Blank(Member.Field declared) implements Open {}

        record BlankConstant(Member.Constant declared) implements AwaitingInitializer {}

        record BlankStatic(Member.StaticField declared) implements AwaitingInitializer {}

        record Defined(Member declared, Object member) implements Slot {}
    }

    // The core members of each kind.
    abstract M method(
            Member.Callable declared,
            CodeBody code,
            List<Param> params,
            List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> throwsClause,
            List<me.supcheg.javafile.annotation.AnnotationUse> annotations);

    abstract M abstractMethod(
            Member.Callable declared,
            List<Param> params,
            List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> throwsClause,
            List<me.supcheg.javafile.annotation.AnnotationUse> annotations);

    abstract M constructor(
            Member.Callable declared,
            ConstructorBody body,
            List<Param> params,
            List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> throwsClause,
            List<me.supcheg.javafile.annotation.AnnotationUse> annotations);

    abstract M field(Member.Field declared, VariableInitializer initializer);

    abstract M constant(Member.Constant declared, VariableInitializer initializer);

    abstract M staticField(Member.StaticField declared, VariableInitializer initializer);
}
