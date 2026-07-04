package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.ClassMember;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// A transform over [ClassMember]s.
///
/// [Transforms#transform(me.supcheg.javafile.model.ClassDecl,ClassTransform)]
/// and
/// [Transforms#transform(me.supcheg.javafile.model.EnumDecl,ClassTransform)]
/// invoke the transform once per member of the source declaration; the
/// transform decides whether to pass the member through unchanged, replace
/// it, drop it, or add new members, all by calling (or not calling) `accept`
/// on the supplied builder. The first argument is the target builder's
/// member sink — typed as `Consumer<ClassMember>` rather than the concrete
/// `ClassBuilder` because this same transform type is reused for both
/// `ClassDecl` and `EnumDecl` round-trips, and `ClassBuilder`/`EnumBuilder`
/// share no common supertype beyond `Consumer<ClassMember>`. The result is a
/// new declaration; the source declaration is not modified.
@FunctionalInterface
public interface ClassTransform extends BiConsumer<Consumer<ClassMember>, ClassMember> {
    /// Returns a transform that applies this transform, then `next`, to each member.
    ///
    /// @param next the transform to apply after this one
    /// @return the combined transform
    default ClassTransform andThen(ClassTransform next) {
        return (builder, member) -> {
            accept(builder, member);
            next.accept(builder, member);
        };
    }
}
