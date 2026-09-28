package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;

/// The state shared by the declared token classes.
abstract class DeclaredTokenData {
    private final ClassOrInterfaceTypeRef typeRef;
    private final ClassDesc erasure;
    private final MethodTable methods;

    DeclaredTokenData(ClassOrInterfaceTypeRef typeRef, MethodTable methods) {
        this.typeRef = typeRef;
        this.erasure = switch (typeRef) {
            case ClassTypeRef cls -> cls.desc();
            case ParameterizedTypeRef parameterized -> parameterized.raw();
            case TypeVarRef var ->
                throw new IllegalArgumentException(
                        "a declared type token needs a class or parameterized type, got type variable " + var.name());
        };
        this.methods = methods;
    }

    public final ClassOrInterfaceTypeRef typeRef() {
        return typeRef;
    }

    public final ClassDesc erasure() {
        return erasure;
    }

    public final MethodTable methods() {
        return methods;
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o.getClass() == getClass() && typeRef.equals(((DeclaredTokenData) o).typeRef);
    }

    @Override
    public final int hashCode() {
        return typeRef.hashCode();
    }

    @Override
    public final String toString() {
        return TypeNames.describe(typeRef);
    }
}
