package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.AnnotationTypeBuilder;
import me.supcheg.javafile.model.AnnotationElementDecl;

import java.util.function.BiConsumer;

/// Rewrites the elements of an annotation type. Called once per element;
/// what you pass to `builder.accept(...)` ends up in the result.
///
/// Forward a element with `builder.accept(element)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add elements. The original declaration is left unchanged.
///
/// Apply it with [me.supcheg.javafile.JavaFile#transformAnnotationType(AnnotationTypeTransform)].
@FunctionalInterface
public interface AnnotationTypeTransform extends BiConsumer<AnnotationTypeBuilder, AnnotationElementDecl> {
    /// Returns a transform that applies this transform, then `next`, to each element.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default AnnotationTypeTransform andThen(AnnotationTypeTransform next) {
        return (builder, element) -> {
            accept(builder, element);
            next.accept(builder, element);
        };
    }
}
