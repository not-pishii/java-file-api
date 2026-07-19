package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

/// A static field access, e.g. `target.name` for `Integer.MAX_VALUE`.
///
/// @param target the type declaring the field
/// @param name the field name
public record StaticFieldAccessExpr(ClassOrInterfaceTypeRef target, String name)
        implements Expr, AssignTarget, ConstantExpr {
    public StaticFieldAccessExpr {
        name = Identifiers.requireValid(name);
    }
}
