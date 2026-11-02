package me.supcheg.javafile.typed.fixtures;

/// A generic record: a record pattern of it is not expressed yet, as the
/// components of the pattern are the types its type arguments give them.
///
/// @param value the value
/// @param <T> the type of the value
public record Boxed<T>(T value) {}
