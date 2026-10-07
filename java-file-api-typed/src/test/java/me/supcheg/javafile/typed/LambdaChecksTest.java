package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Thrower_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.lambdaBlock;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.JavacVerdict.asJavac;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacAccepts;
import static me.supcheg.javafile.typed.JavacVerdict.unlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of lambdas (§6.2, §6.4, §9.1), which Java types
/// cannot express: what a lambda body may capture, what it may throw, and
/// where the lambda may be used. Each is rejected where the lambda — or the
/// statement of its block — is built, and told against javac
/// ([JavacVerdict]).
class LambdaChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static final Supplier_<Integer> INTS = new Supplier_<>(Integer_.TOKEN);
    private static final Callable_<Integer> INT_CALL = new Callable_<>(Integer_.TOKEN);
    private static final Function_<Integer, Integer> INT_FN = new Function_<>(Integer_.TOKEN, Integer_.TOKEN);
    private static final Supplier_<Supplier<Integer>> INT_SUPPLIERS = new Supplier_<>(INTS.token);
    private static final Callable_<Supplier<Integer>> SUPPLIER_CALL = new Callable_<>(INTS.token);

    private static final String NOT_EFFECTIVELY_FINAL =
            "local variables referenced from a lambda expression must be final or effectively final";

    /// Declares the class `Probe` with the given members.
    private static JavaFile declare(Consumer<TypedClassBuilder<?>> members) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    /// Declares `static void m()` with the given body.
    private static JavaFile voidMethod(Function<VoidBody, Terminated<Void>> body) {
        return declare(cb -> cb.voidStaticMethod("m", body));
    }

    /// `Integer.valueOf(new StringReader("a").read())`, which throws `IOException`.
    private static Expr<Integer> read() {
        return box(PrimitiveToken.INT, call(new_(StringReader_.new_String, literal("a")), StringReader_.read));
    }

    @Nested
    class Captures {

        @Test
        void aVariableThatIsAssignedIsNotCaptured() {
            asJavac(
                    () -> voidMethod(b -> b.letVar(
                            Integer_.TOKEN,
                            box(PrimitiveToken.INT, literal(0)),
                            v -> b.exec(assign(v, box(PrimitiveToken.INT, literal(1))))
                                    .let(INTS.token, lambda(INTS.sam, () -> v), _ -> b.end()))),
                    "the mutable local variable of type java.lang.Integer of the body of static method m is used"
                            + " in the lambda body in body of static method m, across a lambda boundary: a lambda"
                            + " can capture only effectively final variables, and a MutVar is assignable; copy it"
                            + " into a let first (§6.2)",
                    "static void m() { Integer v = 0; v = 1; Supplier<Integer> s = () -> v; }",
                    NOT_EFFECTIVELY_FINAL);
        }

        @Test
        void aMutVarThatIsNeverAssignedIsNotCapturedThoughJavacTakesItForEffectivelyFinal() {
            unlikeJavac(
                    () -> voidMethod(b -> b.letVar(
                            Integer_.TOKEN,
                            box(PrimitiveToken.INT, literal(0)),
                            v -> b.let(INTS.token, lambda(INTS.sam, () -> v), _ -> b.end()))),
                    "across a lambda boundary",
                    "static void m() { Integer v = 0; Supplier<Integer> s = () -> v; }");
        }

        @Test
        void aVariableOfTheCodeAroundIsNotAssignedInABlockLambda() {
            asJavac(
                    () -> voidMethod(b -> b.letVar(
                            PrimitiveToken.INT,
                            literal(0),
                            v -> b.let(
                                    Runnable_.TOKEN,
                                    lambdaBlock(
                                            Runnable_.sam,
                                            lb -> lb.exec(assign(v, literal(1))).end()),
                                    _ -> b.end()))),
                    "across a lambda boundary",
                    "static void m() { int v = 0; Runnable r = () -> { v = 1; }; }",
                    NOT_EFFECTIVELY_FINAL);
        }

        @Test
        void theVariableOfAForLoopIsNotCaptured() {
            asJavac(
                    () -> voidMethod(b -> b.for_(
                                    PrimitiveToken.INT,
                                    literal(0),
                                    i -> ltInt(i, literal(3)),
                                    i -> assign(i, addInt(i, literal(1))),
                                    (loop, i, _) -> loop.let(
                                            INTS.token, lambda(INTS.sam, () -> box(PrimitiveToken.INT, i)), _ -> loop))
                            .end()),
                    "the loop variable of type int of the body of for_ in body of static method m is used in the"
                            + " lambda body in body of for_ in body of static method m, across a lambda boundary",
                    "static void m() { for (int i = 0; i < 3; i = i + 1) { Supplier<Integer> s = () -> i; } }",
                    NOT_EFFECTIVELY_FINAL);
        }

        @Test
        void anImmutableVariableAParameterAndAVariableOfAForEachLoopAreCaptured() {
            asJavacAccepts(
                    () -> declare(cb -> cb.voidStaticMethod(
                            "m",
                            new List_<>(Integer_.TOKEN).token,
                            (b, list) -> b.let(
                                    Integer_.TOKEN,
                                    box(PrimitiveToken.INT, literal(0)),
                                    v -> b.let(
                                            INTS.token,
                                            lambda(INTS.sam, () -> v),
                                            _ -> b.forEach(
                                                            Integer_.TOKEN,
                                                            list,
                                                            (loop, each, _) -> loop.let(
                                                                    INT_SUPPLIERS.token,
                                                                    lambda(
                                                                            INT_SUPPLIERS.sam,
                                                                            () -> lambda(INTS.sam, () -> each)),
                                                                    _ -> loop))
                                                    .end())))),
                    """
                    static void m(List<Integer> list) {
                        Integer v = 0;
                        Supplier<Integer> s = () -> v;
                        for (Integer each : list) { Supplier<Supplier<Integer>> t = () -> () -> each; }
                    }
                    """);
        }

        @Test
        void aLambdaBodyDoesNotBreakALoopAroundTheLambda() {
            asJavac(
                    () -> voidMethod(b -> b.while_(
                                    literal(true),
                                    (loop, ctl) -> loop.let(
                                            Runnable_.TOKEN,
                                            lambdaBlock(Runnable_.sam, lb -> lb.break_(ctl)),
                                            _ -> loop))
                            .end()),
                    "break_ in the lambda body in body of while_ in body of static method m targets the loop of"
                            + " the body of while_ in body of static method m across a lambda boundary: a lambda"
                            + " body cannot break or continue an enclosing loop (§6.3)",
                    "static void m() { while (true) { Runnable r = () -> { break; }; } }",
                    "break outside switch or loop");
        }

        @Test
        void aLoopInALambdaBodyIsBrokenAndContinued() {
            asJavacAccepts(
                    () -> voidMethod(b -> b.let(
                            Runnable_.TOKEN,
                            lambdaBlock(
                                    Runnable_.sam,
                                    lb -> lb.while_(literal(true), (loop, ctl) -> loop.break_(ctl))
                                            .end()),
                            _ -> b.end())),
                    "static void m() { Runnable r = () -> { while (true) { break; } }; }");
        }

        @Test
        void theFailureIsWhereTheLambdaIsMade() {
            AtomicReference<String> reached = new AtomicReference<>("nothing");

            assertThatIllegalStateException()
                    .isThrownBy(() -> voidMethod(b -> b.letVar(PrimitiveToken.INT, literal(0), v -> {
                        reached.set("the variable");
                        Expr<Supplier<Integer>> made = lambda(INTS.sam, () -> box(PrimitiveToken.INT, v));
                        reached.set("the lambda");
                        return b.let(INTS.token, made, _ -> b.end());
                    })));

            assertThat(reached).hasValue("the variable");
        }
    }

    @Nested
    class Scoping {

        @Test
        void aParameterOfALambdaIsNotUsedOutsideTheLambda() {
            AtomicReference<Var<Integer>> escaped = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.staticMethod("m", Integer_.TOKEN, b -> {
                        lambda(INT_FN.sam, v -> {
                            escaped.set(v);
                            return v;
                        });
                        return b.return_(escaped.get());
                    })))
                    .withMessageContaining("the lambda parameter of type java.lang.Integer declared in the lambda"
                            + " body in body of static method m is used in the body of static method m, which is"
                            + " not inside that block");
        }

        @Test
        void aParameterOfALambdaIsUsedInALambdaInsideIt() {
            asJavacAccepts(
                    () -> voidMethod(b -> b.let(
                            new Function_<>(Integer_.TOKEN, INTS.token).token,
                            lambda(
                                    new Function_<>(Integer_.TOKEN, INTS.token).sam,
                                    v -> lambdaBlock(INTS.sam, lb -> lb.return_(v))),
                            _ -> b.end())),
                    "static void m() { Function<Integer, Supplier<Integer>> f = (Integer v) -> () -> { return v; }; }");
        }

        @Test
        void aLambdaIsNotUsedOutsideTheBlockItIsBuiltIn() {
            AtomicReference<Expr<Supplier<Integer>>> escaped = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.staticMethod(
                            "m",
                            INTS.token,
                            Integer_.TOKEN,
                            (b, p) -> b.if_(
                                            literal(true),
                                            t -> t.let(Integer_.TOKEN, p, v -> {
                                                escaped.set(lambda(INTS.sam, () -> v));
                                                return t;
                                            }))
                                    .return_(escaped.get()))))
                    .withMessageContaining("a lambda built in the then-branch of if_ in body of static method m is"
                            + " used in the body of static method m, which is not inside that block: the variables"
                            + " it captures are out of scope there (§6.2)");
        }

        @Test
        void theBuilderOfTheBlockAroundIsNotUsedInALambda() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> voidMethod(b -> b.let(
                            Runnable_.TOKEN,
                            lambdaBlock(Runnable_.sam, lb -> {
                                b.exec(call(literal("a"), String_.length));
                                return lb.end();
                            }),
                            _ -> b.end())))
                    .withMessageContaining("the body of static method m is not the innermost open scope");
            assertThatIllegalStateException()
                    .isThrownBy(() -> voidMethod(b -> b.let(
                            INTS.token,
                            lambda(INTS.sam, () -> {
                                b.exec(call(literal("a"), String_.length));
                                return box(PrimitiveToken.INT, literal(1));
                            }),
                            _ -> b.end())))
                    .withMessageContaining("the body of static method m is not the innermost open scope");
        }

        @Test
        void aBlockLambdaEndsWithItsOwnToken() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.staticMethod("m", INTS.token, b -> {
                        Body<Integer> elsewhere = Body.root("body of another method");
                        Terminated<Integer> other =
                                Scopes.within(elsewhere, () -> elsewhere.return_(box(PrimitiveToken.INT, literal(1))));
                        return b.return_(lambdaBlock(INTS.sam, _ -> other));
                    })))
                    .withMessageContaining("the Terminated token handed back for the lambda body in body of static"
                            + " method m was issued by the body of another method");
        }
    }

    @Nested
    class CheckedExceptions {

        @Test
        void aLambdaBodyThrowsNoCheckedExceptionTheMethodOfItsInterfaceDoesNotDeclare() {
            asJavac(
                    () -> voidMethod(b -> b.let(INTS.token, lambda(INTS.sam, LambdaChecksTest::read), _ -> b.end())),
                    "int java.io.StringReader.read() in the lambda body in body of static method m can throw the"
                            + " checked exception java.io.IOException, which no catch_ of an enclosing try catches"
                            + " and the method of the functional interface of the lambda does not declare"
                            + " (declared: nothing): catch it inside the lambda: the code around a lambda catches"
                            + " nothing thrown in it (JLS 11.2.3)",
                    "static void m() { Supplier<Integer> s = () -> new StringReader(\"a\").read(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aStatementOfABlockLambdaIsCheckedAsItIsBuilt() {
            asJavac(
                    () -> voidMethod(b -> b.let(
                            Runnable_.TOKEN,
                            lambdaBlock(Runnable_.sam, lb -> lb.throw_(new_(IOException_.new_String, literal("no")))),
                            _ -> b.end())),
                    "throw_ in the lambda body in body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    "static void m() { Runnable r = () -> { throw new IOException(\"no\"); }; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void theThrowsClauseOfTheMemberAroundTheLambdaCoversNothingOfIt() {
            asJavac(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .voidStaticMethod(
                                    "m",
                                    b -> b.let(INTS.token, lambda(INTS.sam, LambdaChecksTest::read), _ -> b.end()))),
                    "the method of the functional interface of the lambda does not declare",
                    "static void m() throws IOException { Supplier<Integer> s = () -> new StringReader(\"a\").read(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aCatchAroundTheLambdaCoversNothingOfIt() {
            asJavac(
                    () -> voidMethod(b -> b.try_(
                                    h -> h.catch_(IOException_.TOKEN, (handler, _) -> handler.end()),
                                    t -> t.let(INTS.token, lambda(INTS.sam, LambdaChecksTest::read), _ -> t))
                            .end()),
                    "the method of the functional interface of the lambda does not declare",
                    """
                    static void m() {
                        try { Supplier<Integer> s = () -> new StringReader("a").read(); } catch (IOException e) { }
                    }
                    """,
                    "unreported exception java.io.IOException");
        }

        @Test
        void theMethodOfTheFunctionalInterfaceDeclaresWhatTheBodyThrows() {
            asJavacAccepts(
                    () -> voidMethod(b -> b.let(
                            INT_CALL.token,
                            lambda(INT_CALL.sam, LambdaChecksTest::read),
                            _ -> b.let(
                                    INT_CALL.token,
                                    lambdaBlock(INT_CALL.sam, lb -> lb.return_(read())),
                                    _ -> b.end()))),
                    """
                    static void m() {
                        Callable<Integer> c = () -> new StringReader("a").read();
                        Callable<Integer> d = () -> { return new StringReader("a").read(); };
                    }
                    """);
        }

        @Test
        void aLambdaInALambdaIsCoveredByItsOwnInterfaceAlone() {
            asJavac(
                    () -> voidMethod(b -> b.let(
                            SUPPLIER_CALL.token,
                            lambda(SUPPLIER_CALL.sam, () -> lambda(INTS.sam, LambdaChecksTest::read)),
                            _ -> b.end())),
                    "in the lambda body in lambda body in body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    "static void m() { Callable<Supplier<Integer>> c = () -> () -> new StringReader(\"a\").read(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aMethodThatThrowsItsTypeArgumentDeclaresWhatTheArgumentIs() {
            Thrower_<IOException> io = new Thrower_<>(IOException_.TOKEN);
            Thrower_<RuntimeException> unchecked = new Thrower_<>(RuntimeException_.TOKEN);

            asJavacAccepts(
                    () -> voidMethod(b -> b.let(
                            io.token,
                            lambdaBlock(io.sam, lb -> lb.throw_(new_(IOException_.new_String, literal("no")))),
                            _ -> b.end())),
                    "interface Thrower<X extends Throwable> { void run() throws X; }\n"
                            + "static void m() { Thrower<IOException> t = () -> { throw new IOException(\"no\"); }; }");
            asJavac(
                    () -> voidMethod(b -> b.let(
                            unchecked.token,
                            lambdaBlock(unchecked.sam, lb -> lb.throw_(new_(IOException_.new_String, literal("no")))),
                            _ -> b.end())),
                    "the method of the functional interface of the lambda does not declare (declared:"
                            + " java.lang.RuntimeException)",
                    "interface Thrower<X extends Throwable> { void run() throws X; }\n"
                            + "static void m() { Thrower<RuntimeException> t = () -> { throw new IOException(\"no\"); }; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aLambdaOfAFieldInitializerIsCheckedAsAnyOther() {
            asJavac(
                    () -> declare(cb -> cb.field("f", INTS.token, lambda(INTS.sam, LambdaChecksTest::read))),
                    "in the lambda body can throw the checked exception java.io.IOException",
                    "Supplier<Integer> f = () -> new StringReader(\"a\").read();",
                    "unreported exception java.io.IOException");
            asJavacAccepts(
                    () -> declare(cb -> cb.field("f", INT_CALL.token, lambda(INT_CALL.sam, LambdaChecksTest::read))),
                    "Callable<Integer> f = () -> new StringReader(\"a\").read();");
        }
    }
}
