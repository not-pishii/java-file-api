package me.supcheg.javafile.type;

import me.supcheg.javafile.Identifiers;

/// A reference to a type variable by its declared name, e.g. `T`.
///
/// The name is a local symbol introduced by an enclosing declaration's type
/// parameter; it is rendered verbatim and never imported.
///
/// @param name the type variable's name
public record TypeVarRef(String name) implements ClassOrInterfaceTypeRef {
    public TypeVarRef {
        name = Identifiers.requireValid(name);
    }
}
