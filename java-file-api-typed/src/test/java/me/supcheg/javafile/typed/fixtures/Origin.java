package me.supcheg.javafile.typed.fixtures;

/// An interface whose methods a class overrides with narrower results: an `Object`, an array of
/// them, an exception.
public interface Origin {
    Object get();

    // the array is what the fixture proves: a class overrides the method with a narrower array result
    @SuppressWarnings("AvoidObjectArrays")
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
