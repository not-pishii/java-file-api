package me.supcheg.javafile.facts.source;

/// The kind of member a [MemberQuery] asks for.
public enum MemberKind {
    /// An instance method.
    METHOD,
    /// A static method.
    STATIC_METHOD,
    /// A constructor.
    CONSTRUCTOR,
    /// A readable instance field, `final` or not.
    FIELD,
    /// A non-`final` instance field.
    MUTABLE_FIELD,
    /// A readable static field, `final` or not.
    STATIC_FIELD,
    /// A non-`final` static field.
    MUTABLE_STATIC_FIELD
}
