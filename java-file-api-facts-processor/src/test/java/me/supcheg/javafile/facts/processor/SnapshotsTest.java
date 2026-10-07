package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Fixture;
import me.supcheg.javafile.facts.processor.harness.Snapshot;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
    /// `supertypes` and by request in case `declared`. The code of the two
    /// is the same; the comment of the class tells which it is (Q14), and
    /// nothing else differs.
    @Test
    void theMetamodelOfASupertypeNobodyAskedForIsTheOneARequestGives() {
        Path cases = FIXTURES.resolve("inheritance/cases");
        String base = "gen/facts/p/Base_.java";
        String asSupertype =
                Snapshot.read(cases.resolve("supertypes/expected")).files().get(base);
        String asRequested =
                Snapshot.read(cases.resolve("declared/expected")).files().get(base);

        assertThat(asSupertype).isNotNull();
        assertThat(code(asSupertype)).isEqualTo(code(asRequested));
        assertThat(comments(asSupertype))
                .filteredOn(line -> !comments(asRequested).contains(line))
                .containsExactly(
                        "/// The full metamodel of [Base]: a fact of every `public` and every `protected` member the"
                                + " type declares, the latter held back for a subclass.",
                        "/// `@Facts` does not ask for [Base]: it is here as a supertype of [p.Derived], whose"
                                + " inherited members are called through this metamodel.");
        assertThat(comments(asRequested))
                .filteredOn(line -> !comments(asSupertype).contains(line))
                .containsExactly("/// The full metamodel of [Base], which `@Facts` asks for: a fact of every `public`"
                        + " and every `protected` member the type declares, the latter held back for a subclass.");
    }

    /// Every metamodel has documentation comments, and no other comment: a line is a comment or code, so
    /// what holds of the lines that are no comments holds of the code.
    @Test
    void theCommentsOfAMetamodelAreDocumentationCommentsOnLinesOfTheirOwn() {
        assertThat(sources()).isNotEmpty().allSatisfy(source -> {
            assertThat(comments(source.getValue())).as(source.getKey()).isNotEmpty();
            assertThat(code(source.getValue()))
                    .as(source.getKey())
                    .doesNotContain("//")
                    .doesNotContain("/*");
        });
    }

    /// The lines of a source that are not documentation comments.
    private static String code(String source) {
        return source.lines().filter(line -> !line.strip().startsWith("///")).collect(Collectors.joining("\n"));
    }

    /// The documentation comments of a source, line by line.
    private static List<String> comments(String source) {
        return source.lines()
                .map(String::strip)
                .filter(line -> line.startsWith("///"))
                .toList();
    }
}
