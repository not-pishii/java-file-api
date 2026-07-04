package me.supcheg.javafile.code;

/// A unary operator expression.
///
/// @param op the applied operator
/// @param operand the operand
public record UnaryExpr(UnaryOp op, Expr operand) implements Expr {}
