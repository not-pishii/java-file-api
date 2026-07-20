package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A type-qualified method reference target, e.g. the `Type` in `Type::method`.
///
/// @param type the qualifying type
public record TypeMethodRefTarget(TypeRef type) implements MethodRefTarget {}
