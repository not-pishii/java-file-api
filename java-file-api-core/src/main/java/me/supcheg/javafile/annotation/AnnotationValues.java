package me.supcheg.javafile.annotation;

import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.StringLiteral;

import java.lang.constant.ClassDesc;
import java.util.List;

/// Factory methods for constructing [AnnotationValue]s.
///
/// This is the intended entry point for building annotation values; the
/// permitted implementations of [AnnotationValue] are not meant to be
/// instantiated directly.
public final class AnnotationValues {

    private AnnotationValues() {}

    /// Creates a string literal value.
    ///
    /// @param value the literal value
    /// @return an annotation value
    public static SingleAnnotationValue literal(String value) {
        return new LiteralValue(new StringLiteral(value));
    }

    /// Creates an `int` literal value.
    ///
    /// @param value the literal value
    /// @return an annotation value
    public static SingleAnnotationValue literal(int value) {
        return new LiteralValue(new IntLiteral(value));
    }

    /// Creates a `long` literal value.
    ///
    /// @param value the literal value
    /// @return an annotation value
    public static SingleAnnotationValue literal(long value) {
        return new LiteralValue(new LongLiteral(value));
    }

    /// Creates a `double` literal value.
    ///
    /// @param value the literal value
    /// @return an annotation value
    public static SingleAnnotationValue literal(double value) {
        return new LiteralValue(new DoubleLiteral(value));
    }

    /// Creates a `boolean` literal value.
    ///
    /// @param value the literal value
    /// @return an annotation value
    public static SingleAnnotationValue literal(boolean value) {
        return new LiteralValue(new BooleanLiteral(value));
    }

    /// Creates a class literal value, e.g. `Foo.class`.
    ///
    /// @param type the referenced class
    /// @return an annotation value
    public static SingleAnnotationValue classValue(ClassDesc type) {
        return new ClassValue(type);
    }

    /// Creates an enum constant value, e.g. `Level.HIGH`.
    ///
    /// @param enumType the enum class
    /// @param constant the constant's name
    /// @return an annotation value
    public static SingleAnnotationValue enumValue(ClassDesc enumType, String constant) {
        return new EnumValue(enumType, constant);
    }

    /// Creates a nested annotation value.
    ///
    /// @param annotation the nested annotation use
    /// @return an annotation value
    public static SingleAnnotationValue nested(AnnotationUse annotation) {
        return new NestedAnnotationValue(annotation);
    }

    /// Creates an array value, rendered as `{ ... }`.
    ///
    /// @param elements the array's elements, in order
    /// @return an annotation value
    public static AnnotationValue array(SingleAnnotationValue... elements) {
        return new ArrayValue(List.of(elements));
    }
}
