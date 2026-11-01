package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/// The options that place and hide the metamodels: `-Ajavafile.facts.visibility=package` makes them
/// package-private, and `-Ajavafile.facts.mirror=false` writes them into the base package itself, where the
/// code of that package reaches them.
class OptionsTest {
    private static final String LIBRARY = """
            package p;

            public class Svc {
                public String name() {
                    return null;
                }
            }
            """;

    private static final String REQUEST = """
            package gen;

            @me.supcheg.javafile.facts.meta.Facts(p.Svc.class)
            class G {}
            """;

    @TempDir
    Path directory;

    /// The metamodels of `com.acme.meta` are package-private, and the code of that package reaches them while
    /// the code of another package cannot name `Svc_`.
    @Test
    void packageVisibilityInTheBasePackageIsReachedOnlyByItsPackage() {
        Path library = Javac.plain().alone().compile(LIBRARY).orFail().writeTo(directory.resolve("library"));
        Compiled generated = Javac.facts()
                .classpath(library)
                .options(
                        "-Ajavafile.facts.package=com.acme.meta",
                        "-Ajavafile.facts.visibility=package",
                        "-Ajavafile.facts.mirror=false")
                .compile(REQUEST)
                .orFail();

        assertThat(generated.sources())
                .containsKeys("com.acme.meta.Svc_", "com.acme.meta.Svc_Heritage", "com.acme.meta.Object_");
        assertThat(generated.sources().get("com.acme.meta.Svc_")).contains("final class Svc_ {");
        assertThat(generated.sources().get("com.acme.meta.Svc_Heritage"))
                .contains("final class Svc_Heritage implements");
        Path metamodels = generated.writeTo(directory.resolve("metamodels"));

        Compiled inside = Javac.plain()
                .classpath(library, metamodels)
                .compile("package com.acme.meta; class Uses { Object token = Svc_.TOKEN; }");
        assertThat(inside.errors()).isEmpty();

        Compiled outside = Javac.plain()
                .classpath(library, metamodels)
                .compile("package com.acme.elsewhere; class Uses { Object token = com.acme.meta.Svc_.TOKEN; }");
        assertThat(outside.errors())
                .anySatisfy(message -> assertThat(message)
                        .contains("Svc_ is not public in com.acme.meta")
                        .contains("cannot be accessed from outside package"));
    }

    /// Both new options refuse a value that says nothing of their two states, and the errors name them.
    @Test
    void anUnknownVisibilityOrMirrorIsReported() {
        Compiled compiled = Javac.facts()
                .options("-Ajavafile.facts.visibility=private", "-Ajavafile.facts.mirror=no")
                .compile(REQUEST, LIBRARY);

        assertThat(compiled.errors())
                .containsExactly(
                        "-Ajavafile.facts.visibility=private is neither public nor package",
                        "-Ajavafile.facts.mirror=no is neither true nor false");
    }
}
