package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.field;
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
    void anAnnotationTypeDeclaresMemberTypes() throws Throwable {
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.element("value", String_.TOKEN);
                        ab.declareNestedClass("Member", classWith("member"));
                        ab.declareNestedInterface("Contract", new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Inner> TypedInterfaceBodies<Inner> build(TypedInterfaceBuilder<Inner> ib) {
                                ib.declareAbstractMethod("id", String_.TOKEN);
                                return ib.bodies();
                            }
                        });
                        ab.declareNestedRecord("Point", new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Inner> TypedRecordBodies<Inner> build(TypedRecordBuilder<Inner> rb) {
                                rb.component("x", PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        });
                        ab.declareNestedEnum("Level", new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Inner> TypedEnumBodies<Inner> build(TypedEnumBuilder<Inner> eb) {
                                eb.constant("LOW");
                                return eb.bodies();
                            }
                        });
                        ab.declareNestedAnnotationType("Tag", new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Inner> TypedAnnotationTypeBuilder<Inner> build(
                                    TypedAnnotationTypeBuilder<Inner> tag) {
                                tag.element("name", String_.TOKEN);
                                return tag;
                            }
                        });
                        return ab;
                    }
                });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public @interface Nested {
                            String value();

                            public static final class Member {
                                public String value() {
                                    return "member";
                                }
                            }

                            public interface Contract {
                                String id();
                            }

                            public record Point(int x) {
                            }

                            public enum Level {
                                LOW;
                            }

                            public @interface Tag {
                                String name();
                            }
                        }
                        """);

        CompiledClasses compiled = CompiledClasses.of(file);
        Class<?> level = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Nested$Level");
        assertThat(level.getEnumConstants()).extracting(Object::toString).containsExactly("LOW");
    }

    @Test
    void aNestedInterfaceAndRecordHaveUpToThreeTypeParameters() {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareNestedInterface(
                                "One", typeParam("A"), new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                                    @Override
                                    public <Inner, A> TypedInterfaceBodies<Inner> build(
                                            TypedInterfaceBuilder<Inner> ib, TypeVarToken<A> a) {
                                        return ib.bodies();
                                    }
                                });
                        cb.declareNestedInterface(
                                "Two",
                                typeParam("A"),
                                typeParam("B"),
                                new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
                                    @Override
                                    public <Inner, A, B> TypedInterfaceBodies<Inner> build(
                                            TypedInterfaceBuilder<Inner> ib, TypeVarToken<A> a, TypeVarToken<B> b) {
                                        return ib.bodies();
                                    }
                                });
                        cb.declareNestedInterface(
                                "Three",
                                typeParam("A"),
                                typeParam("B"),
                                typeParam("C"),
                                new TypedJavaFile.TypedGenericInterfaceSpec3<>() {
                                    @Override
                                    public <Inner, A, B, C> TypedInterfaceBodies<Inner> build(
                                            TypedInterfaceBuilder<Inner> ib,
                                            TypeVarToken<A> a,
                                            TypeVarToken<B> b,
                                            TypeVarToken<C> c) {
                                        return ib.bodies();
                                    }
                                });
                        cb.declareNestedRecord(
                                "OneRecord", typeParam("A"), new TypedJavaFile.TypedGenericRecordSpec1<>() {
                                    @Override
                                    public <Inner, A> TypedRecordBodies<Inner> build(
                                            TypedRecordBuilder<Inner> rb, TypeVarToken<A> a) {
                                        rb.component("value", a);
                                        return rb.bodies();
                                    }
                                });
                        cb.declareNestedRecord(
                                "TwoRecord",
                                typeParam("A"),
                                typeParam("B"),
                                new TypedJavaFile.TypedGenericRecordSpec2<>() {
                                    @Override
                                    public <Inner, A, B> TypedRecordBodies<Inner> build(
                                            TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b) {
                                        rb.component("first", a);
                                        rb.component("second", b);
                                        return rb.bodies();
                                    }
                                });
                        cb.declareNestedRecord(
                                "ThreeRecord",
                                typeParam("A"),
                                typeParam("B"),
                                typeParam("C"),
                                new TypedJavaFile.TypedGenericRecordSpec3<>() {
                                    @Override
                                    public <Inner, A, B, C> TypedRecordBodies<Inner> build(
                                            TypedRecordBuilder<Inner> rb,
                                            TypeVarToken<A> a,
                                            TypeVarToken<B> b,
                                            TypeVarToken<C> c) {
                                        rb.component("first", a);
                                        rb.component("second", b);
                                        rb.component("third", c);
                                        return rb.bodies();
                                    }
                                });
                        return cb.bodies();
                    }
                });

        assertThat(file.render())
                .contains("public interface One<A> {")
                .contains("public interface Two<A, B> {")
                .contains("public interface Three<A, B, C> {")
                .contains("public record OneRecord<A>(A value) {")
                .contains("public record TwoRecord<A, B>(A first, B second) {")
                .contains("public record ThreeRecord<A, B, C>(A first, B second, C third) {");
        CompiledClasses.of(file);
    }

    @Test
    void aGenericMemberTypeOfAnInterfaceIsGenericToo() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareNestedInterface(
                                "One", typeParam("A"), new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                                    @Override
                                    public <Inner, A> TypedInterfaceBodies<Inner> build(
                                            TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a) {
                                        return inner.bodies();
                                    }
                                });
                        ib.declareNestedRecord(
                                "Pair", typeParam("A"), typeParam("B"), new TypedJavaFile.TypedGenericRecordSpec2<>() {
                                    @Override
                                    public <Inner, A, B> TypedRecordBodies<Inner> build(
                                            TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b) {
                                        rb.component("first", a);
                                        rb.component("second", b);
                                        return rb.bodies();
                                    }
                                });
                        return ib.bodies();
                    }
                });

        assertThat(file.render())
                .contains("public interface One<A> {")
                .contains("public record Pair<A, B>(A first, B second) {");
        CompiledClasses.of(file);
    }

    @Test
    void aGenericMemberTypeOfAnAnnotationTypeIsGenericTooAndAGenericEnumIsRefused() {
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.declareNestedInterface(
                                "One", typeParam("A"), new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                                    @Override
                                    public <Inner, A> TypedInterfaceBodies<Inner> build(
                                            TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a) {
                                        return inner.bodies();
                                    }
                                });
                        ab.declareNestedRecord(
                                "Pair",
                                typeParam("A"),
                                typeParam("B"),
                                typeParam("C"),
                                new TypedJavaFile.TypedGenericRecordSpec3<>() {
                                    @Override
                                    public <Inner, A, B, C> TypedRecordBodies<Inner> build(
                                            TypedRecordBuilder<Inner> rb,
                                            TypeVarToken<A> a,
                                            TypeVarToken<B> b,
                                            TypeVarToken<C> c) {
                                        rb.component("first", a);
                                        rb.component("second", b);
                                        rb.component("third", c);
                                        return rb.bodies();
                                    }
                                });
                        return ab;
                    }
                });

        assertThat(file.render())
                .contains("public interface One<A> {")
                .contains("public record Pair<A, B, C>(A first, B second, C third) {");
        CompiledClasses.of(file);
    }

    @Test
    void aGenericNestedEnumAndAnnotationTypeAreRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareNestedEnum("Level", typeParam("T"), noEnum());
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("an enum has no type parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareNestedAnnotationType("Tag", typeParam("T"), noAnnotationType());
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("an annotation type has no type parameters");
    }

    private static TypedJavaFile.TypedEnumSpec noEnum() {
        return new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                throw new AssertionError("the specification of a refused enum is never called");
            }
        };
    }

    private static TypedJavaFile.TypedAnnotationTypeSpec noAnnotationType() {
        return new TypedJavaFile.TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                throw new AssertionError("the specification of a refused annotation type is never called");
            }
        };
    }

    @Test
    void anInnerClassReachesItsOuterInstanceAndIsCreatedFromAnOuterMethod() throws Throwable {
        Invocable[] ctor = new Invocable[1];
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var label = cb.declareField("label", String_.TOKEN);
                        var inner = cb.declareInnerClass("Inner", new TypedJavaFile.TypedInnerClassSpec<Self>() {
                            @Override
                            public <Inner> MemberBodies<Inner, ?> build(
                                    ClassHeader<Inner> h, InnerClass<Self, Inner> handle) {
                                var members = h.extendsObject().members();
                                var created = members.declareConstructor();
                                var read = members.declareMethod("read", String_.TOKEN);
                                var bodies = members.bodies();
                                bodies.define(created, (b, self) -> b.end());
                                bodies.define(read, (b, self) -> b.return_(field(handle.outerThis(), label)));
                                ctor[0] = created;
                                return bodies;
                            }
                        });
                        var make = cb.declareMethod("make", inner.token());
                        var bodies = cb.bodies();
                        bodies.initialize(label, literal("outer"));
                        bodies.define(make, (b, self) -> b.return_(inner.new_(self, ctor[0])));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("return Nested.this.label;").contains("return this.new Inner();");

        CompiledClasses compiled = CompiledClasses.of(file);
        Object outer = compiled.generated().getConstructor().newInstance();
        Object inner = compiled.generated().getMethod("make").invoke(outer);
        assertThat(inner.getClass().getMethod("read").invoke(inner)).isEqualTo("outer");
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

    @Test
    void aMemberTypeOfEveryGenericArityIsDeclaredInAnInterfaceAndAnAnnotationType() {
        TypedJavaFile.TypedGenericInterfaceSpec1<Object> oneInterface =
                new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                    @Override
                    public <Inner, A> TypedInterfaceBodies<Inner> build(
                            TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a) {
                        return inner.bodies();
                    }
                };
        TypedJavaFile.TypedGenericInterfaceSpec2<Object, Object> twoInterfaces =
                new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
                    @Override
                    public <Inner, A, B> TypedInterfaceBodies<Inner> build(
                            TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a, TypeVarToken<B> b) {
                        return inner.bodies();
                    }
                };
        TypedJavaFile.TypedGenericInterfaceSpec3<Object, Object, Object> threeInterfaces =
                new TypedJavaFile.TypedGenericInterfaceSpec3<>() {
                    @Override
                    public <Inner, A, B, C> TypedInterfaceBodies<Inner> build(
                            TypedInterfaceBuilder<Inner> inner,
                            TypeVarToken<A> a,
                            TypeVarToken<B> b,
                            TypeVarToken<C> c) {
                        return inner.bodies();
                    }
                };
        TypedJavaFile.TypedGenericRecordSpec1<Object> oneRecord = new TypedJavaFile.TypedGenericRecordSpec1<>() {
            @Override
            public <Inner, A> TypedRecordBodies<Inner> build(TypedRecordBuilder<Inner> rb, TypeVarToken<A> a) {
                rb.component("value", a);
                return rb.bodies();
            }
        };
        TypedJavaFile.TypedGenericRecordSpec2<Object, Object> twoRecords =
                new TypedJavaFile.TypedGenericRecordSpec2<>() {
                    @Override
                    public <Inner, A, B> TypedRecordBodies<Inner> build(
                            TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b) {
                        rb.component("first", a);
                        rb.component("second", b);
                        return rb.bodies();
                    }
                };
        TypedJavaFile.TypedGenericRecordSpec3<Object, Object, Object> threeRecords =
                new TypedJavaFile.TypedGenericRecordSpec3<>() {
                    @Override
                    public <Inner, A, B, C> TypedRecordBodies<Inner> build(
                            TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                        rb.component("first", a);
                        rb.component("second", b);
                        rb.component("third", c);
                        return rb.bodies();
                    }
                };

        JavaFile anInterface = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareNestedInterface("I1", typeParam("A"), oneInterface);
                        ib.declareNestedInterface("I2", typeParam("A"), typeParam("B"), twoInterfaces);
                        ib.declareNestedInterface(
                                "I3", typeParam("A"), typeParam("B"), typeParam("C"), threeInterfaces);
                        ib.declareNestedRecord("R1", typeParam("A"), oneRecord);
                        ib.declareNestedRecord("R2", typeParam("A"), typeParam("B"), twoRecords);
                        ib.declareNestedRecord("R3", typeParam("A"), typeParam("B"), typeParam("C"), threeRecords);
                        return ib.bodies();
                    }
                });
        JavaFile anAnnotationType = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.declareNestedInterface("I1", typeParam("A"), oneInterface);
                        ab.declareNestedInterface("I2", typeParam("A"), typeParam("B"), twoInterfaces);
                        ab.declareNestedInterface(
                                "I3", typeParam("A"), typeParam("B"), typeParam("C"), threeInterfaces);
                        ab.declareNestedRecord("R1", typeParam("A"), oneRecord);
                        ab.declareNestedRecord("R2", typeParam("A"), typeParam("B"), twoRecords);
                        ab.declareNestedRecord("R3", typeParam("A"), typeParam("B"), typeParam("C"), threeRecords);
                        return ab;
                    }
                });

        assertThat(anInterface.render())
                .contains("public interface I1<A> {")
                .contains("public interface I2<A, B> {")
                .contains("public interface I3<A, B, C> {")
                .contains("public record R1<A>(A value) {")
                .contains("public record R2<A, B>(A first, B second) {")
                .contains("public record R3<A, B, C>(A first, B second, C third) {");
        assertThat(anAnnotationType.render())
                .contains("public interface I3<A, B, C> {")
                .contains("public record R1<A>(A value) {");
        CompiledClasses.of(anInterface);
        CompiledClasses.of(anAnnotationType);
    }

    @Test
    void aGenericNestedEnumAndAnnotationTypeAreRefusedInAnInterfaceAndAnAnnotationType() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareNestedEnum("Level", typeParam("T"), noEnum());
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("an enum has no type parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareNestedAnnotationType("Tag", typeParam("T"), noAnnotationType());
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("an annotation type has no type parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.declareNestedEnum("Level", typeParam("T"), noEnum());
                                return ab;
                            }
                        }))
                .withMessageContaining("an enum has no type parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.declareNestedAnnotationType("Tag", typeParam("T"), noAnnotationType());
                                return ab;
                            }
                        }))
                .withMessageContaining("an annotation type has no type parameters");
    }

    @Test
    void anInnerInstanceIsCreatedByEveryConstructorArity() throws Throwable {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareInnerClass("Inner", new TypedJavaFile.TypedInnerClassSpec<Self>() {
                            @Override
                            public <Inner> MemberBodies<Inner, ?> build(
                                    ClassHeader<Inner> h, InnerClass<Self, Inner> handle) {
                                var members = h.extendsObject().members();
                                var c0 = members.declareConstructor();
                                var c1 = members.declareConstructor(PrimitiveToken.INT);
                                var c2 = members.declareConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                                var c3 = members.declareConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                                var c4 = members.declareConstructor(
                                        PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                                var m0 = members.declareMethod("make0", handle.token());
                                var m1 = members.declareMethod("make1", handle.token());
                                var m2 = members.declareMethod("make2", handle.token());
                                var m3 = members.declareMethod("make3", handle.token());
                                var m4 = members.declareMethod("make4", handle.token());
                                var bodies = members.bodies();
                                bodies.define(c0, (b, self) -> b.end());
                                bodies.define(c1, (b, self, a1) -> b.end());
                                bodies.define(c2, (b, self, a1, a2) -> b.end());
                                bodies.define(c3, (b, self, a1, a2, a3) -> b.end());
                                bodies.define(c4, (b, self, a1, a2, a3, a4) -> b.end());
                                bodies.define(m0, (b, self) -> b.return_(handle.new_(handle.outerThis(), c0)));
                                bodies.define(
                                        m1, (b, self) -> b.return_(handle.new_(handle.outerThis(), c1, literal(1))));
                                bodies.define(
                                        m2,
                                        (b, self) ->
                                                b.return_(handle.new_(handle.outerThis(), c2, literal(1), literal(2))));
                                bodies.define(
                                        m3,
                                        (b, self) -> b.return_(handle.new_(
                                                handle.outerThis(), c3, literal(1), literal(2), literal(3))));
                                bodies.define(
                                        m4,
                                        (b, self) -> b.return_(handle.new_(
                                                handle.outerThis(),
                                                c4,
                                                literal(1),
                                                literal(2),
                                                literal(3),
                                                literal(4))));
                                return bodies;
                            }
                        });
                        return cb.bodies();
                    }
                });

        assertThat(file.render())
                .contains("return Nested.this.new Inner();")
                .contains("return Nested.this.new Inner(1);")
                .contains("return Nested.this.new Inner(1, 2, 3, 4);");
        CompiledClasses compiled = CompiledClasses.of(file);
        Object outer = compiled.generated().getConstructor().newInstance();
        Class<?> innerType = compiled.generated().getDeclaredClasses()[0];
        Object inner = innerType.getDeclaredConstructor(compiled.generated()).newInstance(outer);
        assertThat(inner.getClass().getSimpleName()).isEqualTo("Inner");
        assertThat(innerType.getMethod("make0").invoke(inner)).isNotNull();
    }

    @Test
    void anInnerHandleRefusesAFactThatIsNoConstructorOfTheInnerClass() {
        Invocable[] other = new Invocable[1];
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                cb.declareInnerClass("Other", new TypedJavaFile.TypedInnerClassSpec<Self>() {
                    @Override
                    public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> h, InnerClass<Self, Inner> handle) {
                        var members = h.extendsObject().members();
                        other[0] = members.declareConstructor();
                        var bodies = members.bodies();
                        bodies.define((me.supcheg.javafile.facts.CtorRef0<Inner>) other[0], (b, self) -> b.end());
                        return bodies;
                    }
                });
                cb.declareInnerClass("Inner", new TypedJavaFile.TypedInnerClassSpec<Self>() {
                    @Override
                    public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> h, InnerClass<Self, Inner> handle) {
                        var members = h.extendsObject().members();
                        var ctor = members.declareConstructor();
                        var read = members.declareMethod("read", String_.TOKEN);
                        var bodies = members.bodies();
                        bodies.define(ctor, (b, self) -> b.end());
                        bodies.define(read, (b, self) -> b.return_(literal("inner")));
                        // neither the constructor of another class nor a method is one
                        assertThatIllegalArgumentException()
                                .isThrownBy(() -> handle.new_(handle.outerThis(), other[0]))
                                .withMessageContaining("is not one of");
                        assertThatIllegalArgumentException()
                                .isThrownBy(() -> handle.new_(handle.outerThis(), read))
                                .withMessageContaining("is no constructor");
                        return bodies;
                    }
                });
                return cb.bodies();
            }
        });
    }

    @Test
    void anEnumConstantBodyDeclaresGenericMemberInterfacesAndRecords() {
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", body -> {
                    body.declareNestedInterface("I1", typeParam("A"), genericInterface1());
                    body.declareNestedInterface("I2", typeParam("A"), typeParam("B"), genericInterface2());
                    body.declareNestedInterface(
                            "I3", typeParam("A"), typeParam("B"), typeParam("C"), genericInterface3());
                    body.declareNestedRecord("R1", typeParam("A"), genericRecord1());
                    body.declareNestedRecord("R2", typeParam("A"), typeParam("B"), genericRecord2());
                    body.declareNestedRecord("R3", typeParam("A"), typeParam("B"), typeParam("C"), genericRecord3());
                });
                return eb.bodies();
            }
        });

        assertThat(file.render())
                .contains("public interface I1<A> {")
                .contains("public interface I2<A, B> {")
                .contains("public interface I3<A, B, C> {")
                .contains("public record R1<A>(A value) {")
                .contains("public record R2<A, B>(A first, B second) {")
                .contains("public record R3<A, B, C>(A first, B second, C third) {");
        CompiledClasses.of(file);
    }

    private static TypedJavaFile.TypedGenericInterfaceSpec1<Object> genericInterface1() {
        return new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
            @Override
            public <Inner, A> TypedInterfaceBodies<Inner> build(TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a) {
                return inner.bodies();
            }
        };
    }

    private static TypedJavaFile.TypedGenericInterfaceSpec2<Object, Object> genericInterface2() {
        return new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
            @Override
            public <Inner, A, B> TypedInterfaceBodies<Inner> build(
                    TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a, TypeVarToken<B> b) {
                return inner.bodies();
            }
        };
    }

    private static TypedJavaFile.TypedGenericInterfaceSpec3<Object, Object, Object> genericInterface3() {
        return new TypedJavaFile.TypedGenericInterfaceSpec3<>() {
            @Override
            public <Inner, A, B, C> TypedInterfaceBodies<Inner> build(
                    TypedInterfaceBuilder<Inner> inner, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                return inner.bodies();
            }
        };
    }

    private static TypedJavaFile.TypedGenericRecordSpec1<Object> genericRecord1() {
        return new TypedJavaFile.TypedGenericRecordSpec1<>() {
            @Override
            public <Inner, A> TypedRecordBodies<Inner> build(TypedRecordBuilder<Inner> rb, TypeVarToken<A> a) {
                rb.component("value", a);
                return rb.bodies();
            }
        };
    }

    private static TypedJavaFile.TypedGenericRecordSpec2<Object, Object> genericRecord2() {
        return new TypedJavaFile.TypedGenericRecordSpec2<>() {
            @Override
            public <Inner, A, B> TypedRecordBodies<Inner> build(
                    TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b) {
                rb.component("first", a);
                rb.component("second", b);
                return rb.bodies();
            }
        };
    }

    private static TypedJavaFile.TypedGenericRecordSpec3<Object, Object, Object> genericRecord3() {
        return new TypedJavaFile.TypedGenericRecordSpec3<>() {
            @Override
            public <Inner, A, B, C> TypedRecordBodies<Inner> build(
                    TypedRecordBuilder<Inner> rb, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                rb.component("first", a);
                rb.component("second", b);
                rb.component("third", c);
                return rb.bodies();
            }
        };
    }
}
