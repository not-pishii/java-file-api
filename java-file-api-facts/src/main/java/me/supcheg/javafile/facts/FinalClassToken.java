package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Supplier;

/// A token of a final class, a record included: it can be instantiated but
/// not extended.
///
/// @param <T> the Java type this token stands for
public final class FinalClassToken<T> extends ClassTokenData implements ConcreteClassToken<T> {

    FinalClassToken(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, Supertypes supertypes, MethodTable methods) {
        super(typeRef, superclasses, supertypes, methods);
    }

    FinalClassToken(
            ClassOrInterfaceTypeRef typeRef,
            List<ClassDesc> superclasses,
            Supertypes supertypes,
            Supplier<MethodTable> methods) {
        super(typeRef, superclasses, supertypes, methods);
    }
}
