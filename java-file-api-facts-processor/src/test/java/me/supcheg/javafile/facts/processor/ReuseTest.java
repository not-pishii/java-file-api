package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `reuse` (mini-spec §6, §9.2): metamodels on the classpath are
/// reused when they match the types on the classpath of this compilation,
/// and generated again, with a warning, when they do not; a metamodel class
/// that already exists under the name of one to generate is an error.
class ReuseTest {
    private static final String SVC = "package p; public class Svc { public Dep dep() { return null; } }";
    private static final String OTHER = "package p; public class Other { public Dep dep() { return null; } }";
    private static final String DEP_V1 = "package p; public class Dep { public int x() { return 0; } }";
    private static final String DEP_V2 =
            "package p; public class Dep { public int x() { return 0; } public void y() {} }";

    @TempDir
    Path root;

    private Path v1;
    private Path v2;
    private Path moduleA;

    @BeforeEach
    void compileLibrariesAndModuleA() throws IOException {
        v1 = ProcessorHarness.library(Files.createDirectory(root.resolve("v1")), List.of(), SVC, OTHER, DEP_V1);
        v2 = ProcessorHarness.library(Files.createDirectory(root.resolve("v2")), List.of(), SVC, OTHER, DEP_V2);
        Compilation a =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1), generator("a", "p.Svc.class")));
        moduleA = ProcessorHarness.write(a, Files.createDirectory(root.resolve("a")));
    }

    private static String generator(String pkg, String facts) {
        return """
                package %s;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(pkg, facts);
    }

    @Test
    void aMatchingTokenOnlyMetamodelOnTheClasspathIsReused() {
        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, moduleA), generator("b", "p.Other.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.p.Other_");
        assertThat(ProcessorHarness.resources(b)).containsOnlyKeys("META-INF/javafile/metamodel/token/p.Other");
        assertThat(b.diagnostics()).isEmpty();
    }

    @Test
    void aStaleMetamodelIsGeneratedAgainWithAWarning() {
        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v2, moduleA), generator("b", "p.Other.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.p.Other_", "b.facts.p.Dep_");
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel a.facts.p.Dep_ on the classpath is stale against p.Dep: it was generated"
                        + " from a different p.Dep; generating b.facts.p.Dep_");
    }

    @Test
    void aStaleMetamodelUnderTheNameToGenerateIsAnError() {
        Compilation b = ProcessorHarness.process(List.of(v2, moduleA), generator("a", "p.Other.class"));
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.ERROR))
                .containsExactly("metamodel a.facts.p.Dep_ of p.Dep already exists in a dependency; reuse it (it does"
                        + " not match p.Dep on this classpath) or choose another package with"
                        + " -Ajavafile.facts.package=<package>");
    }

    @Test
    void aRequestedTypeIsNotSatisfiedByATokenOnlyMetamodel() {
        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, moduleA), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.p.Dep_");
        assertThat(b.diagnostics()).isEmpty();
    }

    @Test
    void aMatchingFullMetamodelIsReusedForARequestAndForAToken() throws IOException {
        Path full = fullMetamodel(fingerprintOfDep(v1));
        Compilation requested =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1, full), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(requested)).isEmpty();
        assertThat(requested.diagnostics()).isEmpty();
        Compilation mentioned = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, full), generator("b", "p.Other.class")));
        assertThat(ProcessorHarness.generatedSources(mentioned)).containsOnlyKeys("b.facts.p.Other_");
        assertThat(mentioned.diagnostics()).isEmpty();
    }

    @Test
    void aFullMetamodelOfAnotherVersionIsNotReused() throws IOException {
        Path full = fullMetamodel(fingerprintOfDep(v2));
        Compilation b =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1, full), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.p.Dep_");
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is stale against p.Dep: it was generated"
                        + " from a different p.Dep; generating b.facts.p.Dep_");
    }

    @Test
    void anIndexEntryThatIsNotAMetamodelIsSkippedWithAWarning() throws IOException {
        Path index = Files.createDirectories(root.resolve("index/META-INF/javafile/metamodel/full"));
        Files.writeString(index.resolve("p.Other"), "x.facts.p.Other_\n");
        Files.writeString(index.resolve("p.Svc"), "p.Dep\n");
        Files.writeString(index.resolve("p.Dep"), "p.Svc\n");
        Compilation b = ProcessorHarness.succeeded(ProcessorHarness.process(
                List.of(v1, root.resolve("index"), moduleA), generator("b", "p.Other.class, p.Svc.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.p.Other_", "b.facts.p.Svc_");
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly(
                        "META-INF/javafile/metamodel/full/p.Other names x.facts.p.Other_, which is not on the"
                                + " classpath; generating b.facts.p.Other_",
                        "p.Dep is not marked @GeneratedMetamodel; generating b.facts.p.Svc_");
    }

    /// A hand-made full metamodel `x.facts.p.Dep_` of `p.Dep`, listed in the index.
    private Path fullMetamodel(String fingerprint) throws IOException {
        Path directory = Files.createDirectory(root.resolve("x"));
        ProcessorHarness.library(directory, List.of(v1), """
                package x.facts.p;
                @me.supcheg.javafile.facts.meta.GeneratedMetamodel(of = p.Dep.class, fingerprint = "%s", complete = true)
                public final class Dep_ {}
                """.formatted(fingerprint));
        Path index = Files.createDirectories(directory.resolve("META-INF/javafile/metamodel/full"));
        Files.writeString(index.resolve("p.Dep"), "x.facts.p.Dep_\n");
        return directory;
    }

    /// The fingerprint of the full metamodel of `p.Dep` on a classpath.
    private static String fingerprintOfDep(Path classpath) {
        List<String> found = new ArrayList<>();
        ProcessorHarness.succeeded(ProcessorHarness.process(
                List.of(classpath),
                List.of(),
                List.of(new AbstractProcessor() {
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
                        if (found.isEmpty()) {
                            Translation<TypeModel> model = new MirrorTranslator(
                                            processingEnv.getElementUtils(), processingEnv.getTypeUtils())
                                    .type(
                                            processingEnv.getElementUtils().getTypeElement("p.Dep"),
                                            MemberFilter.DECLARED_PUBLIC);
                            found.add(Canonical.of(((Translation.Ok<TypeModel>) model).value())
                                    .fingerprint());
                        }
                        return false;
                    }
                }),
                "package gen; class Empty {}"));
        return found.getFirst();
    }
}
