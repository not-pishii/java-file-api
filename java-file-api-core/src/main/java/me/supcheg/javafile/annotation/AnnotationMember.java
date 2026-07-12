package me.supcheg.javafile.annotation;

/// A single named member assignment inside an annotation use,
/// e.g. `key = "greeting"`.
///
/// @param name the member's name
/// @param value the assigned value
public record AnnotationMember(String name, AnnotationValue value) {}
