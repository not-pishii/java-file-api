package me.supcheg.javafile.typed.fixtures;

/// An interface whose method erases as that of [TakesNumbers] does.
public interface TakesTexts {
    /// Takes.
    ///
    /// @param taken what is taken
    default void take(java.util.List<String> taken) {}
}
