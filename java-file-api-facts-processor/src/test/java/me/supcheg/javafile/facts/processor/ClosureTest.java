package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Reason;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.facts.processor.harness.Javac;
import me.supcheg.javafile.facts.processor.harness.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The graph of the types of a round ([Closure], [TypeGraph]) on the library of the fixture `closure` (mini-spec §3,
/// §9.2, Q13): a requested type gets its metamodel, every type it extends or implements gets a full one too, and
/// every class or interface the public signatures of those mention gets a token-only one — and nothing further.
/// What the processor generates of these is in the cases of the fixture.
class ClosureTest {
    /// The library of the fixture `closure`: types that mention each other and extend each other.
    private static final Path LIBRARY = Path.of("src/test/fixtures/closure/lib");

    @TempDir
    Path lib;

    private Path classpath;

    @BeforeEach
    void compileLibrary() {
        classpath = Javac.plain().compile(Source.in(LIBRARY)).orFail().writeTo(lib);
    }

    /// The graph of a round in which `requested` are asked for, in that order.
    private TypeGraph graph(String... requested) {
        GraphProbe probe = new GraphProbe(requested);
        Javac.facts()
                .classpath(classpath)
                .with(probe)
                .compile("package gen; class G {}")
                .orFail();
        return probe.graph();
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
                        mentioned -> assertThat(mentioned.mentioned()).isInstanceOf(Token.Planned.class));
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
                        requested -> assertThat(requested.asked()).isInstanceOf(Request.Ready.class));
        assertThat(graph.waitOf("p.A")).isEmpty();
        assertThat(graph.waitOf("p.B")).isEmpty();
    }

    @Test
    void aSupertypeEdgeLeadsToWhatATypeExtendsAndImplementsAndEverySupertypeGetsAFullMetamodel() {
        TypeGraph graph = graph("p.Leaf", "p.CtorEx");

        assertThat(graph.from("p.Leaf", Edge.Supertype.class).map(Edge::to)).containsExactly("p.Root");
        assertThat(graph.from("p.Root", Edge.Supertype.class).map(Edge::to))
                .containsExactly("java.lang.Object", "p.Mid");
        assertThat(graph.from("p.Mid", Edge.Supertype.class).map(Edge::to)).containsExactly("p.Top");
        assertThat(GraphProbe.supertypes(graph, "p.Leaf"))
                .containsExactly("java.lang.Object", "p.Mid", "p.Root", "p.Top");
        assertThat(GraphProbe.supertypes(graph, "p.CtorEx"))
                .containsExactly(
                        "java.io.Serializable", "java.lang.Exception", "java.lang.Object", "java.lang.Throwable");
        // every supertype is read for a full metamodel, as if it were asked for
        assertThat(Stream.of("p.Root", "p.Mid", "p.Top", "java.lang.Object", "java.lang.Throwable")
                        .map(graph.nodes()::get))
                .allSatisfy(node -> assertThat(node)
                        .isInstanceOfSatisfying(
                                Node.Inherited.class,
                                inherited -> assertThat(inherited.plan()).isInstanceOf(Request.Ready.class)));
        assertThat(graph.reasons("p.Root")).containsExactly(new Reason.Supertype("p.Leaf"));
        assertThat(graph.reasons("java.lang.Object"))
                .contains(new Reason.Supertype("p.CtorEx"), new Reason.Supertype("p.Leaf"));
        assertThat(graph.roots("java.lang.Throwable")).containsExactly("p.CtorEx");
    }

    @Test
    void theSignaturesOfASupertypeGetTokensButNoFurtherMetamodels() {
        TypeGraph graph = graph("p.Sub");

        // Sub extends Sup, whose signatures mention Result: a token of it, and nothing of what Result
        // mentions (Deep) or extends
        assertThat(graph.nodes().get("p.Sup")).isInstanceOf(Node.Inherited.class);
        assertThat(graph.from("p.Sup", Edge.Signature.class).map(Edge::to)).containsExactly("p.Result");
        assertThat(graph.nodes().get("p.Result"))
                .isInstanceOfSatisfying(
                        Node.Mentioned.class,
                        mentioned -> assertThat(mentioned.mentioned()).isInstanceOf(Token.Planned.class));
        assertThat(graph.reasons("p.Result")).containsExactly(new Reason.Mentioned("p.Sup"));
        assertThat(graph.from("p.Result", Edge.class)).isEmpty();
        assertThat(graph.nodes()).doesNotContainKey("p.Deep");
        // what Sub mentions and also extends has a full metamodel, not a token-only one
        assertThat(graph.from("p.Sub", Edge.Signature.class).map(Edge::to)).containsExactly("p.Sup");
        assertThat(graph.reasons("p.Sup"))
                .containsExactly(new Reason.Supertype("p.Sub"), new Reason.Mentioned("p.Sub"));
    }

    @Test
    void aSupertypeThatIsNotPublicIsHiddenAndWhatItExtendsIsASupertypeToo() {
        TypeGraph graph = graph("p.OverHidden");

        assertThat(graph.nodes().get("p.HiddenBase")).isEqualTo(new Node.Hidden("p.HiddenBase"));
        assertThat(graph.from("p.OverHidden", Edge.Supertype.class).map(Edge::to))
                .containsExactly("p.HiddenBase");
        assertThat(graph.from("p.HiddenBase", Edge.Supertype.class).map(Edge::to))
                .containsExactly("p.Root");
        assertThat(GraphProbe.supertypes(graph, "p.OverHidden"))
                .containsExactly("java.lang.Object", "p.HiddenBase", "p.Mid", "p.Root", "p.Top");
        assertThat(graph.nodes().get("p.Root")).isInstanceOf(Node.Inherited.class);
        assertThat(graph.reasons("p.HiddenBase")).containsExactly(new Reason.Supertype("p.OverHidden"));
        // the members OverHidden adopts from HiddenBase are its own: their signatures are mentioned by it
        assertThat(graph.from("p.OverHidden", Edge.Signature.class).map(Edge::to))
                .containsExactly("p.Arg");
    }

    @Test
    void aSupertypeNoFullMetamodelCanBeMadeOfIsDeclinedAndKeepsItsToken() {
        TypeGraph graph = graph("p.OverBounded");

        // the bound of Bounded is not public: no full metamodel, but OverBounded mentions it, so a token
        assertThat(graph.nodes().get("p.Bounded")).isInstanceOfSatisfying(Node.Declined.class, declined -> {
            assertThat(declined.reason())
                    .isEqualTo("the bounds of the type parameters of p.Bounded mention types that are not"
                            + " public: p.Hidden");
            assertThat(declined.mentioned()).containsInstanceOf(Token.Planned.class);
        });
        assertThat(graph.nodes().get("p.OverBounded"))
                .isInstanceOfSatisfying(
                        Node.Requested.class,
                        requested -> assertThat(requested.asked()).isInstanceOf(Request.Ready.class));
        assertThat(GraphProbe.supertypes(graph, "p.OverBounded")).containsExactly("java.lang.Object", "p.Bounded");
    }

    @Test
    void aSupertypeThatIsRequestedIsThatNode() {
        TypeGraph graph = graph("p.Leaf", "p.Root", "p.CtorEx", "p.A");

        assertThat(graph.nodes().get("p.Root")).isInstanceOf(Node.Requested.class);
        assertThat(graph.reasons("p.Root")).containsExactly(new Reason.Asked(), new Reason.Supertype("p.Leaf"));
        assertThat(graph.roots("p.Mid")).containsExactly("p.Leaf", "p.Root");
        assertThat(graph.from("p.A", Edge.Supertype.class).map(Edge::to)).containsExactly("java.lang.Object");
    }

    @Test
    void typesThatMentionEachOtherThroughTheirSupertypesDoNotAwaitEachOther() {
        // Enum<E extends Enum<E>> mentions itself, Comparable<T> mentions nothing, and every enum mentions
        // its supertypes back
        TypeGraph graph = graph("java.util.concurrent.TimeUnit", "java.lang.StringBuilder");

        assertThat(graph.edges()).noneMatch(edge -> edge instanceof Edge.Awaits);
        assertThat(GraphProbe.supertypes(graph, "java.util.concurrent.TimeUnit"))
                .containsExactly(
                        "java.io.Serializable",
                        "java.lang.Comparable",
                        "java.lang.Enum",
                        "java.lang.Object",
                        "java.lang.constant.Constable");
        assertThat(graph.nodes().get("java.lang.Enum")).isInstanceOf(Node.Inherited.class);
        assertThat(graph.nodes().get("java.lang.AbstractStringBuilder"))
                .isEqualTo(new Node.Hidden("java.lang.AbstractStringBuilder"));
        assertThat(graph.nodes().get("java.lang.CharSequence")).isInstanceOf(Node.Inherited.class);
    }

    @Test
    void theGraphDoesNotDependOnTheOrderTheTypesAreAskedForIn() {
        TypeGraph forward = graph("p.A", "p.B", "p.CtorEx", "p.Leaf", "p.Root", "p.OverHidden", "p.OverBounded");
        TypeGraph backward = graph("p.OverBounded", "p.OverHidden", "p.Root", "p.Leaf", "p.CtorEx", "p.B", "p.A");

        assertThat(List.copyOf(backward.edges())).isEqualTo(List.copyOf(forward.edges()));
        assertThat(List.copyOf(backward.nodes().keySet()))
                .isEqualTo(List.copyOf(forward.nodes().keySet()));
        assertThat(backward.nodes().keySet().stream()
                        .map(name -> backward.reasons(name).toList())
                        .toList())
                .isEqualTo(forward.nodes().keySet().stream()
                        .map(name -> forward.reasons(name).toList())
                        .toList());
        assertThat(backward.nodes().values().stream().map(Object::getClass).toList())
                .isEqualTo(
                        forward.nodes().values().stream().map(Object::getClass).toList());
    }
}
