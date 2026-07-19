package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.List;

/// A static method call, e.g. `target.method(args)` for `Math.max(a, b)`.
///
/// @param target the type declaring the method
/// @param method the method name
/// @param args the call arguments, in order; copied defensively
public record StaticMethodCallExpr(ClassOrInterfaceTypeRef target, String method, List<Expr> args)
        implements Expr, StatementExpr {
    public StaticMethodCallExpr {
        method = Identifiers.requireValid(method);
        args = List.copyOf(args);
    }
}
