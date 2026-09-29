package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.util.Collection;
import java.util.List;

/// The classes and interfaces a type reference mentions: the class of a
/// type and of each type argument, wildcard bound and array component, but
/// no type variables or primitives.
final class Mentions {
    private Mentions() {}

    /// Adds what a type mentions.
    ///
    /// @param type the type
    /// @param found where to add
    static void of(TypeRef type, Collection<ClassDesc> found) {
        switch (type) {
            case ClassTypeRef cls -> found.add(cls.desc());
            case ParameterizedTypeRef parameterized -> {
                found.add(parameterized.raw());
                parameterized.args().forEach(arg -> of(arg, found));
            }
            case ArrayTypeRef array -> of(array.component(), found);
            case TypeVarRef ignored -> {}
            case PrimitiveTypeRef ignored -> {}
        }
    }

    /// Adds what the bounds of type parameters mention.
    ///
    /// @param typeParams the type parameters
    /// @param found where to add
    static void of(List<TypeParam> typeParams, Collection<ClassDesc> found) {
        typeParams.forEach(p -> p.bounds().forEach(bound -> of(bound, found)));
    }

    private static void of(TypeArg arg, Collection<ClassDesc> found) {
        switch (arg) {
            case ExactTypeArg exact -> of(exact.type(), found);
            case ExtendsTypeArg bound -> of(bound.bound(), found);
            case SuperTypeArg bound -> of(bound.bound(), found);
            case UnboundedTypeArg ignored -> {}
        }
    }
}
