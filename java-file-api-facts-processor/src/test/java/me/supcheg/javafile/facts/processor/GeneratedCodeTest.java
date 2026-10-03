package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/// What the processor writes (mini-spec §2.3, §2.6) of a library that is made by the test, as a file of it would
/// be too big: the metamodel of a class with hundreds of members. The metamodels of types of every kind are in the
/// fixture `generated`.
class GeneratedCodeTest {
    private static final String CYRILLIC = String.valueOf((char) 0x44f);

    @TempDir
    Path directory;

    /// Compiles a library of the class `p.Wide`, runs the processor on it, compiles the metamodel under every
    /// lint, loads it, and gives the test the shape of `p.Wide` and what the processor generated.
    private void onWide(String library, BiConsumer<Compiled, TypeShape<?>> check) throws Exception {
        Path lib = Javac.plain().compile(library).orFail().writeTo(directory.resolve("lib"));
        Compiled compiled = Javac.facts().classpath(lib).compile("""
                        package gen;

                        import me.supcheg.javafile.facts.meta.Facts;

                        @Facts(p.Wide.class)
                        class G {}
                        """).orFail();
        Path metamodels = Javac.plain()
                .linted()
                .classpath(lib)
                .compile(compiled.generated())
                .clean()
                .writeTo(directory.resolve("metamodels"));
        try (URLClassLoader loader =
                new URLClassLoader(new URL[] {url(metamodels), url(lib)}, GeneratedCodeTest.class.getClassLoader())) {
            check.accept(compiled, shape(loader));
        }
    }

    private static TypeShape<?> shape(ClassLoader loader) throws ReflectiveOperationException {
        return (TypeShape<?>)
                loader.loadClass("gen.facts.p.Wide_$Data").getField("SHAPE").get(null);
    }

    private static URL url(Path path) throws IOException {
        return path.toUri().toURL();
    }

    /// A class with so many methods that the canonical form of its metamodel is longer than a constant of a class file
    /// can be.
    @Test
    void aCanonicalFormTooLongForOneConstantIsJoinedFromParts() throws Exception {
        String wide = IntStream.range(0, 700)
                .mapToObj(i -> " public void method" + i + "(int a, String b) {}")
                .collect(Collectors.joining("", "package p; public class Wide {", " }"));

        onWide(wide, (compiled, shape) -> {
            assertThat(shape.origin()).isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
                String text = origin.canonical().get();
                assertThat(text).hasSizeGreaterThan(65535);
                assertThat(sha256(text)).isEqualTo(origin.fingerprint());
            });
            assertThat(compiled.sources().get("gen.facts.p.Wide_")).contains("String.join(\"\", ");
        });
    }

    /// The members are not public, so there are no facts, but all of them are in the one line of the method table.
    @Test
    void aMetamodelWhoseCanonicalFormHasALineTooLongForOneConstantCompiles() throws Exception {
        String name = "a_method_with_a_name_long_enough_to_fill_the_table_" + "x".repeat(200) + CYRILLIC;
        String wide = IntStream.range(0, 300)
                .mapToObj(i -> " void " + name + i + "(int a, String b) {}")
                .collect(Collectors.joining("", "package p; public class Wide {", " }"));

        onWide(
                wide,
                (compiled, shape) -> assertThat(shape.origin())
                        .isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
                            String text = origin.canonical().get();
                            assertThat(text.lines().mapToInt(GeneratedCodeTest::modifiedUtf8))
                                    .anyMatch(bytes -> bytes > 65535);
                            assertThat(text).contains("table concrete " + name + "0(int, java.lang.String); ");
                        }));
    }

    /// The bytes a class file holds a string constant in.
    private static int modifiedUtf8(String text) {
        return text.chars()
                .map(c -> c == 0 ? 2 : c < 0x80 ? 1 : c < 0x800 ? 2 : 3)
                .sum();
    }

    private static String sha256(String text) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
