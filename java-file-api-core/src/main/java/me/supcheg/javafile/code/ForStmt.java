package me.supcheg.javafile.code;

import java.util.Optional;

/// A classic `for` loop, `for (init; condition; update)`.
///
/// @param init the initializer statement, or empty to omit it
/// @param condition the loop condition, or empty to omit it
/// @param update the per-iteration update statement, or empty to omit it
/// @param body the loop body
public record ForStmt(Optional<LocalVarDeclStmt> init, Optional<Expr> condition, Optional<Stmt> update, CodeBody body)
        implements Stmt {}
