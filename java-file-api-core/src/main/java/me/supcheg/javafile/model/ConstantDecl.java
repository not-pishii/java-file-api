package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// An interface constant, e.g. `int MAX_SIZE = 16;`. It is always
/// `public static final`, so no modifiers are specified.
///
/// @param name the field name, a valid Java identifier
/// @param type the declared field type
/// @param annotations the annotations declared on the field
/// @param initializer the field's initializer expression; must not be `null`
public record ConstantDecl(String name, TypeRef type, List<AnnotationUse> annotations, Expr initializer)
        implements InterfaceMember {
    public ConstantDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
    }
}
