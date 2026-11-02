package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.type.ArrayTypeRef;
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
/// @param arity whether the last parameter is of variable arity, and whether the declaration is
///              `@SafeVarargs`
/// @param declared the parameters as the method table of the type lists them, one per parameter of `params`:
///                 the signature the method is known by among the methods of the type
/// @param throwsTypes the declared exceptions, in declaration order: classes and type variables
/// @param overridability whether the method can be overridden, and must be; [Overridability#FINAL] for a
///                       `static` method
/// @param access the access of the method: `public`, or `protected`
/// @param annotations the annotations the method declares, in declaration order
/// @param parameterAnnotations the annotations of each parameter, in parameter order
public record MethodModel(
        String name,
        boolean isStatic,
        List<TypeParam> typeParams,
        Optional<TypeRef> result,
        List<TypeRef> params,
        List<MethodTableTemplate.Param> declared,
        Arity arity,
        List<ClassOrInterfaceTypeRef> throwsTypes,
        Overridability overridability,
        Access access,
        List<AnnotationUse> annotations,
        List<List<AnnotationUse>> parameterAnnotations)
        implements MemberModel {

    /// A method that declares no annotation.
    ///
    /// @param name the method name
    /// @param isStatic whether the method is `static`
    /// @param typeParams the type parameters of the method
    /// @param result the result type
    /// @param params the parameter types
    /// @param declared the parameters as the method table lists them
    /// @param arity whether the last parameter is of variable arity
    /// @param throwsTypes the declared exceptions
    /// @param overridability whether the method can be overridden
    /// @param access the access of the method
    // one parameter per fact the model holds, as the processor writes it: there is no builder to hide them
    @SuppressWarnings("TooManyParameters")
    public MethodModel(
            String name,
            boolean isStatic,
            List<TypeParam> typeParams,
            Optional<TypeRef> result,
            List<TypeRef> params,
            List<MethodTableTemplate.Param> declared,
            Arity arity,
            List<ClassOrInterfaceTypeRef> throwsTypes,
            Overridability overridability,
            Access access) {
        this(
                name,
                isStatic,
                typeParams,
                result,
                params,
                declared,
                arity,
                throwsTypes,
                overridability,
                access,
                List.of(),
                List.of());
    }

    /// @throws IllegalArgumentException if `name` is not a Java identifier, the method has more
    ///                                  than [#MAX_ARITY] parameters, `declared` is not one per
    ///                                  parameter, or it is `static` but not [Overridability#FINAL]
    public MethodModel {
        Identifiers.requireValid(name);
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        declared = List.copyOf(declared);
        throwsTypes = List.copyOf(throwsTypes);
        annotations = List.copyOf(annotations);
        List<List<AnnotationUse>> byParameter = new java.util.ArrayList<>(parameterAnnotations);
        while (byParameter.size() < params.size()) {
            byParameter.add(List.of());
        }
        parameterAnnotations = byParameter.stream().map(List::copyOf).toList();
        if (params.size() > MAX_ARITY) {
            throw new IllegalArgumentException(
                    "method " + name + " has " + params.size() + " parameters, more than " + MAX_ARITY);
        }
        boolean lastIsArray =
                params.stream().skip(Math.max(params.size() - 1, 0)).anyMatch(ArrayTypeRef.class::isInstance);
        if (arity instanceof Arity.Variable && !lastIsArray) {
            throw new IllegalArgumentException(
                    "method " + name + " is of variable arity, so its last parameter is an array: " + params);
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
