package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The exception check of JLS 11.2.3 (§9.1), run when a statement is
/// appended: a checked exception the statement can throw — one a method or
/// constructor fact declares ([me.supcheg.javafile.facts.MemberTraits#throwsTypes()]),
/// or the static type of a `throw_` — must be caught by a `catch_` of an
/// enclosing `try_`, or declared by the member whose body the statement is
/// in. Java has no union types to carry the exceptions of a body in its
/// type (§10), so the check is made on the facts; it is made where the
/// statement is built, so the stack of the failure points at the call, as
/// with [ScopeCheck].
///
/// Every block knows its [ExceptionScope] before a statement is appended to
/// it — the `catch` clauses of a `try` are built before its `try` block, see
/// [Block#try_] — so the check walks from the block of the statement outwards
/// and stops at the first scope that catches or declares.
///
/// - **Subtypes.** A `catch` or a `throws` of a class covers its subclasses,
///   as the tokens record them ([me.supcheg.javafile.facts.ClassToken#superclasses()]).
/// - **Unchecked exceptions** — subclasses of `RuntimeException` and of
///   `Error` — are not tracked.
/// - **A lambda body** is a boundary: what is thrown in it is covered by
///   the `throws` clause of the method of its functional interface, or
///   caught in it, never by the code around the lambda.
/// - **The fact is the member javac calls.** A method a subclass overrides
///   may declare fewer exceptions there (JLS 8.4.8.3), and javac goes by the
///   method of the static type of the receiver. Lowering therefore casts
///   the receiver to the owner of a fact that declares a checked exception,
///   `((Reader) stringReader).close()`, so that javac sees the `throws`
///   clause this check has seen.
///
/// **The `catch` clauses of a `try`** are checked by the same data once the
/// `try` block is built ([#requireCatchable(Instr.Try)]): javac rejects a
/// clause for a checked exception the `try` block cannot throw. Here a
/// `throw_` of the binding of a `catch_` throws what javac says it does
/// (JLS 11.2.2, the precise rethrow): those exceptions of the `try` block of
/// that clause that the clause catches and the clauses before it do not —
/// the binding is not assignable, so it is always effectively final. What
/// the binding of an enclosing `try` rethrows is known only once that `try`
/// block is built, which is after its `catch` blocks are: a `try` nested in
/// a `catch` block is therefore checked again, with what is then known, when
/// the enclosing statement is complete, and a clause of it may be rejected
/// there rather than where it was added.
///
/// **A `switch` expression** throws where it stands: what its selector and
/// the values of its cases throw is thrown by the expression it is part of,
/// and a block of a case ([YieldBody]) hands what is thrown in it to the
/// block the `switch` was built in, where its statements were checked as
/// they were appended. That block encloses the one the `switch` is used in
/// ([ScopeCheck]), so what covers an exception there covers it here; to the
/// `catch` clauses of a `try` a block of a case is a block of the statement
/// the `switch` is in.
///
/// The untyped code of `Unsafe` is not looked into: what it throws is its
/// author's to catch or declare, and a `try` block that holds some is
/// taken to throw anything, so its `catch` clauses are not checked.
///
/// **Stricter than javac.** Each of these rejects code javac accepts; none
/// accepts code javac rejects.
///
/// - *Where a rethrown binding must be covered.* When `throw_(e)` of the
///   binding of a `catch_` is appended, the `try` block of that clause is
///   not built yet, so the statement is checked as throwing the type of the
///   clause: `catch (Exception e) { throw e; }` needs `Exception` caught or
///   declared around it even where the `try` block throws `IOException`
///   alone.
/// - *A dead clause.* A `catch_` all of whose exceptions the preceding
///   clauses have caught — `catch (FileNotFoundException)`, then `catch
///   (IOException)`, of a `try` block that throws `FileNotFoundException`
///   alone — is rejected; javac only warns of it.
/// - *A type variable in the place of an exception* is known by its bound
///   alone ([ExceptionType.OfVariable]): it is taken as checked unless the
///   bound is `RuntimeException` or `Error` itself — a subclass of those
///   would be unchecked to javac — and it is covered by the same type
///   variable, by the class that is its bound and by `Throwable`, not by
///   the superclasses of the bound in between.
/// - *A block of a `switch` built outside the `try` it is used in.* Its
///   statements are checked where the `switch` is built: a `catch_` of a
///   `try_` the `switch` is then used in does not cover them.
/// - *A field initializer* throws no checked exception. Java lets the
///   initializer of an instance field throw what every constructor of the
///   class declares.
final class Exceptions {
    private static final Set<ClassDesc> EXCEPTION_AND_ABOVE =
            Set.of(ClassDesc.of("java.lang.Exception"), ConstantDescs.CD_Throwable);

    private Exceptions() {}

    /// A checked exception a construct can throw.
    ///
    /// @param type the exception type
    /// @param by the construct, for the message
    private record Raise(ExceptionType type, String by) {}

    /// What the bindings of the enclosing `catch` clauses rethrow (JLS
    /// 11.2.2), where it is known.
    ///
    /// @param thrown the exception types a `throw` of a binding throws
    private record Rethrows(Map<Var<?>, List<ExceptionType>> thrown) {
        static final Rethrows UNKNOWN = new Rethrows(Map.of());

        Rethrows with(Var<?> binding, List<ExceptionType> types) {
            return new Rethrows(Stream.concat(
                            thrown.entrySet().stream().filter(e -> e.getKey() != binding),
                            Stream.of(Map.entry(binding, types)))
                    .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue)));
        }

        Optional<List<ExceptionType>> of(Node thrownExpression) {
            return thrownExpression instanceof Node.Local(var binding)
                    ? Optional.ofNullable(thrown.get(binding))
                    : Optional.empty();
        }
    }

    /// Whether a checked exception thrown in a block is caught, discarded
    /// or declared.
    private sealed interface Coverage {
        /// A `catch` clause catches it, or the member declares it.
        record Covered() implements Coverage {}

        /// It reaches the body that declares, which does not declare it.
        record Uncovered(ExceptionScope.Declares by) implements Coverage {}
    }

    /// What evaluating a node does itself, apart from its subexpressions.
    private sealed interface Act {
        /// Calls a method or a constructor, which may throw what its fact declares.
        record Invokes(Invocable member) implements Act {}

        /// Makes an expression lambda: `value` is evaluated when the lambda is called, in `scope`.
        record Defers(Block<?, ?> scope, Node value) implements Act {}

        /// Nothing that throws a checked exception, or nothing known: the untyped code of `Unsafe`.
        record Computes() implements Act {}
    }

    // ------------------------------------------------------------------
    // Caught or declared
    // ------------------------------------------------------------------

    /// Checks the expressions of `instr`, about to be appended to `block`,
    /// and its `throw`. The statements of its nested blocks were checked
    /// when they were appended to those blocks.
    ///
    /// @throws IllegalStateException if `instr` can throw a checked exception that is neither caught nor declared
    static void check(Instr instr, Block<?, ?> block) {
        raised(instr, Rethrows.UNKNOWN).forEach(raise -> requireCovered(raise, block));
        nodes(instr).forEach(Exceptions::requireCoveredInLambdas);
    }

    /// Checks the initializer of a field: it is in no body, so nothing
    /// catches and nothing declares there.
    ///
    /// @param node the initializer
    /// @param where where it is, for the message
    /// @throws IllegalStateException if `node` can throw a checked exception
    static void initializer(Node node, String where) {
        List<Raise> raised = raised(node).toList();
        if (!raised.isEmpty()) {
            Raise raise = raised.getFirst();
            throw new IllegalStateException(raise.by() + " in the " + where + " can throw the checked exception "
                    + raise.type() + ": the typed layer accepts no checked exception in a field initializer —"
                    + " stricter than Java, which lets the initializer of an instance field throw what every"
                    + " constructor declares; make the call in a constructor or a method (JLS 11.2.3)");
        }
        requireCoveredInLambdas(node);
    }

    /// Checks the body of an expression lambda where the lambda is made: it
    /// is no statement of a block, and what it throws is covered in the
    /// lambda or not at all.
    ///
    /// @param value the body
    /// @param scope the lambda-boundary block of the lambda
    /// @throws IllegalStateException if `value` can throw a checked exception the method of the functional
    ///     interface does not declare
    static void lambdaValue(Node value, Block<?, ?> scope) {
        raised(value).forEach(raise -> requireCovered(raise, scope));
        requireCoveredInLambdas(value);
    }

    /// The body of an expression lambda is no statement of a block, so it
    /// is checked with the expression the lambda is part of, against the
    /// scope of the lambda.
    private static void requireCoveredInLambdas(Node node) {
        deferred(node).forEach(lambda -> {
            raised(lambda.value()).forEach(raise -> requireCovered(raise, lambda.scope()));
            requireCoveredInLambdas(lambda.value());
        });
    }

    private static void requireCovered(Raise raise, Block<?, ?> block) {
        switch (coverage(raise.type(), block)) {
            case Coverage.Covered _ -> {}
            case Coverage.Uncovered(ExceptionScope.Declares(var boundary, var declared)) ->
                throw new IllegalStateException(raise.by() + " in the " + block.path()
                        + " can throw the checked exception " + raise.type()
                        + ", which no catch_ of an enclosing try catches and " + boundary.doesNotDeclare()
                        + " (declared: " + describe(declared) + "): " + boundary.advice() + " (JLS 11.2.3)");
        }
    }

    /// What becomes of `thrown`, thrown in `block`.
    private static Coverage coverage(ExceptionType thrown, Block<?, ?> block) {
        return switch (block.exceptionScope()) {
            case ExceptionScope.Passes _ -> coverage(thrown, enclosing(block));
            case ExceptionScope.Catches(var types) ->
                types.stream().anyMatch(type -> type.covers(thrown))
                        ? new Coverage.Covered()
                        : coverage(thrown, enclosing(block));
            case ExceptionScope.Declares declares ->
                declares.types().stream().anyMatch(type -> type.covers(thrown))
                        ? new Coverage.Covered()
                        : new Coverage.Uncovered(declares);
        };
    }

    /// The block `block` is nested in: only the body of a member or of a
    /// lambda has none, and that one declares.
    private static Block<?, ?> enclosing(Block<?, ?> block) {
        return Objects.requireNonNull(block.parent(), () -> "the " + block.path() + " declares no exceptions");
    }

    // ------------------------------------------------------------------
    // The catch clauses of a try
    // ------------------------------------------------------------------

    /// Rejects a `catch` clause that can catch nothing a preceding clause
    /// leaves: its type is that of a preceding clause or a subclass of one
    /// (JLS 11.2.3).
    ///
    /// @param type the type of the clause
    /// @param preceding the types of the clauses before it
    /// @param statement the statement and where it is, for the message
    /// @throws IllegalStateException if a preceding clause catches `type`
    static void requireNotCaught(ExceptionType.OfClass type, List<ExceptionType.OfClass> preceding, String statement) {
        List<ExceptionType.OfClass> catching =
                preceding.stream().filter(earlier -> earlier.covers(type)).toList();
        if (!catching.isEmpty()) {
            throw new IllegalStateException("catch_ of " + type + " of the " + statement + " comes after the catch_"
                    + " of " + catching.getFirst() + ", which has already caught it: a catch_ of a subclass goes"
                    + " before the one of its superclass (JLS 11.2.3)");
        }
    }

    /// Rejects a `catch` clause of `statement`, or of a `try` nested in it,
    /// that can catch nothing: one of a checked exception class that its
    /// `try` block cannot throw — it throws neither a subclass nor a
    /// superclass of it (JLS 11.2.3). `Exception` and `Throwable` are always
    /// allowed — an unchecked exception may be thrown anywhere — and so is
    /// every unchecked class.
    ///
    /// A clause all of whose exceptions a preceding clause has caught is
    /// rejected too: javac warns of it, and it is dead code.
    ///
    /// The nested statements were checked when they were built, but for
    /// what the bindings of the clauses of `statement` rethrow, which is
    /// known only now.
    ///
    /// @param statement the `try` statement, complete
    /// @throws IllegalStateException if a clause can catch nothing; a `try` block that holds untyped code
    ///     of `Unsafe` is not checked
    static void requireCatchable(Instr.Try statement) {
        requireCatchable(statement, Rethrows.UNKNOWN);
    }

    private static void requireCatchable(Instr.Try statement, Rethrows rethrows) {
        Block<?, ?> body = statement.body();
        List<Instr.Catch> catches = statement.catches();
        boolean known = !throwsUnknown(body);
        List<ExceptionType> thrown = known ? thrown(body, rethrows).toList() : List.of();
        requireCatchableIn(body, rethrows);
        IntStream.range(0, catches.size()).forEach(i -> {
            Instr.Catch clause = catches.get(i);
            List<ExceptionType.OfClass> preceding = types(catches.subList(0, i));
            if (known) {
                requireCatchable(clause(clause), preceding, thrown, body);
            }
            requireCatchableIn(
                    clause.body(),
                    known ? rethrows.with(clause.var(), rethrown(clause(clause), preceding, thrown)) : rethrows);
        });
        statement.finallyBlock().ifPresent(finallyBlock -> requireCatchableIn(finallyBlock, rethrows));
    }

    /// Checks the `try` statements of `block`, at any depth.
    private static void requireCatchableIn(Block<?, ?> block, Rethrows rethrows) {
        block.instrs().forEach(instr -> {
            if (instr instanceof Instr.Try statement) {
                requireCatchable(statement, rethrows);
            } else {
                blocks(instr).forEach(nested -> requireCatchableIn(nested, rethrows));
            }
        });
    }

    private static void requireCatchable(
            ExceptionType.OfClass type,
            List<ExceptionType.OfClass> preceding,
            List<ExceptionType> thrown,
            Block<?, ?> body) {
        if (!type.isChecked() || EXCEPTION_AND_ABOVE.contains(type.token().erasure())) {
            return;
        }
        // A try block that throws a superclass may throw this class at run time.
        if (thrown.stream().anyMatch(t -> t.covers(type))) {
            return;
        }
        List<ExceptionType> catchable = thrown.stream().filter(type::covers).toList();
        if (catchable.isEmpty()) {
            throw new IllegalStateException("catch_ of " + type + ": the " + body.path() + " cannot throw this"
                    + " checked exception (it can throw: " + describe(thrown) + "), so javac rejects the clause;"
                    + " drop it (JLS 11.2.3)");
        }
        if (catchable.stream().allMatch(t -> preceding.stream().anyMatch(earlier -> earlier.covers(t)))) {
            throw new IllegalStateException("catch_ of " + type + " is unreachable: what the " + body.path()
                    + " can throw of it (" + describe(catchable) + ") the preceding catch_ clauses have already"
                    + " caught; drop it (JLS 11.2.3)");
        }
    }

    /// What a `throw` of the binding of a `catch` clause throws (JLS
    /// 11.2.2): of what the `try` block throws and the preceding clauses
    /// do not catch, what the clause catches — the exception itself if the
    /// clause covers it, the type of the clause if the exception covers
    /// that.
    private static List<ExceptionType> rethrown(
            ExceptionType.OfClass type, List<ExceptionType.OfClass> preceding, List<ExceptionType> thrown) {
        return thrown.stream()
                .filter(t -> preceding.stream().noneMatch(earlier -> earlier.covers(t)))
                .flatMap(t -> type.covers(t) ? Stream.of(t) : t.covers(type) ? Stream.of(type) : Stream.empty())
                .toList();
    }

    private static ExceptionType.OfClass clause(Instr.Catch clause) {
        return new ExceptionType.OfClass(clause.type());
    }

    private static List<ExceptionType.OfClass> types(List<Instr.Catch> catches) {
        return catches.stream().map(Exceptions::clause).toList();
    }

    // ------------------------------------------------------------------
    // What a statement throws
    // ------------------------------------------------------------------

    /// The checked exceptions `block` can throw to the block it is nested
    /// in: those of its statements that it does not catch itself.
    private static Stream<ExceptionType> thrown(Block<?, ?> block, Rethrows rethrows) {
        return block.instrs().stream().flatMap(instr -> thrown(instr, rethrows));
    }

    private static Stream<ExceptionType> thrown(Instr instr, Rethrows rethrows) {
        return Stream.concat(
                raised(instr, rethrows).map(Raise::type),
                instr instanceof Instr.Try statement
                        ? thrownByTry(statement, rethrows)
                        : blocks(instr).flatMap(block -> thrown(block, rethrows)));
    }

    /// What a `try` statement throws: what its `try` block throws and no
    /// clause catches, what its `catch` blocks throw, and what its `finally`
    /// block throws. A `finally` block completes normally ([FinallyBody]),
    /// so it discards nothing of the others (JLS 14.20.2).
    private static Stream<ExceptionType> thrownByTry(Instr.Try statement, Rethrows rethrows) {
        return Stream.concat(
                thrownByTryAndCatchBlocks(statement, rethrows),
                statement.finallyBlock().stream().flatMap(block -> thrown(block, rethrows)));
    }

    private static Stream<ExceptionType> thrownByTryAndCatchBlocks(Instr.Try statement, Rethrows rethrows) {
        List<Instr.Catch> catches = statement.catches();
        List<ExceptionType> thrown = thrown(statement.body(), rethrows).toList();
        List<ExceptionType.OfClass> types = types(catches);
        boolean known = !throwsUnknown(statement.body());
        return Stream.concat(
                thrown.stream().filter(t -> types.stream().noneMatch(type -> type.covers(t))),
                IntStream.range(0, catches.size())
                        .boxed()
                        .flatMap(i -> thrown(
                                catches.get(i).body(),
                                known
                                        ? rethrows.with(
                                                catches.get(i).var(),
                                                rethrown(types.get(i), types.subList(0, i), thrown))
                                        : rethrows)));
    }

    /// Whether what `block` throws is not known: it holds, at any depth, a
    /// statement or an expression of `Unsafe`, which may throw anything. A
    /// `catch` clause of a `try` with such a `try` block is the author's
    /// responsibility, as the untyped code is.
    private static boolean throwsUnknown(Block<?, ?> block) {
        return block.instrs().stream()
                .anyMatch(instr -> instr instanceof Instr.Raw
                        || nodes(instr).flatMap(Exceptions::descendants).anyMatch(Node.Raw.class::isInstance)
                        || blocks(instr).anyMatch(Exceptions::throwsUnknown));
    }

    /// The checked exceptions the expressions of `instr` and its `throw`
    /// can throw; not those of its nested blocks. A `throw` of the binding
    /// of a `catch` clause throws what `rethrows` knows of it, or else the
    /// type of the clause.
    private static Stream<Raise> raised(Instr instr, Rethrows rethrows) {
        return Stream.concat(
                nodes(instr).flatMap(Exceptions::raised),
                instr instanceof Instr.Throw(var value, var type)
                        ? rethrows.of(value).orElseGet(() -> List.of(type)).stream()
                                .filter(ExceptionType::isChecked)
                                .map(thrown -> new Raise(thrown, "throw_"))
                        : Stream.empty());
    }

    /// The checked exceptions evaluating `node` can throw. A lambda in it
    /// throws nothing where it stands: its body runs elsewhere.
    private static Stream<Raise> raised(Node node) {
        return descendants(node).flatMap(n -> switch (act(n)) {
            case Act.Invokes(var member) ->
                ExceptionType.ofAll(member.traits().throwsTypes()).stream()
                        .filter(ExceptionType::isChecked)
                        .map(type -> new Raise(type, member.toString()));
            case Act.Defers _, Act.Computes _ -> Stream.empty();
        });
    }

    /// The expression lambdas of `node`, not those in the body of another.
    private static Stream<Act.Defers> deferred(Node node) {
        return descendants(node).map(Exceptions::act).flatMap(act -> switch (act) {
            case Act.Defers lambda -> Stream.of(lambda);
            case Act.Invokes _, Act.Computes _ -> Stream.empty();
        });
    }

    private static Act act(Node node) {
        return switch (node) {
            case Node.Call(var _, var method, var _) -> new Act.Invokes(method);
            case Node.StaticCall(var method, var _) -> new Act.Invokes(method);
            case Node.New(var ctor, var _) -> new Act.Invokes(ctor);
            case Node.Lambda(var _, var _, Node.LambdaBody.Value(var scope, var value)) -> new Act.Defers(scope, value);
            // The statements of a block lambda are checked as they are appended to its block.
            case Node.Lambda(var _, var _, Node.LambdaBody.Block _) -> new Act.Computes();
            case Node.Lit _,
                    Node.RawLit _,
                    Node.Local _,
                    Node.This _,
                    Node.Box _,
                    Node.Unbox _,
                    Node.FieldGet _,
                    Node.StaticFieldGet _,
                    Node.EnumConst _,
                    Node.ArrayAt _,
                    Node.ArrayLength _,
                    Node.NewArray _,
                    Node.Cond _,
                    Node.Binary _,
                    Node.Unary _,
                    Node.Cast _,
                    Node.InstanceOf _,
                    Node.Assign _,
                    Node.Raw _ -> new Act.Computes();
            // A switch throws what its selector, its values and its blocks do, and nothing of its own:
            // a selector that is null is a NullPointerException, which is not checked.
            case Node.Switch _ -> new Act.Computes();
        };
    }

    private static String describe(List<? extends ExceptionType> types) {
        return types.isEmpty()
                ? "nothing"
                : types.stream().map(Object::toString).distinct().collect(Collectors.joining(", "));
    }

    // ------------------------------------------------------------------
    // The expressions and the blocks of a statement
    // ------------------------------------------------------------------

    /// The expressions `instr` evaluates itself, not those of its nested blocks.
    private static Stream<Node> nodes(Instr instr) {
        return switch (instr) {
            case Instr.Let(var _, var init) -> Stream.of(init);
            case Instr.Exec(var effect) -> Stream.of(effect);
            case Instr.If(var condition, var _, var _) -> Stream.of(condition);
            case Instr.IfInstance(var operand, var _, var _, var _, var _) -> Stream.of(operand);
            case Instr.While(var _, var condition, var _) -> Stream.of(condition);
            case Instr.DoWhile(var _, var _, var condition) -> Stream.of(condition);
            case Instr.For(var _, var _, var init, var condition, var update, var _) ->
                Stream.of(init, condition, update);
            case Instr.ForEach(var _, var _, var iterable, var _) -> Stream.of(iterable);
            case Instr.Return(var value) -> value.stream();
            case Instr.Yield(var value) -> Stream.of(value);
            case Instr.Throw(var value, var _) -> Stream.of(value);
            case Instr.Break _, Instr.Continue _, Instr.Try _, Instr.Raw _ -> Stream.empty();
        };
    }

    /// The blocks nested in `instr`: those of the statement, and those of
    /// the cases of the `switch` expressions it evaluates.
    private static Stream<Block<?, ?>> blocks(Instr instr) {
        return Stream.concat(
                Instr.blocks(instr),
                nodes(instr).flatMap(Exceptions::descendants).flatMap(Exceptions::blocks));
    }

    /// The blocks `node` has itself: those of the cases of a `switch`.
    private static Stream<Block<?, ?>> blocks(Node node) {
        return node instanceof Node.Switch switch_
                ? switch_.arms().flatMap(arm -> switch (arm) {
                    case Node.Arm.Value _ -> Stream.empty();
                    case Node.Arm.Block(var block) -> Stream.of(block);
                })
                : Stream.empty();
    }

    /// `node` and every expression evaluated with it; a lambda is one of
    /// them, its body is not.
    private static Stream<Node> descendants(Node node) {
        return Stream.concat(Stream.of(node), children(node).flatMap(Exceptions::descendants));
    }

    private static Stream<Node> children(Node node) {
        return switch (node) {
            case Node.Lit _,
                    Node.RawLit _,
                    Node.Local _,
                    Node.This _,
                    Node.StaticFieldGet _,
                    Node.EnumConst _,
                    Node.Raw _,
                    Node.Lambda _ -> Stream.empty();
            case Node.Box(var _, var operand) -> Stream.of(operand);
            case Node.Unbox(var _, var operand) -> Stream.of(operand);
            case Node.Unary(var _, var operand, var _) -> Stream.of(operand);
            case Node.Cast(var _, var operand) -> Stream.of(operand);
            case Node.InstanceOf(var operand, var _) -> Stream.of(operand);
            case Node.ArrayLength(var array) -> Stream.of(array);
            case Node.NewArray(var _, var length) -> Stream.of(length);
            case Node.FieldGet(var target, var _) -> Stream.of(target.node());
            case Node.Call(var target, var _, var args) ->
                Stream.concat(Stream.of(target.node()), args.stream().map(Node.Operand::node));
            case Node.StaticCall(var _, var args) -> args.stream().map(Node.Operand::node);
            case Node.New(var _, var args) -> args.stream().map(Node.Operand::node);
            case Node.ArrayAt(var array, var index) -> Stream.of(array, index);
            case Node.Binary(var _, var left, var right, var _) -> Stream.of(left, right);
            case Node.Cond(var condition, var whenTrue, var whenFalse) -> Stream.of(condition, whenTrue, whenFalse);
            // The statements of a block of a case are checked as they are appended to it.
            case Node.Switch switch_ ->
                Stream.concat(
                        Stream.of(switch_.selector().node()), switch_.arms().flatMap(arm -> switch (arm) {
                            case Node.Arm.Value(var value) -> Stream.of(value);
                            case Node.Arm.Block _ -> Stream.empty();
                        }));
            case Node.Assign(var target, var value) -> Stream.concat(children(target), Stream.of(value));
        };
    }

    private static Stream<Node> children(Node.Target target) {
        return switch (target) {
            case Node.Target.Local _, Node.Target.StaticField _, Node.Target.Init _ -> Stream.empty();
            case Node.Target.Field(var receiver, var _) -> Stream.of(receiver.node());
            case Node.Target.Element(var array, var index) -> Stream.of(array, index);
        };
    }
}
