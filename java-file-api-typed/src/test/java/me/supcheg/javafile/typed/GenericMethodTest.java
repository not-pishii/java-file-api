package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Number_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// A generic method of a class (§8g): `<T> T id(T)`, a bounded one, one with further bounds, a `static`
/// one, a constructor with type parameters of its own, and a call that passes its type arguments
/// explicitly — each renders, compiles under `-Xlint:all` and runs. The refusals are in
/// [GenericMethodRefusalsTest].
class GenericMethodTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aMethodWithATypeParameterOfItsOwnRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var id = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                            @Override
                            public MethodRef1<Self, Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.declareMethod("id", t, t);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(id, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe {")
                .contains("public <T> T id(T v0) {")
                .contains("return v0;");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("id", Object.class).invoke(probe, "held"))
                .isEqualTo("held");
    }

    @Test
    void aBoundedTypeParameterIsOfItsLeftmostBoundInTheBody() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var id = cb.declareGenericMethod(
                        typeParam("N", Number_.TOKEN),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Number, Number>, Number>() {
                            @Override
                            public MethodRef1<Self, Number, Number> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Number> n) {
                                // a value of N is one of Number to the compiler of the generator
                                return m.declareMethod("id", n, n);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(id, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public <N extends Number> N id(N v0) {")
                .contains("return v0;");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("id", Number.class).invoke(probe, 41))
                .isEqualTo(41);
    }

    @Test
    void aTypeParameterHasTheFurtherBoundsTheDeclarationSays() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var pick = cb.declareGenericMethod(
                        typeParam("C", CharSequence_.TOKEN),
                        new GenericMethodArities.MethodSpec1<
                                Self, Object, MethodRef1<Self, CharSequence, CharSequence>, CharSequence>() {
                            @Override
                            public MethodRef1<Self, CharSequence, CharSequence> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<CharSequence> c) {
                                m.bound(c, new Comparable_<>(c).token);
                                return m.declareMethod("pick", c, c);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(pick, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public <C extends CharSequence & Comparable<C>> C pick(C v0) {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("pick", CharSequence.class).invoke(probe, "held"))
                .isEqualTo("held");
    }

    @Test
    void aStaticMethodWithATypeParameterOfItsOwnRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var of = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, StaticMethodRef1<Object, Object>, Object>() {
                            @Override
                            public StaticMethodRef1<Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.declareStaticMethod("of", t, t);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(of, (b, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public static <T> T of(T v0) {");
        assertThat(compiled.generated().getMethod("of", Object.class).invoke(null, "held"))
                .isEqualTo("held");
    }

    @Test
    void aConstructorWithATypeParameterOfItsOwnRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var value = cb.declareMutableField("value", Object_.TOKEN);
                var ctor = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, CtorRef1<Self, Object>, Object>() {
                            @Override
                            public CtorRef1<Self, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.declareConstructor(t);
                            }
                        });
                var bodies = cb.bodies();
                bodies.initialize(value, literalNull(Object_.TOKEN));
                bodies.define(
                        ctor,
                        (b, self, v) -> b.exec(assignField(self, value, v)).end());
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public <T> Probe(T v0) {");
        assertThat(compiled.generated().getConstructor(Object.class).newInstance("held"))
                .isNotNull();
    }

    @Test
    void aGenericMethodDeclaresACheckedExceptionAndRendersItsTypeParameter() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var read = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef0<Self, String>, Object>() {
                            @Override
                            public MethodRef0<Self, String> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.throwing(IOException_.TOKEN).declareMethod("read", String_.TOKEN);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(read, (b, self) -> b.return_(literal("read")));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public <T> String read() throws IOException {");
    }

    @Test
    void aCallOfAGenericMethodPassesItsTypeArgumentsExplicitly() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var id = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                            @Override
                            public MethodRef1<Self, Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.declareMethod("id", t, t);
                            }
                        });
                var call = cb.declareMethod("call", Object_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(id, (b, self, value) -> b.return_(value));
                // the fact of the call is the one of the method, with the witness the call passes
                bodies.define(call, (b, self, value) -> b.return_(self.call(id.withTypeArgs(String_.TOKEN), value)));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public <T> T id(T v0) {").contains("return this.<String>id(v0);");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("call", String.class).invoke(probe, "held"))
                .isEqualTo("held");
    }
}
