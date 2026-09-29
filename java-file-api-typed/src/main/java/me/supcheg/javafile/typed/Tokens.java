package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.util.List;
import java.util.Optional;

/// Internal helpers shared by the typed-layer combinators.
final class Tokens {
    private Tokens() {}

    /// Whether `a` and `b` stand for the same Java type, as written in
    /// generated code.
    static boolean sameType(TypeToken<?> a, TypeToken<?> b) {
        return a.typeRef().equals(b.typeRef());
    }

    /// Whether `type` is reifiable (JLS 4.7): a primitive, a non-generic or
    /// raw class or interface, a parameterization whose every type argument
    /// is the unbounded wildcard `?`, or an array of a reifiable type. A type
    /// variable and any other parameterization are not.
    static boolean isReifiable(TypeToken<?> type) {
        return switch (type) {
            case PrimitiveToken<?, ?, ?> ignored -> true;
            case ArrayToken<?, ?> array -> isReifiable(array.component());
            case TypeVarToken<?> ignored -> false;
            case DeclaredToken<?> declared ->
                !(declared.typeRef() instanceof ParameterizedTypeRef parameterized)
                        || parameterized.args().stream().allMatch(UnboundedTypeArg.class::isInstance);
        };
    }

    /// An array creation must name a reifiable component type (JLS 15.10.1):
    /// otherwise the array's run-time component type is not the one its
    /// static type claims — heap pollution through a generic array.
    ///
    /// @param type the component type
    /// @throws IllegalArgumentException if `type` is not reifiable
    static void requireReifiableComponent(TypeToken<?> type) {
        if (!isReifiable(type)) {
            throw new IllegalArgumentException("newArray needs a reifiable type (JLS 4.7), but " + type
                    + " is not: its type arguments are not checked at run time. Use a non-generic or raw type,"
                    + " or a parameterization with unbounded wildcards only, e.g. List<?>");
        }
    }

    /// A cast or `instanceof` from the static type `from` to `to` must be a
    /// checked cast (JLS 5.1.6.2): one the run-time check of the erasure
    /// fully decides. It is when `to` is reifiable, when it is `from`
    /// itself, or when `to` is a parameterization `G<A1..An>` of which
    /// `from` is a parameterized supertype that determines every `Ai` — the
    /// supertype of `G`, as recorded in the facts of `to`
    /// ([me.supcheg.javafile.facts.DeclaredToken#supertypes()]), names each
    /// type parameter of `G` as a type argument, and with the `Ai`
    /// substituted is exactly `from`: `(ArrayList<String>) listOfStrings`
    /// and `(List<String>) collectionOfStrings` are checked,
    /// `(ArrayList<Integer>) listOfStrings` and `(ArrayList<String>) object`
    /// are not.
    ///
    /// Conservative where the facts say too little: a type variable or a
    /// wildcard on either side, or a target whose supertypes are not
    /// recorded, is rejected even if javac would accept it.
    ///
    /// @param from the operand's static type
    /// @param to the target type
    /// @param where the construct, for the message
    /// @throws IllegalArgumentException if the cast would be unchecked
    static void requireCheckedCast(TypeToken<?> from, TypeToken<?> to, String where) {
        if (isReifiable(to) || sameType(from, to)) {
            return;
        }
        Optional<String> reason = uncheckedReason(from, to);
        if (reason.isPresent()) {
            throw new IllegalArgumentException(where + " from " + from + " to " + to
                    + " would be an unchecked cast (JLS 5.1.6.2): " + reason.get()
                    + ". Use a reifiable type, e.g. List<?>, or cast from a parameterized supertype"
                    + " that determines every type argument, e.g. List<String> to ArrayList<String>");
        }
    }

    private static Optional<String> uncheckedReason(TypeToken<?> from, TypeToken<?> to) {
        if (!(to instanceof DeclaredToken<?> target
                && target.typeRef() instanceof ParameterizedTypeRef parameterized)) {
            return Optional.of(to + " is not reifiable and not a parameterized class or interface type,"
                    + " so its run-time check does not cover it");
        }
        if (!parameterized.args().stream().allMatch(ExactTypeArg.class::isInstance)) {
            return Optional.of(to + " has wildcard type arguments");
        }
        if (!(from instanceof DeclaredToken<?> source && source.typeRef() instanceof ParameterizedTypeRef sourceType)) {
            return Optional.of("the operand's type " + from
                    + " is not a parameterization, so it does not determine the type arguments of " + to);
        }
        Supertypes supertypes = target.supertypes();
        if (supertypes.equals(Supertypes.NONE)) {
            return Optional.of("the facts of " + to + " record no supertypes");
        }
        Optional<ParameterizedTypeRef> generic = supertypes.supertype(sourceType.raw());
        if (generic.isEmpty()) {
            return Optional.of(from + " is not a recorded supertype of " + to);
        }
        for (TypeVarRef parameter : supertypes.typeParameters()) {
            boolean named = generic.get().args().stream()
                    .anyMatch(a -> a instanceof ExactTypeArg(TypeVarRef var)
                            && var.name().equals(parameter.name()));
            if (!named) {
                return Optional.of(
                        from + " does not determine the type argument for " + parameter.name() + " of " + to);
            }
        }
        List<TypeRef> arguments = parameterized.args().stream()
                .map(a -> ((ExactTypeArg) a).type())
                .toList();
        ParameterizedTypeRef substituted =
                supertypes.supertype(sourceType.raw(), arguments).orElseThrow();
        if (!substituted.equals(sourceType)) {
            return Optional.of(
                    to + " is not a subtype of " + from + ": its supertype is " + TypeNames.describe(substituted));
        }
        return Optional.empty();
    }
}
