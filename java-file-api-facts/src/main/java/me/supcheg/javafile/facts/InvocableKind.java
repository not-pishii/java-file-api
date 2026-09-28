package me.supcheg.javafile.facts;

/// The kind of an [Invocable].
public enum InvocableKind {
    /// An instance method, called on a receiver.
    INSTANCE_METHOD,
    /// A static method, called on its declaring type.
    STATIC_METHOD,
    /// A constructor, called with `new`.
    CONSTRUCTOR
}
