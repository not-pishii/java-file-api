package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A method or constructor parameter: a name paired with its declared type.
///
/// Annotations are defensively copied into an unmodifiable list.
///
/// @param name the parameter name, a valid Java identifier
/// @param type the declared parameter type
/// @param annotations the annotations declared on the parameter
public record Param(String name, TypeRef type, List<AnnotationUse> annotations) {
    public Param {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
    }

    /// Creates a parameter with no annotations.
    ///
    /// @param name the parameter name, a valid Java identifier
    /// @param type the declared parameter type
    public Param(String name, TypeRef type) {
        this(name, type, List.of());
    }
}
