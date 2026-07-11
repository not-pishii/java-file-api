package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// A `default` method declaration inside an interface body.
///
/// Type parameters, parameters, and thrown types are defensively copied into
/// unmodifiable lists. The method always renders with the `default`
/// modifier; it carries no explicit [Modifier] set because that is the only
/// form Java allows for a default method.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param typeParams the method's type parameters, in declaration order
/// @param params the method's parameters, in declaration order
/// @param body the method's body
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record DefaultMethodDecl(
        String name,
        Optional<TypeRef> returnType,
        List<TypeParam> typeParams,
        List<Param> params,
        CodeBody body,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements InterfaceMember {
    public DefaultMethodDecl {
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
