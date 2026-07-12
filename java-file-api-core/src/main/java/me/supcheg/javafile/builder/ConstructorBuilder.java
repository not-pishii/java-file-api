package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a [ConstructorDecl].
///
/// Instances are created by [ClassBuilder#withConstructor(Consumer)] and are
/// not meant to be instantiated directly. Enum constructors use the
/// dedicated [EnumConstructorBuilder] instead, since they are always
/// implicitly private and never carry modifiers. If
/// [#withModifiers(Modifier...)] is never called, the built constructor
/// defaults to the `public` modifier.
///
/// Instances are not thread-safe.
public final class ConstructorBuilder {

    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final Set<Modifier> modifiers = new LinkedHashSet<>();
    private final List<Param> params = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> throwsTypes = new ArrayList<>();
    private CodeBody body = CodeBody.EMPTY;

    ConstructorBuilder() {}

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public ConstructorBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public ConstructorBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public ConstructorBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the constructor declaration.
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public ConstructorBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Adds a parameter to the constructor's parameter list.
    ///
    /// @param name the parameter name
    /// @param type the declared parameter type
    /// @return this builder
    public ConstructorBuilder withParam(String name, TypeRef type) {
        params.add(new Param(name, type));
        return this;
    }

    /// Adds a pre-built parameter to the constructor's parameter list.
    ///
    /// @param param the parameter to add
    /// @return this builder
    public ConstructorBuilder withParam(Param param) {
        params.add(param);
        return this;
    }

    /// Adds types to the constructor's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public ConstructorBuilder withThrows(ClassDesc... types) {
        for (ClassDesc type : types) {
            throwsTypes.add(Types.of(type));
        }
        return this;
    }

    /// Adds types, possibly parameterized or type variables, to the
    /// constructor's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public ConstructorBuilder withThrows(ClassOrInterfaceTypeRef... types) {
        throwsTypes.addAll(List.of(types));
        return this;
    }

    /// Sets the constructor body.
    ///
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public ConstructorBuilder withBody(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.body = cb.build();
        return this;
    }

    ConstructorDecl build() {
        Set<Modifier> effectiveModifiers = modifiers.isEmpty() ? Set.of(Modifier.PUBLIC) : Set.copyOf(modifiers);
        return new ConstructorDecl(
                List.copyOf(annotations), effectiveModifiers, List.copyOf(params), body, List.copyOf(throwsTypes));
    }
}
