package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a top-level interface declaration.
///
/// Always declares with the `public` modifier; there is no way to add
/// further modifiers. Builder methods return `this` for chaining;
/// [#build()] snapshots the accumulated state into an immutable
/// [InterfaceDecl], so a builder may be reused after building.
///
/// Implements `Consumer<InterfaceMember>` so that transforms and other
/// producers can feed pre-built members directly via
/// [#accept(InterfaceMember)].
///
/// Instances are not thread-safe.
public final class InterfaceBuilder implements Consumer<InterfaceMember> {

    private final ClassDesc desc;
    private final List<TypeParam> typeParams = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> extendsInterfaces = new ArrayList<>();
    private final List<ClassDesc> permittedSubtypes = new ArrayList<>();
    private final List<InterfaceMember> members = new ArrayList<>();

    /// Creates a builder for an interface with the given descriptor.
    ///
    /// @param desc the interface to declare; its package and simple name determine the file location
    public InterfaceBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds a type parameter to the interface declaration, e.g. `T` or
    /// `T extends Comparable<T>`.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, or none for an unbounded parameter
    /// @return this builder
    public InterfaceBuilder withTypeParam(String name, ClassOrInterfaceTypeRef... bounds) {
        typeParams.add(new TypeParam(name, List.of(bounds)));
        return this;
    }

    public InterfaceBuilder withExtends(ClassDesc iface) {
        return withExtends(Types.of(iface));
    }

    /// Adds an interface, possibly parameterized, to the declaration's `extends` clause.
    ///
    /// @param iface the extended interface
    /// @return this builder
    public InterfaceBuilder withExtends(ClassOrInterfaceTypeRef iface) {
        extendsInterfaces.add(iface);
        return this;
    }

    /// Adds types to the interface's `permits` clause, for a `sealed` interface.
    ///
    /// @param types the permitted subtypes
    /// @return this builder
    public InterfaceBuilder permits(ClassDesc... types) {
        permittedSubtypes.addAll(List.of(types));
        return this;
    }

    /// Adds an abstract method with a return type and no `throws` clause.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param params the method's parameters, in order
    /// @return this builder
    public InterfaceBuilder withAbstractMethod(
            String name, TypeRef returnType, me.supcheg.javafile.model.Param... params) {
        return withAbstractMethod(name, returnType, params, new ClassDesc[0]);
    }

    /// Adds an abstract method with a return type and a `throws` clause.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param params the method's parameters, in order
    /// @param throwsTypes the thrown exception types
    /// @return this builder
    public InterfaceBuilder withAbstractMethod(
            String name, TypeRef returnType, me.supcheg.javafile.model.Param[] params, ClassDesc... throwsTypes) {
        members.add(new AbstractMethodDecl(
                name,
                Optional.of(returnType),
                List.of(),
                List.of(params),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                normalizeThrows(throwsTypes)));
        return this;
    }

    /// Adds a `void` abstract method with no `throws` clause.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @return this builder
    public InterfaceBuilder withVoidAbstractMethod(String name, me.supcheg.javafile.model.Param... params) {
        return withVoidAbstractMethod(name, params, new ClassDesc[0]);
    }

    /// Adds a `void` abstract method with a `throws` clause.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @param throwsTypes the thrown exception types
    /// @return this builder
    public InterfaceBuilder withVoidAbstractMethod(
            String name, me.supcheg.javafile.model.Param[] params, ClassDesc... throwsTypes) {
        members.add(new AbstractMethodDecl(
                name,
                Optional.empty(),
                List.of(),
                List.of(params),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                normalizeThrows(throwsTypes)));
        return this;
    }

    /// Adds a `default` method.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public InterfaceBuilder withDefaultMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        members.add(new DefaultMethodDecl(
                mb.name(), mb.returnType(), mb.typeParams(), mb.params(), mb.body(), mb.throwsTypes()));
        return this;
    }

    /// Adds a `static` method.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public InterfaceBuilder withStaticMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        members.add(new StaticMethodDecl(
                mb.name(), mb.returnType(), mb.typeParams(), mb.params(), mb.body(), mb.throwsTypes()));
        return this;
    }

    /// Adds a constant field, implicitly `public static final`.
    ///
    /// @param name the constant name
    /// @param type the declared constant type
    /// @param initializer the initializer expression
    /// @return this builder
    public InterfaceBuilder withConstant(String name, TypeRef type, Expr initializer) {
        members.add(new ConstantDecl(name, type, initializer));
        return this;
    }

    /// Appends the given pre-built member to the interface body.
    ///
    /// @param member the member to append
    @Override
    public void accept(InterfaceMember member) {
        members.add(member);
    }

    /// Snapshots the accumulated state into an immutable [InterfaceDecl].
    ///
    /// @return the finished interface declaration
    public InterfaceDecl build() {
        return new InterfaceDecl(
                desc,
                Set.of(Modifier.PUBLIC),
                List.copyOf(typeParams),
                List.copyOf(extendsInterfaces),
                List.copyOf(permittedSubtypes),
                List.copyOf(members));
    }

    private static List<ClassOrInterfaceTypeRef> normalizeThrows(ClassDesc[] types) {
        List<ClassOrInterfaceTypeRef> normalized = new ArrayList<>(types.length);
        for (ClassDesc type : types) {
            normalized.add(Types.of(type));
        }
        return normalized;
    }
}
