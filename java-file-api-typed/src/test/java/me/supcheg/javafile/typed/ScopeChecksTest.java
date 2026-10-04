package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.Object_;
import me.supcheg.javafile.facts.jdk.PrintStream_;
import me.supcheg.javafile.facts.jdk.RuntimeException_;
import me.supcheg.javafile.facts.jdk.System_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.not;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks (§6.2, §6.3, §9) of what Java types cannot
/// express: a [Var] or [LoopCtl] used out of its scope, a [MutVar] or
/// [LoopCtl] across a lambda boundary, unreachable statements (JLS 14.22), a
/// [Terminated] of another block, and a declaration from inside a method
/// body. Each probe of the phase-1 review is reproduced; each is rejected
/// where the misuse is built, with a message naming what and where.
class ScopeChecksTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// A class with one method `int m(boolean flag)`; `flag` is a condition
    /// javac cannot fold.
    private static JavaFile intMethod(BiFunction<Body<Prim.Int>, Var<Prim.Bool>, Terminated<Prim.Int>> body) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.method("m", PrimitiveToken.INT, PrimitiveToken.BOOLEAN, (b, _, flag) -> body.apply(b, flag));
            }
        });
    }

    private static Effect print(Expr<Prim.Int> value) {
        return voidCall(staticField(System_.out), PrintStream_.println_int, value);
    }

    @Nested
    class VarScope {

        @Test
        void aVarOfASiblingBranchIsRejected() {
            AtomicReference<Var<Prim.Int>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.if_(
                                    flag,
                                    t -> t.let(PrimitiveToken.INT, literal(1), v -> {
                                        stash.set(v);
                                        return t;
                                    }),
                                    e -> e.exec(print(stash.get())))
                            .return_(literal(0))))
                    .withMessageContaining("local variable of type int declared in the then-branch of if_ in body"
                            + " of method m is used in the else-branch of if_ in body of method m")
                    .withMessageContaining("out of scope");
        }

        @Test
        void aVarOfANestedBlockIsRejectedAfterIt() {
            // if (true) { int v0 = 1; } return v0;
            AtomicReference<Var<Prim.Int>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.if_(
                                    literal(true),
                                    t -> t.let(PrimitiveToken.INT, literal(1), v -> {
                                        stash.set(v);
                                        return t;
                                    }))
                            .return_(stash.get())))
                    .withMessageContaining("declared in the then-branch of if_ in body of method m is used in the"
                            + " body of method m");
        }

        @Test
        void aVarStoredInAHostListIsRejectedInAnotherMethod() {
            List<Var<Prim.Int>> host = new ArrayList<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.method("a", PrimitiveToken.INT, PrimitiveToken.INT, (b, _, p) -> {
                                        host.add(p);
                                        return b.return_(p);
                                    });
                                    cb.method("b", PrimitiveToken.INT, (b, _) -> b.return_(host.getFirst()));
                                }
                            }))
                    .withMessageContaining("parameter of type int declared in the body of method a is used in the"
                            + " body of method b");
        }

        @Test
        void aVarInAFieldInitializerIsRejected() {
            List<Var<Prim.Int>> host = new ArrayList<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.method("a", PrimitiveToken.INT, PrimitiveToken.INT, (b, _, p) -> {
                                        host.add(p);
                                        return b.return_(p);
                                    });
                                    cb.field("f", PrimitiveToken.INT, host.getFirst());
                                }
                            }))
                    .withMessageContaining("is used in the initializer of field f");
        }

        @Test
        void aLoopVariableIsRejectedAfterItsLoop() {
            AtomicReference<MutVar<Prim.Int>> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.for_(
                                    PrimitiveToken.INT,
                                    literal(0),
                                    _ -> flag,
                                    i -> assign(i, addInt(i, literal(1))),
                                    (_, i, _) -> stash.set(i))
                            .return_(stash.get())))
                    .withMessageContaining("loop variable of type int declared in the body of for_");
        }

        @Test
        void aVarOfTheEnclosingBlockIsInScope() {
            assertThatCode(() -> intMethod((b, flag) -> b.let(
                            PrimitiveToken.INT,
                            literal(1),
                            v -> b.if_(flag, t -> t.if_(flag, u -> u.exec(print(v))))
                                    .return_(v))))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class LambdaBoundary {

        @Test
        void aMutVarIsNotReadAcrossALambdaBoundary() {
            Body<Prim.Int> root = Body.root("body of method m");

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(
                            root,
                            () -> root.letVar(PrimitiveToken.INT, literal(0), mv -> {
                                Body<Prim.Int> lambda = Body.lambdaBody(root);
                                return Scopes.within(lambda, () -> lambda.return_(mv));
                            })))
                    .withMessageContaining("mutable local variable of type int of the body of method m is used in"
                            + " the lambda body in body of method m, across a lambda boundary");
        }

        @Test
        void aMutVarIsNotAssignedAcrossALambdaBoundary() {
            VoidBody root = VoidBody.root("body of method m");

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(
                            root,
                            () -> root.letVar(PrimitiveToken.INT, literal(0), mv -> {
                                VoidBody lambda = VoidBody.lambdaBody(root);
                                return Scopes.within(lambda, () -> lambda.exec(assign(mv, literal(1))));
                            })))
                    .withMessageContaining("across a lambda boundary");
        }

        @Test
        void anImmutableVarIsCapturedAcrossALambdaBoundary() {
            Body<Prim.Int> root = Body.root("body of method m");

            assertThatCode(() -> Scopes.within(
                            root,
                            () -> root.let(PrimitiveToken.INT, literal(0), v -> {
                                Body<Prim.Int> lambda = Body.lambdaBody(root);
                                return Scopes.within(lambda, () -> lambda.return_(v));
                            })))
                    .doesNotThrowAnyException();
        }

        @Test
        void aLoopCtlIsNotUsedAcrossALambdaBoundary() {
            VoidBody root = VoidBody.root("body of method m");

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(
                            root,
                            () -> root.while_(literal(true), (loop, ctl) -> {
                                VoidBody lambda = VoidBody.lambdaBody(loop);
                                Scopes.within(lambda, () -> lambda.break_(ctl));
                            })))
                    .withMessageContaining("break_ in the lambda body in body of while_ in body of method m targets"
                            + " the loop of the body of while_ in body of method m across a lambda boundary");
        }

        @Test
        void aLambdaIsNotUsedOutsideTheBlockItWasBuiltIn() {
            Body<Prim.Int> root = Body.root("body of method m");
            AtomicReference<Body<Prim.Int>> lambdaBody = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> Scopes.within(root, () -> {
                        root.if_(literal(true), t -> {
                            Body<Prim.Int> lambda = Body.lambdaBody(t);
                            Scopes.within(lambda, () -> lambda.return_(literal(1)));
                            lambdaBody.set(lambda);
                        });
                        Node lambda = new Node.Lambda(
                                Object_.hashCode, List.of(), new Node.LambdaBody.Block(lambdaBody.get()));
                        return root.let(Object_.TOKEN, Expr.of(lambda, Object_.TOKEN), _ -> root);
                    }))
                    .withMessageContaining("a lambda built in the then-branch of if_ in body of method m is used in"
                            + " the body of method m");
        }
    }

    @Nested
    class LoopCtlScope {

        @Test
        void aLoopCtlIsRejectedAfterItsLoop() {
            AtomicReference<LoopCtl> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> {
                        b.while_(flag, (_, ctl) -> stash.set(ctl));
                        return b.break_(stash.get());
                    }))
                    .withMessageContaining("break_ in the body of method m targets the loop of the body of while_"
                            + " in body of method m, which does not enclose it");
        }

        @Test
        void aLoopCtlIsRejectedInASiblingLoop() {
            AtomicReference<LoopCtl> stash = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.while_(flag, (_, ctl) -> stash.set(ctl))
                            .while_(flag, (loop, _) -> loop.continue_(stash.get()))
                            .return_(literal(0))))
                    .withMessageContaining("continue_ in the body of while_ in body of method m targets the loop");
        }
    }

    @Nested
    class ReachabilityChecks {

        @Test
        void anIfWhoseBranchesBothEndIsRejectedWithAnIfElseHint() {
            // if (c) return 1; else return 2; return 3;
            assertThatIllegalStateException()
                    .isThrownBy(() ->
                            intMethod((b, flag) -> b.if_(flag, t -> t.return_(literal(1)), e -> e.return_(literal(2)))
                                    .return_(literal(3))))
                    .withMessageContaining(
                            "if_ whose branches both end in the body of method m cannot complete" + " normally")
                    .withMessageContaining("build it with ifElse");
        }

        @Test
        void aTryWhoseBranchesAllEndIsRejectedWithATryTerminatedHint() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.try_(
                                    t -> t.return_(literal(1)),
                                    h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.return_(literal(2))))
                            .return_(literal(3))))
                    .withMessageContaining("cannot complete normally")
                    .withMessageContaining("build it with tryTerminated");
        }

        @Test
        void aTryWhoseFinallyEndsIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.try_(
                                    t -> t.exec(print(literal(1))), h -> h.finally_(f -> f.return_(literal(2))))
                            .return_(literal(3))))
                    .withMessageContaining("build it with tryTerminated");
        }

        @Test
        void aWhileOverConstantFalseIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(
                            (b, _) -> b.while_(literal(false), (_, _) -> {}).return_(literal(0))))
                    .withMessageContaining("the body of while_ with a constant false condition is unreachable");
        }

        @Test
        void aForOverConstantFalseIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.for_(
                                    PrimitiveToken.INT,
                                    literal(0),
                                    _ -> not(literal(true)),
                                    i -> assign(i, addInt(i, literal(1))),
                                    (_, _, _) -> {})
                            .return_(literal(0))))
                    .withMessageContaining("the body of for_ with a constant false condition is unreachable");
        }

        @Test
        void aWhileOverConstantTrueWithoutBreakIsRejectedWithALoopForeverHint() {
            // while (true) {} return 0;
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(
                            (b, _) -> b.while_(literal(true), (_, _) -> {}).return_(literal(0))))
                    .withMessageContaining("while_ with a constant true condition and no break_")
                    .withMessageContaining("loopForever");
        }

        @Test
        void aConstantConditionIsFoldedAsJavacFoldsIt() {
            // while (Integer.MAX_VALUE > 0) {} — a constant variable
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.while_(
                                    gtInt(staticField(Integer_.MAX_VALUE), literal(0)), (_, _) -> {})
                            .return_(literal(0))))
                    .withMessageContaining("loopForever");
        }

        @Test
        void aWhileOverConstantTrueWithABreakCompletes() {
            assertThatCode(() -> intMethod((b, flag) -> b.while_(
                                    literal(true), (loop, ctl) -> loop.if_(flag, t -> t.break_(ctl)))
                            .return_(literal(0))))
                    .doesNotThrowAnyException();
        }

        @Test
        void aBreakSwallowedByAFinallyThatEndsDoesNotExitTheLoop() {
            // while (true) { try { break; } finally { continue; } } — the loop never exits
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> b.while_(
                                    literal(true),
                                    (loop, ctl) -> loop.tryTerminated(
                                            t -> t.break_(ctl), h -> h.finally_(f -> f.continue_(ctl))))
                            .return_(literal(0))))
                    .withMessageContaining("loopForever");
        }

        @Test
        void aDoWhileWhoseBodyEndsIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.doWhile((loop, _) -> loop.return_(literal(1)), flag)
                            .return_(literal(0))))
                    .withMessageContaining("doWhile");
        }

        @Test
        void aStatementAfterAnEndingConstructIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> {
                        Terminated<Prim.Int> ended =
                                b.ifElse(flag, t -> t.return_(literal(1)), e -> e.return_(literal(2)));
                        b.exec(print(literal(3)));
                        return ended;
                    }))
                    .withMessageContaining("the body of method m has already ended with ifElse; a statement after"
                            + " it would be unreachable");
        }

        @Test
        void aStatementAfterLoopForeverIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> {
                        Terminated<Prim.Int> ended = b.loopForever(loop -> loop.exec(print(literal(1))));
                        b.exec(print(literal(2)));
                        return ended;
                    }))
                    .withMessageContaining("has already ended with loopForever");
        }

        @Test
        void aSecondFinallyIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod(
                            (b, _) -> b.try_(_ -> {}, h -> h.finally_(_ -> {}).finally_(_ -> {}))
                                    .return_(literal(0))))
                    .withMessageContaining("already has a finally_ block");
        }
    }

    @Nested
    class ForeignTerminated {

        @Test
        void aTokenOfTheSiblingBranchIsRejected() {
            AtomicReference<Terminated<Prim.Int>> then = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.ifElse(
                            flag,
                            t -> {
                                then.set(t.return_(literal(1)));
                                return then.get();
                            },
                            _ -> then.get())))
                    .withMessageContaining("the Terminated token handed back for the else-branch of ifElse in body"
                            + " of method m was issued by the then-branch of ifElse in body of method m");
        }

        @Test
        void aTokenOfANestedBlockIsRejectedAsTheBodys() {
            AtomicReference<Terminated<Prim.Int>> nested = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> {
                        b.if_(flag, t -> nested.set(t.return_(literal(1))));
                        return nested.get();
                    }))
                    .withMessageContaining("handed back for the body of method m was issued by the then-branch of"
                            + " if_ in body of method m");
        }

        @Test
        void aTokenOfANestedBlockIsRejectedFromLet() {
            AtomicReference<Terminated<Prim.Int>> nested = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, flag) -> b.ifElse(
                            flag,
                            t -> t.let(PrimitiveToken.INT, literal(1), v -> {
                                t.if_(flag, u -> nested.set(u.return_(v)));
                                return nested.get();
                            }),
                            e -> e.return_(literal(2)))))
                    .withMessageContaining("was issued by the then-branch of if_ in then-branch of ifElse");
        }

        @Test
        void aTokenOfAnotherMethodIsRejected() {
            AtomicReference<Terminated<Prim.Int>> other = new AtomicReference<>();

            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.method("a", PrimitiveToken.INT, (b, _) -> {
                                        other.set(b.return_(literal(1)));
                                        return other.get();
                                    });
                                    cb.method("b", PrimitiveToken.INT, (_, _) -> other.get());
                                }
                            }))
                    .withMessageContaining("handed back for the body of method b was issued by the body of method a");
        }
    }

    @Nested
    class Declarations {

        @Test
        void aClassDeclaredInsideAMethodBodyIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> intMethod((b, _) -> {
                        TypedJavaFile.class_(
                                UnsafeFacts.unverifiedClasspath(),
                                ClassDesc.of("me.supcheg.example", "Inner"),
                                new TypedJavaFile.TypedClassSpec() {
                                    @Override
                                    public <Self> void build(TypedClassBuilder<Self> cb) {}
                                });
                        return b.return_(literal(0));
                    }))
                    .withMessageContaining("cannot declare class me.supcheg.example.Inner while the body of method m"
                            + " is being built");
        }

        @Test
        void aMemberDeclaredInsideAMethodBodyIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.method(
                                            "outer",
                                            PrimitiveToken.INT,
                                            (b, _) -> b.if_(
                                                            literal(true),
                                                            _ -> cb.method(
                                                                    "inner",
                                                                    PrimitiveToken.INT,
                                                                    (x, _) -> x.return_(literal(1))))
                                                    .return_(literal(0)));
                                }
                            }))
                    .withMessageContaining("cannot declare int me.supcheg.example.Probe.inner()")
                    .withMessageContaining("while the then-branch of if_ in body of method outer is being built");
        }

        @Test
        void aMemberDeclaredAfterTheClassWasBuiltIsRejected() {
            List<TypedClassBuilder<?>> leaked = new ArrayList<>();
            TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, leaked::add);

            assertThatIllegalStateException()
                    .isThrownBy(() -> leaked.getFirst().field("late", PrimitiveToken.INT, literal(1)))
                    .withMessageContaining("the declaration of class me.supcheg.example.Probe is already complete");
        }
    }
}
