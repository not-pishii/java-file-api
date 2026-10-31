import gen.facts.p.Author_;

/// An element without a default is given before the use is built: no stage
/// before it has `build()`.
class AMissingElementHasNoBuild {
    Object missing = Author_.annotation().build(); // error: cannot find symbol
}
