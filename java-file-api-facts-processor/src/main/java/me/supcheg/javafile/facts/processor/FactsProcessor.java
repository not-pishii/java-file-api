package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
import me.supcheg.javafile.facts.processor.MetamodelDocs.About;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.routine.Pair;
import org.jspecify.annotations.Nullable;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.constant.ClassDesc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The `@Facts` processor: generates the metamodels of the types
/// `@Facts` asks for and of everything they need (§5 of the main spec, the
/// facts-processor mini-spec, Q13): full metamodels of the requested types
/// and of the types they extend and implement, and token-only metamodels of
/// the types the signatures of those mention.
///
/// It is an aggregating processor: what it writes depends on every
/// `@Facts` of the compilation — one metamodel per type, one base package —
/// and on the classpath, whose metamodels it reuses.
///
/// In every round it
///
/// 1. reads the `@Facts` of the round, and again, by name, those with a
///    class literal of a type that did not exist yet (§8);
/// 2. builds the graph of the types of the round ([Closure], [TypeGraph]):
///    what `@Facts` asks for, what those types extend and implement, what
///    the signatures mention, what is not generated yet;
/// 3. reads from the graph what to do: which types have a metamodel on the
///    classpath to reuse, which have none and why, which full and
///    token-only metamodels to write, which wait for a later round, and
///    which token-only ones are held back ([TypeGraph.Token.Held]);
/// 4. writes the metamodels and lists them in the resource index
///    ([ReuseIndex]).
///
/// While `@Facts` asks for a type that is not generated yet, or a type
/// extends or implements one, the graph is not complete
/// ([TypeGraph#complete()]): the missing type may extend or implement a type
/// that is only mentioned so far, of which a full metamodel is then wanted,
/// and a metamodel is written once. What such a round writes is what cannot
/// change: the full metamodels, and the token-only ones of the types
/// nothing can extend. The token-only metamodel of a type that can be
/// extended or implemented is held back until the graph is complete; the
/// metamodels written meanwhile refer to it by the name it will have, in
/// initializers only, which javac resolves once every round is over.
///
/// The last round reads the graph once more and reports what never became
/// ready, with what each type waited for. If the graph is still not
/// complete, no round is left to wait for, so the token-only metamodels
/// held back are written there as they are: javac takes a file of the last
/// round with a warning and does not find it through the `import` of a file
/// of an earlier round, but the metamodels refer to each other by qualified
/// name, so a compiler that goes on after the error of the missing type —
/// `-XDshould-stop.ifError=FLOW` — has every metamodel the others name.
///
/// So a type is known for what it is — asked for, a supertype, only
/// mentioned — before its metamodel is written, as far as the `@Facts` of
/// the compilation go. Only a `@Facts` another processor generates, in a
/// round after the metamodels were written, can ask for more of a type than
/// its metamodel has: a type that has a token-only metamodel by then gets
/// no full one ([TypeGraph.Request.Late], [TypeGraph.Node.Declined]).
///
/// Between rounds it keeps where `@Facts` is and what it asks for, and what
/// became of each type ([Done]) — a metamodel is written once. Everything
/// else is read anew from the graph of each round.
///
/// A type gets a full metamodel, with a fact per member ([MemberPlan]) — in
/// terms of its type parameters if it is generic —, whether `@Facts` asks
/// for it or a requested type inherits from it. What is told of a type
/// `@Facts` does not name says which request it is there for, and is
/// reported on that `@Facts`. A member without a fact is an error under
/// `strict` only in a type `@Facts` asks for, which the reasons the type is
/// in the graph for tell ([TypeGraph#reasons]): of a supertype nobody
/// asked for it stays a warning.
///
/// What the processor knows of a metamodel and its reader does not, the
/// comment of the metamodel class tells (Q14, [MetamodelDocs]): why it is
/// full or token-only — the same reasons —, where the facts of the inherited
/// members are, and which members have no fact, in the words of the
/// warnings. The comment of a fact links to the member the fact is of.
///
/// Options: see [Options].
public final class FactsProcessor extends AbstractProcessor {
    private static final String FORMAT = "me.supcheg.javafile.facts.meta.MetamodelFormat";
    private static final String GENERATED_METAMODEL = "me.supcheg.javafile.facts.meta.GeneratedMetamodel";
    private static final String UNRESOLVED = "a type in @Facts is not resolvable after all rounds";

    private @Nullable Diagnostics diagnostics;
    private @Nullable Options options;
    private boolean stopped;

    private final SortedSet<Site> sites = new TreeSet<>();
    private final SortedSet<Site> unresolvedSites = new TreeSet<>();
    private final SortedMap<String, SortedSet<Site>> requested = new TreeMap<>();
    private final Map<String, Done> done = new HashMap<>();

    /// Creates the processor; javac finds it through the service loader.
    public FactsProcessor() {}

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Requests.FACTS, GENERATED_METAMODEL);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Set.of(Options.PACKAGE, Options.STRICT, Options.INDEX);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        switch (Options.parse(processingEnv.getOptions())) {
            case Options.Parsed.Valid(Options valid) -> {
                options = valid;
                diagnostics = new Diagnostics(processingEnv.getMessager(), valid.strict());
            }
            case Options.Parsed.Invalid(List<String> errors) -> {
                Diagnostics report = new Diagnostics(processingEnv.getMessager(), false);
                errors.forEach(report::error);
                stopped = true;
            }
        }
    }

    /// Claims the annotations the processor supports, which are its own:
    /// `@Facts`, which it reads, and `@GeneratedMetamodel`, which only the
    /// metamodels it writes have. An annotation nobody claims is a warning
    /// of `-Xlint:processing`, an error under `-Werror`. Every other
    /// annotation of the round goes on to the processors after this one —
    /// `javax.annotation.processing.Generated` of the metamodels among
    /// them, which is not this processor's to claim, so that lint still
    /// names it.
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
        run(round);
        return true;
    }

    private void run(RoundEnvironment round) {
        if (stopped || options == null || diagnostics == null) {
            return;
        }
        Elements elements = processingEnv.getElementUtils();
        TypeElement facts = elements.getTypeElement(Requests.FACTS);
        List<Element> annotated = facts == null ? List.of() : List.copyOf(round.getElementsAnnotatedWith(facts));
        if (sites.isEmpty() && !annotated.isEmpty() && !formatMatches(elements, diagnostics)) {
            stopped = true;
            return;
        }
        Map<Site, Element> fresh = annotated.stream()
                .collect(Collectors.toMap(
                        element -> Site.of(element, elements), Function.identity(), (first, second) -> first));
        sites.addAll(fresh.keySet());
        // the `@Facts` of this round, and again those of the earlier rounds that named a type not there yet
        Stream.concat(
                        annotated.stream().map(element -> Pair.pair(Site.of(element, elements), element)),
                        List.copyOf(unresolvedSites).stream()
                                .filter(site -> !fresh.containsKey(site))
                                .flatMap(site ->
                                        site.resolve(elements).stream().map(element -> Pair.pair(site, element))))
                .forEach(read -> record(read.left(), Requests.read(read.right(), diagnostics)));
        Round when = round.processingOver() ? Round.LAST : Round.NOT_LAST;
        if (!requested.isEmpty()) {
            switch (BasePackage.of(
                    options, sites.stream().map(Site::packageName).collect(Collectors.toCollection(TreeSet::new)))) {
                case BasePackage.Chosen(String base) -> generate(base, options.index(), when, elements, diagnostics);
                case BasePackage.Ambiguous ambiguous -> {
                    diagnostics.error(sites.first().resolve(elements), ambiguous.message());
                    stopped = true;
                    return;
                }
            }
        }
        if (when == Round.LAST) {
            unresolvedSites.forEach(site -> site.resolve(elements)
                    .ifPresentOrElse(
                            element -> Requests.unresolved(element)
                                    .forEach(literal -> diagnostics.error(
                                            element, literal.annotation(), literal.value(), UNRESOLVED)),
                            () -> diagnostics.error(UNRESOLVED)));
        }
    }

    /// Remembers what one `@Facts` asks for, and whether it is all there.
    private void record(Site site, Requests.Reading reading) {
        reading.types()
                .forEach(type -> requested
                        .computeIfAbsent(type.getQualifiedName().toString(), _ -> new TreeSet<>())
                        .add(site));
        if (reading.unresolved()) {
            unresolvedSites.add(site);
        } else {
            unresolvedSites.remove(site);
        }
    }

    private static boolean formatMatches(Elements elements, Diagnostics diagnostics) {
        TypeElement format = elements.getTypeElement(FORMAT);
        Optional<Object> version = format == null
                ? Optional.empty()
                : ElementFilter.fieldsIn(format.getEnclosedElements()).stream()
                        .filter(field -> field.getSimpleName().contentEquals("VERSION"))
                        .findFirst()
                        .map(VariableElement::getConstantValue);
        if (version.equals(Optional.of(MetamodelFormat.VERSION))) {
            return true;
        }
        diagnostics.error("the @Facts processor generates metamodel format " + MetamodelFormat.VERSION + ", but "
                + (format == null
                        ? FORMAT + " is not on the classpath"
                        : "java-file-api-facts on the classpath has format " + version.orElse("none"))
                + "; use java-file-api-facts of the same version as the processor");
        return false;
    }

    private void generate(
            String base, Options.Index publishing, Round when, Elements elements, Diagnostics diagnostics) {
        Models models = new Models(elements, processingEnv.getTypeUtils());
        ReuseIndex index = new ReuseIndex(processingEnv.getFiler(), elements, publishing);
        SortedMap<String, Asked> asked = requested.entrySet().stream()
                .flatMap(entry -> Optional.ofNullable(elements.getTypeElement(entry.getKey()))
                        .map(type -> new Asked(models.binaryName(type), type, entry.getValue()))
                        .stream())
                .collect(Collectors.toMap(Asked::name, Function.identity(), (first, second) -> first, TreeMap::new));
        TypeGraph graph = Closure.of(
                asked.values().stream().collect(Collectors.toMap(Asked::name, Asked::type)),
                unresolvedSites.isEmpty() ? TypeGraph.Asked.ALL_THERE : TypeGraph.Asked.SOME_MISSING,
                Map.copyOf(done),
                models,
                index,
                base,
                elements);
        // the `@Facts` a type is there for: those that ask for it, or else for the types that extend it
        Function<String, SortedSet<Site>> sitesOf = name -> Optional.ofNullable(asked.get(name))
                .map(Asked::sites)
                .orElseGet(() -> graph.roots(name)
                        .flatMap(root -> asked.get(root).sites().stream())
                        .collect(Collectors.toCollection(TreeSet::new)));
        // a requested type the round deals with for the first time: one that is settled or written now
        Predicate<String> dealtWith =
                name -> graph.nodes().get(name).request().stream().anyMatch(request -> switch (request) {
                    case TypeGraph.Request.Settled _, TypeGraph.Request.Waiting _ -> false;
                    case TypeGraph.Request.Ready _ -> !graph.waits(name);
                    case TypeGraph.Request.OnClasspath _,
                            TypeGraph.Request.Late _,
                            TypeGraph.Request.Unrepresentable _,
                            TypeGraph.Request.Rejected _ -> true;
                });
        // what the round settles without writing, and what it has to tell of it, type by type
        List<Settlement> settlements = graph.nodes().values().stream()
                .flatMap(node -> settlement(node).stream())
                .toList();
        settlements.forEach(settlement -> done.put(settlement.name(), settlement.done()));
        graph.nodes().values().stream()
                .flatMap(node -> Stream.concat(
                        settlement(node)
                                .flatMap(settlement -> settlement instanceof Settlement.Refused(var _, String error)
                                        ? Optional.<Diagnostics.Message>of(new Diagnostics.Message.Error(
                                                first(sitesOf.apply(node.name()), elements), error))
                                        : Optional.empty())
                                .stream(),
                        declined(node, graph, dealtWith, asked, elements).stream()))
                .forEach(diagnostics::report);
        // what is held back is owed to the metamodels this round and the earlier ones wrote
        graph.held()
                .forEach(name -> done.put(
                        name, new Done.Held(graph.mentioners(name).collect(Collectors.toCollection(TreeSet::new)))));
        Targets targets = new Targets(models, metamodels(graph, base, elements), unavailable(graph));
        List<Planned> planned =
                planned(graph, sitesOf, when, base, elements, models).toList();
        // a metamodel is of the first type that claims its name, among those written before and now
        Map<ClassDesc, String> owners = Stream.concat(
                        done.entrySet().stream().flatMap(entry -> switch (entry.getValue()) {
                            case Done.GeneratedFull(ClassDesc metamodel) ->
                                Stream.of(Map.entry(metamodel, entry.getKey()));
                            case Done.GeneratedToken(ClassDesc metamodel) ->
                                Stream.of(Map.entry(metamodel, entry.getKey()));
                            case Done.ReusedFull _, Done.ReusedToken _, Done.Failed _, Done.Held _ ->
                                Stream.<Map.Entry<ClassDesc, String>>empty();
                        }),
                        planned.stream().map(metamodel -> Map.entry(metamodel.metamodel(), metamodel.binaryName())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first));
        planned.forEach(metamodel -> done.put(
                metamodel.binaryName(),
                write(metamodel, owners.get(metamodel.metamodel()), models, targets, index, elements, diagnostics)));
        if (when == Round.LAST) {
            graph.nodes().values().stream()
                    .filter(node -> node.request().isPresent())
                    .flatMap(node -> graph.waitOf(node.name()).stream()
                            .map(wait -> new Diagnostics.Message.Error(
                                    first(sitesOf.apply(node.name()), elements),
                                    "type " + subject(graph, node.name()) + " in @Facts is not resolvable after all"
                                            + " rounds: it "
                                            + describe(wait, name -> elements.getTypeElement(name) != null))))
                    .forEach(diagnostics::report);
        }
    }

    /// What is told of a supertype no full metamodel can be made of: once to each requested type that
    /// inherits from it, in the round that deals with that type.
    private static Optional<Diagnostics.Message> declined(
            TypeGraph.Node node,
            TypeGraph graph,
            Predicate<String> dealtWith,
            SortedMap<String, Asked> asked,
            Elements elements) {
        if (!(node instanceof TypeGraph.Node.Declined(String name, String reason, var _))) {
            return Optional.empty();
        }
        SortedSet<String> told = graph.roots(name).filter(dealtWith).collect(Collectors.toCollection(TreeSet::new));
        return told.isEmpty()
                ? Optional.empty()
                : Optional.of(new Diagnostics.Message.Warning(
                        told.stream()
                                .flatMap(root -> asked.get(root).sites().stream())
                                .sorted()
                                .findFirst()
                                .flatMap(site -> site.resolve(elements)),
                        String.join(", ", told) + ": no facts of the public members inherited from " + name
                                + ", which has no full metamodel: " + reason));
    }

    /// A type as a diagnostic names it: by its binary name if `@Facts` asks
    /// for it, and with the requests it is there for if it is a supertype
    /// of what `@Facts` asks for — `java.lang.Object (a supertype of p.X)`.
    private static String subject(TypeGraph graph, String name) {
        return graph.nodes().get(name) instanceof TypeGraph.Node.Requested
                ? name
                : name + " (a supertype of " + graph.roots(name).collect(Collectors.joining(", ")) + ")";
    }

    /// What a round settles of a type without writing a metamodel: a
    /// metamodel on the classpath to reuse, or no metamodel at all, with the
    /// error to report.
    private static Optional<Settlement> settlement(TypeGraph.Node node) {
        String name = node.name();
        return node.request()
                .flatMap(request -> switch (request) {
                    case TypeGraph.Request.Late(ClassDesc tokenOnly) ->
                        Optional.<Settlement>of(new Settlement.Refused(
                                name,
                                name + " is requested after its token-only metamodel " + Models.binaryName(tokenOnly)
                                        + " was generated in an earlier round; request it with the types whose"
                                        + " signatures mention it"));
                    case TypeGraph.Request.Unrepresentable(String reason) ->
                        Optional.of(new Settlement.Refused(name, "no metamodel of " + name + ": " + reason));
                    case TypeGraph.Request.Rejected(String reason) ->
                        Optional.of(new Settlement.Refused(name, "no metamodel of " + name + ": " + reason));
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) ->
                        Optional.of(new Settlement.Reused(name, new Done.ReusedFull(metamodel)));
                    case TypeGraph.Request.Settled _, TypeGraph.Request.Ready _, TypeGraph.Request.Waiting _ ->
                        Optional.empty();
                })
                .or(() -> node.token()
                        .flatMap(token -> token
                                        instanceof
                                        TypeGraph.Token.OnClasspath(
                                                ClassDesc metamodel,
                                                ReuseIndex.Completeness completeness)
                                ? Optional.of(new Settlement.Reused(name, Done.reused(metamodel, completeness)))
                                : Optional.empty()));
    }

    /// The metamodel of every type of a round that has one: written already, reused, or to be
    /// written — by this round, or, for a type that waits, by a later one.
    private static Map<String, ClassDesc> metamodels(TypeGraph graph, String base, Elements elements) {
        return graph.nodes().values().stream()
                .flatMap(node ->
                        metamodel(node, base, elements).map(metamodel -> Map.entry(node.name(), metamodel)).stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Optional<ClassDesc> metamodel(TypeGraph.Node node, String base, Elements elements) {
        return node.request()
                .flatMap(request -> switch (request) {
                    case TypeGraph.Request.Settled(Done settled) -> metamodel(settled);
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) -> Optional.of(metamodel);
                    case TypeGraph.Request.Ready(TypeElement type, var _) ->
                        Optional.of(MetamodelNames.metamodel(base, type, elements));
                    case TypeGraph.Request.Late _,
                            TypeGraph.Request.Waiting _,
                            TypeGraph.Request.Unrepresentable _,
                            TypeGraph.Request.Rejected _ -> Optional.<ClassDesc>empty();
                })
                .or(() -> node.token().flatMap(token -> switch (token) {
                    case TypeGraph.Token.Settled(Done settled) -> metamodel(settled);
                    case TypeGraph.Token.OnClasspath(ClassDesc metamodel, var _) -> Optional.of(metamodel);
                    case TypeGraph.Token.Planned(TypeElement type, var _) ->
                        Optional.of(MetamodelNames.metamodel(base, type, elements));
                    case TypeGraph.Token.Held(TypeElement type, var _) ->
                        Optional.of(MetamodelNames.metamodel(base, type, elements));
                    case TypeGraph.Token.Unavailable _ -> Optional.<ClassDesc>empty();
                }));
    }

    private static Optional<ClassDesc> metamodel(Done done) {
        return switch (done) {
            case Done.GeneratedFull(ClassDesc metamodel) -> Optional.of(metamodel);
            case Done.GeneratedToken(ClassDesc metamodel) -> Optional.of(metamodel);
            case Done.ReusedFull(ClassDesc metamodel) -> Optional.of(metamodel);
            case Done.ReusedToken(ClassDesc metamodel) -> Optional.of(metamodel);
            case Done.Failed _, Done.Held _ -> Optional.empty();
        };
    }

    /// Why the types of a round no metamodel can be made of have none, by binary name.
    private static Map<String, String> unavailable(TypeGraph graph) {
        return graph.nodes().values().stream()
                .flatMap(node -> Stream.concat(
                                node
                                        .request()
                                        .flatMap(request ->
                                                request instanceof TypeGraph.Request.Unrepresentable(String reason)
                                                        ? Optional.of(reason)
                                                        : Optional.empty())
                                        .stream(),
                                node
                                        .token()
                                        .flatMap(token -> token instanceof TypeGraph.Token.Unavailable(String reason)
                                                ? Optional.of(reason)
                                                : Optional.empty())
                                        .stream())
                        .map(reason -> Map.entry(node.name(), reason)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /// The metamodels a round writes: the full ones of the requested types
    /// and of their supertypes that are read and await nothing, then the
    /// token-only ones of the types their signatures mention — but those
    /// held back, unless the round is the last. Each is written for the
    /// `@Facts` that ask for the types it is there for.
    private static Stream<Planned> planned(
            TypeGraph graph,
            Function<String, SortedSet<Site>> sitesOf,
            Round when,
            String base,
            Elements elements,
            Models models) {
        return Stream.concat(
                graph.nodes().values().stream()
                        .flatMap(node -> node.request().stream()
                                .flatMap(request ->
                                        request instanceof TypeGraph.Request.Ready(TypeElement type, List<String> stale)
                                                        && !graph.waits(node.name())
                                                ? Stream.of(new Planned(
                                                        type,
                                                        node.name(),
                                                        MetamodelNames.metamodel(base, type, elements),
                                                        ReuseIndex.Completeness.FULL,
                                                        sitesOf.apply(node.name()),
                                                        stale,
                                                        subject(graph, node.name()),
                                                        new About.Full(
                                                                graph.reasons(node.name())
                                                                        .toList(),
                                                                supertypes(graph, node.name(), base, elements, models),
                                                                protectedMembers(type))))
                                                : Stream.empty())),
                graph.nodes().values().stream()
                        .flatMap(node -> node.token().stream()
                                .flatMap(token -> switch (token) {
                                    case TypeGraph.Token.Planned(TypeElement type, List<String> stale) ->
                                        Stream.of(Pair.pair(type, stale));
                                    // no round is left in which the missing type may come
                                    case TypeGraph.Token.Held(TypeElement type, List<String> stale) ->
                                        switch (when) {
                                            case LAST -> Stream.of(Pair.pair(type, stale));
                                            case NOT_LAST -> Stream.<Pair<TypeElement, List<String>>>empty();
                                        };
                                    case TypeGraph.Token.Settled _,
                                            TypeGraph.Token.OnClasspath _,
                                            TypeGraph.Token.Unavailable _ ->
                                        Stream.<Pair<TypeElement, List<String>>>empty();
                                })
                                .map(tokenOnly -> new Planned(
                                        tokenOnly.left(),
                                        node.name(),
                                        MetamodelNames.metamodel(base, tokenOnly.left(), elements),
                                        ReuseIndex.Completeness.TOKEN,
                                        graph.mentioners(node.name())
                                                .flatMap(mentioner -> sitesOf.apply(mentioner).stream())
                                                .collect(Collectors.toCollection(TreeSet::new)),
                                        tokenOnly.right(),
                                        node.name(),
                                        node instanceof TypeGraph.Node.Declined(var _, String reason, var _)
                                                ? new About.Declined(
                                                        graph.reasons(node.name())
                                                                .toList(),
                                                        reason)
                                                : new About.Mentioned(graph.reasons(node.name())
                                                        .toList())))));
    }

    /// The `public` supertypes nearest to a type — those it extends and
    /// implements itself, and those a supertype that is not `public` does
    /// in its place — with what the round has for a full metamodel of each,
    /// sorted by name: what the comment of the metamodel of the type tells of
    /// the members it inherits.
    private static List<About.Supertype> supertypes(
            TypeGraph graph, String name, String base, Elements elements, Models models) {
        return nearestPublic(graph, name)
                .distinct()
                .sorted()
                .map(supertype -> supertype(graph.nodes().get(supertype), base, elements, models))
                .toList();
    }

    private static Stream<String> nearestPublic(TypeGraph graph, String name) {
        return graph.from(name, TypeGraph.Edge.Supertype.class)
                .map(TypeGraph.Edge::to)
                .flatMap(supertype -> graph.nodes().get(supertype) instanceof TypeGraph.Node.Hidden
                        ? nearestPublic(graph, supertype)
                        : Stream.of(supertype));
    }

    private static About.Supertype supertype(TypeGraph.Node node, String base, Elements elements, Models models) {
        String name = node.name();
        if (node instanceof TypeGraph.Node.Declined(var _, String reason, var _)) {
            return new About.Supertype.None(name, reason);
        }
        return node.request()
                .map(request -> switch (request) {
                    case TypeGraph.Request.Settled(Done.GeneratedFull(ClassDesc metamodel)) ->
                        new About.Supertype.Full(metamodel);
                    case TypeGraph.Request.Settled(Done.ReusedFull(ClassDesc metamodel)) ->
                        new About.Supertype.Full(metamodel);
                    case TypeGraph.Request.Settled _ ->
                        new About.Supertype.None(name, "no full metamodel of " + name + " was generated");
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) -> new About.Supertype.Full(metamodel);
                    case TypeGraph.Request.Ready(TypeElement type, var _) ->
                        new About.Supertype.Full(MetamodelNames.metamodel(base, type, elements));
                    // a later round writes it, under the name it has now
                    case TypeGraph.Request.Waiting _ ->
                        models.element(ClassDesc.of(name))
                                .<About.Supertype>map(type ->
                                        new About.Supertype.Full(MetamodelNames.metamodel(base, type, elements)))
                                .orElseGet(() -> new About.Supertype.None(name, name + " is not generated yet"));
                    case TypeGraph.Request.Late _ ->
                        new About.Supertype.None(
                                name, "the token-only metamodel of " + name + " was generated in an earlier round");
                    case TypeGraph.Request.Unrepresentable(String reason) -> new About.Supertype.None(name, reason);
                    case TypeGraph.Request.Rejected(String reason) -> new About.Supertype.None(name, reason);
                })
                .orElseGet(() -> new About.Supertype.None(name, name + " has no full metamodel"));
    }

    /// Whether a type declares `protected` fields, constructors or methods: they have no facts (Q3).
    private static About.Protected protectedMembers(TypeElement type) {
        return type.getEnclosedElements().stream()
                        .anyMatch(member ->
                                member.getModifiers().contains(Modifier.PROTECTED) && !(member instanceof TypeElement))
                ? About.Protected.SOME
                : About.Protected.NONE;
    }

    /// Writes a metamodel.
    ///
    /// @param owner the type the name of the metamodel belongs to: the first that claimed it
    /// @return what became of the type
    private Done write(
            Planned planned,
            String owner,
            Models models,
            Targets targets,
            ReuseIndex index,
            Elements elements,
            Diagnostics diagnostics) {
        Optional<? extends Element> at = first(planned.requesters(), elements);
        ClassDesc metamodel = planned.metamodel();
        String name = Models.binaryName(metamodel);
        if (!owner.equals(planned.binaryName())) {
            diagnostics.error(
                    at, "the metamodels of " + owner + " and " + planned.binaryName() + " would both be " + name);
            return new Done.Failed();
        }
        if (elements.getTypeElement(name) != null) {
            diagnostics.error(
                    at,
                    "metamodel " + name + " of " + planned.subject() + " already exists in a dependency; reuse it"
                            + " (it does not match " + planned.binaryName() + " on this classpath) or choose another"
                            + " package with -A" + Options.PACKAGE + "=<package>");
            return new Done.Failed();
        }
        planned.stale().forEach(reason -> diagnostics.warning(at, reason + "; generating " + name));
        MemberFilter filter =
                switch (planned.completeness()) {
                    case FULL -> MemberFilter.DECLARED_PUBLIC;
                    case TOKEN -> MemberFilter.NONE;
                };
        TypeModel model =
                switch (models.of(planned.type(), filter)) {
                    case Translation.Ok<TypeModel>(TypeModel value) -> value;
                    case Translation.Deferred<TypeModel> _ ->
                        throw new IllegalStateException(planned.binaryName() + " was read, but is deferred now");
                    case Translation.Unrepresentable<TypeModel> _ ->
                        throw new IllegalStateException(planned.binaryName() + " was read, but is unrepresentable now");
                };
        Element[] originating = planned.requesters().stream()
                .map(site -> site.resolve(elements))
                .flatMap(Optional::stream)
                .toArray(Element[]::new);
        Canonical canonical = Canonical.of(model);
        JavaFile file =
                switch (planned.about()) {
                    case About.Full about -> {
                        Set<String> taken = MetamodelEmitter.takenNames(
                                metamodel,
                                model,
                                canonical,
                                MemberPlan.probe(planned.type(), models, targets),
                                targets,
                                about);
                        MemberPlan plan = MemberPlan.of(planned.type(), models, targets, taken);
                        plan.skipped()
                                .forEach(skip -> diagnostics.skipped(
                                        at,
                                        planned.subject() + ": no fact of " + skip.told(),
                                        about.reasons(),
                                        skip.origin()));
                        yield MetamodelEmitter.full(metamodel, model, canonical, plan, targets, taken, about);
                    }
                    case About.Mentioned about ->
                        MetamodelEmitter.tokenOnly(metamodel, model, canonical, targets, about);
                    case About.Declined about ->
                        MetamodelEmitter.tokenOnly(metamodel, model, canonical, targets, about);
                };
        try {
            try (Writer writer = processingEnv
                    .getFiler()
                    .createSourceFile(file.qualifiedName(), originating)
                    .openWriter()) {
                writer.write(MetamodelEmitter.source(file));
            }
            index.publish(planned.completeness(), planned.binaryName(), metamodel, originating);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write " + name, e);
        }
        return Done.generated(metamodel, planned.completeness());
    }

    /// What a type waits for, as a diagnostic tells it: the metamodels that
    /// wait for each other in turn, and the type that is missing at the end
    /// — or that none is, and the metamodels wait in a circle.
    ///
    /// A type that is missing either does not exist — no processor generated
    /// it — or exists, but cannot be named where it is mentioned: one that is
    /// not `public` in another package, which javac reports itself.
    ///
    /// @param wait what the type waits for
    /// @param exists whether the compilation has a type of a name
    /// @return the rest of a sentence that starts with `it`
    static String describe(TypeGraph.Wait wait, Predicate<String> exists) {
        return switch (wait) {
            case TypeGraph.Wait.Missing(List<String> chain) ->
                Stream.concat(
                                awaited(chain.subList(1, chain.size() - 1)),
                                Stream.of("mentions " + chain.getLast()
                                        + (exists.test(chain.getLast())
                                                ? ", which is not accessible there"
                                                : ", which no processor generated")))
                        .collect(Collectors.joining(", which "));
            case TypeGraph.Wait.Cycle(List<String> chain) ->
                awaited(chain.subList(1, chain.size())).collect(Collectors.joining(", which "))
                        + ": these metamodels wait for each other, and no type is missing";
        };
    }

    private static Stream<String> awaited(List<String> types) {
        return types.stream().map(type -> "waits for the metamodel of " + type);
    }

    private static Optional<? extends Element> first(SortedSet<Site> sites, Elements elements) {
        return sites.first().resolve(elements);
    }

    /// A type `@Facts` asks for, as a round finds it.
    ///
    /// @param name the binary name
    /// @param type the type
    /// @param sites the `@Facts` that ask for it
    private record Asked(String name, TypeElement type, SortedSet<Site> sites) {}

    /// A metamodel to generate in this round.
    ///
    /// @param type the type
    /// @param binaryName the binary name of the type
    /// @param metamodel the metamodel class
    /// @param completeness whether the metamodel is full, with a fact per member — that of a type
    ///     `@Facts` asks for or a requested type inherits from — or token-only, that of a type
    ///     signatures mention
    /// @param requesters the `@Facts` the metamodel is generated for: those that ask for the type, for
    ///     its subtypes, or for the types whose signatures mention it
    /// @param stale why the metamodels of the type on the classpath were not reused
    /// @param subject the type as a diagnostic names it, see [#subject]
    /// @param about what the comment of the generated metamodel says of why it is there, and why it is
    ///     full or token-only ([MetamodelDocs]); its reasons, why the type is in the graph of the
    ///     round ([TypeGraph#reasons]), also tell whether a member without a fact is an error under
    ///     `strict`
    private record Planned(
            TypeElement type,
            String binaryName,
            ClassDesc metamodel,
            ReuseIndex.Completeness completeness,
            SortedSet<Site> requesters,
            List<String> stale,
            String subject,
            About about) {}

    /// What a round settles of a type without writing a metamodel.
    private sealed interface Settlement {

        /// The binary name of the type.
        String name();

        /// What became of the type.
        Done done();

        /// A metamodel on the classpath is reused.
        record Reused(String name, Done done) implements Settlement {}

        /// The type has no metamodel.
        ///
        /// @param error why, to report
        record Refused(String name, String error) implements Settlement {
            @Override
            public Done done() {
                return new Done.Failed();
            }
        }
    }

    /// Whether a round is the last: no later round will bring a type that is missing.
    private enum Round {
        /// Another round may follow.
        NOT_LAST,
        /// `processingOver`: what is not there will not come.
        LAST
    }
}
