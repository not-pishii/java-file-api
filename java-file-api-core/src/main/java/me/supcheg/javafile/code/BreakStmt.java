package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

import java.util.Optional;

/// A `break` statement, optionally targeting an enclosing [LabeledStmt].
///
/// @param label the targeted label, or empty for the innermost loop or switch
public record BreakStmt(Optional<String> label) implements Stmt {
    public BreakStmt {
        label = label.map(Identifiers::requireValid);
    }
}
