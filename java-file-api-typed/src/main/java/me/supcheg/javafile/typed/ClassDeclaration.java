package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.typed.jdk.facts.java.lang.Object_;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The declaration of one class, as its stages share it: the header
/// ([ClassHeader], then [ObjectHeader]) and the members ([TypedClassBuilder]).
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
            case Phase.Heading<Self> _, Phase.Members<Self> _ -> List.of(ConstantDescs.CD_Object);
        };
    }

    private MethodTable methodTable() {
        return switch (phase) {
            case Phase.Named<Self> _, Phase.Heading<Self> _ ->
                throw new IllegalStateException("the methods of " + described + " are known only once its"
                        + " declaration is complete: the class is still being declared");
            case Phase.Members<Self>(var builder, var _) -> builder.methodTable();
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
        switch (phase) {
            case Phase.Named<Self> _ ->
                heading(Inheritance.of(described, Inheritance.supertype(described, target, Object_.TOKEN)), List.of());
            case Phase.Heading<Self> _, Phase.Members<Self> _ ->
                throw new IllegalStateException(described + " has its superclass already: a class header is"
                        + " used once, and what follows is declared through the stage it gave");
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
        if (phase instanceof Phase.Heading<Self>(var inherited, var _)) {
            requireOverrides(inherited, Stream.of(intention));
        }
        intentions.add(intention);
        return own;
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
        if (!(phase instanceof Phase.Heading<Self>(var inherited, var interfaces))) {
            throw new IllegalStateException(described + " cannot implement " + proof.supertype()
                    + " now: a superinterface is named after the superclass and before the members");
        }
        DeclaredToken<I> supertype = proof.supertype();
        if (proof.self() != self) {
            throw new IllegalArgumentException(
                    "the proof that " + proof + " is not a proof about " + described + ", which is " + self);
        }
        if (!(supertype instanceof InterfaceToken<I>)) {
            throw new IllegalArgumentException(described + " cannot implement " + supertype
                    + ": it is a class, which a class extends (JLS 8.1.5)");
        }
        target.verify(supertype);
        List<Intention> stepped = proof.steps().stream().map(this::stepped).toList();
        Inheritance merged = inherited.with(Inheritance.supertype(described, target, supertype));
        Set<MethodTableTemplate.Signature> proven =
                proof.steps().stream().map(Complete.Step::method).collect(Collectors.toSet());
        merged.groups()
                .flatMap(group -> group.entries().stream())
                .filter(entry -> entry.through() == supertype
                        && entry.isAbstract()
                        && !proven.contains(entry.method().signature()))
                .findFirst()
                .ifPresent(entry -> {
                    throw new IllegalArgumentException(described + " cannot implement " + supertype + " by " + proof
                            + ": on the target classpath the type has the abstract method " + entry
                            + ", which the proof has no step for — the generator was compiled against another"
                            + " version of the type");
                });
        stepped.stream()
                .filter(intention -> merged.group(intention.own().signature()).isEmpty())
                .findFirst()
                .ifPresent(intention -> {
                    throw new IllegalArgumentException(described + " cannot implement " + supertype + " by " + proof
                            + ": the proof has a step for " + intention.overridden() + ", and on the target"
                            + " classpath the type has no such method — the generator was compiled against"
                            + " another version of the type");
                });
        requireOverrides(merged, intentions.stream());
        heading(
                merged,
                Stream.concat(interfaces.stream(), Stream.of(supertype.typeRef()))
                        .toList());
        return new Upcast<>(self, supertype);
    }

    /// The method the class declares for a step of a proof: one it said it overrides.
    private Intention stepped(Complete.Step<Self> step) {
        return switch (step.how().how()) {
            case Implemented.ByOverride(Invocable overridden, Invocable own) ->
                intentions.stream()
                        .filter(intention -> intention.own() == own)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("the implementation of " + overridden
                                + " is not one " + described + " made: a step takes what overriding(fact) of the"
                                + " header of the class gave"));
            case Implemented.ByInheritance(Invocable inherited) ->
                throw new IllegalArgumentException(described + " cannot have " + inherited
                        + " by inheritance: it extends Object, which has no such method");
        };
    }

    /// The header ends and the members begin: every method the class said it overrides is one it
    /// inherits, and is declared.
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method it does not inherit
    /// @throws IllegalStateException if the class has no superclass yet, or has its members already
    TypedClassBuilder<Self> body() {
        Scopes.requireNoneOpen("the members of " + described);
        if (!(phase instanceof Phase.Heading<Self>(var inherited, var interfaces))) {
            throw new IllegalStateException("the members of " + described + " do not begin here: they follow"
                    + " the header of the class, once");
        }
        TypedClassBuilder<Self> builder = new TypedClassBuilder<>(this, inherited);
        phase = new Phase.Members<>(builder, interfaces);
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
                        + " header never came to the members: end it with extendsObject().body(), whatever"
                        + " the class declares");
            case Phase.Members<Self>(var builder, var interfaces) -> new Declared(interfaces, builder.complete());
        };
    }

    private void heading(Inheritance inherited, List<ClassOrInterfaceTypeRef> interfaces) {
        requireOverrides(inherited, intentions.stream());
        phase = new Phase.Heading<>(inherited, interfaces);
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

    /// The class as it is rendered.
    ///
    /// @param interfaces its superinterfaces, in the order they were named
    /// @param members its members, in declaration order
    record Declared(List<ClassOrInterfaceTypeRef> interfaces, List<ClassMember> members) {}

    /// Where the declaration stands.
    private sealed interface Phase<Self> {
        /// The class has a name and nothing else.
        record Named<Self>() implements Phase<Self> {}

        /// The class has its superclass, and names its superinterfaces.
        ///
        /// @param inherited what it inherits so far
        /// @param interfaces its superinterfaces so far
        record Heading<Self>(Inheritance inherited, List<ClassOrInterfaceTypeRef> interfaces) implements Phase<Self> {}

        /// The class declares its members.
        ///
        /// @param builder the builder of the members
        /// @param interfaces its superinterfaces
        record Members<Self>(TypedClassBuilder<Self> builder, List<ClassOrInterfaceTypeRef> interfaces)
                implements Phase<Self> {}
    }
}
