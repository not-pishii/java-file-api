package me.supcheg.javafile.code;

import java.util.List;

/// An ordered sequence of statements forming a method, block, or lambda body.
///
/// @param statements the statements in execution order; copied defensively
public record CodeBody(List<Stmt> statements) {

    /// A body with no statements.
    public static final CodeBody EMPTY = new CodeBody(List.of());

    public CodeBody {
        statements = List.copyOf(statements);
    }
}
