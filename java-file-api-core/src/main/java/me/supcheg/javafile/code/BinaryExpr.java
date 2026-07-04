package me.supcheg.javafile.code;

/// A binary operator expression, `left op right`.
///
/// @param left the left operand
/// @param op the applied operator
/// @param right the right operand
public record BinaryExpr(Expr left, BinaryOp op, Expr right) implements Expr {}
