package me.supcheg.javafile.type;

/// A type reference valid where Java requires a class or interface type or a
/// type variable: supertypes, type-parameter bounds, and `throws` clauses.
///
/// Primitives and arrays are excluded by construction.
public sealed interface ClassOrInterfaceTypeRef extends TypeRef
        permits ClassTypeRef, ParameterizedTypeRef, TypeVarRef {}
