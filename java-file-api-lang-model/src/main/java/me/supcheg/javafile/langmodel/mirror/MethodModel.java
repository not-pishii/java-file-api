package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.MethodTableTemplate;
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
/// @param declared the parameters as the method table of the type lists them, one per parameter of `params`:
///                 the signature the method is known by among the methods of the type
/// @param throwsTypes the declared exceptions, in declaration order: classes and type variables
/// @param overridability whether the method can be overridden, and must be; [Overridability#FINAL] for a
///                       `static` method
/// @param access the access of the method: `public`, or `protected`
public record MethodModel(
        String name,
        boolean isStatic,
        List<TypeParam> typeParams,
        Optional<TypeRef> result,
        List<TypeRef> params,
        List<MethodTableTemplate.Param> declared,
        List<ClassOrInterfaceTypeRef> throwsTypes,
        Overridability overridability,
        Access access)
        implements MemberModel {

    /// @throws IllegalArgumentException if `name` is not a Java identifier, the method has more
    ///                                  than [#MAX_ARITY] parameters, `declared` is not one per
    ///                                  parameter, or it is `static` but not [Overridability#FINAL]
    public MethodModel {
        Identifiers.requireValid(name);
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        declared = List.copyOf(declared);
        throwsTypes = List.copyOf(throwsTypes);
        if (params.size() > MAX_ARITY) {
            throw new IllegalArgumentException(
                    "method " + name + " has " + params.size() + " parameters, more than " + MAX_ARITY);
        }
        if (declared.size() != params.size()) {
            throw new IllegalArgumentException("method " + name + " has " + params.size() + " parameters, but "
                    + declared.size() + " are declared: " + declared);
        }
        if (isStatic && overridability != Overridability.FINAL) {
            throw new IllegalArgumentException("static method " + name + " cannot be " + overridability);
        }
    }
}
