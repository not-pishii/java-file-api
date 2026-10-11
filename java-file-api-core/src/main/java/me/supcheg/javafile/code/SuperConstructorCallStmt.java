package me.supcheg.javafile.code;

import java.util.List;

/// An explicit invocation of a superclass constructor, `super(args);` — the
/// first statement of the body of a constructor (JLS 8.8.7.1).
///
/// @param args the arguments of the superclass constructor
public record SuperConstructorCallStmt(List<Expr> args) implements Stmt {
    /// Creates an explicit superclass constructor invocation.
    public SuperConstructorCallStmt {
        args = List.copyOf(args);
    }
}
