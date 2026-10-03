package me.supcheg.javafile.facts.processor.harness;

import com.google.testing.compile.Compilation;
import org.opentest4j.AssertionFailedError;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// What came of a run of [Javac], as a value: nothing of javac is alive in it.
///
/// @param succeeded whether javac reported no error
/// @param diagnostics what javac and the processors said, in order
/// @param generated the sources the processors wrote, in the order of their names; none if javac failed
/// @param classOutput the class files and the resources written, by path: `p/T.class`, `META-INF/…`;
///         none if javac failed
public record Compiled(
        boolean succeeded, List<Diagnosed> diagnostics, List<Source> generated, SortedMap<String, byte[]> classOutput) {

    public Compiled {
        diagnostics = List.copyOf(diagnostics);
        generated = List.copyOf(generated);
        classOutput = Collections.unmodifiableSortedMap(new TreeMap<>(classOutput));
    }

    static Compiled of(Compilation compilation) {
        boolean succeeded = compilation.status() == Compilation.Status.SUCCESS;
        return new Compiled(
                succeeded,
                compilation.diagnostics().stream().map(Diagnosed::of).toList(),
                generated(compilation, succeeded)
                        .filter(file -> file.getKind() == JavaFileObject.Kind.SOURCE)
                        .map(file -> new Source(
                                relative(file).replaceFirst("[.]java$", "").replace('/', '.'),
                                Text.normalize(new String(bytes(file), StandardCharsets.UTF_8))))
                        .sorted(Comparator.comparing(Source::name))
                        .toList(),
                generated(compilation, succeeded)
                        .filter(file -> file.toUri().getPath().startsWith("/CLASS_OUTPUT/"))
                        .collect(Collectors.toMap(Compiled::relative, Compiled::bytes, (a, _) -> a, TreeMap::new)));
    }

    /// What a compilation wrote; nothing if it failed, as javac keeps nothing of such a one.
    private static Stream<JavaFileObject> generated(Compilation compilation, boolean succeeded) {
        return succeeded ? compilation.generatedFiles().stream() : Stream.empty();
    }

    /// This, if javac reported no error; fails the test otherwise.
    ///
    /// @return this
    public Compiled orFail() {
        if (!succeeded) {
            throw new AssertionFailedError("javac failed:\n" + rendered());
        }
        return this;
    }

    /// This, if javac had nothing to say at all; fails the test otherwise.
    ///
    /// @return this
    public Compiled clean() {
        if (!succeeded || !diagnostics.isEmpty()) {
            throw new AssertionFailedError("javac has something to say:\n" + rendered());
        }
        return this;
    }

    /// The messages of the errors, in order.
    ///
    /// @return the messages
    public List<String> errors() {
        return messages(Diagnostic.Kind.ERROR).toList();
    }

    /// The messages of the warnings, in order.
    ///
    /// @return the messages
    public List<String> warnings() {
        return Stream.concat(messages(Diagnostic.Kind.WARNING), messages(Diagnostic.Kind.MANDATORY_WARNING))
                .toList();
    }

    /// The generated sources by the qualified names of their classes.
    ///
    /// @return the sources
    public SortedMap<String, String> sources() {
        return generated.stream().collect(Collectors.toMap(Source::name, Source::text, (a, _) -> a, TreeMap::new));
    }

    /// The resources written to the class output — the index of
    /// metamodels under `META-INF` — by path.
    ///
    /// @return the resources
    public SortedMap<String, String> resources() {
        return classOutput.entrySet().stream()
                .filter(entry -> !entry.getKey().endsWith(".class"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new String(entry.getValue(), StandardCharsets.UTF_8),
                        (a, _) -> a,
                        TreeMap::new));
    }

    /// The output of the processor as the files a fixture expects.
    ///
    /// @return the snapshot
    public Snapshot snapshot() {
        return Snapshot.of(this);
    }

    /// Writes the class output — classes and resources — into
    /// `directory`, as a jar would hold it.
    ///
    /// @param directory where the files go
    /// @return `directory`, for use as a classpath entry
    public Path writeTo(Path directory) {
        classOutput.forEach((path, bytes) -> {
            Path target = directory.resolve(path);
            try {
                Files.createDirectories(target.getParent());
                Files.write(target, bytes);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        return directory;
    }

    /// The diagnostics as javac prints them.
    ///
    /// @return the lines
    public String rendered() {
        return diagnostics.stream().map(Diagnosed::render).collect(Collectors.joining());
    }

    private Stream<String> messages(Diagnostic.Kind kind) {
        return diagnostics.stream().filter(d -> d.kind() == kind).map(Diagnosed::message);
    }

    private static String relative(JavaFileObject file) {
        String path = file.toUri().getPath();
        return path.substring(path.indexOf('/', 1) + 1);
    }

    private static byte[] bytes(JavaFileObject file) {
        try (InputStream in = file.openInputStream()) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
