package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ClassDesc;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// An annotation type declaration, `@interface Name { ... }`.
///
/// @param desc the annotation type's name and package
/// @param annotations the annotations declared on the annotation type itself
/// @param modifiers the modifiers on the annotation type declaration; `static`, `private`, and
///                   `protected` are accepted because `AnnotationTypeDecl` also models a nested
///                   member annotation type
/// @param elements the annotation type's elements, in declaration order
public record AnnotationTypeDecl(
        ClassDesc desc, List<AnnotationUse> annotations, Set<Modifier> modifiers, List<AnnotationElementDecl> elements)
        implements TypeDecl {
    public AnnotationTypeDecl {
        modifiers = ModifierValidation.requireValidTopLevel(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.STATIC),
                "annotation type");
        annotations = List.copyOf(annotations);
        elements = List.copyOf(elements);
    }
}
