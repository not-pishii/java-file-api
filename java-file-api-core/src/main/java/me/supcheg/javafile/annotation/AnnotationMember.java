package me.supcheg.javafile.annotation;

import me.supcheg.javafile.Identifiers;

/// A single named member assignment inside an annotation use,
/// e.g. `key = "greeting"`.
///
/// @param name the member's name
/// @param value the assigned value
public record AnnotationMember(String name, AnnotationValue value) {
    public AnnotationMember {
        name = Identifiers.requireValid(name);
    }
}
