package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/// The `finally` block of a `try` statement, which always completes
/// normally.
///
/// A `finally` block that cannot complete normally — one that ends with a
/// `return`, a `yield`, a `throw`, a `break` or a `continue`, or with a
/// statement that never completes — discards the result and the exception of
/// its `try` statement (JLS 14.20.2), and javac warns of it (`-Xlint:finally`).
/// It is not representable here: this block has the statements that
/// complete normally and none of those that end a block — no `return_`,
/// `yield_`, `throw_`, `break_`, `continue_`, `ifElse`, `loopForever` or
/// `tryTerminated` — so a `finally` block that ends does not compile. The
/// statement forms it has are rejected where they are built if they cannot
/// complete normally, as in every block ([Block]): an `if_` both of whose
/// branches end, a `while_` over a constant `true` without a `break_`, a
/// `try_` whose every block ends.
///
/// The blocks nested in it are plain blocks of the code around the `try` —
/// a branch may `return_`, a loop of the `finally` block may be broken, a
/// `try_` in it may throw and catch — for a statement that holds them
/// completes normally whenever one of its ways through does.
///
/// @param <B> the block type of the code around the `try`, which the blocks nested in this one are of
public final class FinallyBody<B extends Block<?, B>> {
    private final B block;

    FinallyBody(B block) {
        this.block = block;
    }

    /// Appends an expression statement, see [Block#exec(Effect)].
    ///
    /// @param effect the effect
    /// @return this block
    public FinallyBody<B> exec(Effect effect) {
        block.exec(effect);
        return this;
    }

    /// Declares a local variable, see [Block#let(TypeToken, Expr, Function)].
    ///
    /// @param type the declared type
    /// @param init the initializer
    /// @param rest the rest of the block, given the variable
    /// @param <T> the variable type
    /// @param <K> what the rest of the block returns
    /// @return what `rest` returned
    public <T, K> K let(TypeToken<T> type, Expr<? extends T> init, Function<? super Var<T>, K> rest) {
        return block.let(type, init, rest);
    }

    /// Declares a local variable that can be assigned, see [Block#letVar(TypeToken, Expr, Function)].
    ///
    /// @param type the declared type
    /// @param init the initializer
    /// @param rest the rest of the block, given the variable
    /// @param <T> the variable type
    /// @param <K> what the rest of the block returns
    /// @return what `rest` returned
    public <T, K> K letVar(TypeToken<T> type, Expr<? extends T> init, Function<? super MutVar<T>, K> rest) {
        return block.letVar(type, init, rest);
    }

    /// Appends `if (condition) { then }`, see [Block#if_(Expr, Consumer)].
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @return this block
    public FinallyBody<B> if_(Expr<Prim.Bool> condition, Consumer<? super B> then) {
        block.if_(condition, then);
        return this;
    }

    /// Appends `if (condition) { then } else { otherwise }`, at least one
    /// branch of which completes normally, see [Block#if_(Expr, Consumer, Consumer)].
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @param otherwise builds the `else` block
    /// @return this block
    /// @throws IllegalStateException if both branches end
    public FinallyBody<B> if_(Expr<Prim.Bool> condition, Consumer<? super B> then, Consumer<? super B> otherwise) {
        block.if_(condition, then, otherwise);
        return this;
    }

    /// Appends `if (operand instanceof U v) { then }`, see [Block#ifInstanceOf(Expr, RefToken, BiConsumer)].
    ///
    /// @param operand the tested expression
    /// @param type the pattern type
    /// @param then builds the `then` block, given the binding
    /// @param <U> the pattern type
    /// @return this block
    /// @throws IllegalArgumentException if the test would be unchecked
    public <U> FinallyBody<B> ifInstanceOf(
            Expr<? super U> operand, RefToken<U> type, BiConsumer<? super B, ? super Var<U>> then) {
        block.ifInstanceOf(operand, type, then);
        return this;
    }

    /// Appends `while (condition) { body }`, see [Block#while_(Expr, BiConsumer)].
    ///
    /// @param condition the condition
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @return this block
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public FinallyBody<B> while_(Expr<Prim.Bool> condition, BiConsumer<? super B, LoopCtl> body) {
        block.while_(condition, body);
        return this;
    }

    /// Appends `do { body } while (condition);`, see [Block#doWhile(BiConsumer, Expr)].
    ///
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @param condition the condition
    /// @return this block
    /// @throws IllegalStateException if the loop never completes
    public FinallyBody<B> doWhile(BiConsumer<? super B, LoopCtl> body, Expr<Prim.Bool> condition) {
        block.doWhile(body, condition);
        return this;
    }

    /// Appends `for (T v = init; condition; update) { body }`, see
    /// [Block#for_(TypeToken, Expr, Function, Function, LoopBody)].
    ///
    /// @param type the loop variable type
    /// @param init the initializer
    /// @param condition the condition, given the variable
    /// @param update the update, given the variable
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public <T> FinallyBody<B> for_(
            TypeToken<T> type,
            Expr<? extends T> init,
            Function<? super MutVar<T>, Expr<Prim.Bool>> condition,
            Function<? super MutVar<T>, ? extends Effect> update,
            LoopBody<? super B, ? super MutVar<T>> body) {
        block.for_(type, init, condition, update, body);
        return this;
    }

    /// Appends `for (T v : iterable) { body }`, see [Block#forEach(TypeToken, Expr, LoopBody)].
    ///
    /// @param element the loop variable type
    /// @param iterable the iterated expression
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    public <T> FinallyBody<B> forEach(
            TypeToken<T> element,
            Expr<? extends Iterable<? extends T>> iterable,
            LoopBody<? super B, ? super Var<T>> body) {
        block.forEach(element, iterable, body);
        return this;
    }

    /// Appends `try { body } catch ... finally ...` that completes normally,
    /// see [Block#try_(Consumer, Consumer)].
    ///
    /// @param handlers adds `catch` clauses and the `finally` block
    /// @param body builds the `try` block
    /// @return this block
    /// @throws IllegalStateException if `handlers` adds neither a `catch` nor a `finally`, if the
    ///     statement cannot complete normally, or if a `catch_` can catch nothing
    public FinallyBody<B> try_(Consumer<? super Handlers<B>> handlers, Consumer<? super B> body) {
        block.try_(handlers, body);
        return this;
    }

    /// Appends an untyped core statement, see [Block#add(UnsafeStmt)]: that
    /// it completes normally is its author's responsibility.
    ///
    /// @param stmt the statement, from [Unsafe#stmt(me.supcheg.javafile.code.Stmt)]
    /// @return this block
    public FinallyBody<B> add(UnsafeStmt stmt) {
        block.add(stmt);
        return this;
    }
}
