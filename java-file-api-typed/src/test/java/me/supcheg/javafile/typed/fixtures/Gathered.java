package me.supcheg.javafile.typed.fixtures;

/// A class whose method takes an array where that of [Joiner] is of variable arity, and a method of
/// variable arity that is the only one of its name.
public class Gathered {
    // the array is what the fixture proves: a method that takes one where another is of variable arity
    @SuppressWarnings("AvoidObjectArrays")
    public String join(String[] parts) {
        return "gathered";
    }

    public int count(Object... parts) {
        return parts.length;
    }
}
