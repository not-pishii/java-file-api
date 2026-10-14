package me.supcheg.javafile.typed.fixtures;

import java.util.function.Function;

/// A class with a method that takes a functional interface, beside one that takes an `int`.
public class Applying {
    /// Makes one.
    public Applying() {}

    /// Applies.
    ///
    /// @param function what is applied
    public void apply(Function<String, String> function) {}

    /// Takes a number.
    ///
    /// @param number the number
    public void take(int number) {}

    /// Applies, with a number.
    ///
    /// @param function what is applied
    /// @param number the number
    public void pair(Function<String, String> function, int number) {}
}
