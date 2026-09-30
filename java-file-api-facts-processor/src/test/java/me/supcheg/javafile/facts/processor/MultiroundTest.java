package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `multiround` (mini-spec §8, §9.2): types another processor
/// generates, requested in `@Facts` or mentioned by a requested type, get
/// their metamodels in a later round; a type that never appears is an
/// error in the last round.
class MultiroundTest {
    /// Generates `gen.Missing` and `gen.Other` in its first round.
    private static final class Generating extends AbstractProcessor {
        private boolean done;

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            if (!done) {
                done = true;
                write("gen.Missing", "package gen; public class Missing { public Other other() { return null; } }");
                write("gen.Other", "package gen; public final class Other {}");
            }
            return false;
        }

        private void write(String name, String source) {
            try (Writer writer = processingEnv.getFiler().createSourceFile(name).openWriter()) {
                writer.write(source);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private static Compilation process(String... sources) {
        return ProcessorHarness.process(List.of(), List.of(), List.of(new Generating()), sources);
    }

    @Test
    void aRequestedTypeGeneratedByAnotherProcessorIsReadInTheNextRound() {
        Compilation compilation = ProcessorHarness.succeeded(
                process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({gen.Missing.class, p.Uses.class})
                class G {}
                """, "package p; public class Uses { public gen.Missing missing() { return null; } }"));
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.keySet())
                .containsExactlyInAnyOrder(
                        "gen.Missing",
                        "gen.Other",
                        "gen.facts.gen.Missing_",
                        "gen.facts.p.Uses_",
                        "gen.facts.gen.Other_");
        assertThat(sources.get("gen.facts.gen.Missing_")).contains("OpenClassToken<Missing> TOKEN");
    }

    @Test
    void aTypeMentionedButNotGeneratedYetDefersTheRequestedType() {
        Compilation compilation = ProcessorHarness.succeeded(
                process("""
                @me.supcheg.javafile.facts.meta.Facts(p.Uses.class)
                package gen;
                """, "package p; public class Uses { public gen.Missing missing() { return null; } }"));
        assertThat(ProcessorHarness.generatedSources(compilation).keySet())
                .containsExactlyInAnyOrder("gen.Missing", "gen.Other", "gen.facts.p.Uses_", "gen.facts.gen.Missing_");
    }

    @Test
    void aRequestedTypeThatNeverAppearsIsAnErrorInTheLastRound() {
        Compilation compilation = process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({gen.Never.class, gen.Missing.class})
                class G {}
                """);
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .contains("a type in @Facts is not resolvable after all rounds");
    }

    @Test
    void aTypeMentioningATypeThatNeverAppearsIsAnErrorInTheLastRound() {
        Compilation compilation =
                process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Broken.class)
                class G {}
                """, "package p; public class Broken { public gen.Never never() { return null; } }");
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .contains("type p.Broken in @Facts is not resolvable after all rounds: it mentions gen.Never, which"
                        + " no processor generated");
    }

    @Test
    void aFullMetamodelWaitsForTheMetamodelOfARequestedTypeItMentions() {
        Compilation compilation = ProcessorHarness.succeeded(process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public gen.Missing missing() { return null; } }"));

        // B has to wait for gen.Missing, and A for B: else A would have no fact of b()
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.get("gen.facts.p.A_")).contains("B_.Data.SHAPE").contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("MethodRef0<B, Missing> missing");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void aFullMetamodelWhoseMentionedRequestedTypeNeverGetsReadyIsAnErrorInTheLastRound() {
        Compilation compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public gen.Never never() { return null; } }");

        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .contains(
                        "type p.B in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated",
                        "type p.A in @Facts is not resolvable after all rounds: it mentions p.B, whose metamodel is"
                                + " not ready");
    }

    @Test
    void anInterfaceWaitsForATypeItsInheritedSamMentions() {
        Compilation compilation = ProcessorHarness.succeeded(process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """,
                "package p; public interface Base { gen.Missing run(); }",
                "package p; public interface Sub extends Base {}"));

        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.get("gen.facts.p.Sub_"))
                .contains("Sam0<Sub, Missing> sam")
                .contains("Missing_.Data.SHAPE");
        assertThat(sources.keySet()).contains("gen.facts.gen.Missing_");
    }
}
