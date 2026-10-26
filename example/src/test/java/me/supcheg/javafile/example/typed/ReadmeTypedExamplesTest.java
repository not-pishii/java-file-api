package me.supcheg.javafile.example.typed;

import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The typed examples of the README are what the README says they are: the code it shows is the code of
/// [ReadmeTypedExamples], the output it shows after each is what that code renders, javac accepts the output under
/// `-Xlint:all` without a warning, and the running class does what the example reads. The README is the source of the
/// output: a block that follows the code of an example is held to be what the example renders.
class ReadmeTypedExamplesTest {
    private static final Path SOURCES = Path.of("src/test/java/me/supcheg/javafile/example/typed");
    private static final String README = read(Path.of("..", "README.md"));
    private static final String EXAMPLES = read(SOURCES.resolve("ReadmeTypedExamples.java"));
    private static final List<Block> BLOCKS = blocks(README);

    private record Block(String language, String content) {}

    private static String read(Path path) {
        try {
            return Files.readString(path).replace("\r\n", "\n");
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read " + path.toAbsolutePath(), e);
        }
    }

    private static List<Block> blocks(String markdown) {
        List<Block> blocks = new ArrayList<>();
        Matcher fence = Pattern.compile("(?ms)^```(\\w*)\\n(.*?)^```$").matcher(markdown);
        while (fence.find()) {
            blocks.add(new Block(fence.group(1), fence.group(2)));
        }
        return blocks;
    }

    /// The lines between the `region` and the `endregion` markers of `name`, as the README shows them.
    private static String region(String name) {
        String begin = "// region " + name + "\n";
        int marker = EXAMPLES.indexOf(begin);
        assertThat(marker).as("region " + name).isNotNegative();
        String indent = EXAMPLES.substring(EXAMPLES.lastIndexOf('\n', marker) + 1, marker);
        int from = marker + begin.length();
        String body = EXAMPLES.substring(from, EXAMPLES.indexOf(indent + "// endregion", from));
        return body.lines()
                        .map(line -> line.isBlank() ? "" : line.substring(indent.length()))
                        .collect(Collectors.joining("\n"))
                + "\n";
    }

    /// The block of the README after the one that shows the code of `name`; the code is shown as it is.
    private static Block shownAfter(String name) {
        Block code = new Block("java", region(name));
        int at = BLOCKS.indexOf(code);
        assertThat(at)
                .as("the README shows the code of " + name + " as it is in ReadmeTypedExamples")
                .isNotNegative();
        assertThat(BLOCKS.size()).isGreaterThan(at + 1);
        return BLOCKS.get(at + 1);
    }

    private static CompiledClasses rendersAsShown(String name, JavaFile file) {
        assertThat(shownAfter(name)).isEqualTo(new Block("java", file.render()));
        return CompiledClasses.of(file);
    }

    @Test
    void theQuickStartIsGreeterThatGreets() throws Throwable {
        CompiledClasses greeter = rendersAsShown("greeter", ReadmeTypedExamples.greeter());

        assertThat(greeter.invoke("greet", "World")).isEqualTo("Hello, World!");
    }

    @Test
    void extendingAndImplementingIsACircleComparedByItsArea() throws Throwable {
        CompiledClasses circle = rendersAsShown("circle", ReadmeTypedExamples.circle());

        Object one = circle.generated().getConstructor().newInstance();
        Object other = circle.generated().getConstructor().newInstance();
        assertThat(circle.invoke("area")).isEqualTo(Math.PI * 1.5 * 1.5);
        assertThat(one).isInstanceOf(Comparable.class);
        @SuppressWarnings("unchecked")
        Comparable<Object> comparable = (Comparable<Object>) one;
        assertThat(comparable.compareTo(other)).isZero();
    }

    @Test
    void aGenericClassFindsTheLargest() throws Throwable {
        CompiledClasses collections = rendersAsShown("collections", ReadmeTypedExamples.collections());

        assertThat(collections.invoke("max", List.of(3, 9, 4))).isEqualTo(9);
        assertThat(collections.invoke("max", List.of("pear", "apple"))).isEqualTo("pear");
    }

    @Test
    void theMethodBodiesDoWhatTheyRead() throws Throwable {
        CompiledClasses signals = rendersAsShown("signals", ReadmeTypedExamples.signals());

        assertThat(signals.invoke("advice", Signal.RED)).isEqualTo("stop");
        assertThat(signals.invoke("advice", Signal.AMBER)).isEqualTo("wait");
        assertThat(signals.invoke("advice", Signal.GREEN)).isEqualTo("go");
        assertThat(signals.invoke("parse", "42")).isEqualTo(42);
        assertThat(signals.invoke("parse", "forty-two")).isEqualTo(-1);
        assertThat(signals.invoke("describe", 5)).isEqualTo("positive int");
        assertThat(signals.invoke("describe", -5)).isEqualTo("something else");
        assertThat(signals.invoke("describe", "abc")).isEqualTo("string of 3");
        assertThat(signals.invoke("describe", 1.5)).isEqualTo("something else");
        @SuppressWarnings("unchecked")
        Predicate<String> startingWithA = (Predicate<String>) signals.invoke("startingWith", "a");
        assertThat(startingWithA.test("abc")).isTrue();
        assertThat(startingWithA.test("bca")).isFalse();
    }

    @Test
    void theArraysAndTheArgumentsOfVariableArityAreWhatTheyRead() throws Throwable {
        CompiledClasses arrays = rendersAsShown("arrays", ReadmeTypedExamples.arrays());

        assertThat(arrays.invoke("csv")).isEqualTo("a, b");
        // one("a") is count(String...); two is count(String...) too, and not the count(String, String) beside it
        assertThat(arrays.invoke("one")).isEqualTo(1);
        assertThat(arrays.invoke("two")).isEqualTo(2);
        assertThat(arrays.invoke("second")).isEqualTo("b");
        Method pair = arrays.generated().getMethod("count", String.class, String.class);
        assertThat(pair.invoke(null, "a", "b")).isEqualTo(0);
    }

    @Test
    void aDivisionByAConstantZeroIsRefusedThoughJavacOnlyWarnsOfIt() {
        String refusal = refusalShownAfter("division");

        assertThatIllegalArgumentException()
                .isThrownBy(ReadmeTypedExamples::division)
                .satisfies(e -> assertThat(e.getClass().getName() + ": " + e.getMessage() + "\n")
                        .isEqualTo(refusal));
        assertThat(javac().withOptions("-Xlint:all")
                        .compile(JavaFileObjects.forSourceString(
                                "com.example.Probe",
                                "package com.example;\nclass Probe { int ratio = 10 / (1 - 1); }")))
                .hadWarningContaining("division by zero");
    }

    @Test
    void twoOverloadsOfFunctionalInterfacesAreRefusedThoughJavacOnlyWarnsOfThem() {
        String refusal = refusalShownAfter("overloads");

        assertThatIllegalArgumentException()
                .isThrownBy(ReadmeTypedExamples::overloads)
                .satisfies(e -> assertThat(e.getClass().getName() + ": " + e.getMessage() + "\n")
                        .isEqualTo(refusal));
        assertThat(javac().withOptions("-Xlint:all")
                        .compile(JavaFileObjects.forSourceString("com.example.Handlers", """
                                package com.example;

                                import java.util.function.Consumer;
                                import java.util.function.Function;

                                class Handlers {
                                    void handle(Function<String, String> function) {}

                                    void handle(Consumer<String> consumer) {}
                                }
                                """)))
                .hadWarningContaining("potentially ambiguous");
    }

    private static String refusalShownAfter(String name) {
        Block refusal = shownAfter(name);
        assertThat(refusal.language()).isEqualTo("text");
        return refusal.content();
    }

    @Test
    void theFactsOfTheExamplesAreRequestedAsTheReadmeShows() {
        String info = read(SOURCES.resolve("package-info.java"));
        int end = info.indexOf(';', info.indexOf("package me.supcheg")) + 1;
        String requested = info.substring(info.indexOf("@Facts("), end);

        assertThat(BLOCKS).contains(new Block("java", requested + "\n"));
    }

    @Test
    void everyLinkOfTheReadmeLeadsToAHeading() {
        List<String> anchors = new ArrayList<>();
        Matcher heading = Pattern.compile("(?m)^#{1,6} (.+)$").matcher(README.replaceAll("(?ms)^```.*?^```$", ""));
        while (heading.find()) {
            anchors.add(heading.group(1)
                    .toLowerCase(Locale.ROOT)
                    .replaceAll("[^a-z0-9 _-]", "")
                    .replace(' ', '-'));
        }
        Matcher link = Pattern.compile("\\]\\(#([^)]+)\\)").matcher(README);
        int links = 0;
        while (link.find()) {
            links++;
            assertThat(anchors).as("the heading of the link " + link.group(1)).contains(link.group(1));
        }
        assertThat(links).isPositive();
    }
}
