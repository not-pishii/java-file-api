package me.supcheg.javafile.code;

import java.util.Optional;

/// An `assert` statement, `assert condition;` or `assert condition : message;`.
///
/// @param condition the asserted condition
/// @param message the diagnostic message expression, or empty to omit it
public record AssertStmt(Expr condition, Optional<Expr> message) implements Stmt {}
