package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `closure` (mini-spec §3, §9.2): a requested type gets its
/// metamodel, and every class or interface its declared public signatures
/// mention gets a token-only one — and nothing further.
class ClosureTest {
    @TempDir
    Path lib;

    @TempDir
    Path out;

    private List<Path> classpath;

    @BeforeEach
    void compileLibrary() {
        classpath = List.of(ProcessorHarness.library(
                lib,
                List.of(),
                """
                package p;
                public class A<T extends Bound> {
                    public A(Param1 p) throws CtorEx {}
                    public static final Field1 F = null;
                    public Result m(Arg a) throws Ex { return null; }
                    public <U extends MBound> Holder<Elem> g(java.util.List<? super Lower> l) { return null; }
                    public Arg[] arr() { return null; }
                    public B b() { return null; }
                    public Dol$lar dollar() { return null; }
                    public Marker marker() { return null; }
                    protected Prot prot() { return null; }
                    Pkg pkg() { return null; }
                    public Hidden hidden() { return null; }
                }
                """,
                "package p; public class B { public A<?> a() { return null; } public Only only() { return null; } }",
                "package p; public interface Bound {}",
                "package p; public class Param1 {}",
                "package p; public class CtorEx extends Exception {}",
                "package p; public class Field1 {}",
                "package p; public class Result { public Deep deep() { return null; } }",
                "package p; public class Deep {}",
                "package p; public class Arg {}",
                "package p; public class Ex extends Exception {}",
                "package p; public interface MBound {}",
                "package p; public class Holder<E> {}",
                "package p; public class Elem {}",
                "package p; public class Lower {}",
                "package p; public class Dol$lar {}",
                "package p; public @interface Marker {}",
                "package p; public class Prot {}",
                "package p; public class Pkg {}",
                "package p; class Hidden {}",
                "package p; public class Only {}"));
    }

    private Compilation generate(String facts) {
        return ProcessorHarness.succeeded(ProcessorHarness.process(classpath, """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(facts)));
    }

    @Test
    void theSignaturesOfTheRequestedTypeGetTokenOnlyMetamodelsToDepthOne() {
        Compilation compilation = generate("p.A.class");
        assertThat(ProcessorHarness.generatedSources(compilation).keySet())
                .containsExactlyInAnyOrder(
                        "gen.facts.p.A_",
                        "gen.facts.p.Bound_",
                        "gen.facts.p.Param1_",
                        "gen.facts.p.CtorEx_",
                        "gen.facts.p.Field1_",
                        "gen.facts.p.Result_",
                        "gen.facts.p.Arg_",
                        "gen.facts.p.Ex_",
                        "gen.facts.p.MBound_",
                        "gen.facts.p.Holder_",
                        "gen.facts.p.Elem_",
                        "gen.facts.java.util.List_",
                        "gen.facts.p.Lower_",
                        "gen.facts.p.B_");
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.WARNING))
                .containsExactly(
                        "p.A: no fact of method dollar(), which mentions p.Dol$lar, which has no metamodel: a class"
                                + " with $ in its simple name is not supported yet",
                        "p.A: no fact of method marker(), which mentions p.Marker, which has no metamodel:"
                                + " annotation interface p.Marker is not supported yet",
                        "p.A: no fact of method hidden(), which mentions types that are not public: p.Hidden");
        assertThat(ProcessorHarness.resources(compilation))
                .hasSize(14)
                .containsEntry("META-INF/javafile/metamodel/full/p.A", "gen.facts.p.A_\n")
                .containsEntry("META-INF/javafile/metamodel/token/java.util.List", "gen.facts.java.util.List_\n");
        ProcessorHarness.compileAndLoad(compilation, out, classpath);
    }

    @Test
    void aClassWithADollarInItsNameCannotBeRequestedYet() {
        Compilation compilation = ProcessorHarness.process(classpath, """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Dol$lar.class)
                class G {}
                """);
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .containsExactly("p.Dol$lar: a class with $ in its simple name is not supported yet");
    }

    @Test
    void aTypeBothRequestedAndMentionedIsGeneratedOnceAsRequested() {
        Compilation compilation = generate("p.A.class, p.B.class, p.A.class");
        assertThat(ProcessorHarness.generatedSources(compilation).keySet())
                .contains("gen.facts.p.A_", "gen.facts.p.B_", "gen.facts.p.Only_")
                .doesNotContain("gen.facts.p.Deep_", "gen.facts.p.Prot_", "gen.facts.p.Pkg_");
        assertThat(ProcessorHarness.resources(compilation)).hasSize(15);
    }

    @Test
    void theMetamodelsOfTwoTypesMayNotShareAName() {
        List<Path> clashing = List.of(ProcessorHarness.library(
                lib.resolve("clash"),
                List.of(),
                "package q; public class Map { public static class Entry {} }",
                "package q; public class Map_Entry {}"));
        Compilation compilation = ProcessorHarness.process(clashing, """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({q.Map.Entry.class, q.Map_Entry.class})
                class G {}
                """);
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .containsExactly("the metamodels of q.Map$Entry and q.Map_Entry would both be gen.facts.q.Map_Entry_");
    }
}
