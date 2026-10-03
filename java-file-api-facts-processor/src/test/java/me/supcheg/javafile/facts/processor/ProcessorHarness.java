package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import javax.annotation.processing.Processor;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

/// Compiles fixture libraries and generators with [FactsProcessor]
/// (mini-spec §9.2): a library is compiled into a directory, the generator
/// against it with the processor, and what the processor wrote is read back
/// as sources, written into a directory for the next compilation, or loaded.
///
/// To be removed (mini-spec §11, 9t): the tests that still use it move to
/// [me.supcheg.javafile.facts.processor.harness.Javac] or into
/// `src/test/fixtures`; nothing new is written against it.
final class ProcessorHarness {
    private static final Pattern PACKAGE = Pattern.compile("\\bpackage\\s+([\\w.]+)\\s*;");
    private static final Pattern TYPE = Pattern.compile("\\b(?:class|interface|enum|record)\\s+([\\w$]+)");

    private ProcessorHarness() {}

    /// A source file named after its package and first top-level type, or
    /// `package-info` for a file that declares none.
    static JavaFileObject source(String source) {
        Matcher pkg = PACKAGE.matcher(source);
        String prefix = pkg.find() ? pkg.group(1) + "." : "";
        Matcher type = TYPE.matcher(source);
        return JavaFileObjects.forSourceString(prefix + (type.find() ? type.group(1) : "package-info"), source);
    }

    /// Compiles a library without annotation processing into `directory`.
    ///
    /// @param directory where the class files go
    /// @param classpath more classpath entries
    /// @param sources the library
    /// @return `directory`, for use as a classpath entry
    static Path library(Path directory, List<Path> classpath, String... sources) {
        Compilation compilation = javac().withClasspath(classpath(classpath))
                .withOptions("-proc:none")
                .compile(Stream.of(sources).map(ProcessorHarness::source).toList());
        assertThat(compilation.status()).as("%s", compilation.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        return write(compilation, directory);
    }

    /// Compiles a generator with [FactsProcessor] and `others`.
    ///
    /// @param classpath the libraries
    /// @param options javac options, such as `-Ajavafile.facts.package=…`
    /// @param others processors to run alongside
    /// @param sources the generator
    /// @return the compilation
    static Compilation process(List<Path> classpath, List<String> options, List<Processor> others, String... sources) {
        List<Processor> processors = new ArrayList<>(others);
        processors.add(new FactsProcessor());
        return javac().withClasspath(classpath(classpath))
                .withProcessors(processors)
                .withOptions(options)
                .compile(Stream.of(sources).map(ProcessorHarness::source).toList());
    }

    /// Compiles a generator with [FactsProcessor] alone and no options.
    static Compilation process(List<Path> classpath, String... sources) {
        return process(classpath, List.of(), List.of(), sources);
    }

    /// Asserts that a compilation succeeded, and returns it.
    static Compilation succeeded(Compilation compilation) {
        assertThat(compilation.status()).as("%s", compilation.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        return compilation;
    }

    /// The sources a compilation generated, by qualified name.
    static Map<String, String> generatedSources(Compilation compilation) {
        Map<String, String> sources = new TreeMap<>();
        for (JavaFileObject file : compilation.generatedSourceFiles()) {
            String path = relative(file);
            String name = path.substring(0, path.length() - ".java".length()).replace('/', '.');
            try {
                sources.put(name, file.getCharContent(true).toString());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return sources;
    }

    /// The metamodels every compilation generates besides those of its
    /// own types, unless it reuses them: the full one of `java.lang.Object`,
    /// which every requested type extends, and the token-only ones of the
    /// types the signatures of `Object` mention.
    ///
    /// @param base the base of the mirror packages
    /// @param others more metamodels, by qualified name
    /// @return `others` and those of `Object`
    static List<String> withObject(String base, String... others) {
        return Stream.concat(
                        Stream.of(others),
                        Stream.of("Object_", "Class_", "InterruptedException_", "String_")
                                .map(name -> base + ".java.lang." + name))
                .toList();
    }

    /// The resources a compilation wrote to the class output, by path.
    static Map<String, String> resources(Compilation compilation) {
        Map<String, String> resources = new TreeMap<>();
        for (JavaFileObject file : compilation.generatedFiles()) {
            if (file.getKind() == JavaFileObject.Kind.OTHER
                    && file.toUri().getPath().startsWith("/CLASS_OUTPUT/")) {
                try {
                    resources.put(relative(file), file.getCharContent(true).toString());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
        return resources;
    }

    /// The diagnostics of a kind, as their messages.
    static List<String> messages(Compilation compilation, Diagnostic.Kind kind) {
        return compilation.diagnostics().stream()
                .filter(d -> d.getKind() == kind)
                .map(d -> d.getMessage(null))
                .toList();
    }

    /// Writes the class output of a compilation — classes and resources —
    /// into `directory`, as a jar would hold it.
    static Path write(Compilation compilation, Path directory) {
        for (JavaFileObject file : compilation.generatedFiles()) {
            if (!file.toUri().getPath().startsWith("/CLASS_OUTPUT/")) {
                continue;
            }
            Path target = directory.resolve(relative(file));
            try (InputStream in = file.openInputStream()) {
                Files.createDirectories(target.getParent());
                Files.write(target, in.readAllBytes());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return directory;
    }

    /// Compiles the sources a compilation generated on their own, every
    /// lint on and warnings as errors, and loads the classes: the generated
    /// code is valid on its own and javac has nothing to say about it.
    ///
    /// @param compilation the compilation of the generator
    /// @param directory where the class files go
    /// @param classpath the libraries
    /// @return a loader of the generated classes and the libraries
    static ClassLoader compileAndLoad(Compilation compilation, Path directory, List<Path> classpath) {
        List<JavaFileObject> sources = generatedSources(compilation).entrySet().stream()
                .map(e -> JavaFileObjects.forSourceString(e.getKey(), e.getValue()))
                .toList();
        Compilation generated = javac().withClasspath(classpath(classpath))
                .withOptions("-proc:none", "-Xlint:all", "-Werror")
                .compile(sources);
        assertThat(generated.status()).as("%s", generated.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        assertThat(generated.diagnostics()).isEmpty();
        write(generated, directory);
        List<URL> urls = new ArrayList<>();
        try {
            urls.add(directory.toUri().toURL());
            for (Path entry : classpath) {
                urls.add(entry.toUri().toURL());
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        return new URLClassLoader(urls.toArray(URL[]::new), ProcessorHarness.class.getClassLoader());
    }

    private static List<File> classpath(List<Path> extra) {
        List<File> entries = new ArrayList<>();
        extra.forEach(p -> entries.add(p.toFile()));
        for (String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            entries.add(new File(entry));
        }
        return entries;
    }

    private static String relative(JavaFileObject file) {
        String path = file.toUri().getPath();
        return path.substring(path.indexOf('/', 1) + 1);
    }
}
