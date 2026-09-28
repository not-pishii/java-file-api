package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.type.ClassTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// Entry point of the typed eDSL (§6.5): declares a generated class through
/// a self-branded [TypedClassBuilder].
public final class TypedJavaFile {
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");

    private TypedJavaFile() {}

    /// Declares a `final` class. `spec` is implemented as an anonymous class
    /// (a generic method is not expressible as a lambda) to receive a
    /// [TypedClassBuilder] branded with a `Self` unique to this call — the
    /// same CPS-brand technique as `Facts.withToken` (§3.1):
    ///
    /// ```java
    /// JavaFile file = TypedJavaFile.class_(desc, new TypedJavaFile.TypedClassSpec() {
    ///     public <Self> void build(TypedClassBuilder<Self> cb) {
    ///         var x = cb.field("x", PrimitiveToken.INT, literal(1));
    ///         ...
    ///     }
    /// });
    /// ```
    ///
    /// @param desc the class's binary name
    /// @param spec populates the class, given its self-branded builder
    /// @return the rendered class, ready for [JavaFile#render()]/[JavaFile#writeTo(java.nio.file.Path)]
    public static JavaFile class_(ClassDesc desc, TypedClassSpec spec) {
        return JavaFile.class_(desc, cb -> declare(desc, cb, spec));
    }

    private static <Self> void declare(
            ClassDesc desc, me.supcheg.javafile.builder.ClassBuilder cb, TypedClassSpec spec) {
        ClassTypeRef typeRef = new ClassTypeRef(desc);
        FinalClassToken<Self> self = FinalClassToken.introduce(typeRef, List.of(OBJECT), MethodTable.EMPTY);
        spec.build(new TypedClassBuilder<>(cb, self));
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
