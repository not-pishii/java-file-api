package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// An element of an annotation type: its name, its declared type, and the
/// default it declares, if it declares one.
///
/// @param name the element name
/// @param type the declared type
/// @param defaultValue the default, if the element declares one
public record AnnotationElementModel(String name, TypeRef type, Optional<AnnotationValue> defaultValue) {
    public AnnotationElementModel {
        name = Identifiers.requireValid(name);
    }

    /// Whether every use of the annotation gives the element a value: it
    /// declares no default (JLS 9.6.2).
    ///
    /// @return `true` if the element has no default
    public boolean required() {
        return defaultValue.isEmpty();
    }
}
