package me.supcheg.javafile.langmodel.mirror;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.type.TypeRef;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

/// Runs [MirrorTranslator] inside javac: a test processor calls an action
/// in every round and keeps what it returns — plain data that outlives the
/// compilation.
final class Harness {
    private static final Pattern PACKAGE = Pattern.compile("package\\s+([\\w.]+)\\s*;");
    private static final Pattern PUBLIC_TYPE = Pattern.compile(
            "(?m)^public\\s+(?:(?:abstract|final|sealed|non-sealed|strictfp)\\s+)*(?:class|interface|enum|record|@interface)\\s+(\\w+)");
    private static final Pattern ANY_TYPE = Pattern.compile("(?:class|interface|enum|record)\\s+(\\w+)");

    private Harness() {}

    /// What an action sees in a round.
    record Env(MirrorTranslator translator, Elements elements, Types types) {

        TypeElement element(String canonicalName) {
            return Objects.requireNonNull(elements.getTypeElement(canonicalName), canonicalName);
        }

        ExecutableElement method(String canonicalName, String method) {
            return ElementFilter.methodsIn(element(canonicalName).getEnclosedElements()).stream()
                    .filter(m -> m.getSimpleName().contentEquals(method))
                    .findFirst()
                    .orElseThrow();
        }

        VariableElement field(String canonicalName, String field) {
            return ElementFilter.fieldsIn(element(canonicalName).getEnclosedElements()).stream()
                    .filter(f -> f.getSimpleName().contentEquals(field))
                    .findFirst()
                    .orElseThrow();
        }

        /// The type of a field of `canonicalName`, translated in the scope of that type.
        Translation<TypeRef> fieldType(String canonicalName, String field) {
            return translator.typeRef(field(canonicalName, field).asType(), VarScope.of(element(canonicalName)));
        }

        Translation<TypeModel> full(String canonicalName) {
            return translator.type(element(canonicalName), MemberFilter.DECLARED_PUBLIC);
        }

        Translation<TypeModel> tokenOnly(String canonicalName) {
            return translator.type(element(canonicalName), MemberFilter.NONE);
        }
    }

    /// Runs `action` in the first round of a successful compilation of
    /// `sources`, or of an empty class for none.
    static <R> R run(Function<Env, R> action, String... sources) {
        List<R> results = new ArrayList<>();
        Compilation compilation = compile(action, results, List.of(), sources);
        assertThat(compilation.status()).as("%s", compilation.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        return results.getFirst();
    }

    /// Runs `action` in the first round of a compilation of `sources` that
    /// may fail, since they mention types nobody generates.
    static <R> R runUnresolved(Function<Env, R> action, String... sources) {
        List<R> results = new ArrayList<>();
        compile(action, results, List.of(), sources);
        return results.getFirst();
    }

    /// Runs `action` in every round but the last, with `others` running
    /// alongside.
    static <R> List<R> everyRound(Function<Env, R> action, List<Processor> others, String... sources) {
        List<R> results = new ArrayList<>();
        Compilation compilation = compile(action, results, others, sources);
        assertThat(compilation.status()).as("%s", compilation.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        return results;
    }

    static TypeModel ok(Translation<TypeModel> translation) {
        assertThat(translation).isInstanceOf(Translation.Ok.class);
        return ((Translation.Ok<TypeModel>) translation).value();
    }

    static JavaFileObject source(String source) {
        Matcher pkg = PACKAGE.matcher(source);
        String prefix = pkg.find() ? pkg.group(1) + "." : "";
        Matcher type = PUBLIC_TYPE.matcher(source);
        if (!type.find()) {
            type = ANY_TYPE.matcher(source);
            if (!type.find()) {
                throw new IllegalArgumentException("no type in " + source);
            }
        }
        return JavaFileObjects.forSourceString(prefix + type.group(1), source);
    }

    private static <R> Compilation compile(
            Function<Env, R> action, List<R> sink, List<Processor> others, String... sources) {
        List<JavaFileObject> files = new ArrayList<>();
        for (String source : sources.length == 0 ? new String[] {"package p; class Empty {}"} : sources) {
            files.add(source(source));
        }
        List<Processor> processors = new ArrayList<>(others);
        processors.add(new Probe<>(action, sink));
        return javac().withProcessors(processors).compile(files);
    }

    private static final class Probe<R> extends AbstractProcessor {
        private final Function<Env, R> action;
        private final List<R> sink;

        Probe(Function<Env, R> action, List<R> sink) {
            this.action = action;
            this.sink = sink;
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
            if (!round.processingOver()) {
                sink.add(action.apply(new Env(
                        new MirrorTranslator(processingEnv.getElementUtils(), processingEnv.getTypeUtils()),
                        processingEnv.getElementUtils(),
                        processingEnv.getTypeUtils())));
            }
            return false;
        }
    }
}
