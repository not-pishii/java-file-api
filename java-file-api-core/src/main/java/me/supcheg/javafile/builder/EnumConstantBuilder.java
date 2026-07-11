package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.type.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for an [EnumConstant]'s constructor arguments and
/// constant-specific class body.
///
/// Instances are created by
/// [EnumBuilder#withConstant(String,Consumer)] and are not meant to be
/// instantiated directly.
///
/// Instances are not thread-safe.
public final class EnumConstantBuilder {

    private final List<Expr> args = new ArrayList<>();
    private final List<EnumConstantMember> body = new ArrayList<>();

    EnumConstantBuilder() {}

    /// Sets the arguments passed to the enum's constructor for this constant.
    ///
    /// @param args the constructor arguments, in order
    /// @return this builder
    public EnumConstantBuilder withArgs(Expr... args) {
        this.args.addAll(List.of(args));
        return this;
    }

    /// Adds a method to this constant's constant-specific class body.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumConstantBuilder withMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        body.add(mb.build());
        return this;
    }

    /// Adds a `void` method to this constant's constant-specific class body.
    ///
    /// @param name the method name
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumConstantBuilder withVoidMethod(String name, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.empty());
        spec.accept(mb);
        body.add(mb.build());
        return this;
    }

    EnumConstant build(String name) {
        return new EnumConstant(name, List.copyOf(args), List.copyOf(body));
    }
}
