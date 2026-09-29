package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.util.List;

/// The scope check of §6.2, run when a statement is appended: every [Var]
/// the statement refers to must be owned by the block it is appended to or
/// by a block that one is nested in, and a [MutVar] or [LoopCtl] must not be
/// reached across a lambda boundary.
///
/// HOAS makes use-before-declaration unrepresentable, but not a variable
/// that is stored and used after its lambda returned; Java types could only
/// rule that out with a brand per block. The generator runs its lambdas
/// once, in order, so checking every append is a complete check, and the
/// exception's stack points at the misuse.
///
/// Nested blocks of a statement are not walked: their statements were
/// checked when they were appended to those blocks.
final class ScopeCheck {
    private ScopeCheck() {}

    /// Checks the expressions and loop capabilities of `instr`, about to be
    /// appended to `block`.
    static void check(Instr instr, Block<?, ?> block) {
        switch (instr) {
            case Instr.Let(var ignored, var init) -> node(init, block, block.path());
            case Instr.Exec(var effect) -> node(effect, block, block.path());
            case Instr.If(var condition, var ignoredThen, var ignoredElse) -> node(condition, block, block.path());
            case Instr.IfInstance(var operand, var ignoredType, var ignoredVar, var ignoredThen, var ignoredElse) ->
                node(operand, block, block.path());
            case Instr.While(var ignored, var condition, var ignoredBody) -> node(condition, block, block.path());
            case Instr.DoWhile(var ignored, var ignoredBody, var condition) -> node(condition, block, block.path());
            case Instr.For(var ignored, var ignoredVar, var init, var condition, var update, var body) -> {
                node(init, block, block.path());
                // The condition and update are in the scope of the loop variable.
                node(condition, body, body.path());
                node(update, body, body.path());
            }
            case Instr.ForEach(var ignored, var ignoredVar, var iterable, var ignoredBody) ->
                node(iterable, block, block.path());
            case Instr.Return(var value) -> value.ifPresent(v -> node(v, block, block.path()));
            case Instr.Throw(var value, var ignoredType) -> node(value, block, block.path());
            case Instr.Break(var ctl) -> loopCtl(ctl, block, "break_");
            case Instr.Continue(var ctl) -> loopCtl(ctl, block, "continue_");
            case Instr.Try ignored -> {}
            case Instr.Raw ignored -> {}
        }
    }

    /// Checks an expression that is used outside of any block, e.g. a field
    /// initializer: it may refer to no variable at all.
    ///
    /// @param node the expression
    /// @param where where it is used, for the message
    static void standalone(Node node, String where) {
        node(node, null, where);
    }

    private static void node(Node node, @Nullable Block<?, ?> use, String where) {
        switch (node) {
            case Node.Local(var v) -> requireInScope(v, use, where);
            case Node.Lit ignored -> {}
            case Node.RawLit ignored -> {}
            case Node.This(var owner) -> requireThisInScope(owner, use, where);
            case Node.StaticFieldGet ignored -> {}
            case Node.EnumConst ignored -> {}
            case Node.Raw ignored -> {}
            case Node.Box(var ignored, var operand) -> node(operand, use, where);
            case Node.Unbox(var ignored, var operand) -> node(operand, use, where);
            case Node.Call(var target, var ignored, var args) -> {
                node(target.node(), use, where);
                operands(args, use, where);
            }
            case Node.StaticCall(var ignored, var args) -> operands(args, use, where);
            case Node.New(var ignored, var args) -> operands(args, use, where);
            case Node.FieldGet(var target, var ignored) -> node(target.node(), use, where);
            case Node.ArrayAt(var array, var index) -> {
                node(array, use, where);
                node(index, use, where);
            }
            case Node.ArrayLength(var array) -> node(array, use, where);
            case Node.NewArray(var ignored, var length) -> node(length, use, where);
            case Node.Cond(var condition, var whenTrue, var whenFalse) -> {
                node(condition, use, where);
                node(whenTrue, use, where);
                node(whenFalse, use, where);
            }
            case Node.Binary(var ignored, var left, var right, var ignoredType) -> {
                node(left, use, where);
                node(right, use, where);
            }
            case Node.Unary(var ignored, var operand, var ignoredType) -> node(operand, use, where);
            case Node.Cast(var ignored, var operand) -> node(operand, use, where);
            case Node.InstanceOf(var operand, var ignored) -> node(operand, use, where);
            case Node.Lambda(var ignoredIface, var ignoredSam, var ignoredParams, var body) -> lambda(body, use, where);
            case Node.Switch(var selector, var ignored, var cases, var otherwise) -> {
                node(selector, use, where);
                for (Node.Case c : cases) {
                    node(c.value(), use, where);
                }
                otherwise.ifPresent(o -> node(o, use, where));
            }
            case Node.Assign(var target, var value) -> {
                target(target, use, where);
                node(value, use, where);
            }
        }
    }

    private static void operands(List<Node.Operand> operands, @Nullable Block<?, ?> use, String where) {
        for (Node.Operand operand : operands) {
            node(operand.node(), use, where);
        }
    }

    private static void target(Node.Target target, @Nullable Block<?, ?> use, String where) {
        switch (target) {
            case Node.Target.Local(var v) -> requireInScope(v, use, where);
            case Node.Target.Field(var t, var ignored) -> node(t.node(), use, where);
            case Node.Target.StaticField ignored -> {}
            case Node.Target.Element(var array, var index) -> {
                node(array, use, where);
                node(index, use, where);
            }
            case Node.Target.Init ignored -> {}
        }
    }

    /// A lambda's body was checked against its own block when it was built;
    /// what remains is that the lambda is used where the block it was built
    /// in is still in scope.
    private static void lambda(Node.LambdaBody body, @Nullable Block<?, ?> use, String where) {
        Block<?, ?> scope =
                switch (body) {
                    case Node.LambdaBody.Value(var s, var value) -> {
                        node(value, s, s.path());
                        yield s;
                    }
                    case Node.LambdaBody.Block(var block) -> block;
                };
        Block<?, ?> builtIn = scope.parent();
        if (builtIn != null && !encloses(builtIn, use)) {
            throw new IllegalStateException("a lambda built in the " + builtIn.path() + " is used in the " + where
                    + ", which is not inside that block: the variables it captures are out of scope there (§6.2)");
        }
    }

    private static boolean encloses(Block<?, ?> owner, @Nullable Block<?, ?> use) {
        for (Block<?, ?> b = use; b != null; b = b.parent()) {
            if (b == owner) {
                return true;
            }
        }
        return false;
    }

    private static void requireInScope(Var<?> var, @Nullable Block<?, ?> use, String where) {
        Block<?, ?> owner = var.owner();
        boolean crossesLambda = false;
        for (Block<?, ?> b = use; b != null; b = b.parent()) {
            if (b == owner) {
                if (crossesLambda && var instanceof MutVar<?>) {
                    throw new IllegalStateException("the " + var + " of the " + owner.path() + " is used in the "
                            + where + ", across a lambda boundary: a lambda can capture only effectively final"
                            + " variables, and a MutVar is assignable; copy it into a let first (§6.2)");
                }
                return;
            }
            crossesLambda |= b.isLambdaBoundary();
        }
        throw new IllegalStateException("the " + var + " declared in the " + owner.path() + " is used in the "
                + where + ", which is not inside that block: the variable is out of scope there — it escaped the"
                + " lambda it was handed to (§6.2)");
    }

    /// `this` is in scope in the body of the instance member it was handed
    /// to, lambdas in it included (it is effectively final), and nowhere
    /// else: not in another member, not in a field initializer (§6.5).
    private static void requireThisInScope(Block<?, ?> owner, @Nullable Block<?, ?> use, String where) {
        if (!encloses(owner, use)) {
            throw new IllegalStateException("the this of the " + owner.path() + " is used in the " + where
                    + ", which is not inside that body: this is handed only to the body of an instance method or"
                    + " constructor, and is in scope only there (§6.5)");
        }
    }

    private static void loopCtl(LoopCtl ctl, Block<?, ?> use, String form) {
        Block<?, ?> loopBody = ctl.body();
        String where = use.path();
        for (Block<?, ?> b = use; b != loopBody; b = b.parent()) {
            if (b == null) {
                throw new IllegalStateException(form + " in the " + where + " targets the loop of the "
                        + loopBody.path() + ", which does not enclose it: the LoopCtl escaped its loop body (§6.3)");
            }
            if (b.isLambdaBoundary()) {
                throw new IllegalStateException(form + " in the " + where + " targets the loop of the "
                        + loopBody.path() + " across a lambda boundary: a lambda body cannot break or continue an"
                        + " enclosing loop (§6.3)");
            }
        }
    }
}
