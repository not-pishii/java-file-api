package me.supcheg.javafile.code;

/// A label matched by a [SwitchCase].
///
/// The permitted implementations cover constant labels, pattern labels
/// (with an optional guard), and the `default` label.
public sealed interface CaseLabel permits ConstantLabel, PatternLabel, DefaultLabel {}
