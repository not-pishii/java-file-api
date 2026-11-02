package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;

/// What a generic method of a type that is not a class being declared is specified by: the
/// declarations of the members, with the type parameters of this member in scope, and the fact of the
/// member it declares. The generated stages of [GenericMethodArities] implement it over the
/// specification a generator writes, which is given the tokens of the type parameters beside it.
///
/// @param <Self> the brand of the type being declared
/// @param <F> the fact of the member declared
@FunctionalInterface
interface TypeGenericMethodBodies<Self, F extends Invocable> {

    /// Declares the member.
    ///
    /// @param declaration the declarations of the members, with the type parameters of this member in scope
    /// @return the fact of the member declared
    F build(TypeGenericMethodDeclaration<Self> declaration);
}
