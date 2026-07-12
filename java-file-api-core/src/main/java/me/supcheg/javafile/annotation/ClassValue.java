package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;

/// A class literal annotation value, e.g. `Foo.class`.
///
/// @param type the referenced class
public record ClassValue(ClassDesc type) implements SingleAnnotationValue {}
