package me.supcheg.javafile.facts;

/// A token of a class that can be extended: neither final, nor a record, nor
/// an enum. A constructor of such a class can be called from a subclass
/// constructor (`SuperCtorRefN`), and only such a class has `protected`
/// members a subclass reaches ([Protected]).
///
/// @param <T> the Java type this token stands for
public sealed interface ExtendableClassToken<T> extends ClassToken<T> permits OpenClassToken, AbstractClassToken {}
