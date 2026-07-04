package me.supcheg.javafile.model;

import me.supcheg.javafile.type.TypeRef;

/// A method or constructor parameter: a name paired with its declared type.
///
/// @param name the parameter name, a valid Java identifier
/// @param type the declared parameter type
public record Param(String name, TypeRef type) {}
