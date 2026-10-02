package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `options` (mini-spec §9.2): the base package of the metamodels
/// (§2.1, Q2), `strict` (Q10), the checks of `@Facts` itself (§1.1) and of
/// the metamodel format (§6.2).
class OptionsTest {
    @TempDir
    Path lib;

    private List<Path> classpath;

    @BeforeEach
    void compileLibrary() {
        classpath = List.of(ProcessorHarness.library(
                lib,
                List.of(),
                "package p; public class Svc { public Dep dep() { return null; } public Hidden hidden() { return null; } }",
                "package p; public class Dep {}",
                "package p; class Hidden {}",
                "package p; public @interface Marker {}",
                "package p; class Internal { public static class Open {} }"));
    }

    private static String generator(String pkg, String facts) {
        return """
                package %s;

                import me.supcheg.javafile.facts.meta.Facts;

                @Facts({%s})
                class G {}
                """.formatted(pkg, facts);
    }

    private static List<String> errors(Compilation compilation) {
        return ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR);
    }

    private static List<String> warnings(Compilation compilation) {
        return ProcessorHarness.messages(compilation, Diagnostic.Kind.WARNING);
    }

    @Test
    void withoutTheOptionTheBaseIsFactsUnderThePackageOfFacts() {
        Compilation compilation = ProcessorHarness.succeeded(
                ProcessorHarness.process(classpath, generator("com.acme.gen", "p.Svc.class")));
        assertThat(ProcessorHarness.generatedSources(compilation))
                .containsOnlyKeys("com.acme.gen.facts.p.Svc_", "com.acme.gen.facts.p.Dep_");
        assertThat(ProcessorHarness.resources(compilation))
                .containsEntry("META-INF/javafile/metamodel/full/p.Svc", "com.acme.gen.facts.p.Svc_\n")
                .containsEntry("META-INF/javafile/metamodel/token/p.Dep", "com.acme.gen.facts.p.Dep_\n")
                .hasSize(2);
    }

    @Test
    void aPackageInfoAndAClassOfOnePackageShareTheDefault() {
        Compilation compilation = ProcessorHarness.succeeded(
                ProcessorHarness.process(classpath, """
                @me.supcheg.javafile.facts.meta.Facts(p.Svc.class)
                package com.acme.gen;
                """, generator("com.acme.gen", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(compilation))
                .containsOnlyKeys("com.acme.gen.facts.p.Svc_", "com.acme.gen.facts.p.Dep_");
    }

    @Test
    void withoutTheOptionFactsInTwoPackagesIsAnError() {
        Compilation compilation = ProcessorHarness.process(
                classpath, generator("com.acme.a", "p.Svc.class"), generator("com.acme.b", "p.Dep.class"));
        assertThat(errors(compilation))
                .containsExactly("@Facts is used in several packages [com.acme.a, com.acme.b]; choose the package of"
                        + " the metamodels with -Ajavafile.facts.package=<package>");
    }

    @Test
    void theOptionNamesTheBase() {
        Compilation compilation = ProcessorHarness.succeeded(ProcessorHarness.process(
                classpath,
                List.of("-Ajavafile.facts.package=com.acme.metamodel"),
                List.of(),
                generator("com.acme.a", "p.Svc.class"),
                generator("com.acme.b", "p.Dep.class")));
        assertThat(ProcessorHarness.generatedSources(compilation))
                .containsOnlyKeys("com.acme.metamodel.p.Svc_", "com.acme.metamodel.p.Dep_");
    }

    @Test
    void anOptionThatIsNotAPackageNameIsAnError() {
        Compilation compilation = ProcessorHarness.process(
                classpath, List.of("-Ajavafile.facts.package=com.1acme"), List.of(), generator("gen", "p.Svc.class"));
        assertThat(errors(compilation)).containsExactly("-Ajavafile.facts.package=com.1acme is not a package name");
    }

    @Test
    void strictIsTrueOrFalse() {
        Compilation compilation = ProcessorHarness.process(
                classpath, List.of("-Ajavafile.facts.strict=yes"), List.of(), generator("gen", "p.Svc.class"));
        assertThat(errors(compilation)).containsExactly("-Ajavafile.facts.strict=yes is neither true nor false");
    }

    @Test
    void aMemberWithoutAFactIsAWarningOrUnderStrictAnError() {
        String skipped = "p.Svc: no fact of method hidden(), which mentions types that are not public: p.Hidden";
        Compilation lenient =
                ProcessorHarness.succeeded(ProcessorHarness.process(classpath, generator("gen", "p.Svc.class")));
        assertThat(warnings(lenient)).containsExactly(skipped);

        Compilation strict = ProcessorHarness.process(
                classpath, List.of("-Ajavafile.facts.strict=true"), List.of(), generator("gen", "p.Svc.class"));
        assertThat(errors(strict)).containsExactly(skipped);
    }

    @Test
    void factsAsksForPublicClassesInterfacesEnumsAndRecords() {
        Compilation compilation = ProcessorHarness.process(
                classpath,
                generator(
                        "p",
                        "int.class, String[].class, void.class, p.Marker.class, p.Internal.class,"
                                + " p.Internal.Open.class"));
        assertThat(errors(compilation))
                .containsExactly(
                        "@Facts asks for classes, interfaces, enums and records, got int",
                        "@Facts asks for classes, interfaces, enums and records, got java.lang.String[]",
                        "@Facts asks for classes, interfaces, enums and records, got void",
                        "annotation interface p.Marker is not supported yet",
                        "p.Internal is not public",
                        "p.Internal.Open is nested in p.Internal, which is not public");
    }

    @Test
    void anInnerClassOfAGenericClassHasNoMetamodel() {
        List<Path> generic = List.of(ProcessorHarness.library(
                lib.resolve("generic"), List.of(), "package q; public class Outer<T> { public class Inner {} }"));
        Compilation compilation = ProcessorHarness.process(generic, generator("gen", "q.Outer.Inner.class"));
        assertThat(errors(compilation))
                .containsExactly("no metamodel of q.Outer$Inner: inner class q.Outer.Inner of generic class q.Outer"
                        + " can mention its type parameters");
    }

    @Test
    void theFormatOfTheFactsOnTheClasspathMustBeTheProcessors() {
        Compilation compilation = ProcessorHarness.process(classpath, """
                package me.supcheg.javafile.facts.meta;
                public final class MetamodelFormat {
                    public static final int VERSION = 999;
                    private MetamodelFormat() {}
                }
                """, generator("gen", "p.Svc.class"));
        assertThat(errors(compilation))
                .containsExactly("the @Facts processor generates metamodel format " + MetamodelFormat.VERSION
                        + ", but java-file-api-facts on the classpath has format 999; use java-file-api-facts of the"
                        + " same version as the processor");
    }

    @Test
    void aCompilationWithoutFactsGeneratesNothing() {
        Compilation compilation =
                ProcessorHarness.succeeded(ProcessorHarness.process(classpath, "package gen; class G {}"));
        assertThat(compilation.generatedFiles()).noneMatch(f -> f.getName().endsWith(".java"));
    }
}
