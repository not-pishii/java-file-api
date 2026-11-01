package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.PackageName;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Objects;

/// The declarations a [TypedJavaFile] entry point makes, generic or not: the plumbing behind the
/// stages of [GenericDeclarationArities], which call it, and behind the entry points that take no
/// type parameter.
///
/// A specification of a generic declaration is a generic method of its own, which a lambda cannot be:
/// each entry point hands the stages here an anonymous implementation of one of the three interfaces
/// below, branded with a fresh `Self`, that gives the specification the declaration and the tokens of
/// its type parameters.
final class GenericDeclarations {
    private GenericDeclarations() {}

    /// What declares a class, given its declaration branded with a `Self` of its own.
    @FunctionalInterface
    interface ClassBodies {
        /// Declares the class through the declaration.
        ///
        /// @param declaration the declaration of the class
        /// @param <Self> the brand of the class being declared
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration);
    }

    /// What declares an interface, given its builder branded with a `Self` of its own.
    @FunctionalInterface
    interface InterfaceBodies {
        /// Declares the interface through the builder.
        ///
        /// @param builder the self-branded interface builder
        /// @param <Self> the brand of the interface being declared
        /// @return the bodies of the members: the stage the declaration of the interface ends in
        <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder);
    }

    /// What declares a record, given its builder branded with a `Self` of its own.
    @FunctionalInterface
    interface RecordBodies {
        /// Declares the record through the builder.
        ///
        /// @param builder the self-branded record builder
        /// @param <Self> the brand of the record being declared
        /// @return the bodies of the members: the stage the declaration of the record ends in
        <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder);
    }

    /// Declares a `public final` class that extends `Object` and implements nothing: what
    /// [#class_(TargetClasspath, ClassDesc, TypedJavaFile.TypedClassHeaderSpec)] says.
    static JavaFile class_(TargetClasspath target, ClassDesc desc, TypedJavaFile.TypedClassHeaderSpec spec) {
        return declareClass(target, desc, List.of(), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration));
            }
        });
    }

    /// Declares a generic `public final` class from its header on.
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param parameters the type parameters, in order
    /// @param spec declares the class, given its declaration branded with a fresh `Self`
    /// @return the rendered class
    static JavaFile declareClass(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, ClassBodies spec) {
        Scopes.requireNoneOpen("class " + described(desc));
        return declare(target, declarable(desc, "class"), parameters, spec);
    }

    /// Declares a `public` interface from its members.
    static JavaFile interface_(TargetClasspath target, ClassDesc desc, TypedJavaFile.TypedInterfaceSpec spec) {
        return interface_(target, desc, List.of(), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder);
            }
        });
    }

    /// Declares a generic `public` interface from its members.
    ///
    /// @param target the classpath the interface is compiled against
    /// @param desc the interface's binary name
    /// @param parameters the type parameters, in order
    /// @param spec declares the interface, given its builder branded with a fresh `Self`
    /// @return the rendered interface
    static JavaFile interface_(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, InterfaceBodies spec) {
        Scopes.requireNoneOpen("interface " + described(desc));
        return declaredInterface(target, declarable(desc, "interface"), parameters, spec);
    }

    /// Declares a `public` record from its components and members.
    static JavaFile record(TargetClasspath target, ClassDesc desc, TypedJavaFile.TypedRecordSpec spec) {
        return record(target, desc, List.of(), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder);
            }
        });
    }

    /// Declares a generic `public` record from its components and members.
    ///
    /// @param target the classpath the record is compiled against
    /// @param desc the record's binary name
    /// @param parameters the type parameters, in order
    /// @param spec declares the record, given its builder branded with a fresh `Self`
    /// @return the rendered record
    static JavaFile record(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, RecordBodies spec) {
        Scopes.requireNoneOpen("record " + described(desc));
        return declaredRecord(target, declarable(desc, "record"), parameters, spec);
    }

    private static <Self> JavaFile declaredInterface(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, InterfaceBodies spec) {
        TypedInterfaceBuilder<Self> builder = new TypedInterfaceBuilder<>(target, desc, parameters, List.of());
        return builder.complete(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the interface"));
    }

    private static <Self> JavaFile declaredRecord(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, RecordBodies spec) {
        TypedRecordBuilder<Self> builder = new TypedRecordBuilder<>(target, desc, parameters, List.of());
        return builder.complete(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the record"));
    }

    private static <Self> JavaFile declare(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, ClassBodies spec) {
        ClassDeclaration<Self> declaration = new ClassDeclaration<>(target, desc, parameters);
        return declaration.complete(Objects.requireNonNull(
                spec.build(declaration),
                "a specification returns the bodies of the members of the class, the stage its declarations end in"));
    }

    /// The token of a type parameter of a class declaration, as a specification takes it.
    ///
    /// @param declaration the declaration of the class
    /// @param index its index
    /// @param <T> the brand of the type parameter
    /// @return the token
    @SuppressWarnings("unchecked")
    static <T> TypeVarToken<T> variable(ClassDeclaration<?> declaration, int index) {
        return (TypeVarToken<T>) declaration.variable(index);
    }

    /// The token of a type parameter of an interface or a record, as a specification takes it.
    ///
    /// @param declaration the declaration of the interface or the record
    /// @param index its index
    /// @param <T> the brand of the type parameter
    /// @return the token
    @SuppressWarnings("unchecked")
    static <T> TypeVarToken<T> variable(TypeDeclarationSupport<?> declaration, int index) {
        return (TypeVarToken<T>) declaration.variable(index);
    }

    /// The type `desc` describes, if the typed layer declares it: a top-level type of the kind `kind`,
    /// whose package and simple name Java source writes as they are (JLS 3.8, 7.4.1), and of no package
    /// of the platform.
    ///
    /// @throws IllegalArgumentException if `desc` describes a nested type, or a name of it cannot be written
    static ClassDesc declarable(ClassDesc desc, String kind) {
        String described = described(desc);
        if (ClassDescNames.nestingChain(desc).size() != 1) {
            throw new IllegalArgumentException("cannot declare the " + kind + " " + described + ": its name is"
                    + " that of a nested " + kind + ", and the typed layer declares a top-level type alone");
        }
        try {
            Identifiers.requireTypeIdentifier(desc.displayName());
            PackageName.of(desc);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "cannot declare the " + kind + " " + described + ": " + e.getMessage(), e);
        }
        if (desc.packageName().equals("java") || desc.packageName().startsWith("java.")) {
            throw new IllegalArgumentException("cannot declare the " + kind + " " + described + ": a package of"
                    + " java is one of the platform, which javac refuses to add to where the module java.base has"
                    + " it and no class loader but the platform's defines a class of");
        }
        return desc;
    }

    /// The type `desc` describes, as a message names it.
    private static String described(ClassDesc desc) {
        return desc.packageName().isEmpty() ? desc.displayName() : desc.packageName() + "." + desc.displayName();
    }
}
