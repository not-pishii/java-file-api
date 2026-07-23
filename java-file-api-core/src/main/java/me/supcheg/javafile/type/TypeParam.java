package me.supcheg.javafile.type;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;

import java.util.List;

/// A type parameter declaration on a class, interface, record, or method,
/// e.g. `T` or `U extends Serializable & Comparable<U>`.
///
/// Bounds are defensively copied into an unmodifiable list; an empty list
/// declares an unbounded parameter. Primitives and arrays cannot appear as
/// bounds by construction.
///
/// @param name the type parameter's name
/// @param bounds the parameter's upper bounds, rendered joined with `&`
/// @param annotations the annotations on the type parameter declaration itself,
///                     e.g. `<@Foo T>`; defensively copied into an unmodifiable list
public record TypeParam(String name, List<ClassOrInterfaceTypeRef> bounds, List<AnnotationUse> annotations) {
    public TypeParam {
        name = Identifiers.requireValid(name);
        bounds = List.copyOf(bounds);
        annotations = List.copyOf(annotations);
    }

    /// Creates a type parameter with no annotations.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, rendered joined with `&`
    public TypeParam(String name, List<ClassOrInterfaceTypeRef> bounds) {
        this(name, bounds, List.of());
    }
}
