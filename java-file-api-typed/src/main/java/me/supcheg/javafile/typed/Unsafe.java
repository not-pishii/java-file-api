package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.model.ClassMember;

/// The single bridge from the typed layer down to untyped `core`/`facts`
/// constructs (§6.6). Everything reachable through `Unsafe` bypasses the
/// guarantees of the typed layer — the declared type or shape is the
/// author's responsibility, not checked here. Everything built from an
/// `Unsafe` result is checked statically again like any other typed value.
///
/// This is the only class in the typed layer that touches raw core
/// constructs, so the guarantee (§1) is auditable mechanically: `grep
/// Unsafe\.` over generator code.
public final class Unsafe {
    private Unsafe() {}

    /// Wraps an untyped core expression as a typed expression of the given
    /// declared type.
    ///
    /// @param expr the untyped core expression
    /// @param type the declared static type, trusted as-is
    /// @param <T> the declared static type
    /// @return a typed expression wrapping `expr`
    public static <T> Expr<T> expr(me.supcheg.javafile.code.Expr expr, TypeToken<T> type) {
        return Expr.of(new Node.Raw(expr), type);
    }

    /// Wraps an untyped core statement so it can be appended to a [Block]
    /// with [Block#add(UnsafeStmt)].
    ///
    /// @param stmt the untyped core statement
    /// @return an unsafe statement
    public static UnsafeStmt stmt(Stmt stmt) {
        return new UnsafeStmt(stmt);
    }

    /// Wraps an untyped core class member so it can be appended to a
    /// [TypedClassBuilder] with [TypedClassBuilder#add(UnsafeMember)].
    ///
    /// @param member the untyped core member
    /// @return an unsafe member
    public static UnsafeMember member(ClassMember member) {
        return new UnsafeMember(member);
    }
}
