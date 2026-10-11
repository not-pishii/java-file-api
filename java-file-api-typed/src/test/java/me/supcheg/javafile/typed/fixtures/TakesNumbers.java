package me.supcheg.javafile.typed.fixtures;

/// An interface whose method erases as that of [TakesTexts] does.
public interface TakesNumbers {
    /// Takes.
    ///
    /// @param taken what is taken
    default void take(java.util.List<Integer> taken) {}
}
