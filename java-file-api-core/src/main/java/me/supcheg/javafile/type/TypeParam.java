package me.supcheg.javafile.type;

import me.supcheg.javafile.Identifiers;

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
public record TypeParam(String name, List<ClassOrInterfaceTypeRef> bounds) {
    public TypeParam {
        name = Identifiers.requireValid(name);
        bounds = List.copyOf(bounds);
    }
}
