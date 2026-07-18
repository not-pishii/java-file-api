package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a [FieldDecl].
///
/// Instances are created by [ClassBuilder#withField(String,TypeRef,Consumer)]
/// and [EnumBuilder#withField(String,TypeRef,Consumer)] and are not meant to
/// be instantiated directly. If [#withModifiers(Modifier...)] is never
/// called, the built field defaults to the `public` modifier.
///
/// Instances are not thread-safe.
public final class FieldBuilder {

    private final String name;
    private final TypeRef type;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final Set<Modifier> modifiers = new LinkedHashSet<>();
    private @Nullable Expr initializer;

    FieldBuilder(String name, TypeRef type) {
        this.name = name;
        this.type = type;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public FieldBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public FieldBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public FieldBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the field declaration.
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public FieldBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(java.util.List.of(mods));
        return this;
    }

    /// Sets the field's initializer expression.
    ///
    /// @param initializer the initializer expression
    /// @return this builder
    public FieldBuilder withInitializer(Expr initializer) {
        this.initializer = initializer;
        return this;
    }

    FieldDecl build() {
        Set<Modifier> effectiveModifiers = modifiers.isEmpty() ? Set.of(Modifier.PUBLIC) : Set.copyOf(modifiers);
        return new FieldDecl(
                name, type, List.copyOf(annotations), effectiveModifiers, Optional.ofNullable(initializer));
    }
}
