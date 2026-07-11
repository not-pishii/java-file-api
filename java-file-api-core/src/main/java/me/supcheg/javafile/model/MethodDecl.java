package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A concrete (non-abstract) method declaration, usable in both class and
/// record bodies.
///
/// Modifiers, type parameters, parameters, and thrown types are defensively
/// copied into unmodifiable collections.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param modifiers the modifiers on the method declaration
/// @param typeParams the method's type parameters, in declaration order
/// @param params the method's parameters, in declaration order
/// @param body the method's body
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record MethodDecl(
        String name,
        Optional<TypeRef> returnType,
        Set<Modifier> modifiers,
        List<TypeParam> typeParams,
        List<Param> params,
        CodeBody body,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements ClassMember, RecordMember, EnumMember, EnumConstantMember {
    public MethodDecl {
        modifiers = Set.copyOf(modifiers);
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
