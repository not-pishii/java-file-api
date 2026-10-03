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
import java.util.regex.Pattern;
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
/// The metamodels of the types of the JDK — the sources under `java/`,
/// `javax/` or `jdk/` of the base package, see [#platform] — are the same
/// in most cases of a fixture, and a fixture keeps them once, in a snapshot
/// of its own that the cases share: see [#common], and [#verify(Path, Snapshot)]
/// for what a case expects then.
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

    private static final Pattern PLATFORM = Pattern.compile("(?:.*/)?(?:java|javax|jdk)/.*\\.java");

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

    /// How this snapshot differs from the one expected when the files of
    /// the JDK that the latter does not hold are those of `shared`: the files
    /// that differ, and the files it holds that are the shared ones and so
    /// need not be there.
    ///
    /// @param expected the snapshot the directory of the case holds
    /// @param shared the files the snapshots of the fixture share
    /// @return the differences, in the order of their paths
    public List<Difference> differences(Snapshot expected, Snapshot shared) {
        Snapshot effective = new Snapshot(Stream.concat(
                        shared.files.entrySet().stream()
                                .filter(file -> platform(file.getKey()) && files.containsKey(file.getKey())),
                        expected.files.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (_, own) -> own, TreeMap::new)));
        return Stream.concat(
                        differences(effective).stream(),
                        expected.files.entrySet().stream()
                                .filter(file -> platform(file.getKey())
                                        && file.getValue().equals(shared.files.get(file.getKey())))
                                .map(file -> new Difference.Redundant(file.getKey())))
                .sorted(Comparator.comparing(Difference::path))
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
        verify(directory, new Snapshot(new TreeMap<>()));
    }

    /// The same for a snapshot that shares its [#platform] files with
    /// others: a file of the JDK that the directory does not hold is
    /// expected to be the one of `shared`, and one the directory holds is
    /// expected to differ from that one — it is the variant of this snapshot
    /// alone. An update writes the directory without the files that are
    /// those of `shared`.
    ///
    /// @param directory the directory of the snapshot expected
    /// @param shared the files the snapshots of the same fixture share, see [#common]
    public void verify(Path directory, Snapshot shared) {
        if (updating()) {
            without(shared).write(directory);
            return;
        }
        Snapshot expected = read(directory);
        List<Difference> differences = differences(expected, shared);
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

    /// Whether a path is that of the metamodel of a type of the JDK, which
    /// a fixture keeps once for its cases: a source under `java`, `javax`
    /// or `jdk` of the base package, `gen/facts/java/lang/String_.java`.
    ///
    /// @param path the path in a snapshot
    /// @return whether it is a source of such a directory
    public static boolean platform(String path) {
        return PLATFORM.matcher(path).matches();
    }

    /// The files of the JDK that the snapshots of the cases of a fixture
    /// share: for each path the text most of them write, and where as many
    /// write one as another, the least of the texts — so that which of the
    /// variants is the shared one does not depend on the order of the cases.
    ///
    /// @param outputs the snapshots of the cases
    /// @return the snapshot of the shared files
    public static Snapshot common(List<Snapshot> outputs) {
        return new Snapshot(outputs.stream()
                .flatMap(output -> output.files.entrySet().stream())
                .filter(file -> platform(file.getKey()))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        TreeMap::new,
                        Collectors.groupingBy(Map.Entry::getValue, Collectors.counting())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        file -> file.getValue().entrySet().stream()
                                .min(Comparator.<Map.Entry<String, Long>>comparingLong(variant -> -variant.getValue())
                                        .thenComparing(Map.Entry::getKey))
                                .orElseThrow()
                                .getKey(),
                        (a, _) -> a,
                        TreeMap::new)));
    }

    /// This snapshot without the files that are those of another.
    ///
    /// @param shared the files the snapshots share
    /// @return the files that are in this snapshot alone, or differ from the shared ones
    public Snapshot without(Snapshot shared) {
        return new Snapshot(files.entrySet().stream()
                .filter(file -> !file.getValue().equals(shared.files.get(file.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, _) -> a, TreeMap::new)));
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

        /// The file is in the snapshot of the case and is the shared one.
        ///
        /// @param path the path in the snapshot
        record Redundant(String path) implements Difference {}

        /// The difference as the failure of a test tells it: for a file
        /// that changed, the lines that differ, as `diff -u` prints them.
        ///
        /// @return the lines, each ended with `\n`
        default String render() {
            return switch (this) {
                case Missing(String path) -> "missing: " + path + " is expected and was not written\n";
                case Redundant(String path) ->
                    "redundant: " + path + " is the shared one, which the case need not hold\n";
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
