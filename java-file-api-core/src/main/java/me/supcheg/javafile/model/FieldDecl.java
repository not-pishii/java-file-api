package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A field declaration inside a class body.
///
/// Annotations are defensively copied into an unmodifiable list; modifiers
/// into an unmodifiable set.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param annotations the annotations declared on the field
/// @param modifiers the modifiers on the field declaration
/// @param initializer the field's initializer expression, if any
public record FieldDecl(
        String name, TypeRef type, List<AnnotationUse> annotations, Set<Modifier> modifiers, Optional<Expr> initializer)
        implements ClassMember, EnumMember, EnumConstantMember {
    public FieldDecl {
        annotations = List.copyOf(annotations);
        modifiers = Set.copyOf(modifiers);
    }
}
