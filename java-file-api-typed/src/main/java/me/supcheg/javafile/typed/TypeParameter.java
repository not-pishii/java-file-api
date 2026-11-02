package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.facts.TypeToken;

/// A type parameter of a type or a member about to be declared: its name, and its
/// leftmost bound — the bound the generator itself knows the type variable
/// by, `<T extends B>` in the specification of the class
/// ([TypedJavaFile.TypedGenericClassSpec1]), so that an expression of `T` is
/// one of `B` wherever a fact asks for a `B`.
///
/// ```java
/// TypedJavaFile.typeParam("T")                  // <T>
/// TypedJavaFile.typeParam("N", Number_.TOKEN)   // <N extends Number>
/// ```
///
/// The bound is a class or an interface, with its type arguments if it is
/// generic, or a type variable: `<U extends T>`, where `T` is an earlier
/// parameter of the same member, or one of the type it is declared in. A bound
/// that names a type parameter declared by this very list is said by the
/// declaration stage instead ([GenericMethodDeclaration#bound(TypeVarToken,
/// TypeVarToken)]), which is given the token of the earlier parameter.
///
/// A bound that names the parameter itself, `T extends Comparable<T>`, and
/// any further bound are said by a witness ([ClassHeader#bound]).
///
/// @param <B> the leftmost bound
public final class TypeParameter<B> {
    private final String name;
    private final TypeToken<B> bound;

    private TypeParameter(String name, TypeToken<B> bound) {
        try {
            this.name = Identifiers.requireTypeIdentifier(name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("a type parameter cannot be named '" + name + "': " + e.getMessage(), e);
        }
        this.bound = bound;
    }

    static TypeParameter<Object> unbounded(String name) {
        return new TypeParameter<>(name, Object_.TOKEN);
    }

    static <B> TypeParameter<B> bounded(String name, TypeToken<B> bound) {
        return new TypeParameter<>(name, bound);
    }

    String name() {
        return name;
    }

    TypeToken<B> bound() {
        return bound;
    }

    @Override
    public String toString() {
        return name + " extends " + bound;
    }
}
