package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// An element declaration inside an [AnnotationTypeDecl]'s body, `type name() [default value];`.
///
/// @param name the element name, a valid Java identifier
/// @param type the element's declared type
/// @param defaultValue the element's default value, or empty if the element has no default
/// @param doc the documentation comment, if any
public record AnnotationElementDecl(
        String name, TypeRef type, Optional<AnnotationValue> defaultValue, Optional<DocComment> doc) {
    public AnnotationElementDecl {
        name = Identifiers.requireValid(name);
    }
}
