package me.supcheg.javafile.facts;

/// A token of a reference type: a class, interface, array, or type variable.
///
/// Only reference tokens can be type arguments, `instanceof` targets, and
/// cast targets, because Java allows only reference types there.
///
/// @param <T> the Java type this token stands for
public sealed interface RefToken<T> extends TypeToken<T> permits DeclaredToken, ArrayToken, TypeVarToken {}
