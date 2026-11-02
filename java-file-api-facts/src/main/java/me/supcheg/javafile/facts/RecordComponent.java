package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

/// A component of a record: its name and its type, as the declaration of
/// the record lists them.
///
/// A record deconstruction pattern names the type of the record and one
/// pattern per component, in this order ([RecordShape#components()]).
///
/// @param name the component name
/// @param type the component type, in terms of the type parameters of the record
public record RecordComponent(String name, TypeRef type) {
    public RecordComponent {
        name = Identifiers.requireValid(name);
    }
}
