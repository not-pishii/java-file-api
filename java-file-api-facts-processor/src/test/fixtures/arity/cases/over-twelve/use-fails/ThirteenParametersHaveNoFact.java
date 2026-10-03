import gen.facts.p.Big_;

/// More than 12 parameters: the member has no fact (and the processor warns, see diagnostics.txt).
class ThirteenParametersHaveNoFact {
    Object constructor = Big_.new_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
    Object method = Big_.thirteen_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
    Object staticMethod = Big_.staticThirteen_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
}
