package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// Exercises every form of the declarations of §6.5: each arity of the
/// declaration and body combinators of an interface, a record and an enum,
/// so that the forms are all rendered and compiled, not only the one a
/// smaller example uses.
class TypedDeclarationFormsTest {

    private static final ClassDesc INTERFACE = ClassDesc.of("me.supcheg.example", "Forms");
    private static final ClassDesc RECORD = ClassDesc.of("me.supcheg.example", "FormsRecord");
    private static final ClassDesc ENUM = ClassDesc.of("me.supcheg.example", "FormsEnum");

    @Test
    void everyInterfaceFormRendersAndCompiles() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), INTERFACE, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var a0 = ib.declareAbstractMethod("a0", PrimitiveToken.INT);
                        var a1 = ib.declareVoidAbstractMethod("a1", PrimitiveToken.INT);
                        var a2 = ib.declareAbstractMethod(
                                "a2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var a3 = ib.declareVoidAbstractMethod(
                                "a3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var a4 = ib.declareAbstractMethod(
                                "a4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var d0 = ib.declareDefaultMethod("d0", PrimitiveToken.INT);
                        var d1 = ib.declareVoidDefaultMethod("d1", PrimitiveToken.INT);
                        var d2 = ib.declareDefaultMethod(
                                "d2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var d3 = ib.declareVoidDefaultMethod(
                                "d3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var d4 = ib.declareDefaultMethod(
                                "d4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var s0 = ib.declareStaticMethod("s0", PrimitiveToken.INT);
                        var s1 = ib.declareVoidStaticMethod("s1", PrimitiveToken.INT);
                        var s2 = ib.declareStaticMethod(
                                "s2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s3 = ib.declareVoidStaticMethod(
                                "s3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s4 = ib.declareStaticMethod(
                                "s4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var p0 = ib.declarePrivateMethod("p0", PrimitiveToken.INT);
                        var p1 = ib.declareVoidPrivateMethod("p1", PrimitiveToken.INT);
                        var p2 = ib.declarePrivateMethod(
                                "p2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var p3 = ib.declareVoidPrivateMethod(
                                "p3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var p4 = ib.declarePrivateMethod(
                                "p4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var ps0 = ib.declarePrivateStaticMethod("ps0", PrimitiveToken.INT);
                        var ps1 = ib.declareVoidPrivateStaticMethod("ps1", PrimitiveToken.INT);
                        var ps2 = ib.declarePrivateStaticMethod(
                                "ps2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var ps3 = ib.declareVoidPrivateStaticMethod(
                                "ps3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var ps4 = ib.declarePrivateStaticMethod(
                                "ps4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var bodies = ib.bodies();
                        bodies.define(d0, (b, self) -> b.return_(literal(0)));
                        bodies.define(d1, (b, self, x) -> b.end());
                        bodies.define(d2, (b, self, x, y) -> b.return_(literal(0)));
                        bodies.define(d3, (b, self, x, y, z) -> b.end());
                        bodies.define(d4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(s0, b -> b.return_(literal(0)));
                        bodies.define(s1, (b, x) -> b.end());
                        bodies.define(s2, (b, x, y) -> b.return_(literal(0)));
                        bodies.define(s3, (b, x, y, z) -> b.end());
                        bodies.define(s4, (b, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(p0, (b, self) -> b.return_(literal(0)));
                        bodies.define(p1, (b, self, x) -> b.end());
                        bodies.define(p2, (b, self, x, y) -> b.return_(literal(0)));
                        bodies.define(p3, (b, self, x, y, z) -> b.end());
                        bodies.define(p4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(ps0, b -> b.return_(literal(0)));
                        bodies.define(ps1, (b, x) -> b.end());
                        bodies.define(ps2, (b, x, y) -> b.return_(literal(0)));
                        bodies.define(ps3, (b, x, y, z) -> b.end());
                        bodies.define(ps4, (b, x, y, z, w) -> b.return_(literal(0)));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("public interface Forms").contains("default int d4(");
        assertThat(me.supcheg.javafile.testing.RenderedJava.compile(file).status())
                .isEqualTo(com.google.testing.compile.Compilation.Status.SUCCESS);
    }

    @Test
    void everyRecordFormRendersAndCompiles() {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), RECORD, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component("a", PrimitiveToken.INT);
                        rb.component("b", PrimitiveToken.INT);
                        rb.component("c", PrimitiveToken.INT);
                        rb.component("d", PrimitiveToken.INT);
                        var compact = rb.declareCompactConstructor(
                                PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var c0 = rb.declareConstructor();
                        var c1 = rb.declareConstructor(PrimitiveToken.INT);
                        var c2 = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var c3 = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var m0 = rb.declareMethod("m0", PrimitiveToken.INT);
                        var m1 = rb.declareVoidMethod("m1", PrimitiveToken.INT);
                        var m2 = rb.declareMethod("m2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var m3 = rb.declareVoidMethod("m3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var m4 = rb.declareMethod(
                                "m4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var sm0 = rb.declareStaticMethod("sm0", PrimitiveToken.INT);
                        var sm1 = rb.declareVoidStaticMethod("sm1", PrimitiveToken.INT);
                        var sm2 = rb.declareStaticMethod(
                                "sm2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sm3 = rb.declareVoidStaticMethod(
                                "sm3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sm4 = rb.declareStaticMethod(
                                "sm4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(compact, (b, self, x, y, z, w) -> b.end());
                        bodies.defineDelegating(
                                c0,
                                java.util.List.of(literal(0), literal(0), literal(0), literal(0)),
                                (b, self) -> b.end());
                        bodies.defineDelegating(
                                c1,
                                java.util.List.of(literal(0), literal(0), literal(0), literal(0)),
                                (b, self, x) -> b.end());
                        bodies.defineDelegating(
                                c2,
                                java.util.List.of(literal(0), literal(0), literal(0), literal(0)),
                                (b, self, x, y) -> b.end());
                        bodies.defineDelegating(
                                c3,
                                java.util.List.of(literal(0), literal(0), literal(0), literal(0)),
                                (b, self, x, y, z) -> b.end());
                        bodies.define(m0, (b, self) -> b.return_(literal(0)));
                        bodies.define(m1, (b, self, x) -> b.end());
                        bodies.define(m2, (b, self, x, y) -> b.return_(literal(0)));
                        bodies.define(m3, (b, self, x, y, z) -> b.end());
                        bodies.define(m4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(sm0, b -> b.return_(literal(0)));
                        bodies.define(sm1, (b, x) -> b.end());
                        bodies.define(sm2, (b, x, y) -> b.return_(literal(0)));
                        bodies.define(sm3, (b, x, y, z) -> b.end());
                        bodies.define(sm4, (b, x, y, z, w) -> b.return_(literal(0)));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("public record FormsRecord(").contains("public FormsRecord()");
        assertThat(me.supcheg.javafile.testing.RenderedJava.compile(file).status())
                .isEqualTo(com.google.testing.compile.Compilation.Status.SUCCESS);
    }

    @Test
    void everyEnumFormRendersAndCompiles() {
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ENUM, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var a0 = eb.declareAbstractMethod("a0", PrimitiveToken.INT);
                var a1 = eb.declareVoidAbstractMethod("a1", PrimitiveToken.INT);
                var a2 = eb.declareAbstractMethod("a2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var a3 = eb.declareVoidAbstractMethod("a3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var a4 = eb.declareAbstractMethod(
                        "a4",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT);
                eb.constructor((b, self) -> b.end());
                eb.constructor(PrimitiveToken.INT, (b, self, x) -> b.end());
                eb.constructor(PrimitiveToken.INT, PrimitiveToken.INT, (b, self, x, y) -> b.end());
                eb.constructor(
                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, (b, self, x, y, z) -> b.end());
                eb.constructor(
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        (b, self, x, y, z, w) -> b.end());
                var field = eb.declareField(
                        "value", PrimitiveToken.INT, java.util.Set.of(me.supcheg.javafile.model.Modifier.PRIVATE));
                eb.constant("ONE", java.util.List.of(literal(1)), cb -> {
                    cb.override(a0, (b, self) -> b.return_(literal(0)));
                    cb.override(a1, (b, self, x) -> b.end());
                    cb.override(a2, (b, self, x, y) -> b.return_(literal(0)));
                    cb.override(a3, (b, self, x, y, z) -> b.end());
                    cb.override(a4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                });
                var m0 = eb.declareMethod("m0", PrimitiveToken.INT);
                var m1 = eb.declareVoidMethod("m1", PrimitiveToken.INT);
                var m2 = eb.declareMethod("m2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var m3 = eb.declareVoidMethod("m3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var m4 = eb.declareMethod(
                        "m4",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT);
                var sm0 = eb.declareStaticMethod("sm0", PrimitiveToken.INT);
                var sm1 = eb.declareVoidStaticMethod("sm1", PrimitiveToken.INT);
                var sm2 = eb.declareStaticMethod("sm2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var sm3 = eb.declareVoidStaticMethod("sm3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var sm4 = eb.declareStaticMethod(
                        "sm4",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.initialize(field, literal(0));
                bodies.define(m0, (b, self) -> b.return_(literal(0)));
                bodies.define(m1, (b, self, x) -> b.end());
                bodies.define(m2, (b, self, x, y) -> b.return_(literal(0)));
                bodies.define(m3, (b, self, x, y, z) -> b.end());
                bodies.define(m4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                bodies.define(sm0, b -> b.return_(literal(0)));
                bodies.define(sm1, (b, x) -> b.end());
                bodies.define(sm2, (b, x, y) -> b.return_(literal(0)));
                bodies.define(sm3, (b, x, y, z) -> b.end());
                bodies.define(sm4, (b, x, y, z, w) -> b.return_(literal(0)));
                return bodies;
            }
        });

        assertThat(file.render()).contains("public enum FormsEnum").contains("ONE(1)");
        assertThat(me.supcheg.javafile.testing.RenderedJava.compile(file).status())
                .isEqualTo(com.google.testing.compile.Compilation.Status.SUCCESS);
    }
}
