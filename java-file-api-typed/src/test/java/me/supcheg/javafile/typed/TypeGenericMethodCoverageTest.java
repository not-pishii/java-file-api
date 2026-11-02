package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Every form of [TypeGenericMethodDeclaration] of a record, at every parameter arity: the method and
/// the `static` method, each with none to sixteen parameters, is declared, so that the arity family of
/// the generic methods of a type is exercised as that of a class is. The members are left undefined:
/// the entry reports them, and the declaration of each form has been made by then.
class TypeGenericMethodCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// The specification of a generic method of one type parameter, whose fact is left an `Invocable`: the
    /// test declares the method, and does not name the family of its fact.
    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, Invocable, Object> spec(
            BiFunction<TypeGenericMethodDeclaration<Self>, TypeVarToken<Object>, Invocable> build) {
        return build::apply;
    }

    @Test
    void everyFormOfAGenericMethodOfARecordIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var t = typeParam("T");
                                rb.declareGenericMethod(t, spec((m, x) -> m.declareMethod("method0", String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t, spec((m, x) -> m.declareMethod("method1", String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method8",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method9",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method10",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method11",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method12",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method13",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method14",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method15",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareMethod(
                                                "method16",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(t, spec((m, x) -> m.declareVoidMethod("voidMethod0")));
                                rb.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidMethod("voidMethod1", String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.declareVoidMethod("voidMethod2", String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod8",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod9",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod10",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod11",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod12",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod13",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod14",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod15",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidMethod(
                                                "voidMethod16",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t, spec((m, x) -> m.declareStaticMethod("static0", String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod("static1", String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static8",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static9",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static10",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static11",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static12",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static13",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static14",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static15",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static16",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(t, spec((m, x) -> m.declareVoidStaticMethod("voidStatic0")));
                                rb.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidStaticMethod("voidStatic1", String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic2", String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic8",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic9",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic10",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic11",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic12",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic13",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic14",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic15",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic16",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }

    @Test
    void everyFormOfAnAbstractGenericMethodOfAnAbstractClassIsDeclared() {
        var file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.abstract_();
                        var cb = h.extendsObject().members();
                        var t = typeParam("T");
                        cb.declareGenericMethod(
                                t, (GenericMethodArities.MethodSpec1<Self, Object, MethodRef0<Self, String>, Object>)
                                        (m, x) -> m.declareAbstractMethod("a0", String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod("a1", String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef2<Self, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod("a2", String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef3<Self, String, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod(
                                        "a3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef4<Self, String, String, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod(
                                        "a4",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN));
                        cb.declareGenericMethod(
                                t, (GenericMethodArities.MethodSpec1<Self, Object, VoidMethodRef0<Self>, Object>)
                                        (m, x) -> m.declareVoidAbstractMethod("va0"));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef1<Self, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod("va1", String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef2<Self, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod("va2", String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef3<Self, String, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod(
                                        "va3", String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef4<Self, String, String, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod(
                                        "va4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        return cb.bodies();
                    }
                });
        assertThat(file.render())
                .contains("public abstract <T> String a0();")
                .contains("public abstract <T> String a4(String v0, String v1, String v2, String v3);")
                .contains("public abstract <T> void va0();")
                .contains("public abstract <T> void va4(String v0, String v1, String v2, String v3);");
    }
}
