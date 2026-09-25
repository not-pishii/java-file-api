package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;
import java.util.List;

/// An annotation applied to a declaration, e.g. `@Deprecated(since = "2.0")`.
///
/// Usually built with [AnnotationBuilder]. With no members it renders as
/// `@Deprecated`; with a single member named `value` it renders as
/// `@Name(value)`.
///
/// @param type the annotation type
/// @param members the member assignments, in order
public record AnnotationUse(ClassDesc type, List<AnnotationMember> members) {
    public AnnotationUse {
        members = List.copyOf(members);
    }
}
