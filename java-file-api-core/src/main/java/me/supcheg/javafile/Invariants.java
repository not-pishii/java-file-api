package me.supcheg.javafile;

import java.util.Optional;

/// Checks of what the code of this library holds true of itself: a check that
/// fails is a bug of the library, never a mistake of its caller, and it says
/// which assumption broke instead of a bare `NoSuchElementException`.
///
/// The message is a format of [String#format(String, Object...)] and is made
/// only when the check fails.
public final class Invariants {

    private Invariants() {}

    /// Returns the value `optional` is known to hold.
    ///
    /// @param optional the value
    /// @param invariant why it is there, as a format
    /// @param parts the arguments of the format
    /// @param <T> the type of the value
    /// @return the value
    /// @throws IllegalStateException if `optional` is empty
    public static <T> T present(Optional<T> optional, String invariant, Object... parts) {
        require(optional.isPresent(), invariant, parts);
        return optional.get();
    }

    /// Checks that what is known to hold does.
    ///
    /// @param holds the condition
    /// @param invariant what it is, as a format
    /// @param parts the arguments of the format
    /// @throws IllegalStateException if `holds` is `false`
    public static void require(boolean holds, String invariant, Object... parts) {
        if (!holds) {
            throw new IllegalStateException(String.format(invariant, parts));
        }
    }
}
