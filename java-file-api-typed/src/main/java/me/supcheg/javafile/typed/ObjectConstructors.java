package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody0;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody1;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody2;

/// The constructors of a class that extends `Object`: each begins with the
/// `super()` javac puts there, so its body is all there is to say. A class
/// with another superclass says which constructor of it each of its own
/// calls ([SubclassConstructors]).
///
/// @param <Self> the brand of the class being declared
public sealed interface ObjectConstructors<Self> permits TypedClassBuilder, ThrowingDeclarations {

    /// The declarations these are of.
    ///
    /// @return the declarations
    Declarations<Self> declarations();

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef0, InstanceBody0)].
    ///
    /// @return the fact of the constructor, which `new_` accepts
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    default CtorRef0<Self> declareConstructor() {
        return declarations()
                .declare(UnsafeFacts.ctor(
                        declarations().builder().self(), declarations().traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef1, InstanceBody1)].
    ///
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    default <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declarations()
                .declare(UnsafeFacts.ctor(
                        declarations().builder().self(), p1, declarations().traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef2, InstanceBody2)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    default <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declarations()
                .declare(UnsafeFacts.ctor(
                        declarations().builder().self(), p1, p2, declarations().traits(MemberTraits.DEFAULT)));
    }

    /// Declares and defines a constructor, see [#declareConstructor()].
    ///
    /// @param body the body, given its block and `this`
    /// @return the fact of the constructor
    default CtorRef0<Self> constructor(InstanceBody0<Self, VoidBody, Void> body) {
        CtorRef0<Self> ctor = declareConstructor();
        declarations().builder().define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken)].
    ///
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    default <A1> CtorRef1<Self, A1> constructor(TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        CtorRef1<Self, A1> ctor = declareConstructor(p1);
        declarations().builder().define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken, TypeToken)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    default <A1, A2> CtorRef2<Self, A1, A2> constructor(
            TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        CtorRef2<Self, A1, A2> ctor = declareConstructor(p1, p2);
        declarations().builder().define(ctor, body);
        return ctor;
    }
}
