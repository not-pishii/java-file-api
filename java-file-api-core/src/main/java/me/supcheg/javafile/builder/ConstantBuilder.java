package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// A mutable builder for a [ConstantDecl] — an interface constant, implicitly
/// `public static final`.
///
/// Instances are created by
/// [InterfaceBuilder#withConstant(String,TypeRef,Consumer)] and are not meant
/// to be instantiated directly.
///
/// Instances are not thread-safe.
public final class ConstantBuilder {

    private final String name;
    private final TypeRef type;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private @Nullable Expr initializer;

    ConstantBuilder(String name, TypeRef type) {
        this.name = name;
        this.type = type;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public ConstantBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public ConstantBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public ConstantBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Sets the constant's initializer expression.
    ///
    /// @param initializer the initializer expression
    /// @return this builder
    public ConstantBuilder withInitializer(Expr initializer) {
        this.initializer = initializer;
        return this;
    }

    ConstantDecl build() {
        if (initializer == null) {
            throw new IllegalStateException("interface constant " + name + " requires an initializer");
        }
        return new ConstantDecl(name, type, List.copyOf(annotations), initializer);
    }
}
