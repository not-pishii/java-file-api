package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

import java.util.Optional;

/// A field access, e.g. `target.name` or, when unqualified, `name`.
///
/// @param target the expression owning the field, or empty for an unqualified access
/// @param name the field name
public record FieldAccessExpr(Optional<Expr> target, String name) implements Expr, AssignTarget, ConstantExpr {
    public FieldAccessExpr {
        name = Identifiers.requireValid(name);
    }
}
