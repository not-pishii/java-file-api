package me.supcheg.javafile.type;

/// A type argument of a [ParameterizedTypeRef]: a type such as `String`, or
/// a wildcard `? extends T`, `? super T`, `?`.
///
/// Create it with [Types#exact(TypeRef)], [Types#extendsBound(TypeRef)],
/// [Types#superBound(TypeRef)], or [Types#unbounded()].
public sealed interface TypeArg permits ExactTypeArg, ExtendsTypeArg, SuperTypeArg, UnboundedTypeArg {}
