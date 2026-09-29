package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

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

    /// A cast, `instanceof` or array creation must name a reifiable type
    /// (JLS 4.7, 15.10.1, 15.20.2): otherwise the check the generated code
    /// makes at run time is not the one its static type claims — an
    /// unchecked cast, or heap pollution through a generic array.
    ///
    /// @param type the type
    /// @param where the construct, for the message
    /// @throws IllegalArgumentException if `type` is not reifiable
    static void requireReifiable(TypeToken<?> type, String where) {
        if (!isReifiable(type)) {
            throw new IllegalArgumentException(where + " needs a reifiable type (JLS 4.7), but " + type
                    + " is not: its type arguments are not checked at run time. Use a non-generic or raw type,"
                    + " or a parameterization with unbounded wildcards only, e.g. List<?>");
        }
    }
}
