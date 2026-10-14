package me.supcheg.javafile.typed.fixtures;

/// An interface whose method has the signature `compareTo(String)` has in a `Comparable<String>`, and is
/// none of its.
public interface Rival {
    /// Compares, otherwise.
    ///
    /// @param other the other
    /// @return what it is
    int compareTo(String other);
}
