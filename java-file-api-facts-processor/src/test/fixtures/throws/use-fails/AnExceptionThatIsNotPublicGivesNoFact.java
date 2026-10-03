import gen.facts.p.Io_;

/// A member that throws a type that is not public has no fact (the processor warns, see diagnostics.txt).
class AnExceptionThatIsNotPublicGivesNoFact {
    Object method = Io_.hidden; // error: cannot find symbol
    Object constructor = Io_.new_String; // error: cannot find symbol
}
