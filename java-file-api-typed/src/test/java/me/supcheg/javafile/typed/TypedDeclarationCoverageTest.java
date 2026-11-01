package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.Class_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Overridden_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.StaticPick_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// Every form of the declarations of §6.5 at every arity: the method declarations and bodies of
/// an interface, the constructors and members of a record, the overrides of a record and of an
/// enum constant body, the constants, constructors, fields and members of an enum, and the
/// elements of an annotation type. Each is rendered and compiled under `-Xlint:all`.
class TypedDeclarationCoverageTest {

    private static final ClassDesc INTERFACE = ClassDesc.of("me.supcheg.example", "EveryInterface");
    private static final ClassDesc RECORD = ClassDesc.of("me.supcheg.example", "EveryRecord");
    private static final ClassDesc ANNOTATION = ClassDesc.of("me.supcheg.example", "EveryAnnotation");

    @Test
    void everyInterfaceMethodFormRendersAndCompiles() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), INTERFACE, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareAbstractMethod("a0", PrimitiveToken.INT);
                        ib.declareVoidAbstractMethod("av0");
                        ib.declareAbstractMethod("a1", PrimitiveToken.INT, PrimitiveToken.INT);
                        ib.declareVoidAbstractMethod("av1", PrimitiveToken.INT);
                        ib.declareAbstractMethod("a2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        ib.declareVoidAbstractMethod("av2", PrimitiveToken.INT, PrimitiveToken.INT);
                        ib.declareAbstractMethod(
                                "a3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        ib.declareVoidAbstractMethod("av3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        ib.declareAbstractMethod(
                                "a4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        ib.declareVoidAbstractMethod(
                                "av4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                        var d0 = ib.declareDefaultMethod("d0", PrimitiveToken.INT);
                        var dv0 = ib.declareVoidDefaultMethod("dv0");
                        var d1 = ib.declareDefaultMethod("d1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var dv1 = ib.declareVoidDefaultMethod("dv1", PrimitiveToken.INT);
                        var d2 = ib.declareDefaultMethod(
                                "d2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var dv2 = ib.declareVoidDefaultMethod("dv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var d3 = ib.declareDefaultMethod(
                                "d3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var dv3 = ib.declareVoidDefaultMethod(
                                "dv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var d4 = ib.declareDefaultMethod(
                                "d4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var dv4 = ib.declareVoidDefaultMethod(
                                "dv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                        var s0 = ib.declareStaticMethod("s0", PrimitiveToken.INT);
                        var sv0 = ib.declareVoidStaticMethod("sv0");
                        var s1 = ib.declareStaticMethod("s1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv1 = ib.declareVoidStaticMethod("sv1", PrimitiveToken.INT);
                        var s2 = ib.declareStaticMethod(
                                "s2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv2 = ib.declareVoidStaticMethod("sv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var s3 = ib.declareStaticMethod(
                                "s3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv3 = ib.declareVoidStaticMethod(
                                "sv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s4 = ib.declareStaticMethod(
                                "s4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var sv4 = ib.declareVoidStaticMethod(
                                "sv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                        var p0 = ib.declarePrivateMethod("p0", PrimitiveToken.INT);
                        var pv0 = ib.declareVoidPrivateMethod("pv0");
                        var p1 = ib.declarePrivateMethod("p1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var pv1 = ib.declareVoidPrivateMethod("pv1", PrimitiveToken.INT);
                        var p2 = ib.declarePrivateMethod(
                                "p2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var pv2 = ib.declareVoidPrivateMethod("pv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var p3 = ib.declarePrivateMethod(
                                "p3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var pv3 = ib.declareVoidPrivateMethod(
                                "pv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var p4 = ib.declarePrivateMethod(
                                "p4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var pv4 = ib.declareVoidPrivateMethod(
                                "pv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                        var ps0 = ib.declarePrivateStaticMethod("ps0", PrimitiveToken.INT);
                        var psv0 = ib.declareVoidPrivateStaticMethod("psv0");
                        var ps1 = ib.declarePrivateStaticMethod("ps1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var psv1 = ib.declareVoidPrivateStaticMethod("psv1", PrimitiveToken.INT);
                        var ps2 = ib.declarePrivateStaticMethod(
                                "ps2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var psv2 = ib.declareVoidPrivateStaticMethod("psv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var ps3 = ib.declarePrivateStaticMethod(
                                "ps3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var psv3 = ib.declareVoidPrivateStaticMethod(
                                "psv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var ps4 = ib.declarePrivateStaticMethod(
                                "ps4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var psv4 = ib.declareVoidPrivateStaticMethod(
                                "psv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                        var bodies = ib.bodies();
                        ib.table();
                        bodies.define(d0, (b, self) -> b.return_(literal(0)));
                        bodies.define(dv0, (b, self) -> b.end());
                        bodies.define(d1, (b, self, x) -> b.return_(literal(0)));
                        bodies.define(dv1, (b, self, x) -> b.end());
                        bodies.define(d2, (b, self, x, y) -> b.return_(literal(0)));
                        bodies.define(dv2, (b, self, x, y) -> b.end());
                        bodies.define(d3, (b, self, x, y, z) -> b.return_(literal(0)));
                        bodies.define(dv3, (b, self, x, y, z) -> b.end());
                        bodies.define(d4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(dv4, (b, self, x, y, z, w) -> b.end());

                        bodies.define(s0, b -> b.return_(literal(0)));
                        bodies.define(sv0, b -> b.end());
                        bodies.define(s1, (b, x) -> b.return_(literal(0)));
                        bodies.define(sv1, (b, x) -> b.end());
                        bodies.define(s2, (b, x, y) -> b.return_(literal(0)));
                        bodies.define(sv2, (b, x, y) -> b.end());
                        bodies.define(s3, (b, x, y, z) -> b.return_(literal(0)));
                        bodies.define(sv3, (b, x, y, z) -> b.end());
                        bodies.define(s4, (b, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(sv4, (b, x, y, z, w) -> b.end());

                        bodies.define(p0, (b, self) -> b.return_(literal(0)));
                        bodies.define(pv0, (b, self) -> b.end());
                        bodies.define(p1, (b, self, x) -> b.return_(literal(0)));
                        bodies.define(pv1, (b, self, x) -> b.end());
                        bodies.define(p2, (b, self, x, y) -> b.return_(literal(0)));
                        bodies.define(pv2, (b, self, x, y) -> b.end());
                        bodies.define(p3, (b, self, x, y, z) -> b.return_(literal(0)));
                        bodies.define(pv3, (b, self, x, y, z) -> b.end());
                        bodies.define(p4, (b, self, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(pv4, (b, self, x, y, z, w) -> b.end());

                        bodies.define(ps0, b -> b.return_(literal(0)));
                        bodies.define(psv0, b -> b.end());
                        bodies.define(ps1, (b, x) -> b.return_(literal(0)));
                        bodies.define(psv1, (b, x) -> b.end());
                        bodies.define(ps2, (b, x, y) -> b.return_(literal(0)));
                        bodies.define(psv2, (b, x, y) -> b.end());
                        bodies.define(ps3, (b, x, y, z) -> b.return_(literal(0)));
                        bodies.define(psv3, (b, x, y, z) -> b.end());
                        bodies.define(ps4, (b, x, y, z, w) -> b.return_(literal(0)));
                        bodies.define(psv4, (b, x, y, z, w) -> b.end());
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains("public interface EveryInterface")
                .contains("default void dv4(")
                .contains("private static int ps4(");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void everyRecordConstructorFormRendersAndCompiles() {
        List<JavaFile> files = new ArrayList<>();
        for (int components = 0; components <= 4; components++) {
            files.add(recordWith("Canonical" + components, components, false));
            files.add(recordWith("Compact" + components, components, true));
        }

        for (JavaFile file : files) {
            assertThat(file.render()).contains("public record " + simpleName(file));
        }
        assertThat(RenderedJava.compile(files)).succeededWithoutWarnings();
    }

    private static String simpleName(JavaFile file) {
        String qualified = file.qualifiedName();
        return qualified.substring(qualified.lastIndexOf('.') + 1);
    }

    /// A record of `components` components whose canonical constructor is compact or explicit, and
    /// which, if `delegating`, declares an additional constructor of every arity but that of the
    /// canonical one.
    private static JavaFile recordWith(String name, int components, boolean compact) {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", name);
        return TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                return switch (components) {
                    case 0 -> {
                        var canonical = compact ? rb.declareCompactConstructor() : rb.declareCanonicalConstructor();
                        var one = rb.declareConstructor(PrimitiveToken.INT);
                        var two = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var three = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var four = rb.declareConstructor(
                                PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(canonical, (b, self) -> b.end());
                        {
                            bodies.defineDelegating(one, List.of(), (b, self, a) -> b.end());
                            bodies.defineDelegating(two, List.of(), (b, self, a, b2) -> b.end());
                            bodies.defineDelegating(three, List.of(), (b, self, a, b2, c) -> b.end());
                            bodies.defineDelegating(four, List.of(), (b, self, a, b2, c, d) -> b.end());
                        }
                        yield bodies;
                    }
                    case 1 -> {
                        var c0 = rb.component("c0", PrimitiveToken.INT);
                        var canonical = compact
                                ? rb.declareCompactConstructor(PrimitiveToken.INT)
                                : rb.declareCanonicalConstructor(PrimitiveToken.INT);
                        var zero = rb.declareConstructor();
                        var two = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var three = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var four = rb.declareConstructor(
                                PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        if (compact) {
                            bodies.define(canonical, (b, self, a) -> b.end());
                        } else {
                            bodies.define(canonical, (b, self, a) -> {
                                b.exec(assignField(self, mutable(c0), a));
                                return b.end();
                            });
                        }
                        {
                            bodies.defineDelegating(zero, List.of(literal(0)), (b, self) -> b.end());
                            bodies.defineDelegating(two, List.of(literal(0)), (b, self, a, b2) -> b.end());
                            bodies.defineDelegating(three, List.of(literal(0)), (b, self, a, b2, c) -> b.end());
                            bodies.defineDelegating(four, List.of(literal(0)), (b, self, a, b2, c, d) -> b.end());
                        }
                        yield bodies;
                    }
                    case 2 -> {
                        var c0 = rb.component("c0", PrimitiveToken.INT);
                        var c1 = rb.component("c1", PrimitiveToken.INT);
                        var canonical = compact
                                ? rb.declareCompactConstructor(PrimitiveToken.INT, PrimitiveToken.INT)
                                : rb.declareCanonicalConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var zero = rb.declareConstructor();
                        var one = rb.declareConstructor(PrimitiveToken.INT);
                        var three = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var four = rb.declareConstructor(
                                PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        if (compact) {
                            bodies.define(canonical, (b, self, a, b2) -> b.end());
                        } else {
                            bodies.define(canonical, (b, self, a, b2) -> {
                                b.exec(assignField(self, mutable(c0), a));
                                b.exec(assignField(self, mutable(c1), b2));
                                return b.end();
                            });
                        }
                        {
                            bodies.defineDelegating(zero, List.of(literal(0), literal(0)), (b, self) -> b.end());
                            bodies.defineDelegating(one, List.of(literal(0), literal(0)), (b, self, a) -> b.end());
                            bodies.defineDelegating(
                                    three, List.of(literal(0), literal(0)), (b, self, a, b2, c) -> b.end());
                            bodies.defineDelegating(
                                    four, List.of(literal(0), literal(0)), (b, self, a, b2, c, d) -> b.end());
                        }
                        yield bodies;
                    }
                    case 3 -> {
                        var c0 = rb.component("c0", PrimitiveToken.INT);
                        var c1 = rb.component("c1", PrimitiveToken.INT);
                        var c2 = rb.component("c2", PrimitiveToken.INT);
                        var canonical = compact
                                ? rb.declareCompactConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT)
                                : rb.declareCanonicalConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var zero = rb.declareConstructor();
                        var one = rb.declareConstructor(PrimitiveToken.INT);
                        var two = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var four = rb.declareConstructor(
                                PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        if (compact) {
                            bodies.define(canonical, (b, self, a, b2, c) -> b.end());
                        } else {
                            bodies.define(canonical, (b, self, a, b2, c) -> {
                                b.exec(assignField(self, mutable(c0), a));
                                b.exec(assignField(self, mutable(c1), b2));
                                b.exec(assignField(self, mutable(c2), c));
                                return b.end();
                            });
                        }
                        {
                            bodies.defineDelegating(zero, arguments(3), (b, self) -> b.end());
                            bodies.defineDelegating(one, arguments(3), (b, self, a) -> b.end());
                            bodies.defineDelegating(two, arguments(3), (b, self, a, b2) -> b.end());
                            bodies.defineDelegating(four, arguments(3), (b, self, a, b2, c, d) -> b.end());
                        }
                        yield bodies;
                    }
                    default -> {
                        var c0 = rb.component("c0", PrimitiveToken.INT);
                        var c1 = rb.component("c1", PrimitiveToken.INT);
                        var c2 = rb.component("c2", PrimitiveToken.INT);
                        var c3 = rb.component("c3", PrimitiveToken.INT);
                        var canonical = compact
                                ? rb.declareCompactConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT)
                                : rb.declareCanonicalConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var zero = rb.declareConstructor();
                        var one = rb.declareConstructor(PrimitiveToken.INT);
                        var two = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                        var three = rb.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        if (compact) {
                            bodies.define(canonical, (b, self, a, b2, c, d) -> b.end());
                        } else {
                            bodies.define(canonical, (b, self, a, b2, c, d) -> {
                                b.exec(assignField(self, mutable(c0), a));
                                b.exec(assignField(self, mutable(c1), b2));
                                b.exec(assignField(self, mutable(c2), c));
                                b.exec(assignField(self, mutable(c3), d));
                                return b.end();
                            });
                        }
                        {
                            bodies.defineDelegating(zero, arguments(4), (b, self) -> b.end());
                            bodies.defineDelegating(one, arguments(4), (b, self, a) -> b.end());
                            bodies.defineDelegating(two, arguments(4), (b, self, a, b2) -> b.end());
                            bodies.defineDelegating(three, arguments(4), (b, self, a, b2, c) -> b.end());
                        }
                        yield bodies;
                    }
                };
            }
        });
    }

    private static List<Expr<?>> arguments(int count) {
        List<Expr<?>> args = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            args.add(literal(0));
        }
        return args;
    }

    /// The component's field as one a canonical constructor assigns: the typed component gives the
    /// fact a body reads it by, and an explicit canonical constructor writes it.
    private static <Self, T> MutableFieldRef<Self, T> mutable(TypedRecordComponent<Self, T> component) {
        return UnsafeFacts.mutableField(component.field().owner(), component.name(), component.type(), Access.PUBLIC);
    }

    @Test
    void everyRecordMemberFormRendersAndCompiles() {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), RECORD, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component(
                                "x",
                                PrimitiveToken.INT,
                                Tag_.annotation()
                                        .value(AnnotationValues.string("x"))
                                        .build());
                        var m0 = rb.declareMethod("m0", PrimitiveToken.INT);
                        var v0 = rb.declareVoidMethod("v0");
                        var m1 = rb.declareMethod("m1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var v1 = rb.declareVoidMethod("v1", PrimitiveToken.INT);
                        var m2 = rb.declareMethod("m2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var v2 = rb.declareVoidMethod("v2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var m3 = rb.declareMethod(
                                "m3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var v3 = rb.declareVoidMethod("v3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var m4 = rb.declareMethod(
                                "m4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var v4 = rb.declareVoidMethod(
                                "v4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s0 = rb.declareStaticMethod("s0", PrimitiveToken.INT);
                        var sv0 = rb.declareVoidStaticMethod("sv0");
                        var s1 = rb.declareStaticMethod("s1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv1 = rb.declareVoidStaticMethod("sv1", PrimitiveToken.INT);
                        var s2 = rb.declareStaticMethod(
                                "s2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv2 = rb.declareVoidStaticMethod("sv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var s3 = rb.declareStaticMethod(
                                "s3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv3 = rb.declareVoidStaticMethod(
                                "sv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s4 = rb.declareStaticMethod(
                                "s4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var sv4 = rb.declareVoidStaticMethod(
                                "sv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(m0, (b, self) -> b.return_(literal(0)));
                        bodies.define(v0, (b, self) -> b.end());
                        bodies.define(m1, (b, self, a) -> b.return_(literal(0)));
                        bodies.define(v1, (b, self, a) -> b.end());
                        bodies.define(m2, (b, self, a, b2) -> b.return_(literal(0)));
                        bodies.define(v2, (b, self, a, b2) -> b.end());
                        bodies.define(m3, (b, self, a, b2, c) -> b.return_(literal(0)));
                        bodies.define(v3, (b, self, a, b2, c) -> b.end());
                        bodies.define(m4, (b, self, a, b2, c, d) -> b.return_(literal(0)));
                        bodies.define(v4, (b, self, a, b2, c, d) -> b.end());
                        bodies.define(s0, b -> b.return_(literal(0)));
                        bodies.define(sv0, b -> b.end());
                        bodies.define(s1, (b, a) -> b.return_(literal(0)));
                        bodies.define(sv1, (b, a) -> b.end());
                        bodies.define(s2, (b, a, b2) -> b.return_(literal(0)));
                        bodies.define(sv2, (b, a, b2) -> b.end());
                        bodies.define(s3, (b, a, b2, c) -> b.return_(literal(0)));
                        bodies.define(sv3, (b, a, b2, c) -> b.end());
                        bodies.define(s4, (b, a, b2, c, d) -> b.return_(literal(0)));
                        bodies.define(sv4, (b, a, b2, c, d) -> b.end());
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("@Tag(\"x\")").contains("public static int s4(");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void everyOverrideFormRendersAndCompiles() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "EveryOverride");
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var up = rb.implements_(Overridden_.TOKEN);
                        var r0 = rb.declareOverride(up, Overridden_.r0);
                        var r1 = rb.declareOverride(up, Overridden_.r1_int);
                        var r2 = rb.declareOverride(up, Overridden_.r2_int_int);
                        var r3 = rb.declareOverride(up, Overridden_.r3_int_int_int);
                        var r4 = rb.declareOverride(up, Overridden_.r4_int_int_int_int);
                        var v0 = rb.declareOverride(up, Overridden_.v0);
                        var v1 = rb.declareOverride(up, Overridden_.v1_int);
                        var v2 = rb.declareOverride(up, Overridden_.v2_int_int);
                        var v3 = rb.declareOverride(up, Overridden_.v3_int_int_int);
                        var v4 = rb.declareOverride(up, Overridden_.v4_int_int_int_int);
                        var bodies = rb.bodies();
                        bodies.define(r0, (b, self) -> b.return_(literal(0)));
                        bodies.define(r1, (b, self, a) -> b.return_(literal(0)));
                        bodies.define(r2, (b, self, a, b2) -> b.return_(literal(0)));
                        bodies.define(r3, (b, self, a, b2, c) -> b.return_(literal(0)));
                        bodies.define(r4, (b, self, a, b2, c, d) -> b.return_(literal(0)));
                        bodies.define(v0, (b, self) -> b.end());
                        bodies.define(v1, (b, self, a) -> b.end());
                        bodies.define(v2, (b, self, a, b2) -> b.end());
                        bodies.define(v3, (b, self, a, b2, c) -> b.end());
                        bodies.define(v4, (b, self, a, b2, c, d) -> b.end());
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("implements Overridden").contains("@Override");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void everyEnumConstantAndMemberFormRendersAndCompiles() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "EveryEnum");
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var run = eb.implements_(Runnable_.TOKEN);
                var overridden = eb.declareOverride(run, Runnable_.run);
                eb.constructor((b, self) -> b.end());
                eb.constructor(PrimitiveToken.INT, (b, self, value) -> b.end());
                eb.constant("A");
                eb.constant("B", literal(0));
                eb.constant("C", List.of(literal(0)));
                eb.constant("D", cb -> {});
                eb.constant("E", List.of(literal(0)), cb -> {});
                var field = eb.declareField("value", PrimitiveToken.INT, Set.of(Modifier.PRIVATE));
                var stored = eb.declareField("stored", PrimitiveToken.INT, Set.of(Modifier.STATIC));
                var m3 = eb.declareMethod(
                        "m3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var v0 = eb.declareVoidMethod("mv0");
                var v2 = eb.declareVoidMethod("mv2", PrimitiveToken.INT, PrimitiveToken.INT);
                var v4 = eb.declareVoidMethod(
                        "mv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var s1 = eb.declareStaticMethod("st1", PrimitiveToken.INT, PrimitiveToken.INT);
                var sv0 = eb.declareVoidStaticMethod("stv0");
                var sv2 = eb.declareVoidStaticMethod("stv2", PrimitiveToken.INT, PrimitiveToken.INT);
                var s3 = eb.declareStaticMethod(
                        "st3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var sv4 = eb.declareVoidStaticMethod(
                        "stv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.initialize(field, literal(0));
                bodies.initialize(stored, literal(0));
                bodies.define(overridden, (b, self) -> b.end());
                bodies.define(m3, (b, self, a, b2, c) -> b.return_(literal(0)));
                bodies.define(v0, (b, self) -> b.end());
                bodies.define(v2, (b, self, a, b2) -> b.end());
                bodies.define(v4, (b, self, a, b2, c, d) -> b.end());
                bodies.define(s1, (b, a) -> b.return_(literal(0)));
                bodies.define(sv0, b -> b.end());
                bodies.define(sv2, (b, a, b2) -> b.end());
                bodies.define(s3, (b, a, b2, c) -> b.return_(literal(0)));
                bodies.define(sv4, (b, a, b2, c, d) -> b.end());
                return bodies;
            }
        });

        assertThat(file.render()).contains("public enum EveryEnum").contains("implements Runnable");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void everyEnumConstantBodyOverrideFormRendersAndCompiles() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "EveryConstantBody");
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var r0 = eb.declareAbstractMethod("r0", PrimitiveToken.INT);
                var v0 = eb.declareVoidAbstractMethod("v0");
                var r1 = eb.declareAbstractMethod("r1", PrimitiveToken.INT, PrimitiveToken.INT);
                var v1 = eb.declareVoidAbstractMethod("v1", PrimitiveToken.INT);
                var r2 = eb.declareAbstractMethod("r2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var v2 = eb.declareVoidAbstractMethod("v2", PrimitiveToken.INT, PrimitiveToken.INT);
                var r3 = eb.declareAbstractMethod(
                        "r3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var v3 = eb.declareVoidAbstractMethod("v3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                var r4 = eb.declareAbstractMethod(
                        "r4",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        PrimitiveToken.INT);
                var v4 = eb.declareVoidAbstractMethod(
                        "v4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                eb.constructor((b, self) -> b.end());
                eb.constant("A", cb -> {
                    cb.override(r0, (b, self) -> b.return_(literal(0)));
                    cb.override(v0, (b, self) -> b.end());
                    cb.override(r1, (b, self, a) -> b.return_(literal(0)));
                    cb.override(v1, (b, self, a) -> b.end());
                    cb.override(r2, (b, self, a, b2) -> b.return_(literal(0)));
                    cb.override(v2, (b, self, a, b2) -> b.end());
                    cb.override(r3, (b, self, a, b2, c) -> b.return_(literal(0)));
                    cb.override(v3, (b, self, a, b2, c) -> b.end());
                    cb.override(r4, (b, self, a, b2, c, d) -> b.return_(literal(0)));
                    cb.override(v4, (b, self, a, b2, c, d) -> b.end());
                });
                return eb.bodies();
            }
        });

        assertThat(file.render()).contains("A() {").contains("@Override");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void everyAnnotationElementFormRendersAndCompiles() {
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), ANNOTATION, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.element("text", String_.TOKEN);
                        ab.element("count", PrimitiveToken.INT, AnnotationValues.int_(0));
                        ab.element("type", Class_.ANY);
                        ab.element("signal", Signal_.TOKEN);
                        ab.element("texts", ArrayToken.of(String_.TOKEN));
                        ab.element(
                                "signals",
                                ArrayToken.of(Signal_.TOKEN),
                                AnnotationValues.array(AnnotationValues.enum_(Signal_.RED)));
                        ab.annotationElement("tag", Tag_.ANNOTATION);
                        ab.annotationElement(
                                "defaultTag",
                                Tag_.ANNOTATION,
                                AnnotationValues.nested(Tag_.annotation()
                                        .value(AnnotationValues.string("tag"))
                                        .build()));
                        ab.annotationArrayElement("tags", Tag_.ANNOTATION);
                        ab.annotationArrayElement(
                                "defaultTags",
                                Tag_.ANNOTATION,
                                AnnotationValues.array(AnnotationValues.nested(Tag_.annotation()
                                        .value(AnnotationValues.string("tag"))
                                        .build())));
                        return ab;
                    }
                });

        assertThat(file.render())
                .contains("Tag defaultTag() default @Tag(\"tag\");")
                .contains("Tag[] defaultTags() default {")
                .contains("Class<?> type();");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void anAnnotationTypeAndTheOtherKindsInTheUnnamedPackageRender() {
        JavaFile annotation = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("EveryAnnotation"),
                new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.element("text", String_.TOKEN);
                        return ab;
                    }
                });
        JavaFile iface = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("EveryInterface"),
                new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        return ib.bodies();
                    }
                });
        JavaFile record = TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), ClassDesc.of("EveryRecord"), new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        return rb.bodies();
                    }
                });
        JavaFile enumeration = TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), ClassDesc.of("EveryEnum"), new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("A");
                        return eb.bodies();
                    }
                });

        assertThat(annotation.render()).startsWith("public @interface EveryAnnotation {");
        assertThat(iface.render()).startsWith("public interface EveryInterface {");
        assertThat(record.render()).startsWith("public record EveryRecord() {");
        assertThat(enumeration.render()).startsWith("public enum EveryEnum {");
        assertThat(RenderedJava.compile(List.of(annotation, iface, record, enumeration)))
                .succeededWithoutWarnings();
    }

    @Test
    void anInterfacePermitsSeveralSubtypes() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), INTERFACE, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.permits(
                                ClassDesc.of("me.supcheg.example", "One"), ClassDesc.of("me.supcheg.example", "Two"));
                        return ib.bodies();
                    }
                });

        assertThat(file.render()).contains("permits One, Two");
    }

    @Test
    void anAnnotationElementOfAClassThatIsNoStringIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), ANNOTATION, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.element("bad", StaticPick_.TOKEN);
                                return ab;
                            }
                        }))
                .withMessageContaining("cannot be of")
                .withMessageContaining("JLS 9.6.1");
    }

    @Test
    void anAnnotationElementOfATypeVariableIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), ANNOTATION, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.element(
                                        "bad",
                                        UnsafeFacts.typeVarToken(
                                                me.supcheg.javafile.type.Types.typeVar("T"),
                                                java.lang.constant.ConstantDescs.CD_Object));
                                return ab;
                            }
                        }))
                .withMessageContaining("cannot be of");
    }
}
