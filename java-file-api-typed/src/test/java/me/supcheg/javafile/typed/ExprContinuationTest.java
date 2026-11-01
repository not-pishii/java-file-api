package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static org.assertj.core.api.Assertions.assertThat;

/// The continuations of a typed expression read left to right, as those of a core expression do: a field read,
/// a call, an array access and length, a cast, an `instanceof`, a method reference and a `void` call continue the
/// expression they are written on, and each chain renders the Java it reads and compiles under `-Xlint:all`.
class ExprContinuationTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Chains");
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final Supplier_<String> SUPPLIER = new Supplier_<>(String_.TOKEN);

    private static JavaFile file(TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    private static void assertChain(JavaFile file, String chain) {
        assertThat(file.render()).contains(chain);
        RenderedJava.assertCompilesCleanly(file);
    }

    @Test
    void aFieldReadContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var read = cb.declareStaticMethod("read", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        read,
                        b -> b.let(
                                Wide_.TOKEN,
                                new_(Wide_.new_),
                                wide -> b.return_(wide.field(Wide_.told).call(String_.trim))));
                return bodies;
            }
        });

        assertChain(file, "v0.told.trim()");
    }

    @Test
    void aCallContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var length = cb.declareStaticMethod("length", PrimitiveToken.INT, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(length, (b, s) -> b.return_(s.call(String_.trim).call(String_.length)));
                return bodies;
            }
        });

        assertChain(file, "v0.trim().length()");
    }

    @Test
    void anArrayAccessContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var first = cb.declareStaticMethod("first", String_.TOKEN, STRINGS);
                var bodies = cb.bodies();
                bodies.define(first, (b, a) -> b.return_(a.arrayAccess(STRINGS, literal(0))));
                return bodies;
            }
        });

        assertChain(file, "v0[0]");
    }

    @Test
    void anArrayLengthContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var size = cb.declareStaticMethod("size", PrimitiveToken.INT, STRINGS);
                var bodies = cb.bodies();
                bodies.define(size, (b, a) -> b.return_(a.length(STRINGS)));
                return bodies;
            }
        });

        assertChain(file, "v0.length");
    }

    @Test
    void aCastContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var cast = cb.declareStaticMethod("asString", String_.TOKEN, Object_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(cast, (b, o) -> b.return_(o.cast(String_.TOKEN)));
                return bodies;
            }
        });

        assertChain(file, "(String) v0");
    }

    @Test
    void anInstanceOfContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var test = cb.declareStaticMethod("isString", PrimitiveToken.BOOLEAN, Object_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(test, (b, o) -> b.return_(o.instanceOf(String_.TOKEN)));
                return bodies;
            }
        });

        assertChain(file, "v0 instanceof String");
    }

    @Test
    void aMethodReferenceContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var trim = cb.declareStaticMethod("trim", SUPPLIER.token, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        trim, (b, s) -> b.return_(s.methodRef(String_.trim, SUPPLIER.token, SUPPLIER.sam.method())));
                return bodies;
            }
        });

        assertChain(file, "v0::trim");
    }

    @Test
    void aVoidCallContinuesTheExpression() {
        JavaFile file = file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var run = cb.declareStaticMethod("run", PrimitiveToken.INT, Runnable_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(run, (b, r) -> b.exec(r.voidCall(Runnable_.run)).return_(literal(1)));
                return bodies;
            }
        });

        assertChain(file, "v0.run();");
    }
}
