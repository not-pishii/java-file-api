package me.supcheg.javafile.typed;

import java.util.List;

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
            case Instr.Plain _ -> true;
            case Instr.ForEach _ -> true;
            case Instr.Synchronized(var _, var body) -> canCompleteNormally(body);
            case Instr.Labeled(var label, var body) ->
                canCompleteNormally(body) || holds(body, new Instr.BreakLabel(label));
            case Instr.If(var _, var then, var otherwise) -> ifCompletes(then, otherwise);
            case Instr.IfInstance(var _, var _, var _, var then, var otherwise) -> ifCompletes(then, otherwise);
            case Instr.While(var ctl, var label, var condition, var body) ->
                !Constants.isConstant(condition, true) || breaks(ctl, label, body);
            case Instr.For(var ctl, var label, var _, var _, var condition, var _, var body) ->
                !Constants.isConstant(condition, true) || breaks(ctl, label, body);
            case Instr.DoWhile(var ctl, var label, var body, var condition) ->
                ((canCompleteNormally(body) || holds(body, new Instr.Continue(ctl)))
                                && !Constants.isConstant(condition, true))
                        || breaks(ctl, label, body);
            case Instr.Try(var body, var catches, var _) ->
                // A finally block completes normally by its type (FinallyBody).
                canCompleteNormally(body) || catches.stream().anyMatch(c -> canCompleteNormally(c.body()));
            case Instr.Jump _ -> false;
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

    /// Whether the loop of `ctl`, labeled `label` if it has one, can be left by a `break`: of the
    /// loop's own capability, or of the label it carries.
    private static boolean breaks(LoopCtl ctl, java.util.Optional<Label> label, Block<?, ?> body) {
        return holds(body, new Instr.Break(ctl))
                || label.map(l -> holds(body, new Instr.BreakLabel(l))).orElse(false);
    }

    private static boolean ifCompletes(Block<?, ?> then, Instr.Otherwise otherwise) {
        return switch (otherwise) {
            // An if without else can always complete normally, even `if (false)` (JLS 14.22).
            case Instr.Otherwise.Nothing _ -> true;
            case Instr.Otherwise.Else(var block) -> canCompleteNormally(then) || canCompleteNormally(block);
        };
    }

    /// Whether `block` holds the jump `jump` — the `break` or the `continue` of a loop, which is the
    /// same jump wherever it is, as a [LoopCtl] is the loop it was given to — at any depth: in a
    /// branch, a body of another loop, a `try` block or a `catch`.
    private static boolean holds(Block<?, ?> block, Instr jump) {
        return block.instrs().stream()
                .anyMatch(instr -> instr.equals(jump) || Instr.blocks(instr).anyMatch(inner -> holds(inner, jump)));
    }
}
