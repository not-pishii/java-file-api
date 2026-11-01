package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// The static fields of the typed declarations of §6.5 — of a class, a record and an enum, immutable and
/// mutable: each form is rendered, the rendered text is asserted, and the code is compiled under
/// `-Xlint:all` and run, so the values it initializes are the ones observed.
class StaticFieldCompileTest {

    private static final ClassDesc COUNTER = ClassDesc.of("me.supcheg.example", "Counter");
    private static final ClassDesc ORIGIN = ClassDesc.of("me.supcheg.example", "Origin");
    private static final ClassDesc PALETTE = ClassDesc.of("me.supcheg.example", "Palette");

    @Test
    void aClassDeclaresStaticFieldsAndInitializesThem() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), COUNTER, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                        var two = cb.declareStaticField("TWO", PrimitiveToken.INT);
                        var base = cb.declareField("base", PrimitiveToken.INT);
                        var bump = cb.declareVoidStaticMethod("bump");
                        var doubled = cb.declareStaticMethod("doubled", PrimitiveToken.INT);
                        var total = cb.declareMutableStaticField("total", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(total, literal(0));
                        bodies.initialize(base, literal(0));
                        bodies.initialize(one, literal(1));
                        bodies.initialize(two, addInt(staticField(one), literal(1)));
                        bodies.define(
                                bump,
                                b -> b.exec(assignStaticField(total, addInt(staticField(total), staticField(two))))
                                        .end());
                        bodies.define(doubled, b -> b.return_(mulInt(staticField(total), literal(2))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).isEqualTo("""
                package me.supcheg.example;

                public final class Counter {
                    public static final int ONE = 1;

                    public static final int TWO = Counter.ONE + 1;

                    public final int base = 0;

                    public static void bump() {
                        Counter.total = Counter.total + Counter.TWO;
                    }

                    public static int doubled() {
                        return Counter.total * 2;
                    }

                    public static int total = 0;
                }
                """);
        assertThat(compiled.invoke("doubled")).isEqualTo(0);
        compiled.invoke("bump");
        assertThat(compiled.invoke("doubled")).isEqualTo(4);
        compiled.invoke("bump");
        assertThat(compiled.invoke("doubled")).isEqualTo(8);
        assertThat(compiled.generated().getField("ONE").getInt(null)).isEqualTo(1);
        assertThat(compiled.generated().getField("TWO").getInt(null)).isEqualTo(2);
    }

    @Test
    void anAnnotatedStaticFieldOfAClassIsRenderedWithItsAnnotation() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), COUNTER, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var tagged = cb.declareStaticField(
                                "TAGGED",
                                PrimitiveToken.INT,
                                Tag_.annotation()
                                        .value(AnnotationValues.string("t"))
                                        .build());
                        var bodies = cb.bodies();
                        bodies.initialize(tagged, literal(7));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).isEqualTo("""
                package me.supcheg.example;

                import me.supcheg.javafile.typed.fixtures.Tag;

                public final class Counter {
                    @Tag("t")
                    public static final int TAGGED = 7;
                }
                """);
        assertThat(compiled.generated().getField("TAGGED").getInt(null)).isEqualTo(7);
    }

    @Test
    void anAnnotatedStaticFieldOfARecordIsRenderedWithItsAnnotation() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), ORIGIN, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var tagged = rb.declareStaticField(
                                "TAGGED",
                                PrimitiveToken.INT,
                                Tag_.annotation()
                                        .value(AnnotationValues.string("t"))
                                        .build());
                        var bodies = rb.bodies();
                        bodies.initialize(tagged, literal(7));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).isEqualTo("""
                package me.supcheg.example;

                import me.supcheg.javafile.typed.fixtures.Tag;

                public record Origin() {
                    @Tag("t")
                    public static final int TAGGED = 7;
                }
                """);
        assertThat(compiled.generated().getField("TAGGED").getInt(null)).isEqualTo(7);
    }

    @Test
    void aRecordDeclaresAStaticField() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), ORIGIN, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var x = rb.component("x", PrimitiveToken.INT);
                        var y = rb.component("y", PrimitiveToken.INT);
                        var zero = rb.declareStaticField("ZERO", PrimitiveToken.INT);
                        var compact = rb.declareCompactConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var sum = rb.declareMethod("sum", PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.initialize(zero, literal(0));
                        bodies.define(compact, (b, self, x0, y0) -> b.end());
                        bodies.define(
                                sum,
                                (b, self) -> b.return_(addInt(
                                        addInt(self.field(x.field()), self.field(y.field())), staticField(zero))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).isEqualTo("""
                package me.supcheg.example;

                public record Origin(int x, int y) {
                    public static final int ZERO = 0;

                    public Origin {
                    }

                    public int sum() {
                        return this.x + this.y + Origin.ZERO;
                    }
                }
                """);
        Object origin =
                compiled.generated().getConstructor(int.class, int.class).newInstance(1, 2);
        assertThat(compiled.generated().getMethod("sum").invoke(origin)).isEqualTo(3);
        assertThat(compiled.generated().getField("ZERO").getInt(null)).isEqualTo(0);
    }

    @Test
    void anEnumDeclaresStaticFieldsAndAssignsTheMutableOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), PALETTE, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        var size = eb.declareStaticField("SIZE", PrimitiveToken.INT);
                        var made = eb.declareMutableStaticField("made", PrimitiveToken.INT);
                        var step = eb.declareStaticField("STEP", PrimitiveToken.INT);
                        eb.constant("RED");
                        eb.constant("GREEN");
                        var next = eb.declareStaticMethod("next", PrimitiveToken.INT);
                        var make = eb.declareVoidStaticMethod("make");
                        var bodies = eb.bodies();
                        bodies.initialize(size, literal(2));
                        bodies.initialize(step, literal(1));
                        bodies.initialize(made, literal(0));
                        bodies.define(
                                make,
                                b -> b.exec(assignStaticField(made, addInt(staticField(made), staticField(step))))
                                        .end());
                        bodies.define(next, b -> b.return_(staticField(made)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).isEqualTo("""
                package me.supcheg.example;

                public enum Palette {
                    RED, GREEN;

                    public static final int SIZE = 2;

                    public static int made = 0;

                    public static final int STEP = 1;

                    public static int next() {
                        return Palette.made;
                    }

                    public static void make() {
                        Palette.made = Palette.made + Palette.STEP;
                    }
                }
                """);
        assertThat(compiled.invoke("next")).isEqualTo(0);
        compiled.invoke("make");
        assertThat(compiled.invoke("next")).isEqualTo(1);
        assertThat(compiled.generated().getField("SIZE").getInt(null)).isEqualTo(2);
        assertThat(compiled.generated().getField("STEP").getInt(null)).isEqualTo(1);
    }

    @Test
    void aStaticFieldInitializedByALiteralIsNoConstantVariableToTheTypedLayer() {
        StaticFieldRef<?>[] one = new StaticFieldRef<?>[1];
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), COUNTER, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var field = cb.declareStaticField("ONE", PrimitiveToken.INT);
                        one[0] = field;
                        var bodies = cb.bodies();
                        bodies.initialize(field, literal(1));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("public static final int ONE = 1;");
        assertThat(one[0].constantValue()).isEmpty();
    }
}
