package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.TypeToken;

/// A component of a record being declared (§6.5): its name and type, the
/// accessor the record has for it, and the private field a body reads it by.
///
/// @param name the component name
/// @param type the component type
/// @param accessor the fact of the accessor, `public T name()`
/// @param field the fact of the private field the accessor reads
/// @param <Self> the brand of the record
/// @param <T> the component type
public record TypedRecordComponent<Self, T>(
        String name, TypeToken<T> type, MethodRef0<Self, T> accessor, FieldRef<Self, T> field) {}
