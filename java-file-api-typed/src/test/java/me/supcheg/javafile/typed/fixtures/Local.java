package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method of the signature of a method of package access of [Base].
public interface Local {
    default String local() {
        return "local";
    }
}
