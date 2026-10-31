package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What the typed declarations of §6.5 refuse: a member javac would reject,
/// a constant body that leaves an abstract method, an element of a type JLS
/// 9.6.1 does not allow, and an annotation that cannot annotate a package.
class TypedDeclarationRefusalsTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void anInterfaceRefusesTwoMethodsOfOneSignature() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.declareAbstractMethod("m", PrimitiveToken.INT);
                                ib.declareDefaultMethod("m", PrimitiveToken.INT);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("clashes with the method")
                .withMessageContaining("both erase to m()");
    }

    @Test
    void anInterfaceRefusesAPermitAfterItsMembersBegan() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.bodies();
                                ib.permits(ClassDesc.of("me.supcheg.example", "One"));
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("the members");
    }

    @Test
    void aRecordRefusesAComponentAfterItsMembersBegan() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var bodies = rb.bodies();
                                rb.component("x", PrimitiveToken.INT);
                                return bodies;
                            }
                        }))
                .withMessageContaining("the members");
    }

    @Test
    void anEnumRefusesAConstantWithoutABodyWhenAnAbstractMethodRemains() {
        assertThatIllegalStateException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                                eb.constant("ONE");
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("has no body")
                .withMessageContaining("abstract methods");
    }

    @Test
    void anAnnotationTypeRefusesAnElementOfAnInterface() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.element("bad", Runnable_.TOKEN);
                                return ab;
                            }
                        }))
                .withMessageContaining("cannot be of")
                .withMessageContaining("interface");
    }

    @Test
    void aPackageInfoRefusesAnAnnotationThatCannotAnnotateAPackage() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.packageInfo(
                        UnsafeFacts.unverifiedClasspath(),
                        "me.supcheg.example",
                        pb -> pb.withAnnotation(Tag_.annotation()
                                .value(me.supcheg.javafile.facts.AnnotationValues.string("t"))
                                .build())))
                .withMessageContaining("cannot annotate a package");
    }

    @Test
    void anOpenModuleRefusesOpens() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.module(
                        UnsafeFacts.unverifiedClasspath(),
                        "com.example.app",
                        mb -> mb.open().opens("com.example.internal")))
                .withMessageContaining("open module cannot declare explicit 'opens'");
    }
}
