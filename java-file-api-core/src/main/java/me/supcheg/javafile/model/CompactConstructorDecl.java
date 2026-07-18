package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// A compact constructor declaration inside a record body.
///
/// Takes no explicit parameter list: its parameters are implicitly the
/// record's components. Annotations, modifiers, and thrown types are
/// defensively copied into unmodifiable collections.
///
/// @param annotations the annotations declared on the compact constructor
/// @param modifiers the modifiers on the compact constructor declaration
/// @param body the compact constructor's body
/// @param throwsTypes the checked exception types declared in the compact
///                     constructor's `throws` clause
public record CompactConstructorDecl(
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        CodeBody body,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements RecordMember {
    public CompactConstructorDecl {
        annotations = List.copyOf(annotations);
        modifiers = ModifierValidation.requireValidMember(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE),
                "compact constructor");
        throwsTypes = List.copyOf(throwsTypes);
    }
}
