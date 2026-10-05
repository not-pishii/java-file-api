package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static org.assertj.core.api.Assertions.assertThat;

/// Positive end-to-end tests of typed declarations (§6.5): declare/define,
/// `this` as a body parameter, and declarations that match their facts —
/// `typed → core → render → javac`, and where it matters, run.
class DeclarationsCompileTest {

    private static Compilation compile(JavaFile file) {
        return javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));
    }

    /// Loads a class compiled by `compilation` into a fresh class loader.
    private static Class<?> load(Compilation compilation, String binaryName) throws IOException {
        JavaFileObject classFile = compilation
                .generatedFile(StandardLocation.CLASS_OUTPUT, binaryName.replace('.', '/') + ".class")
                .orElseThrow();
        byte[] bytes;
        try (InputStream in = classFile.openInputStream()) {
            bytes = in.readAllBytes();
        }
        return new ClassLoader(DeclarationsCompileTest.class.getClassLoader()) {
            Class<?> define() {
                return defineClass(binaryName, bytes, 0, bytes.length);
            }
        }.define();
    }

    @Test
    void mutuallyRecursiveMethodsRenderCompileAndRun() throws Exception {
        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Parity"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        var isEven = cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
                        var isOdd = cb.declareMethod("isOdd", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
                        cb.define(
                                isEven,
                                (b, self, n) -> b.ifElse(
                                        eqInt(n, literal(0)),
                                        t -> t.return_(literal(true)),
                                        e -> e.return_(call(self, isOdd, subInt(n, literal(1))))));
                        cb.define(
                                isOdd,
                                (b, self, n) -> b.ifElse(
                                        eqInt(n, literal(0)),
                                        t -> t.return_(literal(false)),
                                        e -> e.return_(call(self, isEven, subInt(n, literal(1))))));
                        cb.constructor((b, _) -> b.end());
                    }
                });

        Compilation compilation = compile(file);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(file.render())
                .contains("public final class Parity")
                .contains("this.isOdd(")
                .contains("this.isEven(");
        Class<?> parity = load(compilation, "me.supcheg.example.Parity");
        Object instance = parity.getConstructor().newInstance();
        assertThat(parity.getMethod("isEven", int.class).invoke(instance, 10)).isEqualTo(true);
        assertThat(parity.getMethod("isOdd", int.class).invoke(instance, 7)).isEqualTo(true);
        assertThat(parity.getMethod("isEven", int.class).invoke(instance, 7)).isEqualTo(false);
    }

    @Test
    void constructorFieldsAndThisRenderCompileAndRun() throws Exception {
        AtomicReference<FinalClassToken<?>> selfToken = new AtomicReference<>();

        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Counter"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        selfToken.set(cb.self());
                        var limit = cb.field("limit", PrimitiveToken.INT, literal(10));
                        var count = cb.mutableField("count", PrimitiveToken.INT, literal(0));
                        var ctor = cb.declareConstructor(PrimitiveToken.INT);
                        // a static factory uses the constructor before it is defined
                        cb.staticMethod("create", cb.self(), b -> b.return_(new_(ctor, literal(4))));
                        cb.define(
                                ctor,
                                (b, self, start) ->
                                        b.exec(assignField(self, count, start)).end());
                        cb.method(
                                "remaining",
                                PrimitiveToken.INT,
                                (b, self) -> b.return_(subInt(field(self, limit), field(self, count))));
                        cb.method("toString", String_.TOKEN, (b, _) -> b.return_(literal("Counter")));
                    }
                });

        Compilation compilation = compile(file);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(file.render())
                .contains("public final class Counter")
                .contains("public final int limit = 10;")
                .contains("public int count = 0;")
                .contains("this.count = ")
                .contains("@Override");
        Class<?> counter = load(compilation, "me.supcheg.example.Counter");
        Object instance = counter.getMethod("create").invoke(null);
        assertThat(counter.getMethod("remaining").invoke(instance)).isEqualTo(6);
        assertThat(instance).hasToString("Counter");

        // the self token lists the declared and the inherited instance methods, not the static ones
        assertThat(selfToken.get().methods().abstractMethods()).isEmpty();
        assertThat(selfToken.get().methods().concreteMethods())
                .contains(
                        new MethodSignature("remaining", List.of()),
                        new MethodSignature("toString", List.of()),
                        new MethodSignature("wait", List.of(ConstantDescs.CD_long)))
                .doesNotContain(new MethodSignature("create", List.of()));
        assertThat(selfToken.get().superclasses()).containsExactly(ConstantDescs.CD_Object);
    }
}
