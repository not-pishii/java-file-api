package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What the initializer blocks of the typed declarations of §6.5 refuse, each told against javac where javac
/// has an opinion: an interface has no block, a record has no instance one, a static block throws no checked
/// exception and cannot complete abnormally, an instance block throws a checked exception only if every
/// constructor declares it, and it reads only fields whose initializers have run.
class InitializerBlockChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void anInterfaceDeclaresNoInstanceInitializerBlock() {
        JavacVerdict.interfaceAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareInitializerBlock();
                                return Unreached.here();
                            }
                        }),
                "an interface has no initializer blocks",
                "",
                "{ }",
                "initializers not allowed in interfaces");
    }

    @Test
    void anInterfaceDeclaresNoStaticInitializerBlock() {
        JavacVerdict.interfaceAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareStaticInitializerBlock();
                                return Unreached.here();
                            }
                        }),
                "an interface has no initializer blocks",
                "",
                "static { }",
                "initializers not allowed in interfaces");
    }

    @Test
    void aRecordDeclaresNoInstanceInitializerBlock() {
        JavacVerdict.recordAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareInitializerBlock();
                                return Unreached.here();
                            }
                        }),
                "a record has no instance initializer block",
                "(int x)",
                "{ }",
                "instance initializers not allowed in records");
    }

    @Test
    void aStaticInitializerBlockThrowsNoCheckedException() {
        JavacVerdict.asJavac(
                IllegalStateException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareStaticInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(block, b -> b.throw_(new_(IOException_.new_)));
                        return bodies;
                    }
                }),
                "can throw the checked exception java.io.IOException: a static initializer block is in no body"
                        + " and declares nothing",
                "static { throw new java.io.IOException(); }",
                "unreported exception java.io.IOException");
    }

    @Test
    void aStaticInitializerBlockMustCompleteNormally() {
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareStaticInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(block, b -> b.loopForever(_ -> {}));
                        return bodies;
                    }
                }),
                "cannot complete normally: an initializer block must be able to complete normally (JLS 8.7)",
                "static { while (true) { } }",
                "initializer must be able to complete normally");
    }

    @Test
    void anInstanceInitializerBlockMustCompleteNormally() {
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(block, (b, _) -> b.loopForever(_ -> {}));
                        return bodies;
                    }
                }),
                "cannot complete normally: an initializer block must be able to complete normally (JLS 8.6)",
                "{ while (true) { } }",
                "initializer must be able to complete normally");
    }

    @Test
    void anInstanceInitializerBlockThrowsACheckedExceptionNoConstructorDeclares() {
        JavacVerdict.asJavac(
                IllegalStateException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var zero = cb.declareConstructor();
                        var one = cb.declareConstructor(PrimitiveToken.INT);
                        var block = cb.declareInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(zero, (b, _) -> b.end());
                        bodies.define(one, (b, _, value) -> b.end());
                        bodies.define(block, (b, _) -> b.throw_(new_(IOException_.new_)));
                        return bodies;
                    }
                }),
                "which new me.supcheg.example.Probe(), new me.supcheg.example.Probe(int) do not declare",
                "{ throw new java.io.IOException(); } Probe() { } Probe(int v) { }",
                "unreported exception java.io.IOException");
    }

    @Test
    void anInstanceInitializerBlockThrowsACheckedExceptionOneConstructorDoesNotDeclare() {
        JavacVerdict.asJavac(
                IllegalStateException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var throwing = cb.throwing(IOException_.TOKEN).declareConstructor(PrimitiveToken.INT);
                        var plain = cb.declareConstructor();
                        var block = cb.declareInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(throwing, (b, _, value) -> b.end());
                        bodies.define(plain, (b, _) -> b.end());
                        bodies.define(block, (b, _) -> b.throw_(new_(IOException_.new_)));
                        return bodies;
                    }
                }),
                "which new me.supcheg.example.Probe() does not declare",
                "{ throw new java.io.IOException(); } Probe(int v) throws java.io.IOException { } Probe() { }",
                "unreported exception java.io.IOException");
    }

    @Test
    void anInstanceInitializerBlockThrowsACheckedExceptionEveryConstructorDeclares() {
        var compiled = me.supcheg.javafile.testing.CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var read = cb.throwing(IOException_.TOKEN).declareVoidMethod("read");
                        var zero = cb.throwing(IOException_.TOKEN).declareConstructor();
                        var one = cb.throwing(IOException_.TOKEN).declareConstructor(PrimitiveToken.INT);
                        var block = cb.declareInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(read, (b, _) -> b.throw_(new_(IOException_.new_)));
                        bodies.define(zero, (b, _) -> b.end());
                        bodies.define(one, (b, _, value) -> b.end());
                        bodies.define(
                                block, (b, self) -> b.exec(self.voidCall(read)).end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public Probe() throws IOException")
                .contains("public Probe(int v0) throws IOException");
    }

    @Test
    void anInstanceInitializerBlockReadsNoFieldDeclaredAfterIt() {
        JavacVerdict.unlikeJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareInitializerBlock();
                        var later = cb.declareField("later", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(block, (b, self) -> b.let(PrimitiveToken.INT, self.field(later), _ -> b.end()));
                        bodies.initialize(later, literal(1));
                        return bodies;
                    }
                }),
                "reads int me.supcheg.example.Probe.later, which is declared at or after it",
                "{ int i = Probe.this.later; } int later = 1;");
    }

    @Test
    void aStaticInitializerBlockReadsNoFieldDeclaredAfterIt() {
        JavacVerdict.unlikeJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var block = cb.declareStaticInitializerBlock();
                        var later = cb.declareStaticField("LATER", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(block, b -> b.let(PrimitiveToken.INT, staticField(later), _ -> b.end()));
                        bodies.initialize(later, literal(1));
                        return bodies;
                    }
                }),
                "reads static int me.supcheg.example.Probe.LATER, which is declared at or after it",
                "static { int i = Probe.LATER; } static final int LATER = 1;");
    }

    @Test
    void anInitializerBlockIsDefinedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var block = cb.declareInitializerBlock();
                                var bodies = cb.bodies();
                                bodies.define(block, (b, _) -> b.end());
                                bodies.define(block, (b, _) -> b.end());
                                return bodies;
                            }
                        }))
                .withMessageContaining(
                        "instance initializer block of class me.supcheg.example.Probe is" + " already defined");
    }

    @Test
    void anInitializerBlockWithoutABodyIsRefusedAtTheEndOfTheDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareStaticInitializerBlock();
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("the declaration of class me.supcheg.example.Probe is complete")
                .withMessageContaining("static initializer block");
    }

    @Test
    void anInstanceBlockWithoutABodyIsRefusedAtTheEndOfTheDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareInitializerBlock();
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("the declaration of class me.supcheg.example.Probe is complete")
                .withMessageContaining("instance initializer block");
    }

    @Test
    void anEnumBlockWithoutABodyIsRefusedAtTheEndOfTheDeclaration() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant("A");
                                eb.declareStaticInitializerBlock();
                                eb.declareInitializerBlock();
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("the declaration of enum me.supcheg.example.Probe is complete")
                .withMessageContaining("static initializer block")
                .withMessageContaining("instance initializer block");
    }

    @Test
    void anEnumInitializerBlockIsDefinedOnce() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant("A");
                                var block = eb.declareStaticInitializerBlock();
                                var bodies = eb.bodies();
                                bodies.define(block, b -> b.end());
                                bodies.define(block, b -> b.end());
                                return bodies;
                            }
                        }))
                .withMessageContaining(
                        "static initializer block of enum me.supcheg.example.Probe is" + " already defined");
    }

    @Test
    void anEnumWithoutAConstructorHasTheImplicitOneForItsInstanceBlock() throws Throwable {
        var compiled = me.supcheg.javafile.testing.CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("A");
                        var block = eb.declareInitializerBlock();
                        var bodies = eb.bodies();
                        bodies.define(block, (b, _) -> b.end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("enum Probe");
    }

    @Test
    void anInitializerBodyHasNoReturn() {
        assertThat(InitializerBody.class.getMethods())
                .noneMatch(method -> method.getName().equals("return_"));
        com.google.testing.compile.Compilation returned = JavacVerdict.compiled("{ return; }");
        com.google.testing.compile.CompilationSubject.assertThat(returned).failed();
        com.google.testing.compile.CompilationSubject.assertThat(returned).hadErrorContaining("return outside method");
    }

    @Test
    void aBlockOfAnotherEnumIsNotDefinable() {
        StaticInitializerBlock[] foreign = new StaticInitializerBlock[1];
        TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Other"),
                new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("A");
                        foreign[0] = eb.declareStaticInitializerBlock();
                        var bodies = eb.bodies();
                        bodies.define(foreign[0], b -> b.end());
                        return bodies;
                    }
                });

        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant("A");
                                var bodies = eb.bodies();
                                bodies.define(foreign[0], b -> b.end());
                                return bodies;
                            }
                        }))
                .withMessageContaining(
                        "cannot define the static initializer block of enum" + " me.supcheg.example.Probe")
                .withMessageContaining("not declared by the builder of enum me.supcheg.example.Probe");
    }

    @Test
    void aBlockOfAnotherClassIsNotDefinable() {
        InstanceInitializerBlock[] foreign = new InstanceInitializerBlock[1];
        TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Other"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        foreign[0] = cb.declareInitializerBlock();
                        var bodies = cb.bodies();
                        bodies.define(foreign[0], (b, _) -> b.end());
                        return bodies;
                    }
                });

        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var bodies = cb.bodies();
                                bodies.define(foreign[0], (b, _) -> b.end());
                                return bodies;
                            }
                        }))
                .withMessageContaining(
                        "cannot define the instance initializer block of class" + " me.supcheg.example.Probe")
                .withMessageContaining("not declared by the builder of class me.supcheg.example.Probe");
    }
}
