package me.supcheg.javafile.annotation;

/// An annotation value that is not an array: a literal, a class literal, an
/// enum constant, or a nested annotation.
///
/// Only these values can be elements of an [ArrayValue], as in Java.
public sealed interface SingleAnnotationValue extends AnnotationValue
        permits LiteralValue, ClassValue, EnumValue, NestedAnnotationValue {}
