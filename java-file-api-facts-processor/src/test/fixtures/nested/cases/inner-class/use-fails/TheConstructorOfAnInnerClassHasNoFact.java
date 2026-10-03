import gen.facts.p.Outer_NonStatic_;

/// A constructor of an inner class needs an enclosing instance (the processor warns, see diagnostics.txt).
class TheConstructorOfAnInnerClassHasNoFact {
    Object withoutParameters = Outer_NonStatic_.new_; // error: cannot find symbol
    Object withAnInt = Outer_NonStatic_.new_int; // error: cannot find symbol
}
