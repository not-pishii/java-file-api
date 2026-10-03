package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/// A processor that builds the [TypeGraph] of its first round as
/// [FactsProcessor] does, for types given by name in whatever order, and
/// keeps it: the graph of a real compilation, to look at.
final class GraphProbe extends AbstractProcessor {
    private final List<String> requested;
    private Optional<TypeGraph> graph = Optional.empty();

    /// @param requested the canonical names of the types to ask for, in the order to ask in
    GraphProbe(String... requested) {
        this.requested = List.of(requested);
    }

    /// The graph of the first round.
    TypeGraph graph() {
        return graph.orElseThrow(() -> new IllegalStateException("the processor did not run"));
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("*");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
        if (graph.isEmpty()) {
            Elements elements = processingEnv.getElementUtils();
            Models models = new Models(elements, new MirrorTranslator(elements, processingEnv.getTypeUtils()));
            Map<String, TypeElement> types = requested.stream()
                    .map(elements::getTypeElement)
                    .collect(Collectors.toMap(
                            models::binaryName, Function.identity(), (first, second) -> first, LinkedHashMap::new));
            graph = Optional.of(Closure.of(
                    types,
                    Map.of(),
                    models,
                    new ReuseIndex(processingEnv.getFiler(), elements),
                    "gen.facts",
                    elements));
        }
        return false;
    }
}
