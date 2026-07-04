package me.supcheg.javafile.code;

import java.util.List;
import java.util.Optional;

/// A method call, e.g. `target.method(args)` or, when unqualified, `method(args)`.
///
/// @param target the expression owning the method, or empty for an unqualified call
/// @param method the method name
/// @param args the call arguments, in order; copied defensively
public record MethodCallExpr(Optional<Expr> target, String method, List<Expr> args) implements Expr {
    public MethodCallExpr {
        args = List.copyOf(args);
    }
}
