package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of a class that is neither abstract nor final: it can be both
/// instantiated and extended.
///
/// @param <T> the Java type this token stands for
public final class OpenClassToken<T> extends ClassTokenData implements ConcreteClassToken<T> {

    OpenClassToken(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, Supertypes supertypes, MethodTable methods) {
        super(typeRef, superclasses, supertypes, methods);
    }
}
