package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
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
/// In every round but the last it
///
/// 1. reads the `@Facts` of the round, and again, by name, those with a
///    class literal of a type that did not exist yet (§8);
/// 2. builds the graph of the types of the round ([Closure], [TypeGraph]):
///    what `@Facts` asks for, what those types extend and implement, what
///    the signatures mention, what is not generated yet;
/// 3. reads from the graph whether it is complete
///    ([TypeGraph#complete()]): while `@Facts` asks for a type that is not
///    generated yet, or a type extends or implements one, the round writes
///    and settles nothing, for that type may extend or implement any other
///    once it is there, of which a full metamodel is then wanted, and a
///    metamodel is written once;
/// 4. reads from a complete graph what to do: which types have a metamodel
///    on the classpath to reuse, which have none and why, which full and
///    token-only metamodels to write, and which wait for a later round;
/// 5. writes the metamodels and lists them in the resource index
///    ([ReuseIndex]).
///
/// In the last round it reports what never became ready, with what each
/// type waited for. It writes nothing then: javac compiles a file of the
/// last round, but no longer finds its class for the files of the rounds
/// before, which import it.
///
/// So a type is known for what it is — asked for, a supertype, only
/// mentioned — before its metamodel is written, as far as the `@Facts` of
/// the compilation go. Only a `@Facts` another processor generates, in a
/// round after the metamodels were written, can ask for more of a type than
/// its metamodel has: a type that has a token-only metamodel by then gets
/// no full one ([TypeGraph.Request.Late], [TypeGraph.Node.Declined]). To
/// cover that the processor would have to write in the last round.
///
/// Between rounds it keeps where `@Facts` is and what it asks for, what
/// became of each type ([Done]) — a metamodel is written once —, which
/// supertypes it told to have no full metamodel, and, for the report of the
/// last round, which types the latest graph left waiting. Everything else is
/// read anew from the graph of each round.
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
/// Options: see [Options].
public final class FactsProcessor extends AbstractProcessor {
    private static final String FORMAT = "me.supcheg.javafile.facts.meta.MetamodelFormat";
    private static final String HELD =
            "; no metamodel is written before that type is there, which may extend or" + " implement any other";

    private @Nullable Diagnostics diagnostics;
    private @Nullable Options options;
    private boolean stopped;

    private final SortedSet<Site> sites = new TreeSet<>();
    private final SortedSet<Site> unresolvedSites = new TreeSet<>();
    private final SortedMap<String, SortedSet<Site>> requested = new TreeMap<>();
    private final Map<String, Done> done = new HashMap<>();
    private final Map<ClassDesc, String> ownMetamodels = new HashMap<>();
    private final SortedSet<String> declined = new TreeSet<>();
    private List<Stuck> stuck = List.of();

    /// Creates the processor; javac finds it through the service loader.
    public FactsProcessor() {}

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Requests.FACTS);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Set.of(Options.PACKAGE, Options.STRICT);
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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
        if (stopped || options == null || diagnostics == null) {
            return false;
        }
        Elements elements = processingEnv.getElementUtils();
        if (round.processingOver()) {
            reportUnfinished(elements, diagnostics);
            return false;
        }
        TypeElement facts = elements.getTypeElement(Requests.FACTS);
        List<Element> annotated = facts == null ? List.of() : List.copyOf(round.getElementsAnnotatedWith(facts));
        if (sites.isEmpty() && !annotated.isEmpty() && !formatMatches(elements, diagnostics)) {
            stopped = true;
            return false;
        }
        for (Element element : annotated) {
            Site site = Site.of(element, elements);
            sites.add(site);
            read(site, element, diagnostics);
        }
        for (Site site : List.copyOf(unresolvedSites)) {
            if (annotated.stream().noneMatch(e -> Site.of(e, elements).equals(site))) {
                site.resolve(elements).ifPresent(element -> read(site, element, diagnostics));
            }
        }
        if (requested.isEmpty()) {
            return false;
        }
        switch (BasePackage.of(
                options, sites.stream().map(Site::packageName).collect(Collectors.toCollection(TreeSet::new)))) {
            case BasePackage.Chosen(String base) -> generate(base, elements, diagnostics);
            case BasePackage.Ambiguous ambiguous -> {
                diagnostics.error(sites.first().resolve(elements), ambiguous.message());
                stopped = true;
            }
        }
        return false;
    }

    private void read(Site site, Element element, Diagnostics diagnostics) {
        Requests.Reading reading = Requests.read(element, diagnostics);
        for (TypeElement type : reading.types()) {
            requested
                    .computeIfAbsent(type.getQualifiedName().toString(), _ -> new TreeSet<>())
                    .add(site);
        }
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

    private void generate(String base, Elements elements, Diagnostics diagnostics) {
        Models models = new Models(elements, processingEnv.getTypeUtils());
        ReuseIndex index = new ReuseIndex(processingEnv.getFiler(), elements);
        SortedMap<String, Asked> asked = requested.entrySet().stream()
                .flatMap(entry -> Optional.ofNullable(elements.getTypeElement(entry.getKey()))
                        .map(type -> new Asked(models.binaryName(type), type, entry.getValue()))
                        .stream())
                .collect(Collectors.toMap(Asked::name, Function.identity(), (first, second) -> first, TreeMap::new));
        TypeGraph graph = Closure.of(
                asked.values().stream().collect(Collectors.toMap(Asked::name, Asked::type)),
                unresolvedSites.stream().map(Site::name).collect(Collectors.toSet()),
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
        Set<String> missingSupertypes =
                graph.missingSupertypes().map(TypeGraph.Edge::to).collect(Collectors.toSet());
        stuck = graph.nodes().values().stream()
                .filter(node -> node.request().isPresent())
                .flatMap(node -> graph
                        .waitOf(node.name())
                        .map(wait -> new Stuck(
                                sitesOf.apply(node.name()).first(),
                                "type " + subject(graph, node.name()) + " in @Facts is not resolvable after all"
                                        + " rounds: it " + describe(wait)
                                        + (wait instanceof TypeGraph.Wait.Missing(List<String> chain)
                                                        && missingSupertypes.contains(chain.getLast())
                                                ? HELD
                                                : "")))
                        .stream())
                .toList();
        if (!graph.complete()) {
            return;
        }
        graph.nodes().values().forEach(node -> {
            settled(node).ifPresent(settled -> {
                done.put(node.name(), settled.left());
                settled.right()
                        .ifPresent(error -> diagnostics.error(first(sitesOf.apply(node.name()), elements), error));
            });
            if (node instanceof TypeGraph.Node.Declined(String name, String reason, var _) && declined.add(name)) {
                diagnostics.skipped(
                        first(sitesOf.apply(name), elements),
                        graph.roots(name).collect(Collectors.joining(", "))
                                + ": no facts of the public members inherited from " + name
                                + ", which has no full metamodel: " + reason,
                        graph.reasons(name).toList());
            }
        });
        Targets targets = new Targets(models, metamodels(graph, base, elements), unavailable(graph));
        planned(graph, sitesOf)
                .toList()
                .forEach(metamodel -> write(base, metamodel, models, targets, index, elements, diagnostics));
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
    private static Optional<Pair<Done, Optional<String>>> settled(TypeGraph.Node node) {
        String name = node.name();
        return node.request()
                .flatMap(request -> switch (request) {
                    case TypeGraph.Request.Late(ClassDesc tokenOnly) ->
                        refused(name + " is requested after its token-only metamodel " + Models.binaryName(tokenOnly)
                                + " was generated in an earlier round; request it with the types whose"
                                + " signatures mention it");
                    case TypeGraph.Request.Unrepresentable(String reason) ->
                        refused("no metamodel of " + name + ": " + reason);
                    case TypeGraph.Request.Rejected(String reason) ->
                        refused("no metamodel of " + name + ": " + reason);
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) -> reused(metamodel, true);
                    case TypeGraph.Request.Settled _, TypeGraph.Request.Ready _, TypeGraph.Request.Waiting _ ->
                        Optional.empty();
                })
                .or(() -> node.token()
                        .flatMap(
                                token -> token instanceof TypeGraph.Token.OnClasspath(ClassDesc metamodel, boolean full)
                                        ? reused(metamodel, full)
                                        : Optional.empty()));
    }

    private static Optional<Pair<Done, Optional<String>>> reused(ClassDesc metamodel, boolean full) {
        return Optional.of(Pair.pair(new Done.Reused(metamodel, full), Optional.empty()));
    }

    private static Optional<Pair<Done, Optional<String>>> refused(String error) {
        return Optional.of(Pair.pair(new Done.Failed(), Optional.of(error)));
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
                    case TypeGraph.Token.Unavailable _ -> Optional.<ClassDesc>empty();
                }));
    }

    private static Optional<ClassDesc> metamodel(Done done) {
        return switch (done) {
            case Done.Generated(ClassDesc metamodel, var _) -> Optional.of(metamodel);
            case Done.Reused(ClassDesc metamodel, var _) -> Optional.of(metamodel);
            case Done.Failed _ -> Optional.empty();
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
    /// token-only ones of the types their signatures mention. Each is
    /// written for the `@Facts` that ask for the types it is there for.
    private static Stream<Planned> planned(TypeGraph graph, Function<String, SortedSet<Site>> sitesOf) {
        return Stream.concat(
                graph.nodes().values().stream()
                        .flatMap(node -> node
                                .request()
                                .flatMap(request ->
                                        request instanceof TypeGraph.Request.Ready ready && !graph.waits(node.name())
                                                ? Optional.of(ready)
                                                : Optional.empty())
                                .map(ready -> new Planned(
                                        ready.type(),
                                        node.name(),
                                        true,
                                        sitesOf.apply(node.name()),
                                        ready.stale(),
                                        subject(graph, node.name()),
                                        graph.reasons(node.name()).toList()))
                                .stream()),
                graph.nodes().values().stream()
                        .flatMap(node -> node
                                .token()
                                .flatMap(token -> token instanceof TypeGraph.Token.Planned tokenOnly
                                        ? Optional.of(tokenOnly)
                                        : Optional.empty())
                                .map(tokenOnly -> new Planned(
                                        tokenOnly.type(),
                                        node.name(),
                                        false,
                                        graph.mentioners(node.name())
                                                .flatMap(mentioner -> sitesOf.apply(mentioner).stream())
                                                .collect(Collectors.toCollection(TreeSet::new)),
                                        tokenOnly.stale(),
                                        node.name(),
                                        graph.reasons(node.name()).toList()))
                                .stream()));
    }

    private void write(
            String base,
            Planned planned,
            Models models,
            Targets targets,
            ReuseIndex index,
            Elements elements,
            Diagnostics diagnostics) {
        Optional<? extends Element> at = first(planned.requesters(), elements);
        ClassDesc metamodel = MetamodelNames.metamodel(base, planned.type(), elements);
        String name = Models.binaryName(metamodel);
        done.put(planned.binaryName(), new Done.Failed());
        String other = ownMetamodels.putIfAbsent(metamodel, planned.binaryName());
        if (other != null) {
            diagnostics.error(
                    at, "the metamodels of " + other + " and " + planned.binaryName() + " would both be " + name);
            return;
        }
        if (elements.getTypeElement(name) != null) {
            diagnostics.error(
                    at,
                    "metamodel " + name + " of " + planned.binaryName() + " already exists in a dependency; reuse it"
                            + " (it does not match " + planned.binaryName() + " on this classpath) or choose another"
                            + " package with -A" + Options.PACKAGE + "=<package>");
            return;
        }
        planned.stale().forEach(reason -> diagnostics.warning(at, reason + "; generating " + name));
        TypeModel model =
                switch (models.of(planned.type(), planned.full() ? MemberFilter.DECLARED_PUBLIC : MemberFilter.NONE)) {
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
        JavaFile file;
        if (planned.full()) {
            Set<String> taken = MetamodelEmitter.takenNames(
                    metamodel, model, canonical, MemberPlan.probe(planned.type(), models, targets), targets);
            MemberPlan plan = MemberPlan.of(planned.type(), models, targets, taken);
            plan.skipped()
                    .forEach(skip -> diagnostics.skipped(
                            at,
                            planned.subject() + ": no fact of " + skip.member() + ", which " + skip.reason(),
                            planned.reasons()));
            file = MetamodelEmitter.full(metamodel, model, canonical, plan, targets, taken);
        } else {
            file = MetamodelEmitter.tokenOnly(metamodel, model, canonical, targets);
        }
        try {
            try (Writer writer = processingEnv
                    .getFiler()
                    .createSourceFile(file.qualifiedName(), originating)
                    .openWriter()) {
                writer.write(MetamodelEmitter.source(file));
            }
            index.publish(
                    planned.full() ? ReuseIndex.Completeness.FULL : ReuseIndex.Completeness.TOKEN,
                    planned.binaryName(),
                    metamodel,
                    originating);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot write " + name, e);
        }
        done.put(planned.binaryName(), new Done.Generated(metamodel, planned.full()));
    }

    private void reportUnfinished(Elements elements, Diagnostics diagnostics) {
        unresolvedSites.forEach(site -> diagnostics.error(
                site.resolve(elements), "a type in @Facts is not resolvable after all rounds" + HELD));
        stuck.forEach(type -> diagnostics.error(type.site().resolve(elements), type.error()));
    }

    /// What a type waits for, as a diagnostic tells it: the metamodels that
    /// wait for each other in turn, and the type that is missing at the end
    /// — or that none is, and the metamodels wait in a circle.
    ///
    /// @param wait what the type waits for
    /// @return the rest of a sentence that starts with `it`
    static String describe(TypeGraph.Wait wait) {
        return switch (wait) {
            case TypeGraph.Wait.Missing(List<String> chain) ->
                Stream.concat(
                                awaited(chain.subList(1, chain.size() - 1)),
                                Stream.of("mentions " + chain.getLast() + ", which no processor generated"))
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
    /// @param full whether the metamodel is full, with a fact per member — that of a type `@Facts`
    ///     asks for or a requested type inherits from — rather than token-only, that of a type
    ///     signatures mention
    /// @param requesters the `@Facts` the metamodel is generated for: those that ask for the type, for
    ///     its subtypes, or for the types whose signatures mention it
    /// @param stale why the metamodels of the type on the classpath were not reused
    /// @param subject the type as a diagnostic names it, see [#subject]
    /// @param reasons why the type is in the graph of the round, see [TypeGraph#reasons]: what the
    ///     generated metamodel is to say of why it is there, and why it is full or token-only, and
    ///     whether a member of it without a fact is an error under `strict`
    private record Planned(
            TypeElement type,
            String binaryName,
            boolean full,
            SortedSet<Site> requesters,
            List<String> stale,
            String subject,
            List<TypeGraph.Reason> reasons) {}

    /// A type whose full metamodel the latest round did not write because the type waits.
    ///
    /// @param site the first `@Facts` that asks for it, or for a subtype of it
    /// @param error what the last round is to tell of it: what it waits for
    private record Stuck(Site site, String error) {}
}
