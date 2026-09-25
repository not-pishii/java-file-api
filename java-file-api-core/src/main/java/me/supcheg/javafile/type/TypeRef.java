package me.supcheg.javafile.type;

/// A type as written in source: a class or interface (possibly
/// parameterized), a type variable, an array, or a primitive.
///
/// Create it with [Types].
public sealed interface TypeRef permits ClassOrInterfaceTypeRef, ArrayTypeRef, PrimitiveTypeRef {}
