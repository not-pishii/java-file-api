package me.supcheg.javafile.facts;

/// The access a member fact is of (JLS 6.6): who may name the member in
/// generated code. A member that is `private` or has package access has no
/// fact.
public enum Access {
    /// A `public` member: accessible wherever its type is.
    PUBLIC,
    /// A `protected` member of a class: accessible in the body of a subclass
    /// alone, an instance member only through an expression of the type of
    /// that subclass and a constructor only by `super(...)` (JLS 6.6.2). A
    /// fact of one is handed out as a [Protected], except for a constructor,
    /// whose `SuperCtorRefN` nothing but a subclass constructor takes.
    PROTECTED
}
