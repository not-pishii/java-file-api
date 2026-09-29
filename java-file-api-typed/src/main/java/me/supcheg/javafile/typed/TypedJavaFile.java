package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.Modifier;

import java.lang.constant.ClassDesc;
import java.util.List;

/// Entry point of the typed eDSL (§6.5): declares a generated `final` class
/// through a self-branded [TypedClassBuilder].
public final class TypedJavaFile {
    private TypedJavaFile() {}

    /// Declares a `public final` class, extending `Object`. `spec` is implemented as an anonymous class
    /// (a generic method is not expressible as a lambda) to receive a
    /// [TypedClassBuilder] branded with a `Self` unique to this call — the
    /// same CPS-brand technique as `Facts.withToken` (§3.1):
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.class_(desc, new TypedJavaFile.TypedClassSpec() {
    ///     public <Self> void build(TypedClassBuilder<Self> cb) {
    ///         var x = cb.field("x", PrimitiveToken.INT, literal(1));
    ///         cb.method("x2", PrimitiveToken.INT, (b, self) -> b.return_(addInt(field(self, x), field(self, x))));
    ///         ...
    ///     }
    /// });
    /// ```
    ///
    /// @param desc the class's binary name
    /// @param spec populates the class, given its self-branded builder
    /// @return the rendered class, ready for [JavaFile#render()]/[JavaFile#writeTo(java.nio.file.Path)]
    /// @throws IllegalStateException if called while a method body is being built, or if a
    ///     member `spec` declared is not defined
    public static JavaFile class_(ClassDesc desc, TypedClassSpec spec) {
        Scopes.requireNoneOpen(
                "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName());
        List<ClassMember> members = declare(desc, spec);
        return JavaFile.class_(desc, cb -> {
            cb.withModifiers(Modifier.FINAL);
            members.forEach(cb);
        });
    }

    private static <Self> List<ClassMember> declare(ClassDesc desc, TypedClassSpec spec) {
        TypedClassBuilder<Self> builder = new TypedClassBuilder<>(desc);
        spec.build(builder);
        return builder.complete();
    }

    /// A self-branded class specification (§3.1, §6.5).
    public interface TypedClassSpec {
        /// Populates the class through `cb`, branded with a fresh `Self`
        /// unique to this declaration.
        ///
        /// @param cb the self-branded class builder
        /// @param <Self> the brand of the class being declared
        <Self> void build(TypedClassBuilder<Self> cb);
    }
}
