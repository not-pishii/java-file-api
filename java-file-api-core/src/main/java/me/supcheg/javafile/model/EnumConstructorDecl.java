package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.List;

/// An enum's constructor declaration.
///
/// Unlike [ConstructorDecl], there is no `modifiers` field: enum
/// constructors are always implicitly private, so that state is
/// unrepresentable rather than accepted and silently ignored at render time.
/// Parameters and thrown types are defensively copied into unmodifiable
/// lists.
///
/// @param params the constructor's parameters, in declaration order
/// @param body the constructor's body
/// @param throwsTypes the checked exception types declared in the
///                     constructor's `throws` clause
public record EnumConstructorDecl(List<Param> params, CodeBody body, List<ClassOrInterfaceTypeRef> throwsTypes)
        implements EnumMember {
    public EnumConstructorDecl {
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
