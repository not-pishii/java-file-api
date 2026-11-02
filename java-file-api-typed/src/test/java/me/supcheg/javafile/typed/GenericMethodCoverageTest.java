package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The rest of a generic member of a class (§8g): its type parameter written in its own body, one type
/// parameter bound by the type of another, and a member that is documented, annotated and
/// `synchronized` — each renders, compiles under `-Xlint:all` and runs.
class GenericMethodCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aTypeParameterOfAMethodIsWrittenInTheBodyOfItsMember() throws Throwable {
        TypeVarToken<Object>[] captured = captured();
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var held = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                            @Override
                            public MethodRef1<Self, Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                captured[0] = t;
                                return m.declareMethod("held", t, t);
                            }
                        });
                var bodies = cb.bodies();
                // a local of the type parameter, which only the body of its member may write
                bodies.define(held, (b, self, value) -> b.letVar(captured[0], value, local -> b.return_(local)));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("T v1 = v0;").contains("return v1;");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("held", Object.class).invoke(probe, "held"))
                .isEqualTo("held");
    }

    /// A place for a test to keep the token of a type parameter the specification is given.
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static TypeVarToken<Object>[] captured() {
        return (TypeVarToken<Object>[]) new TypeVarToken[1];
    }

    @Test
    void oneTypeParameterIsBoundByTheTypeOfAnother() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
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
                                // `<U extends Comparable<T>>`: the bound names another type parameter of the
                                // member, which one bound says of the other
                                m.bound(u, new Comparable_<>(t).token);
                                return m.declareMethod("pick", t, t);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(pick, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("public <T, U extends Object & Comparable<T>> T pick(T v0) {");
    }

    @Test
    void aMethodTypeParameterIsDeclaredBesideThoseOfItsClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T"),
                new GenericDeclarationArities.TypedGenericClassSpec1<Object>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var held = cb.declareGenericMethod(
                                typeParam("U"),
                                new GenericMethodArities.MethodSpec1<Self, Object, VoidMethodRef1<Self, T>, Object>() {
                                    @Override
                                    public VoidMethodRef1<Self, T> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> u) {
                                        // the signature of the member names the type parameter of its class
                                        return m.declareVoidMethod("held", t);
                                    }
                                });
                        var bodies = cb.bodies();
                        bodies.define(held, (b, self, value) -> b.end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public <U> void held(T v0) {");
    }

    @Test
    void aMethodWithATypeParameterOfItsOwnIsDocumentedAnnotatedAndSynchronized() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var held = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                            @Override
                            public MethodRef1<Self, Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.documented("Holds the value.").declareMethod("held", t, t);
                            }
                        });
                var spin = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, VoidMethodRef0<Self>, Object>() {
                            @Override
                            public VoidMethodRef0<Self> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.annotated(Tag_.annotation()
                                                .value(AnnotationValues.string("generic"))
                                                .build())
                                        .declareVoidMethod("spin");
                            }
                        });
                var synchronizedMethod = cb.declareGenericMethod(
                        typeParam("T"),
                        new GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                            @Override
                            public MethodRef1<Self, Object, Object> build(
                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                                return m.modified(Modifier.SYNCHRONIZED).declareMethod("id", t, t);
                            }
                        });
                var bodies = cb.bodies();
                bodies.define(held, (b, self, value) -> b.return_(value));
                bodies.define(spin, (b, self) -> b.end());
                bodies.define(synchronizedMethod, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("Holds the value.")
                .contains("@Tag(\"generic\")")
                .contains("public synchronized <T> T id(T v0) {");
    }
}
