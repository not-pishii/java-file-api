package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.List;
import java.util.Set;

/// A compact constructor declaration inside a record body.
///
/// Takes no explicit parameter list: its parameters are implicitly the
/// record's components. Modifiers and thrown types are defensively copied
/// into unmodifiable collections.
///
/// @param modifiers the modifiers on the compact constructor declaration
/// @param body the compact constructor's body
/// @param throwsTypes the checked exception types declared in the compact
///                     constructor's `throws` clause
public record CompactConstructorDecl(Set<Modifier> modifiers, CodeBody body, List<ClassOrInterfaceTypeRef> throwsTypes)
        implements RecordMember {
    public CompactConstructorDecl {
        modifiers = Set.copyOf(modifiers);
        throwsTypes = List.copyOf(throwsTypes);
    }
}
