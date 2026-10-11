package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.typed.jdk.facts.java.lang.Object_;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The declaration of one class, as its stages share it: the header
/// ([ClassHeader], then [ObjectHeader] or [SubclassHeader]) and the members
/// ([TypedClassBuilder] or [TypedSubclassBuilder]).
///
/// A stage is a view of the declaration that offers what can be said at its
/// point; the declaration holds where it stands ([Phase]) and rejects a stage
/// used after the declaration moved on, which the types cannot — a stage is
/// an ordinary value a generator may keep.
///
/// The methods the class said it overrides before it had members
/// ([#intend]) are held here until the members begin: each is checked against
/// what the class inherits as soon as a supertype that has the method is
/// named, and all of them when the header ends.
///
/// @param <Self> the brand of the class being declared
final class ClassDeclaration<Self> {
    private final TargetClasspath target;
    private final String described;
    private final FinalClassToken<Self> self;
    private final List<Intention> intentions = new ArrayList<>();
    private final List<Implemented.ByInheritance> inheritances = new ArrayList<>();
    private final List<Protected<?, ?>> opened = new ArrayList<>();
    private Phase<Self> phase = new Phase.Named<>();

    ClassDeclaration(TargetClasspath target, ClassDesc desc) {
        this.target = target;
        this.described = "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName();
        this.self = UnsafeFacts.declaredClassToken(new ClassTypeRef(desc), this::superclasses, this::methodTable);
    }

    TargetClasspath target() {
        return target;
    }

    String described() {
        return described;
    }

    FinalClassToken<Self> self() {
        return self;
    }

    /// The superclass chain of the class, as its token tells it: known once the header names the
    /// superclass.
    private List<ClassDesc> superclasses() {
        return switch (phase) {
            case Phase.Named<Self> _ ->
                throw new IllegalStateException("the superclass of " + described + " is known only once its"
                        + " header names it: say extendsObject() or extends_(proof) first");
            case Phase.Heading<Self>(var _, var superclass, var _) -> superclass.chain();
            case Phase.Members<Self>(var _, var superclass, var _) -> superclass.chain();
        };
    }

    private MethodTable methodTable() {
        return switch (phase) {
            case Phase.Named<Self> _, Phase.Heading<Self> _ ->
                throw new IllegalStateException("the methods of " + described + " are known only once its"
                        + " declaration is complete: the class is still being declared");
            case Phase.Members<Self>(var builder, var _, var _) -> builder.methodTable();
        };
    }

    // ------------------------------------------------------------------
    // The header
    // ------------------------------------------------------------------

    /// The class extends `Object`: it inherits the methods of `Object` as the target has them.
    ///
    /// @throws IllegalStateException if the class has a superclass already
    void extendsObject() {
        Scopes.requireNoneOpen("the superclass of " + described);
        requireNamed();
        Inheritance.Supertype object = Inheritance.supertype(described, target, Object_.TOKEN);
        heading(Inheritance.of(described, object), new Superclass.OfObject(Object_.TOKEN, object), List.of());
    }

    /// The class extends the class of `proof`.
    ///
    /// @param proof the proof that the class has every abstract method of the superclass
    /// @param <S> the superclass
    /// @return the token of the superclass
    /// @throws IllegalArgumentException if the class cannot extend the type so, see [ClassHeader#extends_]
    /// @throws IllegalStateException if the class has a superclass already
    <S> ExtendableClassToken<S> extend(Complete<Self, S> proof) {
        Scopes.requireNoneOpen("the superclass of " + described);
        requireNamed();
        requireAbout(proof);
        if (!(proof.supertype() instanceof ExtendableClassToken<S> superclass)) {
            throw new IllegalArgumentException(described + " cannot extend " + proof.supertype()
                    + ": it is an interface, which a class implements (JLS 8.1.4)");
        }
        target.verify(superclass);
        Inheritance.Supertype heritage = Inheritance.supertype(described, target, superclass);
        Inheritance inherited = Inheritance.of(described, heritage);
        requireHolds(proof, inherited, "extend").forEach(stepped -> {
            switch (stepped) {
                case Stepped.Own _ -> {}
                case Stepped.Inherited(var method) ->
                    throw new IllegalArgumentException(described + " cannot have " + method + " by inheritance"
                            + " from " + superclass + " for the step of " + superclass + " itself: an abstract"
                            + " method of the superclass has no body there, the class overrides it"
                            + " (JLS 8.1.1.1)");
            }
        });
        heading(inherited, new Superclass.Named(superclass, heritage), List.of());
        return superclass;
    }

    /// Takes the fact of a `protected` member out before the superclass is named: that the class
    /// is a subclass of its owner is checked when the superclass is ([#heading]).
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalStateException if the class has its superclass already
    <F extends MemberFact> F open(Protected<?, F> held) {
        requireNamed();
        opened.add(held);
        return UnsafeFacts.open(held);
    }

    private void requireNamed() {
        switch (phase) {
            case Phase.Named<Self> _ -> {}
            case Phase.Heading<Self> _, Phase.Members<Self> _ ->
                throw new IllegalStateException(described + " has its superclass already: a class header is"
                        + " used once, and what follows is declared through the stage it gave");
        }
    }

    private void requireAbout(Complete<Self, ?> proof) {
        if (proof.self() != self) {
            throw new IllegalArgumentException(
                    "the proof that " + proof + " is not a proof about " + described + ", which is " + self);
        }
    }

    /// The class says it overrides `overridden` by the method `own`, to be defined among its members.
    ///
    /// @param overridden the fact of the overridden method
    /// @param own the fact of the method the class declares
    /// @param thrown the exception classes of the `throws` clause of `own`
    /// @return `own`
    /// @throws IllegalArgumentException if the method cannot be overridden, or the class overrides a
    ///     method of that signature already
    /// @throws IllegalStateException if the header of the class has ended
    <F extends Invocable> F intend(Invocable overridden, F own, List<ClassToken<? extends Throwable>> thrown) {
        Scopes.requireNoneOpen("an override of " + described);
        if (phase instanceof Phase.Members<Self>) {
            throw new IllegalStateException("cannot say that " + described + " overrides " + overridden
                    + ": the header of the class has ended; a method is overridden among the members by"
                    + " override(fact, body)");
        }
        if (overridden.traits().overridability() == Overridability.FINAL) {
            throw new IllegalArgumentException(
                    described + " cannot override " + overridden + ": the method is final, or static (JLS 8.4.3.3)");
        }
        if (!overridden.traits().typeArgs().isEmpty()) {
            throw new IllegalArgumentException(described + " cannot override " + overridden
                    + ": the method is generic, and the typed layer declares no generic method");
        }
        MethodSignature signature = own.signature();
        intentions.stream()
                .filter(intention -> intention.own().signature().equals(signature))
                .findFirst()
                .ifPresent(intention -> {
                    throw new IllegalArgumentException(described + " overrides " + signature + " already, as "
                            + intention.overridden() + ": a class declares a method once (JLS 8.4.2) — give"
                            + " the one implementation to every step that takes the method");
                });
        Intention intention = new Intention(overridden, own, thrown);
        if (phase instanceof Phase.Heading<Self>(var inherited, var _, var _)) {
            requireOverrides(inherited, Stream.of(intention));
        }
        intentions.add(intention);
        return own;
    }

    /// The class says it has `method` by inheriting it from its superclass, with its body: what a
    /// step of the proof that the class implements an interface takes for a method the superclass
    /// implements already.
    ///
    /// @param method the fact of the method of the superclass
    /// @return how the class has the method
    /// @throws IllegalArgumentException if the superclass has no such method on the target classpath, or
    ///     one that is abstract, `static` or not `public`, or of another result or `throws` clause than
    ///     the fact tells
    /// @throws IllegalStateException if the class has no superclass yet, or its header has ended
    Implemented.ByInheritance inherit(Invocable method) {
        Scopes.requireNoneOpen("an inherited method of " + described);
        if (!(phase instanceof Phase.Heading<Self>(var _, var superclass, var _))) {
            throw new IllegalStateException(described + " cannot be said to inherit " + method
                    + " now: what a class has from its superclass is said after the superclass is named"
                    + " and before the members");
        }
        superclass.member(described, method).forEach(member -> {
            switch (member.method().dispatch()) {
                case CONCRETE, FINAL -> {}
                case ABSTRACT, DEFAULT, STATIC ->
                    throw new IllegalArgumentException(described + " does not have " + member + " with a body"
                            + " of its superclass " + superclass.token() + ": the method is "
                            + member.method().dispatch().name().toLowerCase(java.util.Locale.ROOT)
                            + " there, and only a method a class declares with a body implements the"
                            + " abstract method of an interface for a subclass (JLS 8.4.8.4)");
            }
            switch (member.method().visibility()) {
                case PUBLIC -> {}
                case PROTECTED, PACKAGE ->
                    throw new IllegalArgumentException(described + " cannot have " + member + " of its superclass "
                            + superclass.token() + " for the method of an interface: it is not public there,"
                            + " and the method of an interface is (JLS 8.4.8.3) — override it");
            }
        });
        Implemented.ByInheritance inheritance = new Implemented.ByInheritance(method);
        inheritances.add(inheritance);
        return inheritance;
    }

    /// The class implements the interface of `proof`.
    ///
    /// @param proof the proof that the class has every abstract method of the interface
    /// @param <I> the interface
    /// @return the witness that the class is of the interface
    /// @throws IllegalArgumentException if the class cannot implement the type so, see [ObjectHeader#implements_]
    /// @throws IllegalStateException if the class has no superclass yet, or its header has ended
    <I> Upcast<Self, I> implement(Complete<Self, I> proof) {
        Scopes.requireNoneOpen("a superinterface of " + described);
        if (!(phase instanceof Phase.Heading<Self>(var inherited, var superclass, var interfaces))) {
            throw new IllegalStateException(described + " cannot implement " + proof.supertype()
                    + " now: a superinterface is named after the superclass and before the members");
        }
        DeclaredToken<I> supertype = proof.supertype();
        requireAbout(proof);
        if (!(supertype instanceof InterfaceToken<I>)) {
            throw new IllegalArgumentException(described + " cannot implement " + supertype
                    + ": it is a class, which a class extends (JLS 8.1.5)");
        }
        target.verify(supertype);
        Inheritance merged = inherited.with(Inheritance.supertype(described, target, supertype));
        requireHolds(proof, merged, "implement").forEach(stepped -> {
            switch (stepped) {
                case Stepped.Own _ -> {}
                case Stepped.Inherited(var method) ->
                    merged.group(method.signature())
                            .ifPresent(
                                    group -> SignatureRegistry.requireInherited(described, method, supertype, group));
            }
        });
        requireOverrides(merged, intentions.stream());
        heading(
                merged,
                superclass,
                Stream.concat(interfaces.stream(), Stream.of(supertype.typeRef()))
                        .toList());
        return new Upcast<>(self, supertype);
    }

    /// Holds `proof` against its type as the target classpath has it, among what the class would
    /// inherit with it: the type has no abstract method the proof has no step for, and every step is
    /// of a method the type has.
    ///
    /// @param proof the proof
    /// @param merged what the class inherits with the type of the proof
    /// @param verb what the class does with the type, for messages
    /// @return how the class has the method of each step
    private List<Stepped> requireHolds(Complete<Self, ?> proof, Inheritance merged, String verb) {
        DeclaredToken<?> supertype = proof.supertype();
        List<Stepped> stepped = proof.steps().stream().map(this::stepped).toList();
        Set<MethodTableTemplate.Signature> proven =
                proof.steps().stream().map(Complete.Step::method).collect(Collectors.toSet());
        merged.groups()
                .flatMap(group -> group.entries().stream())
                .filter(entry -> entry.through() == supertype
                        && entry.isAbstract()
                        && !proven.contains(entry.method().signature()))
                .findFirst()
                .ifPresent(entry -> {
                    throw new IllegalArgumentException(described + " cannot " + verb + " " + supertype + " by "
                            + proof + ": on the target classpath the type has the abstract method " + entry
                            + ", which the proof has no step for — the generator was compiled against another"
                            + " version of the type");
                });
        stepped.stream()
                .filter(step -> merged.group(step.signature()).isEmpty())
                .findFirst()
                .ifPresent(step -> {
                    throw new IllegalArgumentException(described + " cannot " + verb + " " + supertype + " by "
                            + proof + ": the proof has a step for " + step.satisfies() + ", and on the target"
                            + " classpath the type has no such method — the generator was compiled against"
                            + " another version of the type");
                });
        return stepped;
    }

    /// How the class has the method of a step of a proof: by one it said it overrides, or by one it
    /// said it inherits.
    private Stepped stepped(Complete.Step<Self> step) {
        return switch (step.how().how()) {
            case Implemented.ByOverride(Invocable overridden, Invocable own) ->
                intentions.stream()
                        .filter(intention -> intention.own() == own)
                        .findFirst()
                        .<Stepped>map(Stepped.Own::new)
                        .orElseThrow(() -> new IllegalArgumentException("the implementation of " + overridden
                                + " is not one " + described + " made: a step takes what overriding(fact) of the"
                                + " header of the class gave"));
            case Implemented.ByInheritance inheritance ->
                inheritances.stream()
                        .filter(made -> made == inheritance)
                        .findFirst()
                        .<Stepped>map(made -> new Stepped.Inherited(made.inherited()))
                        .orElseThrow(() -> new IllegalArgumentException(described + " cannot have "
                                + inheritance.inherited() + " by inheritance: "
                                + switch (phase) {
                                    case Phase.Heading<Self>(var _, Superclass.OfObject _, var _) ->
                                        "it extends Object, which has no such method";
                                    case Phase.Named<Self> _, Phase.Heading<Self> _, Phase.Members<Self> _ ->
                                        "a step takes what inherited(fact) of the header of the class gave";
                                }));
        };
    }

    /// The header of a class that extends `Object` ends and the members begin.
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method it does not inherit
    /// @throws IllegalStateException if the class has no superclass yet, or has its members already
    TypedClassBuilder<Self> body() {
        return members((inherited, superclass) -> new TypedClassBuilder<>(this, inherited, superclass));
    }

    /// The header of a class that extends `superclass` ends and the members begin.
    ///
    /// @param superclass the superclass, as [#extend] gave it
    /// @param <S> the superclass
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method it does not inherit
    /// @throws IllegalStateException if the class has no superclass yet, or has its members already
    <S> TypedSubclassBuilder<Self, S> body(ExtendableClassToken<S> superclass) {
        return members((inherited, extended) -> new TypedSubclassBuilder<>(this, inherited, extended, superclass));
    }

    /// The header ends and the members begin: every method the class said it overrides is one it
    /// inherits, and is declared.
    private <B extends MemberBuilder<Self>> B members(java.util.function.BiFunction<Inheritance, Superclass, B> make) {
        Scopes.requireNoneOpen("the members of " + described);
        if (!(phase instanceof Phase.Heading<Self>(var inherited, var superclass, var interfaces))) {
            throw new IllegalStateException("the members of " + described + " do not begin here: they follow"
                    + " the header of the class, once");
        }
        B builder = make.apply(inherited, superclass);
        phase = new Phase.Members<>(builder, superclass, interfaces);
        intentions.forEach(intention -> builder.declareOverride(intention.own(), intention.thrown()));
        return builder;
    }

    /// Completes the declaration.
    ///
    /// @return the class as it is rendered
    /// @throws IllegalStateException if the header never came to the members, or a declared member
    ///     is not defined
    Declared complete() {
        return switch (phase) {
            case Phase.Named<Self> _, Phase.Heading<Self> _ ->
                throw new IllegalStateException("the declaration of " + described + " is complete, but its"
                        + " header never came to the members: end it with extendsObject().body(), or with"
                        + " body(ctor -> ...) of the header extends_ gave, whatever the class declares");
            case Phase.Members<Self>(var builder, var superclass, var interfaces) ->
                new Declared(superclass, interfaces, builder.complete());
        };
    }

    private void heading(Inheritance inherited, Superclass superclass, List<ClassOrInterfaceTypeRef> interfaces) {
        opened.stream()
                .filter(held -> !superclass.chain().contains(held.owner().erasure()))
                .findFirst()
                .ifPresent(held -> {
                    throw new IllegalArgumentException(described + " took " + held + " out of its wrapper, and is"
                            + " no subclass of " + held.owner() + ": its superclass is " + superclass.token()
                            + ", and a protected member is reached by a subclass alone (JLS 6.6.2)");
                });
        requireOverrides(inherited, intentions.stream());
        phase = new Phase.Heading<>(inherited, superclass, interfaces);
    }

    /// Rejects the intentions that cannot override what the class inherits under their signature.
    private void requireOverrides(Inheritance inherited, Stream<Intention> intended) {
        intended.forEach(intention -> inherited
                .group(intention.own().signature())
                .ifPresent(group -> SignatureRegistry.requireOverrides(described, intention.own(), group)));
    }

    /// A method the class said it overrides, in its header.
    ///
    /// @param overridden the fact of the overridden method
    /// @param own the fact of the method the class declares
    /// @param thrown the exception classes of the `throws` clause of `own`
    private record Intention(Invocable overridden, Invocable own, List<ClassToken<? extends Throwable>> thrown) {}

    /// How the class has the method of a step of a proof.
    private sealed interface Stepped {
        /// The erased signature of the method the class has, in the class.
        MethodSignature signature();

        /// The fact of the method of the supertype the step is of.
        Invocable satisfies();

        /// By a method it said it overrides.
        record Own(Intention intention) implements Stepped {
            @Override
            public MethodSignature signature() {
                return intention.own().signature();
            }

            @Override
            public Invocable satisfies() {
                return intention.overridden();
            }
        }

        /// By the method of its superclass it said it inherits.
        record Inherited(Invocable method) implements Stepped {
            @Override
            public MethodSignature signature() {
                return method.signature();
            }

            @Override
            public Invocable satisfies() {
                return method;
            }
        }
    }

    /// The class as it is rendered.
    ///
    /// @param superclass its superclass
    /// @param interfaces its superinterfaces, in the order they were named
    /// @param members its members, in declaration order
    record Declared(Superclass superclass, List<ClassOrInterfaceTypeRef> interfaces, List<ClassMember> members) {}

    /// Where the declaration stands.
    private sealed interface Phase<Self> {
        /// The class has a name and nothing else.
        record Named<Self>() implements Phase<Self> {}

        /// The class has its superclass, and names its superinterfaces.
        ///
        /// @param inherited what it inherits so far
        /// @param superclass its superclass
        /// @param interfaces its superinterfaces so far
        record Heading<Self>(Inheritance inherited, Superclass superclass, List<ClassOrInterfaceTypeRef> interfaces)
                implements Phase<Self> {}

        /// The class declares its members.
        ///
        /// @param builder the builder of the members
        /// @param superclass its superclass
        /// @param interfaces its superinterfaces
        record Members<Self>(
                MemberBuilder<Self> builder, Superclass superclass, List<ClassOrInterfaceTypeRef> interfaces)
                implements Phase<Self> {}
    }
}
