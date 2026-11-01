package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.supcheg.javafile.facts.PrimitiveToken.INT;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// Every arity of the constructor declarations of a record and an enum, 0 to 12: each rendered, and all compiled
/// together under `-Xlint:all`, so a form the generator forgot is a form javac never sees.
class ConstructorAritiesTest {

    private static final String EXAMPLE = "me.supcheg.example";
    private static final List<? extends Expr<?>> ZEROES = Collections.nCopies(12, literal(0));
    private static final DocComment DOC = DocComment.of("A documented constructor of the enum.");

    private static ClassDesc desc(String kind, int arity) {
        return ClassDesc.of(EXAMPLE, kind + arity);
    }

    /// `int c1, ..., int cn`, or nothing.
    private static String params(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) {
                sb.append(", ");
            }
            sb.append("int c").append(i);
        }
        return sb.toString();
    }

    /// The component's field as one a canonical constructor assigns: the typed component gives the fact a body
    /// reads it by, and an explicit canonical constructor writes it.
    private static <Self, T> MutableFieldRef<Self, T> mutable(TypedRecordComponent<Self, T> component) {
        return UnsafeFacts.mutableField(component.field().owner(), component.name(), component.type(), Access.PUBLIC);
    }

    @Test
    void everyConstructorArityRendersAndCompiles() {
        List<JavaFile> files = new ArrayList<>();
        for (int n = 0; n <= 12; n++) {
            files.add(compact(n));
            files.add(canonical(n));
        }
        JavaFile additional = additional();
        JavaFile additionalOfTwelve = additionalOfTwelve();
        JavaFile enumeration = enumeration();
        files.add(additional);
        files.add(additionalOfTwelve);
        files.add(enumeration);
        files.add(plainHighArityEnumeration());

        CompiledClasses.ofAll(files);

        for (int n = 0; n <= 12; n++) {
            assertThat(files.get(2 * n).render())
                    .contains("public " + desc("Compact", n).displayName() + " {");
            assertThat(files.get(2 * n + 1).render())
                    .contains("public " + desc("Canonical", n).displayName() + "(" + params(n) + ")");
        }
        assertThat(additional.render()).contains("public Additional() {");
        assertThat(additional.render()).contains("public Additional(int v0, int v1, int v2");
        assertThat(additionalOfTwelve.render())
                .contains("public AdditionalTwelve(int v0, int v1, int v2, int v3, int v4, int v5, int v6, int v7,"
                        + " int v8, int v9, int v10, int v11)");
        assertThat(enumeration.render()).contains("FormsEnum() {");
        assertThat(enumeration
                        .render()
                        .lines()
                        .filter(line -> line.strip().startsWith("FormsEnum(int"))
                        .count())
                .isEqualTo(12);
    }

    private static JavaFile compact(int n) {
        return TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), desc("Compact", n), new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        for (int i = 1; i <= n; i++) {
                            rb.component("c" + i, INT);
                        }
                        return switch (n) {
                            case 0 -> {
                                var c = rb.declareCompactConstructor();
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self) -> b.end());
                                yield bodies;
                            }
                            case 1 -> {
                                var c = rb.declareCompactConstructor(INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1) -> b.end());
                                yield bodies;
                            }
                            case 2 -> {
                                var c = rb.declareCompactConstructor(INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2) -> b.end());
                                yield bodies;
                            }
                            case 3 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3) -> b.end());
                                yield bodies;
                            }
                            case 4 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4) -> b.end());
                                yield bodies;
                            }
                            case 5 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5) -> b.end());
                                yield bodies;
                            }
                            case 6 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6) -> b.end());
                                yield bodies;
                            }
                            case 7 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7) -> b.end());
                                yield bodies;
                            }
                            case 8 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8) -> b.end());
                                yield bodies;
                            }
                            case 9 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9) -> b.end());
                                yield bodies;
                            }
                            case 10 -> {
                                var c = rb.declareCompactConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> b.end());
                                yield bodies;
                            }
                            case 11 -> {
                                var c = rb.declareCompactConstructor(
                                        INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> b.end());
                                yield bodies;
                            }
                            case 12 -> {
                                var c = rb.declareCompactConstructor(
                                        INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(
                                        c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> b.end());
                                yield bodies;
                            }
                            default -> throw new AssertionError(n);
                        };
                    }
                });
    }

    private static JavaFile canonical(int n) {
        return TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), desc("Canonical", n), new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        List<TypedRecordComponent<Self, Prim.Int>> comps = new ArrayList<>();
                        for (int i = 1; i <= n; i++) {
                            comps.add(rb.component("c" + i, INT));
                        }
                        return switch (n) {
                            case 0 -> {
                                var c = rb.declareCanonicalConstructor();
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self) -> b.end());
                                yield bodies;
                            }
                            case 1 -> {
                                var c = rb.declareCanonicalConstructor(INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 2 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 3 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 4 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 5 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 6 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 7 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 8 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    b.exec(assignField(self, mutable(comps.get(7)), a8));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 9 -> {
                                var c = rb.declareCanonicalConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    b.exec(assignField(self, mutable(comps.get(7)), a8));
                                    b.exec(assignField(self, mutable(comps.get(8)), a9));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 10 -> {
                                var c = rb.declareCanonicalConstructor(
                                        INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    b.exec(assignField(self, mutable(comps.get(7)), a8));
                                    b.exec(assignField(self, mutable(comps.get(8)), a9));
                                    b.exec(assignField(self, mutable(comps.get(9)), a10));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 11 -> {
                                var c = rb.declareCanonicalConstructor(
                                        INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    b.exec(assignField(self, mutable(comps.get(7)), a8));
                                    b.exec(assignField(self, mutable(comps.get(8)), a9));
                                    b.exec(assignField(self, mutable(comps.get(9)), a10));
                                    b.exec(assignField(self, mutable(comps.get(10)), a11));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            case 12 -> {
                                var c = rb.declareCanonicalConstructor(
                                        INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                                var bodies = rb.bodies();
                                bodies.define(c, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> {
                                    b.exec(assignField(self, mutable(comps.get(0)), a1));
                                    b.exec(assignField(self, mutable(comps.get(1)), a2));
                                    b.exec(assignField(self, mutable(comps.get(2)), a3));
                                    b.exec(assignField(self, mutable(comps.get(3)), a4));
                                    b.exec(assignField(self, mutable(comps.get(4)), a5));
                                    b.exec(assignField(self, mutable(comps.get(5)), a6));
                                    b.exec(assignField(self, mutable(comps.get(6)), a7));
                                    b.exec(assignField(self, mutable(comps.get(7)), a8));
                                    b.exec(assignField(self, mutable(comps.get(8)), a9));
                                    b.exec(assignField(self, mutable(comps.get(9)), a10));
                                    b.exec(assignField(self, mutable(comps.get(10)), a11));
                                    b.exec(assignField(self, mutable(comps.get(11)), a12));
                                    return b.end();
                                });
                                yield bodies;
                            }
                            default -> throw new AssertionError(n);
                        };
                    }
                });
    }

    /// A record of twelve components with additional constructors of arities 0 to 11, each delegating to the
    /// implicit canonical constructor.
    private static JavaFile additional() {
        return TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "Additional"),
                new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        for (int i = 1; i <= 12; i++) {
                            rb.component("c" + i, INT);
                        }
                        var c0 = rb.declareConstructor();
                        var c1 = rb.declareConstructor(INT);
                        var c2 = rb.declareConstructor(INT, INT);
                        var c3 = rb.declareConstructor(INT, INT, INT);
                        var c4 = rb.declareConstructor(INT, INT, INT, INT);
                        var c5 = rb.declareConstructor(INT, INT, INT, INT, INT);
                        var c6 = rb.declareConstructor(INT, INT, INT, INT, INT, INT);
                        var c7 = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT);
                        var c8 = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT, INT);
                        var c9 = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT);
                        var c10 = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                        var c11 = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                        var bodies = rb.bodies();
                        bodies.defineDelegating(c0, ZEROES, (b, self) -> b.end());
                        bodies.defineDelegating(c1, ZEROES, (b, self, a1) -> b.end());
                        bodies.defineDelegating(c2, ZEROES, (b, self, a1, a2) -> b.end());
                        bodies.defineDelegating(c3, ZEROES, (b, self, a1, a2, a3) -> b.end());
                        bodies.defineDelegating(c4, ZEROES, (b, self, a1, a2, a3, a4) -> b.end());
                        bodies.defineDelegating(c5, ZEROES, (b, self, a1, a2, a3, a4, a5) -> b.end());
                        bodies.defineDelegating(c6, ZEROES, (b, self, a1, a2, a3, a4, a5, a6) -> b.end());
                        bodies.defineDelegating(c7, ZEROES, (b, self, a1, a2, a3, a4, a5, a6, a7) -> b.end());
                        bodies.defineDelegating(c8, ZEROES, (b, self, a1, a2, a3, a4, a5, a6, a7, a8) -> b.end());
                        bodies.defineDelegating(c9, ZEROES, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9) -> b.end());
                        bodies.defineDelegating(
                                c10, ZEROES, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> b.end());
                        bodies.defineDelegating(
                                c11, ZEROES, (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> b.end());
                        return bodies;
                    }
                });
    }

    /// A record of one component with an additional constructor of arity 12, whose signature the canonical one of
    /// [additional()] would otherwise be.
    private static JavaFile additionalOfTwelve() {
        return TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "AdditionalTwelve"),
                new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component("c1", INT);
                        var c = rb.declareConstructor(INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT, INT);
                        var bodies = rb.bodies();
                        bodies.defineDelegating(
                                c,
                                List.of(literal(0)),
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> b.end());
                        return bodies;
                    }
                });
    }

    /// An enum with a constructor of every arity 0 to 12, and no constants.
    private static JavaFile enumeration() {
        return TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "FormsEnum"),
                new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constructor(DOC, (b, self) -> b.end());
                        eb.constructor(DOC, INT, (b, self, a1) -> b.end());
                        eb.constructor(DOC, INT, INT, (b, self, a1, a2) -> b.end());
                        eb.constructor(DOC, INT, INT, INT, (b, self, a1, a2, a3) -> b.end());
                        eb.constructor(DOC, INT, INT, INT, INT, (b, self, a1, a2, a3, a4) -> b.end());
                        eb.constructor(DOC, INT, INT, INT, INT, INT, (b, self, a1, a2, a3, a4, a5) -> b.end());
                        eb.constructor(DOC, INT, INT, INT, INT, INT, INT, (b, self, a1, a2, a3, a4, a5, a6) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> b.end());
                        eb.constructor(
                                DOC,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> b.end());
                        return eb.bodies();
                    }
                });
    }

    /// An enum whose plain constructors of arities 5 to 12 are rendered beside those of
    /// [#enumeration], which documents its own: a constructor is of the parameters alone, so
    /// the documented and the plain forms of one arity cannot share an enum.
    private static JavaFile plainHighArityEnumeration() {
        return TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "PlainEnum"),
                new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constructor(INT, INT, INT, INT, INT, (b, self, a1, a2, a3, a4, a5) -> b.end());
                        eb.constructor(INT, INT, INT, INT, INT, INT, (b, self, a1, a2, a3, a4, a5, a6) -> b.end());
                        eb.constructor(
                                INT, INT, INT, INT, INT, INT, INT, (b, self, a1, a2, a3, a4, a5, a6, a7) -> b.end());
                        eb.constructor(
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8) -> b.end());
                        eb.constructor(
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9) -> b.end());
                        eb.constructor(
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> b.end());
                        eb.constructor(
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> b.end());
                        eb.constructor(
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                INT,
                                (b, self, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> b.end());
                        return eb.bodies();
                    }
                });
    }
}
