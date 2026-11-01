package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Tag;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The class body of an enum constant (§6.5): instance and static fields with their initializers,
/// helper methods the constant calls, member types of every kind including generic classes, and the
/// overrides the enum's abstract methods still get. Each form is rendered, compiled under `-Xlint:all`
/// and run; a helper that would repeat an override, and a field declared twice, are refused.
class EnumConstantBodiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Plan");

    private static JavaFile plan() {
        return TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var apply = eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                eb.constructor(PrimitiveToken.INT, (b, self, value) -> b.end());
                eb.constant("TWICE", java.util.List.of(literal(2)), cb -> {
                    var base = cb.declareField(
                            "base",
                            PrimitiveToken.INT,
                            DocComment.of("The base value."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("field"))
                                    .build());
                    var unit = cb.declareStaticField(
                            "UNIT",
                            PrimitiveToken.INT,
                            DocComment.of("The unit value."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("static field"))
                                    .build());
                    var helper = cb.declareMethod("helper", PrimitiveToken.INT, PrimitiveToken.INT);
                    var unitMethod = cb.declareStaticMethod("unit", PrimitiveToken.INT);
                    cb.override(apply, (b, self, x) -> b.return_(self.call(helper, x)));
                    cb.initialize(base, literal(10));
                    cb.initialize(unit, literal(1));
                    cb.define(
                            helper,
                            (b, self, x) -> b.return_(me.supcheg.javafile.typed.Expressions.addInt(
                                    me.supcheg.javafile.typed.Expressions.mulInt(x, literal(2)), self.field(base))));
                    cb.define(unitMethod, b -> b.return_(me.supcheg.javafile.typed.Expressions.staticField(unit)));
                    cb.declareNestedClass("Box", typeParam("T"), new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Box, T> MemberBodies<Box, ?> build(ClassHeader<Box> h, TypeVarToken<T> t) {
                            var members = h.extendsObject().members();
                            var echo = members.declareMethod("echo", t, t);
                            var bodies = members.bodies();
                            bodies.define(echo, (b, self, value) -> b.return_(value));
                            return bodies;
                        }
                    });
                });
                return eb.bodies();
            }
        });
    }

    @Test
    void aConstantBodyDeclaresFieldsHelpersStaticMembersAndNestedTypesAndRuns() throws Throwable {
        JavaFile file = plan();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Tag;

                        public enum Plan {
                            TWICE(2) {
                                @Override
                                public int apply(int v0) {
                                    return this.helper(v0);
                                }

                                /**
                                 * The base value.
                                 */
                                @Tag("field")
                                public final int base = 10;

                                /**
                                 * The unit value.
                                 */
                                @Tag("static field")
                                public static final int UNIT = 1;

                                public int helper(int v0) {
                                    return v0 * 2 + this.base;
                                }

                                public static int unit() {
                                    return UNIT;
                                }

                                public static final class Box<T> {
                                    public T echo(T v0) {
                                        return v0;
                                    }
                                }
                            };

                            public abstract int apply(int v0);

                            Plan(int v0) {
                            }
                        }
                        """);

        CompiledClasses compiled = CompiledClasses.of(file);
        Class<?> plan = compiled.generated();
        Object twice = plan.getField("TWICE").get(null);
        var apply = twice.getClass().getDeclaredMethod("apply", int.class);
        apply.trySetAccessible();
        var helper = twice.getClass().getDeclaredMethod("helper", int.class);
        helper.trySetAccessible();
        var unit = twice.getClass().getDeclaredMethod("unit");
        unit.trySetAccessible();
        var base = twice.getClass().getDeclaredField("base");
        base.trySetAccessible();
        var shared = twice.getClass().getDeclaredField("UNIT");
        shared.trySetAccessible();
        assertThat(apply.invoke(twice, 3)).isEqualTo(16);
        assertThat(helper.invoke(twice, 4)).isEqualTo(18);
        assertThat(unit.invoke(null)).isEqualTo(1);
        assertThat(base.get(twice)).isEqualTo(10);
        assertThat(base.getAnnotation(Tag.class).value()).isEqualTo("field");
        assertThat(shared.get(null)).isEqualTo(1);
        Class<?> box = compiled.loader().loadClass("me.supcheg.example.Plan$1$Box");
        var constructor = box.getDeclaredConstructor();
        constructor.trySetAccessible();
        Object inside = constructor.newInstance();
        var echo = box.getDeclaredMethod("echo", Object.class);
        echo.trySetAccessible();
        assertThat(echo.invoke(inside, "x")).isEqualTo("x");
    }

    @Test
    void aConstantBodyDeclaresFieldsAndHelpersOfEveryForm() {
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", cb -> {
                    var f0 = cb.documented("A plain field.").declareField("f0", PrimitiveToken.INT);
                    var f1 = cb.declareField(
                            "f1",
                            PrimitiveToken.INT,
                            Tag_.annotation()
                                    .value(AnnotationValues.string("f1"))
                                    .build());
                    var f2 = cb.declareField(
                            "f2",
                            PrimitiveToken.INT,
                            DocComment.of("An annotated field."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("f2"))
                                    .build());
                    var s0 = cb.documented("A plain static field.").declareStaticField("s0", PrimitiveToken.INT);
                    var s1 = cb.declareStaticField(
                            "s1",
                            PrimitiveToken.INT,
                            Tag_.annotation()
                                    .value(AnnotationValues.string("s1"))
                                    .build());
                    var s2 = cb.declareStaticField(
                            "s2",
                            PrimitiveToken.INT,
                            DocComment.of("An annotated static field."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("s2"))
                                    .build());

                    var m0 = cb.documented(DocComment.of("A helper.")).declareMethod("m0", PrimitiveToken.INT);
                    var m1 = cb.declareMethod("m1", PrimitiveToken.INT, PrimitiveToken.INT);
                    var m2 = cb.declareMethod("m2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var m3 = cb.declareMethod(
                            "m3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var m4 = cb.declareMethod(
                            "m4",
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT);
                    var v0 = cb.declareVoidMethod("v0");
                    var v1 = cb.declareVoidMethod("v1", PrimitiveToken.INT);
                    var v2 = cb.declareVoidMethod("v2", PrimitiveToken.INT, PrimitiveToken.INT);
                    var v3 = cb.declareVoidMethod("v3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var v4 = cb.declareVoidMethod(
                            "v4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var sm0 = cb.declareStaticMethod("sm0", PrimitiveToken.INT);
                    var sm1 = cb.declareStaticMethod("sm1", PrimitiveToken.INT, PrimitiveToken.INT);
                    var sm2 = cb.declareStaticMethod("sm2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var sm3 = cb.declareStaticMethod(
                            "sm3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var sm4 = cb.declareStaticMethod(
                            "sm4",
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT,
                            PrimitiveToken.INT);
                    var sv0 = cb.declareVoidStaticMethod("sv0");
                    var sv1 = cb.declareVoidStaticMethod("sv1", PrimitiveToken.INT);
                    var sv2 = cb.declareVoidStaticMethod("sv2", PrimitiveToken.INT, PrimitiveToken.INT);
                    var sv3 = cb.declareVoidStaticMethod(
                            "sv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                    var sv4 = cb.declareVoidStaticMethod(
                            "sv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);

                    cb.initialize(f0, literal(0));
                    cb.initialize(f1, literal(1));
                    cb.initialize(f2, literal(2));
                    cb.initialize(s0, literal(0));
                    cb.initialize(s1, literal(1));
                    cb.initialize(s2, literal(2));
                    cb.define(m0, (b, self) -> b.return_(literal(0)));
                    cb.define(m1, (b, self, a) -> b.return_(literal(0)));
                    cb.define(m2, (b, self, a, b2) -> b.return_(literal(0)));
                    cb.define(m3, (b, self, a, b2, c) -> b.return_(literal(0)));
                    cb.define(m4, (b, self, a, b2, c, d) -> b.return_(literal(0)));
                    cb.define(v0, (b, self) -> b.end());
                    cb.define(v1, (b, self, a) -> b.end());
                    cb.define(v2, (b, self, a, b2) -> b.end());
                    cb.define(v3, (b, self, a, b2, c) -> b.end());
                    cb.define(v4, (b, self, a, b2, c, d) -> b.end());
                    cb.define(sm0, b -> b.return_(literal(0)));
                    cb.define(sm1, (b, a) -> b.return_(literal(0)));
                    cb.define(sm2, (b, a, b2) -> b.return_(literal(0)));
                    cb.define(sm3, (b, a, b2, c) -> b.return_(literal(0)));
                    cb.define(sm4, (b, a, b2, c, d) -> b.return_(literal(0)));
                    cb.define(sv0, b -> b.end());
                    cb.define(sv1, (b, a) -> b.end());
                    cb.define(sv2, (b, a, b2) -> b.end());
                    cb.define(sv3, (b, a, b2, c) -> b.end());
                    cb.define(sv4, (b, a, b2, c, d) -> b.end());
                });
                return eb.bodies();
            }
        });

        assertThat(file.render())
                .contains("public int m4(int v0, int v1, int v2, int v3) {")
                .contains("public void v4(int v0, int v1, int v2, int v3) {")
                .contains("public static int sm4(int v0, int v1, int v2, int v3) {")
                .contains("public static void sv4(int v0, int v1, int v2, int v3) {");
        CompiledClasses.of(file);
    }

    @Test
    void aFieldOfAConstantBodyIsDeclaredOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant("A", cb -> {
                                    cb.declareField("value", PrimitiveToken.INT);
                                    cb.declareField("value", PrimitiveToken.INT);
                                });
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("field value of");
    }

    @Test
    void aHelperCannotRepeatAnOverrideOfTheEnum() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                var apply = eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                                eb.constant("A", cb -> {
                                    cb.declareMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                                    cb.override(apply, (b, self, x) -> b.return_(x));
                                });
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("has the signature of a method the constant overrides");
    }

    @Test
    void aGenericMemberEnumOfAConstantBodyIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant(
                                        "A",
                                        cb -> cb.declareNestedEnum(
                                                "Level", typeParam("T"), new TypedJavaFile.TypedEnumSpec() {
                                                    @Override
                                                    public <Inner> TypedEnumBodies<Inner> build(
                                                            TypedEnumBuilder<Inner> inner) {
                                                        throw new AssertionError("never called");
                                                    }
                                                }));
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("an enum has no type parameters");
    }

    @Test
    void aGenericMemberAnnotationTypeOfAConstantBodyIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant(
                                        "A",
                                        cb -> cb.declareNestedAnnotationType(
                                                "Tag", typeParam("T"), new TypedJavaFile.TypedAnnotationTypeSpec() {
                                                    @Override
                                                    public <Inner> TypedAnnotationTypeBuilder<Inner> build(
                                                            TypedAnnotationTypeBuilder<Inner> tag) {
                                                        throw new AssertionError("never called");
                                                    }
                                                }));
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("an annotation type cannot be declared in the body of an anonymous class");
    }

    @Test
    void aStaticFieldOfAConstantBodyIsInitialized() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("A", cb -> {
                            var shared = cb.declareStaticField("SHARED", String_.TOKEN);
                            cb.initialize(shared, literal("shared"));
                        });
                        return eb.bodies();
                    }
                }));

        Object constant = compiled.generated().getField("A").get(null);
        var shared = constant.getClass().getDeclaredField("SHARED");
        shared.trySetAccessible();
        assertThat(shared.get(null)).isEqualTo("shared");
    }
}
