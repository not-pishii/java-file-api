package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntUnaryOperator_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.lambdaBlock;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// Labels (6.3): a labeled loop is broken or continued by its label, a labeled block is broken, and
/// both render exactly and run.
class LabelsCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Labels");

    @Test
    void aNestedLoopIsBrokenAndContinuedByTheLabelOfTheOuterOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var broken = cb.declareStaticMethod("broken", PrimitiveToken.INT);
                        var continued = cb.declareStaticMethod("continued", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(broken, b -> labelled(b, true));
                        bodies.define(continued, b -> labelled(b, false));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("label0: for (")
                .contains("break label0;")
                .contains("continue label0;");
        assertThat(compiled.invoke("broken")).isEqualTo(1);
        assertThat(compiled.invoke("continued")).isEqualTo(3);
    }

    private static Terminated<Prim.Int> labelled(Body<Prim.Int> b, boolean stop) {
        return b.letVar(PrimitiveToken.INT, literal(0), count -> {
            Label outer = b.label();
            return b.for_(
                            outer,
                            PrimitiveToken.INT,
                            literal(0),
                            i -> ltInt(i, literal(3)),
                            i -> assign(i, addInt(i, literal(1))),
                            (loop, _, _) -> loop.for_(
                                    PrimitiveToken.INT,
                                    literal(0),
                                    j -> ltInt(j, literal(3)),
                                    j -> assign(j, addInt(j, literal(1))),
                                    (inner, j, _) -> inner.if_(eqInt(j, literal(1)), t -> {
                                                if (stop) {
                                                    t.break_(outer);
                                                } else {
                                                    t.continue_(outer);
                                                }
                                            })
                                            .exec(assign(count, addInt(count, literal(1))))))
                    .return_(count);
        });
    }

    @Test
    void aLabeledBlockIsBrokenAndCompletesNormally() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareStaticMethod("block", PrimitiveToken.INT, PrimitiveToken.BOOLEAN);
                        var bodies = cb.bodies();
                        bodies.define(
                                block,
                                (b, stop) -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.labeled(
                                                    label,
                                                    inner -> inner.if_(stop, t -> t.break_(label))
                                                            .exec(assign(count, literal(1))))
                                            .return_(count);
                                }));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("label0: {").contains("break label0;");
        assertThat(compiled.invoke("block", true)).isEqualTo(0);
        assertThat(compiled.invoke("block", false)).isEqualTo(1);
    }

    @Test
    void aLabeledBlockWhoseBodyOnlyBreaksItsLabelCompletesNormally() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var jumped = cb.declareStaticMethod("jumped", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                jumped,
                                b -> b.let(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.labeled(label, inner -> inner.break_(label))
                                            .return_(count);
                                }));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("label0: {").contains("break label0;");
        assertThat(compiled.invoke("jumped")).isEqualTo(0);
    }

    @Test
    void aWhileLoopCarriesALabelAndIsLeftByIt() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var spin = cb.declareStaticMethod("spin", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                spin,
                                b -> b.let(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.while_(label, literal(true), (loop, _) -> loop.break_(label))
                                            .return_(count);
                                }));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("label0: while (true) {").contains("break label0;");
        assertThat(compiled.invoke("spin")).isEqualTo(0);
    }

    @Test
    void everyLoopFormCarriesALabel() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        ArrayToken<int[], Prim.Int> ints = PrimitiveToken.INT.array();
                        var list = new List_<>(String_.TOKEN).token;
                        var loop = cb.declareStaticMethod("loop", PrimitiveToken.INT);
                        var array = cb.declareStaticMethod("array", PrimitiveToken.INT, ints);
                        var iterable = cb.declareStaticMethod("iterable", PrimitiveToken.INT, list);
                        var forever = cb.declareVoidStaticMethod("forever");
                        var bodies = cb.bodies();
                        bodies.define(
                                loop,
                                b -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.doWhile(
                                                    label,
                                                    (body, _) -> body.exec(assign(count, addInt(count, literal(1))))
                                                            .if_(eqInt(count, literal(2)), t -> t.break_(label)),
                                                    literal(true))
                                            .return_(count);
                                }));
                        bodies.define(
                                array,
                                (b, values) -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.forEach(
                                                    label,
                                                    ints,
                                                    values,
                                                    (body, value, _) -> body.if_(
                                                                    eqInt(value, literal(2)), t -> t.break_(label))
                                                            .exec(assign(count, addInt(count, value))))
                                            .return_(count);
                                }));
                        bodies.define(
                                iterable,
                                (b, values) -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                                    Label label = b.label();
                                    return b.forEach(
                                                    label,
                                                    String_.TOKEN,
                                                    values,
                                                    (body, value, _) -> body.if_(
                                                                    eqRef(value, literal("skip")), t -> t.break_(label))
                                                            .exec(assign(
                                                                    count, addInt(count, value.call(String_.length)))))
                                            .return_(count);
                                }));
                        bodies.define(forever, b -> {
                            Label label = b.label();
                            return b.loopForever(
                                    label,
                                    outer -> outer.for_(
                                            PrimitiveToken.INT,
                                            literal(0),
                                            i -> eqInt(i, literal(0)),
                                            i -> assign(i, addInt(i, literal(1))),
                                            (inner, _, _) -> inner.break_(label)));
                        });
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("label0: do {")
                .contains("label0: for (int v2 : v0) {")
                .contains("label0: for (String v2 : v0) {")
                .contains("label0: while (true) {")
                .contains("break label0;");
        assertThat(compiled.invoke("loop")).isEqualTo(2);
        assertThat(compiled.invoke("array", (Object) new int[] {1, 2, 3})).isEqualTo(1);
        assertThat(compiled.invoke("iterable", List.of("a", "bb", "skip", "cccc")))
                .isEqualTo(3);
        compiled.invoke("forever");
    }

    @Test
    void theBlocksOfALabeledStatementAreItsBody() {
        Label label = new Label();
        Body<Prim.Int> body = Body.root("body");
        label.attach(body, false);

        assertThat(Instr.blocks(new Instr.Labeled(label, body))).containsExactly(body);
    }

    @Test
    void aContinueOfALabeledBlockIsRefused() {
        assertThatThrownBy(() -> file(b -> b.let(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(label, inner -> inner.continue_(label));
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("which is no loop");
    }

    @Test
    void aLabelOutsideTheStatementItNamesOrNotNamedYetIsRefused() {
        assertThatThrownBy(() -> file(b -> {
                    Label label = b.label();
                    b.break_(label);
                }))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("names no statement yet");
        assertThatThrownBy(() -> file(b -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(label, inner -> inner.exec(assign(count, literal(1))))
                            .break_(label);
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not enclose it");
    }

    @Test
    void aLabelIsRefusedAcrossALambdaBoundaryAndOutOfASwitchExpression() {
        assertThatThrownBy(() -> file(b -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(
                            label,
                            inner -> inner.let(
                                            IntUnaryOperator_.TOKEN,
                                            lambdaBlock(IntUnaryOperator_.sam, (lb, _) -> lb.break_(label)),
                                            ignored -> inner.exec(assign(count, literal(1))))
                                    .return_(count));
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("across a lambda boundary");
        assertThatThrownBy(() -> file(b -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(
                            label,
                            inner -> inner.let(
                                            PrimitiveToken.INT,
                                            switch_(
                                                    Signal_.TOKEN,
                                                    enumConstant(Signal_.RED),
                                                    PrimitiveToken.INT,
                                                    c -> c.case_(Signal_.RED, arm -> arm.break_(label))),
                                            ignored -> inner.exec(assign(count, literal(1))))
                                    .return_(count));
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("out of a switch expression");
    }

    @Test
    void aLabeledBodyThatCannotCompleteAndDoesNotBreakTheLabelIsRefused() {
        assertThatThrownBy(() -> file(b -> b.let(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(label, inner -> inner.return_(literal(1)));
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot complete normally");
    }

    @Test
    void aLabelNamesOneStatement() {
        assertThatThrownBy(() -> file(b -> b.letVar(PrimitiveToken.INT, literal(0), count -> {
                    Label label = b.label();
                    return b.labeled(label, first -> first.exec(assign(count, literal(1))))
                            .labeled(label, second -> second.exec(assign(count, literal(2))));
                })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already names a statement");
    }

    private static JavaFile file(Consumer<? super Body<Prim.Int>> spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var method = cb.declareStaticMethod("method", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(method, b -> {
                    spec.accept(b);
                    return b.return_(literal(0));
                });
                return bodies;
            }
        });
    }
}
