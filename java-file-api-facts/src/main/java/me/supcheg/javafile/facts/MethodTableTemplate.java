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
/// position. [#instantiate(List)] fills the variables in; a token does so
/// once, with the erasures of its type arguments.
///
/// @param abstractMethods the methods without an implementation
/// @param concreteMethods the methods with an implementation
public record MethodTableTemplate(Set<Signature> abstractMethods, Set<Signature> concreteMethods) {

    /// A template with no methods.
    public static final MethodTableTemplate EMPTY = new MethodTableTemplate(Set.of(), Set.of());

    /// @throws IllegalArgumentException if a method is both abstract and concrete
    public MethodTableTemplate {
        abstractMethods = Set.copyOf(abstractMethods);
        concreteMethods = Set.copyOf(concreteMethods);
        Set<Signature> both = new HashSet<>(abstractMethods);
        both.retainAll(concreteMethods);
        if (!both.isEmpty()) {
            throw new IllegalArgumentException("methods both abstract and concrete: " + both);
        }
    }

    /// The template of a table that does not depend on type arguments: every
    /// parameter is [Fixed].
    ///
    /// @param table the table
    /// @return the template
    /// @throws IllegalArgumentException if a method of `table` is both abstract and concrete
    public static MethodTableTemplate of(MethodTable table) {
        return new MethodTableTemplate(fixed(table.abstractMethods()), fixed(table.concreteMethods()));
    }

    /// The number of type parameters the template refers to: one more than
    /// the greatest [Var#index()], `0` without variables.
    ///
    /// @return the least number of type parameters of a type with this template
    public int typeParameterCount() {
        return Stream.concat(abstractMethods.stream(), concreteMethods.stream())
                .flatMap(s -> s.params().stream())
                .mapToInt(p -> p instanceof Var(int index) ? index + 1 : 0)
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
        return new MethodTable(instantiate(abstractMethods, arguments), instantiate(concreteMethods, arguments));
    }

    private static Set<MethodSignature> instantiate(Set<Signature> signatures, List<ClassDesc> arguments) {
        return signatures.stream()
                .map(s -> new MethodSignature(
                        s.name(),
                        s.params().stream()
                                .map(p -> switch (p) {
                                    case Fixed(ClassDesc erasure) -> erasure;
                                    case Var(int index) -> arguments.get(index);
                                })
                                .toList()))
                .collect(Collectors.toUnmodifiableSet());
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

        @Override
        public String toString() {
            return params.stream().map(Param::toString).collect(Collectors.joining(", ", name + "(", ")"));
        }
    }

    /// A parameter of a [Signature]: its erasure, or where to take it from.
    public sealed interface Param permits Fixed, Var {

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
            return new Var(index);
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
        public String toString() {
            return TypeNames.describe(erasure);
        }
    }

    /// A parameter erased to the erasure of the type argument for a type
    /// parameter of the type.
    ///
    /// @param index the position of the type parameter, from `0`
    public record Var(int index) implements Param {

        /// @throws IllegalArgumentException if `index` is negative
        public Var {
            if (index < 0) {
                throw new IllegalArgumentException("a type parameter index cannot be negative, got " + index);
            }
        }

        @Override
        public String toString() {
            return "#" + index;
        }
    }
}
