import gen.facts.p.Tk_;

/// `Other` is the type of a signature, and `Other_` its metamodel: the field `Other` would be named `Other_`, a name
/// the metamodel itself uses (the processor warns, see diagnostics.txt).
class AMemberWhoseEscapedNameIsTakenHasNoFact {
    Object unescaped = Tk_.Other; // error: cannot find symbol
    Object escaped = Tk_.Other_; // error: cannot find symbol
}
