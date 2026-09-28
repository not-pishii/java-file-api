package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

/// A token of an interface type, e.g. `Runnable` or `List<String>`.
///
/// Only interface tokens can be implemented by a class or extended by an
/// interface.
///
/// @param <T> the Java type this token stands for
public final class InterfaceToken<T> extends DeclaredTokenData implements DeclaredToken<T> {

    private InterfaceToken(ClassOrInterfaceTypeRef typeRef, MethodTable methods) {
        super(typeRef, methods);
    }

    /// Introduces the fact that an interface type exists.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the interface type, plain or parameterized
    /// @param methods the interface's instance methods, declared and inherited
    /// @param <T> the Java type the token stands for; the caller vouches for it
    /// @return the token
    /// @throws IllegalArgumentException if `typeRef` is a type variable
    public static <T> InterfaceToken<T> introduce(ClassOrInterfaceTypeRef typeRef, MethodTable methods) {
        return new InterfaceToken<>(typeRef, methods);
    }
}
