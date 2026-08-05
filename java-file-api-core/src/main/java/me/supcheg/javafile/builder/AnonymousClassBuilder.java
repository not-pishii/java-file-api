package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for the body of an anonymous class, `new Type() { ... }`.
///
/// Members are [EnumConstantMember]s: an anonymous class body, like an enum
/// constant's body, cannot declare a constructor or an abstract method, so
/// those combinations are unrepresentable rather than rejected at runtime.
///
/// Implements `Consumer<EnumConstantMember>` so that transforms and other
/// producers can feed pre-built members directly via
/// [#accept(EnumConstantMember)].
///
/// Instances are not thread-safe.
public final class AnonymousClassBuilder implements Consumer<EnumConstantMember> {

    private final List<EnumConstantMember> members = new ArrayList<>();

    /// Creates an empty anonymous class body builder.
    public AnonymousClassBuilder() {}

    /// Adds a field with no initializer and default modifiers.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @return this builder
    public AnonymousClassBuilder withField(String name, TypeRef type) {
        return withField(name, type, fb -> {});
    }

    /// Adds a field with an initializer and default modifiers.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param initializer the initializer expression
    /// @return this builder
    public AnonymousClassBuilder withField(String name, TypeRef type, Expr initializer) {
        return withField(name, type, fb -> fb.withInitializer(initializer));
    }

    /// Adds a field.
    ///
    /// @param name the field name
    /// @param type the declared field type
    /// @param spec receives the builder to populate the field
    /// @return this builder
    public AnonymousClassBuilder withField(String name, TypeRef type, Consumer<FieldBuilder> spec) {
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
    public AnonymousClassBuilder withMethod(String name, TypeRef returnType, Consumer<MethodBuilder> spec) {
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
    public AnonymousClassBuilder withVoidMethod(String name, Consumer<MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.empty());
        spec.accept(mb);
        members.add(mb.build());
        return this;
    }

    /// Adds a nested class declaration.
    ///
    /// @param desc the nested class to declare
    /// @param spec receives the builder to populate the class declaration
    /// @return this builder
    public AnonymousClassBuilder withNestedClass(ClassDesc desc, Consumer<ClassBuilder> spec) {
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
    public AnonymousClassBuilder withNestedInterface(ClassDesc desc, Consumer<InterfaceBuilder> spec) {
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
    public AnonymousClassBuilder withNestedRecord(ClassDesc desc, Consumer<RecordBuilder> spec) {
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
    public AnonymousClassBuilder withNestedEnum(ClassDesc desc, Consumer<EnumBuilder> spec) {
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
    public AnonymousClassBuilder withNestedAnnotationType(ClassDesc desc, Consumer<AnnotationTypeBuilder> spec) {
        AnnotationTypeBuilder ab = new AnnotationTypeBuilder(desc);
        spec.accept(ab);
        members.add(ab.build());
        return this;
    }

    /// Appends the given pre-built member to the anonymous class body.
    ///
    /// @param member the member to append
    @Override
    public void accept(EnumConstantMember member) {
        members.add(member);
    }

    /// Snapshots the accumulated members.
    ///
    /// @return the finished member list
    public List<EnumConstantMember> build() {
        return List.copyOf(members);
    }
}
