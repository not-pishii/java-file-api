package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.AnnotationTypeBuilder;
import me.supcheg.javafile.model.AnnotationElementDecl;

import java.util.function.BiConsumer;

/// A transform over an annotation type's [AnnotationElementDecl]s.
///
/// [Transforms#transform(me.supcheg.javafile.model.AnnotationTypeDecl,AnnotationTypeTransform)]
/// invokes the transform once per element of the source declaration; the
/// transform decides whether to pass the element through unchanged, replace
/// it, drop it, or add new elements, all by calling (or not calling) `accept`
/// on the supplied builder. The result is a new declaration; the source
/// declaration is not modified.
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
