package me.supcheg.javafile.typed;

import java.util.List;
import java.util.Optional;

/// The `catch` clauses and the `finally` block of a `try` statement, all
/// built, before its `try` block is: what the `try` block is built under,
/// and what makes the statement of it.
///
/// @param catches the `catch` clauses, in order
/// @param finallyBlock the `finally` block, if any
record TryClauses(List<Instr.Catch> catches, Optional<Block<?, ?>> finallyBlock) {
    TryClauses {
        catches = List.copyOf(catches);
    }

    /// The types of the `catch` clauses, in order.
    List<ExceptionType.OfClass> types() {
        return catches.stream().map(c -> new ExceptionType.OfClass(c.type())).toList();
    }

    /// What the `try` block does with a checked exception thrown in it: a
    /// clause catches it or it is handed on. The `finally` block takes
    /// nothing away, as it completes normally ([FinallyBody]).
    ExceptionScope scope() {
        return new ExceptionScope.Catches(types());
    }

    /// The statement, of the `try` block `body` built under [#scope()].
    ///
    /// @throws IllegalStateException if a clause — of this statement or, by what a binding of this one
    ///     rethrows, of one nested in it — catches a checked exception its `try` block cannot throw
    Instr.Try statement(Block<?, ?> body) {
        Instr.Try statement = new Instr.Try(body, catches, finallyBlock);
        Exceptions.requireCatchable(statement);
        return statement;
    }
}
