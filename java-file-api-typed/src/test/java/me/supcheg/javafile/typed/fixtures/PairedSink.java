package me.supcheg.javafile.typed.fixtures;

/// An interface with two abstract methods that are of one signature in a `PairedSink<String>`: one method
/// of a class implements both.
///
/// @param <T> the type of what the first takes
public interface PairedSink<T> {
    /// The method of a `T`.
    ///
    /// @param value the value
    /// @return what it is
    String put(T value);

    /// The method of a `String`.
    ///
    /// @param value the value
    /// @return what it is
    String put(String value);
}
