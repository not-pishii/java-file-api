package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Gathered_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.Function;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.cloneArray;
import static me.supcheg.javafile.typed.Expressions.lambdaBlock;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.new_;
import static org.assertj.core.api.Assertions.assertThat;

/// The operations on arrays beside `at`, `length` and `assignAt`: the creation of an array of arrays with the
/// lengths of several dimensions, the copy of an array, the loop over its elements.
class ArrayOperationsTest {

    private static final ArrayToken<int[], Prim.Int> INTS = PrimitiveToken.INT.array();
    private static final ArrayToken<int[][], int[]> INTS_2 = ArrayToken.of(INTS);
    private static final ArrayToken<int[][][], int[][]> INTS_3 = ArrayToken.of(INTS_2);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    private static JavaFile declare(Function<TypedClassBuilder<Object>, TypedClassBodies<Object>> members) {
        return TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Probe"),
                Unbranded.spec(members));
    }

    @Test
    void anArrayIsCreatedWithTheLengthsOfSeveralDimensions() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Dimensions", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var grid = cb.declareStaticMethod("grid", PrimitiveToken.INT);
                var cube = cb.declareStaticMethod("cube", PrimitiveToken.INT);
                var rows = cb.declareStaticMethod("rows", PrimitiveToken.INT);
                var bodies = cb.bodies();
                // new int[2][3]: two rows of three, none null
                bodies.define(
                        grid,
                        b -> b.let(
                                INTS_2,
                                newArray(INTS_2, literal(2), literal(3)),
                                a -> b.return_(a.arrayAccess(INTS_2, literal(1)).length(INTS))));
                // new int[2][3][]: the third dimension has no length
                bodies.define(
                        cube,
                        b -> b.let(
                                INTS_3,
                                newArray(INTS_3, literal(2), literal(3)),
                                a -> b.return_(a.arrayAccess(INTS_3, literal(1)).length(INTS_2))));
                bodies.define(rows, b -> b.let(INTS_2, newArray(INTS_2, literal(4)), a -> b.return_(a.length(INTS_2))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("new int[2][3]")
                .contains("new int[2][3][]")
                .contains("new int[4][]");
        assertThat(compiled.invoke("grid")).isEqualTo(3);
        assertThat(compiled.invoke("cube")).isEqualTo(3);
        assertThat(compiled.invoke("rows")).isEqualTo(4);
    }

    @Test
    void moreLengthsThanDimensionsAreRefusedAsJavacTypesTheCreationOfAnotherArray() {
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> newArray(INTS_2, literal(1), literal(2), literal(3)),
                "newArray of int[][] takes at most 2 lengths, one for each of its dimensions, and is given 3",
                "void m() { int[][] o = new int[1][2][3]; }",
                "incompatible types");
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> newArray(INTS, literal(1), literal(2)),
                "newArray of int[] takes at most 1 length, one for each of its dimensions, and is given 2",
                "void m() { int[] o = new int[1][2]; }",
                "incompatible types");
    }

    @Test
    void anElementTypeThatIsNotReifiableIsRefusedWhateverTheNumberOfLengths() {
        var strings = new List_<>(String_.TOKEN).token;

        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> newArray(ArrayToken.of(ArrayToken.of(strings)), literal(1), literal(2)),
                "newArray needs a reifiable type (JLS 4.7), but java.util.List<java.lang.String> is not",
                "void m() { Object o = new List<String>[1][2]; }",
                "generic array creation");
    }

    @Test
    void aCopyOfAnArrayIsOfTheTypeOfTheArrayAndNeedsNoCast() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Copies", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var joined = cb.declareStaticMethod("joined", String_.TOKEN);
                var original = cb.declareStaticMethod("original", String_.TOKEN);
                var copied = cb.declareStaticMethod("copied", PrimitiveToken.INT);
                var bodies = cb.bodies();
                // the copy is of exactly String[]: the only join(String[]) takes it as it is
                bodies.define(
                        joined,
                        b -> b.let(
                                STRINGS,
                                newArrayOf(STRINGS, literal("x")),
                                a -> b.return_(new_(Gathered_.new_)
                                        .call(Gathered_.join_StringArray, cloneArray(STRINGS, a)))));
                // a store into the copy does not reach the array
                bodies.define(
                        original,
                        b -> b.let(
                                STRINGS,
                                newArrayOf(STRINGS, literal("x")),
                                a -> b.let(
                                        STRINGS,
                                        cloneArray(STRINGS, a),
                                        c -> b.exec(assignAt(STRINGS, c, literal(0), literal("z")))
                                                .return_(a.arrayAccess(STRINGS, literal(0))))));
                bodies.define(
                        copied,
                        b -> b.return_(cloneArray(INTS, newArrayOf(INTS, literal(1), literal(2)))
                                .length(INTS)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("new Gathered().join(v0.clone())")
                .contains("String[] v1 = v0.clone();")
                .contains("new int[] {1, 2}.clone().length");
        assertThat(compiled.invoke("joined")).isEqualTo("gathered");
        assertThat(compiled.invoke("original")).isEqualTo("x");
        assertThat(compiled.invoke("copied")).isEqualTo(2);
    }

    @Test
    void theElementsOfAnArrayAreLoopedOverAsOfTheirComponentType() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Loops", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var sum = cb.declareStaticMethod("sum", PrimitiveToken.INT, INTS);
                var joined = cb.declareStaticMethod("joined", String_.TOKEN, STRINGS);
                var lambda = cb.declareStaticMethod("lambda", String_.TOKEN, STRINGS);
                var bodies = cb.bodies();
                bodies.define(
                        sum,
                        (b, values) -> b.letVar(
                                PrimitiveToken.INT,
                                literal(0),
                                total -> b.forEach(
                                                INTS,
                                                values,
                                                (loop, value, _) -> loop.exec(assign(total, addInt(total, value))))
                                        .return_(total)));
                // the elements up to "stop", the empty ones left out
                bodies.define(
                        joined,
                        (b, words) -> b.let(
                                StringBuilder_.TOKEN,
                                new_(StringBuilder_.new_),
                                sb -> b.forEach(
                                                STRINGS,
                                                words,
                                                (loop, word, ctl) -> loop.if_(
                                                                word.call(String_.isEmpty), t -> t.continue_(ctl))
                                                        .if_(
                                                                word.call(String_.equals_Object, literal("stop")),
                                                                t -> t.break_(ctl))
                                                        .exec(sb.call(StringBuilder_.append_String, word)))
                                        .return_(sb.call(StringBuilder_.toString))));
                // the loop of a lambda
                bodies.define(
                        lambda,
                        (b, words) -> b.let(
                                StringBuilder_.TOKEN,
                                new_(StringBuilder_.new_),
                                sb -> b.let(
                                        Runnable_.TOKEN,
                                        lambdaBlock(
                                                Runnable_.sam,
                                                lb -> lb.forEach(
                                                                STRINGS,
                                                                words,
                                                                (loop, word, _) -> loop.exec(
                                                                        sb.call(StringBuilder_.append_String, word)))
                                                        .end()),
                                        r -> b.exec(r.voidCall(Runnable_.run))
                                                .return_(sb.call(StringBuilder_.toString)))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("for (int v").contains("for (String v");
        assertThat(compiled.invoke("sum", (Object) new int[] {1, 2, 3})).isEqualTo(6);
        assertThat(compiled.invoke("joined", (Object) new String[] {"a", "", "b", "stop", "c"}))
                .isEqualTo("ab");
        assertThat(compiled.invoke("lambda", (Object) new String[] {"x", "y"})).isEqualTo("xy");
    }

    @Test
    void aCheckedExceptionOfAnElementOrOfTheIteratedArrayIsThatOfTheStatement() {
        var read = new_(StringReader_.new_String, literal("a")).call(StringReader_.read);
        JavacVerdict.asJavac(
                () -> declare(cb -> {
                    var m = cb.declareStaticMethod("m", INTS);
                    var bodies = cb.bodies();
                    bodies.define(m, b -> b.return_(newArrayOf(INTS, read)));
                    return bodies;
                }),
                "can throw the checked exception java.io.IOException",
                "static int[] m() { return new int[] {new StringReader(\"a\").read()}; }",
                "unreported exception java.io.IOException");
        JavacVerdict.asJavac(
                () -> declare(cb -> {
                    var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                    var bodies = cb.bodies();
                    bodies.define(
                            m,
                            b -> b.forEach(INTS, newArrayOf(INTS, read), (_, _, _) -> {})
                                    .return_(literal(0)));
                    return bodies;
                }),
                "can throw the checked exception java.io.IOException",
                "static int m() { for (int x : new int[] {new StringReader(\"a\").read()}) {} return 0; }",
                "unreported exception java.io.IOException");
        JavacVerdict.asJavac(
                () -> declare(cb -> {
                    var m = cb.declareStaticMethod("m", INTS_2);
                    var bodies = cb.bodies();
                    bodies.define(m, b -> b.return_(newArray(INTS_2, read)));
                    return bodies;
                }),
                "can throw the checked exception java.io.IOException",
                "static int[][] m() { return new int[new StringReader(\"a\").read()][]; }",
                "unreported exception java.io.IOException");
        JavacVerdict.asJavac(
                () -> declare(cb -> {
                    var m = cb.declareStaticMethod("m", INTS);
                    var bodies = cb.bodies();
                    bodies.define(m, b -> b.return_(cloneArray(INTS, newArrayOf(INTS, read))));
                    return bodies;
                }),
                "can throw the checked exception java.io.IOException",
                "static int[] m() { return new int[] {new StringReader(\"a\").read()}.clone(); }",
                "unreported exception java.io.IOException");
    }
}
