package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody2;

/// The constructors of a class that extends `S`: each begins with the call
/// of a constructor of `S` its body says ([SuperSite]) — there is no way to
/// declare one that does not, so javac never looks for an `S()` that may not
/// be there (JLS 8.8.7).
///
/// ```java
/// cb.constructor(String_.TOKEN, (ctor, name) -> ctor.super_(base.super_String, name, (b, self) -> b.end()));
/// ```
///
/// A constructor is `public` and declared and defined in one call: nothing
/// needs its fact before its body.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public sealed interface SubclassConstructors<Self, S> permits TypedSubclassBuilder, ThrowingSubclassDeclarations {

    /// The declarations these are of.
    ///
    /// @return the declarations
    Declarations<Self> declarations();

    /// Declares and defines a constructor without parameters.
    ///
    /// @param body the body, given the place of its `super(...)`
    /// @return the fact of the constructor, which `new_` accepts
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    /// @throws IllegalStateException if the body does not begin with `super_`, or calls it twice
    default CtorRef0<Self> constructor(CtorBody0<Self, S> body) {
        return declarations().constructed(body);
    }

    /// Declares and defines a constructor with one parameter.
    ///
    /// @param p1 the parameter type
    /// @param body the body, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    /// @throws IllegalStateException if the body does not begin with `super_`, or calls it twice
    default <A1> CtorRef1<Self, A1> constructor(TypeToken<A1> p1, CtorBody1<Self, S, A1> body) {
        return declarations().constructed(p1, body);
    }

    /// Declares and defines a constructor with two parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    /// @throws IllegalStateException if the body does not begin with `super_`, or calls it twice
    default <A1, A2> CtorRef2<Self, A1, A2> constructor(
            TypeToken<A1> p1, TypeToken<A2> p2, CtorBody2<Self, S, A1, A2> body) {
        return declarations().constructed(p1, p2, body);
    }
}
