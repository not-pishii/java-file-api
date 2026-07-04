package me.supcheg.javafile.model;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// An `interface` declaration.
///
/// The interface is rendered as `sealed` when [#permits] is non-empty.
/// Modifiers, extended interfaces, permitted subtypes, and members are
/// defensively copied into unmodifiable collections.
///
/// @param desc the interface's name and package
/// @param modifiers the modifiers on the interface declaration
/// @param extendsInterfaces the interfaces this interface extends
/// @param permits the subtypes named in a `permits` clause; a non-empty list
///                causes the interface to render as `sealed`
/// @param members the members of the interface body
public record InterfaceDecl(
        ClassDesc desc,
        Set<Modifier> modifiers,
        List<ClassDesc> extendsInterfaces,
        List<ClassDesc> permits,
        List<InterfaceMember> members)
        implements TypeDecl {
    public InterfaceDecl {
        modifiers = Set.copyOf(modifiers);
        extendsInterfaces = List.copyOf(extendsInterfaces);
        permits = List.copyOf(permits);
        members = List.copyOf(members);
    }
}
