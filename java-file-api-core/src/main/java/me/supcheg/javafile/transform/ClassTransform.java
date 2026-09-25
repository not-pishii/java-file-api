package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.ClassMember;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// Rewrites the members of a class. Called once per member; what you pass to
/// `builder.accept(...)` ends up in the result.
///
/// Forward a member with `builder.accept(member)` to keep it, pass a different
/// one to replace it, skip the call to drop it, or call `accept` several times
/// to add members. The original declaration is left unchanged.
///
/// ```java
/// ClassTransform dropDebugMethods = (builder, member) -> {
///     if (!(member instanceof MethodDecl m && m.name().startsWith("debug"))) {
///         builder.accept(member);
///     }
/// };
/// JavaFile cleaned = file.transformClass(dropDebugMethods);
/// ```
///
/// For enums use [EnumTransform].
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
