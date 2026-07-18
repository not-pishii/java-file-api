package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// An `enum` declaration.
///
/// Annotations, constants, interfaces, and members are defensively copied
/// into unmodifiable collections.
///
/// @param desc the enum's name and package
/// @param annotations the annotations declared on the enum
/// @param modifiers the modifiers on the enum declaration; `static`, `private`, and
///                   `protected` are accepted because `EnumDecl` also models a nested
///                   member enum
/// @param constants the enum constants, in declaration order
/// @param interfaces the interfaces the enum implements
/// @param members the members of the enum body, rendered after the constant
///                 list
public record EnumDecl(
        ClassDesc desc,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<EnumConstant> constants,
        List<ClassOrInterfaceTypeRef> interfaces,
        List<EnumMember> members)
        implements TypeDecl {
    public EnumDecl {
        modifiers = ModifierValidation.requireValidTopLevel(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.STATIC),
                "enum");
        annotations = List.copyOf(annotations);
        constants = List.copyOf(constants);
        interfaces = List.copyOf(interfaces);
        members = List.copyOf(members);
    }
}
