package me.supcheg.javafile.langmodel.mirror;

/// Where a type is declared, as far as a class that extends it has to know
/// (JLS 8.1.3): an inner class is extended with an instance of the class it
/// is nested in, any other without. It is part of the [Canonical] form, so
/// a nested class that loses its `static` is another type.
public enum Nesting {
    /// A type of a package.
    TOP_LEVEL,
    /// A member of a type that needs no instance of it: a `static` class, an interface, an enum, a record.
    STATIC_MEMBER,
    /// An inner class: a member class that is not `static`.
    INNER
}
