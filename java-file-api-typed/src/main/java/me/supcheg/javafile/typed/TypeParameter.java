package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.typed.jdk.facts.java.lang.Object_;

/// A type parameter of a class about to be declared: its name, and its
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
/// generic. It names no type parameter of the class — a token of one exists
/// in the specification alone —, so a bound that does, `T extends
/// Comparable<T>`, and any further bound are said there, by a witness
/// ([ClassHeader#bound]).
///
/// @param <B> the leftmost bound
public final class TypeParameter<B> {
    private final String name;
    private final DeclaredToken<B> bound;

    private TypeParameter(String name, DeclaredToken<B> bound) {
        this.name = Identifiers.requireValid(name);
        this.bound = bound;
    }

    static TypeParameter<Object> unbounded(String name) {
        return new TypeParameter<>(name, Object_.TOKEN);
    }

    static <B> TypeParameter<B> bounded(String name, DeclaredToken<B> bound) {
        return new TypeParameter<>(name, bound);
    }

    String name() {
        return name;
    }

    DeclaredToken<B> bound() {
        return bound;
    }

    @Override
    public String toString() {
        return name + " extends " + bound;
    }
}
