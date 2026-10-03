package me.supcheg.javafile.facts.processor.harness;

import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The output of the processor as text files, the way `expected/` of a
/// fixture holds it:
///
/// - `p/q/T_.java` — every generated source, at the path of its class;
/// - `index.txt` — the resources of the class output, one per line as
///   `path -> content`, in the order of the paths; the line end that ends
///   the content is not written, and a content that is not one such line
///   is written as a Java string literal;
/// - `diagnostics.txt` — what javac and the processor said, in order, as
///   javac prints it, see [Diagnosed#render].
///
/// A file that would be empty is not there. Nothing is masked: the
/// fingerprints and the format number are the real ones.
///
/// @param files the texts by path, with `/` between the names and `\n` between the lines
public record Snapshot(SortedMap<String, String> files) {

    /// The system property that makes [#verify] accept the output: `true`,
    /// or set to nothing, as `-Pfixtures.update` of the build sets it.
    public static final String UPDATE = "fixtures.update";

    /// The file of the resources.
    public static final String INDEX = "index.txt";

    /// The file of the diagnostics.
    public static final String DIAGNOSTICS = "diagnostics.txt";

    public Snapshot {
        files = Collections.unmodifiableSortedMap(new TreeMap<>(files));
    }

    static Snapshot of(Compiled compiled) {
        return new Snapshot(Stream.concat(
                        compiled.generated().stream().map(source -> Map.entry(source.path(), source.text())),
                        Stream.of(
                                        Map.entry(
                                                INDEX,
                                                compiled.resources().entrySet().stream()
                                                        .map(resource -> resource.getKey() + " -> "
                                                                + content(resource.getValue()) + "\n")
                                                        .collect(Collectors.joining())),
                                        Map.entry(DIAGNOSTICS, compiled.rendered()))
                                .filter(file -> !file.getValue().isEmpty()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, _) -> a, TreeMap::new)));
    }

    private static String content(String resource) {
        String line = resource.substring(0, Math.max(0, resource.length() - 1));
        return resource.equals(line + "\n") && line.lines().count() == 1 && line.equals(line.strip())
                ? line
                : "\""
                        + resource.replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", "\\n")
                                .replace("\r", "\\r")
                        + "\"";
    }

    /// The files of a directory; none if there is no such directory.
    ///
    /// @param directory the directory, such as `expected/` of a fixture
    /// @return the snapshot it holds
    public static Snapshot read(Path directory) {
        if (!Files.isDirectory(directory)) {
            return new Snapshot(new TreeMap<>());
        }
        try (Stream<Path> paths = Files.walk(directory)) {
            return new Snapshot(paths.filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            file -> Text.path(directory.relativize(file)), Text::read, (a, _) -> a, TreeMap::new)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// Makes a directory hold this snapshot and nothing else: writes the
    /// files, and deletes the files that are not of it and the directories
    /// left empty.
    ///
    /// @param directory the directory, which need not exist
    public void write(Path directory) {
        files.forEach((path, text) -> Text.write(directory.resolve(path), text));
        if (!Files.isDirectory(directory)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(directory)) {
            paths.sorted(Comparator.reverseOrder())
                    .filter(path -> !path.equals(directory))
                    .filter(path -> Files.isDirectory(path)
                            ? isEmpty(path)
                            : !files.containsKey(Text.path(directory.relativize(path))))
                    .forEach(Snapshot::delete);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// How this snapshot differs from the one expected.
    ///
    /// @param expected the snapshot expected
    /// @return the files that differ, in the order of their paths; none if the snapshots are equal
    public List<Difference> differences(Snapshot expected) {
        return Stream.concat(files.keySet().stream(), expected.files.keySet().stream())
                .distinct()
                .sorted()
                .map(path -> difference(
                        path, Optional.ofNullable(expected.files.get(path)), Optional.ofNullable(files.get(path))))
                .flatMap(Optional::stream)
                .toList();
    }

    private static Optional<Difference> difference(String path, Optional<String> expected, Optional<String> actual) {
        return actual.map(text -> expected.<Optional<Difference>>map(wanted -> wanted.equals(text)
                                ? Optional.empty()
                                : Optional.of(new Difference.Changed(path, wanted, text)))
                        .orElseGet(() -> Optional.of(new Difference.Unexpected(path, text))))
                .orElseGet(() -> Optional.of(new Difference.Missing(path)));
    }

    /// Compares this snapshot with the one a directory holds and fails the
    /// test with the difference — or, if the system property [#UPDATE] is
    /// set, makes the directory hold this snapshot.
    ///
    /// @param directory the directory of the snapshot expected
    public void verify(Path directory) {
        if (updating()) {
            write(directory);
            return;
        }
        Snapshot expected = read(directory);
        List<Difference> differences = differences(expected);
        if (!differences.isEmpty()) {
            throw new AssertionFailedError(
                    "the output is not what " + Text.path(directory) + " holds: " + differences.size()
                            + (differences.size() == 1 ? " file differs" : " files differ")
                            + "; if the new output is right, accept it with -P" + UPDATE + "\n\n"
                            + differences.stream().map(Difference::render).collect(Collectors.joining("\n")),
                    expected.concatenated(),
                    concatenated());
        }
    }

    /// Whether [#verify] accepts the output instead of comparing it.
    ///
    /// @return whether the system property [#UPDATE] is set
    public static boolean updating() {
        return Optional.ofNullable(System.getProperty(UPDATE))
                .filter(value -> !value.equals("false"))
                .isPresent();
    }

    /// Every file after its path: what a diff viewer shows of the snapshot.
    private String concatenated() {
        return files.entrySet().stream()
                .map(file -> "=== " + file.getKey() + "\n" + file.getValue())
                .collect(Collectors.joining());
    }

    private static boolean isEmpty(Path directory) {
        try (Stream<Path> children = Files.list(directory)) {
            return children.findAny().isEmpty();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void delete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// How one file of a snapshot differs from the one expected.
    public sealed interface Difference {

        /// The path of the file.
        ///
        /// @return the path in the snapshot
        String path();

        /// The file is expected and is not in the output.
        ///
        /// @param path the path in the snapshot
        record Missing(String path) implements Difference {}

        /// The file is in the output and is not expected.
        ///
        /// @param path the path in the snapshot
        /// @param actual its text
        record Unexpected(String path, String actual) implements Difference {}

        /// The file is in both, with different texts.
        ///
        /// @param path the path in the snapshot
        /// @param expected the text expected
        /// @param actual the text in the output
        record Changed(String path, String expected, String actual) implements Difference {}

        /// The difference as the failure of a test tells it: for a file
        /// that changed, the lines that differ, as `diff -u` prints them.
        ///
        /// @return the lines, each ended with `\n`
        default String render() {
            return switch (this) {
                case Missing(String path) -> "missing: " + path + " is expected and was not written\n";
                case Unexpected(String path, String actual) ->
                    "unexpected: " + path + " was written and is not expected ("
                            + actual.lines().count() + " lines)\n";
                case Changed(String path, String expected, String actual) ->
                    Stream.of(
                                    Stream.of("--- expected: " + path, "+++ actual:   " + path),
                                    LineDiff.of(expected, actual).stream(),
                                    Stream.of("(the texts differ in their last line end alone)")
                                            .filter(_ -> LineDiff.of(expected, actual)
                                                    .isEmpty()))
                            .flatMap(lines -> lines)
                            .map(line -> line + "\n")
                            .collect(Collectors.joining());
            };
        }
    }
}
