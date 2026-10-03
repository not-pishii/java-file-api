package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Full;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `closure` (mini-spec §3, §9.2): a requested type gets its
/// metamodel, and every class or interface its declared public signatures
/// mention gets a token-only one — and nothing further; and the graph of
/// the types of a round ([Closure], [TypeGraph]) that says so.
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
                "package p; public class Only {}",
                "package p; public interface Top { void top(); default void dflt() {} }",
                "package p; public interface Mid extends Top { void mid(); }",
                "package p; public abstract class Root implements Mid { public void top() {}"
                        + " public void root() {} public static void stat() {} }",
                "package p; public class Leaf extends Root { public void mid() {} public void root() {} }"));
    }

    /// The graph of a round in which `requested` are asked for, in that order.
    private TypeGraph graph(String... requested) {
        GraphProbe probe = new GraphProbe(requested);
        ProcessorHarness.succeeded(
                ProcessorHarness.process(classpath, List.of(), List.of(probe), "package gen; class G {}"));
        return probe.graph();
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
    void aSignatureEdgeLeadsToEveryTypeTheDeclaredPublicSignaturesMention() {
        TypeGraph graph = graph("p.A");

        // parameters, results, fields, throws, type arguments, wildcard bounds, array components and
        // the bounds of the type parameters of the type and of a method; not what a member that is not
        // public or that mentions a type that is not public mentions, and nothing a mentioned type mentions
        assertThat(graph.from("p.A", Edge.Signature.class).map(Edge::to))
                .containsExactly(
                        "java.util.List",
                        "p.Arg",
                        "p.B",
                        "p.Bound",
                        "p.CtorEx",
                        "p.Dol$lar",
                        "p.Elem",
                        "p.Ex",
                        "p.Field1",
                        "p.Holder",
                        "p.Lower",
                        "p.MBound",
                        "p.Marker",
                        "p.Param1",
                        "p.Result");
        assertThat(graph.edges()).noneMatch(edge -> edge instanceof Edge.Awaits);
        assertThat(graph.nodes().get("p.A")).isInstanceOf(Node.Requested.class);
        assertThat(graph.nodes().get("p.B"))
                .isInstanceOfSatisfying(
                        Node.Mentioned.class,
                        mentioned -> assertThat(mentioned.token()).isInstanceOf(Token.Planned.class));
        assertThat(graph.nodes().get("p.Marker"))
                .isEqualTo(new Node.Mentioned(
                        "p.Marker", new Token.Unavailable("annotation interface p.Marker is not supported yet")));
        assertThat(graph.nodes().get("p.Dol$lar"))
                .isEqualTo(new Node.Mentioned(
                        "p.Dol$lar", new Token.Unavailable("a class with $ in its simple name is not supported yet")));
        assertThat(graph.nodes()).doesNotContainKeys("p.Hidden", "p.Prot", "p.Pkg", "p.Deep", "p.Only");
    }

    @Test
    void requestedTypesThatMentionEachOtherDoNotAwaitEachOther() {
        TypeGraph graph = graph("p.A", "p.B");

        assertThat(graph.edges())
                .contains(new Edge.Signature("p.A", "p.B"), new Edge.Signature("p.B", "p.A"))
                .noneMatch(edge -> edge instanceof Edge.Awaits);
        assertThat(graph.nodes().get("p.B"))
                .isInstanceOfSatisfying(
                        Node.Requested.class,
                        requested -> assertThat(requested.request()).isInstanceOf(Request.Ready.class));
        assertThat(graph.waitOf("p.A")).isEmpty();
        assertThat(graph.waitOf("p.B")).isEmpty();
    }

    @Test
    void aSupertypeEdgeLeadsToEverySupertypeAndTellsWhetherMembersAreInheritedFromIt() {
        TypeGraph graph = graph("p.Leaf", "p.CtorEx");

        // Root declares top(), which hides that of Top, and stat(); Leaf declares mid() and root() again;
        // Top still has dflt()
        assertThat(graph.from("p.Leaf", Edge.Supertype.class))
                .containsExactly(
                        new Edge.Supertype("p.Leaf", "java.lang.Object", true),
                        new Edge.Supertype("p.Leaf", "p.Mid", false),
                        new Edge.Supertype("p.Leaf", "p.Root", true),
                        new Edge.Supertype("p.Leaf", "p.Top", true));
        // Exception declares constructors only
        assertThat(graph.from("p.CtorEx", Edge.Supertype.class))
                .containsExactly(
                        new Edge.Supertype("p.CtorEx", "java.io.Serializable", false),
                        new Edge.Supertype("p.CtorEx", "java.lang.Exception", false),
                        new Edge.Supertype("p.CtorEx", "java.lang.Object", true),
                        new Edge.Supertype("p.CtorEx", "java.lang.Throwable", true));
        assertThat(graph.inherited("p.Leaf"))
                .containsExactly(
                        new Node.Inherited("java.lang.Object", new Full.Askable()),
                        new Node.Inherited("p.Root", new Full.Askable()),
                        new Node.Inherited("p.Top", new Full.Askable()));
        // a supertype gets no metamodel of its own
        assertThat(graph.nodes().get("p.Root")).isEqualTo(new Node.Inherited("p.Root", new Full.Askable()));
    }

    @Test
    void aSupertypeThatIsRequestedOrMentionedIsThatNode() {
        TypeGraph graph = graph("p.Leaf", "p.Root", "p.CtorEx", "p.A");

        assertThat(graph.inherited("p.Leaf").map(Node::full))
                .containsExactly(new Full.Askable(), new Full.Asked(), new Full.Askable());
        // Root, requested itself, has the supertypes of its own
        assertThat(graph.from("p.Root", Edge.Supertype.class).map(Edge::to))
                .containsExactly("java.lang.Object", "p.Mid", "p.Top");
        assertThat(graph.from("p.A", Edge.Supertype.class).map(Edge::to)).containsExactly("java.lang.Object");
    }

    @Test
    void theGraphDoesNotDependOnTheOrderTheTypesAreAskedForIn() {
        TypeGraph forward = graph("p.A", "p.B", "p.CtorEx", "p.Leaf", "p.Root");
        TypeGraph backward = graph("p.Root", "p.Leaf", "p.CtorEx", "p.B", "p.A");

        assertThat(List.copyOf(backward.edges())).isEqualTo(List.copyOf(forward.edges()));
        assertThat(List.copyOf(backward.nodes().keySet()))
                .isEqualTo(List.copyOf(forward.nodes().keySet()));
        assertThat(backward.nodes().values().stream().map(Node::full).toList())
                .isEqualTo(forward.nodes().values().stream().map(Node::full).toList());
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
