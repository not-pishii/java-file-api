package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.model.TypeDecl;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Objects;

/// Declares the member types of a type the typed layer is declaring (§6.5): each is built by the
/// declaration of its own kind, completes within the call that declares it, and is rendered by the
/// declaration that encloses it. A member type has the binary name of the enclosing type and its own
/// simple name, `Outer$Inner`.
final class NestedDeclarations {
    private NestedDeclarations() {}

    /// The binary name of a member type.
    ///
    /// @param outer the enclosing type
    /// @param name the simple name of the member type
    /// @return the binary name
    /// @throws IllegalArgumentException if `name` cannot name a type
    static ClassDesc desc(ClassDesc outer, String name) {
        try {
            Identifiers.requireTypeIdentifier(name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "a member type of " + outer.displayName() + " cannot be named '" + name + "': " + e.getMessage(),
                    e);
        }
        return outer.nested(name);
    }

    /// What declares a member class, given its declaration: the specification of its header, and the
    /// tokens of such type parameters as it has.
    @FunctionalInterface
    interface ClassBodies {
        <N> MemberBodies<N, ?> build(ClassHeader<N> header, ClassDeclaration<N> declaration);
    }

    /// What declares a member interface, given its builder: the tokens of its type parameters are
    /// the builder's, by [TypeDeclarationSupport#variable].
    @FunctionalInterface
    interface InterfaceBodies {
        /// Declares the interface through the builder.
        ///
        /// @param builder the self-branded interface builder
        /// @param <I> the brand of the interface being declared
        /// @return the bodies of the members: the stage the declaration of the interface ends in
        <I> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> builder);
    }

    /// What declares a member record, given its builder: the tokens of its type parameters are the
    /// builder's, by [TypeDeclarationSupport#variable].
    @FunctionalInterface
    interface RecordBodies {
        /// Declares the record through the builder.
        ///
        /// @param builder the self-branded record builder
        /// @param <R> the brand of the record being declared
        /// @return the bodies of the members: the stage the declaration of the record ends in
        <R> TypedRecordBodies<R> build(TypedRecordBuilder<R> builder);
    }

    /// A member type as it was declared: its token, and the declaration its enclosing file renders.
    ///
    /// @param token the token
    /// @param type the declaration
    /// @param <T> the kind of the token
    record NestedType<T>(T token, TypeDecl type) {}

    @SuppressWarnings("unchecked")
    static <T> TypeVarToken<T> variable(ClassDeclaration<?> declaration, int index) {
        return (TypeVarToken<T>) declaration.variable(index);
    }

    @SuppressWarnings("unchecked")
    static <T> TypeVarToken<T> variable(TypeDeclarationSupport<?> declaration, int index) {
        return (TypeVarToken<T>) declaration.variable(index);
    }

    static <N> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            List<TypeParameter<?>> parameters,
            List<DeclarationIdentity> enclosing,
            ClassBodies spec) {
        ClassDeclaration<N> declaration =
                new ClassDeclaration<>(target, desc(outer, name), parameters, enclosing, false, true, true);
        MemberBodies<N, ?> bodies = Objects.requireNonNull(
                spec.build(new ClassHeader<>(declaration), declaration),
                "a specification returns the bodies of the members of the class");
        TypeDecl type = declaration.completeNested(bodies);
        return new NestedType<>(declaration.self(), type);
    }

    static <N> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            List<TypeParameter<?>> parameters,
            List<DeclarationIdentity> enclosing,
            TypedJavaFile.TypedClassHeaderSpec spec) {
        return class_(target, outer, name, parameters, enclosing, new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> header, ClassDeclaration<N2> declaration) {
                return spec.build(header);
            }
        });
    }

    static <N, B1> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypedJavaFile.TypedGenericClassSpec1<B1> spec) {
        return class_(target, outer, name, List.of(p1), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> header, ClassDeclaration<N2> declaration) {
                return spec.build(header, NestedDeclarations.variable(declaration, 0));
            }
        });
    }

    static <N, B1, B2> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericClassSpec2<B1, B2> spec) {
        return class_(target, outer, name, List.of(p1, p2), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> header, ClassDeclaration<N2> declaration) {
                return spec.build(
                        header,
                        NestedDeclarations.variable(declaration, 0),
                        NestedDeclarations.variable(declaration, 1));
            }
        });
    }

    static <N, B1, B2, B3> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericClassSpec3<B1, B2, B3> spec) {
        return class_(target, outer, name, List.of(p1, p2, p3), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> header, ClassDeclaration<N2> declaration) {
                return spec.build(
                        header,
                        NestedDeclarations.variable(declaration, 0),
                        NestedDeclarations.variable(declaration, 1),
                        NestedDeclarations.variable(declaration, 2));
            }
        });
    }

    /// Declares an inner member class and returns the handle that ties its body to the instance of
    /// the class it is declared in: `Outer.this` and `outer.new Inner(...)`.
    static <O, N> NestedType<InnerClass<O, N>> innerClass(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            FinalClassToken<O> outerToken,
            List<DeclarationIdentity> enclosing,
            TypedJavaFile.TypedInnerClassSpec<O> spec) {
        ClassDeclaration<N> declaration =
                new ClassDeclaration<>(target, desc(outer, name), List.of(), enclosing, true, false, true);
        InnerClass<O, N> inner = new InnerClass<>(outerToken, declaration.self());
        MemberBodies<N, ?> bodies = Objects.requireNonNull(
                spec.build(new ClassHeader<>(declaration), inner),
                "a specification returns the bodies of the members of the inner class");
        TypeDecl type = declaration.completeNested(bodies);
        return new NestedType<>(inner, type);
    }

    static <I> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedInterfaceSpec spec) {
        TypedInterfaceBuilder<I> builder = new TypedInterfaceBuilder<>(target, desc(outer, name), List.of(), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the interface"));
        return new NestedType<>(builderToken(builder), type);
    }

    static <I, B1> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypedJavaFile.TypedGenericInterfaceSpec1<B1> spec) {
        TypedInterfaceBuilder<I> builder =
                new TypedInterfaceBuilder<>(target, desc(outer, name), List.of(p1), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder, NestedDeclarations.variable(builder, 0)),
                "a specification returns the bodies of the members of the interface"));
        return new NestedType<>(builderToken(builder), type);
    }

    static <I, B1, B2> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericInterfaceSpec2<B1, B2> spec) {
        TypedInterfaceBuilder<I> builder =
                new TypedInterfaceBuilder<>(target, desc(outer, name), List.of(p1, p2), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder, NestedDeclarations.variable(builder, 0), NestedDeclarations.variable(builder, 1)),
                "a specification returns the bodies of the members of the interface"));
        return new NestedType<>(builderToken(builder), type);
    }

    static <I, B1, B2, B3> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        TypedInterfaceBuilder<I> builder =
                new TypedInterfaceBuilder<>(target, desc(outer, name), List.of(p1, p2, p3), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(
                        builder,
                        NestedDeclarations.variable(builder, 0),
                        NestedDeclarations.variable(builder, 1),
                        NestedDeclarations.variable(builder, 2)),
                "a specification returns the bodies of the members of the interface"));
        return new NestedType<>(builderToken(builder), type);
    }

    @SuppressWarnings("unchecked")
    private static <I> InterfaceToken<I> builderToken(TypedInterfaceBuilder<I> builder) {
        return (InterfaceToken<I>) builder.self();
    }

    /// Declares a member interface with the `parameters` the specification's tokens describe: what
    /// the generated stages of four to twelve type parameters build, the hand-written ones of one to
    /// three taking the tokens themselves.
    static <I> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            List<TypeParameter<?>> parameters,
            InterfaceBodies spec) {
        TypedInterfaceBuilder<I> builder =
                new TypedInterfaceBuilder<>(target, desc(outer, name), parameters, List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the interface"));
        return new NestedType<>(builderToken(builder), type);
    }

    static <R> NestedType<FinalClassToken<R>> record(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedRecordSpec spec) {
        TypedRecordBuilder<R> builder = new TypedRecordBuilder<>(target, desc(outer, name), List.of(), List.of());
        return recordBuilt(builder, spec.build(builder));
    }

    static <R, B1> NestedType<FinalClassToken<R>> record(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypedJavaFile.TypedGenericRecordSpec1<B1> spec) {
        TypedRecordBuilder<R> builder = new TypedRecordBuilder<>(target, desc(outer, name), List.of(p1), List.of());
        return recordBuilt(builder, spec.build(builder, NestedDeclarations.variable(builder, 0)));
    }

    static <R, B1, B2> NestedType<FinalClassToken<R>> record(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericRecordSpec2<B1, B2> spec) {
        TypedRecordBuilder<R> builder = new TypedRecordBuilder<>(target, desc(outer, name), List.of(p1, p2), List.of());
        return recordBuilt(
                builder,
                spec.build(builder, NestedDeclarations.variable(builder, 0), NestedDeclarations.variable(builder, 1)));
    }

    static <R, B1, B2, B3> NestedType<FinalClassToken<R>> record(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericRecordSpec3<B1, B2, B3> spec) {
        TypedRecordBuilder<R> builder =
                new TypedRecordBuilder<>(target, desc(outer, name), List.of(p1, p2, p3), List.of());
        return recordBuilt(
                builder,
                spec.build(
                        builder,
                        NestedDeclarations.variable(builder, 0),
                        NestedDeclarations.variable(builder, 1),
                        NestedDeclarations.variable(builder, 2)));
    }

    private static <R> NestedType<FinalClassToken<R>> recordBuilt(
            TypedRecordBuilder<R> builder, TypedRecordBodies<R> bodies) {
        TypeDecl type = builder.completeNested(
                Objects.requireNonNull(bodies, "a specification returns the bodies of the members of the record"));
        return new NestedType<>(builderToken(builder), type);
    }

    /// Declares a member record with the `parameters` the specification's tokens describe: what the
    /// generated stages of four to twelve type parameters build, the hand-written ones of one to three
    /// taking the tokens themselves.
    static <R> NestedType<FinalClassToken<R>> record(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            List<TypeParameter<?>> parameters,
            RecordBodies spec) {
        TypedRecordBuilder<R> builder = new TypedRecordBuilder<>(target, desc(outer, name), parameters, List.of());
        return recordBuilt(builder, spec.build(builder));
    }

    @SuppressWarnings("unchecked")
    private static <R> FinalClassToken<R> builderToken(TypedRecordBuilder<R> builder) {
        return (FinalClassToken<R>) builder.self();
    }

    static <E> NestedType<EnumToken<E>> enum_(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedEnumSpec spec) {
        TypedEnumBuilder<E> builder = new TypedEnumBuilder<>(target, desc(outer, name));
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the enum"));
        @SuppressWarnings("unchecked")
        EnumToken<E> token = (EnumToken<E>) builder.self();
        return new NestedType<>(token, type);
    }

    static NestedType<ClassDesc> annotationType(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        ClassDesc desc = desc(outer, name);
        TypedAnnotationTypeBuilder<Object> builder = new TypedAnnotationTypeBuilder<>(target, desc);
        Objects.requireNonNull(spec.build(builder), "a specification returns the builder of the annotation type");
        return new NestedType<>(desc, builder.completeNested());
    }
}
