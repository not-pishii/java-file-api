package me.supcheg.javafile.code;

/// What follows `case` in a switch: a constant, a pattern with an optional
/// `when` guard, or `default`.
public sealed interface CaseLabel permits ConstantLabel, PatternLabel, DefaultLabel {}
