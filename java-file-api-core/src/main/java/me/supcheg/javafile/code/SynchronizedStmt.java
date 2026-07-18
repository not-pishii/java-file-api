package me.supcheg.javafile.code;

/// A `synchronized (lock) { ... }` block.
///
/// @param lock the monitor expression
/// @param body the synchronized block's body
public record SynchronizedStmt(Expr lock, CodeBody body) implements Stmt {}
