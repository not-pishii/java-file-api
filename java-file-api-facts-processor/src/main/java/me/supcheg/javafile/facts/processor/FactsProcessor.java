package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.meta.MetamodelFormat;
import me.supcheg.javafile.filer.JavaFileWriter;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.SkippedMember;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
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
import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
/// 2. for each requested type not handled yet: reuses a matching full
///    metamodel from the classpath, or takes the closure of the type
///    ([Closure]) — waiting for a later round while the type or a type in
///    its signatures is not generated yet;
/// 3. for each type the ready closures mention: reuses a metamodel from the
///    classpath, or generates a token-only one;
/// 4. writes the metamodels and lists them in the resource index
///    ([ReuseIndex]).
///
/// In the last round it reports what never became ready.
///
/// A requested type that is not generic gets a full metamodel, with a fact
/// per member ([MemberPlan]); a generic one, until plan step 9, a token-only
/// metamodel, marked and listed as such, so no other compilation mistakes it
/// for a full one.
///
/// Options: see [Options].
public final class FactsProcessor extends AbstractProcessor {
    private static final String FORMAT = "me.supcheg.javafile.facts.meta.MetamodelFormat";

    private @Nullable Diagnostics diagnostics;
    private @Nullable Options options;
    private boolean stopped;

    private final SortedSet<Site> sites = new TreeSet<>();
    private final SortedSet<Site> unresolvedSites = new TreeSet<>();
    private final SortedMap<String, SortedSet<Site>> requested = new TreeMap<>();
    private final SortedMap<String, String> waiting = new TreeMap<>();
    private final SortedMap<String, String> blocked = new TreeMap<>();
    private final Map<String, Done> done = new HashMap<>();
    private final Map<ClassDesc, String> ownMetamodels = new HashMap<>();

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
        List<Planned> planned = new ArrayList<>();
        SortedMap<String, TypeElement> mentioned = new TreeMap<>();
        Map<String, SortedSet<Site>> mentionedBy = new HashMap<>();
        Set<String> requestedTypes = new TreeSet<>();
        Map<String, String> unavailableTypes = new HashMap<>();
        for (Map.Entry<String, SortedSet<Site>> entry : requested.entrySet()) {
            TypeElement type = elements.getTypeElement(entry.getKey());
            if (type == null) {
                continue;
            }
            String binaryName = models.binaryName(type);
            requestedTypes.add(binaryName);
            SortedSet<Site> requesters = entry.getValue();
            Done previous = done.get(binaryName);
            if (previous != null) {
                if (previous instanceof Done.Generated(ClassDesc metamodel, boolean full) && !full) {
                    done.put(binaryName, new Done.Failed());
                    diagnostics.error(
                            first(requesters, elements),
                            binaryName + " is requested after its token-only metamodel " + Models.binaryName(metamodel)
                                    + " was generated in an earlier round; request it with the types whose"
                                    + " signatures mention it");
                }
                continue;
            }
            List<String> stale;
            switch (index.find(type, ReuseIndex.Completeness.FULL, models)) {
                case ReuseIndex.Lookup.Reusable(ClassDesc metamodel) -> {
                    done.put(binaryName, new Done.Reused(metamodel));
                    continue;
                }
                case ReuseIndex.Lookup.Absent(List<String> reasons) -> stale = reasons;
            }
            switch (Closure.of(type, models)) {
                case Closure.Outcome.Waiting(String unresolved) -> waiting.put(entry.getKey(), unresolved);
                case Closure.Outcome.Rejected(String reason) -> {
                    waiting.remove(entry.getKey());
                    done.put(binaryName, new Done.Failed());
                    diagnostics.error(first(requesters, elements), "no metamodel of " + binaryName + ": " + reason);
                }
                case Closure.Outcome.Ready ready -> {
                    waiting.remove(entry.getKey());
                    Optional<? extends Element> at = first(requesters, elements);
                    boolean full = type.getTypeParameters().isEmpty();
                    if (full) {
                        // the plan of the members reports what it leaves out, and why
                        ready.unavailable().forEach(u -> unavailableTypes.put(u.type(), u.reason()));
                    } else {
                        for (SkippedMember skipped : ready.full().skipped()) {
                            diagnostics.skipped(
                                    at,
                                    binaryName + ": no fact of " + skipped.member() + ", which " + skipped.reason());
                        }
                        for (Closure.Unavailable unavailable : ready.unavailable()) {
                            diagnostics.skipped(
                                    at,
                                    binaryName + ": no metamodel of " + unavailable.type()
                                            + ", which its signatures mention: " + unavailable.reason());
                        }
                    }
                    planned.add(new Planned(
                            type,
                            binaryName,
                            true,
                            full,
                            requesters,
                            stale,
                            ready.signatureTypes().keySet()));
                    ready.signatureTypes().forEach((name, element) -> {
                        mentioned.put(name, element);
                        mentionedBy.computeIfAbsent(name, _ -> new TreeSet<>()).addAll(requesters);
                    });
                }
            }
        }
        for (Map.Entry<String, TypeElement> entry : mentioned.entrySet()) {
            String binaryName = entry.getKey();
            if (requestedTypes.contains(binaryName) || done.containsKey(binaryName)) {
                continue;
            }
            switch (index.find(entry.getValue(), ReuseIndex.Completeness.TOKEN, models)) {
                case ReuseIndex.Lookup.Reusable(ClassDesc metamodel) ->
                    done.put(binaryName, new Done.Reused(metamodel));
                case ReuseIndex.Lookup.Absent(List<String> stale) ->
                    planned.add(new Planned(
                            entry.getValue(), binaryName, false, false, mentionedBy.get(binaryName), stale, Set.of()));
            }
        }
        Map<String, ClassDesc> metamodels = new HashMap<>();
        done.forEach((name, result) -> {
            switch (result) {
                case Done.Generated(ClassDesc metamodel, boolean ignored) -> metamodels.put(name, metamodel);
                case Done.Reused(ClassDesc metamodel) -> metamodels.put(name, metamodel);
                case Done.Failed ignored -> {}
            }
        });
        for (Planned metamodel : planned) {
            metamodels.put(metamodel.binaryName(), MetamodelNames.metamodel(base, metamodel.type(), elements));
        }
        Targets targets = new Targets(models, metamodels, unavailableTypes);
        for (Planned metamodel : planned) {
            String key = metamodel.type().getQualifiedName().toString();
            // a full metamodel refers to the metamodel of a requested type it mentions: one that is not
            // ready yet must be waited for, or the members that mention it would have no fact for good
            Optional<String> notReady = metamodel.full()
                    ? metamodel.mentions().stream()
                            .filter(name -> requestedTypes.contains(name)
                                    && !metamodels.containsKey(name)
                                    && !(done.get(name) instanceof Done.Failed))
                            .findFirst()
                    : Optional.empty();
            if (notReady.isPresent()) {
                blocked.put(key, notReady.get());
            } else {
                blocked.remove(key);
                write(base, metamodel, models, targets, index, elements, diagnostics);
            }
        }
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
        for (String reason : planned.stale()) {
            diagnostics.warning(at, reason + "; generating " + name);
        }
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
        MetamodelEmitter.Emission emission;
        if (planned.full()) {
            MemberPlan plan =
                    MemberPlan.of(planned.type(), models, targets, MetamodelEmitter.takenNames(model, targets));
            for (MemberPlan.Skip skip : plan.skipped()) {
                diagnostics.skipped(
                        at, planned.binaryName() + ": no fact of " + skip.member() + ", which " + skip.reason());
            }
            emission = MetamodelEmitter.full(metamodel, model, canonical, plan, targets);
        } else {
            emission = MetamodelEmitter.tokenOnly(metamodel, model, canonical);
        }
        switch (emission) {
            case MetamodelEmitter.Emission.Clash(String reason) -> diagnostics.error(at, reason);
            case MetamodelEmitter.Emission.Written(var file) -> {
                try {
                    JavaFileWriter.writeTo(file, processingEnv.getFiler(), originating);
                    index.publish(
                            planned.full() ? ReuseIndex.Completeness.FULL : ReuseIndex.Completeness.TOKEN,
                            planned.binaryName(),
                            metamodel,
                            originating);
                } catch (IOException e) {
                    throw new UncheckedIOException("cannot write " + name, e);
                }
                done.put(planned.binaryName(), new Done.Generated(metamodel, planned.requested()));
            }
        }
    }

    private void reportUnfinished(Elements elements, Diagnostics diagnostics) {
        for (Site site : unresolvedSites) {
            diagnostics.error(site.resolve(elements), "a type in @Facts is not resolvable after all rounds");
        }
        blocked.forEach((type, mentioned) -> diagnostics.error(
                first(requested.get(type), elements),
                "type " + type + " in @Facts is not resolvable after all rounds: it mentions " + mentioned
                        + ", whose metamodel is not ready"));
        waiting.forEach((type, unresolved) -> diagnostics.error(
                first(requested.get(type), elements),
                "type " + type + " in @Facts is not resolvable after all rounds: it mentions " + unresolved
                        + ", which no processor generated"));
    }

    private static Optional<? extends Element> first(SortedSet<Site> sites, Elements elements) {
        return sites.first().resolve(elements);
    }

    /// A metamodel to generate in this round.
    ///
    /// @param type the type
    /// @param binaryName the binary name of the type
    /// @param requested whether `@Facts` asks for the type, rather than a signature mentioning it
    /// @param full whether the metamodel has a fact per member: a requested type that is not generic
    /// @param requesters the `@Facts` the metamodel is generated for
    /// @param stale why the metamodels of the type on the classpath were not reused
    /// @param mentions the binary names of the classes and interfaces the signatures of a requested type mention
    private record Planned(
            TypeElement type,
            String binaryName,
            boolean requested,
            boolean full,
            SortedSet<Site> requesters,
            List<String> stale,
            Set<String> mentions) {}

    /// What became of a type.
    private sealed interface Done {

        /// This compilation generated its metamodel.
        record Generated(ClassDesc metamodel, boolean requested) implements Done {}

        /// A metamodel on the classpath is reused.
        record Reused(ClassDesc metamodel) implements Done {}

        /// It has no metamodel; an error is reported.
        record Failed() implements Done {}
    }
}
