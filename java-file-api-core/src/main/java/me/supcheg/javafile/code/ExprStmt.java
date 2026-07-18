package me.supcheg.javafile.code;

/// A statement consisting of a bare expression evaluated for its side effects.
///
/// @param expr the evaluated expression
public record ExprStmt(StatementExpr expr) implements Stmt {}
