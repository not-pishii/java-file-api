package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Line_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Point_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// A record deconstruction pattern in an `if`, end to end: `typed → core →
/// render → javac → run`.
class RecordPatternCompileTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Describe");

    /// `static int sum(Object value)`, whose then-branch binds the components of `Point(int x, int y)`.
    private static JavaFile sum() {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var x = Patterns.binding(PrimitiveToken.INT);
            var y = Patterns.binding(PrimitiveToken.INT);
            var sum = cb.declareStaticMethod("sum", PrimitiveToken.INT, Object_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    sum,
                    (b, value) -> b.ifInstanceOf(
                                    value,
                                    Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y)),
                                    then -> then.return_(addInt(x.var(), y.var())))
                            .return_(literal(0)));
            return bodies;
        }));
    }

    @Test
    void aRecordPatternInAnIfRendersCompilesAndRuns() throws Throwable {
        JavaFile file = sum();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Point;

                        public final class Describe {
                            public static int sum(Object v0) {
                                if (v0 instanceof Point(int v1, int v2)) {
                                    return v1 + v2;
                                }
                                return 0;
                            }
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method sum = describe.getMethod("sum", Object.class);
        assertThat(sum.invoke(null, point(1, 2))).isEqualTo(3);
        assertThat(sum.invoke(null, "not a point")).isEqualTo(0);
        assertThat(sum.invoke(null, (Object) null)).isEqualTo(0);
    }

    @Test
    void aNestedRecordPatternBindsTheComponentsOfBoth() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var x1 = Patterns.binding(PrimitiveToken.INT);
            var y1 = Patterns.binding(PrimitiveToken.INT);
            var x2 = Patterns.binding(PrimitiveToken.INT);
            var y2 = Patterns.binding(PrimitiveToken.INT);
            var total = cb.declareStaticMethod("total", PrimitiveToken.INT, Object_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    total,
                    (b, value) -> b.ifInstanceOf(
                                    value,
                                    Patterns.record(
                                            Line_.TOKEN,
                                            Patterns.record(Point_.TOKEN, Patterns.type(x1), Patterns.type(y1)),
                                            Patterns.record(Point_.TOKEN, Patterns.type(x2), Patterns.type(y2))),
                                    then -> then.return_(
                                            addInt(addInt(x1.var(), y1.var()), addInt(x2.var(), y2.var()))))
                            .return_(literal(-1)));
            return bodies;
        }));

        assertThat(file.render())
                .contains("if (v0 instanceof Line(" + "Point(int v1, int v2), Point(int v3, int v4))) {")
                .contains("return v1 + v2 + (v3 + v4);");

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Object line = line(point(1, 2), point(3, 4));
        assertThat(describe.getMethod("total", Object.class).invoke(null, line)).isEqualTo(10);
        assertThat(describe.getMethod("total", Object.class).invoke(null, point(1, 2)))
                .isEqualTo(-1);
    }

    @Test
    void aRecordPatternInATerminatedIfElseRuns() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var x = Patterns.binding(PrimitiveToken.INT);
            var y = Patterns.binding(PrimitiveToken.INT);
            var kind = cb.declareStaticMethod("kind", String_.TOKEN, Object_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    kind,
                    (b, value) -> b.ifInstanceOfElse(
                            value,
                            Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y)),
                            then -> then.return_(literal("point")),
                            otherwise -> otherwise.return_(literal("other"))));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method kind = describe.getMethod("kind", Object.class);
        assertThat(kind.invoke(null, point(1, 2))).isEqualTo("point");
        assertThat(kind.invoke(null, "not a point")).isEqualTo("other");
    }

    @Test
    void aRecordPatternIsInScopeInTheFinallyBlockOfATry() throws Throwable {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(cb -> {
            var x = Patterns.binding(PrimitiveToken.INT);
            var y = Patterns.binding(PrimitiveToken.INT);
            var kind = cb.declareStaticMethod("kind", String_.TOKEN, Object_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    kind,
                    (b, value) -> b.letVar(
                            String_.TOKEN,
                            literal("other"),
                            out -> b.try_(
                                            h -> h.finally_(f -> f.ifInstanceOf(
                                                    value,
                                                    Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y)),
                                                    then -> then.exec(Expressions.assign(out, literal("point"))))),
                                            t -> t.empty())
                                    .return_(out)));
            return bodies;
        }));

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> describe = load(compilation, "me.supcheg.example.Describe");
        Method kind = describe.getMethod("kind", Object.class);
        assertThat(kind.invoke(null, point(1, 2))).isEqualTo("point");
        assertThat(kind.invoke(null, "not a point")).isEqualTo("other");
    }

    private static Object point(int x, int y) throws Exception {
        Class<?> point = Class.forName("me.supcheg.javafile.typed.fixtures.Point");
        return point.getConstructor(int.class, int.class).newInstance(x, y);
    }

    private static Object line(Object start, Object end) throws Exception {
        Class<?> line = Class.forName("me.supcheg.javafile.typed.fixtures.Line");
        return line.getConstructor(
                        Class.forName("me.supcheg.javafile.typed.fixtures.Point"),
                        Class.forName("me.supcheg.javafile.typed.fixtures.Point"))
                .newInstance(start, end);
    }

    private static Class<?> load(Compilation compilation, String binaryName)
            throws IOException, ClassNotFoundException {
        JavaFileObject classFile = compilation
                .generatedFile(StandardLocation.CLASS_OUTPUT, binaryName.replace('.', '/') + ".class")
                .orElseThrow();
        byte[] bytes;
        try (InputStream in = classFile.openInputStream()) {
            bytes = in.readAllBytes();
        }
        return new ClassLoader(RecordPatternCompileTest.class.getClassLoader()) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.equals(binaryName)) {
                    return defineClass(name, bytes, 0, bytes.length);
                }
                return super.findClass(name);
            }
        }.loadClass(binaryName);
    }
}
