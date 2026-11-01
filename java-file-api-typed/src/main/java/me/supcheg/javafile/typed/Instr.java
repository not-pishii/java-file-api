package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.RefToken;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/// The untyped statement IR of the typed layer.
sealed interface Instr {

    /// A statement without a block that completes normally: a declaration, an expression statement.
    sealed interface Plain extends Instr {}

    /// A statement that completes abruptly, always (JLS 14.22).
    sealed interface Jump extends Instr {}

    /// A local variable declaration with its initializer.
    record Let(Var<?> var, Node init) implements Plain {}

    /// `assert condition;` or `assert condition : message;`.
    ///
    /// @param condition the asserted condition
    /// @param message the diagnostic message, or empty to omit it
    record Assert(Node condition, Optional<Node> message) implements Plain {}

    /// An expression statement: a call, an instance creation, or an assignment.
    record Exec(Node effect) implements Plain {}

    /// `synchronized (lock) { ... }`.
    ///
    /// @param lock the monitor expression
    /// @param body the body, which the statement completes normally exactly when it does
    record Synchronized(Node lock, Block<?, ?> body) implements Instr {}

    /// `label: { ... }`.
    ///
    /// @param label the label
    /// @param body the labeled block
    record Labeled(Label label, Block<?, ?> body) implements Instr {}

    /// `if (c) { ... }`, with what it does where `c` does not hold.
    record If(Node condition, Block<?, ?> then, Otherwise otherwise) implements Instr {}

    /// `if (x instanceof T v) { ... }`, with what it does where `x` is no `T`.
    record IfInstance(Node operand, RefToken<?> type, Var<?> binding, Block<?, ?> then, Otherwise otherwise)
            implements Instr {}

    /// What an `if` does where its condition does not hold: nothing, or its `else` block.
    sealed interface Otherwise {
        /// No `else`.
        record Nothing() implements Otherwise {}

        /// `else { ... }`.
        ///
        /// @param block the block
        record Else(Block<?, ?> block) implements Otherwise {}

        /// The `else` block, if there is one.
        default Stream<Block<?, ?>> blocks() {
            return switch (this) {
                case Nothing _ -> Stream.empty();
                case Else(var block) -> Stream.of(block);
            };
        }
    }

    /// `while (c) { ... }`, and the label of the loop, if it has one.
    record While(LoopCtl ctl, Optional<Label> label, Node condition, Block<?, ?> body) implements Instr {}

    /// `do { ... } while (c);`, and the label of the loop, if it has one.
    record DoWhile(LoopCtl ctl, Optional<Label> label, Block<?, ?> body, Node condition) implements Instr {}

    /// `for (T v = init; c; update) { ... }`, and the label of the loop, if it has one.
    record For(
            LoopCtl ctl, Optional<Label> label, MutVar<?> var, Node init, Node condition, Node update, Block<?, ?> body)
            implements Instr {}

    /// `for (T v : iterated) { ... }`, and the label of the loop, if it has one.
    record ForEach(LoopCtl ctl, Optional<Label> label, Var<?> var, Iterated iterated, Block<?, ?> body)
            implements Instr {}

    /// What a `for`-each loop iterates (JLS 14.14.2): an `Iterable`, or an array.
    sealed interface Iterated {
        /// The expression iterated.
        Node node();

        /// An `Iterable`: javac takes the type of the expression itself, so one seen through a witness is of
        /// the supertype the witness names.
        record OfIterable(Node node) implements Iterated {}

        /// An array, whose elements are those of the loop variable.
        record OfArray(Node node) implements Iterated {}
    }

    /// `return value;`, with the static type of `value`.
    record Return(Node.Operand value) implements Jump {}

    /// `return;` of a `void` body.
    record ReturnNothing() implements Jump {}

    /// `yield value;` of the block of a case of a `switch` expression.
    record Yield(Node value) implements Jump {}

    /// `throw value;`, with the static type of `value`.
    record Throw(Node value, ExceptionType type) implements Jump {}

    /// `break;` out of a loop.
    record Break(LoopCtl ctl) implements Jump {}

    /// `continue;` of a loop.
    record Continue(LoopCtl ctl) implements Jump {}

    /// `break label;` out of the statement the label names.
    record BreakLabel(Label label) implements Jump {}

    /// `continue label;` of the loop the label names.
    record ContinueLabel(Label label) implements Jump {}

    /// `try (resources) { ... } catch (...) { ... } finally { ... }`: a `catch` clause at least, or a
    /// `finally` block.
    ///
    /// @param body the try block
    /// @param resources the resources, in declaration order
    /// @param catches the `catch` clauses
    /// @param finallyBlock the `finally` block, or that there is none
    record Try(Block<?, ?> body, List<Resource> resources, List<Catch> catches, Finally finallyBlock) implements Instr {
        public Try {
            resources = List.copyOf(resources);
        }
    }

    /// A resource of a [Try]: a new variable with its initializer, or an existing effectively-final one.
    ///
    /// @param var the resource variable
    /// @param init the initializer, or empty for an existing variable
    record Resource(Var<?> var, Optional<Node> init) {}

    /// The `finally` block of a `try`, or that it has none.
    sealed interface Finally {
        /// No `finally` block.
        record None() implements Finally {}

        /// `finally { ... }`.
        ///
        /// @param block the block
        record Of(Block<?, ?> block) implements Finally {}

        /// The `finally` block, if there is one.
        default Stream<Block<?, ?>> blocks() {
            return switch (this) {
                case None _ -> Stream.empty();
                case Of(var block) -> Stream.of(block);
            };
        }
    }

    /// A `catch` clause of a [Try].
    ///
    /// @param types the exception classes it names, in the order they are written: one for a single
    ///     `catch`, several for a multi-catch
    /// @param type the type of the binding: for several alternatives their least upper bound, which is
    ///     not written in the source (JLS 14.20)
    /// @param var the binding
    /// @param body the block of the clause
    record Catch(
            List<ClassToken<? extends Throwable>> types,
            ClassToken<? extends Throwable> type,
            Var<?> var,
            Block<?, ?> body) {
        public Catch {
            types = List.copyOf(types);
        }
    }

    /// An untyped core statement from `Unsafe`.
    record Raw(Stmt stmt) implements Plain {}

    /// The blocks that are part of the statement `instr`: its branches,
    /// its body. Not the blocks of the expressions it evaluates.
    ///
    /// @param instr the statement
    /// @return its blocks, in the order of the code
    static Stream<Block<?, ?>> blocks(Instr instr) {
        return switch (instr) {
            case Synchronized(var _, var body) -> Stream.of(body);
            case Labeled(var _, var body) -> Stream.of(body);
            case If(var _, var then, var otherwise) -> Stream.concat(Stream.of(then), otherwise.blocks());
            case IfInstance(var _, var _, var _, var then, var otherwise) ->
                Stream.concat(Stream.of(then), otherwise.blocks());
            case While(var _, var _, var _, var body) -> Stream.of(body);
            case DoWhile(var _, var _, var body, var _) -> Stream.of(body);
            case For(var _, var _, var _, var _, var _, var _, var body) -> Stream.of(body);
            case ForEach(var _, var _, var _, var _, var body) -> Stream.of(body);
            case Try(var body, var _, var catches, var finallyBlock) ->
                Stream.of(Stream.of(body), catches.stream().map(Catch::body), finallyBlock.blocks())
                        .flatMap(Function.identity());
            case Plain _ -> Stream.empty();
            case Jump _ -> Stream.empty();
        };
    }
}
