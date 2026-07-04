package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// An object creation expression, `new type(args)`.
///
/// @param type the instantiated type
/// @param args the constructor arguments, in order; copied defensively
public record NewExpr(TypeRef type, List<Expr> args) implements Expr {
    public NewExpr {
        args = List.copyOf(args);
    }
}
