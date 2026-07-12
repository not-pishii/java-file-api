package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A record component: a name paired with its declared type.
///
/// A record's components determine its canonical constructor parameters and
/// the accessor methods generated for it. Annotations are defensively
/// copied into an unmodifiable list.
///
/// @param name the component name, a valid Java identifier
/// @param type the declared component type
/// @param annotations the annotations declared on the component
public record RecordComponent(String name, TypeRef type, List<AnnotationUse> annotations) {
    public RecordComponent {
        annotations = List.copyOf(annotations);
    }

    /// Creates a record component with no annotations.
    ///
    /// @param name the component name, a valid Java identifier
    /// @param type the declared component type
    public RecordComponent(String name, TypeRef type) {
        this(name, type, List.of());
    }
}
