package me.supcheg.javafile.typed;

import me.supcheg.javafile.InheritedName;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Heritage;
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
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.typed.jdk.facts.java.lang.Object_;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The declaration of one class, as its stages share it: the header
/// ([ClassHeader], then [ObjectHeader] or [SubclassHeader]), the declarations
/// of the members ([TypedClassBuilder] or [TypedSubclassBuilder]) and their
/// bodies ([TypedClassBodies] or [TypedSubclassBodies]).
///
/// A stage is a view of the declaration that offers what can be said at its
/// point; the declaration holds where it stands ([Phase]) and rejects a stage
/// used after the declaration moved on, which the types cannot — a stage is
/// an ordinary value a generator may keep.
///
/// The methods the class said it overrides before it had members
/// ([#intend]) are held by the phase until the members begin: each is checked
/// against what the class inherits as soon as a supertype that has the
/// method is named, and all of them when the header ends. What can be said
/// in one phase alone — a `protected` fact taken out before the superclass,
/// a method had by inheritance and a constructor after it — is a part of that
/// phase and of no other.
///
/// @param <Self> the brand of the class being declared
final class ClassDeclaration<Self> {
    private final TargetClasspath target;
    private final ClassDesc desc;
    private final String described;
    private final Inheritance.Heir heir;
    private final DeclarationIdentity identity;
    private final FinalClassToken<Self> self;
    private List<Variable> variables;
    private Phase<Self> phase = new Phase.Named<>(new Said(List.of(), List.of()), List.of());

    /// @param target the classpath the class is compiled against
    /// @param desc the class
    /// @param parameters the type parameters of the class, in order
    /// @throws IllegalArgumentException if a type parameter has the name of another or of the class, or
    ///     its bound is a raw type
    /// @throws IllegalStateException if a bound mentions a type parameter of another class
    ClassDeclaration(TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters) {
        this.target = target;
        this.desc = desc;
        this.described = "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName();
        this.heir = new Inheritance.Heir.OfClass(described, desc.packageName());
        this.identity = UnsafeFacts.declarationIdentity(described);
        IntStream.range(0, parameters.size()).forEach(i -> {
            TypeParameter<?> parameter = parameters.get(i);
            if (parameters.subList(0, i).stream()
                    .anyMatch(before -> before.name().equals(parameter.name()))) {
                throw new IllegalArgumentException(described + " has two type parameters " + parameter.name()
                        + ": a type parameter has a name of its own (JLS 6.4)");
            }
            if (parameter.name().equals(desc.displayName())) {
                throw new IllegalArgumentException(described + " cannot have the type parameter "
                        + parameter.name() + ": in the class the name would mean the type parameter, and"
                        + " the class is named there by its simple name");
            }
            requireNotRaw(parameter.bound(), parameter.name());
            headerScope().require(parameter.bound());
            target.verify(parameter.bound());
        });
        this.variables = parameters.stream()
                .map(parameter -> new Variable(
                        UnsafeFacts.declaredTypeVarToken(
                                new TypeVarRef(parameter.name()),
                                parameter.bound().erasure(),
                                identity),
                        parameter.bound(),
                        List.of()))
                .toList();
        this.self = variables.isEmpty()
                ? UnsafeFacts.declaredClassToken(new ClassTypeRef(desc), this::superclasses, this::methodTable)
                : UnsafeFacts.declaredClassToken(
                        desc,
                        variables.stream()
                                .map(variable ->
                                        new TypeParam(variable.token().typeRef().name(), leftmost(variable.first())))
                                .toList(),
                        variables.stream().map(Variable::token).toList(),
                        this::superclasses,
                        this::methodTable);
    }

    /// The token of a type parameter of the class.
    ///
    /// @param index its index
    /// @return the token
    TypeVarToken<?> variable(int index) {
        return variables.get(index).token();
    }

    /// The type parameters of the class as the proof of a subtype knows them: each with its bounds.
    List<Subtyping.Bounded> bounded() {
        return variables.stream()
                .map(variable -> new Subtyping.Bounded(
                        variable.token(),
                        Stream.concat(Stream.of(variable.first()), variable.more().stream())
                                .toList()))
                .toList();
    }

    /// The type parameters of the class as they are rendered. The leftmost bound is written
    /// whenever another follows it, `<T extends Object & Comparable<T>>`: the erasure of a type
    /// variable is that of its leftmost bound (JLS 4.6), and the token of the variable tells it
    /// from the first.
    List<TypeParam> typeParams() {
        return variables.stream()
                .map(variable -> new TypeParam(
                        variable.token().typeRef().name(),
                        variable.more().isEmpty()
                                ? leftmost(variable.first())
                                : Stream.concat(Stream.of(variable.first()), variable.more().stream())
                                        .<ClassOrInterfaceTypeRef>map(DeclaredToken::typeRef)
                                        .toList()))
                .toList();
    }

    private static List<ClassOrInterfaceTypeRef> leftmost(DeclaredToken<?> first) {
        return first.erasure().equals(ConstantDescs.CD_Object) ? List.of() : List.of(first.typeRef());
    }

    private void requireNotRaw(DeclaredToken<?> bound, String variable) {
        if (bound.typeRef() instanceof ClassTypeRef
                && !bound.shape().typeParameters().isEmpty()) {
            throw new IllegalArgumentException("the type parameter " + variable + " of " + described
                    + " cannot have the raw bound " + bound + ": the type is generic, name it with its type"
                    + " arguments");
        }
    }

    /// The scope of the header and of an instance member: the type parameters of the class are in scope.
    ///
    /// @param where the place, for messages
    /// @return the scope
    TypeScope scope(String where) {
        return new TypeScope.Instance(identity, where + " of " + described);
    }

    /// The scope of a `static` member: no type parameter of the class is in scope (JLS 8.1.2).
    ///
    /// @param where the place, for messages
    /// @return the scope
    TypeScope staticScope(String where) {
        return new TypeScope.Static(identity, where + " of " + described);
    }

    private TypeScope headerScope() {
        return scope("the header");
    }

    /// A type parameter of the class gets one more bound, an interface, said by a witness.
    ///
    /// @param variable the token of the type parameter
    /// @param bound the interface, with its type arguments
    /// @param <T> the type parameter
    /// @param <B> the interface
    /// @return the witness that the type parameter is of the interface
    /// @throws IllegalArgumentException if the bound cannot be one, see [ClassHeader#bound]
    /// @throws IllegalStateException if the class has its superclass already
    <T, B> Upcast<T, B> bound(TypeVarToken<T> variable, InterfaceToken<B> bound) {
        Scopes.requireNoneOpen("a bound of a type parameter of " + described);
        requireNamed();
        Variable bounded = variables.stream()
                .filter(mine -> mine.token() == variable)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        variable + " is no type parameter of " + described + ": a class bounds its own"));
        String name = variable.typeRef().name();
        requireNotRaw(bound, name);
        headerScope().require(bound);
        target.verify(bound);
        String owner = "the type parameter " + name + " of " + described;
        // What a type variable of several bounds has is what a class of those supertypes inherits (JLS 4.9):
        // javac makes that class and rejects the type parameter where it would reject the class. A bound
        // twice, two parameterizations of one interface and methods that clash are rejected by the merge;
        // methods the class could not have together without one of its own, which it has none of, here.
        Inheritance.Supertype first;
        try {
            first = Inheritance.supertype(owner, target, bounded.first());
        } catch (IllegalArgumentException untold) {
            throw new IllegalArgumentException(
                    owner + " cannot have the bound " + bound + " beside " + bounded.first()
                            + ": what the two give the type variable together is not known, for "
                            + untold.getMessage(),
                    untold);
        }
        Inheritance merged = Stream.concat(bounded.more().stream(), Stream.of(bound))
                .reduce(
                        Inheritance.of(new Inheritance.Heir.OfVariable(owner), first),
                        (had, next) -> had.with(Inheritance.supertype(owner, target, next)),
                        (a, b) -> a);
        merged.groups().flatMap(group -> group.conflict().stream()).findFirst().ifPresent(why -> {
            throw new IllegalArgumentException(owner + " cannot have the bound " + bound
                    + ": a type variable is of the class its bounds make (JLS 4.9), and " + why);
        });
        merged.groups()
                .filter(group -> group.entries().stream()
                        .anyMatch(entry -> !entry.method()
                                .sameResult(group.entries().getFirst().method())))
                .findFirst()
                .ifPresent(group -> {
                    throw new IllegalArgumentException(owner + " cannot have the bound " + bound + ": with it the"
                            + " bounds have the methods "
                            + group.entries().stream().map(Object::toString).collect(Collectors.joining(" and "))
                            + " of one signature and of"
                            + " different results — one has to be return-type-substitutable for the others"
                            + " (JLS 8.4.5, 4.9), which the typed layer holds to: of the same result");
                });
        variables = variables.stream()
                .map(mine ->
                        mine == bounded ? new Variable(mine.token(), mine.first(), with(mine.more(), bound)) : mine)
                .toList();
        return new Upcast<>(variable, bound);
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
            case Phase.Heading<Self> heading -> heading.superclass().chain();
            case Phase.Members<Self>(var _, var extended) ->
                extended.superclass().chain();
            case Phase.Bodies<Self>(var _, var _, var extended) ->
                extended.superclass().chain();
            case Phase.Complete<Self>(var _, var superclass) -> superclass.chain();
        };
    }

    private MethodTable methodTable() {
        return switch (phase) {
            case Phase.Named<Self> _, Phase.Heading<Self> _, Phase.Members<Self> _ ->
                throw new IllegalStateException("the methods of " + described + " are known only once its"
                        + " members are declared: the class is still declaring them, and bodies() of the"
                        + " builder of its members ends that");
            case Phase.Bodies<Self>(var _, var methods, var _) -> methods;
            case Phase.Complete<Self>(var methods, var _) -> methods;
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
        Phase.Named<Self> named = requireNamed();
        Inheritance inherited = Inheritance.of(heir, Inheritance.supertype(described, target, Object_.TOKEN));
        heading(named, inherited, new Superclass.OfObject(Object_.TOKEN, inherited));
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
        Phase.Named<Self> named = requireNamed();
        requireAbout(proof);
        if (!(proof.supertype() instanceof ExtendableClassToken<S> superclass)) {
            throw new IllegalArgumentException(described + " cannot extend " + proof.supertype()
                    + ": it is an interface, which a class implements (JLS 8.1.4)");
        }
        if (!variables.isEmpty() && superclass.isSubclassOf(ConstantDescs.CD_Throwable)) {
            throw new IllegalArgumentException(described + " cannot extend " + superclass
                    + ": a generic class is no subclass of Throwable (JLS 8.1.2) — the catch of an exception"
                    + " is by its class, which tells no type argument");
        }
        headerScope().require(superclass);
        target.verify(superclass);
        Inheritance inherited = Inheritance.of(heir, Inheritance.supertype(described, target, superclass));
        requireHolds(proof, inherited, "extend", named.said(), List.of()).forEach(stepped -> {
            switch (stepped) {
                case Stepped.Own _ -> {}
                case Stepped.Inherited(var method) ->
                    throw new IllegalArgumentException(described + " cannot have " + method + " by inheritance"
                            + " from " + superclass + " for the step of " + superclass + " itself: an abstract"
                            + " method of the superclass has no body there, the class overrides it"
                            + " (JLS 8.1.1.1)");
            }
        });
        heading(named, inherited, new Superclass.Named(superclass, inherited));
        return superclass;
    }

    /// Takes the fact of a `protected` member out before the superclass is named: that the class
    /// is a subclass of its owner is known by the type of the stage that asks ([ExtendingHeader]),
    /// and held again when the superclass is named ([#heading]) — a stage is a value, and the
    /// class may come to extend another.
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalStateException if the class has its superclass already
    <F extends MemberFact> F open(Protected<?, F> held) {
        Phase.Named<Self> named = requireNamed();
        phase = new Phase.Named<>(named.said(), with(named.opened(), held));
        return UnsafeFacts.open(held);
    }

    /// The class says it is about to name `supertype`, and has the witness of it at once: held to
    /// where the header ends ([#members]).
    ///
    /// @param supertype the type, with its type arguments
    /// @param <X> the type
    /// @return the witness that the class is of the type
    /// @throws IllegalStateException if the header of the class has ended
    <X> Upcast<Self, X> promise(DeclaredToken<X> supertype) {
        Scopes.requireNoneOpen("a supertype of " + described);
        Said said = saying(() -> new IllegalStateException(described + " cannot say it is about to be " + supertype
                + ": the header of the class has ended, and the witness of a supertype is the one"
                + " naming it gave"));
        headerScope().require(supertype);
        say(new Said(said.intentions(), with(said.promised(), supertype)));
        return new Upcast<>(self, supertype);
    }

    /// The class as the proof of a subtype knows it in its header: of the supertypes it has named,
    /// and of those it said it is about to name — which the end of the header holds it to.
    private Subtyping.Declaring declaring(Inheritance inherited, Said said) {
        return new Subtyping.Declaring(
                self.erasure(),
                Stream.concat(inherited.declaring(self).supertypes().stream(), said.promised().stream())
                        .toList(),
                bounded());
    }

    private Phase.Named<Self> requireNamed() {
        return switch (phase) {
            case Phase.Named<Self> named -> named;
            case Phase.Heading<Self> _, Phase.Members<Self> _, Phase.Bodies<Self> _, Phase.Complete<Self> _ ->
                throw new IllegalStateException(described + " has its superclass already: a class header is"
                        + " used once, and what follows is declared through the stage it gave");
        };
    }

    /// What the header said so far, while it lasts.
    ///
    /// @param ended what to throw if the header has ended
    private Said saying(Supplier<IllegalStateException> ended) {
        return switch (phase) {
            case Phase.Named<Self> named -> named.said();
            case Phase.Heading<Self> heading -> heading.said();
            case Phase.Members<Self> _, Phase.Bodies<Self> _, Phase.Complete<Self> _ -> throw ended.get();
        };
    }

    /// The header says one thing more: the phase it is in, with `said` for what it said.
    private void say(Said said) {
        phase = switch (phase) {
            case Phase.Named<Self> named -> new Phase.Named<>(said, named.opened());
            case Phase.Heading<Self> heading -> heading.saying(said);
            case Phase.Members<Self> members -> members;
            case Phase.Bodies<Self> bodies -> bodies;
            case Phase.Complete<Self> complete -> complete;
        };
    }

    private static <T> List<T> with(List<? extends T> list, T added) {
        return Stream.concat(list.stream(), Stream.of(added)).toList();
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
        Said said = saying(() -> new IllegalStateException("cannot say that " + described + " overrides "
                + overridden + ": the header of the class has ended; a method is overridden among the members by"
                + " declareOverride(fact)"));
        if (overridden.traits().overridability() == Overridability.FINAL) {
            throw new IllegalArgumentException(
                    described + " cannot override " + overridden + ": the method is final, or static (JLS 8.4.3.3)");
        }
        if (!overridden.traits().typeArgs().isEmpty()) {
            throw new IllegalArgumentException(described + " cannot override " + overridden
                    + ": the method is generic, and the typed layer declares no generic method");
        }
        MethodSignature signature = own.signature();
        said.intentions().stream()
                .filter(intention -> intention.own().signature().equals(signature))
                .findFirst()
                .ifPresent(intention -> {
                    throw new IllegalArgumentException(described + " overrides " + signature + " already, as "
                            + intention.overridden() + ": a class declares a method once (JLS 8.4.2) — give"
                            + " the one implementation to every step that takes the method");
                });
        // What a result is a subtype of is told by its metamodel, which has to hold on the target.
        own.resultType().ifPresent(target::verify);
        Intention intention = new Intention(overridden, own, thrown, Site.here());
        if (phase instanceof Phase.Heading<Self> heading) {
            requireOverrides(heading.inherited(), Stream.of(intention), said);
        }
        say(new Said(with(said.intentions(), intention), said.promised()));
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
        if (!(phase instanceof Phase.Heading<Self> heading)) {
            throw new IllegalStateException(described + " cannot be said to inherit " + method
                    + " now: what a class has from its superclass is said after the superclass is named"
                    + " and before the members");
        }
        Superclass superclass = heading.superclass();
        Inheritance.Group group = superclass.member(described, method);
        Inheritance.Entry member = group.stands();
        Heritage.Dispatch standing = group.standing();
        switch (standing) {
            case CONCRETE, FINAL -> {}
            case ABSTRACT, DEFAULT, STATIC ->
                throw new IllegalArgumentException(described + " does not have " + member + " with a body"
                        + " of its superclass " + superclass.token() + ": the method is "
                        + standing.name().toLowerCase(Locale.ROOT)
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
        Implemented.ByInheritance inheritance = new Implemented.ByInheritance(method);
        phase = new Phase.Heading<>(
                heading.inherited(),
                superclass,
                heading.interfaces(),
                heading.said(),
                with(heading.inheritances(), inheritance));
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
        if (!(phase instanceof Phase.Heading<Self> heading)) {
            throw new IllegalStateException(described + " cannot implement " + proof.supertype()
                    + " now: a superinterface is named after the superclass and before the members");
        }
        Said said = heading.said();
        DeclaredToken<I> supertype = proof.supertype();
        requireAbout(proof);
        if (!(supertype instanceof InterfaceToken<I>)) {
            throw new IllegalArgumentException(described + " cannot implement " + supertype
                    + ": it is a class, which a class extends (JLS 8.1.5)");
        }
        headerScope().require(supertype);
        target.verify(supertype);
        Inheritance merged = heading.inherited().with(Inheritance.supertype(described, target, supertype));
        requireHolds(proof, merged, "implement", said, heading.inheritances()).forEach(stepped -> {
            switch (stepped) {
                case Stepped.Own _ -> {}
                case Stepped.Inherited(var method) ->
                    merged.group(method.signature())
                            .ifPresent(group -> SignatureRegistry.requireInherited(
                                    described, method, supertype, group, declaring(merged, said)));
            }
        });
        requireOverrides(merged, said.intentions().stream(), said);
        phase = new Phase.Heading<>(
                merged,
                heading.superclass(),
                with(heading.interfaces(), supertype.typeRef()),
                said,
                heading.inheritances());
        return new Upcast<>(self, supertype);
    }

    /// Holds `proof` against its type as the target classpath has it, among what the class would
    /// inherit with it: the type has no abstract method the proof has no step for, and every step is
    /// of a method the type has.
    ///
    /// @param proof the proof
    /// @param merged what the class inherits with the type of the proof
    /// @param verb what the class does with the type, for messages
    /// @param said what the header said so far: the methods the class overrides
    /// @param inheritances the methods the class said it has from its superclass
    /// @return how the class has the method of each step
    private List<Stepped> requireHolds(
            Complete<Self, ?> proof,
            Inheritance merged,
            String verb,
            Said said,
            List<Implemented.ByInheritance> inheritances) {
        DeclaredToken<?> supertype = proof.supertype();
        List<Stepped> stepped = proof.steps().stream()
                .map(step -> stepped(step, said, inheritances))
                .toList();
        Set<MethodTableTemplate.Signature> proven =
                proof.steps().stream().map(Complete.Step::method).collect(Collectors.toSet());
        merged.groups()
                .flatMap(group -> group.entries().stream())
                .filter(entry -> entry.through() == supertype
                        && entry.leftAbstract()
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
    private Stepped stepped(Complete.Step<Self> step, Said said, List<Implemented.ByInheritance> inheritances) {
        return switch (step.how().how()) {
            case Implemented.ByOverride(Invocable overridden, Invocable own) ->
                said.intentions().stream()
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
                                    case Phase.Heading<Self> heading
                                    when heading.superclass() instanceof Superclass.OfObject ->
                                        "it extends Object, which has no such method";
                                    case Phase.Named<Self> _,
                                            Phase.Heading<Self> _,
                                            Phase.Members<Self> _,
                                            Phase.Bodies<Self> _,
                                            Phase.Complete<Self> _ ->
                                        "a step takes what inherited(fact) of the header of the class gave";
                                }));
        };
    }

    /// The header of a class that extends `Object` ends and the members begin.
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method it does not inherit
    /// @throws IllegalStateException if the class has no superclass yet, or has its members already
    TypedClassBuilder<Self> members() {
        return members((inherited, superclass) -> new TypedClassBuilder<>(this, inherited, superclass));
    }

    /// The header of a class that extends `superclass` ends and the members begin.
    ///
    /// @param superclass the superclass, as [#extend] gave it
    /// @param <S> the superclass
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method it does not inherit
    /// @throws IllegalStateException if the class has no superclass yet, or has its members already
    <S> TypedSubclassBuilder<Self, S> members(ExtendableClassToken<S> superclass) {
        return members((inherited, extended) -> new TypedSubclassBuilder<>(this, inherited, extended, superclass));
    }

    /// The header ends and the members begin: every method the class said it overrides is one it
    /// inherits, and is declared.
    private <B extends MemberBuilder<Self, ?>> B members(BiFunction<Inheritance, Superclass, B> make) {
        Scopes.requireNoneOpen("the members of " + described);
        if (!(phase instanceof Phase.Heading<Self> heading)) {
            throw new IllegalStateException("the members of " + described + " do not begin here: they follow"
                    + " the header of the class, once");
        }
        Inheritance inherited = heading.inherited();
        Superclass superclass = heading.superclass();
        List<ClassOrInterfaceTypeRef> interfaces = heading.interfaces();
        heading.said().promised().stream()
                .filter(supertype ->
                        !Tokens.sameType(supertype, superclass.token()) && !interfaces.contains(supertype.typeRef()))
                .findFirst()
                .ifPresent(supertype -> {
                    throw new IllegalArgumentException(described + " said it was about to be " + supertype
                            + " (becoming), and its header ends without it: the class extends "
                            + superclass.token() + " and implements " + interfaces + " — a witness is"
                            + " of a type the class names, with these very type arguments");
                });
        B builder = make.apply(inherited, superclass);
        phase = new Phase.Members<>(builder, new Extended(superclass, interfaces, named(inherited)));
        heading.said()
                .intentions()
                .forEach(intention -> builder.declareOverride(intention.own(), intention.thrown(), intention.site()));
        return builder;
    }

    /// The simple names the fields and member types of the supertypes take in the body of the class
    /// (JLS 6.4.1, 6.4.2), which its declaration does not show. One with package access is held to
    /// take its name whatever the package of the class: a type it would hide is written by its
    /// qualified name, which is never wrong.
    private static List<InheritedName> named(Inheritance inherited) {
        return inherited.supertypes().stream()
                .flatMap(supertype -> supertype.told().named().stream())
                .<InheritedName>map(named -> switch (named) {
                    case Heritage.Named.Field(var name, var _, var _) -> new InheritedName.Field(name);
                    case Heritage.Named.MemberType(var name, var _, var _) -> new InheritedName.MemberType(name);
                })
                .distinct()
                .toList();
    }

    /// Holds a use of the builder of the members to the phase it is of: the members are being declared.
    ///
    /// @param builder the builder
    /// @param what what is declared, for messages
    /// @throws IllegalStateException if the declarations of the class have ended, or a body is being built
    void requireDeclaring(MemberBuilder<Self, ?> builder, String what) {
        switch (phase) {
            case Phase.Members<Self> members when members.builder() == builder -> Scopes.requireNoneOpen(what);
            case Phase.Bodies<Self> _ ->
                throw new IllegalStateException("cannot declare " + what + ": the declarations of " + described
                        + " have ended — bodies() began the bodies of its members, and every member of a"
                        + " class is declared before the first body is built");
            case Phase.Complete<Self> _ -> throw completed(what);
            case Phase.Named<Self> _, Phase.Heading<Self> _, Phase.Members<Self> _ ->
                throw new IllegalStateException(
                        "cannot declare " + what + ": this is not the builder of the members of " + described);
        }
    }

    /// Holds a use of the bodies of the members to the phase it is of: the members are being defined.
    ///
    /// @param bodies the bodies
    /// @param what what is defined, for messages
    /// @throws IllegalStateException if the declaration of the class is complete, or a body is being built
    void requireDefining(MemberBodies<Self, ?> bodies, String what) {
        switch (phase) {
            case Phase.Bodies<Self> defining when defining.bodies() == bodies -> Scopes.requireNoneOpen(what);
            case Phase.Complete<Self> _ -> throw completed(what);
            case Phase.Named<Self> _, Phase.Heading<Self> _, Phase.Members<Self> _, Phase.Bodies<Self> _ ->
                throw new IllegalStateException(
                        "cannot define " + what + ": these are not the bodies of the members of " + described);
        }
    }

    private IllegalStateException completed(String what) {
        return new IllegalStateException("cannot declare or define " + what + ": the declaration of " + described
                + " is already complete; a class declares and defines its members in the specification its"
                + " declaration was given, while that runs");
    }

    /// The declarations of the members end and their bodies begin: the methods of the class are
    /// known from here on.
    ///
    /// @param methods the methods of the class
    /// @param make makes the stage of the bodies
    /// @param <B> the stage of the bodies
    /// @return the stage of the bodies
    /// @throws IllegalStateException if the class is not declaring its members
    <B extends MemberBodies<Self, ?>> B bodies(MethodTable methods, Supplier<B> make) {
        if (!(phase instanceof Phase.Members<Self>(var _, var extended))) {
            throw new IllegalStateException("the bodies of the members of " + described + " do not begin here:"
                    + " they follow the declarations of the members, once");
        }
        B bodies = make.get();
        phase = new Phase.Bodies<>(bodies, methods, extended);
        return bodies;
    }

    /// The file of the class as far as it is declared: with `members` for its members.
    ///
    /// @param members the members
    /// @return the file
    /// @throws IllegalStateException if the header of the class has not ended
    JavaFile file(List<ClassMember> members) {
        return switch (phase) {
            case Phase.Members<Self>(var _, var extended) -> file(extended, members);
            case Phase.Bodies<Self>(var _, var _, var extended) -> file(extended, members);
            case Phase.Named<Self> _, Phase.Heading<Self> _, Phase.Complete<Self> _ ->
                throw new IllegalStateException(described + " has a file while its members are declared and"
                        + " defined, not before and not after");
        };
    }

    private JavaFile file(Extended extended, List<ClassMember> members) {
        return JavaFile.class_(desc, cb -> {
                    cb.withModifiers(Modifier.FINAL);
                    typeParams().forEach(cb::withTypeParam);
                    switch (extended.superclass()) {
                        case Superclass.OfObject _ -> {}
                        case Superclass.Named(var superclass, var _) -> cb.withSuperclass(superclass.typeRef());
                    }
                    extended.interfaces().forEach(cb::withInterface);
                    members.forEach(cb);
                })
                .inheriting(extended.named());
    }

    /// Completes the declaration, by the bodies its specification ended in.
    ///
    /// That the specification came to the bodies of the members is what it returning them says:
    /// there is no stage of a declaration left to hold it to here. What is rejected is what no
    /// call before could tell — that a declared member got no definition — and the rejection tells
    /// where each such member was declared ([Site]).
    ///
    /// @param ended the bodies the specification returned
    /// @return the file of the class
    /// @throws IllegalStateException if a declared member is not defined, or the bodies are not
    ///     those of this declaration — which the brand rules out, short of an unchecked cast
    JavaFile complete(MemberBodies<Self, ?> ended) {
        if (!(phase instanceof Phase.Bodies<Self>(var bodies, var methods, var extended)) || bodies != ended) {
            throw new IllegalStateException("the specification of " + described + " returned bodies that are"
                    + " not those of its members: a specification returns the stage its own declarations"
                    + " ended in, once");
        }
        JavaFile file = file(extended, bodies.complete());
        phase = new Phase.Complete<>(methods, extended.superclass());
        return file;
    }

    /// The class has its superclass: what it said before holds of it.
    private void heading(Phase.Named<Self> named, Inheritance inherited, Superclass superclass) {
        named.opened().stream()
                .filter(held -> !superclass.chain().contains(held.owner().erasure()))
                .findFirst()
                .ifPresent(held -> {
                    throw new IllegalArgumentException(described + " took " + held + " out of its wrapper, and is"
                            + " no subclass of " + held.owner() + ": its superclass is " + superclass.token()
                            + ", and a protected member is reached by a subclass alone (JLS 6.6.2)");
                });
        requireOverrides(inherited, named.said().intentions().stream(), named.said());
        phase = new Phase.Heading<>(inherited, superclass, List.of(), named.said(), List.of());
    }

    /// Rejects the intentions that cannot override what the class inherits under their signature.
    private void requireOverrides(Inheritance inherited, Stream<Intention> intended, Said said) {
        intended.forEach(intention -> inherited
                .group(intention.own().signature())
                .ifPresent(group -> SignatureRegistry.requireOverrides(
                        described, intention.own(), group, declaring(inherited, said))));
    }

    /// What a header said so far that the stages after hold it to, in whatever phase it was said.
    ///
    /// @param intentions the methods the class said it overrides, in order
    /// @param promised the supertypes the class said it is about to name
    private record Said(List<Intention> intentions, List<DeclaredToken<?>> promised) {
        Said {
            intentions = List.copyOf(intentions);
            promised = List.copyOf(promised);
        }
    }

    /// A method the class said it overrides, in its header.
    ///
    /// @param overridden the fact of the overridden method
    /// @param own the fact of the method the class declares
    /// @param thrown the exception classes of the `throws` clause of `own`
    /// @param site where it was said
    private record Intention(
            Invocable overridden, Invocable own, List<ClassToken<? extends Throwable>> thrown, Site site) {}

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

    /// What the header of a class said of its supertypes, as its file is rendered of it.
    ///
    /// @param superclass its superclass
    /// @param interfaces its superinterfaces, in the order they were named
    /// @param named the names the fields and member types of its supertypes take in its body
    private record Extended(
            Superclass superclass, List<ClassOrInterfaceTypeRef> interfaces, List<InheritedName> named) {
        Extended {
            interfaces = List.copyOf(interfaces);
            named = List.copyOf(named);
        }
    }

    /// A type parameter of the class.
    ///
    /// @param token its token
    /// @param first its leftmost bound, `Object` for none: a type of the generator
    /// @param more its further bounds, said by witnesses in the header, in order
    private record Variable(TypeVarToken<?> token, DeclaredToken<?> first, List<InterfaceToken<?>> more) {
        Variable {
            more = List.copyOf(more);
        }
    }

    /// Where the declaration stands.
    private sealed interface Phase<Self> {
        /// The class has a name and no superclass yet.
        ///
        /// @param said what its header said so far
        /// @param opened the `protected` facts taken out, each of a class the superclass has to be or extend
        record Named<Self>(Said said, List<Protected<?, ?>> opened) implements Phase<Self> {
            public Named {
                opened = List.copyOf(opened);
            }
        }

        /// The class has its superclass, and names its superinterfaces.
        ///
        /// @param inherited what it inherits so far
        /// @param superclass its superclass
        /// @param interfaces its superinterfaces so far
        /// @param said what its header said so far
        /// @param inheritances the methods it said it has from its superclass
        record Heading<Self>(
                Inheritance inherited,
                Superclass superclass,
                List<ClassOrInterfaceTypeRef> interfaces,
                Said said,
                List<Implemented.ByInheritance> inheritances)
                implements Phase<Self> {
            public Heading {
                interfaces = List.copyOf(interfaces);
                inheritances = List.copyOf(inheritances);
            }

            Heading<Self> saying(Said said) {
                return new Heading<>(inherited, superclass, interfaces, said, inheritances);
            }
        }

        /// The class declares its members.
        ///
        /// @param builder the builder of the members
        /// @param extended what its header said of its supertypes
        record Members<Self>(MemberBuilder<Self, ?> builder, Extended extended) implements Phase<Self> {}

        /// The class defines its members: all of them are declared.
        ///
        /// @param bodies the bodies of the members
        /// @param methods the methods of the class
        /// @param extended what its header said of its supertypes
        record Bodies<Self>(MemberBodies<Self, ?> bodies, MethodTable methods, Extended extended)
                implements Phase<Self> {}

        /// The declaration is complete.
        ///
        /// @param methods the methods of the class
        /// @param superclass its superclass
        record Complete<Self>(MethodTable methods, Superclass superclass) implements Phase<Self> {}
    }
}
