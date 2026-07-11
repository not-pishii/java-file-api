package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.ClassMember;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// A transform over [ClassMember]s.
///
/// [Transforms#transform(me.supcheg.javafile.model.ClassDecl,ClassTransform)]
/// invokes the transform once per member of the source declaration; the
/// transform decides whether to pass the member through unchanged, replace
/// it, drop it, or add new members, all by calling (or not calling) `accept`
/// on the supplied builder. The first argument is the target builder's
/// member sink — typed as `Consumer<ClassMember>` rather than the concrete
/// `ClassBuilder` so that a transform can be defined without depending on
/// the builder's other methods. The result is a new declaration; the source
/// declaration is not modified.
///
/// Enum declarations use the separate [EnumTransform] instead, since
/// `EnumDecl` members are typed as [me.supcheg.javafile.model.EnumMember],
/// not [ClassMember].
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
