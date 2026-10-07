package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/// Collects the `catch` clauses, each of which must end, and the optional
/// `finally` block of a [Block#tryTerminated(Consumer, java.util.function.Function)].
/// They are built before the `try` block, which is checked against them: a
/// checked exception thrown in it must be of a clause here, or caught or
/// declared further out.
///
/// @param <R> the result type of the enclosing method or lambda
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
public final class TerminatedHandlers<R, B extends Block<R, B>> {
    private final B enclosing;
    private final CatchClauses<B> clauses;

    TerminatedHandlers(B enclosing) {
        this.enclosing = enclosing;
        this.clauses = new CatchClauses<>("tryTerminated", enclosing);
    }

    /// Adds `catch (Type v) { body }` whose block ends. The clause catches
    /// `type` and its subclasses; an exception thrown in `body` — the caught
    /// one rethrown too — is one of the code around the `try`.
    ///
    /// @param type the caught exception type
    /// @param body builds the `catch` block, given the binding; it must end
    /// @param <E> the caught exception type
    /// @return this
    /// @throws IllegalStateException if a clause added before catches `type` or a superclass of it, so
    ///     that this one would catch nothing (JLS 11.2.3); if called while another clause is being
    ///     built, or after the `try` block was
    public <E extends Throwable> TerminatedHandlers<R, B> catch_(
            ClassToken<E> type, BiFunction<? super B, ? super Var<E>, Terminated<R>> body) {
        clauses.catch_(type, (block, binding) -> enclosing.fillEnding(block, b -> body.apply(b, binding)));
        return this;
    }

    /// Adds `finally { body }`. The block completes normally, and the
    /// statement ends all the same: it has no statement that ends it
    /// ([FinallyBody]).
    ///
    /// @param body builds the `finally` block
    /// @return this
    /// @throws IllegalStateException if a `finally` block was already added; if called while another
    ///     clause is being built, or after the `try` block was
    public TerminatedHandlers<R, B> finally_(Consumer<? super FinallyBody<B>> body) {
        clauses.finally_(block -> enclosing.fill(block, b -> body.accept(new FinallyBody<>(b))));
        return this;
    }

    /// The clauses, complete: the `try` block is built under them.
    TryClauses close() {
        return clauses.close();
    }
}
