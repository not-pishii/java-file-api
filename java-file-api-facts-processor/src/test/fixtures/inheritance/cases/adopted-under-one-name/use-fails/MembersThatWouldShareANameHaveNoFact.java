import gen.facts.p.Both_;

/// `K` of `One` and `K` of `Other`: javac does not tell them apart either
/// — `Both.K` is ambiguous — so there is no fact, and a warning, see
/// `expected/diagnostics.txt`.
class MembersThatWouldShareANameHaveNoFact {
    Object k = Both_.K; // error: cannot find symbol
}
