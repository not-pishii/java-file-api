package me.supcheg.javafile.type;

/// A type argument that is a concrete type with no wildcard, e.g. the
/// `String` in `List<String>`.
///
/// @param type the exact type argument
public record ExactTypeArg(TypeRef type) implements TypeArg {}
