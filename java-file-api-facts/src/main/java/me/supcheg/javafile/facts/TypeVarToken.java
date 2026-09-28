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

    TypeVarToken(TypeVarRef typeRef, ClassDesc erasure) {
        this.typeRef = typeRef;
        this.erasure = erasure;
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
