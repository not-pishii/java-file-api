package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Gathered_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;

/// An array created with its elements, `new E[] {e1, e2}`, is written `{e1, e2}` where it initializes a
/// variable of exactly its type, and in full everywhere else; an element type that is not reifiable is
/// refused (JLS 15.10.1), as javac refuses the generic array creation, and no element is boxed implicitly.
class ArrayInitializersTest {

    private static final ArrayToken<int[], Prim.Int> INTS = PrimitiveToken.INT.array();
    private static final ArrayToken<int[][], int[]> INTS_2 = ArrayToken.of(INTS);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<Object[], Object> OBJECTS = ArrayToken.of(Object_.TOKEN);
    private static final ArrayToken<Runnable[], Runnable> RUNNABLES = ArrayToken.of(Runnable_.TOKEN);

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    @Test
    void theElementsStandAloneWhereTheTypeIsToldAndInFullWhereItIsNot() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Initializers", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var argument = cb.declareStaticMethod("argument", String_.TOKEN);
                var local = cb.declareStaticMethod("local", String_.TOKEN);
                var widened = cb.declareStaticMethod("widened", PrimitiveToken.INT);
                var nested = cb.declareStaticMethod("nested", PrimitiveToken.INT);
                var empty = cb.declareStaticMethod("empty", PrimitiveToken.INT);
                var lambdas = cb.declareStaticMethod("lambdas", String_.TOKEN, StringBuilder_.TOKEN);
                var returned = cb.declareStaticMethod("returned", STRINGS);
                var element = cb.declareStaticMethod("element", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(
                        argument,
                        b -> b.return_(call(
                                new_(Gathered_.new_),
                                Gathered_.join_StringArray,
                                newArrayOf(STRINGS, literal("a"), literal("b")))));
                bodies.define(
                        local,
                        b -> b.let(
                                STRINGS,
                                newArrayOf(STRINGS, literal("x"), literal("y")),
                                v -> b.return_(at(STRINGS, v, literal(1)))));
                bodies.define(
                        widened,
                        b -> b.let(OBJECTS, newArrayOf(STRINGS, literal("x")), v -> b.return_(length(OBJECTS, v))));
                bodies.define(
                        nested,
                        b -> b.let(
                                INTS_2,
                                newArrayOf(INTS_2, newArrayOf(INTS, literal(1)), newArrayOf(INTS)),
                                v -> b.return_(length(INTS, at(INTS_2, v, literal(0))))));
                bodies.define(empty, b -> b.let(INTS, newArrayOf(INTS), v -> b.return_(length(INTS, v))));
                bodies.define(
                        lambdas,
                        (b, sb) -> b.let(
                                RUNNABLES,
                                newArrayOf(
                                        RUNNABLES,
                                        lambda(
                                                Runnable_.sam,
                                                () -> call(sb, StringBuilder_.append_String, literal("x")))),
                                rs -> b.exec(voidCall(at(RUNNABLES, rs, literal(0)), Runnable_.run))
                                        .return_(call(sb, StringBuilder_.toString))));
                bodies.define(returned, b -> b.return_(newArrayOf(STRINGS, literal("r"))));
                bodies.define(
                        element,
                        b -> b.let(
                                OBJECTS,
                                newArrayOf(OBJECTS, newArrayOf(STRINGS, literal("in"))),
                                v -> b.return_(length(OBJECTS, v))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("new Gathered().join(new String[] {\"a\", \"b\"})")
                .contains("String[] v0 = {\"x\", \"y\"};")
                .contains("Object[] v0 = new String[] {\"x\"};")
                .contains("int[][] v0 = {{1}, {}};")
                .contains("int[] v0 = {};")
                .contains("Runnable[] v1 = {() -> v0.append(\"x\")};")
                .contains("return new String[] {\"r\"};")
                .contains("Object[] v0 = {new String[] {\"in\"}};");
        assertThat(compiled.invoke("argument")).isEqualTo("gathered");
        assertThat(compiled.invoke("local")).isEqualTo("y");
        assertThat(compiled.invoke("widened")).isEqualTo(1);
        assertThat(compiled.invoke("nested")).isEqualTo(1);
        assertThat(compiled.invoke("empty")).isEqualTo(0);
        assertThat(compiled.invoke("lambdas", new StringBuilder())).isEqualTo("x");
        assertThat((String[]) compiled.invoke("returned")).containsExactly("r");
        assertThat(compiled.invoke("element")).isEqualTo(1);
    }

    @Test
    void aFieldOfAnArrayTypeIsInitializedByTheElementsAlone() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Tables", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var exact = cb.declareField("exact", INTS_2);
                var wider = cb.declareField("wider", OBJECTS);
                var width = cb.declareMethod("width", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.initialize(exact, newArrayOf(INTS_2, newArrayOf(INTS, literal(1), literal(2))));
                bodies.initialize(wider, newArrayOf(STRINGS, literal("w")));
                bodies.define(
                        width,
                        (b, self) -> b.return_(length(INTS, at(INTS_2, Expressions.field(self, exact), literal(0)))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final int[][] exact = {{1, 2}};")
                .contains("public final Object[] wider = new String[] {\"w\"};");
        assertThat(compiled.invoke("width")).isEqualTo(2);
    }

    @Test
    void anElementTypeThatIsNotReifiableIsRefusedAsJavacRefusesTheGenericArray() {
        TypeVarToken<Object> t = UnsafeFacts.typeVarToken(Types.typeVar("T"), ConstantDescs.CD_Object);
        var strings = new List_<>(String_.TOKEN).token;

        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> newArrayOf(ArrayToken.of(t)),
                "newArrayOf needs a reifiable type (JLS 4.7), but T is not",
                "<T> void m() { Object o = new T[] {}; }",
                "generic array creation");
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> newArrayOf(ArrayToken.of(strings)),
                "newArrayOf needs a reifiable type (JLS 4.7), but java.util.List<java.lang.String> is not",
                "void m() { Object o = new List<String>[] {}; }",
                "generic array creation");
    }

    @Test
    void anElementTypeOfUnboundedWildcardsIsAccepted() {
        InterfaceToken<List<?>> anyList = UnsafeFacts.interfaceToken(
                Types.parameterized(ConstantDescs.CD_List, Types.unbounded()), MethodTable.EMPTY);
        ArrayToken<List<?>[], List<?>> lists = ArrayToken.of(anyList);

        JavacVerdict.asJavacAccepts(
                () -> file("Wildcards", new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var made = cb.declareStaticMethod("made", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(made, b -> b.return_(length(lists, newArrayOf(lists))));
                        return bodies;
                    }
                }),
                "void m() { Object o = new List<?>[] {}; }");
    }

    @Test
    void aPrimitiveElementOfAReferenceArrayIsRefusedThoughJavacBoxesIt() {
        JavacVerdict.unlikeJavac(
                IllegalArgumentException.class,
                () -> newArrayOf(OBJECTS, literal(1)),
                "newArrayOf takes a java.lang.Object and is given a value of the primitive type int",
                "void m() { Object[] o = new Object[] {1}; }");
    }
}
