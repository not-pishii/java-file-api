package me.supcheg.javafile.type;

/// A reference to a Java type, as it would appear in source: a class or
/// interface, a parameterized generic type, an array, or a primitive.
///
/// Implementations are immutable values. Use [Types] to construct instances
/// rather than the permitted implementations directly.
public sealed interface TypeRef permits ClassTypeRef, ParameterizedTypeRef, ArrayTypeRef, PrimitiveTypeRef {}
