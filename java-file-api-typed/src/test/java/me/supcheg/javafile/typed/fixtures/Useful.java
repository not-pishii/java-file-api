package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method of the signature of a `static` method of [Base].
public interface Useful {
    default String util() {
        return "useful";
    }
}
