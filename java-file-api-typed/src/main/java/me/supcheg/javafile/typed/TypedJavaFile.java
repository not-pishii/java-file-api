package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeVarToken;

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

    /// A type parameter without a bound, `<T>`.
    ///
    /// @param name its name
    /// @return the type parameter
    /// @throws IllegalArgumentException if `name` is no identifier
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
    /// @throws IllegalArgumentException if `name` is no identifier
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

    /// What declares a class, given its declaration branded with a `Self` of its own.
    private interface Generic {
        <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration);
    }

    private static JavaFile declared(
            TargetClasspath target, ClassDesc desc, List<TypeParameter<?>> parameters, Generic spec) {
        Scopes.requireNoneOpen(
                "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
        return declare(target, desc, parameters, spec);
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
