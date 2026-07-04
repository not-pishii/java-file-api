package me.supcheg.javafile.code;

/// A `throw` statement.
///
/// @param exception the thrown expression
public record ThrowStmt(Expr exception) implements Stmt {}
