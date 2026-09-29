package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A constructor.
///
/// @param typeParams the type parameters of the constructor itself, with their bounds; usually none
/// @param params the parameter types, in order; a variable-arity parameter is its array type
/// @param throwsTypes the declared exceptions, in declaration order: classes and type variables
public record CtorModel(List<TypeParam> typeParams, List<TypeRef> params, List<ClassOrInterfaceTypeRef> throwsTypes)
        implements MemberModel {

    /// @throws IllegalArgumentException if the constructor has more than [#MAX_ARITY] parameters
    public CtorModel {
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
        if (params.size() > MAX_ARITY) {
            throw new IllegalArgumentException(
                    "a constructor has " + params.size() + " parameters, more than " + MAX_ARITY);
        }
    }
}
