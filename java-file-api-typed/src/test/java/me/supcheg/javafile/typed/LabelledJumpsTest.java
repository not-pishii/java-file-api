package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static org.assertj.core.api.Assertions.assertThat;

/// `break` and `continue` of an outer loop from an inner one are labelled with the outer loop; compiled
/// and run, each jumps where it says.
class LabelledJumpsTest {

    @Test
    void anInnerLoopBreaksAndContinuesTheOuterOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Jumps"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var continued = cb.declareStaticMethod("continued", PrimitiveToken.INT);
                        var broken = cb.declareStaticMethod("broken", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        // for i < 3: for j < 3: if j == 1 continue outer; count++
                        bodies.define(
                                continued,
                                b -> b.letVar(
                                        PrimitiveToken.INT,
                                        literal(0),
                                        count -> b.for_(
                                                        PrimitiveToken.INT,
                                                        literal(0),
                                                        i -> ltInt(i, literal(3)),
                                                        i -> assign(i, addInt(i, literal(1))),
                                                        (outer, i, outerCtl) -> outer.for_(
                                                                PrimitiveToken.INT,
                                                                literal(0),
                                                                j -> ltInt(j, literal(3)),
                                                                j -> assign(j, addInt(j, literal(1))),
                                                                (inner, j, innerCtl) -> inner.if_(
                                                                                eqInt(j, literal(1)),
                                                                                t -> t.continue_(outerCtl))
                                                                        .exec(assign(
                                                                                count, addInt(count, literal(1))))))
                                                .return_(count)));
                        // for i < 3: for j < 3: if j == 1 break outer; count++
                        bodies.define(
                                broken,
                                b -> b.letVar(
                                        PrimitiveToken.INT,
                                        literal(0),
                                        count -> b.for_(
                                                        PrimitiveToken.INT,
                                                        literal(0),
                                                        i -> ltInt(i, literal(3)),
                                                        i -> assign(i, addInt(i, literal(1))),
                                                        (outer, i, outerCtl) -> outer.for_(
                                                                PrimitiveToken.INT,
                                                                literal(0),
                                                                j -> ltInt(j, literal(3)),
                                                                j -> assign(j, addInt(j, literal(1))),
                                                                (inner, j, innerCtl) -> inner.if_(
                                                                                eqInt(j, literal(1)),
                                                                                t -> t.break_(outerCtl))
                                                                        .exec(assign(
                                                                                count, addInt(count, literal(1))))))
                                                .return_(count)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("continue loop").contains("break loop");
        assertThat(compiled.invoke("continued")).isEqualTo(3);
        assertThat(compiled.invoke("broken")).isEqualTo(1);
    }
}
