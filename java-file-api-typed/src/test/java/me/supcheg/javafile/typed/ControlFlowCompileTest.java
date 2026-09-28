package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.NumberFormatException_;
import me.supcheg.javafile.facts.jdk.PrintStream_;
import me.supcheg.javafile.facts.jdk.RuntimeException_;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.facts.jdk.System_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.geInt;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;

/// Positive end-to-end compile tests (§11) of the terminating forms
/// (`loopForever`, `tryTerminated`, `ifElse` with `end()`) and of legal
/// nested scoping: what the construction-time checks accept renders to code
/// javac accepts — no "unreachable statement", no "missing return", no
/// variable out of scope, no duplicate name.
class ControlFlowCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "ControlFlow");

    private static Effect print(Expr<Prim.Int> value) {
        return voidCall(staticField(System_.out), PrintStream_.println_int, value);
    }

    private static Compilation compile(JavaFile file) {
        return javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));
    }

    @Test
    void terminatingFormsRenderAndCompile() {
        JavaFile file = TypedJavaFile.class_(DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                // while (true) { if (flag) { return 1; } } — no return after it
                cb.method(
                        "forever",
                        PrimitiveToken.INT,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.loopForever(loop -> loop.if_(flag, t -> t.return_(literal(1)))));

                // try { return Integer.parseInt(s); } catch (NumberFormatException e) { return -1; }
                // finally { System.out.println(); }
                cb.method(
                        "parse",
                        PrimitiveToken.INT,
                        String_.TOKEN,
                        (b, s) -> b.tryTerminated(
                                t -> t.return_(staticCall(Integer_.parseInt, s)),
                                h -> h.catch_(NumberFormatException_.TOKEN, (c, _) -> c.return_(literal(-1)))
                                        .finally_(f ->
                                                f.exec(voidCall(staticField(System_.out), PrintStream_.println)))));

                // if (flag) {} else { return; } — a void body may fall off the end
                cb.voidMethod(
                        "maybe", PrimitiveToken.BOOLEAN, (b, flag) -> b.ifElse(flag, VoidBody::end, VoidBody::return_));

                // a try_ statement whose catch completes normally is continued
                cb.method(
                        "recover",
                        PrimitiveToken.INT,
                        b -> b.try_(
                                        t -> t.return_(literal(1)),
                                        h -> h.catch_(RuntimeException_.TOKEN, (c, _) -> c.exec(print(literal(0)))))
                                .return_(literal(2)));

                // while (true) { if (i >= n) break; i = i + 1; } return i;
                cb.method(
                        "count",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        (b, n) -> b.letVar(
                                PrimitiveToken.INT,
                                literal(0),
                                i -> b.while_(
                                                literal(true),
                                                (loop, ctl) -> loop.if_(geInt(i, n), t -> t.break_(ctl))
                                                        .exec(assign(i, addInt(i, literal(1)))))
                                        .return_(i)));

                // outer: while (flag) { while (true) { break outer; } } return 0;
                cb.method(
                        "labeled",
                        PrimitiveToken.INT,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.while_(
                                        flag, (outer, outerCtl) -> outer.loopForever(inner -> inner.break_(outerCtl)))
                                .return_(literal(0)));

                // do { if (flag) continue; println(1); } while (flag); return 0;
                cb.method(
                        "doLoop",
                        PrimitiveToken.INT,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.doWhile(
                                        (loop, ctl) -> loop.if_(flag, t -> t.continue_(ctl))
                                                .exec(print(literal(1))),
                                        flag)
                                .return_(literal(0)));
            }
        });

        Compilation compilation = compile(file);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(file.render()).contains("while (true)").contains("finally");
    }

    @Test
    void legalNestedScopingRendersAndCompiles() {
        JavaFile file = TypedJavaFile.class_(DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.method(
                        "nested",
                        PrimitiveToken.INT,
                        PrimitiveToken.INT,
                        (b, n) -> b.let(
                                PrimitiveToken.INT,
                                addInt(n, literal(1)),
                                a -> b
                                        // an outer variable in a nested block, and a nested variable in its own block
                                        .if_(
                                                gtInt(a, literal(0)),
                                                t -> t.let(
                                                        PrimitiveToken.INT,
                                                        addInt(a, literal(1)),
                                                        x -> t.if_(ltInt(x, literal(10)), u -> u.exec(print(x)))))
                                        // sibling blocks each declare a variable
                                        .if_(
                                                gtInt(a, literal(1)),
                                                t -> t.let(PrimitiveToken.INT, literal(2), x -> t.exec(print(x))),
                                                e -> e.let(PrimitiveToken.INT, literal(3), x -> e.exec(print(x))))
                                        // the loop variable in the condition, the update, and the body
                                        .for_(
                                                PrimitiveToken.INT,
                                                literal(0),
                                                i -> ltInt(i, a),
                                                i -> assign(i, addInt(i, literal(1))),
                                                (loop, i, _) -> loop.let(
                                                        PrimitiveToken.INT, addInt(i, a), y -> loop.exec(print(y))))
                                        .ifElse(
                                                gtInt(a, n),
                                                t -> t.return_(a),
                                                e -> e.let(PrimitiveToken.INT, literal(4), e::return_))));
            }
        });

        assertThat(compile(file)).succeededWithoutWarnings();
    }
}
