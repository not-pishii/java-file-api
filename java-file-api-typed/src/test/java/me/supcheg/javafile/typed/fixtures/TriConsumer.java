package me.supcheg.javafile.typed.fixtures;

/// A functional interface of three parameters and no result, which the JDK
/// has none of.
///
/// @param <A> the type of the first parameter
/// @param <B> the type of the second parameter
/// @param <C> the type of the third parameter
@FunctionalInterface
public interface TriConsumer<A, B, C> {
    /// Takes the arguments.
    ///
    /// @param a the first argument
    /// @param b the second argument
    /// @param c the third argument
    void accept(A a, B b, C c);
}
