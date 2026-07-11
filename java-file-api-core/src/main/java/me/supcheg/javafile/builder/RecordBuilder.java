package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/// A mutable builder for a top-level record declaration.
///
/// Always declares with the `public` modifier; there is no way to add
/// further modifiers. Builder methods return `this` for chaining;
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

    /// Adds a record component, in declaration order.
    ///
    /// @param name the component name
    /// @param type the declared component type
    /// @return this builder
    public RecordBuilder withComponent(String name, TypeRef type) {
        components.add(new RecordComponent(name, type));
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
        members.add(new CompactConstructorDecl(modifiers, cb.build(), normalizedThrows));
        return this;
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
                mb.name(), mb.returnType(), mb.modifiers(), mb.typeParams(), mb.params(), mb.body(), mb.throwsTypes()));
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
                mb.name(), mb.returnType(), mb.modifiers(), mb.typeParams(), mb.params(), mb.body(), mb.throwsTypes()));
        return this;
    }

    /// Adds a static field.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param initializer the initializer expression
    /// @return this builder
    public RecordBuilder withStaticField(String name, TypeRef type, Expr initializer) {
        members.add(new StaticFieldDecl(name, type, initializer));
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
                Set.of(Modifier.PUBLIC),
                List.copyOf(typeParams),
                List.copyOf(components),
                List.copyOf(interfaces),
                List.copyOf(members));
    }
}
