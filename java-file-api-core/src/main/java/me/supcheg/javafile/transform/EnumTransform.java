package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.EnumMember;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// A transform over [EnumMember]s.
///
/// [Transforms#transform(me.supcheg.javafile.model.EnumDecl,EnumTransform)]
/// invokes the transform once per member — both the enum's own members and,
/// per constant, the members of that constant's constant-specific body — the
/// transform decides whether to pass the member through unchanged, replace
/// it, drop it, or add new members, all by calling (or not calling) `accept`
/// on the supplied sink. Each constant's body members are visited before the
/// enum's own top-level members. The first argument is typed `Consumer<EnumMember>`
/// rather than the concrete `EnumBuilder` so that a transform can be defined
/// without depending on the builder's other methods, and so the same
/// transform value can be reused against the narrower sink backing an enum
/// constant's body. The result is a new declaration; the source declaration
/// is not modified.
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
