package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import me.supcheg.javafile.facts.processor.harness.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.processing.Processor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/// A generator as its user writes it — an annotation processor with
/// `@Facts`, compiled against one version of a library — run in a
/// compilation that has another version: the whole way of the check against
/// the target classpath, from `TargetClasspaths.of` in `init` to the class
/// written through the filer and compiled in the same run, or to the error
/// the generator reports.
///
/// The library and its versions are those of the fixture `target`, whose
/// `use/` tells every change apart; here are the three outcomes.
class GeneratorAgainstAnotherVersionTest {
    private static final Path FIXTURE = Path.of("src/test/fixtures/target");

    private static final String GENERATOR = """
            package gen;

            import gen.facts.java.lang.String_;
            import gen.facts.p.Svc_;
            import java.io.IOException;
            import java.io.UncheckedIOException;
            import java.lang.constant.ClassDesc;
            import java.util.Set;
            import javax.annotation.processing.AbstractProcessor;
            import javax.annotation.processing.ProcessingEnvironment;
            import javax.annotation.processing.RoundEnvironment;
            import javax.lang.model.SourceVersion;
            import javax.lang.model.element.TypeElement;
            import javax.tools.Diagnostic;
            import me.supcheg.javafile.JavaFile;
            import me.supcheg.javafile.facts.TargetClasspath;
            import me.supcheg.javafile.facts.TargetClasspathMismatchException;
            import me.supcheg.javafile.facts.meta.Facts;
            import me.supcheg.javafile.filer.JavaFileWriter;
            import me.supcheg.javafile.langmodel.mirror.TargetClasspaths;
            import me.supcheg.javafile.typed.TypedClassBuilder;
            import me.supcheg.javafile.typed.TypedJavaFile;
            import p.Svc;

            import static me.supcheg.javafile.typed.Expressions.call;
            import static me.supcheg.javafile.typed.Expressions.new_;

            /// Generates `app.Generated` with `static String only(String s) { return new Svc().only(s); }`.
            @Facts(Svc.class)
            public final class Generator extends AbstractProcessor {
                private TargetClasspath target;
                private boolean generated;

                @Override
                public synchronized void init(ProcessingEnvironment env) {
                    super.init(env);
                    target = TargetClasspaths.of(env);
                }

                @Override
                public Set<String> getSupportedAnnotationTypes() {
                    return Set.of("*");
                }

                @Override
                public SourceVersion getSupportedSourceVersion() {
                    return SourceVersion.latestSupported();
                }

                @Override
                public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
                    if (generated) {
                        return false;
                    }
                    generated = true;
                    try {
                        JavaFile file = TypedJavaFile.class_(
                                target, ClassDesc.of("app", "Generated"), new TypedJavaFile.TypedClassSpec() {
                                    @Override
                                    public <Self> void build(TypedClassBuilder<Self> cb) {
                                        cb.staticMethod(
                                                "only",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                (b, s) -> b.return_(call(new_(Svc_.new_), Svc_.only_Object, s)));
                                    }
                                });
                        JavaFileWriter.writeTo(file, processingEnv.getFiler());
                    } catch (TargetClasspathMismatchException e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                    return false;
                }
            }
            """;

    /// The code of the compilation the generator runs in: it calls what
    /// the generator writes.
    private static final String APP = """
            package app;

            public final class App {
                public static String run(String s) {
                    return Generated.only(s);
                }
            }
            """;

    @TempDir
    Path root;

    private Path v1;
    private Path generator;

    @BeforeEach
    void compileTheGeneratorAgainstTheLibrary() {
        v1 = Javac.plain().compile(Source.in(FIXTURE.resolve("lib"))).orFail().writeTo(root.resolve("v1"));
        generator = Javac.facts().classpath(v1).compile(GENERATOR).orFail().writeTo(root.resolve("generator"));
    }

    private Path version(String name) {
        return Javac.plain()
                .compile(Source.overlaid(FIXTURE.resolve("targets").resolve(name), FIXTURE.resolve("lib")))
                .orFail()
                .writeTo(root.resolve(name));
    }

    /// The compilation of [#APP] against a library, with the generator as
    /// a build tool loads it: its classes and the library it was compiled
    /// against, apart from the classpath of the compilation.
    private Compiled compile(Path library) throws Exception {
        try (URLClassLoader loader = new URLClassLoader(
                new URL[] {url(generator), url(v1)}, GeneratorAgainstAnotherVersionTest.class.getClassLoader())) {
            Processor processor = (Processor)
                    loader.loadClass("gen.Generator").getConstructor().newInstance();
            return Javac.plain()
                    .alone()
                    .linted()
                    .classpath(library)
                    .with(processor)
                    .compile(APP);
        }
    }

    private Object run(Compiled compiled, Path library) throws Exception {
        try (URLClassLoader loader =
                new URLClassLoader(new URL[] {url(compiled.writeTo(root.resolve("app"))), url(library)}, null)) {
            return loader.loadClass("app.App").getMethod("run", String.class).invoke(null, "a");
        }
    }

    private static URL url(Path directory) throws MalformedURLException {
        return directory.toUri().toURL();
    }

    @Test
    void againstTheVersionItWasCompiledWithTheGeneratorWritesItsClass() throws Exception {
        Compiled compiled = compile(v1).clean();

        assertThat(compiled.sources().get("app.Generated")).contains("return new Svc().only(v0);");
        assertThat(run(compiled, v1)).isEqualTo("only(Object) a");
    }

    @Test
    void againstAVersionWithAnOverloadAddedItCallsTheMethodOfItsFact() throws Exception {
        Path added = version("added-overload");

        Compiled compiled = compile(added).clean();

        // without the cast javac would choose only(String), which the version has added
        assertThat(compiled.sources().get("app.Generated")).contains("return new Svc().only((Object) v0);");
        assertThat(run(compiled, added)).isEqualTo("only(Object) a");
    }

    @Test
    void againstAVersionItsMetamodelDoesNotHoldOfItReportsTheDifferences() throws Exception {
        Compiled compiled = compile(version("removed-method"));

        assertThat(compiled.succeeded()).isFalse();
        assertThat(compiled.sources()).doesNotContainKey("app.Generated");
        // as javac prints a message of several lines: the lines after the first indented
        assertThat(compiled.errors().getFirst().lines().map(String::strip))
                .containsExactly(
                        "metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:",
                        "missing: method public overridable m(java.lang.String) -> java.lang.String throws -",
                        "The generator was compiled against another p.Svc than this compilation has (another version"
                                + " of its library, or another --release). Generate the metamodels against this"
                                + " version: rebuild the generator against it, or align the versions.");
        // and nothing else: javac stops at the error of a processor
        assertThat(compiled.errors()).hasSize(1);
    }
}
