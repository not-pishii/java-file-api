package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

/// A `default` method declaration inside an interface body.
///
/// Parameters and thrown types are defensively copied into unmodifiable
/// lists. The method always renders with the `default` modifier; it carries
/// no explicit [Modifier] set because that is the only form Java allows for a
/// default method.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param params the method's parameters, in declaration order
/// @param body the method's body
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record DefaultMethodDecl(
        String name, Optional<TypeRef> returnType, List<Param> params, CodeBody body, List<ClassDesc> throwsTypes)
        implements InterfaceMember {
    public DefaultMethodDecl {
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
