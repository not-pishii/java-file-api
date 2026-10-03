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
/// error in the last round, which tells what each type waited for.
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

    /// Generates `gen.Late`, which extends `p.Base`, in its first round.
    private static final class GeneratingASubtype extends AbstractProcessor {
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
                try (Writer writer =
                        processingEnv.getFiler().createSourceFile("gen.Late").openWriter()) {
                    writer.write("package gen; public class Late extends p.Base { public void late() {} }");
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            return false;
        }
    }

    /// The errors of the last round about the requested types that never became ready, in order.
    private static List<String> unresolvable(Compilation compilation) {
        return ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR).stream()
                .filter(message -> message.startsWith("type "))
                .toList();
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
                .containsExactlyInAnyOrderElementsOf(ProcessorHarness.withObject(
                        "gen.facts",
                        "gen.Missing",
                        "gen.Other",
                        "gen.facts.gen.Missing_",
                        "gen.facts.p.Uses_",
                        "gen.facts.gen.Other_"));
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
                .containsExactlyInAnyOrderElementsOf(ProcessorHarness.withObject(
                        "gen.facts", "gen.Missing", "gen.Other", "gen.facts.p.Uses_", "gen.facts.gen.Missing_"));
    }

    @Test
    void aSupertypeGeneratedByAnotherProcessorGetsItsFullMetamodelInTheNextRound() {
        Compilation compilation = ProcessorHarness.succeeded(
                process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """, "package p; public class Sub extends gen.Missing { public void sub() {} }"));
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);

        // Sub waits for its superclass; then Missing, which is not asked for, is read as a supertype, and
        // Other, which Missing mentions, gets a token
        assertThat(sources.keySet())
                .containsExactlyInAnyOrderElementsOf(ProcessorHarness.withObject(
                        "gen.facts",
                        "gen.Missing",
                        "gen.Other",
                        "gen.facts.p.Sub_",
                        "gen.facts.gen.Missing_",
                        "gen.facts.gen.Other_"));
        assertThat(sources.get("gen.facts.gen.Missing_"))
                .contains("complete = true")
                .contains("MethodRef0<Missing, Other> other");
        assertThat(sources.get("gen.facts.gen.Other_")).contains("complete = false");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void aSupertypeThatMentionsATypeThatNeverAppearsIsToldWithTheRequestItIsThereFor() {
        Compilation compilation = process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """, "package p; public class Sub extends Sup { public void sub() {} }", """
                package p;
                public class Sup { public gen.Never never() { return null; } }
                """);

        // Sub does not wait for the metamodel of its supertype: only Sup is stuck
        assertThat(unresolvable(compilation))
                .containsExactly("type p.Sup (a supertype of p.Sub) in @Facts is not resolvable after all rounds: it"
                        + " mentions gen.Never, which no processor generated");
    }

    @Test
    void aSupertypeWhoseTokenOnlyMetamodelWasWrittenBeforeItsSubtypeAppearedHasNoFullOne() {
        Compilation compilation = ProcessorHarness.succeeded(ProcessorHarness.process(
                List.of(),
                List.of(),
                List.of(new GeneratingASubtype()),
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class})
                class G {}
                """,
                "package p; public class Holder { public Base base() { return null; } }",
                "package p; public class Base { public void inherited() {} }"));
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);

        // round 1 knows Base only as a type Holder mentions; round 2 finds it to be a supertype too
        assertThat(sources.get("gen.facts.p.Base_")).contains("complete = false");
        assertThat(sources.get("gen.facts.gen.Late_")).contains("complete = true");
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.WARNING))
                .containsExactly("gen.Late: no facts of the public members inherited from p.Base, which has no full"
                        + " metamodel: its token-only metamodel gen.facts.p.Base_ was generated in an earlier round,"
                        + " before a type that extends or implements p.Base was asked for");
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
    void aFullMetamodelWaitsForARequestedTypeATypeArgumentOrABoundMentions() {
        Compilation compilation = ProcessorHarness.succeeded(process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.C.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public void all(java.util.List<? extends B> all) {} }",
                "package p; public class C { public <T extends B> void bound(T t) {} }",
                "package p; public class B { public gen.Missing missing() { return null; } }"));

        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.get("gen.facts.p.A_"))
                .contains("VoidMethodRef1<A, List<? extends B>> all_List")
                .contains("TokenArg.extendsBound(UnsafeFacts.<B>openClassToken(B_.Data.SHAPE))");
        assertThat(sources.get("gen.facts.p.C_"))
                .contains("public static <T extends B> VoidMethodRef1<C, T> bound_T(RefToken<T> t) {");
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

        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.B,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void theErrorOfTheLastRoundTellsTheChainOfMetamodelsATypeWaitsFor() {
        Compilation compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.C.class, p.A.class, p.B.class, p.Fine.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public C c() { return null; } }",
                "package p; public class C { public gen.Never never() { return null; } }",
                "package p; public class Fine { public int size() { return 0; } }");

        // A waits for B though B is read: its metamodel is not written before that of C is
        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.B,"
                                + " which waits for the metamodel of p.C, which mentions gen.Never, which no"
                                + " processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.C in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void typesThatMentionEachOtherWaitForTheMissingTypeNotForEachOther() {
        Compilation compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class, p.C.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } public C c() { return null; } }",
                "package p; public class B { public A a() { return null; } public C c() { return null; } }",
                "package p; public class C { public A a() { return null; } public gen.Never never() { return null; } }");

        // the three mention each other in a circle; what is reported is the missing type, by the shortest way
        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.C in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void requestedTypesThatMentionEachOtherAreGeneratedInOneRound() {
        Compilation compilation = ProcessorHarness.succeeded(process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public A a() { return null; } }"));

        // a metamodel refers to another through its Data.SHAPE only: neither has to be there first
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.get("gen.facts.p.A_")).contains("B_.Data.SHAPE").contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("A_.Data.SHAPE").contains("MethodRef0<B, A> a");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void typesThatMentionEachOtherAndATypeGeneratedLaterAreGeneratedOnceItIsThere() {
        Compilation compilation = ProcessorHarness.succeeded(process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class, p.C.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public A a() { return null; } public C c() { return null; } }",
                "package p; public class C { public gen.Missing missing() { return null; } }"));

        // C waits for gen.Missing, B for C and A for B, though A and B mention each other
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        assertThat(sources.get("gen.facts.p.A_")).contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("MethodRef0<B, A> a").contains("MethodRef0<B, C> c");
        assertThat(sources.get("gen.facts.p.C_")).contains("MethodRef0<C, Missing> missing");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void theGraphOfARoundHasAnEdgeToWhatEachTypeWaitsFor() {
        GraphProbe probe = new GraphProbe("p.E", "p.D", "p.C", "p.B", "p.A");
        ProcessorHarness.succeeded(ProcessorHarness.process(
                List.of(),
                List.of(),
                List.of(new Generating(), probe),
                "package gen; class G {}",
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public gen.Missing missing() { return null; } }",
                "package p; public class C { public D d() { return null; } }",
                "package p; public class D { public C c() { return null; } }",
                "package p; public class E { public A a() { return null; } public <T> E(T t, B b) {} }"));
        TypeGraph graph = probe.graph();

        // in the first round gen.Missing is not generated yet; C and D mention each other, and await nothing;
        // E mentions B in a constructor that gets no fact, and so does not await it
        assertThat(graph.edges().stream().filter(edge -> edge instanceof TypeGraph.Edge.Awaits))
                .containsExactly(
                        new TypeGraph.Edge.Awaits("p.A", "p.B"),
                        new TypeGraph.Edge.Awaits("p.B", "gen.Missing"),
                        new TypeGraph.Edge.Awaits("p.E", "p.A"));
        assertThat(graph.nodes().get("gen.Missing")).isEqualTo(new TypeGraph.Node.Absent("gen.Missing"));
        assertThat(graph.nodes().get("p.B"))
                .isEqualTo(new TypeGraph.Node.Requested("p.B", new TypeGraph.Request.Waiting()));
        assertThat(graph.waitOf("p.E"))
                .contains(new TypeGraph.Wait.Missing(List.of("p.E", "p.A", "p.B", "gen.Missing")));
        assertThat(graph.waitOf("p.C")).isEmpty();
        assertThat(graph.waitOf("p.D")).isEmpty();
        assertThat(graph.from("p.E", TypeGraph.Edge.Signature.class).map(TypeGraph.Edge::to))
                .containsExactly("p.A", "p.B");
    }

    @Test
    void aFullMetamodelDoesNotWaitForATypeOnlyMembersWithoutAFactMention() {
        Compilation compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public <T> A(T t, B b) {} public void take(Hidden h, B b) {}"
                        + " public int size() { return 0; } }",
                "package p; class Hidden {}",
                "package p; public class B { public gen.Never never() { return null; } }");

        // A(T, B) and take(Hidden, B) get no fact whatever becomes of B: A has nothing to wait for
        // the compilation fails for B, so what was generated cannot be read: the warnings of A, given
        // as its metamodel is written, tell that it was
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .contains("type p.B in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                        + " processor generated")
                .noneMatch(message -> message.startsWith("type p.A in @Facts"));
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.WARNING))
                .contains(
                        "p.A: no fact of constructor <T>A(T,p.B), which is a generic constructor, whose type"
                                + " arguments a fact cannot give explicitly",
                        "p.A: no fact of method take(p.Hidden,p.B), which mentions types that are not public:"
                                + " p.Hidden");
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
