package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The types of one round and how they depend on each other (mini-spec §3,
/// §8, Q13): every type that is needed to generate what `@Facts` asks for
/// in full, and why it is needed.
///
/// A type is a node, named by its binary name, and says what the round makes
/// of its metamodel:
///
/// - [Node.Requested] — `@Facts` asks for the type: a full metamodel;
/// - [Node.Inherited] — a requested type extends or implements the type,
///   directly or through others: a full metamodel too, so that a member the
///   requested type inherits is reached through the metamodel of the type
///   that declares it (Q6(b)), without `@Facts` naming every supertype;
/// - [Node.Hidden] — such a supertype that is not `public`: no metamodel,
///   its `public` members are facts of the metamodels of its nearest
///   `public` subtypes;
/// - [Node.Declined] — such a supertype no full metamodel can be made of
///   for another reason: the members inherited from it have no facts;
/// - [Node.Mentioned] — a signature of a type with a full metamodel mentions
///   the type: a token-only metamodel, and nothing further, neither for
///   what its own signatures mention nor for what it extends;
/// - [Node.Absent] — a type no processor has generated yet.
///
/// An edge is one of three kinds:
///
/// - [Edge.Signature] — a signature of a type with a full metamodel mentions
///   the type, which therefore needs a token;
/// - [Edge.Supertype] — a type extends or implements the type directly;
/// - [Edge.Awaits] — the full metamodel of a type cannot be written before
///   the type is there: a type no processor generated yet, or a type that
///   itself awaits one.
///
/// Why a type is in the graph is read from it ([#reasons(String)]): it is
/// asked for, it is a supertype of what is asked for, signatures mention
/// it.
///
/// The graph is complete ([#complete()]) once every type a full metamodel
/// is wanted of is in it: every class literal of `@Facts` names a type that
/// is there, and so does every type the requested types and their
/// supertypes extend and implement. Until then a type that is missing may
/// turn out to extend or implement a type of the graph, which is then
/// wanted in full, though the graph has it as a type that is only
/// mentioned. A full metamodel is what it is whatever is missing, and so is
/// the token-only one of a type nothing can extend or implement — a `final`
/// class, an enum, a record: both are written from a graph that is not
/// complete. Only the token-only metamodel of a type that may yet turn out
/// a supertype is held back ([Token.Held], [#held()]) until the graph is
/// complete, or there is no round left to wait for.
///
/// The graph is a value: it is built once per round ([Closure]), nothing in
/// it changes, and what the processor does in the round — which metamodels
/// it writes, which it holds back, which wait, which are refused — is read
/// from it. Nodes are sorted by name and edges by source, kind and target,
/// so every answer comes in the same order however the graph was put
/// together.
///
/// @param nodes the types, by binary name
/// @param edges the dependencies between them
/// @param asked whether every type `@Facts` asks for is a node
record TypeGraph(SortedMap<String, Node> nodes, SortedSet<Edge> edges, Asked asked) {

    /// @throws IllegalArgumentException if a node is not under its own name, an edge is from or to a
    ///                                  type that is not a node, or a token-only metamodel is held
    ///                                  back though the graph is complete
    TypeGraph {
        SortedMap<String, Node> sortedNodes = new TreeMap<>(nodes);
        SortedSet<Edge> sortedEdges = new TreeSet<>(Edge.ORDER);
        sortedEdges.addAll(edges);
        sortedNodes.forEach((name, node) -> {
            if (!node.name().equals(name)) {
                throw new IllegalArgumentException("node " + node.name() + " is under the name " + name);
            }
        });
        sortedEdges.stream()
                .flatMap(edge -> Stream.of(edge.from(), edge.to()))
                .filter(name -> !sortedNodes.containsKey(name))
                .findFirst()
                .ifPresent(name -> {
                    throw new IllegalArgumentException("an edge is at " + name + ", which is not a node");
                });
        nodes = Collections.unmodifiableSortedMap(sortedNodes);
        edges = Collections.unmodifiableSortedSet(sortedEdges);
        boolean complete = asked == Asked.ALL_THERE
                && sortedEdges.stream()
                        .noneMatch(edge ->
                                edge instanceof Edge.Supertype && sortedNodes.get(edge.to()) instanceof Node.Absent);
        if (complete) {
            sortedNodes.values().stream()
                    .filter(node ->
                            node.token().filter(Token.Held.class::isInstance).isPresent())
                    .findFirst()
                    .ifPresent(node -> {
                        throw new IllegalArgumentException(
                                "the token-only metamodel of " + node.name() + " is held back, but no type is missing");
                    });
        }
    }

    /// A graph of nodes and edges, in whatever order they come, in which
    /// every class literal of `@Facts` names a type that is there.
    ///
    /// @param nodes the types
    /// @param edges the dependencies between them
    /// @return the graph
    /// @throws IllegalArgumentException if two nodes have the same name, or an edge is from or to a
    ///                                  type that is not a node
    static TypeGraph of(Stream<? extends Node> nodes, Stream<? extends Edge> edges) {
        return of(nodes, edges, Asked.ALL_THERE);
    }

    /// A graph of nodes and edges, in whatever order they come.
    ///
    /// @param nodes the types
    /// @param edges the dependencies between them
    /// @param asked whether every type `@Facts` asks for is among the nodes
    /// @return the graph
    /// @throws IllegalArgumentException if two nodes have the same name, an edge is from or to a
    ///                                  type that is not a node, or a token-only metamodel is held
    ///                                  back though the graph is complete
    static TypeGraph of(Stream<? extends Node> nodes, Stream<? extends Edge> edges, Asked asked) {
        return new TypeGraph(
                nodes.collect(Collectors.toMap(
                        Node::name,
                        Function.<Node>identity(),
                        (first, second) -> {
                            throw new IllegalArgumentException("two nodes are named " + first.name());
                        },
                        TreeMap::new)),
                edges.collect(Collectors.toCollection(() -> new TreeSet<>(Edge.ORDER))),
                asked);
    }

    /// Whether the graph has every type a full metamodel is wanted of, so
    /// that what each metamodel is — full or token-only — is final: no
    /// `@Facts` asks for a type that is not generated yet, and no type
    /// extends or implements one ([#missingSupertypes()]). Such a type may
    /// extend or implement any other once it is there that can be extended
    /// or implemented, a type that is only mentioned so far as much as one
    /// the graph does not have yet.
    ///
    /// @return `true` if no type is missing that may make a mentioned type a supertype
    boolean complete() {
        return asked == Asked.ALL_THERE && missingSupertypes().findAny().isEmpty();
    }

    /// The mentioned types whose token-only metamodels are held back
    /// ([Token.Held]), sorted: empty in a complete graph.
    ///
    /// @return the binary names of the types
    Stream<String> held() {
        return nodes.values().stream()
                .filter(node ->
                        node.token().filter(Token.Held.class::isInstance).isPresent())
                .map(Node::name);
    }

    /// The supertypes that are not generated yet: the [Edge.Supertype] that
    /// lead to a [Node.Absent], sorted.
    ///
    /// @return the edges, each from a requested type or a supertype of one to the missing type
    Stream<Edge.Supertype> missingSupertypes() {
        return edges.stream()
                .flatMap(edge -> edge instanceof Edge.Supertype supertype ? Stream.of(supertype) : Stream.empty())
                .filter(supertype -> nodes.get(supertype.to()) instanceof Node.Absent);
    }

    /// The edges of one kind that leave a type, sorted by target.
    ///
    /// @param name the binary name of the type
    /// @param kind the kind of the edges
    /// @param <E> the kind of the edges
    /// @return the edges
    <E extends Edge> Stream<E> from(String name, Class<E> kind) {
        return edges.stream()
                .filter(kind::isInstance)
                .map(kind::cast)
                .filter(edge -> edge.from().equals(name));
    }

    /// The types whose signatures mention a type, sorted.
    ///
    /// @param name the binary name of the mentioned type
    /// @return the binary names of the types that mention it
    Stream<String> mentioners(String name) {
        return edges.stream()
                .filter(edge -> edge instanceof Edge.Signature && edge.to().equals(name))
                .map(Edge::from);
    }

    /// The types a type extends or implements, directly or through others,
    /// sorted.
    ///
    /// @param name the binary name of the type
    /// @return the binary names of its supertypes
    Stream<String> supertypes(String name) {
        Map<String, List<String>> direct = supertypeEdges(Edge::from, Edge::to);
        return reach(new TreeSet<>(Set.of(name)), direct).stream().filter(supertype -> !supertype.equals(name));
    }

    /// The requested types a type is a supertype of, and the type itself if
    /// it is requested, sorted: the requests the type is in the graph for,
    /// unless it is only mentioned.
    ///
    /// @param name the binary name of the type
    /// @return the binary names of the requested types
    Stream<String> roots(String name) {
        Map<String, List<String>> direct = supertypeEdges(Edge::to, Edge::from);
        return reach(new TreeSet<>(Set.of(name)), direct).stream()
                .filter(subtype -> nodes.get(subtype) instanceof Node.Requested);
    }

    /// Why a type is in the graph: every reason there is, a request first,
    /// then the requested types it is a supertype of, then the types that
    /// mention it, each sorted.
    ///
    /// @param name the binary name of the type
    /// @return the reasons; empty for a type that is only awaited
    Stream<Reason> reasons(String name) {
        return Stream.<Stream<? extends Reason>>of(
                        nodes.get(name) instanceof Node.Requested ? Stream.of(new Reason.Asked()) : Stream.empty(),
                        roots(name).filter(root -> !root.equals(name)).map(Reason.Supertype::new),
                        mentioners(name).map(Reason.Mentioned::new))
                .flatMap(Function.identity());
    }

    private Map<String, List<String>> supertypeEdges(Function<Edge, String> key, Function<Edge, String> value) {
        return edges.stream()
                .filter(Edge.Supertype.class::isInstance)
                .collect(Collectors.groupingBy(key, Collectors.mapping(value, Collectors.toList())));
    }

    /// The types in `reached` and every type a step leads to from them, and so on until no type is added.
    private static SortedSet<String> reach(SortedSet<String> reached, Map<String, List<String>> steps) {
        SortedSet<String> grown = Stream.concat(
                        reached.stream(),
                        reached.stream().flatMap(type -> steps.getOrDefault(type, List.of()).stream()))
                .collect(Collectors.toCollection(TreeSet::new));
        return grown.size() == reached.size() ? reached : reach(grown, steps);
    }

    /// Whether a type awaits another: its metamodel is not written in this round.
    ///
    /// @param name the binary name of the type
    /// @return `true` if an [Edge.Awaits] leaves the type
    boolean waits(String name) {
        return from(name, Edge.Awaits.class).findAny().isPresent();
    }

    /// What a type waits for in the end: the shortest way along
    /// [Edge.Awaits] to a type that awaits nothing — the one that is missing
    /// — or, if there is no such type, the circle the type is caught in.
    /// Among ways of the same length the first by the names of the types is
    /// taken.
    ///
    /// @param name the binary name of the type
    /// @return what it waits for, empty if it awaits nothing
    Optional<Wait> waitOf(String name) {
        return waits(name) ? Optional.of(search(name, List.of(List.of(name)), Set.of(name))) : Optional.empty();
    }

    /// A step of the search, breadth first: `frontier` holds the shortest way to each type that is
    /// that many steps away, and every type on it awaits something.
    private Wait search(String name, List<List<String>> frontier, Set<String> seen) {
        List<List<String>> next = List.copyOf(frontier.stream()
                .flatMap(way -> from(way.getLast(), Edge.Awaits.class)
                        .map(Edge::to)
                        .filter(to -> !seen.contains(to))
                        .map(to -> append(way, to)))
                .collect(Collectors.toMap(
                        List::getLast, Function.identity(), (first, second) -> first, LinkedHashMap::new))
                .values());
        if (next.isEmpty()) {
            return new Wait.Cycle(circle(List.of(name)));
        }
        return next.stream()
                .filter(way -> !waits(way.getLast()))
                .findFirst()
                .<Wait>map(Wait.Missing::new)
                .orElseGet(() -> search(
                        name,
                        next,
                        Stream.concat(seen.stream(), next.stream().map(List::getLast))
                                .collect(Collectors.toSet())));
    }

    /// Follows the first edge of every type until a type comes up again.
    private List<String> circle(List<String> way) {
        String next = from(way.getLast(), Edge.Awaits.class)
                .map(Edge::to)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(way.getLast() + " awaits nothing"));
        List<String> longer = append(way, next);
        return way.contains(next) ? longer : circle(longer);
    }

    private static List<String> append(List<String> way, String next) {
        return Stream.concat(way.stream(), Stream.of(next)).toList();
    }

    /// A type of the round.
    sealed interface Node {

        /// The binary name of the type, `java.util.Map$Entry`.
        ///
        /// @return the name
        String name();

        /// What becomes of the full metamodel of the type in this round.
        ///
        /// @return empty unless a full metamodel is wanted of the type
        default Optional<Request> request() {
            return Optional.empty();
        }

        /// What the round has for a token of the type, where it has no full metamodel.
        ///
        /// @return empty unless a signature mentions the type and no full metamodel is wanted of it
        default Optional<Token> token() {
            return Optional.empty();
        }

        /// A type `@Facts` asks for.
        ///
        /// @param name the binary name
        /// @param asked what becomes of the request in this round
        record Requested(String name, Request asked) implements Node {
            @Override
            public Optional<Request> request() {
                return Optional.of(asked);
            }
        }

        /// A `public` type a requested type extends or implements, directly
        /// or through others, which `@Facts` does not ask for: it gets a full
        /// metamodel as if it were asked for.
        ///
        /// @param name the binary name
        /// @param plan what becomes of its full metamodel in this round
        record Inherited(String name, Request.Plan plan) implements Node {
            @Override
            public Optional<Request> request() {
                return Optional.of(plan);
            }
        }

        /// A `public` type a requested type extends or implements that no
        /// full metamodel can be made of: the members inherited from it have
        /// no facts, and the requested type is generated without them.
        ///
        /// @param name the binary name
        /// @param reason why there is no full metamodel, a sentence about the type
        /// @param mentioned what the round has for a token of the type, if a signature mentions it
        record Declined(String name, String reason, Optional<Token> mentioned) implements Node {
            @Override
            public Optional<Token> token() {
                return mentioned;
            }
        }

        /// A type a requested type extends or implements that is not
        /// `public`, or is nested in a type that is not: no metamodel can
        /// name it. Its `public` members are facts of the full metamodels
        /// of the nearest `public` subtypes, which adopt them.
        ///
        /// @param name the binary name
        record Hidden(String name) implements Node {}

        /// A type a signature mentions, of which no full metamodel is wanted.
        ///
        /// @param name the binary name
        /// @param mentioned what the round has for a token of the type
        record Mentioned(String name, Token mentioned) implements Node {
            @Override
            public Optional<Token> token() {
                return Optional.of(mentioned);
            }
        }

        /// A type that does not exist yet: no processor has generated it.
        ///
        /// @param name the name, as javac gives it
        record Absent(String name) implements Node {}
    }

    /// What becomes of the full metamodel of a type in a round.
    sealed interface Request {

        /// What becomes of a full metamodel that can be made: of a
        /// requested type, and of a supertype of one, which is in the graph
        /// as [Node.Inherited] only with such a plan.
        sealed interface Plan extends Request {}

        /// An earlier round dealt with the type.
        ///
        /// @param done what became of it
        record Settled(Done done) implements Plan {}

        /// A full metamodel on the classpath matches the type and is reused.
        ///
        /// @param metamodel the metamodel class
        record OnClasspath(ClassDesc metamodel) implements Plan {}

        /// The type and what its signatures mention are read: the full metamodel is written in this
        /// round unless the type awaits another.
        ///
        /// @param type the type
        /// @param stale why the metamodels of the type on the classpath are not reused
        record Ready(TypeElement type, List<String> stale) implements Plan {
            /// Copies the reasons.
            public Ready {
                stale = List.copyOf(stale);
            }
        }

        /// The type or a type it mentions is not generated yet: an [Edge.Awaits] leads to it.
        record Waiting() implements Plan {}

        /// An earlier round generated a token-only metamodel of the type, before `@Facts` asked for
        /// it: a metamodel is written once, so there is no full one. That is the case only of a
        /// `@Facts` that was not there in that round, one another processor generated: the graph of
        /// a round is complete as far as the `@Facts` of the round go, not for what is yet to ask.
        ///
        /// @param tokenOnly the metamodel class
        record Late(ClassDesc tokenOnly) implements Request {}

        /// No metamodel can be made of the type, full or token-only.
        ///
        /// @param reason why, for a diagnostic
        record Unrepresentable(String reason) implements Request {}

        /// No full metamodel can be made of the type.
        ///
        /// @param reason why, for a diagnostic
        record Rejected(String reason) implements Request {}
    }

    /// What a round has for a token of a mentioned type.
    sealed interface Token {

        /// An earlier round dealt with the type.
        ///
        /// @param done what became of it
        record Settled(Done done) implements Token {}

        /// A metamodel on the classpath matches the type and is reused.
        ///
        /// @param metamodel the metamodel class
        /// @param full whether the metamodel is full
        record OnClasspath(ClassDesc metamodel, boolean full) implements Token {}

        /// A token-only metamodel is written in this round.
        ///
        /// @param type the type
        /// @param stale why the metamodels of the type on the classpath are not reused
        record Planned(TypeElement type, List<String> stale) implements Token {
            /// Copies the reasons.
            public Planned {
                stale = List.copyOf(stale);
            }
        }

        /// A token-only metamodel that is not written while the graph is not complete
        /// ([TypeGraph#complete()]): the type can be extended or implemented, so a type that is not
        /// there yet may turn out to do so, and a full metamodel is then wanted of this one. Other
        /// metamodels refer to it all the same, by the name it will have either way. It is written
        /// by the round whose graph is complete, as what the type is then; or, token-only, by the
        /// last round, if the missing type never comes.
        ///
        /// @param type the type
        /// @param stale why the metamodels of the type on the classpath are not reused
        record Held(TypeElement type, List<String> stale) implements Token {
            /// Copies the reasons.
            public Held {
                stale = List.copyOf(stale);
            }
        }

        /// No metamodel can be made of the type, such as an annotation interface: the members that
        /// mention it get no facts.
        ///
        /// @param reason why
        record Unavailable(String reason) implements Token {}
    }

    /// Whether every type `@Facts` asks for is in the graph. A class literal of a type that is
    /// not generated yet has no name javac would tell, so such a type is no node: the graph
    /// knows only that there is one.
    enum Asked {
        /// Every class literal of `@Facts` names a type of the graph.
        ALL_THERE,
        /// A class literal of `@Facts` names a type no processor has generated yet.
        SOME_MISSING
    }

    /// Why a type is in the graph. A type may have several reasons; the
    /// first of [#reasons(String)] that applies decides its metamodel: a
    /// full one for a type that is asked for or is a supertype, a token-only
    /// one for a type that is only mentioned.
    sealed interface Reason {

        /// `@Facts` asks for the type.
        record Asked() implements Reason {}

        /// A requested type extends or implements the type, directly or through others.
        ///
        /// @param of the binary name of the requested type
        record Supertype(String of) implements Reason {}

        /// A signature of a type with a full metamodel mentions the type.
        ///
        /// @param by the binary name of the type whose signature it is, which has reasons of its own
        record Mentioned(String by) implements Reason {}
    }

    /// A dependency of a type on another.
    sealed interface Edge {

        /// The order of the edges: by source, then by kind — signatures, supertypes, awaited types —
        /// then by target.
        Comparator<Edge> ORDER =
                Comparator.comparing(Edge::from).thenComparingInt(Edge::rank).thenComparing(Edge::to);

        /// The type that depends.
        ///
        /// @return the binary name
        String from();

        /// The type it depends on.
        ///
        /// @return the binary name
        String to();

        private int rank() {
            return switch (this) {
                case Signature _ -> 0;
                case Supertype _ -> 1;
                case Awaits _ -> 2;
            };
        }

        /// A signature of `from`, a type whose full metamodel is read, mentions `to`: in a
        /// parameter, a result, a field, `throws`, a type argument, a bound of a type parameter of
        /// the type or of a method, or the single abstract method of a functional interface,
        /// declared or inherited.
        ///
        /// @param from the type with the full metamodel
        /// @param to the mentioned type
        record Signature(String from, String to) implements Edge {}

        /// `from` extends or implements `to` directly.
        ///
        /// @param from a requested type or a supertype of one
        /// @param to its superclass or a superinterface it names, a [Node.Absent] if that is not
        ///     generated yet
        record Supertype(String from, String to) implements Edge {}

        /// The full metamodel of `from` is not written before `to` is there: `to` is not generated
        /// yet, or is a type with a full metamodel that a fact of `from` mentions, which itself
        /// awaits a type.
        ///
        /// @param from the type with the full metamodel
        /// @param to the awaited type
        record Awaits(String from, String to) implements Edge {}
    }

    /// What a type that awaits another waits for in the end.
    sealed interface Wait {

        /// A type that awaits nothing itself, so is what is missing.
        ///
        /// @param chain the type, the types that await each other in turn, and the missing type last
        record Missing(List<String> chain) implements Wait {
            /// Copies the chain.
            public Missing {
                chain = List.copyOf(chain);
            }
        }

        /// Types that await each other in a circle, none of which is missing.
        ///
        /// @param chain the type and the types that await each other in turn, up to the first that
        ///     comes up a second time, which is last
        record Cycle(List<String> chain) implements Wait {
            /// Copies the chain.
            public Cycle {
                chain = List.copyOf(chain);
            }
        }
    }
}
