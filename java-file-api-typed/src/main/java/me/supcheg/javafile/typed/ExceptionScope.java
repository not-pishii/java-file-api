package me.supcheg.javafile.typed;

import java.util.List;

/// What a [Block] does with a checked exception thrown in it (JLS 11.2.3):
/// every block is given one when it is created, so [Exceptions] decides at
/// the statement that throws whether the exception is caught or declared.
sealed interface ExceptionScope {

    /// The scope of a block nested in another one that is neither a `try`
    /// block nor a lambda body.
    ExceptionScope PASSES = new Passes();

    /// The block hands the exception to the block it is nested in: a
    /// branch of an `if`, a loop body, a `catch` or `finally` block.
    record Passes() implements ExceptionScope {}

    /// The block is where an exception stops: the body of a method or
    /// constructor, which may throw what its `throws` clause declares, or
    /// the body of a lambda, which may throw what the method of its
    /// functional interface declares, or a block of an expression built
    /// outside of any body, which may throw nothing. An exception that is not a subtype of
    /// one of `types` cannot be thrown here.
    ///
    /// @param boundary what the block is the body of
    /// @param types the declared exception types
    record Declares(Boundary boundary, List<ExceptionType> types) implements ExceptionScope {
        public Declares {
            types = List.copyOf(types);
        }
    }

    /// The block is a `try` block: an exception that is a subtype of one
    /// of `types` is caught, any other is handed on.
    ///
    /// @param types the types of the `catch` clauses, in order
    record Catches(List<ExceptionType.OfClass> types) implements ExceptionScope {
        public Catches {
            types = List.copyOf(types);
        }
    }

    /// The block is a `try` block whose `finally` block cannot complete
    /// normally: whatever the `try` block throws is discarded (JLS 14.20.2),
    /// so it need not be caught or declared.
    record Discards() implements ExceptionScope {}

    /// What a block that [Declares] is the body of: who declares, and what
    /// the author can do about an exception that is not declared.
    enum Boundary {
        /// The body of a method.
        METHOD("the method does not declare", "catch it, or declare the method through cb.throwing(...)"),
        /// The body of a constructor.
        CONSTRUCTOR(
                "the constructor does not declare", "catch it, or declare the constructor through cb.throwing(...)"),
        /// The body of a lambda: only the method of its functional interface declares.
        LAMBDA(
                "the method of the functional interface of the lambda does not declare",
                "catch it inside the lambda: the code around a lambda catches nothing thrown in it"),
        /// A block of an expression built outside of any body, which can only be a field initializer.
        INITIALIZER(
                "nothing declares outside of the body of a member",
                "catch it inside the block, or build the expression in the body of a method or constructor that"
                        + " declares it");

        private final String doesNotDeclare;
        private final String advice;

        Boundary(String doesNotDeclare, String advice) {
            this.doesNotDeclare = doesNotDeclare;
            this.advice = advice;
        }

        /// "the method does not declare", to be followed by the exception.
        String doesNotDeclare() {
            return doesNotDeclare;
        }

        /// What the author can do.
        String advice() {
            return advice;
        }
    }
}
