package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/// The helper methods of the body of an enum constant (8g): `declareMethod`, `declareVoidMethod`,
/// `declareStaticMethod` and `declareVoidStaticMethod` reach twelve parameters, generated into the
/// superclass of the body ([EnumConstantBodyArities]) as the other member forms are. The body of four,
/// eight and twelve is rendered, compiled under `-Xlint:all` and run.
class EnumConstantHelperAritiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Plan");
    private static final TargetClasspath TARGET = UnsafeFacts.unverifiedClasspath();

    @Test
    void aConstantBodyDeclaresHelpersOfEveryArity() {
        TypedEnumBuilder<String> plan = new TypedEnumBuilder<>(TARGET, DESC);
        plan.constant("ONLY", cb -> {
            try {
                for (int n = 0; n <= 12; n++) {
                    declare(cb, "declareMethod", "m" + n, n, String_.TOKEN);
                    declare(cb, "declareVoidMethod", "v" + n, n, null);
                    declare(cb, "declareStaticMethod", "s" + n, n, String_.TOKEN);
                    declare(cb, "declareVoidStaticMethod", "sv" + n, n, null);
                }
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        });

        assertThat(plan).isNotNull();
    }

    /// Calls the generated `declare*` helper of the arity `n`, which a test cannot name one by one.
    private static void declare(TypedEnumConstantBodies<?> cb, String form, String name, int n, TypeToken<?> result)
            throws ReflectiveOperationException {
        java.util.List<Class<?>> parameters = new java.util.ArrayList<>();
        parameters.add(String.class);
        if (result != null) {
            parameters.add(TypeToken.class);
        }
        for (int i = 1; i <= n; i++) {
            parameters.add(TypeToken.class);
        }
        java.util.List<Object> arguments = new java.util.ArrayList<>();
        arguments.add(name);
        if (result != null) {
            arguments.add(result);
        }
        for (int i = 1; i <= n; i++) {
            arguments.add(String_.TOKEN);
        }
        Method method = TypedEnumConstantBodies.class.getMethod(form, parameters.toArray(Class<?>[]::new));
        method.setAccessible(true);
        method.invoke(cb, arguments.toArray());
    }

    @Test
    void aConstantBodyDefinesHelpersOfFiveToTwelveParameters() {
        JavaFile file = TypedJavaFile.enum_(TARGET, DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("ONLY", cb -> {
                    var sum5 = cb.declareMethod(
                            "sum5",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var run5 = cb.declareVoidMethod(
                            "run5", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    var static5 = cb.declareStaticMethod(
                            "static5",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var staticVoid5 = cb.declareVoidStaticMethod(
                            "staticVoid5", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    var sum6 = cb.declareMethod(
                            "sum6",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var run6 = cb.declareVoidMethod(
                            "run6",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var static6 = cb.declareStaticMethod(
                            "static6",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var staticVoid6 = cb.declareVoidStaticMethod(
                            "staticVoid6",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var sum7 = cb.declareMethod(
                            "sum7",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var run7 = cb.declareVoidMethod(
                            "run7",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var static7 = cb.declareStaticMethod(
                            "static7",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var staticVoid7 = cb.declareVoidStaticMethod(
                            "staticVoid7",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var sum9 = cb.declareMethod(
                            "sum9",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var run9 = cb.declareVoidMethod(
                            "run9",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var static9 = cb.declareStaticMethod(
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
                            String_.TOKEN);
                    var staticVoid9 = cb.declareVoidStaticMethod(
                            "staticVoid9",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var sum10 = cb.declareMethod(
                            "sum10",
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
                            String_.TOKEN);
                    var run10 = cb.declareVoidMethod(
                            "run10",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var static10 = cb.declareStaticMethod(
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
                            String_.TOKEN);
                    var staticVoid10 = cb.declareVoidStaticMethod(
                            "staticVoid10",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var sum11 = cb.declareMethod(
                            "sum11",
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
                            String_.TOKEN);
                    var run11 = cb.declareVoidMethod(
                            "run11",
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
                            String_.TOKEN);
                    var static11 = cb.declareStaticMethod(
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
                            String_.TOKEN);
                    var staticVoid11 = cb.declareVoidStaticMethod(
                            "staticVoid11",
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
                            String_.TOKEN);

                    cb.define(sum5, (b, self, p1, p2, p3, p4, p5) -> b.return_(p1));
                    cb.define(run5, (b, self, p1, p2, p3, p4, p5) -> b.end());
                    cb.define(static5, (b, p1, p2, p3, p4, p5) -> b.return_(p1));
                    cb.define(staticVoid5, (b, p1, p2, p3, p4, p5) -> b.end());
                    cb.define(sum6, (b, self, p1, p2, p3, p4, p5, p6) -> b.return_(p1));
                    cb.define(run6, (b, self, p1, p2, p3, p4, p5, p6) -> b.end());
                    cb.define(static6, (b, p1, p2, p3, p4, p5, p6) -> b.return_(p1));
                    cb.define(staticVoid6, (b, p1, p2, p3, p4, p5, p6) -> b.end());
                    cb.define(sum7, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(p1));
                    cb.define(run7, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.end());
                    cb.define(static7, (b, p1, p2, p3, p4, p5, p6, p7) -> b.return_(p1));
                    cb.define(staticVoid7, (b, p1, p2, p3, p4, p5, p6, p7) -> b.end());
                    cb.define(sum9, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(p1));
                    cb.define(run9, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.end());
                    cb.define(static9, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(p1));
                    cb.define(staticVoid9, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.end());
                    cb.define(sum10, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.return_(p1));
                    cb.define(run10, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.end());
                    cb.define(static10, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.return_(p1));
                    cb.define(staticVoid10, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.end());
                    cb.define(sum11, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(p1));
                    cb.define(run11, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.end());
                    cb.define(static11, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(p1));
                    cb.define(staticVoid11, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.end());
                });
                return eb.bodies();
            }
        });

        assertThat(file.render()).contains("public String sum5(").contains("public static String static11(");
        CompiledClasses.of(file);
    }

    @Test
    void aConstantBodyRunsHelpersOfFourEightAndTwelveParameters() throws Throwable {
        JavaFile file = TypedJavaFile.enum_(TARGET, DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("ONLY", cb -> {
                    var sum4 = cb.declareMethod(
                            "sum4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    var run4 = cb.declareVoidMethod("run4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    var static4 = cb.declareStaticMethod(
                            "static4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    var staticVoid4 = cb.declareVoidStaticMethod(
                            "staticVoid4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
                    cb.define(sum4, (b, self, p1, p2, p3, p4) -> b.return_(p1));
                    cb.define(run4, (b, self, p1, p2, p3, p4) -> b.end());
                    cb.define(static4, (b, p1, p2, p3, p4) -> b.return_(p1));
                    cb.define(staticVoid4, (b, p1, p2, p3, p4) -> b.end());

                    var sum8 = cb.declareMethod(
                            "sum8",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var run8 = cb.declareVoidMethod(
                            "run8",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var static8 = cb.declareStaticMethod(
                            "static8",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    var staticVoid8 = cb.declareVoidStaticMethod(
                            "staticVoid8",
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN,
                            String_.TOKEN);
                    cb.define(sum8, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(p1));
                    cb.define(run8, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.end());
                    cb.define(static8, (b, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(p1));
                    cb.define(staticVoid8, (b, p1, p2, p3, p4, p5, p6, p7, p8) -> b.end());

                    var sum12 = cb.declareMethod(
                            "sum12",
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
                            String_.TOKEN);
                    var run12 = cb.declareVoidMethod(
                            "run12",
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
                            String_.TOKEN);
                    var static12 = cb.declareStaticMethod(
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
                            String_.TOKEN);
                    var staticVoid12 = cb.declareVoidStaticMethod(
                            "staticVoid12",
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
                            String_.TOKEN);
                    cb.define(sum12, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(p1));
                    cb.define(run12, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.end());
                    cb.define(static12, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(p1));
                    cb.define(staticVoid12, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.end());
                });
                return eb.bodies();
            }
        });
        CompiledClasses compiled = CompiledClasses.of(file);
        Object constant =
                compiled.generated().getMethod("valueOf", String.class).invoke(null, "ONLY");

        assertThat(invoke(constant, "sum4", 4)).isEqualTo("x");
        assertThat(invoke(constant, "static4", 4)).isEqualTo("x");
        invokeVoid(constant, "run4", 4);
        invokeVoid(constant, "staticVoid4", 4);

        assertThat(invoke(constant, "sum8", 8)).isEqualTo("x");
        assertThat(invoke(constant, "static8", 8)).isEqualTo("x");
        invokeVoid(constant, "run8", 8);
        invokeVoid(constant, "staticVoid8", 8);

        assertThat(invoke(constant, "sum12", 12)).isEqualTo("x");
        assertThat(invoke(constant, "static12", 12)).isEqualTo("x");
        invokeVoid(constant, "run12", 12);
        invokeVoid(constant, "staticVoid12", 12);
    }

    private static Method helper(Object target, String name, int n) throws NoSuchMethodException {
        Class<?>[] parameters = new Class<?>[n];
        Arrays.fill(parameters, String.class);
        Method method = target.getClass().getMethod(name, parameters);
        method.setAccessible(true);
        return method;
    }

    private static Object[] arguments(int n) {
        Object[] arguments = new Object[n];
        Arrays.fill(arguments, "x");
        return arguments;
    }

    private static Object invoke(Object target, String name, int n) throws ReflectiveOperationException {
        return helper(target, name, n).invoke(target, arguments(n));
    }

    private static void invokeVoid(Object target, String name, int n) throws ReflectiveOperationException {
        helper(target, name, n).invoke(target, arguments(n));
    }
}
