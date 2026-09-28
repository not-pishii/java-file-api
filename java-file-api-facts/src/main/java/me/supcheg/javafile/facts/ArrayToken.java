package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;

/// A token of an array type, typed by both the array and its element.
///
/// An array of a reference type comes from [#of(RefToken)], e.g.
/// `ArrayToken<String[], String>`; an array of a primitive type from
/// [PrimitiveToken#array()], whose array phantom is the primitive array type
/// itself and whose element phantom is the primitive's marker, e.g.
/// `ArrayToken<int[], Prim.Int>`. The token is the witness the array
/// combinators of the typed layer take — `at`, `length`, `newArray` — so the
/// element type of an access is always the one the token proves.
///
/// Java arrays are covariant: a `String[]` is an `Object[]`, and storing an
/// `Integer` through the `Object[]` view throws `ArrayStoreException` at run
/// time. The typed layer inherits this, as javac does.
///
/// @param <A> the Java array type this token stands for
/// @param <E> the element type
public final class ArrayToken<A, E> implements RefToken<A> {
    private final TypeToken<E> component;
    private final ArrayTypeRef typeRef;

    ArrayToken(TypeToken<E> component) {
        this.component = component;
        this.typeRef = Types.array(component.typeRef());
    }

    /// Returns the token of an array of a reference type.
    ///
    /// @param component the element type
    /// @param <E> the element type
    /// @return the array token
    public static <E> ArrayToken<E[], E> of(RefToken<E> component) {
        return new ArrayToken<>(component);
    }

    /// The element type.
    ///
    /// @return the component token
    public TypeToken<E> component() {
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
        return o instanceof ArrayToken<?, ?> other && component.equals(other.component);
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
