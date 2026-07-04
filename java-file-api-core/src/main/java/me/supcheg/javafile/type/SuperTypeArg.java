package me.supcheg.javafile.type;

/// A lower-bounded wildcard type argument, e.g. `? super Integer`.
///
/// @param bound the lower bound of the wildcard
public record SuperTypeArg(TypeRef bound) implements TypeArg {}
