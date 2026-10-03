package me.supcheg.javafile.facts.processor.harness;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/// A directory of `src/test/fixtures`: a library and the cases that ask
/// the processor for facts of it. `README.md` there tells the layout.
///
/// @param name the name of the directory
/// @param directory the directory
/// @param cases its cases, in the order of their names: the fixture itself if it has `request/`,
///         or the directories of `cases/`
public record Fixture(String name, Path directory, List<Case> cases) {

    public Fixture {
        cases = List.copyOf(cases);
    }

    /// The fixtures of a directory, in the order of their names.
    ///
    /// @param root the directory, `src/test/fixtures`
    /// @return the fixtures
    public static List<Fixture> all(Path root) {
        return directories(root).map(Fixture::of).toList();
    }

    /// Reads a fixture.
    ///
    /// @param directory its directory
    /// @return the fixture
    /// @throws IllegalStateException if the directory has both `request/` and `cases/`, or neither
    public static Fixture of(Path directory) {
        String name = directory.getFileName().toString();
        boolean single = Files.isDirectory(directory.resolve("request"));
        boolean several = Files.isDirectory(directory.resolve("cases"));
        if (single == several) {
            throw new IllegalStateException("fixture " + name + " must have either request/ or cases/");
        }
        return new Fixture(
                name,
                directory,
                single
                        ? List.of(new Case(name, directory))
                        : directories(directory.resolve("cases"))
                                .map(each -> new Case(each.getFileName().toString(), each))
                                .toList());
    }

    /// The sources of the library every case shares.
    ///
    /// @return `lib/`
    public Path lib() {
        return directory.resolve("lib");
    }

    /// The metamodels of the types of the JDK, which the cases of the
    /// fixture write alike: kept here once instead of in `expected/` of
    /// every case, see [Snapshot#common].
    ///
    /// @return `expected-jdk/`
    public Path shared() {
        return directory.resolve("expected-jdk");
    }

    /// Whether the fixture is its only case: it has `request/` itself.
    ///
    /// @return whether there is no `cases/`
    public boolean single() {
        return cases.stream().anyMatch(each -> each.directory().equals(directory));
    }

    /// One request to the processor and what is expected of it.
    ///
    /// @param name the name of the directory
    /// @param directory the directory of `request/`, `expected/`, `use/` and `use-fails/`
    public record Case(String name, Path directory) {

        /// The sources the processor runs on — the classes with `@Facts`
        /// — next to `options.txt` and `classpath.txt`.
        ///
        /// @return `request/`
        public Path request() {
            return directory.resolve("request");
        }

        /// The options of javac for the request, such as
        /// `-Ajavafile.facts.strict=true`.
        ///
        /// @return the lines of `request/options.txt`; none without the file
        public List<String> options() {
            return Text.entries(request().resolve("options.txt"));
        }

        /// The cases of the same fixture whose output — the metamodels
        /// compiled, and their index — is on the classpath of this one,
        /// as the jar of another module would be.
        ///
        /// @return the lines of `request/classpath.txt`; none without the file
        public List<String> classpath() {
            return Text.entries(request().resolve("classpath.txt"));
        }

        /// The sources that replace those of the library of the fixture,
        /// or add to them, for this case: another version of the library.
        ///
        /// @return `lib/` of the case
        public Path lib() {
            return directory.resolve("lib");
        }

        /// The snapshot the output of the processor must equal.
        ///
        /// @return `expected/`
        public Path expected() {
            return directory.resolve("expected");
        }

        /// The code that compiles against the metamodels and runs.
        ///
        /// @return `use/`
        public Path use() {
            return directory.resolve("use");
        }

        /// The code that must not compile against the metamodels.
        ///
        /// @return `use-fails/`
        public Path useFails() {
            return directory.resolve("use-fails");
        }
    }

    private static Stream<Path> directories(Path parent) {
        try (Stream<Path> children = Files.list(parent)) {
            return children.filter(Files::isDirectory).sorted().toList().stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
