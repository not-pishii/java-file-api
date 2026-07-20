package me.supcheg.javafile.code;

/// A ternary conditional expression, `condition ? whenTrue : whenFalse`.
///
/// @param condition the tested condition
/// @param whenTrue the result when `condition` is true
/// @param whenFalse the result when `condition` is false
public record ConditionalExpr(Expr condition, Expr whenTrue, Expr whenFalse) implements Expr {}
