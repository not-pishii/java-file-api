import gen.facts.p.Vis_;

/// Members that are neither `public` nor `protected` have no fact, nor have accessible ones with a type in their
/// signature that is not `public` (the processor warns of the latter, see diagnostics.txt). A `protected`
/// constructor is no `new_`: it is a `super_`, for a subclass.
class WhatIsNotPublicHasNoFact {
    Object packageField = Vis_.pkg; // error: cannot find symbol
    Object privateField = Vis_.priv; // error: cannot find symbol
    Object protectedConstructor = Vis_.new_int; // error: cannot find symbol
    Object packageConstructor = Vis_.new_String; // error: cannot find symbol
    Object privateConstructor = Vis_.new_long; // error: cannot find symbol
    Object packageMethod = Vis_.pkgM; // error: cannot find symbol
    Object privateMethod = Vis_.privM; // error: cannot find symbol
    // a type that is not public
    Object field = Vis_.hf; // error: cannot find symbol
    Object fieldOfAGenericType = Vis_.hl; // error: cannot find symbol
    Object constructor = Vis_.new_Hidden; // error: cannot find symbol
    Object result = Vis_.hidden; // error: cannot find symbol
    Object parameter = Vis_.takes_Hidden; // error: cannot find symbol
    Object array = Vis_.arr; // error: cannot find symbol
    Object nested = Vis_.nested_PkgNested; // error: cannot find symbol
}
