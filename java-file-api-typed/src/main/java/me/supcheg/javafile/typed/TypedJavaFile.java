package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.PackageName;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Objects;

/// Entry point of the typed eDSL (§6.5): declares a generated `final` class
/// through a self-branded [TypedClassBuilder].
///
/// A class is declared in stages, each a type that offers what can be said
/// at its point and gives the next: the header ([ClassHeader]) names the
/// supertypes, the builder of the members ([MemberBuilder]) declares every
/// member, their bodies ([MemberBodies]) define each. A specification
/// returns the last of them: one that does not come to the bodies of the
/// members has nothing to return, and does not compile. What is wrong is
/// rejected by the call that says it, in the generator; the entry itself
/// rejects only what no call could tell — a member declared and never
/// defined — and tells the line that declared it.
///
/// The entry takes the [TargetClasspath] the generated code is compiled
/// against (§5): the facts a generator uses come from metamodels generated
/// against the classpath of the generator, and the class is rendered only
/// of those that hold on the target. There is no entry without one. In an
/// annotation processor it is `TargetClasspaths.of(processingEnv)` of
/// `java-file-api-lang-model`, made in `init`; where there is no
/// compilation to check against, `UnsafeFacts.unverifiedClasspath()`.
public final class TypedJavaFile {
    private TypedJavaFile() {}

    /// Declares a `public final` class that extends `Object` and implements nothing; short for
    /// a [TypedClassHeaderSpec] that goes `h.extendsObject().members()`. `spec` is implemented as an
    /// anonymous class (a generic method is not expressible as a lambda) to receive a
    /// [TypedClassBuilder] branded with a `Self` unique to this call — the
    /// same CPS-brand technique as `Facts.withToken` (§3.1):
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.class_(target, desc, new TypedJavaFile.TypedClassSpec() {
    ///     public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
    ///         var x = cb.declareField("x", PrimitiveToken.INT);
    ///         var x2 = cb.declareMethod("x2", PrimitiveToken.INT);
    ///         var bodies = cb.bodies();
    ///         bodies.initialize(x, literal(1));
    ///         bodies.define(x2, (b, self) -> b.return_(addInt(field(self, x), field(self, x))));
    ///         return bodies;
    ///     }
    /// });
    /// ```
    ///
    /// @param target the classpath the class is compiled against: every metamodel the class is
    ///     rendered of is checked against it, once, when lowering first meets it
    /// @param desc the class's binary name
    /// @param spec populates the class, given its self-branded builder
    /// @return the rendered class, ready for [JavaFile#render()]/[JavaFile#writeTo(java.nio.file.Path)]
    /// @throws IllegalArgumentException if `desc` describes a nested class, or a class of a package of
    ///     `java`, or its package or simple name cannot be written in Java (a keyword, `var`, ...) — before
    ///     `spec` is called
    /// @throws IllegalStateException if called while a method body is being built, or if a member
    ///     `spec` declared is not defined
    /// @throws TargetClasspathMismatchException if a metamodel the class uses does not hold on `target`:
    ///     the generator was compiled against another version of the type — thrown by the call of
    ///     `spec` that uses it
    /// @throws FactLookupException if a member the class uses has an overload on `target` that no
    ///     argument tells it from; as the mismatch is, a [me.supcheg.javafile.facts.FactException]
    public static JavaFile class_(TargetClasspath target, ClassDesc desc, TypedClassSpec spec) {
        return declareClass(target, desc, new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                return spec.build(h.extendsObject().members());
            }
        });
    }

    /// Declares a `public final` class from its header on: `spec` receives the first stage of
    /// the header ([Header]), branded with a `Self` unique to this call, says what the class
    /// extends and implements, and declares the members with the builder the header ends in:
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.declareClass(target, desc, new TypedJavaFile.TypedClassHeaderSpec() {
    ///     public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
    ///         var cmp = new Comparable_<>(h.self());
    ///         var compareTo = h.overriding(cmp.compareTo_T);
    ///         var oh = h.extendsObject();
    ///         var asComparable = oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
    ///         var bodies = oh.members().bodies();
    ///         bodies.define(compareTo.method(), (b, self, other) -> b.return_(literal(0)));
    ///         return bodies;
    ///     }
    /// });
    /// ```
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param spec declares the class, given the first stage of its header
    /// @return the rendered class
    /// @throws IllegalArgumentException if `desc` describes a nested class, or a class of a package of
    ///     `java`, or its package or simple name cannot be written in Java (a keyword, `var`, ...) — before
    ///     `spec` is called
    /// @throws IllegalStateException if called while a method body is being built, or if a member
    ///     `spec` declared is not defined
    /// @throws TargetClasspathMismatchException if a metamodel the class uses does not hold on `target`
    /// @throws FactLookupException if a member the class uses has an overload on `target` that no
    ///     argument tells it from
    public static JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypedClassHeaderSpec spec) {
        return declared(target, desc, List.of(), new Generic() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration));
            }
        });
    }

    /// Declares a `public` interface, from its members: abstract, default, static and private methods,
    /// constants, the interfaces it extends, and the subtypes it permits if it is `sealed`.
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.interface_(target, desc, new TypedJavaFile.TypedInterfaceSpec() {
    ///     public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
    ///         var area = ib.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
    ///         return ib.bodies();
    ///     }
    /// });
    /// ```
    ///
    /// @param target the classpath the interface is compiled against
    /// @param desc the interface's binary name
    /// @param spec declares the interface, given its self-branded builder
    /// @return the rendered interface
    /// @throws IllegalArgumentException if `desc` describes a nested type, or its package or simple name cannot
    ///     be written in Java
    /// @throws IllegalStateException if a member `spec` declared is not defined
    public static JavaFile interface_(TargetClasspath target, ClassDesc desc, TypedInterfaceSpec spec) {
        requireInterfaceName(desc);
        return declaredInterface(target, declarable(desc, "interface"), List.of(), List.of(), spec);
    }

    /// Declares a generic `public` interface with one type parameter, from its members: the type
    /// parameter's token is in scope in the header, the members and the member types of the interface,
    /// as the token of the class's type parameter is ([#declareClass(TargetClasspath, ClassDesc,
    /// TypeParameter, TypedGenericClassSpec1)]).
    ///
    /// @param target the classpath the interface is compiled against
    /// @param desc the interface's binary name
    /// @param p1 the type parameter
    /// @param spec declares the interface, given its self-branded builder and the token of the type parameter
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the rendered interface
    /// @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or
    ///     its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
    public static <B1> JavaFile interface_(
            TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericInterfaceSpec1<B1> spec) {
        requireInterfaceName(desc);
        return declaredInterface(
                target, declarable(desc, "interface"), List.of(p1), List.of(), new TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        return spec.build(ib, TypedJavaFile.<B1>variable(ib, 0));
                    }
                });
    }

    /// Declares a generic `public` interface with two type parameters, see
    /// [#interface_(TargetClasspath, ClassDesc, TypeParameter, TypedGenericInterfaceSpec1)].
    ///
    /// @param target the classpath the interface is compiled against
    /// @param desc the interface's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the rendered interface
    public static <B1, B2> JavaFile interface_(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedGenericInterfaceSpec2<B1, B2> spec) {
        requireInterfaceName(desc);
        return declaredInterface(
                target, declarable(desc, "interface"), List.of(p1, p2), List.of(), new TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        return spec.build(ib, TypedJavaFile.<B1>variable(ib, 0), TypedJavaFile.<B2>variable(ib, 1));
                    }
                });
    }

    /// Declares a generic `public` interface with three type parameters, see
    /// [#interface_(TargetClasspath, ClassDesc, TypeParameter, TypedGenericInterfaceSpec1)].
    ///
    /// @param target the classpath the interface is compiled against
    /// @param desc the interface's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the rendered interface
    public static <B1, B2, B3> JavaFile interface_(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        requireInterfaceName(desc);
        return declaredInterface(
                target, declarable(desc, "interface"), List.of(p1, p2, p3), List.of(), new TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        return spec.build(
                                ib,
                                TypedJavaFile.<B1>variable(ib, 0),
                                TypedJavaFile.<B2>variable(ib, 1),
                                TypedJavaFile.<B3>variable(ib, 2));
                    }
                });
    }

    private static void requireInterfaceName(ClassDesc desc) {
        Scopes.requireNoneOpen(
                "interface " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
    }

    private static <Self> JavaFile declaredInterface(
            TargetClasspath target,
            ClassDesc desc,
            List<TypeParameter<?>> parameters,
            List<me.supcheg.javafile.facts.DeclarationIdentity> enclosing,
            TypedInterfaceSpec spec) {
        TypedInterfaceBuilder<Self> builder = new TypedInterfaceBuilder<>(target, desc, parameters, enclosing);
        return builder.complete(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the interface"));
    }

    /// The specification of an interface (§6.5).
    public interface TypedInterfaceSpec {
        /// Declares the interface through `ib`, branded with a fresh `Self`.
        ///
        /// @param ib the self-branded interface builder
        /// @param <Self> the brand of the interface being declared
        /// @return the bodies of the members: the stage the declaration ends in
        <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib);
    }

    /// The specification of a generic interface with one type parameter, whose leftmost bound is `B1`.
    ///
    /// @param <B1> the leftmost bound of the type parameter, `Object` for none
    public interface TypedGenericInterfaceSpec1<B1> {
        /// Declares the interface through `ib`, branded with a fresh `Self`, with the token of its type
        /// parameter, branded with a fresh `T1`.
        ///
        /// @param ib the self-branded interface builder
        /// @param t1 the token of the type parameter
        /// @param <Self> the brand of the interface being declared
        /// @param <T1> the brand of the type parameter
        /// @return the bodies of the members: the stage the declaration of the interface ends in
        <Self, T1 extends B1> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1);
    }

    /// The specification of a generic interface with two type parameters, see [TypedGenericInterfaceSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    public interface TypedGenericInterfaceSpec2<B1, B2> {
        /// Declares the interface through `ib`, with the tokens of its type parameters.
        ///
        /// @param ib the self-branded interface builder
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param <Self> the brand of the interface being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @return the bodies of the members
        <Self, T1 extends B1, T2 extends B2> TypedInterfaceBodies<Self> build(
                TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /// The specification of a generic interface with three type parameters, see [TypedGenericInterfaceSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    /// @param <B3> the leftmost bound of the third type parameter, `Object` for none
    public interface TypedGenericInterfaceSpec3<B1, B2, B3> {
        /// Declares the interface through `ib`, with the tokens of its type parameters.
        ///
        /// @param ib the self-branded interface builder
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param t3 the token of the third type parameter
        /// @param <Self> the brand of the interface being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @param <T3> the brand of the third type parameter
        /// @return the bodies of the members
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> TypedInterfaceBodies<Self> build(
                TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /// Declares a `public` record, from its components and members.
    ///
    /// @param target the classpath the record is compiled against
    /// @param desc the record's binary name
    /// @param spec declares the record, given its self-branded builder
    /// @return the rendered record
    /// @throws IllegalArgumentException if `desc` describes a nested type, or its package or simple name cannot
    ///     be written in Java
    /// @throws IllegalStateException if a member `spec` declared is not defined
    public static JavaFile record(TargetClasspath target, ClassDesc desc, TypedRecordSpec spec) {
        requireRecordName(desc);
        return declaredRecord(target, declarable(desc, "record"), List.of(), List.of(), spec);
    }

    /// Declares a generic `public` record with one type parameter: the token is in scope in its
    /// components, its header, its members and its member types.
    ///
    /// @param target the classpath the record is compiled against
    /// @param desc the record's binary name
    /// @param p1 the type parameter
    /// @param spec declares the record, given its builder and the token of the type parameter
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the rendered record
    /// @throws IllegalArgumentException if a type parameter has the name of another or of the record, or
    ///     its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
    public static <B1> JavaFile record(
            TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericRecordSpec1<B1> spec) {
        requireRecordName(desc);
        return declaredRecord(target, declarable(desc, "record"), List.of(p1), List.of(), new TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                return spec.build(rb, TypedJavaFile.<B1>variable(rb, 0));
            }
        });
    }

    /// Declares a generic `public` record with two type parameters, see
    /// [#record(TargetClasspath, ClassDesc, TypeParameter, TypedGenericRecordSpec1)].
    ///
    /// @param target the classpath the record is compiled against
    /// @param desc the record's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the rendered record
    public static <B1, B2> JavaFile record(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedGenericRecordSpec2<B1, B2> spec) {
        requireRecordName(desc);
        return declaredRecord(target, declarable(desc, "record"), List.of(p1, p2), List.of(), new TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                return spec.build(rb, TypedJavaFile.<B1>variable(rb, 0), TypedJavaFile.<B2>variable(rb, 1));
            }
        });
    }

    /// Declares a generic `public` record with three type parameters, see
    /// [#record(TargetClasspath, ClassDesc, TypeParameter, TypedGenericRecordSpec1)].
    ///
    /// @param target the classpath the record is compiled against
    /// @param desc the record's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the rendered record
    public static <B1, B2, B3> JavaFile record(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedGenericRecordSpec3<B1, B2, B3> spec) {
        requireRecordName(desc);
        return declaredRecord(
                target, declarable(desc, "record"), List.of(p1, p2, p3), List.of(), new TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        return spec.build(
                                rb,
                                TypedJavaFile.<B1>variable(rb, 0),
                                TypedJavaFile.<B2>variable(rb, 1),
                                TypedJavaFile.<B3>variable(rb, 2));
                    }
                });
    }

    private static void requireRecordName(ClassDesc desc) {
        Scopes.requireNoneOpen(
                "record " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
    }

    private static <Self> JavaFile declaredRecord(
            TargetClasspath target,
            ClassDesc desc,
            List<TypeParameter<?>> parameters,
            List<me.supcheg.javafile.facts.DeclarationIdentity> enclosing,
            TypedRecordSpec spec) {
        TypedRecordBuilder<Self> builder = new TypedRecordBuilder<>(target, desc, parameters, enclosing);
        return builder.complete(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the record"));
    }

    /// The specification of a record (§6.5).
    public interface TypedRecordSpec {
        /// Declares the record through `rb`, branded with a fresh `Self`.
        ///
        /// @param rb the self-branded record builder
        /// @param <Self> the brand of the record being declared
        /// @return the bodies of the members: the stage the declaration ends in
        <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb);
    }

    /// The specification of a generic record with one type parameter, whose leftmost bound is `B1`.
    ///
    /// @param <B1> the leftmost bound of the type parameter, `Object` for none
    public interface TypedGenericRecordSpec1<B1> {
        /// Declares the record through `rb`, branded with a fresh `Self`, with the token of its type parameter.
        ///
        /// @param rb the self-branded record builder
        /// @param t1 the token of the type parameter
        /// @param <Self> the brand of the record being declared
        /// @param <T1> the brand of the type parameter
        /// @return the bodies of the members: the stage the declaration of the record ends in
        <Self, T1 extends B1> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1);
    }

    /// The specification of a generic record with two type parameters, see [TypedGenericRecordSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    public interface TypedGenericRecordSpec2<B1, B2> {
        /// Declares the record through `rb`, with the tokens of its type parameters.
        ///
        /// @param rb the self-branded record builder
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param <Self> the brand of the record being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @return the bodies of the members
        <Self, T1 extends B1, T2 extends B2> TypedRecordBodies<Self> build(
                TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /// The specification of a generic record with three type parameters, see [TypedGenericRecordSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    /// @param <B3> the leftmost bound of the third type parameter, `Object` for none
    public interface TypedGenericRecordSpec3<B1, B2, B3> {
        /// Declares the record through `rb`, with the tokens of its type parameters.
        ///
        /// @param rb the self-branded record builder
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param t3 the token of the third type parameter
        /// @param <Self> the brand of the record being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @param <T3> the brand of the third type parameter
        /// @return the bodies of the members
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> TypedRecordBodies<Self> build(
                TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /// Declares a `public` enum, from its constants and members.
    ///
    /// @param target the classpath the enum is compiled against
    /// @param desc the enum's binary name
    /// @param spec declares the enum, given its self-branded builder
    /// @return the rendered enum
    /// @throws IllegalArgumentException if `desc` describes a nested type, or its package or simple name cannot
    ///     be written in Java
    /// @throws IllegalStateException if a member `spec` declared is not defined
    public static JavaFile enum_(TargetClasspath target, ClassDesc desc, TypedEnumSpec spec) {
        Scopes.requireNoneOpen(
                "enum " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
        return declaredEnum(target, declarable(desc, "enum"), spec);
    }

    private static <Self> JavaFile declaredEnum(TargetClasspath target, ClassDesc desc, TypedEnumSpec spec) {
        TypedEnumBuilder<Self> builder = new TypedEnumBuilder<>(target, desc);
        return builder.complete(Objects.requireNonNull(
                spec.build(builder), "a specification returns the bodies of the members of the enum"));
    }

    /// Refuses a generic enum: an enum cannot have type parameters (JLS 8.9), and an enum constant
    /// would name one in its class body, where javac rejects it.
    ///
    /// @param target the classpath the enum would be compiled against
    /// @param desc the enum's binary name
    /// @param parameter the type parameter
    /// @param spec declares the enum, which is never called
    /// @return never
    /// @throws IllegalArgumentException always, naming the enum and the type parameter
    public static JavaFile enum_(
            TargetClasspath target, ClassDesc desc, TypeParameter<?> parameter, TypedEnumSpec spec) {
        throw genericKind("enum", desc, "an enum has no type parameters (JLS 8.9)");
    }

    /// The specification of an enum (§6.5).
    public interface TypedEnumSpec {
        /// Declares the enum through `eb`, branded with a fresh `Self`.
        ///
        /// @param eb the self-branded enum builder
        /// @param <Self> the brand of the enum being declared
        /// @return the bodies of the members: the stage the declaration ends in
        <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb);
    }

    /// Declares a `public` annotation type, from its elements and meta-annotations.
    ///
    /// @param target the classpath the annotation type is compiled against
    /// @param desc the annotation type's binary name
    /// @param spec declares the annotation type, given its self-branded builder
    /// @return the rendered annotation type
    /// @throws IllegalArgumentException if `desc` describes a nested type, or its package or simple name cannot
    ///     be written in Java
    public static JavaFile annotationType(TargetClasspath target, ClassDesc desc, TypedAnnotationTypeSpec spec) {
        Scopes.requireNoneOpen("annotation type " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".")
                + desc.displayName());
        return declaredAnnotationType(target, declarable(desc, "annotation type"), spec);
    }

    /// Refuses a generic annotation type: an annotation type cannot have type parameters (JLS 9.6), and
    /// an element or a meta-annotation would name one, where javac rejects it.
    ///
    /// @param target the classpath the annotation type would be compiled against
    /// @param desc the annotation type's binary name
    /// @param parameter the type parameter
    /// @param spec declares the annotation type, which is never called
    /// @return never
    /// @throws IllegalArgumentException always, naming the annotation type and the type parameter
    public static JavaFile annotationType(
            TargetClasspath target, ClassDesc desc, TypeParameter<?> parameter, TypedAnnotationTypeSpec spec) {
        throw genericKind("annotation type", desc, "an annotation type has no type parameters (JLS 9.6)");
    }

    private static IllegalArgumentException genericKind(String kind, ClassDesc desc, String why) {
        String described =
                desc.packageName().isEmpty() ? desc.displayName() : desc.packageName() + "." + desc.displayName();
        return new IllegalArgumentException("cannot declare the " + kind + " " + described + " with a type parameter: "
                + why + ", and the typed layer declares only what javac accepts");
    }

    private static <Self> JavaFile declaredAnnotationType(
            TargetClasspath target, ClassDesc desc, TypedAnnotationTypeSpec spec) {
        TypedAnnotationTypeBuilder<Self> builder = new TypedAnnotationTypeBuilder<>(target, desc);
        Objects.requireNonNull(spec.build(builder), "a specification returns the builder of the annotation type");
        return builder.complete();
    }

    /// The specification of an annotation type (§6.5).
    public interface TypedAnnotationTypeSpec {
        /// Declares the annotation type through `ab`, branded with a fresh `Self`.
        ///
        /// @param ab the self-branded annotation type builder
        /// @param <Self> the brand of the annotation type being declared
        /// @return the builder, complete
        <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab);
    }

    /// Declares a `package-info.java` for a package, from its annotations.
    ///
    /// @param target the classpath the package annotations are checked against
    /// @param packageName the annotated package
    /// @param spec populates the annotations
    /// @return the package-info file
    /// @throws IllegalArgumentException if the package is unnamed, or an annotation type cannot annotate a
    ///     package
    public static PackageInfoFile packageInfo(TargetClasspath target, String packageName, TypedPackageInfoSpec spec) {
        if (packageName.isEmpty()) {
            throw new IllegalArgumentException(
                    "the unnamed package has no package declaration, so no package-info.java");
        }
        Scopes.requireNoneOpen("package " + packageName);
        TypedPackageInfoBuilder builder = new TypedPackageInfoBuilder(target, packageName);
        spec.build(builder);
        return builder.complete();
    }

    /// The specification of a `package-info.java` (§6.5).
    public interface TypedPackageInfoSpec {
        /// Adds the annotations of the package.
        ///
        /// @param pb the builder
        void build(TypedPackageInfoBuilder pb);
    }

    /// Declares a `module-info.java`, from its directives.
    ///
    /// @param target the classpath the module is checked against
    /// @param moduleName the declared module
    /// @param spec populates the directives
    /// @return the module-info file
    /// @throws IllegalArgumentException if a directive is one the core rejects
    public static ModuleFile module(TargetClasspath target, String moduleName, TypedModuleSpec spec) {
        Scopes.requireNoneOpen("module " + moduleName);
        TypedModuleBuilder builder = new TypedModuleBuilder(moduleName);
        spec.build(builder);
        return builder.complete();
    }

    /// The specification of a `module-info.java` (§6.5).
    public interface TypedModuleSpec {
        /// Adds the directives of the module.
        ///
        /// @param mb the builder
        void build(TypedModuleBuilder mb);
    }

    /// A type parameter without a bound, `<T>`.
    ///
    /// @param name its name
    /// @return the type parameter
    /// @throws IllegalArgumentException if `name` cannot name a type (JLS 3.8): it is no identifier, or one
    ///     of `permits`, `record`, `sealed`, `var` and `yield`
    public static TypeParameter<Object> typeParam(String name) {
        return TypeParameter.unbounded(name);
    }

    /// A type parameter with a leftmost bound, `<N extends Number>`: a class or an interface, with
    /// its type arguments if it is generic. Further bounds, and one that mentions a type parameter
    /// of the class, are said in the header ([ClassHeader#bound]).
    ///
    /// @param name its name
    /// @param bound its leftmost bound
    /// @param <B> the bound
    /// @return the type parameter
    /// @throws IllegalArgumentException if `name` cannot name a type (JLS 3.8): it is no identifier, or one
    ///     of `permits`, `record`, `sealed`, `var` and `yield`
    public static <B> TypeParameter<B> typeParam(String name, DeclaredToken<B> bound) {
        return TypeParameter.bounded(name, bound);
    }

    /// Declares a generic `public final` class with one type parameter, from its header on:
    /// `spec` receives the header, branded with a `Self` unique to this call, and the token of
    /// the type parameter, branded with a type variable of its own whose bound is that of the
    /// type parameter.
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.declareClass(
    ///         target, desc, typeParam("T", Number_.TOKEN), new TypedJavaFile.TypedGenericClassSpec1<Number>() {
    ///     public <Self, T extends Number> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
    ///         var cb = h.extendsObject().members();
    ///         var item = cb.declareField("item", t);
    ///         var asInt = cb.declareMethod("asInt", INT);
    ///         var bodies = cb.bodies();
    ///         bodies.initialize(item, literalNull(t));
    ///         bodies.define(asInt, (b, self) -> b.return_(call(field(self, item), Number_.intValue)));
    ///         return bodies;
    ///     }
    /// });
    /// // public final class Box<T extends Number> { ... }
    /// ```
    ///
    /// `Self` is the class with its own type parameters for its type arguments, `Box<T>` — the
    /// type of `this`, and the only type of the class there is a token of: `Box<String>` and
    /// `Box<?>` are not expressible. A type parameter is in scope in the header and the instance
    /// members of the class: mentioned in a `static` member, or in another class, it is rejected
    /// (JLS 8.1.2, 6.3).
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param p1 the type parameter
    /// @param spec declares the class, given the first stage of its header and the token of its type parameter
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the rendered class
    /// @throws IllegalArgumentException if the type parameter has the simple name of the class, or its
    ///     bound is a raw type
    /// @throws IllegalArgumentException if `desc` describes a nested class, or a class of a package of
    ///     `java`, or its package or simple name cannot be written in Java (a keyword, `var`, ...) — before
    ///     `spec` is called
    /// @throws IllegalStateException as [#declareClass(TargetClasspath, ClassDesc, TypedClassHeaderSpec)]
    /// @throws TargetClasspathMismatchException if a metamodel the class uses does not hold on `target`
    /// @throws FactLookupException if a member the class uses has an overload on `target` that no
    ///     argument tells it from
    public static <B1> JavaFile declareClass(
            TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericClassSpec1<B1> spec) {
        return declared(target, desc, List.of(p1), new Generic() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), TypedJavaFile.<B1>variable(declaration, 0));
            }
        });
    }

    /// Declares a generic `public final` class with two type parameters, see
    /// [#declareClass(TargetClasspath, ClassDesc, TypeParameter, TypedGenericClassSpec1)].
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the class, given the first stage of its header and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the rendered class
    /// @throws IllegalArgumentException if a type parameter has the name of the other or of the class, or
    ///     its bound is a raw type
    public static <B1, B2> JavaFile declareClass(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedGenericClassSpec2<B1, B2> spec) {
        return declared(target, desc, List.of(p1, p2), new Generic() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(
                        new ClassHeader<>(declaration),
                        TypedJavaFile.<B1>variable(declaration, 0),
                        TypedJavaFile.<B2>variable(declaration, 1));
            }
        });
    }

    /// Declares a generic `public final` class with three type parameters, see
    /// [#declareClass(TargetClasspath, ClassDesc, TypeParameter, TypedGenericClassSpec1)].
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the class, given the first stage of its header and the tokens of its type parameters
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the rendered class
    /// @throws IllegalArgumentException if a type parameter has the name of another or of the class, or
    ///     its bound is a raw type
    public static <B1, B2, B3> JavaFile declareClass(
            TargetClasspath target,
            ClassDesc desc,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedGenericClassSpec3<B1, B2, B3> spec) {
        return declared(target, desc, List.of(p1, p2, p3), new Generic() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(
                        new ClassHeader<>(declaration),
                        TypedJavaFile.<B1>variable(declaration, 0),
                        TypedJavaFile.<B2>variable(declaration, 1),
                        TypedJavaFile.<B3>variable(declaration, 2));
            }
        });
    }

    /// The token of a type parameter of a declaration, as a specification takes it: of a type
    /// variable of the generator the call of `build` makes anew, bounded by the leftmost bound.
    @SuppressWarnings("unchecked")
    private static <B> TypeVarToken<? extends B> variable(ClassDeclaration<?> declaration, int index) {
        return (TypeVarToken<? extends B>) declaration.variable(index);
    }

    /// The token of a type parameter of an interface or a record, as a specification takes it.
    @SuppressWarnings("unchecked")
    private static <B> TypeVarToken<? extends B> variable(TypeDeclarationSupport<?> declaration, int index) {
        return (TypeVarToken<? extends B>) declaration.variable(index);
    }

    /// What declares a class, given its declaration branded with a `Self` of its own.
    private interface Generic {
        <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration);
    }

    private static JavaFile declared(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, Generic spec) {
        Scopes.requireNoneOpen(
                "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
        return declare(target, declarable(desc), parameters, spec);
    }

    /// The class `desc` describes, if the typed layer declares it: a top-level class, whose package and
    /// simple name Java source writes as they are (JLS 3.8, 7.4.1), and of no package of the platform.
    ///
    /// @throws IllegalArgumentException if `desc` describes a nested class, or a name of it cannot be written
    private static ClassDesc declarable(ClassDesc desc) {
        return declarable(desc, "class");
    }

    /// The type `desc` describes, if the typed layer declares it: a top-level type of the kind `kind`,
    /// whose package and simple name Java source writes as they are (JLS 3.8, 7.4.1), and of no package
    /// of the platform.
    ///
    /// @throws IllegalArgumentException if `desc` describes a nested type, or a name of it cannot be written
    private static ClassDesc declarable(ClassDesc desc, String kind) {
        String described =
                desc.packageName().isEmpty() ? desc.displayName() : desc.packageName() + "." + desc.displayName();
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

    private static <Self> JavaFile declare(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, Generic spec) {
        ClassDeclaration<Self> declaration = new ClassDeclaration<>(target, desc, parameters);
        return declaration.complete(Objects.requireNonNull(
                spec.build(declaration),
                "a specification returns the bodies of the members of the class, the stage its declarations end in"));
    }

    /// The specification of a generic class with one type parameter, whose leftmost bound is `B1`.
    ///
    /// @param <B1> the leftmost bound of the type parameter, `Object` for none
    public interface TypedGenericClassSpec1<B1> {
        /// Declares the class through `h`, branded with a fresh `Self`, with the token of its type
        /// parameter, branded with a fresh `T1`.
        ///
        /// @param h the first stage of the header of the class
        /// @param t1 the token of the type parameter
        /// @param <Self> the brand of the class being declared: the class with `T1` for its type argument
        /// @param <T1> the brand of the type parameter
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self, T1 extends B1> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1);
    }

    /// The specification of a generic class with two type parameters, see [TypedGenericClassSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    public interface TypedGenericClassSpec2<B1, B2> {
        /// Declares the class through `h`, with the tokens of its type parameters.
        ///
        /// @param h the first stage of the header of the class
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param <Self> the brand of the class being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self, T1 extends B1, T2 extends B2> MemberBodies<Self, ?> build(
                ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /// The specification of a generic class with three type parameters, see [TypedGenericClassSpec1].
    ///
    /// @param <B1> the leftmost bound of the first type parameter, `Object` for none
    /// @param <B2> the leftmost bound of the second type parameter, `Object` for none
    /// @param <B3> the leftmost bound of the third type parameter, `Object` for none
    public interface TypedGenericClassSpec3<B1, B2, B3> {
        /// Declares the class through `h`, with the tokens of its type parameters.
        ///
        /// @param h the first stage of the header of the class
        /// @param t1 the token of the first type parameter
        /// @param t2 the token of the second type parameter
        /// @param t3 the token of the third type parameter
        /// @param <Self> the brand of the class being declared
        /// @param <T1> the brand of the first type parameter
        /// @param <T2> the brand of the second type parameter
        /// @param <T3> the brand of the third type parameter
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> MemberBodies<Self, ?> build(
                ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /// A self-branded class specification that begins with the header of the class.
    ///
    /// It returns the bodies of the members, which only the stages of the declaration give, each
    /// from the one before: that a header came to its members and the members to their bodies is
    /// held by the compiler of the generator, and the brand makes the bodies those of this class.
    public interface TypedClassHeaderSpec {
        /// Declares the class through `h`, branded with a fresh `Self` unique to this declaration.
        ///
        /// @param h the first stage of the header of the class
        /// @param <Self> the brand of the class being declared
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h);
    }

    /// A self-branded class specification (§3.1, §6.5) of a class that extends `Object` and
    /// implements nothing: its members alone.
    public interface TypedClassSpec {
        /// Populates the class through `cb`, branded with a fresh `Self`
        /// unique to this declaration.
        ///
        /// @param cb the self-branded class builder
        /// @param <Self> the brand of the class being declared
        /// @return the bodies of the members: the stage the declaration of the class ends in
        <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb);
    }
}
