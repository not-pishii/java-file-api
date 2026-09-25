package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.EnumMember;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// Rewrites the members of an enum. Called once per member; what you pass to
/// `builder.accept(...)` ends up in the result.
///
/// Forward a member with `builder.accept(member)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add members. The original declaration is left unchanged.
///
/// Members inside constant bodies (`PLUS { ... }`) are visited too, before the
/// enum's own members. Passing something a constant body cannot hold, such as
/// a constructor, there throws `IllegalArgumentException`.
///
/// Apply it with [me.supcheg.javafile.JavaFile#transformEnum(EnumTransform)].
@FunctionalInterface
public interface EnumTransform extends BiConsumer<Consumer<EnumMember>, EnumMember> {
    /// Returns a transform that applies this transform, then `next`, to each member.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default EnumTransform andThen(EnumTransform next) {
        return (builder, member) -> {
            accept(builder, member);
            next.accept(builder, member);
        };
    }
}
