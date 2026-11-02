package me.supcheg.javafile;

import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

import java.util.Optional;
import java.util.concurrent.Callable;

/// Checks of what the code of this library holds true of itself: a check that
/// fails is a bug of the library, never a mistake of its caller, and it says
/// which assumption broke instead of a bare `NoSuchElementException`.
///
/// The message is a format of [String#format(String, Object...)] and is made
/// only when the check fails.
///
/// Public because the modules of this library share it (`facts`, `lang-model`,
/// `facts-processor`, `typed`) and the build has no module system to export it to them alone:
/// it is no API for the code a generator writes, and code that generates Java does not need it.
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
    @FormatMethod
    public static <T> T present(Optional<T> optional, @FormatString String invariant, Object... parts) {
        return optional.orElseThrow(() -> new IllegalStateException(String.format(invariant, parts)));
    }

    /// Returns what `action` gives, which is known not to fail though its signature lets it: the digest
    /// of an algorithm every Java platform has.
    ///
    /// @param action what is done
    /// @param invariant why it does not fail, as a format
    /// @param parts the arguments of the format
    /// @param <T> the type of the result
    /// @return the result
    /// @throws IllegalStateException if `action` throws, with what it threw as the cause
    @FormatMethod
    public static <T> T sure(Callable<T> action, @FormatString String invariant, Object... parts) {
        try {
            return action.call();
        } catch (Exception e) {
            throw new IllegalStateException(String.format(invariant, parts), e);
        }
    }

    /// The merge function of a collector whose keys are known to differ: each has one value, so there is
    /// nothing to merge.
    ///
    /// @param first the value a key has
    /// @param second another value of that key
    /// @param <T> the type of the values
    /// @return never
    /// @throws IllegalStateException always
    // every use of it is a `Collectors.toMap` merge function, which the contract of the collector never calls,
    // so `@DoNotCall` would reject the references that are the only way this method is meant to be used
    @SuppressWarnings("DoNotCallSuggester")
    public static <T> T unique(T first, T second) {
        throw new IllegalStateException("one key has two values: " + first + " and " + second);
    }

    /// Checks that what is known to hold does.
    ///
    /// @param holds the condition
    /// @param invariant what it is, as a format
    /// @param parts the arguments of the format
    /// @throws IllegalStateException if `holds` is `false`
    @FormatMethod
    public static void require(boolean holds, @FormatString String invariant, Object... parts) {
        if (!holds) {
            throw new IllegalStateException(String.format(invariant, parts));
        }
    }
}
