package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A use of an annotation on a declaration, e.g. `@ContractMeta(...)`.
///
/// Members are defensively copied into an unmodifiable list; an empty list
/// renders as a marker annotation without parentheses.
///
/// @param type the annotation type
/// @param members the member assignments, in order
public record AnnotationUse(ClassDesc type, List<AnnotationMember> members) {
    public AnnotationUse {
        members = List.copyOf(members);
    }
}
