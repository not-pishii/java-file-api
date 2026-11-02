package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A generic method of each declaration kind (§8g): an interface — abstract, `default`, `private` and
/// `static` —, a record and an enum, and an abstract generic method of an abstract class, each
/// rendered, compiled under `-Xlint:all` and run. A type parameter bounded by an earlier one of its
/// member, `<T, U extends T>`, is here too, with what it refuses.
class TypeGenericMethodTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile interfaceWithGenericMethods() {
        return TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var id = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareAbstractMethod("id", t, t);
                                    }
                                });
                        var once = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareDefaultMethod("once", t, t);
                                    }
                                });
                        var twice = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declarePrivateMethod("twice", t, t);
                                    }
                                });
                        var of = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, StaticMethodRef1<Object, Object>, Object>() {
                                    @Override
                                    public StaticMethodRef1<Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareStaticMethod("of", t, t);
                                    }
                                });
                        var bodies = ib.bodies();
                        bodies.define(once, (b, self, v) -> b.return_(self.call(twice, v)));
                        bodies.define(twice, (b, self, v) -> b.return_(v));
                        bodies.define(of, (b, v) -> b.return_(v));
                        // `id` is abstract, and is not defined here
                        assertThat(id).isNotNull();
                        return bodies;
                    }
                });
    }

    @Test
    void aGenericMethodOfAnInterfaceRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(interfaceWithGenericMethods(), "me.supcheg.example.Impl", """
                package me.supcheg.example;

                public final class Impl implements Probe {
                    @Override
                    public <K> K id(K v) {
                        return v;
                    }
                }
                """);

        assertThat(compiled.source())
                .contains("<T> T id(T v0);")
                .contains("default <T> T once(T v0) {")
                .contains("private <T> T twice(T v0) {")
                .contains("static <T> T of(T v0) {")
                .contains("return this.twice(v0);");
        Object impl = compiled.instantiate("me.supcheg.example.Impl");
        assertThat(impl.getClass().getMethod("once", Object.class).invoke(impl, "held"))
                .isEqualTo("held");
        assertThat(compiled.generated().getMethod("of", Object.class).invoke(null, "held"))
                .isEqualTo("held");
    }

    @Test
    void aGenericMethodOfARecordRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var value = rb.component("value", String_.TOKEN);
                        var id = rb.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareMethod("id", t, t);
                                    }
                                });
                        var bodies = rb.bodies();
                        bodies.define(id, (b, self, v) -> b.return_(v));
                        assertThat(value).isNotNull();
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public record Probe(String value) {")
                .contains("public <T> T id(T v0) {")
                .contains("return v0;");
        Object probe = compiled.generated().getConstructor(String.class).newInstance("held");
        assertThat(compiled.generated().getMethod("id", Object.class).invoke(probe, "held"))
                .isEqualTo("held");
    }

    @Test
    void aGenericMethodOfAnEnumRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("ONLY");
                        var id = eb.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareMethod("id", t, t);
                                    }
                                });
                        var of = eb.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, StaticMethodRef1<Object, Object>, Object>() {
                                    @Override
                                    public StaticMethodRef1<Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareStaticMethod("of", t, t);
                                    }
                                });
                        var bodies = eb.bodies();
                        bodies.define(id, (b, self, v) -> b.return_(v));
                        bodies.define(of, (b, v) -> b.return_(v));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public enum Probe {")
                .contains("public <T> T id(T v0) {")
                .contains("public static <T> T of(T v0) {");
        Object only = compiled.generated().getField("ONLY").get(null);
        assertThat(compiled.generated().getMethod("id", Object.class).invoke(only, "held"))
                .isEqualTo("held");
        assertThat(compiled.generated().getMethod("of", Object.class).invoke(null, "held"))
                .isEqualTo("held");
    }

    @Test
    void anAbstractClassDeclaresAnAbstractGenericMethodThatASubclassImplements() {
        CompiledClasses compiled = CompiledClasses.of(
                TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.abstract_();
                                var cb = h.extendsObject().members();
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                                return m.declareAbstractMethod("id", t, t);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }),
                "me.supcheg.example.Impl",
                """
                package me.supcheg.example;

                public final class Impl extends Probe {
                    // the type parameters of the method it implements, under names of its own
                    @Override
                    public <K> K id(K v) {
                        return v;
                    }
                }
                """);

        assertThat(compiled.source())
                .contains("public abstract class Probe {")
                .contains("public abstract <T> T id(T v0);");
    }

    @Test
    void aTypeParameterIsBoundedByAnEarlierOneOfItsMember() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var pick = cb.declareGenericMethod(
                                typeParam("T"),
                                typeParam("U"),
                                new GenericMethodArities.MethodSpec2<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t,
                                            TypeVarToken<Object> u) {
                                        m.bound(u, t);
                                        return m.declareMethod("pick", u, u);
                                    }
                                });
                        var held = cb.declareMutableField("held", Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.initialize(held, literalNull(Object_.TOKEN));
                        bodies.define(pick, (b, self, v) -> b.return_(v));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public <T, U extends T> U pick(U v0) {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("pick", Object.class).invoke(probe, "held"))
                .isEqualTo("held");
    }

    @Test
    void aTypeParameterIsNotBoundedByOneDeclaredAfterIt() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.MethodSpec2<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                // `T` is declared before `U`, and a bound names one that is
                                                m.bound(t, u);
                                                return m.declareMethod("pick", t, t);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("declared before the one it bounds");
    }

    @Test
    void aBoundedTypeParameterGetsNoTypeVariableBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U", String_.TOKEN),
                                        new GenericMethodArities.MethodSpec2<
                                                Self, Object, MethodRef1<Self, String, String>, Object, String>() {
                                            @Override
                                            public MethodRef1<Self, String, String> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<String> u) {
                                                m.bound(u, t);
                                                return m.declareMethod("pick", u, u);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("has the leftmost bound java.lang.String already");
    }

    @Test
    void aTypeParameterOfTheTypeIsNotNamedByAGenericMethodOfIt() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec1<Object>() {
                            @Override
                            public <Self, T> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> u) {
                                                return m.declareAbstractMethod("id", u, u);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("the type declares a type parameter of that name");
    }

    @Test
    void aGenericMemberOfAnInterfaceIsDefinedAsAnyOther() {
        // a body uses the token of the type parameter as its type, and returns it
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var id = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareDefaultMethod("id", t, t);
                                    }
                                });
                        var bodies = ib.bodies();
                        bodies.define(id, (b, self, v) -> b.return_(v));
                        return bodies;
                    }
                });
        assertThat(file.render()).contains("default <T> T id(T v0) {").contains("return v0;");
    }

    /// A generic method of an interface, of a record and of an enum declares no method of a class
    /// that a body would define: the arity family of its kind alone is there.
    @Test
    void aGenericVoidMethodRendersItsTypeParameters() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var put = ib.declareGenericMethod(
                                typeParam("T"),
                                new GenericMethodArities.TypeMethodSpec1<
                                        Self, me.supcheg.javafile.facts.VoidMethodRef1<Self, Object>, Object>() {
                                    @Override
                                    public me.supcheg.javafile.facts.VoidMethodRef1<Self, Object> build(
                                            TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                        return m.declareVoidDefaultMethod("put", t);
                                    }
                                });
                        var bodies = ib.bodies();
                        bodies.define(put, (b, self, v) -> b.end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("default <T> void put(T v0) {");
    }

    @Test
    void aTypeParameterOfAGenericMethodIsNotNamedTwiceInAnInterface() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("T"),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                return m.declareAbstractMethod("id", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("declares the type parameter T twice");
    }

    @Test
    void aTypeParameterOfAnInterfaceMethodIsBoundedByAnEarlierOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(
                TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var pick = ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(u, t);
                                                return m.declareDefaultMethod("pick", u, u);
                                            }
                                        });
                                var bodies = ib.bodies();
                                bodies.define(pick, (b, self, v) -> b.return_(v));
                                return bodies;
                            }
                        }),
                "me.supcheg.example.Impl",
                """
                package me.supcheg.example;

                public final class Impl implements Probe {
                }
                """);

        assertThat(compiled.source()).contains("default <T, U extends T> U pick(U v0) {");
        Object impl = compiled.instantiate("me.supcheg.example.Impl");
        assertThat(impl.getClass().getMethod("pick", Object.class).invoke(impl, "held"))
                .isEqualTo("held");
    }

    @Test
    void aBoundOfAnInterfaceMethodIsSaidOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                                m.bound(t, new Comparable_<>(t).token);
                                                m.bound(t, new Comparable_<>(t).token);
                                                return m.declareAbstractMethod("id", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("already: a bound is said once");
    }

    @Test
    void aTokenOfNoTypeParameterOfAnInterfaceMemberIsNoBound() {
        var other = UnsafeFacts.typeVarToken(
                me.supcheg.javafile.type.Types.typeVar("X"), java.lang.constant.ConstantDescs.CD_Object);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                                m.bound(other, t);
                                                return m.declareAbstractMethod("id", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("is no type parameter of the member being declared");
    }

    @Test
    void aBoundOfNoTypeParameterOfAnInterfaceMemberIsNoBound() {
        var other = UnsafeFacts.typeVarToken(
                me.supcheg.javafile.type.Types.typeVar("X"), java.lang.constant.ConstantDescs.CD_Object);
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
                                                m.bound(t, other);
                                                return m.declareAbstractMethod("id", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("is no type parameter of the member");
    }

    @Test
    void aTypeParameterBoundedByAnEarlierOneIsNotBoundedTwiceInAnInterface() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(u, t);
                                                m.bound(u, t);
                                                return m.declareAbstractMethod("pick", u, u);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("has the leftmost bound T already");
    }

    @Test
    void aBoundOfAGenericMethodOfAnInterfaceNamesNoTypeParameterOfTheInterface() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec1<Object>() {
                            @Override
                            public <Self, T> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.declareGenericMethod(
                                        typeParam("U", new Comparable_<>(t).token),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Comparable<T>, Comparable<T>>, Comparable<T>>() {
                                            @Override
                                            public MethodRef1<Self, Comparable<T>, Comparable<T>> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Comparable<T>> u) {
                                                return m.declareAbstractMethod("id", u, u);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("which is no type parameter of the member");
    }

    @Test
    void aFurtherBoundOfAMethodNamesAnEarlierTypeParameter() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var pick = cb.declareGenericMethod(
                                typeParam("T"),
                                typeParam("U"),
                                new GenericMethodArities.MethodSpec2<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t,
                                            TypeVarToken<Object> u) {
                                        m.bound(u, new Comparable_<>(t).token);
                                        return m.declareMethod("pick", u, u);
                                    }
                                });
                        var bodies = cb.bodies();
                        bodies.define(pick, (b, self, v) -> b.return_(v));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public <T, U extends Object & Comparable<T>> U pick(U v0) {");
    }

    @Test
    void aFurtherBoundOfAMethodNamesNoTypeParameterDeclaredAfterIt() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.MethodSpec2<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(t, new Comparable_<>(u).token);
                                                return m.declareMethod("pick", t, t);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("which is declared after it");
    }

    @Test
    void aFurtherBoundOfAnInterfaceMethodNamesAnEarlierTypeParameter() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var pick = ib.declareGenericMethod(
                                typeParam("T"),
                                typeParam("U"),
                                new GenericMethodArities.TypeMethodSpec2<
                                        Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            TypeGenericMethodDeclaration<Self> m,
                                            TypeVarToken<Object> t,
                                            TypeVarToken<Object> u) {
                                        m.bound(u, new Comparable_<>(t).token);
                                        return m.declareDefaultMethod("pick", u, u);
                                    }
                                });
                        var bodies = ib.bodies();
                        bodies.define(pick, (b, self, v) -> b.return_(v));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("default <T, U extends Object & Comparable<T>> U pick(U v0) {");
    }

    @Test
    void aFurtherBoundOfAnInterfaceMethodNamesNoTypeParameterDeclaredAfterIt() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(t, new Comparable_<>(u).token);
                                                return m.declareAbstractMethod("pick", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("which is declared after it");
    }

    @Test
    void aTypeParameterOfAnInterfaceMethodIsNotBoundedByOneDeclaredAfterIt() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(t, u);
                                                return m.declareAbstractMethod("pick", t, t);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("declared before the one it bounds");
    }

    @Test
    void aBoundedTypeParameterOfAnInterfaceMethodGetsNoTypeVariableBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U", String_.TOKEN),
                                        new GenericMethodArities.TypeMethodSpec2<
                                                Self, MethodRef1<Self, String, String>, Object, String>() {
                                            @Override
                                            public MethodRef1<Self, String, String> build(
                                                    TypeGenericMethodDeclaration<Self> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<String> u) {
                                                m.bound(u, t);
                                                return m.declareAbstractMethod("pick", u, u);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("has the leftmost bound java.lang.String already");
    }

    @Test
    void aBoundOfAPrimitiveIsNoBoundOfAnInterfaceMember() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(
                                        TypeParameter.bounded("P", PrimitiveToken.INT),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef0<Self, Object>, Prim.Int>() {
                                            @Override
                                            public MethodRef0<Self, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Prim.Int> p) {
                                                return m.declareAbstractMethod("id", Object_.TOKEN);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("is no class or interface");
    }

    @Test
    void aFurtherBoundOfAMethodNamesNoTypeParameterOfTheClass() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericClassSpec1<Object>() {
                            @Override
                            public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                                var cb = h.extendsObject().members();
                                cb.declareGenericMethod(
                                        typeParam("U"),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> u) {
                                                m.bound(u, new List_<>(t).token);
                                                return m.declareMethod("id", u, u);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("which is no type parameter of the member");
    }

    @Test
    void aFurtherBoundOfAnInterfaceMethodNamesNoTypeParameterOfTheInterface() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec1<Object>() {
                            @Override
                            public <Self, T> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.declareGenericMethod(
                                        typeParam("U"),
                                        new GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> u) {
                                                m.bound(u, new List_<>(t).token);
                                                return m.declareAbstractMethod("id", u, u);
                                            }
                                        });
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("which is no type parameter of the member");
    }

    @Test
    void aBoundOfATypeParameterOfAMemberIsSaidOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                                m.bound(t, new Comparable_<>(t).token);
                                                m.bound(t, new Comparable_<>(t).token);
                                                return m.declareMethod("pick", t, t);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("already: a bound is said once");
    }

    @Test
    void aTokenOfNoTypeParameterOfTheMemberIsNoBound() {
        var other = UnsafeFacts.typeVarToken(
                me.supcheg.javafile.type.Types.typeVar("X"), java.lang.constant.ConstantDescs.CD_Object);
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                                m.bound(t, other);
                                                return m.declareMethod("pick", t, t);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("is no type parameter of the member");
    }

    @Test
    void aTypeParameterBoundedByAnEarlierOneIsNotBoundedTwice() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        typeParam("U"),
                                        new GenericMethodArities.MethodSpec2<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<Object> t,
                                                    TypeVarToken<Object> u) {
                                                m.bound(u, t);
                                                m.bound(u, t);
                                                return m.declareMethod("pick", u, u);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("has the leftmost bound T already");
    }

    @Test
    void aBoundThatIsNoTypeIsNoBoundOfAMember() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareGenericMethod(
                                        TypeParameter.bounded("P", PrimitiveToken.INT),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef0<Self, Object>, Prim.Int>() {
                                            @Override
                                            public MethodRef0<Self, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<Prim.Int> p) {
                                                return m.declareMethod("id", Object_.TOKEN);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("is no class or interface");
    }

    @Test
    void aTypeParameterOfATypeIsNotBoundedByATypeVariable() {
        var other = UnsafeFacts.typeVarToken(
                me.supcheg.javafile.type.Types.typeVar("X"), java.lang.constant.ConstantDescs.CD_Object);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        TypeParameter.bounded("U", other),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec1<Object>() {
                            @Override
                            public <Self, U> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<U> u) {
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("is bounded by a class or an interface");
    }
}
