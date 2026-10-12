package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// What a class being declared inherits: the heritages of its superclass and
/// of its superinterfaces ([Heritage]), each applied to its type arguments,
/// merged as javac merges the members of the supertypes of a class (JLS
/// 8.4.8, 9.4.1.3).
///
/// - A method of an interface that is override-equivalent to a `public`
///   method of `Object` is no member to inherit: the class has the method of
///   `Object` for it (JLS 9.2).
/// - The methods are grouped by their erased signature in the class: a
///   [Group] is one method to the class, which it inherits, overrides or has
///   to implement. A method one of the same group overrides is not inherited:
///   that of the subinterface is.
/// - A method with package access of a class of another package is no member
///   of a class being declared ([Heir.OfClass]): it is neither inherited nor
///   overridden (JLS 8.4.8.1), and is no method of its group — but is kept
///   beside it ([Group#hidden()]), for the virtual machine finds it where
///   javac does not. The class a type variable is of by its bounds
///   ([Heir.OfVariable]) is in no package the typed layer knows, and has
///   every such method.
/// - The supertypes have to agree, and a disagreement is rejected when the
///   supertype that brings it is added: a `static` method and an instance one
///   of one signature, methods of one erasure whose parameters differ, a
///   method with package access beside one of an interface, two methods one
///   of which would get the bridge of the other (JLS 8.4.8.3), one interface
///   with two parameterizations (JLS 8.1.5).
/// - What a group is to the class is what [Heritage.Method#standing()] makes
///   of its declarations ([Group#standing()]): the method of a class stands,
///   whatever an interface declares. Whether the methods can be had together
///   without a method of the class is [Group#conflict()].
///
/// What a member the class declares has to observe is told here as data —
/// the group a signature is of, the method whose erasure it would clash with
/// — and judged by the [SignatureRegistry] of the class.
final class Inheritance {
    private final Heir heir;
    private final String owner;
    private final List<Supertype> supertypes;
    private final Map<MethodSignature, Group> groups;

    private Inheritance(Heir heir, List<Supertype> supertypes) {
        this.heir = heir;
        this.owner = heir.described();
        this.supertypes = List.copyOf(supertypes);
        this.groups = merged(heir, this.supertypes);
    }

    /// The inheritance of a class that extends `superclass` and implements nothing yet.
    ///
    /// @param heir who inherits
    /// @param superclass the superclass with its heritage
    /// @return the inheritance
    /// @throws IllegalArgumentException if the heritage disagrees with itself
    static Inheritance of(Heir heir, Supertype superclass) {
        return new Inheritance(heir, List.of(superclass));
    }

    /// The inheritance with one more supertype.
    ///
    /// @param supertype the supertype with its heritage
    /// @return the inheritance of the class that has `supertype` too
    /// @throws IllegalArgumentException if the class has the type already, has a type of another
    ///     parameterization of one of its supertypes, or the heritage disagrees with what the class
    ///     inherits already
    Inheritance with(Supertype supertype) {
        ClassDesc added = supertype.token().shape().desc();
        if (supertypes.stream().anyMatch(had -> had.token().shape().desc().equals(added))) {
            throw new IllegalArgumentException(owner + " has the supertype " + TypeNames.describe(added)
                    + " already: a class names a supertype once (JLS 8.1.5)");
        }
        List<Supertype> all =
                Stream.concat(supertypes.stream(), Stream.of(supertype)).toList();
        requireOneParameterization(owner, all);
        return new Inheritance(heir, all);
    }

    /// The supertype `token` with its heritage as the target classpath has the type.
    ///
    /// @param owner the class being declared, for messages
    /// @param target the classpath the class is compiled against
    /// @param token the supertype
    /// @return the supertype with its heritage, applied to the type arguments of `token`
    /// @throws IllegalArgumentException if `token` is raw or has a wildcard type argument (JLS 8.1.4,
    ///     8.1.5), or its shape tells no heritage
    /// @throws TargetClasspathMismatchException if the metamodel of the type does not hold on `target`
    static Supertype supertype(String owner, TargetClasspath target, DeclaredToken<?> token) {
        TypeShape<?> shape = token.shape();
        return switch (target.heritage(shape)) {
            case Heritage.Untold _ ->
                throw new IllegalArgumentException(owner + " cannot have the supertype " + token
                        + ": its metamodel tells no heritage — the type cannot be extended, or the"
                        + " metamodel is not a full one");
            case Heritage.Told told -> new Supertype(token, told.applied(arguments(owner, token)));
        };
    }

    private static List<TypeRef> arguments(String owner, DeclaredToken<?> token) {
        return switch (token.typeRef()) {
            case ClassTypeRef _ when token.shape().typeParameters().isEmpty() -> List.of();
            case ClassTypeRef _ ->
                throw new IllegalArgumentException(owner + " cannot have the raw supertype " + token
                        + ": the type is generic, name it with its type arguments");
            case ParameterizedTypeRef parameterized ->
                parameterized.args().stream()
                        .map(argument -> switch (argument) {
                            case ExactTypeArg(TypeRef type) -> type;
                            case ExtendsTypeArg _, SuperTypeArg _, UnboundedTypeArg _ ->
                                throw new IllegalArgumentException(owner + " cannot have the supertype " + token
                                        + ": a supertype has no wildcard type argument (JLS 8.1.4, 8.1.5)");
                        })
                        .toList();
            case TypeVarRef variable ->
                throw new IllegalArgumentException(
                        owner + " cannot have the type variable " + variable.name() + " for a supertype");
        };
    }

    /// The group of the methods the class inherits under a signature.
    ///
    /// @param signature the erased signature, in the class
    /// @return the group, or empty if the class inherits no method of that signature
    Optional<Group> group(MethodSignature signature) {
        return Optional.ofNullable(groups.get(signature));
    }

    /// The groups, each one method to the class.
    ///
    /// @return the groups, in the order the supertypes tell them
    Stream<Group> groups() {
        return groups.values().stream();
    }

    /// An inherited method of another signature that a method of `signature` in the class would
    /// clash with: it erases, or one it overrides does, to the parameters of `signature`, so the
    /// bridge javac makes for it would be the method (JLS 8.4.8.3).
    ///
    /// @param signature the erased signature of a method of the class
    /// @return such an inherited method, if there is one
    Optional<Entry> bridged(MethodSignature signature) {
        return groups().filter(group -> !group.signature().equals(signature)
                        && group.signature().name().equals(signature.name()))
                .flatMap(group -> group.entries().stream())
                .filter(entry -> entry.method().erasures().contains(signature.params()))
                .findFirst();
    }

    /// The supertypes, the superclass first.
    ///
    /// @return the supertypes
    List<Supertype> supertypes() {
        return supertypes;
    }

    /// The class as a proof of subtyping knows it: of the supertypes it has so far.
    ///
    /// @param self the token of the class being declared
    /// @return the class with its supertypes
    Subtyping.Declaring declaring(DeclaredToken<?> self) {
        return new Subtyping.Declaring(
                self.erasure(),
                supertypes.stream().<DeclaredToken<?>>map(Supertype::token).toList());
    }

    // ------------------------------------------------------------------
    // The merge
    // ------------------------------------------------------------------

    private static Map<MethodSignature, Group> merged(Heir heir, List<Supertype> supertypes) {
        String owner = heir.described();
        requireImplementable(heir, supertypes);
        Set<MethodSignature> ofObject = supertypes.stream()
                .filter(supertype -> !isInterface(supertype))
                .flatMap(supertype -> entries(heir, supertype))
                .filter(entry -> entry.method().visibility() == Heritage.Visibility.PUBLIC
                        && (entry.method().declaredBy().equals(ConstantDescs.CD_Object)
                                || entry.method().overrides().contains(ConstantDescs.CD_Object)))
                .map(Entry::signature)
                .collect(Collectors.toSet());
        Map<MethodSignature, List<Entry>> bySignature = supertypes.stream()
                .flatMap(supertype -> isInterface(supertype)
                        ? entries(heir, supertype).filter(entry -> !ofObject.contains(entry.signature()))
                        : entries(heir, supertype))
                .collect(Collectors.groupingBy(Entry::signature, LinkedHashMap::new, Collectors.toList()));
        Map<MethodSignature, List<Entry>> hidden = supertypes.stream()
                .flatMap(Inheritance::told)
                .filter(entry -> !reached(heir, entry))
                .collect(Collectors.groupingBy(Entry::signature));
        Map<MethodSignature, Group> groups = bySignature.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new Group(
                                entry.getKey(),
                                inherited(entry.getValue()),
                                hidden.getOrDefault(entry.getKey(), List.of())),
                        (first, _) -> first,
                        LinkedHashMap::new));
        groups.values().forEach(group -> requireAgreed(owner, group));
        requireNoBridgeClash(owner, groups);
        return Collections.unmodifiableMap(groups);
    }

    private static boolean isInterface(Supertype supertype) {
        return supertype.token() instanceof InterfaceToken<?>;
    }

    /// Every declaration of a supertype, as an entry.
    private static Stream<Entry> told(Supertype supertype) {
        List<ClassDesc> erasures = supertype.token().argumentErasures();
        return supertype.told().methods().stream().flatMap(method -> {
            MethodSignature signature = method.signature().instantiate(erasures);
            return method.declarations().stream()
                    .map(declared -> new Entry(supertype.token(), new Member(method, declared), signature));
        });
    }

    /// The declarations of a supertype that are something to `heir`: not the method with package
    /// access of a class of another package than that of a class being declared.
    private static Stream<Entry> entries(Heir heir, Supertype supertype) {
        return told(supertype).filter(entry -> reached(heir, entry));
    }

    private static boolean reached(Heir heir, Entry entry) {
        return switch (entry.method().visibility()) {
            case PUBLIC, PROTECTED -> true;
            case PACKAGE ->
                switch (heir) {
                    case Heir.OfClass(var _, var packageName) ->
                        entry.method().declaredBy().packageName().equals(packageName);
                    case Heir.OfVariable _ -> true;
                };
        };
    }

    /// Rejects, for a class being declared, an abstract method with package access: the typed layer
    /// declares no method that overrides one of package access, and outside the package none can.
    private static void requireImplementable(Heir heir, List<Supertype> supertypes) {
        switch (heir) {
            case Heir.OfVariable _ -> {}
            case Heir.OfClass ofClass ->
                supertypes.stream()
                        .flatMap(Inheritance::told)
                        .filter(entry -> entry.method().visibility() == Heritage.Visibility.PACKAGE
                                && entry.method().dispatch() == Heritage.Dispatch.ABSTRACT)
                        .findFirst()
                        .ifPresent(entry -> {
                            throw new IllegalArgumentException(ofClass.described() + " cannot have the supertype "
                                    + entry.through() + ": " + entry + " is abstract and has package access, so"
                                    + " a class of another package cannot implement it, and the typed layer"
                                    + " declares no method that overrides one of package access (JLS 8.1.1.1,"
                                    + " 8.4.8.1)");
                        });
        }
    }

    /// The methods of one signature the class inherits: not the one another overrides, and one
    /// that two supertypes have from a common supertype once.
    private static List<Entry> inherited(List<Entry> entries) {
        return IntStream.range(0, entries.size())
                .filter(i -> IntStream.range(0, entries.size())
                        .noneMatch(j -> j != i && shadows(entries.get(j), j, entries.get(i), i)))
                .mapToObj(entries::get)
                .toList();
    }

    private static boolean shadows(Entry by, int byIndex, Entry shadowed, int shadowedIndex) {
        ClassDesc declarer = shadowed.method().declaredBy();
        return by.method().overrides().contains(declarer)
                || (by.method().declaredBy().equals(declarer) && byIndex < shadowedIndex);
    }

    /// Rejects a group whose methods the class cannot inherit together, whatever it declares.
    private static void requireAgreed(String owner, Group group) {
        Entry first = group.entries().getFirst();
        group.entries().stream().skip(1).forEach(other -> {
            if (first.isStatic() != other.isStatic()) {
                throw new IllegalArgumentException(owner + " cannot inherit both " + first + " and " + other
                        + ": one is static and the other an instance method of the same signature, and no"
                        + " method of the class makes them agree (JLS 8.4.8.1, 8.4.8.2)");
            }
            if (!first.method().sameParameters(other.method())) {
                throw new IllegalArgumentException(owner + " cannot inherit both " + first + " and " + other
                        + ": name clash, both erase to " + group.signature()
                        + " and neither overrides the other (JLS 8.4.8.3)");
            }
        });
        group.entries().stream()
                .filter(entry -> entry.method().visibility() == Heritage.Visibility.PACKAGE)
                .findFirst()
                .ifPresent(hidden -> group.entries().stream()
                        .filter(Entry::ofInterface)
                        .findFirst()
                        .ifPresent(other -> {
                            throw new IllegalArgumentException(owner + " cannot inherit both " + hidden + " and "
                                    + other + ": the first has package access, and a method that stands for one"
                                    + " of an interface is public (JLS 8.4.8.3) — the typed layer does not tell"
                                    + " whether a method with package access is inherited, and holds it to"
                                    + " what it would have to be");
                        }));
    }

    /// Rejects two groups one of which has a method whose bridge would be a method of the other.
    private static void requireNoBridgeClash(String owner, Map<MethodSignature, Group> groups) {
        groups.values()
                .forEach(group -> groups.values().stream()
                        .filter(other -> other != group
                                && other.signature()
                                        .name()
                                        .equals(group.signature().name()))
                        .flatMap(other -> other.entries().stream())
                        .filter(entry -> entry.method()
                                        .erasures()
                                        .contains(group.signature().params())
                                || group.entries().stream()
                                        .anyMatch(mine -> mine.method().erasures().stream()
                                                .anyMatch(entry.method().erasures()::contains)))
                        .findFirst()
                        .ifPresent(entry -> {
                            throw new IllegalArgumentException(owner + " cannot inherit both "
                                    + group.entries().getFirst() + " and " + entry + ": name clash, the methods are of"
                                    + " different signatures in the class and of one erasure, so the bridge of one"
                                    + " would be the other (JLS 8.4.8.3)");
                        }));
    }

    private static void requireOneParameterization(String owner, List<Supertype> supertypes) {
        Map<ClassDesc, List<ParameterizedTypeRef>> byRaw = supertypes.stream()
                .flatMap(Inheritance::parameterizations)
                .distinct()
                .collect(Collectors.groupingBy(ParameterizedTypeRef::raw, LinkedHashMap::new, Collectors.toList()));
        byRaw.values().stream()
                .filter(parameterizations -> parameterizations.size() > 1)
                .findFirst()
                .ifPresent(parameterizations -> {
                    throw new IllegalArgumentException(owner + " cannot inherit "
                            + parameterizations.stream()
                                    .map(TypeNames::describe)
                                    .collect(Collectors.joining(" and "))
                            + ": a class has one parameterization of a generic type among its supertypes"
                            + " (JLS 8.1.5)");
                });
    }

    /// The generic types a supertype makes the class a subtype of, each with its type arguments.
    private static Stream<ParameterizedTypeRef> parameterizations(Supertype supertype) {
        DeclaredToken<?> token = supertype.token();
        return switch (token.typeRef()) {
            case ParameterizedTypeRef parameterized -> {
                List<TypeRef> arguments = parameterized.args().stream()
                        .flatMap(argument -> argument instanceof ExactTypeArg(TypeRef type)
                                ? Stream.of(type)
                                : Stream.<TypeRef>empty())
                        .toList();
                yield Stream.concat(
                        Stream.of(parameterized),
                        token.supertypes().supertypes().stream()
                                .flatMap(generic -> token.supertypes().supertype(generic.raw(), arguments).stream()));
            }
            case ClassTypeRef _ -> token.supertypes().supertypes().stream();
            case TypeVarRef _ -> Stream.empty();
        };
    }

    /// A supertype of the class being declared.
    ///
    /// @param token the supertype
    /// @param told its heritage, in terms of the type arguments of `token`
    record Supertype(DeclaredToken<?> token, Heritage.Applied told) {}

    /// Who inherits: what decides whether a method with package access is a member to inherit.
    sealed interface Heir {

        /// The heir, for messages.
        ///
        /// @return the description
        String described();

        /// A class being declared: a method with package access of a class of another package is
        /// none of its members (JLS 8.4.8.1), and it has to implement every abstract method.
        ///
        /// @param described the class, for messages
        /// @param packageName the package of the class
        record OfClass(String described, String packageName) implements Heir {}

        /// A type variable, by the class javac makes of its bounds (JLS 4.9): an abstract class of
        /// no package the typed layer knows, so every method with package access is held one of its.
        ///
        /// @param described the type variable, for messages
        record OfVariable(String described) implements Heir {}
    }

    /// One declaration of a method of a heritage, with what holds of every declaration the supertype
    /// has under its signature.
    ///
    /// @param group the methods the supertype has under the signature
    /// @param declared the declaration, one of those of `group`
    record Member(Heritage.Method<Heritage.Inherited> group, Heritage.Inherited declared) {

        Heritage.Dispatch dispatch() {
            return declared.by().dispatch();
        }

        Heritage.Visibility visibility() {
            return declared.by().visibility();
        }

        ClassDesc declaredBy() {
            return declared.by().type();
        }

        Set<List<ClassDesc>> erasures() {
            return group.erasures();
        }

        List<ClassDesc> overrides() {
            return group.overrides();
        }

        MethodTableTemplate.Signature signature() {
            return group.signature();
        }

        /// Whether the two methods, which erase alike, have the same parameter types: two generic
        /// methods if they are told alike by the positions of their type parameters.
        boolean sameParameters(Member other) {
            return switch (declared) {
                case Heritage.Plain plain ->
                    other.declared() instanceof Heritage.Plain otherPlain
                            && plain.params().equals(otherPlain.params());
                case Heritage.Generic(Heritage.Declaration told) ->
                    other.declared() instanceof Heritage.Generic(Heritage.Declaration otherTold)
                            && told.typeParams().equals(otherTold.typeParams())
                            && told.params().equals(otherTold.params());
            };
        }

        /// Whether the two methods, of the same parameters, have the same result as it is written.
        boolean sameResult(Member other) {
            return switch (declared) {
                case Heritage.Plain plain ->
                    other.declared() instanceof Heritage.Plain otherPlain
                            && plain.result().equals(otherPlain.result());
                case Heritage.Generic(Heritage.Declaration told) ->
                    other.declared() instanceof Heritage.Generic(Heritage.Declaration otherTold)
                            && told.result().equals(otherTold.result());
            };
        }

        /// The exception types of the `throws` clause the other method does not have in its own, as
        /// they are written.
        private List<String> undeclaredBy(Member other) {
            return switch (declared) {
                case Heritage.Plain plain ->
                    other.declared() instanceof Heritage.Plain otherPlain
                            ? plain.throwsTypes().stream()
                                    .filter(thrown -> !otherPlain.throwsTypes().contains(thrown))
                                    .map(TypeNames::describe)
                                    .toList()
                            : List.of("of a method that is not generic, as the other is");
                case Heritage.Generic(Heritage.Declaration told) ->
                    other.declared() instanceof Heritage.Generic(Heritage.Declaration otherTold)
                            ? told.throwsTypes().stream()
                                    .filter(thrown -> !otherTold.throwsTypes().contains(thrown))
                                    .map(Object::toString)
                                    .toList()
                            : List.of("of a generic method, as the other is not");
            };
        }

        /// Why the method, one of a class, cannot be had for `other`, one of an interface of the same
        /// parameters, without a method that overrides both: what an override has to observe, the
        /// method of the class has to (JLS 8.4.8.3, 8.4.8.4) — held to the types as they are
        /// written, there being no fact of either to prove a subtype by.
        ///
        /// @param other a method of an interface
        /// @return the reason, or empty if the method stands for the other
        Optional<String> cannotStandFor(Member other) {
            return switch (visibility()) {
                case PROTECTED, PACKAGE ->
                    Optional.of("it is not public, and the method of an interface is: weaker access (JLS 8.4.8.3)");
                case PUBLIC ->
                    declared.arity() != other.declared().arity()
                            ? Optional.of("the last parameter of one is of variable arity and that of the other"
                                    + " an array, of which javac warns")
                            : !sameResult(other)
                                    ? Optional.of("its result is not the one the other has — a result that is a subtype"
                                            + " would do (JLS 8.4.5), which the typed layer proves by the fact of a"
                                            + " method alone, and there is none here")
                                    : undeclaredBy(other).isEmpty()
                                            ? Optional.empty()
                                            : Optional.of("it declares the exception "
                                                    + String.join(", ", undeclaredBy(other))
                                                    + ", which the other does not (JLS 8.4.8.3) — an unchecked one"
                                                    + " would do, which the typed layer does not tell here");
            };
        }
    }

    /// A method a supertype has, as a member the class inherits.
    ///
    /// @param through the supertype of the class that has the method
    /// @param method the method, in terms of the type arguments of `through`
    /// @param signature the erased signature of the method in the class
    record Entry(DeclaredToken<?> through, Member method, MethodSignature signature) {

        boolean isStatic() {
            return switch (method.dispatch()) {
                case STATIC -> true;
                case ABSTRACT, DEFAULT, CONCRETE, FINAL -> false;
            };
        }

        /// Whether an interface declares the method.
        boolean ofInterface() {
            return switch (method.declared().by()) {
                case Heritage.Declarer.OfInterface _ -> true;
                case Heritage.Declarer.OfClass _ -> false;
            };
        }

        /// Whether the supertype the class has the method through leaves it abstract: what the
        /// proof that the class extends or implements that supertype has a step for.
        boolean leftAbstract() {
            return switch (method.group().standing()) {
                case ABSTRACT -> true;
                case STATIC, DEFAULT, CONCRETE, FINAL -> false;
            };
        }

        /// How the method differs from what `fact` tells of a method of this signature: its
        /// parameters, its result or its `throws` clause. A fact of a class above the one that has
        /// the method tells the method as that class has it, which a class between may have
        /// overridden with a narrower result or clause.
        ///
        /// The types are compared as they are written. A fact and the heritage of a type are
        /// written by one translator of one model of the type, and the heritage is applied to the
        /// type arguments the token of the fact's owner has: a type that is the same is written the
        /// same — a primitive, an array, a parameterization with a wildcard, a type argument of the
        /// class. Were one written otherwise, the method would be rejected here, not let through.
        ///
        /// @param fact the fact of a method of the erased signature of this one
        /// @return the difference, or empty if the method is as the fact tells it
        Optional<String> differsFrom(Invocable fact) {
            List<TypeRef> params =
                    fact.params().stream().<TypeRef>map(TypeToken::typeRef).toList();
            Heritage.Result<TypeRef> result = fact.resultType()
                    .<Heritage.Result<TypeRef>>map(type -> new Heritage.Result.Of<>(type.typeRef()))
                    .orElseGet(Heritage.Result::nothing);
            Set<TypeRef> thrown = fact.traits().throwsTypes().stream()
                    .<TypeRef>map(TypeToken::typeRef)
                    .collect(Collectors.toSet());
            return switch (method.declared()) {
                case Heritage.Generic _ -> Optional.of("it is generic, and the fact tells it with type arguments");
                case Heritage.Plain plain ->
                    !params.equals(plain.params())
                            ? Optional.of("its parameters are not those of the fact")
                            : !result.equals(plain.result())
                                    ? Optional.of("its result is not that of the fact")
                                    : !thrown.equals(Set.<TypeRef>copyOf(plain.throwsTypes()))
                                            ? Optional.of("its throws clause is not that of the fact")
                                            : Optional.empty();
            };
        }

        /// Whether the method comes with a body the class would inherit.
        boolean hasBody() {
            return switch (method.dispatch()) {
                case DEFAULT, CONCRETE, FINAL -> true;
                case ABSTRACT, STATIC -> false;
            };
        }

        @Override
        public String toString() {
            return signature + " of " + TypeNames.describe(method.declaredBy())
                    + (through.shape().desc().equals(method.declaredBy()) ? "" : ", inherited through " + through);
        }
    }

    /// The methods of one erased signature the class inherits: one method to the class.
    ///
    /// @param signature the erased signature, in the class
    /// @param entries the methods, none of which overrides another; at least one
    /// @param hidden the methods of that signature with package access of the classes of another
    ///     package above the class: no members of the class to javac, and yet the methods the
    ///     virtual machine finds first when the method is called on the class (JVMS 5.4.3.3, 5.4.6)
    record Group(MethodSignature signature, List<Entry> entries, List<Entry> hidden) {
        Group {
            entries = List.copyOf(entries);
            hidden = List.copyOf(hidden);
        }

        /// What the method is to the class, without a method of its own: the declarations told as
        /// those of one type ([Heritage.Method#standing()]).
        ///
        /// @return the dispatch
        Heritage.Dispatch standing() {
            return new Heritage.Method<>(
                            entries.getFirst().method().signature(),
                            entries.stream()
                                    .map(entry -> entry.method().declared())
                                    .toList(),
                            entries.stream()
                                    .flatMap(entry -> entry.method().erasures().stream())
                                    .collect(Collectors.toSet()),
                            entries.stream()
                                    .flatMap(entry -> entry.method().overrides().stream())
                                    .distinct()
                                    .toList())
                    .standing();
        }

        /// Whether the last parameter of the method is of variable arity: as the first declaration
        /// has it, which an override is held to be of every one ([SignatureRegistry]).
        ///
        /// @return the arity
        Heritage.Arity arity() {
            return entries.getFirst().method().declared().arity();
        }

        /// The declaration that tells the standing: that of a class the heir reaches, which stands
        /// whatever an interface declares, or the first there is.
        ///
        /// @return the entry
        Entry stands() {
            return ofClass(entries).orElseGet(entries::getFirst);
        }

        /// Why a class that has these methods and declares none of their signature is rejected.
        ///
        /// @return the reason, or empty if the class can have them together
        Optional<String> conflict() {
            return conflict(entries).or(this::unreached);
        }

        /// A method with package access of a class of another package above the class, beside
        /// methods of interfaces alone: javac compiles the class, which has the method of the
        /// interface for its own, and a call of it fails where it runs — the virtual machine
        /// selects the method of the class above, which the class does not reach.
        private Optional<String> unreached() {
            return hidden.stream()
                    .findFirst()
                    .filter(_ -> ofClass(entries).isEmpty())
                    .map(unreached -> unreached + " has package access in another package, where the class has "
                            + entries.getFirst() + " under its signature: javac takes the method of the"
                            + " interface for a member of the class, and the virtual machine selects the one"
                            + " with package access, which the class does not reach — IllegalAccessError"
                            + " where it is called (JVMS 5.4.6)");
        }

        /// As [#conflict()], of the methods no step of a proof settles: a method the supertype it
        /// comes through leaves abstract is implemented by what the step for it took — a method
        /// of the class, or one of the superclass held to it by its fact.
        ///
        /// @return the reason, or empty if nothing is left for a method of the class to settle
        Optional<String> unsettled() {
            return conflict(entries.stream()
                            .filter(entry -> !entry.leftAbstract())
                            .toList())
                    .or(this::unreached);
        }

        private static Optional<Entry> ofClass(List<Entry> entries) {
            return ofClasses(entries).findFirst();
        }

        private static Stream<Entry> ofClasses(List<Entry> entries) {
            return entries.stream()
                    .filter(entry ->
                            !entry.ofInterface() && entry.method().visibility() != Heritage.Visibility.PACKAGE);
        }

        /// JLS 8.4.8.4, 9.4.1.3: the method of a class stands for those of the interfaces, and has
        /// to be what an override of them is; without one, an interface that gives a body leaves
        /// no other interface a say.
        private static Optional<String> conflict(List<Entry> entries) {
            List<Entry> ofInterfaces =
                    entries.stream().filter(Entry::ofInterface).toList();
            if (ofClasses(entries).count() > 1) {
                // m(T) of a Base<String> and m(String) of its subclass: two methods, one signature
                return Optional.of(ofClasses(entries).map(Object::toString).collect(Collectors.joining(" and "))
                        + " are unrelated methods of the classes above, of one signature in the class and"
                        + " neither overriding the other (JLS 8.4.8.3)");
            }
            return ofClass(entries)
                    .map(standing -> ofInterfaces.stream()
                            .flatMap(other -> standing.method().cannotStandFor(other.method()).stream()
                                    .map(why -> standing + " cannot stand for " + other + ": " + why))
                            .findFirst())
                    .orElseGet(() -> ofInterfaces.size() < 2
                                    || ofInterfaces.stream().noneMatch(Entry::hasBody)
                            ? Optional.empty()
                            : Optional.of(
                                    ofInterfaces.stream().map(Object::toString).collect(Collectors.joining(" and "))
                                            + (ofInterfaces.stream().allMatch(Entry::hasBody)
                                                    ? " are unrelated methods with a body each"
                                                    : " are unrelated methods of which one gives a body and another"
                                                            + " leaves abstract")
                                            + ", and no class among the supertypes declares the method"
                                            + " (JLS 8.4.8.4, 9.4.1.3)"));
        }
    }
}
