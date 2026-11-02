package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
/// reached across a lambda boundary, nor a [LoopCtl] from a block of a
/// `switch` expression. A variable or loop capability that was
/// smuggled out of its HOAS lambda — into a sibling branch, past the end of
/// its block, into another method — is rejected right at the misuse.
///
/// **Reachability (JLS 14.22).** Statements that end the block (`return_`,
/// `yield_`, `throw_`, `break_`, `continue_`, and the constructs that cannot complete
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
/// member the body belongs to ([MemberBuilder#throwing]); anywhere else
/// appending it fails, at that statement. A `catch_` covers the subclasses
/// of its type, unchecked exceptions are not tracked, and a `catch_` of a
/// checked exception its `try` block cannot throw is rejected as javac
/// rejects it. See [Exceptions].
///
/// Only the innermost block being built accepts statements; using the
/// builder of an enclosing block inside a nested block or lambda fails fast.
///
/// @param <R> the result type of the enclosing method or lambda, or the type of the `switch` expression
///     this is a block of
/// @param <B> the type of this block, which nested blocks share
public abstract sealed class Block<R, B extends Block<R, B>> permits Body, VoidBody, InitializerBody, YieldBody {
    /// What to do instead of a statement form that cannot complete normally and so
    /// cannot be continued: the way the author is told depends on the block the
    /// form is in, for the `finally` block ([FinallyBody]) has no form that ends it.
    private enum Instead {
        IF_ELSE("build it with ifElse, which returns the Terminated of this block"),
        LOOP_FOREVER("a loop that never completes ends the block: build it with loopForever, which returns the"
                + " Terminated of this block, or break_ out of it"),
        TRY_TERMINATED("build it with tryTerminated, which returns the Terminated of this block"),
        SYNCHRONIZED("build it with synchronizedTerminated, which returns the Terminated of this block"),
        LABELED("break_ the label instead, or give the body a way to complete normally");

        private final String advice;

        Instead(String advice) {
            this.advice = advice;
        }

        String advice(Nesting nesting) {
            return switch (nesting) {
                case FINALLY_BLOCK ->
                    "a finally block completes normally, so a form that cannot is not expressible in it:"
                            + " give it a way to complete, or leave it out of the finally block";
                case PLAIN, LAMBDA_BODY, SWITCH_BLOCK -> advice;
            };
        }
    }

    /// What a block is to the block it is nested in.
    enum Nesting {
        /// A block of a statement — a branch, a loop body, a `try` or `catch` block — or the body of
        /// a member.
        PLAIN,
        /// The `finally` block of a `try`, which completes normally: it has no form that ends it
        /// ([FinallyBody]). To the scopes it is a block of a statement, as [#PLAIN] is.
        FINALLY_BLOCK,
        /// The body of a lambda: a [MutVar] and a [LoopCtl] do not cross into it.
        LAMBDA_BODY,
        /// The block of a case of a `switch` expression: a [LoopCtl] does not cross into it
        /// (JLS 15.28.1), a [MutVar] does.
        SWITCH_BLOCK;

        /// Whether a block nested so is a block of an expression — the body of a lambda or a block
        /// of a `switch` —, not of a statement of the block it is nested in.
        boolean ofExpression() {
            return switch (this) {
                case PLAIN, FINALLY_BLOCK -> false;
                case LAMBDA_BODY, SWITCH_BLOCK -> true;
            };
        }
    }

    private final Enclosing enclosing;
    private final Nesting nesting;
    private final String what;
    private final ExceptionScope exceptionScope;
    private final List<Instr> instrs = new ArrayList<>();
    private Ending ending = new Ending.Open();

    /// What a block is nested in.
    sealed interface Enclosing {
        /// Nothing: the body of a member, or a block of an expression built outside of any body.
        record Root() implements Enclosing {}

        /// A block.
        ///
        /// @param block the block
        record In(Block<?, ?> block) implements Enclosing {}
    }

    /// Whether a block has ended: a statement after the one that ended it would be unreachable.
    private sealed interface Ending {
        /// It has not.
        record Open() implements Ending {}

        /// It has, by `form`.
        ///
        /// @param form the form that ended it, for messages
        record Ended(String form) implements Ending {}
    }

    /// @param enclosing what this block is nested in
    Block(Enclosing enclosing, Nesting nesting, String what, ExceptionScope exceptionScope) {
        this.enclosing = enclosing;
        this.nesting = nesting;
        this.what = what;
        this.exceptionScope = exceptionScope;
    }

    abstract B self();

    /// Creates a block nested in this one, of the same kind.
    abstract B child(String what, Nesting nesting, ExceptionScope exceptionScope);

    /// Creates a block of a statement nested in this one, of the same kind.
    final B child(String what, ExceptionScope exceptionScope) {
        return child(what, Nesting.PLAIN, exceptionScope);
    }

    /// Creates the `finally` block of a `try` of this block, of the same kind, that hands the
    /// exceptions thrown in it to this one.
    final B finallyChild(String what) {
        return child(what, Nesting.FINALLY_BLOCK, ExceptionScope.PASSES);
    }

    /// Creates a block nested in this one, of the same kind, that hands
    /// the exceptions thrown in it to this one.
    final B child(String what) {
        return child(what, ExceptionScope.PASSES);
    }

    /// What becomes of a checked exception thrown in this block.
    final ExceptionScope exceptionScope() {
        return exceptionScope;
    }

    /// What this block is nested in.
    final Enclosing enclosing() {
        return enclosing;
    }

    /// This block and the blocks it is nested in, the innermost first.
    final Stream<Block<?, ?>> chain() {
        return Stream.concat(
                Stream.of(this),
                switch (enclosing) {
                    case Enclosing.Root _ -> Stream.<Block<?, ?>>empty();
                    case Enclosing.In(var block) -> block.chain();
                });
    }

    /// The blocks `use` is, and is nested in, the innermost first; none outside of any body.
    static Stream<Block<?, ?>> chain(Enclosing use) {
        return switch (use) {
            case Enclosing.Root _ -> Stream.<Block<?, ?>>empty();
            case Enclosing.In(var block) -> block.chain();
        };
    }

    /// What this block is to the block it is nested in.
    final Nesting nesting() {
        return nesting;
    }

    /// Where this block is, for diagnostics: `then-branch of if_ in body of method m`.
    final String path() {
        return switch (enclosing) {
            case Enclosing.Root _ -> what;
            case Enclosing.In(var block) -> what + " in " + block.path();
        };
    }

    /// The statements of this block so far, as a view.
    final List<Instr> instrs() {
        return Collections.unmodifiableList(instrs);
    }

    final void requireOpen() {
        Scopes.requireInnermost(this, "the " + path());
        if (ending instanceof Ending.Ended(var form)) {
            throw new IllegalStateException("the " + path() + " has already ended with " + form
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
    private B continueWith(Instr instr, String form, Instead instead) {
        requireOpen();
        ScopeCheck.check(instr, this);
        Exceptions.check(instr, this);
        if (!Reachability.canCompleteNormally(instr)) {
            throw new IllegalStateException(form + " in the " + path()
                    + " cannot complete normally, so a statement after it would be unreachable (JLS 14.22); "
                    + instead.advice(nesting));
        }
        instrs.add(instr);
        return self();
    }

    final Terminated<R> appendFinal(Instr instr, String form) {
        append(instr);
        return endHere(form, false);
    }

    final Terminated<R> endHere(String form) {
        return endHere(form, true);
    }

    private Terminated<R> endHere(String form, boolean completesNormally) {
        requireOpen();
        ending = new Ending.Ended(form);
        return new Terminated<>(this, completesNormally);
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
        build(child, spec);
    }

    /// Builds `block` from `spec`, with `block` the innermost scope; `spec`
    /// must hand back the token of `block`.
    static <R, B extends Block<R, B>> void build(B block, Function<? super B, Terminated<R>> spec) {
        requireIssuedBy(Scopes.within(block, () -> spec.apply(block)), block);
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
        Tokens.requireNoBoxing(init.type(), type, "let");
        Var<T> var = new Var<>(type, Var.Role.Fixed.LOCAL, this);
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
        Tokens.requireNoBoxing(init.type(), type, "letVar");
        MutVar<T> var = new MutVar<>(type, Var.Role.Mutable.LOCAL, this);
        append(new Instr.Let(var, init.node()));
        return rest.apply(var);
    }

    /// Appends the empty statement, `;`. It changes neither reachability nor scope.
    ///
    /// @return this block
    public final B empty() {
        return append(new Instr.Empty());
    }

    /// Appends `assert condition;`.
    ///
    /// @param condition the asserted condition
    /// @return this block
    public final B assert_(Expr<Prim.Bool> condition) {
        return append(new Instr.Assert(condition.node(), Optional.empty()));
    }

    /// Appends `assert condition : message;`.
    ///
    /// @param condition the asserted condition
    /// @param message the diagnostic message
    /// @return this block
    public final B assert_(Expr<Prim.Bool> condition, Expr<String> message) {
        return append(new Instr.Assert(condition.node(), Optional.of(message.node())));
    }

    /// Appends `synchronized (lock) { body }`.
    ///
    /// @param lock the monitor, a reference-typed expression
    /// @param body builds the body
    /// @return this block
    /// @throws IllegalArgumentException if the monitor is of a primitive type
    /// @throws IllegalStateException if the body cannot complete normally; build it with
    ///     [#synchronizedTerminated(Expr, Function)] then
    public final B synchronized_(Expr<?> lock, Consumer<? super B> body) {
        requireReference(lock, "synchronized_");
        B bodyBlock = open("body of synchronized_", body);
        return continueWith(
                new Instr.Synchronized(lock.node(), bodyBlock),
                "synchronized_ whose body cannot complete normally",
                Instead.SYNCHRONIZED);
    }

    /// Appends `synchronized (lock) { body }` whose body cannot complete normally, and so ends this block.
    ///
    /// @param lock the monitor, a reference-typed expression
    /// @param body builds the body, which must end
    /// @return the proof that this block ended
    /// @throws IllegalArgumentException if the monitor is of a primitive type
    public final Terminated<R> synchronizedTerminated(Expr<?> lock, Function<? super B, Terminated<R>> body) {
        requireReference(lock, "synchronizedTerminated");
        B bodyBlock = closed("body of synchronizedTerminated", body);
        return appendFinal(new Instr.Synchronized(lock.node(), bodyBlock), "synchronizedTerminated");
    }

    /// A monitor is a reference: a primitive value is no object to lock on.
    private static void requireReference(Expr<?> lock, String form) {
        if (lock.type() instanceof PrimitiveToken<?, ?, ?> primitive) {
            throw new IllegalArgumentException(form + " locks on an expression of the primitive type " + primitive
                    + ": a monitor is a reference (JLS 14.19); box it first, e.g. box(PrimitiveToken."
                    + primitive.typeRef().sourceName().toUpperCase(java.util.Locale.ROOT) + ", expr)");
        }
    }

    /// Appends `if (condition) { then }`. It never ends this block.
    ///
    /// @param condition the condition
    /// @param then builds the `then` block
    /// @return this block
    public final B if_(Expr<Prim.Bool> condition, Consumer<? super B> then) {
        requireOpen();
        return append(new Instr.If(condition.node(), open("then-branch of if_", then), new Instr.Otherwise.Nothing()));
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
                new Instr.If(condition.node(), thenBlock, new Instr.Otherwise.Else(elseBlock)),
                "if_ whose branches both end",
                Instead.IF_ELSE);
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
        return appendFinal(new Instr.If(condition.node(), thenBlock, new Instr.Otherwise.Else(elseBlock)), "ifElse");
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
        PatternBinding<U> binding = Patterns.binding(type);
        return ifInstanceOf(operand, Patterns.type(binding), b -> then.accept(b, binding.var()));
    }

    /// Appends `if (operand instanceof pattern) { then }`; every binding the
    /// pattern makes exists only in `then` (flow typing through HOAS).
    ///
    /// A pattern is a [TypedPattern.Type] — the form of
    /// [#ifInstanceOf(Expr, RefToken, BiConsumer)] — or a record
    /// deconstruction made by [Patterns], whose component bindings are in
    /// scope in `then` with the types of the components. The type of the
    /// pattern must be a subtype of the operand's, and the test checked, as
    /// for the type pattern.
    ///
    /// @param operand the tested expression
    /// @param pattern the pattern
    /// @param then builds the `then` block
    /// @param <U> the type the pattern matches
    /// @return this block
    /// @throws IllegalArgumentException if the test would be unchecked
    public final <U> B ifInstanceOf(Expr<? super U> operand, TypedPattern<U> pattern, Consumer<? super B> then) {
        requireOpen();
        Patterns.requireReference(pattern.matched(), "an instanceof pattern");
        Tokens.requireCheckedCast(operand.type(), pattern.matched(), "an instanceof pattern");
        B thenBlock = child("then-branch of ifInstanceOf");
        Patterns.bind(pattern, thenBlock);
        fill(thenBlock, then);
        return append(new Instr.IfInstance(operand.node(), pattern, thenBlock, new Instr.Otherwise.Nothing()));
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
        PatternBinding<U> binding = Patterns.binding(type);
        return ifInstanceOfElse(operand, Patterns.type(binding), b -> then.apply(b, binding.var()), otherwise);
    }

    /// Appends `if (operand instanceof pattern) { then } else { otherwise }`
    /// whose branches both end, and so ends this block.
    ///
    /// @param operand the tested expression
    /// @param pattern the pattern
    /// @param then builds the `then` block; it must end
    /// @param otherwise builds the `else` block; it must end
    /// @param <U> the type the pattern matches
    /// @return the proof that this block ended
    /// @throws IllegalArgumentException if the test would be unchecked
    public final <U> Terminated<R> ifInstanceOfElse(
            Expr<? super U> operand,
            TypedPattern<U> pattern,
            Function<? super B, Terminated<R>> then,
            Function<? super B, Terminated<R>> otherwise) {
        requireOpen();
        Patterns.requireReference(pattern.matched(), "an instanceof pattern");
        Tokens.requireCheckedCast(operand.type(), pattern.matched(), "an instanceof pattern");
        B thenBlock = child("then-branch of ifInstanceOfElse");
        Patterns.bind(pattern, thenBlock);
        fillEnding(thenBlock, then);
        B elseBlock = closed("else-branch of ifInstanceOfElse", otherwise);
        return appendFinal(
                new Instr.IfInstance(operand.node(), pattern, thenBlock, new Instr.Otherwise.Else(elseBlock)),
                "ifInstanceOfElse");
    }

    /// Appends `while (condition) { body }`.
    ///
    /// A loop that cannot complete normally - a constant `true` condition
    /// and no `break_` - is rejected: use [#loopForever(Consumer)].
    ///
    /// @param condition the condition
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @return this block
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public final B while_(Expr<Prim.Bool> condition, BiConsumer<? super B, LoopCtl> body) {
        return while_(Optional.empty(), condition, body);
    }

    /// Appends `label: while (condition) { body }`.
    ///
    /// @param label the label of the loop
    /// @param condition the condition
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @return this block
    /// @throws IllegalStateException if the condition is constant `false`, or the loop never completes
    public final B while_(Label label, Expr<Prim.Bool> condition, BiConsumer<? super B, LoopCtl> body) {
        return while_(Optional.of(label), condition, body);
    }

    private B while_(Optional<Label> label, Expr<Prim.Bool> condition, BiConsumer<? super B, LoopCtl> body) {
        requireOpen();
        requireReachableBody(condition, "while_");
        B bodyBlock = child("body of while_");
        label.ifPresent(l -> l.attach(bodyBlock, true));
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, ctl));
        return continueWith(
                new Instr.While(ctl, label, condition.node(), bodyBlock),
                "while_ with a constant true condition and no break_",
                Instead.LOOP_FOREVER);
    }

    /// Appends `while (true) { body }`, which never completes normally and so
    /// ends this block (JLS 14.22): the body gets no `break` capability for
    /// this loop, so the only ways out are `return_`, `throw_`, or a
    /// `break_`/`continue_` of an enclosing loop.
    ///
    /// @param body builds the body
    /// @return the proof that this block ended
    public final Terminated<R> loopForever(Consumer<? super B> body) {
        return loopForever(Optional.empty(), body);
    }

    /// Appends `label: while (true) { body }`, which never completes normally
    /// and so ends this block (JLS 14.22). The label lets a `break_` or a
    /// `continue_` of a nested loop reach this loop by name.
    ///
    /// @param label the label of the loop
    /// @param body builds the body
    /// @return the proof that this block ended
    public final Terminated<R> loopForever(Label label, Consumer<? super B> body) {
        return loopForever(Optional.of(label), body);
    }

    private Terminated<R> loopForever(Optional<Label> label, Consumer<? super B> body) {
        requireOpen();
        B bodyBlock = child("body of loopForever");
        label.ifPresent(l -> l.attach(bodyBlock, true));
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, body);
        return appendFinal(new Instr.While(ctl, label, Expressions.literal(true).node(), bodyBlock), "loopForever");
    }

    /// Appends `do { body } while (condition);`.
    ///
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @param condition the condition
    /// @return this block
    /// @throws IllegalStateException if the loop never completes
    public final B doWhile(BiConsumer<? super B, LoopCtl> body, Expr<Prim.Bool> condition) {
        return doWhile(Optional.empty(), body, condition);
    }

    /// Appends `label: do { body } while (condition);`.
    ///
    /// @param label the label of the loop
    /// @param body builds the body, given the loop's `break`/`continue` capability
    /// @param condition the condition
    /// @return this block
    /// @throws IllegalStateException if the loop never completes
    public final B doWhile(Label label, BiConsumer<? super B, LoopCtl> body, Expr<Prim.Bool> condition) {
        return doWhile(Optional.of(label), body, condition);
    }

    private B doWhile(Optional<Label> label, BiConsumer<? super B, LoopCtl> body, Expr<Prim.Bool> condition) {
        requireOpen();
        B bodyBlock = child("body of doWhile");
        label.ifPresent(l -> l.attach(bodyBlock, true));
        LoopCtl ctl = new LoopCtl(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, ctl));
        return continueWith(
                new Instr.DoWhile(ctl, label, bodyBlock, condition.node()),
                "doWhile whose body ends without continue_, or whose condition is constant true, and no break_",
                Instead.LOOP_FOREVER);
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
        return for_(Optional.empty(), type, init, condition, update, body);
    }

    /// Appends `label: for (T v = init; condition; update) { body }`.
    ///
    /// @param label the label of the loop
    /// @param type the loop variable type
    /// @param init the initializer
    /// @param condition the condition, given the variable
    /// @param update the update, given the variable
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    public final <T> B for_(
            Label label,
            TypeToken<T> type,
            Expr<? extends T> init,
            Function<? super MutVar<T>, Expr<Prim.Bool>> condition,
            Function<? super MutVar<T>, ? extends Effect> update,
            LoopBody<? super B, ? super MutVar<T>> body) {
        return for_(Optional.of(label), type, init, condition, update, body);
    }

    private <T> B for_(
            Optional<Label> label,
            TypeToken<T> type,
            Expr<? extends T> init,
            Function<? super MutVar<T>, Expr<Prim.Bool>> condition,
            Function<? super MutVar<T>, ? extends Effect> update,
            LoopBody<? super B, ? super MutVar<T>> body) {
        requireOpen();
        B bodyBlock = child("body of for_");
        label.ifPresent(l -> l.attach(bodyBlock, true));
        LoopCtl ctl = new LoopCtl(bodyBlock);
        Tokens.requireNoBoxing(init.type(), type, "for_");
        MutVar<T> var = new MutVar<>(type, Var.Role.Mutable.FOR_VARIABLE, bodyBlock);
        Expr<Prim.Bool> conditionExpr = condition.apply(var);
        requireReachableBody(conditionExpr, "for_");
        Node updateNode = Assignment.nodeOf(update.apply(var));
        fill(bodyBlock, b -> body.accept(b, var, ctl));
        return continueWith(
                new Instr.For(ctl, label, var, init.node(), conditionExpr.node(), updateNode, bodyBlock),
                "for_ with a constant true condition and no break_",
                Instead.LOOP_FOREVER);
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
        return forEachOf(Optional.empty(), element, new Instr.Iterated.OfIterable(iterable.node()), body);
    }

    /// Appends `label: for (T v : iterable) { body }`.
    ///
    /// @param label the label of the loop
    /// @param element the loop variable type
    /// @param iterable the iterated expression
    /// @param body builds the body, given the variable and the loop capability
    /// @param <T> the loop variable type
    /// @return this block
    public final <T> B forEach(
            Label label,
            TypeToken<T> element,
            Expr<? extends Iterable<? extends T>> iterable,
            LoopBody<? super B, ? super Var<T>> body) {
        return forEachOf(Optional.of(label), element, new Instr.Iterated.OfIterable(iterable.node()), body);
    }

    /// Appends `for (T v : array) { body }`, the loop over the elements of an array (JLS 14.14.2); the
    /// variable is of the component of the array type, `for (int v : ints)`.
    ///
    /// @param type the array type
    /// @param array the iterated array
    /// @param body builds the body, given the variable and the loop capability
    /// @param <A> the array type
    /// @param <T> the component type, the loop variable type
    /// @return this block
    public final <A, T> B forEach(
            ArrayToken<A, T> type, Expr<? extends A> array, LoopBody<? super B, ? super Var<T>> body) {
        return forEachOf(Optional.empty(), type.component(), new Instr.Iterated.OfArray(array.node()), body);
    }

    /// Appends `label: for (T v : array) { body }`.
    ///
    /// @param label the label of the loop
    /// @param type the array type
    /// @param array the iterated array
    /// @param body builds the body, given the variable and the loop capability
    /// @param <A> the array type
    /// @param <T> the component type, the loop variable type
    /// @return this block
    public final <A, T> B forEach(
            Label label, ArrayToken<A, T> type, Expr<? extends A> array, LoopBody<? super B, ? super Var<T>> body) {
        return forEachOf(Optional.of(label), type.component(), new Instr.Iterated.OfArray(array.node()), body);
    }

    private <T> B forEachOf(
            Optional<Label> label,
            TypeToken<T> element,
            Instr.Iterated iterated,
            LoopBody<? super B, ? super Var<T>> body) {
        requireOpen();
        B bodyBlock = child("body of forEach");
        label.ifPresent(l -> l.attach(bodyBlock, true));
        LoopCtl ctl = new LoopCtl(bodyBlock);
        Var<T> var = new Var<>(element, Var.Role.Fixed.FOR_EACH_VARIABLE, bodyBlock);
        fill(bodyBlock, b -> body.accept(b, var, ctl));
        return append(new Instr.ForEach(ctl, label, var, iterated, bodyBlock));
    }

    /// Creates a label for a statement of this block, which [#labeled(Label, Consumer)] or a loop
    /// attaches to the statement it names.
    ///
    /// @return the label
    public final Label label() {
        return new Label();
    }

    /// Appends `label: { body }`.
    ///
    /// @param label the label of the block
    /// @param body builds the block
    /// @return this block
    /// @throws IllegalStateException if the label names another statement already, or the body cannot
    ///     complete normally without breaking the label
    public final B labeled(Label label, Consumer<? super B> body) {
        requireOpen();
        B bodyBlock = child("body of labeled");
        label.attach(bodyBlock, false);
        fill(bodyBlock, body);
        return continueWith(
                new Instr.Labeled(label, bodyBlock), "labeled whose body cannot complete normally", Instead.LABELED);
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

    /// Appends `break label;` out of the statement the label names, and ends this block.
    ///
    /// @param label the label of an enclosing statement
    /// @return the proof that this block ended
    /// @throws IllegalStateException if the label names no statement yet, or it does not enclose this block
    public final Terminated<R> break_(Label label) {
        return appendFinal(new Instr.BreakLabel(label), "break_");
    }

    /// Appends `continue label;` of the loop the label names, and ends this block.
    ///
    /// @param label the label of an enclosing loop
    /// @return the proof that this block ended
    /// @throws IllegalStateException if the label names no statement yet, it does not enclose this block,
    ///     or it names a block rather than a loop
    public final Terminated<R> continue_(Label label) {
        return appendFinal(new Instr.ContinueLabel(label), "continue_");
    }

    /// Appends `throw exception;` and ends this block. A checked exception —
    /// by the static type of `exception` — must be caught by a `catch_` of
    /// an enclosing `try_` or declared by the member
    /// ([MemberBuilder#throwing]). A caught exception that is rethrown
    /// is thrown at the type of its `catch_`: there is no precise rethrow
    /// (JLS 11.2.2).
    ///
    /// @param exception the thrown expression
    /// @return the proof that this block ended
    /// @throws IllegalStateException if the exception is checked and neither caught nor declared
    /// @throws IllegalArgumentException if the type of `exception` is neither a class nor a type variable,
    ///     which only an expression of `Unsafe` can be
    public final Terminated<R> throw_(Expr<? extends Throwable> exception) {
        // javac goes by the expression itself: one seen through a witness is of the class it is of.
        TypeToken<?> thrown = Lowering.seen(new Node.Operand(exception.node(), exception.type()))
                .type();
        return appendFinal(new Instr.Throw(exception.node(), ExceptionType.of(thrown)), "throw_");
    }

    /// Ends the block with `throw exception;` of a class that is an exception by a witness alone:
    /// the class being declared, `b.throw_(cb.asSuper(), new_(ctor))`. The statement is checked by
    /// that class, as javac checks it.
    ///
    /// @param witness the witness that the class of `exception` is a `Throwable`
    /// @param exception the thrown expression
    /// @param <E> the class of the exception
    /// @return the proof that this block ended
    /// @throws IllegalArgumentException if the witness is of another class than `exception`
    /// @throws IllegalStateException as [#throw_(Expr)]
    public final <E> Terminated<R> throw_(Upcast<E, ? extends Throwable> witness, Expr<E> exception) {
        if (!(exception.type() instanceof ClassToken<E> type)) {
            throw new IllegalArgumentException("the witness that " + witness + " is no witness that " + exception.type()
                    + ", which is no class, is an exception");
        }
        return appendFinal(
                new Instr.Throw(exception.node(), new ExceptionType.OfClass(Upcast.throwable(witness, type))),
                "throw_");
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
                clauses.statement(bodyBlock, List.of()),
                "try_ whose try block and every catch_ end",
                Instead.TRY_TERMINATED);
    }

    /// Appends `try { body } catch ... finally ...` whose `try` block and
    /// every `catch` block end, and so ends this block. The `finally` block,
    /// if any, completes normally ([FinallyBody]).
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
        return appendFinal(clauses.statement(bodyBlock, List.of()), "tryTerminated");
    }

    /// Appends `try (resources) { body } catch ... finally ...` as a statement. It must complete
    /// normally; if the `try` block and every `catch` end, use
    /// [#tryWithResourcesTerminated(Consumer, BiFunction)], which ends this block.
    ///
    /// The handlers come first, as with [#try_(Consumer, Consumer)], and the resources are declared by
    /// `body` before it runs, in its order: a resource is in scope in the body, and in the initializers
    /// of the resources after it.
    ///
    /// @param handlers adds `catch` clauses and the `finally` block
    /// @param body declares the resources, given the resource builder, and builds the try block
    /// @return this block
    /// @throws IllegalStateException if `handlers` adds neither a `catch` nor a `finally`, if the
    ///     statement cannot complete normally, if a `catch_` can catch nothing, or if `body` declares no
    ///     resource
    public final B tryWithResources(
            Consumer<? super Handlers<B>> handlers, BiConsumer<? super B, ? super Resources> body) {
        requireOpen();
        Handlers<B> collected = new Handlers<>(self());
        handlers.accept(collected);
        TryClauses clauses = collected.close();
        B bodyBlock = child("try block of tryWithResources", clauses.scope());
        Resources resources = new Resources(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, resources));
        List<Instr.Resource> made = resources.close();
        return continueWith(
                clauses.statement(bodyBlock, made),
                "tryWithResources whose try block and every catch_ end",
                Instead.TRY_TERMINATED);
    }

    /// Appends `try (resources) { body } catch ... finally ...` whose `try` block and every `catch`
    /// block end, and so ends this block.
    ///
    /// @param handlers adds `catch` clauses, each of which must end, and the `finally` block
    /// @param body declares the resources, given the resource builder, and builds the try block, which
    ///     must end
    /// @return the proof that this block ended
    /// @throws IllegalStateException if `handlers` adds neither a `catch` nor a `finally`, if a
    ///     `catch_` can catch nothing, or if `body` declares no resource
    public final Terminated<R> tryWithResourcesTerminated(
            Consumer<? super TerminatedHandlers<R, B>> handlers,
            BiFunction<? super B, ? super Resources, Terminated<R>> body) {
        requireOpen();
        TerminatedHandlers<R, B> collected = new TerminatedHandlers<>(self());
        handlers.accept(collected);
        TryClauses clauses = collected.close();
        B bodyBlock = child("try block of tryWithResourcesTerminated", clauses.scope());
        Resources resources = new Resources(bodyBlock);
        Block.build(bodyBlock, b -> body.apply(b, resources));
        List<Instr.Resource> made = resources.close();
        return appendFinal(clauses.statement(bodyBlock, made), "tryWithResourcesTerminated");
    }

    /// Appends `try (resources) { body }` with neither a `catch` clause nor a
    /// `finally` block (JLS 14.20.3), as a statement. It must complete
    /// normally; if `body` ends, use
    /// [#tryWithResourcesTerminated(BiFunction)].
    ///
    /// The resources close on every way out of the body, and a checked
    /// exception a `close()` throws must be caught by an enclosing `try_` or
    /// declared by the member the body belongs to, as javac requires it.
    ///
    /// @param body declares the resources, given the resource builder, and builds the try block
    /// @return this block
    /// @throws IllegalStateException if the statement cannot complete normally, or if `body`
    ///     declares no resource
    public final B tryWithResources(BiConsumer<? super B, ? super Resources> body) {
        requireOpen();
        TryClauses clauses = new TryClauses(List.of(), new Instr.Finally.None());
        B bodyBlock = child("try block of tryWithResources", clauses.scope());
        Resources resources = new Resources(bodyBlock);
        fill(bodyBlock, b -> body.accept(b, resources));
        List<Instr.Resource> made = resources.close();
        return continueWith(
                clauses.statement(bodyBlock, made), "tryWithResources whose try block ends", Instead.TRY_TERMINATED);
    }

    /// Appends `try (resources) { body }` with neither a `catch` clause nor a
    /// `finally` block (JLS 14.20.3), whose try block ends and so ends this
    /// block.
    ///
    /// @param body declares the resources, given the resource builder, and builds the try block, which
    ///     must end
    /// @return the proof that this block ended
    /// @throws IllegalStateException if `body` declares no resource
    public final Terminated<R> tryWithResourcesTerminated(
            BiFunction<? super B, ? super Resources, Terminated<R>> body) {
        requireOpen();
        TryClauses clauses = new TryClauses(List.of(), new Instr.Finally.None());
        B bodyBlock = child("try block of tryWithResourcesTerminated", clauses.scope());
        Resources resources = new Resources(bodyBlock);
        Block.build(bodyBlock, b -> body.apply(b, resources));
        List<Instr.Resource> made = resources.close();
        return appendFinal(clauses.statement(bodyBlock, made), "tryWithResourcesTerminated");
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
