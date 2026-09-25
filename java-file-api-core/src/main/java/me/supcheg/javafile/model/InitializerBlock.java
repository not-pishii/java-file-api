package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;

/// An initializer block: `{ ... }` or `static { ... }`.
///
/// @param isStatic whether this is a `static { ... }` block rather than an instance `{ ... }` block
/// @param body the block's body
public record InitializerBlock(boolean isStatic, CodeBody body) implements ClassMember, EnumMember {}
