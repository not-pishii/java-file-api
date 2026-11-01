package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;

/// An initializer block: `{ ... }` or `static { ... }`.
///
/// A record body holds a `static { ... }` block alone: javac rejects an instance one there, which a
/// compact constructor replaces (JLS 8.10.4).
///
/// @param isStatic whether this is a `static { ... }` block rather than an instance `{ ... }` block
/// @param body the block's body
public record InitializerBlock(boolean isStatic, CodeBody body) implements ClassMember, EnumMember, RecordMember {}
