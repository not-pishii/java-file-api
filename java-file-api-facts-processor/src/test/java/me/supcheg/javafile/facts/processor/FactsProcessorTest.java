package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/// The rounds of [FactsProcessor] in the compilations the fixtures do not have: one without `@Facts` to read, and
/// types of a kind that no member of a metamodel is told of.
class FactsProcessorTest {
    private static final String REQUEST = """
            package gen;
            @me.supcheg.javafile.facts.meta.Facts(p.Sealed.class)
            class G {}
            """;

    /// Reads the annotations of the metamodels the processor claims, where the compilation has no `@Facts` to ask
    /// for any: a class with the marker of a metamodel is no request.
    @Test
    void aCompilationWhoseClasspathHasNoFactsHasNothingToRead() {
        Compiled compiled = Javac.facts()
                .alone()
                .compile(
                        "package me.supcheg.javafile.facts.meta; public @interface GeneratedMetamodel {}",
                        "@me.supcheg.javafile.facts.meta.GeneratedMetamodel class Marked {}")
                .orFail();

        assertThat(compiled.generated()).isEmpty();
        assertThat(compiled.diagnostics()).isEmpty();
    }

    @Test
    void theProtectedMembersOfATypeThatCannotBeExtendedAreToldToHaveNoFacts() {
        Compiled compiled = Javac.facts()
                .compile(REQUEST, "package p; public final class Sealed { protected void hook() {} }")
                .orFail();

        assertThat(compiled.sources().get("gen.facts.p.Sealed_")).contains("only a subclass reaches them");
    }

    /// A member type is no member of the type a metamodel has a fact of, `protected` or not.
    @Test
    void aProtectedMemberTypeIsNoProtectedMemberWithoutAFact() {
        Compiled compiled = Javac.facts()
                .compile(REQUEST, "package p; public final class Sealed { protected static class Inner {} }")
                .orFail();

        assertThat(compiled.sources().get("gen.facts.p.Sealed_")).doesNotContain("only a subclass reaches them");
    }
}
