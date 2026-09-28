package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/// Collects the `catch` clauses and the optional `finally` block of a
/// [Block#try_(Consumer, Consumer)].
///
/// @param <B> the block type of the `try`/`catch`/`finally` bodies
public final class Handlers<B extends Block<?, B>> {
    private final Function<Consumer<? super B>, B> opener;
    private final List<Instr.Catch> catches = new ArrayList<>();
    private B finallyBlock;

    Handlers(Function<Consumer<? super B>, B> opener) {
        this.opener = opener;
    }

    /// Adds `catch (Type v) { body }`.
    ///
    /// @param type the caught exception type
    /// @param body builds the `catch` block, given the binding
    /// @param <E> the caught exception type
    /// @return this
    public <E extends Throwable> Handlers<B> catch_(ClassToken<E> type, BiConsumer<? super B, ? super Var<E>> body) {
        Var<E> binding = new Var<>(type, "caught exception");
        B block = opener.apply(b -> body.accept(b, binding));
        catches.add(new Instr.Catch(type, binding, block));
        return this;
    }

    /// Adds `finally { body }`.
    ///
    /// @param body builds the `finally` block
    /// @return this
    public Handlers<B> finally_(Consumer<? super B> body) {
        finallyBlock = opener.apply(body);
        return this;
    }

    Instr.Try toInstr(Block<?, ?> body) {
        if (catches.isEmpty() && finallyBlock == null) {
            throw new IllegalArgumentException("try_ requires at least one catch_ or a finally_");
        }
        Optional<Block<?, ?>> finallyOpt = Optional.ofNullable(finallyBlock);
        return new Instr.Try(body, List.copyOf(catches), finallyOpt);
    }
}
