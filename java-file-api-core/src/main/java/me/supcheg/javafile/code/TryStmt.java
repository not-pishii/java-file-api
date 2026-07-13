package me.supcheg.javafile.code;

import java.util.List;

/// A `try` statement: `try`-`catch`, `try`-`finally`, `try`-`catch`-`finally`,
/// and try-with-resources variants of each.
///
/// Java requires at least one `catch` clause or a `finally` block — a bare
/// `try { }` is invalid. This is enforced by the two permitted forms: only
/// [WithFinally] may have zero `catch` clauses; [CatchOnly] requires at least
/// one via [NonEmptyList]. A `try` with neither is unrepresentable.
public sealed interface TryStmt extends Stmt permits TryStmt.WithFinally, TryStmt.CatchOnly {

    /// A `try` with a `finally` block, and zero or more `catch` clauses.
    ///
    /// @param resources the try-with-resources resources, in declaration order; may be empty; copied defensively
    /// @param block the try block's body
    /// @param catches the `catch` clauses, in order; may be empty (`try { } finally { }`); copied defensively
    /// @param finallyBody the `finally` block's body
    record WithFinally(List<Resource> resources, CodeBody block, List<CatchClause> catches, CodeBody finallyBody)
            implements TryStmt {
        public WithFinally {
            resources = List.copyOf(resources);
            catches = List.copyOf(catches);
        }
    }

    /// A `try` with one or more `catch` clauses and no `finally` block.
    ///
    /// @param resources the try-with-resources resources, in declaration order; may be empty; copied defensively
    /// @param block the try block's body
    /// @param catches the `catch` clauses, in order; at least one
    record CatchOnly(List<Resource> resources, CodeBody block, NonEmptyList<CatchClause> catches) implements TryStmt {
        public CatchOnly {
            resources = List.copyOf(resources);
        }
    }
}
