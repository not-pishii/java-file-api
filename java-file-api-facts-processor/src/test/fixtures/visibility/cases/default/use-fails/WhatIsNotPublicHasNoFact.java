import gen.facts.p.Vis_;

/// Members that are not `public` have no fact, nor have `public` ones with a type in their signature that is not
/// (the processor warns of the latter, see diagnostics.txt).
class WhatIsNotPublicHasNoFact {
    Object protectedField = Vis_.prot; // error: cannot find symbol
    Object packageField = Vis_.pkg; // error: cannot find symbol
    Object privateField = Vis_.priv; // error: cannot find symbol
    Object protectedConstructor = Vis_.new_int; // error: cannot find symbol
    Object packageConstructor = Vis_.new_String; // error: cannot find symbol
    Object privateConstructor = Vis_.new_long; // error: cannot find symbol
    Object protectedMethod = Vis_.protM; // error: cannot find symbol
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
