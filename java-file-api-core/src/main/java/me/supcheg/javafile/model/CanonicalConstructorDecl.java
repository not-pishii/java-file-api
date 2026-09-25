package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// A record constructor with an explicit parameter list,
/// e.g. `public Point(int x, int y) { ... }`.
///
/// The parameters must match the record's components in name, type, and
/// order; otherwise rendering throws `IllegalArgumentException`.
///
/// @param annotations the annotations declared on the constructor
/// @param modifiers the modifiers on the constructor declaration
/// @param params the constructor's parameters; must mirror the record's components exactly
/// @param body the constructor's body
/// @param throwsTypes the checked exception types declared in the constructor's `throws` clause
public record CanonicalConstructorDecl(
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<Param> params,
        CodeBody body,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements RecordMember {
    public CanonicalConstructorDecl {
        annotations = List.copyOf(annotations);
        modifiers = ModifierValidation.requireValidMember(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE),
                "canonical constructor");
        params = ModifierValidation.requireVarargsOnlyLast(List.copyOf(params));
        throwsTypes = List.copyOf(throwsTypes);
    }
}
