package me.supcheg.javafile.code;

/// A constant-valued expression.
///
/// The permitted implementations cover string, integer, long, double,
/// boolean, and `null` literals.
public sealed interface LiteralExpr extends Expr
        permits StringLiteral, IntLiteral, LongLiteral, DoubleLiteral, BooleanLiteral, NullLiteral {}
