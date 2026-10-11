package me.supcheg.javafile.typed.fixtures;

/// An interface of a method [Maker] has as a `void` one.
public interface Resetting {
    default Object reset() {
        return null;
    }
}
