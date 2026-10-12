package me.supcheg.javafile.typed.fixtures;

/// An interface with a static method of the signature of the default one of [Named].
public interface Stated {
    static String label() {
        return "stated";
    }
}
