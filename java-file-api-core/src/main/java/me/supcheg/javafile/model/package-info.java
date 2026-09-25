/// The declarations a generated file consists of: types, their members,
/// parameters, and `module-info` directives.
///
/// Builders from [me.supcheg.javafile.builder] create these for you. You meet
/// them directly when writing a transform, where you inspect members with
/// pattern matching and pass modified copies on:
///
/// ```java
/// file.transformClass((builder, member) -> {
///     if (member instanceof FieldDecl f) {
///         builder.accept(new FieldDecl(f.name(), f.type(), f.annotations(),
///                 EnumSet.of(Modifier.PRIVATE, Modifier.FINAL), f.initializer()));
///     } else {
///         builder.accept(member);
///     }
/// });
/// ```
///
/// All types here are immutable. Constructors reject invalid names and
/// modifier combinations with `IllegalArgumentException`.
@NullMarked
package me.supcheg.javafile.model;

import org.jspecify.annotations.NullMarked;
