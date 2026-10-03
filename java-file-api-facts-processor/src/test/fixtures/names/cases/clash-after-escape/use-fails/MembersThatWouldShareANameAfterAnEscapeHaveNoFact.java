import gen.facts.p.Sh_;

/// The field `x_` and the method `x()` would both be named `x_` (the processor warns, see diagnostics.txt).
class MembersThatWouldShareANameAfterAnEscapeHaveNoFact {
    Object field = Sh_.x_; // error: cannot find symbol
}
