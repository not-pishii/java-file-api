package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.TypeShape;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/// What the fixtures of full metamodels (mini-spec §9.2) share: a fixture
/// library is compiled, `@Facts` asks for some of its types, and the
/// generated metamodels are compiled under every lint, loaded, and read back
/// as the facts they hold.
abstract class FixtureSupport {
    @TempDir
    Path lib;

    @TempDir
    Path out;

    /// Compiles `library`, and a generator that asks for `facts` — class
    /// literals, comma separated — with `options`; the processor must not
    /// fail.
    Compilation generate(List<String> options, String facts, String... library) {
        List<Path> classpath = List.of(ProcessorHarness.library(lib, List.of(), library));
        return ProcessorHarness.succeeded(
                ProcessorHarness.process(classpath, options, List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(facts)));
    }

    Compilation generate(String facts, String... library) {
        return generate(List.of(), facts, library);
    }

    /// The same, but the compilation may fail.
    Compilation attempt(List<String> options, String facts, String... library) {
        List<Path> classpath = List.of(ProcessorHarness.library(lib, List.of(), library));
        return ProcessorHarness.process(classpath, options, List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(facts));
    }

    /// Compiles what a compilation generated under every lint, and loads it.
    ClassLoader load(Compilation compilation) {
        return ProcessorHarness.compileAndLoad(compilation, out, List.of(lib));
    }

    static List<String> warnings(Compilation compilation) {
        return ProcessorHarness.messages(compilation, Diagnostic.Kind.WARNING);
    }

    static List<String> errors(Compilation compilation) {
        return ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR);
    }

    static Map<String, String> sources(Compilation compilation) {
        return ProcessorHarness.generatedSources(compilation);
    }

    /// The value of a `public static` field of a metamodel.
    static Object fact(ClassLoader loader, String metamodel, String field) throws ReflectiveOperationException {
        return loader.loadClass(metamodel).getField(field).get(null);
    }

    /// The names of the `public static` fields of a metamodel that hold facts, sorted: all but `TOKEN`.
    static List<String> factNames(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        return java.util.Arrays.stream(loader.loadClass(metamodel).getFields())
                .filter(f -> java.lang.reflect.Modifier.isStatic(f.getModifiers()))
                .map(java.lang.reflect.Field::getName)
                .filter(name -> !name.equals("TOKEN"))
                .sorted()
                .toList();
    }

    static TypeShape<?> shape(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        return (TypeShape<?>) fact(loader, metamodel + "$Data", "SHAPE");
    }

    static DeclaredToken<?> token(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        return (DeclaredToken<?>) fact(loader, metamodel, "TOKEN");
    }

    static void assertNoTokenOfAnotherMetamodel(Map<String, String> sources) {
        assertThat(sources.values()).noneMatch(source -> source.matches("(?s).*\\b[A-Z]\\w*_\\.TOKEN\\b.*"));
    }
}
