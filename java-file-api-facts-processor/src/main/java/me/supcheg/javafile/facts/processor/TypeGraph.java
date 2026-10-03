package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
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
/// §8): what `@Facts` asks for, what the signatures of those types mention,
/// what they extend and implement, and what is not generated yet.
///
/// A type is a node, named by its binary name, and says what the round makes
/// of its metamodel. An edge is one of three kinds:
///
/// - [Edge.Signature] — a signature of a requested type mentions the type,
///   which therefore needs a token;
/// - [Edge.Supertype] — a requested type extends or implements the type,
///   directly or through others;
/// - [Edge.Awaits] — the full metamodel of a requested type cannot be
///   written before the type is there: a type no processor generated yet, or
///   a requested type that itself awaits one.
///
/// The graph is a value: it is built once per round ([Closure]), nothing in
/// it changes, and what the processor does in the round — which metamodels
/// it writes, which wait, which are refused — is read from it. Nodes are
/// sorted by name and edges by source, kind and target, so every answer
/// comes in the same order however the graph was put together.
///
/// @param nodes the types, by binary name
/// @param edges the dependencies between them
record TypeGraph(SortedMap<String, Node> nodes, SortedSet<Edge> edges) {

    /// @throws IllegalArgumentException if a node is not under its own name, or an edge is from or
    ///                                  to a type that is not a node
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
    }

    /// A graph of nodes and edges, in whatever order they come.
    ///
    /// @param nodes the types
    /// @param edges the dependencies between them
    /// @return the graph
    /// @throws IllegalArgumentException if two nodes have the same name, or an edge is from or to a
    ///                                  type that is not a node
    static TypeGraph of(Stream<? extends Node> nodes, Stream<? extends Edge> edges) {
        return new TypeGraph(
                nodes.collect(Collectors.toMap(
                        Node::name,
                        Function.<Node>identity(),
                        (first, second) -> {
                            throw new IllegalArgumentException("two nodes are named " + first.name());
                        },
                        TreeMap::new)),
                edges.collect(Collectors.toCollection(() -> new TreeSet<>(Edge.ORDER))));
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

    /// The requested types whose signatures mention a type, sorted.
    ///
    /// @param name the binary name of the mentioned type
    /// @return the binary names of the types that mention it
    Stream<String> mentioners(String name) {
        return edges.stream()
                .filter(edge -> edge instanceof Edge.Signature && edge.to().equals(name))
                .map(Edge::from);
    }

    /// The supertypes a type inherits `public` members from, sorted by name:
    /// the members are facts of the metamodels of those types, not of its
    /// own (Q6(b)).
    ///
    /// @param name the binary name of a requested type
    /// @return the nodes of the supertypes
    Stream<Node> inherited(String name) {
        return from(name, Edge.Supertype.class).filter(Edge.Supertype::inherits).map(edge -> nodes.get(edge.to()));
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

        /// What full metamodel there is of the type, for a type that inherits `public` members from it.
        ///
        /// @return the full metamodel, or why there is none
        Full full();

        /// A type `@Facts` asks for.
        ///
        /// @param name the binary name
        /// @param request what becomes of the request in this round
        record Requested(String name, Request request) implements Node {
            @Override
            public Full full() {
                return new Full.Asked();
            }
        }

        /// A type a signature of a requested type mentions, which `@Facts` does not ask for.
        ///
        /// @param name the binary name
        /// @param token what the round has for a token of the type
        record Mentioned(String name, Token token) implements Node {
            @Override
            public Full full() {
                return switch (token) {
                    case Token.OnClasspath(var _, boolean full) when full -> new Full.OnClasspath();
                    case Token.Settled(Done.Reused(var _, boolean full)) when full -> new Full.OnClasspath();
                    case Token.Unavailable(String reason) -> new Full.Refused(name + ": " + reason);
                    case Token.OnClasspath _, Token.Settled _, Token.Planned _ -> new Full.Askable();
                };
            }
        }

        /// A type a requested type extends or implements, which `@Facts` does not ask for and no
        /// signature mentions.
        ///
        /// @param name the binary name
        /// @param full what full metamodel there is of it
        record Inherited(String name, Full full) implements Node {}

        /// A type that does not exist yet: no processor has generated it.
        ///
        /// @param name the name, as javac gives it
        record Absent(String name) implements Node {
            @Override
            public Full full() {
                return new Full.Refused(name + " does not exist");
            }
        }
    }

    /// What becomes of a requested type in a round.
    sealed interface Request {

        /// An earlier round dealt with the type.
        ///
        /// @param done what became of it
        record Settled(Done done) implements Request {}

        /// An earlier round generated a token-only metamodel of the type, before `@Facts` asked for
        /// it: a metamodel is written once, so there is no full one.
        ///
        /// @param tokenOnly the metamodel class
        record Late(ClassDesc tokenOnly) implements Request {}

        /// A full metamodel on the classpath matches the type and is reused.
        ///
        /// @param metamodel the metamodel class
        record OnClasspath(ClassDesc metamodel) implements Request {}

        /// The type and what its signatures mention are read: the full metamodel is written in this
        /// round unless the type awaits another.
        ///
        /// @param type the type
        /// @param stale why the metamodels of the type on the classpath are not reused
        record Ready(TypeElement type, List<String> stale) implements Request {
            /// Copies the reasons.
            public Ready {
                stale = List.copyOf(stale);
            }
        }

        /// The type or a type it mentions is not generated yet: an [Edge.Awaits] leads to it.
        record Waiting() implements Request {}

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

        /// No metamodel can be made of the type, such as an annotation interface: the members that
        /// mention it get no facts.
        ///
        /// @param reason why
        record Unavailable(String reason) implements Token {}
    }

    /// What full metamodel there is of a type.
    sealed interface Full {

        /// `@Facts` asks for one.
        record Asked() implements Full {}

        /// One on the classpath matches the type.
        record OnClasspath() implements Full {}

        /// None, but `@Facts` could ask for one.
        record Askable() implements Full {}

        /// None, and `@Facts` cannot ask for one.
        ///
        /// @param reason why, a sentence about the type
        record Refused(String reason) implements Full {}
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

        /// A signature of the requested type `from` mentions `to`: in a parameter, a result, a
        /// field, `throws`, a type argument, a bound of a type parameter of the type or of a
        /// method, or the single abstract method of a functional interface, declared or inherited.
        ///
        /// @param from the requested type
        /// @param to the mentioned type
        record Signature(String from, String to) implements Edge {}

        /// The requested type `from` extends or implements `to`, directly or through other types.
        ///
        /// @param from the requested type
        /// @param to the superclass or superinterface
        /// @param inherits whether `from` inherits `public` fields or methods that `to` declares and
        ///     no type between them, nor `from` itself, declares again
        record Supertype(String from, String to, boolean inherits) implements Edge {}

        /// The full metamodel of the requested type `from` is not written before `to` is there:
        /// `to` is not generated yet, or is a requested type a fact of `from` mentions, which
        /// itself awaits a type.
        ///
        /// @param from the requested type
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
