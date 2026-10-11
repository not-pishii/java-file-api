package me.supcheg.javafile.typed.fixtures;

/// An interface whose method has the signature of that of [Counted] and another result.
public interface Measured {
    /// The size.
    ///
    /// @return `0`
    default long size() {
        return 0;
    }
}
