package me.supcheg.javafile.typed.fixtures;

/// A [Named] that overrides the default method.
public interface Renamed extends Named {
    @Override
    default String label() {
        return "renamed";
    }
}
