package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Named;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.BaseTwin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Following_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Op_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.SubTwin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Titled_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Arrays;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/// What is wrong with a class is rejected by the call that says it (phase-1
/// review, item 10): the members of a class are declared by one stage and
/// defined by the next, so a body is lowered where it is given, and what can
/// be judged of the declarations is judged where they end.
///
/// Each test catches the rejection around the one call that is wrong — the
/// call before it and the call after it are not in the `catchThrowable` —
/// and then says the thing rightly: the declaration completes, so nothing of
/// what was rejected is left for the end to find. Where a test asks for the
/// frame, the stack of the rejection is that of the line of the generator.
///
/// What only the end of the declaration can tell ([Completion]) is rejected
/// there, and names the line that said the thing.
class WhereItIsSaidTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final String OP = "me.supcheg.javafile.typed.fixtures.Op";

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    /// The line this is called at.
    private static int line() {
        return StackWalker.getInstance()
                .walk(frames -> frames.skip(1).findFirst().orElseThrow())
                .getLineNumber();
    }

    /// The innermost frame of this test a rejection was thrown through: the line of the generator.
    private static StackTraceElement generator(Throwable rejection) {
        return Arrays.stream(rejection.getStackTrace())
                .filter(frame -> frame.getClassName().startsWith(WhereItIsSaidTest.class.getName()))
                .findFirst()
                .orElseThrow();
    }

    /// Whether a frame of the completion of a declaration is on the stack of a rejection.
    private static boolean atCompletion(Throwable rejection) {
        return Arrays.stream(rejection.getStackTrace())
                .anyMatch(frame -> frame.getClassName().equals(ClassDeclaration.class.getName())
                        && frame.getMethodName().equals("complete"));
    }

    @Nested
    class Bodies {

        @Test
        void aProtectedMemberReachedOutOfASubclassIsRejectedByTheDefinition() {
            JavaFile file = file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    var motto = cb.declareMethod("motto", String_.TOKEN);
                    var bodies = cb.bodies();

                    int before = line();
                    Throwable rejection = catchThrowable(() -> bodies.define(motto, WhereItIsSaidTest::protectedMotto));

                    assertThat(rejection)
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("is protected: it is reached in the body of a subclass of");
                    assertThat(generator(rejection).getLineNumber()).isEqualTo(before + 1);
                    assertThat(atCompletion(rejection)).isFalse();
                    // the member has no body yet, and gets one
                    bodies.define(motto, (b, self) -> b.return_(literal("mine")));
                }
            });

            assertThat(file.render()).contains("return \"mine\";");
        }

        @Test
        void aProtectedMethodCalledOnAnotherValueIsRejectedByTheDefinition() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Base_.extend(h.self()));
                    var cb = sh.members();
                    var ctor = cb.declareConstructor();
                    var hook = cb.protected_(Base_.hook_String);
                    var other = cb.declareMethod("other", String_.TOKEN, Base_.TOKEN);
                    var bodies = cb.bodies(ctor, c -> c.super_(Base_.new_, (b, self) -> b.end()));

                    assertThat(catchThrowable(() ->
                                    bodies.define(other, (b, self, base) -> b.return_(call(base, hook, literal("x"))))))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("is protected, and is called on an expression of"
                                    + " me.supcheg.javafile.typed.fixtures.Base");
                    bodies.define(
                            other,
                            (b, self, base) -> b.return_(call(bodies.asSuper().apply(self), hook, literal("x"))));
                }
            });
        }

        @Test
        void anOverloadNoArgumentTellsFromTheOneMeantIsRejectedByTheDefinition() {
            BaseTwin_<String> base = new BaseTwin_<>(String_.TOKEN);
            SubTwin_<String> sub = new SubTwin_<>(String_.TOKEN);

            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(sub.extend(h.self()));
                    var cb = sh.members();
                    var ctor = cb.declareConstructor();
                    var m = cb.declareOverride(base.m_T);
                    var p = cb.declareOverride(cb.protected_(base.p_T));
                    var f = cb.declareMethod("f", String_.TOKEN);
                    var bodies = cb.bodies(ctor, c -> c.super_(sub.new_, (b, self) -> b.end()));
                    bodies.define(m, (b, self, v) -> b.return_(concat(literal("mine:"), v)));
                    bodies.define(p, (b, self, v) -> b.return_(concat(literal("mine:"), v)));

                    // m(String) of SubTwin<String> beside the m(T) it has from BaseTwin<String>
                    assertThat(catchThrowable(() -> bodies.define(
                                    f, (b, self) -> b.return_(bodies.superCall(self, sub.m_String, literal("x"))))))
                            .isInstanceOf(FactLookupException.class)
                            .hasMessageContaining("as the overload declared m(#0) is");
                    assertThat(catchThrowable(() -> bodies.define(
                                    f, (b, self) -> b.return_(bodies.superCall(self, base.m_T, literal("x"))))))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("super.m(...) cannot name");
                    bodies.define(f, (b, self) -> b.return_(call(self, m, literal("x"))));
                }
            });
        }

        @Test
        void aTypeParameterOutOfItsScopeIsRejectedByTheDefinition() {
            TypedJavaFile.declareClass(
                    UnsafeFacts.unverifiedClasspath(),
                    DESC,
                    typeParam("T"),
                    new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            var cb = h.extendsObject().members();
                            var m = cb.declareVoidStaticMethod("m");
                            var bodies = cb.bodies();

                            assertThat(catchThrowable(() -> bodies.define(
                                            m,
                                            b -> b.let(ArrayToken.of(t), literalNull(ArrayToken.of(t)), v -> b.end()))))
                                    .isInstanceOf(IllegalStateException.class)
                                    .hasMessageContaining("the type parameter T, which T[] mentions, is not in scope"
                                            + " in the body of static void me.supcheg.example.Probe<T>.m()");
                            bodies.define(m, VoidBody::end);
                        }
                    });
        }

        @Test
        void aNameTheFileCannotWriteIsRejectedByTheDefinitionThatBringsIt() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    // the field Op makes the enum Op be written by its qualified name, which begins with me
                    var op = cb.declareField("Op", PrimitiveToken.INT);
                    var me = cb.declareField("me", PrimitiveToken.INT);
                    var which = cb.declareMethod("which", Op_.TOKEN);
                    var other = cb.declareMethod("other", PrimitiveToken.INT);
                    // the declarations name no type before a member: they end
                    var bodies = cb.bodies();
                    bodies.initialize(op, literal(1));
                    bodies.initialize(me, literal(2));
                    bodies.define(other, (b, self) -> b.return_(literal(3)));

                    assertThat(catchThrowable(
                                    () -> bodies.define(which, (b, self) -> b.return_(enumConstant(Op_.SUB)))))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("the body of method which of class me.supcheg.example.Probe cannot be"
                                    + " written: cannot name " + OP + " before a member in a file that declares the"
                                    + " variable me");
                    bodies.define(which, (b, self) -> b.return_(literalNull(Op_.TOKEN)));
                }
            });
        }

        @Test
        void aNameTheFileCannotWriteIsRejectedByTheInitializerThatBringsIt() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    var op = cb.declareField("Op", PrimitiveToken.INT);
                    var me = cb.declareField("me", Op_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.initialize(op, literal(1));

                    assertThat(catchThrowable(() -> bodies.initialize(me, enumConstant(Op_.SUB))))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("the initializer of field me of class me.supcheg.example.Probe"
                                    + " cannot be written");
                    bodies.initialize(me, literalNull(Op_.TOKEN));
                }
            });
        }

        @Test
        void aBodyIsLoweredAgainstTheMethodsDeclaredAfterItsOwn() {
            // pick(String) is declared after pick(Object) and after the method that calls it: the call is
            // lowered where it is defined, and by then the class has both
            JavaFile file = file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    var picked = cb.declareMethod("picked", String_.TOKEN);
                    var ofObject = cb.declareMethod("pick", String_.TOKEN, Object_.TOKEN);
                    var ofString = cb.declareMethod("pick", String_.TOKEN, String_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(picked, (b, self) -> b.return_(call(self, ofObject, literal("x"))));
                    bodies.define(ofObject, (b, self, o) -> b.return_(literal("Object")));
                    bodies.define(ofString, (b, self, s) -> b.return_(literal("String")));
                }
            });

            assertThat(file.render()).contains("return this.pick((Object) \"x\");");
        }
    }

    @Nested
    class Declarations {

        @Test
        void equalsWithoutHashCodeIsRejectedWhereTheDeclarationsEnd() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    var equal = cb.declareOverride(Object_.equals_Object);

                    int before = line();
                    Throwable rejection = catchThrowable(cb::bodies);

                    assertThat(rejection)
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("the declarations of class me.supcheg.example.Probe cannot end here:"
                                    + " it overrides equals(java.lang.Object) but not hashCode()");
                    assertThat(generator(rejection).getLineNumber()).isEqualTo(before + 1);
                    assertThat(atCompletion(rejection)).isFalse();
                    // the declarations have not ended: the other override is declared, and they do
                    var hash = cb.declareOverride(Object_.hashCode);
                    var bodies = cb.bodies();
                    bodies.define(equal, (b, self, other) -> b.return_(literal(true)));
                    bodies.define(hash, (b, self) -> b.return_(literal(1)));
                }
            });
        }

        @Test
        void methodsTheClassCannotHaveTogetherAreRejectedWhereTheDeclarationsEnd() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var oh = h.extendsObject();
                    Upcast<Self, Named> asNamed = oh.implements_(Named_.implement(h.self()));
                    oh.implements_(Titled_.implement(h.self()));
                    var cb = oh.members();

                    assertThat(catchThrowable(cb::bodies))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("overrides none of them")
                            .hasMessageContaining("are unrelated methods with a body each");
                    var label = cb.declareOverride(asNamed, Named_.label);
                    cb.bodies().define(label, (b, self) -> b.return_(literal("mine")));
                }
            });
        }

        @Test
        void aSupertypeTheClassSaidItIsAboutToNameIsHeldWhereTheHeaderEnds() {
            Throwable atTheEnd = catchThrowable(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    h.becoming(Following_.TOKEN);
                    var run = h.overriding(Runnable_.run);
                    var oh = h.extendsObject();
                    oh.implements_(Runnable_.implement(h.self()).run(run));

                    Throwable rejection = catchThrowable(oh::members);

                    assertThat(rejection)
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessageContaining("said it was about to be me.supcheg.javafile.typed.fixtures.Following"
                                    + " (becoming), and its header ends without it");
                    assertThat(atCompletion(rejection)).isFalse();
                }
            }));

            // the header did not end, which is all the end of the declaration has to say
            assertThat(atTheEnd)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("never came to the members");
        }

        @Test
        void aMemberIsNotDeclaredOnceTheBodiesBegan() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    cb.bodies();

                    assertThat(catchThrowable(() -> cb.declareMethod("late", PrimitiveToken.INT)))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("cannot declare int me.supcheg.example.Probe.late(): the declarations"
                                    + " of class me.supcheg.example.Probe have ended");
                    assertThat(catchThrowable(() -> cb.declareField("late", PrimitiveToken.INT)))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("cannot declare field late of class me.supcheg.example.Probe");
                    assertThat(catchThrowable(cb::bodies))
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("the declarations of class me.supcheg.example.Probe have ended");
                }
            });
        }
    }

    /// What no call before the end of the declaration can tell: each rejection names the line
    /// that said the thing it is about, in its message and by the stack of its cause.
    @Nested
    class Completion {

        @Test
        void aMemberDeclaredAndNeverDefinedIsRejectedWithTheLineThatDeclaredIt() {
            int[] declared = new int[2];

            Throwable rejection = catchThrowable(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    declared[0] = line();
                    cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
                    declared[1] = line();
                    cb.declareField("count", PrimitiveToken.INT);
                    var defined = cb.declareVoidMethod("defined");
                    cb.bodies().define(defined, (b, self) -> b.end());
                }
            }));

            assertThat(rejection)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("the declaration of class me.supcheg.example.Probe is complete, but these"
                            + " declared members are never defined: boolean me.supcheg.example.Probe.isEven(int)"
                            + " (declared at " + WhereItIsSaidTest.class.getName())
                    .hasMessageContaining("WhereItIsSaidTest.java:" + (declared[0] + 1) + ")")
                    .hasMessageContaining("field int me.supcheg.example.Probe.count (declared at ")
                    .hasMessageContaining("WhereItIsSaidTest.java:" + (declared[1] + 1) + ")")
                    .hasMessageNotContaining("defined()");
            assertThat(atCompletion(rejection)).isTrue();
            // the stack of the cause is the line that declared the first of them, of the next a suppressed one
            assertThat(rejection.getCause())
                    .hasMessage("boolean me.supcheg.example.Probe.isEven(int) was declared here");
            assertThat(rejection.getCause().getStackTrace()).hasSize(1);
            assertThat(rejection.getCause().getStackTrace()[0].getLineNumber()).isEqualTo(declared[0] + 1);
            assertThat(rejection.getSuppressed()).hasSize(1);
            assertThat(rejection.getSuppressed()[0].getStackTrace()[0].getLineNumber())
                    .isEqualTo(declared[1] + 1);
        }

        @Test
        void anOverrideSaidInTheHeaderAndNeverDefinedIsRejectedWithTheLineOfTheHeader() {
            int[] said = new int[1];

            Throwable rejection = catchThrowable(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    said[0] = line();
                    var run = h.overriding(Runnable_.run);
                    var oh = h.extendsObject();
                    oh.implements_(Runnable_.implement(h.self()).run(run));
                    oh.members().bodies();
                }
            }));

            assertThat(rejection)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(
                            "these declared members are never defined: void me.supcheg.example.Probe.run()"
                                    + " (declared at ")
                    .hasMessageContaining("WhereItIsSaidTest.java:" + (said[0] + 1) + ")");
        }

        @Test
        void membersThatNeverComeToTheirBodiesAreRejectedWithTheLineTheyBeganAt() {
            int[] began = new int[1];

            Throwable rejection = catchThrowable(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var oh = h.extendsObject();
                    began[0] = line();
                    oh.members();
                }
            }));

            assertThat(rejection)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("its members, which began at ")
                    .hasMessageContaining("WhereItIsSaidTest.java:" + (began[0] + 1) + ")")
                    .hasMessageContaining("never came to their bodies: end the declarations with bodies()");
            assertThat(rejection.getCause().getStackTrace()[0].getLineNumber()).isEqualTo(began[0] + 1);
        }

        @Test
        void aHeaderThatNeverComesToTheMembersIsRejectedWithTheLineThatNamedTheSuperclass() {
            int[] named = new int[1];

            Throwable rejection = catchThrowable(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    named[0] = line();
                    h.extendsObject();
                }
            }));

            assertThat(rejection)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("its header, which named the superclass at ")
                    .hasMessageContaining("WhereItIsSaidTest.java:" + (named[0] + 1) + ")")
                    .hasMessageContaining("never came to the members");
        }
    }

    /// A body of the first test: `Base.motto` is a `protected static` method, and the class is no subclass.
    private static <Self> Terminated<String> protectedMotto(Body<String> b, This<Self> self) {
        return b.return_(staticCall(UnsafeFacts.open(Base_.motto)));
    }
}
