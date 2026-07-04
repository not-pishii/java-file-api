package me.supcheg.javafile.code;

import java.util.Optional;

/// A `return` statement.
///
/// @param value the returned expression, or empty for a bare `return`
public record ReturnStmt(Optional<Expr> value) implements Stmt {}
