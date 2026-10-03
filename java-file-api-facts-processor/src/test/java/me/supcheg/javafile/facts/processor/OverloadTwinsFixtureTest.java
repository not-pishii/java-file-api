package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.facts.FactLookupException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// Fixture `overloads`: overloads that erase alike under some type
/// arguments — a generic method and one of the type it is given, a method
/// of a type parameter and one of the type argument, the same for
/// constructors and `static` methods. A fact is the member it was made of:
/// the typed layer either renders a call javac resolves to that member,
/// which the test compiles and runs, or rejects the fact, where Java has no
/// such call.
class OverloadTwinsFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {"""
        package p;
        public class Ov {
            public <T> String m(T t) { return "generic"; }
            public String m(String s) { return "string"; }
            public static <T> String s(T t) { return "generic"; }
            public static String s(Integer i) { return "integer"; }
            public <T extends Comparable<T>> String c(T t) { return "generic"; }
            public String c(String s) { return "string"; }
            public <T> String solo(T t) { return "solo"; }
        }
        """, """
        package p;
        public class Ov2 {
            public static <T> java.util.Optional<T> wrap(T t) { return java.util.Optional.of(t); }
            public static StringBuilder wrap(String s) { return new StringBuilder(s); }
        }
        """, """
        package p;
        public class T2<T> {
            public final String made;
            public T2() { made = "none"; }
            public T2(T t) { made = "t"; }
            public T2(String s) { made = "string"; }
            public String m(T t) { return "t"; }
            public String m(String s) { return "string"; }
            public String one(T t) { return "one"; }
        }
        """, """
        package p;
        public abstract class Abs<T> {
            public abstract String m(T t);
            public String m(String s) { return "string"; }
        }
        """, """
        package p;
        public class SubOv extends Ov {
            public String solo(String s) { return "sub"; }
        }
        """};

    /// A generator: each method renders `out.Out` with one `static` method `go` whose body uses one fact.
    private static final String GENERATOR = """
            package use;

            import gen.facts.java.lang.Integer_;
            import gen.facts.java.lang.String_;
            import gen.facts.java.util.Optional_;
            import gen.facts.p.Abs_;
            import gen.facts.p.Ov2_;
            import gen.facts.p.Ov_;
            import gen.facts.p.SubOv_;
            import gen.facts.p.T2_;
            import java.lang.constant.ClassDesc;
            import java.util.function.Function;
            import me.supcheg.javafile.facts.TypeToken;
            import me.supcheg.javafile.typed.Expr;
            import me.supcheg.javafile.typed.TypedClassBuilder;
            import me.supcheg.javafile.typed.TypedJavaFile;

            import static me.supcheg.javafile.typed.Expressions.call;
            import static me.supcheg.javafile.typed.Expressions.field;
            import static me.supcheg.javafile.typed.Expressions.literal;
            import static me.supcheg.javafile.typed.Expressions.literalNull;
            import static me.supcheg.javafile.typed.Expressions.new_;
            import static me.supcheg.javafile.typed.Expressions.staticCall;

            public final class Run {
                private static final T2_<String> STRINGS = new T2_<>(String_.TOKEN);
                private static final T2_<Integer> INTEGERS = new T2_<>(Integer_.TOKEN);

                private static <R, P> String render(
                        TypeToken<R> result, TypeToken<P> param, Function<Expr<P>, Expr<R>> body) {
                    return TypedJavaFile.class_(ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.staticMethod("go", result, param, (b, p) -> b.return_(body.apply(p)));
                                }
                            })
                            .render();
                }

                private static <P> String render(TypeToken<P> param, Function<Expr<P>, Expr<String>> body) {
                    return render(String_.TOKEN, param, body);
                }

                // a generic method and its overload of the type it is given

                public static String genericOfString() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.m_T(String_.TOKEN), literal("x")));
                }

                public static String genericOfInteger() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.m_T(Integer_.TOKEN), literalNull(Integer_.TOKEN)));
                }

                public static String overloadOfString() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.m_String, literal("x")));
                }

                public static String boundedOfString() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.c_T(String_.TOKEN), literal("x")));
                }

                public static String boundedOfInteger() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.c_T(Integer_.TOKEN), literalNull(Integer_.TOKEN)));
                }

                public static String soloOfString() {
                    return render(Ov_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x")));
                }

                public static String soloOfStringThroughSubtype() {
                    return render(SubOv_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x")));
                }

                // static

                public static String staticGenericOfInteger() {
                    return render(Integer_.TOKEN, i -> staticCall(Ov_.s_T(Integer_.TOKEN), i));
                }

                public static String staticGenericOfString() {
                    return render(String_.TOKEN, s -> staticCall(Ov_.s_T(String_.TOKEN), s));
                }

                public static String staticOverloadOfInteger() {
                    return render(Integer_.TOKEN, i -> staticCall(Ov_.s_Integer, i));
                }

                public static String wrapOfString() {
                    return render(
                            new Optional_<>(String_.TOKEN).token,
                            String_.TOKEN,
                            s -> staticCall(Ov2_.wrap_T(String_.TOKEN), s));
                }

                public static String wrapOfInteger() {
                    return render(
                            new Optional_<>(Integer_.TOKEN).token,
                            Integer_.TOKEN,
                            i -> staticCall(Ov2_.wrap_T(Integer_.TOKEN), i));
                }

                // a method of a type parameter and its overload of the type argument

                public static String ofTypeParameterInStrings() {
                    return render(STRINGS.token, t -> call(t, STRINGS.m_T, literal("x")));
                }

                public static String ofStringInStrings() {
                    return render(STRINGS.token, t -> call(t, STRINGS.m_String, literal("x")));
                }

                public static String ofTypeParameterInIntegers() {
                    return render(INTEGERS.token, t -> call(t, INTEGERS.m_T, literalNull(Integer_.TOKEN)));
                }

                public static String ofStringInIntegers() {
                    return render(INTEGERS.token, t -> call(t, INTEGERS.m_String, literal("x")));
                }

                public static String loneInStrings() {
                    return render(STRINGS.token, t -> call(t, STRINGS.one_T, literal("x")));
                }

                public static String abstractOfTypeParameterInStrings() {
                    Abs_<String> strings = new Abs_<>(String_.TOKEN);
                    return render(strings.token, t -> call(t, strings.m_T, literal("x")));
                }

                // constructors

                public static String newOfTypeParameterInStrings() {
                    return render(String_.TOKEN, s -> field(new_(STRINGS.new_T, s), STRINGS.made));
                }

                public static String newOfStringInStrings() {
                    return render(String_.TOKEN, s -> field(new_(STRINGS.new_String, s), STRINGS.made));
                }

                public static String newOfTypeParameterInIntegers() {
                    return render(Integer_.TOKEN, i -> field(new_(INTEGERS.new_T, i), INTEGERS.made));
                }

                public static String newOfStringInIntegers() {
                    return render(String_.TOKEN, s -> field(new_(INTEGERS.new_String, s), INTEGERS.made));
                }

                public static String newOfNothingInStrings() {
                    return render(String_.TOKEN, s -> field(new_(STRINGS.new_), STRINGS.made));
                }
            }
            """;

    @TempDir
    Path generator;

    @TempDir
    Path generated;

    private ClassLoader metamodels;
    private Class<?> run;

    @BeforeEach
    void generateAndCompileTheGenerator() throws Exception {
        metamodels = load(generate(
                "p.Ov.class, p.Ov2.class, p.T2.class, p.Abs.class, p.SubOv.class, String.class, Integer.class,"
                        + " java.util.Optional.class",
                LIBRARY));
        Compilation compiled = use(GENERATOR);
        assertThat(compiled.status()).as("%s", compiled.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        ProcessorHarness.write(compiled, generator);
        run = new URLClassLoader(new URL[] {generator.toUri().toURL()}, metamodels).loadClass("use.Run");
    }

    /// The source a method of the generator renders.
    private String rendered(String method) throws ReflectiveOperationException {
        try {
            return (String) run.getMethod(method).invoke(null);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException thrown) {
                throw thrown;
            }
            throw e;
        }
    }

    /// Compiles what a method of the generator renders against the library, under every lint.
    private URLClassLoader compiled(String method) throws Exception {
        String source = rendered(method);
        Path directory = generated.resolve(method);
        Compilation compiled = Compiler.javac()
                .withClasspath(List.of(lib.toFile()))
                .withOptions("-proc:none", "-Xlint:all", "-Werror")
                .compile(JavaFileObjects.forSourceString("out.Out", source));
        assertThat(compiled.status())
                .as("%s%n%s", compiled.diagnostics(), source)
                .isEqualTo(Compilation.Status.SUCCESS);
        ProcessorHarness.write(compiled, directory);
        return new URLClassLoader(
                new URL[] {directory.toUri().toURL(), lib.toUri().toURL()}, null);
    }

    /// What the rendered `go` returns for `argument`.
    private Object ran(String method, Class<?> parameter, Object argument) throws Exception {
        try (URLClassLoader loader = compiled(method)) {
            return loader.loadClass("out.Out").getMethod("go", parameter).invoke(null, argument);
        }
    }

    /// What the rendered `go` returns for a new instance of a class of the library.
    private Object ranOn(String method, String receiver) throws Exception {
        try (URLClassLoader loader = compiled(method)) {
            Class<?> type = loader.loadClass(receiver);
            return loader.loadClass("out.Out")
                    .getMethod("go", type)
                    .invoke(null, type.getConstructor().newInstance());
        }
    }

    @Test
    void aGenericMethodGivenTheTypeOfItsOverloadIsRejected() {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("genericOfString"))
                .withMessageContaining("declared m(java.lang.Object) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(java.lang.String) is in p.Ov");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("boundedOfString"))
                .withMessageContaining("declared c(java.lang.Comparable) and is c(java.lang.String) here");
    }

    @Test
    void aGenericMethodGivenAnotherTypeAndItsOverloadAreTheMethodsJavacResolves() throws Exception {
        assertThat(ranOn("genericOfInteger", "p.Ov")).isEqualTo("generic");
        assertThat(ranOn("overloadOfString", "p.Ov")).isEqualTo("string");
        assertThat(ranOn("boundedOfInteger", "p.Ov")).isEqualTo("generic");
        // no overload: any type argument
        assertThat(ranOn("soloOfString", "p.Ov")).isEqualTo("solo");
        assertThat(rendered("soloOfString")).contains(".<String>solo(\"x\")");
    }

    @Test
    void anOverloadTheReceiversTypeAddsIsLeftOutByCallingThroughTheOwner() throws Exception {
        // SubOv adds solo(String), which javac would prefer for a String
        assertThat(rendered("soloOfStringThroughSubtype")).contains("((Ov) v0).<String>solo(\"x\")");
        assertThat(ranOn("soloOfStringThroughSubtype", "p.SubOv")).isEqualTo("solo");
    }

    @Test
    void aStaticGenericMethodGivenTheTypeOfItsOverloadIsRejected() throws Exception {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("staticGenericOfInteger"))
                .withMessageContaining("declared s(java.lang.Object) and is s(java.lang.Integer) here");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("wrapOfString"))
                .withMessageContaining("declared wrap(java.lang.Object) and is wrap(java.lang.String) here")
                .withMessageContaining("in p.Ov2");
        assertThat(ran("staticGenericOfString", String.class, "x")).isEqualTo("generic");
        assertThat(ran("staticOverloadOfInteger", Integer.class, 1)).isEqualTo("integer");
        assertThat(ran("wrapOfInteger", Integer.class, 1)).isEqualTo(java.util.Optional.of(1));
    }

    @Test
    void aMethodOfATypeParameterAndTheOverloadOfItsArgumentAreRejectedBothWays() {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("ofTypeParameterInStrings"))
                .withMessageContaining("declared m(#0) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(java.lang.String) is in p.T2<java.lang.String>");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("ofStringInStrings"))
                .withMessageContaining("as the overload declared m(#0) is in p.T2<java.lang.String>");
        // javac would call the concrete m(String) for the abstract m(T)
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("abstractOfTypeParameterInStrings"))
                .withMessageContaining("declared m(#0) and is m(java.lang.String) here");
    }

    @Test
    void underAnotherTypeArgumentBothMethodsAreTheOnesJavacResolves() throws Exception {
        assertThat(ranOn("ofTypeParameterInIntegers", "p.T2")).isEqualTo("t");
        assertThat(ranOn("ofStringInIntegers", "p.T2")).isEqualTo("string");
        // the only method of its name is found in the table: no cast
        assertThat(ranOn("loneInStrings", "p.T2")).isEqualTo("one");
        assertThat(rendered("loneInStrings")).contains("v0.one(\"x\")");
    }

    @Test
    void aConstructorOfATypeParameterAndTheOverloadOfItsArgumentAreRejectedBothWays() throws Exception {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("newOfTypeParameterInStrings"))
                .withMessageContaining("declared T2(#0) and is T2(java.lang.String) here")
                .withMessageContaining("as the overload declared T2(java.lang.String) is in p.T2<java.lang.String>");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered("newOfStringInStrings"))
                .withMessageContaining("as the overload declared T2(#0) is");
        assertThat(ran("newOfTypeParameterInIntegers", Integer.class, 1)).isEqualTo("t");
        assertThat(ran("newOfStringInIntegers", String.class, "x")).isEqualTo("string");
        assertThat(ran("newOfNothingInStrings", String.class, "x")).isEqualTo("none");
    }
}
