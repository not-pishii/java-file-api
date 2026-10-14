package me.supcheg.javafile.typed.fixtures;

/// A class of two supertypes a type parameter may be bounded by: a `Comparable<String>` and a `Runnable`.
public final class Ranked implements Comparable<String>, Runnable {
    /// How often it ran.
    public int runs;

    @Override
    public int compareTo(String other) {
        return other.length();
    }

    @Override
    public void run() {
        runs++;
    }
}
