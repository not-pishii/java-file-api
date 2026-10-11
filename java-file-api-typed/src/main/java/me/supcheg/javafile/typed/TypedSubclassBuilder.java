package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.ExtendableClassToken;

import java.util.List;
import java.util.stream.Stream;

/// Builds the members of a typed `final` class that extends `S` (§6.5),
/// branded with `Self`: everything a [TypedClassBuilder] builds
/// ([MemberBuilder], [Declarations]), and what the superclass adds.
///
/// **`S` is a parameter, not a bound.** The brand `Self` extends nothing to
/// javac; that the class is an `S` is known by the witness [#asSuper()], and
/// by `S` in the type of this builder, which decides what it accepts: a
/// constructor of `S` in `super(...)`, a method of `S` or of a class above it
/// to override, to call as `super.m()`, or to take out of its `protected`
/// wrapper.
///
/// **Every constructor calls one of the superclass.** A constructor is
/// declared with the place of its `super(...)` ([SubclassConstructors],
/// [SuperSite]) and has `this` only after it; the first one is declared in
/// the header and defined by the step that ends it ([SubclassOverriding#body]),
/// which is how this builder is got.
///
/// **Overriding** a method the class inherits from `S` is by its fact
/// ([OverridingDeclarations]): `cb.override(coll.toString, (b, self) -> ...)`.
///
/// **`super.m()` and `protected` members** are those of [SuperAccess].
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class TypedSubclassBuilder<Self, S> extends MemberBuilder<Self>
        implements SubclassConstructors<Self, S>, OverridingDeclarations<Self, S>, SuperAccess<Self, S> {
    private final ExtendableClassToken<S> extended;

    TypedSubclassBuilder(
            ClassDeclaration<Self> declared,
            Inheritance inherited,
            Superclass superclass,
            ExtendableClassToken<S> extended) {
        super(declared, inherited, superclass);
        this.extended = extended;
    }

    /// The witness that the class is of its superclass: `cb.asSuper().apply(self)` is `this` as an
    /// `S`, by which the facts of `S` and of the classes above it are called on it.
    ///
    /// @return the witness
    public Upcast<Self, S> asSuper() {
        return new Upcast<>(self(), extended);
    }

    /// The declarations of the methods and constructors of this class that declare `exceptions`
    /// in their `throws` clause, see [TypedClassBuilder#throwing].
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingSubclassDeclarations<Self, S> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingSubclassDeclarations<>(this, List.of(exceptions));
    }

    /// The declarations of the members whose `throws` clause names a class known to be an
    /// exception by a witness alone — the class being declared, which has no bound to tell it:
    /// `cb.throwing(cb.asSuper(), cb.self())` of a class that extends `Exception`.
    ///
    /// @param witness the witness that `exception` is a `Throwable`
    /// @param exception the class the witness is of
    /// @param others the other exception types of the clause, after it
    /// @param <E> the class the witness is of
    /// @return the declarations of the members that declare the exceptions
    /// @throws IllegalArgumentException if the witness is of another class than `exception`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <E> ThrowingSubclassDeclarations<Self, S> throwing(
            Upcast<E, ? extends Throwable> witness,
            ClassToken<E> exception,
            ClassToken<? extends Throwable>... others) {
        return new ThrowingSubclassDeclarations<>(
                this,
                Stream.concat(Stream.of(Upcast.throwable(witness, exception)), Stream.of(others))
                        .toList());
    }

    /// Defines a declared constructor: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)`
    /// @throws IllegalStateException if the constructor is defined already, or was not declared by this class
    public void define(CtorRef0<Self> ctor, CtorBody0<Self, S> body) {
        defineWithSuper(ctor, body);
    }

    /// Defines a declared constructor with a parameter: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException if the constructor is defined already, or was not declared by this class
    public <A1> void define(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> body) {
        defineWithSuper(ctor, body);
    }

    /// Defines a declared constructor with two parameters: its body begins with `super(...)`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException if the constructor is defined already, or was not declared by this class
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
