package me.supcheg.javafile.code;

/// A literal: a string, number, boolean, or `null`.
///
/// Create it with [Exprs#literal(String)] and its overloads, or [Exprs#literalNull()].
public sealed interface LiteralExpr extends Expr, ConstantExpr permits ConstantLiteral, NullLiteral {}
