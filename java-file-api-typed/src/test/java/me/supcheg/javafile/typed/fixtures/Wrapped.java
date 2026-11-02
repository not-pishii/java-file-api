package me.supcheg.javafile.typed.fixtures;

/// A record whose component is of an open class: a record pattern matches it
/// with a pattern of the class, or of a subtype of it.
///
/// @param value the value
public record Wrapped(Base value) {}
