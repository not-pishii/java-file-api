package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Named;
import me.supcheg.javafile.typed.fixtures.Numbered;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Number_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Titled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Worded_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A generic interface and a generic record (§8g): their type parameters are tokens the specification
/// is given, in scope in the header, the components, the members and the member types. The declarations
/// are rendered, compiled under `-Xlint:all` and run; a generic enum and a generic annotation type are
/// refused at the entry point, as javac refuses them.
class GenericDeclarationsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Box");

    @Test
    void aGenericInterfaceDeclaresMethodsOfItsTypeParameterAndIsImplemented() throws Throwable {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                    @Override
                    public <Self, T extends Number> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                        ib.extends_(new Comparable_<>(t).token);
                        var id = ib.declareAbstractMethod("id", t, t);
                        var zero = ib.declareDefaultMethod("zero", t, t);
                        var bodies = ib.bodies();
                        bodies.define(zero, (b, self, value) -> b.return_(value));
                        return bodies;
                    }
                });

        CompiledClasses compiled = CompiledClasses.of(
                file,
                "me.supcheg.example.Impl",
                "package me.supcheg.example; public final class Impl implements Box<Integer> {"
                        + " public Integer id(Integer v0) { return v0; }"
                        + " public int compareTo(Integer v0) { return 0; } }");

        assertThat(compiled.source())
                .contains("public interface Box<T extends Number> extends Comparable<T> {")
                .contains("T id(T v0);")
                .contains("default T zero(T v0) {");
        Object impl = compiled.instantiate("me.supcheg.example.Impl");
        assertThat(impl.getClass().getMethod("id", Integer.class).invoke(impl, 3))
                .isEqualTo(3);
        assertThat(impl.getClass().getMethod("zero", Number.class).invoke(impl, 4))
                .isEqualTo(4);
    }

    @Test
    void aGenericRecordDeclaresComponentsAndMethodsOfItsTypeParameter() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), new TypedJavaFile.TypedGenericRecordSpec1<>() {
                    @Override
                    public <Self, T> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T> t) {
                        rb.implements_(new Comparable_<>(t).token);
                        var first = rb.component("first", t);
                        var second = rb.component("second", t);
                        var same = rb.declareMethod("same", PrimitiveToken.BOOLEAN);
                        var compareTo = rb.declareMethod("compareTo", PrimitiveToken.INT, t);
                        var bodies = rb.bodies();
                        bodies.define(
                                same,
                                (b, self) -> b.return_(call(
                                        call(self, first.accessor()),
                                        Object_.equals_Object,
                                        call(self, second.accessor()))));
                        bodies.define(compareTo, (b, self, other) -> b.return_(literal(0)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public record Box<T>(T first, T second) implements Comparable<T> {")
                .contains("public boolean same() {");
        Object box =
                compiled.generated().getConstructor(Object.class, Object.class).newInstance("a", "a");
        assertThat(compiled.generated().getMethod("same").invoke(box)).isEqualTo(true);
        assertThat(compiled.generated().getMethod("first").invoke(box)).isEqualTo("a");
    }

    @Test
    void aNestedGenericClassIsDeclaredThroughItsEnclosingClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareNestedClass("Inner", typeParam("T"), new TypedJavaFile.TypedGenericClassSpec1<>() {
                            @Override
                            public <Self2, T> MemberBodies<Self2, ?> build(ClassHeader<Self2> h, TypeVarToken<T> t) {
                                var inner = h.extendsObject().members();
                                var value = inner.declareMethod("value", t, t);
                                var ctor = inner.declareConstructor(t);
                                var bodies = inner.bodies();
                                bodies.define(ctor, (b, self, v) -> b.end());
                                bodies.define(value, (b, self, v) -> b.return_(v));
                                return bodies;
                            }
                        });
                        return cb.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("public static final class Inner<T> {")
                .contains("public Inner(T v0) {")
                .contains("public T value(T v0) {");
        Class<?> inner = compiled.generated().getClassLoader().loadClass("me.supcheg.example.Box$Inner");
        Object value = inner.getConstructor(Object.class).newInstance("held");
        assertThat(inner.getMethod("value", Object.class).invoke(value, "returned"))
                .isEqualTo("returned");
    }

    @Test
    void aGenericInterfaceAndRecordHaveUpToThreeTypeParameters() {
        JavaFile pair = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B", Number_.TOKEN),
                new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
                    @Override
                    public <Self, A, B extends Number> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib, TypeVarToken<A> a, TypeVarToken<B> b) {
                        ib.declareAbstractMethod("first", a, b);
                        return ib.bodies();
                    }
                });
        JavaFile triple = TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B"),
                typeParam("C", Number_.TOKEN),
                new TypedJavaFile.TypedGenericRecordSpec3<>() {
                    @Override
                    public <Self, A, B, C extends Number> TypedRecordBodies<Self> build(
                            TypedRecordBuilder<Self> rb, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                        rb.component("a", a);
                        rb.component("b", b);
                        rb.component("c", c);
                        return rb.bodies();
                    }
                });

        JavaFile threeInterfaces = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B"),
                typeParam("C", new Comparable_<>(String_.TOKEN).token),
                new TypedJavaFile.TypedGenericInterfaceSpec3<>() {
                    @Override
                    public <Self, A, B, C extends Comparable<String>> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                        ib.declareAbstractMethod("first", a, b, c);
                        return ib.bodies();
                    }
                });
        JavaFile twoRecords = TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B"),
                new TypedJavaFile.TypedGenericRecordSpec2<>() {
                    @Override
                    public <Self, A, B> TypedRecordBodies<Self> build(
                            TypedRecordBuilder<Self> rb, TypeVarToken<A> a, TypeVarToken<B> b) {
                        rb.component("a", a);
                        rb.component("b", b);
                        return rb.bodies();
                    }
                });

        assertThat(pair.render())
                .contains("public interface Box<A, B extends Number> {")
                .contains("A first(B v0);");
        assertThat(triple.render()).contains("public record Box<A, B, C extends Number>(A a, B b, C c) {");
        assertThat(threeInterfaces.render()).contains("public interface Box<A, B, C extends Comparable<String>> {");
        assertThat(twoRecords.render()).contains("public record Box<A, B>(A a, B b) {");
    }

    @Test
    void aGenericInterfaceRefusesTypeParametersThatShareANameOrNameTheType() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        typeParam("T"),
                        new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
                            @Override
                            public <Self, T1, T2> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("has two type parameters T");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("Box"),
                        new TypedJavaFile.TypedGenericRecordSpec1<>() {
                            @Override
                            public <Self, T> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb, TypeVarToken<T> t) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot have the type parameter Box");
    }

    @Test
    void aGenericInterfaceRefusesARawBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", UnsafeFacts.<Comparable<?>>interfaceToken(Comparable_.Data.SHAPE)),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                            @Override
                            public <Self, T extends Comparable<?>> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot have the raw bound java.lang.Comparable");
    }

    @Test
    void aGenericEnumIsRefusedAtTheEntryPoint() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.enum_(
                        UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot declare the enum me.supcheg.example.Box with a type parameter")
                .withMessageContaining("an enum has no type parameters (JLS 8.9)");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.enum_(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of("Box"),
                        typeParam("T"),
                        new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot declare the enum Box with a type parameter");
    }

    @Test
    void aGenericAnnotationTypeIsRefusedAtTheEntryPoint() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining(
                        "cannot declare the annotation type me.supcheg.example.Box with a type parameter")
                .withMessageContaining("an annotation type has no type parameters (JLS 9.6)");
    }

    @Test
    void anInterfaceAndARecordDeclareFurtherBoundsOnTheirTypeParameters() {
        JavaFile anInterface = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                    @Override
                    public <Self, T extends Number> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                        var comparable = new Comparable_<>(t);
                        ib.bound(t, comparable.token);
                        ib.declareAbstractMethod("compare", PrimitiveToken.INT, t);
                        return ib.bodies();
                    }
                });
        JavaFile aRecord = TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericRecordSpec1<>() {
                    @Override
                    public <Self, T extends Number> TypedRecordBodies<Self> build(
                            TypedRecordBuilder<Self> rb, TypeVarToken<T> t) {
                        rb.bound(t, new Comparable_<>(t).token);
                        rb.component("value", t);
                        return rb.bodies();
                    }
                });

        assertThat(anInterface.render()).isEqualTo("""
                        package me.supcheg.example;

                        public interface Box<T extends Number & Comparable<T>> {
                            int compare(T v0);
                        }
                        """);
        assertThat(aRecord.render()).isEqualTo("""
                        package me.supcheg.example;

                        public record Box<T extends Number & Comparable<T>>(T value) {
                        }
                        """);

        CompiledClasses.of(anInterface);
        CompiledClasses.of(aRecord);
    }

    @Test
    void anInterfaceAndARecordRefuseFurtherBoundsTheyCannotHave() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Number_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                            @Override
                            public <Self, T extends Number> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.declareAbstractMethod("compare", PrimitiveToken.INT, t);
                                ib.bound(t, new Comparable_<>(t).token);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("the members of interface me.supcheg.example.Box have begun");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Number_.TOKEN),
                        new TypedJavaFile.TypedGenericRecordSpec1<>() {
                            @Override
                            public <Self, T extends Number> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb, TypeVarToken<T> t) {
                                rb.bound(t, Comparable_.ANY);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("cannot have the supertype java.lang.Comparable");

        TypeVarToken<?>[] kept = new TypeVarToken<?>[1];
        TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Other"),
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T extends Number> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        kept[0] = t;
                        return h.extendsObject().members().bodies();
                    }
                });
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Number_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<>() {
                            @Override
                            public <Self, T extends Number> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.bound((TypeVarToken) kept[0], new Comparable_<>(t).token);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("is no type parameter of interface me.supcheg.example.Box");
    }

    @Test
    void anInterfaceRefusesFurtherBoundsWhoseBoundsCannotBeTogether() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", String_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<String>() {
                            @Override
                            public <Self, T extends String> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.bound(t, Runnable_.TOKEN);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("cannot have the bound java.lang.Runnable beside java.lang.String");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Numbered_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<Numbered>() {
                            @Override
                            public <Self, T extends Numbered> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.bound(t, Worded_.TOKEN);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("of one signature and of different results");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Named_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<Named>() {
                            @Override
                            public <Self, T extends Named> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.bound(t, Titled_.TOKEN);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("cannot have the bound");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Origin_.TOKEN),
                        new TypedJavaFile.TypedGenericInterfaceSpec1<me.supcheg.javafile.typed.fixtures.Origin>() {
                            @Override
                            public <Self, T extends me.supcheg.javafile.typed.fixtures.Origin>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib, TypeVarToken<T> t) {
                                ib.bound(t, Worded_.TOKEN);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("of one signature and of different results");
    }

    @Test
    void aBoundOfOneTypeParameterLeavesTheOtherAsItIs() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B", Number_.TOKEN),
                new TypedJavaFile.TypedGenericInterfaceSpec2<>() {
                    @Override
                    public <Self, A, B extends Number> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib, TypeVarToken<A> a, TypeVarToken<B> b) {
                        ib.bound(a, Runnable_.TOKEN);
                        ib.bound(b, new Comparable_<>(b).token);
                        return ib.bodies();
                    }
                });

        assertThat(file.render())
                .contains("public interface Box<A extends Object & Runnable, B extends Number & Comparable<B>> {");
        CompiledClasses.of(file);
    }
}
