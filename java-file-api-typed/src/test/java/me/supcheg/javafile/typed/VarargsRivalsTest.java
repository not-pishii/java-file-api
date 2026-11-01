package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Overloads_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.VarargsArray_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.VarargsBase_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.varargs;
import static org.assertj.core.api.Assertions.assertThat;

/// The arguments of a parameter of variable arity one by one, where another method of the name could be
/// applicable to them by its arity: javac takes the method of variable arity when none of the others can be applied
/// to the types of the arguments the call is rendered with, and the call is then written with those types. Where the
/// facts do not disprove that another can, the array of the arguments is written. Every claim is a call of javac,
/// which the rendered class makes: it is compiled under every lint and run.
class VarargsRivalsTest {
    private static final ArrayToken<Object[], Object> OBJECTS = ArrayToken.of(Object_.TOKEN);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<int[], me.supcheg.javafile.facts.Prim.Int> INTS = PrimitiveToken.INT.array();
    private static final ArrayToken<Integer[], Integer> INTEGERS = ArrayToken.of(PrimitiveToken.INT.boxed());

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    @Test
    void anOverloadThatCannotApplyToTheTypesOfTheArgumentsIsOutrankedAndOneThatMayIsNot() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Rivalry", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var format = cb.declareStaticMethod("format", String_.TOKEN, String_.TOKEN);
                var two = cb.declareStaticMethod("two", String_.TOKEN, Object_.TOKEN);
                var same = cb.declareStaticMethod("same", String_.TOKEN, Object_.TOKEN);
                var single = cb.declareStaticMethod("single", String_.TOKEN, Object_.TOKEN);
                var ints = cb.declareStaticMethod("ints", String_.TOKEN);
                var intsTwo = cb.declareStaticMethod("intsTwo", String_.TOKEN);
                var objs = cb.declareStaticMethod("objs", String_.TOKEN);
                var objsTwo = cb.declareStaticMethod("objsTwo", String_.TOKEN);
                var bodies = cb.bodies();
                // format(Locale, String, Object...) takes no String for a Locale: the String is an element
                bodies.define(
                        format,
                        (b, text) -> b.return_(
                                staticCall(String_.format_String_ObjectArray, literal("%s"), varargs(OBJECTS, text))));
                // two(Number, Object...) takes no String for a Number
                bodies.define(
                        two,
                        (b, o) -> b.return_(
                                staticCall(Overloads_.two_String_ObjectArray, literal("s"), varargs(OBJECTS, o))));
                // m(Object, Object) takes (o, o): the array of the arguments tells m(Object...), and takes no (o)
                bodies.define(same, (b, o) -> b.return_(staticCall(Overloads_.m_ObjectArray, varargs(OBJECTS, o, o))));
                bodies.define(single, (b, o) -> b.return_(staticCall(Overloads_.m_ObjectArray, varargs(OBJECTS, o))));
                // ints(long) takes an int, and takes none of two
                bodies.define(ints, b -> b.return_(staticCall(Overloads_.ints_intArray, varargs(INTS, literal(1)))));
                bodies.define(
                        intsTwo,
                        b -> b.return_(staticCall(Overloads_.ints_intArray, varargs(INTS, literal(1), literal(2)))));
                // objs(int) takes an Integer by unboxing, and takes none of two
                bodies.define(
                        objs,
                        b -> b.return_(staticCall(
                                Overloads_.objs_IntegerArray, varargs(INTEGERS, box(PrimitiveToken.INT, literal(1))))));
                bodies.define(
                        objsTwo,
                        b -> b.return_(staticCall(
                                Overloads_.objs_IntegerArray,
                                varargs(
                                        INTEGERS,
                                        box(PrimitiveToken.INT, literal(1)),
                                        box(PrimitiveToken.INT, literal(2))))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("String.format(\"%s\", (Object) v0)")
                .contains("Overloads.two(\"s\", v0)")
                .contains("Overloads.m(new Object[] {v0, v0})")
                .contains("Overloads.m(v0)")
                .contains("Overloads.ints(new int[] {1})")
                .contains("Overloads.ints(1, 2)")
                .contains("Overloads.objs(new Integer[] {Integer.valueOf(1)})")
                .contains("Overloads.objs(Integer.valueOf(1), Integer.valueOf(2))");
        assertThat(compiled.invoke("format", "x")).isEqualTo("x");
        assertThat(compiled.invoke("two", "o")).isEqualTo("string");
        assertThat(compiled.invoke("same", "o")).isEqualTo("var");
        assertThat(compiled.invoke("single", "o")).isEqualTo("var");
        assertThat(compiled.invoke("ints")).isEqualTo("var");
        assertThat(compiled.invoke("intsTwo")).isEqualTo("var");
        assertThat(compiled.invoke("objs")).isEqualTo("var");
        assertThat(compiled.invoke("objsTwo")).isEqualTo("var");
    }

    @Test
    void whereTheTypeDeclaresTheArrayForTheVariableArityTheArgumentsAreTheArray() throws Throwable {
        VarargsBase_<String> base = new VarargsBase_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(file("Arrayed", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var ctor = cb.declareConstructor();
                var m = cb.declareMethod("m", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(ctor, (b, self) -> b.end());
                bodies.define(
                        m,
                        (b, self) -> b.return_(new_(VarargsArray_.new_)
                                .call(base.join_TArray, varargs(STRINGS, literal("a"), literal("b")))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.VarargsArray;

                        public final class Arrayed {
                            public Arrayed() {
                            }

                            public String m() {
                                return new VarargsArray().join(new String[] {"a", "b"});
                            }
                        }
                        """);
        assertThat(compiled.invoke("m")).isEqualTo("array:a+b");
    }
}
