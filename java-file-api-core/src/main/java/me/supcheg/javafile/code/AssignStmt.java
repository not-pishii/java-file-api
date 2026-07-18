package me.supcheg.javafile.code;

/// An assignment statement, `target = value`.
///
/// @param target the assignment target
/// @param value the assigned expression
public record AssignStmt(AssignTarget target, Expr value) implements Stmt {}
