package me.supcheg.javafile.typed.fixtures;

/// An interface whose method takes an array where that of [Joiner] is of variable arity.
public interface Taking {
    // the array is what the fixture proves: a method that takes one where another is of variable arity
    @SuppressWarnings("AvoidObjectArrays")
    default String join(String[] parts) {
        return "taking";
    }
}
