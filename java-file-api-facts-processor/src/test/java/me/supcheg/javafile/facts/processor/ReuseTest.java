package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
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
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.constant.ConstantDescs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        assertThat(ProcessorHarness.resources(b)).containsOnlyKeys("META-INF/javafile/metamodel/full/p.Other");
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
    void aTokenOnlyMetamodelIsNotReusedForATypeThatTurnsOutToBeASupertype() {
        AbstractProcessor generating = new AbstractProcessor() {
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
                    try (Writer writer = processingEnv
                            .getFiler()
                            .createSourceFile("gen.Late")
                            .openWriter()) {
                        writer.write("package gen; public class Late extends p.Dep {}");
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                return false;
            }
        };
        Compilation b = ProcessorHarness.succeeded(ProcessorHarness.process(
                List.of(v1, moduleA), List.of(), List.of(generating), generator("b", "p.Other.class, gen.Late.class")));
        Map<String, String> sources = ProcessorHarness.generatedSources(b);

        // round 1 would reuse the token-only a.facts.p.Dep_ for Other, but settles nothing: gen.Late is
        // not there; round 2 finds Dep to be a supertype, and every metamodel refers to its full one
        assertThat(sources.keySet())
                .containsExactlyInAnyOrder("gen.Late", "b.facts.p.Other_", "b.facts.p.Dep_", "b.facts.gen.Late_");
        assertThat(sources.get("b.facts.p.Dep_")).contains("complete = true");
        assertThat(sources.get("b.facts.p.Other_")).contains("Dep_.Data.SHAPE").doesNotContain("a.facts");
        assertThat(b.diagnostics()).isEmpty();
    }

    @Test
    void aMatchingFullMetamodelIsReusedForARequestAndForAToken() throws IOException {
        // the hand-made module has no metamodel of Object, which Dep extends: that one is generated
        Path full = fullMetamodel(fingerprintOfDep(v1), MetamodelFormat.VERSION);
        Compilation requested =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1, full), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(requested))
                .containsOnlyKeys(ProcessorHarness.withObject("b.facts"));
        assertThat(requested.diagnostics()).isEmpty();
        Compilation mentioned = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, full), generator("b", "p.Other.class")));
        assertThat(ProcessorHarness.generatedSources(mentioned))
                .containsOnlyKeys(ProcessorHarness.withObject("b.facts", "b.facts.p.Other_"));
        assertThat(mentioned.diagnostics()).isEmpty();
    }

    @Test
    void theFullMetamodelOfASupertypeOnTheClasspathIsReused() throws IOException {
        String sub = "package q; public class Sub extends p.Dep { public void sub() {} }";
        Path subV1 = ProcessorHarness.library(Files.createDirectory(root.resolve("sub1")), List.of(v1), sub);
        Compilation a =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1), generator("a", "p.Dep.class")));
        Path deps = ProcessorHarness.write(a, Files.createDirectory(root.resolve("deps")));

        // Dep_ and Object_ of module a are full: Sub, which extends Dep, needs nothing more
        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, subV1, deps), generator("b", "q.Sub.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.q.Sub_");
        assertThat(ProcessorHarness.resources(b)).containsOnlyKeys("META-INF/javafile/metamodel/full/q.Sub");
        assertThat(b.diagnostics()).isEmpty();
    }

    @Test
    void aStaleFullMetamodelOfASupertypeIsGeneratedAgainWithAWarning() throws IOException {
        String sub = "package q; public class Sub extends p.Dep { public void sub() {} }";
        Path subV2 = ProcessorHarness.library(Files.createDirectory(root.resolve("sub2")), List.of(v2), sub);
        Compilation a =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1), generator("a", "p.Dep.class")));
        Path deps = ProcessorHarness.write(a, Files.createDirectory(root.resolve("deps")));

        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v2, subV2, deps), generator("b", "q.Sub.class")));
        assertThat(ProcessorHarness.generatedSources(b)).containsOnlyKeys("b.facts.q.Sub_", "b.facts.p.Dep_");
        assertThat(ProcessorHarness.generatedSources(b).get("b.facts.p.Dep_")).contains("complete = true");
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel a.facts.p.Dep_ on the classpath is stale against p.Dep: it was generated"
                        + " from a different p.Dep; generating b.facts.p.Dep_");
    }

    @Test
    void aFullMetamodelOfAnotherVersionIsNotReused() throws IOException {
        Path full = fullMetamodel(fingerprintOfDep(v2), MetamodelFormat.VERSION);
        Compilation b =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1, full), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(b))
                .containsOnlyKeys(ProcessorHarness.withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is stale against p.Dep: it was generated"
                        + " from a different p.Dep; generating b.facts.p.Dep_");
    }

    @Test
    void aFullMetamodelOfAnotherFormatIsNotReused() throws IOException {
        // which members have a fact is decided by the format: the same type, but not the same facts
        Path full = fullMetamodel(fingerprintOfDep(v1), MetamodelFormat.VERSION - 1);
        Compilation b =
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(v1, full), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(b))
                .containsOnlyKeys(ProcessorHarness.withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is of format "
                        + (MetamodelFormat.VERSION - 1) + ", not of format " + MetamodelFormat.VERSION
                        + ", which this processor generates; generating b.facts.p.Dep_");
    }

    @Test
    void aFullMetamodelMarkedBeforeFormatsWereRecordedIsNotReused() throws IOException {
        // compiled against the marker of that time, which had no `format`: the class file has no such element
        Path directory = Files.createDirectory(root.resolve("x"));
        ProcessorHarness.library(directory, List.of(v1), """
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
                """.formatted(fingerprintOfDep(v1)));
        Files.delete(directory.resolve("me/supcheg/javafile/facts/meta/GeneratedMetamodel.class"));
        Path index = Files.createDirectories(directory.resolve("META-INF/javafile/metamodel/full"));
        Files.writeString(index.resolve("p.Dep"), "x.facts.p.Dep_\n");

        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(v1, directory), generator("b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(b))
                .containsOnlyKeys(ProcessorHarness.withObject("b.facts", "b.facts.p.Dep_"));
        assertThat(ProcessorHarness.messages(b, Diagnostic.Kind.WARNING))
                .containsExactly("metamodel x.facts.p.Dep_ on the classpath is of an older format, not of format "
                        + MetamodelFormat.VERSION + ", which this processor generates; generating b.facts.p.Dep_");
    }

    @Test
    void aGenericFullMetamodelOfAnotherModuleIsReusedForTheTypesMadeOfIt() throws Exception {
        Path generic = ProcessorHarness.library(Files.createDirectory(root.resolve("generic")), List.of(), """
                package p;
                public class Box<T extends Comparable<T>> {
                    public T value;
                    public Box(T value) {}
                    public T[] all(T... more) { return more; }
                    public <R> R as(R other) { return other; }
                }
                """, """
                package p;
                public class Uses {
                    public Box<String> strings() { return null; }
                    public Box<?> any(Box<Integer> integers) { return null; }
                }
                """);
        Compilation a = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(generic), generator("a", "p.Box.class, String.class")));
        Path boxes = ProcessorHarness.write(a, Files.createDirectory(root.resolve("boxes")));
        assertThat(ProcessorHarness.generatedSources(a).get("a.facts.p.Box_"))
                .contains("public final class Box_<T extends Comparable<T>> {");

        Compilation b = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(generic, boxes), generator("b", "p.Uses.class")));
        // Box_, String_ and Comparable_ are those of module a; Integer has no metamodel there
        assertThat(ProcessorHarness.generatedSources(b))
                .containsOnlyKeys("b.facts.p.Uses_", "b.facts.java.lang.Integer_");
        assertThat(ProcessorHarness.generatedSources(b).get("b.facts.p.Uses_"))
                .contains("a.facts.p.Box_")
                .contains("a.facts.java.lang.String_");
        assertThat(b.diagnostics()).isEmpty();

        ClassLoader loader = ProcessorHarness.compileAndLoad(
                b, Files.createDirectory(root.resolve("uses")), List.of(generic, boxes));
        Object shape = loader.loadClass("a.facts.p.Box_$Data").getField("SHAPE").get(null);
        Invocable strings = (Invocable)
                loader.loadClass("b.facts.p.Uses_").getField("strings").get(null);
        DeclaredToken<?> made = (DeclaredToken<?>) strings.resultType().orElseThrow();
        assertThat(made.shape()).isSameAs(shape);
        // the table of the other module's shape, under the type argument of this one
        assertThat(made.methods().concreteMethods())
                .contains(new MethodSignature("all", List.of(ConstantDescs.CD_String.arrayType())));
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
    private Path fullMetamodel(String fingerprint, int format) throws IOException {
        Path directory = Files.createDirectory(root.resolve("x"));
        ProcessorHarness.library(directory, List.of(v1), """
                package x.facts.p;
                @me.supcheg.javafile.facts.meta.GeneratedMetamodel(of = p.Dep.class, fingerprint = "%s", complete = true, format = %d)
                public final class Dep_ {
                    public static final class Data {
                        public static final me.supcheg.javafile.facts.TypeShape<me.supcheg.javafile.facts.DeclaredKind.OpenClass> SHAPE = null;
                    }
                }
                """.formatted(fingerprint, format));
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
