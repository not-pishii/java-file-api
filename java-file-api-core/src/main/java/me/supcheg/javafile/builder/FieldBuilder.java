package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeRef;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

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
    private final Set<Modifier> modifiers = new LinkedHashSet<>();
    private Expr initializer;

    FieldBuilder(String name, TypeRef type) {
        this.name = name;
        this.type = type;
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
        return new FieldDecl(name, type, effectiveModifiers, Optional.ofNullable(initializer));
    }
}
