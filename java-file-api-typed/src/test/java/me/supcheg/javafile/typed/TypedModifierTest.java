package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The modifiers of the typed declarations of §6.5 that the forms do not fix — `synchronized` and `native`
/// methods, `transient` and `volatile` fields, and the header of a class that is `abstract`, `non-sealed` or
/// `sealed` — rendered, compiled under `-Xlint:all`, and run where their behavior is observable.
class TypedModifierTest {

    private static final ClassDesc LOCKS = ClassDesc.of("me.supcheg.example", "Locks");
    private static final String EXAMPLE = "me.supcheg.example";

    @Test
    void synchronizedAndNativeMethodsAndTransientAndVolatileFieldsRenderCompileAndRun() throws Throwable {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var cache = cb.declareField(
                                "cache",
                                PrimitiveToken.INT,
                                Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.TRANSIENT));
                        var counter = cb.declareMutableField(
                                "counter", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.VOLATILE));
                        var stamp = cb.declareMutableField(
                                "stamp", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.TRANSIENT));
                        var bump = cb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("bump");
                        var read = cb.modified(Modifier.SYNCHRONIZED).declareStaticMethod("read", PrimitiveToken.INT);
                        cb.modified(Modifier.NATIVE)
                                .declareMethod("sum", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.modified(Modifier.NATIVE)
                                .variableArity()
                                .declareMethod(
                                        "all",
                                        PrimitiveToken.INT,
                                        me.supcheg.javafile.typed.testfacts.java.lang.String_.TOKEN,
                                        me.supcheg.javafile.facts.ArrayToken.of(
                                                me.supcheg.javafile.typed.testfacts.java.lang.String_.TOKEN));
                        cb.modified(Modifier.NATIVE).declareStaticMethod("count", PrimitiveToken.INT);
                        cb.throwing(me.supcheg.javafile.typed.testfacts.java.io.IOException_.TOKEN)
                                .modified(Modifier.NATIVE)
                                .declareMethod("open", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(cache, literal(7));
                        bodies.initialize(counter, literal(0));
                        bodies.initialize(stamp, literal(0));
                        bodies.define(
                                bump,
                                (b, self) -> b.exec(
                                                assignField(self, counter, addInt(field(self, counter), literal(1))))
                                        .end());
                        bodies.define(read, b -> b.return_(literal(3)));
                        return bodies;
                    }
                });

        assertThat(file.render()).isEqualTo("""
                package me.supcheg.example;

                import java.io.IOException;

                public final class Locks {
                    public final transient int cache = 7;

                    public volatile int counter = 0;

                    public transient int stamp = 0;

                    public synchronized void bump() {
                        this.counter = this.counter + 1;
                    }

                    public static synchronized int read() {
                        return 3;
                    }

                    public native int sum(int v0, int v1);

                    public native int all(String v0, String... v1);

                    public static native int count();

                    public native int open() throws IOException;
                }
                """);
        CompiledClasses compiled = CompiledClasses.of(file);
        assertThat(compiled.invoke("read")).isEqualTo(3);
        Object instance = compiled.generated().getConstructor().newInstance();
        compiled.generated().getMethod("bump").invoke(instance);
        assertThat(compiled.generated().getField("counter").getInt(instance)).isEqualTo(1);
    }

    @Test
    void aConstructorIsNeitherSynchronizedNorNative() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.modified(Modifier.SYNCHRONIZED).declareConstructor();
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a constructor cannot be");
    }

    @Test
    void aNativeMethodCannotBeDefined() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var read = cb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
                                var bodies = cb.bodies();
                                bodies.define(read, (b, self) -> b.return_(literal(0)));
                                return bodies;
                            }
                        }))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot define");
    }

    @Test
    void anAbstractMethodIsNeitherSynchronizedNorNative() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var abstractMethod = UnsafeInvocables.method(
                                        cb.self(), "m", PrimitiveToken.INT, MemberTraits.ABSTRACT);
                                cb.modified(Modifier.SYNCHRONIZED).declare(abstractMethod);
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("an abstract method is neither synchronized nor native");
    }

    @Test
    void aModifierTheFormsDoNotAddIsRefused() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.modified(Modifier.PRIVATE).declareMethod("read", PrimitiveToken.INT);
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot add the modifier PRIVATE");
    }

    @Test
    void aFieldIsTransientOrVolatileOnlyWhereJavaAllowsIt() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("x", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.VOLATILE));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a field of the class is public final");
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField(
                                        "x", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.FINAL));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a mutable field of the class is public");
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField(
                                        "x", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.STATIC));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a mutable field of the class is public");
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField(
                                        "x",
                                        PrimitiveToken.INT,
                                        Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a field of the class is public final");
    }

    @Test
    void aFieldSetWithoutAnAccessModifierIsRefused() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("x", PrimitiveToken.INT, Set.of(Modifier.TRANSIENT));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a field of the class is public final");
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), LOCKS, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField("x", PrimitiveToken.INT, Set.of(Modifier.VOLATILE));
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a mutable field of the class is public");
    }

    @Test
    void anAbstractClassRendersCompilesAndHasNoConstructor() {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "Root"),
                new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.abstract_();
                        return h.extendsObject().members().bodies();
                    }
                });

        assertThat(file.render()).isEqualTo("""
                package me.supcheg.example;

                public abstract class Root {
                }
                """);
        CompiledClasses.of(file);

        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(EXAMPLE, "Root"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.abstract_();
                                var cb = h.extendsObject().members();
                                cb.declareConstructor();
                                return cb.bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the class is abstract");
    }

    @Test
    void anAbstractClassIsDeclaredOnce() {
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(EXAMPLE, "Root"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.abstract_().abstract_();
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("is abstract already");
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(EXAMPLE, "Root"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.nonSealed().nonSealed();
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("is non-sealed already");
    }

    @Test
    void aSealedClassNamesItsPermittedSubtypesAndCompiles() {
        ClassDesc node = ClassDesc.of(EXAMPLE, "Node");
        ClassDesc leaf = ClassDesc.of(EXAMPLE, "Leaf");
        var leafToken =
                UnsafeFacts.finalClassToken(Types.of(leaf), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.permits(leafToken);
                        return h.extendsObject().members().bodies();
                    }
                });

        assertThat(file.render()).isEqualTo("""
                package me.supcheg.example;

                public sealed class Node permits Leaf {
                }
                """);
        CompiledClasses.ofAll(
                List.of(file),
                leaf.displayName(),
                "package me.supcheg.example; public final class Leaf extends Node { }");
    }

    @Test
    void namingNoPermittedSubtypeLeavesTheClassFinal() {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of(EXAMPLE, "Node"),
                new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.permits();
                        return h.extendsObject().members().bodies();
                    }
                });

        assertThat(file.render()).contains("public final class Node");
    }

    @Test
    void aSealedClassIsNotFinalAndCannotBeNonSealed() {
        ClassDesc node = ClassDesc.of(EXAMPLE, "Node");
        ClassDesc leaf = ClassDesc.of(EXAMPLE, "Leaf");
        var leafToken =
                UnsafeFacts.finalClassToken(Types.of(leaf), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

        JavaFile sealed = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.permits(leafToken);
                        return h.extendsObject().members().bodies();
                    }
                });
        assertThat(sealed.render()).contains("public sealed class Node permits Leaf");
        assertThat(sealed.render()).doesNotContain("final");

        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.nonSealed().permits(leafToken);
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both non-sealed and sealed");
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.permits(leafToken).nonSealed();
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both non-sealed and sealed");
    }

    @Test
    void aSealedClassPermitsASubtypeOnceAndNotItself() {
        ClassDesc node = ClassDesc.of(EXAMPLE, "Node");
        ClassDesc leaf = ClassDesc.of(EXAMPLE, "Leaf");
        var leafToken =
                UnsafeFacts.finalClassToken(Types.of(leaf), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        var nodeToken =
                UnsafeFacts.finalClassToken(Types.of(node), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.permits(leafToken, leafToken);
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("permits Leaf already");
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), node, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.permits(nodeToken);
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot permit itself");
    }

    @Test
    void aNonSealedClassNeedsASealedSupertype() {
        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(EXAMPLE, "Open"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                h.nonSealed();
                                return h.extendsObject().members().bodies();
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("extends or implements no sealed type");
    }

    @Test
    void aNonSealedClassExtendsASealedOneAndCompiles() {
        ClassDesc base = ClassDesc.of(EXAMPLE, "Base");
        ClassDesc open = ClassDesc.of(EXAMPLE, "Open");
        TypeShape<me.supcheg.javafile.facts.DeclaredKind.OpenClass> sealed = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of(EXAMPLE, "Base_"), "0".repeat(64), () -> ""),
                me.supcheg.javafile.facts.DeclaredKind.OPEN_CLASS,
                base,
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                true,
                List.of(),
                () -> new Heritage.Told(
                        List.of(),
                        List.of(new Heritage.Constructor(
                                Heritage.Visibility.PUBLIC,
                                MethodTableTemplate.Signature.of("Base"),
                                me.supcheg.javafile.facts.Arity.FIXED,
                                List.of(),
                                List.of(),
                                List.of())),
                        List.of()));
        OpenClassToken<Object> sealedBase = UnsafeFacts.openClassToken(sealed);

        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), open, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.nonSealed();
                        var sh = h.extends_(
                                UnsafeFacts.steps(sealedBase, h.self()).complete());
                        var cb = sh.members();
                        var ctor = cb.declareConstructor();
                        return cb.bodies(
                                ctor,
                                c -> c.super_(
                                        UnsafeInvocables.superCtor(sealedBase, MemberTraits.DEFAULT),
                                        (b, self) -> b.end()));
                    }
                });

        assertThat(file.render()).isEqualTo("""
                package me.supcheg.example;

                public non-sealed class Open extends Base {
                    public Open() {
                        super();
                    }
                }
                """);
        CompiledClasses.of(
                file, base.displayName(), "package me.supcheg.example; public sealed class Base permits Open { }");
    }

    @Test
    void aSealedSupertypeWithoutHeritageIsRefusedAsSealed() {
        ClassDesc base = ClassDesc.of(EXAMPLE, "Base");
        var sealedShape = UnsafeFacts.shape(
                me.supcheg.javafile.facts.DeclaredKind.OPEN_CLASS,
                base,
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                true);
        var sealedBase = UnsafeFacts.openClassToken(sealedShape);

        assertThatThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of(EXAMPLE, "Sub"),
                        new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var sh = h.extends_(
                                        UnsafeFacts.steps(sealedBase, h.self()).complete());
                                var cb = sh.members();
                                var ctor = cb.declareConstructor();
                                return cb.bodies(
                                        ctor,
                                        c -> c.super_(
                                                UnsafeInvocables.superCtor(sealedBase, MemberTraits.DEFAULT),
                                                (b, self) -> b.end()));
                            }
                        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("it is sealed");
    }
}
