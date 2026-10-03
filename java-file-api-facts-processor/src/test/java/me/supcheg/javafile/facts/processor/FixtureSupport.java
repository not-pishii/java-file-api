package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeShape;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// What the fixtures of full metamodels (mini-spec §9.2) share: a fixture
/// library is compiled, `@Facts` asks for some of its types, and the
/// generated metamodels are compiled under every lint, loaded, and read back
/// as the facts they hold.
///
/// To be removed (mini-spec §11, 9t): a fixture is a directory of
/// `src/test/fixtures` now, see `README.md` there and [FixturesTest];
/// nothing new is written against this class.
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
        return Arrays.stream(loader.loadClass(metamodel).getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .map(Field::getName)
                .filter(name -> !name.equals("TOKEN"))
                .sorted()
                .toList();
    }

    /// The names of the facts of a metamodel, sorted: its `public` fields, of the class and of its
    /// instances, but `TOKEN`, `ANY` and `token`, and its methods, which make the facts of generic methods.
    static List<String> memberNames(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        Class<?> type = loader.loadClass(metamodel);
        return Stream.concat(
                        Arrays.stream(type.getFields())
                                .map(Field::getName)
                                .filter(name ->
                                        !List.of("TOKEN", "ANY", "token").contains(name)),
                        Arrays.stream(type.getDeclaredMethods())
                                .filter(m -> Modifier.isPublic(m.getModifiers()))
                                .map(Method::getName))
                .sorted()
                .toList();
    }

    /// An instance of a generic metamodel: its constructor takes a token per type parameter.
    static Object instance(ClassLoader loader, String metamodel, RefToken<?>... witnesses)
            throws ReflectiveOperationException {
        Class<?>[] parameters = new Class<?>[witnesses.length];
        Arrays.fill(parameters, RefToken.class);
        return loader.loadClass(metamodel).getConstructor(parameters).newInstance((Object[]) witnesses);
    }

    /// The value of a `public` field of an instance of a generic metamodel.
    static Object fact(Object instance, String field) throws ReflectiveOperationException {
        return instance.getClass().getField(field).get(instance);
    }

    /// The fact of a generic method: what the method of a metamodel returns for a token per type
    /// parameter. `instance` is `null` for a `static` one.
    static Object made(ClassLoader loader, String metamodel, Object instance, String method, RefToken<?>... witnesses)
            throws ReflectiveOperationException {
        Class<?>[] parameters = new Class<?>[witnesses.length];
        Arrays.fill(parameters, RefToken.class);
        return loader.loadClass(metamodel).getMethod(method, parameters).invoke(instance, (Object[]) witnesses);
    }

    /// Compiles code that uses the generated metamodels, after [#load] has compiled them.
    Compilation use(String source) {
        List<File> classpath = new ArrayList<>(List.of(out.toFile(), lib.toFile()));
        for (String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            classpath.add(new File(entry));
        }
        return Compiler.javac()
                .withClasspath(classpath)
                .withOptions("-proc:none")
                .compile(ProcessorHarness.source(source));
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
