package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

/// One element of an annotation as it is given a value, e.g. the `name =
/// "Ada"` of `@Author(name = "Ada")`.
///
/// The generated builder of an annotation makes these; a generator does not
/// write one by hand, and so names no element with a string.
///
/// @param name the element name
/// @param value the value
public record AnnotationMember(String name, AnnotationValue<?> value) {
    public AnnotationMember {
        name = Identifiers.requireValid(name);
    }
}
