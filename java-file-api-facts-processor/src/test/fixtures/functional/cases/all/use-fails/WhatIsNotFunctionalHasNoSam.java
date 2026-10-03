import gen.facts.p.Def_;
import gen.facts.p.Empty_;
import gen.facts.p.Gen_;
import gen.facts.p.Sealed_;
import gen.facts.p.SealedSub_;
import gen.facts.p.Two_;

/// What is not a functional interface has no `sam`: JLS 9.8.
class WhatIsNotFunctionalHasNoSam {
    // two abstract methods
    Object two = Two_.sam; // error: cannot find symbol
    // none
    Object none = Empty_.sam; // error: cannot find symbol
    // the method is overridden by a default one
    Object overridden = Def_.sam; // error: cannot find symbol
    // generic: a lambda cannot implement it
    Object generic = Gen_.sam; // error: cannot find symbol
    // a functional interface is not sealed
    Object sealed = Sealed_.sam; // error: cannot find symbol
    Object sealedSub = SealedSub_.sam; // error: cannot find symbol
}
