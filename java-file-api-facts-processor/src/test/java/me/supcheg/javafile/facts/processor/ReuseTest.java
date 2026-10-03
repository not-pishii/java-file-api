package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.meta.MetamodelFormat;
import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import me.supcheg.javafile.facts.processor.harness.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// Reuse of metamodels (mini-spec §6, §9.2) where it has to be driven step by step: metamodels made by hand on the
/// classpath — of the right type, and of another version or format —, and a processor that generates a source in a
/// later round. The reuse that a library and the output of another module are enough for is in the fixture `reuse`.
class ReuseTest {
    private static final Path FIXTURE = Path.of("src/test/fixtures/reuse");
    private static final Pattern FINGERPRINT = Pattern.compile("fingerprint = \"(\\w+)\"");

    @TempDir
    Path root;

    private Path v1;
    private Path v2;
    private Path moduleA;

    @BeforeEach
    void compileLibrariesAndModuleA() {
        // the library of the fixture, and the same with the other `Dep` of the case that has it
        v1 = Javac.plain().compile(Source.in(FIXTURE.resolve("lib"))).orFail().writeTo(root.resolve("v1"));
        v2 = Javac.plain()
                .compile(Stream.concat(
                                Source.in(FIXTURE.resolve("cases/b-stale/lib")).stream(),
                                Source.in(FIXTURE.resolve("lib")).stream()
                                        .filter(source -> !source.name().equals("p.Dep")))
                        .toList())
                .orFail()
                .writeTo(root.resolve("v2"));
        moduleA = Javac.facts()
                .classpath(v1)
                .compile(generator("a", "p.Svc.class"))
                .orFail()
                .writeTo(root.resolve("a"));
    }

    private static String generator(String pkg, String facts) {
        return """
                package %s;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(pkg, facts);
    }

    /// The metamodels every compilation generates besides those of its own types, unless it reuses them: the full
    /// one of `java.lang.Object`, which every requested type extends, and the token-only ones of the types the
    /// signatures of `Object` mention.
    private static Set<String> withObject(String base, String... others) {
        return Stream.concat(
                        Stream.of(others),
                        Stream.of("Object_", "Class_", "InterruptedException_", "String_")
                                .map(name -> base + ".java.lang." + name))
                .collect(Collectors.toSet());
    }

    /// A processor that writes `gen.Late`, which extends `p.Dep`, in its first round.
    private static final class Generating extends AbstractProcessor {
        private boolean done;

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
            if (!done) {
                done = true;
                try (Writer writer =
                        processingEnv.getFiler().createSourceFile("gen.Late").openWriter()) {
                    writer.write("package gen; public class Late extends p.Dep {}");
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            return false;
        }
    }

    @Test
    void aTokenOnlyMetamodelIsNotReusedForATypeThatTurnsOutToBeASupertype() {
        Compiled b = Javac.facts()
                .classpath(v1, moduleA)
                .with(new Generating())
                .compile(generator("b", "p.Other.class, gen.Late.class"))
                .orFail();

        // round 1 would reuse the token-only a.facts.p.Dep_ for Other, but settles nothing: gen.Late is
        // not there; round 2 finds Dep to be a supertype, and every metamodel refers to its full one
        assertThat(b.sources().keySet())
                .containsExactlyInAnyOrder("gen.Late", "b.facts.p.Other_", "b.facts.p.Dep_", "b.facts.gen.Late_");
        assertThat(b.sources().get("b.facts.p.Dep_")).contains("complete = true");
        assertThat(b.sources().get("b.facts.p.Other_"))
                .contains("Dep_.Data.SHAPE")
                .doesNotContain("a.facts");
        assertThat(b.diagnostics()).isEmpty();
    }

    @Test
    void aMatchingFullMetamodelIsReusedForARequestAndForAToken() throws IOException {
        // the hand-made module has no metamodel of Object, which Dep extends: that one is generated
        Path full = fullMetamodel(fingerprintOfDep(v1), MetamodelFormat.VERSION);

        Compiled requested = Javac.facts()
                .classpath(v1, full)
                .compile(generator("b", "p.Dep.class"))
                .orFail();
        assertThat(requested.sources().keySet()).containsExactlyInAnyOrderElementsOf(withObject("b.facts"));
        assertThat(requested.diagnostics()).isEmpty();

        Compiled mentioned = Javac.facts()
                .classpath(v1, full)
                .compile(generator("b", "p.Other.class"))
                .orFail();
        assertThat(mentioned.sources().keySet())
                .containsExactlyInAnyOrderElementsOf(withObject("b.facts", "b.facts.p.Other_"));
        assertThat(mentioned.diagnostics()).isEmpty();
    }

    @Test
    void aFullMetamodelOfAnotherVersionIsNotReused() throws IOException {
        Path full = fullMetamodel(fingerprintOfDep(v2), MetamodelFormat.VERSION);

        Compiled b = Javac.facts()
                .classpath(v1, full)
                .compile(generator("b", "p.Dep.class"))
                .orFail();

        assertThat(b.sources().keySet()).containsExactlyInAnyOrderElementsOf(withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(b.warnings())
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is stale against p.Dep: it was generated"
                        + " from a different p.Dep; generating b.facts.p.Dep_");
    }

    @Test
    void aFullMetamodelOfAnotherFormatIsNotReused() throws IOException {
        // which members have a fact is decided by the format: the same type, but not the same facts
        Path full = fullMetamodel(fingerprintOfDep(v1), MetamodelFormat.VERSION - 1);

        Compiled b = Javac.facts()
                .classpath(v1, full)
                .compile(generator("b", "p.Dep.class"))
                .orFail();

        assertThat(b.sources().keySet()).containsExactlyInAnyOrderElementsOf(withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(b.warnings())
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is of format "
                        + (MetamodelFormat.VERSION - 1) + ", not of format " + MetamodelFormat.VERSION
                        + ", which this processor generates; generating b.facts.p.Dep_");
    }

    @Test
    void aFullMetamodelMarkedBeforeFormatsWereRecordedIsNotReused() throws IOException {
        // compiled against the marker of that time, which had no `format`: the class file has no such element
        Path directory = Javac.plain()
                .classpath(v1)
                .compile("""
                        package me.supcheg.javafile.facts.meta;
                        public @interface GeneratedMetamodel {
                            Class<?> of();
                            String fingerprint();
                            boolean complete();
                        }
                        """, """
                        package x.facts.p;
                        @me.supcheg.javafile.facts.meta.GeneratedMetamodel(of = p.Dep.class, fingerprint = "%s", complete = true)
                        public final class Dep_ {}
                        """.formatted(fingerprintOfDep(v1)))
                .orFail()
                .writeTo(root.resolve("x"));
        Files.delete(directory.resolve("me/supcheg/javafile/facts/meta/GeneratedMetamodel.class"));
        index(directory, "p.Dep", "x.facts.p.Dep_\n");

        Compiled b = Javac.facts()
                .classpath(v1, directory)
                .compile(generator("b", "p.Dep.class"))
                .orFail();

        assertThat(b.sources().keySet()).containsExactlyInAnyOrderElementsOf(withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(b.warnings())
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is of an older format, not of format "
                        + MetamodelFormat.VERSION + ", which this processor generates; generating b.facts.p.Dep_");
    }

    @Test
    void anIndexEntryThatIsNotAMetamodelIsSkippedWithAWarning() throws IOException {
        Path index = root.resolve("index");
        index(index, "p.Other", "x.facts.p.Other_\n");
        index(index, "p.Svc", "p.Dep\n");
        index(index, "p.Dep", "p.Svc\n");

        Compiled b = Javac.facts()
                .classpath(v1, index, moduleA)
                .compile(generator("b", "p.Other.class, p.Svc.class"))
                .orFail();

        assertThat(b.sources().keySet()).containsExactlyInAnyOrder("b.facts.p.Other_", "b.facts.p.Svc_");
        assertThat(b.warnings())
                .containsExactly(
                        "META-INF/javafile/metamodel/full/p.Other names x.facts.p.Other_, which is not on the"
                                + " classpath; generating b.facts.p.Other_",
                        "p.Dep is not marked @GeneratedMetamodel; generating b.facts.p.Svc_");
    }

    /// A hand-made full metamodel `x.facts.p.Dep_` of `p.Dep`, listed in the index.
    private Path fullMetamodel(String fingerprint, int format) throws IOException {
        Path directory = Javac.plain()
                .classpath(v1)
                .compile("""
                        package x.facts.p;
                        @me.supcheg.javafile.facts.meta.GeneratedMetamodel(of = p.Dep.class, fingerprint = "%s", complete = true, format = %d)
                        public final class Dep_ {
                            public static final class Data {
                                public static final me.supcheg.javafile.facts.TypeShape<me.supcheg.javafile.facts.DeclaredKind.OpenClass> SHAPE = null;
                            }
                        }
                        """.formatted(fingerprint, format))
                .orFail()
                .writeTo(root.resolve("x"));
        index(directory, "p.Dep", "x.facts.p.Dep_\n");
        return directory;
    }

    private static void index(Path directory, String type, String metamodel) throws IOException {
        Path index = Files.createDirectories(directory.resolve("META-INF/javafile/metamodel/full"));
        Files.writeString(index.resolve(type), metamodel);
    }

    /// The fingerprint of the full metamodel of `p.Dep` on a classpath: the one the processor writes in it.
    private static String fingerprintOfDep(Path classpath) {
        Matcher fingerprint = FINGERPRINT.matcher(Javac.facts()
                .classpath(classpath)
                .compile(generator("fingerprint", "p.Dep.class"))
                .orFail()
                .sources()
                .get("fingerprint.facts.p.Dep_"));
        assertThat(fingerprint.find()).isTrue();
        return fingerprint.group(1);
    }
}
