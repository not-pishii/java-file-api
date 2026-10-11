package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody2;

import java.util.List;

/// What is said of a class that extends `S` once its superclass is named:
/// the methods it overrides ([Overriding]), and — the step that ends the
/// header — its first constructor.
///
/// **A subclass has a constructor.** The members of the class begin with
/// `body(ctor -> ctor.super_(...))`: the builder of the members is what
/// declaring the first constructor gives, so a class that extends `S` and
/// calls no constructor of `S` is not written. The constructor is `public`,
/// declares the exceptions of this stage — those of [SubclassHeader#throwing],
/// or none — and begins with the `super(...)` its body says
/// ([SuperSite]).
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

    private ThrowingSubclassDeclarations<Self, S> constructors() {
        List<ClassToken<? extends Throwable>> thrown = thrown();
        return new ThrowingSubclassDeclarations<>(declaration.body(superclass), thrown);
    }

    /// Ends the header with the first constructor of the class, one without parameters: the
    /// members of the class follow.
    ///
    /// ```java
    /// var cb = sh.body(ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
    /// ```
    ///
    /// @param first the body of the constructor, given the place of its `super(...)`
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has
    /// @throws IllegalStateException if the members have begun already
    public final TypedSubclassBuilder<Self, S> body(CtorBody0<Self, S> first) {
        ThrowingSubclassDeclarations<Self, S> constructors = constructors();
        constructors.constructor(first);
        return constructors.subclass();
    }

    /// Ends the header with the first constructor of the class, one with a parameter.
    ///
    /// @param p1 the parameter type
    /// @param first the body of the constructor, given the place of its `super(...)` and the parameter
    /// @param <A1> the parameter type
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has
    /// @throws IllegalStateException if the members have begun already
    public final <A1> TypedSubclassBuilder<Self, S> body(TypeToken<A1> p1, CtorBody1<Self, S, A1> first) {
        ThrowingSubclassDeclarations<Self, S> constructors = constructors();
        constructors.constructor(p1, first);
        return constructors.subclass();
    }

    /// Ends the header with the first constructor of the class, one with two parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param first the body of the constructor, given the place of its `super(...)` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has
    /// @throws IllegalStateException if the members have begun already
    public final <A1, A2> TypedSubclassBuilder<Self, S> body(
            TypeToken<A1> p1, TypeToken<A2> p2, CtorBody2<Self, S, A1, A2> first) {
        ThrowingSubclassDeclarations<Self, S> constructors = constructors();
        constructors.constructor(p1, p2, first);
        return constructors.subclass();
    }
}
