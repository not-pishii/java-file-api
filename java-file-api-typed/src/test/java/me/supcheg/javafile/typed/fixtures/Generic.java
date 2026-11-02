package me.supcheg.javafile.typed.fixtures;

/// A class with generic methods of its own: `<K> K id(K)`, which has no bound, and
/// `<K extends CharSequence> CharSequence length(K)`, which has one. A class that extends it overrides
/// them, which is what the tests of the typed layer's generic methods need.
public class Generic {
    public <K> K id(K item) {
        return item;
    }

    public <K extends CharSequence> CharSequence length(K item) {
        return item;
    }

    /// `<K extends Comparable<K>> K max(K, K)`: a bound that mentions the type parameter itself.
    public <K extends Comparable<K>> K max(K left, K right) {
        return left.compareTo(right) >= 0 ? left : right;
    }
}
