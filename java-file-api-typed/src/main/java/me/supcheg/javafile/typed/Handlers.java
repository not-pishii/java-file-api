package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// Collects the `catch` clauses and the optional `finally` block of a
/// [Block#try_(Consumer, Consumer)]. They are built before the `try` block,
/// which is checked against them: a checked exception thrown in it must be
/// of a clause here, or caught or declared further out.
///
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
public final class Handlers<B extends Block<?, B>> {
    private final B enclosing;
    private final CatchClauses<B> clauses;

    Handlers(B enclosing) {
        this.enclosing = enclosing;
        this.clauses = new CatchClauses<>("try_", enclosing);
    }

    /// Adds `catch (Type v) { body }`. The clause catches `type` and its
    /// subclasses; an exception thrown in `body` — the caught one rethrown
    /// too — is one of the code around the `try`.
    ///
    /// @param type the caught exception type
    /// @param body builds the `catch` block, given the binding
    /// @param <E> the caught exception type
    /// @return this
    /// @throws IllegalStateException if a clause added before catches `type` or a superclass of it, so
    ///     that this one would catch nothing (JLS 11.2.3); if called while another clause is being
    ///     built, or after the `try` block was
    public <E extends Throwable> Handlers<B> catch_(ClassToken<E> type, BiConsumer<? super B, ? super Var<E>> body) {
        clauses.catch_(type, (block, binding) -> enclosing.fill(block, b -> body.accept(b, binding)));
        return this;
    }

    /// Adds `finally { body }`. The block completes normally: it has no
    /// statement that ends it ([FinallyBody]).
    ///
    /// @param body builds the `finally` block
    /// @return this
    /// @throws IllegalStateException if a `finally` block was already added; if called while another
    ///     clause is being built, or after the `try` block was
    public Handlers<B> finally_(Consumer<? super FinallyBody<B>> body) {
        clauses.finally_(block -> enclosing.fill(block, b -> body.accept(new FinallyBody<>(b))));
        return this;
    }

    /// The clauses, complete: the `try` block is built under them.
    TryClauses close() {
        return clauses.close();
    }
}
