package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A reference to a non-generic class or interface type.
///
/// @param desc the referenced class or interface
/// @param annotations the type-use annotations on this reference (JLS 9.7.4);
///                     defensively copied into an unmodifiable list
public record ClassTypeRef(ClassDesc desc, List<AnnotationUse> annotations) implements ClassOrInterfaceTypeRef {
    public ClassTypeRef {
        annotations = List.copyOf(annotations);
    }

    /// Creates a reference with no type-use annotations.
    ///
    /// @param desc the referenced class or interface
    public ClassTypeRef(ClassDesc desc) {
        this(desc, List.of());
    }
}
