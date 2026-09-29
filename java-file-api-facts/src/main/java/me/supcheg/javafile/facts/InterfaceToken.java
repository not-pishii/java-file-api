package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of an interface type, e.g. `Runnable` or `List<String>`.
///
/// Only interface tokens can be implemented by a class or extended by an
/// interface.
///
/// @param <T> the Java type this token stands for
public final class InterfaceToken<T> extends DeclaredTokenData implements DeclaredToken<T> {

    InterfaceToken(TypeShape<DeclaredKind.Interface> shape, List<TokenArg> args) {
        super(shape, args);
    }

    InterfaceToken(
            TypeShape<DeclaredKind.Interface> shape, ClassOrInterfaceTypeRef typeRef, List<ClassDesc> arguments) {
        super(shape, typeRef, arguments);
    }
}
