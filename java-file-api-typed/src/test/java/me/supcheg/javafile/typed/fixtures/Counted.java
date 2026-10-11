package me.supcheg.javafile.typed.fixtures;

/// An interface whose method has the signature of that of [Measured] and another result.
public interface Counted {
    /// The size.
    ///
    /// @return `0`
    default int size() {
        return 0;
    }
}
