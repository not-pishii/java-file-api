package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import org.junit.jupiter.api.Test;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// Runtime/lowering checks (§7) for properties that are structural to
/// lowering itself, not part of the residual §9 checklist: a [Var] used
/// outside the lambda scope that introduced it, and a [Block] builder used
/// after its scope closed. Both are representable in plain Java (a
/// reference can always be stored and reused), so unlike the
/// [NegativeCompileTest] fixtures they are only caught at lowering/runtime,
/// exactly as their javadoc says.
class LoweringChecksTest {

    @Test
    void referencingAVariableNeverDeclaredInThisLoweringFails() {
        // A Var minted independently of any Lowering pass (as if it had
        // escaped the lambda that was supposed to be its only scope) is not
        // in that Lowering's NameEnv.
        Var<Prim.Int> escaped = Var.param(PrimitiveToken.INT);
        Lowering lowering = new Lowering();

        assertThrows(IllegalStateException.class, () -> lowering.lowerExpr(new Node.Local(escaped)));
    }

    @Test
    void usingABlockBuilderAfterItsScopeClosedFailsFast() {
        Body<Prim.Int> body = new Body<>();
        Scopes.within(body, () -> body.return_(literal(1)));

        // The scope has since been popped (and the block has ended); any
        // further use of this exact builder instance must fail fast rather
        // than silently append unreachable statements.
        assertThrows(IllegalStateException.class, () -> body.if_(literal(true), b -> {}));
    }
}
