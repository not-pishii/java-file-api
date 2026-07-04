package me.supcheg.javafile.type;

/// An upper-bounded wildcard type argument, e.g. `? extends Number`.
///
/// @param bound the upper bound of the wildcard
public record ExtendsTypeArg(TypeRef bound) implements TypeArg {}
