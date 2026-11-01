package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// An element declaration inside an [AnnotationTypeDecl]'s body, `type name() [default value];`.
///
/// @param name the element name, a valid Java identifier
/// @param type the element's declared type
/// @param annotations the annotations declared on the element
/// @param defaultValue the element's default value, or empty if the element has no default
/// @param doc the documentation comment, if any
public record AnnotationElementDecl(
        String name,
        TypeRef type,
        List<AnnotationUse> annotations,
        Optional<AnnotationValue> defaultValue,
        Optional<DocComment> doc) {
    public AnnotationElementDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
    }

    /// Creates an element with no annotations.
    ///
    /// @param name the element name, a valid Java identifier
    /// @param type the element's declared type
    /// @param defaultValue the element's default value, or empty if the element has no default
    /// @param doc the documentation comment, if any
    public AnnotationElementDecl(
            String name, TypeRef type, Optional<AnnotationValue> defaultValue, Optional<DocComment> doc) {
        this(name, type, List.of(), defaultValue, doc);
    }
}
