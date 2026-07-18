package me.supcheg.javafile.code;

/// A switch case label matching a constant value.
///
/// @param value the matched constant expression
public record ConstantLabel(ConstantExpr value) implements CaseLabel {}
