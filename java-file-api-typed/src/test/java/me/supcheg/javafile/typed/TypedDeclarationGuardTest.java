package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Defaulted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.PairedSink_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Stated_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Unconstrained_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What the declarations of §6.5 refuse: a member of a kind the type cannot have, a member defined
/// or declared twice, a member declared and never defined, a constant that is no constant
/// expression, an override of nothing, a body of another declaration, and a member declared after
/// the declaration or its bodies ended. The paths the public builders cannot reach — a constructor
/// of an interface or an enum declared as a method fact, and a constant body's field, constant,
/// constructor or abstract method — are held directly.
class TypedDeclarationGuardTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Guard");

    @Test
    void aRecordRefusesAnAbstractMethod() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareAbstractMethod("m", PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("a record cannot declare an abstract method");
    }

    @Test
    void aRecordRefusesAnExplicitInvocationInItsCanonicalConstructor() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.component("x", PrimitiveToken.INT);
                                var canonical = rb.declareCanonicalConstructor(PrimitiveToken.INT);
                                var bodies = rb.bodies();
                                bodies.defineDelegating(canonical, List.of(literal(0)), (b, self, x) -> b.end());
                                return bodies;
                            }
                        }))
                .withMessageContaining("a canonical constructor of a record begins with no explicit invocation");
    }

    @Test
    void aRecordRefusesAField() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var field = rb.declareField("f", PrimitiveToken.INT, Set.of(Modifier.PRIVATE));
                                var bodies = rb.bodies();
                                bodies.initialize(field, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("a record has no instance field of its own");
    }

    @Test
    void aRecordRefusesAConstant() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareMethod("m", PrimitiveToken.INT);
                                rb.declareField("f", PrimitiveToken.INT, Set.of());
                                var constant = rb.declareConstant("C", PrimitiveToken.INT);
                                var bodies = rb.bodies();
                                bodies.initialize(constant, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("a record declares no constant");
    }

    @Test
    void anInterfaceRefusesAnInstanceMethod() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var method = ib.declareMethod("m", PrimitiveToken.INT);
                                var bodies = ib.bodies();
                                bodies.define(method, (b, self) -> b.return_(literal(0)));
                                return bodies;
                            }
                        }))
                .withMessageContaining("an interface cannot declare");
    }

    @Test
    void anInterfaceRefusesAField() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var field = ib.declareField("f", PrimitiveToken.INT, Set.of());
                                var bodies = ib.bodies();
                                bodies.initialize(field, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("an interface declares constants, not fields");
    }

    @Test
    void anInterfaceHasNoConstructorOfAFact() {
        TypedInterfaceBuilder[] builder = new TypedInterfaceBuilder[1];
        TypedInterfaceBodies[] bodies = new TypedInterfaceBodies[1];
        TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                builder[0] = ib;
                var built = ib.bodies();
                bodies[0] = built;
                return built;
            }
        });
        var callable = callable(builder[0]);

        assertThatIllegalStateException()
                .isThrownBy(() -> bodies[0].constructor(callable, null, List.of(), List.of(), List.of()))
                .withMessageContaining("an interface has no constructor");
    }

    @Test
    void anEnumHasNoConstructorOfAFact() {
        TypedEnumBuilder[] builder = new TypedEnumBuilder[1];
        TypedEnumBodies[] bodies = new TypedEnumBodies[1];
        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                builder[0] = eb;
                eb.constant("A");
                var built = eb.bodies();
                bodies[0] = built;
                return built;
            }
        });
        var callable = callable(builder[0]);

        assertThatIllegalStateException()
                .isThrownBy(() -> bodies[0].constructor(callable, null, List.of(), List.of(), List.of()))
                .withMessageContaining("an enum constructor is declared through the builder");
    }

    @Test
    void anEnumDeclaresNoConstantField() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                var constant = eb.declareConstant("C", PrimitiveToken.INT);
                                eb.constant("A");
                                var bodies = eb.bodies();
                                bodies.initialize(constant, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("an enum declares constants, not constant fields");
    }

    @Test
    void aConstantBodyDeclaresMethodsAlone() {
        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var abstractMethod = eb.declareAbstractMethod("a", PrimitiveToken.INT);
                eb.constructor((b, self) -> b.end());
                eb.constant("A", cb -> {
                    var callable = callable(eb);
                    var field = new TypeDeclarationSupport.Member.Field(
                            UnsafeFacts.field(eb.self(), "f", PrimitiveToken.INT),
                            Set.of(),
                            List.of(),
                            java.util.Optional.empty(),
                            Site.here());
                    var constant = new TypeDeclarationSupport.Member.Constant(
                            UnsafeFacts.staticField(eb.self(), "C", PrimitiveToken.INT),
                            List.of(),
                            java.util.Optional.empty(),
                            Site.here());
                    assertThatIllegalStateException()
                            .isThrownBy(() -> cb.abstractMethod(callable, List.of(), List.of(), List.of()))
                            .withMessageContaining("a constant body cannot declare an abstract method");
                    assertThatIllegalStateException()
                            .isThrownBy(() -> cb.constructor(callable, null, List.of(), List.of(), List.of()))
                            .withMessageContaining("a constant body has no constructor");
                    assertThatIllegalStateException()
                            .isThrownBy(() -> cb.field(field, null))
                            .withMessageContaining("a constant body of the typed layer declares methods alone");
                    assertThatIllegalStateException()
                            .isThrownBy(() -> cb.constant(constant, null))
                            .withMessageContaining("a constant body of the typed layer declares methods alone");
                    cb.override(abstractMethod, (b, self) -> b.return_(literal(0)));
                });
                return eb.bodies();
            }
        });
    }

    @Test
    void anAnnotationTypeIsCompleteOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.complete();
                                return ab;
                            }
                        }))
                .withMessageContaining("already complete");
    }

    @Test
    void anAnnotationTypeRefusesAnElementAfterCompletion() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.complete();
                                ab.element("text", String_.TOKEN);
                                return ab;
                            }
                        }))
                .withMessageContaining("the declaration of annotation type");
    }

    @Test
    void aPackageInfoIsCompleteOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.packageInfo(
                        UnsafeFacts.unverifiedClasspath(), "me.supcheg.example", TypedPackageInfoBuilder::complete))
                .withMessageContaining("already complete");
    }

    @Test
    void aPackageInfoRefusesAnAnnotationAfterCompletion() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.packageInfo(UnsafeFacts.unverifiedClasspath(), "me.supcheg.example", pb -> {
                            pb.complete();
                            pb.withAnnotation(Unconstrained_.annotation()
                                    .value(AnnotationValues.string("tag"))
                                    .build());
                        }))
                .withMessageContaining("already complete");
    }

    @Test
    void aPackageInfoOfTheUnnamedPackageIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.packageInfo(UnsafeFacts.unverifiedClasspath(), "", pb -> {}))
                .withMessageContaining("the unnamed package");
    }

    @Test
    void aModuleIsCompleteOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.module(
                        UnsafeFacts.unverifiedClasspath(), "com.example", TypedModuleBuilder::complete))
                .withMessageContaining("already complete");
    }

    @Test
    void aModuleRefusesADirectiveAfterCompletion() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.module(UnsafeFacts.unverifiedClasspath(), "com.example", mb -> {
                    mb.complete();
                    mb.requires("java.base");
                }))
                .withMessageContaining("already complete");
    }

    @Test
    void aTypeRefusesAMemberAfterItsBodies() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.bodies();
                                ib.declareAbstractMethod("m", PrimitiveToken.INT);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("the declarations of interface");
    }

    @Test
    void aTypeRefusesAMemberAfterCompletion() {
        TypedInterfaceBuilder[] builder = new TypedInterfaceBuilder[1];
        TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                builder[0] = ib;
                return ib.bodies();
            }
        });

        assertThatIllegalStateException()
                .isThrownBy(() -> builder[0].declareAbstractMethod("m", PrimitiveToken.INT))
                .withMessageContaining("already complete");
    }

    @Test
    void aTypeRefusesADefinitionAfterCompletion() {
        TypedInterfaceBuilder[] builder = new TypedInterfaceBuilder[1];
        TypedInterfaceBodies[] bodies = new TypedInterfaceBodies[1];
        TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                builder[0] = ib;
                var method = ib.declareDefaultMethod("m", PrimitiveToken.INT);
                var built = ib.bodies();
                bodies[0] = built;
                built.define(method, (b, self) -> b.return_(literal(0)));
                return built;
            }
        });
        var field = UnsafeFacts.field(builder[0].self(), "f", PrimitiveToken.INT);

        assertThatIllegalStateException()
                .isThrownBy(() -> bodies[0].initialize(field, literal(0)))
                .withMessageContaining("already complete");
    }

    @Test
    void aTypeKnowsItsMethodsOnlyOnceItsMembersAreDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.self().methods();
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("the methods of interface");
    }

    @Test
    void aTypeRefusesTheBodiesOfAnotherDeclaration() {
        TypedInterfaceBodies[] first = new TypedInterfaceBodies[1];
        TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "First"),
                new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var built = ib.bodies();
                        first[0] = built;
                        return built;
                    }
                });

        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            @SuppressWarnings("unchecked")
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                return (TypedInterfaceBodies<Self>) first[0];
                            }
                        }))
                .withMessageContaining("returned bodies before its declarations ended");
    }

    @Test
    void anOverrideRefusesAWitnessOfAnotherType() {
        Upcast[] witness = new Upcast[1];
        TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "First"),
                new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var up = rb.implements_(Named_.TOKEN);
                        witness[0] = up;
                        var label = rb.declareOverride(up, Named_.label);
                        var bodies = rb.bodies();
                        bodies.define(label, (b, self) -> b.return_(literal("named")));
                        return bodies;
                    }
                });

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            @SuppressWarnings({"unchecked", "rawtypes"})
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareOverride((Upcast) witness[0], Named_.label);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("the witness is of");
    }

    @Test
    void anOverrideRefusesAMethodTheSupertypeHasNot() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var up = rb.implements_(Stated_.TOKEN);
                                rb.declareOverride(
                                        up,
                                        UnsafeInvocables.method(
                                                Stated_.TOKEN, "nope", PrimitiveToken.INT, MemberTraits.DEFAULT));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("overrides nothing");
    }

    @Test
    void aRecordDeclaresItsCanonicalConstructorOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareField("f", PrimitiveToken.INT, Set.of());
                                rb.declareMethod("m", PrimitiveToken.INT);
                                rb.declareCompactConstructor();
                                rb.declareCanonicalConstructor();
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("declares its canonical constructor already");
    }

    @Test
    void aRecordDeclaresItsCompactConstructorOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareCanonicalConstructor();
                                rb.declareCompactConstructor();
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("declares its canonical constructor already");
    }

    @Test
    void aCompactConstructorNamesItsParametersBeforeTheGeneratedOnes() {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component("v0", PrimitiveToken.INT);
                        var compact = rb.declareCompactConstructor(PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(
                                compact, (b, self, v0) -> b.let(PrimitiveToken.INT, literal(1), local -> b.end()));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("int v1 = 1;");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void aRecordTokenKnowsItsSuperclassChain() {
        TypedRecordBuilder[] builder = new TypedRecordBuilder[1];
        TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                builder[0] = rb;
                return rb.bodies();
            }
        });

        assertThat(((me.supcheg.javafile.facts.ClassToken<?>) builder[0].self()).superclasses())
                .containsExactly(ClassDesc.of("java.lang", "Record"), java.lang.constant.ConstantDescs.CD_Object);
    }

    @Test
    void anInterfaceRefusesAProtectedMethodReachedInItsBody() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var secret = UnsafeInvocables.method(
                                        ib.self(),
                                        "secret",
                                        PrimitiveToken.INT,
                                        MemberTraits.DEFAULT.with(Access.PROTECTED));
                                var peek = ib.declareDefaultMethod("peek", PrimitiveToken.INT);
                                var bodies = ib.bodies();
                                bodies.define(peek, (b, self) -> b.return_(call(self, secret)));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is protected");
    }

    @Test
    void aTypeRefusesAFieldOfADuplicateName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.declareField("f", PrimitiveToken.INT, Set.of());
                                eb.declareField("f", PrimitiveToken.INT, Set.of());
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("is already declared");
    }

    @Test
    void anInterfaceRefusesAStaticMethodOfAnInheritedSignature() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.extends_(me.supcheg.javafile.typed.testfacts.java.lang.Runnable_.TOKEN);
                                ib.declareVoidStaticMethod("run");
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("a static method neither overrides nor hides");
    }

    @Test
    void aTypeRefusesAMethodWhoseBridgeWouldBeAnother() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                InterfaceToken<?> sink = UnsafeFacts.interfaceToken(
                                        PairedSink_.Data.SHAPE, TokenArg.exact(String_.TOKEN));
                                rb.implements_(sink);
                                rb.declareMethod("put", String_.TOKEN, Object_.TOKEN);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("name clash");
    }

    @Test
    void aRecordRefusesTwoConstructorsOfOneParameterList() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.component("x", PrimitiveToken.INT);
                                rb.declareCanonicalConstructor(PrimitiveToken.INT);
                                rb.declareConstructor(PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("clashes with the constructor");
    }

    @Test
    void aRecordRefusesToEndWithUnsettledDefaults() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.implements_(Named_.TOKEN);
                                rb.implements_(Defaulted_.TOKEN);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("overrides none of them");
    }

    @Test
    void aRecordRefusesToEndWithAnAbstractMethodUnimplemented() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.implements_(Numbered_.TOKEN);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("and declares no method of its signature");
    }

    @Test
    void aRecordPartitionsAnInheritedStaticMethodOutOfItsTable() {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.implements_(Stated_.TOKEN);
                        return rb.bodies();
                    }
                });

        assertThat(file.render()).contains("implements Stated");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void aTypeRefusesAnUndefinedMember() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareMethod("m", PrimitiveToken.INT);
                                rb.declareField("f", PrimitiveToken.INT, Set.of());
                                rb.declareConstant("C", PrimitiveToken.INT);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("never defined")
                .withMessageContaining("declared at");
    }

    @Test
    void aTypeRefusesADefinitionTwice() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var method = ib.declareDefaultMethod("m", PrimitiveToken.INT);
                                var bodies = ib.bodies();
                                bodies.define(method, (b, self) -> b.return_(literal(0)));
                                bodies.define(method, (b, self) -> b.return_(literal(0)));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is already defined");
    }

    @Test
    void aFieldIsInitializedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.declareConstant("C", PrimitiveToken.INT);
                                var first = eb.declareField("f", PrimitiveToken.INT, Set.of());
                                var second = eb.declareField("g", PrimitiveToken.INT, Set.of());
                                var method = eb.declareMethod("m", PrimitiveToken.INT);
                                eb.constant("A");
                                var bodies = eb.bodies();
                                bodies.define(method, (b, self) -> b.return_(literal(0)));
                                bodies.initialize(second, literal(0));
                                bodies.initialize(first, literal(0));
                                bodies.initialize(first, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is already defined");
    }

    @Test
    void aConstantIsInitializedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var first = ib.declareConstant("C", PrimitiveToken.INT);
                                var second = ib.declareConstant("D", PrimitiveToken.INT);
                                var bodies = ib.bodies();
                                bodies.initialize(second, literal(0));
                                bodies.initialize(first, literal(0));
                                bodies.initialize(first, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is already defined");
    }

    @Test
    void aTypeRefusesADefinitionOfAnUndeclaredMember() {
        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A");
                var bodies = eb.bodies();
                var undeclaredMethod =
                        UnsafeInvocables.method(eb.self(), "nope", PrimitiveToken.INT, MemberTraits.DEFAULT);
                var undeclaredField = UnsafeFacts.field(eb.self(), "nope", PrimitiveToken.INT);
                var undeclaredConstant = UnsafeFacts.staticField(eb.self(), "NOPE", PrimitiveToken.INT);

                assertThatIllegalStateException()
                        .isThrownBy(() -> bodies.define(undeclaredMethod, (b, self) -> b.return_(literal(0))))
                        .withMessageContaining("it was not declared by the builder");
                assertThatIllegalStateException()
                        .isThrownBy(() -> bodies.initialize(undeclaredField, literal(0)))
                        .withMessageContaining("it was not declared by the builder");
                assertThatIllegalStateException()
                        .isThrownBy(() -> bodies.initialize(undeclaredConstant, literal(0)))
                        .withMessageContaining("it was not declared by the builder");
                return bodies;
            }
        });
    }

    @Test
    void anInterfaceRefusesANonConstantConstantInitializer() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var constant = ib.declareConstant("C", PrimitiveToken.INT);
                                var bodies = ib.bodies();
                                bodies.initialize(constant, call(literal("x"), String_.length));
                                return bodies;
                            }
                        }))
                .withMessageContaining("is not a constant expression");
    }

    @Test
    void aRecordInitializesAConstantWithoutAConstantExpression() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var constant = rb.declareConstant("C", PrimitiveToken.INT);
                                var bodies = rb.bodies();
                                bodies.initialize(constant, literal(0));
                                return bodies;
                            }
                        }))
                .withMessageContaining("a record declares no constant");
    }

    @Test
    void aTypeFindsADeclaredMemberAmongTheOthers() {
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var first = ib.declareConstant("C", PrimitiveToken.INT);
                        var before = ib.declareDefaultMethod("before", PrimitiveToken.INT);
                        var after = ib.declareDefaultMethod("after", PrimitiveToken.INT);
                        var second = ib.declareConstant("D", PrimitiveToken.INT);
                        var bodies = ib.bodies();
                        bodies.define(after, (b, self) -> b.return_(literal(0)));
                        bodies.initialize(second, literal(0));
                        bodies.define(before, (b, self) -> b.return_(literal(0)));
                        bodies.initialize(first, literal(0));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("int C = 0;").contains("int D = 0;");
    }

    @Test
    void aPackageInfoAnnotatesAPackageWithAnAnnotationOfNoTarget() {
        var file = TypedJavaFile.packageInfo(
                UnsafeFacts.unverifiedClasspath(),
                "me.supcheg.example",
                pb -> pb.withAnnotation(Unconstrained_.annotation()
                        .value(AnnotationValues.string("tag"))
                        .build()));

        assertThat(file.render()).contains("@Unconstrained(\"tag\")");
    }

    private static TypeDeclarationSupport.Member.Callable callable(TypeDeclarationSupport<?> declaration) {
        return new TypeDeclarationSupport.Member.Callable(
                UnsafeInvocables.method(declaration.self(), "guard", PrimitiveToken.INT, MemberTraits.DEFAULT),
                TypeDeclarationSupport.Member.Kind.METHOD,
                List.of(),
                List.of(),
                List.of(),
                declaration.scope("the guard"),
                false,
                Set.of(),
                java.util.Optional.empty(),
                Site.here());
    }
}
