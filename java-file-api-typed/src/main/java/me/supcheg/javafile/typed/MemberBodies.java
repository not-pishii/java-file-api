package me.supcheg.javafile.typed;

import me.supcheg.javafile.Invariants;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.InitializerBlock;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.MemberBuilder.Member;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
public abstract sealed class MemberBodies<Self, S> extends MemberBodyArities<Self, S>
        permits TypedClassBodyArities, TypedSubclassBodyArities {
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");
    private static final ClassDesc SAFE_VARARGS = ClassDesc.of("java.lang", "SafeVarargs");

    private final ClassDeclaration<Self> owner;
    private final TargetClasspath target;
    private final String described;
    private final FinalClassToken<Self> self;
    private final Superclass superclass;
    private final Function<List<ClassMember>, String> written;
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
            Function<List<ClassMember>, String> written) {
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
                    case Member.StaticField staticField -> new Slot.BlankStatic(staticField);
                    case Member.Initializer initializer -> new Slot.AwaitingInitializer(initializer);
                    case Member.Given given -> new Slot.Defined(given, given.member());
                    case Member.SerialVersion serialVersion ->
                        new Slot.Defined(
                                serialVersion,
                                new FieldDecl(
                                        Member.SerialVersion.NAME,
                                        Types.LONG,
                                        List.of(new AnnotationUse(ClassDesc.of("java.io", "Serial"), List.of())),
                                        Set.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL),
                                        Optional.of(Exprs.literal(serialVersion.value())),
                                        Optional.empty()));
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
        Tokens.requireNoBoxing(initializer.type(), field.type(), "the initializer of field " + name);
        Slot.Blank slot = initializable(field);
        ScopeCheck.standalone(initializer.node(), "initializer of field " + name);
        Exceptions.initializer(initializer.node(), "initializer of field " + name);
        defined(
                slot,
                new FieldDecl(
                        name,
                        field.type().typeRef(),
                        slot.declared().annotations().stream()
                                .map(AnnotationRef::toUse)
                                .toList(),
                        slot.declared().modifiers(),
                        Optional.of(Lowering.instanceInitializer(
                                        target,
                                        new Lowering.Place.InClass(
                                                self,
                                                owner.scope("the initializer of field " + name),
                                                owner.bounded(),
                                                Set.of()),
                                        "the initializer of field " + name,
                                        instanceFieldsFrom(slot))
                                .lowerInitializer(initializer.node(), field.type())),
                        slot.declared().doc()),
                "the initializer of field " + name);
    }

    /// Gives a declared static field its initializer, immutable or mutable. The initializer is static: it is
    /// in no body's scope and in the scope of no type parameter of the class (JLS 8.1.2), so it can neither use
    /// `this` nor a variable, nor mention a type parameter, nor throw a checked exception; and it reads only the
    /// static fields the class declares before the field it initializes (JLS 8.3.3).
    ///
    /// A static field is no constant variable to the typed layer, whatever its initializer: its fact carries no
    /// value, so a read of it is no constant expression (JLS 4.12.4) and folding counts it as none.
    ///
    /// @param field the declared static field
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @throws IllegalStateException if the field has its initializer already or was not declared by this
    ///     class, if the initializer can throw a checked exception, or cannot be lowered or written
    ///     (see [#define(MethodRef0, InstanceBody0)])
    /// @throws IllegalArgumentException if the initializer reads a static field of the class that is declared
    ///     at or after the field it initializes (JLS 8.3.3)
    public final <T> void initialize(StaticFieldRef<T> field, Expr<? extends T> initializer) {
        String name = field.name();
        Tokens.requireNoBoxing(initializer.type(), field.type(), "the initializer of static field " + name);
        Slot.BlankStatic slot = staticInitializable(field);
        ScopeCheck.standalone(initializer.node(), "initializer of static field " + name);
        Exceptions.initializer(initializer.node(), "initializer of static field " + name);
        defined(
                slot,
                new FieldDecl(
                        name,
                        field.type().typeRef(),
                        slot.declared().annotations().stream()
                                .map(AnnotationRef::toUse)
                                .toList(),
                        slot.declared().modifiers(),
                        Optional.of(Lowering.staticInitializer(
                                        target,
                                        new Lowering.Place.InClass(
                                                self,
                                                owner.staticScope("the initializer of static field " + name),
                                                owner.bounded(),
                                                Set.of()),
                                        field,
                                        declaredFrom(field))
                                .lowerInitializer(initializer.node(), field.type())),
                        slot.declared().doc()),
                "the initializer of static field " + name);
    }

    /// The static fields of the class that the initializer of `field` may not read: `field` itself and each
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

    // ------------------------------------------------------------------
    // Initializer blocks
    // ------------------------------------------------------------------

    /// Defines a declared instance initializer block. Its body is built and lowered here, with `this`, against
    /// every member the class declared; it may throw a checked exception only if every constructor declares it
    /// (JLS 11.2.3), and it reads only the instance fields whose initializers have run before it.
    ///
    /// @param block the declared block
    /// @param body the body, given its block, `this`
    /// @throws IllegalStateException if the block is defined already or was not declared by this class, if the
    ///     body can throw a checked exception a constructor does not declare, or cannot be lowered or written
    /// @throws IllegalArgumentException if the body reads an instance field declared at or after the block
    public final void define(InstanceInitializerBlock block, InstanceBody0<Self, InitializerBody, Void> body) {
        String what = "the instance initializer block of " + described;
        Slot.AwaitingInitializer slot = initializer(block, what);
        InitializerBody root = InitializerBody.root(what, new ExceptionScope.InstanceInitializer(constructors()));
        This<Self> bodySelf = thisOf(root);
        requireCompletesNormally(built(root, b -> body.build(b, bodySelf)), what, "8.6");
        Lowering lowering = Lowering.instanceInitializer(
                target,
                new Lowering.Place.InClass(self(), owner.scope(what), owner.bounded(), Set.of()),
                what,
                instanceFieldsFrom(slot));
        defined(slot, new InitializerBlock(false, lowering.lowerBlock(root.instrs())), what);
    }

    /// Defines a declared static initializer block. Its body is built and lowered here, with no `this`, against
    /// every member the class declared; it throws no checked exception at all (JLS 11.2.3), and it reads only
    /// the static fields whose initializers have run before it.
    ///
    /// @param block the declared block
    /// @param body the body, given its block
    /// @throws IllegalStateException if the block is defined already or was not declared by this class, if the
    ///     body can throw a checked exception, or cannot be lowered or written
    /// @throws IllegalArgumentException if the body reads a static field declared at or after the block
    public final void define(StaticInitializerBlock block, Function<InitializerBody, Terminated<Void>> body) {
        String what = "the static initializer block of " + described;
        Slot.AwaitingInitializer slot = initializer(block, what);
        InitializerBody root = InitializerBody.root(what, new ExceptionScope.StaticInitializer());
        requireCompletesNormally(built(root, body), what, "8.7");
        Lowering lowering = Lowering.staticInitializer(
                target,
                new Lowering.Place.InClass(self(), owner.staticScope(what), owner.bounded(), Set.of()),
                what,
                staticFieldsFrom(slot));
        defined(slot, new InitializerBlock(true, lowering.lowerBlock(root.instrs())), what);
    }

    /// The slot of an initializer block that is declared and has no body yet.
    private Slot.AwaitingInitializer initializer(Object handle, String what) {
        owner.requireDefining(what);
        Slot found = slots.stream()
                .filter(slot -> slot.declared() instanceof Member.Initializer)
                .filter(slot -> ((Member.Initializer) slot.declared()).handle() == handle)
                .findFirst()
                .orElseThrow(() -> undeclared(what));
        if (found instanceof Slot.Defined) {
            throw alreadyDefined(((Member.Initializer) found.declared()).described());
        }
        return (Slot.AwaitingInitializer) found;
    }

    /// The constructors of the class, with what each declares, against which an instance initializer block is
    /// checked: a class that declares none has the implicit one, which declares nothing.
    private List<ExceptionScope.InstanceInitializer.Constructor> constructors() {
        List<ExceptionScope.InstanceInitializer.Constructor> declared = slots.stream()
                .map(Slot::declared)
                .filter(Member.Callable.class::isInstance)
                .map(Member.Callable.class::cast)
                .filter(callable -> callable.fact().kind() == InvocableKind.CONSTRUCTOR)
                .map(callable -> new ExceptionScope.InstanceInitializer.Constructor(
                        callable.fact().toString(), ExceptionType.ofAll(callable.thrown())))
                .toList();
        return declared.isEmpty()
                ? List.of(new ExceptionScope.InstanceInitializer.Constructor(
                        "the implicit constructor " + described + "()", List.of()))
                : declared;
    }

    /// The instance fields of the class that `block` may not read: those declared at or after it, in the order of
    /// the declarations.
    private List<FieldRef<?, ?>> instanceFieldsFrom(Slot block) {
        return slots.subList(slots.indexOf(block), slots.size()).stream()
                .map(Slot::declared)
                .flatMap(member -> member instanceof Member.Field field
                        ? Stream.<FieldRef<?, ?>>of(field.fact())
                        : Stream.<FieldRef<?, ?>>empty())
                .toList();
    }

    /// The static fields of the class that `block` may not read: those declared at or after it, in the order of
    /// the declarations.
    private List<StaticFieldRef<?>> staticFieldsFrom(Slot block) {
        return slots.subList(slots.indexOf(block), slots.size()).stream()
                .map(Slot::declared)
                .flatMap(member -> member instanceof Member.StaticField field
                        ? Stream.<StaticFieldRef<?>>of(field.fact())
                        : Stream.<StaticFieldRef<?>>empty())
                .toList();
    }

    // ------------------------------------------------------------------
    // Definitions: `define` of a method or constructor of every arity, and `superCall`, are generated into the
    // superclass of this class (MemberBodyArities, see TypedCodegen) and, for the constructors, into those of
    // the classes below (TypedClassBodyArities, TypedSubclassBodyArities). They call the hooks below.
    // ------------------------------------------------------------------

    /// The place of `super(...)` in the body `root` of a constructor.
    final SuperSite<Self, S> superSite(VoidBody root) {
        return new SuperSite<>(superclass, root, thisOf(root));
    }

    /// The constructor of `slot` is there whole: its body `root` began with `super(...)` at `site`.
    final void definedWithSuper(Slot.Awaiting slot, VoidBody root, SuperSite<Self, S> site, List<Var<?>> params) {
        defined(slot, root, params, new Start.Super(site.invocation()));
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

    /// `super.method(args)`, on `this` of this class.
    @Override
    Node superCallNode(This<Self> receiver, Invocable method, List<Node.Operand> args, Node.Passing passing) {
        if (receiver.type() != self) {
            throw new IllegalArgumentException("super." + method.name() + "(...) is called on this of " + described
                    + ", got this of " + receiver.type());
        }
        Inheritance.Group group = superclass.member(described, method);
        Inheritance.Entry member = group.stands();
        return switch (group.standing()) {
            case DEFAULT, CONCRETE, FINAL ->
                switch (member.method().visibility()) {
                    case PUBLIC, PROTECTED ->
                        new Node.SuperCall(receiver.body(), superclass.token(), method, args, passing);
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
                    case Slot.BlankStatic(var declared) ->
                        Stream.of(new Undefined("field " + declared.fact(), declared.site()));
                    case Slot.AwaitingInitializer(var declared) ->
                        Stream.of(new Undefined(declared.described(), declared.site()));
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
                                declared.annotations().stream()
                                        .map(AnnotationRef::toUse)
                                        .toList(),
                                declared.modifiers(),
                                Optional.empty(),
                                declared.doc()));
                    case Slot.BlankStatic(var declared) ->
                        Stream.of(new FieldDecl(
                                declared.fact().name(),
                                declared.fact().type().typeRef(),
                                declared.annotations().stream()
                                        .map(AnnotationRef::toUse)
                                        .toList(),
                                declared.modifiers(),
                                Optional.empty(),
                                declared.doc()));
                    case Slot.AwaitingInitializer _ -> Stream.<ClassMember>empty();
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
            written.apply(members(with));
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
    @Override
    Slot.Awaiting definable(Invocable member) {
        String what = member.toString();
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.Awaiting awaiting when awaiting.declared().fact() == member -> Stream.of(awaiting);
                    case Slot.Awaiting _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.Blank _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.BlankStatic _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.AwaitingInitializer _ -> Stream.<Slot.Awaiting>empty();
                    case Slot.Defined(Member.Callable declared, var _)
                    when declared.fact() == member -> throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.Awaiting>empty();
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
                    case Slot.Blank _ -> Stream.<Slot.Blank>empty();
                    case Slot.Awaiting _ -> Stream.<Slot.Blank>empty();
                    case Slot.BlankStatic _ -> Stream.<Slot.Blank>empty();
                    case Slot.AwaitingInitializer _ -> Stream.<Slot.Blank>empty();
                    case Slot.Defined(Member.Field declared, var _)
                    when declared.fact() == field -> throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.Blank>empty();
                })
                .findFirst()
                .orElseThrow(() -> undeclared(what));
    }

    /// The slot of a static field that is declared and has no initializer yet.
    private Slot.BlankStatic staticInitializable(StaticFieldRef<?> field) {
        String what = "field " + field;
        owner.requireDefining(what);
        return slots.stream()
                .flatMap(slot -> switch (slot) {
                    case Slot.BlankStatic blank when blank.declared().fact() == field -> Stream.of(blank);
                    case Slot.BlankStatic _ -> Stream.<Slot.BlankStatic>empty();
                    case Slot.Awaiting _ -> Stream.<Slot.BlankStatic>empty();
                    case Slot.Blank _ -> Stream.<Slot.BlankStatic>empty();
                    case Slot.AwaitingInitializer _ -> Stream.<Slot.BlankStatic>empty();
                    case Slot.Defined(Member.StaticField declared, var _)
                    when declared.fact() == field -> throw alreadyDefined(what);
                    case Slot.Defined _ -> Stream.<Slot.BlankStatic>empty();
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

    @Override
    This<Self> thisOf(Block<?, ?> root) {
        return new This<>(root, self);
    }

    /// The last parameter of a declared member, of `type`: that of a member of variable arity declared `@SafeVarargs`
    /// is
    /// one the body may read and store into and loop over, and may not let the array out of the member.
    static <T> Var<T> lastParameter(Slot.Awaiting slot, TypeToken<T> type, Block<?, ?> root) {
        Var.Role.Fixed role =
                switch (slot.declared().arity()) {
                    case Arity.Fixed _ -> Var.Role.Fixed.PARAMETER;
                    case Arity.Variable(var safety) ->
                        switch (safety) {
                            case UNCHECKED -> Var.Role.Fixed.PARAMETER;
                            case SAFE_VARARGS -> Var.Role.Fixed.SAFE_VARARGS_PARAMETER;
                        };
                };
        return new Var<>(type, role, root);
    }

    /// Builds `root` — the root of its scope tree — from `bodyFn`.
    static <R, B extends Block<R, B>> Terminated<R> built(B root, Function<? super B, Terminated<R>> bodyFn) {
        Terminated<R> terminated = Scopes.within(root, () -> bodyFn.apply(root));
        Block.requireIssuedBy(terminated, root);
        return terminated;
    }

    /// Holds an initializer block to what javac requires of it: it must be able to complete normally, so a
    /// `throw` of a whole body, a `loopForever` or an `ifElse` whose both branches end is refused (JLS 8.6,
    /// 8.7).
    ///
    /// @param ended the proof that the body ended
    /// @param what the block, for the message
    /// @param jls the section of the rule
    /// @throws IllegalArgumentException if the body cannot complete normally
    static void requireCompletesNormally(Terminated<?> ended, String what, String jls) {
        if (!ended.completesNormally()) {
            throw new IllegalArgumentException(what + " cannot complete normally: an initializer block must be"
                    + " able to complete normally (JLS " + jls + "), so a throw, a loop that never ends or a"
                    + " branch that does is refused where it ends the body — catch the exception inside the block,"
                    + " or let the block fall off its end with end()");
        }
    }

    /// The declared member gets the body `bodyFn` builds: built and lowered here.
    @Override
    <R, B extends Block<R, B>> void defineIn(
            Slot.Awaiting slot, B root, List<Var<?>> params, Function<? super B, Terminated<R>> bodyFn) {
        built(root, bodyFn);
        defined(slot, root, params);
    }

    /// The declared member gets the body built in `root`: lowered here, against all the class declared.
    private void defined(Slot.Awaiting slot, Block<?, ?> root, List<Var<?>> params) {
        defined(slot, root, params, new Start.Implicit());
    }

    /// Defines a declared member: a method, or a constructor that begins as `start` tells.
    private void defined(Slot.Awaiting slot, Block<?, ?> root, List<Var<?>> params, Start start) {
        defined(slot, lower(slot.declared(), root, params, start), "the " + root.path());
    }

    /// How the body of a constructor begins.
    private sealed interface Start {
        /// With no explicit invocation: javac calls `super()`. So does the body of every method.
        record Implicit() implements Start {}

        /// With `super(args)`.
        ///
        /// @param invocation the invocation
        record Super(SuperInvocation invocation) implements Start {}
    }

    /// The declared member is there whole.
    private void defined(Slot.Open from, ClassMember member, String what) {
        List<Slot> with = slots.stream()
                .map(slot -> slot == from ? new Slot.Defined(from.declared(), member) : slot)
                .toList();
        requireWritable(with, what);
        slots = with;
    }

    private ClassMember lower(Member.Callable declared, Block<?, ?> root, List<Var<?>> params, Start start) {
        Invocable fact = declared.fact();
        Lowering lowering = new Lowering(
                target,
                new Lowering.Place.InClass(self, declared.scope(), owner.bounded(), Set.of()),
                fact.resultType());
        List<Param> named = java.util.stream.IntStream.range(0, params.size())
                .mapToObj(index -> {
                    Var<?> param = params.get(index);
                    // the callable holds one list of annotations per parameter, as its builder makes it
                    List<AnnotationUse> annotations = declared.parameterAnnotations().get(index).stream()
                            .map(AnnotationRef::toUse)
                            .toList();
                    return new Param(
                            lowering.declareUpfront(param), param.type().typeRef(), annotations);
                })
                .toList();
        List<Param> coreParams =
                switch (declared.arity()) {
                    case Arity.Fixed _ -> named;
                    case Arity.Variable _ ->
                        Stream.concat(named.stream().limit(named.size() - 1L), Stream.of(variable(named.getLast())))
                                .toList();
                };
        List<AnnotationUse> annotations = Stream.of(
                        declared.annotations().stream().map(AnnotationRef::toUse),
                        switch (declared.kind()) {
                            case OVERRIDES -> Stream.of(new AnnotationUse(OVERRIDE, List.of()));
                            case DECLARES, ABSTRACT -> Stream.<AnnotationUse>empty();
                        },
                        safeVarargs(declared.arity()))
                .flatMap(Function.identity())
                .toList();
        return switch (fact.kind()) {
            case CONSTRUCTOR -> {
                ConstructorBody body =
                        switch (start) {
                            case Start.Implicit _ -> lowering.lowerConstructor(root.instrs());
                            case Start.Super(var invocation) -> lowering.lowerConstructor(invocation, root.instrs());
                        };
                yield new ConstructorDecl(
                        annotations,
                        Set.of(Modifier.PUBLIC),
                        coreParams,
                        body,
                        throwsClause(declared, lowering),
                        declared.doc());
            }
            case INSTANCE_METHOD -> {
                CodeBody code = lowering.lowerBlock(root.instrs());
                yield new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        annotations,
                        methodModifiers(declared, finalIfSafeVarargs(declared.arity())),
                        List.of(),
                        coreParams,
                        code,
                        throwsClause(declared, lowering),
                        declared.doc());
            }
            case STATIC_METHOD -> {
                CodeBody code = lowering.lowerBlock(root.instrs());
                yield new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        annotations,
                        methodModifiers(declared, Set.of(Modifier.PUBLIC, Modifier.STATIC)),
                        List.of(),
                        coreParams,
                        code,
                        throwsClause(declared, lowering),
                        declared.doc());
            }
        };
    }

    /// The modifiers of a method: those its form fixes, and the `synchronized`
    /// or `native` it was declared with.
    private static Set<Modifier> methodModifiers(Member.Callable declared, Set<Modifier> form) {
        if (declared.modifiers().isEmpty()) {
            return form;
        }
        return Stream.concat(form.stream(), declared.modifiers().stream())
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    /// `@SafeVarargs`, of a member of variable arity that vouches for the array of its last parameter: the one with
    /// a type that is not reifiable, which javac would warn of (JLS 9.6.4.7).
    private static Stream<AnnotationUse> safeVarargs(Arity arity) {
        return switch (arity) {
            case Arity.Fixed _ -> Stream.empty();
            case Arity.Variable(var safety) ->
                switch (safety) {
                    case UNCHECKED -> Stream.empty();
                    case SAFE_VARARGS -> Stream.of(new AnnotationUse(SAFE_VARARGS, List.of()));
                };
        };
    }

    /// The modifiers of an instance method: `public`, and `final` where it is `@SafeVarargs`, which javac accepts
    /// of a method that cannot be overridden alone (JLS 9.6.4.7).
    private static Set<Modifier> finalIfSafeVarargs(Arity arity) {
        return safeVarargs(arity).findAny().isPresent()
                ? Set.of(Modifier.PUBLIC, Modifier.FINAL)
                : Set.of(Modifier.PUBLIC);
    }

    /// The `throws` clause of a member, written after its body is lowered.
    private static List<ClassOrInterfaceTypeRef> throwsClause(Member.Callable declared, Lowering lowering) {
        return declared.thrown().stream()
                .<ClassOrInterfaceTypeRef>map(type -> lowering.named(type).typeRef())
                .toList();
    }

    /// The last parameter of a method of variable arity, `T... v`: its type is the array `T[]` to
    /// the body, and is written by its component.
    private static Param variable(Param last) {
        // the parameters of an override are those of the method it overrides, whose last is an array (Heritage.Plain)
        ArrayTypeRef array = Invariants.present(
                Optional.of(last.type()).filter(ArrayTypeRef.class::isInstance).map(ArrayTypeRef.class::cast),
                "the last parameter of a method of variable arity is an array, got %s",
                last.type());
        return new Param(last.name(), array.component(), last.annotations(), true);
    }

    /// The `throws` clause of a member, as the scope of its body.
    static ExceptionScope.Declares declares(Invocable member) {
        return new ExceptionScope.Declares(
                switch (member.kind()) {
                    case CONSTRUCTOR -> ExceptionScope.Boundary.CONSTRUCTOR;
                    case INSTANCE_METHOD, STATIC_METHOD -> ExceptionScope.Boundary.METHOD;
                },
                ExceptionType.ofAll(member.traits().throwsTypes()));
    }

    /// A member of the class, in the order it was declared.
    sealed interface Slot {
        /// The member as it was declared.
        Member declared();

        /// A slot that waits for what defines it.
        sealed interface Open extends Slot {}

        /// A method or constructor that has no body yet.
        ///
        /// @param declared the member as it was declared
        record Awaiting(Member.Callable declared) implements Open {}

        /// A field that has no initializer yet.
        ///
        /// @param declared the field as it was declared
        record Blank(Member.Field declared) implements Open {}

        /// A static field that has no initializer yet.
        ///
        /// @param declared the static field as it was declared
        record BlankStatic(Member.StaticField declared) implements Open {}

        /// An initializer block that has no body yet.
        ///
        /// @param declared the block as it was declared
        record AwaitingInitializer(Member.Initializer declared) implements Open {}

        /// A member that is there whole.
        ///
        /// @param declared the member as it was declared
        /// @param member the member as it is rendered
        record Defined(Member declared, ClassMember member) implements Slot {}
    }
}
