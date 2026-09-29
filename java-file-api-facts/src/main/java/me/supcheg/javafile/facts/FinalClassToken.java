package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of a final class, a record included: it can be instantiated but
/// not extended.
///
/// @param <T> the Java type this token stands for
public final class FinalClassToken<T> extends ClassTokenData implements ConcreteClassToken<T> {

    FinalClassToken(TypeShape<DeclaredKind.FinalClass> shape, List<TokenArg> args) {
        super(shape, args);
    }

    FinalClassToken(
            TypeShape<DeclaredKind.FinalClass> shape, ClassOrInterfaceTypeRef typeRef, List<ClassDesc> arguments) {
        super(shape, typeRef, arguments);
    }
}
