package me.supcheg.javafile.code;

import java.util.List;

/// A `switch` expression.
///
/// @param selector the switch selector expression
/// @param cases the switch cases, in order; copied defensively
public record SwitchExpr(Expr selector, List<SwitchCase> cases) implements Expr {
    public SwitchExpr {
        cases = List.copyOf(cases);
    }
}
