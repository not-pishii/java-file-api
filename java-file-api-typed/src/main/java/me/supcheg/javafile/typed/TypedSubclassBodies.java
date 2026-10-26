package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ExtendableClassToken;

/// Defines the members of a typed `final` class that extends `S` (§6.5),
/// branded with `Self`: the stage [TypedSubclassBuilder#bodies(me.supcheg.javafile.facts.CtorRef0,
/// TypedSubclassBodyArities.CtorBody0)] gives. Everything [MemberBodies] defines, and the
/// constructors: each has the place of its `super(...)` ([SuperSite]) and
/// `this` only after it, `define(constructor, body)`. The bodies of the constructors,
/// `CtorBody0` and its like, are those of [TypedSubclassBodyArities].
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class TypedSubclassBodies<Self, S> extends TypedSubclassBodyArities<Self, S> {
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
}
