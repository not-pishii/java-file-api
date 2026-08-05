package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.CanonicalConstructorDecl;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.RecordComponent;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.model.StaticFieldDecl;
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

/// A mutable builder for a top-level record declaration.
///
/// Starts with the `public` modifier already applied. Builder methods
/// return `this` for chaining;
/// [#build()] snapshots the accumulated state into an immutable
/// [RecordDecl], so a builder may be reused after building.
///
/// Implements `Consumer<RecordMember>` so that transforms and other
/// producers can feed pre-built members directly via
/// [#accept(RecordMember)].
///
/// Instances are not thread-safe.
public final class RecordBuilder implements Consumer<RecordMember> {

    private final ClassDesc desc;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC));
    private final List<TypeParam> typeParams = new ArrayList<>();
    private final List<RecordComponent> components = new ArrayList<>();
    private final List<ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private final List<RecordMember> members = new ArrayList<>();

    /// Creates a builder for a record with the given descriptor.
    ///
    /// @param desc the record to declare; its package and simple name determine the file location
    public RecordBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public RecordBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public RecordBuilder withAnnotation(ClassDesc type, Consumer<AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public RecordBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Adds the given modifiers to the declaration.
    ///
    /// Modifiers accumulate across calls and duplicates are ignored; the initial
    /// `public` modifier cannot be removed by this method — see [#withExactModifiers(Set)].
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public RecordBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
        return this;
    }

    /// Replaces the accumulated modifiers with exactly the given set, bypassing
    /// the initial `public` seed that [#withModifiers(Modifier...)] can only add
    /// to. Intended for producers — like
    /// [me.supcheg.javafile.transform.Transforms] — that must reproduce an
    /// existing declaration's modifiers exactly; ordinary hand-authored
    /// declarations should use [#withModifiers(Modifier...)]. A later
    /// [#withModifiers(Modifier...)] call still adds to the set installed here.
    ///
    /// @param mods the exact modifier set to use
    /// @return this builder
    public RecordBuilder withExactModifiers(Set<Modifier> mods) {
        modifiers.clear();
        modifiers.addAll(mods);
        return this;
    }

    /// Adds a type parameter to the record declaration, e.g. `T` or
    /// `T extends Comparable<T>`.
    ///
    /// @param name the type parameter's name
    /// @param bounds the parameter's upper bounds, or none for an unbounded parameter
    /// @return this builder
    public RecordBuilder withTypeParam(String name, ClassOrInterfaceTypeRef... bounds) {
        typeParams.add(new TypeParam(name, List.of(bounds)));
        return this;
    }

    /// Adds a pre-built type parameter to the record declaration.
    ///
    /// @param typeParam the type parameter to add
    /// @return this builder
    public RecordBuilder withTypeParam(TypeParam typeParam) {
        typeParams.add(typeParam);
        return this;
    }

    /// Adds a record component, in declaration order.
    ///
    /// @param name the component name
    /// @param type the declared component type
    /// @return this builder
    public RecordBuilder withComponent(String name, TypeRef type) {
        components.add(new RecordComponent(name, type));
        return this;
    }

    /// Adds a pre-built record component, in declaration order.
    ///
    /// @param component the component to add
    /// @return this builder
    public RecordBuilder withComponent(RecordComponent component) {
        components.add(component);
        return this;
    }

    public RecordBuilder withInterface(ClassDesc iface) {
        return withInterface(Types.of(iface));
    }

    /// Adds an interface, possibly parameterized, to the record's `implements` clause.
    ///
    /// @param iface the implemented interface
    /// @return this builder
    public RecordBuilder withInterface(ClassOrInterfaceTypeRef iface) {
        interfaces.add(iface);
        return this;
    }

    /// Adds a compact constructor with the `public` modifier and no `throws` clause.
    ///
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public RecordBuilder withCompactConstructor(Consumer<CodeBuilder> spec) {
        return withCompactConstructor(Set.of(Modifier.PUBLIC), List.of(), spec);
    }

    /// Adds a compact constructor with explicit modifiers and a `throws` clause.
    ///
    /// @param modifiers the constructor's modifiers
    /// @param throwsTypes the thrown exception types
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public RecordBuilder withCompactConstructor(
            Set<Modifier> modifiers, List<ClassDesc> throwsTypes, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        List<ClassOrInterfaceTypeRef> normalizedThrows = new ArrayList<>(throwsTypes.size());
        for (ClassDesc type : throwsTypes) {
            normalizedThrows.add(Types.of(type));
        }
        members.add(new CompactConstructorDecl(List.of(), modifiers, cb.build(), normalizedThrows));
        return this;
    }

    /// Adds an explicit (non-compact) canonical constructor with the `public`
    /// modifier and no `throws` clause. The given `params` must match the
    /// record's components exactly in name, type, and order — enforced when
    /// the record is rendered.
    ///
    /// @param params the constructor's parameters, matching the record's components exactly
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public RecordBuilder withCanonicalConstructor(List<Param> params, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        members.add(new CanonicalConstructorDecl(List.of(), Set.of(Modifier.PUBLIC), params, cb.build(), List.of()));
        return this;
    }

    /// Adds an explicit (non-compact) canonical constructor with the `public`
    /// modifier and no `throws` clause. The given `params` must match the
    /// record's components exactly in name, type, and order — enforced when
    /// the record is rendered.
    ///
    /// Declared as `Param[]` rather than `Param...`: paired with
    /// [#withCanonicalConstructor(List,Consumer)], a `Param...` form would
    /// make a call with an empty parameter list ambiguous between the two
    /// overloads.
    ///
    /// @param params the constructor's parameters, matching the record's components exactly
    /// @param spec receives the builder to populate the constructor body
    /// @return this builder
    public RecordBuilder withCanonicalConstructor(Param[] params, Consumer<CodeBuilder> spec) {
        return withCanonicalConstructor(List.of(params), spec);
    }

    /// Adds a method with a return type.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public RecordBuilder withMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
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
    public RecordBuilder withVoidMethod(String name, Consumer<MethodBuilder> spec) {
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

    /// Adds a static field.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param initializer the initializer expression
    /// @return this builder
    public RecordBuilder withStaticField(String name, TypeRef type, Expr initializer) {
        members.add(new StaticFieldDecl(name, type, List.of(), initializer));
        return this;
    }

    /// Adds a nested class declaration.
    ///
    /// @param desc the nested class to declare
    /// @param spec receives the builder to populate the class declaration
    /// @return this builder
    public RecordBuilder withNestedClass(ClassDesc desc, Consumer<ClassBuilder> spec) {
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
    public RecordBuilder withNestedInterface(ClassDesc desc, Consumer<InterfaceBuilder> spec) {
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
    public RecordBuilder withNestedRecord(ClassDesc desc, Consumer<RecordBuilder> spec) {
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
    public RecordBuilder withNestedEnum(ClassDesc desc, Consumer<EnumBuilder> spec) {
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
    public RecordBuilder withNestedAnnotationType(ClassDesc desc, Consumer<AnnotationTypeBuilder> spec) {
        AnnotationTypeBuilder ab = new AnnotationTypeBuilder(desc);
        spec.accept(ab);
        members.add(ab.build());
        return this;
    }

    /// Appends the given pre-built member to the record body.
    ///
    /// @param member the member to append
    @Override
    public void accept(RecordMember member) {
        members.add(member);
    }

    /// Snapshots the accumulated state into an immutable [RecordDecl].
    ///
    /// @return the finished record declaration
    public RecordDecl build() {
        return new RecordDecl(
                desc,
                List.copyOf(annotations),
                Set.copyOf(modifiers),
                List.copyOf(typeParams),
                List.copyOf(components),
                List.copyOf(interfaces),
                List.copyOf(members));
    }
}
