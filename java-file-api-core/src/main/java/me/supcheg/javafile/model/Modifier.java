package me.supcheg.javafile.model;

/// A modifier of a declaration.
///
/// Modifiers are always written in the conventional order, whatever order you
/// pass them in: the order of the constants below, which is the one javac
/// prints.
///
/// `sealed` is not a constant: a [ClassDecl] or an [InterfaceDecl] with a
/// non-empty `permits` list is rendered `sealed`. `default` is no modifier
/// either: a [DefaultMethodDecl] is rendered `default`, and no other member
/// is. `strictfp` is obsolete, and javac warns of it under `-Xlint:all`
/// (`[strictfp]`, as of release 17 all floating-point expressions are
/// evaluated strictly); the model does not emit it.
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
    /// The `transient` modifier of an instance field.
    TRANSIENT,
    /// The `volatile` modifier of an instance field.
    VOLATILE,
    /// The `synchronized` modifier of a method.
    SYNCHRONIZED,
    /// The `native` modifier of a method without a body.
    NATIVE,
    /// The `non-sealed` modifier.
    NON_SEALED
}
