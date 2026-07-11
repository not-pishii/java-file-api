package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.model.MethodDecl;
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

/// A mutable builder for a method declaration.
///
/// Instances are created by the `with*Method` methods of [ClassBuilder],
/// [InterfaceBuilder], [RecordBuilder], and [EnumBuilder], and are not meant
/// to be instantiated directly. Depending on which of those callers builds
/// the final declaration, the accumulated state maps onto a [MethodDecl],
/// [me.supcheg.javafile.model.DefaultMethodDecl], or
/// [me.supcheg.javafile.model.StaticMethodDecl]; modifiers added via
/// [#withModifiers(Modifier...)] apply only where the target declaration has
/// a modifier set. If [#withModifiers(Modifier...)] is never called, a
/// [MethodDecl] defaults to the `public` modifier.
///
/// Instances are not thread-safe.
public final class MethodBuilder {

    private final String name;
    private final Optional<TypeRef> returnType;
    private final Set<Modifier> modifiers = new LinkedHashSet<>();
    private final List<TypeParam> typeParams = new ArrayList<>();
    private final List<Param> params = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> throwsTypes = new ArrayList<>();
    private CodeBody body = CodeBody.EMPTY;

    MethodBuilder(String name, Optional<TypeRef> returnType) {
        this.name = name;
        this.returnType = returnType;
    }

    /// Adds the given modifiers to the method declaration.
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public MethodBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Adds a type parameter to the method declaration, e.g. `<T>`.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, or none for an unbounded parameter
    /// @return this builder
    public MethodBuilder withTypeParam(String name, ClassOrInterfaceTypeRef... bounds) {
        typeParams.add(new TypeParam(name, List.of(bounds)));
        return this;
    }

    /// Adds a parameter to the method's parameter list.
    ///
    /// @param name the parameter name
    /// @param type the declared parameter type
    /// @return this builder
    public MethodBuilder withParam(String name, TypeRef type) {
        params.add(new Param(name, type));
        return this;
    }

    /// Adds types to the method's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public MethodBuilder withThrows(ClassDesc... types) {
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
    public MethodBuilder withThrows(ClassOrInterfaceTypeRef... types) {
        throwsTypes.addAll(List.of(types));
        return this;
    }

    /// Sets the method body.
    ///
    /// @param spec receives the builder to populate the method body
    /// @return this builder
    public MethodBuilder withBody(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.body = cb.build();
        return this;
    }

    String name() {
        return name;
    }

    Optional<TypeRef> returnType() {
        return returnType;
    }

    Set<Modifier> modifiers() {
        return modifiers.isEmpty() ? Set.of(Modifier.PUBLIC) : Set.copyOf(modifiers);
    }

    List<TypeParam> typeParams() {
        return List.copyOf(typeParams);
    }

    List<Param> params() {
        return List.copyOf(params);
    }

    CodeBody body() {
        return body;
    }

    List<ClassOrInterfaceTypeRef> throwsTypes() {
        return List.copyOf(throwsTypes);
    }

    MethodDecl build() {
        return new MethodDecl(
                name,
                returnType,
                modifiers(),
                List.copyOf(typeParams),
                List.copyOf(params),
                body,
                List.copyOf(throwsTypes));
    }
}
