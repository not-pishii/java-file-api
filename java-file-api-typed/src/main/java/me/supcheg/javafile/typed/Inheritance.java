package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
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
/// - The supertypes have to agree, and a disagreement is rejected when the
///   supertype that brings it is added: a `static` method and an instance one
///   of one signature, methods of one erasure whose parameters differ, two
///   methods one of which would get the bridge of the other (JLS 8.4.8.3),
///   one interface with two parameterizations (JLS 8.1.5).
///
/// What a member the class declares has to observe is told here as data —
/// the group a signature is of, the method whose erasure it would clash with
/// — and judged by the [SignatureRegistry] of the class.
final class Inheritance {
    private final String owner;
    private final List<Supertype> supertypes;
    private final Map<MethodSignature, Group> groups;

    private Inheritance(String owner, List<Supertype> supertypes) {
        this.owner = owner;
        this.supertypes = List.copyOf(supertypes);
        this.groups = merged(owner, this.supertypes);
    }

    /// The inheritance of a class that extends `superclass` and implements nothing yet.
    ///
    /// @param owner the class being declared, for messages
    /// @param superclass the superclass with its heritage
    /// @return the inheritance
    /// @throws IllegalArgumentException if the heritage disagrees with itself
    static Inheritance of(String owner, Supertype superclass) {
        return new Inheritance(owner, List.of(superclass));
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
        return new Inheritance(owner, all);
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
            case Heritage.Told told ->
                new Supertype(token, told.applied(shape.typeParameters(), arguments(owner, token)));
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

    // ------------------------------------------------------------------
    // The merge
    // ------------------------------------------------------------------

    private static Map<MethodSignature, Group> merged(String owner, List<Supertype> supertypes) {
        Set<MethodSignature> ofObject = supertypes.stream()
                .filter(supertype -> !isInterface(supertype))
                .flatMap(Inheritance::entries)
                .filter(entry -> entry.method().visibility() == Heritage.Visibility.PUBLIC
                        && (entry.method().declaredBy().equals(ConstantDescs.CD_Object)
                                || entry.method().overrides().contains(ConstantDescs.CD_Object)))
                .map(Entry::signature)
                .collect(Collectors.toSet());
        Map<MethodSignature, List<Entry>> bySignature = supertypes.stream()
                .flatMap(supertype -> isInterface(supertype)
                        ? entries(supertype).filter(entry -> !ofObject.contains(entry.signature()))
                        : entries(supertype))
                .collect(Collectors.groupingBy(Entry::signature, LinkedHashMap::new, Collectors.toList()));
        Map<MethodSignature, Group> groups = bySignature.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new Group(entry.getKey(), inherited(entry.getValue())),
                        (first, _) -> first,
                        LinkedHashMap::new));
        groups.values().forEach(group -> requireAgreed(owner, group));
        requireNoBridgeClash(owner, groups);
        return Collections.unmodifiableMap(groups);
    }

    private static boolean isInterface(Supertype supertype) {
        return supertype.token() instanceof InterfaceToken<?>;
    }

    private static Stream<Entry> entries(Supertype supertype) {
        List<ClassDesc> erasures = supertype.token().argumentErasures();
        return supertype.told().methods().stream()
                .map(method ->
                        new Entry(supertype.token(), method, method.signature().instantiate(erasures)));
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
            if (!first.method().params().equals(other.method().params())) {
                throw new IllegalArgumentException(owner + " cannot inherit both " + first + " and " + other
                        + ": name clash, both erase to " + group.signature()
                        + " and neither overrides the other (JLS 8.4.8.3)");
            }
        });
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
    record Supertype(DeclaredToken<?> token, Heritage.Told told) {

        /// The methods the type has, as a member of its own, under a signature.
        ///
        /// @param signature the erased signature, in the type as `token` names it
        /// @return the methods of that signature
        Stream<Entry> members(MethodSignature signature) {
            return entries(this).filter(entry -> entry.signature().equals(signature));
        }
    }

    /// A method a supertype has, as a member the class inherits.
    ///
    /// @param through the supertype of the class that has the method
    /// @param method the method, in terms of the type arguments of `through`
    /// @param signature the erased signature of the method in the class
    record Entry(DeclaredToken<?> through, Heritage.Method method, MethodSignature signature) {

        boolean isStatic() {
            return method.dispatch() == Heritage.Dispatch.STATIC;
        }

        boolean isAbstract() {
            return method.dispatch() == Heritage.Dispatch.ABSTRACT;
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
            Heritage.Result result = fact.resultType()
                    .<Heritage.Result>map(type -> new Heritage.Result.Of(type.typeRef()))
                    .orElseGet(Heritage.Result.Nothing::new);
            Set<TypeRef> thrown = fact.traits().throwsTypes().stream()
                    .<TypeRef>map(TypeToken::typeRef)
                    .collect(Collectors.toSet());
            return !params.equals(method.params())
                    ? Optional.of("its parameters are not those of the fact")
                    : !result.equals(method.result())
                            ? Optional.of("its result is not that of the fact")
                            : !thrown.equals(Set.<TypeRef>copyOf(method.throwsTypes()))
                                    ? Optional.of("its throws clause is not that of the fact")
                                    : Optional.empty();
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
    record Group(MethodSignature signature, List<Entry> entries) {
        Group {
            entries = List.copyOf(entries);
        }

        /// The methods with a body. More than one are bodies of unrelated supertypes, none of
        /// which the class can be said to inherit.
        ///
        /// @return the methods that are neither abstract nor `static`
        List<Entry> bodies() {
            return entries.stream().filter(Entry::hasBody).toList();
        }
    }
}
