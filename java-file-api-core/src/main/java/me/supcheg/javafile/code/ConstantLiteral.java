package me.supcheg.javafile.code;

/// A string, `int`, `long`, `float`, `double`, or `boolean` literal - any
/// literal but `null`. Used where Java requires a constant, such as
/// annotation values.
public sealed interface ConstantLiteral extends LiteralExpr
        permits StringLiteral, IntLiteral, LongLiteral, FloatLiteral, DoubleLiteral, BooleanLiteral {}
