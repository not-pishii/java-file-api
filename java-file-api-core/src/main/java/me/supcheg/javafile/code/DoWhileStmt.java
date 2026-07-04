package me.supcheg.javafile.code;

/// A `do`-`while` loop, whose condition is evaluated after the body.
///
/// @param body the loop body
/// @param condition the loop condition
public record DoWhileStmt(CodeBody body, Expr condition) implements Stmt {}
