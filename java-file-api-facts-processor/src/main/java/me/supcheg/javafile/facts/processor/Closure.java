package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.TypeGraph.Edge;
import me.supcheg.javafile.facts.processor.TypeGraph.Node;
import me.supcheg.javafile.facts.processor.TypeGraph.Request;
import me.supcheg.javafile.facts.processor.TypeGraph.Token;
import me.supcheg.javafile.langmodel.mirror.Hierarchy;
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
import java.util.TreeMap;
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
/// generated yet. The graph tells so ([TypeGraph#complete()]): once the
/// missing type is there, a type this round has as only mentioned may be a
/// supertype of it, of which a full metamodel is wanted. So the token-only
/// metamodel of a mentioned type that can be extended or implemented
/// ([Inheritance#open]) is held back in such a graph ([Token.Held]); what
/// nothing can extend gets its token-only metamodel at once, and the full
/// metamodels are written whatever is missing.
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
    /// @param asked whether every class literal of `@Facts` names a type that is there
    /// @param done what became of types in earlier rounds, by binary name
    /// @param models the models of the round
    /// @param index the metamodels on the classpath
    /// @param base the base of the mirror packages
    /// @param elements the element utilities of the compilation
    /// @return the graph
    static TypeGraph of(
            Map<String, TypeElement> requested,
            TypeGraph.Asked asked,
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
                        ? new Kin.Asked(entry.getKey(), find(entry.getKey(), entry.getValue(), done, models, index))
                        : inherit(entry.getKey(), entry.getValue(), done, models, index))
                .toList();
        SortedMap<String, Reading.Ready> ready = kin.stream()
                .flatMap(type -> found(type)
                        .flatMap(found -> found instanceof Found.Read(Reading.Ready read, var _)
                                ? Stream.of(Map.entry(type.name(), read))
                                : Stream.empty()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        SortedMap<String, String> waiting = kin.stream()
                .flatMap(type -> found(type)
                        .flatMap(found -> found instanceof Found.Waiting(String awaited)
                                ? Stream.of(Map.entry(type.name(), awaited))
                                : Stream.empty()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        // a type of which a full metamodel is wanted needs no token-only one, and a hidden type has none
        Set<String> untokened = kin.stream()
                .filter(type -> !(type instanceof Kin.Declined))
                .map(Kin::name)
                .collect(Collectors.toSet());
        List<Edge.Supertype> missing = family.entrySet().stream()
                .flatMap(entry -> Inheritance.missing(entry.getValue())
                        .map(supertype -> new Edge.Supertype(entry.getKey(), supertype)))
                .toList();
        // while a type is missing, a mentioned type it may extend or implement is not known to be only mentioned
        Holding holding =
                asked == TypeGraph.Asked.ALL_THERE && missing.isEmpty() ? Holding.NOTHING : Holding.WHAT_IS_OPEN;
        // the types whose metamodels are owed are mentioned by types no round reads again
        SortedMap<String, Done.Held> owed = done.entrySet().stream()
                .flatMap(entry -> entry.getValue() instanceof Done.Held held
                        ? Stream.of(Map.entry(entry.getKey(), held))
                        : Stream.empty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, TreeMap::new));
        SortedMap<String, Mention> mentioned = Stream.concat(
                        ready.values().stream().flatMap(read -> read.mentions().stream()),
                        owed.keySet().stream().flatMap(name -> mention(name, models).right().stream()))
                .filter(mention -> !untokened.contains(mention.name()))
                .collect(Collectors.toMap(Mention::name, Function.identity(), (first, second) -> first, TreeMap::new));
        Function<String, Optional<Token>> tokens = name ->
                Optional.ofNullable(mentioned.get(name)).map(mention -> token(mention, done, models, index, holding));
        List<Node> present = Stream.<Stream<? extends Node>>of(
                        kin.stream().map(type -> switch (type) {
                            case Kin.Asked(String name, Found found) -> new Node.Requested(name, request(found));
                            case Kin.Inherited(String name, Found.Full found) -> new Node.Inherited(name, plan(found));
                            // what a supertype without a full metamodel is, is known: its token is not held
                            case Kin.Declined(String name, String reason) ->
                                new Node.Declined(
                                        name,
                                        reason,
                                        Optional.ofNullable(mentioned.get(name))
                                                .map(mention -> token(mention, done, models, index, Holding.NOTHING)));
                            case Kin.Hidden(String name) -> new Node.Hidden(name);
                        }),
                        mentioned.keySet().stream()
                                .filter(name -> !family.containsKey(name))
                                .map(name -> new Node.Mentioned(
                                        name, tokens.apply(name).orElseThrow())))
                .<Node>flatMap(Function.identity())
                .toList();
        Set<String> names = present.stream().map(Node::name).collect(Collectors.toSet());
        Stream<Node> absent = Stream.concat(
                        waiting.values().stream(), missing.stream().map(Edge::to))
                .distinct()
                .filter(name -> !names.contains(name))
                .map(Node.Absent::new);
        Stream<Edge> signatures = Stream.concat(
                ready.entrySet().stream()
                        .flatMap(entry -> entry.getValue().mentions().stream()
                                .map(mention -> new Edge.Signature(entry.getKey(), mention.name()))),
                owed.entrySet().stream()
                        .flatMap(entry -> entry.getValue().mentioners().stream()
                                .map(mentioner -> new Edge.Signature(mentioner, entry.getKey()))));
        Stream<Edge> inheritance = family.entrySet().stream()
                .flatMap(entry -> Inheritance.direct(entry.getValue(), elements)
                        .map(supertype -> new Edge.Supertype(entry.getKey(), models.binaryName(supertype))));
        return TypeGraph.of(
                Stream.concat(present.stream(), absent),
                Stream.of(signatures, inheritance, missing.stream(), awaits(ready, waiting, models, base, elements))
                        .flatMap(Function.identity()),
                asked);
    }

    /// What becomes of a supertype of a requested type that is not requested itself.
    private static Kin inherit(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        if (!MirrorTranslator.isPublic(type)) {
            return new Kin.Hidden(name);
        }
        return MirrorTranslator.refusal(type)
                .<Kin>map(refusal -> new Kin.Declined(name, refusal))
                .orElseGet(() -> switch (find(name, type, done, models, index)) {
                    case Found.Full full -> new Kin.Inherited(name, full);
                    case Found.Late(ClassDesc tokenOnly) ->
                        new Kin.Declined(
                                name,
                                "its token-only metamodel " + Models.binaryName(tokenOnly) + " was generated in an"
                                        + " earlier round, before a type that extends or implements " + name
                                        + " was asked for");
                    case Found.Unrepresentable(String reason) -> new Kin.Declined(name, reason);
                    case Found.Rejected(String reason) -> new Kin.Declined(name, reason);
                });
    }

    /// What a round finds for the full metamodel wanted of a type: what an
    /// earlier round made of it, or else one on the classpath to reuse, or
    /// else the type as it is read.
    private static Found find(String name, TypeElement type, Map<String, Done> done, Models models, ReuseIndex index) {
        return switch (done.get(name)) {
            case Done.GeneratedToken(ClassDesc metamodel) -> new Found.Late(metamodel);
            case Done.GeneratedFull settled -> new Found.Settled(settled);
            case Done.ReusedFull settled -> new Found.Settled(settled);
            case Done.Failed settled -> new Found.Settled(settled);
            // neither is a full metamodel: one is looked for as if no round had dealt with the type
            case Done.ReusedToken _, Done.Held _ -> look(type, models, index);
            case null -> look(type, models, index);
        };
    }

    private static Found look(TypeElement type, Models models, ReuseIndex index) {
        return switch (index.find(type, ReuseIndex.Completeness.FULL, models)) {
            case ReuseIndex.Lookup.Reusable(ClassDesc metamodel, var _) -> new Found.Reusable(metamodel);
            case ReuseIndex.Lookup.Absent(List<String> stale) ->
                switch (read(type, models)) {
                    case Reading.Ready read -> new Found.Read(read, stale);
                    case Reading.Waiting(String unresolved) -> new Found.Waiting(unresolved);
                    case Reading.Unrepresentable(String reason) -> new Found.Unrepresentable(reason);
                    case Reading.Rejected(String reason) -> new Found.Rejected(reason);
                };
        };
    }

    /// What the round finds for the full metamodel of a type of the family, if one is wanted of it.
    private static Stream<Found> found(Kin kin) {
        return switch (kin) {
            case Kin.Asked(var _, Found found) -> Stream.of(found);
            case Kin.Inherited(var _, Found.Full found) -> Stream.of(found);
            case Kin.Declined _, Kin.Hidden _ -> Stream.empty();
        };
    }

    /// What becomes of a request, as the graph tells it.
    private static Request request(Found found) {
        return switch (found) {
            case Found.Full full -> plan(full);
            case Found.Late(ClassDesc tokenOnly) -> new Request.Late(tokenOnly);
            case Found.Unrepresentable(String reason) -> new Request.Unrepresentable(reason);
            case Found.Rejected(String reason) -> new Request.Rejected(reason);
        };
    }

    /// What becomes of a full metamodel that can be made, as the graph tells it.
    private static Request.Plan plan(Found.Full found) {
        return switch (found) {
            case Found.Settled(Done done) -> new Request.Settled(done);
            case Found.Reusable(ClassDesc metamodel) -> new Request.OnClasspath(metamodel);
            case Found.Read(Reading.Ready read, List<String> stale) -> new Request.Ready(read.type(), stale);
            case Found.Waiting _ -> new Request.Waiting();
        };
    }

    /// Reads a type a full metamodel is wanted of and what its signatures mention.
    private static Reading read(TypeElement requested, Models models) {
        return switch (models.of(requested, MemberFilter.DECLARED_ACCESSIBLE)) {
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
    private static Token token(
            Mention mention, Map<String, Done> done, Models models, ReuseIndex index, Holding holding) {
        return switch (mention) {
            case Mention.Unavailable(var _, String reason) -> new Token.Unavailable(reason);
            case Mention.Available(String name, TypeElement type) ->
                Optional.ofNullable(done.get(name))
                        .filter(settled -> !(settled instanceof Done.Held))
                        .<Token>map(Token.Settled::new)
                        .orElseGet(() -> switch (index.find(type, ReuseIndex.Completeness.TOKEN, models)) {
                            case ReuseIndex.Lookup.Reusable(
                                    ClassDesc metamodel,
                                    ReuseIndex.Completeness completeness) ->
                                new Token.OnClasspath(metamodel, completeness);
                            case ReuseIndex.Lookup.Absent(List<String> stale) ->
                                holding == Holding.WHAT_IS_OPEN && Inheritance.open(type)
                                        ? new Token.Held(type, stale)
                                        : new Token.Planned(type, stale);
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
        // the types that wait: those that are not read, and every type a fact of which mentions one that waits
        Set<String> held = Stream.concat(
                        waiting.keySet().stream(),
                        Hierarchy.beyond(
                                List.copyOf(waiting.keySet()),
                                awaited -> factMentions.entrySet().stream()
                                        .filter(entry -> entry.getValue().contains(awaited))
                                        .map(Map.Entry::getKey)))
                .collect(Collectors.toSet());
        return Stream.concat(
                waiting.entrySet().stream().map(entry -> new Edge.Awaits(entry.getKey(), entry.getValue())),
                factMentions.entrySet().stream()
                        .filter(entry -> held.contains(entry.getKey()))
                        .flatMap(entry -> entry.getValue().stream()
                                .filter(mentioned -> held.contains(mentioned) && !mentioned.equals(entry.getKey()))
                                .map(mentioned -> new Edge.Awaits(entry.getKey(), mentioned))));
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

    /// Which token-only metamodels a round holds back.
    private enum Holding {
        /// None: the graph is complete.
        NOTHING,
        /// Those of the types a type that is missing may extend or implement.
        WHAT_IS_OPEN
    }

    /// A type of the family — a requested type or a supertype of one — and what the round makes of it.
    private sealed interface Kin {

        /// The binary name of the type.
        String name();

        /// A type `@Facts` asks for.
        ///
        /// @param found what the round finds for its full metamodel
        record Asked(String name, Found found) implements Kin {}

        /// A supertype that gets a full metamodel.
        ///
        /// @param found what the round finds for its full metamodel
        record Inherited(String name, Found.Full found) implements Kin {}

        /// A supertype no full metamodel can be made of, though it is `public`.
        record Declined(String name, String reason) implements Kin {}

        /// A supertype that is not `public`.
        record Hidden(String name) implements Kin {}
    }

    /// What a round finds for the full metamodel wanted of a type.
    private sealed interface Found {

        /// A full metamodel is there, is reused, or can be written once the type is read.
        sealed interface Full extends Found {}

        /// An earlier round dealt with the type.
        record Settled(Done done) implements Full {}

        /// A full metamodel on the classpath matches the type.
        record Reusable(ClassDesc metamodel) implements Full {}

        /// The type is read: its full metamodel can be written.
        ///
        /// @param read the type and what its signatures mention
        /// @param stale why the metamodels of the type on the classpath are not reused
        record Read(Reading.Ready read, List<String> stale) implements Full {}

        /// The type or a type it mentions is not generated yet.
        ///
        /// @param unresolved the missing type, as javac names it
        record Waiting(String unresolved) implements Full {}

        /// An earlier round generated a token-only metamodel of the type.
        record Late(ClassDesc tokenOnly) implements Found {}

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
