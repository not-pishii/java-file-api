package me.supcheg.javafile.model;

/// A Java access or class modifier keyword that can be attached to a type or
/// member declaration.
///
/// Declaration order is significant: when a set of modifiers is rendered to
/// source, they are emitted sorted by this enum's ordinal, matching
/// conventional Java modifier ordering. [#NON_SEALED] renders as the
/// hyphenated keyword `non-sealed` rather than its enum constant name.
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
