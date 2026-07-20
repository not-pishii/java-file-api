package me.supcheg.javafile.code;

import java.util.Optional;

/// A switch case label matching a pattern, with an optional guard.
///
/// @param pattern the matched pattern
/// @param guard an additional `when` condition, or empty if absent
public record PatternLabel(Pattern pattern, Optional<Expr> guard) implements CaseLabel {}
