package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Fixture;
import me.supcheg.javafile.facts.processor.harness.Snapshot;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// What holds of the snapshots of the fixtures taken together — of the
/// files in `expected/`, which [FixturesTest] keeps equal to the output of
/// the processor: a rule every generated source follows, and two cases
/// that must agree.
class SnapshotsTest {
    private static final Path FIXTURES = Path.of("src/test/fixtures");

    /// `Other_.TOKEN`: the token of another metamodel, which would initialize that class.
    private static final Pattern TOKEN_OF_ANOTHER = Pattern.compile("\\b[A-Z]\\w*_\\.TOKEN\\b");

    /// `new Other_<…>(…)`: an instance of another generic metamodel.
    private static final Pattern INSTANCE_OF_ANOTHER = Pattern.compile("\\bnew \\w+_<");

    /// The generated sources of every case of every fixture, and the metamodels of the JDK
    /// the cases of a fixture share, by `fixture/case/path` and `fixture/expected-jdk/path`.
    private static Stream<Map.Entry<String, String>> sources() {
        return Fixture.all(FIXTURES).stream()
                .flatMap(fixture -> Stream.concat(
                        sources(fixture.name() + "/expected-jdk", fixture.shared()),
                        fixture.cases().stream()
                                .flatMap(each -> sources(fixture.name() + "/" + each.name(), each.expected()))));
    }

    private static Stream<Map.Entry<String, String>> sources(String name, Path directory) {
        return Snapshot.read(directory).files().entrySet().stream()
                .filter(file -> file.getKey().endsWith(".java"))
                .map(file -> Map.entry(name + "/" + file.getKey(), file.getValue()));
    }

    /// Mini-spec §2.6: the metamodels are leaves — a cycle of types is no
    /// cycle of class initialization, whichever metamodel is touched first.
    @Test
    void aMetamodelRefersToAnotherByItsShapeAlone() {
        assertThat(sources())
                .isNotEmpty()
                .allSatisfy(source -> assertThat(source.getValue())
                        .as(source.getKey())
                        .doesNotContainPattern(TOKEN_OF_ANOTHER)
                        .doesNotContainPattern(INSTANCE_OF_ANOTHER));
    }

    /// The source of a metamodel is ASCII, so what the metamodel says is the same whatever `-encoding` it is
    /// compiled with: a constant or a name that is not ASCII is written as an escape (fixture `encoding`).
    @Test
    void theSourceOfAMetamodelIsAscii() {
        assertThat(sources())
                .isNotEmpty()
                .allSatisfy(source -> assertThat(source.getValue().chars().filter(c -> c >= 0x7f))
                        .as(source.getKey())
                        .isEmpty());
    }

    /// Q13: `Base` gets its metamodel as a supertype of `Derived` in case
    /// `supertypes` and by request in case `declared`.
    @Test
    void theMetamodelOfASupertypeNobodyAskedForIsTheOneARequestGives() {
        Path cases = FIXTURES.resolve("inheritance/cases");
        String base = "gen/facts/p/Base_.java";

        assertThat(Snapshot.read(cases.resolve("supertypes/expected")).files().get(base))
                .isNotNull()
                .isEqualTo(Snapshot.read(cases.resolve("declared/expected"))
                        .files()
                        .get(base));
    }
}
