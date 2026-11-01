package me.supcheg.javafile.typed.fixtures;

/// A sealed interface that permits [SealedDot].
public sealed interface SealedShape permits SealedDot {
    /// The number of sides.
    ///
    /// @return the number
    int sides();
}
