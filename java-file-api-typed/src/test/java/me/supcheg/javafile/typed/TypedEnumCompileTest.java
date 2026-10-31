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
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static org.assertj.core.api.Assertions.assertThat;

/// The typed enum of §6.5: constants without and with arguments, a constant
/// body that overrides an abstract method, a private constructor, and an
/// instance method.
class TypedEnumCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Op");

    private static JavaFile op() {
        return TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var apply = eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                eb.constructor(PrimitiveToken.INT, (b, self, value) -> b.end());
                eb.constant("ONE", List.of(literal(1)), cb -> cb.override(apply, (b, self, x) -> b.return_(x)));
                eb.constant(
                        "DOUBLE",
                        List.of(literal(2)),
                        cb -> cb.override(apply, (b, self, x) -> b.return_(mulInt(x, literal(2)))));
                var twice = eb.declareMethod("twice", PrimitiveToken.INT, PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.define(twice, (b, self, x) -> b.return_(mulInt(x, literal(2))));
                return bodies;
            }
        });
    }

    @Test
    void anEnumRendersCompilesAndRuns() throws Exception {
        JavaFile file = op();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public enum Op {
                            ONE(1) {
                                @Override
                                public int apply(int v0) {
                                    return v0;
                                }
                            }, DOUBLE(2) {
                                @Override
                                public int apply(int v0) {
                                    return v0 * 2;
                                }
                            };

                            public abstract int apply(int v0);

                            public int twice(int v0) {
                                return v0 * 2;
                            }

                            Op(int v0) {
                            }
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file);
        assertThat(compilation).succeededWithoutWarnings();

        Loader loader = new Loader(compilation);
        Class<?> op = loader.define("me.supcheg.example.Op");
        loader.define("me.supcheg.example.Op$1");
        loader.define("me.supcheg.example.Op$2");
        Object one = op.getField("ONE").get(null);
        Object doubled = op.getField("DOUBLE").get(null);
        assertThat(op.getMethod("apply", int.class).invoke(one, 3)).isEqualTo(3);
        assertThat(op.getMethod("apply", int.class).invoke(doubled, 3)).isEqualTo(6);
        assertThat(op.getMethod("twice", int.class).invoke(one, 3)).isEqualTo(6);
    }

    private static final class Loader extends ClassLoader {
        private final Compilation compilation;

        Loader(Compilation compilation) {
            super(TypedEnumCompileTest.class.getClassLoader());
            this.compilation = compilation;
        }

        Class<?> define(String binaryName) throws IOException {
            JavaFileObject classFile = compilation
                    .generatedFile(StandardLocation.CLASS_OUTPUT, binaryName.replace('.', '/') + ".class")
                    .orElseThrow();
            byte[] bytes;
            try (InputStream in = classFile.openInputStream()) {
                bytes = in.readAllBytes();
            }
            return defineClass(binaryName, bytes, 0, bytes.length);
        }
    }
}
