package me.supcheg.javafile.code;

import java.util.Optional;

/// A switch case label matching a pattern, with an optional guard.
///
/// A type pattern must declare a variable (`case String s`, not
/// `case String`); otherwise the constructor throws `IllegalArgumentException`.
///
/// @param pattern the matched pattern
/// @param guard an additional `when` condition, or empty if absent
public record PatternLabel(Pattern pattern, Optional<Expr> guard) implements CaseLabel {
    public PatternLabel {
        if (pattern instanceof TypePattern typePattern
                && typePattern.bindingName().isEmpty()) {
            throw new IllegalArgumentException("switch pattern label must bind a name");
        }
    }
}
