package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.model.InterfaceMember;

import java.util.function.BiConsumer;

/// Rewrites the members of an interface. Called once per member; what you
/// pass to `builder.accept(...)` ends up in the result.
///
/// Forward a member with `builder.accept(member)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add members. The original declaration is left unchanged.
///
/// Apply it with [me.supcheg.javafile.JavaFile#transformInterface(InterfaceTransform)].
@FunctionalInterface
public interface InterfaceTransform extends BiConsumer<InterfaceBuilder, InterfaceMember> {
    /// Returns a transform that applies this transform, then `next`, to each member.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default InterfaceTransform andThen(InterfaceTransform next) {
        return (builder, member) -> {
            accept(builder, member);
            next.accept(builder, member);
        };
    }
}
