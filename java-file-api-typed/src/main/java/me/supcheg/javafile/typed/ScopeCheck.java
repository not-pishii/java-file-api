package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/// The scope check of §6.2, run when a statement is appended: every [Var]
/// the statement refers to must be owned by the block it is appended to or
/// by a block that one is nested in, a [MutVar] or [LoopCtl] must not be
/// reached across a lambda boundary, nor a [LoopCtl] out of a block of a
/// `switch` expression.
///
/// An expression that has a block of its own — a lambda, a `switch` with a
/// case that is a block — was checked statement by statement against the
/// block it was built in, so it is used in that block or one nested in it,
/// and a `switch` on the same side of every lambda boundary: its block
/// reads and assigns the [MutVar]s of the block it was built in, which a
/// lambda body could not.
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
            case Instr.Yield(var value) -> node(value, block, block.path());
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

    /// Checks an expression that is the body of an expression lambda, where
    /// the lambda is made: `scope` is the block that owns its parameters.
    ///
    /// @param node the expression
    /// @param scope the lambda-boundary block of the lambda
    static void lambdaValue(Node node, Block<?, ?> scope) {
        node(node, scope, scope.path());
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
            case Node.Lambda(var ignoredSam, var ignoredParams, var body) -> lambda(body, use, where);
            case Node.Switch switch_ -> {
                node(switch_.selector().node(), use, where);
                switch_.arms().forEach(arm -> arm(arm, use, where));
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
        if (builtIn != null && outside(builtIn, use)) {
            throw new IllegalStateException("a lambda built in the " + builtIn.path() + " is used in the " + where
                    + ", which is not inside that block: the variables it captures are out of scope there (§6.2)");
        }
    }

    /// A value of a `switch` is an expression of the block the `switch` is
    /// used in. A block of one was checked against the block the `switch`
    /// was built in, as it was built: the `switch` is used in that block, or
    /// in one nested in it that is not across a lambda boundary.
    private static void arm(Node.Arm arm, @Nullable Block<?, ?> use, String where) {
        switch (arm) {
            case Node.Arm.Value(var value) -> node(value, use, where);
            case Node.Arm.Block(var block) -> {
                Block<?, ?> builtIn = block.parent();
                if (builtIn == null) {
                    return;
                }
                if (outside(builtIn, use)) {
                    throw new IllegalStateException("a switch_ built in the " + builtIn.path() + " is used in the "
                            + where + ", which is not inside that block: the variables its blocks use are out of"
                            + " scope there (§6.2)");
                }
                if (crossesLambda(builtIn, use)) {
                    throw new IllegalStateException("a switch_ built in the " + builtIn.path() + " is used in the "
                            + where + ", across a lambda boundary: its blocks were checked as code of the block"
                            + " it was built in, which may assign a MutVar and throw what that block catches or"
                            + " declares, and a lambda body may not; build the switch_ inside the lambda (§6.2)");
                }
            }
        }
    }

    /// Whether `use`, or a block between it and `owner`, which encloses it,
    /// is the body of a lambda or a block of a `switch`: a block of an
    /// expression.
    private static boolean nestedSpecially(Block<?, ?> owner, @Nullable Block<?, ?> use) {
        return Stream.<@Nullable Block<?, ?>>iterate(use, b -> b != null && b != owner, Block::parent)
                .anyMatch(b -> b.nesting().ofExpression());
    }

    /// Whether a lambda body is between `use` and `owner`, which encloses it.
    private static boolean crossesLambda(Block<?, ?> owner, @Nullable Block<?, ?> use) {
        for (Block<?, ?> b = use; b != null && b != owner; b = b.parent()) {
            if (b.nesting() == Block.Nesting.LAMBDA_BODY) {
                return true;
            }
        }
        return false;
    }

    /// Whether `use` is neither `owner` nor a block nested in it.
    private static boolean outside(Block<?, ?> owner, @Nullable Block<?, ?> use) {
        return Stream.<@Nullable Block<?, ?>>iterate(use, Objects::nonNull, Block::parent)
                .noneMatch(b -> b == owner);
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
            crossesLambda |= b.nesting() == Block.Nesting.LAMBDA_BODY;
        }
        Block<?, ?> around = owner.parent();
        if (var.isVariableOfFor() && around != null && !outside(around, use) && nestedSpecially(around, use)) {
            throw new IllegalStateException("the " + var + " declared in the " + owner.path() + " is used in the "
                    + where + ", which is not inside the body of the loop: the variable is out of scope there. A"
                    + " lambda or a block of a switch_ built in the condition or the update of a for_ is a block"
                    + " of the code around the loop, not of its body: there, give the switch_ a case that is a"
                    + " value instead of a block, or use the variable in the body of the loop (§6.2)");
        }
        throw new IllegalStateException("the " + var + " declared in the " + owner.path() + " is used in the "
                + where + ", which is not inside that block: the variable is out of scope there — it escaped the"
                + " lambda it was handed to (§6.2)");
    }

    /// `this` is in scope in the body of the instance member it was handed
    /// to, lambdas in it included (it is effectively final), and nowhere
    /// else: not in another member, not in a field initializer (§6.5).
    private static void requireThisInScope(Block<?, ?> owner, @Nullable Block<?, ?> use, String where) {
        if (outside(owner, use)) {
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
            switch (b.nesting()) {
                case PLAIN, FINALLY_BLOCK -> {}
                case LAMBDA_BODY ->
                    throw new IllegalStateException(form + " in the " + where + " targets the loop of the "
                            + loopBody.path() + " across a lambda boundary: a lambda body cannot break or continue"
                            + " an enclosing loop (§6.3)");
                case SWITCH_BLOCK ->
                    throw new IllegalStateException(form + " in the " + where + " targets the loop of the "
                            + loopBody.path() + " out of a switch expression: a block of a switch expression"
                            + " cannot break or continue a loop around the switch (JLS 15.28.1)");
            }
        }
    }
}
