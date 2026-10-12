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
                            "me.supcheg.javafile.typed.testfacts.java.lang",
                            "me.supcheg.javafile.typed.testfacts.java.util",
                            "me.supcheg.javafile.typed.testfacts.java.util.function",
                            "me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures",
                            "me.supcheg.javafile.typed.fixtures",
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
                    TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "Generated"), new TypedJavaFile.TypedClassSpec() {
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
                cb.bodies().define(cb.declareMethod("length", PrimitiveToken.INT, PrimitiveToken.INT.boxed()), (b, self, boxed) -> b.let(
                        PrimitiveToken.INT, unbox(PrimitiveToken.INT, boxed), i -> b.if_(
                                ltInt(i, call(literal("abc"), String_.length)),
                                t -> t.exec(call(literal("abc"), String_.charAt_int, i)))
                        .return_(addInt(i, literal(1)))));
                cb.bodies().define(cb.declareMethod("boxed", Integer_.TOKEN), (b, self) -> b.return_(box(PrimitiveToken.INT, literal(1))));
                cb.bodies().define(cb.declareMethod("empty", String_.TOKEN), (b, self) -> b.return_(literalNull(String_.TOKEN)));
                cb.bodies().define(cb.declareMethod("same", PrimitiveToken.BOOLEAN), (b, self) -> b.return_(eqRef(literal("a"), literal("b"))));
                new_(Object_.new_);
                cb.bodies().define(cb.declareMethod("lazy", new Supplier_<>(String_.TOKEN).token, String_.TOKEN), (b, self, s) -> b.return_(
                        lambda(new Supplier_<>(String_.TOKEN).sam, () -> call(s, String_.trim))));
                cb.bodies().define(cb.declareMethod("later", Runnable_.TOKEN, String_.TOKEN), (b, self, s) -> b.return_(
                        lambdaBlock(Runnable_.sam, lb -> lb.exec(call(s, String_.trim)).end())));
                cb.bodies().define(cb.declareMethod("advice", String_.TOKEN, Signal_.TOKEN), (b, self, s) -> b.return_(
                        switch_(Signal_.TOKEN, s, String_.TOKEN, c -> c
                                .case_(Signal_.RED, literal("stop"))
                                .case_(List.of(Signal_.AMBER, Signal_.GREEN), y -> y.yield_(literal("go"))))));
                """);

        assertThat(compile("Control", body)).succeeded();
    }

    @Test
    void wrongArgumentTypePassedToCall() {
        assertRejected(
                "WrongArgumentType",
                "void use() { call(literal(\"hi\"), String_.charAt_int, literal(\"not an int\")); }",
                "no suitable method found for call");
    }

    @Test
    void methodBodyWithoutReturnDoesNotCompile() {
        assertRejected(
                "MissingReturn",
                inClass("cb.bodies().define(cb.declareMethod(\"compute\", PrimitiveToken.INT), (body, self) -> { });"),
                "bad return type in lambda expression",
                "missing return value");
    }

    @Test
    void implicitBoxingInReturnDoesNotCompile() {
        assertRejected(
                "ImplicitBoxing",
                inClass(
                        "cb.bodies().define(cb.declareMethod(\"boxed\", Integer_.TOKEN), (b, self) -> b.return_(literal(1)));"),
                "cannot be converted to me.supcheg.javafile.typed.Expr<? extends java.lang.Integer>");
    }

    @Test
    void implicitUnboxingInReturnDoesNotCompile() {
        assertRejected(
                "ImplicitUnboxing",
                inClass("cb.bodies().define(cb.declareMethod(\"unboxed\", PrimitiveToken.INT),"
                        + " (b, self) -> b.return_(box(PrimitiveToken.INT, literal(1))));"),
                "inference variable B has incompatible bounds",
                "upper bounds: me.supcheg.javafile.facts.Prim.Int");
    }

    @Test
    void implicitUnboxingOfAnArgumentDoesNotCompile() {
        assertRejected(
                "ImplicitUnboxingArgument",
                "void use() { call(literal(\"hi\"), String_.charAt_int, box(PrimitiveToken.INT, literal(0))); }",
                "no suitable method found for call");
    }

    @Test
    void boxedConditionDoesNotCompile() {
        assertRejected(
                "BoxedCondition",
                inClass(
                        "cb.bodies().define(cb.declareVoidMethod(\"m\"), (b, self) -> b.if_(box(PrimitiveToken.BOOLEAN, literal(true)), t -> {}).end());"),
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
        // The constructor fact of an abstract class is an SuperCtorRef0,
        // which new_ does not take; it exists only for a subclass constructor.
        assertRejected(
                "NewAbstract",
                """
                void use(SuperCtorRef0<Number> numberCtor) {
                    new_(numberCtor);
                }
                """,
                "no suitable method found for new_(me.supcheg.javafile.facts.SuperCtorRef0<java.lang.Number>)");
    }

    @Test
    void aProtectedFactHeldBackIsNoFactACallTakes() {
        // The fact of a protected member is held back in a Protected, which only the declaration of a
        // subclass opens: no expression is built of it, nor is it defined as a member of the class.
        assertRejected(
                "ProtectedCall",
                """
                void use(Expr<Object> target) {
                    call(target, Object_.clone);
                }
                """,
                "no suitable method found for call(me.supcheg.javafile.typed.Expr<java.lang.Object>,"
                        + "me.supcheg.javafile.facts.Protected<");
        assertRejected(
                "ProtectedDefine",
                inClass("cb.bodies().define(Object_.clone, (b, self) -> b.return_(self));"),
                "no suitable method found for define(");
        assertRejected(
                "ProtectedOpen",
                "Object use() { return Object_.clone.fact(); }",
                "fact() is not public in me.supcheg.javafile.facts.Protected");
    }

    @Test
    void forgingAMethodFactDoesNotCompile() {
        assertRejected(
                "ForgedMethod",
                """
                void use() {
                    new MethodRef0<String, String>(
                            String_.TOKEN, "nonexistent", String_.TOKEN, MemberTraits.DEFAULT, List.of());
                }
                """,
                "is not public in me.supcheg.javafile.facts.MethodRef0; cannot be accessed from outside package");
    }

    @Test
    void theFormerIntroduceFactoryIsGone() {
        assertRejected("ForgedByIntroduce", """
                void use() {
                    MethodRef0.introduce(String_.TOKEN, "nonexistent", String_.TOKEN, MemberTraits.DEFAULT, List.of());
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
    void forgingATypeShapeDoesNotCompile() {
        // A shape vouches for a type: only UnsafeFacts makes one.
        assertRejected(
                "ForgedShape",
                """
                void use() {
                    new TypeShape<DeclaredKind.FinalClass>(ShapeOrigin.UNSAFE, DeclaredKind.FINAL_CLASS,
                            ClassDesc.of("a.B"), List.of(), List.of(), List.of(), Supertypes.NONE, MethodTableTemplate.EMPTY,
                            List.of(), false);
                }
                """,
                "is not public in me.supcheg.javafile.facts.TypeShape; cannot be accessed from outside package");
    }

    @Test
    void aShapeCannotClaimTheBuiltinOrigin() {
        // Only a metamodel origin can be claimed; the others belong to the modules that own them.
        assertRejected("ClaimedBuiltin", """
                void use() {
                    UnsafeFacts.shape(ShapeOrigin.BUILTIN, DeclaredKind.FINAL_CLASS, ClassDesc.of("a.B"),
                            List.of(), List.of(), List.of(), Supertypes.NONE, MethodTableTemplate.EMPTY, List.of(), false);
                }
                """, "no suitable method found for shape");
    }

    @Test
    void aShapeMakesOnlyTokensOfItsKind() {
        assertRejected("KindMismatch", """
                InterfaceToken<String> use(TypeShape<DeclaredKind.FinalClass> shape) {
                    return UnsafeFacts.interfaceToken(shape);
                }
                """, "no suitable method found for interfaceToken");
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
                        "cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT), (b, self) -> b.loopForever((loop, ctl) -> loop.break_(ctl)));"),
                "incompatible parameter types in lambda expression");
    }

    @Test
    void nothingFollowsLoopForever() {
        assertRejected(
                "AfterForever",
                inClass("cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT),"
                        + " (b, self) -> b.loopForever(loop -> {}).return_(literal(1)));"),
                "cannot find symbol",
                "symbol:   method return_(me.supcheg.javafile.typed.Expr<me.supcheg.javafile.facts.Prim.Int>)");
    }

    @Test
    void tryTerminatedRequiresAnEndingTryBlock() {
        assertRejected(
                "TryBlockNotEnding",
                inClass("cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT), (b, self) -> b.tryTerminated("
                        + "h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.return_(literal(1))),"
                        + " t -> t.exec(call(literal(\"x\"), String_.length))));"),
                "bad return type in lambda expression");
    }

    @Test
    void tryTerminatedRequiresEndingCatchBlocks() {
        assertRejected(
                "CatchNotEnding",
                inClass("cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT), (b, self) -> b.tryTerminated("
                        + "h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.exec(call(literal(\"x\"),"
                        + " String_.length))),"
                        + " t -> t.return_(literal(1))));"),
                "bad return type in lambda expression");
    }

    @Test
    void thereIsNoThisFactory() {
        // this is handed to an instance body as a parameter; it cannot be
        // made from a token, of another class or in a static context.
        assertRejected(
                "ThisFromToken",
                inClass("cb.bodies().define(cb.declareStaticMethod(\"m\", String_.TOKEN),"
                        + " b -> b.return_(call(this_(String_.TOKEN), Object_.toString)));"),
                "cannot find symbol",
                "method this_(me.supcheg.javafile.facts.FinalClassToken<java.lang.String>)");
    }

    @Test
    void aStaticMethodBodyGetsNoThis() {
        assertRejected(
                "StaticThis",
                inClass(
                        "cb.bodies().define(cb.declareStaticMethod(\"m\", PrimitiveToken.INT), (b, self) -> b.return_(literal(1)));"),
                "incompatible parameter types in lambda expression");
    }

    @Test
    void theThisOfOneClassIsUnusableInAnotherClass() {
        assertRejected(
                "ForeignThis",
                """
                void use() {
                    TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var x = a.declareField("x", PrimitiveToken.INT);
                            TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
                                public <B> void build(TypedClassBuilder<B> b) {
                                    b.bodies().define(b.declareMethod("m", PrimitiveToken.INT), (body, self) -> body.return_(field(self, x)));
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
                    TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var m = a.declareMethod("m", PrimitiveToken.INT);
                            TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
                                public <B> void build(TypedClassBuilder<B> b) {
                                    b.bodies().define(m, (body, self) -> body.return_(literal(1)));
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
                inClass("cb.bodies().define(Object_.toString, (b, self) -> b.return_(literal(\"x\")));"),
                "no suitable method found for define(me.supcheg.javafile.facts.MethodRef0<java.lang.Object,"
                        + "java.lang.String>");
    }

    @Test
    void anImmutableFieldCannotBeAssigned() {
        assertRejected(
                "AssignFinalField",
                inClass("""
                        var x = cb.declareField("x", PrimitiveToken.INT);
                        cb.bodies().define(cb.declareConstructor(), (b, self) -> b.exec(assignField(self, x, literal(2))).end());
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

    @Test
    void aThrowOfWhatIsNotThrowableDoesNotCompile() {
        // §9.1, the static half: what is thrown, caught and declared is a Throwable
        assertRejected(
                "ThrowOfAString",
                inClass("cb.bodies().define(cb.declareVoidMethod(\"m\"), (b, self) -> b.throw_(literal(\"x\")));"),
                "me.supcheg.javafile.typed.Expr<java.lang.String> cannot be converted to"
                        + " me.supcheg.javafile.typed.Expr<? extends java.lang.Throwable>");
    }

    @Test
    void theClassIsNotTheResultOfAnOverrideByTheWitnessOfAnUnrelatedSupertype() {
        // Following next(): the witness that the class is a Runnable says nothing of a Following
        assertRejected(
                "SelfResultByAnUnrelatedWitness",
                inHeader("h.overriding(Following_.next, h.becoming(Runnable_.TOKEN));"),
                "no suitable method found for overriding(me.supcheg.javafile.facts.MethodRef0<"
                        + "me.supcheg.javafile.typed.fixtures.Following,me.supcheg.javafile.typed.fixtures.Following>,"
                        + "me.supcheg.javafile.typed.Upcast<Self,java.lang.Runnable>)");
    }

    @Test
    void aStepDoesNotTakeAnOverrideWithTheClassForItsResultByTheWitnessOfAnUnrelatedSupertype() {
        // Object next() of Linked is answered by the witness of a Runnable; the step of Following asks for one
        assertRejected(
                "SelfResultInAnUnrelatedStep",
                inHeader("var next = h.overriding(Linked_.next, h.becoming(Runnable_.TOKEN));"
                        + " Following_.implement(h.self()).next(next);"),
                "cannot be converted to me.supcheg.javafile.facts.Implementation0<Self,? extends"
                        + " me.supcheg.javafile.typed.fixtures.Following>");
    }

    /// The header of a generic class `Generated<N extends Number>`: `%s` is the statements using `h` and `n`.
    private static String inGenericHeader(String statements) {
        return """
                void use() {
                    TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "Generated"), TypedJavaFile.typeParam("N", Number_.TOKEN), new TypedJavaFile.TypedGenericClassSpec1<Number>() {
                        public <Self, N extends Number> void build(ClassHeader<Self> h, TypeVarToken<N> n) {
                            %s
                        }
                    });
                }
                """.formatted(statements);
    }

    @Test
    void aFurtherBoundOfATypeParameterIsAnInterface() {
        // JLS 4.4: a class is the leftmost bound alone, which is a type of the generator
        assertRejected(
                "ClassForAFurtherBound",
                inGenericHeader("h.bound(n, Object_.TOKEN);"),
                "me.supcheg.javafile.facts.OpenClassToken<java.lang.Object> cannot be converted to"
                        + " me.supcheg.javafile.facts.InterfaceToken<B>");
    }

    @Test
    void aValueOfATypeParameterHasNoMethodOfATypeThatIsNotItsBound() {
        assertRejected(
                "MethodOfAnUnrelatedType",
                inGenericHeader(
                        "var cb = h.extendsObject().members();"
                                + " cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT, n), (b, self, v) -> b.return_(call(v, String_.length)));"),
                "no suitable method found for call(me.supcheg.javafile.typed.Var<N>,"
                        + "me.supcheg.javafile.facts.MethodRef0<java.lang.String,me.supcheg.javafile.facts.Prim.Int>)");
    }

    @Test
    void aTypeParameterIsNoExceptionOfAThrowsClause() {
        // JLS 8.1.2 leaves a type variable no place in a catch; the typed layer gives it none in a throws clause
        assertRejected(
                "ThrowsOfATypeParameter",
                inGenericHeader("h.extendsObject().members().throwing(n);"),
                "me.supcheg.javafile.facts.TypeVarToken<N> cannot be converted to"
                        + " me.supcheg.javafile.facts.ClassToken<? extends java.lang.Throwable>");
    }

    @Test
    void theClassIsItselfWithItsTypeParametersAndNothingElse() {
        // Self is Generated<N>: a method of the class is not called on a value of another type
        assertRejected(
                "SelfIsNoOtherType",
                inGenericHeader(
                        "var cb = h.extendsObject().members();"
                                + " var m = cb.declareMethod(\"m\", PrimitiveToken.INT);"
                                + " cb.bodies().define(cb.declareMethod(\"k\", PrimitiveToken.INT, n), (b, self, v) -> b.return_(call(v, m)));"),
                "no suitable method found for call(me.supcheg.javafile.typed.Var<N>,"
                        + "me.supcheg.javafile.facts.MethodRef0<Self,me.supcheg.javafile.facts.Prim.Int>)");
    }

    /// The header of a generated class, for fixtures of a class with a superclass: `%s` is the
    /// statements using `h`.
    private static String inHeader(String statements) {
        return """
                void use() {
                    TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "Generated"), new TypedJavaFile.TypedClassHeaderSpec() {
                        public <Self> void build(ClassHeader<Self> h) {
                            %s
                        }
                    });
                }
                """.formatted(statements);
    }

    /// The header, the members and the first constructor of a class that extends `Base`: `sh`, `cb` and `bodies`.
    private static final String SUBCLASS = "var sh = h.extends_(Base_.extend(h.self()));"
            + " var cb = sh.members();"
            + " var bodies = cb.bodies(sh.declareConstructor(), ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));";

    /// The header, the members and the first constructor of a class that extends `Maker`: `sh`, `cb` and `bodies`.
    private static final String SUBCLASS_OF_MAKER = "var sh = h.extends_(Maker_.extend(h.self()));"
            + " var cb = sh.members();"
            + " var bodies = cb.bodies(sh.declareConstructor(), ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));";

    @Test
    void aSubclassDeclaredByItsHeaderCompiles() {
        assertThat(compile(
                        "Subclass",
                        inHeader(SUBCLASS + " var hook = cb.protected_(Base_.hook_String);"
                                + " bodies.define(cb.declareMethod(\"m\", String_.TOKEN), (b, self) -> b.return_(bodies.superCall(self, hook,"
                                + " literal(\"x\"))));")))
                .succeeded();
        assertThat(
                        compile(
                                "Extending",
                                inHeader(
                                        "var list = new AbstractList_<>(String_.TOKEN); var eh = h.extending(list.token);"
                                                + " var get = eh.overriding(list.get_int);"
                                                + " var size = eh.overriding(new AbstractCollection_<>(String_.TOKEN).size);"
                                                + " var sh = eh.extends_(list.extend(h.self()).get_int(get).size(size));"
                                                + " var make = sh.declareConstructor();"
                                                + " sh.members().bodies(make, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));")))
                .succeeded();
    }

    @Test
    void theMembersOfASubclassDoNotBeginWithoutAConstructor() {
        assertRejected(
                "NoConstructor",
                inHeader("h.extends_(Base_.extend(h.self())).members().bodies();"),
                "no suitable method found for bodies(no arguments)");
        assertRejected(
                "NoBody",
                inHeader("var sh = h.extends_(Base_.extend(h.self())); sh.members().bodies(sh.declareConstructor());"),
                "no suitable method found for bodies(me.supcheg.javafile.facts.CtorRef0<Self>)");
    }

    @Test
    void aConstructorOfASubclassEndsBySuperAlone() {
        assertRejected(
                "NoSuper",
                inHeader("var sh = h.extends_(Base_.extend(h.self()));"
                        + " sh.members().bodies(sh.declareConstructor(), (b, self) -> b.end());"),
                "incompatible parameter types in lambda expression");
        assertRejected(
                "DefinedWithoutSuper",
                inHeader(SUBCLASS + " bodies.define(cb.declareConstructor(String_.TOKEN), (b, self, s) -> b.end());"),
                "no suitable method found for define(me.supcheg.javafile.facts.CtorRef1<Self,java.lang.String>");
    }

    @Test
    void anOverrideOfANarrowerResultCompiles() {
        assertThat(compile(
                        "Narrower",
                        inHeader("var get = h.overriding(Origin_.get, String_.TOKEN); var oh = h.extendsObject();"
                                + " Upcast<Self, Origin> up = oh.implements_(Origin_.implement(h.self()).get(get));"
                                + " oh.implements_(Worded_.implement(h.self()).get(get)); var cb = oh.members();"
                                + " var all = cb.declareOverride(up, Origin_.all, ArrayToken.of(String_.TOKEN));"
                                + " var clone = cb.declareOverride(cb.protected_(Object_.clone), cb.self());"
                                + " var bodies = cb.bodies();"
                                + " bodies.define(get.method(), (b, self) -> b.return_(literal(\"\")));"
                                + " bodies.define(all,"
                                + " (b, self) -> b.return_(newArray(ArrayToken.of(String_.TOKEN), literal(1))));"
                                + " bodies.define(clone, (b, self) -> b.return_(self));")))
                .succeeded();
    }

    @Test
    void theResultOfAnOverrideIsASubtypeOfThatOfTheNamedMethodAndOfEveryStep() {
        // an Integer is no CharSequence
        assertRejected(
                "Unrelated",
                inHeader("h.overriding(Worded_.get, Integer_.TOKEN);"),
                "no suitable method found for overriding");
        // a String answers Origin and not Numbered, whose get() is an Integer
        assertRejected(
                "UnrelatedStep",
                inHeader("var get = h.overriding(Origin_.get, String_.TOKEN);"
                        + " Numbered_.implement(h.self()).get(get);"),
                "incompatible types");
        // the body returns what the token says
        assertRejected(
                "BodyOfTheOverriddenResult",
                inHeader(SUBCLASS_OF_MAKER + " bodies.define(cb.declareOverride(Maker_.make, String_.TOKEN),"
                        + " (b, self) -> b.return_(new_(Object_.new_)));"),
                "incompatible types");
        // a method of the superclass stands for one of an interface by a result that is of its type
        assertRejected(
                "InheritedUnrelated",
                inHeader("var sh = h.extends_(Maker_.extend(h.self()));"
                        + " Numbered_.implement(h.self()).get(sh.inherited(Maker_.get));"),
                "incompatible types");
    }

    @Test
    void aPrimitiveAndAVoidResultHaveNoNarrowerOne() {
        assertRejected(
                "PrimitiveResult",
                inHeader("h.overriding(Maker_.count, PrimitiveToken.INT);"),
                "no suitable method found for overriding");
        assertRejected(
                "BoxOfAPrimitive",
                inHeader("h.overriding(Maker_.count, Integer_.TOKEN);"),
                "no suitable method found for overriding");
        assertRejected(
                "ResultOfAVoid",
                inHeader("h.overriding(Maker_.reset, String_.TOKEN);"),
                "no suitable method found for overriding");
        assertRejected(
                "PrimitiveAmongTheMembers",
                inHeader(SUBCLASS_OF_MAKER + " cb.declareOverride(Maker_.count, Integer_.TOKEN);"),
                "no suitable method found for declareOverride");
    }

    @Test
    void superCallsAConstructorOfTheSuperclass() {
        assertRejected(
                "SuperOfAnother",
                inHeader(
                        "var sh = h.extends_(Base_.extend(h.self()));"
                                + " sh.members().bodies(sh.declareConstructor(), ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));"),
                "no suitable method found for super_(me.supcheg.javafile.facts.CtorRef0<me.supcheg.javafile.typed.fixtures.Mid>");
    }

    @Test
    void aProtectedFactIsTakenOutOfASupertypeOfTheSuperclassAlone() {
        assertRejected(
                "ProtectedOfAnother",
                inHeader(SUBCLASS + " cb.protected_(Mid_.quiet);"),
                "method protected_ in class me.supcheg.javafile.typed.MemberBuilder<Self,S> cannot be applied to given types");
        assertRejected(
                "ProtectedOfAnotherInTheHeader",
                inHeader("h.extending(Base_.TOKEN).protected_(Shape_.describe_String);"),
                "method protected_ in class me.supcheg.javafile.typed.ExtendingHeader<Self,S> cannot be applied to given"
                        + " types");
        assertRejected(
                "ProtectedBeforeTheSuperclassIsNamed",
                inHeader("h.protected_(Shape_.describe_String);"),
                "cannot find symbol");
        assertRejected(
                "ProtectedUsedHeldBack",
                "void use() { call(literal(\"x\"), Base_.hook_String, literal(\"y\")); }",
                "no suitable method found for call");
    }

    @Test
    void aClassExtendsTheClassItSaidItIsAboutToExtend() {
        assertRejected(
                "ExtendsAnother",
                inHeader("h.extending(Base_.TOKEN).extends_(Mid_.extend(h.self()));"),
                "incompatible types",
                "me.supcheg.javafile.typed.fixtures.Mid");
    }

    @Test
    void superIsCalledOnThisAndOfAMethodOfTheSuperclass() {
        assertRejected(
                "SuperOnAValue",
                inHeader(SUBCLASS
                        + " bodies.define(cb.declareMethod(\"m\", String_.TOKEN, cb.self()), (b, self, other) ->"
                        + " b.return_(bodies.superCall(other, Base_.greet)));"),
                "no suitable method found for superCall");
        assertRejected(
                "SuperOfAnotherClass",
                inHeader(
                        SUBCLASS
                                + " bodies.define(cb.declareMethod(\"m\", String_.TOKEN), (b, self) -> b.return_(bodies.superCall(self, Mid_.label)));"),
                "no suitable method found for superCall");
    }

    @Test
    void aStepLeftOutOfTheProofOfASuperclassDoesNotCompile() {
        assertRejected(
                "MissingStep",
                inHeader("var area = h.overriding(Shape_.area); h.extends_(Shape_.extend(h.self()).area(area));"),
                "method extends_ in class me.supcheg.javafile.typed.ClassHeader<Self> cannot be applied to given types",
                "cannot infer type-variable(s) S");
    }

    @Test
    void aClassIsAnExceptionByItsWitnessAlone() {
        assertRejected(
                "ThrowingSelf",
                inHeader("var sh = h.extends_(Exception_.extend(h.self()));"
                        + " var cb = sh.members(); cb.throwing(cb.self());"),
                "no suitable method found for throwing");
    }

    @Test
    void aCatchOfWhatIsNotThrowableDoesNotCompile() {
        assertRejected(
                "CatchOfAString",
                inClass(
                        "cb.bodies().define(cb.declareVoidMethod(\"m\"), (b, self) -> b.try_(h -> h.catch_(String_.TOKEN, (c, e) -> {}), t -> {})"
                                + ".end());"),
                "no suitable method found for catch_(",
                "upper bounds: java.lang.Throwable");
    }

    @Test
    void aThrowsClauseOfWhatIsNotThrowableDoesNotCompile() {
        assertRejected(
                "ThrowsAString",
                inClass("cb.throwing(String_.TOKEN);"),
                "method throwing in class me.supcheg.javafile.typed.MemberBuilder<Self,S> cannot be applied to given types",
                "cannot be converted to me.supcheg.javafile.facts.ClassToken<? extends java.lang.Throwable>");
    }

    @Test
    void aCaughtExceptionIsOfTheTypeOfItsClause() {
        // the binding of catch_(RuntimeException) is no NumberFormatException
        assertRejected(
                "CaughtType",
                inClass("cb.bodies().define(cb.declareMethod(\"m\", PrimitiveToken.INT), (b, self) -> b.tryTerminated("
                        + "h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.return_("
                        + "call(e, Integer_.intValue))),"
                        + " t -> t.return_(literal(1))));"),
                "no suitable method found for call(me.supcheg.javafile.typed.Var<java.lang.RuntimeException>");
    }

    @Test
    void theHandlersOfATryComeBeforeItsTryBlock() {
        // the order of the parameters is the order of building
        assertRejected(
                "TryBlockFirst",
                inClass(
                        "cb.bodies().define(cb.declareVoidMethod(\"m\"), (b, self) -> b.try_(t -> {}, h -> h.finally_(f -> {})).end());"),
                "cannot find symbol",
                "location: variable h of type me.supcheg.javafile.typed.VoidBody");
    }

    @Test
    void aThrowsClauseDeclaresMembersAndBeginsNoBodies() {
        // throwing(...) gives the declarations of methods and constructors alone: the bodies begin by the builder
        assertRejected(
                "ThrowingDefines",
                inClass("var m = cb.declareVoidStaticMethod(\"m\");"
                        + " cb.throwing(RuntimeException_.TOKEN).bodies();"),
                "cannot find symbol",
                "symbol:   method bodies()");
    }

    @Test
    void aMemberIsDeclaredByTheBuilderAndDefinedByItsBodiesAlone() {
        // the declarations and the bodies are two stages and two types: a member is not defined before the
        // declarations end, nor declared after
        assertRejected(
                "DefinedByTheBuilder",
                inClass("var m = cb.declareVoidStaticMethod(\"m\"); cb.define(m, VoidBody::end);"),
                "cannot find symbol",
                "symbol:   method define(");
        assertRejected(
                "InitializedByTheBuilder",
                inClass("var f = cb.declareField(\"f\", PrimitiveToken.INT); cb.initialize(f, literal(1));"),
                "cannot find symbol",
                "symbol:   method initialize(");
        assertRejected(
                "DeclaredByTheBodies",
                inClass("cb.bodies().declareVoidStaticMethod(\"m\");"),
                "cannot find symbol",
                "symbol:   method declareVoidStaticMethod(");
        assertRejected(
                "FieldDeclaredByTheBodies",
                inClass("cb.bodies().declareField(\"f\", PrimitiveToken.INT);"),
                "cannot find symbol",
                "symbol:   method declareField(");
    }

    @Test
    void aThrowsClauseDeclaresNoField() {
        assertRejected(
                "ThrowingField",
                inClass("cb.throwing(RuntimeException_.TOKEN).declareField(\"f\", PrimitiveToken.INT);"),
                "cannot find symbol",
                "symbol:   method declareField(");
    }

    @Test
    void aThrowsClauseIsGivenAtOnce() {
        assertRejected(
                "ThrowingTwice",
                inClass("cb.throwing(RuntimeException_.TOKEN).throwing(RuntimeException_.TOKEN);"),
                "cannot find symbol",
                "symbol:   method throwing(");
    }

    // ------------------------------------------------------------------
    // Lambdas (§6.4): the parameters and the result are those of the `sam` fact
    // ------------------------------------------------------------------

    private static final String STRING_TO_INTEGER = "new Function_<>(String_.TOKEN, Integer_.TOKEN).sam";

    @Test
    void aLambdaHasTheParametersOfItsFunctionalInterface() {
        assertRejected(
                "LambdaArity",
                "void use() { lambda(" + STRING_TO_INTEGER + ", () -> box(PrimitiveToken.INT, literal(1))); }",
                "no suitable method found for lambda(",
                "incompatible parameter types in lambda expression");
        assertRejected(
                "LambdaTwoForOne",
                "void use() { lambda(" + STRING_TO_INTEGER + ", (a, b) -> box(PrimitiveToken.INT, literal(1))); }",
                "no suitable method found for lambda(",
                "incompatible parameter types in lambda expression");
    }

    @Test
    void aParameterOfALambdaIsOfTheTypeOfItsFunctionalInterface() {
        assertRejected(
                "LambdaParameterType",
                "void use() { lambda(" + STRING_TO_INTEGER + ", s -> call(s, Integer_.intValue)); }",
                "no suitable method found for call(me.supcheg.javafile.typed.Var<java.lang.String>,");
    }

    @Test
    void aLambdaGivesTheResultOfItsFunctionalInterface() {
        assertRejected(
                "LambdaResultType",
                "void use() { lambda(" + STRING_TO_INTEGER + ", s -> s); }",
                "inference variable R has incompatible bounds",
                "lower bounds: java.lang.String");
        assertRejected(
                "LambdaUnboxedResult",
                "void use() { lambda(" + STRING_TO_INTEGER + ", s -> call(s, String_.length)); }",
                "incompatible bounds",
                "equality constraints: java.lang.Integer");
    }

    @Test
    void aBlockLambdaWithAResultReturns() {
        assertRejected(
                "LambdaNoReturn",
                "void use() { lambdaBlock(" + STRING_TO_INTEGER + ", (lb, s) -> { }); }",
                "no suitable method found for lambdaBlock(",
                "missing return value");
        assertRejected(
                "LambdaEnd",
                "void use() { lambdaBlock(" + STRING_TO_INTEGER + ", (lb, s) -> lb.end()); }",
                "cannot find symbol",
                "symbol:   method end()");
    }

    @Test
    void aLambdaWithoutAResultIsAnEffectAndReturnsNoValue() {
        assertRejected(
                "VoidLambdaValue",
                "void use() { lambda(Runnable_.sam, () -> literal(1)); }",
                "no suitable method found for lambda(me.supcheg.javafile.facts.VoidSam0<java.lang.Runnable>,");
        assertRejected(
                "VoidLambdaReturn",
                "void use() { lambdaBlock(Runnable_.sam, lb -> lb.return_(literal(1))); }",
                "method return_ in class me.supcheg.javafile.typed.VoidBody cannot be applied to given types");
    }

    @Test
    void aLambdaWithAResultIsNotAVoidCall() {
        assertRejected(
                "LambdaOfVoidCall",
                "void use(Expr<Runnable> r) { lambda(new Supplier_<>(String_.TOKEN).sam,"
                        + " () -> voidCall(r, Runnable_.run)); }",
                "no suitable method found for lambda(me.supcheg.javafile.facts.Sam0<");
    }

    @Test
    void aLambdaIsTypedByTheSamFactAndNotByAMethodFact() {
        assertRejected(
                "LambdaOfMethodFact",
                "void use() { lambda(String_.length, () -> literal(1)); }",
                "no suitable method found for lambda");
    }

    @Test
    void aLambdaIsOfItsFunctionalInterfaceAndNoOther() {
        assertRejected(
                "LambdaOtherInterface",
                inClass("cb.bodies().define(cb.declareMethod(\"m\", Runnable_.TOKEN), (b, self) -> b.return_("
                        + "lambda(new Supplier_<>(String_.TOKEN).sam, () -> literal(\"a\"))));"),
                "inference variable F has incompatible bounds",
                "upper bounds: java.lang.Runnable");
    }

    // ------------------------------------------------------------------
    // switch expressions (§6.3)
    // ------------------------------------------------------------------

    @Test
    void theBlockOfACaseHasNoReturn() {
        assertRejected(
                "ReturnInSwitch",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.default_(y -> y.return_(literal(\"a\")))); }",
                "cannot find symbol",
                "symbol:   method return_(");
        assertRejected(
                "ReturnInBranchInSwitch",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.default_(y -> y.ifElse(literal(true), t -> t.return_(literal(\"a\")),"
                        + " e -> e.yield_(literal(\"b\"))))); }",
                "cannot find symbol",
                "symbol:   method return_(");
    }

    @Test
    void theBlockOfACaseYields() {
        assertRejected(
                "NoYield",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.default_(y -> { })); }",
                "no suitable method found for default_(",
                "missing return value");
    }

    @Test
    void aResultIsOfTheTypeOfTheSwitch() {
        assertRejected(
                "ValueType",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.default_(literal(1))); }",
                "no suitable method found for default_(me.supcheg.javafile.typed.Expr<me.supcheg.javafile.facts.Prim.Int>)");
        assertRejected(
                "YieldType",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.default_(y -> y.yield_(literal(1)))); }",
                "cannot be converted to me.supcheg.javafile.typed.Expr<? extends java.lang.String>");
    }

    @Test
    void theSelectorIsOfTheEnum() {
        assertRejected(
                "SelectorType",
                "void use() { switch_(Signal_.TOKEN, literal(\"a\"), String_.TOKEN,"
                        + " c -> c.default_(literal(\"a\"))); }",
                "inference variable E has incompatible bounds",
                "lower bounds: java.lang.String");
    }

    @Test
    void aCaseIsOfAConstantOfTheEnum() {
        assertRejected(
                "ConstantType",
                "void use(Expr<Signal> s, EnumConstant<Thread.State> other) { switch_(Signal_.TOKEN, s, String_.TOKEN,"
                        + " c -> c.case_(other, literal(\"a\"))); }",
                "no suitable method found for case_(me.supcheg.javafile.facts.EnumConstant<java.lang.Thread.State>,");
    }

    @Test
    void aSwitchIsOverAnEnum() {
        assertRejected(
                "NotAnEnum",
                "void use() { switch_(String_.TOKEN, literal(\"a\"), String_.TOKEN,"
                        + " c -> c.default_(literal(\"a\"))); }",
                "cannot be converted to me.supcheg.javafile.facts.EnumToken<E>");
    }

    @Test
    void aSwitchExpressionIsNoStatement() {
        assertRejected(
                "SwitchStatement",
                inClass(
                        "cb.bodies().define(cb.declareVoidMethod(\"m\", Signal_.TOKEN), (b, self, s) -> b.exec(switch_(Signal_.TOKEN, s,"
                                + " String_.TOKEN, c -> c.default_(literal(\"a\")))).end());"),
                "conforms to me.supcheg.javafile.typed.Effect");
    }

    @Test
    void theCasesAndTheBlockOfACaseHaveNoPublicConstructor() {
        assertRejected(
                "StrayEnumSwitchCases",
                "void use() { new EnumSwitchCases<Signal, String>(Signal_.TOKEN, String_.TOKEN, null); }",
                "is not public in me.supcheg.javafile.typed.EnumSwitchCases");
        assertRejected("StrayYieldBody", "void use() { YieldBody.ofCase(null, String_.TOKEN, \"stray\"); }", "ofCase");
    }

    // ------------------------------------------------------------------
    // finally blocks: none of the statements that end a block (FinallyBody)
    // ------------------------------------------------------------------

    private static String inFinally(String statement) {
        return inClass(
                "cb.bodies().define(cb.declareStaticMethod(\"m\", PrimitiveToken.INT), b -> b.while_(literal(true), (loop, ctl) ->"
                        + " loop.try_(h -> h.finally_(f -> " + statement
                        + "), t -> t.break_(ctl))).return_(literal(0)));");
    }

    @Test
    void aFinallyBlockDoesNotReturnThrowBreakOrContinue() {
        assertRejected("FinallyReturns", inFinally("f.return_(literal(1))"), "cannot find symbol", "method return_(");
        assertRejected(
                "FinallyThrows",
                inFinally("f.throw_(new_(RuntimeException_.new_))"),
                "cannot find symbol",
                "method throw_(");
        assertRejected("FinallyBreaks", inFinally("f.break_(ctl)"), "cannot find symbol", "method break_(");
        assertRejected("FinallyContinues", inFinally("f.continue_(ctl)"), "cannot find symbol", "method continue_(");
    }

    @Test
    void aFinallyBlockHasNoneOfTheFormsThatEndABlock() {
        assertRejected(
                "FinallyLoopsForever", inFinally("f.loopForever(loop2 -> {})"), "cannot find symbol", "loopForever");
        assertRejected(
                "FinallyIfElse",
                inFinally("f.ifElse(literal(true), t -> t.return_(literal(1)), e -> e.return_(literal(2)))"),
                "cannot find symbol",
                "ifElse");
        assertRejected(
                "FinallyTryTerminated",
                inFinally("f.tryTerminated(g -> g.finally_(x -> {}), t -> t.return_(literal(1)))"),
                "cannot find symbol",
                "tryTerminated");
    }

    @Test
    void aFinallyBlockOfACaseDoesNotYield() {
        assertRejected(
                "FinallyYields",
                "void use(Expr<Signal> s) { switch_(Signal_.TOKEN, s, String_.TOKEN, c -> c.default_(y ->"
                        + " y.tryTerminated(h -> h.finally_(f -> f.yield_(literal(\"f\"))),"
                        + " t -> t.yield_(literal(\"t\"))))); }",
                "cannot find symbol",
                "method yield_(");
    }
}
