package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
/// and stops at the first scope that catches, discards or declares.
///
/// - **Subtypes.** A `catch` or a `throws` of a class covers its subclasses,
///   as the tokens record them ([ClassToken#superclasses()]).
/// - **Unchecked exceptions** — subclasses of `RuntimeException` and of
///   `Error` — are not tracked.
/// - **A lambda body** is a boundary: what is thrown in it is covered by
///   the `throws` clause of the method of its functional interface, or
///   caught in it, never by the code around the lambda.
/// - **A type variable** in the place of an exception (`<X extends
///   Throwable> ... throws X`, with a type variable given for `X`) is known
///   by its bound alone: it is taken as checked unless the bound is
///   `RuntimeException` or `Error` itself, and is covered by the same type
///   variable, by the class that is its bound, and by `Throwable`.
///
/// The same data validates the `catch` clauses of a `try` ([#requireCatchable]):
/// javac rejects a clause for a checked exception the `try` block cannot
/// throw.
///
/// The untyped code of `Unsafe` is not looked into: what it throws is its
/// author's to catch or declare, and a `try` block that holds some is
/// taken to throw anything, so its `catch` clauses are not checked.
///
/// Stricter than javac in two places, both of which reject code javac
/// accepts and neither of which lets through code it rejects: a `catch`
/// parameter that is rethrown is thrown at its declared type (no precise
/// rethrow, JLS 11.2.2), and an exception thrown in a `catch` block is
/// checked even where the `finally` block of that `try` would discard it.
final class Exceptions {
    private static final ClassDesc CD_RUNTIME_EXCEPTION = ClassDesc.of("java.lang.RuntimeException");
    private static final ClassDesc CD_ERROR = ClassDesc.of("java.lang.Error");
    private static final Set<ClassDesc> CD_EXCEPTION_AND_ABOVE =
            Set.of(ClassDesc.of("java.lang.Exception"), ConstantDescs.CD_Throwable);

    private Exceptions() {}

    /// A checked exception a construct can throw.
    ///
    /// @param type the exception type
    /// @param by the construct, for the message
    private record Raise(RefToken<? extends Throwable> type, String by) {}

    // ------------------------------------------------------------------
    // Caught or declared
    // ------------------------------------------------------------------

    /// Checks the expressions of `instr`, about to be appended to `block`,
    /// and its `throw`. The statements of its nested blocks were checked
    /// when they were appended to those blocks.
    ///
    /// @throws IllegalStateException if `instr` can throw a checked exception that is neither caught nor declared
    static void check(Instr instr, Block<?, ?> block) {
        raised(instr).forEach(raise -> requireCovered(raise, block));
        nodes(instr).forEach(Exceptions::expressionLambdas);
    }

    /// Checks an expression that is used outside of any body, e.g. a field
    /// initializer: nothing catches and nothing declares there.
    ///
    /// @param node the expression
    /// @param where where it is used, for the message
    /// @throws IllegalStateException if `node` can throw a checked exception
    static void standalone(Node node, String where) {
        VoidBody nowhere = VoidBody.root(
                where,
                ExceptionScope.declaresNothing("make the call in a constructor or a method, which can catch or"
                        + " declare it: an initializer does neither"));
        raised(node).forEach(raise -> requireCovered(raise, nowhere));
        expressionLambdas(node);
    }

    /// The body of an expression lambda is no statement of a block, so it
    /// is checked with the expression the lambda is part of, against the
    /// scope of the lambda.
    private static void expressionLambdas(Node node) {
        descendants(node).forEach(n -> {
            if (n instanceof Node.Lambda(var _, var _, Node.LambdaBody.Value(var scope, var value))) {
                raised(value).forEach(raise -> requireCovered(raise, scope));
                expressionLambdas(value);
            }
        });
    }

    private static void requireCovered(Raise raise, Block<?, ?> block) {
        uncovering(raise.type(), block).ifPresent(declares -> {
            throw new IllegalStateException(raise.by() + " in the " + block.path()
                    + " can throw the checked exception " + raise.type()
                    + ", which no catch_ of an enclosing try_ catches and which is not declared (declared: "
                    + describe(declares.types()) + "): catch it, or " + declares.remedy() + " (JLS 11.2.3)");
        });
    }

    /// The scope at which `thrown`, thrown in `block`, stops without being
    /// covered; empty if it is caught, discarded or declared.
    private static Optional<ExceptionScope.Declares> uncovering(RefToken<?> thrown, Block<?, ?> block) {
        return switch (block.exceptionScope()) {
            case ExceptionScope.Passes _ -> uncovering(thrown, enclosing(block));
            case ExceptionScope.Catches(var types) ->
                types.stream().anyMatch(type -> covers(type, thrown))
                        ? Optional.empty()
                        : uncovering(thrown, enclosing(block));
            case ExceptionScope.Discards _ -> Optional.empty();
            case ExceptionScope.Declares declares ->
                declares.types().stream().anyMatch(type -> covers(type, thrown))
                        ? Optional.empty()
                        : Optional.of(declares);
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
    /// @param form the statement, for the message
    /// @throws IllegalStateException if a preceding clause catches `type`
    static void requireNotCaught(ClassToken<?> type, List<ClassToken<? extends Throwable>> preceding, String form) {
        preceding.stream().filter(earlier -> covers(earlier, type)).findFirst().ifPresent(earlier -> {
            throw new IllegalStateException("catch_ of " + type + " in " + form + " comes after the catch_ of "
                    + earlier + ", which has already caught it: a catch_ of a subclass goes before the one of its"
                    + " superclass (JLS 11.2.3)");
        });
    }

    /// Rejects a `catch` clause of a checked exception class that the
    /// `try` block cannot throw (JLS 11.2.3): it throws neither a subclass
    /// nor a superclass of it. `Exception` and `Throwable` are always
    /// allowed — an unchecked exception may be thrown anywhere — and so is
    /// every unchecked class.
    ///
    /// A clause all of whose exceptions a preceding clause has caught —
    /// `catch (FileNotFoundException)`, then `catch (IOException)`, of a
    /// `try` block that throws `FileNotFoundException` alone — is rejected
    /// too: javac warns of it, and it is dead code.
    ///
    /// @param type the type of the clause
    /// @param preceding the types of the clauses before it
    /// @param thrown the checked exceptions the `try` block can throw
    /// @param form the statement, for the message
    /// @throws IllegalStateException if the clause can catch nothing
    static void requireCatchable(
            ClassToken<?> type,
            List<ClassToken<? extends Throwable>> preceding,
            List<RefToken<? extends Throwable>> thrown,
            String form) {
        if (!type.isCheckedException() || CD_EXCEPTION_AND_ABOVE.contains(type.erasure())) {
            return;
        }
        // A try block that throws a superclass may throw this class at run time.
        if (thrown.stream().anyMatch(t -> covers(t, type))) {
            return;
        }
        List<RefToken<? extends Throwable>> catchable =
                thrown.stream().filter(t -> covers(type, t)).toList();
        if (catchable.isEmpty()) {
            throw new IllegalStateException("catch_ of " + type + " in " + form + ": the try block cannot throw"
                    + " this checked exception (it can throw: " + describe(thrown) + "), so javac rejects the"
                    + " clause; drop it (JLS 11.2.3)");
        }
        if (catchable.stream().allMatch(t -> preceding.stream().anyMatch(earlier -> covers(earlier, t)))) {
            throw new IllegalStateException("catch_ of " + type + " in " + form + " is unreachable: what the try"
                    + " block can throw of it (" + describe(catchable) + ") the preceding catch_ clauses have"
                    + " already caught; drop it (JLS 11.2.3)");
        }
    }

    // ------------------------------------------------------------------
    // What a statement throws
    // ------------------------------------------------------------------

    /// The checked exceptions `block` can throw to the block it is nested
    /// in: those of its statements that it does not catch itself.
    ///
    /// @return the exception types, one per construct that throws
    static Stream<RefToken<? extends Throwable>> thrown(Block<?, ?> block) {
        return block.instrs().stream().flatMap(Exceptions::thrown);
    }

    private static Stream<RefToken<? extends Throwable>> thrown(Instr instr) {
        return Stream.concat(raised(instr).map(Raise::type), thrownByNested(instr));
    }

    private static Stream<RefToken<? extends Throwable>> thrownByNested(Instr instr) {
        return instr instanceof Instr.Try(var body, var catches, var finallyBlock)
                ? thrownByTry(body, catches, finallyBlock)
                : blocks(instr).flatMap(Exceptions::thrown);
    }

    /// Whether what `block` throws is not known: it holds, at any depth, a
    /// statement or an expression of `Unsafe`, which may throw anything. A
    /// `catch` clause of a `try` with such a `try` block is the author's
    /// responsibility, as the untyped code is.
    static boolean throwsUnknown(Block<?, ?> block) {
        return block.instrs().stream()
                .anyMatch(instr -> instr instanceof Instr.Raw
                        || nodes(instr).flatMap(Exceptions::descendants).anyMatch(Node.Raw.class::isInstance)
                        || blocks(instr).anyMatch(Exceptions::throwsUnknown));
    }

    /// The blocks nested in `instr`.
    private static Stream<Block<?, ?>> blocks(Instr instr) {
        return switch (instr) {
            case Instr.If(var _, var then, var otherwise) -> Stream.concat(Stream.of(then), otherwise.stream());
            case Instr.IfInstance(var _, var _, var _, var then, var otherwise) ->
                Stream.concat(Stream.of(then), otherwise.stream());
            case Instr.While(var _, var _, var body) -> Stream.of(body);
            case Instr.DoWhile(var _, var body, var _) -> Stream.of(body);
            case Instr.For(var _, var _, var _, var _, var _, var body) -> Stream.of(body);
            case Instr.ForEach(var _, var _, var _, var body) -> Stream.of(body);
            case Instr.Try(var body, var catches, var finallyBlock) ->
                Stream.of(Stream.of(body), catches.stream().map(Instr.Catch::body), finallyBlock.stream())
                        .flatMap(s -> s);
            case Instr.Let _, Instr.Exec _, Instr.Return _, Instr.Throw _ -> Stream.empty();
            case Instr.Break _, Instr.Continue _, Instr.Raw _ -> Stream.empty();
        };
    }

    /// What a `try` statement throws: what its `try` block throws and no
    /// clause catches, what its `catch` blocks throw, and what its `finally`
    /// block throws — that alone if the `finally` block cannot complete
    /// normally, as everything else is then discarded (JLS 14.20.2).
    private static Stream<RefToken<? extends Throwable>> thrownByTry(
            Block<?, ?> body, List<Instr.Catch> catches, Optional<Block<?, ?>> finallyBlock) {
        Stream<RefToken<? extends Throwable>> byFinally = finallyBlock.stream().flatMap(Exceptions::thrown);
        if (finallyBlock.filter(f -> !Reachability.canCompleteNormally(f)).isPresent()) {
            return byFinally;
        }
        return Stream.of(
                        thrown(body).filter(t -> catches.stream().noneMatch(c -> covers(c.type(), t))),
                        catches.stream().flatMap(c -> thrown(c.body())),
                        byFinally)
                .flatMap(s -> s);
    }

    /// The checked exceptions the expressions of `instr` and its `throw`
    /// can throw; not those of its nested blocks.
    private static Stream<Raise> raised(Instr instr) {
        Stream<Raise> byExpressions = nodes(instr).flatMap(Exceptions::raised);
        return instr instanceof Instr.Throw(var _, var type)
                ? Stream.concat(byExpressions, thrownBy(type))
                : byExpressions;
    }

    private static Stream<Raise> thrownBy(TypeToken<? extends Throwable> type) {
        return switch (type) {
            case RefToken<? extends Throwable> ref ->
                Stream.of(ref).filter(Exceptions::isChecked).map(t -> new Raise(t, "throw_"));
            case PrimitiveToken<?, ?, ?> primitive ->
                throw new IllegalStateException("throw_ of a primitive " + primitive);
        };
    }

    /// The checked exceptions evaluating `node` can throw. A lambda in it
    /// throws nothing where it stands: its body runs elsewhere.
    private static Stream<Raise> raised(Node node) {
        return descendants(node).flatMap(Exceptions::raisedItself);
    }

    private static Stream<Raise> raisedItself(Node node) {
        return switch (node) {
            case Node.Call(var _, var method, var _) -> declaredBy(method);
            case Node.StaticCall(var method, var _) -> declaredBy(method);
            case Node.New(var ctor, var _) -> declaredBy(ctor);
            default -> Stream.empty();
        };
    }

    private static Stream<Raise> declaredBy(Invocable member) {
        return member.traits().throwsTypes().stream()
                .filter(Exceptions::isChecked)
                .map(type -> new Raise(type, member.toString()));
    }

    // ------------------------------------------------------------------
    // The exception types
    // ------------------------------------------------------------------

    /// Whether `type` is a checked exception type (JLS 11.1.1). A type
    /// variable is known by its bound alone, so it is unchecked only if
    /// the bound is `RuntimeException` or `Error` itself.
    static boolean isChecked(RefToken<?> type) {
        return switch (type) {
            case ClassToken<?> cls -> cls.isCheckedException();
            default ->
                !type.erasure().equals(CD_RUNTIME_EXCEPTION) && !type.erasure().equals(CD_ERROR);
        };
    }

    /// Whether a `catch` or a `throws` of `handler` covers the exception
    /// type `thrown`: `thrown` is `handler` or a subtype of it.
    private static boolean covers(RefToken<?> handler, RefToken<?> thrown) {
        return switch (handler) {
            case ClassToken<?> cls ->
                switch (thrown) {
                    case ClassToken<?> thrownClass -> thrownClass.isSubclassOf(cls.erasure());
                    // A type variable is a subtype of its bound, and of nothing else that is known.
                    default ->
                        cls.erasure().equals(thrown.erasure()) || cls.erasure().equals(ConstantDescs.CD_Throwable);
                };
            default -> Tokens.sameType(handler, thrown);
        };
    }

    private static String describe(List<? extends RefToken<?>> types) {
        return types.isEmpty()
                ? "nothing"
                : types.stream().map(Object::toString).distinct().collect(Collectors.joining(", "));
    }

    // ------------------------------------------------------------------
    // The expressions of a statement
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
            case Instr.Throw(var value, var _) -> Stream.of(value);
            case Instr.Break _, Instr.Continue _, Instr.Try _, Instr.Raw _ -> Stream.empty();
        };
    }

    /// `node` and every expression evaluated with it; a lambda is one of
    /// them, its body is not.
    private static Stream<Node> descendants(Node node) {
        return Stream.concat(Stream.of(node), children(node).flatMap(Exceptions::descendants));
    }

    private static Stream<Node> children(Node node) {
        return switch (node) {
            case Node.Lit _, Node.RawLit _, Node.Local _, Node.This _ -> Stream.empty();
            case Node.StaticFieldGet _, Node.EnumConst _, Node.Raw _, Node.Lambda _ -> Stream.empty();
            case Node.Box(var _, var operand) -> Stream.of(operand);
            case Node.Unbox(var _, var operand) -> Stream.of(operand);
            case Node.Call(var target, var _, var args) ->
                Stream.concat(Stream.of(target.node()), args.stream().map(Node.Operand::node));
            case Node.StaticCall(var _, var args) -> args.stream().map(Node.Operand::node);
            case Node.New(var _, var args) -> args.stream().map(Node.Operand::node);
            case Node.FieldGet(var target, var _) -> Stream.of(target.node());
            case Node.ArrayAt(var array, var index) -> Stream.of(array, index);
            case Node.ArrayLength(var array) -> Stream.of(array);
            case Node.NewArray(var _, var length) -> Stream.of(length);
            case Node.Cond(var condition, var whenTrue, var whenFalse) -> Stream.of(condition, whenTrue, whenFalse);
            case Node.Binary(var _, var left, var right, var _) -> Stream.of(left, right);
            case Node.Unary(var _, var operand, var _) -> Stream.of(operand);
            case Node.Cast(var _, var operand) -> Stream.of(operand);
            case Node.InstanceOf(var operand, var _) -> Stream.of(operand);
            case Node.Switch(var selector, var _, var cases, var otherwise) ->
                Stream.of(Stream.of(selector), cases.stream().map(Node.Case::value), otherwise.stream())
                        .flatMap(s -> s);
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
