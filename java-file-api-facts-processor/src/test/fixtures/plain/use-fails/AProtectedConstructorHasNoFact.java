import gen.facts.p.Abs_;

/// `Abs(long)` is `protected`: only `public` members have facts.
class AProtectedConstructorHasNoFact {
    Object none = Abs_.super_long; // error: cannot find symbol
}
