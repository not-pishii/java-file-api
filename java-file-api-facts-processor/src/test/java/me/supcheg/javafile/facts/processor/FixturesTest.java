package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.FixtureRun;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/// The fixtures of `src/test/fixtures` (mini-spec §9.2, §11 9t): the
/// processor on real libraries, its output against the snapshots, and the
/// code that uses the metamodels. `README.md` of that directory tells how
/// a fixture is laid out and how its snapshots are accepted.
class FixturesTest {

    /// The directory of the fixtures in that of the project, where Gradle and the IDE run the tests.
    private static final Path FIXTURES = Path.of("src/test/fixtures");

    /// The system property that leaves one fixture, `plain`, or one case
    /// of one, `inheritance/supertypes`, as `-Pfixtures.only=…` of the
    /// build sets it.
    private static final String ONLY = "fixtures.only";

    @TempDir
    static Path work;

    @TestFactory
    Stream<DynamicNode> fixtures() {
        return FixtureRun.tests(
                FIXTURES, work, Optional.ofNullable(System.getProperty(ONLY)).filter(name -> !name.isEmpty()));
    }
}
