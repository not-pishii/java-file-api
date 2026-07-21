package me.supcheg.javafile.code;

/// An assignment statement, `target op value` (e.g. `target = value` or `target += value`).
///
/// @param target the assignment target
/// @param op the assignment operator
/// @param value the assigned expression
public record AssignStmt(AssignTarget target, AssignOp op, Expr value) implements Stmt {}
