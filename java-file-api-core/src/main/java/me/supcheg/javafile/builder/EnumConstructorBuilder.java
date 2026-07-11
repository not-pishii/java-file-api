package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.model.EnumConstructorDecl;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// A mutable builder for an [EnumConstructorDecl].
///
/// Instances are created by [EnumBuilder#withConstructor(Consumer)] and are
/// not meant to be instantiated directly. Unlike [ConstructorBuilder], there
/// is no `withModifiers`: enum constructors are always implicitly private.
///
/// Instances are not thread-safe.
public final class EnumConstructorBuilder {

    private final List<Param> params = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> throwsTypes = new ArrayList<>();
    private CodeBody body = CodeBody.EMPTY;

    EnumConstructorBuilder() {}

    /// Adds a parameter to the constructor's parameter list.
    ///
    /// @param name the parameter name
    /// @param type the declared parameter type
    /// @return this builder
    public EnumConstructorBuilder withParam(String name, TypeRef type) {
        params.add(new Param(name, type));
        return this;
    }

    /// Adds types to the constructor's `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return this builder
    public EnumConstructorBuilder withThrows(ClassDesc... types) {
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
    public EnumConstructorBuilder withThrows(ClassOrInterfaceTypeRef... types) {
        throwsTypes.addAll(List.of(types));
        return this;
    }

    /// Sets the constructor body.
    ///
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public EnumConstructorBuilder withBody(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.body = cb.build();
        return this;
    }

    EnumConstructorDecl build() {
        return new EnumConstructorDecl(List.copyOf(params), body, List.copyOf(throwsTypes));
    }
}
