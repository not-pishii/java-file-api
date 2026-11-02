package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Disc_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Figure_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Piece_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Point_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Square_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.ExpressionArities.staticCall;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.switchPattern;
import static org.assertj.core.api.Assertions.assertThat;

/// A pattern `switch` expression over a sealed hierarchy, end to end:
/// `typed → core → render → javac → run`, under every lint.
class PatternSwitchCompileTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Describe");

    @Test
    void everyPermittedSubtypeHasACaseAndTheSwitchIsExhaustive() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var side = Patterns.binding(PrimitiveToken.INT);
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.case_(
                                            Disc_.TOKEN,
                                            (body, disc) -> body.yield_(concat(
                                                    literal("disc "),
                                                    staticCall(Integer_.toString_int, disc.call(Disc_.radius)))))
                                    .case_(
                                            Patterns.record(Square_.TOKEN, Patterns.type(side)),
                                            body -> body.yield_(concat(
                                                    literal("square "), staticCall(Integer_.toString_int, side.var()))))
                                    .case_(Piece_.TOKEN, (body, piece) -> body.yield_(literal("piece"))))));
            return bodies;
        }));

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Disc;
                        import me.supcheg.javafile.typed.fixtures.Figure;
                        import me.supcheg.javafile.typed.fixtures.Piece;
                        import me.supcheg.javafile.typed.fixtures.Square;

                        public final class Describe {
                            public static String describe(Figure v0) {
                                return switch (v0) {
                                    case Disc v1 -> {
                                        yield "disc " + Integer.toString(v1.radius());
                                    }
                                    case Square(int v2) -> {
                                        yield "square " + Integer.toString(v2);
                                    }
                                    case Piece v3 -> {
                                        yield "piece";
                                    }
                                };
                            }
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, of("Disc", 3))).isEqualTo("disc 3");
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("square 2");
        assertThat(method.invoke(null, piece())).isEqualTo("piece");
    }

    @Test
    void nullAndDefaultAreCasesOfTheirOwn() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.caseNull(body -> body.yield_(literal("nothing")))
                                    .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
                                    .default_(body -> body.yield_(literal("other"))))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, (Object) null)).isEqualTo("nothing");
        assertThat(method.invoke(null, of("Disc", 3))).isEqualTo("disc");
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("other");
    }

    @Test
    void aGuardedCaseIsAcceptedAndRuns() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.case_(
                                            Disc_.TOKEN,
                                            (body, disc) -> gtInt(disc.call(Disc_.radius), literal(1)),
                                            (body, disc) -> body.yield_(literal("big disc")))
                                    .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("small disc")))
                                    .default_(body -> body.yield_(literal("other"))))));
            return bodies;
        }));

        assertThat(file.render()).contains("case Disc v1 when v1.radius() > 1 -> {");

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, of("Disc", 3))).isEqualTo("big disc");
        assertThat(method.invoke(null, of("Disc", 1))).isEqualTo("small disc");
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("other");
    }

    @Test
    void aGuardedRecordPatternIsAcceptedAndRuns() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var radius = Patterns.binding(PrimitiveToken.INT);
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.case_(
                                            Patterns.record(Disc_.TOKEN, Patterns.type(radius)),
                                            body -> gtInt(radius.var(), literal(1)),
                                            body -> body.yield_(concat(
                                                    literal("big disc "),
                                                    staticCall(Integer_.toString_int, radius.var()))))
                                    .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("small disc")))
                                    .caseNull(literal("nothing"))
                                    .default_(body -> body.yield_(literal("other"))))));
            return bodies;
        }));

        assertThat(file.render()).contains("case Disc(int v1) when v1 > 1 -> {");

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, of("Disc", 3))).isEqualTo("big disc 3");
        assertThat(method.invoke(null, of("Disc", 1))).isEqualTo("small disc");
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("other");
    }

    @Test
    void anUnconditionalPatternCoversEveryValueWithoutADefault() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.case_(Figure_.TOKEN, (body, any) -> body.yield_(literal("figure"))))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("figure");
    }

    @Test
    void anEnumSelectorWithoutADefaultCoversItsEveryConstant() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Signal_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, signal) -> b.return_(switchPattern(
                            Signal_.TOKEN,
                            signal,
                            String_.TOKEN,
                            c -> c.case_(Signal_.RED, literal("stop"))
                                    .case_(Signal_.AMBER, literal("wait"))
                                    .case_(Signal_.GREEN, literal("go")))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Signal"));
        assertThat(method.invoke(null, signal("GREEN"))).isEqualTo("go");
    }

    @Test
    void aNestedRecordPatternDeconstructsAComponentInTurn() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var x = Patterns.binding(PrimitiveToken.INT);
            var y = Patterns.binding(PrimitiveToken.INT);
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.return_(switchPattern(
                            Figure_.TOKEN,
                            figure,
                            String_.TOKEN,
                            c -> c.case_(
                                            Patterns.record(
                                                    Piece_.TOKEN,
                                                    Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y))),
                                            body -> body.yield_(concat(
                                                    literal("piece at "),
                                                    staticCall(Integer_.toString_int, addInt(x.var(), y.var())))))
                                    .default_(body -> body.yield_(literal("other"))))));
            return bodies;
        }));

        assertThat(file.render()).contains("case Piece(Point(int v1, int v2)) -> {");

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, piece())).isEqualTo("piece at 3");
        assertThat(method.invoke(null, of("Disc", 1))).isEqualTo("other");
    }

    @Test
    void overAnEnumAConstantIsACaseAsInASwitchOverItsConstants() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Signal_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, signal) -> b.return_(switchPattern(
                            Signal_.TOKEN,
                            signal,
                            String_.TOKEN,
                            c -> c.case_(Signal_.RED, literal("stop"))
                                    .caseNull(literal("nothing"))
                                    .case_(Signal_.AMBER, body -> body.yield_(literal("halt")))
                                    .case_(Signal_.GREEN, body -> body.yield_(literal("go"))))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Signal"));
        assertThat(method.invoke(null, (Object) null)).isEqualTo("nothing");
        assertThat(method.invoke(null, signal("RED"))).isEqualTo("stop");
        assertThat(method.invoke(null, signal("AMBER"))).isEqualTo("halt");
        assertThat(method.invoke(null, signal("GREEN"))).isEqualTo("go");
    }

    @Test
    void aTryWithACatchHoldsAPatternSwitch() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe,
                    (b, figure) -> b.tryTerminated(
                            h -> h.catch_(IllegalStateException_.TOKEN, (c, e) -> c.return_(literal("caught"))),
                            t -> t.return_(switchPattern(
                                    Figure_.TOKEN,
                                    figure,
                                    String_.TOKEN,
                                    c -> c.caseNull(literal("nothing"))
                                            .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
                                            .case_(Square_.TOKEN, (body, square) -> body.yield_(literal("square")))
                                            .case_(Piece_.TOKEN, (body, piece) -> body.yield_(literal("piece")))))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method method = describe.getMethod("describe", Class.forName("me.supcheg.javafile.typed.fixtures.Figure"));
        assertThat(method.invoke(null, of("Square", 2))).isEqualTo("square");
    }

    private static Object of(String record, int component) throws Exception {
        Class<?> type = Class.forName("me.supcheg.javafile.typed.fixtures." + record);
        return type.getConstructor(int.class).newInstance(component);
    }

    private static Object piece() throws Exception {
        Class<?> type = Class.forName("me.supcheg.javafile.typed.fixtures.Piece");
        return type.getConstructor(Class.forName("me.supcheg.javafile.typed.fixtures.Point"))
                .newInstance(point(1, 2));
    }

    private static Object point(int x, int y) throws Exception {
        Class<?> type = Class.forName("me.supcheg.javafile.typed.fixtures.Point");
        return type.getConstructor(int.class, int.class).newInstance(x, y);
    }

    private static Object signal(String name) throws Exception {
        Class<?> type = Class.forName("me.supcheg.javafile.typed.fixtures.Signal");
        return type.getMethod("valueOf", String.class).invoke(null, name);
    }

    private static Class<?> load(Compilation compilation, String binaryName) throws ClassNotFoundException {
        return new ClassLoader(PatternSwitchCompileTest.class.getClassLoader()) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.equals(binaryName) || name.startsWith(binaryName + "$")) {
                    try {
                        JavaFileObject classFile = compilation
                                .generatedFile(StandardLocation.CLASS_OUTPUT, name.replace('.', '/') + ".class")
                                .orElseThrow(() -> new ClassNotFoundException(name));
                        byte[] bytes;
                        try (InputStream in = classFile.openInputStream()) {
                            bytes = in.readAllBytes();
                        }
                        return defineClass(name, bytes, 0, bytes.length);
                    } catch (IOException e) {
                        throw new ClassNotFoundException(name, e);
                    }
                }
                return super.findClass(name);
            }
        }.loadClass(binaryName);
    }
}
