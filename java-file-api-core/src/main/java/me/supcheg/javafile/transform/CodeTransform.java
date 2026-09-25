package me.supcheg.javafile.transform;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Stmt;

import java.util.function.BiConsumer;

/// Rewrites the statements of a method or constructor body. Called once per
/// top-level statement; what you pass to `builder.accept(...)` ends up in the
/// result.
///
/// Forward a statement with `builder.accept(statement)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add statements. The original body is left unchanged.
///
/// Apply it with [Transforms#transform(CodeBody,CodeTransform)].
@FunctionalInterface
public interface CodeTransform extends BiConsumer<CodeBuilder, Stmt> {
    /// Returns a transform that applies this transform, then `next`, to each statement.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default CodeTransform andThen(CodeTransform next) {
        return (builder, stmt) -> {
            accept(builder, stmt);
            next.accept(builder, stmt);
        };
    }
}
