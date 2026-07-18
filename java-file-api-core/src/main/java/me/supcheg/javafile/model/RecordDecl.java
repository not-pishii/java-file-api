package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;

import java.lang.constant.ClassDesc;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/// A `record` declaration.
///
/// Annotations, type parameters, components, interfaces, and members are
/// defensively copied into unmodifiable collections.
///
/// @param desc the record's name and package
/// @param annotations the annotations declared on the record
/// @param modifiers the modifiers on the record declaration; `static`, `private`, and
///                   `protected` are accepted because `RecordDecl` also models a nested
///                   member record
/// @param typeParams the declaration's type parameters, in order
/// @param components the record's components, determining its canonical
///                    constructor parameters and accessors
/// @param interfaces the interfaces the record implements
/// @param members the members of the record body
public record RecordDecl(
        ClassDesc desc,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<TypeParam> typeParams,
        List<RecordComponent> components,
        List<ClassOrInterfaceTypeRef> interfaces,
        List<RecordMember> members)
        implements TypeDecl {
    public RecordDecl {
        modifiers = ModifierValidation.requireValidTopLevel(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.STATIC),
                "record");
        annotations = List.copyOf(annotations);
        typeParams = List.copyOf(typeParams);
        components = List.copyOf(components);
        interfaces = List.copyOf(interfaces);
        members = List.copyOf(members);
    }
}
