package me.supcheg.javafile.facts;

/// What the facts of members have in common: the methods and constructors
/// ([Invocable]), the instance fields ([FieldRef]) and the `static` ones
/// ([StaticFieldRef]).
public sealed interface MemberFact permits Invocable, FieldRef, StaticFieldRef {

    /// The type declaring or inheriting the member.
    ///
    /// @return the owner token
    DeclaredToken<?> owner();

    /// The access of the member.
    ///
    /// @return the access
    Access access();
}
