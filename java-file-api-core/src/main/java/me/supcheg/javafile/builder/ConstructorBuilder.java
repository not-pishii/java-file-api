package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a [ConstructorDecl].
///
/// Instances are created by [ClassBuilder#withConstructor(Consumer)] and
/// [EnumBuilder#withConstructor(Consumer)] and are not meant to be
/// instantiated directly. If [#withModifiers(Modifier...)] is never called,
/// the built constructor defaults to the `public` modifier.
///
/// Instances are not thread-safe.
public final class ConstructorBuilder {

    private final Set<Modifier> modifiers = new LinkedHashSet<>();
    private final List<Param> params = new ArrayList<>();
    private final List<ClassDesc> throwsTypes = new ArrayList<>();
    private CodeBody body = CodeBody.EMPTY;

    ConstructorBuilder() {}

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

    /// Adds types to the constructor's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public ConstructorBuilder withThrows(ClassDesc... types) {
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
        return new ConstructorDecl(effectiveModifiers, List.copyOf(params), body, List.copyOf(throwsTypes));
    }
}
