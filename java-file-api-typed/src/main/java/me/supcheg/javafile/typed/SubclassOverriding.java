package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import java.util.List;

/// What is said of a class that extends `S` once its superclass is named:
/// the methods it overrides ([Overriding]), its constructors, and — the step
/// that ends the header — that its members follow.
///
/// **A subclass has a constructor.** A constructor is declared here
/// ([#declareConstructor()]) or among the members
/// ([Declarations#declareConstructor()]), which gives its fact, and the bodies
/// of the class begin with that of one, `cb.bodies(fact, ctor ->
/// ctor.super_(...))` ([TypedSubclassBuilder]): so a class that extends `S`
/// and calls no constructor of `S` is not written. A constructor is `public`,
/// declares the exceptions of the stage it is declared at — those of
/// [SubclassHeader#throwing], or none — and begins with the `super(...)` its
/// body says ([SuperSite]). One declared and never defined is rejected when
/// the declaration completes, as any declared member is.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class SubclassOverriding<Self, S> extends Overriding<Self>
        permits SubclassHeader, ThrowingSubclassHeader {
    private final ClassDeclaration<Self> declaration;
    private final ExtendableClassToken<S> superclass;

    SubclassOverriding(ClassDeclaration<Self> declaration, ExtendableClassToken<S> superclass) {
        this.declaration = declaration;
        this.superclass = superclass;
    }

    @Override
    final ClassDeclaration<Self> declaration() {
        return declaration;
    }

    final ExtendableClassToken<S> superclass() {
        return superclass;
    }

    private MemberTraits traits() {
        return new MemberTraits(
                List.copyOf(thrown()),
                MemberTraits.DEFAULT.overridability(),
                MemberTraits.DEFAULT.typeArgs(),
                MemberTraits.DEFAULT.access());
    }

    /// Declares a `public` constructor without parameters, before the class has members: its
    /// fact, which `new_` accepts, is there for every body of the class. It declares the
    /// exceptions of this stage, and is defined by the bodies of the members — the first
    /// constructor by the step that begins them ([TypedSubclassBuilder#bodies(CtorRef0,
    /// TypedSubclassBodies.CtorBody0)]).
    ///
    /// @return the fact of the constructor
    /// @throws IllegalStateException if the header of the class has ended
    public final CtorRef0<Self> declareConstructor() {
        return declaration.intendConstructor(UnsafeFacts.ctor(declaration.self(), traits()), thrown());
    }

    /// Declares a `public` constructor with one parameter, see [#declareConstructor()].
    ///
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalStateException if the header of the class has ended
    public final <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declaration.intendConstructor(UnsafeFacts.ctor(declaration.self(), p1, traits()), thrown());
    }

    /// Declares a `public` constructor with two parameters, see [#declareConstructor()].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalStateException if the header of the class has ended
    public final <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaration.intendConstructor(UnsafeFacts.ctor(declaration.self(), p1, p2, traits()), thrown());
    }

    /// Ends the header: the declarations of the members of the class follow.
    ///
    /// ```java
    /// var make = sh.declareConstructor();
    /// var cb = sh.members();
    /// var bodies = cb.bodies(make, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
    /// ```
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has, or
    ///     declared two constructors of the same erased parameters
    /// @throws IllegalStateException if the members have begun already
    public final TypedSubclassBuilder<Self, S> members() {
        return declaration.members(superclass);
    }
}
