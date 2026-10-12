package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.ExtendableClassToken;

/// Defines the members of a typed `final` class that extends `S` (§6.5),
/// branded with `Self`: the stage [TypedSubclassBuilder#bodies(CtorRef0,
/// CtorBody0)] gives. Everything [MemberBodies] defines, and the
/// constructors: each has the place of its `super(...)` ([SuperSite]) and
/// `this` only after it.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class TypedSubclassBodies<Self, S> extends MemberBodies<Self, S> {
    private final ExtendableClassToken<S> extended;

    TypedSubclassBodies(Declared<Self> declared, ExtendableClassToken<S> extended) {
        super(declared);
        this.extended = extended;
    }

    /// The witness that the class is of its superclass: `bodies.asSuper().apply(self)` is `this`
    /// as an `S`, by which the facts of `S` and of the classes above it are called on it.
    ///
    /// @return the witness
    public Upcast<Self, S> asSuper() {
        return new Upcast<>(self(), extended);
    }

    /// Defines a declared constructor: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)`
    /// @throws IllegalStateException if the body does not begin with `super(...)`, and as a
    ///     definition of a method does ([MemberBodies])
    public void define(CtorRef0<Self> ctor, CtorBody0<Self, S> body) {
        defineWithSuper(ctor, body);
    }

    /// Defines a declared constructor with a parameter: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as [#define(CtorRef0, CtorBody0)]
    public <A1> void define(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> body) {
        defineWithSuper(ctor, body);
    }

    /// Defines a declared constructor with two parameters: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException as [#define(CtorRef0, CtorBody0)]
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, CtorBody2<Self, S, A1, A2> body) {
        defineWithSuper(ctor, body);
    }

    // ------------------------------------------------------------------
    // Bodies of constructors: the place of `super(...)`, then the parameters
    // ------------------------------------------------------------------

    /// The body of a constructor without parameters of a class that extends `S`.
    ///
    /// @param <Self> the class
    /// @param <S> its superclass
    @FunctionalInterface
    public interface CtorBody0<Self, S> {
        /// Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
        ///
        /// @param ctor the place of `super(...)`
        /// @return the proof that the body ended, as `super_` gave it
        Terminated<Void> build(SuperSite<Self, S> ctor);
    }

    /// The body of a constructor with one parameter of a class that extends `S`.
    ///
    /// @param <Self> the class
    /// @param <S> its superclass
    /// @param <A1> the parameter type
    @FunctionalInterface
    public interface CtorBody1<Self, S, A1> {
        /// Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
        ///
        /// @param ctor the place of `super(...)`
        /// @param p1 the parameter
        /// @return the proof that the body ended, as `super_` gave it
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1);
    }

    /// The body of a constructor with two parameters of a class that extends `S`.
    ///
    /// @param <Self> the class
    /// @param <S> its superclass
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    @FunctionalInterface
    public interface CtorBody2<Self, S, A1, A2> {
        /// Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
        ///
        /// @param ctor the place of `super(...)`
        /// @param p1 the first parameter
        /// @param p2 the second parameter
        /// @return the proof that the body ended, as `super_` gave it
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2);
    }
}
