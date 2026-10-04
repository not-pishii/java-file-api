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
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
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
///   The arguments of static methods and constructors are always cast.
/// - **Overloads that erase alike.** A fact is known among the members of
///   its owner by the signature it declares
///   ([Invocable#declared()]), not by the erased one: `m(T)` of a
///   `Box<String>` and its `m(String)` take the same argument types, and so
///   do `<T> m(T)` with `String` for `T` and `m(String)`. No cast of an
///   argument tells them apart and explicit type arguments do not rule out
///   a method that is not generic (JLS 15.12.2.1), so javac would report an
///   ambiguity or pick the other one. Where both are members of the owner
///   such a call cannot be written in Java, and lowering rejects it with a
///   [FactLookupException]; where the other one is added by the type of
///   the receiver, a subtype of the owner, the receiver is cast to the
///   owner.
/// - **Receivers of fields.** A field read or assigned through a receiver
///   whose static type is not the field's owner is qualified by a cast,
///   `((Owner) recv).f`, so a field of the receiver's type hiding it (JLS
///   8.3) is never picked, and a name the receiver's type has from two
///   supertypes (JLS 8.3.3.3) is not ambiguous; a static field is qualified
///   by its owner, `Owner.f`, for the same reasons.
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
/// **Lowering checks the facts against the target classpath** (§5). The
/// metamodels a generator uses were generated against the classpath the
/// generator was compiled with; the code is compiled against another one.
/// Every type lowering renders code of — the owner, the parameters, the
/// result and the exceptions of a member, the type of a receiver and of an
/// argument, of a variable, a cast, a `catch` — is verified with the
/// [TargetClasspath] when it is first met, and the method tables that decide
/// the casts above are read through it: an overload the target has added is
/// a candidate here as it is to javac. A metamodel that does not hold is a
/// [TargetClasspathMismatchException].
///
/// The casts that depend on nothing but the expression itself — `((T)
/// null)`, the branches of `cond` — are part of the node already, built by
/// [Expressions], so that constant folding ([Constants]) sees the tree javac
/// sees; the ones decided here are never in a constant expression.
final class Lowering {

    private final TargetClasspath target;
    private final NameEnv names = new NameEnv();
    private Deque<LoopCtl> loopStack = new ArrayDeque<>();
    private final Map<LoopCtl, String> loopLabelNames = new HashMap<>();
    private final Set<LoopCtl> usedLabels = new HashSet<>();
    private int labelCounter;

    /// The result type of the body being lowered, the target type of its
    /// `return` statements; `null` for a `void` body or an expression.
    private @Nullable TypeToken<?> result;

    /// A lowering of a `void` body or of a standalone expression.
    ///
    /// @param target the classpath the lowered code is compiled against
    Lowering(TargetClasspath target) {
        this(target, null);
    }

    /// A lowering of a body whose `return` statements return `result`.
    ///
    /// @param target the classpath the lowered code is compiled against
    /// @param result the result type, or `null` for a `void` body
    /// @throws TargetClasspathMismatchException if a metamodel of `result` does not hold on `target`
    Lowering(TargetClasspath target, @Nullable TypeToken<?> result) {
        this.target = target;
        this.result = result;
        if (result != null) {
            target.verify(result);
        }
    }

    /// Declares a variable up front (e.g. a method parameter) so the body can
    /// reference it before lowering the body itself.
    String declareUpfront(Var<?> var) {
        return declare(var);
    }

    /// Declares a variable in the innermost scope: its type is one the
    /// lowered code names.
    private String declare(Var<?> var) {
        target.verify(var.type());
        return names.declare(var);
    }

    /// Lowers a single expression outside statement context.
    Expr lowerExpr(Node node) {
        return expr(node);
    }

    /// Lowers the initializer of a variable or field declared of type
    /// `declared`, where the diamond may stand.
    Expr lowerInitializer(Node node, TypeToken<?> declared) {
        target.verify(declared);
        return initializer(node, declared);
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
                String name = declare(v);
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
                    target.verify(type);
                    condition = operandExpr.instanceOf(type.typeRef(), declare(binding));
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
                    initStmt = new LocalVarDeclStmt.Typed(var_.type().typeRef(), declare(var_), Optional.of(initExpr));
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
                    name = declare(var_);
                    bodyCode = withLoop(ctl, body);
                } finally {
                    names.pop();
                }
                yield labeled(ctl, new EnhancedForStmt(var_.type().typeRef(), name, iterableExpr, bodyCode));
            }
            case Instr.Return(var value) -> new ReturnStmt(value.map(this::returned));
            case Instr.Throw(var value, var type) -> {
                target.verify(type);
                yield new ThrowStmt(expr(value));
            }
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
                target.verify(c.type());
                String name = declare(c.var());
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
        AssignTarget assigned =
                switch (assign.target()) {
                    case Node.Target.Local(var v) -> new FieldAccessExpr(Optional.empty(), names.nameOf(v));
                    case Node.Target.Field(var t, var field) ->
                        new FieldAccessExpr(Optional.of(fieldReceiver(t, field)), field.name());
                    case Node.Target.StaticField(var field) ->
                        new StaticFieldAccessExpr(staticOwner(field), field.name());
                    case Node.Target.Element(var array, var index) ->
                        expr(array).arrayAccess(expr(index));
                    case Node.Target.Init(var field) -> {
                        uses(field);
                        yield new FieldAccessExpr(Optional.of(new ThisExpr()), field.name());
                    }
                };
        return new AssignStmt(assigned, AssignOp.ASSIGN, value);
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
            case Node.Call(var receiver, var method, var args) -> call(receiver, method, args, pin);
            case Node.StaticCall(var method, var args) ->
                new StaticMethodCallExpr(
                        raw(method.owner()), method.name(), arguments(method, null, args), typeArgs(method));
            case Node.New(var ctor, var args) -> Exprs.new_(ctor.owner().typeRef(), arguments(ctor, null, args));
            case Node.FieldGet(var receiver, var field) ->
                fieldReceiver(receiver, field).field(field.name());
            case Node.StaticFieldGet(var field) -> Exprs.staticField(staticOwner(field), field.name());
            case Node.EnumConst(var constant) -> {
                target.verify(constant.owner());
                yield Exprs.staticField(raw(constant.owner()), constant.name());
            }
            case Node.ArrayAt(var array, var index) -> expr(array, pin).arrayAccess(expr(index));
            case Node.ArrayLength(var array) -> expr(array).field("length");
            case Node.NewArray(var component, var length) -> {
                target.verify(component);
                yield Exprs.newArray(component.typeRef(), expr(length));
            }
            // Each branch is of the conditional's type already (Expressions.cond); pinned, the
            // conditional is of exactly that type (JLS 15.25).
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                Exprs.cond(expr(condition), expr(whenTrue, true), expr(whenFalse, true));
            case Node.Binary(var op, var left, var right, var ignoredType) ->
                new BinaryExpr(expr(left), op, expr(right));
            case Node.Unary(var op, var operand, var ignoredType) -> new UnaryExpr(op, expr(operand));
            case Node.Cast(var type, var operand) -> {
                target.verify(type);
                yield Exprs.cast(type.typeRef(), expr(operand));
            }
            case Node.InstanceOf(var operand, var type) -> {
                target.verify(type);
                yield expr(operand).instanceOf(type.typeRef());
            }
            case Node.Lambda(var sam, var params, var body) -> lambda(sam, params, body);
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

    /// An expression whose target type is `declared`, in an assignment
    /// context: the diamond infers exactly `declared` there (JLS 15.9.1).
    private Expr initializer(Node node, TypeToken<?> declared) {
        if (node instanceof Node.New(var ctor, var args)
                && ctor.owner().typeRef() instanceof ParameterizedTypeRef
                && Tokens.sameType(ctor.owner(), declared)) {
            return Exprs.newDiamond(ctor.owner().erasure(), arguments(ctor, null, args));
        }
        return expr(node);
    }

    /// `target.method(args)`. The receiver is cast to the method's owner
    /// where the call must be pinned and the receiver is of a subtype, and
    /// where the subtype has an overload that erases as the method does
    /// ([#hasTwin]); otherwise it is pinned itself where its type decides the
    /// overload.
    private Expr call(Node.Operand receiving, Invocable method, List<Node.Operand> args, boolean pin) {
        DeclaredToken<?> owner = method.owner();
        target.verify(receiving.type());
        Expr receiver;
        TypeToken<?> searched;
        if ((pin && !Tokens.sameType(receiving.type(), owner)) || hasTwin(receiving.type(), method)) {
            receiver = Exprs.cast(owner.typeRef(), expr(receiving.node()));
            searched = owner;
        } else {
            boolean settled = IntStream.range(0, args.size())
                    .allMatch(i -> settled(args.get(i), method.params().get(i)));
            receiver = expr(receiving.node(), pin || !settled);
            searched = receiving.type();
        }
        return new MethodCallExpr(
                Optional.of(receiver), method.name(), arguments(method, searched, args), typeArgs(method));
    }

    /// The arguments of `member`, each of exactly its parameter's type unless
    /// `searched` — the type whose methods javac searches, `null` if there is
    /// no method table to consult — has no other candidate.
    ///
    /// @throws FactLookupException if no arguments make javac resolve `member`, see [#requireDistinct]
    private List<Expr> arguments(Invocable member, @Nullable TypeToken<?> searched, List<Node.Operand> args) {
        uses(member);
        args.forEach(arg -> target.verify(arg.type()));
        requireDistinct(member);
        boolean onlyCandidate = onlyCandidate(searched, member);
        List<TypeToken<?>> params = member.params();
        return IntStream.range(0, args.size())
                .mapToObj(i -> {
                    Node.Operand arg = args.get(i);
                    TypeToken<?> param = params.get(i);
                    boolean boxes = arg.type() instanceof PrimitiveToken<?, ?, ?>
                            && !(param instanceof PrimitiveToken<?, ?, ?>);
                    return boxes || (!onlyCandidate && !Tokens.sameType(arg.type(), param))
                            ? Exprs.cast(param.typeRef(), expr(arg.node()))
                            : expr(arg.node(), !onlyCandidate);
                })
                .toList();
    }

    /// Whether javac types `arg` by exactly `param` without a cast.
    private static boolean settled(Node.Operand arg, TypeToken<?> param) {
        return Tokens.sameType(arg.type(), param) && Node.isExact(arg.node());
    }

    /// Whether the method table of `searched` proves `method` is its only
    /// method of that name and arity, instance or `static`: javac considers
    /// both kinds when it resolves an instance call (JLS 15.12.2.1). A table
    /// that does not list `method` at all is taken as incomplete, proving
    /// nothing.
    ///
    /// The candidates are the signatures of the table's template, not the
    /// erased ones of the token: `m(T)` and `m(String)` are two methods of a
    /// `Box<T>` though both erase to `m(String)` in `Box<String>`. In the
    /// type that owns `method` the one candidate is the fact if it is the
    /// signature the fact declares; in a subtype, whose template lists the
    /// method in terms of its own type parameters, if it erases as the fact
    /// does.
    private boolean onlyCandidate(@Nullable TypeToken<?> searched, Invocable method) {
        if (!(searched instanceof DeclaredToken<?> declared)) {
            return false;
        }
        List<MethodTableTemplate.Signature> candidates =
                candidates(target.methods(declared.shape()), method).toList();
        if (candidates.size() != 1) {
            return false;
        }
        return declared.shape() == method.owner().shape()
                ? candidates.getFirst().equals(method.declared())
                : candidates.getFirst().instantiate(declared.argumentErasures()).equals(method.signature());
    }

    /// Whether a receiver type that is not the owner of `method` has
    /// another method that erases as `method` does: an overload the subtype
    /// adds, `m(String)` beside the `m(T)` it inherits from a `Base<String>`,
    /// or beside an inherited `<T> m(T)` called with `String` for `T`. Through
    /// the owner the subtype's overload is not a candidate.
    ///
    /// The method itself is among the subtype's methods under a signature
    /// in terms of the subtype's type parameters, which erases as it does
    /// unless a type parameter of the method decides its erasure.
    ///
    /// A type variable does not say what its bounds add: it may have such
    /// an overload of any method that takes arguments.
    private boolean hasTwin(TypeToken<?> searched, Invocable method) {
        DeclaredToken<?> owner = method.owner();
        return switch (searched) {
            case DeclaredToken<?> declared when declared.shape() == owner.shape() -> false;
            case DeclaredToken<?> declared -> {
                MethodSignature erased = method.signature();
                int itself =
                        method.declared().instantiate(owner.argumentErasures()).equals(erased) ? 1 : 0;
                yield candidates(target.methods(declared.shape()), method)
                                .filter(s -> s.instantiate(declared.argumentErasures())
                                        .equals(erased))
                                .count()
                        > itself;
            }
            case TypeVarToken<?> _ -> !method.params().isEmpty();
            case ArrayToken<?, ?> _, PrimitiveToken<?, ?, ?> _ -> false;
        };
    }

    /// The members of a type javac chooses among for a use of `member`: its
    /// methods of that name and arity, instance and `static`, or its
    /// constructors of that arity.
    private static Stream<MethodTableTemplate.Signature> candidates(MethodTableTemplate template, Invocable member) {
        Stream<MethodTableTemplate.Signature> members =
                member.kind() == InvocableKind.CONSTRUCTOR ? template.constructors().stream() : template.methods();
        return members.filter(s -> s.name().equals(member.name())
                && s.params().size() == member.params().size());
    }

    /// Rejects a fact whose owner has another member that erases as the fact
    /// does under the type arguments of the fact: `m(T)` and `m(String)` of
    /// a `Box<String>`, `<T> m(T)` with `String` for `T` and `m(String)`.
    /// Both take the arguments of the fact, cast or not, so javac reports
    /// an ambiguity or resolves the other one.
    ///
    /// A member of a type without type parameters that is declared as it
    /// erases has no such twin, and a table that does not list the fact
    /// proves nothing.
    ///
    /// @throws FactLookupException if the owner of `member` has such a member
    private void requireDistinct(Invocable member) {
        DeclaredToken<?> owner = member.owner();
        List<ClassDesc> arguments = owner.argumentErasures();
        MethodTableTemplate.Signature declared = member.declared();
        MethodSignature erased = member.signature();
        if (arguments.isEmpty() && declared.instantiate(arguments).equals(erased)) {
            return;
        }
        List<MethodTableTemplate.Signature> candidates =
                candidates(target.methods(owner.shape()), member).toList();
        if (!candidates.contains(declared)) {
            return;
        }
        List<String> twins = candidates.stream()
                .filter(s -> !s.equals(declared) && s.instantiate(arguments).equals(erased))
                .map(MethodTableTemplate.Signature::toString)
                .sorted()
                .toList();
        if (!twins.isEmpty()) {
            throw new FactLookupException(
                    "way to make javac resolve " + member + ": it is declared " + declared + " and is " + erased
                            + " here, as the overload declared " + String.join(", ", twins) + " is",
                    owner.toString(),
                    twins);
        }
    }

    /// The receiver of an instance field: cast to the owner of the field
    /// if it is of another type, so javac looks the field up in the owner —
    /// not in a subtype that hides it with a field of its own, or that has
    /// the name from two supertypes and so for neither (JLS 8.3, 8.3.3.3).
    private Expr fieldReceiver(Node.Operand receiving, FieldRef<?, ?> field) {
        uses(field);
        target.verify(receiving.type());
        DeclaredToken<?> owner = field.owner();
        if (!Tokens.sameType(receiving.type(), owner)) {
            return Exprs.cast(owner.typeRef(), expr(receiving.node()));
        }
        return expr(receiving.node(), true);
    }

    /// The raw type qualifying a static field: its owner, whatever type the
    /// generator reached the fact through, so no field of a subtype hides
    /// it and no other supertype of one makes the name ambiguous.
    private ClassTypeRef staticOwner(StaticFieldRef<?> field) {
        target.verify(field.owner());
        target.verify(field.type());
        return raw(field.owner());
    }

    /// A method or constructor fact the lowered code calls: the types of
    /// its signature are ones javac resolves the call by.
    private void uses(Invocable member) {
        Stream.of(
                        Stream.of(member.owner()),
                        member.params().stream(),
                        member.resultType().stream(),
                        member.traits().throwsTypes().stream(),
                        member.traits().typeArgs().stream())
                .<TypeToken<?>>flatMap(types -> types)
                .forEach(target::verify);
    }

    /// An instance field fact the lowered code reads or assigns.
    private void uses(FieldRef<?, ?> field) {
        target.verify(field.owner());
        target.verify(field.type());
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
        uses(sam);
        Deque<LoopCtl> enclosingLoops = loopStack;
        TypeToken<?> enclosingResult = result;
        loopStack = new ArrayDeque<>();
        result = sam.resultType().orElse(null);
        names.push();
        try {
            List<Param> coreParams = new ArrayList<>(params.size());
            for (Var<?> param : params) {
                coreParams.add(new Param(declare(param), param.type().typeRef()));
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
