package me.supcheg.javafile.typed.fixtures;

/// A [Named] that makes the default method abstract again.
public interface Unnamed extends Named {
    @Override
    String label();
}
