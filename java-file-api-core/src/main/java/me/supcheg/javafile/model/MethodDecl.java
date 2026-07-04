package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A concrete (non-abstract) method declaration, usable in both class and
/// record bodies.
///
/// Modifiers, parameters, and thrown types are defensively copied into
/// unmodifiable collections.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param modifiers the modifiers on the method declaration
/// @param params the method's parameters, in declaration order
/// @param body the method's body
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record MethodDecl(
        String name,
        Optional<TypeRef> returnType,
        Set<Modifier> modifiers,
        List<Param> params,
        CodeBody body,
        List<ClassDesc> throwsTypes)
        implements ClassMember, RecordMember {
    public MethodDecl {
        modifiers = Set.copyOf(modifiers);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
