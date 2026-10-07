package me.supcheg.javafile.facts.processor.harness;

import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The harness on fixtures made for it: what the tests of a fixture are,
/// and that each fails when it must and tells why — a test that cannot
/// fail checks nothing.
class FixtureRunTest {
    private static final String THING = """
            package p;

            public class Thing {
                public static final int LIMIT = 3;

                public Dep dep() {
                    return null;
                }
            }
            """;
    private static final String DEP = "package p; public class Dep { public int x() { return 0; } }";
    private static final String DEP_V2 =
            "package p; public class Dep { public int x() { return 0; } public void y() {} }";

    @TempDir
    Path fixtures;

    @TempDir
    Path work;

    private void file(String path, String text) {
        Text.write(fixtures.resolve(path), text);
    }

    private void request(String directory, String pkg, String type) {
        file(directory + "/request/" + pkg + "/G.java", """
                package %s;

                @me.supcheg.javafile.facts.meta.Facts(p.%s.class)
                class G {}
                """.formatted(pkg, type));
    }

    /// Runs the tests of the fixtures, in order, each to its failure if it has one.
    private Map<String, Optional<Throwable>> run() {
        return run(false);
    }

    /// The same with the snapshots accepted, as `-Pfixtures.update` does.
    private Map<String, Optional<Throwable>> update() {
        return run(true);
    }

    /// Runs the tests with the system property [Snapshot#UPDATE_FLAG] as given, whatever the build has set it to.
    private Map<String, Optional<Throwable>> run(boolean update) {
        return ScopedValue.where(Snapshot.UPDATE, update)
                .call(() -> outcomes(FixtureRun.tests(
                                fixtures, work.resolve(String.valueOf(System.nanoTime())), Optional.empty()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static Stream<Map.Entry<String, Optional<Throwable>>> outcomes(Stream<? extends DynamicNode> nodes) {
        return nodes.flatMap(node -> switch (node) {
            case DynamicContainer container -> outcomes(container.getChildren());
            case DynamicTest test -> Stream.of(Map.entry(test.getDisplayName(), outcome(test)));
            default -> throw new IllegalStateException(node.toString());
        });
    }

    private static Optional<Throwable> outcome(DynamicTest test) {
        try {
            test.getExecutable().execute();
            return Optional.empty();
        } catch (Throwable failure) {
            return Optional.of(failure);
        }
    }

    private static final String OUTPUT = "one: the output of the processor is expected/";
    private static final String SHARED = "one: the metamodels of the JDK are expected-jdk/";
    private static final String HERITAGE = "one: the heritage a metamodel holds is the one its canonical form tells";

    @Test
    void theSnapshotIsComparedFileByFileAndLineByLine() throws IOException {
        file("one/lib/p/Thing.java", THING);
        file("one/lib/p/Dep.java", DEP);
        request("one", "gen", "Thing");

        // nothing is expected yet: every file of the output is one too many
        assertThat(run().get(OUTPUT))
                .get()
                .asString()
                .contains("unexpected: gen/facts/p/Thing_.java was written and is not expected")
                .contains("unexpected: index.txt")
                .contains("-Pfixtures.update");

        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(run().values()).containsOnly(Optional.empty());
        Path expected = fixtures.resolve("one/expected");
        assertThat(Text.read(expected.resolve("index.txt")))
                .contains("META-INF/javafile/metamodel/full/p.Thing -> gen.facts.p.Thing_\n")
                .contains("META-INF/javafile/metamodel/token/p.Dep -> gen.facts.p.Dep_\n");

        // one line changes, one file goes and one comes
        Path thing = expected.resolve("gen/facts/p/Thing_.java");
        Text.write(thing, Text.read(thing).replace("\"LIMIT\", PrimitiveToken.INT, 3)", "\"LIMIT\", null, 4)"));
        Files.delete(expected.resolve("gen/facts/p/Dep_.java"));
        file("one/expected/gen/facts/p/Gone_.java", "class Gone_ {}\n");
        assertThat(run().get(OUTPUT))
                .get()
                .asString()
                .contains("3 files differ")
                .contains("unexpected: gen/facts/p/Dep_.java was written and is not expected")
                .contains("missing: gen/facts/p/Gone_.java is expected and was not written")
                .contains("--- expected: gen/facts/p/Thing_.java\n+++ actual:   gen/facts/p/Thing_.java\n@@ -")
                .containsPattern("\n- .*\"LIMIT\", null, 4\\);\n\\+ .*\"LIMIT\", PrimitiveToken.INT, 3\\);\n");

        // an update writes what changed and deletes what is no longer written
        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(expected.resolve("gen/facts/p/Gone_.java")).doesNotExist();
        assertThat(run().values()).containsOnly(Optional.empty());
    }

    @Test
    void theChecksOfUseRunEachOnItsOwnAndFailByThrowing() {
        file("one/lib/p/Thing.java", THING);
        file("one/lib/p/Dep.java", DEP);
        request("one", "gen", "Thing");
        file("one/use/Checks.java", """
                import gen.facts.java.lang.String_;
                import gen.facts.p.Thing_;
                import me.supcheg.javafile.facts.Prim;
                import me.supcheg.javafile.facts.StaticFieldRef;
                import me.supcheg.javafile.facts.processor.harness.Typed;
                import p.Thing;

                import static me.supcheg.javafile.typed.Expressions.staticField;
                import static org.assertj.core.api.Assertions.assertThat;

                public final class Checks {
                    public static void holds() {
                        StaticFieldRef<Prim.Int> limit = Thing_.LIMIT;
                        assertThat(limit.constantValue()).contains(3);
                    }

                    public static void doesNotHold() {
                        assertThat(Thing_.LIMIT.constantValue()).contains(4);
                    }

                    public static void failsAnAssertStatement() {
                        assert Thing_.LIMIT.name().isEmpty() : "the name is " + Thing_.LIMIT.name();
                    }

                    public static void throughTheTypedLayer(Typed typed) {
                        assertThat(typed.apply(
                                        Thing_.LIMIT.type(), Thing_.TOKEN, thing -> staticField(Thing_.LIMIT), new Thing()))
                                .isEqualTo(3);
                    }

                    public static void anArgumentOfTheJdkAndAClassOfTheLibrary(Typed typed) {
                        assertThat(typed.apply(
                                        Thing_.LIMIT.type(), String_.TOKEN, text -> staticField(Thing_.LIMIT), "x"))
                                .isEqualTo(3);
                    }

                    public static void renderedWithoutRunning(Typed typed) {
                        assertThat(typed.render(
                                        Thing_.LIMIT.type(), Thing_.TOKEN, thing -> staticField(Thing_.LIMIT)))
                                .contains("class Out")
                                .contains("go(Thing ")
                                .contains("Thing.LIMIT");
                    }

                    static void notPublicSoNoCheck() {
                        throw new IllegalStateException();
                    }

                    public static int notVoidSoNoCheck() {
                        throw new IllegalStateException();
                    }
                }
                """);
        file("one/use/Helper.java", "final class Helper { public static void notOfAPublicClassSoNoCheck() {} }");
        update();

        Map<String, Optional<Throwable>> outcomes = run();

        assertThat(outcomes.keySet())
                .containsExactlyInAnyOrder(
                        SHARED,
                        OUTPUT,
                        "one: the metamodels compile under -Xlint:all -Xdoclint:all/protected -Werror",
                        HERITAGE,
                        "one: use/ compiles under -Xlint:all -Werror",
                        "one: use/Checks.holds",
                        "one: use/Checks.doesNotHold",
                        "one: use/Checks.failsAnAssertStatement",
                        "one: use/Checks.throughTheTypedLayer",
                        "one: use/Checks.renderedWithoutRunning",
                        "one: use/Checks.anArgumentOfTheJdkAndAClassOfTheLibrary");
        assertThat(outcomes.get("one: use/Checks.holds")).isEmpty();
        assertThat(outcomes.get("one: use/Checks.throughTheTypedLayer")).isEmpty();
        assertThat(outcomes.get("one: use/Checks.renderedWithoutRunning")).isEmpty();
        assertThat(outcomes.get("one: use/Checks.anArgumentOfTheJdkAndAClassOfTheLibrary"))
                .isEmpty();
        assertThat(outcomes.get("one: use/Checks.doesNotHold"))
                .get()
                .isInstanceOf(AssertionError.class)
                .asString()
                .contains("Optional[3]");
        assertThat(outcomes.get("one: use/Checks.failsAnAssertStatement"))
                .get()
                .isInstanceOf(AssertionError.class)
                .asString()
                .contains("the name is LIMIT");
    }

    @Test
    void useThatDoesNotCompileOrHasAWarningFailsOnce() {
        file("one/lib/p/Thing.java", THING);
        file("one/lib/p/Dep.java", DEP);
        request("one", "gen", "Thing");
        file("one/use/Checks.java", """
                public final class Checks {
                    public static void raw() {
                        java.util.List raw = new java.util.ArrayList<String>();
                    }
                }
                """);
        update();

        Map<String, Optional<Throwable>> outcomes = run();

        assertThat(outcomes.keySet())
                .containsExactlyInAnyOrder(
                        SHARED,
                        OUTPUT,
                        "one: the metamodels compile under -Xlint:all -Xdoclint:all/protected -Werror",
                        HERITAGE,
                        "one: use/ compiles under -Xlint:all -Werror");
        assertThat(outcomes.get("one: use/ compiles under -Xlint:all -Werror"))
                .get()
                .asString()
                .contains("Checks.java:3:")
                .contains("found raw type");
    }

    @Test
    void aFileOfUseFailsMustFailAsItsCommentsTell() {
        file("one/lib/p/Thing.java", THING);
        file("one/lib/p/Dep.java", DEP);
        request("one", "gen", "Thing");
        file("one/use-fails/Rejected.java", """
                import gen.facts.p.Thing_;

                class Rejected {
                    String limit = Thing_.LIMIT; // error: incompatible types
                    Object none = Thing_.NONE; // error: cannot find symbol
                }
                """);
        file("one/use-fails/Compiles.java", """
                import gen.facts.p.Thing_;

                class Compiles {
                    Object limit = Thing_.LIMIT; // error: incompatible types
                }
                """);
        file("one/use-fails/ForAnotherReason.java", """
                import gen.facts.p.Thing_;

                class ForAnotherReason {
                    String limit = Thing_.LIMIT; // error: cannot find symbol
                    Object typo = Thing_.LIMITT;
                }
                """);
        file("one/use-fails/Untold.java", "class Untold { String limit = gen.facts.p.Thing_.LIMIT; }");
        update();

        Map<String, Optional<Throwable>> outcomes = run();

        assertThat(outcomes.get("one: use-fails/Rejected.java")).isEmpty();
        assertThat(outcomes.get("one: use-fails/Compiles.java"))
                .get()
                .asString()
                .contains("Compiles.java is not rejected as its comments tell:")
                .contains("it compiles")
                .contains("line 4: no error with \"incompatible types\"");
        assertThat(outcomes.get("one: use-fails/ForAnotherReason.java"))
                .get()
                .asString()
                .contains("line 4: no error with \"cannot find symbol\"")
                .contains("an error that is not expected: ForAnotherReason.java:4:")
                .contains("an error that is not expected: ForAnotherReason.java:5:");
        assertThat(outcomes.get("one: use-fails/Untold.java"))
                .get()
                .asString()
                .contains("no line has an `// error: …` comment");
    }

    @Test
    void aCaseWhoseProcessorFailsExpectsItsDiagnosticsAlone() {
        file("one/lib/p/Hidden.java", "package p; class Hidden {}");
        file("one/lib/p/Thing.java", "package p; public class Thing { public void lost(Hidden h) {} }");
        request("one", "gen", "Thing");
        file("one/request/options.txt", """
                # a member without a fact is an error
                -Ajavafile.facts.strict=true
                """);
        update();

        assertThat(run()).containsOnlyKeys(SHARED, OUTPUT).containsValue(Optional.empty());
        assertThat(Snapshot.read(fixtures.resolve("one/expected")).files())
                .containsOnlyKeys("diagnostics.txt")
                .containsValue("gen/G.java:4:1: error: p.Thing: no fact of method lost(p.Hidden), which mentions"
                        + " types that are not public: p.Hidden\n");

        // code that uses the metamodels of such a case cannot be
        file("one/use/Checks.java", "public final class Checks { public static void check() {} }");
        assertThat(run().get("one: use/ is not there, as the processor fails"))
                .get()
                .asString()
                .contains("the processor fails, so there are no metamodels for use/");
    }

    @Test
    void casesShareTheLibraryAndOneMayBeOnTheClasspathOfAnother() {
        file("reuse/lib/p/Thing.java", THING);
        file("reuse/lib/p/Other.java", "package p; public class Other { public Dep dep() { return null; } }");
        file("reuse/lib/p/Dep.java", DEP);
        request("reuse/cases/a", "a", "Thing");
        // module b has module a on its classpath, and the same library
        request("reuse/cases/b", "b", "Other");
        file("reuse/cases/b/request/classpath.txt", "a\n");
        file("reuse/cases/b/use/Checks.java", """
                import static org.assertj.core.api.Assertions.assertThat;

                public final class Checks {
                    public static void theMetamodelOfTheOtherModuleIsTheOneUsed() {
                        assertThat(b.facts.p.Other_.dep.result()).isEqualTo(a.facts.p.Dep_.TOKEN);
                    }
                }
                """);
        // module c has module a on its classpath too, and another version of the library
        request("reuse/cases/c", "c", "Other");
        file("reuse/cases/c/request/classpath.txt", "a\n");
        file("reuse/cases/c/lib/p/Dep.java", DEP_V2);

        assertThat(update().values()).containsOnly(Optional.empty());
        Map<String, Optional<Throwable>> outcomes = run();

        assertThat(outcomes.values()).containsOnly(Optional.empty());
        assertThat(outcomes)
                .containsKeys(
                        "reuse/a: the output of the processor is expected/",
                        "reuse/b: use/Checks.theMetamodelOfTheOtherModuleIsTheOneUsed",
                        "reuse/c: the metamodels compile under -Xlint:all -Xdoclint:all/protected -Werror");
        Path cases = fixtures.resolve("reuse/cases");
        assertThat(Snapshot.read(cases.resolve("a/expected")).files()).containsKey("a/facts/p/Dep_.java");
        assertThat(Snapshot.read(cases.resolve("b/expected")).files())
                .containsOnlyKeys("b/facts/p/Other_.java", "index.txt");
        assertThat(Snapshot.read(cases.resolve("c/expected")).files())
                .containsOnlyKeys("c/facts/p/Other_.java", "c/facts/p/Dep_.java", "index.txt", "diagnostics.txt")
                .hasEntrySatisfying(
                        "diagnostics.txt",
                        diagnostics -> assertThat(diagnostics)
                                .endsWith(
                                        "warning: metamodel a.facts.p.Dep_ on the classpath is stale against p.Dep: it was"
                                                + " generated from a different p.Dep; generating c.facts.p.Dep_"
                                                + (char) 10));
    }

    @Test
    void theTypedLayerRendersAgainstTheLibraryOrAgainstAVersionOfIt() {
        file("one/lib/p/Thing.java", THING);
        file("one/lib/p/Dep.java", DEP);
        file("one/lib/p/Extra.java", "package p; public class Extra {}");
        request("one", "gen", "Dep");
        // a version replaces a file of the library, adds one, and takes one out with an empty file
        file("one/targets/more/p/Dep.java", DEP_V2);
        file("one/targets/more/p/Added.java", "package p; public class Added {}");
        file("one/targets/more/p/Extra.java", "");
        file("one/targets/less/p/Dep.java", "package p; public class Dep {}");
        file("one/use/Checks.java", """
                import gen.facts.java.lang.Object_;
                import gen.facts.p.Dep_;
                import me.supcheg.javafile.facts.PrimitiveToken;
                import me.supcheg.javafile.facts.Prim;
                import me.supcheg.javafile.facts.TargetClasspathMismatchException;
                import me.supcheg.javafile.facts.processor.harness.Typed;
                import me.supcheg.javafile.typed.Expr;
                import p.Dep;

                import static me.supcheg.javafile.typed.Expressions.call;
                import static me.supcheg.javafile.typed.Expressions.new_;
                import static org.assertj.core.api.Assertions.assertThat;
                import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
                import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
                import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

                public final class Checks {
                    private static Expr<Prim.Int> x(Expr<Dep> dep) {
                        return call(dep, Dep_.x);
                    }

                    public static void theLibraryIsTheTargetClasspath(Typed typed) {
                        assertThat(typed.verified(PrimitiveToken.INT, Dep_.TOKEN, Checks::x))
                                .containsExactly("p.Dep: unchanged");
                        assertThat(typed.apply(PrimitiveToken.INT, Dep_.TOKEN, Checks::x, new Dep())).isEqualTo(0);
                    }

                    public static void aVersionIsTheLibraryWithItsFiles(Typed typed) {
                        Typed more = typed.against("more");

                        assertThat(more.verified(PrimitiveToken.INT, Dep_.TOKEN, Checks::x))
                                .containsExactly("p.Dep: changed");
                        assertThat(more.render(PrimitiveToken.INT, Dep_.TOKEN, Checks::x)).contains("return v0.x();");
                        assertThat(more.apply(PrimitiveToken.INT, Object_.TOKEN, o -> x(new_(Dep_.new_)), "a"))
                                .isEqualTo(0);
                    }

                    public static void aVersionTheMetamodelDoesNotHoldOfRejectsTheFact(Typed typed) {
                        Typed less = typed.against("less");

                        assertThat(less.verified(PrimitiveToken.INT, Dep_.TOKEN, Checks::x))
                                .containsExactly("p.Dep: mismatched");
                        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                                .isThrownBy(() -> less.render(PrimitiveToken.INT, Dep_.TOKEN, Checks::x))
                                .withMessageContaining("missing: method public overridable x() -> int throws -");
                    }

                    public static void anArgumentOfTheLibraryIsNotOfTheVersion(Typed typed) {
                        assertThatIllegalArgumentException()
                                .isThrownBy(() -> typed.against("more")
                                        .apply(PrimitiveToken.INT, Dep_.TOKEN, Checks::x, new Dep()))
                                .withMessageContaining("give an argument of the JDK");
                    }

                    public static void aVersionIsADirectoryOfTargets(Typed typed) {
                        assertThatIllegalStateException()
                                .isThrownBy(() -> typed.against("none"))
                                .withMessage("case one of fixture one has no targets/none/");
                    }

                    public static void expectsWhatTheVersionDoesNotGive(Typed typed) {
                        assertThat(typed.against("less").verified(PrimitiveToken.INT, Dep_.TOKEN, Checks::x))
                                .containsExactly("p.Dep: unchanged");
                    }
                }
                """);
        update();

        Map<String, Optional<Throwable>> outcomes = run();

        assertThat(failed(outcomes)).containsExactly("one: use/Checks.expectsWhatTheVersionDoesNotGive");
        assertThat(message(outcomes, "one: use/Checks.expectsWhatTheVersionDoesNotGive"))
                .contains("p.Dep: mismatched");
        assertThat(outcomes)
                .containsKeys(
                        "one: use/Checks.theLibraryIsTheTargetClasspath",
                        "one: use/Checks.aVersionIsTheLibraryWithItsFiles",
                        "one: use/Checks.aVersionTheMetamodelDoesNotHoldOfRejectsTheFact",
                        "one: use/Checks.anArgumentOfTheLibraryIsNotOfTheVersion",
                        "one: use/Checks.aVersionIsADirectoryOfTargets");
        // the version was compiled with its files: Added is there, Extra is not
        assertThat(compiledClasses("targets/more/lib"))
                .contains("p/Added.class", "p/Dep.class", "p/Thing.class")
                .doesNotContain("p/Extra.class");
    }

    /// The class files under the directories of the work directory that end with `suffix`, by their paths in it.
    private List<String> compiledClasses(String suffix) {
        try (Stream<Path> files = Files.walk(work)) {
            return files.filter(Files::isRegularFile)
                    .map(file -> Text.path(work.relativize(file)))
                    .filter(path -> path.contains(suffix + "/") && path.endsWith(".class"))
                    .map(path -> path.substring(path.indexOf(suffix + "/") + suffix.length() + 1))
                    .toList();
        } catch (IOException e) {
            throw new java.io.UncheckedIOException(e);
        }
    }

    private static Set<String> failed(Map<String, Optional<Throwable>> outcomes) {
        return outcomes.entrySet().stream()
                .filter(outcome -> outcome.getValue().isPresent())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private static String message(Map<String, Optional<Throwable>> outcomes, String test) {
        return outcomes.get(test).orElseThrow().getMessage();
    }

    @Test
    void theMetamodelsOfTheJdkAreKeptOnceForTheFixtureAndAVariantStaysInItsCase() throws IOException {
        file("jdk/lib/p/Thing.java", "package p; public class Thing { public Number any() { return null; } }");
        // a and b mention Number: its metamodel is the token-only one
        request("jdk/cases/a", "gen", "Thing");
        request("jdk/cases/b", "gen", "Thing");
        // c asks for Number: the full one, which differs
        file("jdk/cases/c/request/gen/G.java", """
                package gen;

                @me.supcheg.javafile.facts.meta.Facts({p.Thing.class, Number.class})
                class G {}
                """);
        String number = "gen/facts/java/lang/Number_.java";
        String shared = "jdk: the metamodels of the JDK are expected-jdk/";
        String a = "jdk/a: the output of the processor is expected/";
        String b = "jdk/b: the output of the processor is expected/";
        String c = "jdk/c: the output of the processor is expected/";
        Path root = fixtures.resolve("jdk");

        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(run().values()).containsOnly(Optional.empty());
        assertThat(Snapshot.read(root.resolve("expected-jdk")).files().keySet())
                .contains(number)
                .allMatch(Snapshot::platform);
        assertThat(Snapshot.read(root.resolve("cases/a/expected")).files().keySet())
                .containsExactlyInAnyOrder("gen/facts/p/Thing_.java", "index.txt");
        assertThat(Snapshot.read(root.resolve("cases/c/expected")).files())
                .containsKey(number)
                .hasEntrySatisfying(
                        "index.txt",
                        index -> assertThat(index)
                                .contains("full/java.lang.Number -> gen.facts.java.lang.Number_")
                                .contains("full/p.Thing -> gen.facts.p.Thing_"));
        assertThat(Snapshot.read(root.resolve("cases/a/expected")).files().get("index.txt"))
                .contains("token/java.lang.Number -> gen.facts.java.lang.Number_");
        assertThat(Text.read(root.resolve("cases/c/expected").resolve(number)))
                .hasSizeGreaterThan(
                        Text.read(root.resolve("expected-jdk").resolve(number)).length());

        // the shared file is not what the output is: it fails for the cases that share it, and the shared test
        Path sharedFile = root.resolve("expected-jdk").resolve(number);
        Text.write(sharedFile, Text.read(sharedFile) + "// not so\n");
        Map<String, Optional<Throwable>> outcomes = run();
        assertThat(failed(outcomes)).containsExactlyInAnyOrder(shared, a, b);
        assertThat(message(outcomes, a)).contains("--- expected: " + number).contains("\n- // not so\n");
        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(run().values()).containsOnly(Optional.empty());

        // the variant of a case is its own: without it the case has the shared file, which differs
        Path variant = root.resolve("cases/c/expected").resolve(number);
        String text = Text.read(variant);
        Files.delete(variant);
        assertThat(failed(run())).containsExactly(c);
        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(Text.read(variant)).isEqualTo(text);

        // a file of a case that is the shared one is one too many
        Path same = root.resolve("cases/a/expected").resolve(number);
        Text.write(same, Text.read(sharedFile));
        outcomes = run();
        assertThat(failed(outcomes)).containsExactly(a);
        assertThat(message(outcomes, a)).contains("redundant: " + number);
        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(same).doesNotExist();

        // a shared file no case writes is one too many
        Path gone = root.resolve("expected-jdk/gen/facts/java/lang/Gone_.java");
        Text.write(gone, "class Gone_ {}\n");
        outcomes = run();
        assertThat(failed(outcomes)).containsExactly(shared);
        assertThat(message(outcomes, shared))
                .contains("missing: gen/facts/java/lang/Gone_.java is expected and was not written");
        assertThat(update().values()).containsOnly(Optional.empty());
        assertThat(gone).doesNotExist();

        // and without the shared file the cases that need it have none
        Files.delete(sharedFile);
        assertThat(failed(run())).containsExactlyInAnyOrder(shared, a, b);
    }

    @Test
    void theSharedVariantIsTheMostCommonAndOnATieTheLeastWhateverTheOrder() {
        String path = "gen/facts/java/lang/Number_.java";
        Snapshot x = snapshot(Map.of(path, "x", "index.txt", "x\n"));
        Snapshot y = snapshot(Map.of(path, "y", "index.txt", "y\n", "gen/facts/p/Thing_.java", "y"));

        assertThat(Snapshot.common(List.of(x, y, y)).files()).containsExactly(Map.entry(path, "y"));
        assertThat(Snapshot.common(List.of(y, x)).files()).containsExactly(Map.entry(path, "x"));
        assertThat(Snapshot.common(List.of(x, y)).files())
                .isEqualTo(Snapshot.common(List.of(y, x)).files());
        assertThat(Snapshot.common(List.of()).files()).isEmpty();
    }

    @Test
    void aSourceUnderJavaJavaxOrJdkOfTheBasePackageIsOneOfTheJdk() {
        assertThat(Stream.of(
                        "gen/facts/java/lang/String_.java",
                        "gen/facts/javax/annotation/processing/Processor_.java",
                        "com/acme/jdk/internal/Unsafe_.java",
                        "java/util/List_.java"))
                .allMatch(Snapshot::platform);
        assertThat(Stream.of(
                        "gen/facts/p/Thing_.java",
                        "gen/facts/p/javafile/Thing_.java",
                        "gen/facts/p/java_.java",
                        "index.txt",
                        "gen/facts/java/lang/String_.txt"))
                .noneMatch(Snapshot::platform);
    }

    private static Snapshot snapshot(Map<String, String> files) {
        return new Snapshot(new TreeMap<>(files));
    }

    @Test
    void aFixtureIsItsOnlyCaseOrHasCases() {
        file("both/request/gen/G.java", "package gen; class G {}");
        file("both/cases/a/request/gen/G.java", "package gen; class G {}");

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Fixture.all(fixtures))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("fixture both must have either request/ or cases/");
    }
}
