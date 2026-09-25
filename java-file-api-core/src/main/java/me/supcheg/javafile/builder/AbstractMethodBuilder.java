package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// Configures an abstract method, e.g. `public abstract String name();`.
///
/// Obtained from `withAbstractMethod`/`withVoidAbstractMethod` of
/// [ClassBuilder], [InterfaceBuilder], and [EnumBuilder]. The method is
/// `public abstract` unless you change the modifiers.
///
/// Instances are not thread-safe.
public final class AbstractMethodBuilder {

    private final String name;
    private final Optional<TypeRef> returnType;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC, Modifier.ABSTRACT));
    private final List<TypeParam> typeParams = new ArrayList<>();
    private final List<Param> params = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> throwsTypes = new ArrayList<>();

    AbstractMethodBuilder(String name, Optional<TypeRef> returnType) {
        this.name = name;
        this.returnType = returnType;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public AbstractMethodBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public AbstractMethodBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public AbstractMethodBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the method declaration.
    ///
    /// Adds to the modifiers already set, which start as `public`. To remove
    /// `public`, use [#withExactModifiers(Set)].
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public AbstractMethodBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Replaces all modifiers, including the default `public`.
    ///
    /// Use it to declare a package-private type or member, e.g.
    /// `withExactModifiers(Set.of(Modifier.FINAL))`; [#withModifiers(Modifier...)]
    /// can only add modifiers.
    /// `abstract` is always kept.
    ///
    /// @param mods the exact modifier set to use
    /// @return this builder
    public AbstractMethodBuilder withExactModifiers(Set<Modifier> mods) {
        modifiers.clear();
        modifiers.addAll(mods);
        return this;
    }

    /// Adds a type parameter to the method declaration, e.g. `<T>`.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, or none for an unbounded parameter
    /// @return this builder
    public AbstractMethodBuilder withTypeParam(String name, ClassOrInterfaceTypeRef... bounds) {
        typeParams.add(new TypeParam(name, List.of(bounds)));
        return this;
    }

    /// Adds a pre-built type parameter to the method declaration.
    ///
    /// @param typeParam the type parameter to add
    /// @return this builder
    public AbstractMethodBuilder withTypeParam(TypeParam typeParam) {
        typeParams.add(typeParam);
        return this;
    }

    /// Adds a parameter to the method's parameter list.
    ///
    /// @param name the parameter name
    /// @param type the declared parameter type
    /// @return this builder
    public AbstractMethodBuilder withParam(String name, TypeRef type) {
        params.add(new Param(name, type));
        return this;
    }

    /// Adds a pre-built parameter to the method's parameter list.
    ///
    /// @param param the parameter to add
    /// @return this builder
    public AbstractMethodBuilder withParam(Param param) {
        params.add(param);
        return this;
    }

    /// Adds a varargs parameter to the method's parameter list, e.g. `int... values`.
    ///
    /// Must be the last parameter; otherwise building the method throws
    /// `IllegalArgumentException`.
    ///
    /// @param name the parameter name
    /// @param componentType the parameter's component type, not an array type
    /// @return this builder
    public AbstractMethodBuilder withVarargsParam(String name, TypeRef componentType) {
        params.add(new Param(name, componentType, List.of(), true));
        return this;
    }

    /// Adds types to the method's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public AbstractMethodBuilder withThrows(ClassDesc... types) {
        for (ClassDesc type : types) {
            throwsTypes.add(Types.of(type));
        }
        return this;
    }

    /// Adds types, possibly parameterized or type variables, to the method's
    /// `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public AbstractMethodBuilder withThrows(ClassOrInterfaceTypeRef... types) {
        throwsTypes.addAll(List.of(types));
        return this;
    }

    AbstractMethodDecl build() {
        Set<Modifier> builtModifiers = new LinkedHashSet<>(modifiers);
        builtModifiers.add(Modifier.ABSTRACT);
        return new AbstractMethodDecl(
                name,
                returnType,
                List.copyOf(typeParams),
                List.copyOf(params),
                List.copyOf(annotations),
                Set.copyOf(builtModifiers),
                List.copyOf(throwsTypes));
    }
}
