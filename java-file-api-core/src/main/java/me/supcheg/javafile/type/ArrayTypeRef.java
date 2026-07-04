package me.supcheg.javafile.type;

/// A reference to an array type.
///
/// @param component the type of the array's elements
public record ArrayTypeRef(TypeRef component) implements TypeRef {}
