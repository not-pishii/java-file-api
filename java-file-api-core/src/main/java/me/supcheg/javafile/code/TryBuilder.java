package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for a [TryStmt]'s resources, `catch` clauses, and
/// `finally` block.
///
/// Instances are created by [CodeBuilder#try_(Consumer,Consumer)] and are not
/// meant to be instantiated directly.
///
/// Instances are not thread-safe.
public final class TryBuilder {

    private final List<Resource> resources = new ArrayList<>();
    private final List<CatchClause> catches = new ArrayList<>();
    private CodeBody finallyBody;

    TryBuilder() {}

    /// Declares a typed try-with-resources resource, e.g. `Reader r = open()`.
    ///
    /// @param name the resource variable name
    /// @param type the declared resource type
    /// @param initializer the initializer expression
    /// @return this builder
    public TryBuilder resource_(String name, TypeRef type, Expr initializer) {
        resources.add(new Resource.Declared(Optional.of(type), name, initializer));
        return this;
    }

    /// Declares an inferred (`var`) try-with-resources resource, e.g. `var r = open()`.
    ///
    /// @param name the resource variable name
    /// @param initializer the initializer expression
    /// @return this builder
    public TryBuilder resource_(String name, Expr initializer) {
        resources.add(new Resource.Declared(Optional.empty(), name, initializer));
        return this;
    }

    /// References an existing effectively-final variable as a resource,
    /// e.g. `try (existingReader) { ... }`.
    ///
    /// @param existingVarName the referenced variable's name
    /// @return this builder
    public TryBuilder resource_(String existingVarName) {
        resources.add(new Resource.Existing(existingVarName));
        return this;
    }

    /// Appends a `catch` clause. May be called more than once; multiple
    /// `types` model a multi-catch (`A | B`).
    ///
    /// @param types the caught exception types, in order; at least one
    /// @param paramName the caught exception's parameter name
    /// @param spec receives the builder to populate the clause's body
    /// @return this builder
    /// @throws IllegalArgumentException if `types` is empty
    public TryBuilder catch_(List<ClassOrInterfaceTypeRef> types, String paramName, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        catches.add(new CatchClause(NonEmptyList.copyOf(types), paramName, cb.build()));
        return this;
    }

    /// Sets the `finally` block.
    ///
    /// @param spec receives the builder to populate the `finally` body
    /// @return this builder
    public TryBuilder finally_(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.finallyBody = cb.build();
        return this;
    }

    /// Builds the [TryStmt], choosing [TryStmt.WithFinally] if [#finally_]
    /// was called, otherwise [TryStmt.CatchOnly].
    ///
    /// @param block the try block's body
    /// @return the built statement
    /// @throws IllegalArgumentException if neither [#catch_] nor [#finally_] was called
    TryStmt build(CodeBody block) {
        List<Resource> resourcesCopy = List.copyOf(resources);
        if (finallyBody != null) {
            return new TryStmt.WithFinally(resourcesCopy, block, List.copyOf(catches), finallyBody);
        }
        if (catches.isEmpty()) {
            throw new IllegalArgumentException("try requires at least one catch clause or a finally block");
        }
        return new TryStmt.CatchOnly(resourcesCopy, block, NonEmptyList.copyOf(catches));
    }
}
