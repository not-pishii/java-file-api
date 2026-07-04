package me.supcheg.javafile.model;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A `class` declaration.
///
/// The class is rendered as `sealed` when [#permits] is non-empty. Modifiers,
/// interfaces, permitted subtypes, and members are defensively copied into
/// unmodifiable collections.
///
/// @param desc the class's name and package
/// @param modifiers the modifiers on the class declaration
/// @param superclass the superclass, if the class extends one other than
///                    `Object`
/// @param interfaces the interfaces the class implements
/// @param permits the subtypes named in a `permits` clause; a non-empty list
///                causes the class to render as `sealed`
/// @param members the members of the class body
public record ClassDecl(
        ClassDesc desc,
        Set<Modifier> modifiers,
        Optional<ClassDesc> superclass,
        List<ClassDesc> interfaces,
        List<ClassDesc> permits,
        List<ClassMember> members)
        implements TypeDecl {
    public ClassDecl {
        modifiers = Set.copyOf(modifiers);
        interfaces = List.copyOf(interfaces);
        permits = List.copyOf(permits);
        members = List.copyOf(members);
    }
}
