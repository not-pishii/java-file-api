package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/// Collects the `catch` clauses and the optional `finally` block of a
/// [Block#try_(Consumer, Consumer)].
///
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
public final class Handlers<B extends Block<?, B>> {
    private final B enclosing;
    private final List<Instr.Catch> catches = new ArrayList<>();
    private @Nullable B finallyBlock;

    Handlers(B enclosing) {
        this.enclosing = enclosing;
    }

    /// Adds `catch (Type v) { body }`.
    ///
    /// @param type the caught exception type
    /// @param body builds the `catch` block, given the binding
    /// @param <E> the caught exception type
    /// @return this
    public <E extends Throwable> Handlers<B> catch_(ClassToken<E> type, BiConsumer<? super B, ? super Var<E>> body) {
        B block = enclosing.child("catch block of try_");
        Var<E> binding = new Var<>(type, "caught exception", block);
        enclosing.fill(block, b -> body.accept(b, binding));
        catches.add(new Instr.Catch(type, binding, block));
        return this;
    }

    /// Adds `finally { body }`.
    ///
    /// @param body builds the `finally` block
    /// @return this
    /// @throws IllegalStateException if a `finally` block was already added
    public Handlers<B> finally_(Consumer<? super B> body) {
        if (finallyBlock != null) {
            throw new IllegalStateException("try_ already has a finally_ block");
        }
        B block = enclosing.child("finally block of try_");
        enclosing.fill(block, body);
        finallyBlock = block;
        return this;
    }

    Instr.Try toInstr(Block<?, ?> body) {
        if (catches.isEmpty() && finallyBlock == null) {
            throw new IllegalArgumentException("try_ requires at least one catch_ or a finally_");
        }
        return new Instr.Try(body, List.copyOf(catches), Optional.ofNullable(finallyBlock));
    }
}
