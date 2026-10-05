import gen.facts.java.io.IOException_;
import gen.facts.java.lang.IllegalStateException_;
import gen.facts.java.lang.InterruptedException_;
import gen.facts.java.lang.String_;
import gen.facts.p.Failure_;
import gen.facts.p.Io_;
import java.lang.constant.ClassDesc;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.TypedClassBuilder;
import me.supcheg.javafile.typed.TypedJavaFile;
import me.supcheg.javafile.typed.VoidBody;
import p.Io;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.Expressions.voidStaticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// The `throws` clause of a fact is what the typed layer checks a call of the member by: a checked exception of
/// it is caught or declared where the call is, or the call is not built.
public final class Checked {
    private Checked() {}

    private static String rendered(Members members) {
        return TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of("out", "Out"),
                        new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> void build(TypedClassBuilder<Self> cb) {
                                members.declare(cb);
                            }
                        })
                .render();
    }

    private interface Members {
        <Self> void declare(TypedClassBuilder<Self> cb);
    }

    public static void aCheckedExceptionOfAConstructorIsNeitherCaughtNorDeclaredInAMethodWithoutAClause(Typed typed) {
        assertThatIllegalStateException()
                .isThrownBy(() -> typed.render(Io_.TOKEN, String_.TOKEN, _ -> new_(Io_.new_)))
                .withMessageContaining(
                        "new p.Io() in the body of static method go can throw the checked exception java.io.IOException");
    }

    public static void anUncheckedExceptionOfAFactIsNotTracked(Typed typed) {
        assertThat(typed.apply(PrimitiveToken.INT, Io_.TOKEN, io -> call(io, Io_.unchecked), new Io(0)))
                .isEqualTo(0);
    }

    /// `static void declares() throws Failure {}`: nothing but the `throws` clause names `Failure`.
    private static final TypedJavaFile.TypedClassSpec DECLARES_FAILURE = new TypedJavaFile.TypedClassSpec() {
        @Override
        public <Self> void build(TypedClassBuilder<Self> cb) {
            cb.throwing(Failure_.TOKEN).voidStaticMethod("declares", VoidBody::end);
        }
    };

    public static void theThrowsClauseOfADeclaredMemberIsCheckedAgainstTheTargetClasspath(Typed typed) {
        assertThat(typed.render(DECLARES_FAILURE)).contains("public static void declares() throws Failure {");
    }

    public static void theThrowsClauseOfADeclaredMemberDoesNotHoldWhereTheExceptionIsAnotherClass(Typed typed) {
        // targets/unchecked-failure: Failure extends RuntimeException
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("unchecked-failure").render(DECLARES_FAILURE))
                .withMessageContaining("p.Failure");
    }

    public static void aCheckedExceptionOfAMethodIsDeclared() {
        String source = rendered(new Members() {
            @Override
            public <Self> void declare(TypedClassBuilder<Self> cb) {
                cb.throwing(Failure_.TOKEN)
                        .voidStaticMethod("custom", Io_.TOKEN, (b, io) -> b.exec(voidCall(io, Io_.custom))
                                .end());
                cb.throwing(IOException_.TOKEN)
                        .staticMethod("made", Io_.TOKEN, b -> b.return_(new_(Io_.new_)));
            }
        });

        assertThat(source)
                .contains("public static void custom(Io v0) throws Failure {")
                .contains("public static Io made() throws IOException {");
    }

    public static void everyCheckedExceptionOfAClauseIsCaughtAndTheUncheckedOneIsNot() {
        // multi() throws IOException, InterruptedException, IllegalStateException
        String source = rendered(new Members() {
            @Override
            public <Self> void declare(TypedClassBuilder<Self> cb) {
                cb.voidStaticMethod("multi", Io_.TOKEN, (b, io) -> b.try_(
                                h -> h.catch_(IOException_.TOKEN, (_, _) -> {})
                                        .catch_(InterruptedException_.TOKEN, (_, _) -> {}),
                                t -> t.exec(voidCall(io, Io_.multi)))
                        .end());
            }
        });

        assertThat(source).contains("} catch (IOException v1) {").contains("} catch (InterruptedException v2) {");
        assertThatIllegalStateException()
                .isThrownBy(() -> rendered(new Members() {
                    @Override
                    public <Self> void declare(TypedClassBuilder<Self> cb) {
                        cb.voidStaticMethod("multi", Io_.TOKEN, (b, io) -> b.try_(
                                        h -> h.catch_(IOException_.TOKEN, (_, _) -> {}),
                                        t -> t.exec(voidCall(io, Io_.multi)))
                                .end());
                    }
                }))
                .withMessageContaining("void p.Io.multi() in the try block of try_ in body of static method multi"
                        + " can throw the checked exception java.lang.InterruptedException");
    }

    public static void theClauseOfAStaticMethodIsChecked() {
        // util() throws Exception: IOException does not cover it, Exception does
        assertThatIllegalStateException()
                .isThrownBy(() -> rendered(new Members() {
                    @Override
                    public <Self> void declare(TypedClassBuilder<Self> cb) {
                        cb.throwing(IOException_.TOKEN)
                                .voidStaticMethod("util", b -> b.exec(voidStaticCall(Io_.util))
                                        .end());
                    }
                }))
                .withMessageContaining("static void p.Io.util() in the body of static method util can throw the"
                        + " checked exception java.lang.Exception")
                .withMessageContaining("declared: java.io.IOException");
    }

    public static void theTokenGivenForATypeVariableOfAThrowsClauseIsWhatIsThrown() {
        // <X extends Throwable> void generic() throws X
        assertThatIllegalStateException()
                .isThrownBy(() -> rendered(new Members() {
                    @Override
                    public <Self> void declare(TypedClassBuilder<Self> cb) {
                        cb.voidStaticMethod(
                                "generic", Io_.TOKEN, (b, io) -> b.exec(voidCall(io, Io_.generic(Failure_.TOKEN)))
                                        .end());
                    }
                }))
                .withMessageContaining("can throw the checked exception p.Failure");

        String source = rendered(new Members() {
            @Override
            public <Self> void declare(TypedClassBuilder<Self> cb) {
                cb.voidStaticMethod(
                        "generic",
                        Io_.TOKEN,
                        (b, io) -> b.exec(voidCall(io, Io_.generic(IllegalStateException_.TOKEN)))
                                .end());
            }
        });

        assertThat(source).contains("v0.<IllegalStateException>generic();").doesNotContain("throws");
    }
}
