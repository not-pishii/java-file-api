package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Full;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.routine.Either;
import me.supcheg.routine.EitherCollectors;
import me.supcheg.routine.Pair;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
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

/// Builds the [TypeGraph] of a round: the closure of the types `@Facts`
/// asks for (mini-spec §3).
///
/// A requested type that no earlier round dealt with, and that has no full
/// metamodel on the classpath to reuse, is read with its declared `public`
/// members (Q6). It then has
///
/// - a [Edge.Signature] to every class or interface in the signatures of
///   those members — parameters, results, fields, `throws`, type arguments,
///   the bounds of the type parameters of the type and of its members. A
///   functional interface has a fact of its single abstract method whether
///   it declares the method or inherits it, so the types of that signature
///   are mentioned too. Each mentioned type that is not requested itself
///   gets a token-only metamodel, unless one on the classpath is reused;
/// - a [Edge.Supertype] to every type it extends or implements, directly or
///   through others ([Inheritance]);
/// - a [Edge.Awaits] to the type it cannot be read for, if a type it
///   mentions is not generated yet; or, once read, to every requested type
///   a fact of it mentions that itself awaits a type: a full metamodel
///   refers to the metamodel of a requested type it mentions, so one that
///   is not there yet must be waited for, or the members that mention it
///   would have no fact for good. Two requested types that mention each
///   other do not await each other: a metamodel refers to another through
///   its `Data.SHAPE` only (§2.6), so both are written in the same round.
///   Only a type that is missing makes a type wait, and every type that
///   waits is on a way to one.
///
/// A generic type is rejected if a bound of its type parameters mentions a
/// type the metamodel cannot declare the bound with — one that is not
/// `public`, or that no metamodel can name: without the bound javac would
/// accept a token of a type argument the type does not.
///
/// The closure has depth 1: a token-only metamodel needs only the shape of
/// its type, which describes supertypes and methods by descriptors, not by
/// tokens, so nothing further is needed. Nor is a metamodel generated for a
/// supertype: the edge is there to tell where the inherited members are.
final class Closure {
    private Closure() {}

    /// The graph of a round.
    ///
    /// @param requested the types `@Facts` asks for, by binary name
    /// @param done what became of types in earlier rounds, by binary name
    /// @param models the models of the round
    /// @param index the metamodels on the classpath
    /// @param base the base of the mirror packages
    /// @param elements the element utilities of the compilation
    /// @return the graph
    static TypeGraph of(
            Map<String, TypeElement> requested,
            Map<String, Done> done,
            Models models,
            ReuseIndex index,
            String base,
            Elements elements) {
        List<Asked> asked = requested.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> ask(entry.getKey(), entry.getValue(), done, models, index))
                .toList();
        SortedMap<String, Reading.Ready> ready = asked.stream()
                .flatMap(type -> type.ready().map(read -> Map.entry(type.name(), read)).stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        SortedMap<String, String> waiting = asked.stream()
                .flatMap(type -> type.unresolved().map(unresolved -> Map.entry(type.name(), unresolved)).stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        SortedMap<String, Mention> mentioned = ready.values().stream()
                .flatMap(read -> read.mentions().stream())
                .filter(mention -> !requested.containsKey(mention.name()))
                .collect(Collectors.toMap(Mention::name, Function.identity(), (first, second) -> first, TreeMap::new));
        SortedMap<String, TypeElement> supertypes = ready.values().stream()
                .flatMap(read -> Inheritance.supertypes(read.type()))
                .collect(Collectors.toMap(
                        models::binaryName, Function.identity(), (first, second) -> first, TreeMap::new));
        List<Node> present = Stream.<Stream<? extends Node>>of(
                        asked.stream().map(type -> new Node.Requested(type.name(), type.request())),
                        mentioned.values().stream()
                                .map(mention ->
                                        new Node.Mentioned(mention.name(), token(mention, done, models, index))),
                        supertypes.entrySet().stream()
                                .filter(entry -> !requested.containsKey(entry.getKey())
                                        && !mentioned.containsKey(entry.getKey()))
                                .map(entry -> new Node.Inherited(
                                        entry.getKey(), full(entry.getKey(), entry.getValue(), done, models, index))))
                .<Node>flatMap(Function.identity())
                .toList();
        Set<String> names = present.stream().map(Node::name).collect(Collectors.toSet());
        Stream<Node> absent = waiting.values().stream()
                .distinct()
                .filter(unresolved -> !names.contains(unresolved))
                .map(Node.Absent::new);
        Stream<Edge> signatures = ready.entrySet().stream()
                .flatMap(entry -> entry.getValue().mentions().stream()
                        .map(mention -> new Edge.Signature(entry.getKey(), mention.name())));
        Stream<Edge> inheritance = ready.entrySet().stream()
                .flatMap(entry -> supertypes(entry.getKey(), entry.getValue(), models, elements));
        return TypeGraph.of(
                Stream.concat(present.stream(), absent),
                Stream.of(signatures, inheritance, awaits(ready, waiting, models, base, elements))
                        .flatMap(Function.identity()));
    }

    /// What becomes of a requested type in this round.
    private static Asked ask(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        Done previous = done.get(name);
        if (previous != null) {
            return Asked.of(
                    name,
                    previous instanceof Done.Generated(ClassDesc metamodel, boolean full) && !full
                            ? new Request.Late(metamodel)
                            : new Request.Settled(previous));
        }
        return switch (index.find(type, ReuseIndex.Completeness.FULL, models)) {
            case ReuseIndex.Lookup.Reusable(ClassDesc metamodel, var _) ->
                Asked.of(name, new Request.OnClasspath(metamodel));
            case ReuseIndex.Lookup.Absent(List<String> stale) ->
                switch (read(type, models)) {
                    case Reading.Ready read ->
                        new Asked(name, new Request.Ready(type, stale), Optional.of(read), Optional.empty());
                    case Reading.Waiting(String unresolved) ->
                        new Asked(name, new Request.Waiting(), Optional.empty(), Optional.of(unresolved));
                    case Reading.Unrepresentable(String reason) -> Asked.of(name, new Request.Unrepresentable(reason));
                    case Reading.Rejected(String reason) -> Asked.of(name, new Request.Rejected(reason));
                };
        };
    }

    /// Reads a requested type and what its signatures mention.
    private static Reading read(TypeElement requested, Models models) {
        return switch (models.of(requested, MemberFilter.DECLARED_PUBLIC)) {
            case Translation.Deferred<TypeModel>(String unresolved) -> new Reading.Waiting(unresolved);
            case Translation.Unrepresentable<TypeModel>(String reason) -> new Reading.Unrepresentable(reason);
            case Translation.Ok<TypeModel>(TypeModel full)
            when !full.nonPublicBoundTypes().isEmpty() ->
                new Reading.Rejected("the bounds of the type parameters of " + requested.getQualifiedName()
                        + " mention types that are not public: " + String.join(", ", full.nonPublicBoundTypes()));
            // the sam of a functional interface is a fact even where it is inherited: its signature is mentioned too
            case Translation.Ok<TypeModel>(TypeModel full) ->
                switch (models.sam(requested)) {
                    case Translation.Ok<Optional<SamModel>>(var sam) ->
                        read(requested, full, sam.stream().map(SamModel::method).flatMap(Mentions::of), models);
                    case Translation.Deferred<Optional<SamModel>>(var unresolved) -> new Reading.Waiting(unresolved);
                    case Translation.Unrepresentable<Optional<SamModel>> _ ->
                        read(requested, full, Stream.empty(), models);
                };
        };
    }

    private static Reading read(TypeElement requested, TypeModel full, Stream<ClassDesc> samMentions, Models models) {
        String self = Models.binaryName(full.desc());
        Pair<List<String>, List<Mention>> mentions = Stream.concat(Mentions.of(full), samMentions)
                .map(Models::binaryName)
                .filter(name -> !name.equals(self))
                .distinct()
                .sorted()
                .map(name -> mention(name, models))
                .collect(EitherCollectors.groupingTo(Collectors.toList(), Collectors.toList()));
        if (!mentions.left().isEmpty()) {
            return new Reading.Waiting(mentions.left().getFirst());
        }
        List<Mention> resolved = mentions.right();
        Set<String> bounds =
                Mentions.ofBounds(full.typeParams()).map(Models::binaryName).collect(Collectors.toSet());
        return resolved.stream()
                .flatMap(
                        mention -> mention instanceof Mention.Unavailable unavailable && bounds.contains(mention.name())
                                ? Stream.of(unavailable)
                                : Stream.empty())
                .findFirst()
                .<Reading>map(unavailable ->
                        new Reading.Rejected("the bounds of the type parameters of " + requested.getQualifiedName()
                                + " mention " + unavailable.name() + ", which has no metamodel: "
                                + unavailable.reason()))
                .orElseGet(() -> new Reading.Ready(requested, resolved));
    }

    /// A type a signature mentions, or the type it is not read for yet.
    private static Either<String, Mention> mention(String name, Models models) {
        ClassDesc desc = ClassDesc.of(name);
        if (desc.packageName().isEmpty()) {
            return Either.right(new Mention.Unavailable(name, "a metamodel in a named package cannot refer to it"));
        }
        return models.element(desc)
                .<Either<String, Mention>>map(element -> switch (models.of(element, MemberFilter.NONE)) {
                    case Translation.Ok<TypeModel> _ -> Either.right(new Mention.Available(name, element));
                    case Translation.Deferred<TypeModel>(String unresolved) -> Either.left(unresolved);
                    case Translation.Unrepresentable<TypeModel>(String reason) ->
                        Either.right(new Mention.Unavailable(name, reason));
                })
                .orElseGet(() -> Either.right(
                        new Mention.Unavailable(name, "a class with $ in its simple name is not supported yet")));
    }

    /// What the round has for a token of a mentioned type that is not requested.
    private static Token token(Mention mention, Map<String, Done> done, Models models, ReuseIndex index) {
        return switch (mention) {
            case Mention.Unavailable(var _, String reason) -> new Token.Unavailable(reason);
            case Mention.Available(String name, TypeElement type) ->
                Optional.ofNullable(done.get(name))
                        .<Token>map(Token.Settled::new)
                        .orElseGet(() -> switch (index.find(type, ReuseIndex.Completeness.TOKEN, models)) {
                            case ReuseIndex.Lookup.Reusable(
                                    ClassDesc metamodel,
                                    ReuseIndex.Completeness completeness) ->
                                new Token.OnClasspath(metamodel, completeness == ReuseIndex.Completeness.FULL);
                            case ReuseIndex.Lookup.Absent(List<String> stale) -> new Token.Planned(type, stale);
                        });
        };
    }

    /// What full metamodel there is of a supertype that is neither requested nor mentioned.
    private static Full full(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        return Requests.refusal(type).<Full>map(Full.Refused::new).orElseGet(() -> switch (done.get(name)) {
            case Done.Reused(var _, boolean full) when full -> new Full.OnClasspath();
            case Done.Reused _, Done.Generated _, Done.Failed _ -> new Full.Askable();
            case null ->
                switch (index.find(type, ReuseIndex.Completeness.FULL, models)) {
                    case ReuseIndex.Lookup.Reusable _ -> new Full.OnClasspath();
                    case ReuseIndex.Lookup.Absent _ -> new Full.Askable();
                };
        });
    }

    private static Stream<Edge> supertypes(String name, Reading.Ready read, Models models, Elements elements) {
        boolean functional =
                models.sam(read.type()) instanceof Translation.Ok<Optional<SamModel>>(var sam) && sam.isPresent();
        Set<TypeElement> declarers = Inheritance.declarers(read.type(), functional, elements);
        return Inheritance.supertypes(read.type())
                .map(supertype ->
                        new Edge.Supertype(name, models.binaryName(supertype), declarers.contains(supertype)));
    }

    /// The edges to what the requested types wait for: the missing type of
    /// each type that is not read for it, and, of each type that is read,
    /// the requested types its facts mention that wait themselves.
    private static Stream<Edge> awaits(
            SortedMap<String, Reading.Ready> ready,
            SortedMap<String, String> waiting,
            Models models,
            String base,
            Elements elements) {
        if (waiting.isEmpty()) {
            return Stream.empty();
        }
        SortedMap<String, Set<String>> factMentions = ready.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> factMentions(entry.getKey(), entry.getValue(), models, base, elements),
                        (first, second) -> first,
                        TreeMap::new));
        SortedSet<String> held = held(new TreeSet<>(waiting.keySet()), factMentions);
        return Stream.concat(
                waiting.entrySet().stream().map(entry -> new Edge.Awaits(entry.getKey(), entry.getValue())),
                factMentions.entrySet().stream()
                        .filter(entry -> held.contains(entry.getKey()))
                        .flatMap(entry -> entry.getValue().stream()
                                .filter(mentioned -> held.contains(mentioned) && !mentioned.equals(entry.getKey()))
                                .map(mentioned -> new Edge.Awaits(entry.getKey(), mentioned))));
    }

    /// The types that wait: those in `held` already, and every type a fact of which mentions one of
    /// them, and so on until no type is added.
    private static SortedSet<String> held(SortedSet<String> held, Map<String, Set<String>> factMentions) {
        SortedSet<String> grown = Stream.concat(
                        held.stream(),
                        factMentions.entrySet().stream()
                                .filter(entry -> entry.getValue().stream().anyMatch(held::contains))
                                .map(Map.Entry::getKey))
                .collect(Collectors.toCollection(TreeSet::new));
        return grown.size() == held.size() ? held : held(grown, factMentions);
    }

    /// The types whose metamodels the facts of a requested type would refer
    /// to if every type its signatures mention had one: those of the members
    /// that get a fact, not of the ones left out whatever happens — a generic
    /// constructor, a member that mentions a type that is not public.
    private static Set<String> factMentions(
            String name, Reading.Ready read, Models models, String base, Elements elements) {
        Map<String, ClassDesc> assumed = Stream.concat(
                        read.mentions().stream()
                                .flatMap(mention ->
                                        mention instanceof Mention.Available(String mentioned, TypeElement type)
                                                ? Stream.of(Map.entry(mentioned, type))
                                                : Stream.empty()),
                        Stream.of(Map.entry(name, read.type())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> MetamodelNames.metamodel(base, entry.getValue(), elements),
                        (first, second) -> second));
        Map<String, String> unavailable = read.mentions().stream()
                .flatMap(mention -> mention instanceof Mention.Unavailable none ? Stream.of(none) : Stream.empty())
                .collect(Collectors.toMap(Mention.Unavailable::name, Mention.Unavailable::reason));
        return MemberPlan.of(read.type(), models, new Targets(models, assumed, unavailable), Set.of())
                .mentionedTypes();
    }

    /// A requested type and what the round makes of it.
    ///
    /// @param name the binary name
    /// @param request what becomes of the request
    /// @param ready what the type mentions, if it is read
    /// @param unresolved the type it is not read for, if it waits
    private record Asked(String name, Request request, Optional<Reading.Ready> ready, Optional<String> unresolved) {
        static Asked of(String name, Request request) {
            return new Asked(name, request, Optional.empty(), Optional.empty());
        }
    }

    /// A requested type as it is read.
    private sealed interface Reading {

        /// The full metamodel can be generated.
        ///
        /// @param type the type
        /// @param mentions the classes and interfaces the signatures mention, but the type itself, sorted
        record Ready(TypeElement type, List<Mention> mentions) implements Reading {}

        /// The type or a type it mentions is not generated yet.
        ///
        /// @param unresolved the missing type, as javac names it
        record Waiting(String unresolved) implements Reading {}

        /// No metamodel can be made of the type.
        ///
        /// @param reason why, for a diagnostic
        record Unrepresentable(String reason) implements Reading {}

        /// No full metamodel can be made of the type.
        ///
        /// @param reason why, for a diagnostic
        record Rejected(String reason) implements Reading {}
    }

    /// A class or interface a signature mentions.
    private sealed interface Mention {

        /// The binary name of the type.
        String name();

        /// A type a metamodel can be made of.
        record Available(String name, TypeElement type) implements Mention {}

        /// A type no metamodel can be made of, such as an annotation interface: the members that
        /// mention it get no facts.
        record Unavailable(String name, String reason) implements Mention {}
    }
}
