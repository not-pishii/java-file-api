package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.List;

/// An enum constructor. It is implicitly private, so no modifiers are specified.
///
/// @param annotations the annotations declared on the constructor
/// @param params the constructor's parameters, in declaration order
/// @param body the constructor's body
/// @param throwsTypes the checked exception types declared in the
///                     constructor's `throws` clause
public record EnumConstructorDecl(
        List<AnnotationUse> annotations, List<Param> params, CodeBody body, List<ClassOrInterfaceTypeRef> throwsTypes)
        implements EnumMember {
    public EnumConstructorDecl {
        annotations = List.copyOf(annotations);
        params = ModifierValidation.requireVarargsOnlyLast(List.copyOf(params));
        throwsTypes = List.copyOf(throwsTypes);
    }
}
