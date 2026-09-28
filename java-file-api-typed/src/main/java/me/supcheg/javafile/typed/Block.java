package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/// A block of statements being built (§6.2, §6.3).
///
/// Statements are appended in call order. Variables are bound through host
/// lambdas (HOAS) — [#let(TypeToken, Expr, Function)] hands the new variable
/// to the rest of the block — so a variable is usable exactly where Java
/// would allow it. Statements that end the block (`return_`, `throw_`,
/// `break_`, `continue_`, `ifElse`, ...) return a [Terminated] token and
/// close the block: appending after them, which would be unreachable code,
/// fails fast.
///
/// Only the innermost block being built accepts statements; using the
/// builder of an enclosing block inside a nested block or lambda fails fast.
///
/// @param <R> the result type of the enclosing method or lambda
/// @param <B> the type of this block, which nested blocks share
public abstract sealed class Block<R, B extends Block<R, B>> permits Body, VoidBody {
    private final List<Instr> instrs = new ArrayList<>();
    private boolean ended;

    Block() {}

    abstract B self();

    abstract B child();

    final List<Instr> instrs() {
        return List.copyOf(instrs);
    }

    final void requireOpen() {
        Scopes.requireInnermost(this, "this block");
        if (ended) {
            throw new IllegalStateException(
                    "the block has already ended; a statement after its end would be unreachable");
        }
    }

    final B append(Instr instr) {
        requireOpen();
        instrs.add(instr);
        return self();
    }

    final Terminated<R> appendFinal(Instr instr) {
        append(instr);
        return endHere();
    }

    final Terminated<R> endHere() {
        requireOpen();
        ended = true;
        return new Terminated<>(this);
    }

    final B open(Consumer<? super B> spec) {
        B child = child();
        Scopes.within(child, () -> {
            spec.accept(child);
            return child;
        });
        return child;
    }

    final B closed(Function<? super B, Terminated<R>> spec) {
        B child = child();
        requireIssuedBy(Scopes.within(child, () -> spec.apply(child)), child);
        return child;
    }

    static void requireIssuedBy(Terminated<?> terminated, Block<?, ?> block) {
        if (terminated.issuer() != block) {
            throw new IllegalStateException("the Terminated token was issued by another block than the one it ends");
        }
    }

    /// Appends an expression statement: a call, an instance creation, or an
    /// assignment.
    ///
    /// @param effect the effect
    /// @return this block
    public final B exec(Effect effect) {
        return append(new Instr.Exec(Assignment.nodeOf(effect)));
    }

    /// Declares a local variable with an initializer and hands it to the
    /// rest of the block, `T v = init; rest...`.
    ///
    /// `rest` continues this very block: in a body that must terminate it
    /// returns the [Terminated] of this block, elsewhere it may return this
    /// block, and `let` returns whatever `rest` returns.
    ///
    /// @param type the declared type
    /// @param init the initializer
    /// @param rest the rest of the block, given the variable
    /// @param <T> the variable type
    /// @param <K> what the rest of the block returns
    /// @return what `rest` returned
    public final <T, K> K let(TypeToken<T> type, Expr<? extends T> init, Function<? super Var<T>, K> rest) {
        Var<T> var = new Var<>(type, "local variable");
        append(new Instr.Let(var, init.node()));
        return rest.apply(var);
    }

    /// Like [#let(TypeToken, Expr, Function)], but the variable can be assigned.
    ///
    /// @param type the declared type
    /// @param init the initializer
    /// @param rest the rest of the block, given the variable
    /// @param <T> the variable type
    /// @param <K> what the rest of the block returns
    /// @return what `rest` returned
    public final <T, K> K letVar(TypeToken<T> type, Expr<? extends T> init, Function<? super MutVar<T>, K> rest) {
        MutVar<T> var = new MutVar<>(type, "mutable local variable");
        append(new Instr.Let(var, init.node()));
        return rest.apply(var);
    }

    /// Appends `if (condition) { then }`. It never ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @return this block
    public final B if_(Expr<Boolean> condition, Consumer<? super B> then) {
        requireOpen();
        return append(new Instr.If(condition.node(), open(then), Optional.empty()));
    }

    /// Appends `if (condition) { then } else { otherwise }` as a statement.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @param otherwise builds the `else` block
    /// @return this block
    public final B if_(Expr<Boolean> condition, Consumer<? super B> then, Consumer<? super B> otherwise) {
        requireOpen();
        B thenBlock = open(then);
        return append(new Instr.If(condition.node(), thenBlock, Optional.of(open(otherwise))));
    }

    /// Appends an `if`-`else` whose branches both end, and so ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block, which must end
    /// @param otherwise builds the `else` block, which must end
    /// @return the proof that this block ended
    public final Terminated<R> ifElse(
            Expr<Boolean> condition,
            Function<? super B, Terminated<R>> then,
            Function<? super B, Terminated<R>> otherwise) {
        requireOpen();
        B thenBlock = closed(then);
        return appendFinal(new Instr.If(condition.node(), thenBlock, Optional.of(closed(otherwise))));
    }

    /// Appends `if (operand instanceof U v) { then }`; the binding `v` exists
    /// only in `then` (flow typing through HOAS).
    ///
    /// `U` must be a subtype of the operand's type, and reifiable.
    ///
    /// @param operand the tested expression
    /// @param type the pattern type
    /// @param then builds the `then` block, given the binding
    /// @param <U> the pattern type
    /// @return this block
    /// @throws IllegalArgumentException if `type` is not reifiable
    public final <U> B ifInstanceOf(
            Expr<? super U> operand, RefToken<U> type, BiConsumer<? super B, ? super Var<U>> then) {
        requireOpen();
        Tokens.requireReifiable(type);
        Var<U> binding = new Var<>(type, "pattern binding");
        B thenBlock = open(b -> then.accept(b, binding));
        return append(new Instr.IfInstance(operand.node(), type, binding, thenBlock, Optional.empty()));
    }

    /// Appends `if (operand instanceof U v) { then } else { otherwise }`
    /// whose branches both end, and so ends this block.
    ///
    /// @param operand the tested expression
    /// @param type the pattern type
    /// @param then builds the `then` block, given the binding; it must end
    /// @param otherwise builds the `else` block; it must end
    /// @param <U> the pattern type
    /// @return the proof that this block ended
    /// @throws IllegalArgumentException if `type` is not reifiable
    public final <U> Terminated<R> ifInstanceOfElse(
            Expr<? super U> operand,
            RefToken<U> type,
            BiFunction<? super B, ? super Var<U>, Terminated<R>> then,
            Function<? super B, Terminated<R>> otherwise) {
        requireOpen();
        Tokens.requireReifiable(type);
        Var<U> binding = new Var<>(type, "pattern binding");
        B thenBlock = closed(b -> then.apply(b, binding));
        return appendFinal(
                new Instr.IfInstance(operand.node(), type, binding, thenBlock, Optional.of(closed(otherwise))));
    }

    /// Appends `while (condition) { body }`.
    ///
    /// @param condition the condition
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @return this block
    public final B while_(Expr<Boolean> condition, BiConsumer<? super B, LoopCtl> body) {
        requireOpen();
        LoopCtl ctl = new LoopCtl();
        return append(new Instr.While(ctl, condition.node(), open(b -> body.accept(b, ctl))));
    }

    /// Appends `do { body } while (condition);`.
    ///
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @param condition the condition
    /// @return this block
    public final B doWhile(BiConsumer<? super B, LoopCtl> body, Expr<Boolean> condition) {
        requireOpen();
        LoopCtl ctl = new LoopCtl();
        return append(new Instr.DoWhile(ctl, open(b -> body.accept(b, ctl)), condition.node()));
    }

    /// Appends `for (T v = init; condition; update) { body }`.
    ///
    /// @param type the loop variable type
    /// @param init the initializer
    /// @param condition the condition, given the variable
    /// @param update the update, given the variable
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    public final <T> B for_(
            TypeToken<T> type,
            Expr<? extends T> init,
            Function<? super MutVar<T>, Expr<Boolean>> condition,
            Function<? super MutVar<T>, ? extends Effect> update,
            LoopBody<? super B, ? super MutVar<T>> body) {
        requireOpen();
        LoopCtl ctl = new LoopCtl();
        MutVar<T> var = new MutVar<>(type, "loop variable");
        Node conditionNode = condition.apply(var).node();
        Node updateNode = Assignment.nodeOf(update.apply(var));
        B bodyBlock = open(b -> body.accept(b, var, ctl));
        return append(new Instr.For(ctl, var, init.node(), conditionNode, updateNode, bodyBlock));
    }

    /// Appends `for (T v : iterable) { body }`.
    ///
    /// @param element the loop variable type
    /// @param iterable the iterated expression
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    public final <T> B forEach(
            TypeToken<T> element,
            Expr<? extends Iterable<? extends T>> iterable,
            LoopBody<? super B, ? super Var<T>> body) {
        requireOpen();
        LoopCtl ctl = new LoopCtl();
        Var<T> var = new Var<>(element, "loop variable");
        return append(new Instr.ForEach(ctl, var, iterable.node(), open(b -> body.accept(b, var, ctl))));
    }

    /// Appends `break;` out of the loop `ctl` belongs to, and ends this block.
    ///
    /// @param ctl the loop capability
    /// @return the proof that this block ended
    public final Terminated<R> break_(LoopCtl ctl) {
        return appendFinal(new Instr.Break(ctl));
    }

    /// Appends `continue;` of the loop `ctl` belongs to, and ends this block.
    ///
    /// @param ctl the loop capability
    /// @return the proof that this block ended
    public final Terminated<R> continue_(LoopCtl ctl) {
        return appendFinal(new Instr.Continue(ctl));
    }

    /// Appends `throw exception;` and ends this block. Lowering checks that
    /// a checked exception is caught or declared.
    ///
    /// @param exception the thrown expression
    /// @return the proof that this block ended
    public final Terminated<R> throw_(Expr<? extends Throwable> exception) {
        return appendFinal(new Instr.Throw(exception.node(), exception.type()));
    }

    /// Appends `try { body } catch ... finally ...`.
    ///
    /// @param body builds the `try` block
    /// @param handlers adds `catch` clauses and the `finally` block
    /// @return this block
    /// @throws IllegalArgumentException if `handlers` adds neither a `catch` nor a `finally`
    public final B try_(Consumer<? super B> body, Consumer<? super Handlers<B>> handlers) {
        requireOpen();
        B bodyBlock = open(body);
        Handlers<B> collected = new Handlers<B>(this::open);
        handlers.accept(collected);
        return append(collected.toInstr(bodyBlock));
    }

    /// Appends an untyped core statement.
    ///
    /// @param stmt the statement, from [Unsafe#stmt(me.supcheg.javafile.code.Stmt)]
    /// @return this block
    public final B add(UnsafeStmt stmt) {
        return append(new Instr.Raw(stmt.stmt()));
    }
}
