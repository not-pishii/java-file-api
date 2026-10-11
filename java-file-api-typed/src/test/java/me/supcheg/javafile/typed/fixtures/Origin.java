package me.supcheg.javafile.typed.fixtures;

/// An interface whose methods a class overrides with narrower results: an `Object`, an array of
/// them, an exception.
public interface Origin {
    Object get();

    default Object[] all() {
        return new Object[] {"origin"};
    }

    default Iterable<Object> each() {
        return java.util.List.of("origin");
    }

    default Exception failure() {
        return new Exception("origin");
    }
}
