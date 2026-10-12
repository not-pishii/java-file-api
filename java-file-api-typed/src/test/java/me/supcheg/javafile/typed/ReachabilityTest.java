package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.EmptyStmt;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.testfacts.java.io.PrintStream_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.System_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Reachability (JLS 14.22) told against javac: a body the typed layer builds
/// is one javac finds no unreachable statement in, and a statement the typed
/// layer refuses to append — after an `if_` whose branches both end, a loop
/// that cannot complete normally, a `try_` whose blocks all end — is one javac
/// rejects in the Java the body stands for.
///
/// A body is that of `static int m(T x)`; `x == x` is a condition javac
/// does not fold.
class ReachabilityTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Reached");

    /// A body of `static int m(T x)`, in Java and as the typed layer builds it.
    ///
    /// @param type `T` as Java writes it
    /// @param token `T`
    /// @param java the statements of the body
    /// @param typed builds the body
    /// @param <T> the type of the parameter
    record Probe<T>(
            String type,
            TypeToken<T> token,
            String java,
            BiFunction<Body<Prim.Int>, Var<T>, Terminated<Prim.Int>> typed) {

        JavaFile build() {
            return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                @Override
                public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                    var m = cb.declareStaticMethod("m", PrimitiveToken.INT, token);
                    var bodies = cb.bodies();
                    bodies.define(m, typed);
                    return bodies;
                }
            });
        }

        /// Whether javac finds an unreachable statement in the body, and nothing else to reject it for.
        boolean unreachableToJavac() {
            Compilation compilation = javac().compile(JavaFileObjects.forSourceString(
                    "Probe",
                    "import java.util.*;\nimport " + Signal.class.getCanonicalName() + ";\nclass Probe {\n"
                            + "static int m(" + type + " x) {\n" + java + "\n}\n}\n"));
            List<String> errors = compilation.errors().stream()
                    .map(error -> error.getMessage(Locale.ROOT))
                    .toList();
            assertThat(errors).allMatch(error -> error.contains("unreachable statement"));
            return !errors.isEmpty();
        }

        @Override
        public String toString() {
            return java;
        }
    }

    private static Probe<Object> ofObject(
            String java, BiFunction<Body<Prim.Int>, Var<Object>, Terminated<Prim.Int>> typed) {
        return new Probe<>("Object", Object_.TOKEN, java, typed);
    }

    private static <T> Expr<Prim.Bool> unknown(Var<T> x) {
        return eqRef(x, x);
    }

    private static Effect print() {
        return voidCall(staticField(System_.out), PrintStream_.println_int, literal(1));
    }

    private static final String PRINT = "System.out.println(1);";

    /// `if (x == x) { <then> } else { return 1; } return 0;`: what follows the `if` is reachable where
    /// its `then` block can complete normally, and there alone.
    private static Probe<Object> thenOf(String java, BiConsumer<Body<Prim.Int>, Var<Object>> then) {
        return ofObject(
                "if (x == x) { " + java + " } else { return 1; } return 0;",
                (b, x) -> b.if_(unknown(x), t -> then.accept(t, x), e -> e.return_(literal(1)))
                        .return_(literal(0)));
    }

    /// `loop: while (true) { <body> } return 0;`: what follows the loop is reachable where its body
    /// holds a `break` of it, at any depth.
    private static Probe<Object> foreverOf(String java, Function3<Body<Prim.Int>, Var<Object>, LoopCtl, ?> body) {
        return ofObject(
                "loop: while (true) { " + java + " } return 0;",
                (b, x) -> b.while_(literal(true), (loop, ctl) -> body.apply(loop, x, ctl))
                        .return_(literal(0)));
    }

    static Stream<Probe<?>> bodies() {
        return Stream.of(lastStatements(), endings(), loops(), tries()).flatMap(probes -> probes);
    }

    /// The statements a block may end with and complete normally.
    private static Stream<Probe<?>> lastStatements() {
        return Stream.of(
                thenOf("int v = 1;", (t, _) -> t.let(PrimitiveToken.INT, literal(1), _ -> t)),
                thenOf(PRINT, (t, _) -> t.exec(print())),
                thenOf(";", (t, _) -> t.add(Unsafe.stmt(new EmptyStmt()))),
                new Probe<>(
                        "List<String>",
                        new List_<>(String_.TOKEN).token,
                        "if (x == x) { for (String s : x) { } } else { return 1; } return 0;",
                        (b, x) -> b.if_(
                                        unknown(x),
                                        t -> t.forEach(String_.TOKEN, x, (_, _, _) -> {}),
                                        e -> e.return_(literal(1)))
                                .return_(literal(0))),
                thenOf("if (x instanceof String s) { }", (t, x) -> t.ifInstanceOf(x, String_.TOKEN, (_, _) -> {})),
                thenOf("if (x == x) { }", (t, x) -> t.if_(unknown(x), _ -> {})),
                thenOf(
                        "if (x == x) { return 2; } else { }",
                        (t, x) -> t.if_(unknown(x), a -> a.return_(literal(2)), _ -> {})),
                thenOf(
                        "if (x == x) { } else { return 2; }",
                        (t, x) -> t.if_(unknown(x), _ -> {}, c -> c.return_(literal(2)))),
                thenOf("while (x == x) { }", (t, x) -> t.while_(unknown(x), (_, _) -> {})),
                thenOf(
                        "for (int i = 0; i < 1; i = i + 1) { }",
                        (t, _) -> t.for_(
                                PrimitiveToken.INT,
                                literal(0),
                                i -> ltInt(i, literal(1)),
                                i -> assign(i, addInt(i, literal(1))),
                                (_, _, _) -> {})),
                thenOf("do { } while (x == x);", (t, x) -> t.doWhile((_, _) -> {}, unknown(x))),
                thenOf(
                        "try { " + PRINT + " } catch (RuntimeException e) { }",
                        (t, _) -> t.try_(
                                h -> h.catch_(RuntimeException_.TOKEN, (_, _) -> {}), body -> body.exec(print()))));
    }

    /// The statements a block ends with and cannot complete normally: an `if_` of two such blocks is
    /// refused, as what follows it is unreachable.
    private static Stream<Probe<?>> endings() {
        return Stream.of(
                thenOf("return 2;", (t, _) -> t.return_(literal(2))),
                thenOf("throw new RuntimeException();", (t, _) -> t.throw_(new_(RuntimeException_.new_))),
                thenOf(
                        "if (x == x) { return 2; } else { return 3; }",
                        (t, x) -> t.ifElse(unknown(x), a -> a.return_(literal(2)), c -> c.return_(literal(3)))),
                thenOf(
                        "if (x instanceof String s) { return 2; } else { return 3; }",
                        (t, x) -> t.ifInstanceOfElse(
                                x, String_.TOKEN, (a, _) -> a.return_(literal(2)), c -> c.return_(literal(3)))),
                thenOf("while (true) { }", (t, _) -> t.loopForever(_ -> {})),
                thenOf(
                        "try { return 2; } catch (RuntimeException e) { return 3; }",
                        (t, _) -> t.tryTerminated(
                                h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(3))),
                                body -> body.return_(literal(2)))),
                ofObject(
                        "while (x == x) { if (x == x) { break; } else { continue; } " + PRINT + " } return 0;",
                        (b, x) -> b.while_(
                                        unknown(x),
                                        (loop, ctl) -> loop.if_(unknown(x), t -> t.break_(ctl), e -> e.continue_(ctl))
                                                .exec(print()))
                                .return_(literal(0))),
                new Probe<>(
                        "Signal",
                        Signal_.TOKEN,
                        "return switch (x) { case RED -> { if (x == x) { yield 1; } else { yield 2; } " + PRINT
                                + " yield 3; } default -> 0; };",
                        (b, x) -> b.return_(switch_(
                                Signal_.TOKEN,
                                x,
                                PrimitiveToken.INT,
                                cases -> cases.case_(
                                                Signal_.RED,
                                                y -> y.if_(
                                                                unknown(x),
                                                                t -> t.yield_(literal(1)),
                                                                e -> e.yield_(literal(2)))
                                                        .exec(print())
                                                        .yield_(literal(3)))
                                        .default_(literal(0))))));
    }

    /// A loop of a constant `true` condition completes normally by a `break` of it alone.
    private static Stream<Probe<?>> loops() {
        return Stream.of(
                foreverOf("", (loop, _, _) -> loop),
                foreverOf(PRINT, (loop, _, _) -> loop.exec(print())),
                foreverOf("break loop;", (loop, _, ctl) -> loop.break_(ctl)),
                foreverOf("if (x == x) { break loop; }", (loop, x, ctl) -> loop.if_(unknown(x), t -> t.break_(ctl))),
                foreverOf(
                        "if (x == x) { } else { break loop; }",
                        (loop, x, ctl) -> loop.if_(unknown(x), _ -> {}, e -> e.break_(ctl))),
                foreverOf(
                        "if (x instanceof String s) { break loop; }",
                        (loop, x, ctl) -> loop.ifInstanceOf(x, String_.TOKEN, (t, _) -> t.break_(ctl))),
                foreverOf(
                        "while (x == x) { break loop; }",
                        (loop, x, ctl) -> loop.while_(unknown(x), (inner, _) -> inner.break_(ctl))),
                // a `break` of another loop is none of this one
                foreverOf(
                        "while (x == x) { break; }",
                        (loop, x, _) -> loop.while_(unknown(x), (inner, innerCtl) -> inner.break_(innerCtl))),
                foreverOf(
                        "if (x == x) { continue loop; }",
                        (loop, x, ctl) -> loop.if_(unknown(x), t -> t.continue_(ctl))),
                foreverOf(
                        "do { if (x == x) { break loop; } } while (x == x);",
                        (loop, x, ctl) ->
                                loop.doWhile((inner, _) -> inner.if_(unknown(x), t -> t.break_(ctl)), unknown(x))),
                foreverOf(
                        "for (int i = 0; i < 1; i = i + 1) { break loop; }",
                        (loop, _, ctl) -> loop.for_(
                                PrimitiveToken.INT,
                                literal(0),
                                i -> ltInt(i, literal(1)),
                                i -> assign(i, addInt(i, literal(1))),
                                (inner, _, _) -> inner.break_(ctl))),
                foreverOf(
                        "try { break loop; } catch (RuntimeException e) { }",
                        (loop, _, ctl) -> loop.try_(
                                h -> h.catch_(RuntimeException_.TOKEN, (_, _) -> {}), body -> body.break_(ctl))),
                foreverOf(
                        "try { " + PRINT + " } catch (RuntimeException e) { break loop; }",
                        (loop, _, ctl) -> loop.try_(
                                h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.break_(ctl)),
                                body -> body.exec(print()))),
                new Probe<>(
                        "List<String>",
                        new List_<>(String_.TOKEN).token,
                        "loop: while (true) { for (String s : x) { break loop; } } return 0;",
                        (b, x) -> b.while_(
                                        literal(true),
                                        (loop, ctl) ->
                                                loop.forEach(String_.TOKEN, x, (inner, _, _) -> inner.break_(ctl)))
                                .return_(literal(0))),
                ofObject(
                        "for (int i = 0; true; i = i + 1) { } return 0;",
                        (b, _) -> b.for_(
                                        PrimitiveToken.INT,
                                        literal(0),
                                        _ -> literal(true),
                                        i -> assign(i, addInt(i, literal(1))),
                                        (_, _, _) -> {})
                                .return_(literal(0))),
                ofObject(
                        "for (int i = 0; true; i = i + 1) { if (x == x) { break; } } return 0;",
                        (b, x) -> b.for_(
                                        PrimitiveToken.INT,
                                        literal(0),
                                        _ -> literal(true),
                                        i -> assign(i, addInt(i, literal(1))),
                                        (loop, _, ctl) -> loop.if_(unknown(x), t -> t.break_(ctl)))
                                .return_(literal(0))),
                ofObject(
                        "do { } while (true); return 0;",
                        (b, _) -> b.doWhile((_, _) -> {}, literal(true)).return_(literal(0))),
                ofObject(
                        "do { if (x == x) { break; } } while (true); return 0;",
                        (b, x) -> b.doWhile((loop, ctl) -> loop.if_(unknown(x), t -> t.break_(ctl)), literal(true))
                                .return_(literal(0))),
                // the condition of a `do` is reached by a body that completes normally, or by a `continue`
                ofObject(
                        "do { return 1; } while (x == x); return 0;",
                        (b, x) -> b.doWhile((loop, _) -> loop.return_(literal(1)), unknown(x))
                                .return_(literal(0))),
                ofObject(
                        "do { if (x == x) { continue; } return 1; } while (x == x); return 0;",
                        (b, x) -> b.doWhile(
                                        (loop, ctl) -> loop.if_(unknown(x), t -> t.continue_(ctl))
                                                .return_(literal(1)),
                                        unknown(x))
                                .return_(literal(0))),
                ofObject(
                        "do { if (x == x) { continue; } return 1; } while (true); return 0;",
                        (b, x) -> b.doWhile(
                                        (loop, ctl) -> loop.if_(unknown(x), t -> t.continue_(ctl))
                                                .return_(literal(1)),
                                        literal(true))
                                .return_(literal(0))),
                ofObject(
                        "do { if (x == x) { break; } return 1; } while (x == x); return 0;",
                        (b, x) -> b.doWhile(
                                        (loop, ctl) -> loop.if_(unknown(x), t -> t.break_(ctl))
                                                .return_(literal(1)),
                                        unknown(x))
                                .return_(literal(0))));
    }

    /// A `try` completes normally where its `try` block or one of its `catch` blocks does.
    private static Stream<Probe<?>> tries() {
        return Stream.of(
                ofObject(
                        "try { return 1; } catch (RuntimeException e) { } return 0;",
                        (b, _) -> b.try_(
                                        h -> h.catch_(RuntimeException_.TOKEN, (_, _) -> {}),
                                        body -> body.return_(literal(1)))
                                .return_(literal(0))),
                ofObject(
                        "try { " + PRINT + " } catch (RuntimeException e) { return 1; } return 0;",
                        (b, _) -> b.try_(
                                        h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(1))),
                                        body -> body.exec(print()))
                                .return_(literal(0))),
                ofObject(
                        "try { return 1; } catch (RuntimeException e) { return 2; } return 0;",
                        (b, _) -> b.try_(
                                        h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(2))),
                                        body -> body.return_(literal(1)))
                                .return_(literal(0))));
    }

    @ParameterizedTest
    @MethodSource("bodies")
    void aBodyIsBuiltWhereJavacFindsNoUnreachableStatement(Probe<?> probe) {
        if (probe.unreachableToJavac()) {
            assertThatIllegalStateException().isThrownBy(probe::build).withMessageContaining("unreachable (JLS 14.22)");
        } else {
            JavaFile file = probe.build();
            CompilationSubject.assertThat(javac().withOptions("-Xlint:all")
                            .compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render())))
                    .succeededWithoutWarnings();
        }
    }
}
