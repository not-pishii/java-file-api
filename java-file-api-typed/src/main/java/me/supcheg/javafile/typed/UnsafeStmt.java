package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Stmt;

/// A single untyped statement, produced by [Unsafe#stmt(Stmt)] and accepted
/// by [Block#add(UnsafeStmt)]. There is no public constructor: the only way
/// to obtain one is through `Unsafe`, keeping the escape hatch auditable
/// (`grep Unsafe\.`).
public final class UnsafeStmt {
    private final Stmt stmt;

    UnsafeStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    Stmt stmt() {
        return stmt;
    }
}
