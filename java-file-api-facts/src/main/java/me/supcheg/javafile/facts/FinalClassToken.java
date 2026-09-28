package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of a final class, a record included: it can be instantiated but
/// not extended.
///
/// @param <T> the Java type this token stands for
public final class FinalClassToken<T> extends ClassTokenData implements ConcreteClassToken<T> {

    private FinalClassToken(ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        super(typeRef, superclasses, methods);
    }

    /// Introduces the fact that a final class exists.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the class type, plain or parameterized
    /// @param superclasses the erased superclass chain, see [ClassToken#superclasses()]
    /// @param methods the class's instance methods, declared and inherited
    /// @param <T> the Java type the token stands for; the caller vouches for it
    /// @return the token
    /// @throws IllegalArgumentException if `typeRef` is a type variable
    public static <T> FinalClassToken<T> introduce(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        return new FinalClassToken<>(typeRef, superclasses, methods);
    }
}
