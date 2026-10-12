package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.EmptyStmt;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.neqRef;
import static org.assertj.core.api.Assertions.assertThat;

/// A `finally` block has every statement that completes normally
/// ([FinallyBody]): the block is compiled under `-Xlint:all` — javac warns of
/// a `finally` that cannot complete normally — and run, and tells what it
/// reached.
class FinallyCompileTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static Effect told(Expr<StringBuilder> out, Expr<String> what) {
        return call(out, StringBuilder_.append_String, what);
    }

    @Test
    void theStatementsOfAFinallyBlockRunAfterItsTryBlock() throws Throwable {
        var strings = new List_<>(String_.TOKEN);
        var arrayList = new ArrayList_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var m = cb.declareVoidMethod("m", StringBuilder_.TOKEN, strings.token);
                        var bodies = cb.bodies();
                        bodies.define(
                                m,
                                (b, _, out, list) -> b.try_(
                                                h -> h.finally_(f -> f.let(
                                                                String_.TOKEN,
                                                                literal(" let"),
                                                                v -> f.exec(told(out, v)))
                                                        .letVar(
                                                                String_.TOKEN,
                                                                literal(" var"),
                                                                v -> f.exec(assign(v, concat(v, literal("!"))))
                                                                        .exec(told(out, v)))
                                                        .if_(
                                                                eqRef(out, out),
                                                                t -> t.exec(told(out, literal(" then"))),
                                                                e -> e.exec(told(out, literal(" else"))))
                                                        .ifInstanceOf(
                                                                list,
                                                                arrayList.token,
                                                                (t, _) -> t.exec(told(out, literal(" is"))))
                                                        .doWhile(
                                                                (loop, _) -> loop.exec(told(out, literal(" do"))),
                                                                neqRef(out, out))
                                                        .for_(
                                                                PrimitiveToken.INT,
                                                                literal(0),
                                                                i -> ltInt(i, literal(2)),
                                                                i -> assign(i, addInt(i, literal(1))),
                                                                (loop, _, _) -> loop.exec(told(out, literal(" for"))))
                                                        .forEach(
                                                                String_.TOKEN,
                                                                list,
                                                                (loop, each, _) -> loop.exec(told(out, each)))
                                                        .add(Unsafe.stmt(new EmptyStmt()))),
                                                t -> t.exec(told(out, literal("try"))))
                                        .end());
                        return bodies;
                    }
                }));
        StringBuilder out = new StringBuilder();

        compiled.generated()
                .getMethod("m", StringBuilder.class, List.class)
                .invoke(compiled.generated().getConstructor().newInstance(), out, new ArrayList<>(List.of(" a", " b")));

        assertThat(out).hasToString("try let var! then is do for for a b");
    }
}
