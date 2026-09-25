package me.supcheg.javafile.model;

/// A modifier of a declaration.
///
/// Modifiers are always written in the conventional order, whatever order you
/// pass them in.
public enum Modifier {
    /// The `public` access modifier.
    PUBLIC,
    /// The `protected` access modifier.
    PROTECTED,
    /// The `private` access modifier.
    PRIVATE,
    /// The `abstract` modifier.
    ABSTRACT,
    /// The `static` modifier.
    STATIC,
    /// The `final` modifier.
    FINAL,
    /// The `non-sealed` modifier.
    NON_SEALED
}
