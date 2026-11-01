package me.supcheg.javafile.code;

/// A block statement, `{ ... }`, which a [LabeledStmt] can name. Java's method, constructor and loop
/// bodies are [CodeBody] themselves; this is the standalone block of a statement.
///
/// @param body the statements of the block
public record BlockStmt(CodeBody body) implements Stmt {}
