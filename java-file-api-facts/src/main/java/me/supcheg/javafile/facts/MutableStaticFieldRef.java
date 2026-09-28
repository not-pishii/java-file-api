package me.supcheg.javafile.facts;

import java.util.Optional;

/// The fact that a type has a non-`final` static field, which can be both
/// read and assigned.
///
/// @param <T> the field type
public final class MutableStaticFieldRef<T> extends StaticFieldRef<T> {

    MutableStaticFieldRef(DeclaredToken<?> owner, String name, TypeToken<T> type) {
        super(owner, name, type, Optional.empty());
    }
}
