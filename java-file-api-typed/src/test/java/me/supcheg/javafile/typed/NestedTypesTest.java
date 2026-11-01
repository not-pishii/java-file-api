package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A member type of a class, an interface, a record, an enum and an enum constant's body (§6.5): each
/// is declared through the declaration that encloses it, rendered inside it, compiled under
/// `-Xlint:all` and, where it has a body, run.
class NestedTypesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Nested");

    private static TypedJavaFile.TypedClassHeaderSpec classWith(String value) {
        return new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var m = cb.declareMethod("value", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(m, (b, self) -> b.return_(literal(value)));
                return bodies;
            }
        };
    }

    @Test
    void aClassDeclaresAMemberClassStaticAndInnerAndEveryOtherMemberType() throws Throwable {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareNestedClass("Static", classWith("static"));
                        cb.declareInnerClass("Inner", classWith("inner"));
                        cb.declareNestedInterface("Contract", new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self2> TypedInterfaceBodies<Self2> build(TypedInterfaceBuilder<Self2> ib) {
                                ib.declareAbstractMethod("id", String_.TOKEN);
                                return ib.bodies();
                            }
                        });
                        cb.declareNestedRecord("Point", new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self2> TypedRecordBodies<Self2> build(TypedRecordBuilder<Self2> rb) {
                                rb.component("x", PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        });
                        cb.declareNestedEnum("Level", new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self2> TypedEnumBodies<Self2> build(TypedEnumBuilder<Self2> eb) {
                                eb.constant("LOW");
                                return eb.bodies();
                            }
                        });
                        cb.declareNestedAnnotationType("Tag", new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self2> TypedAnnotationTypeBuilder<Self2> build(
                                    TypedAnnotationTypeBuilder<Self2> ab) {
                                ab.element("value", String_.TOKEN);
                                return ab;
                            }
                        });
                        return cb.bodies();
                    }
                });

        CompiledClasses compiled = CompiledClasses.of(file);

        assertThat(compiled.source())
                .contains("public static final class Static {")
                .contains("public final class Inner {")
                .contains("public interface Contract {")
                .contains("public record Point(int x) {")
                .contains("public enum Level {")
                .contains("public @interface Tag {");
        Object outer = compiled.generated().getConstructor().newInstance();
        Class<?> still = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Nested$Static");
        assertThat(still.getMethod("value").invoke(still.getConstructor().newInstance()))
                .isEqualTo("static");
        Class<?> level = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Nested$Level");
        assertThat(level.getEnumConstants()).extracting(Object::toString).contains("LOW");
        Class<?> inner = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Nested$Inner");
        Object innerValue = inner.getDeclaredConstructor(compiled.generated()).newInstance(outer);
        assertThat(inner.getMethod("value").invoke(innerValue)).isEqualTo("inner");
        Class<?> point = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Nested$Point");
        assertThat(point.getMethod("x").invoke(point.getConstructor(int.class).newInstance(7)))
                .isEqualTo(7);
    }

    @Test
    void anInterfaceDeclaresMemberTypes() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareNestedClass("Member", classWith("member"));
                        ib.declareNestedInterface("Inner", new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self2> TypedInterfaceBodies<Self2> build(TypedInterfaceBuilder<Self2> inner) {
                                inner.declareAbstractMethod("id", String_.TOKEN);
                                return inner.bodies();
                            }
                        });
                        return ib.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("public interface Inner {")
                .contains("public static final class Member {")
                .contains("public interface Inner {");
    }

    @Test
    void aRecordDeclaresMemberTypes() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component("x", PrimitiveToken.INT);
                        rb.declareNestedInterface("Named", new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self2> TypedInterfaceBodies<Self2> build(TypedInterfaceBuilder<Self2> ib) {
                                ib.declareAbstractMethod("name", String_.TOKEN);
                                return ib.bodies();
                            }
                        });
                        rb.declareNestedEnum("Kind", new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self2> TypedEnumBodies<Self2> build(TypedEnumBuilder<Self2> eb) {
                                eb.constant("ONE");
                                return eb.bodies();
                            }
                        });
                        rb.declareNestedAnnotationType("Mark", new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self2> TypedAnnotationTypeBuilder<Self2> build(
                                    TypedAnnotationTypeBuilder<Self2> ab) {
                                ab.element("value", String_.TOKEN);
                                return ab;
                            }
                        });
                        return rb.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("public record Nested(int x) {")
                .contains("public interface Named {")
                .contains("public enum Kind {")
                .contains("public @interface Mark {");
    }

    @Test
    void anEnumAndAnEnumConstantBodyDeclareMemberTypes() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("A", body -> {
                            body.declareNestedClass("Member", classWith("member"));
                            body.declareNestedInterface("Inside", new TypedJavaFile.TypedInterfaceSpec() {
                                @Override
                                public <Self2> TypedInterfaceBodies<Self2> build(TypedInterfaceBuilder<Self2> ib) {
                                    ib.declareAbstractMethod("id", String_.TOKEN);
                                    return ib.bodies();
                                }
                            });
                            body.declareNestedRecord("Element", new TypedJavaFile.TypedRecordSpec() {
                                @Override
                                public <Self2> TypedRecordBodies<Self2> build(TypedRecordBuilder<Self2> rb) {
                                    rb.component("name", String_.TOKEN);
                                    return rb.bodies();
                                }
                            });
                            body.declareNestedEnum("Choice", new TypedJavaFile.TypedEnumSpec() {
                                @Override
                                public <Self2> TypedEnumBodies<Self2> build(TypedEnumBuilder<Self2> inner) {
                                    inner.constant("X");
                                    return inner.bodies();
                                }
                            });
                        });
                        eb.declareNestedRecord("Pair", new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self2> TypedRecordBodies<Self2> build(TypedRecordBuilder<Self2> rb) {
                                rb.component("left", String_.TOKEN);
                                return rb.bodies();
                            }
                        });
                        return eb.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("A() {")
                .contains("public static final class Member {")
                .contains("public interface Inside {")
                .contains("public record Element(String name) {")
                .contains("public enum Choice {")
                .contains("public record Pair(String left) {");
    }

    @Test
    void anInnerClassOfAGenericClassSeesItsTypeParameter() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        cb.declareInnerClass("Inner", new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> ih) {
                                var inner = ih.extendsObject().members();
                                var echo = inner.declareMethod("echo", t, t);
                                var bodies = inner.bodies();
                                bodies.define(echo, (b, self, value) -> b.return_(value));
                                return bodies;
                            }
                        });
                        return cb.bodies();
                    }
                }));

        assertThat(compiled.source()).contains("public final class Inner {").contains("public T echo(T v0) {");
    }

    @Test
    void aMemberClassHasUpToThreeTypeParameters() {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareNestedClass(
                                "Two", typeParam("A"), typeParam("B"), new TypedJavaFile.TypedGenericClassSpec2<>() {
                                    @Override
                                    public <Inner, A, B> MemberBodies<Inner, ?> build(
                                            ClassHeader<Inner> h, TypeVarToken<A> a, TypeVarToken<B> b) {
                                        var inner = h.extendsObject().members();
                                        var m = inner.declareMethod("first", a, b);
                                        var bodies = inner.bodies();
                                        bodies.define(m, (body, self, value) -> body.return_(literalNull(a)));
                                        return bodies;
                                    }
                                });
                        cb.declareNestedClass(
                                "Three",
                                typeParam("A"),
                                typeParam("B"),
                                typeParam("C"),
                                new TypedJavaFile.TypedGenericClassSpec3<>() {
                                    @Override
                                    public <Inner, A, B, C> MemberBodies<Inner, ?> build(
                                            ClassHeader<Inner> h,
                                            TypeVarToken<A> a,
                                            TypeVarToken<B> b,
                                            TypeVarToken<C> c) {
                                        var inner = h.extendsObject().members();
                                        var m = inner.declareMethod("first", a, a, b, c);
                                        var bodies = inner.bodies();
                                        bodies.define(m, (body, self, one, two, three) -> body.return_(one));
                                        return bodies;
                                    }
                                });
                        return cb.bodies();
                    }
                });

        assertThat(file.render())
                .contains("public static final class Two<A, B> {")
                .contains("public static final class Three<A, B, C> {");
    }

    @Test
    void aMemberTypeHasANameOfItsOwn() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareNestedClass("not a name", classWith("no"));
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("a member type of Nested cannot be named 'not a name'");
    }

    @Test
    void aMemberTypeOfAnotherDeclarationIsNotInScope() {
        TypeVarToken<?>[] kept = new TypeVarToken<?>[1];
        TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Other"),
                typeParam("T"),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        kept[0] = t;
                        return h.extendsObject().members().bodies();
                    }
                });

        assertThat(org.assertj.core.api.Assertions.catchThrowable(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareInnerClass("Inner", new TypedJavaFile.TypedClassHeaderSpec() {
                                    @Override
                                    public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> ih) {
                                        ih.extendsObject().members().declareMethod("m", kept[0]);
                                        return Unreached.here();
                                    }
                                });
                                return cb.bodies();
                            }
                        })))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("is not in scope in the signature of");
    }

    @Test
    void aMemberTypeIsRefusedInAnAnnotationType() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.nestedType("Member");
                                return ab;
                            }
                        }))
                .withMessageContaining("an annotation type declares elements alone, and no member type");
    }

    @Test
    void aMemberTypeIsRefusedInAPackageInfoAndAModuleInfo() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.packageInfo(
                        UnsafeFacts.unverifiedClasspath(), "me.supcheg.example", pb -> pb.nestedType("Member")))
                .withMessageContaining("a package-info.java declares annotations alone");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.module(
                        UnsafeFacts.unverifiedClasspath(), "me.supcheg.example", mb -> mb.nestedType("Member")))
                .withMessageContaining("a module-info.java declares directives alone");
    }

    @Test
    void aMemberAnnotationTypeIsRefusedInAnEnumConstantBody() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant(
                                        "A",
                                        body -> body.declareNestedAnnotationType(
                                                "Tag", new TypedJavaFile.TypedAnnotationTypeSpec() {
                                                    @Override
                                                    public <Self2> TypedAnnotationTypeBuilder<Self2> build(
                                                            TypedAnnotationTypeBuilder<Self2> ab) {
                                                        return ab;
                                                    }
                                                }));
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("an annotation type cannot be declared in the body of an anonymous class");
    }
}
