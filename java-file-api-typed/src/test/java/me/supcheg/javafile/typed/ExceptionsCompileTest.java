package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.io.FileNotFoundException_;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.Reader_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.InterruptedException_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// Positive end-to-end tests of checked exceptions (§9.1): what the
/// exception check accepts — a checked exception declared with
/// [TypedClassBuilder#throwing] or caught by a `catch_` — renders to code
/// javac accepts under every lint, and the code throws and catches at run
/// time as it reads.
class ExceptionsCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Throwing");

    private static CompiledClasses compiled;

    @BeforeAll
    static void compile() {
        compiled = CompiledClasses.of(
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        members(cb);
                    }
                }));
    }

    private static <Self> void members(TypedClassBuilder<Self> cb) {
        // public Throwing() throws IOException { new StringReader("a").read(); }
        var ctor = cb.throwing(IOException_.TOKEN)
                .constructor((b, _) -> b.exec(call(new_(StringReader_.new_String, literal("a")), StringReader_.read))
                        .end());

        // static int made() { try { new Throwing(); return 1; } catch (IOException e) { return 0; } }
        cb.staticMethod(
                "made",
                PrimitiveToken.INT,
                b -> b.tryTerminated(
                        h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0))),
                        t -> t.exec(new_(ctor)).return_(literal(1))));

        // static int declared(String s) throws IOException { return new StringReader(s).read(); }
        var declared =
                cb.throwing(IOException_.TOKEN).declareStaticMethod("declared", PrimitiveToken.INT, String_.TOKEN);

        // static int twice(String s) throws IOException { return declared(s) + declared(s); }
        cb.throwing(IOException_.TOKEN)
                .staticMethod(
                        "twice",
                        PrimitiveToken.INT,
                        String_.TOKEN,
                        (b, s) -> b.return_(addInt(staticCall(declared, s), staticCall(declared, s))));
        cb.define(declared, (b, s) -> b.return_(call(new_(StringReader_.new_String, s), StringReader_.read)));

        // static int caught(String s) {
        //     StringReader r = new StringReader(s); r.close();
        //     try { return r.read(); } catch (IOException e) { return -1; }
        // }
        cb.staticMethod(
                "caught",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.let(
                        StringReader_.TOKEN,
                        new_(StringReader_.new_String, s),
                        r -> b.exec(voidCall(r, StringReader_.close))
                                .tryTerminated(
                                        h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(-1))),
                                        t -> t.return_(call(r, StringReader_.read)))));

        // static String bySuperclass(String s) {
        //     try { throw new FileNotFoundException(s); } catch (IOException e) { return e.getMessage(); }
        // }
        cb.staticMethod(
                "bySuperclass",
                String_.TOKEN,
                String_.TOKEN,
                (b, s) -> b.tryTerminated(
                        h -> h.catch_(IOException_.TOKEN, (c, e) -> c.return_(call(e, Throwable_.getMessage))),
                        t -> t.throw_(new_(FileNotFoundException_.new_String, s))));

        // static int ordered(boolean missing) {
        //     try { if (missing) { throw new FileNotFoundException(); } throw new IOException(); }
        //     catch (FileNotFoundException e) { return 1; } catch (IOException e) { return 2; }
        // }
        cb.staticMethod(
                "ordered",
                PrimitiveToken.INT,
                PrimitiveToken.BOOLEAN,
                (b, missing) -> b.tryTerminated(
                        h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(1)))
                                .catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))),
                        t -> t.if_(missing, x -> x.throw_(new_(FileNotFoundException_.new_)))
                                .throw_(new_(IOException_.new_))));

        // static int nested(boolean missing) {
        //     try {
        //         try { if (missing) { throw new FileNotFoundException(); } throw new IOException(); }
        //         catch (FileNotFoundException e) { return 1; }
        //     } catch (IOException e) { return 2; }
        // }
        cb.staticMethod(
                "nested",
                PrimitiveToken.INT,
                PrimitiveToken.BOOLEAN,
                (b, missing) -> b.tryTerminated(
                        h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(2))),
                        outer -> outer.tryTerminated(
                                h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(1))),
                                t -> t.if_(missing, x -> x.throw_(new_(FileNotFoundException_.new_)))
                                        .throw_(new_(IOException_.new_)))));

        // static int rethrown(String s) throws IOException {
        //     try { return new StringReader(s).read(); } catch (IOException e) { throw e; } finally { s.length(); }
        // }
        cb.throwing(IOException_.TOKEN)
                .staticMethod(
                        "rethrown",
                        PrimitiveToken.INT,
                        String_.TOKEN,
                        (b, s) -> b.tryTerminated(
                                h -> h.catch_(IOException_.TOKEN, (c, e) -> c.throw_(e))
                                        .finally_(f -> f.exec(call(s, String_.length))),
                                t -> t.return_(call(new_(StringReader_.new_String, s), StringReader_.read))));

        // static int wrapped(String s) throws Exception {
        //     try { return new StringReader(s).read(); } catch (IOException e) { throw new Exception(e); }
        // }
        cb.throwing(Exception_.TOKEN)
                .staticMethod(
                        "wrapped",
                        PrimitiveToken.INT,
                        String_.TOKEN,
                        (b, s) -> b.tryTerminated(
                                h -> h.catch_(
                                        IOException_.TOKEN, (c, e) -> c.throw_(new_(Exception_.new_Throwable, e))),
                                t -> t.return_(call(new_(StringReader_.new_String, s), StringReader_.read))));

        // static int recovered(String s) {
        //     int result = 0;
        //     try { result = new StringReader(s).read(); } catch (IOException e) { result = -1; }
        //     return result;
        // }
        cb.staticMethod(
                "recovered",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.letVar(
                        PrimitiveToken.INT,
                        literal(0),
                        result -> b.try_(
                                        h -> h.catch_(
                                                IOException_.TOKEN, (c, _) -> c.exec(assign(result, literal(-1)))),
                                        t -> t.exec(assign(
                                                result, call(new_(StringReader_.new_String, s), StringReader_.read))))
                                .return_(result)));

        // static int anything(String s) { try { return Integer.parseInt(s); } catch (Exception e) { return -1; } }
        cb.staticMethod(
                "anything",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.tryTerminated(
                        h -> h.catch_(Exception_.TOKEN, (c, _) -> c.return_(literal(-1))),
                        t -> t.return_(staticCall(Integer_.parseInt_String, s))));

        // static void either(boolean io) throws IOException, InterruptedException {
        //     if (io) { throw new IOException(); } else { throw new InterruptedException(); }
        // }
        cb.throwing(IOException_.TOKEN, InterruptedException_.TOKEN)
                .voidStaticMethod(
                        "either",
                        PrimitiveToken.BOOLEAN,
                        (b, io) -> b.ifElse(
                                io,
                                t -> t.throw_(new_(IOException_.new_)),
                                e -> e.throw_(new_(InterruptedException_.new_))));

        // static void closed(StringReader r) throws IOException { ((Reader) r).close(); }
        cb.throwing(IOException_.TOKEN)
                .voidStaticMethod(
                        "closed",
                        StringReader_.TOKEN,
                        (b, r) -> b.exec(voidCall(r, Reader_.close)).end());

        // static int closedAndCaught(StringReader r) {
        //     try { ((Reader) r).close(); return 1; } catch (IOException e) { return 0; }
        // }
        cb.staticMethod(
                "closedAndCaught",
                PrimitiveToken.INT,
                StringReader_.TOKEN,
                (b, r) -> b.tryTerminated(
                        h -> h.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal(0))),
                        t -> t.exec(voidCall(r, Reader_.close)).return_(literal(1))));

        // static int precise() throws IOException {
        //     try { try { throw new FileNotFoundException(); } catch (IOException e) { throw e; } }
        //     catch (FileNotFoundException e) { return 1; }
        // }
        cb.throwing(IOException_.TOKEN)
                .staticMethod(
                        "precise",
                        PrimitiveToken.INT,
                        b -> b.tryTerminated(
                                h -> h.catch_(FileNotFoundException_.TOKEN, (c, _) -> c.return_(literal(1))),
                                o -> o.tryTerminated(
                                        h -> h.catch_(IOException_.TOKEN, (c, e) -> c.throw_(e)),
                                        t -> t.throw_(new_(FileNotFoundException_.new_)))));

        // static void unchecked() { throw new RuntimeException("unchecked"); }
        cb.voidStaticMethod("unchecked", b -> b.throw_(new_(RuntimeException_.new_String, literal("unchecked"))));

        // static void declaresUnchecked() throws RuntimeException { unchecked(); }
        cb.throwing(RuntimeException_.TOKEN)
                .voidStaticMethod(
                        "declaresUnchecked", b -> b.throw_(new_(RuntimeException_.new_String, literal("declared"))));
    }

    @Test
    void theThrowsClauseIsRendered() {
        assertThat(compiled.source())
                .contains("public Throwing() throws IOException {")
                .contains("public static int declared(String v0) throws IOException {")
                .contains("public static void either(boolean v0) throws IOException, InterruptedException {")
                .contains("public static void declaresUnchecked() throws RuntimeException {")
                .contains("public static int caught(String v0) {");
    }

    @Test
    void aDeclaredExceptionPassesThroughTheMemberAndItsCallers() throws Throwable {
        assertThat(compiled.invoke("declared", "a")).isEqualTo((int) 'a');
        assertThat(compiled.invoke("twice", "a")).isEqualTo(2 * 'a');
        assertThat(compiled.invoke("made")).isEqualTo(1);
        assertThatThrownBy(() -> compiled.invoke("either", true)).isInstanceOf(IOException.class);
        assertThatThrownBy(() -> compiled.invoke("either", false)).isInstanceOf(InterruptedException.class);
    }

    @Test
    void aCatchClauseCatchesItsTypeAndTheSubclasses() throws Throwable {
        assertThat(compiled.invoke("caught", "a")).isEqualTo(-1);
        assertThat(compiled.invoke("bySuperclass", "missing")).isEqualTo("missing");
        assertThat(compiled.invoke("ordered", true)).isEqualTo(1);
        assertThat(compiled.invoke("ordered", false)).isEqualTo(2);
        assertThat(compiled.invoke("nested", true)).isEqualTo(1);
        assertThat(compiled.invoke("nested", false)).isEqualTo(2);
        assertThat(compiled.invoke("recovered", "a")).isEqualTo((int) 'a');
        assertThat(compiled.invoke("anything", "not a number")).isEqualTo(-1);
    }

    @Test
    void aCaughtExceptionIsRethrownOrWrappedUnderTheThrowsClause() throws Throwable {
        assertThat(compiled.invoke("rethrown", "a")).isEqualTo((int) 'a');
        assertThat(compiled.invoke("wrapped", "a")).isEqualTo((int) 'a');
    }

    /// `Reader.close() throws IOException`, `StringReader.close()` does not: through a receiver cast
    /// to the owner of the fact javac goes by the clause of the fact, so the `throws` and the `catch`
    /// the typed layer asked for are the ones javac asks for.
    @Test
    void aMethodThatDeclaresACheckedExceptionIsCalledThroughItsOwner() throws Throwable {
        assertThat(compiled.source())
                .contains("((Reader) v0).close();")
                // a receiver of the owner's own type is not cast
                .contains("return v1.read();");
        assertThat(compiled.invoke("closedAndCaught", new StringReader("a"))).isEqualTo(1);
    }

    @Test
    void aRethrownBindingThrowsWhatItsTryBlockThrows() throws Throwable {
        // the outer catch (FileNotFoundException) is one javac accepts only by the precise rethrow
        assertThat(compiled.invoke("precise")).isEqualTo(1);
    }

    @Test
    void anUncheckedExceptionNeedsNoClause() {
        assertThatThrownBy(() -> compiled.invoke("unchecked"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("unchecked");
        assertThatThrownBy(() -> compiled.invoke("declaresUnchecked")).hasMessage("declared");
    }

    /// `try { return r.read(); } finally { return 0; }`: the `finally` block
    /// discards the exception, so it is neither caught nor declared — javac
    /// accepts it, with the `finally` lint only.
    @Test
    void aFinallyThatCannotCompleteNormallyDiscardsTheExceptionsOfItsTryBlock() {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        cb.staticMethod(
                                "discarded",
                                PrimitiveToken.INT,
                                String_.TOKEN,
                                (b, s) -> b.tryTerminated(
                                        h -> h.finally_(f -> f.return_(literal(0))),
                                        t -> t.return_(call(new_(StringReader_.new_String, s), StringReader_.read))));
                    }
                });

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeeded();
    }
}
