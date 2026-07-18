package me.supcheg.javafile.code;

/// A pre/post increment or decrement expression, e.g. `++operand` or `operand--`.
///
/// @param op the applied operator
/// @param operand the incremented or decremented operand
public record IncDecExpr(IncDecOp op, Expr operand) implements Expr, StatementExpr {}
