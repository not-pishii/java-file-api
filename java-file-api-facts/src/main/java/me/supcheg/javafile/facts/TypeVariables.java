package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.util.Set;

/// Checks that a type names only declared type variables.
final class TypeVariables {
    private TypeVariables() {}

    /// @param type the type to check
    /// @param declared the names of the declared type variables
    /// @param what what names `type`, for the message, e.g. "a supertype"
    /// @throws IllegalArgumentException if `type` names a type variable not in `declared`
    static void requireDeclared(TypeRef type, Set<String> declared, String what) {
        switch (type) {
            case TypeVarRef var -> {
                if (!declared.contains(var.name())) {
                    throw new IllegalArgumentException(what + " names undeclared type variable " + var.name());
                }
            }
            case ParameterizedTypeRef p -> p.args().forEach(a -> requireDeclared(a, declared, what));
            case ArrayTypeRef array -> requireDeclared(array.component(), declared, what);
            case ClassTypeRef ignored -> {}
            case PrimitiveTypeRef ignored -> {}
        }
    }

    private static void requireDeclared(TypeArg arg, Set<String> declared, String what) {
        switch (arg) {
            case ExactTypeArg exact -> requireDeclared(exact.type(), declared, what);
            case ExtendsTypeArg bound -> requireDeclared(bound.bound(), declared, what);
            case SuperTypeArg bound -> requireDeclared(bound.bound(), declared, what);
            case UnboundedTypeArg ignored -> {}
        }
    }
}
