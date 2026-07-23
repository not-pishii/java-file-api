package me.supcheg.javafile.type;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;

import java.util.List;

/// A reference to a type variable by its declared name, e.g. `T`.
///
/// The name is a local symbol introduced by an enclosing declaration's type
/// parameter; it is rendered verbatim and never imported.
///
/// @param name the type variable's name
/// @param annotations the type-use annotations on this reference (JLS 9.7.4);
///                     defensively copied into an unmodifiable list
public record TypeVarRef(String name, List<AnnotationUse> annotations) implements ClassOrInterfaceTypeRef {
    public TypeVarRef {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
    }

    /// Creates a reference with no type-use annotations.
    ///
    /// @param name the type variable's name
    public TypeVarRef(String name) {
        this(name, List.of());
    }
}
