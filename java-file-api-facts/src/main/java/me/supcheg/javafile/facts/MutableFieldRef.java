package me.supcheg.javafile.facts;

/// The fact that a type has a non-`final` instance field, which can be both
/// read and assigned.
///
/// @param <O> the type owning the field
/// @param <T> the field type
public final class MutableFieldRef<O, T> extends FieldRef<O, T> {

    MutableFieldRef(DeclaredToken<O> owner, String name, TypeToken<T> type) {
        super(owner, name, type);
    }
}
