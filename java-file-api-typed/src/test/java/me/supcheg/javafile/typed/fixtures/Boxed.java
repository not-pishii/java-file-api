package me.supcheg.javafile.typed.fixtures;

/// A generic record: a record pattern of it substitutes the type argument its
/// token carries for the type of its component.
///
/// @param value the value
/// @param <T> the type of the value
public record Boxed<T>(T value) {}
