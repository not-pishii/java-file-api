package me.supcheg.javafile.facts;

/// The fact that a type has a non-`final` instance field, which can be both
/// read and assigned.
///
/// @param <O> the type owning the field
/// @param <T> the field type
public final class MutableFieldRef<O, T> extends FieldRef<O, T> {

    private MutableFieldRef(DeclaredToken<O> owner, String name, TypeToken<T> type) {
        super(owner, name, type);
    }

    /// Introduces the fact that a type has a non-`final` instance field.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param owner the type owning the field
    /// @param name the field name
    /// @param type the field type, as a member of `owner`
    /// @param <O> the owner type
    /// @param <T> the field type
    /// @return the fact
    public static <O, T> MutableFieldRef<O, T> introduce(DeclaredToken<O> owner, String name, TypeToken<T> type) {
        return new MutableFieldRef<>(owner, name, type);
    }
}
