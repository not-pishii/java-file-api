package me.supcheg.javafile.typed;

/// A function of four arguments, which `java.util.function` has none of: the
/// shape of the generator's lambda that builds the body of a lambda of the
/// generated code ([Expressions]).
///
/// @param <T1> the type of the first argument
/// @param <T2> the type of the second argument
/// @param <T3> the type of the third argument
/// @param <T4> the type of the fourth argument
/// @param <R> the type of the result
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {
    /// Applies the function.
    ///
    /// @param t1 the first argument
    /// @param t2 the second argument
    /// @param t3 the third argument
    /// @param t4 the fourth argument
    /// @return the result
    R apply(T1 t1, T2 t2, T3 t3, T4 t4);
}
