package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.ArrayCreationExpr;
import me.supcheg.javafile.code.ArrayInitializer;
import me.supcheg.javafile.code.ArrayInitializerExpr;
import me.supcheg.javafile.code.AssertStmt;
import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.AssignTarget;
import me.supcheg.javafile.code.BinaryExpr;
import me.supcheg.javafile.code.BlockCaseBody;
import me.supcheg.javafile.code.BlockStmt;
import me.supcheg.javafile.code.BreakStmt;
import me.supcheg.javafile.code.CaseBody;
import me.supcheg.javafile.code.CaseLabel;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstantLabel;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.ConstructorInvocation;
import me.supcheg.javafile.code.ContinueStmt;
import me.supcheg.javafile.code.DefaultLabel;
import me.supcheg.javafile.code.DoWhileStmt;
import me.supcheg.javafile.code.EnhancedForStmt;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.ExprCaseBody;
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
import me.supcheg.javafile.code.SuperExpr;
import me.supcheg.javafile.code.SwitchCase;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.SynchronizedStmt;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.UnaryExpr;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.code.YieldStmt;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.InterfaceToken;
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

import java.lang.constant.ClassDesc;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The pure `typed → core` lowering pass (§7). Erases the phantom types and
/// the HOAS variable identities, translating [Node]/[Instr] into `core`
/// [Expr]/[Stmt].
///
/// Residual semantic checks that Java's type system cannot express (§9 —
/// `implement` completeness, modifier validity) are
/// the responsibility of the declaration layer that calls into this class,
/// not of this class itself; that a checked exception is caught or declared
/// (§9.1) and that a `switch` is exhaustive (§9.2) is checked before
/// lowering, where each statement and each `switch` is built
/// ([Exceptions], [EnumSwitchCases]);
/// this class performs the structural checks that are intrinsic to lowering
/// itself: a variable must be in scope where it is referenced (enforced by
/// the scope stack of [NameEnv]) and `break`/`continue` must target a loop
/// currently being lowered in the same lambda (enforced by the loop stack
/// below, which a lambda body starts afresh) — both would otherwise be
/// silently mis-rendered rather than caught. The builder already rejects
/// both when the statement is built ([ScopeCheck]); these are defence in
/// depth.
///
/// **A body is lowered where it is defined.** The members of a class are
/// all declared before the first body is built ([MemberBuilder],
/// [MemberBodies]), so the methods of the class — which decide the casts
/// below as those of any other type do — are known to the lowering of every
/// body, and `define` lowers the body it is given. What lowering rejects —
/// a `protected` member reached where it cannot be, an overload no argument
/// tells from the one meant, a type parameter out of its scope, a metamodel
/// that does not hold on the target — is so rejected by the call of the
/// generator that gave the body.
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
///   is refused, as a primitive returned of a reference result is: boxing is
///   never left implicit (§6.1), and the generator boxes by `box`.
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
///   added where javac would find it redundant. So is a call by the fact of
///   a supertype of a method the class being declared overrides with a
///   narrower result (JLS 8.4.5): `this.get()` is of the result of the class
///   to javac. The type matters, beside the above, in an operand of `==` and
///   `!=` (JLS 15.21.3), in the receiver of a method that takes arguments —
///   the narrower type may have an overload that erases as the method does —,
///   in an array an element of which is assigned (JLS 15.26.1), and in a
///   value thrown, which is checked by its token ([Exceptions]).
/// - **Witnesses.** An expression an [Upcast] witness was applied to is
///   rendered as it was: its token names a supertype, javac types it by the
///   class. Its receiver, argument and selector positions are judged by the
///   type before the witness, as those of any subtype of the owner are, and
///   where javac checks the type itself — the operand of a cast, of
///   `instanceof`, of `==`, a branch of a conditional — it is cast to the
///   supertype.
/// - **The `throws` clause.** An instance method that declares a checked
///   exception is called through its owner, `((Reader) stringReader).close()`:
///   the override of the receiver's type may declare fewer exceptions, and
///   javac would then reject the `catch` the fact asked for, or accept code
///   the typed layer was right to refuse. The upcast is never redundant to
///   javac.
/// - **Generic members.** A static member of a parameterized owner is
///   qualified by the raw type, `List.of()`, never `List<String>.of()`; the
///   type arguments of a generic method's fact
///   ([me.supcheg.javafile.facts.MemberTraits#typeArgs()]) are rendered as
///   explicit witnesses, `List.<String>of()`, `stream.<R>map(f)`, so javac
///   never infers others.
/// - **Lambdas.** A lambda has no type of its own to javac, only that of
///   its context (JLS 15.27.3), and a typed lambda may stand where there is
///   none — the receiver of a call, an operand of `==`, the initializer of
///   an `Object` — or where the context has another type than its fact, or
///   one that decides an overload. It is therefore rendered under a cast to
///   the functional interface of its fact, `(Function<String, Integer>)
///   (String v0) -> ...`, which is never redundant to javac, and without
///   one only where the target type is exactly that interface and nothing
///   else is in question: the initializer of a variable or a field of that
///   type, the value returned from a body of that result type, the
///   argument of the only candidate of a call whose parameter is of that
///   type. Its parameters are always typed explicitly.
/// - **`switch` expressions.** Every result of a `switch` is of the type
///   of the expression, cast to it where the typed expression is of another
///   ([Expressions#switch_]), so javac types the `switch` by that type and
///   never by promotion, boxing or a least upper bound (JLS 15.28.1).
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
    private final Place place;
    /// The names of the variables of the top-level body, which its lambdas share.
    private final NameEnv names;
    /// The labels of the loops of the top-level body, which its lambdas share: a label is unique in it.
    private final Labels labels;
    /// The loops of this body around the statement being lowered, the innermost first: a lambda
    /// body is lowered by a lowering of its own, which no loop around the lambda is one of.
    private final Deque<LoopCtl> loopStack = new ArrayDeque<>();

    /// The result type of the body being lowered, the target type of its
    /// `return` statements; empty for a `void` body or an expression.
    private final Optional<TypeToken<?>> result;

    /// The static initializer being lowered, if the code is one: the field it initializes, and the static
    /// fields of the class it may not read (JLS 8.3.3).
    private final Optional<Forward> forward;

    /// The labels of the loops a `break` or a `continue` of an inner loop names.
    private static final class Labels {
        private final Map<LoopCtl, String> names = new HashMap<>();
        private final Set<LoopCtl> used = new HashSet<>();
        private final Map<Label, String> labels = new HashMap<>();

        /// The label of `ctl`, the next free one the first time.
        String of(LoopCtl ctl) {
            return names.computeIfAbsent(ctl, _ -> "loop" + names.size());
        }

        /// The name of the label `label`, the next free one the first time.
        String of(Label label) {
            return labels.computeIfAbsent(label, _ -> "label" + labels.size());
        }
    }

    /// A lowering of a `void` body or of a standalone expression.
    ///
    /// @param target the classpath the lowered code is compiled against
    Lowering(TargetClasspath target) {
        this(target, new Place.Standalone(), Optional.empty());
    }

    /// A lowering of a body whose `return` statements return `result`.
    ///
    /// @param target the classpath the lowered code is compiled against
    /// @param place the class whose body the code is in, or that it is in none
    /// @param result the result type; empty for a `void` body
    /// @throws TargetClasspathMismatchException if a metamodel of `result` does not hold on `target`
    Lowering(TargetClasspath target, Place place, Optional<TypeToken<?>> result) {
        this(target, place, new NameEnv(), new Labels(), result, Optional.empty());
    }

    /// A lowering of the initializer of a static field: `field` is the field it initializes, and `later` the
    /// static fields of the class that are declared at or after it — itself among them —, which the initializer
    /// may not read (JLS 8.3.3).
    ///
    /// @param target the classpath the lowered code is compiled against
    /// @param place the class whose initializer the code is
    /// @param field the static field the code initializes
    /// @param later the static fields the code may not read, `field` and those declared after it
    /// @return the lowering
    static Lowering staticInitializer(
            TargetClasspath target, Place place, StaticFieldRef<?> field, List<StaticFieldRef<?>> later) {
        return new Lowering(
                target, place, new NameEnv(), new Labels(), Optional.empty(), Optional.of(new Forward(field, later)));
    }

    /// What a static initializer may not read: the field it initializes, and the static fields of the class that
    /// are declared at or after it, in the order of the declarations (JLS 8.3.3).
    ///
    /// @param field the field the initializer initializes
    /// @param later the fields it may not read, `field` first
    private record Forward(StaticFieldRef<?> field, List<StaticFieldRef<?>> later) {
        Forward {
            later = List.copyOf(later);
        }
    }

    /// The lowering of the body of a lambda of the body `enclosing` lowers, whose `return` statements
    /// return `result`: in its names and its labels, with no loop around it, and — the lambda being a body the
    /// initializer runs — in the static initializer `enclosing` lowers, if it does.
    private Lowering(Lowering enclosing, Optional<TypeToken<?>> result) {
        this(enclosing.target, enclosing.place, enclosing.names, enclosing.labels, result, enclosing.forward);
    }

    private Lowering(
            TargetClasspath target,
            Place place,
            NameEnv names,
            Labels labels,
            Optional<TypeToken<?>> result,
            Optional<Forward> forward) {
        this.target = target;
        this.place = place;
        this.names = names;
        this.labels = labels;
        this.result = result;
        this.forward = forward;
        result.ifPresent(this::mentions);
    }

    /// A type the lowered code mentions — writes, or reaches a member through: its type variables
    /// are in scope where the code stands ([TypeScope]), and its metamodels hold on the target.
    ///
    /// @throws IllegalStateException if a type parameter of a class is mentioned out of its scope
    /// @throws TargetClasspathMismatchException if a metamodel of `type` does not hold on the target classpath
    private void mentions(TypeToken<?> type) {
        written(type);
        target.verify(type);
    }

    /// A type the lowered code writes: its type variables are in scope where the code stands, and it
    /// mentions no raw type ([Tokens#requireNotRaw]).
    ///
    /// @return the type given
    /// @throws IllegalStateException if a type parameter of a class is written out of its scope, or the
    ///     type mentions a raw type
    private <T extends TypeToken<?>> T written(T type) {
        Tokens.requireNotRaw(type);
        switch (place) {
            case Place.Standalone _ -> new TypeScope.Standalone().require(type);
            case Place.InClass(var _, var scope, var _, var _) -> scope.require(type);
        }
        return type;
    }

    /// Declares a variable up front (e.g. a method parameter) so the body can
    /// reference it before lowering the body itself.
    String declareUpfront(Var<?> var) {
        return declare(var);
    }

    /// Declares a variable up front under a name the declaration gives it, as
    /// the components of a compact constructor are named.
    String declareUpfront(Var<?> var, String name) {
        mentions(var.type());
        return names.declare(var, name);
    }

    /// Declares a variable in the innermost scope: its type is one the
    /// lowered code names.
    private String declare(Var<?> var) {
        mentions(var.type());
        return names.declare(var);
    }

    /// A type the lowered code names outside of any statement, e.g. in a
    /// `throws` clause: verified as every other.
    ///
    /// @param type the type
    /// @param <T> the kind of token
    /// @return the type given, verified
    /// @throws TargetClasspathMismatchException if a metamodel of `type` does not hold on the target classpath
    <T extends TypeToken<?>> T named(T type) {
        mentions(type);
        return type;
    }

    /// Lowers a single expression outside statement context.
    Expr lowerExpr(Node node) {
        return expr(node);
    }

    /// Lowers the initializer of a variable or field declared of type
    /// `declared`, where the diamond may stand.
    VariableInitializer lowerInitializer(Node node, TypeToken<?> declared) {
        mentions(declared);
        return declared(node, declared);
    }

    /// Lowers a sequence of statements into a `core` method/constructor/loop
    /// body.
    CodeBody lowerBlock(List<Instr> instrs) {
        names.push();
        try {
            // in the order of the code: a statement declares the names the ones after it see
            return new CodeBody(instrs.stream().map(this::stmt).toList());
        } finally {
            names.pop();
        }
    }

    /// Lowers the body of a constructor that begins with no explicit invocation: javac calls `super()`.
    ConstructorBody lowerConstructor(List<Instr> instrs) {
        return new ConstructorBody.Implicit(lowerBlock(instrs));
    }

    /// Lowers the body of a constructor that begins with `super(args)`, and the statements after it.
    ConstructorBody lowerConstructor(SuperInvocation invocation, List<Instr> instrs) {
        // Every argument is of exactly its parameter's type, as those of `new` are.
        return new ConstructorBody.Explicit(
                CodeBody.EMPTY,
                new ConstructorInvocation.Super(arguments(
                        invocation.ctor(), new Searched.Unconsulted(), invocation.args(), invocation.passing())),
                lowerBlock(instrs));
    }

    // ------------------------------------------------------------------
    // Statements
    // ------------------------------------------------------------------

    private Stmt stmt(Instr instr) {
        return switch (instr) {
            case Instr.Let(var v, var init) -> {
                VariableInitializer initExpr = declared(init, v.type());
                String name = declare(v);
                yield new LocalVarDeclStmt.Typed(written(v.type()).typeRef(), name, Optional.of(initExpr));
            }
            case Instr.Assert(var condition, var message) -> new AssertStmt(expr(condition), message.map(this::expr));
            case Instr.Synchronized(var lock, var body) -> new SynchronizedStmt(expr(lock), lowerBlock(body.instrs()));
            case Instr.Labeled(var label, var body) ->
                new LabeledStmt(labels.of(label), new BlockStmt(lowerBlock(body.instrs())));
            case Instr.Exec(var effect) -> effectStmt(effect);
            case Instr.If(var condition, var then, var otherwise) ->
                new IfStmt(expr(condition), lowerBlock(then.instrs()), List.of(), lowerOptional(otherwise));
            case Instr.IfInstance(var operand, var type, var binding, var then, var otherwise) -> {
                Expr operandExpr = expr(operand, Typing.EXACT);
                // The binding is in scope in the then-branch only.
                names.push();
                Expr condition;
                CodeBody thenCode;
                try {
                    mentions(type);
                    condition = operandExpr.instanceOf(written(type).typeRef(), declare(binding));
                    thenCode = lowerBlock(then.instrs());
                } finally {
                    names.pop();
                }
                yield new IfStmt(condition, thenCode, List.of(), lowerOptional(otherwise));
            }
            case Instr.While(var ctl, var label, var condition, var body) -> {
                Expr conditionExpr = expr(condition);
                CodeBody bodyCode = withLoop(ctl, body);
                yield labeled(label, ctl, new WhileStmt(conditionExpr, bodyCode));
            }
            case Instr.DoWhile(var ctl, var label, var body, var condition) -> {
                CodeBody bodyCode = withLoop(ctl, body);
                yield labeled(label, ctl, new DoWhileStmt(bodyCode, expr(condition)));
            }
            case Instr.For(var ctl, var label, var var_, var init, var condition, var update, var body) -> {
                VariableInitializer initExpr = declared(init, var_.type());
                // The loop variable is in scope in the condition, the update, and the body.
                names.push();
                LocalVarDeclStmt.Typed initStmt;
                Expr conditionExpr;
                Stmt updateStmt;
                CodeBody bodyCode;
                try {
                    initStmt = new LocalVarDeclStmt.Typed(
                            written(var_.type()).typeRef(), declare(var_), Optional.of(initExpr));
                    conditionExpr = expr(condition);
                    updateStmt = effectStmt(update);
                    bodyCode = withLoop(ctl, body);
                } finally {
                    names.pop();
                }
                yield labeled(
                        label,
                        ctl,
                        new ForStmt(
                                Optional.of(initStmt), Optional.of(conditionExpr), Optional.of(updateStmt), bodyCode));
            }
            case Instr.ForEach(var ctl, var label, var var_, var iterated, var body) -> {
                // javac takes the type of the iterated expression itself (JLS 14.14.2): one seen
                // through a witness is of the supertype the witness names. An array is iterated as it
                // stands: its elements are assigned to the variable of the component, whatever javac types them.
                Expr iterableExpr =
                        switch (iterated) {
                            case Instr.Iterated.OfIterable(var iterable) ->
                                expr(iterable, Typing.exactIf(iterable instanceof Node.Upcast));
                            case Instr.Iterated.OfArray(var array) -> expr(array);
                        };
                names.push();
                String name;
                CodeBody bodyCode;
                try {
                    name = declare(var_);
                    bodyCode = withLoop(ctl, body);
                } finally {
                    names.pop();
                }
                yield labeled(
                        label, ctl, new EnhancedForStmt(written(var_.type()).typeRef(), name, iterableExpr, bodyCode));
            }
            case Instr.Return(var value) -> {
                // the result type of the body is known here alone: a primitive value of a reference one would box
                result.ifPresent(declared -> Tokens.requireNoBoxing(value.type(), declared, "return_"));
                yield new ReturnStmt(Optional.of(returned(value.node())));
            }
            case Instr.ReturnNothing _ -> new ReturnStmt(Optional.empty());
            // The value is of the type of the switch already (YieldBody.yield_); pinned, it is exactly.
            case Instr.Yield(var value) -> new YieldStmt(expr(value, Typing.EXACT));
            case Instr.Throw(var value, var type) -> {
                mentions(type.token());
                // The statement was checked by the type of the value (Exceptions): a value seen
                // through a witness by the class it is of, as javac types it; any other javac may
                // type narrower by exactly its token, or a catch of another subclass would be one
                // of an exception javac says is never thrown (JLS 11.2.3).
                yield new ThrowStmt(expr(value, Typing.exactIf(!(value instanceof Node.Upcast))));
            }
            case Instr.Break(var ctl) -> new BreakStmt(label(ctl));
            case Instr.Continue(var ctl) -> new ContinueStmt(label(ctl));
            case Instr.BreakLabel(var label) -> new BreakStmt(Optional.of(labels.of(label)));
            case Instr.ContinueLabel(var label) -> new ContinueStmt(Optional.of(labels.of(label)));
            case Instr.Try(var body, var resources, var catches, var finallyBlock) ->
                tryStmt(body, resources, catches, finallyBlock);
            case Instr.Raw(var s) -> s;
        };
    }

    private Optional<CodeBody> lowerOptional(Instr.Otherwise otherwise) {
        return otherwise.blocks().findFirst().map(block -> lowerBlock(block.instrs()));
    }

    private CodeBody withLoop(LoopCtl ctl, Block<?, ?> body) {
        loopStack.push(ctl);
        try {
            return lowerBlock(body.instrs());
        } finally {
            loopStack.pop();
        }
    }

    private Stmt labeled(Optional<Label> explicit, LoopCtl ctl, Stmt loopStmt) {
        Stmt byControl = labels.used.contains(ctl) ? new LabeledStmt(labels.of(ctl), loopStmt) : loopStmt;
        return explicit.map(label -> (Stmt) new LabeledStmt(labels.of(label), byControl))
                .orElse(byControl);
    }

    /// The label of a `break` or a `continue` of `ctl`: none for the innermost loop, the label of an
    /// outer one, which it is then given.
    private Optional<String> label(LoopCtl ctl) {
        if (!loopStack.contains(ctl)) {
            throw new IllegalStateException(
                    "break/continue used for a loop that is not currently being lowered: the LoopCtl escaped its"
                            + " loop's scope, e.g. by being captured into a lambda that outlives the loop");
        }
        if (loopStack.peek() == ctl) {
            return Optional.empty();
        }
        labels.used.add(ctl);
        return Optional.of(labels.of(ctl));
    }

    private Stmt tryStmt(
            Block<?, ?> body, List<Instr.Resource> resources, List<Instr.Catch> catches, Instr.Finally finallyBlock) {
        names.push();
        try {
            // In declaration order: a resource is in scope in the initializers after it and in the body.
            List<me.supcheg.javafile.code.Resource> coreResources =
                    resources.stream().map(this::resource).toList();
            CodeBody bodyCode = lowerBlock(body.instrs());
            List<CatchClause> coreCatches =
                    catches.stream().map(this::catchClause).toList();
            return switch (finallyBlock) {
                case Instr.Finally.Of(var block) ->
                    new TryStmt.WithFinally(coreResources, bodyCode, coreCatches, lowerBlock(block.instrs()));
                // A try without a finally block has a catch clause (CatchClauses.close).
                case Instr.Finally.None _ ->
                    new TryStmt.CatchOnly(coreResources, bodyCode, NonEmptyList.copyOf(coreCatches));
            };
        } finally {
            names.pop();
        }
    }

    private me.supcheg.javafile.code.Resource resource(Instr.Resource resource) {
        if (resource.init().isPresent()) {
            Expr init = initializer(resource.init().get(), resource.var().type());
            String name = declare(resource.var());
            return new me.supcheg.javafile.code.Resource.Declared(
                    Optional.of(written(resource.var().type()).typeRef()), name, init);
        }
        return new me.supcheg.javafile.code.Resource.Existing(names.nameOf(resource.var()));
    }

    private CatchClause catchClause(Instr.Catch clause) {
        names.push();
        try {
            mentions(clause.type());
            String name = declare(clause.var());
            CodeBody catchBody = lowerBlock(clause.body().instrs());
            return new CatchClause(
                    NonEmptyList.copyOf(List.of(written(clause.type()).typeRef())), name, catchBody);
        } finally {
            names.pop();
        }
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
                        localStatic(field.owner(), field.name())
                                ? new FieldAccessExpr(Optional.empty(), field.name())
                                : new StaticFieldAccessExpr(staticOwner(field), field.name());
                    // An array javac types narrower than its token takes fewer values (JLS 15.26.1).
                    case Node.Target.Element(var array, var index) ->
                        expr(array, Typing.EXACT).arrayAccess(expr(index));
                };
        return new AssignStmt(assigned, AssignOp.ASSIGN, value);
    }

    // ------------------------------------------------------------------
    // Expressions
    // ------------------------------------------------------------------

    private Expr expr(Node node) {
        return expr(node, Typing.AS_IT_STANDS);
    }

    /// Lowers `node`; [Typing#EXACT], so that javac types it by exactly its token
    /// even where [Node#isExact(Node)] does not hold.
    private Expr expr(Node node, Typing typing) {
        return switch (node) {
            case Node.Lit(var literal) -> literal;
            case Node.TextBlock(var value) -> Exprs.textBlock(value);
            case Node.Null(var type) -> {
                mentions(type);
                yield Exprs.cast(written(type).typeRef(), Exprs.literalNull());
            }
            case Node.Local(var v) -> new FieldAccessExpr(Optional.empty(), names.nameOf(v));
            case Node.This ignored -> new ThisExpr();
            case Node.OuterThis(var outer) -> {
                mentions(outer);
                yield new me.supcheg.javafile.code.QualifiedThisExpr(outer.erasure());
            }
            case Node.InnerNew(var enclosing, var ctor, var args, var passing) ->
                new me.supcheg.javafile.code.InnerNewExpr(
                        expr(enclosing.node()),
                        new me.supcheg.javafile.code.TypedNewTarget(
                                written(ctor.owner()).typeRef()),
                        arguments(ctor, new Searched.Unconsulted(), args, passing));
            case Node.Box(var type, var operand) ->
                Exprs.staticCall(type.boxed().typeRef(), "valueOf", expr(operand));
            case Node.Unbox(var type, var operand) -> expr(operand).call(type.unboxMethodName());
            case Node.Call(var receiver, var method, var args, var passing) ->
                call(receiver, method, args, passing, typing);
            case Node.SuperCall(var _, var superclass, var method, var args, var passing) ->
                superCall(superclass, method, args, passing);
            case Node.StaticCall(var method, var args, var passing) -> {
                subclassReaching(method.access(), method.owner(), method);
                var lowered = arguments(method, new Searched.Unconsulted(), args, passing);
                yield localStatic(method.owner(), method.name())
                        ? Exprs.call(method.name(), lowered)
                        : new StaticMethodCallExpr(raw(method.owner()), method.name(), lowered, typeArgs(method));
            }
            case Node.New(var ctor, var args, var passing) -> {
                subclassReaching(ctor.access(), ctor.owner(), ctor).ifPresent(subclass -> {
                    throw new IllegalStateException(ctor + " is protected: " + subclass + " calls it by the"
                            + " super(...) of its constructor alone, a class instance creation is no access a"
                            + " subclass has (JLS 6.6.2.2)");
                });
                yield Exprs.new_(
                        written(ctor.owner()).typeRef(), arguments(ctor, new Searched.Unconsulted(), args, passing));
            }
            case Node.FieldGet(var receiver, var field) ->
                fieldReceiver(receiver, field).field(field.name());
            case Node.StaticFieldGet(var field) -> {
                ClassTypeRef owner = staticOwner(field);
                yield localStatic(field.owner(), field.name())
                        ? new FieldAccessExpr(Optional.empty(), field.name())
                        : Exprs.staticField(owner, field.name());
            }
            case Node.EnumConst(var constant) -> {
                mentions(constant.owner());
                yield Exprs.staticField(raw(constant.owner()), constant.name());
            }
            case Node.ArrayAt(var array, var index) -> expr(array, typing).arrayAccess(expr(index));
            case Node.ArrayLength(var array) -> expr(array).field("length");
            case Node.ArrayClone(var array) -> expr(array, typing).call("clone");
            case Node.NewArray(var component, var lengths) -> {
                mentions(component);
                yield new ArrayCreationExpr(
                        written(component).typeRef(),
                        new NonEmptyList<>(
                                expr(lengths.head()),
                                lengths.tail().stream().map(this::expr).toList()));
            }
            case Node.NewArrayOf array -> {
                mentions(array.type());
                yield new ArrayInitializerExpr(written(array.type().component()).typeRef(), arrayInitializer(array));
            }
            // Each branch is of the conditional's type already (Expressions.cond); pinned, the
            // conditional is of exactly that type (JLS 15.25).
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                Exprs.cond(expr(condition), expr(whenTrue, Typing.EXACT), expr(whenFalse, Typing.EXACT));
            case Node.Binary(var op, var left, var right) ->
                new BinaryExpr(compared(op, left), op.rendered(), compared(op, right));
            case Node.Not(var operand) -> new UnaryExpr(UnaryOp.NOT, expr(operand));
            case Node.Convert(var conversion, var operand) ->
                Exprs.cast(conversion.type().typeRef(), expr(operand, Typing.EXACT));
            case Node.Cast(var type, var operand) -> {
                mentions(type);
                requireChecked(type, operand, "the cast");
                yield Exprs.cast(written(type).typeRef(), expr(operand.node(), Typing.EXACT));
            }
            // Unpinned, the expression is rendered as it is and javac types it by the class it is of;
            // pinned, the cast makes it of the supertype, and is no redundant one to javac.
            case Node.Upcast(var inner, var _, var type) -> {
                mentions(type);
                yield switch (typing) {
                    case EXACT -> Exprs.cast(written(type).typeRef(), expr(inner));
                    case AS_IT_STANDS -> expr(inner);
                };
            }
            // No target type is known here: the cast gives the lambda the one of its fact.
            case Node.Lambda lambda -> Exprs.cast(written(lambda.sam().owner()).typeRef(), lambda(lambda));
            // A method reference has no type of its own either: the cast gives it the one of its fact.
            case Node.MethodRef methodRef ->
                Exprs.cast(written(methodRef.sam().owner()).typeRef(), methodRef(methodRef));
            case Node.Switch switch_ -> switchExpr(switch_);
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
        return result.map(declared -> initializer(value, declared)).orElseGet(() -> expr(value));
    }

    /// An expression whose target type is `declared`, in an assignment
    /// context: the diamond infers exactly `declared` there (JLS 15.9.1),
    /// and a lambda is of `declared` (JLS 15.27.3).
    private Expr initializer(Node node, TypeToken<?> declared) {
        if (node instanceof Node.New(var ctor, var args, var passing)
                && ctor.owner().typeRef() instanceof ParameterizedTypeRef
                && Tokens.sameType(ctor.owner(), declared)) {
            return Exprs.newDiamond(ctor.owner().erasure(), arguments(ctor, new Searched.Unconsulted(), args, passing));
        }
        return targeted(node, declared);
    }

    /// The initializer of a local variable or a field declared of type `declared`: the `{e1, e2}` of an
    /// array creation of exactly that type, whose type the declaration tells (JLS 10.6), and
    /// [#initializer] of any other node.
    private VariableInitializer declared(Node node, TypeToken<?> declared) {
        return node instanceof Node.NewArrayOf array && Tokens.sameType(array.type(), declared)
                ? arrayInitializer(array)
                : initializer(node, declared);
    }

    /// The `{e1, e2}` of an array creation, whose type is known where it stands: an element that is
    /// an array creation of the component type is the `{...}` of that array in turn.
    private ArrayInitializer arrayInitializer(Node.NewArrayOf array) {
        TypeToken<?> component = array.type().component();
        return new ArrayInitializer(array.elements().stream()
                .<VariableInitializer>map(element ->
                        element instanceof Node.NewArrayOf nested && Tokens.sameType(nested.type(), component)
                                ? arrayInitializer(nested)
                                : targeted(element, component))
                .toList());
    }

    /// An expression in an assignment or invocation context whose target
    /// type is `targetType`, there being no other type javac could take the
    /// context for: a lambda of exactly that type stands as it is, where a
    /// cast to it would be redundant.
    private Expr targeted(Node node, TypeToken<?> targetType) {
        return switch (node) {
            case Node.Lambda lambda when Tokens.sameType(lambda.sam().owner(), targetType) -> lambda(lambda);
            default -> expr(node);
        };
    }

    /// An operand as javac sees it: the expression an [Upcast] witness was applied to, of the type it
    /// was of before. The witness changes the token and nothing in the rendered code, so the members
    /// javac searches for `this.m(x)` are those of the class, whatever supertype the token names: the
    /// receiver of a call or a field and the selector of a `switch` are judged by the type before
    /// the witness, as any receiver of a subtype of the owner is.
    static Node.Operand seen(Node.Operand operand) {
        return operand.node() instanceof Node.Upcast(var inner, var innerType, var _)
                ? seen(new Node.Operand(inner, innerType))
                : operand;
    }

    /// An operand of a binary operator. javac rejects `==` and `!=` of two references neither of
    /// which can be cast to the other (JLS 15.21.3), and the typed layer went by the tokens: an
    /// expression an [Upcast] witness was applied to is compared as of the supertype, and so is any
    /// other javac may type narrower than its token ([Node#isExact(Node)]) — a call of a method the
    /// type of the receiver overrides with a narrower result.
    private Expr compared(Node.Operator op, Node operand) {
        boolean ofReferences = op == Node.Operator.EQ_REF || op == Node.Operator.NEQ_REF;
        return expr(operand, Typing.exactIf(ofReferences && !Node.isExact(operand)));
    }

    /// `target.method(args)`. The receiver is cast to the method's owner
    /// where the receiver is of a subtype and the call must be pinned, or
    /// the method declares a checked exception
    /// ([#declaresCheckedException]), or the subtype has an overload that
    /// erases as the method does ([#hasTwin]); otherwise it is pinned itself
    /// where its type decides the overload.
    private Expr call(
            Node.Operand witnessed, Invocable method, List<Node.Operand> args, Node.Passing passing, Typing typing) {
        Node.Operand receiving = seen(witnessed);
        DeclaredToken<?> owner = method.owner();
        mentions(receiving.type());
        Optional<ClassToken<?>> subclass = subclassReaching(method.access(), owner, method);
        if (subclass.isPresent()) {
            return protectedCall(subclass.get(), receiving, method, args, passing, typing);
        }
        Expr receiver;
        Searched searched;
        if (!Tokens.sameType(receiving.type(), owner)
                && (typing == Typing.EXACT || declaresCheckedException(method) || hasTwin(receiving.type(), method))) {
            receiver = cast(owner, receiving, method);
            searched = new Searched.In(owner);
        } else {
            boolean settled = IntStream.range(0, args.size())
                    .allMatch(i -> settled(args.get(i), Node.parameter(method, passing, i)));
            // A receiver javac may type narrower than its token is of a type whose methods are not
            // known here: it may have an overload that erases as the method does, as a type
            // variable may (hasTwin), which no method without parameters has.
            boolean narrowed = !args.isEmpty() && !Node.isExact(receiving.node());
            receiver = expr(receiving.node(), Typing.exactIf(typing == Typing.EXACT || !settled || narrowed));
            searched = new Searched.In(receiving.type());
        }
        return new MethodCallExpr(
                Optional.of(receiver), method.name(), arguments(method, searched, args, passing), typeArgs(method));
    }

    /// `receiver.method(args)` of a `protected` method, in the body of `subclass`: the receiver is
    /// of the class itself (JLS 6.6.2.1) and stays so — cast to the owner of the method it would
    /// be an expression the class has no access to the member through. So where [#call] would
    /// cast the receiver, the call is rejected.
    private Expr protectedCall(
            ClassToken<?> subclass,
            Node.Operand receiving,
            Invocable method,
            List<Node.Operand> args,
            Node.Passing passing,
            Typing typing) {
        if (!Tokens.sameType(receiving.type(), subclass)) {
            throw new IllegalStateException(method + " is protected, and is called on an expression of "
                    + receiving.type() + ": in the body of " + subclass + " a protected method of a superclass is"
                    + " called on this or on another value of the class itself (JLS 6.6.2.1)");
        }
        castWanted(subclass, method, typing).ifPresent(why -> {
            throw new IllegalStateException(method + " is protected, and cannot be called on a value of " + subclass
                    + " here: " + why + ". The receiver cannot be cast to " + method.owner()
                    + ", through which the class has no access to the member (JLS 6.6.2.1)");
        });
        return new MethodCallExpr(
                Optional.of(expr(receiving.node(), Typing.EXACT)),
                method.name(),
                arguments(method, new Searched.In(subclass), args, passing),
                typeArgs(method));
    }

    /// Why a call of `method` on a value of `subclass` would have its receiver cast to the owner of
    /// the method ([#call]), if it would.
    private Optional<String> castWanted(ClassToken<?> subclass, Invocable method, Typing typing) {
        if (typing == Typing.EXACT) {
            return Optional.of("its result has to be of exactly its type where it stands, which only a receiver"
                    + " of the type of its owner makes it — bind the result to a variable with let first");
        }
        if (declaresCheckedException(method)) {
            return Optional.of("it declares a checked exception, of which the method the class has under its"
                    + " signature may declare fewer (JLS 8.4.8.3) — call it as super." + method.name()
                    + "(...) by superCall, or override it and call the override by its own fact");
        }
        return hasTwin(subclass, method)
                ? Optional.of("the class has another method that erases as it does, which javac would take for"
                        + " it — "
                        + "a generic method of a superclass is not called in a class that has such an overload")
                : Optional.empty();
    }

    /// `super.method(args)`: javac searches the methods of the superclass, and no cast of the
    /// receiver is there to tell an overload of it that erases as the method does.
    private Expr superCall(
            DeclaredToken<?> superclass, Invocable method, List<Node.Operand> args, Node.Passing passing) {
        mentions(superclass);
        subclassReaching(method.access(), method.owner(), method);
        if (hasTwin(superclass, method)) {
            throw new IllegalStateException("super." + method.name() + "(...) cannot name " + method + ": "
                    + superclass + " has another method that erases as it does, which javac would take for it,"
                    + " and super cannot be cast to " + method.owner());
        }
        return new MethodCallExpr(
                Optional.of(new SuperExpr()),
                method.name(),
                arguments(method, new Searched.In(superclass), args, passing),
                typeArgs(method));
    }

    /// The class in whose body a member of `access` of `owner` is reached, if the member is
    /// `protected`: a subclass of the owner, and nothing else reaches it (JLS 6.6.2).
    ///
    /// @return the class being declared, for a `protected` member; empty for a `public` one
    /// @throws IllegalStateException if a `protected` member is reached outside the body of a subclass of
    ///     its owner
    private Optional<ClassToken<?>> subclassReaching(Access access, DeclaredToken<?> owner, Object member) {
        return switch (access) {
            case PUBLIC -> Optional.empty();
            case PROTECTED ->
                switch (place) {
                    case Place.Standalone _ ->
                        throw new IllegalStateException(member + " is protected: it is reached in the body of a"
                                + " subclass of " + owner + " alone (JLS 6.6.2), and this code is in the body of"
                                + " no class");
                    case Place.InClass(var self, var _, var _, var _) -> {
                        if (!isSubclassOf(self, owner.erasure())) {
                            throw new IllegalStateException(member + " is protected: it is reached in the body"
                                    + " of a subclass of " + owner + " alone (JLS 6.6.2), and " + self
                                    + " is none — the fact was taken out for another class");
                        }
                        if (!(self instanceof ClassToken<?> cls)) {
                            throw new IllegalStateException(member + " is protected: it is reached in the body"
                                    + " of a class, and " + self + " is an interface");
                        }
                        yield Optional.of(cls);
                    }
                };
        };
    }

    /// Whether a type is `type` or a subtype of it: a class by its superclass chain, a declared
    /// interface by the type itself alone, which is the one the typed layer declares its body in.
    private static boolean isSubclassOf(DeclaredToken<?> self, ClassDesc type) {
        return switch (self) {
            case ClassToken<?> cls -> cls.isSubclassOf(type);
            case InterfaceToken<?> iface -> iface.erasure().equals(type);
        };
    }

    /// Whether the `throws` clause of `method` has a checked exception. A
    /// subtype may override the method with a narrower clause (JLS 8.4.8.3),
    /// and javac goes by the method of the receiver's static type: through
    /// a receiver cast to the owner it goes by the clause of the fact, which
    /// is the one the statement was checked by ([Exceptions]).
    private static boolean declaresCheckedException(Invocable method) {
        return ExceptionType.ofAll(method.traits().throwsTypes()).stream().anyMatch(ExceptionType::isChecked);
    }

    /// The arguments of `member` as `passing` hands them to its parameters.
    ///
    /// @throws FactLookupException if no arguments make javac resolve `member`, see [#requireDistinct]
    /// @throws IllegalStateException if the arguments are the elements of the array of the last parameter, and
    ///     javac may take another method for them while the array that tells it cannot be written
    private List<Expr> arguments(Invocable member, Searched searched, List<Node.Operand> args, Node.Passing passing) {
        return switch (passing) {
            case Node.Passing.Each _ -> each(member, searched, args);
            case Node.Passing.Spread(var type) -> spread(member, searched, args, type);
        };
    }

    /// The arguments of a call that gives the elements of the array of the last parameter of variable arity, one by
    /// one: written so if javac resolves the call to the member by them (JLS 15.12.2, where a method applicable
    /// without variable arity is chosen first), else as the array of the arguments, which pins the member.
    private List<Expr> spread(Invocable member, Searched searched, List<Node.Operand> args, ArrayToken<?, ?> type) {
        uses(member);
        Node.Passing passing = new Node.Passing.Spread(type);
        List<TypeToken<?>> types = IntStream.range(0, args.size())
                .<TypeToken<?>>mapToObj(i -> Node.parameter(member, passing, i))
                .toList();
        return switch (rivalry(member, searched, types)) {
            case Rivalry.Alone _ -> given(member, args, type, Pinning.AS_THEY_ARE);
            case Rivalry.Outranked _ -> given(member, args, type, Pinning.BY_CASTS);
            case Rivalry.Unproven(var why) -> explicit(member, searched, args, type, why);
        };
    }

    /// `m(a, e1, e2)`: javac takes the fact for these arguments, so they are given as they are, to the parameter, or
    /// the element type, they are passed to; where another method of the name takes as many arguments and none of
    /// them applies to arguments of exactly those types, [Pinning#BY_CASTS] makes every argument of another type a
    /// cast to the type, so that no other is applicable to the arguments and javac takes the fact (JLS 15.12.2.5).
    ///
    /// A lone element is pinned to its token either way. An element that is an array of the element type is one
    /// element, which javac would take for the array of the parameter: `m((Object) strings)` for `m(Object...)`.
    /// A lone element whose token is not an array may be of one to javac — a call of a method a subtype overrides
    /// with an array result — and the pinning makes javac type it by its token, so it stays one element.
    private List<Expr> given(Invocable member, List<Node.Operand> args, ArrayToken<?, ?> type, Pinning pinning) {
        args.forEach(arg -> mentions(arg.type()));
        requireDistinct(member);
        Node.Passing passing = new Node.Passing.Spread(type);
        TypeToken<?> component = type.component();
        int leading = member.params().size() - 1;
        return IntStream.range(0, args.size())
                .mapToObj(i -> {
                    Node.Operand arg = args.get(i);
                    TypeToken<?> param = Node.parameter(member, passing, i);
                    requireNoBoxing(member, param, arg);
                    boolean lone = args.size() == leading + 1 && i == leading;
                    if (lone && arg.type() instanceof ArrayToken<?, ?> && !Tokens.sameType(arg.type(), component)) {
                        return cast(param, arg, member);
                    }
                    if (pinning == Pinning.BY_CASTS && !Tokens.sameType(arg.type(), param)) {
                        return cast(param, arg, member);
                    }
                    return lone || pinning == Pinning.BY_CASTS
                            ? expr(arg.node(), Typing.EXACT)
                            : targeted(arg.node(), param);
                })
                .toList();
    }

    /// `m(a, new E[] {e1, e2})`: javac may take another method for the arguments, and the array tells which is
    /// meant. An array of an element type that is not reifiable cannot be written.
    private List<Expr> explicit(
            Invocable member, Searched searched, List<Node.Operand> args, ArrayToken<?, ?> type, String why) {
        int leading = member.params().size() - 1;
        if (!Tokens.isReifiable(type.component())) {
            throw new IllegalStateException("cannot call " + member + " with these " + (args.size() - leading)
                    + " arguments spread over its parameter of variable arity: " + why + " (JLS 15.12.2: a method"
                    + " applicable without variable arity is chosen first), and the array of " + type.component()
                    + " cannot be created to pass it explicitly (generic array creation, JLS 15.10.1); call the fact"
                    + " of the method javac takes, or give the arguments another way");
        }
        Node.Operand array = new Node.Operand(
                new Node.NewArrayOf(
                        type,
                        args.subList(leading, args.size()).stream()
                                .map(Node.Operand::node)
                                .toList()),
                type);
        return each(
                member,
                searched,
                Stream.concat(args.subList(0, leading).stream(), Stream.of(array))
                        .toList());
    }

    /// Whether javac could take another method than `member` for a call of `argc` arguments, as far as the facts
    /// of the type searched tell it.
    private Rivalry rivalry(Invocable member, Searched searched, List<TypeToken<?>> types) {
        return switch (searched) {
            case Searched.In(DeclaredToken<?> declared) -> rivalry(member, declared, types);
            case Searched.In(var other) -> new Rivalry.Unproven("the methods of " + other + " are not known");
            case Searched.Unconsulted _ -> rivalry(member, member.owner(), types);
        };
    }

    /// Every other member of the name that javac may apply to `argc` arguments is a rival: one that takes that many
    /// parameters, and one whose last parameter is of variable arity, which the template tells. The facts of
    /// `searched` must list the method of `member`'s signature and form, or its members are not known: a type that
    /// declares the array where the fact is of variable arity reads the arguments of a call differently, and the
    /// call is written as the array of the arguments, or refused where no array can be written.
    private Rivalry rivalry(Invocable member, DeclaredToken<?> searched, List<TypeToken<?>> types) {
        List<ClassDesc> erasures = searched.argumentErasures();
        MethodTableTemplate template = target.methods(searched.shape());
        List<MethodTableTemplate.Signature> named = named(template, member).toList();
        Predicate<MethodTableTemplate.Signature> isMember =
                signature -> isMember(signature, member, searched, template, erasures);
        if (named.stream().noneMatch(isMember)) {
            return new Rivalry.Unproven(notListed(member, searched, named, erasures));
        }
        List<MethodTableTemplate.Signature> rivals = named.stream()
                .filter(isMember.negate())
                .filter(signature -> mayTake(signature, template, erasures, types.size()))
                .toList();
        if (rivals.isEmpty()) {
            return new Rivalry.Alone();
        }
        List<String> applicable = rivals.stream()
                .filter(signature ->
                        Rivals.mayApply(signature.instantiate(erasures).params(), types))
                .map(MethodTableTemplate.Signature::toString)
                .sorted()
                .toList();
        return applicable.isEmpty()
                ? new Rivalry.Outranked()
                : new Rivalry.Unproven("javac may take " + String.join(", ", applicable) + " for the call");
    }

    /// Whether `signature` is the signature of `member` in `searched`, of the same form.
    private static boolean isMember(
            MethodTableTemplate.Signature signature,
            Invocable member,
            DeclaredToken<?> searched,
            MethodTableTemplate template,
            List<ClassDesc> erasures) {
        boolean same;
        if (searched.shape() == member.owner().shape()) {
            same = signature.equals(member.declared());
        } else {
            same = signature
                    .instantiate(erasures)
                    .params()
                    .equals(member.signature().params());
        }
        return same && sameForm(template.isVariableArity(signature), member.arity());
    }

    /// Why the facts of `searched` do not list `member`: no method of its signature, or one of another form.
    private static String notListed(
            Invocable member,
            DeclaredToken<?> searched,
            List<MethodTableTemplate.Signature> named,
            List<ClassDesc> erasures) {
        return named.stream()
                .filter(signature -> signature
                        .instantiate(erasures)
                        .params()
                        .equals(member.signature().params()))
                .findFirst()
                .map(signature -> searched + " declares " + signature + " with the last parameter of fixed arity,"
                        + " not of variable arity as " + member + " is: javac reads the arguments of such a call"
                        + " as those of the parameters alone (JLS 15.12.2)")
                .orElseGet(() -> "the methods of " + searched + " do not list " + member);
    }

    /// Whether the form of a signature — whether it is of variable arity — is that of `arity`.
    private static boolean sameForm(boolean variable, Arity arity) {
        return variable == (arity instanceof Arity.Variable);
    }

    /// Whether javac may apply the method of `signature` to `argc` arguments: as one of that many parameters, or
    /// as one of variable arity if its last parameter is of variable arity.
    private static boolean mayTake(
            MethodTableTemplate.Signature signature, MethodTableTemplate template, List<ClassDesc> erasures, int argc) {
        List<ClassDesc> params = signature.instantiate(erasures).params();
        return params.size() == argc || (template.isVariableArity(signature) && params.size() - 1 <= argc);
    }

    /// Whether javac may take another method than the fact for a call that spreads its arguments.
    private sealed interface Rivalry {
        /// The facts of the type searched list the fact and no other member of its name that javac could apply.
        record Alone() implements Rivalry {}

        /// Other members of the name take that many arguments, none of which can be applied to arguments of the types
        /// the call is rendered with ([Rivals]): it is written with those types, and javac takes the fact.
        record Outranked() implements Rivalry {}

        /// The facts do not prove it is the only one.
        ///
        /// @param why what javac may take, or why the members are not known
        record Unproven(String why) implements Rivalry {}
    }

    /// Rejects a primitive argument of a parameter of a reference type: boxing is never left implicit.
    ///
    /// @throws IllegalStateException if `arg` is of a primitive type and `param` is not
    private static void requireNoBoxing(Invocable member, TypeToken<?> param, Node.Operand arg) {
        if (arg.type() instanceof PrimitiveToken<?, ?, ?> && !(param instanceof PrimitiveToken<?, ?, ?>)) {
            throw new IllegalStateException(member + " takes a " + param + " and is given a value of the primitive"
                    + " type " + arg.type() + ", which Java would box implicitly; box it explicitly, by box(...)");
        }
    }

    /// The arguments of `member`, one for each of its parameters, each of exactly its parameter's type unless
    /// `searched`, the type whose methods javac searches, has no other candidate.
    ///
    /// @throws FactLookupException if no arguments make javac resolve `member`, see [#requireDistinct]
    private List<Expr> each(Invocable member, Searched searched, List<Node.Operand> args) {
        uses(member);
        args.forEach(arg -> mentions(arg.type()));
        requireDistinct(member);
        boolean onlyCandidate = onlyCandidate(searched, member);
        List<TypeToken<?>> params = member.params();
        return IntStream.range(0, args.size())
                .mapToObj(i -> {
                    Node.Operand arg = args.get(i);
                    TypeToken<?> param = params.get(i);
                    requireNoBoxing(member, param, arg);
                    // An array javac may type otherwise than a last parameter of an array type —
                    // `String[]` for `Object...` — would be a call javac warns of where the parameter is
                    // of variable arity, not knowing which of the two calls is meant. Of exactly the type
                    // of the parameter it is the array of the arguments, whatever the arity.
                    boolean lastArray = i == args.size() - 1 && param instanceof ArrayToken<?, ?>;
                    if (!Tokens.sameType(arg.type(), param) && (!onlyCandidate || lastArray)) {
                        return cast(param, arg, member);
                    }
                    if (lastArray) {
                        return expr(arg.node(), Typing.EXACT);
                    }
                    // The one candidate gives a lambda its target type; among several, the cast of
                    // the lambda does (expr), as the type of any other argument decides.
                    return onlyCandidate ? targeted(arg.node(), param) : expr(arg.node(), Typing.EXACT);
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
    private boolean onlyCandidate(Searched searched, Invocable method) {
        if (!(searched instanceof Searched.In(DeclaredToken<?> declared))) {
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
    /// A type variable has the methods of its bounds (JLS 4.9): a twin is one a bound has. One the
    /// generator vouched for does not say what its bounds add: it may have such an overload of any
    /// method that takes arguments.
    boolean hasTwin(TypeToken<?> searched, Invocable method) {
        DeclaredToken<?> owner = method.owner();
        return switch (searched) {
            case me.supcheg.javafile.facts.AnnotatedRefToken<?> annotated -> hasTwin(annotated.token(), method);
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
            case TypeVarToken<?> variable ->
                bounds(variable)
                        .map(bounds -> bounds.stream().anyMatch(bound -> hasTwinThrough(bound, method)))
                        .orElseGet(() -> !method.params().isEmpty());
            // an array has the methods of Object and no other, and a primitive is no receiver (Expressions)
            case ArrayToken<?, ?> _, PrimitiveToken<?, ?, ?> _ -> false;
        };
    }

    /// Whether `bound`, a bound of a type variable, has another method than `method` that erases as it
    /// does: one of a subtype of the owner of `method` as [#hasTwin] tells it, and any method of that
    /// signature of a bound that is none — which the shape of the bound proves it is not
    /// ([me.supcheg.javafile.facts.TypeShape#interfaces()]), or which it is held not to be.
    private boolean hasTwinThrough(DeclaredToken<?> bound, Invocable method) {
        ClassDesc owner = method.owner().erasure();
        boolean inherits = bound.shape() == method.owner().shape()
                || bound.shape().superclasses().contains(owner)
                || bound.shape().interfaces().contains(owner);
        return inherits
                ? hasTwin(bound, method)
                : candidates(target.methods(bound.shape()), method)
                        .anyMatch(s -> s.instantiate(bound.argumentErasures()).equals(method.signature()));
    }

    /// The bounds of a type parameter of the class the code is in; empty for a type variable the
    /// generator vouched for, whose bounds are not known.
    private Optional<List<DeclaredToken<?>>> bounds(TypeVarToken<?> variable) {
        return switch (place) {
            case Place.Standalone _ -> Optional.empty();
            case Place.InClass(var _, var _, var variables, var _) ->
                variables.stream()
                        .filter(bounded -> bounded.variable() == variable)
                        .findFirst()
                        .map(Subtyping.Bounded::bounds);
        };
    }

    /// `(type) operand`, where `of`, the member reached, needs the operand of exactly `type`. javac
    /// warns of the cast of a value of a type variable of several bounds to a parameterized type as
    /// unchecked (JLS 5.1.6.2, `-Xlint:unchecked`), and the typed layer writes no code javac warns of:
    /// such a cast is refused.
    ///
    /// @throws IllegalStateException if `operand` is of a type parameter of several bounds and `type` is
    ///     parameterized
    private Expr cast(TypeToken<?> type, Node.Operand operand, Object of) {
        requireChecked(type, operand, of);
        return Exprs.cast(written(type).typeRef(), expr(operand.node()));
    }

    /// Rejects the cast of `operand` to `type` that javac warns of as unchecked: of a value of a type
    /// parameter of several bounds to a parameterized type (JLS 5.1.6.2, `-Xlint:unchecked`).
    ///
    /// @param of what the cast is for, for the message
    /// @throws IllegalStateException if `operand` is of a type parameter of several bounds and `type` is
    ///     parameterized
    private void requireChecked(TypeToken<?> type, Node.Operand operand, Object of) {
        if (operand.type() instanceof TypeVarToken<?> variable
                && type.typeRef() instanceof ParameterizedTypeRef
                && bounds(variable).filter(bounds -> bounds.size() > 1).isPresent()) {
            throw new IllegalStateException(of + " is reached through a value of the type parameter " + variable
                    + ", of several bounds, which would be cast to " + type + " for it: javac warns of such a"
                    + " cast as unchecked (-Xlint:unchecked) — bind the value to a variable of " + type
                    + " first, by let");
        }
    }

    /// The members of a type javac chooses among for a use of `member`: its
    /// methods of that name and arity, instance and `static`, or its
    /// constructors of that arity.
    private static Stream<MethodTableTemplate.Signature> candidates(MethodTableTemplate template, Invocable member) {
        return named(template, member)
                .filter(s -> s.params().size() == member.params().size());
    }

    /// The members of a type javac may choose among for a use of `member`, of whatever arity: its methods of
    /// that name, instance and `static`, or its constructors.
    private static Stream<MethodTableTemplate.Signature> named(MethodTableTemplate template, Invocable member) {
        Stream<MethodTableTemplate.Signature> members =
                member.kind() == InvocableKind.CONSTRUCTOR ? template.constructors().stream() : template.methods();
        return members.filter(s -> s.name().equals(member.name()));
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
    private Expr fieldReceiver(Node.Operand witnessed, FieldRef<?, ?> field) {
        Node.Operand receiving = seen(witnessed);
        uses(field);
        mentions(receiving.type());
        DeclaredToken<?> owner = field.owner();
        Optional<ClassToken<?>> subclass = subclassReaching(field.access(), owner, field);
        if (subclass.isPresent()) {
            return protectedFieldReceiver(subclass.get(), receiving, field);
        }
        if (!Tokens.sameType(receiving.type(), owner)) {
            return cast(owner, receiving, field);
        }
        return expr(receiving.node(), Typing.EXACT);
    }

    /// The receiver of a `protected` instance field, in the body of `subclass`: `super`. The
    /// field is that of the direct superclass and is read on `this`, so `super.f` is the field —
    /// whatever field the class declares by that name and whatever constant an interface of it
    /// has (JLS 15.11.2), and with no cast to a type the class has no access to the member through.
    private Expr protectedFieldReceiver(ClassToken<?> subclass, Node.Operand receiving, FieldRef<?, ?> field) {
        if (!(receiving.node() instanceof Node.This)) {
            throw new IllegalStateException(field + " is protected, and is reached on an expression that is not"
                    + " this: the typed layer reads and assigns a protected field of the superclass as super."
                    + field.name() + ", on this alone");
        }
        if (!subclass.superclasses().getFirst().equals(field.owner().erasure())) {
            throw new IllegalStateException(field + " is protected, and is a field of a class above the superclass"
                    + " of " + subclass + ": the typed layer cannot tell whether a class between hides it, and"
                    + " reaches the protected fields of the direct superclass alone");
        }
        return new SuperExpr();
    }

    /// Whether a static member is one the body being lowered declares itself: the body of an enum
    /// constant is an anonymous class, and reaches its own static members unqualified - it has no name
    /// to qualify them by.
    private boolean localStatic(DeclaredToken<?> owner, String name) {
        return place instanceof Place.InClass(var self, var _, var _, var localNames)
                && owner.erasure().equals(self.erasure())
                && localNames.contains(name);
    }

    /// The raw type qualifying a static field: its owner, whatever type the
    /// generator reached the fact through, so no field of a subtype hides
    /// it and no other supertype of one makes the name ambiguous.
    ///
    /// A static field read while a static initializer of the class is lowered is one of the fields that
    /// initializer may not read if it is declared at or after the field initialized — itself among them —:
    /// Java forbids reading such a field of the class being initialized by its simple name (JLS 8.3.3), and
    /// the qualified name the typed layer writes has read no value yet, only the default one.
    ///
    /// @throws IllegalArgumentException if the code is a static initializer and reads a field it may not
    private ClassTypeRef staticOwner(StaticFieldRef<?> field) {
        if (forward.isPresent() && forward.get().later().contains(field)) {
            throw new IllegalArgumentException(
                    "the initializer of " + forward.get().field() + " reads " + field
                            + ", which is declared at or after it: a static initializer reads no static field of its own"
                            + " class before that field is declared (JLS 8.3.3), and the read would be of the field's"
                            + " default value — declare " + field.name() + " before the field it is read by, or read it"
                            + " from the body of a method");
        }
        subclassReaching(field.access(), field.owner(), field);
        mentions(field.owner());
        mentions(field.type());
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
                .forEach(this::mentions);
    }

    /// An instance field fact the lowered code reads or assigns.
    private void uses(FieldRef<?, ?> field) {
        mentions(field.owner());
        mentions(field.type());
    }

    /// The raw type qualifying a static member of `owner`: `List`, never
    /// `List<String>`, which Java rejects there.
    private static ClassTypeRef raw(DeclaredToken<?> owner) {
        return Types.of(owner.erasure());
    }

    /// The explicit type arguments of a generic method's fact.
    private List<TypeRef> typeArgs(Invocable method) {
        return method.traits().typeArgs().stream()
                .<TypeRef>map(type -> written(type).typeRef())
                .toList();
    }

    /// How an expression is lowered: as it stands, javac typing it as it will, or so that javac types
    /// it by exactly its token even where [Node#isExact(Node)] does not hold — pinned.
    private enum Typing {
        AS_IT_STANDS,
        EXACT;

        static Typing exactIf(boolean exact) {
            return exact ? EXACT : AS_IT_STANDS;
        }
    }

    /// How the arguments of a spread call are pinned to the fact: as they are, or, where another
    /// method of the name takes as many arguments and none of them applies to arguments of exactly
    /// those types, each of another type cast to its parameter's.
    private enum Pinning {
        AS_THEY_ARE,
        BY_CASTS
    }

    /// Where javac searches for the member a call names.
    private sealed interface Searched {
        /// Among the methods of a type.
        ///
        /// @param type the type
        record In(TypeToken<?> type) implements Searched {}

        /// Where there is no method table to consult — the constructors of a class, the `static`
        /// methods of a type named outright: every argument is given exactly its parameter's type.
        record Unconsulted() implements Searched {}
    }

    /// Where the lowered code stands: what decides whether it reaches a `protected` member.
    sealed interface Place {
        /// In the body of no class: a standalone expression.
        record Standalone() implements Place {}

        /// In the body of the class being declared: a member, or the initializer of a field.
        ///
        /// @param self the class
        /// @param scope the type parameters of the class that are in scope there
        /// @param variables the type parameters of the class, each with its bounds
        /// @param localStatics the static fields and methods the body being lowered declares itself,
        ///     which it reaches unqualified: it is the body of an anonymous class, and the class has no
        ///     name to qualify them by
        record InClass(
                DeclaredToken<?> self, TypeScope scope, List<Subtyping.Bounded> variables, Set<String> localStatics)
                implements Place {
            public InClass {
                variables = List.copyOf(variables);
                localStatics = Set.copyOf(localStatics);
            }
        }
    }

    // ------------------------------------------------------------------

    /// A lambda, without a target type of its own: `(String v0) -> ...`.
    /// Its parameters are typed explicitly, so its function type is that of
    /// its fact whatever the target type lets javac infer.
    ///
    /// A lambda body is a scope of its own for the parameters, and a fresh
    /// loop context: `break`/`continue` cannot jump out of a lambda. Its
    /// `return` statements return the result of the functional interface's
    /// method, which is the target type of an expression body too.
    private Expr lambda(Node.Lambda lambda) {
        uses(lambda.sam());
        return new Lowering(this, lambda.sam().resultType()).lambdaBody(lambda);
    }

    /// `receiver::method` or `Type::method`, without the cast to the functional interface of its fact.
    private Expr methodRef(Node.MethodRef reference) {
        uses(reference.method());
        if (reference.receiver().isPresent()) {
            return expr(reference.receiver().get().node())
                    .methodRef(reference.method().name());
        }
        mentions(reference.type());
        return Exprs.methodRef(
                written(reference.type()).typeRef(), reference.method().name());
    }

    /// The parameters and the body of a lambda, lowered by the lowering of its body.
    private Expr lambdaBody(Node.Lambda lambda) {
        names.push();
        try {
            List<Param> coreParams = lambda.params().stream()
                    .map(param ->
                            new Param(declare(param), written(param.type()).typeRef()))
                    .toList();
            return switch (lambda.body()) {
                // An assignment is a statement of the core, not an expression: `(v0) -> { this.f = v0; }`.
                case Node.LambdaBody.Value(var _, Node.Assign assign) ->
                    Exprs.typedLambda(coreParams, cb -> cb.accept(assignStmt(assign)));
                case Node.LambdaBody.Value(var _, var value) -> Exprs.typedLambda(coreParams, returned(value));
                case Node.LambdaBody.Block(var block) -> {
                    List<Stmt> stmts = lowerBlock(block.instrs()).statements();
                    yield Exprs.typedLambda(coreParams, stmts::forEach);
                }
            };
        } finally {
            names.pop();
        }
    }

    /// A `switch` expression over an enum. The selector is of the enum to
    /// javac, cast to it if its static type is another; every result is of
    /// the type of the expression ([Expressions#switch_]), so that is the
    /// type javac gives the `switch` where it stands alone (JLS 15.28.1). A
    /// block of a case is in the loops of the code around it, though it
    /// cannot break or continue them ([ScopeCheck]).
    private Expr switchExpr(Node.Switch switch_) {
        EnumToken<?> enumType = switch_.enumType();
        Node.Operand selector = seen(switch_.selector());
        mentions(enumType);
        mentions(selector.type());
        Expr selected = Tokens.sameType(selector.type(), enumType)
                ? expr(selector.node(), Typing.EXACT)
                : Exprs.cast(written(enumType).typeRef(), expr(selector.node()));
        Stream<SwitchCase> cases = switch_.cases().stream()
                .map(c -> new SwitchCase(
                        NonEmptyList.copyOf(c.constants().stream()
                                .<CaseLabel>map(constant ->
                                        new ConstantLabel(new FieldAccessExpr(Optional.empty(), constant.name())))
                                .toList()),
                        caseBody(c.arm())));
        Stream<SwitchCase> otherwise = (switch (switch_.otherwise()) {
                    case Node.Default.None _ -> Stream.<Node.Arm>empty();
                    case Node.Default.Of(var arm) -> Stream.of(arm);
                })
                .map(arm -> new SwitchCase(NonEmptyList.copyOf(List.<CaseLabel>of(new DefaultLabel())), caseBody(arm)));
        return new SwitchExpr(selected, Stream.concat(cases, otherwise).toList());
    }

    private CaseBody caseBody(Node.Arm arm) {
        return switch (arm) {
            case Node.Arm.Value(var value) -> new ExprCaseBody(expr(value, Typing.EXACT));
            case Node.Arm.Block(var block) -> new BlockCaseBody(lowerBlock(block.instrs()));
        };
    }
}
