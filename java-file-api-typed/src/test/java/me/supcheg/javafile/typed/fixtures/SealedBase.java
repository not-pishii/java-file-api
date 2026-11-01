package me.supcheg.javafile.typed.fixtures;

/// A sealed class that permits [SealedChild] and [SealedOpen].
public sealed class SealedBase permits SealedChild, SealedOpen {
    /// Makes one.
    public SealedBase() {}
}
