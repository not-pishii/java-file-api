package me.supcheg.javafile.annotation;

/// A value of an annotation member: a literal, `Foo.class`, an enum constant,
/// a nested annotation, or an array `{ ... }` of those.
///
/// Create values with [AnnotationValues].
public sealed interface AnnotationValue permits SingleAnnotationValue, ArrayValue {}
