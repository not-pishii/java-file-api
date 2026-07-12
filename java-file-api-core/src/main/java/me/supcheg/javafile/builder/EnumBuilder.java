package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a top-level enum declaration.
///
/// Always declares with the `public` modifier; there is no way to add
/// further modifiers. Builder methods return `this` for chaining;
/// [#build()] snapshots the accumulated state into an immutable
/// [EnumDecl], so a builder may be reused after building.
///
/// Implements `Consumer<EnumMember>` so that transforms and other producers
/// can feed pre-built members directly via [#accept(EnumMember)].
///
/// Instances are not thread-safe.
public final class EnumBuilder implements Consumer<EnumMember> {

    private final ClassDesc desc;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final List<EnumConstant> constants = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private final List<EnumMember> members = new ArrayList<>();

    /// Creates a builder for an enum with the given descriptor.
    ///
    /// @param desc the enum to declare; its package and simple name determine the file location
    public EnumBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public EnumBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public EnumBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public EnumBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds a constant with no constructor arguments and no constant-specific body.
    ///
    /// @param name the constant name
    /// @return this builder
    public EnumBuilder withConstant(String name) {
        return withConstant(name, spec -> {});
    }

    /// Adds a constant with constructor arguments and no constant-specific body.
    ///
    /// @param name the constant name
    /// @param args the constructor arguments, in order
    /// @return this builder
    public EnumBuilder withConstant(String name, Expr... args) {
        return withConstant(name, spec -> spec.withArgs(args));
    }

    /// Adds a constant, populated via an [EnumConstantBuilder].
    ///
    /// @param name the constant name
    /// @param spec receives the builder to populate the constant's arguments and body
    /// @return this builder
    public EnumBuilder withConstant(String name, Consumer<EnumConstantBuilder> spec) {
        EnumConstantBuilder ecb = new EnumConstantBuilder();
        spec.accept(ecb);
        constants.add(ecb.build(name));
        return this;
    }

    /// Adds a pre-built constant.
    ///
    /// @param constant the constant to add
    /// @return this builder
    public EnumBuilder withConstant(EnumConstant constant) {
        constants.add(constant);
        return this;
    }

    public EnumBuilder withInterface(ClassDesc iface) {
        return withInterface(Types.of(iface));
    }

    /// Adds an interface, possibly parameterized, to the enum's `implements` clause.
    ///
    /// @param iface the implemented interface
    /// @return this builder
    public EnumBuilder withInterface(ClassOrInterfaceTypeRef iface) {
        interfaces.add(iface);
        return this;
    }

    /// Adds a constructor.
    ///
    /// @param spec receives the builder to populate the constructor
    /// @return this builder
    public EnumBuilder withConstructor(Consumer<EnumConstructorBuilder> spec) {
        EnumConstructorBuilder cb = new EnumConstructorBuilder();
        spec.accept(cb);
        members.add(cb.build());
        return this;
    }

    /// Adds a field.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param spec receives the builder to populate the field
    /// @return this builder
    public EnumBuilder withField(String name, TypeRef type, Consumer<FieldBuilder> spec) {
        FieldBuilder fb = new FieldBuilder(name, type);
        spec.accept(fb);
        members.add(fb.build());
        return this;
    }

    /// Adds a method with a return type.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumBuilder withMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        members.add(new MethodDecl(
                mb.name(),
                mb.returnType(),
                mb.annotations(),
                mb.modifiers(),
                mb.typeParams(),
                mb.params(),
                mb.body(),
                mb.throwsTypes()));
        return this;
    }

    /// Adds a `void` method.
    ///
    /// @param name the method name
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumBuilder withVoidMethod(String name, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.empty());
        spec.accept(mb);
        members.add(new MethodDecl(
                mb.name(),
                mb.returnType(),
                mb.annotations(),
                mb.modifiers(),
                mb.typeParams(),
                mb.params(),
                mb.body(),
                mb.throwsTypes()));
        return this;
    }

    /// Adds an abstract method with a return type, implemented per-constant.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param params the method's parameters, in order
    /// @return this builder
    public EnumBuilder withAbstractMethod(String name, TypeRef returnType, Param... params) {
        members.add(new AbstractMethodDecl(
                name,
                Optional.of(returnType),
                List.of(),
                List.of(params),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                List.of()));
        return this;
    }

    /// Adds a `void` abstract method, implemented per-constant.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @return this builder
    public EnumBuilder withVoidAbstractMethod(String name, Param... params) {
        members.add(new AbstractMethodDecl(
                name,
                Optional.empty(),
                List.of(),
                List.of(params),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                List.of()));
        return this;
    }

    /// Appends the given pre-built member to the enum body.
    ///
    /// @param member the member to append
    @Override
    public void accept(EnumMember member) {
        members.add(member);
    }

    /// Snapshots the accumulated state into an immutable [EnumDecl].
    ///
    /// @return the finished enum declaration
    public EnumDecl build() {
        return new EnumDecl(
                desc,
                List.copyOf(annotations),
                Set.of(Modifier.PUBLIC),
                List.copyOf(constants),
                List.copyOf(interfaces),
                List.copyOf(members));
    }
}
