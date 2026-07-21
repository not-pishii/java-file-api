package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

/// A method reference, `target::method`.
///
/// @param target the type-qualified or instance-bound target
/// @param method the referenced method name
public record MethodRefExpr(MethodRefTarget target, String method) implements Expr {
    public MethodRefExpr {
        method = Identifiers.requireValid(method);
    }
}
