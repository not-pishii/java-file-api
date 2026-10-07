package me.supcheg.javafile.typed.fixtures;

/// A functional interface whose method throws its type argument.
///
/// @param <X> what the method throws
@FunctionalInterface
public interface Thrower<X extends Throwable> {
    /// Runs, or throws.
    ///
    /// @throws X as the implementation says
    void run() throws X;
}
