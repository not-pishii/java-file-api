package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.model.RecordMember;

import java.util.function.BiConsumer;

/// A transform over [RecordMember]s.
///
/// [Transforms#transform(me.supcheg.javafile.model.RecordDecl,RecordTransform)]
/// invokes the transform once per member of the source declaration; the
/// transform decides whether to pass the member through unchanged, replace
/// it, drop it, or add new members, all by calling (or not calling) `accept`
/// on the supplied builder. The result is a new declaration; the source
/// declaration is not modified.
@FunctionalInterface
public interface RecordTransform extends BiConsumer<RecordBuilder, RecordMember> {
    /// Returns a transform that applies this transform, then `next`, to each member.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default RecordTransform andThen(RecordTransform next) {
        return (builder, member) -> {
            accept(builder, member);
            next.accept(builder, member);
        };
    }
}
