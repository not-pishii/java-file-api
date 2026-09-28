package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

/// The fact that a type has an instance field `name` of type `T` that can be
/// read. A field that can also be assigned is a [MutableFieldRef].
///
/// @param <O> the type owning the field
/// @param <T> the field type
public sealed class FieldRef<O, T> permits MutableFieldRef {
    private final DeclaredToken<O> owner;
    private final String name;
    private final TypeToken<T> type;

    FieldRef(DeclaredToken<O> owner, String name, TypeToken<T> type) {
        this.owner = owner;
        this.name = Identifiers.requireValid(name);
        this.type = type;
    }

    /// The type owning the field.
    ///
    /// @return the owner token
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

    @Override
    public final String toString() {
        return type + " " + owner + "." + name;
    }
}
