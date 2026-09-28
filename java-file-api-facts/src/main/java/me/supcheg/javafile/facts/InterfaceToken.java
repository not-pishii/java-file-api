package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

/// A token of an interface type, e.g. `Runnable` or `List<String>`.
///
/// Only interface tokens can be implemented by a class or extended by an
/// interface.
///
/// @param <T> the Java type this token stands for
public final class InterfaceToken<T> extends DeclaredTokenData implements DeclaredToken<T> {

    InterfaceToken(ClassOrInterfaceTypeRef typeRef, MethodTable methods) {
        super(typeRef, methods);
    }
}
