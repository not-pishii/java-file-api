package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A reference to a generic type applied to type arguments, e.g. `List<String>`.
///
/// @param raw the generic type's raw class or interface
/// @param args the type arguments applied to `raw`; defensively copied into an
///             unmodifiable list
/// @param annotations the type-use annotations on this reference (JLS 9.7.4);
///                     defensively copied into an unmodifiable list
public record ParameterizedTypeRef(ClassDesc raw, List<TypeArg> args, List<AnnotationUse> annotations)
        implements ClassOrInterfaceTypeRef {
    public ParameterizedTypeRef {
        args = List.copyOf(args);
        annotations = List.copyOf(annotations);
    }

    /// Creates a reference with no type-use annotations.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param args the type arguments applied to `raw`; defensively copied into an
    ///             unmodifiable list
    public ParameterizedTypeRef(ClassDesc raw, List<TypeArg> args) {
        this(raw, args, List.of());
    }
}
