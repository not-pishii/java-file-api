package me.supcheg.javafile.code;

/// A `yield` statement, for use inside a `switch` expression's block case.
///
/// @param value the yielded expression
public record YieldStmt(Expr value) implements Stmt {}
