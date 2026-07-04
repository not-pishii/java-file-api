package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// A constructor declaration inside a class body.
///
/// Modifiers, parameters, and thrown types are defensively copied into
/// unmodifiable collections. When rendered inside an enum body, the
/// constructor's modifiers are omitted, since enum constructors are always
/// implicitly private.
///
/// @param modifiers the modifiers on the constructor declaration
/// @param params the constructor's parameters, in declaration order
/// @param body the constructor's body
/// @param throwsTypes the checked exception types declared in the
///                     constructor's `throws` clause
public record ConstructorDecl(Set<Modifier> modifiers, List<Param> params, CodeBody body, List<ClassDesc> throwsTypes)
        implements ClassMember {
    public ConstructorDecl {
        modifiers = Set.copyOf(modifiers);
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
