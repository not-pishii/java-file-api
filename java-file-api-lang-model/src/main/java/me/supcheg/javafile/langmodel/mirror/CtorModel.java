package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A constructor.
///
/// @param typeParams the type parameters of the constructor itself, with their bounds; usually none
/// @param params the parameter types, in order; a variable-arity parameter is its array type
/// @param declared the parameters as the method table of the type lists them, one per parameter of `params`
/// @param throwsTypes the declared exceptions, in declaration order: classes and type variables
public record CtorModel(
        List<TypeParam> typeParams,
        List<TypeRef> params,
        List<MethodTableTemplate.Param> declared,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements MemberModel {

    /// @throws IllegalArgumentException if the constructor has more than [#MAX_ARITY] parameters, or
    ///                                  `declared` is not one per parameter
    public CtorModel {
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        declared = List.copyOf(declared);
        throwsTypes = List.copyOf(throwsTypes);
        if (params.size() > MAX_ARITY) {
            throw new IllegalArgumentException(
                    "a constructor has " + params.size() + " parameters, more than " + MAX_ARITY);
        }
        if (declared.size() != params.size()) {
            throw new IllegalArgumentException("a constructor has " + params.size() + " parameters, but "
                    + declared.size() + " are declared: " + declared);
        }
    }
}
