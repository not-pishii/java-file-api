package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;

/// An enum constant annotation value, e.g. `Level.HIGH`.
///
/// @param enumType the enum class
/// @param constant the constant's name
public record EnumValue(ClassDesc enumType, String constant) implements SingleAnnotationValue {}
