package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/// Collects the `catch` clauses, each of which must end, and the optional
/// `finally` block of a [Block#tryTerminated(java.util.function.Function, Consumer)].
///
/// @param <R> the result type of the enclosing method or lambda
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
public final class TerminatedHandlers<R, B extends Block<R, B>> {
    private final B enclosing;
    private final List<Instr.Catch> catches = new ArrayList<>();
    private @Nullable B finallyBlock;

    TerminatedHandlers(B enclosing) {
        this.enclosing = enclosing;
    }

    /// Adds `catch (Type v) { body }` whose block ends.
    ///
    /// @param type the caught exception type
    /// @param body builds the `catch` block, given the binding; it must end
    /// @param <E> the caught exception type
    /// @return this
    public <E extends Throwable> TerminatedHandlers<R, B> catch_(
            ClassToken<E> type, BiFunction<? super B, ? super Var<E>, Terminated<R>> body) {
        B block = enclosing.child("catch block of tryTerminated");
        Var<E> binding = new Var<>(type, "caught exception", block);
        enclosing.fillEnding(block, b -> body.apply(b, binding));
        catches.add(new Instr.Catch(type, binding, block));
        return this;
    }

    /// Adds `finally { body }`. The `finally` block is a plain block: the
    /// statement ends whether or not it does.
    ///
    /// @param body builds the `finally` block
    /// @return this
    /// @throws IllegalStateException if a `finally` block was already added
    public TerminatedHandlers<R, B> finally_(Consumer<? super B> body) {
        if (finallyBlock != null) {
            throw new IllegalStateException("tryTerminated already has a finally_ block");
        }
        B block = enclosing.child("finally block of tryTerminated");
        enclosing.fill(block, body);
        finallyBlock = block;
        return this;
    }

    Instr.Try toInstr(Block<?, ?> body) {
        if (catches.isEmpty() && finallyBlock == null) {
            throw new IllegalArgumentException("tryTerminated requires at least one catch_ or a finally_");
        }
        return new Instr.Try(body, List.copyOf(catches), Optional.ofNullable(finallyBlock));
    }
}
