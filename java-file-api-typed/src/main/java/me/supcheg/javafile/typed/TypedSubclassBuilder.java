package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.typed.TypedSubclassBodies.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBodies.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBodies.CtorBody2;

import java.util.stream.Stream;

/// Declares the members of a typed `final` class that extends `S` (§6.5),
/// branded with `Self`: everything a [TypedClassBuilder] declares
/// ([MemberBuilder], [Declarations]), and what the superclass adds.
///
/// **`S` is a parameter, not a bound.** The brand `Self` extends nothing to
/// javac; that the class is an `S` is known by the witness [#asSuper()], and
/// by `S` in the type of this builder, which decides what it accepts: a
/// method of `S` or of a class above it to override, or to take out of its
/// `protected` wrapper.
///
/// **Every constructor calls one of the superclass, and the class has one.**
/// The declarations end by giving a declared constructor its body, which
/// begins with `super(...)` ([#bodies(CtorRef0, CtorBody0)], [SuperSite]): so
/// a subclass without a constructor has no bodies, and none is ever rendered.
///
/// ```java
/// var sh = h.extends_(proof);
/// var cb = sh.members();
/// var ctor = cb.declareConstructor(String_.TOKEN);
/// var describe = cb.declareOverride(Base_.describe);
/// var bodies = cb.bodies(ctor, (c, name) -> c.super_(Base_.new_String, name, (b, self) -> b.end()));
/// bodies.define(describe, (b, self) -> b.return_(bodies.superCall(self, Base_.describe)));
/// ```
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class TypedSubclassBuilder<Self, S> extends MemberBuilder<Self, S> {
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
    public final <E> ThrowingDeclarations<Self, S> throwing(
            Upcast<E, ? extends Throwable> witness,
            ClassToken<E> exception,
            ClassToken<? extends Throwable>... others) {
        return new ThrowingDeclarations<>(
                this,
                Stream.concat(Stream.of(Upcast.throwable(witness, exception)), Stream.of(others))
                        .toList());
    }

    /// The declarations of the class end and the bodies of its members begin, with that of a
    /// constructor: `ctor.super_(...)`, whose last argument builds what follows `super(...)`.
    ///
    /// What can be judged of the declarations alone is judged here, as
    /// [TypedClassBuilder#bodies()] does; the body of the constructor is built and lowered
    /// here, as every body is by the call that gives it ([MemberBodies]).
    ///
    /// @param ctor a declared constructor of the class
    /// @param first its body, given the place of its `super(...)`
    /// @return the stage that defines the other declared members
    /// @throws IllegalStateException if the declarations have ended already, or leave undone what
    ///     only a method of the class can do ([SignatureRegistry#requireComplete()]); if the
    ///     constructor was not declared by this class; and as a definition of the bodies does
    ///     ([TypedSubclassBodies#define(CtorRef0, CtorBody0)])
    public TypedSubclassBodies<Self, S> bodies(CtorRef0<Self> ctor, CtorBody0<Self, S> first) {
        TypedSubclassBodies<Self, S> bodies = begin(declared -> new TypedSubclassBodies<>(declared, extended));
        bodies.define(ctor, first);
        return bodies;
    }

    /// The declarations of the class end and the bodies of its members begin, with that of a
    /// constructor with one parameter, see [#bodies(CtorRef0, CtorBody0)].
    ///
    /// @param ctor a declared constructor of the class
    /// @param first its body, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @return the stage that defines the other declared members
    /// @throws IllegalStateException as [#bodies(CtorRef0, CtorBody0)]
    public <A1> TypedSubclassBodies<Self, S> bodies(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> first) {
        TypedSubclassBodies<Self, S> bodies = begin(declared -> new TypedSubclassBodies<>(declared, extended));
        bodies.define(ctor, first);
        return bodies;
    }

    /// The declarations of the class end and the bodies of its members begin, with that of a
    /// constructor with two parameters, see [#bodies(CtorRef0, CtorBody0)].
    ///
    /// @param ctor a declared constructor of the class
    /// @param first its body, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the stage that defines the other declared members
    /// @throws IllegalStateException as [#bodies(CtorRef0, CtorBody0)]
    public <A1, A2> TypedSubclassBodies<Self, S> bodies(CtorRef2<Self, A1, A2> ctor, CtorBody2<Self, S, A1, A2> first) {
        TypedSubclassBodies<Self, S> bodies = begin(declared -> new TypedSubclassBodies<>(declared, extended));
        bodies.define(ctor, first);
        return bodies;
    }
}
