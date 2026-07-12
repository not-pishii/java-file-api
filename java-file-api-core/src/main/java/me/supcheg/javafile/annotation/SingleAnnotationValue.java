package me.supcheg.javafile.annotation;

/// A non-array annotation value: a constant literal, a class literal, an enum
/// constant, or a nested annotation.
///
/// This is the only kind of value allowed inside an [ArrayValue], mirroring
/// Java's ban on multi-dimensional annotation arrays.
public sealed interface SingleAnnotationValue extends AnnotationValue
        permits LiteralValue, ClassValue, EnumValue, NestedAnnotationValue {}
