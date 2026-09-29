package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Supplier;

/// The state shared by the class token classes.
abstract class ClassTokenData extends DeclaredTokenData {
    private final List<ClassDesc> superclasses;

    ClassTokenData(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, Supertypes supertypes, MethodTable methods) {
        super(typeRef, supertypes, methods);
        this.superclasses = List.copyOf(superclasses);
    }

    ClassTokenData(
            ClassOrInterfaceTypeRef typeRef,
            List<ClassDesc> superclasses,
            Supertypes supertypes,
            Supplier<MethodTable> methods) {
        super(typeRef, supertypes, methods);
        this.superclasses = List.copyOf(superclasses);
    }

    public final List<ClassDesc> superclasses() {
        return superclasses;
    }
}
