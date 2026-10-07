package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method, which [Titled] has too.
public interface Named {
    /// The label.
    ///
    /// @return `named`
    default String label() {
        return "named";
    }
}
