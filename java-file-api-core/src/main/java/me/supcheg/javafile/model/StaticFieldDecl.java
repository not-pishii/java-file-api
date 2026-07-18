package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A `public static final` field declaration inside a record body.
///
/// Renders unconditionally with `public static final` modifiers, regardless
/// of how the field is constructed. Annotations are defensively copied into
/// an unmodifiable list.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param annotations the annotations declared on the field
/// @param initializer the field's initializer expression; must not be `null`
public record StaticFieldDecl(String name, TypeRef type, List<AnnotationUse> annotations, Expr initializer)
        implements RecordMember {
    public StaticFieldDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
    }
}
