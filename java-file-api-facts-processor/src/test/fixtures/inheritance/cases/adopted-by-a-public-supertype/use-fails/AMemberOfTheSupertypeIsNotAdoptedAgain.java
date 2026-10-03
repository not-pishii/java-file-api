import gen.facts.p.X_;

/// `run()` and `K` of `HiddenI` are facts of `Mid_` alone.
class AMemberOfTheSupertypeIsNotAdoptedAgain {
    Object run = X_.run; // error: cannot find symbol
    Object k = X_.K; // error: cannot find symbol
}
