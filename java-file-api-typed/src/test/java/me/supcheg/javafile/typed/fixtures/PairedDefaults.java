package me.supcheg.javafile.typed.fixtures;

/// An interface with two default methods that are of one signature in a `PairedDefaults<String>`: a class
/// that implements it has two bodies for one method (JLS 8.4.8.4).
///
/// @param <T> the type of what the first takes
public interface PairedDefaults<T> {
    /// The method of a `T`.
    ///
    /// @param value the value
    /// @return what it is
    default String put(T value) {
        return "T";
    }

    /// The method of a `String`.
    ///
    /// @param value the value
    /// @return what it is
    default String put(String value) {
        return "String";
    }
}
