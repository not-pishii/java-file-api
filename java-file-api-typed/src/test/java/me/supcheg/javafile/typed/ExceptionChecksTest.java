package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.io.FileNotFoundException_;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.Reader_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.io.UnsupportedEncodingException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.InterruptedException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.Expressions.voidStaticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of checked exceptions (§9.1, JLS 11.2.3), which
/// Java types cannot express: a checked exception that is neither caught nor
/// declared, and a `catch` clause that can catch nothing. Each is rejected
/// where the statement or the clause is built, with a message naming what
/// throws, where, and what to do.
///
/// A check is told against javac, by the Java the typed code stands for:
/// [#asJavac] where javac rejects that Java too — else the test could pass
/// for a reason of its own — [#unlikeJavac] where the typed layer is
/// stricter and javac accepts it, and [#asJavacAccepts] for what both accept.
class ExceptionChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// `new StringReader("a").read()`, which throws `IOException`.
    private static final String READ = "new StringReader(\"a\").read()";

    /// Declares the class `Probe` with the given members.
    private static void declare(Consumer<TypedClassBuilder<?>> members) {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    /// Declares `static int m()` with the given body.
    private static void intMethod(Function<Body<Prim.Int>, Terminated<Prim.Int>> body) {
        declare(cb -> cb.staticMethod("m", PrimitiveToken.INT, body));
    }

    /// `new StringReader("a").read()`.
    private static Invocation<Prim.Int> read() {
        return call(new_(StringReader_.new_String, literal("a")), StringReader_.read);
    }

    /// Compiles the members as those of a class `Probe`, without lints.
    private static Compilation compiled(String members) {
        return javac().compile(JavaFileObjects.forSourceString(
                "Probe", "import java.io.*;\nclass Probe {\n" + members + "\n}\n"));
    }

    /// The typed layer rejects `typed` with `message`, and javac rejects the Java it stands for with `error`.
    private static void asJavac(ThrowingCallable typed, String message, String java, String error) {
        assertThatIllegalStateException().isThrownBy(typed).withMessageContaining(message);
        Compilation compilation = compiled(java);
        CompilationSubject.assertThat(compilation).failed();
        CompilationSubject.assertThat(compilation).hadErrorContaining(error);
    }

    /// The typed layer rejects `typed` with `message` though javac accepts the Java it stands for.
    private static void unlikeJavac(ThrowingCallable typed, String message, String java) {
        assertThatIllegalStateException().isThrownBy(typed).withMessageContaining(message);
        CompilationSubject.assertThat(compiled(java)).succeeded();
    }

    /// The typed layer accepts `typed`, and javac the Java it stands for.
    private static void asJavacAccepts(ThrowingCallable typed, String java) {
        assertThatCode(typed).doesNotThrowAnyException();
        CompilationSubject.assertThat(compiled(java)).succeeded();
    }

    @Nested
    class CaughtOrDeclared {

        @Test
        void aCallThatThrowsACheckedExceptionIsRejectedWhereNothingCatchesOrDeclaresIt() {
            asJavac(
                    () -> intMethod(b -> b.return_(read())),
                    "int java.io.StringReader.read() in the body of static method m can throw the checked exception"
                            + " java.io.IOException, which no catch_ of an enclosing try catches and the method"
                            + " does not declare (declared: nothing): catch it, or declare the method through"
                            + " cb.throwing(...) (JLS 11.2.3)",
                    "static int m() { return " + READ + "; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void theFailureIsAtTheStatementThatThrows() {
            // the statements before the call are built, the one after it is not
            AtomicReference<String> reached = new AtomicReference<>("nothing");

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> {
                        b.exec(call(literal("a"), String_.length));
                        reached.set("the statement before");
                        b.exec(read());
                        reached.set("the statement after");
                        return b.return_(literal(0));
                    }));

            assertThat(reached).hasValue("the statement before");
        }

        @Test
        void aCallNestedInAnExpressionIsFound() {
            asJavac(
                    () -> intMethod(
                            b -> b.return_(call(call(literal("a"), String_.repeat_int, read()), String_.length))),
                    "the checked exception java.io.IOException",
                    "static int m() { return \"a\".repeat(" + READ + ").length(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aConstructorThatThrowsACheckedExceptionIsRejected() {
            asJavac(
                    () -> declare(cb -> {
                        var ctor = cb.throwing(IOException_.TOKEN).constructor((b, _) -> b.end());
                        cb.voidStaticMethod("m", b -> b.exec(new_(ctor)).end());
                    }),
                    "new me.supcheg.example.Probe() in the body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    "Probe() throws IOException {} static void m() { new Probe(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void theBodyOfAConstructorIsToldToDeclareTheConstructor() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(
                            cb -> cb.constructor((b, _) -> b.exec(read()).end())))
                    .withMessageContaining("in the body of constructor can throw")
                    .withMessageContaining("the constructor does not declare (declared: nothing): catch it, or"
                            + " declare the constructor through cb.throwing(...)");
        }

        @Test
        void aThrowOfACheckedExceptionIsRejected() {
            asJavac(
                    () -> intMethod(b -> b.throw_(new_(IOException_.new_))),
                    "throw_ in the body of static method m can throw the checked exception java.io.IOException",
                    "static int m() { throw new IOException(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aThrowIsCheckedByTheStaticTypeOfItsExpression() {
            asJavac(
                    () -> intMethod(b -> b.let(Throwable_.TOKEN, new_(RuntimeException_.new_), b::throw_)),
                    "the checked exception java.lang.Throwable",
                    "static int m() { Throwable t = new RuntimeException(); throw t; }",
                    "unreported exception java.lang.Throwable");
        }

        @Test
        void anUncheckedExceptionIsNotTracked() {
            asJavacAccepts(
                    () -> intMethod(b -> b.throw_(new_(RuntimeException_.new_))),
                    "static int m() { throw new RuntimeException(); }");
        }

        @Test
        void aDeclaredSuperclassCoversTheException() {
            asJavacAccepts(
                    () -> declare(cb -> cb.throwing(Exception_.TOKEN)
                            .staticMethod(
                                    "m",
                                    PrimitiveToken.INT,
                                    b -> b.exec(read()).throw_(new_(FileNotFoundException_.new_)))),
                    "static int m() throws Exception { " + READ + "; throw new FileNotFoundException(); }");
        }

        @Test
        void aDeclaredSubclassDoesNotCoverTheException() {
            asJavac(
                    () -> declare(cb -> cb.throwing(FileNotFoundException_.TOKEN)
                            .staticMethod("m", PrimitiveToken.INT, b -> b.return_(read()))),
                    "does not declare (declared: java.io.FileNotFoundException)",
                    "static int m() throws FileNotFoundException { return " + READ + "; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void anExceptionAMemberOfTheClassDeclaresIsCheckedAtItsCalls() {
            asJavac(
                    () -> declare(cb -> {
                        var read = cb.throwing(IOException_.TOKEN).declareStaticMethod("read", PrimitiveToken.INT);
                        cb.staticMethod("m", PrimitiveToken.INT, b -> b.return_(staticCall(read)));
                    }),
                    "static int me.supcheg.example.Probe.read() in the body of static method m can throw the"
                            + " checked exception java.io.IOException",
                    "static int read() throws IOException { return 0; } static int m() { return read(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void theBodyOfAMemberMayThrowWhatItsDeclarationDeclares() {
            // declared with the clause, defined by the builder
            assertThatCode(() -> declare(cb -> define(cb))).doesNotThrowAnyException();
        }

        private static <Self> void define(TypedClassBuilder<Self> cb) {
            var read = cb.throwing(IOException_.TOKEN).declareMethod("read", PrimitiveToken.INT);
            cb.define(read, (b, _) -> b.return_(read()));
        }

        @Test
        void aFieldInitializerThrowsNoCheckedException() {
            asJavac(
                    () -> declare(cb -> cb.field("first", PrimitiveToken.INT, read())),
                    "int java.io.StringReader.read() in the initializer of field first can throw the checked"
                            + " exception java.io.IOException: the typed layer accepts no checked exception in a"
                            + " field initializer",
                    "int first = " + READ + ";",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aFieldInitializerThrowsNoCheckedExceptionThoughEveryConstructorDeclaresIt() {
            unlikeJavac(
                    () -> declare(cb -> {
                        cb.throwing(IOException_.TOKEN).constructor((b, _) -> b.end());
                        cb.field("first", PrimitiveToken.INT, read());
                    }),
                    "stricter than Java, which lets the initializer of an instance field throw what every"
                            + " constructor declares",
                    "Probe() throws IOException {} int first = " + READ + ";");
        }
    }

    /// A fact of a supertype called on a receiver of a subtype that overrides the method with a
    /// narrower `throws` clause: `Reader.close() throws IOException`, `StringReader.close()`. Lowering
    /// casts the receiver to the owner of the fact, so the clause javac goes by is the one of the fact.
    @Nested
    class TheClauseOfTheFact {

        @Test
        void theCallThrowsWhatTheFactDeclaresWhateverTheOverrideOfTheReceiverDoes() {
            asJavac(
                    () -> declare(cb -> cb.voidStaticMethod(
                            "m",
                            StringReader_.TOKEN,
                            (b, r) -> b.exec(voidCall(r, Reader_.close)).end())),
                    "void java.io.Reader.close() in the body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    "static void m(StringReader r) { ((Reader) r).close(); }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void withoutTheCastJavacWouldGoByTheOverride() {
            // what the typed layer rendered before: the catch the fact asks for is one javac rejects
            CompilationSubject.assertThat(
                            compiled("static void m(StringReader r) { try { r.close(); } catch (IOException e) { } }"))
                    .hadErrorContaining(
                            "exception java.io.IOException is never thrown in body of corresponding try statement");
        }
    }

    @Nested
    class TryBlocks {

        @Test
        void aCatchOfAnotherExceptionDoesNotCover() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))),
                            t -> t.return_(read()))),
                    "in the try block of tryTerminated in body of static method m can throw the checked exception"
                            + " java.io.IOException",
                    "static int m() { try { return " + READ + "; } catch (InterruptedException e) { return 0; } }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void aCatchOfASubclassDoesNotCover() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0))),
                            t -> t.return_(read()))),
                    "the checked exception java.io.IOException",
                    "static int m() { try { return " + READ + "; } catch (FileNotFoundException e) { return 0; } }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void anExceptionThrownInACatchBlockIsNotCaughtByItsOwnTry() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, e) -> c.throw_(e)), t -> t.return_(read()))),
                    "throw_ in the catch block of tryTerminated in body of static method m can throw the checked"
                            + " exception java.io.IOException",
                    "static int m() { try { return " + READ + "; } catch (IOException e) { throw e; } }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void anExceptionThrownInAFinallyBlockIsNotCaughtByItsOwnTry() {
            asJavac(
                    () -> intMethod(b -> b.try_(
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {})
                                            .finally_(f -> f.exec(read())),
                                    t -> t.exec(read()))
                            .return_(literal(0))),
                    "in the finally block of try_ in body of static method m can throw",
                    "static int m() { try { " + READ + "; } catch (IOException e) { } finally { " + READ
                            + "; } return 0; }",
                    "unreported exception java.io.IOException");
        }

        @Test
        void anEnclosingTryCatchesWhatTheInnerOneDoesNot() {
            asJavacAccepts(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))),
                            outer -> outer.try_(
                                            h -> h.catch_(FileNotFoundException_.TOKEN, (_, _) -> {}),
                                            t -> t.exec(read()))
                                    .return_(literal(1)))),
                    "static int m() { try { try { " + READ + "; } catch (FileNotFoundException e) { } return 1; }"
                            + " catch (IOException e) { return 2; } }");
        }

        @Test
        void aLoopOrBranchInATryBlockIsCovered() {
            asJavacAccepts(
                    () -> intMethod(b -> b.try_(
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {}),
                                    t -> t.if_(
                                            literal(true),
                                            x -> x.while_(
                                                    call(literal("a"), String_.isEmpty),
                                                    (loop, _) -> loop.exec(read()))))
                            .return_(literal(0))),
                    "static int m() { try { if (true) { while (\"a\".isEmpty()) { " + READ
                            + "; } } } catch (IOException e) { } return 0; }");
        }

        @Test
        void theHandlersAreBuiltBeforeTheTryBlock() {
            List<String> order = new CopyOnWriteArrayList<>();

            intMethod(b -> b.try_(
                            h -> h.catch_(IOException_.TOKEN, (_, _) -> order.add("catch"))
                                    .finally_(_ -> order.add("finally")),
                            t -> {
                                order.add("try");
                                t.exec(read());
                            })
                    .return_(literal(0)));

            assertThat(order).containsExactly("catch", "finally", "try");
        }

        @Test
        void aTryNeedsACatchOrAFinally() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(_ -> {}, _ -> {}).return_(literal(0))))
                    .withMessageContaining(
                            "the try_ in the body of static method m requires at least one catch_ or a finally_");
        }

        @Test
        void aClauseAddedAfterTheTryBlockIsRejected() {
            AtomicReference<Handlers<Body<Prim.Int>>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(
                                    h -> stash.set(h.finally_(_ -> {})),
                                    _ -> stash.get().catch_(IOException_.TOKEN, (_, _) -> {}))
                            .return_(literal(0))))
                    .withMessageContaining("catch_ of java.io.IOException of the try_ in the body of static method"
                            + " m after its try block was built");
        }

        @Test
        void aCatchAddedFromInsideTheBlockOfAnotherIsRejected() {
            // it would be rendered before the clause it was added in: catch (IOException), then
            // catch (FileNotFoundException), which javac rejects
            asJavac(
                    () -> intMethod(b -> b.try_(
                                    h -> h.catch_(
                                            FileNotFoundException_.TOKEN,
                                            (_, _) -> h.catch_(IOException_.TOKEN, (_, _) -> {})),
                                    t -> t.exec(read()))
                            .return_(literal(0))),
                    "catch_ of java.io.IOException of the try_ in the body of static method m while its catch_ of"
                            + " java.io.FileNotFoundException is being built",
                    "static int m() { try { " + READ + "; } catch (IOException e) { }"
                            + " catch (FileNotFoundException e) { } return 0; }",
                    "exception java.io.FileNotFoundException has already been caught");
        }

        @Test
        void aFinallyAddedFromInsideTheBlockOfAClauseIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(
                                    h -> h.catch_(RuntimeException_.TOKEN, (_, _) -> h.finally_(_ -> {})),
                                    t -> t.exec(read()))
                            .return_(literal(0))))
                    .withMessageContaining("finally_ of the try_ in the body of static method m while its catch_ of"
                            + " java.lang.RuntimeException is being built");
        }

        @Test
        void aCatchAddedFromInsideTheFinallyBlockIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(
                                    h -> h.finally_(_ -> h.catch_(RuntimeException_.TOKEN, (_, _) -> {})),
                                    t -> t.exec(call(literal("a"), String_.length)))
                            .return_(literal(0))))
                    .withMessageContaining("while its finally_ is being built");
        }
    }

    @Nested
    class CatchClausesThatCatchNothing {

        @Test
        void aCatchOfACheckedExceptionTheTryBlockCannotThrowIsRejected() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0))),
                            t -> t.return_(literal(1)))),
                    "catch_ of java.io.IOException: the try block of tryTerminated in body of static method m"
                            + " cannot throw this checked exception (it can throw: nothing)",
                    "static int m() { try { return 1; } catch (IOException e) { return 0; } }",
                    "exception java.io.IOException is never thrown in body of corresponding try statement");
        }

        @Test
        void aCatchOfAnUnrelatedCheckedExceptionIsRejected() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))),
                            t -> t.return_(read()))),
                    "catch_ of java.lang.InterruptedException: the try block of tryTerminated in body of static"
                            + " method m cannot throw this checked exception (it can throw: java.io.IOException)",
                    "static int m() { try { return " + READ + "; } catch (IOException e) { return 0; }"
                            + " catch (InterruptedException e) { return 0; } }",
                    "exception java.lang.InterruptedException is never thrown in body of corresponding try statement");
        }

        @Test
        void aCatchOfExceptionThrowableOrAnUncheckedClassIsAlwaysAllowed() {
            asJavacAccepts(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(Exception_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(Throwable_.TOKEN, (c, _) -> c.return_(literal(0))),
                            t -> t.return_(literal(1)))),
                    "static int m() { try { return 1; } catch (RuntimeException e) { return 0; }"
                            + " catch (Exception e) { return 0; } catch (Throwable e) { return 0; } }");
        }

        @Test
        void aCatchOfASubclassOfWhatTheTryBlockThrowsIsAllowed() {
            // read() declares IOException, which may be a FileNotFoundException at run time
            asJavacAccepts(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .staticMethod(
                                    "m",
                                    PrimitiveToken.INT,
                                    b -> b.tryTerminated(
                                            h -> h.catch_(
                                                    FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0))),
                                            t -> t.return_(read())))),
                    "static int m() throws IOException { try { return " + READ
                            + "; } catch (FileNotFoundException e) { return 0; } }");
        }

        @Test
        void aCatchAfterTheOneOfItsSuperclassIsRejectedWhenItIsAdded() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(1))),
                            t -> t.return_(read()))),
                    "catch_ of java.io.FileNotFoundException of the tryTerminated in the body of static method m"
                            + " comes after the catch_ of java.io.IOException, which has already caught it",
                    "static int m() { try { return " + READ + "; } catch (IOException e) { return 0; }"
                            + " catch (FileNotFoundException e) { return 1; } }",
                    "exception java.io.FileNotFoundException has already been caught");
        }

        @Test
        void aCatchOfTheSameTypeTwiceIsRejected() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(1))),
                            t -> t.return_(literal(1)))),
                    "has already caught it",
                    "static int m() { try { return 1; } catch (RuntimeException e) { return 0; }"
                            + " catch (RuntimeException e) { return 1; } }",
                    "exception java.lang.RuntimeException has already been caught");
        }

        @Test
        void aCatchWhoseExceptionsThePrecedingClausesHaveAllCaughtIsRejectedThoughJavacOnlyWarns() {
            unlikeJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(1))),
                            t -> t.throw_(new_(FileNotFoundException_.new_)))),
                    "catch_ of java.io.IOException is unreachable: what the try block of tryTerminated in body of"
                            + " static method m can throw of it (java.io.FileNotFoundException)",
                    "static int m() { try { throw new FileNotFoundException(); }"
                            + " catch (FileNotFoundException e) { return 0; } catch (IOException e) { return 1; } }");
        }

        @Test
        void whatAnInnerTryCatchesIsNotThrownToTheOuterOne() {
            asJavac(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))),
                            outer -> outer.try_(h -> h.catch_(IOException_.TOKEN, (_, _) -> {}), t -> t.exec(read()))
                                    .return_(literal(1)))),
                    "cannot throw this checked exception",
                    "static int m() { try { try { " + READ + "; } catch (IOException e) { } return 1; }"
                            + " catch (IOException e) { return 2; } }",
                    "exception java.io.IOException is never thrown in body of corresponding try statement");
        }

        @Test
        void whatACatchBlockThrowsIsThrownToTheOuterTry() {
            asJavacAccepts(
                    () -> intMethod(b -> b.tryTerminated(
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))),
                            outer -> outer.try_(
                                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.exec(read())),
                                            t -> t.exec(call(literal("a"), String_.length)))
                                    .return_(literal(1)))),
                    "static int m() { try { try { \"a\".length(); } catch (RuntimeException e) { " + READ
                            + "; } return 1; } catch (IOException e) { return 2; } }");
        }

        @Test
        void theClausesOfATryBlockWithUntypedCodeAreNotChecked() {
            // what an Unsafe statement or expression throws is not known
            UnsafeStmt statement =
                    Unsafe.stmt(new ThrowStmt(Exprs.new_(Types.of(ClassDesc.of("java.io.IOException")))));
            Expr<Prim.Int> expression = Unsafe.expr(Exprs.literal(1), PrimitiveToken.INT);

            assertThatCode(() -> intMethod(b -> b.try_(
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {}),
                                    t -> t.if_(literal(true), x -> x.add(statement)))
                            .tryTerminated(
                                    h -> h.catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))),
                                    t -> t.return_(expression))))
                    .doesNotThrowAnyException();
        }
    }

    /// A `throw` of the binding of a `catch` throws what the `try` block of the clause does and the
    /// clause catches, not the type of the clause (JLS 11.2.2).
    @Nested
    class PreciseRethrow {

        @Test
        void aRethrownBindingThrowsWhatItsTryBlockThrowsOfTheClause() {
            // the inner try rethrows FileNotFoundException, not every IOException
            asJavac(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .voidStaticMethod(
                                    "m",
                                    b -> b.try_(
                                                    h -> h.catch_(UnsupportedEncodingException_.TOKEN, (_, _) -> {}),
                                                    o -> o.if_(
                                                            literal(true),
                                                            i -> i.tryTerminated(
                                                                    h -> h.catch_(
                                                                            IOException_.TOKEN, (c, e) -> c.throw_(e)),
                                                                    t -> t.throw_(new_(FileNotFoundException_.new_)))))
                                            .end())),
                    "catch_ of java.io.UnsupportedEncodingException: the try block of try_ in body of static method"
                            + " m cannot throw this checked exception (it can throw: java.io.FileNotFoundException)",
                    "static void m() throws IOException { try { if (true) { try { throw new"
                            + " FileNotFoundException(); } catch (IOException e) { throw e; } } }"
                            + " catch (UnsupportedEncodingException e) { } }",
                    "exception java.io.UnsupportedEncodingException is never thrown in body of corresponding try statement");
        }

        @Test
        void aRethrownBindingOfExceptionThrowsTheCheckedExceptionsOfItsTryBlock() {
            asJavac(
                    () -> declare(cb -> cb.throwing(Exception_.TOKEN)
                            .voidStaticMethod(
                                    "m",
                                    b -> b.try_(
                                                    h -> h.catch_(InterruptedException_.TOKEN, (_, _) -> {}),
                                                    o -> o.try_(
                                                            h -> h.catch_(Exception_.TOKEN, (c, e) -> c.throw_(e)),
                                                            t -> t.exec(read())))
                                            .end())),
                    "catch_ of java.lang.InterruptedException: the try block of try_ in body of static method m"
                            + " cannot throw this checked exception (it can throw: java.io.IOException)",
                    "static void m() throws Exception { try { try { " + READ + "; } catch (Exception e) { throw e; }"
                            + " } catch (InterruptedException e) { } }",
                    "exception java.lang.InterruptedException is never thrown in body of corresponding try statement");
        }

        @Test
        void aClauseBeforeTakesWhatItCatchesFromWhatTheBindingRethrows() {
            // e is no FileNotFoundException: the clause before caught that
            asJavac(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .voidStaticMethod(
                                    "m",
                                    b -> b.try_(
                                                    h -> h.catch_(FileNotFoundException_.TOKEN, (_, _) -> {}),
                                                    o -> o.try_(
                                                            h -> h.catch_(FileNotFoundException_.TOKEN, (_, _) -> {})
                                                                    .catch_(IOException_.TOKEN, (c, e) -> c.throw_(e)),
                                                            t -> t.if_(
                                                                            literal(true),
                                                                            x -> x.throw_(
                                                                                    new_(FileNotFoundException_.new_)))
                                                                    .throw_(new_(UnsupportedEncodingException_.new_))))
                                            .end())),
                    "cannot throw this checked exception (it can throw: java.io.UnsupportedEncodingException)",
                    "static void m() throws IOException { try { try { if (true) { throw new"
                            + " FileNotFoundException(); } throw new UnsupportedEncodingException(); }"
                            + " catch (FileNotFoundException e) { } catch (IOException e) { throw e; } }"
                            + " catch (FileNotFoundException e) { } }",
                    "exception java.io.FileNotFoundException is never thrown in body of corresponding try statement");
        }

        @Test
        void aTryInACatchBlockIsCheckedAgainOnceWhatTheBindingRethrowsIsKnown() {
            // e is an UnsupportedEncodingException, known once the outer try block is built
            asJavac(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .voidStaticMethod(
                                    "m",
                                    b -> b.try_(
                                                    h -> h.catch_(
                                                            IOException_.TOKEN,
                                                            (c, e) -> c.try_(
                                                                    inner -> inner.catch_(
                                                                            FileNotFoundException_.TOKEN, (_, _) -> {}),
                                                                    t -> t.throw_(e))),
                                                    t -> t.throw_(new_(UnsupportedEncodingException_.new_)))
                                            .end())),
                    "catch_ of java.io.FileNotFoundException: the try block of try_ in catch block of try_ in body"
                            + " of static method m cannot throw this checked exception (it can throw:"
                            + " java.io.UnsupportedEncodingException)",
                    "static void m() throws IOException { try { throw new UnsupportedEncodingException(); }"
                            + " catch (IOException e) { try { throw e; } catch (FileNotFoundException x) { } } }",
                    "exception java.io.FileNotFoundException is never thrown in body of corresponding try statement");
        }

        @Test
        void aBindingThatIsCastIsThrownAtTheTypeOfTheCast() {
            // throw (FileNotFoundException) e; is no rethrow of the binding, which would be an
            // UnsupportedEncodingException
            asJavacAccepts(
                    () -> declare(cb -> cb.voidStaticMethod(
                            "m",
                            b -> b.try_(
                                            h -> h.catch_(FileNotFoundException_.TOKEN, (_, _) -> {}),
                                            o -> o.tryTerminated(
                                                    h -> h.catch_(
                                                            IOException_.TOKEN,
                                                            (c, e) -> c.throw_(
                                                                    castChecked(FileNotFoundException_.TOKEN, e))),
                                                    t -> t.throw_(new_(UnsupportedEncodingException_.new_))))
                                    .end())),
                    "static void m() { try { try { throw new UnsupportedEncodingException(); }"
                            + " catch (IOException e) { throw (FileNotFoundException) e; } }"
                            + " catch (FileNotFoundException x) { } }");
        }

        @Test
        void whereItIsThrownARethrownBindingMustBeCoveredAtTheTypeOfItsClause() {
            // stricter: the try block of the clause is not built yet when throw_(e) is
            unlikeJavac(
                    () -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .staticMethod(
                                    "m",
                                    PrimitiveToken.INT,
                                    b -> b.tryTerminated(
                                            h -> h.catch_(Exception_.TOKEN, (c, e) -> c.throw_(e)),
                                            t -> t.return_(read())))),
                    "throw_ in the catch block of tryTerminated in body of static method m can throw the checked"
                            + " exception java.lang.Exception",
                    "static int m() throws IOException { try { return " + READ
                            + "; } catch (Exception e) { throw e; } }");
        }
    }

    /// A type variable in the place of an exception, which no declaration of the typed layer gives
    /// yet: a fact of a generic method takes one for `<X extends Throwable> ... throws X`.
    @Nested
    class TypeVariables {

        private static <X extends Throwable> TypeVarToken<X> variable(String name, ClassToken<?> bound) {
            return UnsafeFacts.typeVarToken(new TypeVarRef(name), bound.erasure());
        }

        /// `static <X extends Throwable> void run() throws X`, with `thrown` for `X`.
        private static VoidStaticMethodRef0 run(TypeVarToken<? extends Throwable> thrown) {
            return UnsafeFacts.voidStaticMethod(
                    Object_.TOKEN, "run", MemberTraits.FINAL.throwing(thrown).withTypeArgs(thrown));
        }

        private static ThrowingCallable calling(
                TypeVarToken<? extends Throwable> thrown, List<ExceptionType> declared) {
            VoidBody root = VoidBody.root(
                    "body of method m", new ExceptionScope.Declares(ExceptionScope.Boundary.METHOD, declared));
            return () -> Scopes.within(root, () -> root.exec(voidStaticCall(run(thrown))));
        }

        private static List<ExceptionType> classes(ClassToken<?>... types) {
            return Stream.of(types)
                    .<ExceptionType>map(ExceptionType.OfClass::new)
                    .toList();
        }

        @Test
        void aTypeVariableIsCheckedUnlessItsBoundIsRuntimeExceptionOrError() {
            assertThatIllegalStateException()
                    .isThrownBy(calling(variable("X", IOException_.TOKEN), List.of()))
                    .withMessageContaining("can throw the checked exception X");
            assertThatCode(calling(variable("X", RuntimeException_.TOKEN), List.of()))
                    .doesNotThrowAnyException();
        }

        @Test
        void aTypeVariableIsCoveredByItselfItsBoundAndThrowable() {
            TypeVarToken<IOException> x = variable("X", IOException_.TOKEN);

            assertThatCode(calling(x, List.of(new ExceptionType.OfVariable(x)))).doesNotThrowAnyException();
            assertThatCode(calling(x, classes(IOException_.TOKEN))).doesNotThrowAnyException();
            assertThatCode(calling(x, classes(Throwable_.TOKEN))).doesNotThrowAnyException();
        }

        @Test
        void aTypeVariableIsNotCoveredByAnotherOneNorByASuperclassOfItsBoundThoughJavaCoversIt() {
            // stricter: a token of a type variable knows the class of its bound, not its superclasses
            TypeVarToken<IOException> x = variable("X", IOException_.TOKEN);

            assertThatIllegalStateException()
                    .isThrownBy(calling(x, List.of(new ExceptionType.OfVariable(variable("Y", IOException_.TOKEN)))))
                    .withMessageContaining("declared: Y");
            assertThatIllegalStateException()
                    .isThrownBy(calling(x, classes(Exception_.TOKEN)))
                    .withMessageContaining("declared: java.lang.Exception");
            CompilationSubject.assertThat(
                            compiled("static <X extends IOException> void m(X x) throws Exception { throw x; }"))
                    .succeeded();
        }

        @Test
        void aTypeVariableCoversNoClass() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> {
                        VoidBody root = VoidBody.root(
                                "body of method m",
                                new ExceptionScope.Declares(
                                        ExceptionScope.Boundary.METHOD,
                                        List.of(new ExceptionType.OfVariable(variable("X", IOException_.TOKEN)))));
                        Scopes.within(root, () -> root.exec(read()));
                    })
                    .withMessageContaining("the checked exception java.io.IOException");
        }

        @Test
        void whatIsNoClassAndNoTypeVariableIsNoExceptionType() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ExceptionType.of(PrimitiveToken.INT))
                    .withMessageContaining("not an exception type: int");
        }
    }

    @Nested
    class Lambdas {

        private static ExceptionScope.Declares declaring(ClassToken<?> type) {
            return new ExceptionScope.Declares(
                    ExceptionScope.Boundary.METHOD, List.of(new ExceptionType.OfClass(type)));
        }

        @Test
        void aLambdaBodyIsNotCoveredByTheCodeAroundTheLambda() {
            Body<Prim.Int> root = Body.root("body of method m", declaring(IOException_.TOKEN));

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(root, () -> {
                        Body<Prim.Int> lambda = Body.lambdaBody(root);
                        return Scopes.within(lambda, () -> lambda.return_(read()));
                    }))
                    .withMessageContaining("in the lambda body in body of method m can throw the checked exception"
                            + " java.io.IOException")
                    .withMessageContaining("the method of the functional interface of the lambda does not declare"
                            + " (declared: nothing): catch it inside the lambda");
        }

        @Test
        void aLambdaBodyMayThrowWhatTheMethodOfItsFunctionalInterfaceDeclares() {
            Body<Prim.Int> root = Body.root("body of method m");

            assertThatCode(() -> Scopes.within(root, () -> {
                        Body<Prim.Int> lambda = Body.lambdaBody(root, List.of(Exception_.TOKEN));
                        return Scopes.within(lambda, () -> lambda.return_(read()));
                    }))
                    .doesNotThrowAnyException();
        }

        @Test
        void theBodyOfAnExpressionLambdaIsCheckedWhereTheLambdaIsUsed() {
            Body<Prim.Int> root = Body.root("body of method m", declaring(IOException_.TOKEN));
            Body<Prim.Int> scope = Body.lambdaBody(root);
            Node lambda = new Node.Lambda(Object_.hashCode, List.of(), new Node.LambdaBody.Value(scope, read().node()));

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(root, () -> root.append(new Instr.Exec(lambda))))
                    .withMessageContaining("in the lambda body in body of method m can throw the checked exception"
                            + " java.io.IOException");
        }

        @Test
        void aLambdaThrowsNothingWhereItStands() {
            VoidBody root = VoidBody.root("body of method m");
            Body<Prim.Int> scope = Body.lambdaBody(root, List.of(IOException_.TOKEN));
            Node lambda = new Node.Lambda(Object_.hashCode, List.of(), new Node.LambdaBody.Value(scope, read().node()));

            assertThatCode(() -> Scopes.within(root, () -> root.append(new Instr.Exec(lambda))))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class Overrides {

        @Test
        void anOverrideOfAMethodOfObjectDeclaresNoCheckedException() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .method("toString", String_.TOKEN, (b, _) -> b.return_(literal("x")))))
                    .withMessageContaining("overrides toString() of java.lang.Object, which declares no exception:"
                            + " an override cannot declare the checked exception java.io.IOException")
                    .withMessageContaining("JLS 8.4.8.3");
            CompilationSubject.assertThat(compiled("public String toString() throws IOException { return \"x\"; }"))
                    .hadErrorContaining("overridden method does not throw java.io.IOException");
        }

        @Test
        void anOverrideMayDeclareAnUncheckedException() {
            asJavacAccepts(
                    () -> declare(cb -> cb.throwing(RuntimeException_.TOKEN)
                            .method("toString", String_.TOKEN, (b, _) -> b.return_(literal("x")))),
                    "public String toString() throws RuntimeException { return \"x\"; }");
        }
    }
}
