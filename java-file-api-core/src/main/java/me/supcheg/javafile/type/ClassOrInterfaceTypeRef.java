package me.supcheg.javafile.type;

/// A class, interface, parameterized, or type-variable reference — anything
/// but a primitive or an array.
///
/// Required where Java allows only such types: `extends`/`implements`,
/// type-parameter bounds, and `throws` clauses.
public sealed interface ClassOrInterfaceTypeRef extends TypeRef
        permits ClassTypeRef, ParameterizedTypeRef, TypeVarRef {}
