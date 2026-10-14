package me.supcheg.javafile.typed.fixtures;

/// A class with two methods that are of one signature in a `Paired<String>`: a subclass of that has both,
/// neither overriding the other (JLS 8.4.8.4).
///
/// @param <T> the type of what the first takes
public class Paired<T> {
    /// The method of a `T`.
    ///
    /// @param value the value
    /// @return what it is
    public String put(T value) {
        return "T";
    }

    /// The method of a `String`.
    ///
    /// @param value the value
    /// @return what it is
    public String put(String value) {
        return "String";
    }
}
