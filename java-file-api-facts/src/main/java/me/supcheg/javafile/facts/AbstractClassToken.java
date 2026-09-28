package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of an abstract class: it can be extended but not instantiated.
///
/// @param <T> the Java type this token stands for
public final class AbstractClassToken<T> extends ClassTokenData implements ClassToken<T> {

    private AbstractClassToken(ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        super(typeRef, superclasses, methods);
    }

    /// Introduces the fact that an abstract class exists.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the class type, plain or parameterized
    /// @param superclasses the erased superclass chain, see [ClassToken#superclasses()]
    /// @param methods the class's instance methods, declared and inherited
    /// @param <T> the Java type the token stands for; the caller vouches for it
    /// @return the token
    /// @throws IllegalArgumentException if `typeRef` is a type variable
    public static <T> AbstractClassToken<T> introduce(
            ClassOrInterfaceTypeRef typeRef, List<ClassDesc> superclasses, MethodTable methods) {
        return new AbstractClassToken<>(typeRef, superclasses, methods);
    }
}
