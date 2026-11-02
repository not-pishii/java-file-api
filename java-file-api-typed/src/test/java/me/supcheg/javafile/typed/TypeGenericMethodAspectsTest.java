package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The rest of a generic method of an interface, a record and an enum: documented, annotated, annotated
/// at a parameter, `synchronized` where the kind allows it, `throwing` a checked exception, and of
/// variable arity — each rendered, compiled under `-Xlint:all` and run, with what the stage refuses.
class TypeGenericMethodAspectsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static me.supcheg.javafile.facts.AnnotationRef<?> tag(String value) {
        return Tag_.annotation().value(AnnotationValues.string(value)).build();
    }

    @Test
    void aGenericDefaultMethodOfAnInterfaceIsDocumentedAnnotatedThrowingAndOfVariableArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(
                TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var read =
                                        ib.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                        Self, VoidMethodRef2<Self, Object, String[]>, Object>)
                                                (m, t) -> m.documented("Reads the value and its parts.")
                                                        .annotated(tag("generic"))
                                                        .annotatedParameter(0, tag("first"))
                                                        .annotatedParameter(1, tag("rest"))
                                                        .throwing(IOException_.TOKEN)
                                                        .variableArity()
                                                        .declareVoidDefaultMethod(
                                                                "read", t, ArrayToken.of(String_.TOKEN)));
                                var bodies = ib.bodies();
                                bodies.define(read, (b, self, value, parts) -> b.end());
                                return bodies;
                            }
                        }),
                "me.supcheg.example.Impl",
                """
                package me.supcheg.example;

                public final class Impl implements Probe {
                }
                """);

        assertThat(compiled.source())
                .contains("Reads the value and its parts.")
                .contains("@Tag(\"generic\")")
                .contains("@Tag(\"first\") T v0")
                .contains("@Tag(\"rest\")")
                .contains(
                        "default <T> void read(@Tag(\"first\") T v0, @Tag(\"rest\") String... v1) throws IOException {");
        Object impl = compiled.instantiate("me.supcheg.example.Impl");
        impl.getClass().getMethod("read", Object.class, String[].class).invoke(impl, "value", new String[] {"a", "b"});
    }

    @Test
    void aGenericMethodOfARecordIsDocumentedAnnotatedSynchronizedThrowingAndOfVariableArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var read = rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, String, Object[]>, Object>)
                                (m, t) -> m.documented("Reads the values.")
                                        .annotated(tag("generic"))
                                        .annotatedParameter(0, tag("first"))
                                        .throwing(IOException_.TOKEN)
                                        .modified(Modifier.SYNCHRONIZED)
                                        .variableArity()
                                        .declareMethod("read", String_.TOKEN, ArrayToken.of(t)));
                        var annotatedParameter =
                                rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, String, Object>, Object>)
                                        (m, t) -> m.annotatedParameter(0, tag("only"))
                                                .declareMethod("only", String_.TOKEN, t));
                        var documented = rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, String, Object>, Object>)
                                (m, t) ->
                                        m.documented("Only documented.").declareMethod("documented", String_.TOKEN, t));
                        var annotated = rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, String, Object>, Object>)
                                (m, t) -> m.annotated(tag("declared")).declareMethod("annotated", String_.TOKEN, t));
                        var bodies = rb.bodies();
                        bodies.define(read, (b, self, values) -> b.return_(literal("read")));
                        bodies.define(annotatedParameter, (b, self, value) -> b.return_(literal("only")));
                        bodies.define(documented, (b, self, value) -> b.return_(literal("documented")));
                        bodies.define(annotated, (b, self, value) -> b.return_(literal("annotated")));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("Reads the values.")
                .contains("Only documented.")
                .contains("@Tag(\"generic\")")
                .contains("@Tag(\"first\") T... v0")
                .contains("@Tag(\"only\") T v0")
                .contains("@Tag(\"declared\")")
                .contains("@SafeVarargs")
                .contains("public final synchronized <T> String read(@Tag(\"first\") T... v0) throws IOException {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("read", Object[].class).invoke(probe, (Object)
                        new Object[] {"a", "b"}))
                .isEqualTo("read");
    }

    @Test
    void aGenericMethodOfAnEnumIsDocumentedAnnotatedSynchronizedThrowingAndOfVariableArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("ONLY");
                        var pick = eb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef1<Self, String, Object[]>, Object>)
                                (m, t) -> m.documented("Picks one of the values.")
                                        .annotated(tag("generic"))
                                        .annotatedParameter(0, tag("first"))
                                        .throwing(IOException_.TOKEN)
                                        .modified(Modifier.SYNCHRONIZED)
                                        .variableArity()
                                        .declareMethod("pick", String_.TOKEN, ArrayToken.of(t)));
                        var bodies = eb.bodies();
                        bodies.define(pick, (b, self, values) -> b.return_(literal("picked")));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("Picks one of the values.")
                .contains("@Tag(\"generic\")")
                .contains("@Tag(\"first\") T... v0")
                .contains("@SafeVarargs")
                .contains("public final synchronized <T> String pick(@Tag(\"first\") T... v0) throws IOException {");
        Object only = compiled.generated().getField("ONLY").get(null);
        assertThat(compiled.generated().getMethod("pick", Object[].class).invoke(only, (Object) new Object[] {"a"}))
                .isEqualTo("picked");
    }

    @Test
    void aStaticGenericMethodVouchesForAnArrayOfNonReifiableElements() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var put = ib.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, VoidStaticMethodRef1<Object[]>, Object>)
                                (m, t) -> m.variableArity().declareVoidStaticMethod("put", ArrayToken.of(t)));
                        var bodies = ib.bodies();
                        bodies.define(put, (b, parts) -> b.end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("@SafeVarargs").contains("static <T> void put(T... v0) {");
    }

    @Test
    void anInterfaceGenericMethodIsRefusedSynchronizedAndNative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, VoidMethodRef2<Self, Object, String[]>, Object>)
                                        (m, t) -> m.modified(Modifier.SYNCHRONIZED)
                                                .variableArity()
                                                .declareVoidDefaultMethod("read", t, ArrayToken.of(String_.TOKEN)));
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("an interface has no synchronized or native method");
    }

    @Test
    void aRecordRefusesANativeGenericMethod() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, String, Object>, Object>)
                                        (m, t) -> m.modified(Modifier.NATIVE).declareMethod("read", String_.TOKEN, t));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("javac rejects a native method of a record");
    }

    @Test
    void anAbstractGenericMethodIsRefusedSynchronized() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>)
                                        (m, t) -> m.modified(Modifier.SYNCHRONIZED)
                                                .declareAbstractMethod("id", t, t));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("an abstract method is neither synchronized nor native");
    }

    @Test
    void aGenericDefaultMethodRefusesAnArrayOfNonReifiableElements() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, VoidMethodRef2<Self, Object, Object[]>, Object>)
                                        (m, t) ->
                                                m.variableArity().declareVoidDefaultMethod("put", t, ArrayToken.of(t)));
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("cannot be of variable arity with elements that are not reifiable");
    }

    @Test
    void aGenericMethodRefusesAModifierThatIsNotSynchronizedOrNative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>)
                                        (m, t) -> m.modified(Modifier.FINAL).declareMethod("id", t, t));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("cannot add the modifier FINAL");
    }

    @Test
    void aGenericMethodRefusesADocumentationThatEndsTheComment() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareGenericMethod(typeParam("T"), (GenericMethodArities.TypeMethodSpec1<
                                                Self, MethodRef1<Self, Object, Object>, Object>)
                                        (m, t) -> m.documented("ends the comment */ here")
                                                .declareMethod("id", t, t));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("contains */");
    }
}
