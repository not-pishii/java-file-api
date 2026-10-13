package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.DeclaredTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// The state shared by the class token classes.
abstract class ClassTokenData extends DeclaredTokenData {

    ClassTokenData(TypeShape<?> shape, List<TokenArg> args) {
        super(shape, args);
    }

    ClassTokenData(TypeShape<?> shape, DeclaredTypeRef typeRef, List<ClassDesc> arguments) {
        super(shape, typeRef, arguments);
    }

    public final List<ClassDesc> superclasses() {
        return shape().superclasses();
    }
}
