package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// A method, instance or `static`.
///
/// @param name the method name
/// @param isStatic whether the method is `static`
/// @param typeParams the type parameters of the method, with their bounds; empty unless it is generic
/// @param result the result type; empty for `void`
/// @param params the parameter types, in order; a variable-arity parameter is its array type
/// @param throwsTypes the declared exceptions, in declaration order: classes and type variables
/// @param overridability whether the method can be overridden, and must be; [Overridability#FINAL] for a
///                       `static` method
public record MethodModel(
        String name,
        boolean isStatic,
        List<TypeParam> typeParams,
        Optional<TypeRef> result,
        List<TypeRef> params,
        List<ClassOrInterfaceTypeRef> throwsTypes,
        Overridability overridability)
        implements MemberModel {

    /// @throws IllegalArgumentException if `name` is not a Java identifier, the method has more
    ///                                  than [#MAX_ARITY] parameters, or it is `static` but not
    ///                                  [Overridability#FINAL]
    public MethodModel {
        Identifiers.requireValid(name);
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
        if (params.size() > MAX_ARITY) {
            throw new IllegalArgumentException(
                    "method " + name + " has " + params.size() + " parameters, more than " + MAX_ARITY);
        }
        if (isStatic && overridability != Overridability.FINAL) {
            throw new IllegalArgumentException("static method " + name + " cannot be " + overridability);
        }
    }
}
