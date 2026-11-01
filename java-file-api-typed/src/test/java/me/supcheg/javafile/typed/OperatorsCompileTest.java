package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.addLong;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.bitAndInt;
import static me.supcheg.javafile.typed.Expressions.bitAndLong;
import static me.supcheg.javafile.typed.Expressions.bitNotInt;
import static me.supcheg.javafile.typed.Expressions.bitOrInt;
import static me.supcheg.javafile.typed.Expressions.bitOrLong;
import static me.supcheg.javafile.typed.Expressions.bitXorInt;
import static me.supcheg.javafile.typed.Expressions.classLiteral;
import static me.supcheg.javafile.typed.Expressions.instanceOf;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulLong;
import static me.supcheg.javafile.typed.Expressions.negInt;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.postDecrement;
import static me.supcheg.javafile.typed.Expressions.postIncrement;
import static me.supcheg.javafile.typed.Expressions.preDecrement;
import static me.supcheg.javafile.typed.Expressions.preIncrement;
import static me.supcheg.javafile.typed.Expressions.shlInt;
import static me.supcheg.javafile.typed.Expressions.shlLong;
import static me.supcheg.javafile.typed.Expressions.shrInt;
import static me.supcheg.javafile.typed.Expressions.unaryPlusInt;
import static me.supcheg.javafile.typed.Expressions.ushrInt;
import static me.supcheg.javafile.typed.Expressions.ushrLong;
import static org.assertj.core.api.Assertions.assertThat;

/// The operators of the typed layer: unary, bitwise and shift expressions, class literals, plain
/// `instanceof`, increments and compound assignments. Each form is rendered, compiled under
/// `-Xlint:all`, and run, so the value observed is the one Java gives it.
class OperatorsCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Operators");
    private static final ArrayToken<int[], Prim.Int> INTS = PrimitiveToken.INT.array();

    @Test
    void unaryBitwiseAndShiftOperatorsRenderAndRun() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var ints = cb.declareStaticMethod(
                                "ints",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.LONG);
                        var longs = cb.declareStaticMethod(
                                "longs",
                                PrimitiveToken.LONG,
                                PrimitiveToken.LONG,
                                PrimitiveToken.LONG,
                                PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                ints,
                                (b, a, y, distance) -> b.return_(bitXorInt(
                                        addInt(negInt(a), shlInt(y, distance)),
                                        addInt(
                                                bitOrInt(bitAndInt(a, y), bitNotInt(unaryPlusInt(a))),
                                                shrInt(ushrInt(a, distance), distance)))));
                        bodies.define(
                                longs,
                                (b, a, y, distance) -> b.return_(bitOrLong(
                                        bitAndLong(addLong(a, y), mulLong(a, y)),
                                        shlLong(ushrLong(a, distance), distance))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("-v0")
                .contains("~+v0")
                .contains("v0 & v1")
                .contains("| ~+v0")
                .contains(" ^ ")
                .contains("v1 << v2")
                .contains("v0 >>> v2")
                .contains(">> v2");
        assertThat(compiled.invoke("ints", 5, 3, 33L)).isEqualTo(-3);
        assertThat(compiled.invoke("longs", 6L, 3L, 1)).isEqualTo(6L);
    }

    @Test
    void incrementsReachEveryPlaceInBothOrders() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var count = cb.declareMutableStaticField("count", PrimitiveToken.INT);
                        var total = cb.declareMutableField("total", PrimitiveToken.INT);
                        var local = cb.declareStaticMethod("local", PrimitiveToken.INT, PrimitiveToken.INT);
                        var field = cb.declareMethod("field", PrimitiveToken.INT, PrimitiveToken.INT);
                        var shared = cb.declareStaticMethod("shared", PrimitiveToken.INT, PrimitiveToken.INT);
                        var element = cb.declareStaticMethod("element", PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(count, literal(1));
                        bodies.initialize(total, literal(1));
                        bodies.define(
                                local,
                                (b, seed) -> b.letVar(
                                        PrimitiveToken.INT,
                                        seed,
                                        x -> sequence(
                                                b,
                                                preIncrement(x),
                                                postIncrement(x),
                                                preDecrement(x),
                                                postDecrement(x),
                                                x)));
                        bodies.define(
                                field,
                                (b, self, seed) -> b.exec(assignField(self, total, seed))
                                        .let(
                                                PrimitiveToken.INT,
                                                Expressions.field(self, total),
                                                _ -> sequence(
                                                        b,
                                                        preIncrement(self, total),
                                                        postIncrement(self, total),
                                                        preDecrement(self, total),
                                                        postDecrement(self, total),
                                                        Expressions.field(self, total))));
                        bodies.define(
                                shared,
                                (b, seed) -> b.exec(assignStaticField(count, seed))
                                        .let(
                                                PrimitiveToken.INT,
                                                Expressions.staticField(count),
                                                _ -> sequence(
                                                        b,
                                                        preIncrement(count),
                                                        postIncrement(count),
                                                        preDecrement(count),
                                                        postDecrement(count),
                                                        Expressions.staticField(count))));
                        bodies.define(
                                element,
                                (b, seed) -> b.letVar(
                                        INTS,
                                        newArrayOf(INTS, seed),
                                        array -> b.let(
                                                PrimitiveToken.INT,
                                                Expressions.at(INTS, array, literal(0)),
                                                _ -> sequence(
                                                        b,
                                                        preIncrement(INTS, array, literal(0)),
                                                        postIncrement(INTS, array, literal(0)),
                                                        preDecrement(INTS, array, literal(0)),
                                                        postDecrement(INTS, array, literal(0)),
                                                        Expressions.at(INTS, array, literal(0))))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("++v1")
                .contains("v1++")
                .contains("--v1")
                .contains("v1--")
                .contains("++this.total")
                .contains("this.total++")
                .contains("--this.total")
                .contains("this.total--")
                .contains("++Operators.count")
                .contains("Operators.count++")
                .contains("--Operators.count")
                .contains("Operators.count--")
                .contains("++v1[0]")
                .contains("v1[0]++")
                .contains("--v1[0]")
                .contains("v1[0]--");
        assertThat(compiled.invoke("local", 1)).isEqualTo(9);
        assertThat(compiled.invoke("field", 1)).isEqualTo(9);
        assertThat(compiled.invoke("shared", 1)).isEqualTo(9);
        assertThat(compiled.invoke("element", 1)).isEqualTo(9);
    }

    /// The four increments and decrements of a place, in order, and the place: the value of each
    /// operator is `seed + 1` and the place is `seed` again, so the sum is `5 * seed + 4`.
    private static Terminated<Prim.Int> sequence(
            Body<Prim.Int> b,
            Expr<Prim.Int> preIncrement,
            Expr<Prim.Int> postIncrement,
            Expr<Prim.Int> preDecrement,
            Expr<Prim.Int> postDecrement,
            Expr<Prim.Int> place) {
        return b.let(
                PrimitiveToken.INT,
                preIncrement,
                first -> b.let(
                        PrimitiveToken.INT,
                        postIncrement,
                        second -> b.let(
                                PrimitiveToken.INT,
                                preDecrement,
                                third -> b.let(
                                        PrimitiveToken.INT,
                                        postDecrement,
                                        fourth -> b.return_(addInt(
                                                addInt(addInt(first, second), addInt(third, fourth)), place))))));
    }

    @Test
    void compoundAssignmentsReachAFieldAStaticFieldAndAnArrayElement() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var count = cb.declareMutableStaticField("count", PrimitiveToken.INT);
                        var total = cb.declareMutableField("total", PrimitiveToken.INT);
                        var cycle = cb.declareMethod("cycle", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(count, literal(10));
                        bodies.initialize(total, literal(20));
                        bodies.define(
                                cycle,
                                (b, self) -> b.letVar(
                                        INTS,
                                        newArrayOf(INTS, literal(5)),
                                        array -> b.letVar(
                                                PrimitiveToken.INT,
                                                literal(1),
                                                local -> b.exec(assign(local, AssignOp.ADD_ASSIGN, literal(2)))
                                                        .exec(assignField(self, total, AssignOp.MUL_ASSIGN, literal(2)))
                                                        .exec(assignStaticField(count, AssignOp.ADD_ASSIGN, literal(4)))
                                                        .exec(assignAt(
                                                                INTS,
                                                                array,
                                                                literal(0),
                                                                AssignOp.ADD_ASSIGN,
                                                                literal(4)))
                                                        .return_(addInt(
                                                                addInt(local, Expressions.field(self, total)),
                                                                addInt(
                                                                        Expressions.staticField(count),
                                                                        Expressions.at(INTS, array, literal(0))))))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("v1 += 2")
                .contains("this.total *= 2")
                .contains("Operators.count += 4")
                .contains("v0[0] += 4");
        assertThat(compiled.invoke("cycle")).isEqualTo(66);
        assertThat(compiled.generated().getField("count").getInt(null)).isEqualTo(14);
    }

    @Test
    void aClassLiteralIsOfClassAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var type = cb.declareStaticMethod("type", new Class_<>(String_.TOKEN).token);
                        var bodies = cb.bodies();
                        bodies.define(type, b -> b.return_(classLiteral(String_.TOKEN)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return String.class;");
        assertThat(compiled.invoke("type")).isEqualTo(String.class);
    }

    @Test
    void aPlainInstanceofIsABooleanExpression() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var text = cb.declareStaticMethod("isText", PrimitiveToken.BOOLEAN, Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(text, (b, value) -> b.return_(instanceOf(value, String_.TOKEN)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return v0 instanceof String;");
        assertThat(compiled.invoke("isText", "text")).isEqualTo(true);
        assertThat(compiled.invoke("isText", 1)).isEqualTo(false);
        assertThat(compiled.invoke("isText", (Object) null)).isEqualTo(false);
    }
}
