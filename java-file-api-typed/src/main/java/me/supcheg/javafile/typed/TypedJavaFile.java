package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.model.Modifier;

import java.lang.constant.ClassDesc;

/// Entry point of the typed eDSL (§6.5): declares a generated `final` class
/// through a self-branded [TypedClassBuilder].
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
    /// a [TypedClassHeaderSpec] that goes `h.extendsObject().body()`. `spec` is implemented as an anonymous class
    /// (a generic method is not expressible as a lambda) to receive a
    /// [TypedClassBuilder] branded with a `Self` unique to this call — the
    /// same CPS-brand technique as `Facts.withToken` (§3.1):
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.class_(target, desc, new TypedJavaFile.TypedClassSpec() {
    ///     public <Self> void build(TypedClassBuilder<Self> cb) {
    ///         var x = cb.field("x", PrimitiveToken.INT, literal(1));
    ///         cb.method("x2", PrimitiveToken.INT, (b, self) -> b.return_(addInt(field(self, x), field(self, x))));
    ///         ...
    ///     }
    /// });
    /// ```
    ///
    /// @param target the classpath the class is compiled against: every metamodel the class is
    ///     rendered of is checked against it, once, when lowering first meets it
    /// @param desc the class's binary name
    /// @param spec populates the class, given its self-branded builder
    /// @return the rendered class, ready for [JavaFile#render()]/[JavaFile#writeTo(java.nio.file.Path)]
    /// @throws IllegalStateException if called while a method body is being built, or if a
    ///     member `spec` declared is not defined
    /// @throws TargetClasspathMismatchException if a metamodel the class uses does not hold on `target`:
    ///     the generator was compiled against another version of the type
    /// @throws FactLookupException if a member the class uses has an overload on `target` that no
    ///     argument tells it from; as the mismatch is, a [me.supcheg.javafile.facts.FactException]
    public static JavaFile class_(TargetClasspath target, ClassDesc desc, TypedClassSpec spec) {
        return declareClass(target, desc, new TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                spec.build(h.extendsObject().body());
            }
        });
    }

    /// Declares a `public final` class from its header on: `spec` receives the first stage of
    /// the header ([Header]), branded with a `Self` unique to this call, says what the class
    /// extends and implements, and declares the members with the builder the header ends in:
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.declareClass(target, desc, new TypedJavaFile.TypedClassHeaderSpec() {
    ///     public <Self> void build(ClassHeader<Self> h) {
    ///         var cmp = new Comparable_<>(h.self());
    ///         var compareTo = h.overriding(cmp.compareTo_T);
    ///         var oh = h.extendsObject();
    ///         var asComparable = oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
    ///         var cb = oh.body();
    ///         cb.define(compareTo.method(), (b, self, other) -> b.return_(literal(0)));
    ///     }
    /// });
    /// ```
    ///
    /// @param target the classpath the class is compiled against
    /// @param desc the class's binary name
    /// @param spec declares the class, given the first stage of its header
    /// @return the rendered class
    /// @throws IllegalStateException if called while a method body is being built, if the header
    ///     never came to the members, or if a member `spec` declared is not defined
    /// @throws TargetClasspathMismatchException if a metamodel the class uses does not hold on `target`
    /// @throws FactLookupException if a member the class uses has an overload on `target` that no
    ///     argument tells it from
    public static JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypedClassHeaderSpec spec) {
        Scopes.requireNoneOpen(
                "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
        ClassDeclaration.Declared declared = declare(target, desc, spec);
        return JavaFile.class_(desc, cb -> {
            cb.withModifiers(Modifier.FINAL);
            declared.interfaces().forEach(cb::withInterface);
            declared.members().forEach(cb);
        });
    }

    private static <Self> ClassDeclaration.Declared declare(
            TargetClasspath target, ClassDesc desc, TypedClassHeaderSpec spec) {
        ClassDeclaration<Self> declaration = new ClassDeclaration<>(target, desc);
        spec.build(new ClassHeader<>(declaration));
        return declaration.complete();
    }

    /// A self-branded class specification that begins with the header of the class.
    public interface TypedClassHeaderSpec {
        /// Declares the class through `h`, branded with a fresh `Self` unique to this declaration.
        ///
        /// @param h the first stage of the header of the class
        /// @param <Self> the brand of the class being declared
        <Self> void build(ClassHeader<Self> h);
    }

    /// A self-branded class specification (§3.1, §6.5) of a class that extends `Object` and
    /// implements nothing: its members alone.
    public interface TypedClassSpec {
        /// Populates the class through `cb`, branded with a fresh `Self`
        /// unique to this declaration.
        ///
        /// @param cb the self-branded class builder
        /// @param <Self> the brand of the class being declared
        <Self> void build(TypedClassBuilder<Self> cb);
    }
}
