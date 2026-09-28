package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;

/// A token of an array type.
///
/// An array of a reference type comes from [#of(RefToken)]; an array of a
/// primitive type from [PrimitiveToken#array()], whose phantom is the
/// primitive array type itself, e.g. `int[]`.
///
/// @param <T> the Java array type this token stands for
public final class ArrayToken<T> implements RefToken<T> {
    private final TypeToken<?> component;
    private final ArrayTypeRef typeRef;

    ArrayToken(TypeToken<?> component) {
        this.component = component;
        this.typeRef = Types.array(component.typeRef());
    }

    /// Returns the token of an array of a reference type.
    ///
    /// @param component the element type
    /// @param <E> the element type
    /// @return the array token
    public static <E> ArrayToken<E[]> of(RefToken<E> component) {
        return new ArrayToken<>(component);
    }

    /// The element type.
    ///
    /// @return the component token
    public TypeToken<?> component() {
        return component;
    }

    @Override
    public ArrayTypeRef typeRef() {
        return typeRef;
    }

    @Override
    public ClassDesc erasure() {
        return component.erasure().arrayType();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArrayToken<?> other && component.equals(other.component);
    }

    @Override
    public int hashCode() {
        return component.hashCode() + 1;
    }

    @Override
    public String toString() {
        return TypeNames.describe(typeRef);
    }
}
