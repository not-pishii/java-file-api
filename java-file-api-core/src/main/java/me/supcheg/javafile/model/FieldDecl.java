package me.supcheg.javafile.model;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;
import java.util.Set;

/// A field declaration inside a class body.
///
/// Modifiers are defensively copied into an unmodifiable set.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param modifiers the modifiers on the field declaration
/// @param initializer the field's initializer expression, if any
public record FieldDecl(String name, TypeRef type, Set<Modifier> modifiers, Optional<Expr> initializer)
        implements ClassMember {
    public FieldDecl {
        modifiers = Set.copyOf(modifiers);
    }
}
