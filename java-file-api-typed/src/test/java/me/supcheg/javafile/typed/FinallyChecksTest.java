package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.geInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacAccepts;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacWarns;
import static me.supcheg.javafile.typed.JavacVerdict.javacWarns;

/// A `finally` block completes normally ([FinallyBody]). javac accepts one
/// that does not and warns of it (`-Xlint:finally`: *finally clause cannot
/// complete normally*), and the typed layer renders no code javac warns of.
///
/// The forms javac warns of are those whose `finally` block cannot complete
/// normally (JLS 14.22), whatever makes it so. Each is told against javac
/// here and closed one of two ways: a statement that ends the `finally`
/// block itself is no method of [FinallyBody], so it does not compile
/// ([NegativeCompileTest]); a statement of it that holds blocks and cannot
/// complete normally is rejected where it is built, as in any block. What
/// javac does not warn of — a branch that ends, a loop of the `finally`
/// block that is broken, an exception thrown and caught in it — is accepted
/// and renders without a warning.
class FinallyChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static final String WARNING = "finally clause cannot complete normally";

    /// Declares `static int m(Signal s)` of a class `Probe` with the given body.
    private static JavaFile intMethod(BiFunction<Body<Prim.Int>, Var<Signal>, Terminated<Prim.Int>> body) {
        Consumer<TypedClassBuilder<?>> members =
                cb -> cb.throwing(IOException_.TOKEN).staticMethod("m", PrimitiveToken.INT, Signal_.TOKEN, body);
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    private static Expr<Prim.Bool> isRed(Expr<Signal> s) {
        return eqRef(s, enumConstant(Signal_.RED));
    }

    @Nested
    class NotRepresentable {

        /// The `finally` blocks that end by a statement of their own: each is a method [FinallyBody]
        /// does not have ([NegativeCompileTest]).
        @ParameterizedTest
        @ValueSource(
                strings = {
                    "static int m(Signal s) { try { return 1; } finally { return 2; } }",
                    "static int m(Signal s) { try { return 1; } finally { throw new IllegalStateException(); } }",
                    "static int m(Signal s) { while (true) { try { return 1; } finally { break; } } return 0; }",
                    "static int m(Signal s) { while (s == Signal.RED) { try { return 1; } finally { continue; } } return 0; }",
                    "static int m(Signal s) { return switch (s) { default -> { try { yield 1; } finally { yield 2; } } }; }",
                    "static int m(Signal s) { try { return 1; } finally { while (true) { } } }",
                    "static int m(Signal s) { try { return 1; } finally { if (s == Signal.RED) { return 2; } else { return 3; } } }",
                    "static int m(Signal s) { try { return 1; } finally { try { return 2; } finally { } } }"
                })
        void javacWarnsOfAFinallyBlockThatEnds(String java) {
            javacWarns(java, WARNING);
        }
    }

    @Nested
    class RejectedWhereItIsBuilt {

        @Test
        void aLoopOfAFinallyBlockThatNeverCompletes() {
            asJavacWarns(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.finally_(f -> f.while_(literal(true), (_, _) -> {})), t -> t.return_(literal(1)))),
                    "while_ with a constant true condition and no break_ in the finally block of tryTerminated in"
                            + " body of static method m cannot complete normally",
                    "static int m(Signal s) { try { return 1; } finally { while (true) { } } }",
                    WARNING);
        }

        @Test
        void anIfOfAFinallyBlockBothOfWhoseBranchesEnd() {
            asJavacWarns(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.finally_(
                                    f -> f.if_(isRed(s), t -> t.return_(literal(2)), e -> e.return_(literal(3)))),
                            t -> t.return_(literal(1)))),
                    "if_ whose branches both end in the finally block of tryTerminated in body of static method m"
                            + " cannot complete normally",
                    "static int m(Signal s) { try { return 1; } finally { if (s == Signal.RED) { return 2; } else { return 3; } } }",
                    WARNING);
        }

        @Test
        void aTryOfAFinallyBlockWhoseEveryBlockEnds() {
            asJavacWarns(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.finally_(f -> f.try_(g -> g.finally_(_ -> {}), t -> t.return_(literal(2)))),
                            t -> t.return_(literal(1)))),
                    "try_ whose try block and every catch_ end in the finally block of tryTerminated in body of"
                            + " static method m cannot complete normally",
                    "static int m(Signal s) { try { return 1; } finally { try { return 2; } finally { } } }",
                    WARNING);
        }
    }

    @Nested
    class Accepted {

        @Test
        void aBranchOfAFinallyBlockEnds() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.finally_(f -> f.if_(isRed(s), t -> t.return_(literal(2)))),
                            t -> t.return_(literal(1)))),
                    "static int m(Signal s) { try { return 1; } finally { if (s == Signal.RED) { return 2; } } }");
        }

        @Test
        void aBranchOfAFinallyBlockThrowsWhatTheMemberDeclares() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.try_(
                                    h -> h.finally_(f -> f.if_(
                                            isRed(s), t -> t.throw_(new_(IOException_.new_String, literal("red"))))),
                                    t -> t.exec(call(literal("a"), String_.length)))
                            .return_(literal(1))),
                    """
                    static int m(Signal s) throws IOException {
                        try { "a".length(); } finally { if (s == Signal.RED) { throw new IOException("red"); } }
                        return 1;
                    }
                    """);
        }

        @Test
        void aLoopOfAFinallyBlockIsBrokenAndContinuedAndALoopAroundTheTryIsToo() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.letVar(
                            PrimitiveToken.INT,
                            literal(0),
                            n -> b.while_(
                                            isRed(s),
                                            (outer, outerCtl) -> outer.try_(
                                                    h -> h.finally_(f -> f.while_(
                                                                    literal(true),
                                                                    (loop, ctl) -> loop.if_(
                                                                                    geInt(n, literal(3)),
                                                                                    t -> t.break_(ctl))
                                                                            .exec(assign(n, addInt(n, literal(1))))
                                                                            .continue_(ctl))
                                                            .if_(geInt(n, literal(9)), t -> t.break_(outerCtl))),
                                                    t -> t.exec(call(literal("a"), String_.length))))
                                    .return_(n))),
                    """
                    static int m(Signal s) {
                        int n = 0;
                        while (s == Signal.RED) {
                            try { "a".length(); }
                            finally {
                                while (true) { if (n >= 3) { break; } n = n + 1; continue; }
                                if (n >= 9) { break; }
                            }
                        }
                        return n;
                    }
                    """);
        }

        @Test
        void anExceptionThrownInAFinallyBlockIsCaughtInIt() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.tryTerminated(
                            h -> h.finally_(f -> f.try_(
                                    g -> g.catch_(IOException_.TOKEN, (_, _) -> {}),
                                    t -> t.throw_(new_(IOException_.new_String, literal("caught"))))),
                            t -> t.return_(literal(1)))),
                    """
                    static int m(Signal s) {
                        try { return 1; } finally { try { throw new IOException("caught"); } catch (IOException e) { } }
                    }
                    """);
        }

        @Test
        void aBranchOfAFinallyBlockOfACaseYields() {
            asJavacAccepts(
                    () -> intMethod((b, s) -> b.return_(switch_(
                            Signal_.TOKEN,
                            s,
                            PrimitiveToken.INT,
                            c -> c.default_(y -> y.tryTerminated(
                                    h -> h.finally_(f -> f.if_(isRed(s), t -> t.yield_(literal(2)))),
                                    t -> t.yield_(literal(1))))))),
                    """
                    static int m(Signal s) {
                        return switch (s) { default -> { try { yield 1; } finally { if (s == Signal.RED) { yield 2; } } } };
                    }
                    """);
        }
    }
}
