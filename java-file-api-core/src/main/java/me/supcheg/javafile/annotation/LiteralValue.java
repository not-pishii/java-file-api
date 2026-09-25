package me.supcheg.javafile.annotation;

import me.supcheg.javafile.code.ConstantLiteral;

/// A string, numeric, or boolean annotation value, e.g. `"x"` or `42`.
///
/// Create it with one of the [AnnotationValues] `literal` methods.
///
/// @param literal the literal expression
public record LiteralValue(ConstantLiteral literal) implements SingleAnnotationValue {}
