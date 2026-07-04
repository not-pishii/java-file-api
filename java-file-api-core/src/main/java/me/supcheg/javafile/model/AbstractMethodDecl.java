package me.supcheg.javafile.model;

import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// An abstract method declaration: no body, usable in both class and
/// interface bodies.
///
/// Parameters, modifiers, and thrown types are defensively copied into
/// unmodifiable collections.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param params the method's parameters, in declaration order
/// @param modifiers the modifiers on the method declaration
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record AbstractMethodDecl(
        String name,
        Optional<TypeRef> returnType,
        List<Param> params,
        Set<Modifier> modifiers,
        List<ClassDesc> throwsTypes)
        implements InterfaceMember, ClassMember {
    public AbstractMethodDecl {
        params = List.copyOf(params);
        modifiers = Set.copyOf(modifiers);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
