package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.util.List;
import java.util.stream.Collectors;

/// A type parameter a method or constructor of the generated code declares, `<T extends Number &
/// Comparable<T>>`: its token, which the body of the member uses as the type, and the bounds it is
/// declared with, the leftmost first.
///
/// A type parameter of a member is in scope in the signature and the body of that member alone
/// (JLS 6.3): the token says where it comes from ([TypeVarToken.Origin]), and the typed layer holds
/// every type the body mentions to it.
///
/// @param token the token of the type parameter, whose name the declaration writes
/// @param bounds the bounds, the leftmost first; never empty, `Object` for an unbounded parameter
public record MethodTypeParameter(TypeVarToken<?> token, List<ClassOrInterfaceTypeRef> bounds) {

    /// @throws IllegalArgumentException if there is no bound
    public MethodTypeParameter {
        bounds = List.copyOf(bounds);
        if (bounds.isEmpty()) {
            throw new IllegalArgumentException("a type parameter of a method or constructor has a leftmost"
                    + " bound: Object where the declaration names none, got " + token);
        }
    }

    /// The name the declaration writes.
    ///
    /// @return the name of the type parameter
    public String name() {
        return token.typeRef().name();
    }

    /// The parameter as the `throws` clause of a diagnostic names it, `T extends A & B`.
    ///
    /// @return the parameter, e.g. `T extends java.lang.Number`
    public String declaration() {
        return bounds.stream().map(TypeNames::describe).collect(Collectors.joining(" & ", name() + " extends ", ""));
    }

    @Override
    public String toString() {
        return declaration();
    }
}
