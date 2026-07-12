package me.supcheg.javafile.annotation;

import me.supcheg.javafile.code.ConstantLiteral;

/// A constant annotation value: a string, numeric, or boolean literal.
///
/// Typed as [ConstantLiteral], so the `null` literal — invalid in an
/// annotation — is unrepresentable.
///
/// @param literal the literal expression
public record LiteralValue(ConstantLiteral literal) implements SingleAnnotationValue {}
