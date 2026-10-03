import gen.facts.p.Cf_;

/// `x()` and `x_()` would both be named `x_`: neither has a fact (the processor warns, see diagnostics.txt).
class MembersThatWouldShareANameHaveNoFact {
    Object first = Cf_.x_; // error: cannot find symbol
}
