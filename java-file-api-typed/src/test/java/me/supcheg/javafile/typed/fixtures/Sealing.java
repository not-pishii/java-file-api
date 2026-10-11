package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method of the signature of a `final` method of [Base].
public interface Sealing {
    default String sealed() {
        return "sealing";
    }
}
