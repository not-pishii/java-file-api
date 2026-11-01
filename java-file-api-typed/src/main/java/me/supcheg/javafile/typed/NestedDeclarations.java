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

    static <N> NestedType<FinalClassToken<N>> class_(
            TargetClasspath target,
            ClassDesc outer,
            String name,
            List<TypeParameter<?>> parameters,
            List<DeclarationIdentity> enclosing,
            boolean inner,
            ClassBodies spec) {
        ClassDeclaration<N> declaration =
                new ClassDeclaration<>(target, desc(outer, name), parameters, enclosing, inner, !inner, true);
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
            boolean inner,
            TypedJavaFile.TypedClassHeaderSpec spec) {
        return class_(target, outer, name, parameters, enclosing, inner, new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> header, ClassDeclaration<N2> declaration) {
                return spec.build(header);
            }
        });
    }

    static <I> NestedType<InterfaceToken<I>> interface_(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedInterfaceSpec spec) {
        TypedInterfaceBuilder<I> builder = new TypedInterfaceBuilder<>(target, desc(outer, name), List.of(), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the interface"));
        @SuppressWarnings("unchecked")
        InterfaceToken<I> token = (InterfaceToken<I>) builder.self();
        return new NestedType<>(token, type);
    }

    static <R> NestedType<FinalClassToken<R>> record(
            TargetClasspath target, ClassDesc outer, String name, TypedJavaFile.TypedRecordSpec spec) {
        TypedRecordBuilder<R> builder = new TypedRecordBuilder<>(target, desc(outer, name), List.of(), List.of());
        TypeDecl type = builder.completeNested(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the record"));
        @SuppressWarnings("unchecked")
        FinalClassToken<R> token = (FinalClassToken<R>) builder.self();
        return new NestedType<>(token, type);
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
