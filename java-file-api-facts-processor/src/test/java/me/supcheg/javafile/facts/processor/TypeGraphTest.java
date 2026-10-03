package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Full;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.facts.processor.TypeGraph.Wait;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The graph of the types of a round as a value: its edges by kind, what a
/// type waits for along them, and that nothing depends on the order the
/// graph is put together in.
class TypeGraphTest {
    private static final ClassDesc METAMODEL = ClassDesc.of("gen.facts.p.T_");

    private static Node waiting(String name) {
        return new Node.Requested(name, new Request.Waiting());
    }

    private static Node absent(String name) {
        return new Node.Absent(name);
    }

    private static Edge awaits(String from, String to) {
        return new Edge.Awaits(from, to);
    }

    private static TypeGraph graph(List<Node> nodes, List<Edge> edges) {
        return TypeGraph.of(nodes.stream(), edges.stream());
    }

    // ------------------------------------------------------------------
    // the three kinds of edges
    // ------------------------------------------------------------------

    @Test
    void theEdgesOfATypeAreReadByKind() {
        TypeGraph graph = graph(
                List.of(
                        waiting("p.A"),
                        waiting("p.B"),
                        new Node.Mentioned("p.M", new Token.Unavailable("no reason")),
                        new Node.Inherited("p.S", new Full.Askable()),
                        new Node.Inherited("p.T", new Full.Askable()),
                        absent("gen.Never")),
                List.of(
                        new Edge.Signature("p.A", "p.M"),
                        new Edge.Signature("p.A", "p.B"),
                        new Edge.Signature("p.B", "p.M"),
                        new Edge.Supertype("p.A", "p.T", false),
                        new Edge.Supertype("p.A", "p.S", true),
                        awaits("p.A", "p.B"),
                        awaits("p.B", "gen.Never")));

        assertThat(graph.from("p.A", Edge.Signature.class))
                .containsExactly(new Edge.Signature("p.A", "p.B"), new Edge.Signature("p.A", "p.M"));
        assertThat(graph.from("p.A", Edge.Supertype.class))
                .containsExactly(new Edge.Supertype("p.A", "p.S", true), new Edge.Supertype("p.A", "p.T", false));
        assertThat(graph.from("p.A", Edge.Awaits.class)).containsExactly(new Edge.Awaits("p.A", "p.B"));
        assertThat(graph.from("p.M", Edge.class)).isEmpty();
        assertThat(graph.mentioners("p.M")).containsExactly("p.A", "p.B");
        assertThat(graph.inherited("p.A")).containsExactly(new Node.Inherited("p.S", new Full.Askable()));
        assertThat(graph.waits("p.A")).isTrue();
        assertThat(graph.waits("p.M")).isFalse();
    }

    @Test
    void theEdgesAreSortedBySourceThenKindThenTarget() {
        TypeGraph graph = graph(
                List.of(waiting("p.A"), waiting("p.B"), waiting("p.C")),
                List.of(
                        awaits("p.B", "p.A"),
                        awaits("p.A", "p.C"),
                        new Edge.Supertype("p.A", "p.C", false),
                        new Edge.Supertype("p.A", "p.B", true),
                        new Edge.Signature("p.A", "p.C"),
                        new Edge.Signature("p.A", "p.B")));

        assertThat(graph.edges())
                .containsExactly(
                        new Edge.Signature("p.A", "p.B"),
                        new Edge.Signature("p.A", "p.C"),
                        new Edge.Supertype("p.A", "p.B", true),
                        new Edge.Supertype("p.A", "p.C", false),
                        awaits("p.A", "p.C"),
                        awaits("p.B", "p.A"));
    }

    @Test
    void aGraphIsTheSameInWhateverOrderItIsPutTogether() {
        List<Node> nodes = List.of(waiting("p.A"), waiting("p.B"), waiting("p.C"), waiting("p.D"), absent("gen.X"));
        List<Edge> edges = List.of(
                awaits("p.A", "p.B"),
                awaits("p.A", "p.C"),
                awaits("p.B", "p.D"),
                awaits("p.C", "p.D"),
                awaits("p.D", "gen.X"),
                new Edge.Signature("p.A", "p.D"),
                new Edge.Supertype("p.B", "p.C", true));
        TypeGraph expected = graph(nodes, edges);

        for (int seed = 0; seed < 20; seed++) {
            List<Node> shuffledNodes = new ArrayList<>(nodes);
            List<Edge> shuffledEdges = new ArrayList<>(edges);
            Collections.shuffle(shuffledNodes, new Random(seed));
            Collections.shuffle(shuffledEdges, new Random(seed));
            TypeGraph shuffled = graph(shuffledNodes, shuffledEdges);

            assertThat(shuffled).isEqualTo(expected);
            assertThat(List.copyOf(shuffled.edges())).isEqualTo(List.copyOf(expected.edges()));
            assertThat(List.copyOf(shuffled.nodes().keySet())).containsExactly("gen.X", "p.A", "p.B", "p.C", "p.D");
            // of the two ways of the same length, the one through the first name
            assertThat(shuffled.waitOf("p.A")).contains(new Wait.Missing(List.of("p.A", "p.B", "p.D", "gen.X")));
        }
    }

    @Test
    void anEdgeIsBetweenNodesAndANameIsOfOneNode() {
        assertThatThrownBy(() -> graph(List.of(waiting("p.A")), List.of(awaits("p.A", "p.B"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("an edge is at p.B, which is not a node");
        assertThatThrownBy(() -> graph(List.of(waiting("p.A"), absent("p.A")), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("two nodes are named p.A");
    }

    // ------------------------------------------------------------------
    // what a type waits for
    // ------------------------------------------------------------------

    @Test
    void aTypeThatAwaitsNothingDoesNotWait() {
        TypeGraph graph = graph(List.of(waiting("p.A"), absent("gen.Never")), List.of(awaits("p.A", "gen.Never")));

        assertThat(graph.waitOf("gen.Never")).isEmpty();
        assertThat(graph.waitOf("p.A")).contains(new Wait.Missing(List.of("p.A", "gen.Never")));
    }

    @Test
    void aChainOfAwaitedTypesEndsInTheMissingOne() {
        TypeGraph graph = graph(
                List.of(waiting("p.A"), waiting("p.B"), waiting("p.C"), absent("gen.Never")),
                List.of(awaits("p.A", "p.B"), awaits("p.B", "p.C"), awaits("p.C", "gen.Never")));

        assertThat(graph.waitOf("p.A")).contains(new Wait.Missing(List.of("p.A", "p.B", "p.C", "gen.Never")));
        assertThat(graph.waitOf("p.B")).contains(new Wait.Missing(List.of("p.B", "p.C", "gen.Never")));
        assertThat(graph.waitOf("p.C")).contains(new Wait.Missing(List.of("p.C", "gen.Never")));
    }

    @Test
    void theShortestWayToAMissingTypeIsTaken() {
        TypeGraph graph = graph(
                List.of(waiting("p.A"), waiting("p.B"), waiting("p.C"), absent("gen.Far"), absent("gen.Near")),
                List.of(
                        awaits("p.A", "p.B"),
                        awaits("p.B", "p.C"),
                        awaits("p.C", "gen.Far"),
                        awaits("p.A", "p.C"),
                        awaits("p.B", "gen.Near")));

        assertThat(graph.waitOf("p.A")).contains(new Wait.Missing(List.of("p.A", "p.B", "gen.Near")));
    }

    @Test
    void typesThatAwaitEachOtherAndAMissingTypeWaitForTheMissingType() {
        TypeGraph graph = graph(
                List.of(waiting("p.A"), waiting("p.B"), absent("gen.Never")),
                List.of(awaits("p.A", "p.B"), awaits("p.B", "p.A"), awaits("p.B", "gen.Never")));

        assertThat(graph.waitOf("p.A")).contains(new Wait.Missing(List.of("p.A", "p.B", "gen.Never")));
        assertThat(graph.waitOf("p.B")).contains(new Wait.Missing(List.of("p.B", "gen.Never")));
    }

    @Test
    void typesThatOnlyAwaitEachOtherWaitInACircle() {
        TypeGraph graph = graph(
                List.of(waiting("p.A"), waiting("p.B"), waiting("p.C"), waiting("p.D")),
                List.of(awaits("p.A", "p.B"), awaits("p.B", "p.C"), awaits("p.C", "p.A"), awaits("p.D", "p.B")));

        assertThat(graph.waitOf("p.A")).contains(new Wait.Cycle(List.of("p.A", "p.B", "p.C", "p.A")));
        // p.D is not in the circle, but waits for it
        assertThat(graph.waitOf("p.D")).contains(new Wait.Cycle(List.of("p.D", "p.B", "p.C", "p.A", "p.B")));
    }

    @Test
    void aTypeThatAwaitsItselfWaitsInACircle() {
        TypeGraph graph = graph(List.of(waiting("p.A")), List.of(awaits("p.A", "p.A")));

        assertThat(graph.waitOf("p.A")).contains(new Wait.Cycle(List.of("p.A", "p.A")));
    }

    @Test
    void whatATypeWaitsForIsToldAsAChain() {
        assertThat(FactsProcessor.describe(new Wait.Missing(List.of("p.A", "gen.Never"))))
                .isEqualTo("mentions gen.Never, which no processor generated");
        assertThat(FactsProcessor.describe(new Wait.Missing(List.of("p.A", "p.B", "p.C", "gen.Never"))))
                .isEqualTo("waits for the metamodel of p.B, which waits for the metamodel of p.C, which mentions"
                        + " gen.Never, which no processor generated");
        assertThat(FactsProcessor.describe(new Wait.Cycle(List.of("p.A", "p.B", "p.A"))))
                .isEqualTo("waits for the metamodel of p.B, which waits for the metamodel of p.A: these metamodels"
                        + " wait for each other, and no type is missing");
    }

    // ------------------------------------------------------------------
    // the full metamodel of a supertype
    // ------------------------------------------------------------------

    @Test
    void aNodeTellsWhatFullMetamodelThereIsOfItsType() {
        assertThat(Stream.of(
                                waiting("p.A"),
                                new Node.Mentioned("p.B", new Token.OnClasspath(METAMODEL, true)),
                                new Node.Mentioned("p.C", new Token.OnClasspath(METAMODEL, false)),
                                new Node.Mentioned("p.D", new Token.Settled(new Done.Reused(METAMODEL, true))),
                                new Node.Mentioned("p.E", new Token.Settled(new Done.Generated(METAMODEL, false))),
                                new Node.Mentioned("p.F", new Token.Settled(new Done.Failed())),
                                new Node.Mentioned("p.G", new Token.Unavailable("it is odd")),
                                new Node.Inherited("p.H", new Full.Refused("p.H is not public")),
                                absent("gen.Never"))
                        .map(Node::full))
                .containsExactly(
                        new Full.Asked(),
                        new Full.OnClasspath(),
                        new Full.Askable(),
                        new Full.OnClasspath(),
                        new Full.Askable(),
                        new Full.Askable(),
                        new Full.Refused("p.G: it is odd"),
                        new Full.Refused("p.H is not public"),
                        new Full.Refused("gen.Never does not exist"));
    }
}
