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

/// Builds an annotation type, e.g. `public @interface Route { String value(); }`.
///
/// Obtained from [me.supcheg.javafile.JavaFile#annotationType(ClassDesc,Consumer)]
/// or a `withNestedAnnotationType` method. The type is `public` by default.
///
/// ```java
/// JavaFile.annotationType(ClassDesc.of("com.example", "Route"), ab -> ab
///         .withElement("value", Types.STRING)
///         .withElement("priority", Types.INT, AnnotationValues.literal(0)));
/// ```
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
    /// Adds to the modifiers already set, which start as `public`. To remove
    /// `public`, use [#withExactModifiers(Set)].
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public AnnotationTypeBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Replaces all modifiers, including the default `public`.
    ///
    /// Use it to declare a package-private type or member, e.g.
    /// `withExactModifiers(Set.of(Modifier.FINAL))`; [#withModifiers(Modifier...)]
    /// can only add modifiers.
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

    /// Adds a ready-made element, e.g. one passed to a transform.
    ///
    /// @param element the element to append
    @Override
    public void accept(AnnotationElementDecl element) {
        elements.add(element);
    }

    /// Returns the declaration built so far.
    ///
    /// @return the finished annotation type declaration
    public AnnotationTypeDecl build() {
        return new AnnotationTypeDecl(desc, List.copyOf(annotations), Set.copyOf(modifiers), List.copyOf(elements));
    }
}
