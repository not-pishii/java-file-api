package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Math_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of typed declarations (§6.5, §9.7) that Java
/// types cannot express: completeness of declare/define, the signature
/// registry, `this` out of its body, and the self token's method table
/// before the declaration completes.
class DeclarationChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// Declares the class `Probe` with the given members.
    private static void declare(Consumer<TypedClassBuilder<?>> members) {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    @Nested
    class DeclareDefine {

        @Test
        void aDeclaredButUndefinedMemberIsRejectedWhenTheDeclarationCompletes() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
                        cb.declareConstructor();
                    }))
                    .withMessageContaining("the declaration of class me.supcheg.example.Probe is complete, but these"
                            + " declared members are never defined: boolean me.supcheg.example.Probe.isEven(int),"
                            + " new me.supcheg.example.Probe()");
        }

        @Test
        void aMemberDefinedTwiceIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var m = cb.declareVoidStaticMethod("m");
                        cb.define(m, VoidBody::end);
                        cb.define(m, VoidBody::end);
                    }))
                    .withMessageContaining("static void me.supcheg.example.Probe.m() of class"
                            + " me.supcheg.example.Probe is already defined; each declared member is defined exactly"
                            + " once");
        }

        @Test
        void aStaticFactOfAnotherClassIsRejectedWhenDefined() {
            // A static fact carries no owner brand, so this compiles.
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.define(Math_.toIntExact_long, (b, _) -> b.return_(literal(0)))))
                    .withMessageContaining("cannot define static int java.lang.Math.toIntExact(long): it was not"
                            + " declared by the builder of class me.supcheg.example.Probe");
        }

        @Test
        void aDefinitionInsideAMethodBodyIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    var late = cb.declareVoidMethod("late");
                                    cb.voidMethod("outer", (b, _) -> {
                                        cb.define(late, (x, _) -> x.end());
                                        return b.end();
                                    });
                                }
                            }))
                    .withMessageContaining("cannot declare void me.supcheg.example.Probe.late() while the body of"
                            + " method outer is being built");
        }

        @Test
        void aDefinitionAfterTheDeclarationCompletedIsRejected() {
            AtomicReference<Runnable> late = new AtomicReference<>();
            declare(cb -> {
                var m = cb.declareVoidStaticMethod("m");
                cb.define(m, VoidBody::end);
                late.set(() -> cb.define(m, VoidBody::end));
            });

            assertThatIllegalStateException()
                    .isThrownBy(() -> late.get().run())
                    .withMessageContaining("the declaration of class me.supcheg.example.Probe is already complete");
        }
    }

    @Nested
    class Signatures {

        @Test
        void aDuplicateMethodByErasureIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.staticMethod("m", PrimitiveToken.INT, PrimitiveToken.INT, Body::return_);
                        cb.declareMethod("m", String_.TOKEN, PrimitiveToken.INT);
                    }))
                    .withMessageContaining("the method java.lang.String me.supcheg.example.Probe.m(int) of class"
                            + " me.supcheg.example.Probe clashes with the method static int"
                            + " me.supcheg.example.Probe.m(int) declared before: both erase to m(int) (JLS 8.4.2)");
        }

        @Test
        void aDuplicateConstructorByErasureIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.declareConstructor(String_.TOKEN);
                        cb.declareConstructor(String_.TOKEN);
                    }))
                    .withMessageContaining("clashes with the constructor new me.supcheg.example.Probe(java.lang.String)"
                            + " declared before");
        }

        @Test
        void aDuplicateFieldIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.field("x", PrimitiveToken.INT, literal(1));
                        cb.mutableField("x", String_.TOKEN, literal("x"));
                    }))
                    .withMessageContaining("field x of class me.supcheg.example.Probe is already declared");
        }

        @Test
        void anOverrideOfAFinalObjectMethodIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareVoidMethod("wait", PrimitiveToken.LONG)))
                    .withMessageContaining("would override the final method wait(long) of java.lang.Object");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("getClass", String_.TOKEN)))
                    .withMessageContaining("would override the final method getClass() of java.lang.Object");
        }

        @Test
        void anIncompatibleToStringIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("toString", PrimitiveToken.INT)))
                    .withMessageContaining("int me.supcheg.example.Probe.toString() of class me.supcheg.example.Probe"
                            + " overrides toString() of java.lang.Object, whose result is String: the return types"
                            + " are incompatible");
        }

        @Test
        void anIncompatibleEqualsAndHashCodeAreRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareVoidMethod("equals", Object_.TOKEN)))
                    .withMessageContaining("whose result is boolean");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("hashCode", PrimitiveToken.LONG)))
                    .withMessageContaining("whose result is int");
        }

        @Test
        void aStaticMethodHidingAnObjectMethodIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareStaticMethod("hashCode", PrimitiveToken.INT)))
                    .withMessageContaining("would hide the instance method hashCode() of java.lang.Object");
        }
    }

    @Nested
    class This {

        @Test
        void thisOfAnotherMemberIsRejected() {
            AtomicReference<Expr<?>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.voidMethod("a", (b, self) -> {
                            stash.set(self);
                            return b.end();
                        });
                        cb.method("b", String_.TOKEN, (b, _) -> b.return_(call(stash.get(), Object_.toString)));
                    }))
                    .withMessageContaining("the this of the body of method a is used in the body of method b, which is"
                            + " not inside that body");
        }

        @Test
        void thisOfAnotherClassIsRejected() {
            // A this typed as Expr<Object> by a wildcard escapes the brand;
            // the scope check still catches it.
            AtomicReference<Expr<?>> stash = new AtomicReference<>();
            declare(cb -> cb.voidMethod("a", (b, self) -> {
                stash.set(self);
                return b.end();
            }));

            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(),
                            ClassDesc.of("me.supcheg.example", "Other"),
                            new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.method(
                                            "b",
                                            String_.TOKEN,
                                            (b, _) -> b.return_(call(stash.get(), Object_.toString)));
                                }
                            }))
                    .withMessageContaining("the this of the body of method a is used in the body of method b");
        }

        @Test
        void thisInAFieldInitializerIsRejected() {
            AtomicReference<Expr<?>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.constructor((b, self) -> {
                            stash.set(self);
                            return b.end();
                        });
                        cb.field("f", String_.TOKEN, call(stash.get(), Object_.toString));
                    }))
                    .withMessageContaining("the this of the body of constructor is used in the initializer of field f");
        }
    }

    @Test
    void theSelfMethodTableIsUnknownWhileTheClassIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> declare(cb -> cb.self().methods()))
                .withMessageContaining("the methods of class me.supcheg.example.Probe are known only once its"
                        + " declaration is complete");
    }
}
