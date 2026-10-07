package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

/// The fact that a type has an instance field `name` of type `T` that can be
/// read. A field that can also be assigned is a [MutableFieldRef].
///
/// @param <O> the type owning the field
/// @param <T> the field type
public sealed class FieldRef<O, T> implements MemberFact permits MutableFieldRef {
    private final DeclaredToken<O> owner;
    private final String name;
    private final TypeToken<T> type;
    private final Access access;

    FieldRef(DeclaredToken<O> owner, String name, TypeToken<T> type, Access access) {
        this.owner = owner;
        this.name = Identifiers.requireValid(name);
        this.type = type;
        this.access = access;
    }

    /// The type owning the field.
    ///
    /// @return the owner token
    @Override
    public final DeclaredToken<O> owner() {
        return owner;
    }

    /// The field name.
    ///
    /// @return the name
    public final String name() {
        return name;
    }

    /// The field type.
    ///
    /// @return the type token
    public final TypeToken<T> type() {
        return type;
    }

    /// The access of the field.
    ///
    /// @return the access
    @Override
    public final Access access() {
        return access;
    }

    @Override
    public final String toString() {
        return type + " " + owner + "." + name;
    }
}
