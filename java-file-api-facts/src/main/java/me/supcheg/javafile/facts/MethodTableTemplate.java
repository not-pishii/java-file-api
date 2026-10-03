package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ClassDesc;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The [MethodTable] of a generic type before its type arguments are known,
/// as a [TypeShape] records it.
///
/// A parameter's erasure may depend on the type arguments: `add(E)` of
/// `List<E>` erases to `add(String)` in `List<String>`. A parameter is
/// therefore either [Fixed], an erasure known up front, or [Var], the
/// erasure of the type argument for a type parameter of the type, by
/// position, or an array of it. [#instantiate(List)] fills the variables in;
/// a token does so once, with the erasures of its type arguments.
///
/// The signatures of the template are the identities of the members: two
/// overloads that erase to one signature under some type arguments — `m(T)`
/// and `m(String)` of a `Box<String>` — are still two signatures here. A
/// method or constructor fact names its own ([Invocable#declared()]), and
/// lowering tells by them what else javac has to choose from.
///
/// The constructors are listed under the simple name of the class, as
/// [Invocable#name()] of a constructor fact is; an instantiated
/// [MethodTable] has none.
///
/// @param abstractMethods the instance methods without an implementation
/// @param concreteMethods the instance methods with an implementation
/// @param staticMethods the `static` methods
/// @param constructors the constructors that are not `private`
public record MethodTableTemplate(
        Set<Signature> abstractMethods,
        Set<Signature> concreteMethods,
        Set<Signature> staticMethods,
        Set<Signature> constructors) {

    /// A template with no methods.
    public static final MethodTableTemplate EMPTY = new MethodTableTemplate(Set.of(), Set.of(), Set.of());

    /// @throws IllegalArgumentException if a method is both abstract and concrete, or
    ///                                  both static and an instance method
    public MethodTableTemplate {
        abstractMethods = Set.copyOf(abstractMethods);
        concreteMethods = Set.copyOf(concreteMethods);
        staticMethods = Set.copyOf(staticMethods);
        constructors = Set.copyOf(constructors);
        Set<Signature> both = new HashSet<>(abstractMethods);
        both.retainAll(concreteMethods);
        if (!both.isEmpty()) {
            throw new IllegalArgumentException("methods both abstract and concrete: " + both);
        }
        Set<Signature> instance = new HashSet<>(abstractMethods);
        instance.addAll(concreteMethods);
        instance.retainAll(staticMethods);
        if (!instance.isEmpty()) {
            throw new IllegalArgumentException("methods both static and instance: " + instance);
        }
    }

    /// A template that lists no constructors.
    ///
    /// @param abstractMethods the instance methods without an implementation
    /// @param concreteMethods the instance methods with an implementation
    /// @param staticMethods the `static` methods
    /// @throws IllegalArgumentException if a method is both abstract and concrete, or
    ///                                  both static and an instance method
    public MethodTableTemplate(
            Set<Signature> abstractMethods, Set<Signature> concreteMethods, Set<Signature> staticMethods) {
        this(abstractMethods, concreteMethods, staticMethods, Set.of());
    }

    /// The template of a table that does not depend on type arguments: every
    /// parameter is [Fixed]. It lists no constructors.
    ///
    /// @param table the table
    /// @return the template
    /// @throws IllegalArgumentException if a method of `table` is both abstract and concrete
    public static MethodTableTemplate of(MethodTable table) {
        return new MethodTableTemplate(
                fixed(table.abstractMethods()), fixed(table.concreteMethods()), fixed(table.staticMethods()));
    }

    /// Every method of the template, instance and `static`: what javac
    /// considers for a call by name (JLS 15.12.2.1).
    ///
    /// @return the signatures of the methods
    public Stream<Signature> methods() {
        return Stream.of(abstractMethods, concreteMethods, staticMethods).flatMap(Set::stream);
    }

    /// The number of type parameters the template refers to: one more than
    /// the greatest [Var#index()], `0` without variables.
    ///
    /// @return the least number of type parameters of a type with this template
    public int typeParameterCount() {
        return Stream.concat(methods(), constructors.stream())
                .flatMap(s -> s.params().stream())
                .mapToInt(p -> switch (p) {
                    case Fixed _ -> 0;
                    case Var(int index, int _) -> index + 1;
                })
                .max()
                .orElse(0);
    }

    /// The table with every [Var] replaced by the erasure at its index.
    ///
    /// @param arguments the erasures of the type arguments, one per type parameter
    /// @return the table
    /// @throws IllegalArgumentException if a [Var] has no erasure in `arguments`
    public MethodTable instantiate(List<ClassDesc> arguments) {
        if (typeParameterCount() > arguments.size()) {
            throw new IllegalArgumentException("the template refers to " + typeParameterCount()
                    + " type parameters, but " + arguments.size() + " type arguments are given");
        }
        return new MethodTable(
                instantiate(abstractMethods, arguments),
                instantiate(concreteMethods, arguments),
                instantiate(staticMethods, arguments));
    }

    private static Set<MethodSignature> instantiate(Set<Signature> signatures, List<ClassDesc> arguments) {
        return signatures.stream().map(s -> s.instantiate(arguments)).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Signature> fixed(Set<MethodSignature> signatures) {
        return signatures.stream()
                .map(s -> new Signature(
                        s.name(), s.params().stream().<Param>map(Fixed::new).toList()))
                .collect(Collectors.toUnmodifiableSet());
    }

    /// A method signature of the template: a name and a [Param] per
    /// parameter.
    ///
    /// @param name the method name
    /// @param params the parameters, in order
    public record Signature(String name, List<Param> params) {

        /// @throws IllegalArgumentException if `name` is not a Java identifier
        public Signature {
            Identifiers.requireValid(name);
            params = List.copyOf(params);
        }

        /// Creates a signature.
        ///
        /// @param name the method name
        /// @param params the parameters, in order
        /// @return the signature
        public static Signature of(String name, Param... params) {
            return new Signature(name, List.of(params));
        }

        /// The erased signature under type arguments: every [Var] replaced
        /// by the erasure at its index.
        ///
        /// @param arguments the erasures of the type arguments, one per type parameter
        /// @return the erased signature
        /// @throws IllegalArgumentException if a [Var] has no erasure in `arguments`
        public MethodSignature instantiate(List<ClassDesc> arguments) {
            return new MethodSignature(
                    name, params.stream().map(p -> p.instantiate(arguments)).toList());
        }

        @Override
        public String toString() {
            return params.stream().map(Param::toString).collect(Collectors.joining(", ", name + "(", ")"));
        }
    }

    /// A parameter of a [Signature]: its erasure, or where to take it from.
    public sealed interface Param permits Fixed, Var {

        /// The erasure of the parameter under type arguments.
        ///
        /// @param arguments the erasures of the type arguments, one per type parameter
        /// @return the erasure
        /// @throws IllegalArgumentException if the parameter is a [Var] with no erasure in `arguments`
        ClassDesc instantiate(List<ClassDesc> arguments);

        /// A parameter of a known erasure.
        ///
        /// @param erasure the erasure
        /// @return the parameter
        static Fixed fixed(ClassDesc erasure) {
            return new Fixed(erasure);
        }

        /// A parameter erased to the type argument for a type parameter.
        ///
        /// @param index the position of the type parameter
        /// @return the parameter
        static Var var(int index) {
            return new Var(index, 0);
        }

        /// A parameter erased to an array of the type argument for a type
        /// parameter: `T[]`, and `T...`.
        ///
        /// @param index the position of the type parameter
        /// @param dimensions the number of array dimensions, `0` for the type parameter itself
        /// @return the parameter
        static Var var(int index, int dimensions) {
            return new Var(index, dimensions);
        }
    }

    /// A parameter whose erasure does not depend on the type arguments.
    ///
    /// @param erasure the erasure, a class, interface, array or primitive type
    public record Fixed(ClassDesc erasure) implements Param {

        /// @throws IllegalArgumentException if `erasure` is `void`
        public Fixed {
            if (erasure.descriptorString().equals("V")) {
                throw new IllegalArgumentException("a parameter cannot be void");
            }
        }

        @Override
        public ClassDesc instantiate(List<ClassDesc> arguments) {
            return erasure;
        }

        @Override
        public String toString() {
            return TypeNames.describe(erasure);
        }
    }

    /// A parameter erased to the erasure of the type argument for a type
    /// parameter of the type, or to an array of it: `T`, `T[]`, `T...`.
    ///
    /// @param index the position of the type parameter, from `0`
    /// @param dimensions the number of array dimensions, `0` for the type parameter itself
    public record Var(int index, int dimensions) implements Param {

        /// @throws IllegalArgumentException if `index` or `dimensions` is negative
        public Var {
            if (index < 0) {
                throw new IllegalArgumentException("a type parameter index cannot be negative, got " + index);
            }
            if (dimensions < 0) {
                throw new IllegalArgumentException("an array cannot have " + dimensions + " dimensions");
            }
        }

        @Override
        public ClassDesc instantiate(List<ClassDesc> arguments) {
            if (index >= arguments.size()) {
                throw new IllegalArgumentException(
                        "no type argument for type parameter #" + index + ", " + arguments.size() + " are given");
            }
            ClassDesc argument = arguments.get(index);
            return dimensions == 0 ? argument : argument.arrayType(dimensions);
        }

        @Override
        public String toString() {
            return "#" + index + "[]".repeat(dimensions);
        }
    }
}
