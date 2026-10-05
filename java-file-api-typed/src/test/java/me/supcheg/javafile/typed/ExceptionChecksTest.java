package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.io.FileNotFoundException_;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.InterruptedException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of checked exceptions (§9.1, JLS 11.2.3), which
/// Java types cannot express: a checked exception that is neither caught nor
/// declared, and a `catch` clause that can catch nothing. Each is rejected
/// where the statement or the clause is built, with a message naming what
/// throws, where, and what to do.
class ExceptionChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// Declares the class `Probe` with the given members.
    private static void declare(Consumer<TypedClassBuilder<?>> members) {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, members::accept);
    }

    /// Declares `static int m()` with the given body.
    private static void intMethod(Function<Body<Prim.Int>, Terminated<Prim.Int>> body) {
        declare(cb -> cb.staticMethod("m", PrimitiveToken.INT, body));
    }

    /// `new StringReader("a").read()`, which throws `IOException`.
    private static Invocation<Prim.Int> read() {
        return call(reader(), StringReader_.read);
    }

    private static Invocation<StringReader> reader() {
        return new_(StringReader_.new_String, literal("a"));
    }

    @Nested
    class CaughtOrDeclared {

        @Test
        void aCallThatThrowsACheckedExceptionIsRejectedWhereNothingCatchesOrDeclaresIt() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.return_(read())))
                    .withMessageContaining("int java.io.StringReader.read() in the body of static method m can throw"
                            + " the checked exception java.io.IOException")
                    .withMessageContaining("declared: nothing")
                    .withMessageContaining("cb.throwing(...)")
                    .withMessageContaining("JLS 11.2.3");
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
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(
                            b -> b.return_(call(call(literal("a"), String_.repeat_int, read()), String_.length))))
                    .withMessageContaining("java.io.IOException");
        }

        @Test
        void aConstructorThatThrowsACheckedExceptionIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var ctor = cb.throwing(IOException_.TOKEN).constructor((b, _) -> b.end());
                        cb.voidStaticMethod("m", b -> b.exec(new_(ctor)).end());
                    }))
                    .withMessageContaining("new me.supcheg.example.Probe() in the body of static method m can throw"
                            + " the checked exception java.io.IOException");
        }

        @Test
        void aThrowOfACheckedExceptionIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.throw_(new_(IOException_.new_))))
                    .withMessageContaining("throw_ in the body of static method m can throw the checked exception"
                            + " java.io.IOException");
        }

        @Test
        void aThrowIsCheckedByTheStaticTypeOfItsExpression() {
            // Throwable t = new RuntimeException(); throw t;
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.let(Throwable_.TOKEN, new_(RuntimeException_.new_), b::throw_)))
                    .withMessageContaining("the checked exception java.lang.Throwable");
        }

        @Test
        void anUncheckedExceptionIsNotTracked() {
            assertThatCode(() -> intMethod(b -> b.throw_(new_(RuntimeException_.new_))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aDeclaredSuperclassCoversTheException() {
            assertThatCode(() -> declare(cb -> cb.throwing(Exception_.TOKEN)
                            .staticMethod(
                                    "m",
                                    PrimitiveToken.INT,
                                    b -> b.exec(read()).throw_(new_(FileNotFoundException_.new_)))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aDeclaredSubclassDoesNotCoverTheException() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.throwing(FileNotFoundException_.TOKEN)
                            .staticMethod("m", PrimitiveToken.INT, b -> b.return_(read()))))
                    .withMessageContaining("the checked exception java.io.IOException")
                    .withMessageContaining("declared: java.io.FileNotFoundException");
        }

        @Test
        void anExceptionAMemberOfTheClassDeclaresIsCheckedAtItsCalls() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> {
                        var read = cb.throwing(IOException_.TOKEN).declareStaticMethod("read", PrimitiveToken.INT);
                        cb.staticMethod("m", PrimitiveToken.INT, b -> b.return_(staticCall(read)));
                    }))
                    .withMessageContaining("static int me.supcheg.example.Probe.read() in the body of static method"
                            + " m can throw the checked exception java.io.IOException");
        }

        @Test
        void theBodyOfAMemberMayThrowWhatItsDeclarationDeclaresWhereverItIsDefined() {
            // declared through the view, defined through the builder itself
            assertThatCode(() -> declare(cb -> define(cb))).doesNotThrowAnyException();
        }

        private static <Self> void define(TypedClassBuilder<Self> cb) {
            var read = cb.throwing(IOException_.TOKEN).declareMethod("read", PrimitiveToken.INT);
            cb.define(read, (b, _) -> b.return_(read()));
        }

        @Test
        void aFieldInitializerThrowsNoCheckedException() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> declare(cb -> cb.field("first", PrimitiveToken.INT, read())))
                    .withMessageContaining("int java.io.StringReader.read() in the initializer of field first can"
                            + " throw the checked exception java.io.IOException")
                    .withMessageContaining("an initializer does neither");
        }
    }

    @Nested
    class TryBlocks {

        @Test
        void aCatchOfAnotherExceptionDoesNotCover() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(read()),
                            h -> h.catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .withMessageContaining("in the try block of tryTerminated in body of static method m can throw"
                            + " the checked exception java.io.IOException");
        }

        @Test
        void aCatchOfASubclassDoesNotCover() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(read()),
                            h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .withMessageContaining("the checked exception java.io.IOException");
        }

        @Test
        void anExceptionThrownInACatchBlockIsNotCaughtByItsOwnTry() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(read()), h -> h.catch_(IOException_.TOKEN, (c, e) -> c.throw_(e)))))
                    .withMessageContaining("throw_ in the catch block of tryTerminated in body of static method m"
                            + " can throw the checked exception java.io.IOException");
        }

        @Test
        void anExceptionThrownInAFinallyBlockIsNotCaughtByItsOwnTry() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(
                                    t -> t.exec(read()),
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {})
                                            .finally_(f -> f.exec(read())))
                            .return_(literal(0))))
                    .withMessageContaining("in the finally block of try_ in body of static method m can throw");
        }

        @Test
        void anEnclosingTryCatchesWhatTheInnerOneDoesNot() {
            assertThatCode(() -> intMethod(b -> b.tryTerminated(
                            outer -> outer.try_(
                                            t -> t.exec(read()),
                                            h -> h.catch_(FileNotFoundException_.TOKEN, (_, _) -> {}))
                                    .return_(literal(1)),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aLoopOrBranchInATryBlockIsCovered() {
            assertThatCode(() -> intMethod(b -> b.try_(
                                    t -> t.if_(
                                            literal(true),
                                            x -> x.while_(
                                                    call(literal("a"), String_.isEmpty),
                                                    (loop, _) -> loop.exec(read()))),
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {}))
                            .return_(literal(0))))
                    .doesNotThrowAnyException();
        }

        @Test
        void theHandlersAreBuiltBeforeTheTryBlock() {
            List<String> order = new CopyOnWriteArrayList<>();

            intMethod(b -> b.try_(
                            t -> {
                                order.add("try");
                                t.exec(read());
                            },
                            h -> h.catch_(IOException_.TOKEN, (_, _) -> order.add("catch"))
                                    .finally_(_ -> order.add("finally")))
                    .return_(literal(0)));

            assertThat(order).containsExactly("catch", "finally", "try");
        }

        @Test
        void aClauseAddedAfterTheTryBlockIsRejected() {
            AtomicReference<Handlers<Body<Prim.Int>>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.try_(
                                    t -> stash.get().catch_(IOException_.TOKEN, (_, _) -> {}),
                                    h -> stash.set(h.finally_(_ -> {})))
                            .return_(literal(0))))
                    .withMessageContaining("catch_ after the try block of try_ was built");
        }
    }

    @Nested
    class CatchClausesThatCatchNothing {

        @Test
        void aCatchOfACheckedExceptionTheTryBlockCannotThrowIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(literal(1)),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .withMessageContaining("catch_ of java.io.IOException in tryTerminated: the try block cannot"
                            + " throw this checked exception (it can throw: nothing)");
        }

        @Test
        void aCatchOfAnUnrelatedCheckedExceptionIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(read()),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .withMessageContaining("catch_ of java.lang.InterruptedException in tryTerminated")
                    .withMessageContaining("it can throw: java.io.IOException");
        }

        @Test
        void aCatchOfExceptionThrowableOrAnUncheckedClassIsAlwaysAllowed() {
            assertThatCode(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(literal(1)),
                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(Exception_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(Throwable_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aCatchOfASubclassOfWhatTheTryBlockThrowsIsAllowed() {
            // read() declares IOException, which may be a FileNotFoundException at run time
            assertThatCode(() -> declare(cb -> cb.throwing(IOException_.TOKEN)
                            .staticMethod(
                                    "m",
                                    PrimitiveToken.INT,
                                    b -> b.tryTerminated(
                                            t -> t.return_(read()),
                                            h -> h.catch_(
                                                    FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0)))))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aCatchAfterTheOneOfItsSuperclassIsRejectedWhenItIsAdded() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(read()),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(1))))))
                    .withMessageContaining("catch_ of java.io.FileNotFoundException in tryTerminated comes after"
                            + " the catch_ of java.io.IOException, which has already caught it");
        }

        @Test
        void aCatchOfTheSameTypeTwiceIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.return_(literal(1)),
                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(1))))))
                    .withMessageContaining("has already caught it");
        }

        @Test
        void aCatchWhoseExceptionsThePrecedingClausesHaveAllCaughtIsRejected() {
            // try { throw new FileNotFoundException(); } catch (FileNotFoundException e) {} catch (IOException e) {}
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            t -> t.throw_(new_(FileNotFoundException_.new_)),
                            h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(0)))
                                    .catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(1))))))
                    .withMessageContaining("catch_ of java.io.IOException in tryTerminated is unreachable")
                    .withMessageContaining("java.io.FileNotFoundException");
        }

        @Test
        void theClausesOfATryBlockWithUntypedCodeAreNotChecked() {
            // what an Unsafe statement or expression throws is not known
            UnsafeStmt statement =
                    Unsafe.stmt(new ThrowStmt(Exprs.new_(Types.of(ClassDesc.of("java.io.IOException")))));
            Expr<Prim.Int> expression = Unsafe.expr(Exprs.literal(1), PrimitiveToken.INT);

            assertThatCode(() -> intMethod(b -> b.try_(
                                    t -> t.if_(literal(true), x -> x.add(statement)),
                                    h -> h.catch_(IOException_.TOKEN, (_, _) -> {}))
                            .tryTerminated(
                                    t -> t.return_(expression),
                                    h -> h.catch_(InterruptedException_.TOKEN, (c, _) -> c.return_(literal(0))))))
                    .doesNotThrowAnyException();
        }

        @Test
        void whatAnInnerTryCatchesIsNotThrownToTheOuterOne() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            outer -> outer.try_(t -> t.exec(read()), h -> h.catch_(IOException_.TOKEN, (_, _) -> {}))
                                    .return_(literal(1)),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))))))
                    .withMessageContaining("the try block cannot throw this checked exception");
        }

        @Test
        void whatACatchBlockThrowsIsThrownToTheOuterTry() {
            assertThatCode(() -> intMethod(b -> b.tryTerminated(
                            outer -> outer.try_(
                                            t -> t.exec(call(literal("a"), String_.length)),
                                            h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.exec(read())))
                                    .return_(literal(1)),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))))))
                    .doesNotThrowAnyException();
        }

        @Test
        void whatAFinallyThatCannotCompleteNormallyDiscardsIsNotThrownToTheOuterTry() {
            // try { try { read(); return 1; } finally { return 0; } } catch (IOException e) { return 2; }
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(b -> b.tryTerminated(
                            outer -> outer.tryTerminated(
                                    t -> t.return_(read()), h -> h.finally_(f -> f.return_(literal(0)))),
                            h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))))))
                    .withMessageContaining("the try block cannot throw this checked exception");
        }
    }

    @Nested
    class Lambdas {

        @Test
        void aLambdaBodyIsNotCoveredByTheCodeAroundTheLambda() {
            Body<Prim.Int> root = Body.root("body of method m", List.of(IOException_.TOKEN));

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(root, () -> {
                        Body<Prim.Int> lambda = Body.lambdaBody(root);
                        return Scopes.within(lambda, () -> lambda.return_(read()));
                    }))
                    .withMessageContaining("in the lambda body in body of method m can throw the checked exception"
                            + " java.io.IOException")
                    .withMessageContaining("catch it inside the lambda");
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
            Body<Prim.Int> root = Body.root("body of method m", List.of(IOException_.TOKEN));
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
        }

        @Test
        void anOverrideMayDeclareAnUncheckedException() {
            assertThatCode(() -> declare(cb -> cb.throwing(RuntimeException_.TOKEN)
                            .method("toString", String_.TOKEN, (b, _) -> b.return_(literal("x")))))
                    .doesNotThrowAnyException();
        }
    }
}
