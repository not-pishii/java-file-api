package me.supcheg.javafile.facts;

import java.util.List;

/// Makes the values elements of an annotation take (§3.6). The phantom of a
/// value is the element's type as the generated builder of the annotation
/// takes it, so an element of another type is not given this value:
///
/// ```java
/// Author_.annotation()
///         .name(AnnotationValues.string("Ada"))
///         .year(AnnotationValues.int_(1815))
///         .build();
/// ```
///
/// A literal of a `byte`, `short`, `char` or `float` element is written as
/// the `int` or `double` it is: javac narrows a constant expression in an
/// annotation element, as it does in an assignment (JLS 5.2).
public final class AnnotationValues {
    private AnnotationValues() {}

    /// A `String` value.
    ///
    /// @param value the value
    /// @return the value of a `String` element
    public static AnnotationValue<String> string(String value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// A `boolean` value.
    ///
    /// @param value the value
    /// @return the value of a `boolean` element
    public static AnnotationValue<Prim.Bool> bool(boolean value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// An `int` value.
    ///
    /// @param value the value
    /// @return the value of an `int` element
    public static AnnotationValue<Prim.Int> int_(int value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// A `long` value.
    ///
    /// @param value the value
    /// @return the value of a `long` element
    public static AnnotationValue<Prim.Long> long_(long value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// A `double` value.
    ///
    /// @param value the value
    /// @return the value of a `double` element
    public static AnnotationValue<Prim.Double> double_(double value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// A `float` value, written as the `double` javac narrows to the element.
    ///
    /// @param value the value
    /// @return the value of a `float` element
    public static AnnotationValue<Prim.Float> float_(float value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal(value));
    }

    /// A `byte` value, written as the `int` javac narrows to the element.
    ///
    /// @param value the value
    /// @return the value of a `byte` element
    public static AnnotationValue<Prim.Byte> byte_(byte value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal((int) value));
    }

    /// A `short` value, written as the `int` javac narrows to the element.
    ///
    /// @param value the value
    /// @return the value of a `short` element
    public static AnnotationValue<Prim.Short> short_(short value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal((int) value));
    }

    /// A `char` value, written as the `int` javac narrows to the element.
    ///
    /// @param value the value
    /// @return the value of a `char` element
    public static AnnotationValue<Prim.Char> char_(char value) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.literal((int) value));
    }

    /// An enum constant value.
    ///
    /// @param constant the constant
    /// @param <E> the enum type
    /// @return the value of an element of the enum
    public static <E> AnnotationValue<E> enum_(EnumConstant<E> constant) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.enumValue(
                constant.owner().erasure(), constant.name()));
    }

    /// A class literal value.
    ///
    /// @param type the type named by the literal
    /// @param <T> the type
    /// @return the value of a `Class` element
    public static <T> AnnotationValue<Class<?>> class_(RefToken<T> type) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.classValue(type.erasure()));
    }

    /// A nested annotation value.
    ///
    /// @param annotation the annotation
    /// @param <A> the annotation type
    /// @return the value of an element of the annotation type
    public static <A> AnnotationValue<A> nested(AnnotationRef<A> annotation) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.nested(annotation.toUse()));
    }

    /// An array value.
    ///
    /// @param elements the elements, in order
    /// @param <V> the element type of the array
    /// @return the value of an element that is an array of the type
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <V> AnnotationValue<V[]> array(AnnotationValue<V>... elements) {
        return array(List.of(elements));
    }

    /// An array value.
    ///
    /// @param elements the elements, in order
    /// @param <V> the element type of the array
    /// @return the value of an element that is an array of the type
    public static <V> AnnotationValue<V[]> array(List<? extends AnnotationValue<V>> elements) {
        return new AnnotationValue<>(me.supcheg.javafile.annotation.AnnotationValues.array(
                elements.stream().map(AnnotationValue::single).toList()));
    }
}
