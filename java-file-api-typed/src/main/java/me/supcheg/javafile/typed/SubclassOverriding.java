package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody2;

import java.util.List;

/// What is said of a class that extends `S` once its superclass is named:
/// the methods it overrides ([Overriding]), and — the step that ends the
/// header — its first constructor.
///
/// **A subclass has a constructor.** A constructor is declared here
/// ([#declareConstructor()]), which gives its fact before any member, and the
/// members of the class begin with `body(fact, ctor -> ctor.super_(...))`:
/// the builder of the members is what defining a declared constructor gives,
/// so a class that extends `S` and calls no constructor of `S` is not
/// written. A constructor is `public`, declares the exceptions of the stage
/// it is declared at — those of [SubclassHeader#throwing], or none — and
/// begins with the `super(...)` its body says ([SuperSite]). One declared
/// here and defined neither by `body` nor among the members is rejected when
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
    /// exceptions of this stage, and is defined by [#body(CtorRef0, CtorBody0)] — the first
    /// constructor — or by the builder of the members.
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

    /// Ends the header by defining a constructor it declared, one without parameters: the
    /// members of the class follow.
    ///
    /// ```java
    /// var make = sh.declareConstructor();
    /// var cb = sh.body(make, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
    /// ```
    ///
    /// @param ctor a constructor this header declared
    /// @param first the body of the constructor, given the place of its `super(...)`
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has, or
    ///     declared two constructors of the same erased parameters
    /// @throws IllegalStateException if the members have begun already, or `ctor` is not a constructor
    ///     the header of this class declared
    public final TypedSubclassBuilder<Self, S> body(CtorRef0<Self> ctor, CtorBody0<Self, S> first) {
        TypedSubclassBuilder<Self, S> builder = declaration.body(superclass);
        builder.define(ctor, first);
        return builder;
    }

    /// Ends the header by defining a constructor it declared, one with a parameter.
    ///
    /// @param ctor a constructor this header declared
    /// @param first the body of the constructor, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @return the builder of the members
    /// @throws IllegalArgumentException as [#body(CtorRef0, CtorBody0)]
    /// @throws IllegalStateException as [#body(CtorRef0, CtorBody0)]
    public final <A1> TypedSubclassBuilder<Self, S> body(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> first) {
        TypedSubclassBuilder<Self, S> builder = declaration.body(superclass);
        builder.define(ctor, first);
        return builder;
    }

    /// Ends the header by defining a constructor it declared, one with two parameters.
    ///
    /// @param ctor a constructor this header declared
    /// @param first the body of the constructor, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the builder of the members
    /// @throws IllegalArgumentException as [#body(CtorRef0, CtorBody0)]
    /// @throws IllegalStateException as [#body(CtorRef0, CtorBody0)]
    public final <A1, A2> TypedSubclassBuilder<Self, S> body(
            CtorRef2<Self, A1, A2> ctor, CtorBody2<Self, S, A1, A2> first) {
        TypedSubclassBuilder<Self, S> builder = declaration.body(superclass);
        builder.define(ctor, first);
        return builder;
    }
}
