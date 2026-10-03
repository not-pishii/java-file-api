import gen.facts.p.Impl2_;
import gen.facts.p.Impl_;

/// See `expected/diagnostics.txt` for the warnings.
class AnAmbiguousFieldHasNoFact {
    Object constant = Impl_.K; // error: cannot find symbol
    Object instance = Impl_.name; // error: cannot find symbol
    Object ofTheHiddenInterface = Impl2_.K; // error: cannot find symbol
}
