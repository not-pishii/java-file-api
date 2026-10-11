package me.supcheg.javafile.typed.fixtures;

/// A generic superclass whose methods of the type parameter get overloads in [SubTwin] that erase
/// as they do once the type argument is `String`.
///
/// @param <T> the type of the values
public class BaseTwin<T> {
    public String m(T value) {
        return "base:" + value;
    }

    protected String p(T value) {
        return "base:" + value;
    }

    protected <U> String g(U value) {
        return "generic:" + value;
    }
}
