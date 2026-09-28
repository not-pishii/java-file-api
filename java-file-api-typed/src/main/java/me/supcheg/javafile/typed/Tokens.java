package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeVarToken;

/// Internal helpers shared by the typed-layer combinators.
final class Tokens {
    private Tokens() {}

    /// A pattern (`instanceof`/`cast`) type must be reifiable (JLS 4.7); a
    /// bare type variable is the only [RefToken] that is not.
    ///
    /// @param type the pattern type
    /// @throws IllegalArgumentException if `type` is a type variable
    static void requireReifiable(RefToken<?> type) {
        if (type instanceof TypeVarToken<?>) {
            throw new IllegalArgumentException("type " + type
                    + " is a type variable, not reifiable: instanceof/cast patterns require a" + " reifiable type");
        }
    }
}
