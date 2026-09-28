package me.supcheg.javafile.facts;

import java.util.Optional;

/// The fact that a type has a non-`final` static field, which can be both
/// read and assigned.
///
/// @param <T> the field type
public final class MutableStaticFieldRef<T> extends StaticFieldRef<T> {

    private MutableStaticFieldRef(DeclaredToken<?> owner, String name, TypeToken<T> type) {
        super(owner, name, type, Optional.empty());
    }

    /// Introduces the fact that a type has a non-`final` static field.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param owner the type owning the field
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact
    public static <T> MutableStaticFieldRef<T> introduce(DeclaredToken<?> owner, String name, TypeToken<T> type) {
        return new MutableStaticFieldRef<>(owner, name, type);
    }
}
