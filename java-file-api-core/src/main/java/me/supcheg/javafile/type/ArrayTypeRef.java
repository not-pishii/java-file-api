package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.util.List;

/// A reference to an array type.
///
/// @param component the type of the array's elements
/// @param annotations the type-use annotations on this array level (JLS 9.7.4);
///                     defensively copied into an unmodifiable list
public record ArrayTypeRef(TypeRef component, List<AnnotationUse> annotations) implements TypeRef {
    public ArrayTypeRef {
        annotations = List.copyOf(annotations);
    }

    /// Creates a reference with no type-use annotations.
    ///
    /// @param component the type of the array's elements
    public ArrayTypeRef(TypeRef component) {
        this(component, List.of());
    }
}
