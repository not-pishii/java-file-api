package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.routine.Either;
import me.supcheg.routine.EitherCollectors;
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
/// `@Facts` asks for, and token-only metamodels of the types their
/// signatures mention (§5 of the main spec, the facts-processor mini-spec).
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
///    what `@Facts` asks for, what the signatures of those types mention,
///    what they extend and implement, what is not generated yet;
/// 3. reads from the graph what to do: which types have a metamodel on the
///    classpath to reuse, which have none and why, which full and token-only
///    metamodels to write, and which wait for a later round;
/// 4. writes the metamodels and lists them in the resource index
///    ([ReuseIndex]).
///
/// In the last round it reports what never became ready, with what each
/// type waited for.
///
/// Between rounds it keeps where `@Facts` is and what it asks for, what
/// became of each type ([Done]) — a metamodel is written once — and, for the
/// report of the last round, which types the latest graph left waiting.
/// Everything else is read anew from the graph of each round.
///
/// A requested type gets a full metamodel, with a fact per member
/// ([MemberPlan]) — in terms of its type parameters if it is generic.
///
/// Options: see [Options].
public final class FactsProcessor extends AbstractProcessor {
    private static final String FORMAT = "me.supcheg.javafile.facts.meta.MetamodelFormat";
    private static final String OBJECT = "java.lang.Object";

    private @Nullable Diagnostics diagnostics;
    private @Nullable Options options;
    private boolean stopped;

    private final SortedSet<Site> sites = new TreeSet<>();
    private final SortedSet<Site> unresolvedSites = new TreeSet<>();
    private final SortedMap<String, SortedSet<Site>> requested = new TreeMap<>();
    private final Map<String, Done> done = new HashMap<>();
    private final Map<ClassDesc, String> ownMetamodels = new HashMap<>();
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
        Models models = new Models(elements, new MirrorTranslator(elements, processingEnv.getTypeUtils()));
        ReuseIndex index = new ReuseIndex(processingEnv.getFiler(), elements);
        SortedMap<String, Asked> asked = requested.entrySet().stream()
                .flatMap(entry -> Optional.ofNullable(elements.getTypeElement(entry.getKey()))
                        .map(type -> new Asked(models.binaryName(type), type, entry.getValue()))
                        .stream())
                .collect(Collectors.toMap(Asked::name, Function.identity(), (first, second) -> first, TreeMap::new));
        TypeGraph graph = Closure.of(
                asked.values().stream().collect(Collectors.toMap(Asked::name, Asked::type)),
                Map.copyOf(done),
                models,
                index,
                base,
                elements);
        graph.nodes()
                .values()
                .forEach(node -> settled(node).ifPresent(settled -> {
                    done.put(node.name(), settled.left());
                    settled.right()
                            .ifPresent(error -> diagnostics.error(
                                    first(asked.get(node.name()).sites(), elements), error));
                }));
        Targets targets = new Targets(models, metamodels(graph, base, elements), unavailable(graph));
        planned(graph, asked)
                .toList()
                .forEach(metamodel -> write(base, metamodel, models, targets, index, elements, diagnostics));
        stuck = asked.values().stream()
                .flatMap(type ->
                        graph
                                .waitOf(type.name())
                                .map(wait -> new Stuck(type.name(), type.sites().first(), wait))
                                .stream())
                .toList();
    }

    /// What a round settles of a type without writing a metamodel: a
    /// metamodel on the classpath to reuse, or no metamodel at all, with the
    /// error to report.
    private static Optional<Pair<Done, Optional<String>>> settled(TypeGraph.Node node) {
        return switch (node) {
            case TypeGraph.Node.Requested(String name, TypeGraph.Request request) ->
                switch (request) {
                    case TypeGraph.Request.Late(ClassDesc tokenOnly) ->
                        refused(name + " is requested after its token-only metamodel " + Models.binaryName(tokenOnly)
                                + " was generated in an earlier round; request it with the types whose"
                                + " signatures mention it");
                    case TypeGraph.Request.Unrepresentable(String reason) ->
                        refused("no metamodel of " + name + ": " + reason);
                    case TypeGraph.Request.Rejected(String reason) ->
                        refused("no metamodel of " + name + ": " + reason);
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) ->
                        Optional.of(Pair.pair(new Done.Reused(metamodel, true), Optional.empty()));
                    case TypeGraph.Request.Settled _, TypeGraph.Request.Ready _, TypeGraph.Request.Waiting _ ->
                        Optional.empty();
                };
            case TypeGraph.Node.Mentioned(var _, TypeGraph.Token.OnClasspath(ClassDesc metamodel, boolean full)) ->
                Optional.of(Pair.pair(new Done.Reused(metamodel, full), Optional.empty()));
            case TypeGraph.Node.Mentioned _, TypeGraph.Node.Inherited _, TypeGraph.Node.Absent _ -> Optional.empty();
        };
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
        return switch (node) {
            case TypeGraph.Node.Requested(var _, TypeGraph.Request request) ->
                switch (request) {
                    case TypeGraph.Request.Settled(Done settled) -> metamodel(settled);
                    case TypeGraph.Request.OnClasspath(ClassDesc metamodel) -> Optional.of(metamodel);
                    case TypeGraph.Request.Ready(TypeElement type, var _) ->
                        Optional.of(MetamodelNames.metamodel(base, type, elements));
                    case TypeGraph.Request.Late _,
                            TypeGraph.Request.Waiting _,
                            TypeGraph.Request.Unrepresentable _,
                            TypeGraph.Request.Rejected _ -> Optional.empty();
                };
            case TypeGraph.Node.Mentioned(var _, TypeGraph.Token token) ->
                switch (token) {
                    case TypeGraph.Token.Settled(Done settled) -> metamodel(settled);
                    case TypeGraph.Token.OnClasspath(ClassDesc metamodel, var _) -> Optional.of(metamodel);
                    case TypeGraph.Token.Planned(TypeElement type, var _) ->
                        Optional.of(MetamodelNames.metamodel(base, type, elements));
                    case TypeGraph.Token.Unavailable _ -> Optional.empty();
                };
            case TypeGraph.Node.Inherited _, TypeGraph.Node.Absent _ -> Optional.empty();
        };
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
                .flatMap(node -> switch (node) {
                    case TypeGraph.Node.Requested(String name, TypeGraph.Request.Unrepresentable(String reason)) ->
                        Stream.of(Map.entry(name, reason));
                    case TypeGraph.Node.Mentioned(String name, TypeGraph.Token.Unavailable(String reason)) ->
                        Stream.of(Map.entry(name, reason));
                    case TypeGraph.Node.Requested _,
                            TypeGraph.Node.Mentioned _,
                            TypeGraph.Node.Inherited _,
                            TypeGraph.Node.Absent _ -> Stream.<Map.Entry<String, String>>empty();
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /// The metamodels a round writes: the full ones of the requested types that are read and await
    /// nothing, then the token-only ones of the types their signatures mention.
    private static Stream<Planned> planned(TypeGraph graph, SortedMap<String, Asked> asked) {
        return Stream.concat(
                graph.nodes().values().stream().flatMap(node -> switch (node) {
                    case TypeGraph.Node.Requested(
                            String name,
                            TypeGraph.Request.Ready(TypeElement type, List<String> stale))
                    when !graph.waits(name) ->
                        Stream.of(new Planned(
                                type,
                                name,
                                true,
                                asked.get(name).sites(),
                                stale,
                                inherited(graph, name).toList()));
                    default -> Stream.<Planned>empty();
                }),
                graph.nodes().values().stream().flatMap(node -> switch (node) {
                    case TypeGraph.Node.Mentioned(
                            String name,
                            TypeGraph.Token.Planned(TypeElement type, List<String> stale)) ->
                        Stream.of(new Planned(
                                type,
                                name,
                                false,
                                graph.mentioners(name)
                                        .flatMap(mentioner -> asked.get(mentioner).sites().stream())
                                        .collect(Collectors.toCollection(TreeSet::new)),
                                stale,
                                List.of()));
                    default -> Stream.<Planned>empty();
                }));
    }

    /// What to tell of the `public` members a requested type inherits
    /// (Q6(b)): they are facts of the metamodels of the supertypes that
    /// declare them, so those are to be asked for too — and where `@Facts`
    /// cannot ask for one, the members have no facts at all. Nothing is said
    /// of `java.lang.Object`, which every type inherits from.
    private static Stream<String> inherited(TypeGraph graph, String name) {
        Pair<List<Pair<String, String>>, List<String>> supertypes = graph.inherited(name)
                .filter(supertype -> !supertype.name().equals(OBJECT))
                .flatMap(supertype -> switch (supertype.full()) {
                    case TypeGraph.Full.Asked _, TypeGraph.Full.OnClasspath _ ->
                        Stream.<Either<Pair<String, String>, String>>empty();
                    case TypeGraph.Full.Askable _ ->
                        Stream.<Either<Pair<String, String>, String>>of(Either.right(supertype.name()));
                    case TypeGraph.Full.Refused(String reason) ->
                        Stream.<Either<Pair<String, String>, String>>of(
                                Either.left(Pair.pair(supertype.name(), reason)));
                })
                .collect(EitherCollectors.groupingTo(Collectors.toList(), Collectors.toList()));
        List<Pair<String, String>> refused = supertypes.left();
        List<String> askable = supertypes.right();
        String members = name + ": no facts of the public members inherited from ";
        return Stream.concat(
                askable.isEmpty()
                        ? Stream.empty()
                        : Stream.of(members + String.join(", ", askable) + "; add "
                                + (askable.size() == 1 ? "this type" : "these types")
                                + " to @Facts to use the members"),
                refused.isEmpty()
                        ? Stream.empty()
                        : Stream.of(members
                                + refused.stream().map(Pair::left).collect(Collectors.joining(", "))
                                + ", which @Facts cannot ask for: "
                                + refused.stream().map(Pair::right).collect(Collectors.joining("; "))));
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
                            at, planned.binaryName() + ": no fact of " + skip.member() + ", which " + skip.reason()));
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
        unresolvedSites.forEach(site ->
                diagnostics.error(site.resolve(elements), "a type in @Facts is not resolvable after all rounds"));
        stuck.forEach(type -> diagnostics.error(
                type.site().resolve(elements),
                "type " + type.name() + " in @Facts is not resolvable after all rounds: it " + describe(type.cause())));
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
    /// @param full whether `@Facts` asks for the type, rather than a signature mentioning it: the
    ///     metamodel is full then, with a fact per member, and token-only otherwise
    /// @param requesters the `@Facts` the metamodel is generated for
    /// @param stale why the metamodels of the type on the classpath were not reused
    /// @param inherited what to tell of the members a requested type inherits
    private record Planned(
            TypeElement type,
            String binaryName,
            boolean full,
            SortedSet<Site> requesters,
            List<String> stale,
            List<String> inherited) {}

    /// A requested type whose metamodel the latest round did not write.
    ///
    /// @param name the binary name
    /// @param site the first `@Facts` that asks for it
    /// @param cause what it waits for
    private record Stuck(String name, Site site, TypeGraph.Wait cause) {}
}
