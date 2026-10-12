package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Carrier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Worded_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What a generic class is held to (§8g): the scope of its type parameters
/// (JLS 6.3, 8.1.2), their bounds (JLS 4.4), and what a generic class cannot
/// be (JLS 8.1.2) — each against javac, which the typed layer is never
/// weaker than; where it is stricter, the test says so.
class GenericClassChecksTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Class<IllegalStateException> OUT_OF_SCOPE = IllegalStateException.class;
    private static final Class<IllegalArgumentException> AT_THE_PLACE = IllegalArgumentException.class;

    private static JavaFile probe(TypedJavaFile.TypedGenericClassSpec1<Object> spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), spec);
    }

    /// What a `static` method of the generic class `Probe<T>` does with the token of `T` and the builder.
    private interface StaticBody {
        <Self, T> Terminated<Void> build(VoidBody b, TypeVarToken<T> t, TypedClassBuilder<Self> cb);
    }

    /// The class `Probe<T>` with a constructor and a `static void m()` of `body`.
    private static JavaFile withStatic(StaticBody body) {
        return probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
            @Override
            public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                var cb = h.extendsObject().members();
                var m = cb.declareVoidStaticMethod("m");
                var bodies = cb.bodies();
                bodies.define(m, b -> body.build(b, t, cb));
            }
        });
    }

    private static final String STATIC_CONTEXT =
            "non-static type variable T cannot be referenced from a static context";

    @Nested
    class StaticContext {

        @Test
        void theResultOfAStaticMethodIsNoTypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.extendsObject().members().declareStaticMethod("make", t);
                        }
                    }),
                    "the type parameter T is not in scope in the signature of static T"
                            + " me.supcheg.example.Probe<T>.make() of class me.supcheg.example.Probe: a static member"
                            + " of a generic class refers neither to a type parameter of the class nor to the class"
                            + " with its type parameters (JLS 8.1.2)",
                    "<T>",
                    "public static T make() { return null; }",
                    STATIC_CONTEXT);
        }

        @Test
        void aParameterOfAStaticMethodIsOfNoTypeThatMentionsATypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.extendsObject()
                                    .members()
                                    .declareVoidStaticMethod("take", new ArrayList_<>(ArrayToken.of(t)).token);
                        }
                    }),
                    "the type parameter T, which java.util.ArrayList<T[]> mentions, is not in scope in the signature of",
                    "<T>",
                    "public static void take(ArrayList<T[]> list) { }",
                    STATIC_CONTEXT);
        }

        @Test
        void theClassWithItsTypeParametersIsNoTypeOfAStaticMethod() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            var cb = h.extendsObject().members();
                            cb.declareStaticMethod("create", cb.self());
                            cb.bodies();
                        }
                    }),
                    "the type parameter T, which me.supcheg.example.Probe<T> mentions, is not in scope in the"
                            + " signature of",
                    "<T>",
                    "public static Probe<T> create() { return null; }",
                    STATIC_CONTEXT);
        }

        @Test
        void aLocalVariableOfAStaticMethodIsOfNoTypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> withStatic(new StaticBody() {
                        @Override
                        public <Self, T> Terminated<Void> build(
                                VoidBody b, TypeVarToken<T> t, TypedClassBuilder<Self> cb) {
                            return b.let(
                                    Object_.TOKEN,
                                    literal("x"),
                                    o -> b.let(ArrayToken.of(t), literalNull(ArrayToken.of(t)), v -> b.end()));
                        }
                    }),
                    "the type parameter T, which T[] mentions, is not in scope in the body of static void"
                            + " me.supcheg.example.Probe<T>.m() of class me.supcheg.example.Probe: a static member of"
                            + " a generic class refers neither to a type parameter of the class nor to the class"
                            + " with its type parameters (JLS 8.1.2)",
                    "<T>",
                    "public static void m() { T[] v = null; }",
                    STATIC_CONTEXT);
        }

        @Test
        void aStaticMethodMakesNoObjectOfATypeThatMentionsATypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> withStatic(new StaticBody() {
                        @Override
                        public <Self, T> Terminated<Void> build(
                                VoidBody b, TypeVarToken<T> t, TypedClassBuilder<Self> cb) {
                            return b.let(Object_.TOKEN, new_(new ArrayList_<>(t).new_), o -> b.end());
                        }
                    }),
                    "the type parameter T, which java.util.ArrayList<T> mentions, is not in scope in the body of static",
                    "<T>",
                    "public static void m() { Object o = new ArrayList<T>(); }",
                    STATIC_CONTEXT);
        }

        @Test
        void aStaticMethodMakesNoObjectOfTheClassItself() {
            // stricter than javac, which infers the type argument of a diamond there: the class has no token
            // but the one of its type parameters
            classUnlikeJavac(
                    OUT_OF_SCOPE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            var cb = h.extendsObject().members();
                            var ctor = cb.declareConstructor();
                            var m = cb.declareVoidStaticMethod("m");
                            var bodies = cb.bodies();
                            bodies.define(ctor, (b, self) -> b.end());
                            bodies.define(m, b -> b.let(Object_.TOKEN, new_(ctor), o -> b.end()));
                        }
                    }),
                    "the type parameter T, which me.supcheg.example.Probe<T> mentions, is not in scope in the body of",
                    "<T>",
                    "public Probe() { } public static void m() { Object o = new Probe<>(); }");
        }

        @Test
        void aStaticMethodCastsToNoTypeThatMentionsATypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> withStatic(new StaticBody() {
                        @Override
                        public <Self, T> Terminated<Void> build(
                                VoidBody b, TypeVarToken<T> t, TypedClassBuilder<Self> cb) {
                            var comparable = new Comparable_<>(t);
                            // the receiver of a call is cast to the owner of the method, a type of T
                            return b.let(
                                    PrimitiveToken.INT,
                                    call(literalNull(comparable.token), comparable.compareTo_T, literalNull(t)),
                                    v -> b.end());
                        }
                    }),
                    "is not in scope in the body of static void me.supcheg.example.Probe<T>.m()",
                    "<T>",
                    "public static void m() { int v = ((Comparable<T>) null).compareTo((T) null); }",
                    STATIC_CONTEXT);
        }

        @Test
        void aLambdaOfAStaticMethodIsOfNoTypeThatMentionsATypeParameter() {
            classAsJavac(
                    OUT_OF_SCOPE,
                    () -> withStatic(new StaticBody() {
                        @Override
                        public <Self, T> Terminated<Void> build(
                                VoidBody b, TypeVarToken<T> t, TypedClassBuilder<Self> cb) {
                            return b.let(
                                    Object_.TOKEN,
                                    lambda(
                                            new Supplier_<>(new ArrayList_<>(t).token).sam,
                                            () -> literalNull(new ArrayList_<>(t).token)),
                                    o -> b.end());
                        }
                    }),
                    "is not in scope in the body of static void me.supcheg.example.Probe<T>.m()",
                    "<T>",
                    "public static void m() { Object o = (java.util.function.Supplier<ArrayList<T>>) () -> null; }",
                    STATIC_CONTEXT);
        }

        @Test
        void anInstanceMemberMentionsTheTypeParametersFreely() {
            assertThat(probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                                    var cb = h.extendsObject().members();
                                    var list = new ArrayList_<>(t);
                                    var items = cb.declareField("items", list.token);
                                    var m = cb.declareVoidStaticMethod("m");
                                    var bodies = cb.bodies();
                                    bodies.initialize(items, new_(list.new_));
                                    bodies.define(m, b -> b.let(Object_.TOKEN, literal("x"), o -> b.end()));
                                }
                            })
                            .render())
                    .contains("public final ArrayList<T> items = new ArrayList<>();")
                    .contains("public static void m() {");
        }
    }

    /// A class that is not generic, with a method that has a local variable of the type variable `kept`.
    private static <X> JavaFile withALocalOf(TypeVarToken<X> kept) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                var m = cb.declareVoidMethod("m");
                var bodies = cb.bodies();
                bodies.define(m, (b, self) -> b.let(kept, literalNull(kept), v -> b.end()));
            }
        });
    }

    @Nested
    class Scope {

        @Test
        void aTypeParameterOfAnotherClassIsNotInScope() {
            // both classes have a type parameter T: the name is the same, the variable is not
            assertThatIllegalStateException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> outer) {
                            TypedJavaFile.declareClass(
                                    UnsafeFacts.unverifiedClasspath(),
                                    ClassDesc.of("me.supcheg.example", "Other"),
                                    typeParam("T", new Comparable_<>(String_.TOKEN).token),
                                    new TypedJavaFile.TypedGenericClassSpec1<>() {
                                        @Override
                                        public <Self2, T2 extends Comparable<String>> void build(
                                                ClassHeader<Self2> h2, TypeVarToken<T2> inner) {
                                            h2.extendsObject().members().declareMethod("m", outer);
                                        }
                                    });
                        }
                    }))
                    .withMessageContaining("the type parameter T is not in scope in the signature of T"
                            + " me.supcheg.example.Other<T>.m() of class me.supcheg.example.Other: it is a type"
                            + " parameter of another class, in scope in the declaration of that class alone"
                            + " (JLS 6.3)");
        }

        @Test
        void aTypeParameterIsNotInScopeInAClassDeclaredAfterItsOwn() {
            TypeVarToken<?>[] kept = new TypeVarToken<?>[1];
            probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                @Override
                public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                    kept[0] = t;
                    h.extendsObject().members().bodies();
                }
            });

            assertThatIllegalStateException()
                    .isThrownBy(() -> withALocalOf(kept[0]))
                    .withMessageContaining("the type parameter T is not in scope in the body of void"
                            + " me.supcheg.example.Probe.m() of class me.supcheg.example.Probe: it is a type"
                            + " parameter of another class");
        }

        @Test
        void aSupertypeAndABoundMentionNoTypeParameterOfAnotherClass() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> outer) {
                            TypedJavaFile.declareClass(
                                    UnsafeFacts.unverifiedClasspath(),
                                    ClassDesc.of("me.supcheg.example", "Other"),
                                    new TypedJavaFile.TypedClassHeaderSpec() {
                                        @Override
                                        public <Self2> void build(ClassHeader<Self2> h2) {
                                            h2.extends_(new Carrier_<>(outer).extend(h2.self()));
                                        }
                                    });
                        }
                    }))
                    .withMessageContaining("which me.supcheg.javafile.typed.fixtures.Carrier<T> mentions, is not in"
                            + " scope in the header of class me.supcheg.example.Other");
            assertThatIllegalStateException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> outer) {
                            TypedJavaFile.declareClass(
                                    UnsafeFacts.unverifiedClasspath(),
                                    ClassDesc.of("me.supcheg.example", "Other"),
                                    typeParam("U", new Comparable_<>(outer).token),
                                    new TypedJavaFile.TypedGenericClassSpec1<>() {
                                        @Override
                                        public <Self2, U extends Comparable<T>> void build(
                                                ClassHeader<Self2> h2, TypeVarToken<U> u) {}
                                    });
                        }
                    }))
                    .withMessageContaining("which java.lang.Comparable<T> mentions, is not in scope in the header of"
                            + " class me.supcheg.example.Other");
        }
    }

    @Nested
    class Bounds {

        @Test
        void anInterfaceIsABoundOnce() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.bound(t, Runnable_.TOKEN);
                            h.bound(t, Runnable_.TOKEN);
                        }
                    }),
                    "the type parameter T of class me.supcheg.example.Probe has the supertype java.lang.Runnable"
                            + " already",
                    "<T extends Object & Runnable & Runnable>",
                    "",
                    "repeated interface");
        }

        @Test
        void aGenericInterfaceIsNoBoundWithTwoTypeArguments() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.bound(t, new Comparable_<>(String_.TOKEN).token);
                            h.bound(t, new Comparable_<>(Integer_.TOKEN).token);
                        }
                    }),
                    "has the supertype java.lang.Comparable already",
                    "<T extends Object & Comparable<String> & Comparable<Integer>>",
                    "",
                    "repeated interface");
        }

        @Test
        void theMethodsOfTheBoundsDoNotClash() {
            // Integer get() of Numbered, CharSequence get() of Worded
            classAsJavac(
                    AT_THE_PLACE,
                    () -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam("T", Numbered_.TOKEN),
                            new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T extends me.supcheg.javafile.typed.fixtures.Numbered> void build(
                                        ClassHeader<Self> h, TypeVarToken<T> t) {
                                    h.bound(t, Worded_.TOKEN);
                                }
                            }),
                    "the type parameter T of class me.supcheg.example.Probe cannot have the bound"
                            + " me.supcheg.javafile.typed.fixtures.Worded: with it the bounds have the methods",
                    "<T extends Numbered & Worded>",
                    "",
                    "both define get(), but with unrelated return types");
        }

        @Test
        void theMethodsOfTheBoundsOfOneSignatureHaveOneResult() {
            // stricter than javac, which takes CharSequence get() of Worded for Object get() of Origin
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam(
                                    "T",
                                    me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_
                                            .TOKEN),
                            new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T extends me.supcheg.javafile.typed.fixtures.Origin> void build(
                                        ClassHeader<Self> h, TypeVarToken<T> t) {
                                    h.bound(t, Worded_.TOKEN);
                                }
                            }),
                    "of one signature and of different results",
                    "<T extends Origin & Worded>",
                    "");
        }

        @Test
        void aBoundIsNoRawType() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.bound(t, UnsafeFacts.<Comparable<?>>interfaceToken(Comparable_.Data.SHAPE));
                        }
                    }))
                    .withMessageContaining("the type parameter T of class me.supcheg.example.Probe cannot have the raw"
                            + " bound java.lang.Comparable");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam("T", UnsafeFacts.<Comparable<?>>interfaceToken(Comparable_.Data.SHAPE)),
                            new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T extends Comparable<?>> void build(
                                        ClassHeader<Self> h, TypeVarToken<T> t) {}
                            }))
                    .withMessageContaining("cannot have the raw bound java.lang.Comparable");
        }

        @Test
        void aFurtherBoundHasNoWildcardAndStandsBesideALeftmostBoundOfAKnownHeritage() {
            // stricter than javac: what the bounds give together is told by their heritage
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.bound(t, Comparable_.ANY);
                        }
                    }),
                    "a supertype has no wildcard type argument",
                    "<T extends Object & Comparable<?>>",
                    "");
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam("T", String_.TOKEN),
                            new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T extends String> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                                    h.bound(t, Runnable_.TOKEN);
                                }
                            }),
                    "cannot have the bound java.lang.Runnable beside java.lang.String: what the two give the type"
                            + " variable together is not known",
                    "<T extends String & Runnable>",
                    "");
        }

        @Test
        void aBoundIsSaidBeforeTheSuperclassAndOfATypeParameterOfTheClass() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.extendsObject();
                            h.bound(t, Runnable_.TOKEN);
                        }
                    }))
                    .withMessageContaining("has its superclass already");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> outer) {
                            TypedJavaFile.declareClass(
                                    UnsafeFacts.unverifiedClasspath(),
                                    ClassDesc.of("me.supcheg.example", "Other"),
                                    typeParam("T"),
                                    new TypedJavaFile.TypedGenericClassSpec1<>() {
                                        @Override
                                        public <Self2, T2> void build(ClassHeader<Self2> h2, TypeVarToken<T2> inner) {
                                            h2.bound(outer, Runnable_.TOKEN);
                                        }
                                    });
                        }
                    }))
                    .withMessageContaining("T is no type parameter of class me.supcheg.example.Other");
        }

        @Test
        void aTypeParameterHasANameOfItsOwn() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam("T"),
                            typeParam("T"),
                            new TypedJavaFile.TypedGenericClassSpec2<>() {
                                @Override
                                public <Self, T1, T2> void build(
                                        ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {}
                            }),
                    "class me.supcheg.example.Probe has two type parameters T",
                    "<T, T>",
                    "",
                    "T is already defined");
            // stricter than javac, where the name then means the type parameter in the class
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(),
                            DESC,
                            typeParam("Probe"),
                            new TypedJavaFile.TypedGenericClassSpec1<>() {
                                @Override
                                public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {}
                            }),
                    "class me.supcheg.example.Probe cannot have the type parameter Probe",
                    "<Probe>",
                    "");
            assertThatIllegalArgumentException().isThrownBy(() -> typeParam("not a name"));
        }
    }

    @Nested
    class NotExpressible {

        @Test
        void aGenericClassIsNoThrowable() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            h.extends_(Exception_.extend(h.self()));
                        }
                    }),
                    "class me.supcheg.example.Probe cannot extend java.lang.Exception: a generic class is no"
                            + " subclass of Throwable (JLS 8.1.2)",
                    "<T> extends Exception",
                    "",
                    "a generic class may not extend java.lang.Throwable");
        }

        @Test
        void noArrayOfATypeParameterIsCreated() {
            JavacVerdict.asJavac(
                    AT_THE_PLACE,
                    () -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            newArray(ArrayToken.of(t), literal(1));
                        }
                    }),
                    "newArray needs a reifiable type (JLS 4.7), but T is not",
                    "<T> void m() { Object o = new T[1]; }",
                    "generic array creation");
        }

        @Test
        void nothingIsCastToATypeParameterOrToTheClassWithThem() {
            // javac accepts the casts with an unchecked warning: the typed layer renders no code javac warns of
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            castChecked(t, literalNull(Object_.TOKEN));
                        }
                    }))
                    .withMessageContaining("would be an unchecked cast (JLS 5.1.6.2)");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> probe(new TypedJavaFile.TypedGenericClassSpec1<>() {
                        @Override
                        public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                            castChecked(h.self(), literalNull(Object_.TOKEN));
                        }
                    }))
                    .withMessageContaining("would be an unchecked cast (JLS 5.1.6.2)");
            JavacVerdict.javacWarns("<T> void m(Object o) { T t = (T) o; }", "unchecked cast");
        }
    }
}
