package me.supcheg.javafile.typed;

import java.util.List;
import java.util.Optional;

/// "Can complete normally" of JLS 14.22 for the statements the typed layer
/// builds, with loop conditions folded as constant expressions (JLS 15.29,
/// [Constants]).
///
/// Blocks never hold an unreachable statement — appending after a statement
/// that cannot complete normally is rejected, and so is a loop body under a
/// constant `false` condition — so every `break`/`continue` found in a
/// statement is reachable, as the rules require. `catch` blocks are taken as
/// reachable; which exceptions a `try` block can throw is §9.1's concern.
final class Reachability {
    private Reachability() {}

    /// Whether `block` can complete normally: its last statement can, since
    /// every earlier one can by construction.
    static boolean canCompleteNormally(Block<?, ?> block) {
        List<Instr> instrs = block.instrs();
        return instrs.isEmpty() || canCompleteNormally(instrs.getLast());
    }

    static boolean canCompleteNormally(Instr instr) {
        return switch (instr) {
            case Instr.Let ignored -> true;
            case Instr.Exec ignored -> true;
            case Instr.Raw ignored -> true;
            case Instr.ForEach ignored -> true;
            case Instr.If(var ignored, var then, var otherwise) -> ifCompletes(then, otherwise);
            case Instr.IfInstance(var ignored, var ignoredType, var ignoredVar, var then, var otherwise) ->
                ifCompletes(then, otherwise);
            case Instr.While(var ctl, var condition, var body) ->
                !Constants.isConstant(condition, true) || exits(ctl, body, true);
            case Instr.For(var ctl, var ignoredVar, var ignoredInit, var condition, var ignoredUpdate, var body) ->
                !Constants.isConstant(condition, true) || exits(ctl, body, true);
            case Instr.DoWhile(var ctl, var body, var condition) ->
                ((canCompleteNormally(body) || exits(ctl, body, false)) && !Constants.isConstant(condition, true))
                        || exits(ctl, body, true);
            case Instr.Try(var body, var catches, var ignoredFinally) ->
                // A finally block completes normally by its type (FinallyBody).
                canCompleteNormally(body) || catches.stream().anyMatch(c -> canCompleteNormally(c.body()));
            case Instr.Return ignored -> false;
            case Instr.Yield ignored -> false;
            case Instr.Throw ignored -> false;
            case Instr.Break ignored -> false;
            case Instr.Continue ignored -> false;
        };
    }

    /// Whether `block` holds a `yield`, at any depth: one of the `switch`
    /// expression `block` is a block of, as a block of another `switch` is
    /// part of an expression, not of a statement. A statement a block holds
    /// is reachable, so the `yield` is.
    static boolean yields(Block<?, ?> block) {
        return block.instrs().stream()
                .anyMatch(instr ->
                        instr instanceof Instr.Yield || Instr.blocks(instr).anyMatch(Reachability::yields));
    }

    private static boolean ifCompletes(Block<?, ?> then, Optional<Block<?, ?>> otherwise) {
        // An if without else can always complete normally, even `if (false)` (JLS 14.22).
        return otherwise.isEmpty() || canCompleteNormally(then) || canCompleteNormally(otherwise.get());
    }

    /// Whether `block` holds a `break` (or `continue`) of the loop `ctl`
    /// that exits (continues) it.
    private static boolean exits(LoopCtl ctl, Block<?, ?> block, boolean isBreak) {
        for (Instr instr : block.instrs()) {
            if (exits(ctl, instr, isBreak)) {
                return true;
            }
        }
        return false;
    }

    private static boolean exits(LoopCtl ctl, Instr instr, boolean isBreak) {
        return switch (instr) {
            case Instr.Break(var target) -> isBreak && target == ctl;
            case Instr.Continue(var target) -> !isBreak && target == ctl;
            case Instr.If(var ignored, var then, var otherwise) ->
                exits(ctl, then, isBreak)
                        || otherwise.map(o -> exits(ctl, o, isBreak)).orElse(false);
            case Instr.IfInstance(var ignored, var ignoredType, var ignoredVar, var then, var otherwise) ->
                exits(ctl, then, isBreak)
                        || otherwise.map(o -> exits(ctl, o, isBreak)).orElse(false);
            case Instr.While(var ignored, var ignoredCondition, var body) -> exits(ctl, body, isBreak);
            case Instr.DoWhile(var ignored, var body, var ignoredCondition) -> exits(ctl, body, isBreak);
            case Instr.For(
                    var ignored,
                    var ignoredVar,
                    var ignoredInit,
                    var ignoredCond,
                    var ignoredUpdate,
                    var body) -> exits(ctl, body, isBreak);
            case Instr.ForEach(var ignored, var ignoredVar, var ignoredIterable, var body) -> exits(ctl, body, isBreak);
            case Instr.Try(var body, var catches, var finallyBlock) ->
                // The finally block completes normally, so it swallows no jump of the others.
                finallyBlock.map(f -> exits(ctl, f, isBreak)).orElse(false)
                        || exits(ctl, body, isBreak)
                        || catches.stream().anyMatch(c -> exits(ctl, c.body(), isBreak));
            case Instr.Let ignored -> false;
            case Instr.Exec ignored -> false;
            case Instr.Raw ignored -> false;
            case Instr.Return ignored -> false;
            case Instr.Yield ignored -> false;
            case Instr.Throw ignored -> false;
        };
    }
}
