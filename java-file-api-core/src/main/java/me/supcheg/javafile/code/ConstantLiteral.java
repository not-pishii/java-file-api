package me.supcheg.javafile.code;

/// A string, `int`, `long`, `double`, or `boolean` literal — any literal but
/// `null`. Used where Java requires a constant, such as annotation values.
public sealed interface ConstantLiteral extends LiteralExpr
        permits StringLiteral, IntLiteral, LongLiteral, DoubleLiteral, BooleanLiteral {}
