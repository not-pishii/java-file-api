package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Gathered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.stream.Stream;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.varargs;
import static org.assertj.core.api.Assertions.assertThat;

/// The forms of the typed layer that take a list where they take their arguments one by one: `varargs`,
/// `newArrayOf`, `newArray` and `throwing`. Each list renders what the enumerated form renders, so the
/// calls the two make are byte-identical; every class is compiled under every lint and run.
class ListFormsTest {
    private static final ArrayToken<Object[], Object> OBJECTS = ArrayToken.of(Object_.TOKEN);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<String[][], String[]> PLACES = ArrayToken.of(STRINGS);
    private static final Callable_<String> CALLABLE = new Callable_<>(String_.TOKEN);
    private static final List<ClassToken<? extends Throwable>> IO = List.of(IOException_.TOKEN);

    private static JavaFile class_(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    private static JavaFile header(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    /// The same class, with the elements of `varargs`, `newArrayOf` and `newArray` one by one or as a
    /// list.
    private static JavaFile arrayForms(boolean listed) {
        return class_("Forms", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var spread = cb.declareStaticMethod("spread", PrimitiveToken.INT);
                var arrayed = cb.declareStaticMethod("arrayed", STRINGS);
                var placed = cb.declareStaticMethod("placed", PLACES);
                List<Expr<? extends String>> parts = List.of(literal("a"), literal("b"));
                var bodies = cb.bodies();
                bodies.define(
                        spread,
                        b -> b.return_(call(
                                new_(Gathered_.new_),
                                Gathered_.count_ObjectArray,
                                listed ? varargs(OBJECTS, parts) : varargs(OBJECTS, literal("a"), literal("b")))));
                bodies.define(
                        arrayed,
                        b -> b.return_(
                                listed ? newArrayOf(STRINGS, parts) : newArrayOf(STRINGS, literal("a"), literal("b"))));
                bodies.define(
                        placed,
                        b -> b.return_(
                                listed
                                        ? newArray(PLACES, literal(2), List.of(literal(3)))
                                        : newArray(PLACES, literal(2), literal(3))));
                return bodies;
            }
        });
    }

    @Test
    void varargsNewArrayOfAndNewArrayTakeTheElementsOfAListAsTheyTakeThemOneByOne() throws Throwable {
        CompiledClasses enumerated = CompiledClasses.of(arrayForms(false));
        CompiledClasses listed = CompiledClasses.of(arrayForms(true));

        assertThat(listed.source()).isEqualTo(enumerated.source());
        assertThat(listed.source()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Gathered;

                        public final class Forms {
                            public static int spread() {
                                return new Gathered().count("a", "b");
                            }

                            public static String[] arrayed() {
                                return new String[] {"a", "b"};
                            }

                            public static String[][] placed() {
                                return new String[2][3];
                            }
                        }
                        """);
        assertThat(listed.invoke("spread")).isEqualTo(2);
        assertThat((String[]) listed.invoke("arrayed")).containsExactly("a", "b");
        assertThat((String[][]) listed.invoke("placed")).hasDimensions(2, 3);
    }

    /// The same class, with no element at all: `varargs(type)`, `newArrayOf(type)`, `newArray(type, length)`.
    private static JavaFile emptyForms(boolean listed) {
        return class_("Empties", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var spread = cb.declareStaticMethod("spread", PrimitiveToken.INT);
                var arrayed = cb.declareStaticMethod("arrayed", STRINGS);
                var placed = cb.declareStaticMethod("placed", PLACES);
                var bodies = cb.bodies();
                bodies.define(
                        spread,
                        b -> b.return_(call(
                                new_(Gathered_.new_),
                                Gathered_.count_ObjectArray,
                                listed ? varargs(OBJECTS, List.of()) : varargs(OBJECTS))));
                bodies.define(arrayed, b -> b.return_(listed ? newArrayOf(STRINGS, List.of()) : newArrayOf(STRINGS)));
                bodies.define(
                        placed,
                        b -> b.return_(
                                listed ? newArray(PLACES, literal(2), List.of()) : newArray(PLACES, literal(2))));
                return bodies;
            }
        });
    }

    @Test
    void anEmptyListIsNoArgument() throws Throwable {
        CompiledClasses enumerated = CompiledClasses.of(emptyForms(false));
        CompiledClasses listed = CompiledClasses.of(emptyForms(true));

        assertThat(listed.source()).isEqualTo(enumerated.source());
        assertThat(listed.source()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Gathered;

                        public final class Empties {
                            public static int spread() {
                                return new Gathered().count();
                            }

                            public static String[] arrayed() {
                                return new String[] {};
                            }

                            public static String[][] placed() {
                                return new String[2][];
                            }
                        }
                        """);
        assertThat(listed.invoke("spread")).isEqualTo(0);
        assertThat((String[]) listed.invoke("arrayed")).isEmpty();
        String[][] placed = (String[][]) listed.invoke("placed");
        assertThat(placed.length).isEqualTo(2);
        assertThat(placed[0]).isNull();
    }

    @Test
    void theElementsOfAStreamAreGivenToAParameterOfVariableArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(class_("Streamed", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var spread = cb.declareStaticMethod("spread", PrimitiveToken.INT);
                List<Expr<String>> parts =
                        Stream.of("a", "b", "c").map(Expressions::literal).toList();
                var bodies = cb.bodies();
                bodies.define(
                        spread,
                        b -> b.return_(
                                call(new_(Gathered_.new_), Gathered_.count_ObjectArray, varargs(OBJECTS, parts))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Gathered;

                        public final class Streamed {
                            public static int spread() {
                                return new Gathered().count("a", "b", "c");
                            }
                        }
                        """);
        assertThat(compiled.invoke("spread")).isEqualTo(3);
    }

    /// The same class, with the `throws` clause of an override and of a declared method one by one or as
    /// a list; `none` declares no exception either way.
    private static JavaFile throwingForms(boolean listed) {
        return header("Throwers", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var call = (listed ? h.throwing(IO) : h.throwing(IOException_.TOKEN)).overriding(CALLABLE.call);
                var oh = h.extendsObject();
                oh.implements_(CALLABLE.implement(h.self()).call(call));
                var cb = oh.members();
                var m = (listed ? cb.throwing(IO) : cb.throwing(IOException_.TOKEN))
                        .declareStaticMethod("m", PrimitiveToken.INT);
                var none = (listed ? cb.throwing(List.of()) : cb.throwing())
                        .declareStaticMethod("none", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(call.method(), (b, self) -> b.return_(literal("called")));
                bodies.define(m, b -> b.return_(literal(1)));
                bodies.define(none, b -> b.return_(literal(0)));
                return bodies;
            }
        });
    }

    @Test
    void throwingTakesTheExceptionsOfAListAsItTakesThemOneByOne() throws Throwable {
        CompiledClasses enumerated = CompiledClasses.of(throwingForms(false));
        CompiledClasses listed = CompiledClasses.of(throwingForms(true));

        assertThat(listed.source()).isEqualTo(enumerated.source());
        assertThat(listed.source()).isEqualTo("""
                        package me.supcheg.example;

                        import java.io.IOException;
                        import java.util.concurrent.Callable;

                        public final class Throwers implements Callable<String> {
                            @Override
                            public String call() throws IOException {
                                return "called";
                            }

                            public static int m() throws IOException {
                                return 1;
                            }

                            public static int none() {
                                return 0;
                            }
                        }
                        """);
        assertThat(listed.generated().getMethod("call").getExceptionTypes()).containsExactly(IOException.class);
        assertThat(listed.invoke("m")).isEqualTo(1);
        assertThat(listed.invoke("none")).isEqualTo(0);
    }

    /// The same class, with the `throws` clause of an override one by one or as a list, through the header
    /// of a subclass.
    private static JavaFile risky(boolean listed) {
        return header("Risky", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(Mid_.extend(h.self()));
                var risk = (listed ? sh.throwing(IO) : sh.throwing(IOException_.TOKEN)).overriding(Mid_.risk);
                var cb = sh.members();
                var constructor = cb.declareConstructor();
                var bodies = cb.bodies(constructor, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                bodies.define(risk.method(), (b, self) -> b.end());
                return bodies;
            }
        });
    }

    @Test
    void theHeaderOfASubclassTakesTheExceptionsOfAListAsItTakesThemOneByOne() throws Throwable {
        CompiledClasses enumerated = CompiledClasses.of(risky(false));
        CompiledClasses listed = CompiledClasses.of(risky(true));

        assertThat(listed.source()).isEqualTo(enumerated.source());
        assertThat(listed.source()).isEqualTo("""
                        package me.supcheg.example;

                        import java.io.IOException;
                        import me.supcheg.javafile.typed.fixtures.Mid;

                        public final class Risky extends Mid {
                            @Override
                            public void risk() throws IOException {
                            }

                            public Risky() {
                                super();
                            }
                        }
                        """);
        assertThat(listed.generated().getMethod("risk").getExceptionTypes()).containsExactly(IOException.class);
    }

    /// The same class, with the `throws` clause of an override and of a declared method of a class that
    /// extends an exception, the class itself named by a witness, one by one or as a list.
    private static JavaFile witnessed(boolean listed) {
        return header("Witnessed", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(Exception_.extend(h.self()));
                Upcast<Self, Exception> asException = sh.asSuper();
                var call = (listed
                                ? sh.throwing(asException, h.self(), IO)
                                : sh.throwing(asException, h.self(), IOException_.TOKEN))
                        .overriding(CALLABLE.call);
                sh.implements_(CALLABLE.implement(h.self()).call(call));
                var cb = sh.members();
                cb.declareSerialVersionUID(1L);
                var constructor = cb.declareConstructor();
                var raise = (listed
                                ? cb.throwing(asException, cb.self(), IO)
                                : cb.throwing(asException, cb.self(), IOException_.TOKEN))
                        .declareStaticMethod("raise", PrimitiveToken.INT);
                var bodies = cb.bodies(
                        constructor,
                        ctor -> ctor.super_(Exception_.new_String, literal("failed"), (b, self) -> b.end()));
                bodies.define(call.method(), (b, self) -> b.return_(literal("called")));
                bodies.define(raise, b -> b.return_(literal(1)));
                return bodies;
            }
        });
    }

    @Test
    void aWitnessTakesTheOtherExceptionsOfAListAsItTakesThemOneByOne() throws Throwable {
        CompiledClasses enumerated = CompiledClasses.of(witnessed(false));
        CompiledClasses listed = CompiledClasses.of(witnessed(true));

        assertThat(listed.source()).isEqualTo(enumerated.source());
        assertThat(listed.source()).isEqualTo("""
                        package me.supcheg.example;

                        import java.io.IOException;
                        import java.io.Serial;
                        import java.util.concurrent.Callable;

                        public final class Witnessed extends Exception implements Callable<String> {
                            @Override
                            public String call() throws Witnessed, IOException {
                                return "called";
                            }

                            @Serial
                            private static final long serialVersionUID = 1L;

                            public Witnessed() {
                                super("failed");
                            }

                            public static int raise() throws Witnessed, IOException {
                                return 1;
                            }
                        }
                        """);
        assertThat(listed.generated().getMethod("call").getExceptionTypes())
                .extracting(Class::getSimpleName)
                .containsExactlyInAnyOrder("Witnessed", "IOException");
        assertThat(listed.invoke("call")).isEqualTo("called");
        assertThat(listed.invoke("raise")).isEqualTo(1);
    }
}
