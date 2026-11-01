package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/// The `catch` clauses and the `finally` block of a `try` statement being
/// built, behind [Handlers] and [TerminatedHandlers].
///
/// They are built before the `try` block, one at a time, and the collector
/// is in one of three phases: [Phase.Open] to a clause, [Phase.Building]
/// one, or [Phase.Closed] once the `try` block is to be built
/// ([#close()]). A clause is added in the first alone. The handle is in the
/// hands of the generator's lambdas, which Java types cannot keep from
/// calling it while a clause is being built or after the statement is
/// made — a `catch_` from inside the block of another would put it before
/// that one, and javac goes by the order — so the phase is checked where it
/// is called, as the innermost scope is for a block ([Scopes]).
///
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
final class CatchClauses<B extends Block<?, B>> {
    private final String form;
    private final B enclosing;
    private Phase phase = new Phase.Open(new TryClauses(List.of(), new Instr.Finally.None()));

    private sealed interface Phase {
        /// A clause may be added: `clauses` are the ones built so far.
        record Open(TryClauses clauses) implements Phase {}

        /// The block of `clause` is being built.
        record Building(String clause) implements Phase {}

        /// The clauses were handed to the `try` block.
        record Closed() implements Phase {}
    }

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
    /// @throws IllegalStateException if a clause added before catches `type`, or if this is not the time
    ///     to add a clause
    <E extends Throwable> void catch_(ClassToken<E> type, BiConsumer<? super B, ? super Var<E>> fill) {
        catch_(type, type, fill);
    }

    /// Adds a `catch` clause of a class that is an exception by a witness alone.
    ///
    /// @param type the caught exception type, as the type of the binding
    /// @param caught the same class, as the exception class it is ([Upcast#throwable])
    /// @param fill builds the block of the clause, given the block and the binding
    /// @throws IllegalStateException as [#catch_(ClassToken, BiConsumer)]
    <E> void catch_(
            ClassToken<E> type, ClassToken<? extends Throwable> caught, BiConsumer<? super B, ? super Var<E>> fill) {
        String clause = "catch_ of " + type;
        TryClauses before = open(clause);
        Exceptions.requireNotCaught(new ExceptionType.OfClass(caught), before.types(), statement());
        phase = new Phase.Building(clause);
        B block = enclosing.child("catch block of " + form);
        Var<E> binding = new Var<>(type, Var.Role.Fixed.CAUGHT_EXCEPTION, block);
        fill.accept(block, binding);
        phase = new Phase.Open(new TryClauses(
                Stream.concat(
                                before.catches().stream(),
                                Stream.of(new Instr.Catch(List.of(caught), caught, binding, block)))
                        .toList(),
                before.finallyBlock()));
    }

    /// Adds a multi-catch clause, `catch (A | B v) { ... }`.
    ///
    /// The binding is typed by the least upper bound of `types`, which Java does not write in the
    /// source and computes from the facts ([Exceptions#leastUpperBound]): the alternatives themselves
    /// are what the clause names and what javac checks.
    ///
    /// @param types the alternatives, in the order they are written
    /// @param fill builds the block of the clause, given the block and the binding
    /// @throws IllegalArgumentException if `types` is empty; two alternatives are the same class or
    ///     related by subclassing, which javac rejects (JLS 14.20); or the facts name no class common
    ///     to the alternatives, so the binding has no type to check the body with
    /// @throws IllegalStateException if a clause added before catches one of the alternatives, or if
    ///     this is not the time to add a clause
    void catch_(
            List<? extends ClassToken<? extends Throwable>> types,
            BiConsumer<? super B, ? super Var<? extends Throwable>> fill) {
        List<ClassToken<? extends Throwable>> alternatives = List.copyOf(types);
        if (alternatives.isEmpty()) {
            throw new IllegalArgumentException("a catch_ of the " + statement()
                    + " names no exception class: a catch clause catches at least one (JLS 14.20)");
        }
        String clause = "catch_ of " + Exceptions.alternatives(alternatives);
        TryClauses before = open(clause);
        Exceptions.requireUnrelated(alternatives, statement());
        Exceptions.requireNotCaught(alternatives, before.types(), statement());
        ClassToken<? extends Throwable> type = Exceptions.leastUpperBound(alternatives, statement());
        phase = new Phase.Building(clause);
        B block = enclosing.child("catch block of " + form);
        Var<? extends Throwable> binding = new Var<>(type, Var.Role.Fixed.CAUGHT_EXCEPTION, block);
        fill.accept(block, binding);
        phase = new Phase.Open(new TryClauses(
                Stream.concat(before.catches().stream(), Stream.of(new Instr.Catch(alternatives, type, binding, block)))
                        .toList(),
                before.finallyBlock()));
    }

    /// Adds the `finally` block.
    ///
    /// @param fill builds the block
    /// @throws IllegalStateException if a `finally` block was already added, or if this is not the time to
    ///     add a clause
    void finally_(Consumer<? super B> fill) {
        TryClauses before = open("finally_");
        if (before.finallyBlock() instanceof Instr.Finally.Of) {
            throw new IllegalStateException("the " + statement() + " already has a finally_ block");
        }
        phase = new Phase.Building("finally_");
        B block = enclosing.finallyChild("finally block of " + form);
        fill.accept(block);
        phase = new Phase.Open(new TryClauses(before.catches(), new Instr.Finally.Of(block)));
    }

    /// The clauses, complete: none can be added afterwards, as the `try`
    /// block is built and checked under these.
    ///
    /// @throws IllegalStateException if there is neither a `catch` clause nor a `finally` block
    TryClauses close() {
        TryClauses clauses = open("the try block");
        if (clauses.catches().isEmpty() && clauses.finallyBlock() instanceof Instr.Finally.None) {
            throw new IllegalStateException("the " + statement() + " requires at least one catch_ or a finally_");
        }
        phase = new Phase.Closed();
        return clauses;
    }

    /// The clauses built so far, if `next` may come now.
    private TryClauses open(String next) {
        return switch (phase) {
            case Phase.Open(var clauses) -> clauses;
            case Phase.Building(var clause) ->
                throw new IllegalStateException(next + " of the " + statement() + " while its " + clause
                        + " is being built: a clause is added by the handlers of the statement itself, not from"
                        + " inside the block of another clause, which it would come before");
            case Phase.Closed _ ->
                throw new IllegalStateException(next + " of the " + statement() + " after its try block was"
                        + " built: the clauses of a try are added by its handlers, which run before its try"
                        + " block, and nowhere else");
        };
    }

    /// The statement and where it is, for messages: `try_ in the body of method m`.
    private String statement() {
        return form + " in the " + enclosing.path();
    }
}
