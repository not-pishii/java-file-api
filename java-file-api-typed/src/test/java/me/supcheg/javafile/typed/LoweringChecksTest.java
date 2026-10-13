package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// Runtime/lowering checks (§7) for properties that are structural to
/// lowering itself, not part of the residual §9 checklist: a [Var] out of
/// scope where it is referenced, and a [Block] builder used after its scope
/// closed. The builder already rejects an out-of-scope variable when the
/// statement is built ([ScopeChecksTest]); lowering rejects it again, as
/// defence in depth.
class LoweringChecksTest {

    @Test
    void referencingAVariableNeverDeclaredInThisLoweringFails() {
        // A Var minted independently of any Lowering pass (as if it had
        // escaped the lambda that was supposed to be its only scope) is not
        // in that Lowering's NameEnv.
        Var<Prim.Int> escaped = Var.param(PrimitiveToken.INT, Body.root("elsewhere"));
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());

        assertThrows(IllegalStateException.class, () -> lowering.lowerExpr(new Node.Local(escaped)));
    }

    @Test
    void aVariableIsOutOfScopeOnceItsScopeIsLeft() {
        Var<Prim.Int> inner = Var.param(PrimitiveToken.INT, Body.root("elsewhere"));
        NameEnv names = new NameEnv();
        names.push();
        String name = names.declare(inner);
        assertThat(names.nameOf(inner)).isEqualTo(name);
        names.pop();

        assertThatIllegalStateException()
                .isThrownBy(() -> names.nameOf(inner))
                .withMessageContaining("is not in scope where it is used");
    }

    @Test
    void aLambdaBodySeesTheEnclosingVariables() {
        Body<Prim.Int> root = Body.root("root");
        Var<Prim.Int> outer = Var.param(PrimitiveToken.INT, root);
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());
        String outerName = lowering.declareUpfront(outer);
        Body<Prim.Int> lambdaBody = Body.lambdaBody(root);
        Scopes.within(lambdaBody, () -> lambdaBody.return_(outer));
        Node lambda = new Node.Lambda(Object_.hashCode, List.of(), new Node.LambdaBody.Block(lambdaBody));

        assertThat(lowering.lowerExpr(lambda).toString()).contains(outerName);
    }

    @Test
    void usingABlockBuilderAfterItsScopeClosedFailsFast() {
        Body<Prim.Int> body = Body.root("root");
        Scopes.within(body, () -> body.return_(literal(1)));

        // The scope has since been popped (and the block has ended); any
        // further use of this exact builder instance must fail fast rather
        // than silently append unreachable statements.
        assertThrows(IllegalStateException.class, () -> body.if_(literal(true), _ -> {}));
    }

    @Test
    void aBreakOfALoopThatIsNotBeingLoweredFails() {
        // the builder gives a LoopCtl to the body of its loop alone; one that escaped it is of no loop here
        LoopCtl escaped = new LoopCtl(Body.root("elsewhere"));
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());

        assertThatIllegalStateException()
                .isThrownBy(() -> lowering.lowerBlock(List.of(new Instr.Break(escaped))))
                .withMessageContaining("break/continue used for a loop that is not currently being lowered");
    }

    @Test
    void anExpressionThatIsNoStatementIsNotExecuted() {
        // `1;` is no statement (JLS 14.8): exec takes an Effect, which a literal is none of
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());

        assertThatIllegalStateException()
                .isThrownBy(() ->
                        lowering.lowerBlock(List.of(new Instr.Exec(literal(1).node()))))
                .withMessageContaining("not a statement expression");
    }

    @Test
    void anAssignmentIsNoValue() {
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());
        Node one = literal(1).node();

        assertThatIllegalStateException()
                .isThrownBy(() -> lowering.lowerExpr(new Node.Assign(new Node.Target.Element(one, one), one)))
                .withMessageContaining("an assignment was used as a value");
    }

    @Test
    void aProtectedMemberIsReachedInTheBodyOfNoClass() {
        Lowering lowering = new Lowering(UnsafeFacts.unverifiedClasspath());

        assertThatIllegalStateException()
                .isThrownBy(() -> lowering.lowerExpr(
                        staticCall(UnsafeFacts.open(Base_.motto)).node()))
                .withMessageContaining("is protected: it is reached in the body of a subclass of")
                .withMessageContaining("this code is in the body of no class");
    }

    @Test
    void aProtectedConstructorCreatesNoInstanceInASubclass() {
        // new Base("x") in a subclass of Base in another package: super("x") alone is its access (JLS 6.6.2.2)
        Lowering lowering = new Lowering(
                UnsafeFacts.unverifiedClasspath(),
                new Lowering.Place.InClass(Mid_.TOKEN, new TypeScope.Standalone()),
                Optional.empty());
        Node created = new Node.New(
                Base_.super_String, List.of(new Node.Operand(literal("x").node(), String_.TOKEN)));

        assertThatIllegalStateException()
                .isThrownBy(() -> lowering.lowerExpr(created))
                .withMessageContaining("is protected")
                .withMessageContaining("a class instance creation is no access a subclass has (JLS 6.6.2.2)");
    }

    @Test
    void aMethodOfObjectIsCalledOnAnArrayAsItStands() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Probe"),
                new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                m,
                                b -> b.return_(
                                        call(newArray(ArrayToken.of(String_.TOKEN), literal(1)), Object_.hashCode)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return new String[1].hashCode();");
        assertThat(compiled.invoke("m")).isInstanceOf(Integer.class);
    }
}
