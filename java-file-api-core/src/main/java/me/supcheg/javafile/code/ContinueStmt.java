package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

import java.util.Optional;

/// A `continue` statement, optionally targeting an enclosing [LabeledStmt].
///
/// @param label the targeted label, or empty for the innermost loop
public record ContinueStmt(Optional<String> label) implements Stmt {
    public ContinueStmt {
        label = label.map(Identifiers::requireValid);
    }
}
