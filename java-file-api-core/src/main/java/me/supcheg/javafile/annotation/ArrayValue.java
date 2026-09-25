package me.supcheg.javafile.annotation;

import java.util.List;

/// An array annotation value, rendered as `{ ... }`.
///
/// Create it with [AnnotationValues#array(SingleAnnotationValue...)] or
/// [AnnotationBuilder#withArrayMember(String,java.util.function.Consumer)].
///
/// @param elements the array's elements, in order
public record ArrayValue(List<SingleAnnotationValue> elements) implements AnnotationValue {
    public ArrayValue {
        elements = List.copyOf(elements);
    }
}
