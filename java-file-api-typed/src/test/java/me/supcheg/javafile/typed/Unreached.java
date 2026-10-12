package me.supcheg.javafile.typed;

/// What the specification of a test returns where it is to be rejected
/// before its end: a specification returns the bodies of the members, and one
/// whose subject is a rejection on the way to them has none to return.
final class Unreached {
    private Unreached() {}

    /// Fails the test: the specification came to its end, and was to be rejected before it.
    ///
    /// @param <T> the stage the specification returns
    /// @return never
    static <T> T here() {
        throw new AssertionError("the specification was to be rejected before its end, and came to it");
    }
}
