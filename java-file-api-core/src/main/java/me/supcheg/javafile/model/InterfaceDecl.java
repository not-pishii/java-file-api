package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// An `interface` declaration.
///
/// The interface is rendered as `sealed` when [#permits] is non-empty.
/// Annotations, modifiers, type parameters, extended interfaces, permitted
/// subtypes, and members are defensively copied into unmodifiable
/// collections.
///
/// @param desc the interface's name and package
/// @param annotations the annotations declared on the interface
/// @param modifiers the modifiers on the interface declaration
/// @param typeParams the declaration's type parameters, in order
/// @param extendsInterfaces the interfaces this interface extends
/// @param permits the subtypes named in a `permits` clause; a non-empty list
///                causes the interface to render as `sealed`
/// @param members the members of the interface body
public record InterfaceDecl(
        ClassDesc desc,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<TypeParam> typeParams,
        List<ClassOrInterfaceTypeRef> extendsInterfaces,
        List<ClassDesc> permits,
        List<InterfaceMember> members)
        implements TypeDecl {
    public InterfaceDecl {
        annotations = List.copyOf(annotations);
        modifiers = Set.copyOf(modifiers);
        typeParams = List.copyOf(typeParams);
        extendsInterfaces = List.copyOf(extendsInterfaces);
        permits = List.copyOf(permits);
        members = List.copyOf(members);
    }
}
