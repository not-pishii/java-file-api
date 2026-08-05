package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// A mutable builder for an array-valued annotation member, rendered as `{ ... }`.
///
/// Instances are created by
/// [AnnotationBuilder#withArrayMember(String,Consumer)] and are not meant to be
/// instantiated directly. Elements are [SingleAnnotationValue]s, so an array of
/// arrays — which Java forbids — is unrepresentable.
///
/// Instances are not thread-safe.
public final class ArrayValueBuilder {

    private final List<SingleAnnotationValue> elements = new ArrayList<>();

    ArrayValueBuilder() {}

    /// Appends a string literal element.
    ///
    /// @param value the literal value
    /// @return this builder
    public ArrayValueBuilder withLiteral(String value) {
        elements.add(AnnotationValues.literal(value));
        return this;
    }

    /// Appends an `int` literal element.
    ///
    /// @param value the literal value
    /// @return this builder
    public ArrayValueBuilder withLiteral(int value) {
        elements.add(AnnotationValues.literal(value));
        return this;
    }

    /// Appends a `long` literal element.
    ///
    /// @param value the literal value
    /// @return this builder
    public ArrayValueBuilder withLiteral(long value) {
        elements.add(AnnotationValues.literal(value));
        return this;
    }

    /// Appends a `double` literal element.
    ///
    /// @param value the literal value
    /// @return this builder
    public ArrayValueBuilder withLiteral(double value) {
        elements.add(AnnotationValues.literal(value));
        return this;
    }

    /// Appends a `boolean` literal element.
    ///
    /// @param value the literal value
    /// @return this builder
    public ArrayValueBuilder withLiteral(boolean value) {
        elements.add(AnnotationValues.literal(value));
        return this;
    }

    /// Appends a class literal element, e.g. `Foo.class`.
    ///
    /// @param type the referenced class
    /// @return this builder
    public ArrayValueBuilder withClass(ClassDesc type) {
        elements.add(AnnotationValues.classValue(type));
        return this;
    }

    /// Appends an enum constant element, e.g. `Level.HIGH`.
    ///
    /// @param enumType the enum class
    /// @param constant the constant's name
    /// @return this builder
    public ArrayValueBuilder withEnum(ClassDesc enumType, String constant) {
        elements.add(AnnotationValues.enumValue(enumType, constant));
        return this;
    }

    /// Appends a nested annotation element, populated via an [AnnotationBuilder].
    ///
    /// @param type the nested annotation type
    /// @param spec receives the builder to populate the nested annotation's members
    /// @return this builder
    public ArrayValueBuilder withNested(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        elements.add(AnnotationValues.nested(type, spec));
        return this;
    }

    /// Appends a pre-built element.
    ///
    /// @param value the element to append
    /// @return this builder
    public ArrayValueBuilder withValue(SingleAnnotationValue value) {
        elements.add(value);
        return this;
    }

    ArrayValue build() {
        return new ArrayValue(List.copyOf(elements));
    }
}
