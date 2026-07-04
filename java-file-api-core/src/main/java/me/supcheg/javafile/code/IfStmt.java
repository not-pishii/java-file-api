package me.supcheg.javafile.code;

import java.util.List;
import java.util.Optional;

/// An `if` statement, with optional `else if` and `else` clauses.
///
/// @param condition the `if` condition
/// @param thenBody the body executed when `condition` is true
/// @param elseIfClauses the `else if` clauses, in order; copied defensively
/// @param elseBody the trailing `else` body, or empty if absent
public record IfStmt(Expr condition, CodeBody thenBody, List<ElseIfClause> elseIfClauses, Optional<CodeBody> elseBody)
        implements Stmt {
    public IfStmt {
        elseIfClauses = List.copyOf(elseIfClauses);
    }
}
