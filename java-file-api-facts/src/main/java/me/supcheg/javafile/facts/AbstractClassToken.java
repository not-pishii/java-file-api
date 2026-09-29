package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A token of an abstract class: it can be extended but not instantiated.
///
/// @param <T> the Java type this token stands for
public final class AbstractClassToken<T> extends ClassTokenData implements ClassToken<T> {

    AbstractClassToken(TypeShape<DeclaredKind.AbstractClass> shape, List<TokenArg> args) {
        super(shape, args);
    }

    AbstractClassToken(
            TypeShape<DeclaredKind.AbstractClass> shape, ClassOrInterfaceTypeRef typeRef, List<ClassDesc> arguments) {
        super(shape, typeRef, arguments);
    }
}
