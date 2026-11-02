package me.supcheg.javafile.typed.fixtures;

/// An interface with a static method of the signature of the default one of [Named].
// the fixture proves a static method of an interface beside a default one of a supertype
@SuppressWarnings("InterfaceWithOnlyStatics")
public interface Stated {
    static String label() {
        return "stated";
    }
}
