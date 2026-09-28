package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.AssignTarget;
import me.supcheg.javafile.code.BinaryExpr;
import me.supcheg.javafile.code.BreakStmt;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ContinueStmt;
import me.supcheg.javafile.code.DoWhileStmt;
import me.supcheg.javafile.code.EnhancedForStmt;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.ForStmt;
import me.supcheg.javafile.code.IfStmt;
import me.supcheg.javafile.code.LabeledStmt;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.StatementExpr;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.UnaryExpr;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.model.Param;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/// The pure `typed → core` lowering pass (§7). Erases the phantom types and
/// the HOAS variable identities, translating [Node]/[Instr] into `core`
/// [Expr]/[Stmt].
///
/// Residual semantic checks that Java's type system cannot express (§9 —
/// checked-exception coverage, switch exhaustiveness, `implement`
/// completeness, modifier validity) are the responsibility of the
/// declaration layer that calls into this class, not of this class itself;
/// this class performs the structural checks that are intrinsic to lowering
/// itself: a variable must be in scope where it is referenced (enforced by
/// the scope stack of [NameEnv]) and `break`/`continue` must target a loop
/// currently being lowered in the same lambda (enforced by the loop stack
/// below, which a lambda body starts afresh) — both would otherwise be
/// silently mis-rendered rather than caught. The builder already rejects
/// both when the statement is built ([ScopeCheck]); these are defence in
/// depth.
///
/// One instance lowers exactly one top-level body (a method, constructor, or
/// lambda); nested blocks and lambdas share its [NameEnv] so names stay
/// unique, and nested blocks share its loop stack so `break`/`continue`
/// resolve correctly across nesting.
final class Lowering {

    private final NameEnv names = new NameEnv();
    private Deque<LoopCtl> loopStack = new ArrayDeque<>();
    private final Map<LoopCtl, String> loopLabelNames = new HashMap<>();
    private final Set<LoopCtl> usedLabels = new HashSet<>();
    private int labelCounter;

    /// Declares a variable up front (e.g. a method parameter) so the body can
    /// reference it before lowering the body itself.
    String declareUpfront(Var<?> var) {
        return names.declare(var);
    }

    /// Lowers a single expression outside statement context, e.g. a field
    /// initializer.
    Expr lowerExpr(Node node) {
        return expr(node);
    }

    /// Lowers a sequence of statements into a `core` method/constructor/loop
    /// body.
    CodeBody lowerBlock(List<Instr> instrs) {
        names.push();
        try {
            List<Stmt> stmts = new ArrayList<>(instrs.size());
            for (Instr instr : instrs) {
                stmts.add(stmt(instr));
            }
            return new CodeBody(List.copyOf(stmts));
        } finally {
            names.pop();
        }
    }

    // ------------------------------------------------------------------
    // Statements
    // ------------------------------------------------------------------

    private Stmt stmt(Instr instr) {
        return switch (instr) {
            case Instr.Let(var v, var init) -> {
                Expr initExpr = expr(init);
                String name = names.declare(v);
                yield new LocalVarDeclStmt.Typed(v.type().typeRef(), name, Optional.of(initExpr));
            }
            case Instr.Exec(var effect) -> effectStmt(effect);
            case Instr.If(var condition, var then, var otherwise) ->
                new IfStmt(expr(condition), lowerBlock(then.instrs()), List.of(), lowerOptional(otherwise));
            case Instr.IfInstance(var operand, var type, var binding, var then, var otherwise) -> {
                Expr operandExpr = expr(operand);
                // The binding is in scope in the then-branch only.
                names.push();
                Expr condition;
                CodeBody thenCode;
                try {
                    condition = operandExpr.instanceOf(type.typeRef(), names.declare(binding));
                    thenCode = lowerBlock(then.instrs());
                } finally {
                    names.pop();
                }
                yield new IfStmt(condition, thenCode, List.of(), lowerOptional(otherwise));
            }
            case Instr.While(var ctl, var condition, var body) -> {
                Expr conditionExpr = expr(condition);
                CodeBody bodyCode = withLoop(ctl, body);
                yield labeled(ctl, new WhileStmt(conditionExpr, bodyCode));
            }
            case Instr.DoWhile(var ctl, var body, var condition) -> {
                CodeBody bodyCode = withLoop(ctl, body);
                yield labeled(ctl, new DoWhileStmt(bodyCode, expr(condition)));
            }
            case Instr.For(var ctl, var var_, var init, var condition, var update, var body) -> {
                Expr initExpr = expr(init);
                // The loop variable is in scope in the condition, the update, and the body.
                names.push();
                LocalVarDeclStmt.Typed initStmt;
                Expr conditionExpr;
                Stmt updateStmt;
                CodeBody bodyCode;
                try {
                    initStmt = new LocalVarDeclStmt.Typed(
                            var_.type().typeRef(), names.declare(var_), Optional.of(initExpr));
                    conditionExpr = expr(condition);
                    updateStmt = effectStmt(update);
                    bodyCode = withLoop(ctl, body);
                } finally {
                    names.pop();
                }
                yield labeled(
                        ctl,
                        new ForStmt(
                                Optional.of(initStmt), Optional.of(conditionExpr), Optional.of(updateStmt), bodyCode));
            }
            case Instr.ForEach(var ctl, var var_, var iterable, var body) -> {
                Expr iterableExpr = expr(iterable);
                names.push();
                String name;
                CodeBody bodyCode;
                try {
                    name = names.declare(var_);
                    bodyCode = withLoop(ctl, body);
                } finally {
                    names.pop();
                }
                yield labeled(ctl, new EnhancedForStmt(var_.type().typeRef(), name, iterableExpr, bodyCode));
            }
            case Instr.Return(var value) -> new ReturnStmt(value.map(this::expr));
            case Instr.Throw(var value, var ignoredType) -> new ThrowStmt(expr(value));
            case Instr.Break(var ctl) -> breakOrContinue(ctl, true);
            case Instr.Continue(var ctl) -> breakOrContinue(ctl, false);
            case Instr.Try(var body, var catches, var finallyBlock) -> tryStmt(body, catches, finallyBlock);
            case Instr.Raw(var s) -> s;
        };
    }

    private Optional<CodeBody> lowerOptional(Optional<Block<?, ?>> block) {
        return block.map(b -> lowerBlock(b.instrs()));
    }

    private CodeBody withLoop(LoopCtl ctl, Block<?, ?> body) {
        loopStack.push(ctl);
        try {
            return lowerBlock(body.instrs());
        } finally {
            loopStack.pop();
        }
    }

    private Stmt labeled(LoopCtl ctl, Stmt loopStmt) {
        return usedLabels.contains(ctl) ? new LabeledStmt(labelFor(ctl), loopStmt) : loopStmt;
    }

    private String labelFor(LoopCtl ctl) {
        return loopLabelNames.computeIfAbsent(ctl, _ -> "loop" + (labelCounter++));
    }

    private Stmt breakOrContinue(LoopCtl ctl, boolean isBreak) {
        if (!loopStack.contains(ctl)) {
            throw new IllegalStateException(
                    "break/continue used for a loop that is not currently being lowered: the LoopCtl escaped its"
                            + " loop's scope, e.g. by being captured into a lambda that outlives the loop");
        }
        if (loopStack.peek() == ctl) {
            return isBreak ? new BreakStmt(Optional.empty()) : new ContinueStmt(Optional.empty());
        }
        usedLabels.add(ctl);
        String label = labelFor(ctl);
        return isBreak ? new BreakStmt(Optional.of(label)) : new ContinueStmt(Optional.of(label));
    }

    private Stmt tryStmt(Block<?, ?> body, List<Instr.Catch> catches, Optional<Block<?, ?>> finallyBlock) {
        CodeBody bodyCode = lowerBlock(body.instrs());
        List<CatchClause> coreCatches = new ArrayList<>();
        for (Instr.Catch c : catches) {
            names.push();
            try {
                String name = names.declare(c.var());
                CodeBody catchBody = lowerBlock(c.body().instrs());
                coreCatches.add(
                        new CatchClause(NonEmptyList.copyOf(List.of(c.type().typeRef())), name, catchBody));
            } finally {
                names.pop();
            }
        }
        if (finallyBlock.isPresent()) {
            CodeBody finallyCode = lowerBlock(finallyBlock.get().instrs());
            return new TryStmt.WithFinally(List.of(), bodyCode, coreCatches, finallyCode);
        }
        return new TryStmt.CatchOnly(List.of(), bodyCode, NonEmptyList.copyOf(coreCatches));
    }

    /// An [Instr.Exec] or a `for`-loop update wraps an [Effect]'s [Node]: an
    /// assignment lowers to [AssignStmt], everything else is a
    /// [StatementExpr] wrapped in [me.supcheg.javafile.code.ExprStmt].
    private Stmt effectStmt(Node effect) {
        if (effect instanceof Node.Assign assign) {
            return assignStmt(assign);
        }
        Expr lowered = expr(effect);
        if (lowered instanceof StatementExpr statementExpr) {
            return new ExprStmt(statementExpr);
        }
        throw new IllegalStateException("not a statement expression: " + effect);
    }

    private Stmt assignStmt(Node.Assign assign) {
        Expr value = expr(assign.value());
        AssignTarget target =
                switch (assign.target()) {
                    case Node.Target.Local(var v) -> new FieldAccessExpr(Optional.empty(), names.nameOf(v));
                    case Node.Target.Field(var t, var field) -> new FieldAccessExpr(Optional.of(expr(t)), field.name());
                    case Node.Target.StaticField(var field) ->
                        new StaticFieldAccessExpr(field.owner().typeRef(), field.name());
                    case Node.Target.Element(var array, var index) ->
                        expr(array).arrayAccess(expr(index));
                    case Node.Target.Init(var field) -> new FieldAccessExpr(Optional.of(new ThisExpr()), field.name());
                };
        return new AssignStmt(target, AssignOp.ASSIGN, value);
    }

    // ------------------------------------------------------------------
    // Expressions
    // ------------------------------------------------------------------

    private Expr expr(Node node) {
        return switch (node) {
            case Node.Lit(var literal, var ignoredValue) -> literal;
            case Node.RawLit(var literal) -> literal;
            case Node.Local(var v) -> new FieldAccessExpr(Optional.empty(), names.nameOf(v));
            case Node.This() -> new ThisExpr();
            case Node.Box(var type, var operand) ->
                Exprs.staticCall(type.boxed().typeRef(), "valueOf", expr(operand));
            case Node.Unbox(var type, var operand) -> expr(operand).call(type.unboxMethodName());
            case Node.Call(var target, var method, var args) -> expr(target).call(method.name(), lowerArgs(args));
            case Node.StaticCall(var method, var args) ->
                Exprs.staticCall(method.owner().typeRef(), method.name(), lowerArgs(args));
            case Node.New(var ctor, var args, var diamond) ->
                diamond
                        ? Exprs.newDiamond(ctor.owner().erasure(), lowerArgs(args))
                        : Exprs.new_(ctor.owner().typeRef(), lowerArgs(args));
            case Node.FieldGet(var target, var field) -> expr(target).field(field.name());
            case Node.StaticFieldGet(var field) ->
                Exprs.staticField(field.owner().typeRef(), field.name());
            case Node.EnumConst(var constant) ->
                Exprs.staticField(constant.owner().typeRef(), constant.name());
            case Node.ArrayAt(var array, var index) -> expr(array).arrayAccess(expr(index));
            case Node.ArrayLength(var array) -> expr(array).field("length");
            case Node.NewArray(var component, var length) -> Exprs.newArray(component.typeRef(), expr(length));
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                Exprs.cond(expr(condition), expr(whenTrue), expr(whenFalse));
            case Node.Binary(var op, var left, var right, var ignoredType) ->
                new BinaryExpr(expr(left), op, expr(right));
            case Node.Unary(var op, var operand, var ignoredType) -> new UnaryExpr(op, expr(operand));
            case Node.Cast(var type, var operand) -> Exprs.cast(type, expr(operand));
            case Node.InstanceOf(var operand, var type) -> expr(operand).instanceOf(type);
            case Node.Lambda(var ignoredIface, var ignoredSam, var params, var body) -> lambda(params, body);
            case Node.Switch ignored ->
                throw new UnsupportedOperationException(
                        "typed switch expressions are not implemented yet (phase 2 — exhaustive enum switch, §3.5/§9)");
            case Node.Assign ignored ->
                throw new IllegalStateException(
                        "an assignment was used as a value; it is only representable as a statement (Effect)");
            case Node.Raw(var raw) -> raw;
        };
    }

    private List<Expr> lowerArgs(List<Node> args) {
        List<Expr> result = new ArrayList<>(args.size());
        for (Node arg : args) {
            result.add(expr(arg));
        }
        return result;
    }

    /// A lambda body is a scope of its own for the parameters, and a fresh
    /// loop context: `break`/`continue` cannot jump out of a lambda.
    private Expr lambda(List<Var<?>> params, Node.LambdaBody body) {
        Deque<LoopCtl> enclosingLoops = loopStack;
        loopStack = new ArrayDeque<>();
        names.push();
        try {
            List<Param> coreParams = new ArrayList<>(params.size());
            for (Var<?> param : params) {
                coreParams.add(new Param(names.declare(param), param.type().typeRef()));
            }
            return switch (body) {
                case Node.LambdaBody.Value(var ignoredScope, var value) -> Exprs.typedLambda(coreParams, expr(value));
                case Node.LambdaBody.Block(var block) -> {
                    List<Stmt> stmts = lowerBlock(block.instrs()).statements();
                    yield Exprs.typedLambda(coreParams, cb -> {
                        for (Stmt s : stmts) {
                            cb.accept(s);
                        }
                    });
                }
            };
        } finally {
            names.pop();
            loopStack = enclosingLoops;
        }
    }
}
