package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.model.RecordMember;

import java.util.function.BiConsumer;

/// Rewrites the members of a record. Called once per member; what you pass to
/// `builder.accept(...)` ends up in the result. Components are kept as is.
///
/// Forward a member with `builder.accept(member)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add members. The original declaration is left unchanged.
///
/// Apply it with [me.supcheg.javafile.JavaFile#transformRecord(RecordTransform)].
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
