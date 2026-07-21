package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.model.AnnotationElementDecl;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A mutable builder for a top-level annotation type declaration.
///
/// Instances are not thread-safe.
public final class AnnotationTypeBuilder {

    private final ClassDesc desc;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final List<AnnotationElementDecl> elements = new ArrayList<>();

    /// Creates a builder for an annotation type with the given descriptor.
    ///
    /// @param desc the annotation type to declare; its package and simple name determine the file location
    public AnnotationTypeBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds an element with no default value.
    ///
    /// @param name the element name
    /// @param type the element's declared type
    /// @return this builder
    public AnnotationTypeBuilder withElement(String name, TypeRef type) {
        elements.add(new AnnotationElementDecl(name, type, Optional.empty()));
        return this;
    }

    /// Adds an element with a default value.
    ///
    /// @param name the element name
    /// @param type the element's declared type
    /// @param defaultValue the element's default value
    /// @return this builder
    public AnnotationTypeBuilder withElement(String name, TypeRef type, AnnotationValue defaultValue) {
        elements.add(new AnnotationElementDecl(name, type, Optional.of(defaultValue)));
        return this;
    }

    /// Snapshots the accumulated state into an immutable [AnnotationTypeDecl].
    ///
    /// @return the finished annotation type declaration
    public AnnotationTypeDecl build() {
        return new AnnotationTypeDecl(desc, List.copyOf(annotations), Set.of(Modifier.PUBLIC), List.copyOf(elements));
    }
}
