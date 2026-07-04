package me.supcheg.javafile.type;

/// A type argument used in a [ParameterizedTypeRef], covering exact types and
/// the wildcard forms (`? extends`, `? super`, `?`) that Java's generics allow.
///
/// Implementations are immutable values. Use [Types] to construct instances
/// rather than the permitted implementations directly.
public sealed interface TypeArg permits ExactTypeArg, ExtendsTypeArg, SuperTypeArg, UnboundedTypeArg {}
