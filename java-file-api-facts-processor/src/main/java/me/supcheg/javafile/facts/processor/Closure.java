package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
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

/// Builds the [TypeGraph] of a round: every type that is needed to
/// generate the types `@Facts` asks for in full (mini-spec §3, Q13).
///
/// A full metamodel is wanted of every requested type, and of every type a
/// requested type extends or implements, directly or through others
/// ([Inheritance]): a full metamodel has the facts of the members its type
/// declares (Q6(b)), so an inherited member is reached through the metamodel
/// of the supertype, which is therefore generated without `@Facts` naming
/// it — `java.lang.Object` like any other. Of those supertypes
///
/// - one that is not `public` has no metamodel ([Node.Hidden]): its `public`
///   members are adopted by its nearest `public` subtypes;
/// - one no full metamodel can be made of for another reason is declined
///   ([Node.Declined]), which does not fail the requested type.
///
/// A type a full metamodel is wanted of, that no earlier round dealt with
/// and that has no full metamodel on the classpath to reuse, is read with
/// the `public` members it declares and adopts. It then has
///
/// - a [Edge.Signature] to every class or interface in the signatures of
///   those members — parameters, results, fields, `throws`, type arguments,
///   the bounds of the type parameters of the type and of its members. A
///   functional interface has a fact of its single abstract method whether
///   it declares the method or inherits it, so the types of that signature
///   are mentioned too. Each mentioned type of which no full metamodel is
///   wanted gets a token-only one, unless one on the classpath is reused;
/// - a [Edge.Awaits] to the type it cannot be read for, if a type it
///   mentions is not generated yet; or, once read, to every type with a
///   full metamodel that a fact of it mentions and that itself awaits a
///   type: a full metamodel refers to the metamodel of such a type, so one
///   that is not there yet must be waited for, or the members that mention
///   it would have no fact for good. Two types that mention each other do
///   not await each other: a metamodel refers to another through its
///   `Data.SHAPE` only (§2.6), so both are written in the same round. Nor
///   does a type await its supertypes: its metamodel does not refer to
///   theirs. Only a type that is missing makes a type wait, and every type
///   that waits is on a way to one.
///
/// Every type of the family — the requested types and their supertypes —
/// has a [Edge.Supertype] to each type it extends or implements directly,
/// one that is not generated yet among them: what such a type extends and
/// implements in turn is not known, so the family is not all there, and nor
/// is it while a class literal of `@Facts` names a type that is not
/// generated yet. The graph tells so ([TypeGraph#complete()]), and nothing
/// is written from it: once the missing type is there, a type this round
/// has as only mentioned, or has not at all, may be a supertype of it, of
/// which a full metamodel is wanted.
///
/// A generic type is rejected if a bound of its type parameters mentions a
/// type the metamodel cannot declare the bound with — one that is not
/// `public`, or that no metamodel can name: without the bound javac would
/// accept a token of a type argument the type does not.
///
/// Along signatures the closure has depth 1: a token-only metamodel needs
/// only the shape of its type, which describes supertypes and methods by
/// descriptors, not by tokens, so neither what a mentioned type mentions nor
/// what it extends is needed. Along supertypes it is transitive.
final class Closure {
    private Closure() {}

    /// The graph of a round.
    ///
    /// @param requested the types `@Facts` asks for, by binary name
    /// @param unresolved the `@Facts` with a class literal of a type that is not generated yet, by the
    ///     name of the type or package each is on
    /// @param done what became of types in earlier rounds, by binary name
    /// @param models the models of the round
    /// @param index the metamodels on the classpath
    /// @param base the base of the mirror packages
    /// @param elements the element utilities of the compilation
    /// @return the graph
    static TypeGraph of(
            Map<String, TypeElement> requested,
            Set<String> unresolved,
            Map<String, Done> done,
            Models models,
            ReuseIndex index,
            String base,
            Elements elements) {
        SortedMap<String, TypeElement> family = requested.values().stream()
                .flatMap(type -> Stream.concat(Stream.of(type), Inheritance.supertypes(type, elements)))
                .collect(Collectors.toMap(
                        models::binaryName, Function.identity(), (first, second) -> first, TreeMap::new));
        List<Kin> kin = family.entrySet().stream()
                .map(entry -> requested.containsKey(entry.getKey())
                        ? ask(entry.getKey(), entry.getValue(), done, models, index)
                        : inherit(entry.getKey(), entry.getValue(), done, models, index))
                .toList();
        SortedMap<String, Reading.Ready> ready = kin.stream()
                .flatMap(type -> type.ready().map(read -> Map.entry(type.name(), read)).stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        SortedMap<String, String> waiting = kin.stream()
                .flatMap(type -> type.unresolved().map(awaited -> Map.entry(type.name(), awaited)).stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        // a type of which a full metamodel is wanted needs no token-only one, and a hidden type has none
        Set<String> untokened = kin.stream()
                .filter(type -> !(type instanceof Kin.Declined))
                .map(Kin::name)
                .collect(Collectors.toSet());
        SortedMap<String, Mention> mentioned = ready.values().stream()
                .flatMap(read -> read.mentions().stream())
                .filter(mention -> !untokened.contains(mention.name()))
                .collect(Collectors.toMap(Mention::name, Function.identity(), (first, second) -> first, TreeMap::new));
        Function<String, Optional<Token>> tokens =
                name -> Optional.ofNullable(mentioned.get(name)).map(mention -> token(mention, done, models, index));
        List<Node> present = Stream.<Stream<? extends Node>>of(
                        kin.stream().map(type -> switch (type) {
                            case Kin.Asked(String name, Request request, var _, var _) ->
                                new Node.Requested(name, request);
                            case Kin.Inherited(String name, Request.Plan plan, var _, var _) ->
                                new Node.Inherited(name, plan);
                            case Kin.Declined(String name, String reason) ->
                                new Node.Declined(name, reason, tokens.apply(name));
                            case Kin.Hidden(String name) -> new Node.Hidden(name);
                        }),
                        mentioned.keySet().stream()
                                .filter(name -> !family.containsKey(name))
                                .map(name -> new Node.Mentioned(
                                        name, tokens.apply(name).orElseThrow())))
                .<Node>flatMap(Function.identity())
                .toList();
        Set<String> names = present.stream().map(Node::name).collect(Collectors.toSet());
        List<Edge.Supertype> missing = family.entrySet().stream()
                .flatMap(entry -> Inheritance.missing(entry.getValue())
                        .map(supertype -> new Edge.Supertype(entry.getKey(), supertype)))
                .toList();
        Stream<Node> absent = Stream.concat(
                        waiting.values().stream(), missing.stream().map(Edge::to))
                .distinct()
                .filter(name -> !names.contains(name))
                .map(Node.Absent::new);
        Stream<Edge> signatures = ready.entrySet().stream()
                .flatMap(entry -> entry.getValue().mentions().stream()
                        .map(mention -> new Edge.Signature(entry.getKey(), mention.name())));
        Stream<Edge> inheritance = family.entrySet().stream()
                .flatMap(entry -> Inheritance.direct(entry.getValue(), elements)
                        .map(supertype -> new Edge.Supertype(entry.getKey(), models.binaryName(supertype))));
        return TypeGraph.of(
                Stream.concat(present.stream(), absent),
                Stream.of(signatures, inheritance, missing.stream(), awaits(ready, waiting, models, base, elements))
                        .flatMap(Function.identity()),
                unresolved.stream());
    }

    /// What becomes of a requested type in this round.
    private static Kin ask(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        return switch (done.get(name)) {
            case Done.Generated(ClassDesc metamodel, boolean full)
            when !full -> Kin.Asked.of(name, new Request.Late(metamodel));
            case Done.Generated settled -> Kin.Asked.of(name, new Request.Settled(settled));
            case Done.Failed settled -> Kin.Asked.of(name, new Request.Settled(settled));
            case Done.Reused(var _, boolean full) when !full -> asked(name, find(type, models, index));
            case Done.Reused settled -> Kin.Asked.of(name, new Request.Settled(settled));
            case null -> asked(name, find(type, models, index));
        };
    }

    private static Kin asked(String name, Found found) {
        return switch (found) {
            case Found.Planned(Request.Plan plan, var ready, var unresolved) ->
                new Kin.Asked(name, plan, ready, unresolved);
            case Found.Unrepresentable(String reason) -> Kin.Asked.of(name, new Request.Unrepresentable(reason));
            case Found.Rejected(String reason) -> Kin.Asked.of(name, new Request.Rejected(reason));
        };
    }

    /// What becomes of a supertype of a requested type that is not requested itself.
    private static Kin inherit(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        if (!MirrorTranslator.isPublic(type)) {
            return new Kin.Hidden(name);
        }
        Optional<String> refusal = MirrorTranslator.refusal(type);
        if (refusal.isPresent()) {
            return new Kin.Declined(name, refusal.get());
        }
        return switch (done.get(name)) {
            case Done.Generated(ClassDesc metamodel, boolean full)
            when !full ->
                new Kin.Declined(
                        name,
                        "its token-only metamodel " + Models.binaryName(metamodel) + " was generated in an earlier"
                                + " round, before a type that extends or implements " + name + " was asked for");
            case Done.Generated settled -> Kin.Inherited.of(name, new Request.Settled(settled));
            case Done.Failed settled -> Kin.Inherited.of(name, new Request.Settled(settled));
            case Done.Reused(var _, boolean full) when !full -> inherited(name, find(type, models, index));
            case Done.Reused settled -> Kin.Inherited.of(name, new Request.Settled(settled));
            case null -> inherited(name, find(type, models, index));
        };
    }

    private static Kin inherited(String name, Found found) {
        return switch (found) {
            case Found.Planned(Request.Plan plan, var ready, var unresolved) ->
                new Kin.Inherited(name, plan, ready, unresolved);
            case Found.Unrepresentable(String reason) -> new Kin.Declined(name, reason);
            case Found.Rejected(String reason) -> new Kin.Declined(name, reason);
        };
    }

    /// What a round finds for the full metamodel of a type no earlier round
    /// dealt with: one on the classpath to reuse, or else the type as it is read.
    private static Found find(TypeElement type, Models models, ReuseIndex index) {
        return switch (index.find(type, ReuseIndex.Completeness.FULL, models)) {
            case ReuseIndex.Lookup.Reusable(ClassDesc metamodel, var _) ->
                new Found.Planned(new Request.OnClasspath(metamodel), Optional.empty(), Optional.empty());
            case ReuseIndex.Lookup.Absent(List<String> stale) ->
                switch (read(type, models)) {
                    case Reading.Ready read ->
                        new Found.Planned(new Request.Ready(type, stale), Optional.of(read), Optional.empty());
                    case Reading.Waiting(String unresolved) ->
                        new Found.Planned(new Request.Waiting(), Optional.empty(), Optional.of(unresolved));
                    case Reading.Unrepresentable(String reason) -> new Found.Unrepresentable(reason);
                    case Reading.Rejected(String reason) -> new Found.Rejected(reason);
                };
        };
    }

    /// Reads a type a full metamodel is wanted of and what its signatures mention.
    private static Reading read(TypeElement requested, Models models) {
        return switch (models.of(requested, MemberFilter.DECLARED_PUBLIC)) {
            case Translation.Deferred<TypeModel>(String unresolved) -> new Reading.Waiting(unresolved);
            case Translation.Unrepresentable<TypeModel>(String reason) -> new Reading.Unrepresentable(reason);
            case Translation.Ok<TypeModel>(TypeModel full) ->
                switch (models.translator().rejection(requested, full)) {
                    case Translation.Ok<Optional<String>>(Optional<String> rejection) ->
                        rejection
                                .<Reading>map(Reading.Rejected::new)
                                .orElseGet(() -> readSignatures(requested, full, models));
                    case Translation.Deferred<Optional<String>>(String unresolved) -> new Reading.Waiting(unresolved);
                    case Translation.Unrepresentable<Optional<String>>(String reason) ->
                        new Reading.Unrepresentable(reason);
                };
        };
    }

    /// Reads what the signatures of a type mention, of which a full metamodel can be made.
    private static Reading readSignatures(TypeElement requested, TypeModel full, Models models) {
        // the sam of a functional interface is a fact even where it is inherited: its signature is mentioned too
        return switch (models.sam(requested)) {
            case Translation.Ok<Optional<SamModel>>(var sam) ->
                read(requested, full, sam.stream().map(SamModel::method).flatMap(Mentions::of), models);
            case Translation.Deferred<Optional<SamModel>>(var unresolved) -> new Reading.Waiting(unresolved);
            case Translation.Unrepresentable<Optional<SamModel>> _ -> read(requested, full, Stream.empty(), models);
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
        return new Reading.Ready(requested, mentions.right());
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

    /// The edges to what the types with full metamodels wait for: the
    /// missing type of each type that is not read for it, and, of each type
    /// that is read, the types with full metamodels its facts mention that
    /// wait themselves.
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

    /// The types whose metamodels the facts of a type would refer
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

    /// A type of the family — a requested type or a supertype of one — and what the round makes of it.
    private sealed interface Kin {

        /// The binary name of the type.
        String name();

        /// What the type mentions, if a full metamodel is wanted of it and it is read.
        default Optional<Reading.Ready> ready() {
            return Optional.empty();
        }

        /// The type it is not read for, if a full metamodel is wanted of it and it waits.
        default Optional<String> unresolved() {
            return Optional.empty();
        }

        /// A type `@Facts` asks for.
        record Asked(String name, Request request, Optional<Reading.Ready> ready, Optional<String> unresolved)
                implements Kin {
            static Asked of(String name, Request request) {
                return new Asked(name, request, Optional.empty(), Optional.empty());
            }
        }

        /// A supertype that gets a full metamodel.
        record Inherited(String name, Request.Plan plan, Optional<Reading.Ready> ready, Optional<String> unresolved)
                implements Kin {
            static Inherited of(String name, Request.Plan plan) {
                return new Inherited(name, plan, Optional.empty(), Optional.empty());
            }
        }

        /// A supertype no full metamodel can be made of, though it is `public`.
        record Declined(String name, String reason) implements Kin {}

        /// A supertype that is not `public`.
        record Hidden(String name) implements Kin {}
    }

    /// What a round finds for the full metamodel of a type.
    private sealed interface Found {

        /// A full metamodel can be made, or is there to reuse.
        ///
        /// @param plan what becomes of it
        /// @param ready what the type mentions, if it is read
        /// @param unresolved the type it is not read for, if it waits
        record Planned(Request.Plan plan, Optional<Reading.Ready> ready, Optional<String> unresolved)
                implements Found {}

        /// No metamodel can be made of the type.
        record Unrepresentable(String reason) implements Found {}

        /// No full metamodel can be made of the type.
        record Rejected(String reason) implements Found {}
    }

    /// A type a full metamodel is wanted of, as it is read.
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
