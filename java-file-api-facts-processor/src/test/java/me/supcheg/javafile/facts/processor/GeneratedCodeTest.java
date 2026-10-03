package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/// What the processor writes (mini-spec §2.3, §2.6) that no fixture holds: a library made by the test, as it is too
/// big to be a file. The metamodels of types of every kind are in the fixture `generated`.
class GeneratedCodeTest {
    @TempDir
    Path directory;

    /// A class with so many methods that the canonical form of its metamodel is longer than a constant of a class file
    /// can be.
    @Test
    void aCanonicalFormTooLongForOneConstantIsJoinedFromParts() throws Exception {
        String wide = IntStream.range(0, 700)
                .mapToObj(i -> " public void method" + i + "(int a, String b) {}")
                .collect(Collectors.joining("", "package p; public class Wide {", " }"));
        Path library = Javac.plain().compile(wide).orFail().writeTo(directory.resolve("lib"));
        Compiled compiled = Javac.facts().classpath(library).compile("""
                        package gen;

                        import me.supcheg.javafile.facts.meta.Facts;

                        @Facts(p.Wide.class)
                        class G {}
                        """).orFail();
        Path metamodels = Javac.plain()
                .linted()
                .classpath(library)
                .compile(compiled.generated())
                .clean()
                .writeTo(directory.resolve("metamodels"));

        try (URLClassLoader loader = new URLClassLoader(
                new URL[] {metamodels.toUri().toURL(), library.toUri().toURL()},
                GeneratedCodeTest.class.getClassLoader())) {
            TypeShape<?> shape = (TypeShape<?>)
                    loader.loadClass("gen.facts.p.Wide_$Data").getField("SHAPE").get(null);

            assertThat(shape.origin()).isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
                String text = origin.canonical().get();
                assertThat(text).hasSizeGreaterThan(65535);
                assertThat(sha256(text)).isEqualTo(origin.fingerprint());
            });
        }
        assertThat(compiled.sources().get("gen.facts.p.Wide_")).contains("String.join(\"\", ");
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
