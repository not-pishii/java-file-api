import gen.facts.p.PubAbs_;
import gen.facts.p.PubFn2_;

/// `Object get()` of the hidden supertype is not what `get()` is on the
/// subtype: the fact is `PStr_.get`.
class TheMethodOfThePublicSupertypeIsNotAdopted {
    Object ofTheInterface = PubFn2_.get; // error: cannot find symbol
    Object ofTheClass = PubAbs_.get; // error: cannot find symbol
}
