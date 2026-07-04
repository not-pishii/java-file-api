package me.supcheg.javafile.model;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.Objects;

/// A constant field declaration inside an interface body.
///
/// Interface fields are implicitly `public static final`; this type carries
/// no explicit [Modifier] set because that is the only form Java allows.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param initializer the field's initializer expression; must not be `null`
public record ConstantDecl(String name, TypeRef type, Expr initializer) implements InterfaceMember {
    /// @throws NullPointerException if `initializer` is `null`
    public ConstantDecl {
        Objects.requireNonNull(initializer, "a constant field must be initialized");
    }
}
