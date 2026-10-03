import gen.facts.p.PolyFn_;
import gen.facts.p.Poly_;

class WhatIsNotAGenericMethodFactHasNone {
    // new gives a constructor no explicit type arguments, and a fact leaves none to inference
    Object genericConstructor = Poly_.new_T; // error: cannot find symbol
    // no lambda implements a generic method (JLS 15.27.3)
    Object sam = PolyFn_.sam; // error: cannot find symbol
}
