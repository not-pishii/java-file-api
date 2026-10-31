package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static org.assertj.core.api.Assertions.assertThat;

/// The typed interface of §6.5: abstract, default, static and private methods,
/// a constant, and the rendering, compilation and execution of what it makes.
class TypedInterfaceCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Shape");

    private static final class Loader extends ClassLoader {
        private final Compilation compilation;

        Loader(Compilation compilation) {
            super(TypedInterfaceCompileTest.class.getClassLoader());
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

    private static JavaFile shape() {
        return TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var area = ib.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
                        var describe = ib.declareDefaultMethod("describe", String_.TOKEN);
                        var twice = ib.declarePrivateMethod("twice", PrimitiveToken.INT, PrimitiveToken.INT);
                        var unit = ib.declareStaticMethod("unit", PrimitiveToken.DOUBLE);
                        var radius = ib.declareConstant("RADIUS", PrimitiveToken.DOUBLE);
                        var bodies = ib.bodies();
                        bodies.initialize(radius, literal(1.5));
                        bodies.define(twice, (b, self, x) -> b.return_(mulInt(x, literal(2))));
                        bodies.define(describe, (b, self) -> b.return_(literal("shape")));
                        bodies.define(unit, b -> b.return_(literal(1.0)));
                        return bodies;
                    }
                });
    }

    private static JavaFileObject circle() {
        return JavaFileObjects.forSourceString("me.supcheg.example.Circle", """
                package me.supcheg.example;

                public final class Circle implements Shape {
                    @Override
                    public double area() {
                        return 3.0;
                    }

                    @Override
                    public String describe() {
                        return "circle";
                    }
                }
                """);
    }

    @Test
    void anInterfaceExtendsSeveralInterfacesAndIsSealed() {
        ClassDesc child = ClassDesc.of("me.supcheg.example", "Child");
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), child, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.extends_(me.supcheg.javafile.typed.testfacts.java.lang.Runnable_.TOKEN);
                        ib.permits(ClassDesc.of("me.supcheg.example", "Impl"));
                        return ib.bodies();
                    }
                });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public sealed interface Child extends Runnable permits Impl {
                        }
                        """);

        Compilation compilation =
                RenderedJava.compile(file, JavaFileObjects.forSourceString("me.supcheg.example.Impl", """
                        package me.supcheg.example;

                        public final class Impl implements Child {
                            @Override
                            public void run() {
                            }
                        }
                        """));
        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void anInterfaceRendersCompilesAndRuns() throws Exception {
        JavaFile file = shape();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public interface Shape {
                            double area();

                            default String describe() {
                                return "shape";
                            }

                            private int twice(int v0) {
                                return v0 * 2;
                            }

                            static double unit() {
                                return 1.0;
                            }

                            double RADIUS = 1.5;
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file, circle());
        assertThat(compilation).succeededWithoutWarnings();

        Loader loader = new Loader(compilation);
        Class<?> shape = loader.define("me.supcheg.example.Shape");
        Class<?> circle = loader.define("me.supcheg.example.Circle");
        Object instance = circle.getConstructor().newInstance();
        assertThat(circle.getMethod("describe").invoke(instance)).isEqualTo("circle");
        assertThat(circle.getMethod("area").invoke(instance)).isEqualTo(3.0);
        assertThat(shape.getMethod("unit").invoke(null)).isEqualTo(1.0);
    }
}
