package me.supcheg.javafile.code;

/// A literal with a compile-time constant value: string, integer, long,
/// double, or boolean — every literal except `null`.
///
/// This is the subset of [LiteralExpr] valid where Java requires a constant,
/// such as annotation member values; `null` is excluded by construction.
public sealed interface ConstantLiteral extends LiteralExpr
        permits StringLiteral, IntLiteral, LongLiteral, DoubleLiteral, BooleanLiteral {}
