package me.supcheg.javafile.code;

import java.util.Optional;

/// A switch case label matching a pattern, with an optional guard.
///
/// @param pattern the matched pattern
/// @param guard an additional `when` condition, or empty if absent
/// @throws IllegalArgumentException if `pattern` is a [TypePattern] with no binding name — JLS
///         requires a switch pattern label to bind a name, unlike the standalone `instanceof`
///         form, which allows an unnamed type test
public record PatternLabel(Pattern pattern, Optional<Expr> guard) implements CaseLabel {
    public PatternLabel {
        if (pattern instanceof TypePattern typePattern
                && typePattern.bindingName().isEmpty()) {
            throw new IllegalArgumentException("switch pattern label must bind a name");
        }
    }
}
