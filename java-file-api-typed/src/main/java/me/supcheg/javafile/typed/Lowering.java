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
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.StatementExpr;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.StaticMethodCallExpr;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.UnaryExpr;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
///
/// **Lowering pins the fact** (§6.1, §10: one overload is one fact). javac
/// resolves members by the static types of the rendered code; lowering
/// renders the code so that javac resolves exactly the member the fact
/// names, and adds a cast only where it can make a difference:
///
/// - **Arguments.** An argument whose static type is not the parameter's
///   is cast to the parameter type, `(P) arg`. Once every argument is
///   exactly of its parameter's type, the fact is the most specific
///   applicable method whatever else the searched type declares (JLS
///   15.12.2.5). The casts are skipped when the method table of the
///   receiver type proves the fact is its only method of that name and
///   arity — the only candidate of the fixed-arity phases, which is where an
///   applicable fact is found. A primitive argument of a reference parameter
///   is always cast, `(Object) 1`: boxing is never left implicit (§6.1).
///   Static methods and constructors have no table to prove it by.
/// - **Receivers of fields.** A field read or assigned through a receiver
///   whose static type is not the field's owner is qualified by a cast,
///   `((Owner) recv).f`, so a field of the receiver's type hiding it (JLS
///   8.3) is never picked.
/// - **Inexact expressions** ([Node#isExact(Node)]). A call through a
///   receiver of a subtype of its owner may be typed narrower by javac than
///   by its fact; where the type matters — as an argument, a receiver, a
///   branch of a conditional — its receiver is cast to the owner,
///   `((Owner) recv).m()`, which types it by the fact again. A cast is never
///   added where javac would find it redundant.
/// - **Generic members.** A static member of a parameterized owner is
///   qualified by the raw type, `List.of()`, never `List<String>.of()`; the
///   type arguments of a generic method's fact
///   ([me.supcheg.javafile.facts.MemberTraits#typeArgs()]) are rendered as
///   explicit witnesses, `List.<String>of()`, `stream.<R>map(f)`, so javac
///   never infers others.
/// - **The diamond.** `new T<>(...)` is rendered only where the target type
///   is exactly the constructed type — a local variable's initializer, a
///   field's initializer, the value returned from a body of that result
///   type — and the class is generic; everywhere else the type arguments
///   are explicit, `new ArrayList<String>()`.
///
/// The casts that depend on nothing but the expression itself — `((T)
/// null)`, the branches of `cond` — are part of the node already, built by
/// [Expressions], so that constant folding ([Constants]) sees the tree javac
/// sees; the ones decided here are never in a constant expression.
final class Lowering {

    private final NameEnv names = new NameEnv();
    private Deque<LoopCtl> loopStack = new ArrayDeque<>();
    private final Map<LoopCtl, String> loopLabelNames = new HashMap<>();
    private final Set<LoopCtl> usedLabels = new HashSet<>();
    private int labelCounter;

    /// The result type of the body being lowered, the target type of its
    /// `return` statements; `null` for a `void` body or an expression.
    private @Nullable TypeToken<?> result;

    /// A lowering of a `void` body or of a standalone expression.
    Lowering() {
        this(null);
    }

    /// A lowering of a body whose `return` statements return `result`.
    ///
    /// @param result the result type, or `null` for a `void` body
    Lowering(@Nullable TypeToken<?> result) {
        this.result = result;
    }

    /// Declares a variable up front (e.g. a method parameter) so the body can
    /// reference it before lowering the body itself.
    String declareUpfront(Var<?> var) {
        return names.declare(var);
    }

    /// Lowers a single expression outside statement context.
    Expr lowerExpr(Node node) {
        return expr(node);
    }

    /// Lowers the initializer of a variable or field declared of type
    /// `target`, where the diamond may stand.
    Expr lowerInitializer(Node node, TypeToken<?> target) {
        return initializer(node, target);
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
                Expr initExpr = initializer(init, v.type());
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
                Expr initExpr = initializer(init, var_.type());
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
            case Instr.Return(var value) -> new ReturnStmt(value.map(this::returned));
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
                    case Node.Target.Field(var t, var field) ->
                        new FieldAccessExpr(Optional.of(fieldReceiver(t, field.owner())), field.name());
                    case Node.Target.StaticField(var field) ->
                        new StaticFieldAccessExpr(raw(field.owner()), field.name());
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
        return expr(node, false);
    }

    /// Lowers `node`; if `pin`, so that javac types it by exactly its token
    /// even where [Node#isExact(Node)] does not hold.
    private Expr expr(Node node, boolean pin) {
        return switch (node) {
            case Node.Lit(var literal, var ignoredValue) -> literal;
            case Node.RawLit(var literal) -> literal;
            case Node.Local(var v) -> new FieldAccessExpr(Optional.empty(), names.nameOf(v));
            case Node.This ignored -> new ThisExpr();
            case Node.Box(var type, var operand) ->
                Exprs.staticCall(type.boxed().typeRef(), "valueOf", expr(operand));
            case Node.Unbox(var type, var operand) -> expr(operand).call(type.unboxMethodName());
            case Node.Call(var target, var method, var args) -> call(target, method, args, pin);
            case Node.StaticCall(var method, var args) ->
                new StaticMethodCallExpr(
                        raw(method.owner()), method.name(), arguments(method, null, args), typeArgs(method));
            case Node.New(var ctor, var args) -> Exprs.new_(ctor.owner().typeRef(), arguments(ctor, null, args));
            case Node.FieldGet(var target, var field) ->
                fieldReceiver(target, field.owner()).field(field.name());
            case Node.StaticFieldGet(var field) -> Exprs.staticField(raw(field.owner()), field.name());
            case Node.EnumConst(var constant) -> Exprs.staticField(raw(constant.owner()), constant.name());
            case Node.ArrayAt(var array, var index) -> expr(array, pin).arrayAccess(expr(index));
            case Node.ArrayLength(var array) -> expr(array).field("length");
            case Node.NewArray(var component, var length) -> Exprs.newArray(component.typeRef(), expr(length));
            // Each branch is of the conditional's type already (Expressions.cond); pinned, the
            // conditional is of exactly that type (JLS 15.25).
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                Exprs.cond(expr(condition), expr(whenTrue, true), expr(whenFalse, true));
            case Node.Binary(var op, var left, var right, var ignoredType) ->
                new BinaryExpr(expr(left), op, expr(right));
            case Node.Unary(var op, var operand, var ignoredType) -> new UnaryExpr(op, expr(operand));
            case Node.Cast(var type, var operand) -> Exprs.cast(type, expr(operand));
            case Node.InstanceOf(var operand, var type) -> expr(operand).instanceOf(type);
            case Node.Lambda(var ignoredIface, var sam, var params, var body) -> lambda(sam, params, body);
            case Node.Switch ignored ->
                throw new UnsupportedOperationException(
                        "typed switch expressions are not implemented yet (phase 2 — exhaustive enum switch, §3.5/§9)");
            case Node.Assign ignored ->
                throw new IllegalStateException(
                        "an assignment was used as a value; it is only representable as a statement (Effect)");
            case Node.Raw(var raw) -> raw;
        };
    }

    // ------------------------------------------------------------------
    // Pinning the fact (see the class documentation)
    // ------------------------------------------------------------------

    /// The value of a `return`, whose target type is the result type.
    private Expr returned(Node value) {
        return result == null ? expr(value) : initializer(value, result);
    }

    /// An expression whose target type is `target`, in an assignment
    /// context: the diamond infers exactly `target` there (JLS 15.9.1).
    private Expr initializer(Node node, TypeToken<?> target) {
        if (node instanceof Node.New(var ctor, var args)
                && ctor.owner().typeRef() instanceof ParameterizedTypeRef
                && Tokens.sameType(ctor.owner(), target)) {
            return Exprs.newDiamond(ctor.owner().erasure(), arguments(ctor, null, args));
        }
        return expr(node);
    }

    /// `target.method(args)`. The receiver is cast to the method's owner
    /// where the call must be pinned and the receiver is of a subtype;
    /// otherwise it is pinned itself where its type decides the overload.
    private Expr call(Node.Operand target, Invocable method, List<Node.Operand> args, boolean pin) {
        DeclaredToken<?> owner = method.owner();
        Expr receiver;
        TypeToken<?> searched;
        if (pin && !Tokens.sameType(target.type(), owner)) {
            receiver = Exprs.cast(owner.typeRef(), expr(target.node()));
            searched = owner;
        } else {
            boolean settled = true;
            for (int i = 0; i < args.size(); i++) {
                settled &= settled(args.get(i), method.params().get(i));
            }
            receiver = expr(target.node(), pin || !settled);
            searched = target.type();
        }
        return new MethodCallExpr(
                Optional.of(receiver), method.name(), arguments(method, searched, args), typeArgs(method));
    }

    /// The arguments of `member`, each of exactly its parameter's type unless
    /// `searched` — the type whose methods javac searches, `null` if there is
    /// no method table to consult — has no other candidate.
    private List<Expr> arguments(Invocable member, @Nullable TypeToken<?> searched, List<Node.Operand> args) {
        boolean onlyCandidate = onlyCandidate(searched, member);
        List<TypeToken<?>> params = member.params();
        List<Expr> lowered = new ArrayList<>(args.size());
        for (int i = 0; i < args.size(); i++) {
            Node.Operand arg = args.get(i);
            TypeToken<?> param = params.get(i);
            boolean boxes =
                    arg.type() instanceof PrimitiveToken<?, ?, ?> && !(param instanceof PrimitiveToken<?, ?, ?>);
            if (boxes || (!onlyCandidate && !Tokens.sameType(arg.type(), param))) {
                lowered.add(Exprs.cast(param.typeRef(), expr(arg.node())));
            } else {
                lowered.add(expr(arg.node(), !onlyCandidate));
            }
        }
        return lowered;
    }

    /// Whether javac types `arg` by exactly `param` without a cast.
    private static boolean settled(Node.Operand arg, TypeToken<?> param) {
        return Tokens.sameType(arg.type(), param) && Node.isExact(arg.node());
    }

    /// Whether the method table of `searched` proves `method` is its only
    /// method of that name and arity. A table that does not list `method`
    /// at all is taken as incomplete, proving nothing. The table lists
    /// instance methods only: a static method of the same name and arity is
    /// not seen, which the fact sources are relied on to rule out.
    private static boolean onlyCandidate(@Nullable TypeToken<?> searched, Invocable method) {
        if (!(searched instanceof DeclaredToken<?> declared)) {
            return false;
        }
        MethodTable table = declared.methods();
        MethodSignature signature = method.signature();
        if (!table.concreteMethods().contains(signature)
                && !table.abstractMethods().contains(signature)) {
            return false;
        }
        return Stream.concat(table.concreteMethods().stream(), table.abstractMethods().stream())
                        .filter(s -> s.name().equals(signature.name())
                                && s.params().size() == signature.params().size())
                        .count()
                == 1;
    }

    /// The receiver of a field of `owner`: cast to `owner` if it is of
    /// another type, so no field hiding it is picked.
    private Expr fieldReceiver(Node.Operand target, DeclaredToken<?> owner) {
        if (!Tokens.sameType(target.type(), owner)) {
            return Exprs.cast(owner.typeRef(), expr(target.node()));
        }
        return expr(target.node(), true);
    }

    /// The raw type qualifying a static member of `owner`: `List`, never
    /// `List<String>`, which Java rejects there.
    private static ClassTypeRef raw(DeclaredToken<?> owner) {
        return Types.of(owner.erasure());
    }

    /// The explicit type arguments of a generic method's fact.
    private static List<TypeRef> typeArgs(Invocable method) {
        return method.traits().typeArgs().stream().map(TypeToken::typeRef).toList();
    }

    // ------------------------------------------------------------------

    /// A lambda body is a scope of its own for the parameters, and a fresh
    /// loop context: `break`/`continue` cannot jump out of a lambda. Its
    /// `return` statements return the result of the functional interface's
    /// method.
    private Expr lambda(Invocable sam, List<Var<?>> params, Node.LambdaBody body) {
        Deque<LoopCtl> enclosingLoops = loopStack;
        TypeToken<?> enclosingResult = result;
        loopStack = new ArrayDeque<>();
        result = sam.resultType().orElse(null);
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
            result = enclosingResult;
        }
    }
}
