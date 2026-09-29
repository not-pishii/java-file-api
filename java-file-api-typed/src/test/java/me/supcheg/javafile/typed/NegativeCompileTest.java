package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/// Negative compile tests (§11): fixtures of invalid typed-API *usage* — not
/// invalid generated code — that must fail to compile as generator source,
/// each with the one javac error that proves the guarantee, not just any
/// failure. These guard the guarantee (§1) against a combinator's signature
/// accidentally widening enough to admit misuse; if any of these starts
/// compiling, the guarantee has a hole.
///
/// Each fixture is generator code compiled with the typed/facts/core module
/// classes on the classpath; [#controlFixtureCompiles()] compiles a valid
/// fixture on the same harness, so a broken classpath cannot make the
/// negative tests pass.
class NegativeCompileTest {

    /// The fixtures import whole packages; the on-demand imports are
    /// assembled so that the wildcard lint of this source does not trip.
    private static final String IMPORTS = "package fixtures;\n"
            + "import java.lang.constant.ClassDesc;\n"
            + "import java.util.List;\n"
            + "import me.supcheg.javafile.type.Types;\n"
            + Stream.of(
                            "me.supcheg.javafile.facts",
                            "me.supcheg.javafile.facts.jdk",
                            "me.supcheg.javafile.typed",
                            "static me.supcheg.javafile.typed.Expressions")
                    .map(on -> "import " + on + ".*;\n")
                    .collect(Collectors.joining());

    private static Compilation compile(String simpleName, String body) {
        List<File> classpath = Arrays.stream(
                        System.getProperty("java.class.path").split(File.pathSeparator))
                .map(File::new)
                .toList();
        String source = IMPORTS + "\nclass " + simpleName + " {\n" + body + "\n}\n";
        return javac().withClasspath(classpath)
                .compile(JavaFileObjects.forSourceString("fixtures." + simpleName, source));
    }

    private static void assertRejected(String simpleName, String body, String... expectedErrors) {
        Compilation compilation = compile(simpleName, body);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        for (String expectedError : expectedErrors) {
            assertThat(compilation).hadErrorContaining(expectedError);
        }
    }

    /// A method body builder of a generated class, for fixtures that need a
    /// block: `%s` is the statement using `cb`.
    private static String inClass(String statement) {
        return """
                void use() {
                    TypedJavaFile.class_(ClassDesc.of("fixtures", "Generated"), new TypedJavaFile.TypedClassSpec() {
                        public <Self> void build(TypedClassBuilder<Self> cb) {
                            %s
                        }
                    });
                }
                """.formatted(statement);
    }

    @Test
    void controlFixtureCompiles() {
        String body = inClass("""
                cb.method("length", PrimitiveToken.INT, PrimitiveToken.INT.boxed(), (b, self, boxed) -> b.let(
                        PrimitiveToken.INT, unbox(PrimitiveToken.INT, boxed), i -> b.if_(
                                ltInt(i, call(literal("abc"), String_.length)),
                                t -> t.exec(call(literal("abc"), String_.charAt, i)))
                        .return_(addInt(i, literal(1)))));
                cb.method("boxed", Integer_.TOKEN, (b, self) -> b.return_(box(PrimitiveToken.INT, literal(1))));
                cb.method("empty", String_.TOKEN, (b, self) -> b.return_(literalNull(String_.TOKEN)));
                cb.method("same", PrimitiveToken.BOOLEAN, (b, self) -> b.return_(eqRef(literal("a"), literal("b"))));
                new_(Object_.new_);
                """);

        assertThat(compile("Control", body)).succeeded();
    }

    @Test
    void wrongArgumentTypePassedToCall() {
        assertRejected(
                "WrongArgumentType",
                "void use() { call(literal(\"hi\"), String_.charAt, literal(\"not an int\")); }",
                "no suitable method found for call");
    }

    @Test
    void methodBodyWithoutReturnDoesNotCompile() {
        assertRejected(
                "MissingReturn",
                inClass("cb.method(\"compute\", PrimitiveToken.INT, (body, self) -> { });"),
                "bad return type in lambda expression",
                "missing return value");
    }

    @Test
    void implicitBoxingInReturnDoesNotCompile() {
        assertRejected(
                "ImplicitBoxing",
                inClass("cb.method(\"boxed\", Integer_.TOKEN, (b, self) -> b.return_(literal(1)));"),
                "cannot be converted to me.supcheg.javafile.typed.Expr<? extends java.lang.Integer>");
    }

    @Test
    void implicitUnboxingInReturnDoesNotCompile() {
        assertRejected(
                "ImplicitUnboxing",
                inClass("cb.method(\"unboxed\", PrimitiveToken.INT,"
                        + " (b, self) -> b.return_(box(PrimitiveToken.INT, literal(1))));"),
                "inference variable B has incompatible bounds",
                "upper bounds: me.supcheg.javafile.facts.Prim.Int");
    }

    @Test
    void implicitUnboxingOfAnArgumentDoesNotCompile() {
        assertRejected(
                "ImplicitUnboxingArgument",
                "void use() { call(literal(\"hi\"), String_.charAt, box(PrimitiveToken.INT, literal(0))); }",
                "no suitable method found for call");
    }

    @Test
    void boxedConditionDoesNotCompile() {
        assertRejected(
                "BoxedCondition",
                inClass(
                        "cb.voidMethod(\"m\", (b, self) -> b.if_(box(PrimitiveToken.BOOLEAN, literal(true)), t -> {}).end());"),
                "incompatible equality constraints me.supcheg.javafile.facts.Prim.Bool,java.lang.Boolean");
    }

    @Test
    void nullOfAPrimitiveTypeDoesNotCompile() {
        assertRejected(
                "PrimitiveNull",
                "void use() { literalNull(PrimitiveToken.INT); }",
                "cannot be converted to me.supcheg.javafile.facts.RefToken<T>");
    }

    @Test
    void newOfAnAbstractClassDoesNotCompile() {
        // The constructor fact of an abstract class is an AbstractCtorRef0,
        // which new_ does not take; it exists only for a subclass constructor.
        assertRejected(
                "NewAbstract",
                """
                void use(AbstractCtorRef0<Number> numberCtor) {
                    new_(numberCtor);
                }
                """,
                "no suitable method found for new_(me.supcheg.javafile.facts.AbstractCtorRef0<java.lang.Number>)");
    }

    @Test
    void forgingAMethodFactDoesNotCompile() {
        assertRejected(
                "ForgedMethod",
                """
                void use() {
                    new MethodRef0<String, String>(
                            String_.TOKEN, "nonexistent", String_.TOKEN, MemberTraits.DEFAULT);
                }
                """,
                "is not public in me.supcheg.javafile.facts.MethodRef0; cannot be accessed from outside package");
    }

    @Test
    void theFormerIntroduceFactoryIsGone() {
        assertRejected("ForgedByIntroduce", """
                void use() {
                    MethodRef0.introduce(String_.TOKEN, "nonexistent", String_.TOKEN, MemberTraits.DEFAULT);
                }
                """, "cannot find symbol");
    }

    @Test
    void forgingATokenDoesNotCompile() {
        assertRejected(
                "ForgedToken",
                """
                void use() {
                    new FinalClassToken<Integer>(null, Types.STRING, List.of());
                }
                """,
                "is not public in me.supcheg.javafile.facts.FinalClassToken; cannot be accessed from outside package");
    }

    @Test
    void forgingAFactSourceDoesNotCompile() {
        assertRejected(
                "ForgedSource",
                """
                void use() {
                    new FactSource<String>(String_.TOKEN, query -> null);
                }
                """,
                "is not public in me.supcheg.javafile.facts.FactSource; cannot be accessed from outside package");
    }

    @Test
    void subclassingAFactSourceDoesNotCompile() {
        // A subclass is rejected twice over: FactSource is final, and its
        // only constructor is package-private.
        Compilation compilation = compile("SubclassedSource", """
                abstract static class Forged extends FactSource<String> {}
                """);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(2);
        assertThat(compilation).hadErrorContaining("cannot inherit from final me.supcheg.javafile.facts.FactSource");
    }

    @Test
    void loopCtlHasNoPublicConstructor() {
        // break_/continue_ take a LoopCtl, obtainable only as a parameter of
        // a loop body; a stray break outside a loop cannot be assembled.
        assertRejected(
                "StrayLoopCtl",
                "void use() { new LoopCtl(null); }",
                "LoopCtl(me.supcheg.javafile.typed.Block<?,?>) is not public in me.supcheg.javafile.typed.LoopCtl");
    }

    @Test
    void varHasNoPublicConstructor() {
        // Var/MutVar are only ever handed out by HOAS-binding combinators.
        assertRejected(
                "StrayVar",
                "void use() { new Var<>(PrimitiveToken.INT, \"fabricated\", null); }",
                "is not public in me.supcheg.javafile.typed.Var");
    }

    @Test
    void loopForeverGivesNoBreakOfItsOwnLoop() {
        // The body of loopForever gets no LoopCtl, so the loop provably never
        // completes normally and loopForever may return Terminated.
        assertRejected(
                "BreakOutOfForever",
                inClass(
                        "cb.method(\"m\", PrimitiveToken.INT, (b, self) -> b.loopForever((loop, ctl) -> loop.break_(ctl)));"),
                "incompatible parameter types in lambda expression");
    }

    @Test
    void nothingFollowsLoopForever() {
        assertRejected(
                "AfterForever",
                inClass("cb.method(\"m\", PrimitiveToken.INT,"
                        + " (b, self) -> b.loopForever(loop -> {}).return_(literal(1)));"),
                "cannot find symbol",
                "symbol:   method return_(me.supcheg.javafile.typed.Expr<me.supcheg.javafile.facts.Prim.Int>)");
    }

    @Test
    void tryTerminatedRequiresAnEndingTryBlock() {
        assertRejected(
                "TryBlockNotEnding",
                inClass("cb.method(\"m\", PrimitiveToken.INT, (b, self) -> b.tryTerminated("
                        + "t -> t.exec(call(literal(\"x\"), String_.length)),"
                        + " h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.return_(literal(1)))));"),
                "bad return type in lambda expression");
    }

    @Test
    void tryTerminatedRequiresEndingCatchBlocks() {
        assertRejected(
                "CatchNotEnding",
                inClass("cb.method(\"m\", PrimitiveToken.INT, (b, self) -> b.tryTerminated("
                        + "t -> t.return_(literal(1)),"
                        + " h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.exec(call(literal(\"x\"),"
                        + " String_.length)))));"),
                "bad return type in lambda expression");
    }

    @Test
    void thereIsNoThisFactory() {
        // this is handed to an instance body as a parameter; it cannot be
        // made from a token, of another class or in a static context.
        assertRejected(
                "ThisFromToken",
                inClass("cb.staticMethod(\"m\", String_.TOKEN,"
                        + " b -> b.return_(call(this_(String_.TOKEN), Object_.toString)));"),
                "cannot find symbol",
                "method this_(me.supcheg.javafile.facts.FinalClassToken<java.lang.String>)");
    }

    @Test
    void aStaticMethodBodyGetsNoThis() {
        assertRejected(
                "StaticThis",
                inClass("cb.staticMethod(\"m\", PrimitiveToken.INT, (b, self) -> b.return_(literal(1)));"),
                "incompatible parameter types in lambda expression");
    }

    @Test
    void theThisOfOneClassIsUnusableInAnotherClass() {
        assertRejected(
                "ForeignThis",
                """
                void use() {
                    TypedJavaFile.class_(ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var x = a.field("x", PrimitiveToken.INT, literal(1));
                            TypedJavaFile.class_(ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
                                public <B> void build(TypedClassBuilder<B> b) {
                                    b.method("m", PrimitiveToken.INT, (body, self) -> body.return_(field(self, x)));
                                }
                            });
                        }
                    });
                }
                """,
                "method field in class me.supcheg.javafile.typed.Expressions cannot be applied to given types",
                "inference variable O has incompatible bounds",
                "equality constraints: A",
                "lower bounds: B");
    }

    @Test
    void aMemberOfAnotherClassCannotBeDefined() {
        assertRejected(
                "ForeignDefine",
                """
                void use() {
                    TypedJavaFile.class_(ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var m = a.declareMethod("m", PrimitiveToken.INT);
                            TypedJavaFile.class_(ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
                                public <B> void build(TypedClassBuilder<B> b) {
                                    b.define(m, (body, self) -> body.return_(literal(1)));
                                }
                            });
                        }
                    });
                }
                """,
                "no suitable method found for define(me.supcheg.javafile.facts.MethodRef0<A,"
                        + "me.supcheg.javafile.facts.Prim.Int>",
                "me.supcheg.javafile.facts.MethodRef0<A,me.supcheg.javafile.facts.Prim.Int> cannot be converted to"
                        + " me.supcheg.javafile.facts.MethodRef0<B,R>");
    }

    @Test
    void aFactOfAnExistingClassCannotBeDefined() {
        assertRejected(
                "JdkDefine",
                inClass("cb.define(Object_.toString, (b, self) -> b.return_(literal(\"x\")));"),
                "no suitable method found for define(me.supcheg.javafile.facts.MethodRef0<java.lang.Object,"
                        + "java.lang.String>");
    }

    @Test
    void anImmutableFieldCannotBeAssigned() {
        assertRejected(
                "AssignFinalField",
                inClass("""
                        var x = cb.field("x", PrimitiveToken.INT, literal(1));
                        cb.constructor((b, self) -> b.exec(assignField(self, x, literal(2))).end());
                        """),
                "method assignField in class me.supcheg.javafile.typed.Expressions cannot be applied to given types",
                "me.supcheg.javafile.facts.FieldRef<Self,me.supcheg.javafile.facts.Prim.Int> cannot be converted to"
                        + " me.supcheg.javafile.facts.MutableFieldRef<O,T>");
    }

    @Test
    void newDiamondIsNotPartOfTheApi() {
        // B12: lowering places the diamond itself, where the target type is the constructed one
        assertRejected("NewDiamond", "void use() { newDiamond(Object_.new_); }", "cannot find symbol", "newDiamond");
    }

    @Test
    void aCastToAPrimitiveTypeDoesNotCompile() {
        // B13, the static half: a cast or pattern type is a reference type
        assertRejected(
                "CastToPrimitive",
                "void use() { castChecked(PrimitiveToken.INT, literalNull(Object_.TOKEN)); }",
                "method castChecked in class me.supcheg.javafile.typed.Expressions cannot be applied to given types",
                "cannot be converted to me.supcheg.javafile.facts.RefToken<T>");
    }

    @Test
    void aPrimitiveCondWithReferenceBranchesDoesNotCompile() {
        // M2, the static half: a primitive conditional takes branches of exactly its type
        assertRejected(
                "PrimitiveCond",
                "void use() { cond(literal(true), literal(\"a\"), literal(\"b\"), PrimitiveToken.INT); }",
                "method cond in class me.supcheg.javafile.typed.Expressions cannot be applied to given types",
                "inference variable T has incompatible bounds");
    }
}
