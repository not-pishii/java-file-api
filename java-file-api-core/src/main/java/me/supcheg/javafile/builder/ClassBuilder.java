package me.supcheg.javafile.builder;

import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ClassMember;
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

/// A mutable builder for a top-level class declaration.
///
/// Starts with the `public` modifier already applied. Builder methods return
/// `this` for chaining; [#build()] snapshots the accumulated state into an
/// immutable [ClassDecl], so a builder may be reused after building.
///
/// Implements `Consumer<ClassMember>` so that transforms and other producers
/// can feed pre-built members directly via [#accept(ClassMember)].
///
/// Instances are not thread-safe.
public final class ClassBuilder implements Consumer<ClassMember> {

    private final ClassDesc desc;
    private final Set<Modifier> modifiers = new LinkedHashSet<>(Set.of(Modifier.PUBLIC));
    private final List<TypeParam> typeParams = new ArrayList<>();
    private ClassOrInterfaceTypeRef superclass;
    private final List<ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private final List<ClassDesc> permits = new ArrayList<>();
    private final List<ClassMember> members = new ArrayList<>();

    /// Creates a builder for a class with the given descriptor.
    ///
    /// @param desc the class to declare; its package and simple name determine the file location
    public ClassBuilder(ClassDesc desc) {
        this.desc = desc;
    }

    /// Adds the given modifiers to the declaration.
    ///
    /// Modifiers accumulate across calls and duplicates are ignored; the initial
    /// `public` modifier cannot be removed.
    ///
    /// @param mods the modifiers to add
    /// @return this builder
    public ClassBuilder withModifiers(Modifier... mods) {
        modifiers.addAll(List.of(mods));
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
    public ClassBuilder permits(ClassDesc... types) {
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
                name, Optional.of(returnType), List.of(params), Set.of(Modifier.PUBLIC, Modifier.ABSTRACT), List.of()));
        return this;
    }

    /// Adds a `void` abstract method.
    ///
    /// @param name the method name
    /// @param params the method's parameters, in order
    /// @return this builder
    public ClassBuilder withVoidAbstractMethod(String name, Param... params) {
        members.add(new AbstractMethodDecl(
                name, Optional.empty(), List.of(params), Set.of(Modifier.PUBLIC, Modifier.ABSTRACT), List.of()));
        return this;
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

    /// Appends the given pre-built member to the class body.
    ///
    /// @param member the member to append
    @Override
    public void accept(ClassMember member) {
        members.add(member);
    }

    /// Snapshots the accumulated state into an immutable [ClassDecl].
    ///
    /// @return the finished class declaration
    public ClassDecl build() {
        return new ClassDecl(
                desc,
                Set.copyOf(modifiers),
                List.copyOf(typeParams),
                Optional.ofNullable(superclass),
                List.copyOf(interfaces),
                List.copyOf(permits),
                List.copyOf(members));
    }
}
