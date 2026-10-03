import gen.facts.p.Pub2_;

/// `zero()` of `H0` and `one()` of `Pub1` are facts of `Pub1_`.
class WhatIsBeyondAPublicSupertypeIsNotAdopted {
    Object ofTheHiddenSupertypeBeyond = Pub2_.zero; // error: cannot find symbol
    Object ofThePublicSupertype = Pub2_.one; // error: cannot find symbol
}
