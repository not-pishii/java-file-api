package me.supcheg.javafile.typed.fixtures;

/// A generic interface whose method [Carrier] has with a body.
///
/// @param <T> the type of what is carried
public interface Carrying<T> {
    T carried();
}
