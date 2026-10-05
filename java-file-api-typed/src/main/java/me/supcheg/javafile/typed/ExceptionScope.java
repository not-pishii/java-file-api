package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.RefToken;

import java.util.List;

/// What a [Block] does with a checked exception thrown in it (JLS 11.2.3):
/// every block is given one when it is created, so [Exceptions] decides at
/// the statement that throws whether the exception is caught or declared.
sealed interface ExceptionScope {

    /// The scope of a block nested in another one that is neither a `try`
    /// block nor a lambda body.
    ExceptionScope PASSES = new Passes();

    /// The body of a member or of a lambda that declares no exception: `remedy` is the way out the
    /// message names.
    ///
    /// @param remedy what the author can do besides catching the exception
    /// @return the scope
    static Declares declaresNothing(String remedy) {
        return new Declares(List.of(), remedy);
    }

    /// The block hands the exception to the block it is nested in: a
    /// branch of an `if`, a loop body, a `catch` or `finally` block.
    record Passes() implements ExceptionScope {}

    /// The block is where an exception stops: the body of a method or
    /// constructor, which may throw what its `throws` clause declares, or
    /// the body of a lambda, which may throw what the method of its
    /// functional interface declares. An exception that is not a subtype of
    /// one of `types` cannot be thrown here.
    ///
    /// @param types the declared exception types
    /// @param remedy what the author can do besides catching the exception, for the message
    record Declares(List<RefToken<? extends Throwable>> types, String remedy) implements ExceptionScope {
        public Declares {
            types = List.copyOf(types);
        }
    }

    /// The block is a `try` block: an exception that is a subtype of one
    /// of `types` is caught, any other is handed on.
    ///
    /// @param types the types of the `catch` clauses, in order
    record Catches(List<ClassToken<? extends Throwable>> types) implements ExceptionScope {
        public Catches {
            types = List.copyOf(types);
        }
    }

    /// The block is a `try` block whose `finally` block cannot complete
    /// normally: whatever the `try` block throws is discarded (JLS 14.20.2),
    /// so it need not be caught or declared.
    record Discards() implements ExceptionScope {}
}
