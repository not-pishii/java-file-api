package me.supcheg.javafile.code;

/// A constant-valued expression.
///
/// The permitted implementations cover the compile-time constants
/// ([ConstantLiteral]: string, integer, long, double, and boolean literals)
/// and the `null` literal.
public sealed interface LiteralExpr extends Expr, ConstantExpr permits ConstantLiteral, NullLiteral {}
