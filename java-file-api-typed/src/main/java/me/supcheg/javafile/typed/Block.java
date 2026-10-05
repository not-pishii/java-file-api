package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
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
/// would allow it.
///
/// **Scopes.** Blocks form a tree: every nested block knows the block it is
/// nested in, and a lambda body is marked as a lambda boundary. Every [Var]
/// and [LoopCtl] knows the block it belongs to. Appending a statement checks
/// each variable it refers to: the variable's block must be this block or
/// one this block is nested in, and a [MutVar] or [LoopCtl] must not be
/// reached across a lambda boundary. A variable or loop capability that was
/// smuggled out of its HOAS lambda — into a sibling branch, past the end of
/// its block, into another method — is rejected right at the misuse.
///
/// **Reachability (JLS 14.22).** Statements that end the block (`return_`,
/// `throw_`, `break_`, `continue_`, and the constructs that cannot complete
/// normally: `ifElse`, `ifInstanceOfElse`, `tryTerminated`, `loopForever`)
/// return a [Terminated] token and close the block: appending after them,
/// which would be unreachable code, fails fast. The statement forms that
/// return the block (`if_`, `while_`, `try_`, ...) must complete normally;
/// one that cannot — `if_` with both branches ending, `while_` over a
/// constant `true` without a `break_`, `try_` whose every branch ends — is
/// rejected with a hint to the form that returns [Terminated]. Loop
/// conditions are folded as javac folds constant expressions (JLS 15.29),
/// and a loop body that could never run (`while_` over a constant `false`)
/// is rejected.
///
/// **Checked exceptions (JLS 11.2.3).** A statement that can throw a checked
/// exception — a call of a method or constructor whose fact declares one,
/// a `throw_` of one — is accepted only where the exception is caught by a
/// `catch_` of an enclosing `try_`/`tryTerminated`, or declared by the
/// member the body belongs to ([TypedClassBuilder#throwing]); anywhere else
/// appending it fails, at that statement. A `catch_` covers the subclasses
/// of its type, unchecked exceptions are not tracked, and a `catch_` of a
/// checked exception its `try` block cannot throw is rejected as javac
/// rejects it. See [Exceptions].
///
/// Only the innermost block being built accepts statements; using the
/// builder of an enclosing block inside a nested block or lambda fails fast.
///
/// @param <R> the result type of the enclosing method or lambda
/// @param <B> the type of this block, which nested blocks share
public abstract sealed class Block<R, B extends Block<R, B>> permits Body, VoidBody {
    private static final String LOOP_FOREVER_HINT =
            "a loop that never completes ends the block: build it with loopForever, which returns the Terminated"
                    + " of this block, or break_ out of it";

    private final @Nullable Block<?, ?> parent;
    private final boolean lambdaBoundary;
    private final String what;
    private final ExceptionScope exceptionScope;
    private final List<Instr> instrs = new ArrayList<>();
    private @Nullable String endedBy;

    Block(@Nullable Block<?, ?> parent, boolean lambdaBoundary, String what, ExceptionScope exceptionScope) {
        this.parent = parent;
        this.lambdaBoundary = lambdaBoundary;
        this.what = what;
        this.exceptionScope = exceptionScope;
    }

    abstract B self();

    /// Creates a block nested in this one, of the same kind.
    abstract B child(String what, ExceptionScope exceptionScope);

    /// Creates a block nested in this one, of the same kind, that hands
    /// the exceptions thrown in it to this one.
    final B child(String what) {
        return child(what, ExceptionScope.PASSES);
    }

    /// What becomes of a checked exception thrown in this block.
    final ExceptionScope exceptionScope() {
        return exceptionScope;
    }

    /// The block this one is nested in, or `null` for the body of a member.
    final @Nullable Block<?, ?> parent() {
        return parent;
    }

    /// Whether this block is the body of a lambda of the generated code.
    final boolean isLambdaBoundary() {
        return lambdaBoundary;
    }

    /// Where this block is, for diagnostics: `then-branch of if_ in body of method m`.
    final String path() {
        return parent == null ? what : what + " in " + parent.path();
    }

    /// The statements of this block so far, as a view.
    final List<Instr> instrs() {
        return Collections.unmodifiableList(instrs);
    }

    final void requireOpen() {
        Scopes.requireInnermost(this, "the " + path());
        if (endedBy != null) {
            throw new IllegalStateException("the " + path() + " has already ended with " + endedBy
                    + "; a statement after it would be unreachable (JLS 14.22)");
        }
    }

    /// Appends a statement that must complete normally, and continues this block.
    final B append(Instr instr) {
        requireOpen();
        ScopeCheck.check(instr, this);
        Exceptions.check(instr, this);
        instrs.add(instr);
        return self();
    }

    /// Appends a statement form that returns this block to be continued; if
    /// it cannot complete normally, anything after it would be unreachable.
    private B continueWith(Instr instr, String form, String hint) {
        requireOpen();
        ScopeCheck.check(instr, this);
        Exceptions.check(instr, this);
        if (!Reachability.canCompleteNormally(instr)) {
            throw new IllegalStateException(form + " in the " + path()
                    + " cannot complete normally, so a statement after it would be unreachable (JLS 14.22); "
                    + hint);
        }
        instrs.add(instr);
        return self();
    }

    final Terminated<R> appendFinal(Instr instr, String form) {
        append(instr);
        return endHere(form);
    }

    final Terminated<R> endHere(String form) {
        requireOpen();
        endedBy = form;
        return new Terminated<>(this);
    }

    /// Builds `child` from `spec`, with `child` the innermost scope.
    final void fill(B child, Consumer<? super B> spec) {
        Scopes.within(child, () -> {
            spec.accept(child);
            return child;
        });
    }

    /// Builds `child` from `spec`, which must hand back the token of `child`.
    final void fillEnding(B child, Function<? super B, Terminated<R>> spec) {
        requireIssuedBy(Scopes.within(child, () -> spec.apply(child)), child);
    }

    private B open(String what, Consumer<? super B> spec) {
        return open(what, ExceptionScope.PASSES, spec);
    }

    private B open(String what, ExceptionScope exceptionScope, Consumer<? super B> spec) {
        B child = child(what, exceptionScope);
        fill(child, spec);
        return child;
    }

    private B closed(String what, Function<? super B, Terminated<R>> spec) {
        return closed(what, ExceptionScope.PASSES, spec);
    }

    private B closed(String what, ExceptionScope exceptionScope, Function<? super B, Terminated<R>> spec) {
        B child = child(what, exceptionScope);
        fillEnding(child, spec);
        return child;
    }

    /// A [Terminated] proves the end of the block that issued it and no
    /// other (§6.3); Java types cannot brand every block, so this is checked
    /// where the token is handed back.
    static void requireIssuedBy(Terminated<?> terminated, Block<?, ?> block) {
        if (terminated.issuer() != block) {
            throw new IllegalStateException("the Terminated token handed back for the " + block.path()
                    + " was issued by the " + terminated.issuer().path()
                    + ": a Terminated proves the end of the block that issued it only; return the token of the"
                    + " block the lambda was given");
        }
    }

    private static void requireReachableBody(Expr<Prim.Bool> condition, String form) {
        if (Constants.isConstant(condition.node(), false)) {
            throw new IllegalStateException("the body of " + form
                    + " with a constant false condition is unreachable (JLS 14.22); drop the loop");
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
        Var<T> var = new Var<>(type, "local variable", this);
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
        MutVar<T> var = new MutVar<>(type, "mutable local variable", this);
        append(new Instr.Let(var, init.node()));
        return rest.apply(var);
    }

    /// Appends `if (condition) { then }`. It never ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @return this block
    public final B if_(Expr<Prim.Bool> condition, Consumer<? super B> then) {
        requireOpen();
        return append(new Instr.If(condition.node(), open("then-branch of if_", then), Optional.empty()));
    }

    /// Appends `if (condition) { then } else { otherwise }` as a statement.
    /// At least one branch must complete normally; if both end, use
    /// [#ifElse(Expr, Function, Function)], which ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @param otherwise builds the `else` block
    /// @return this block
    /// @throws IllegalStateException if both branches end
    public final B if_(Expr<Prim.Bool> condition, Consumer<? super B> then, Consumer<? super B> otherwise) {
        requireOpen();
        B thenBlock = open("then-branch of if_", then);
        B elseBlock = open("else-branch of if_", otherwise);
        return continueWith(
                new Instr.If(condition.node(), thenBlock, Optional.of(elseBlock)),
                "if_ whose branches both end",
                "build it with ifElse, which returns the Terminated of this block");
    }

    /// Appends an `if`-`else` whose branches both end, and so ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block, which must end
    /// @param otherwise builds the `else` block, which must end
    /// @return the proof that this block ended
    public final Terminated<R> ifElse(
            Expr<Prim.Bool> condition,
            Function<? super B, Terminated<R>> then,
            Function<? super B, Terminated<R>> otherwise) {
        requireOpen();
        B thenBlock = closed("then-branch of ifElse", then);
        B elseBlock = closed("else-branch of ifElse", otherwise);
        return appendFinal(new Instr.If(condition.node(), thenBlock, Optional.of(elseBlock)), "ifElse");
    }

    /// Appends `if (operand instanceof U v) { then }`; the binding `v` exists
    /// only in `then` (flow typing through HOAS).
    ///
    /// `U` must be a subtype of the operand's type, and the test checked
    /// (JLS 15.20.2, 5.1.6.2): `U` is reifiable, or the operand's type is a
    /// parameterized supertype of `U` that determines its type arguments —
    /// `listOfStrings instanceof ArrayList<String> a` is accepted,
    /// `object instanceof List<String> l` is not.
    ///
    /// @param operand the tested expression
    /// @param type the pattern type
    /// @param then builds the `then` block, given the binding
    /// @param <U> the pattern type
    /// @return this block
    /// @throws IllegalArgumentException if the test would be unchecked
    public final <U> B ifInstanceOf(
            Expr<? super U> operand, RefToken<U> type, BiConsumer<? super B, ? super Var<U>> then) {
        requireOpen();
        Tokens.requireCheckedCast(operand.type(), type, "an instanceof pattern");
        B thenBlock = child("then-branch of ifInstanceOf");
        Var<U> binding = new Var<>(type, "pattern binding", thenBlock);
        fill(thenBlock, b -> then.accept(b, binding));
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
    /// @throws IllegalArgumentException if the test would be unchecked
    public final <U> Terminated<R> ifInstanceOfElse(
            Expr<? super U> operand,
            RefToken<U> type,
            BiFunction<? super B, ? super Var<U>, Terminated<R>> then,
            Function<? super B, Terminated<R>> otherwise) {
        requireOpen();
        Tokens.requireCheckedCast(operand.type(), type, "an instanceof pattern");
        B thenBlock = child("then-branch of ifInstanceOfElse");
        Var<U> binding = new Var<>(type, "pattern binding", thenBlock);
        fillEnding(thenBlock, b -> then.apply(b, binding));
        B elseBlock = closed("else-branch of ifInstanceOfElse", otherwise);
        return appendFinal(
                new Instr.IfInstance(operand.node(), type, binding, thenBlock, Optional.of(elseBlock)),
                "ifInstanceOfElse");
    }

    /// Appends `while (condition) { body }`.
    ///
    /// A loop that cannot complete normally — a constant `true` condition
    /// and no `break_` — is rejected: use [#loopForever(Consumer)].
    ///
    /// @param condition the condition
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @return this block
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public final B while_(Expr<Prim.Bool> condition, BiConsumer<? super B, LoopCtl> body) {
        requireOpen();
        requireReachableBody(condition, "while_");
        B bodyBlock = child("body of while_");
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, ctl));
        return continueWith(
                new Instr.While(ctl, condition.node(), bodyBlock),
                "while_ with a constant true condition and no break_",
                LOOP_FOREVER_HINT);
    }

    /// Appends `while (true) { body }`, which never completes normally and so
    /// ends this block (JLS 14.22): the body gets no `break` capability for
    /// this loop, so the only ways out are `return_`, `throw_`, or a
    /// `break_`/`continue_` of an enclosing loop.
    ///
    /// @param body builds the body
    /// @return the proof that this block ended
    public final Terminated<R> loopForever(Consumer<? super B> body) {
        requireOpen();
        B bodyBlock = child("body of loopForever");
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, body);
        return appendFinal(new Instr.While(ctl, Expressions.literal(true).node(), bodyBlock), "loopForever");
    }

    /// Appends `do { body } while (condition);`.
    ///
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @param condition the condition
    /// @return this block
    /// @throws IllegalStateException if the loop never completes
    public final B doWhile(BiConsumer<? super B, LoopCtl> body, Expr<Prim.Bool> condition) {
        requireOpen();
        B bodyBlock = child("body of doWhile");
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, ctl));
        return continueWith(
                new Instr.DoWhile(ctl, bodyBlock, condition.node()),
                "doWhile whose body ends without continue_, or whose condition is constant true, and no break_",
                LOOP_FOREVER_HINT);
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
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public final <T> B for_(
            TypeToken<T> type,
            Expr<? extends T> init,
            Function<? super MutVar<T>, Expr<Prim.Bool>> condition,
            Function<? super MutVar<T>, ? extends Effect> update,
            LoopBody<? super B, ? super MutVar<T>> body) {
        requireOpen();
        B bodyBlock = child("body of for_");
        LoopCtl ctl = new LoopCtl(bodyBlock);
        MutVar<T> var = new MutVar<>(type, "loop variable", bodyBlock);
        Expr<Prim.Bool> conditionExpr = condition.apply(var);
        requireReachableBody(conditionExpr, "for_");
        Node updateNode = Assignment.nodeOf(update.apply(var));
        fill(bodyBlock, b -> body.accept(b, var, ctl));
        return continueWith(
                new Instr.For(ctl, var, init.node(), conditionExpr.node(), updateNode, bodyBlock),
                "for_ with a constant true condition and no break_",
                LOOP_FOREVER_HINT);
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
        B bodyBlock = child("body of forEach");
        LoopCtl ctl = new LoopCtl(bodyBlock);
        Var<T> var = new Var<>(element, "loop variable", bodyBlock);
        fill(bodyBlock, b -> body.accept(b, var, ctl));
        return append(new Instr.ForEach(ctl, var, iterable.node(), bodyBlock));
    }

    /// Appends `break;` out of the loop `ctl` belongs to, and ends this block.
    ///
    /// @param ctl the loop capability
    /// @return the proof that this block ended
    /// @throws IllegalStateException if this block is not inside that loop's body
    public final Terminated<R> break_(LoopCtl ctl) {
        return appendFinal(new Instr.Break(ctl), "break_");
    }

    /// Appends `continue;` of the loop `ctl` belongs to, and ends this block.
    ///
    /// @param ctl the loop capability
    /// @return the proof that this block ended
    /// @throws IllegalStateException if this block is not inside that loop's body
    public final Terminated<R> continue_(LoopCtl ctl) {
        return appendFinal(new Instr.Continue(ctl), "continue_");
    }

    /// Appends `throw exception;` and ends this block. A checked exception —
    /// by the static type of `exception` — must be caught by a `catch_` of
    /// an enclosing `try_` or declared by the member
    /// ([TypedClassBuilder#throwing]). A caught exception that is rethrown
    /// is thrown at the type of its `catch_`: there is no precise rethrow
    /// (JLS 11.2.2).
    ///
    /// @param exception the thrown expression
    /// @return the proof that this block ended
    /// @throws IllegalStateException if the exception is checked and neither caught nor declared
    /// @throws IllegalArgumentException if the type of `exception` is neither a class nor a type variable,
    ///     which only an expression of `Unsafe` can be
    public final Terminated<R> throw_(Expr<? extends Throwable> exception) {
        return appendFinal(new Instr.Throw(exception.node(), ExceptionType.of(exception.type())), "throw_");
    }

    /// Appends `try { body } catch ... finally ...` as a statement. It must
    /// complete normally; if the `try` block and every `catch` end, use
    /// [#tryTerminated(Consumer, Function)], which ends this block.
    ///
    /// **The handlers come first**, as they are built first: what a
    /// statement of the `try` block may throw depends on the `catch`
    /// clauses around it, and a statement is checked when it is built, so a
    /// call in `body` that throws a checked exception no clause catches
    /// fails right there. The rendered code is in the order of Java.
    ///
    /// A `catch_` is rejected if it can catch nothing: when it is added, if
    /// a preceding `catch_` has its type or a superclass of it; once `body`
    /// is built, if its type is a checked exception — other than `Exception`
    /// and `Throwable` — that the `try` block cannot throw, or of which the
    /// preceding clauses leave nothing (JLS 11.2.3).
    ///
    /// @param handlers adds `catch` clauses and the `finally` block
    /// @param body builds the `try` block
    /// @return this block
    /// @throws IllegalStateException if `handlers` adds neither a `catch` nor a `finally`, if the
    ///     statement cannot complete normally, or if a `catch_` can catch nothing
    public final B try_(Consumer<? super Handlers<B>> handlers, Consumer<? super B> body) {
        requireOpen();
        Handlers<B> collected = new Handlers<>(self());
        handlers.accept(collected);
        TryClauses clauses = collected.close();
        B bodyBlock = open("try block of try_", clauses.scope(), body);
        return continueWith(
                clauses.statement(bodyBlock),
                "try_ whose try block and every catch_ end, or whose finally_ ends,",
                "build it with tryTerminated, which returns the Terminated of this block");
    }

    /// Appends `try { body } catch ... finally ...` whose `try` block and
    /// every `catch` block end, and so ends this block. The `finally` block,
    /// if any, is a plain block.
    ///
    /// As with [#try_(Consumer, Consumer)], the handlers come first and are
    /// built first, and a `catch_` that can catch nothing is rejected.
    ///
    /// @param handlers adds `catch` clauses, each of which must end, and the `finally` block
    /// @param body builds the `try` block, which must end
    /// @return the proof that this block ended
    /// @throws IllegalStateException if `handlers` adds neither a `catch` nor a `finally`, or if a
    ///     `catch_` can catch nothing
    public final Terminated<R> tryTerminated(
            Consumer<? super TerminatedHandlers<R, B>> handlers, Function<? super B, Terminated<R>> body) {
        requireOpen();
        TerminatedHandlers<R, B> collected = new TerminatedHandlers<>(self());
        handlers.accept(collected);
        TryClauses clauses = collected.close();
        B bodyBlock = closed("try block of tryTerminated", clauses.scope(), body);
        return appendFinal(clauses.statement(bodyBlock), "tryTerminated");
    }

    /// Appends an untyped core statement. Its reachability is the author's
    /// responsibility; it is assumed to complete normally. So are the
    /// checked exceptions it throws: none of them is checked, and the
    /// `catch_` clauses of a `try_` whose `try` block holds the statement
    /// are not checked against what the block throws.
    ///
    /// @param stmt the statement, from [Unsafe#stmt(me.supcheg.javafile.code.Stmt)]
    /// @return this block
    public final B add(UnsafeStmt stmt) {
        return append(new Instr.Raw(stmt.stmt()));
    }
}
