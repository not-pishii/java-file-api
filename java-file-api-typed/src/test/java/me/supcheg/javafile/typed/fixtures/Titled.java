package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method, which [Named] has too: the two are unrelated.
public interface Titled {
    /// The label.
    ///
    /// @return `titled`
    default String label() {
        return "titled";
    }
}
