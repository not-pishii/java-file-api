import gen.facts.p.Mix_;

/// A generic constructor, and a member that mentions a type no metamodel can be made of, have no fact
/// (the processor warns, see diagnostics.txt).
class WhatNoMetamodelCanBeMadeOfHasNoFact {
    Object genericConstructor = Mix_.new_T_int; // error: cannot find symbol
    Object dollarInTheName = Mix_.dollar; // error: cannot find symbol
    Object annotation = Mix_.marker; // error: cannot find symbol
    // as a type argument
    Object annotationAsATypeArgument = Mix_.markers; // error: cannot find symbol
    // as a bound of a type parameter
    Object annotationAsABound = Mix_.marked_T; // error: cannot find symbol
}
