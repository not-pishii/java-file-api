package me.supcheg.javafile.facts;

import me.supcheg.javafile.annotation.ArrayValue;
import me.supcheg.javafile.annotation.SingleAnnotationValue;

/// The value of an element of an annotation (§3.6), typed by the Java type
/// of the element: a `String`, a primitive marker of [Prim], an enum
/// constant's enum, a nested annotation's type, or the array of one of them.
///
/// The phantom `V` is what a generated builder of an annotation takes for
/// its element, so a value of another type is not expressible:
/// `AnnotationValues.string("x")` is an `AnnotationValue<String>`, never one
/// of an `int` element.
///
/// Values are made by [AnnotationValues]; the class holds the value the
/// renderer writes.
///
/// @param <V> the Java type of the element
public final class AnnotationValue<V> {
    private final me.supcheg.javafile.annotation.AnnotationValue value;

    AnnotationValue(me.supcheg.javafile.annotation.AnnotationValue value) {
        this.value = value;
    }

    /// The value as the core renderer takes it.
    ///
    /// @return the core annotation value
    public me.supcheg.javafile.annotation.AnnotationValue toCore() {
        return value;
    }

    /// The value as an element of an array: a value that is an array is no
    /// element of another one (JLS 9.6.1).
    ///
    /// @return the value
    /// @throws IllegalArgumentException if the value is an array
    SingleAnnotationValue single() {
        if (value instanceof ArrayValue) {
            throw new IllegalArgumentException("an annotation value that is an array is no element of another"
                    + " array: the element type of an annotation element is no array of arrays (JLS 9.6.1)");
        }
        return (SingleAnnotationValue) value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AnnotationValue<?> other && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
