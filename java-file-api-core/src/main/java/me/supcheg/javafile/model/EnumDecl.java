package me.supcheg.javafile.model;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// An `enum` declaration.
///
/// Constants, interfaces, and members are defensively copied into
/// unmodifiable collections.
///
/// @param desc the enum's name and package
/// @param modifiers the modifiers on the enum declaration
/// @param constants the enum constants, in declaration order
/// @param interfaces the interfaces the enum implements
/// @param members the members of the enum body, rendered after the constant
///                 list
public record EnumDecl(
        ClassDesc desc,
        Set<Modifier> modifiers,
        List<EnumConstant> constants,
        List<ClassOrInterfaceTypeRef> interfaces,
        List<ClassMember> members)
        implements TypeDecl {
    public EnumDecl {
        modifiers = Set.copyOf(modifiers);
        constants = List.copyOf(constants);
        interfaces = List.copyOf(interfaces);
        members = List.copyOf(members);
    }
}
