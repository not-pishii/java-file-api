package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;

/// A token of a type variable of the generated code, e.g. the `T` of a
/// generated generic method.
///
/// Typed declarations introduce it through a CPS brand, so the phantom `T`
/// is a fresh type variable of the generator as well.
///
/// @param <T> the Java type variable this token stands for
public final class TypeVarToken<T> implements RefToken<T> {
    private final TypeVarRef typeRef;
    private final ClassDesc erasure;

    private TypeVarToken(TypeVarRef typeRef, ClassDesc erasure) {
        this.typeRef = typeRef;
        this.erasure = erasure;
    }

    /// Introduces the fact that a type variable is in scope.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the type variable
    /// @param erasure the erasure of its leftmost bound, `java.lang.Object` for none
    /// @param <T> the type variable the token stands for; the caller vouches for it
    /// @return the token
    public static <T> TypeVarToken<T> introduce(TypeVarRef typeRef, ClassDesc erasure) {
        return new TypeVarToken<>(typeRef, erasure);
    }

    @Override
    public TypeVarRef typeRef() {
        return typeRef;
    }

    @Override
    public ClassDesc erasure() {
        return erasure;
    }

    @Override
    public String toString() {
        return typeRef.name();
    }
}
