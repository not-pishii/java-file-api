package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.model.AnnotationElementDecl;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a top-level annotation type declaration.
///
/// Starts with the `public` modifier already applied. Builder methods
/// return `this` for chaining;
/// [#build()] snapshots the accumulated state into an immutable
/// [AnnotationTypeDecl], so a builder may be reused after building.
///
/// Implements `Consumer<AnnotationElementDecl>` so that transforms and other
/// producers can feed pre-built elements directly via
/// [#accept(AnnotationElementDecl)].
///
/// Instances are not thread-safe.
public final class AnnotationTypeBuilder implements Consumer<AnnotationElementDecl> {

    private final ClassDesc desc;
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC));
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final List<AnnotationElementDecl> elements = new ArrayList<>();

    /// Creates a builder for an annotation type with the given descriptor.
    ///
    /// @param desc the annotation type to declare; its package and simple name determine the file location
    public AnnotationTypeBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public AnnotationTypeBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public AnnotationTypeBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public AnnotationTypeBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the declaration.
    ///
    /// Modifiers accumulate across calls and duplicates are ignored; the initial
    /// `public` modifier cannot be removed by this method — see [#withExactModifiers(Set)].
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public AnnotationTypeBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Replaces the accumulated modifiers with exactly the given set, bypassing
    /// the initial `public` seed that [#withModifiers(Modifier...)] can only add
    /// to. Intended for producers — like
    /// [me.supcheg.javafile.transform.Transforms] — that must reproduce an
    /// existing declaration's modifiers exactly; ordinary hand-authored
    /// declarations should use [#withModifiers(Modifier...)]. A later
    /// [#withModifiers(Modifier...)] call still adds to the set installed here.
    ///
    /// @param mods the exact modifier set to use
    /// @return this builder
    public AnnotationTypeBuilder withExactModifiers(Set<Modifier> mods) {
        modifiers.clear();
        modifiers.addAll(mods);
        return this;
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

    /// Appends the given pre-built element to the annotation type body.
    ///
    /// @param element the element to append
    @Override
    public void accept(AnnotationElementDecl element) {
        elements.add(element);
    }

    /// Snapshots the accumulated state into an immutable [AnnotationTypeDecl].
    ///
    /// @return the finished annotation type declaration
    public AnnotationTypeDecl build() {
        return new AnnotationTypeDecl(desc, List.copyOf(annotations), Set.copyOf(modifiers), List.copyOf(elements));
    }
}
