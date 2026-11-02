package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Every form of a generic method of variable arity of a record, at every parameter arity from one to
/// sixteen: the method and the `static` method are declared, so that the arity family of the generic
/// methods of variable arity of a type is exercised as that of a class is. The members are left
/// undefined: the entry reports them, and the declaration of each form has been made by then.
class TypeGenericVariableArityRecordCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// The specification of a generic method of one type parameter, whose fact is left an `Invocable`: the
    /// test declares the method, and does not name the family of its fact.
    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, Invocable, Object> spec(
            BiFunction<TypeGenericMethodDeclaration<Self>, TypeVarToken<Object>, Invocable> build) {
        return build::apply;
    }

    @Test
    void everyFormOfAVariableArityGenericMethodOfARecordIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var t = typeParam("T");
                                var strings = ArrayToken.of(String_.TOKEN);
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareMethod("method1", String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod("method2", String_.TOKEN, String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
                                                        "method8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t, spec((m, x) -> m.variableArity().declareVoidMethod("voidMethod1", strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod("voidMethod2", String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod3", String_.TOKEN, String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
                                                        "voidMethod9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod("static1", String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static2", String_.TOKEN, String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareVoidStaticMethod("voidStatic1", strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod("voidStatic2", String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic3", String_.TOKEN, String_.TOKEN, strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                rb.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
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
                                                        strings)));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }
}
