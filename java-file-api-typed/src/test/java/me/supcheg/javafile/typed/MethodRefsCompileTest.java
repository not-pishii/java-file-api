package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Math_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntUnaryOperator_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.methodRef;
import static org.assertj.core.api.Assertions.assertThat;

/// Method references of the generated code (6.4): a bound receiver, an unbound instance reference and
/// a static reference, each of the functional interface its method implements, rendered exactly and run.
// the casts check the compile-time refusals, and are unchecked on purpose
@SuppressWarnings({"rawtypes", "unchecked"})
class MethodRefsCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "References");

    private static final Supplier_<String> SUPPLIER = new Supplier_<>(String_.TOKEN);
    private static final Function_<String, String> FUNCTION = new Function_<>(String_.TOKEN, String_.TOKEN);

    @Test
    void aBoundReceiverAnUnboundInstanceAndAStaticMethodReferenceCompileAndRun() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var bound = cb.declareStaticMethod("bound", SUPPLIER.token, String_.TOKEN);
                        var unbound = cb.declareStaticMethod("unbound", FUNCTION.token);
                        var statics = cb.declareStaticMethod("statics", IntUnaryOperator_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(
                                bound,
                                (b, s) -> b.return_(s.methodRef(String_.trim, SUPPLIER.token, SUPPLIER.sam.method())));
                        bodies.define(
                                unbound,
                                b -> b.return_(
                                        methodRef(String_.TOKEN, String_.trim, FUNCTION.token, FUNCTION.sam.method())));
                        bodies.define(
                                statics,
                                b -> b.return_(methodRef(
                                        Math_.TOKEN,
                                        Math_.abs_int,
                                        IntUnaryOperator_.TOKEN,
                                        IntUnaryOperator_.sam.method())));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("return (Supplier<String>) v0::trim;")
                .contains("return (Function<String, String>) String::trim;")
                .contains("return (IntUnaryOperator) Math::abs;");

        Supplier<String> bound = (Supplier<String>) compiled.invoke("bound", "  a  ");
        assertThat(bound.get()).isEqualTo("a");
        Function<String, String> unbound = (Function<String, String>) compiled.invoke("unbound");
        assertThat(unbound.apply("  b  ")).isEqualTo("b");
        IntUnaryOperator statics = (IntUnaryOperator) compiled.invoke("statics");
        assertThat(statics.applyAsInt(-3)).isEqualTo(3);
    }

    @Test
    void aMethodReferenceIsCastWhereTheContextDoesNotGiveItItsInterface() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var same = cb.declareStaticMethod("same", PrimitiveToken.BOOLEAN, String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(
                                same,
                                (b, s) -> b.return_(eqRef(
                                        s.methodRef(String_.trim, SUPPLIER.token, SUPPLIER.sam.method()),
                                        s.methodRef(String_.trim, SUPPLIER.token, SUPPLIER.sam.method()))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("(Supplier<String>) v0::trim");
        assertThat(compiled.invoke("same", "a")).isEqualTo(false);
    }
}
