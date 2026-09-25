package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.InitializerBlock;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// Builds a class.
///
/// Obtained from [me.supcheg.javafile.JavaFile#class_(ClassDesc,Consumer)] or
/// a `withNestedClass` method. The class is `public` by default; use
/// [#withModifiers(Modifier...)] to add modifiers such as `final` or
/// [#withExactModifiers(java.util.Set)] to replace them.
///
/// ```java
/// JavaFile.class_(ClassDesc.of("com.example", "Counter"), cb -> cb
///         .withModifiers(Modifier.FINAL)
///         .withField("count", Types.INT, fb -> fb.withModifiers(Modifier.PRIVATE))
///         .withVoidMethod("increment", mb -> mb
///                 .withBody(b -> b.exprStatement(postIncrement(field("count"))))));
/// ```
///
/// Instances are not thread-safe.
public final class ClassBuilder implements Consumer<ClassMember> {

    private final ClassDesc desc;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC));
    private final List<TypeParam> typeParams = new ArrayList<>();
    private @Nullable ClassOrInterfaceTypeRef superclass;
    private final List<ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private final List<ClassDesc> permits = new ArrayList<>();
    private final List<ClassMember> members = new ArrayList<>();

    /// Creates a builder for a class with the given descriptor.
    ///
    /// @param desc the class to declare; its package and simple name determine the file location
    public ClassBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public ClassBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public ClassBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public ClassBuilder withAnnotation(AnnotationUse annotation) {
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
    public ClassBuilder withModifiers(Modifier... mods) {
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
    public ClassBuilder withExactModifiers(Set<Modifier> mods) {
        modifiers.clear();
        modifiers.addAll(mods);
        return this;
    }

    /// Adds a type parameter to the class declaration, e.g. `T` or
    /// `T extends Comparable<T>`.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, or none for an unbounded parameter
    /// @return this builder
    public ClassBuilder withTypeParam(String name, ClassOrInterfaceTypeRef... bounds) {
        typeParams.add(new TypeParam(name, List.of(bounds)));
        return this;
    }

    /// Adds a pre-built type parameter to the class declaration.
    ///
    /// @param typeParam the type parameter to add
    /// @return this builder
    public ClassBuilder withTypeParam(TypeParam typeParam) {
        typeParams.add(typeParam);
        return this;
    }

    /// Sets the class's `extends` superclass.
    ///
    /// @param superclass the superclass to extend
    /// @return this builder
    public ClassBuilder withSuperclass(ClassDesc superclass) {
        return withSuperclass(Types.of(superclass));
    }

    /// Sets the class's `extends` superclass, possibly parameterized.
    ///
    /// @param superclass the superclass to extend
    /// @return this builder
    public ClassBuilder withSuperclass(ClassOrInterfaceTypeRef superclass) {
        this.superclass = superclass;
        return this;
    }

    /// Adds an interface to the class's `implements` clause.
    ///
    /// @param iface the implemented interface
    /// @return this builder
    public ClassBuilder withInterface(ClassDesc iface) {
        return withInterface(Types.of(iface));
    }

    /// Adds an interface, possibly parameterized, to the class's `implements` clause.
    ///
    /// @param iface the implemented interface
    /// @return this builder
    public ClassBuilder withInterface(ClassOrInterfaceTypeRef iface) {
        interfaces.add(iface);
        return this;
    }

    /// Adds types to the class's `permits` clause, for a `sealed` class.
    ///
    /// @param types the permitted subtypes
    /// @return this builder
    public ClassBuilder withPermits(ClassDesc... types) {
        permits.addAll(List.of(types));
        return this;
    }

    /// Adds an abstract method with a return type.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param params the method's parameters, in order
    /// @return this builder
    public ClassBuilder withAbstractMethod(String name, TypeRef returnType, Param... params) {
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

    /// Adds a `void` abstract method.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @return this builder
    public ClassBuilder withVoidAbstractMethod(String name, Param... params) {
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
    public ClassBuilder withAbstractMethod(String name, TypeRef returnType, Consumer<AbstractMethodBuilder> spec) {
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
    public ClassBuilder withVoidAbstractMethod(String name, Consumer<AbstractMethodBuilder> spec) {
        AbstractMethodBuilder amb = new AbstractMethodBuilder(name, Optional.empty());
        spec.accept(amb);
        members.add(amb.build());
        return this;
    }

    /// Adds a `public` field with no initializer.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @return this builder
    public ClassBuilder withField(String name, TypeRef type) {
        return withField(name, type, fb -> {});
    }

    /// Adds a `public` field with an initializer.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param initializer the initializer expression
    /// @return this builder
    public ClassBuilder withField(String name, TypeRef type, Expr initializer) {
        return withField(name, type, fb -> fb.withInitializer(initializer));
    }

    /// Adds a field.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param spec receives the builder to populate the field
    /// @return this builder
    public ClassBuilder withField(String name, TypeRef type, Consumer<FieldBuilder> spec) {
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
    public ClassBuilder withMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        members.add(mb.build());
        return this;
    }

    /// Adds a `void` method.
    ///
    /// @param name the method name
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public ClassBuilder withVoidMethod(String name, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.empty());
        spec.accept(mb);
        members.add(mb.build());
        return this;
    }

    /// Adds a constructor.
    ///
    /// @param spec receives the builder to populate the constructor
    /// @return this builder
    public ClassBuilder withConstructor(Consumer<ConstructorBuilder> spec) {
        ConstructorBuilder cb = new ConstructorBuilder();
        spec.accept(cb);
        members.add(cb.build());
        return this;
    }

    /// Adds an instance initializer block, `{ ... }`.
    ///
    /// @param spec receives the builder to populate the block's body
    /// @return this builder
    public ClassBuilder withInitializerBlock(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        members.add(new InitializerBlock(false, cb.build()));
        return this;
    }

    /// Adds a static initializer block, `static { ... }`.
    ///
    /// @param spec receives the builder to populate the block's body
    /// @return this builder
    public ClassBuilder withStaticInitializerBlock(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        members.add(new InitializerBlock(true, cb.build()));
        return this;
    }

    /// Adds a nested class declaration.
    ///
    /// @param desc the nested class to declare
    /// @param spec receives the builder to populate the class declaration
    /// @return this builder
    public ClassBuilder withNestedClass(ClassDesc desc, Consumer<ClassBuilder> spec) {
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
    public ClassBuilder withNestedInterface(ClassDesc desc, Consumer<InterfaceBuilder> spec) {
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
    public ClassBuilder withNestedRecord(ClassDesc desc, Consumer<RecordBuilder> spec) {
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
    public ClassBuilder withNestedEnum(ClassDesc desc, Consumer<EnumBuilder> spec) {
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
    public ClassBuilder withNestedAnnotationType(ClassDesc desc, Consumer<AnnotationTypeBuilder> spec) {
        AnnotationTypeBuilder ab = new AnnotationTypeBuilder(desc);
        spec.accept(ab);
        members.add(ab.build());
        return this;
    }

    /// Adds a ready-made member, e.g. one passed to a transform.
    ///
    /// @param member the member to append
    @Override
    public void accept(ClassMember member) {
        members.add(member);
    }

    /// Returns the declaration built so far.
    ///
    /// @return the finished class declaration
    public ClassDecl build() {
        return new ClassDecl(
                desc,
                List.copyOf(annotations),
                Set.copyOf(modifiers),
                List.copyOf(typeParams),
                Optional.ofNullable(superclass),
                List.copyOf(interfaces),
                List.copyOf(permits),
                List.copyOf(members));
    }
}
