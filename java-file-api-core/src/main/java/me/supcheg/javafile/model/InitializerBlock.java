package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;

/// An instance or static initializer block.
///
/// Deliberately does not implement [RecordMember]: records don't allow
/// instance initializers at all, since every field is finalized by the
/// canonical/compact constructor, so that combination is unrepresentable
/// rather than rejected at runtime.
///
/// @param isStatic whether this is a `static { ... }` block rather than an instance `{ ... }` block
/// @param body the block's body
public record InitializerBlock(boolean isStatic, CodeBody body) implements ClassMember, EnumMember {}
