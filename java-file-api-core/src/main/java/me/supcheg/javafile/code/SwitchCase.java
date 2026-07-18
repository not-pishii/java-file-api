package me.supcheg.javafile.code;

/// A single case of a `switch` statement or expression: one or more labels
/// sharing a body.
///
/// @param labels the labels matched by this case, in order; at least one
/// @param body the case's body
public record SwitchCase(NonEmptyList<CaseLabel> labels, CaseBody body) {}
