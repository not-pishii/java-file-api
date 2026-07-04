package me.supcheg.javafile.code;

/// A `while` loop, whose condition is evaluated before the body.
///
/// @param condition the loop condition
/// @param body the loop body
public record WhileStmt(Expr condition, CodeBody body) implements Stmt {}
