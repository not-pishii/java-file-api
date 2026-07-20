package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

/// A labeled statement, `label: statement`. Only [BreakStmt] and
/// [ContinueStmt] inside `statement` may reference `label`.
///
/// @param label the statement's label, a valid Java identifier
/// @param statement the labeled statement, typically a loop
public record LabeledStmt(String label, Stmt statement) implements Stmt {
    public LabeledStmt {
        label = Identifiers.requireValid(label);
    }
}
