package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;
import java.util.function.Supplier;

/// The state shared by the declared token classes.
abstract class DeclaredTokenData {
    private final ClassOrInterfaceTypeRef typeRef;
    private final ClassDesc erasure;
    private final Supertypes supertypes;
    private final Supplier<MethodTable> methods;

    DeclaredTokenData(ClassOrInterfaceTypeRef typeRef, Supertypes supertypes, MethodTable methods) {
        this(typeRef, supertypes, () -> methods);
    }

    /// For a type whose methods are known only later: a class still being
    /// declared, whose table is complete once its declaration is.
    DeclaredTokenData(ClassOrInterfaceTypeRef typeRef, Supertypes supertypes, Supplier<MethodTable> methods) {
        this.typeRef = typeRef;
        this.erasure = switch (typeRef) {
            case ClassTypeRef cls -> cls.desc();
            case ParameterizedTypeRef parameterized -> parameterized.raw();
            case TypeVarRef var ->
                throw new IllegalArgumentException(
                        "a declared type token needs a class or parameterized type, got type variable " + var.name());
        };
        int arity = typeRef instanceof ParameterizedTypeRef parameterized
                ? parameterized.args().size()
                : 0;
        if (!supertypes.equals(Supertypes.NONE) && supertypes.typeParameters().size() != arity) {
            throw new IllegalArgumentException("the supertypes of " + TypeNames.describe(typeRef) + " are given for "
                    + supertypes.typeParameters().size() + " type parameters, but it has " + arity
                    + " type arguments");
        }
        this.supertypes = supertypes;
        this.methods = methods;
    }

    public final ClassOrInterfaceTypeRef typeRef() {
        return typeRef;
    }

    public final ClassDesc erasure() {
        return erasure;
    }

    public final Supertypes supertypes() {
        return supertypes;
    }

    public final MethodTable methods() {
        return methods.get();
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
