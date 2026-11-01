package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.SealedBase_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.SealedShape_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// A sealed supertype ([me.supcheg.javafile.typed.fixtures.SealedBase],
/// [me.supcheg.javafile.typed.fixtures.SealedShape]): a generated class has it
/// exactly when it is a subtype the type permits, and a `non-sealed` class
/// extends one that permits it.
class SealedInheritanceTest {
    private static final String FIXTURES = "me.supcheg.javafile.typed.fixtures";
    private static final ClassDesc SEALED_CHILD = ClassDesc.of(FIXTURES, "SealedChild");
    private static final ClassDesc SEALED_DOT = ClassDesc.of(FIXTURES, "SealedDot");
    private static final ClassDesc SEALED_OPEN = ClassDesc.of(FIXTURES, "SealedOpen");
    private static final ClassDesc SEALED_REFUSED = ClassDesc.of(FIXTURES, "SealedRefused");

    @Test
    void aPermittedSubclassOfASealedClassRendersCompilesAndRuns() throws Throwable {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), SEALED_CHILD, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sh = h.extends_(SealedBase_.extend(h.self()));
                        var cb = sh.members();
                        var ctor = cb.declareConstructor();
                        var answer = cb.declareStaticMethod("answer", PrimitiveToken.INT);
                        var bodies = cb.bodies(ctor, c -> c.super_(SealedBase_.new_, (b, self) -> b.end()));
                        bodies.define(answer, b -> b.return_(literal(42)));
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains("public final class SealedChild extends SealedBase")
                .contains("public static int answer()");
        assertThat(compileAndLoad(file).getMethod("answer").invoke(null)).isEqualTo(42);
    }

    @Test
    void aPermittedImplementationOfASealedInterfaceRendersCompilesAndRuns() throws Throwable {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), SEALED_DOT, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sides = h.overriding(SealedShape_.sides);
                        var oh = h.extendsObject();
                        oh.implements_(SealedShape_.implement(h.self()).sides(sides));
                        var cb = oh.members();
                        var bodies = cb.bodies();
                        bodies.define(sides.method(), (b, self) -> b.return_(literal(7)));
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains("public final class SealedDot implements SealedShape")
                .contains("public int sides()");
        Object dot = compileAndLoad(file).getConstructor().newInstance();
        assertThat(dot.getClass().getMethod("sides").invoke(dot)).isEqualTo(7);
    }

    @Test
    void aSubclassThatIsNotPermittedIsRefused() {
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), SEALED_REFUSED, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var cb =
                                        h.extends_(SealedBase_.extend(h.self())).members();
                                var ctor = cb.declareConstructor();
                                return cb.bodies(ctor, c -> c.super_(SealedBase_.new_, (b, self) -> b.end()));
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(FIXTURES + ".SealedBase")
                .hasMessageContaining(FIXTURES + ".SealedChild")
                .hasMessageContaining(FIXTURES + ".SealedOpen");
    }

    @Test
    void anImplementationThatIsNotPermittedIsRefused() {
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(FIXTURES, "SealedRefusedShape"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var sides = h.overriding(SealedShape_.sides);
                                var oh = h.extendsObject();
                                oh.implements_(SealedShape_.implement(h.self()).sides(sides));
                                return oh.members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(FIXTURES + ".SealedShape")
                .hasMessageContaining(FIXTURES + ".SealedDot");
    }

    @Test
    void aNonSealedSubclassOfASealedClassRendersCompilesAndRuns() throws Throwable {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), SEALED_OPEN, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.nonSealed();
                        var sh = h.extends_(SealedBase_.extend(h.self()));
                        var cb = sh.members();
                        var ctor = cb.declareConstructor();
                        var answer = cb.declareStaticMethod("answer", PrimitiveToken.INT);
                        var bodies = cb.bodies(ctor, c -> c.super_(SealedBase_.new_, (b, self) -> b.end()));
                        bodies.define(answer, b -> b.return_(literal(9)));
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains("public non-sealed class SealedOpen extends SealedBase")
                .contains("public static int answer()");
        assertThat(compileAndLoad(file).getMethod("answer").invoke(null)).isEqualTo(9);
    }

    /// Compiles the rendered class and loads it ahead of the fixture class of
    /// the same name, so that what runs is the generated one.
    private static Class<?> compileAndLoad(JavaFile file) {
        Compilation compilation = RenderedJava.compile(file);
        CompilationSubject.assertThat(compilation).succeededWithoutWarnings();
        ClassLoader loader = new ClassLoader(SealedInheritanceTest.class.getClassLoader()) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                // the sealed supertype and the generated subtype must be of one module: the same loader
                return name.startsWith(FIXTURES + ".") ? findClass(name) : super.loadClass(name, resolve);
            }

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                String path = name.replace('.', '/') + ".class";
                JavaFileObject generated = compilation
                        .generatedFile(StandardLocation.CLASS_OUTPUT, path)
                        .orElse(null);
                try (InputStream in = generated != null
                        ? generated.openInputStream()
                        : SealedInheritanceTest.class.getClassLoader().getResourceAsStream(path)) {
                    if (in == null) {
                        throw new ClassNotFoundException(name);
                    }
                    byte[] bytes = in.readAllBytes();
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name, e);
                }
            }
        };
        try {
            return loader.loadClass(file.qualifiedName());
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }
}
