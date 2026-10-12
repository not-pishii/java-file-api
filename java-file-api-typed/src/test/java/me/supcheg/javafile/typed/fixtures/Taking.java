package me.supcheg.javafile.typed.fixtures;

/// An interface whose method takes an array where that of [Joiner] is of variable arity.
public interface Taking {
    default String join(String[] parts) {
        return "taking";
    }
}
