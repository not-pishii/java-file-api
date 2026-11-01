package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What the static fields of the typed declarations of §6.5 refuse: a type parameter of the class, which a
/// static member has not in scope (JLS 8.1.2); a forward reference, a read of a static field declared at or
/// after the one initialized (JLS 8.3.3); `this`, which no static context hands out; a name another member
/// has, an initializer given twice or never, a field of another declaration, and a mutable static field of a
/// record. Each refusal is told against javac, which rejects the Java it stands for — or accepts it, where the
/// typed layer is stricter and the test says so.
class StaticFieldChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final String STATIC_CONTEXT =
            "non-static type variable T cannot be referenced from a static context";

    private static JavaFile probe(TypedJavaFile.TypedGenericClassSpec1<Object> spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, TypedJavaFile.typeParam("T"), spec);
    }

    @Test
    void aStaticFieldOfATypeParameterOfTheClassIsRefusedAsJavacRefusesIt() {
        JavacVerdict.classAsJavac(
                IllegalStateException.class,
                () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        h.extendsObject().members().declareStaticField("value", t);
                        return Unreached.here();
                    }
                }),
                "the type parameter T is not in scope in the type of static field value of class"
                        + " me.supcheg.example.Probe: a static member of a generic class refers neither to a"
                        + " type parameter of the class nor to the class with its type parameters (JLS 8.1.2)",
                "<T>",
                "public static final T value = null;",
                STATIC_CONTEXT);
    }

    @Test
    void aStaticInitializerMentioningATypeParameterOfTheClassIsRefusedAsJavacRefusesIt() {
        JavacVerdict.classAsJavac(
                IllegalStateException.class,
                () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var value = cb.declareStaticField("value", Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.initialize(value, literalNull(t).cast(t));
                        return bodies;
                    }
                }),
                "the type parameter T is not in scope in the initializer of static field value of class"
                        + " me.supcheg.example.Probe: a static member of a generic class refers neither to a type"
                        + " parameter of the class nor to the class with its type parameters (JLS 8.1.2)",
                "<T>",
                "public static final Object value = (T) null;",
                STATIC_CONTEXT);
    }

    /// A static initializer reads only the static fields of the class that are declared before the field it
    /// initializes (JLS 8.3.3): the typed layer renders every static field by its qualified name, which javac
    /// accepts there, so the read would be of the default value rather than of the field.
    @Test
    void aStaticInitializerReadingAStaticFieldDeclaredLaterIsRefused() {
        JavacVerdict.unlikeJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                        var two = cb.declareStaticField("TWO", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(two, literal(2));
                        bodies.initialize(one, addInt(staticField(two), literal(1)));
                        return bodies;
                    }
                }),
                "the initializer of static int me.supcheg.example.Probe.ONE reads static int"
                        + " me.supcheg.example.Probe.TWO, which is declared at or after it",
                "static final int ONE = Probe.TWO + 1; static final int TWO = 2;");
    }

    /// The read JLS 8.3.3 does forbid is by the simple name, and javac rejects it.
    @Test
    void javacRejectsTheSimpleNameFormOfTheForwardReference() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                                var two = cb.declareStaticField("TWO", PrimitiveToken.INT);
                                var bodies = cb.bodies();
                                bodies.initialize(two, literal(2));
                                bodies.initialize(one, addInt(staticField(two), literal(1)));
                                return bodies;
                            }
                        }))
                .withMessageContaining("which is declared at or after it");

        assertThat(JavacVerdict.compiled("static final int ONE = TWO + 1; static final int TWO = 2;"))
                .hadErrorContaining("illegal forward reference");
    }

    @Test
    void aStaticInitializerReadingItselfIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                                var bodies = cb.bodies();
                                bodies.initialize(one, addInt(staticField(one), literal(1)));
                                return bodies;
                            }
                        }))
                .withMessageContaining(
                        "reads static int me.supcheg.example.Probe.ONE, which is declared at or after it")
                .withMessageContaining("JLS 8.3.3");
    }

    /// The static initializer of a class is no body of one: the `this` a method body is handed is out of
    /// scope in it.
    @Test
    void aStaticInitializerCannotUseThis() {
        This<?>[] smuggled = new This<?>[1];
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var value = cb.declareStaticField("value", Object_.TOKEN);
                                var name = cb.declareMethod("name", String_.TOKEN);
                                var bodies = cb.bodies();
                                bodies.define(name, (b, self) -> {
                                    smuggled[0] = self;
                                    return b.return_(literal("x"));
                                });
                                bodies.initialize(value, smuggled[0]);
                                return bodies;
                            }
                        }))
                .withMessageContaining("the this of the body of method name is used in the initializer of static")
                .withMessageContaining("field value, which is not inside that body");
    }

    @Test
    void aNameAnotherMemberHasIsRefusedAsItIsForAnyOtherField() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("value", PrimitiveToken.INT);
                                cb.declareStaticField("value", PrimitiveToken.INT);
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("field value of class me.supcheg.example.Probe is already declared")
                .withMessageContaining("JLS 8.3");
    }

    @Test
    void aStaticFieldIsInitializedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                                var bodies = cb.bodies();
                                bodies.initialize(one, literal(1));
                                bodies.initialize(one, literal(2));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is already defined; each declared member is defined exactly once");
    }

    @Test
    void aStaticFieldOfAnEnumIsInitializedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                var one = eb.declareStaticField("ONE", PrimitiveToken.INT);
                                eb.constant("A");
                                var bodies = eb.bodies();
                                bodies.initialize(one, literal(1));
                                bodies.initialize(one, literal(2));
                                return bodies;
                            }
                        }))
                .withMessageContaining("field static int me.supcheg.example.Probe.ONE of enum"
                        + " me.supcheg.example.Probe is already defined");
    }

    @Test
    void aStaticFieldWithoutAnInitializerIsRefusedAtTheEndOfTheDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("base", PrimitiveToken.INT);
                                cb.declareStaticField("ONE", PrimitiveToken.INT);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("is complete, but these declared members are never defined")
                .withMessageContaining("field static int me.supcheg.example.Probe.ONE")
                .withMessageContaining("give each field its initializer");
    }

    @Test
    void aStaticFieldIsInitializedOnlyByItsOwnDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                                var other = UnsafeFacts.staticField(cb.self(), "OTHER", PrimitiveToken.INT);
                                var bodies = cb.bodies();
                                bodies.initialize(one, literal(1));
                                bodies.initialize(other, literal(2));
                                return bodies;
                            }
                        }))
                .withMessageContaining("cannot define field static int me.supcheg.example.Probe.OTHER: it was")
                .withMessageContaining("not declared by the builder of class me.supcheg.example.Probe");
    }

    /// A static field of a record is `public static final` in the model of a record the typed layer renders,
    /// so it is refused as a mutable one before anything is declared.
    @Test
    void aRecordRefusesAMutableStaticField() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareMutableStaticField("count", PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("a static field of record me.supcheg.example.Probe cannot be mutable")
                .withMessageContaining("public static final alone");
    }

    @Test
    void aStaticFieldOfAnEnumWithoutAnInitializerIsRefusedAtTheEndOfTheDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.declareField("value", PrimitiveToken.INT, Set.of(Modifier.PRIVATE));
                                eb.declareStaticField("COUNT", PrimitiveToken.INT);
                                var a = eb.declareStaticMethod("a", PrimitiveToken.INT);
                                eb.constant("ONE");
                                var bodies = eb.bodies();
                                bodies.define(a, b -> b.return_(literal(1)));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is complete, but these declared members are never defined")
                .withMessageContaining("field int me.supcheg.example.Probe.value")
                .withMessageContaining("field static int me.supcheg.example.Probe.COUNT");
    }

    /// An interface declares constants, not static fields: the guard of the kind that the public builder does
    /// not reach is held directly.
    @Test
    void anInterfaceDeclaresNoStaticField() {
        TypeDeclarationSupport.Member.StaticField field = new TypeDeclarationSupport.Member.StaticField(
                UnsafeFacts.staticField(String_.TOKEN, "S", PrimitiveToken.INT),
                Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                List.of(),
                java.util.Optional.empty(),
                Site.here());
        TypedInterfaceBodies[] interfaceBodies = new TypedInterfaceBodies[1];
        TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                interfaceBodies[0] = ib.bodies();
                return interfaceBodies[0];
            }
        });

        assertThatIllegalStateException()
                .isThrownBy(() -> interfaceBodies[0].staticField(field, null))
                .withMessageContaining("an interface declares constants, not static fields");
    }
}
