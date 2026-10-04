import gen.facts.p.Impl_;

class AnAmbiguousFieldHasNoFactOfTheSubtype {
    Object instance = Impl_.k; // error: cannot find symbol
    Object constant = Impl_.s; // error: cannot find symbol
}
