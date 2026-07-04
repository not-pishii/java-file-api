package me.supcheg.javafile.model;

import me.supcheg.javafile.type.TypeRef;

/// A record component: a name paired with its declared type.
///
/// A record's components determine its canonical constructor parameters and
/// the accessor methods generated for it.
///
/// @param name the component name, a valid Java identifier
/// @param type the declared component type
public record RecordComponent(String name, TypeRef type) {}
