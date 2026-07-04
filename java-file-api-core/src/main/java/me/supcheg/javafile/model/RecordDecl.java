package me.supcheg.javafile.model;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// A `record` declaration.
///
/// Components, interfaces, and members are defensively copied into
/// unmodifiable collections.
///
/// @param desc the record's name and package
/// @param modifiers the modifiers on the record declaration
/// @param components the record's components, determining its canonical
///                    constructor parameters and accessors
/// @param interfaces the interfaces the record implements
/// @param members the members of the record body
public record RecordDecl(
        ClassDesc desc,
        Set<Modifier> modifiers,
        List<RecordComponent> components,
        List<ClassDesc> interfaces,
        List<RecordMember> members)
        implements TypeDecl {
    public RecordDecl {
        modifiers = Set.copyOf(modifiers);
        components = List.copyOf(components);
        interfaces = List.copyOf(interfaces);
        members = List.copyOf(members);
    }
}
