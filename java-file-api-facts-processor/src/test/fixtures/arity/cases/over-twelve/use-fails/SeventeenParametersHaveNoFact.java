/// More than 16 parameters: the member has no fact (and the processor warns, see diagnostics.txt).
class SeventeenParametersHaveNoFact {
    Object constructor = Big_.new_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
    Object method = Big_.seventeen_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
    Object staticMethod = Big_.staticSeventeen_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int_int; // error: cannot find symbol
}
