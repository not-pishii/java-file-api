package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The `synchronized` and `native` methods of a record and an enum (§6.5): declared through
/// `modified`, rendered, compiled and run; refused on an interface, whose methods are abstract,
/// default, static or private and never synchronized or native.
class TypeModifiersTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void aRecordDeclaresSynchronizedMethodsAndRefusesNativeOnes() throws Throwable {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var x = rb.component("x", PrimitiveToken.INT);
                        var doubled = rb.modified(Modifier.SYNCHRONIZED).declareMethod("doubled", PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(doubled, (b, self) -> b.return_(mulInt(self.call(x.accessor()), literal(2))));
                        return bodies;
                    }
                });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public record Probe(int x) {
                            public synchronized int doubled() {
                                return this.x() * 2;
                            }
                        }
                        """);

        CompiledClasses compiled = CompiledClasses.of(file);
        Object probe = compiled.generated().getConstructor(int.class).newInstance(21);
        assertThat(compiled.generated().getMethod("doubled").invoke(probe)).isEqualTo(42);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.modified(Modifier.NATIVE);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("javac rejects a native method of a record");
    }

    @Test
    void anEnumDeclaresSynchronizedAndNativeMethods() {
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A");
                var spin = eb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
                eb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT, PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.define(spin, (b, self) -> b.end());
                return bodies;
            }
        });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public enum Probe {
                            A;

                            public synchronized void spin() {
                            }

                            public native int read(int v0);
                        }
                        """);

        CompiledClasses.of(file);
    }

    @Test
    void everyModifiedFormIsDeclaredAndTheSynchronizedOnesRun() throws Throwable {
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        var sync = rb.modified(Modifier.SYNCHRONIZED);
                        var r0 = sync.declareMethod("r0", PrimitiveToken.INT);
                        var r1 = sync.declareMethod("r1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var r2 = sync.declareMethod("r2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var r3 = sync.declareMethod(
                                "r3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var r4 = sync.declareMethod(
                                "r4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var v0 = sync.declareVoidMethod("v0");
                        var v1 = sync.declareVoidMethod("v1", PrimitiveToken.INT);
                        var v2 = sync.declareVoidMethod("v2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var v3 = sync.declareVoidMethod(
                                "v3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var v4 = sync.declareVoidMethod(
                                "v4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s0 = sync.declareStaticMethod("s0", PrimitiveToken.INT);
                        var s1 = sync.declareStaticMethod("s1", PrimitiveToken.INT, PrimitiveToken.INT);
                        var s2 = sync.declareStaticMethod(
                                "s2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s3 = sync.declareStaticMethod(
                                "s3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var s4 = sync.declareStaticMethod(
                                "s4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        var sv0 = sync.declareVoidStaticMethod("sv0");
                        var sv1 = sync.declareVoidStaticMethod("sv1", PrimitiveToken.INT);
                        var sv2 = sync.declareVoidStaticMethod("sv2", PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv3 = sync.declareVoidStaticMethod(
                                "sv3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var sv4 = sync.declareVoidStaticMethod(
                                "sv4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        var bodies = rb.bodies();
                        bodies.define(r0, (b, self) -> b.return_(literal(1)));
                        bodies.define(r1, (b, self, a1) -> b.return_(a1));
                        bodies.define(r2, (b, self, a1, a2) -> b.return_(a2));
                        bodies.define(r3, (b, self, a1, a2, a3) -> b.return_(a3));
                        bodies.define(r4, (b, self, a1, a2, a3, a4) -> b.return_(a4));
                        bodies.define(v0, (b, self) -> b.end());
                        bodies.define(v1, (b, self, a1) -> b.end());
                        bodies.define(v2, (b, self, a1, a2) -> b.end());
                        bodies.define(v3, (b, self, a1, a2, a3) -> b.end());
                        bodies.define(v4, (b, self, a1, a2, a3, a4) -> b.end());
                        bodies.define(s0, b -> b.return_(literal(2)));
                        bodies.define(s1, (b, a1) -> b.return_(a1));
                        bodies.define(s2, (b, a1, a2) -> b.return_(a2));
                        bodies.define(s3, (b, a1, a2, a3) -> b.return_(a3));
                        bodies.define(s4, (b, a1, a2, a3, a4) -> b.return_(a4));
                        bodies.define(sv0, b -> b.end());
                        bodies.define(sv1, (b, a1) -> b.end());
                        bodies.define(sv2, (b, a1, a2) -> b.end());
                        bodies.define(sv3, (b, a1, a2, a3) -> b.end());
                        bodies.define(sv4, (b, a1, a2, a3, a4) -> b.end());
                        return bodies;
                    }
                });

        CompiledClasses compiled = CompiledClasses.of(file);
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated()
                        .getMethod("r4", int.class, int.class, int.class, int.class)
                        .invoke(probe, 1, 2, 3, 4))
                .isEqualTo(4);
        assertThat(compiled.generated().getMethod("s2", int.class, int.class).invoke(null, 1, 2))
                .isEqualTo(2);
    }

    @Test
    void anInterfaceRefusesSynchronizedAndNativeMethods() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.modified(Modifier.SYNCHRONIZED);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("an interface has no synchronized or native method");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                ib.modified(Modifier.NATIVE);
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("an interface has no synchronized or native method");
    }

    @Test
    void aModifierThatIsNotSynchronizedOrNativeIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.modified(Modifier.FINAL);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("cannot add the modifier FINAL");
    }
}
