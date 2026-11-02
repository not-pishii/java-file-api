package me.supcheg.javafile.typed.fixtures;

/// An interface with a method of variable arity whose element type is a type parameter.
///
/// @param <T> the element type
public interface Scattering<T> {
    // the element type is a type parameter, so the array javac makes of the arguments is not reifiable
    @SuppressWarnings("unchecked")
    default int scatter(T... all) {
        return all.length;
    }
}
