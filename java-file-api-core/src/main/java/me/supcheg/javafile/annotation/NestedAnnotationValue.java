package me.supcheg.javafile.annotation;

/// A nested annotation value, e.g. `@MessageMeta(...)` inside another
/// annotation's member.
///
/// @param annotation the nested annotation use
public record NestedAnnotationValue(AnnotationUse annotation) implements SingleAnnotationValue {}
