package me.supcheg.javafile.typed.fixtures;

/// A functional interface of three parameters, which the JDK has none of.
///
/// @param <A> the type of the first parameter
/// @param <B> the type of the second parameter
/// @param <C> the type of the third parameter
/// @param <R> the type of the result
@FunctionalInterface
public interface TriFunction<A, B, C, R> {
    /// Applies the function.
    ///
    /// @param a the first argument
    /// @param b the second argument
    /// @param c the third argument
    /// @return the result
    R apply(A a, B b, C c);
}
