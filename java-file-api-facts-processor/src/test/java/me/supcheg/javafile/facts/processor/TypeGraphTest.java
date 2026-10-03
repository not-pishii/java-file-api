package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Reason;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.facts.processor.TypeGraph.Wait;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The graph of the types of a round as a value: its edges by kind, the
/// supertypes of a type and the requests it is there for along them, why a
/// type is in the graph, what a type waits for, and that nothing depends on
/// the order the graph is put together in.
class TypeGraphTest {
    private static final ClassDesc METAMODEL = ClassDesc.of("gen.facts.p.T_");

    private static Node waiting(String name) {
        return new Node.Requested(name, new Request.Waiting());
    }

    private static Node inherited(String name) {
        return new Node.Inherited(name, new Request.Waiting());
    }

    private static Node mentioned(String name) {
        return new Node.Mentioned(name, new Token.Unavailable("no reason"));
    }

    private static Node absent(String name) {
        return new Node.Absent(name);
    }

    private static Edge.Supertype extends_(String from, String to) {
        return new Edge.Supertype(from, to);
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
                        mentioned("p.M"),
                        inherited("p.S"),
                        inherited("p.T"),
                        absent("gen.Never")),
                List.of(
                        new Edge.Signature("p.A", "p.M"),
                        new Edge.Signature("p.A", "p.B"),
                        new Edge.Signature("p.B", "p.M"),
                        extends_("p.A", "p.T"),
                        extends_("p.A", "p.S"),
                        awaits("p.A", "p.B"),
                        awaits("p.B", "gen.Never")));

        assertThat(graph.from("p.A", Edge.Signature.class))
                .containsExactly(new Edge.Signature("p.A", "p.B"), new Edge.Signature("p.A", "p.M"));
        assertThat(graph.from("p.A", Edge.Supertype.class))
                .containsExactly(extends_("p.A", "p.S"), extends_("p.A", "p.T"));
        assertThat(graph.from("p.A", Edge.Awaits.class)).containsExactly(new Edge.Awaits("p.A", "p.B"));
        assertThat(graph.from("p.M", Edge.class)).isEmpty();
        assertThat(graph.mentioners("p.M")).containsExactly("p.A", "p.B");
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
                        extends_("p.A", "p.C"),
                        extends_("p.A", "p.B"),
                        new Edge.Signature("p.A", "p.C"),
                        new Edge.Signature("p.A", "p.B")));

        assertThat(graph.edges())
                .containsExactly(
                        new Edge.Signature("p.A", "p.B"),
                        new Edge.Signature("p.A", "p.C"),
                        extends_("p.A", "p.B"),
                        extends_("p.A", "p.C"),
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
                extends_("p.B", "p.C"));
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
            assertThat(GraphProbe.supertypes(shuffled, "p.B")).containsExactly("p.C");
            assertThat(shuffled.roots("p.C")).containsExactly("p.B", "p.C");
            assertThat(shuffled.reasons("p.D")).containsExactly(new Reason.Asked(), new Reason.Mentioned("p.A"));
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
    // whether every type is there
    // ------------------------------------------------------------------

    @Test
    void aGraphIsCompleteUnlessARequestedTypeOrASupertypeIsMissing() {
        // a type a signature mentions that is not generated yet makes a type wait, but adds no supertype
        TypeGraph mentionsMissing =
                graph(List.of(waiting("p.A"), absent("gen.Missing")), List.of(awaits("p.A", "gen.Missing")));
        assertThat(mentionsMissing.complete()).isTrue();
        assertThat(mentionsMissing.missingSupertypes()).isEmpty();

        TypeGraph extendsMissing = graph(
                List.of(waiting("p.A"), new Node.Hidden("p.H"), absent("gen.Missing"), absent("gen.Other")),
                List.of(
                        extends_("p.A", "p.H"),
                        extends_("p.H", "gen.Other"),
                        extends_("p.A", "gen.Missing"),
                        awaits("p.A", "gen.Missing")));
        assertThat(extendsMissing.complete()).isFalse();
        assertThat(extendsMissing.missingSupertypes())
                .containsExactly(extends_("p.A", "gen.Missing"), extends_("p.H", "gen.Other"));

        TypeGraph asksForMissing =
                TypeGraph.of(Stream.of(mentioned("p.M")), Stream.empty(), TypeGraph.Asked.SOME_MISSING);
        assertThat(asksForMissing.complete()).isFalse();
        assertThat(family().complete()).isTrue();
    }

    @Test
    void aTokenOnlyMetamodelIsHeldBackOnlyWhileATypeIsMissing() {
        Node held = new Node.Mentioned("p.M", new Token.Held(null, List.of()));

        TypeGraph asksForMissing =
                TypeGraph.of(Stream.of(held, mentioned("p.N")), Stream.empty(), TypeGraph.Asked.SOME_MISSING);
        assertThat(asksForMissing.held()).containsExactly("p.M");
        TypeGraph extendsMissing =
                graph(List.of(waiting("p.A"), held, absent("gen.Missing")), List.of(extends_("p.A", "gen.Missing")));
        assertThat(extendsMissing.held()).containsExactly("p.M");

        assertThat(family().held()).isEmpty();
        assertThatThrownBy(() -> graph(List.of(held), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("the token-only metamodel of p.M is held back, but no type is missing");
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
        assertThat(FactsProcessor.describe(new Wait.Missing(List.of("p.A", "gen.Never")), name -> false))
                .isEqualTo("mentions gen.Never, which no processor generated");
        assertThat(FactsProcessor.describe(new Wait.Missing(List.of("p.A", "p.B", "p.C", "gen.Never")), name -> false))
                .isEqualTo("waits for the metamodel of p.B, which waits for the metamodel of p.C, which mentions"
                        + " gen.Never, which no processor generated");
        // a type that exists, but cannot be named where it is mentioned
        assertThat(FactsProcessor.describe(new Wait.Missing(List.of("p.A", "q.Hid")), "q.Hid"::equals))
                .isEqualTo("mentions q.Hid, which is not accessible there");
        assertThat(FactsProcessor.describe(new Wait.Cycle(List.of("p.A", "p.B", "p.A")), name -> false))
                .isEqualTo("waits for the metamodel of p.B, which waits for the metamodel of p.A: these metamodels"
                        + " wait for each other, and no type is missing");
    }

    // ------------------------------------------------------------------
    // supertypes, and why a type is in the graph
    // ------------------------------------------------------------------

    /// `X` and `Y` are asked for; `X` extends the hidden `H`, which extends `S`, which implements `I`;
    /// `Y` extends `S` too, and implements `D`, of which there is no full metamodel; the signatures of
    /// `X` mention `M` and `D`, those of `S` mention `N` and `X`, and those of `I` mention `M`.
    private static TypeGraph family() {
        return graph(
                List.of(
                        new Node.Requested("p.X", new Request.Waiting()),
                        new Node.Requested("p.Y", new Request.Waiting()),
                        new Node.Hidden("p.H"),
                        inherited("p.S"),
                        inherited("p.I"),
                        new Node.Declined("p.D", "p.D is odd", Optional.of(new Token.Unavailable("it is odd"))),
                        mentioned("p.M"),
                        mentioned("p.N")),
                List.of(
                        extends_("p.X", "p.H"),
                        extends_("p.H", "p.S"),
                        extends_("p.S", "p.I"),
                        extends_("p.Y", "p.S"),
                        extends_("p.Y", "p.D"),
                        new Edge.Signature("p.X", "p.M"),
                        new Edge.Signature("p.X", "p.D"),
                        new Edge.Signature("p.S", "p.N"),
                        new Edge.Signature("p.S", "p.X"),
                        new Edge.Signature("p.I", "p.M")));
    }

    @Test
    void theSupertypesOfATypeAreAllItExtendsAndImplementsThroughOthers() {
        TypeGraph graph = family();

        assertThat(graph.from("p.X", Edge.Supertype.class).map(Edge::to)).containsExactly("p.H");
        assertThat(GraphProbe.supertypes(graph, "p.X")).containsExactly("p.H", "p.I", "p.S");
        assertThat(GraphProbe.supertypes(graph, "p.Y")).containsExactly("p.D", "p.I", "p.S");
        assertThat(GraphProbe.supertypes(graph, "p.S")).containsExactly("p.I");
        assertThat(GraphProbe.supertypes(graph, "p.I")).isEmpty();
        // a signature leads no further: what a mentioned type extends is not followed
        assertThat(GraphProbe.supertypes(graph, "p.M")).isEmpty();
    }

    @Test
    void theRootsOfATypeAreTheRequestsItIsThereFor() {
        TypeGraph graph = family();

        assertThat(graph.roots("p.X")).containsExactly("p.X");
        assertThat(graph.roots("p.H")).containsExactly("p.X");
        assertThat(graph.roots("p.S")).containsExactly("p.X", "p.Y");
        assertThat(graph.roots("p.I")).containsExactly("p.X", "p.Y");
        assertThat(graph.roots("p.D")).containsExactly("p.Y");
        // a type that is only mentioned is there for the types that mention it, not for a request of its own
        assertThat(graph.roots("p.M")).isEmpty();
        assertThat(graph.mentioners("p.M").flatMap(graph::roots).distinct()).containsExactly("p.X", "p.Y");
    }

    @Test
    void aTypeIsInTheGraphForEveryReasonThereIs() {
        TypeGraph graph = family();

        // asked for, and mentioned by a supertype of its own
        assertThat(graph.reasons("p.X")).containsExactly(new Reason.Asked(), new Reason.Mentioned("p.S"));
        assertThat(graph.reasons("p.Y")).containsExactly(new Reason.Asked());
        assertThat(graph.reasons("p.H")).containsExactly(new Reason.Supertype("p.X"));
        assertThat(graph.reasons("p.S")).containsExactly(new Reason.Supertype("p.X"), new Reason.Supertype("p.Y"));
        assertThat(graph.reasons("p.I")).containsExactly(new Reason.Supertype("p.X"), new Reason.Supertype("p.Y"));
        assertThat(graph.reasons("p.D")).containsExactly(new Reason.Supertype("p.Y"), new Reason.Mentioned("p.X"));
        assertThat(graph.reasons("p.M")).containsExactly(new Reason.Mentioned("p.I"), new Reason.Mentioned("p.X"));
        assertThat(graph.reasons("p.N")).containsExactly(new Reason.Mentioned("p.S"));
    }

    @Test
    void aNodeTellsWhatIsWantedOfItsType() {
        TypeGraph graph = family();

        // a full metamodel of what is asked for and of its public supertypes
        assertThat(graph.nodes().get("p.X").request()).contains(new Request.Waiting());
        assertThat(graph.nodes().get("p.S").request()).contains(new Request.Waiting());
        assertThat(graph.nodes().get("p.X").token()).isEmpty();
        // a token of what is mentioned, a declined supertype among it
        assertThat(graph.nodes().get("p.M").token()).contains(new Token.Unavailable("no reason"));
        assertThat(graph.nodes().get("p.M").request()).isEmpty();
        assertThat(graph.nodes().get("p.D").token()).contains(new Token.Unavailable("it is odd"));
        assertThat(graph.nodes().get("p.D").request()).isEmpty();
        // nothing of a hidden supertype and of a type that is missing
        assertThat(graph.nodes().get("p.H").request()).isEmpty();
        assertThat(graph.nodes().get("p.H").token()).isEmpty();
        assertThat(absent("gen.Never").request()).isEmpty();
        assertThat(absent("gen.Never").token()).isEmpty();
        assertThat(METAMODEL.displayName()).isEqualTo("T_");
    }
}
