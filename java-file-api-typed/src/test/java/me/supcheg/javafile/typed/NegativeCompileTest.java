package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/// Negative compile tests (§11): fixtures of invalid typed-API *usage* — not
/// invalid generated code — that must fail to compile as generator source.
/// These guard the guarantee (§1) against a combinator's signature
/// accidentally widening enough to admit misuse; if any of these starts
/// compiling, the guarantee has a hole.
///
/// Each fixture is generator code (not typed-layer *output*) compiled with
/// the typed/facts/core module classes on the classpath.
class NegativeCompileTest {

    private static Compiler compilerWithModuleClasspath() {
        List<File> classpath = Arrays.stream(
                        System.getProperty("java.class.path").split(File.pathSeparator))
                .map(File::new)
                .toList();
        return javac().withClasspath(classpath);
    }

    private static Compilation compileFixture(String simpleName, String source) {
        return compilerWithModuleClasspath().compile(JavaFileObjects.forSourceString("fixtures." + simpleName, source));
    }

    @Test
    void wrongArgumentTypePassedToCall() {
        // String_.length takes no arguments; passing one, of the wrong type
        // besides, must not compile.
        String source = """
                package fixtures;

                import me.supcheg.javafile.facts.jdk.String_;

                import static me.supcheg.javafile.typed.Expressions.call;
                import static me.supcheg.javafile.typed.Expressions.literal;

                class WrongArgumentType {
                    void use() {
                        call(literal("hi"), String_.length, literal("unexpected argument"));
                    }
                }
                """;

        Compilation compilation = compileFixture("WrongArgumentType", source);

        assertThat(compilation).failed();
    }

    @Test
    void methodBodyWithoutReturnDoesNotCompile() {
        // A Body<R>-returning lambda that never calls return_/throw_/ifElse
        // cannot produce a Terminated<R> — the lambda body has no return
        // statement, which javac itself rejects for a non-void functional
        // interface method.
        String source = """
                package fixtures;

                import java.lang.constant.ClassDesc;
                import me.supcheg.javafile.facts.PrimitiveToken;
                import me.supcheg.javafile.typed.TypedClassBuilder;
                import me.supcheg.javafile.typed.TypedJavaFile;

                class MissingReturn {
                    void use() {
                        TypedJavaFile.class_(
                                ClassDesc.of("fixtures", "Generated"),
                                new TypedJavaFile.TypedClassSpec() {
                                    public <Self> void build(TypedClassBuilder<Self> cb) {
                                        cb.method("compute", PrimitiveToken.INT, body -> {
                                            // no return_ call: Terminated<Integer> cannot be produced
                                        });
                                    }
                                });
                    }
                }
                """;

        Compilation compilation = compileFixture("MissingReturn", source);

        assertThat(compilation).failed();
    }

    @Test
    void loopCtlHasNoPublicConstructor() {
        // break_/continue_ take a LoopCtl, obtainable only as the third
        // parameter of a loop body (while_/for_/forEach); it has no public
        // constructor, so a stray break outside a loop cannot be assembled.
        String source = """
                package fixtures;

                import me.supcheg.javafile.typed.LoopCtl;

                class StrayLoopCtl {
                    void use() {
                        new LoopCtl();
                    }
                }
                """;

        Compilation compilation = compileFixture("StrayLoopCtl", source);

        assertThat(compilation).failed();
    }

    @Test
    void varHasNoPublicConstructor() {
        // Var/MutVar are only ever handed out by HOAS-binding combinators
        // (let, letVar, method parameters, loop variables, ...); fabricating
        // one outside its introducing lambda is not representable.
        String source = """
                package fixtures;

                import me.supcheg.javafile.facts.PrimitiveToken;
                import me.supcheg.javafile.typed.Var;

                class StrayVar {
                    void use() {
                        new Var<>(PrimitiveToken.INT, "fabricated");
                    }
                }
                """;

        Compilation compilation = compileFixture("StrayVar", source);

        assertThat(compilation).failed();
    }
}
