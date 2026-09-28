package me.supcheg.javafile.facts;

/// Whether an instance method can be overridden, and must be.
public enum Overridability {
    /// An abstract method: a concrete subclass must implement it.
    ABSTRACT,
    /// A concrete method that a subclass may override.
    OVERRIDABLE,
    /// A `final` method, or any member that cannot be overridden: static
    /// methods and constructors.
    FINAL
}
