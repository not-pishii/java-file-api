package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntSupplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.lambdaBlock;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.JavacVerdict.asJavac;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacAccepts;
import static me.supcheg.javafile.typed.JavacVerdict.unlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of the `switch` expression over an enum (§6.3,
/// §9.2), which Java types cannot express: the cases are distinct and
/// exhaustive, the `switch` has a result, a block of a case jumps nowhere
/// out of the `switch`, and the `switch` is used where its blocks were
/// checked. Each is rejected where the case, the `switch` or the statement
/// is built, and told against javac ([JavacVerdict]).
class SwitchChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// Declares the class `Probe` with the given members.
    private static void declare(Consumer<TypedClassBuilder<?>> members) {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    /// Declares `static int m(Signal s)` with the given body.
    private static void intMethod(BiFunction<Body<Prim.Int>, Var<Signal>, Terminated<Prim.Int>> body) {
        declare(cb -> cb.staticMethod("m", PrimitiveToken.INT, Signal_.TOKEN, body));
    }

    /// `switch (s) { cases }` of type `int`.
    private static Expr<Prim.Int> intSwitch(Expr<Signal> s, Consumer<SwitchCases<Signal, Prim.Int>> cases) {
        return switch_(s, Signal_.TOKEN, PrimitiveToken.INT, cases);
    }

    /// `new StringReader("a").read()`, which throws `IOException`.
    private static Invocation<Prim.Int> read() {
        return call(new_(StringReader_.new_String, literal("a")), StringReader_.read);
    }

    @Nested
    class Cases {

        @Test
        void aConstantHasOneCase() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(
                            intSwitch(s, c -> c.case_(Signal_.RED, literal(1)).case_(Signal_.RED, literal(2))))),
                    "case_ RED of the switch_ over me.supcheg.javafile.typed.fixtures.Signal in the body of static"
                            + " method m: the constant RED has a case already; a constant is the label of one"
                            + " case (JLS 14.11.1)",
                    "static int m(Signal s) { return switch (s) { case RED -> 1; case RED -> 2; default -> 3; }; }",
                    "duplicate case label");
        }

        @Test
        void aConstantIsOnceInACaseOfSeveral() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(
                            s,
                            c -> c.case_(Signal_.AMBER, literal(1))
                                    .case_(List.of(Signal_.RED, Signal_.AMBER), literal(2))))),
                    "case_ RED, AMBER of the switch_ over me.supcheg.javafile.typed.fixtures.Signal in the body of"
                            + " static method m: the constant AMBER has a case already",
                    "static int m(Signal s) { return switch (s) { case AMBER -> 1; case RED, AMBER -> 2; default -> 3; }; }",
                    "duplicate case label");
            asJavac(
                    () -> intMethod((b, s) ->
                            b.return_(intSwitch(s, c -> c.case_(List.of(Signal_.RED, Signal_.RED), literal(2))))),
                    "the constant RED has a case already",
                    "static int m(Signal s) { return switch (s) { case RED, RED -> 2; default -> 3; }; }",
                    "duplicate case label");
        }

        @Test
        void theFailureIsAtTheCaseThatRepeatsAConstant() {
            AtomicReference<String> reached = new AtomicReference<>("nothing");

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.return_(intSwitch(s, c -> {
                        c.case_(Signal_.RED, literal(1));
                        reached.set("the first case");
                        c.case_(Signal_.RED, literal(2));
                        reached.set("the second case");
                    }))));

            assertThat(reached).hasValue("the first case");
        }

        @Test
        void aSwitchWithoutADefaultHasACaseOfEveryConstant() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(s, c -> c.case_(Signal_.RED, literal(1))))),
                    "the switch_ over me.supcheg.javafile.typed.fixtures.Signal in the body of static method m is"
                            + " not exhaustive: me.supcheg.javafile.typed.fixtures.Signal has the constants AMBER,"
                            + " GREEN, which have no case_, and there is no default_; a switch expression covers"
                            + " every value of its selector (JLS 15.28.1)",
                    "static int m(Signal s) { return switch (s) { case RED -> 1; }; }",
                    "the switch expression does not cover all possible input values");
        }

        @Test
        void aSwitchWithoutACaseIsNotExhaustive() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(s, _ -> {}))),
                    "is not exhaustive",
                    "static int m(Signal s) { return switch (s) { }; }",
                    "switch expression does not have any case clauses");
        }

        @Test
        void theFailureIsWhereTheSwitchIsMade() {
            AtomicReference<String> reached = new AtomicReference<>("nothing");

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> {
                        reached.set("the body");
                        Expr<Prim.Int> made = intSwitch(s, c -> c.case_(Signal_.RED, literal(1)));
                        reached.set("the switch");
                        return b.return_(made);
                    }));

            assertThat(reached).hasValue("the body");
        }

        @Test
        void aDefaultOrACaseOfEveryConstantOrBothMakeItExhaustive() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.let(
                            PrimitiveToken.INT,
                            intSwitch(s, c -> c.default_(literal(0))),
                            _ -> b.let(
                                    PrimitiveToken.INT,
                                    intSwitch(
                                            s,
                                            c -> c.case_(List.of(Signal_.RED, Signal_.AMBER), literal(1))
                                                    .case_(Signal_.GREEN, literal(2))),
                                    _ -> b.return_(intSwitch(
                                            s,
                                            c -> c.case_(List.of(Signal_.RED, Signal_.AMBER, Signal_.GREEN), literal(1))
                                                    .default_(literal(2))))))),
                    """
                    static int m(Signal s) {
                        int a = switch (s) { default -> 0; };
                        int b = switch (s) { case RED, AMBER -> 1; case GREEN -> 2; };
                        return switch (s) { case RED, AMBER, GREEN -> 1; default -> 2; };
                    }
                    """);
        }

        @Test
        void aSwitchHasAResult() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(
                            s,
                            c -> c.case_(
                                            Signal_.RED,
                                            y -> y.throw_(new_(IllegalStateException_.new_String, literal("r"))))
                                    .default_(y -> y.loopForever(loop -> {}))))),
                    "the switch_ over me.supcheg.javafile.typed.fixtures.Signal in the body of static method m has"
                            + " no result: every case is a block that never yields; a switch expression has at"
                            + " least one result expression (JLS 15.28.1)",
                    """
                    static int m(Signal s) {
                        return switch (s) { case RED -> { throw new IllegalStateException("r"); } default -> { while (true) { } } };
                    }
                    """,
                    "switch expression does not have any result expressions");
        }

        @Test
        void aYieldOfASwitchInsideABlockIsNotAResultOfTheSwitchAround() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.return_(intSwitch(
                            s,
                            c -> c.default_(y -> y.let(
                                    PrimitiveToken.INT,
                                    intSwitch(s, inner -> inner.default_(z -> z.yield_(literal(1)))),
                                    _ -> y.throw_(new_(IllegalStateException_.new_String, literal("r")))))))))
                    .withMessageContaining("has no result");
        }

        @Test
        void theDefaultIsTheLastCaseThoughJavaLetsItStandAnywhere() {
            unlikeJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(s, c -> {
                        c.default_(literal(0));
                        c.case_(Signal_.RED, literal(1));
                    }))),
                    "case_ RED of the switch_ over me.supcheg.javafile.typed.fixtures.Signal in the body of static"
                            + " method m after its default_: the default_ case is the last",
                    "static int m(Signal s) { return switch (s) { default -> 0; case RED -> 1; }; }");
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.return_(intSwitch(s, c -> {
                        c.default_(literal(0));
                        c.default_(literal(1));
                    }))))
                    .withMessageContaining("default_ of the switch_")
                    .withMessageContaining("after its default_");
        }

        @Test
        void aCaseIsNotAddedFromInsideTheBlockOfAnother() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.return_(intSwitch(
                            s,
                            c -> c.case_(Signal_.RED, y -> {
                                        c.case_(Signal_.AMBER, literal(1));
                                        return y.yield_(literal(2));
                                    })
                                    .default_(literal(0))))))
                    .withMessageContaining("case_ AMBER of the switch_ over me.supcheg.javafile.typed.fixtures.Signal"
                            + " in the body of static method m while its case_ RED is being built");
        }

        @Test
        void aCaseIsNotAddedOnceTheSwitchIsMade() {
            AtomicReference<SwitchCases<Signal, Prim.Int>> escaped = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> {
                        Expr<Prim.Int> made = intSwitch(s, c -> {
                            escaped.set(c);
                            c.case_(List.of(Signal_.RED, Signal_.AMBER, Signal_.GREEN), literal(1));
                        });
                        escaped.get().default_(literal(2));
                        return b.return_(made);
                    }))
                    .withMessageContaining("after the switch was made");
        }

        @Test
        void aCaseHasAConstant() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> intMethod((b, s) -> b.return_(intSwitch(s, c -> c.case_(List.of(), literal(1))))))
                    .withMessageContaining("without a constant: a case has at least one");
        }

        @Test
        void aConstantIsOneOfTheEnumOfTheSwitch() {
            // another fact of an enum of the same phantom type, as only UnsafeFacts makes one
            EnumToken<Signal> other = UnsafeFacts.enumToken(
                    Types.of(ClassDesc.of("fixtures", "Light")), List.of("ON", "OFF"), MethodTable.EMPTY);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> intMethod((b, s) ->
                            b.return_(intSwitch(s, c -> c.case_(other.constant("ON"), literal(1))))))
                    .withMessageContaining("fixtures.Light.ON is not a constant of the"
                            + " me.supcheg.javafile.typed.fixtures.Signal this fact of the enum has (RED, AMBER,"
                            + " GREEN)");
        }

        @Test
        void primitiveAndReferenceResultsDoNotMixThoughJavaBoxesThem() {
            unlikeJavac(
                    IllegalArgumentException.class,
                    () -> declare(cb -> cb.staticMethod(
                            "m",
                            Object_.TOKEN,
                            Signal_.TOKEN,
                            (b, s) ->
                                    b.return_(switch_(s, Signal_.TOKEN, Object_.TOKEN, c -> c.default_(literal(1)))))),
                    "switch_ of type java.lang.Object has a result of type int: switch_ does not mix primitive and"
                            + " reference results",
                    "static Object m(Signal s) { return switch (s) { default -> 1; }; }");
            unlikeJavac(
                    IllegalArgumentException.class,
                    () -> declare(cb -> cb.staticMethod(
                            "m",
                            Object_.TOKEN,
                            Signal_.TOKEN,
                            (b, s) -> b.return_(switch_(
                                    s, Signal_.TOKEN, Object_.TOKEN, c -> c.default_(y -> y.yield_(literal(1))))))),
                    "switch_ of type java.lang.Object has a result of type int",
                    "static Object m(Signal s) { return switch (s) { default -> { yield 1; } }; }");
        }
    }

    @Nested
    class Blocks {

        @Test
        void aBlockDoesNotBreakALoopAroundTheSwitch() {
            asJavac(
                    () -> intMethod((b, s) -> b.while_(
                                    literal(true),
                                    (loop, ctl) -> loop.let(
                                            PrimitiveToken.INT,
                                            intSwitch(
                                                    s,
                                                    c -> c.case_(Signal_.RED, y -> y.break_(ctl))
                                                            .default_(literal(1))),
                                            _ -> loop))
                            .return_(literal(0))),
                    "break_ in the block of case_ RED of switch_ in body of while_ in body of static method m"
                            + " targets the loop of the body of while_ in body of static method m out of a switch"
                            + " expression: a block of a switch expression cannot break or continue a loop around"
                            + " the switch (JLS 15.28.1)",
                    """
                    static int m(Signal s) {
                        while (true) { int v = switch (s) { case RED -> { break; } default -> 1; }; }
                    }
                    """,
                    "attempt to break out of a switch expression");
        }

        @Test
        void aBlockDoesNotContinueALoopAroundTheSwitch() {
            asJavac(
                    () -> intMethod((b, s) -> b.while_(
                                    literal(true),
                                    (loop, ctl) -> loop.let(
                                            PrimitiveToken.INT,
                                            intSwitch(
                                                    s,
                                                    c -> c.default_(y -> y.if_(literal(true), t -> t.continue_(ctl))
                                                            .yield_(literal(1)))),
                                            _ -> loop))
                            .return_(literal(0))),
                    "continue_ in the then-branch of if_ in block of default_ of switch_ in body of while_ in body"
                            + " of static method m targets the loop of the body of while_ in body of static method"
                            + " m out of a switch expression",
                    """
                    static int m(Signal s) {
                        while (true) { int v = switch (s) { default -> { if (true) { continue; } yield 1; } }; }
                    }
                    """,
                    "attempt to continue out of a switch expression");
        }

        @Test
        void aBlockReadsAndAssignsTheVariablesOfTheCodeAroundTheSwitch() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.letVar(
                            PrimitiveToken.INT,
                            literal(0),
                            n -> b.return_(intSwitch(
                                    s,
                                    c -> c.default_(
                                            y -> y.exec(assign(n, literal(1))).yield_(n)))))),
                    "static int m(Signal s) { int n = 0; return switch (s) { default -> { n = 1; yield n; } }; }");
        }

        @Test
        void theBuilderOfTheBlockAroundIsNotUsedInABlockOfTheSwitch() {
            assertThatIllegalStateException()
                    .isThrownBy(() ->
                            intMethod((b, s) -> b.return_(intSwitch(s, c -> c.default_(y -> b.return_(literal(1)))))))
                    .withMessageContaining("the body of static method m is not the innermost open scope");
        }

        @Test
        void aBlockEndsWithItsOwnToken() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> {
                        AtomicReference<Terminated<Prim.Int>> first = new AtomicReference<>();
                        return b.return_(intSwitch(
                                s,
                                c -> c.case_(Signal_.RED, y -> {
                                            first.set(y.yield_(literal(1)));
                                            return first.get();
                                        })
                                        .default_(_ -> first.get())));
                    }))
                    .withMessageContaining("the Terminated token handed back for the block of default_ of switch_ in"
                            + " body of static method m was issued by the block of case_ RED of switch_ in body of"
                            + " static method m");
        }

        @Test
        void aCheckedExceptionOfABlockIsCaughtOrDeclaredAroundTheSwitch() {
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(s, c -> c.default_(y -> y.yield_(read()))))),
                    "int java.io.StringReader.read() in the block of default_ of switch_ in body of static method m"
                            + " can throw the checked exception java.io.IOException, which no catch_ of an"
                            + " enclosing try catches and the method does not declare",
                    "static int m(Signal s) { return switch (s) { default -> { yield new StringReader(\"a\").read(); } }; }",
                    "unreported exception java.io.IOException");
            asJavac(
                    () -> intMethod((b, s) -> b.return_(intSwitch(s, c -> c.default_(read())))),
                    "in the body of static method m can throw the checked exception java.io.IOException",
                    "static int m(Signal s) { return switch (s) { default -> new StringReader(\"a\").read(); }; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aCatchAroundTheSwitchCatchesWhatItsBlocksThrowAndIsNotTakenForOneThatCatchesNothing() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (handler, _) -> handler.return_(literal(-1))),
                            t -> t.return_(intSwitch(s, c -> c.default_(y -> y.yield_(read())))))),
                    """
                    static int m(Signal s) {
                        try { return switch (s) { default -> { yield new StringReader("a").read(); } }; }
                        catch (IOException e) { return -1; }
                    }
                    """);
        }

        @Test
        void aSwitchOfAFieldInitializerThrowsNoCheckedException() {
            unlikeJavac(
                    () -> declare(cb -> {
                        cb.throwing(IOException_.TOKEN).constructor((b, _) -> b.end());
                        cb.field(
                                "f",
                                PrimitiveToken.INT,
                                switch_(
                                        Expressions.enumConstant(Signal_.RED),
                                        Signal_.TOKEN,
                                        PrimitiveToken.INT,
                                        c -> c.default_(y -> y.yield_(read()))));
                    }),
                    "int java.io.StringReader.read() in the block of default_ of switch_ can throw the checked"
                            + " exception java.io.IOException, which no catch_ of an enclosing try catches and"
                            + " nothing declares outside of the body of a member",
                    """
                    Probe() throws IOException { }
                    int f = switch (Signal.RED) { default -> { yield new StringReader("a").read(); } };
                    """);
        }
    }

    @Nested
    class Uses {

        @Test
        void aSwitchWithABlockIsNotUsedOutsideTheBlockItIsBuiltIn() {
            AtomicReference<Expr<Prim.Int>> escaped = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.if_(
                                    literal(true),
                                    t -> t.let(PrimitiveToken.INT, literal(1), v -> {
                                        escaped.set(intSwitch(s, c -> c.default_(y -> y.yield_(v))));
                                        return t;
                                    }))
                            .return_(escaped.get())))
                    .withMessageContaining("a switch_ built in the then-branch of if_ in body of static method m is"
                            + " used in the body of static method m, which is not inside that block");
        }

        @Test
        void aSwitchWithABlockIsNotUsedAcrossALambdaBoundary() {
            // the block may assign n, which the lambda body may not
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, s) -> b.letVar(PrimitiveToken.INT, literal(0), n -> {
                        Expr<Prim.Int> built = intSwitch(
                                s,
                                c -> c.default_(
                                        y -> y.exec(assign(n, literal(1))).yield_(literal(1))));
                        return b.let(
                                IntSupplier_.TOKEN, lambda(IntSupplier_.sam, () -> built), _ -> b.return_(literal(0)));
                    })))
                    .withMessageContaining("a switch_ built in the body of static method m is used in the lambda body"
                            + " in body of static method m, across a lambda boundary");
        }

        @Test
        void aSwitchBuiltInALambdaIsCheckedAsCodeOfTheLambda() {
            asJavac(
                    () -> intMethod((b, s) -> b.letVar(
                            PrimitiveToken.INT,
                            literal(0),
                            n -> b.let(
                                    Runnable_.TOKEN,
                                    lambdaBlock(
                                            Runnable_.sam,
                                            lb -> lb.let(
                                                    PrimitiveToken.INT,
                                                    intSwitch(
                                                            s,
                                                            c -> c.default_(y -> y.exec(assign(n, literal(1)))
                                                                    .yield_(literal(1)))),
                                                    _ -> lb.end())),
                                    _ -> b.return_(literal(0))))),
                    "across a lambda boundary: a lambda can capture only effectively final variables",
                    """
                    static int m(Signal s) {
                        int n = 0;
                        Runnable r = () -> { int v = switch (s) { default -> { n = 1; yield 1; } }; };
                        return 0;
                    }
                    """,
                    "local variables referenced from a lambda expression must be final or effectively final");
        }

        @Test
        void aSwitchOfValuesIsAnExpressionOfWhereItIsUsed() {
            AtomicReference<Expr<Prim.Int>> escaped = new AtomicReference<>();

            asJavacAccepts(
                    () -> intMethod((b, s) -> b.if_(
                                    literal(true), t -> escaped.set(intSwitch(s, c -> c.default_(literal(1)))))
                            .return_(escaped.get())),
                    "static int m(Signal s) { if (true) { } return switch (s) { default -> 1; }; }");
        }

        @Test
        void aBlockBuiltOutsideATryIsNotCoveredByItsCatchThoughItIsToJavac() {
            unlikeJavac(
                    () -> intMethod((b, s) -> {
                        Expr<Prim.Int> built = intSwitch(s, c -> c.default_(y -> y.yield_(read())));
                        return b.tryTerminated(
                                h -> h.catch_(IOException_.TOKEN, (handler, _) -> handler.return_(literal(-1))),
                                t -> t.return_(built));
                    }),
                    "in the block of default_ of switch_ in body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    """
                    static int m(Signal s) {
                        try { return switch (s) { default -> { yield new StringReader("a").read(); } }; }
                        catch (IOException e) { return -1; }
                    }
                    """);
        }
    }
}
