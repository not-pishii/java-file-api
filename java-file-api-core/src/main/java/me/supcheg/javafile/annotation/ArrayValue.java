package me.supcheg.javafile.annotation;

import java.util.List;

/// An array annotation value, rendered as `{ ... }`.
///
/// Elements are defensively copied into an unmodifiable list; they are
/// [SingleAnnotationValue]s, so nested arrays are unrepresentable.
///
/// @param elements the array's elements, in order
public record ArrayValue(List<SingleAnnotationValue> elements) implements AnnotationValue {
    public ArrayValue {
        elements = List.copyOf(elements);
    }
}
