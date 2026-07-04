package me.supcheg.javafile.model;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.Objects;

/// A `public static final` field declaration inside a record body.
///
/// Renders unconditionally with `public static final` modifiers, regardless
/// of how the field is constructed.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param initializer the field's initializer expression; must not be `null`
public record StaticFieldDecl(String name, TypeRef type, Expr initializer) implements RecordMember {
    /// @throws NullPointerException if `initializer` is `null`
    public StaticFieldDecl {
        Objects.requireNonNull(initializer, "a static record field must be initialized");
    }
}
