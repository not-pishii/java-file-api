package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// Builds an interface.
///
/// Obtained from [me.supcheg.javafile.JavaFile#interface_(ClassDesc,Consumer)]
/// or a `withNestedInterface` method. The interface is `public` by default and
/// becomes `sealed` once [#withPermits(ClassDesc...)] is called.
///
/// ```java
/// JavaFile.interface_(ClassDesc.of("com.example", "Shape"), ib -> ib
///         .withAbstractMethod("area", Types.DOUBLE));
/// ```
///
/// Instances are not thread-safe.
public final class InterfaceBuilder implements Consumer<InterfaceMember> {

    private final ClassDesc desc;
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC));
    private final List<AnnotationUse> annotations = new ArrayList<>();
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

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public InterfaceBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public InterfaceBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public InterfaceBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the declaration.
    ///
    /// Adds to the modifiers already set, which start as `public`. To remove
    /// `public`, use [#withExactModifiers(Set)].
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public InterfaceBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Replaces all modifiers, including the default `public`.
    ///
    /// Use it to declare a package-private type or member, e.g.
    /// `withExactModifiers(Set.of(Modifier.FINAL))`; [#withModifiers(Modifier...)]
    /// can only add modifiers.
    ///
    /// @param mods the exact modifier set to use
    /// @return this builder
    public InterfaceBuilder withExactModifiers(Set<Modifier> mods) {
        modifiers.clear();
        modifiers.addAll(mods);
        return this;
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

    /// Adds a pre-built type parameter to the interface declaration.
    ///
    /// @param typeParam the type parameter to add
    /// @return this builder
    public InterfaceBuilder withTypeParam(TypeParam typeParam) {
        typeParams.add(typeParam);
        return this;
    }

    /// Adds an interface to the declaration's `extends` clause.
    ///
    /// @param iface the extended interface
    /// @return this builder
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
    public InterfaceBuilder withPermits(ClassDesc... types) {
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

    /// Adds a `void` abstract method with no `throws` clause.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @return this builder
    public InterfaceBuilder withVoidAbstractMethod(String name, me.supcheg.javafile.model.Param... params) {
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

    /// Adds an abstract method with a return type, populated via an
    /// [AbstractMethodBuilder].
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public InterfaceBuilder withAbstractMethod(String name, TypeRef returnType, Consumer<AbstractMethodBuilder> spec) {
        AbstractMethodBuilder amb = new AbstractMethodBuilder(name, Optional.of(returnType));
        spec.accept(amb);
        members.add(amb.build());
        return this;
    }

    /// Adds a `void` abstract method, populated via an [AbstractMethodBuilder].
    ///
    /// @param name the method name
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public InterfaceBuilder withVoidAbstractMethod(String name, Consumer<AbstractMethodBuilder> spec) {
        AbstractMethodBuilder amb = new AbstractMethodBuilder(name, Optional.empty());
        spec.accept(amb);
        members.add(amb.build());
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
                mb.name(),
                mb.returnType(),
                mb.annotations(),
                mb.typeParams(),
                mb.params(),
                mb.body(),
                mb.throwsTypes()));
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
                mb.name(),
                mb.returnType(),
                mb.annotations(),
                mb.typeParams(),
                mb.params(),
                mb.body(),
                mb.throwsTypes()));
        return this;
    }

    /// Adds a constant field, implicitly `public static final`.
    ///
    /// @param name the constant name
    /// @param type the declared constant type
    /// @param initializer the initializer expression
    /// @return this builder
    public InterfaceBuilder withConstant(String name, TypeRef type, Expr initializer) {
        members.add(new ConstantDecl(name, type, List.of(), initializer));
        return this;
    }

    /// Adds a constant field, implicitly `public static final`, populated via a
    /// [ConstantBuilder].
    ///
    /// @param name the constant name
    /// @param type the declared constant type
    /// @param spec receives the builder to populate the constant
    /// @return this builder
    /// @throws IllegalStateException if `spec` never sets an initializer
    public InterfaceBuilder withConstant(String name, TypeRef type, Consumer<ConstantBuilder> spec) {
        ConstantBuilder cb = new ConstantBuilder(name, type);
        spec.accept(cb);
        members.add(cb.build());
        return this;
    }

    /// Adds a nested class declaration.
    ///
    /// @param desc the nested class to declare
    /// @param spec receives the builder to populate the class declaration
    /// @return this builder
    public InterfaceBuilder withNestedClass(ClassDesc desc, Consumer<ClassBuilder> spec) {
        ClassBuilder cb = new ClassBuilder(desc);
        spec.accept(cb);
        members.add(cb.build());
        return this;
    }

    /// Adds a nested interface declaration.
    ///
    /// @param desc the nested interface to declare
    /// @param spec receives the builder to populate the interface declaration
    /// @return this builder
    public InterfaceBuilder withNestedInterface(ClassDesc desc, Consumer<InterfaceBuilder> spec) {
        InterfaceBuilder ib = new InterfaceBuilder(desc);
        spec.accept(ib);
        members.add(ib.build());
        return this;
    }

    /// Adds a nested record declaration.
    ///
    /// @param desc the nested record to declare
    /// @param spec receives the builder to populate the record declaration
    /// @return this builder
    public InterfaceBuilder withNestedRecord(ClassDesc desc, Consumer<RecordBuilder> spec) {
        RecordBuilder rb = new RecordBuilder(desc);
        spec.accept(rb);
        members.add(rb.build());
        return this;
    }

    /// Adds a nested enum declaration.
    ///
    /// @param desc the nested enum to declare
    /// @param spec receives the builder to populate the enum declaration
    /// @return this builder
    public InterfaceBuilder withNestedEnum(ClassDesc desc, Consumer<EnumBuilder> spec) {
        EnumBuilder eb = new EnumBuilder(desc);
        spec.accept(eb);
        members.add(eb.build());
        return this;
    }

    /// Adds a nested annotation type declaration.
    ///
    /// @param desc the nested annotation type to declare
    /// @param spec receives the builder to populate the annotation type declaration
    /// @return this builder
    public InterfaceBuilder withNestedAnnotationType(ClassDesc desc, Consumer<AnnotationTypeBuilder> spec) {
        AnnotationTypeBuilder ab = new AnnotationTypeBuilder(desc);
        spec.accept(ab);
        members.add(ab.build());
        return this;
    }

    /// Adds a ready-made member, e.g. one passed to a transform.
    ///
    /// @param member the member to append
    @Override
    public void accept(InterfaceMember member) {
        members.add(member);
    }

    /// Returns the declaration built so far.
    ///
    /// @return the finished interface declaration
    public InterfaceDecl build() {
        return new InterfaceDecl(
                desc,
                List.copyOf(annotations),
                Set.copyOf(modifiers),
                List.copyOf(typeParams),
                List.copyOf(extendsInterfaces),
                List.copyOf(permittedSubtypes),
                List.copyOf(members));
    }
}
