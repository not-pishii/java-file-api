package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.doc.DocComment;

import java.lang.constant.ClassDesc;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// An annotation type declaration, `@interface Name { ... }`.
///
/// @param desc the annotation type's name and package
/// @param annotations the annotations declared on the annotation type itself
/// @param modifiers the modifiers on the annotation type declaration; `static`, `private`, and
///                   `protected` are accepted because `AnnotationTypeDecl` also models a nested
///                   member annotation type
/// @param elements the annotation type's elements, in declaration order
/// @param members the member types of the annotation type, in declaration order; an annotation type
///                declares constants, member types and elements (JLS 9.6.1)
/// @param doc the documentation comment, if any
public record AnnotationTypeDecl(
        ClassDesc desc,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<AnnotationElementDecl> elements,
        List<TypeDecl> members,
        Optional<DocComment> doc)
        implements TypeDecl {
    public AnnotationTypeDecl {
        desc = DeclaredTypes.requireDeclarable(desc);
        modifiers = ModifierValidation.requireValidTopLevel(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.STATIC),
                "annotation type");
        annotations = List.copyOf(annotations);
        elements = List.copyOf(elements);
        members = List.copyOf(members);
    }
}
