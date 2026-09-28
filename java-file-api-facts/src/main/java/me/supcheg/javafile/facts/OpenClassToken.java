package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of a class that is neither abstract nor final: it can be both
/// instantiated and extended.
///
/// @param <T> the Java type this token stands for
public final class OpenClassToken<T> extends ClassTokenData implements ConcreteClassToken<T> {

    private OpenClassToken(ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        super(typeRef, superclasses, methods);
    }

    /// Introduces the fact that a non-final, non-abstract class exists.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the class type, plain or parameterized
    /// @param superclasses the erased superclass chain, see [ClassToken#superclasses()]
    /// @param methods the class's instance methods, declared and inherited
    /// @param <T> the Java type the token stands for; the caller vouches for it
    /// @return the token
    /// @throws IllegalArgumentException if `typeRef` is a type variable
    public static <T> OpenClassToken<T> introduce(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        return new OpenClassToken<>(typeRef, superclasses, methods);
    }
}
