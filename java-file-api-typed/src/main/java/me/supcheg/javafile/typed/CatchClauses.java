package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.RefToken;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/// The `catch` clauses and the `finally` block of a `try` statement being
/// built, behind [Handlers] and [TerminatedHandlers].
///
/// They are built before the `try` block: [#scope()] is what the `try`
/// block does with an exception thrown in it, so each of its statements is
/// checked when it is appended ([Exceptions]). Once the `try` block is built
/// the clauses are checked against what it throws ([#toInstr(Block)]).
///
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
final class CatchClauses<B extends Block<?, B>> {
    private final String form;
    private final B enclosing;
    private final List<Instr.Catch> catches = new ArrayList<>();
    private @Nullable B finallyBlock;
    private boolean complete;

    /// @param form the statement, `try_` or `tryTerminated`, for messages
    /// @param enclosing the block the statement is appended to
    CatchClauses(String form, B enclosing) {
        this.form = form;
        this.enclosing = enclosing;
    }

    /// Adds a `catch` clause.
    ///
    /// @param type the caught exception type
    /// @param fill builds the block of the clause, given the block and the binding
    /// @throws IllegalStateException if a clause added before catches `type`
    <E extends Throwable> void catch_(ClassToken<E> type, BiConsumer<? super B, ? super Var<E>> fill) {
        requireIncomplete("catch_");
        Exceptions.requireNotCaught(type, types(), form);
        B block = enclosing.child("catch block of " + form);
        Var<E> binding = new Var<>(type, "caught exception", block);
        fill.accept(block, binding);
        catches.add(new Instr.Catch(type, binding, block));
    }

    /// Adds the `finally` block.
    ///
    /// @param fill builds the block
    /// @throws IllegalStateException if a `finally` block was already added
    void finally_(Consumer<? super B> fill) {
        requireIncomplete("finally_");
        if (finallyBlock != null) {
            throw new IllegalStateException(form + " already has a finally_ block");
        }
        B block = enclosing.child("finally block of " + form);
        fill.accept(block);
        finallyBlock = block;
    }

    /// What the `try` block does with a checked exception thrown in it.
    /// No clause can be added afterwards: the `try` block is checked
    /// against these.
    ///
    /// @throws IllegalArgumentException if there is neither a `catch` clause nor a `finally` block
    ExceptionScope scope() {
        if (catches.isEmpty() && finallyBlock == null) {
            throw new IllegalArgumentException(form + " requires at least one catch_ or a finally_");
        }
        complete = true;
        return finallyBlock != null && !Reachability.canCompleteNormally(finallyBlock)
                ? new ExceptionScope.Discards()
                : new ExceptionScope.Catches(types());
    }

    /// The statement, of the `try` block `body` built under [#scope()].
    ///
    /// @throws IllegalStateException if a clause catches a checked exception `body` cannot throw; not
    ///     checked if `body` holds untyped code of `Unsafe`
    Instr.Try toInstr(Block<?, ?> body) {
        if (!Exceptions.throwsUnknown(body)) {
            List<RefToken<? extends Throwable>> thrown = Exceptions.thrown(body).toList();
            List<ClassToken<? extends Throwable>> types = types();
            IntStream.range(0, types.size())
                    .forEach(i -> Exceptions.requireCatchable(types.get(i), types.subList(0, i), thrown, form));
        }
        return new Instr.Try(body, List.copyOf(catches), Optional.ofNullable(finallyBlock));
    }

    private List<ClassToken<? extends Throwable>> types() {
        return catches.stream()
                .<ClassToken<? extends Throwable>>map(Instr.Catch::type)
                .toList();
    }

    private void requireIncomplete(String clause) {
        if (complete) {
            throw new IllegalStateException(clause + " after the try block of " + form + " was built: the clauses"
                    + " of a try are added by its handlers, which run before its try block, and nowhere else");
        }
    }
}
