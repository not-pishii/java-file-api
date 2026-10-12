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
    private static void declare(Consumer<TypedClassBuilder<Object>> members) {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(members));
    }

    @Nested
    class DeclareDefine {

        @Test
        void aDeclaredButUndefinedMemberIsRejectedWhenTheDeclarationCompletes() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
                        cb.declareConstructor();
                        cb.bodies();
                    }))
                    .withMessageContaining("the declaration of class me.supcheg.example.Probe is complete, but these"
                            + " declared members are never defined: boolean me.supcheg.example.Probe.isEven(int)"
                            + " (declared at ")
                    .withMessageContaining("new me.supcheg.example.Probe() (declared at ");
        }

        @Test
        void aMemberDefinedTwiceIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var m = cb.declareVoidStaticMethod("m");
                        var bodies = cb.bodies();
                        bodies.define(m, VoidBody::end);
                        bodies.define(m, VoidBody::end);
                    }))
                    .withMessageContaining("static void me.supcheg.example.Probe.m() of class"
                            + " me.supcheg.example.Probe is already defined; each declared member is defined exactly"
                            + " once");
        }

        @Test
        void aStaticFactOfAnotherClassIsRejectedWhenDefined() {
            // A static fact carries no owner brand, so this compiles.
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var bodies = cb.bodies();
                        bodies.define(Math_.toIntExact_long, (b, _) -> b.return_(literal(0)));
                    }))
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
                                    var member1 = cb.declareVoidMethod("outer");
                                    var bodies = cb.bodies();
                                    bodies.define(member1, (b, _) -> {
                                        bodies.define(late, (x, _) -> x.end());
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
                var bodies = cb.bodies();
                bodies.define(m, VoidBody::end);
                late.set(() -> bodies.define(m, VoidBody::end));
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
                        var member2 = cb.declareStaticMethod("m", PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareMethod("m", String_.TOKEN, PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(member2, Body::return_);
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
                        cb.bodies();
                    }))
                    .withMessageContaining("clashes with the constructor new me.supcheg.example.Probe(java.lang.String)"
                            + " declared before");
        }

        @Test
        void aDuplicateFieldIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        var field1 = cb.declareField("x", PrimitiveToken.INT);
                        var field2 = cb.declareMutableField("x", String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.initialize(field1, literal(1));
                        bodies.initialize(field2, literal("x"));
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
        void aMethodDeclaredByNameWithTheSignatureOfAnInheritedOneIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("toString", String_.TOKEN)))
                    .withMessageContaining("the method java.lang.String me.supcheg.example.Probe.toString() of class"
                            + " me.supcheg.example.Probe has the signature of toString() of java.lang.Object, which"
                            + " the class inherits: a method declared by its name overrides nothing")
                    .withMessageContaining("declareOverride(fact)");
            // whatever its result, which javac would call incompatible
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("toString", PrimitiveToken.INT)))
                    .withMessageContaining("has the signature of toString() of java.lang.Object");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareVoidMethod("equals", Object_.TOKEN)))
                    .withMessageContaining("has the signature of equals(java.lang.Object) of java.lang.Object");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("hashCode", PrimitiveToken.LONG)))
                    .withMessageContaining("has the signature of hashCode() of java.lang.Object");
            // a protected method of Object too: its fact is held back for a subclass that opens it
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.declareMethod("clone", Object_.TOKEN)))
                    .withMessageContaining("has the signature of clone() of java.lang.Object");
        }

        @Test
        void anOverloadOfAnInheritedMethodIsDeclaredByName() {
            declare(cb -> {
                var own3 = cb.declareMethod("equals", PrimitiveToken.BOOLEAN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(own3, (b, _, _) -> b.return_(literal(true)));
            });
        }

        @Test
        void aMethodIsOverriddenOnce() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        var own4 = cb.declareOverride(Object_.toString);
                        var own5 = cb.declareOverride(Object_.toString);
                        var bodies = cb.bodies();
                        bodies.define(own4, (b, _) -> b.return_(literal("a")));
                        bodies.define(own5, (b, _) -> b.return_(literal("b")));
                    }))
                    .withMessageContaining(
                            "clashes with the method java.lang.String me.supcheg.example.Probe.toString()"
                                    + " declared before: both erase to toString() (JLS 8.4.2)");
        }

        @Test
        void aFinalMethodIsNotOverriddenByItsFact() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> {
                        var own6 = cb.declareOverride(Object_.notify);
                        var bodies = cb.bodies();
                        bodies.define(own6, (b, _) -> b.end());
                    }))
                    .withMessageContaining(
                            "cannot override notify() of java.lang.Object: the method is final" + " (JLS 8.4.3.3)");
        }

        @Test
        void equalsIsNotOverriddenWithoutHashCode() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var own7 = cb.declareOverride(Object_.equals_Object);
                        var bodies = cb.bodies();
                        bodies.define(own7, (b, _, _) -> b.return_(literal(true)));
                    }))
                    .withMessageContaining("overrides equals(java.lang.Object) but not hashCode()");
            declare(cb -> {
                var own8 = cb.declareOverride(Object_.equals_Object);
                var own9 = cb.declareOverride(Object_.hashCode);
                var bodies = cb.bodies();
                bodies.define(own8, (b, _, _) -> b.return_(literal(true)));
                bodies.define(own9, (b, _) -> b.return_(literal(1)));
            });
            // hashCode alone is nothing javac warns of
            declare(cb -> {
                var own10 = cb.declareOverride(Object_.hashCode);
                var bodies = cb.bodies();
                bodies.define(own10, (b, _) -> b.return_(literal(1)));
            });
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
                        var own11 = cb.declareVoidMethod("a");
                        var own12 = cb.declareMethod("b", String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(own11, (b, self) -> {
                            stash.set(self);
                            return b.end();
                        });
                        bodies.define(own12, (b, _) -> b.return_(call(stash.get(), Object_.toString)));
                    }))
                    .withMessageContaining("the this of the body of method a is used in the body of method b, which is"
                            + " not inside that body");
        }

        @Test
        void thisOfAnotherClassIsRejected() {
            // A this typed as Expr<Object> by a wildcard escapes the brand;
            // the scope check still catches it.
            AtomicReference<Expr<?>> stash = new AtomicReference<>();
            declare(cb -> {
                var own13 = cb.declareVoidMethod("a");
                var bodies = cb.bodies();
                bodies.define(own13, (b, self) -> {
                    stash.set(self);
                    return b.end();
                });
            });

            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(),
                            ClassDesc.of("me.supcheg.example", "Other"),
                            new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    var member5 = cb.declareMethod("b", String_.TOKEN);
                                    var bodies = cb.bodies();
                                    bodies.define(member5, (b, _) -> b.return_(call(stash.get(), Object_.toString)));
                                }
                            }))
                    .withMessageContaining("the this of the body of method a is used in the body of method b");
        }

        @Test
        void thisInAFieldInitializerIsRejected() {
            AtomicReference<Expr<?>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var own14 = cb.declareConstructor();
                        var field15 = cb.declareField("f", String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(own14, (b, self) -> {
                            stash.set(self);
                            return b.end();
                        });
                        bodies.initialize(field15, call(stash.get(), Object_.toString));
                    }))
                    .withMessageContaining("the this of the body of constructor is used in the initializer of field f");
        }
    }

    @Test
    void theSelfMethodTableIsUnknownWhileTheClassIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> declare(cb -> cb.self().methods()))
                .withMessageContaining("the methods of class me.supcheg.example.Probe are known only once its"
                        + " members are declared");
    }
}
