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
                cb.method("length", PrimitiveToken.INT, PrimitiveToken.INT.boxed(), (b, self, boxed) -> b.let(
                        PrimitiveToken.INT, unbox(PrimitiveToken.INT, boxed), i -> b.if_(
                                ltInt(i, call(literal("abc"), String_.length)),
                                t -> t.exec(call(literal("abc"), String_.charAt_int, i)))
                        .return_(addInt(i, literal(1)))));
                cb.method("boxed", Integer_.TOKEN, (b, self) -> b.return_(box(PrimitiveToken.INT, literal(1))));
                cb.method("empty", String_.TOKEN, (b, self) -> b.return_(literalNull(String_.TOKEN)));
                cb.method("same", PrimitiveToken.BOOLEAN, (b, self) -> b.return_(eqRef(literal("a"), literal("b"))));
                new_(Object_.new_);
                cb.method("lazy", new Supplier_<>(String_.TOKEN).token, String_.TOKEN, (b, self, s) -> b.return_(
                        lambda(new Supplier_<>(String_.TOKEN).sam, () -> call(s, String_.trim))));
                cb.method("later", Runnable_.TOKEN, String_.TOKEN, (b, self, s) -> b.return_(
                        lambdaBlock(Runnable_.sam, lb -> lb.exec(call(s, String_.trim)).end())));
                cb.method("advice", String_.TOKEN, Signal_.TOKEN, (b, self, s) -> b.return_(
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
                "void use() { call(literal(\"hi\"), String_.charAt_int, box(PrimitiveToken.INT, literal(0))); }",
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
                inClass("cb.define(Object_.clone, (b, self) -> b.return_(self));"),
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
                        + "h -> h.catch_(RuntimeException_.TOKEN, (c, e) -> c.return_(literal(1))),"
                        + " t -> t.exec(call(literal(\"x\"), String_.length))));"),
                "bad return type in lambda expression");
    }

    @Test
    void tryTerminatedRequiresEndingCatchBlocks() {
        assertRejected(
                "CatchNotEnding",
                inClass("cb.method(\"m\", PrimitiveToken.INT, (b, self) -> b.tryTerminated("
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
                    TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var x = a.field("x", PrimitiveToken.INT, literal(1));
                            TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
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
                    TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "A"), new TypedJavaFile.TypedClassSpec() {
                        public <A> void build(TypedClassBuilder<A> a) {
                            var m = a.declareMethod("m", PrimitiveToken.INT);
                            TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("fixtures", "B"), new TypedJavaFile.TypedClassSpec() {
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

    @Test
    void aThrowOfWhatIsNotThrowableDoesNotCompile() {
        // §9.1, the static half: what is thrown, caught and declared is a Throwable
        assertRejected(
                "ThrowOfAString",
                inClass("cb.voidMethod(\"m\", (b, self) -> b.throw_(literal(\"x\")));"),
                "me.supcheg.javafile.typed.Expr<java.lang.String> cannot be converted to"
                        + " me.supcheg.javafile.typed.Expr<? extends java.lang.Throwable>");
    }

    @Test
    void aCatchOfWhatIsNotThrowableDoesNotCompile() {
        assertRejected(
                "CatchOfAString",
                inClass("cb.voidMethod(\"m\", (b, self) -> b.try_(h -> h.catch_(String_.TOKEN, (c, e) -> {}), t -> {})"
                        + ".end());"),
                "method catch_ in class me.supcheg.javafile.typed.Handlers<B> cannot be applied to given types",
                "upper bounds: java.lang.Throwable");
    }

    @Test
    void aThrowsClauseOfWhatIsNotThrowableDoesNotCompile() {
        assertRejected(
                "ThrowsAString",
                inClass("cb.throwing(String_.TOKEN);"),
                "method throwing in class me.supcheg.javafile.typed.TypedClassBuilder<Self> cannot be applied to given types",
                "cannot be converted to me.supcheg.javafile.facts.ClassToken<? extends java.lang.Throwable>");
    }

    @Test
    void aCaughtExceptionIsOfTheTypeOfItsClause() {
        // the binding of catch_(RuntimeException) is no NumberFormatException
        assertRejected(
                "CaughtType",
                inClass("cb.method(\"m\", PrimitiveToken.INT, (b, self) -> b.tryTerminated("
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
                inClass("cb.voidMethod(\"m\", (b, self) -> b.try_(t -> {}, h -> h.finally_(f -> {})).end());"),
                "cannot find symbol",
                "location: variable h of type me.supcheg.javafile.typed.VoidBody");
    }

    @Test
    void aThrowsClauseDeclaresMembersAndDefinesNone() {
        // throwing(...) gives the declarations of methods and constructors alone
        assertRejected(
                "ThrowingDefines",
                inClass("var m = cb.declareVoidStaticMethod(\"m\");"
                        + " cb.throwing(RuntimeException_.TOKEN).define(m, VoidBody::end);"),
                "cannot find symbol",
                "symbol:   method define(");
    }

    @Test
    void aThrowsClauseDeclaresNoField() {
        assertRejected(
                "ThrowingField",
                inClass("cb.throwing(RuntimeException_.TOKEN).field(\"f\", PrimitiveToken.INT, literal(1));"),
                "cannot find symbol",
                "symbol:   method field(");
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
                inClass("cb.method(\"m\", Runnable_.TOKEN, (b, self) -> b.return_("
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
                inClass("cb.voidMethod(\"m\", Signal_.TOKEN, (b, self, s) -> b.exec(switch_(Signal_.TOKEN, s,"
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
        return inClass("cb.staticMethod(\"m\", PrimitiveToken.INT, b -> b.while_(literal(true), (loop, ctl) ->"
                + " loop.try_(h -> h.finally_(f -> " + statement + "), t -> t.break_(ctl))).return_(literal(0)));");
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
