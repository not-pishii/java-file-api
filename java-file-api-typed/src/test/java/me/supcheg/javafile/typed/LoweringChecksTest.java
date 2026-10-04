package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.jdk.Object_;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.supcheg.javafile.typed.Expressions.literal;
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
}
