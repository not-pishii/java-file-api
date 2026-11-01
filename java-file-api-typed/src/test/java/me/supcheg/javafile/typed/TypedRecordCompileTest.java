package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// The typed record of §6.5: components, a compact constructor, an
/// additional constructor that delegates to the canonical one, and an
/// accessor called by its fact.
class TypedRecordCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Point");

    private static JavaFile point() {
        return TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                var x = rb.component("x", PrimitiveToken.INT);
                var y = rb.component("y", PrimitiveToken.INT);
                var compact = rb.declareCompactConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                var empty = rb.declareConstructor();
                var sum = rb.declareMethod("sum", PrimitiveToken.INT);
                var bodies = rb.bodies();
                bodies.define(compact, (b, self, x0, y0) -> b.end());
                bodies.defineDelegating(empty, List.of(literal(0), literal(0)), (b, self) -> b.end());
                bodies.define(sum, (b, self) -> b.return_(addInt(self.call(x.accessor()), self.call(y.accessor()))));
                return bodies;
            }
        });
    }

    @Test
    void aRecordImplementsAnInterface() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Numbered");
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var value = rb.component("value", PrimitiveToken.INT);
                        var upcast = rb.implements_(
                                me.supcheg.javafile.typed.testfacts.java.util.function.IntUnaryOperator_.TOKEN);
                        var apply = rb.declareOverride(
                                upcast,
                                me.supcheg.javafile.typed.testfacts.java.util.function.IntUnaryOperator_
                                        .applyAsInt_int);
                        var bodies = rb.bodies();
                        bodies.define(apply, (b, self, x) -> b.return_(x));
                        return bodies;
                    }
                });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import java.util.function.IntUnaryOperator;

                        public record Numbered(int value) implements IntUnaryOperator {
                            @Override
                            public int applyAsInt(int v0) {
                                return v0;
                            }
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void aRecordRendersCompilesAndRuns() throws Exception {
        JavaFile file = point();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public record Point(int x, int y) {
                            public Point {
                            }

                            public Point() {
                                this(0, 0);
                            }

                            public int sum() {
                                return this.x() + this.y();
                            }
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Class<?> point = load(compilation, "me.supcheg.example.Point");
        Object instance = point.getConstructor(int.class, int.class).newInstance(1, 2);
        assertThat(point.getMethod("sum").invoke(instance)).isEqualTo(3);
        assertThat(point.getMethod("x").invoke(instance)).isEqualTo(1);
        Object empty = point.getConstructor().newInstance();
        assertThat(point.getMethod("sum").invoke(empty)).isEqualTo(0);
    }

    private static Class<?> load(Compilation compilation, String binaryName) throws IOException {
        JavaFileObject classFile = compilation
                .generatedFile(StandardLocation.CLASS_OUTPUT, binaryName.replace('.', '/') + ".class")
                .orElseThrow();
        byte[] bytes;
        try (InputStream in = classFile.openInputStream()) {
            bytes = in.readAllBytes();
        }
        return new ClassLoader(TypedRecordCompileTest.class.getClassLoader()) {
            Class<?> define() {
                return defineClass(binaryName, bytes, 0, bytes.length);
            }
        }.define();
    }
}
