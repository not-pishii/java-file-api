package me.supcheg.javafile.code;

/// An `instanceof` test against a pattern.
///
/// @param target the tested expression
/// @param pattern the matched pattern
public record InstanceOfExpr(Expr target, Pattern pattern) implements Expr {}
