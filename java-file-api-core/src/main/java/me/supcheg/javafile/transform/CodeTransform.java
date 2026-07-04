package me.supcheg.javafile.transform;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Stmt;

import java.util.function.BiConsumer;

/// A transform over [Stmt]s.
///
/// [Transforms#transform(CodeBody,CodeTransform)] invokes the transform once
/// per statement of the source body; the transform decides whether to pass
/// the statement through unchanged, replace it, drop it, or add new
/// statements, all by calling (or not calling) `accept` on the supplied
/// builder. The result is a new body; the source body is not modified.
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
