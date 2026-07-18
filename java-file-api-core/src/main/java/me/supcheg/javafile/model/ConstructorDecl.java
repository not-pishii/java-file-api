package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// A constructor declaration inside a class body.
///
/// Annotations, modifiers, parameters, and thrown types are defensively
/// copied into unmodifiable collections. Enum constructors are represented
/// separately by [EnumConstructorDecl], which has no modifiers field since
/// enum constructors are always implicitly private.
///
/// @param annotations the annotations declared on the constructor
/// @param modifiers the modifiers on the constructor declaration
/// @param params the constructor's parameters, in declaration order
/// @param body the constructor's body
/// @param throwsTypes the checked exception types declared in the
///                     constructor's `throws` clause
public record ConstructorDecl(
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<Param> params,
        CodeBody body,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements ClassMember {
    public ConstructorDecl {
        annotations = List.copyOf(annotations);
        modifiers = ModifierValidation.requireValidMember(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE),
                "constructor");
        params = List.copyOf(params);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
