package me.supcheg.javafile.facts;

/// A token of a class that can be instantiated: neither abstract nor an enum.
///
/// @param <T> the Java type this token stands for
public sealed interface ConcreteClassToken<T> extends ClassToken<T> permits OpenClassToken, FinalClassToken {}
