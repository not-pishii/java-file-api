package me.supcheg.javafile.code;

import java.util.List;

/// A `switch` statement.
///
/// @param selector the switch selector expression
/// @param cases the switch cases, in order; copied defensively
public record SwitchStmt(Expr selector, List<SwitchCase> cases) implements Stmt {
    public SwitchStmt {
        cases = List.copyOf(cases);
    }
}
