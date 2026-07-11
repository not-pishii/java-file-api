package me.supcheg.javafile.code;

import java.util.List;

/// An object creation expression, `new type(args)` or `new type<>(args)`.
///
/// @param target the instantiated target
/// @param args the constructor arguments, in order; copied defensively
public record NewExpr(NewTarget target, List<Expr> args) implements Expr {
    public NewExpr {
        args = List.copyOf(args);
    }
}
