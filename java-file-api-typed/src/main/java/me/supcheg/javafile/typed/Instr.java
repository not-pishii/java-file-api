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

    /// A local variable declaration with its initializer.
    record Let(Var<?> var, Node init) implements Instr {}

    /// An expression statement: a call, an instance creation, or an assignment.
    record Exec(Node effect) implements Instr {}

    /// `if (c) { ... }` with an optional `else { ... }`.
    record If(Node condition, Block<?, ?> then, Optional<Block<?, ?>> otherwise) implements Instr {}

    /// `if (x instanceof T v) { ... }` with an optional `else { ... }`.
    record IfInstance(Node operand, RefToken<?> type, Var<?> binding, Block<?, ?> then, Optional<Block<?, ?>> otherwise)
            implements Instr {}

    /// `while (c) { ... }`.
    record While(LoopCtl ctl, Node condition, Block<?, ?> body) implements Instr {}

    /// `do { ... } while (c);`.
    record DoWhile(LoopCtl ctl, Block<?, ?> body, Node condition) implements Instr {}

    /// `for (T v = init; c; update) { ... }`.
    record For(LoopCtl ctl, MutVar<?> var, Node init, Node condition, Node update, Block<?, ?> body) implements Instr {}

    /// `for (T v : iterable) { ... }`.
    record ForEach(LoopCtl ctl, Var<?> var, Node iterable, Block<?, ?> body) implements Instr {}

    /// `return;` or `return value;`.
    record Return(Optional<Node> value) implements Instr {}

    /// `yield value;` of the block of a case of a `switch` expression.
    record Yield(Node value) implements Instr {}

    /// `throw value;`, with the static type of `value`.
    record Throw(Node value, ExceptionType type) implements Instr {}

    /// `break;` out of a loop.
    record Break(LoopCtl ctl) implements Instr {}

    /// `continue;` of a loop.
    record Continue(LoopCtl ctl) implements Instr {}

    /// `try { ... } catch (...) { ... } finally { ... }`.
    record Try(Block<?, ?> body, List<Catch> catches, Optional<Block<?, ?>> finallyBlock) implements Instr {}

    /// A `catch` clause of a [Try].
    record Catch(ClassToken<? extends Throwable> type, Var<?> var, Block<?, ?> body) {}

    /// An untyped core statement from `Unsafe`.
    record Raw(Stmt stmt) implements Instr {}

    /// The blocks that are part of the statement `instr`: its branches,
    /// its body. Not the blocks of the expressions it evaluates.
    ///
    /// @param instr the statement
    /// @return its blocks, in the order of the code
    static Stream<Block<?, ?>> blocks(Instr instr) {
        return switch (instr) {
            case If(var _, var then, var otherwise) -> Stream.concat(Stream.of(then), otherwise.stream());
            case IfInstance(var _, var _, var _, var then, var otherwise) ->
                Stream.concat(Stream.of(then), otherwise.stream());
            case While(var _, var _, var body) -> Stream.of(body);
            case DoWhile(var _, var body, var _) -> Stream.of(body);
            case For(var _, var _, var _, var _, var _, var body) -> Stream.of(body);
            case ForEach(var _, var _, var _, var body) -> Stream.of(body);
            case Try(var body, var catches, var finallyBlock) ->
                Stream.of(Stream.of(body), catches.stream().map(Catch::body), finallyBlock.stream())
                        .flatMap(Function.identity());
            case Let _, Exec _, Return _, Yield _, Throw _, Break _, Continue _, Raw _ -> Stream.empty();
        };
    }
}
