package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;

import java.lang.constant.ClassDesc;
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
///
/// The entry points of a generic class, interface or record, and the specification stages they take,
/// are generated into [GenericDeclarationArities] for every number of type parameters from one to
/// twelve: the class extends it, so `TypedJavaFile.declareClass(...)` and
/// `new TypedJavaFile.TypedGenericClassSpec4<>()` read here as they read there.
public final class TypedJavaFile extends GenericDeclarationArities {
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
        return GenericDeclarations.class_(target, desc, new TypedClassHeaderSpec() {
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
        return GenericDeclarations.class_(target, desc, spec);
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
        return GenericDeclarations.interface_(target, desc, spec);
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
        return GenericDeclarations.record(target, desc, spec);
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
        return declaredEnum(target, GenericDeclarations.declarable(desc, "enum"), spec);
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
        return declaredAnnotationType(target, GenericDeclarations.declarable(desc, "annotation type"), spec);
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

    /// The specification of an inner member class, which receives the handle that ties it to the
    /// class that encloses it: the handle gives `Outer.this` and an inner instance created from an
    /// outer one ([InnerClass]).
    ///
    /// @param <Outer> the class the inner class is declared in
    public interface TypedInnerClassSpec<Outer> {
        /// Declares the inner class through `h`, branded with a fresh `Inner` unique to this
        /// declaration.
        ///
        /// @param h the first stage of the header of the inner class
        /// @param inner the handle of the inner class, tied to the class that encloses it
        /// @param <Inner> the brand of the inner class being declared
        /// @return the bodies of the members: the stage the declaration of the inner class ends in
        <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> h, InnerClass<Outer, Inner> inner);
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
