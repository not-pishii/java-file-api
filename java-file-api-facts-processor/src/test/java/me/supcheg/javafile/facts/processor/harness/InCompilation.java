package me.supcheg.javafile.facts.processor.harness;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.platform.commons.support.AnnotationSupport;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;

/// Runs a test method inside a compilation, in the first round of a
/// processor, and gives it what a processor has: a parameter of type
/// [Elements], [Types], [ProcessingEnvironment] or [RoundEnvironment].
/// For the unit tests of the functions that take the elements of
/// `javax.lang.model`, which live only while javac runs.
///
/// ```java
/// @ExtendWith(InCompilation.class)
/// class MetamodelNamesTest {
///     @Test
///     @InCompilation.Sources("package p; public class R { public void token(int i) {} }")
///     void aReservedNameWithParametersNeedsNoEscape(Elements elements) {
///         TypeElement type = elements.getTypeElement("p.R");
///         …
///     }
/// }
/// ```
///
/// The compilation is of the sources [Sources] names on the method, or
/// else on its class; without it, of nothing of the test's own: the types
/// of the JDK and of the classpath of the tests are there all the same.
/// The test fails if the sources do not compile.
public final class InCompilation implements ParameterResolver, InvocationInterceptor {

    /// The sources of the compilation a test runs in.
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Sources {

        /// Sources written in the test, see [Source#of].
        ///
        /// @return the sources
        String[] value() default {};

        /// The fixtures of `src/test/fixtures` whose libraries are compiled too.
        ///
        /// @return the names of the fixtures
        String[] libraries() default {};
    }

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(InCompilation.class);
    private static final Path FIXTURES = Path.of("src/test/fixtures");
    private static final String NOTHING = "final class NothingOfTheTest {}";

    /// The first round of the compilation, as its processor sees it.
    private record Round(ProcessingEnvironment processing, RoundEnvironment environment) {}

    /// What a test may take, and where a round has it.
    private static final Map<Class<?>, Function<Round, Object>> PROVIDED = Map.of(
            ProcessingEnvironment.class,
            Round::processing,
            RoundEnvironment.class,
            Round::environment,
            Elements.class,
            round -> round.processing().getElementUtils(),
            Types.class,
            round -> round.processing().getTypeUtils());

    @Override
    public boolean supportsParameter(ParameterContext parameter, ExtensionContext context) {
        return PROVIDED.containsKey(parameter.getParameter().getType());
    }

    /// The parameters are resolved before javac runs: each is a proxy of
    /// what the round has once the method runs in it.
    @Override
    public Object resolveParameter(ParameterContext parameter, ExtensionContext context) {
        Class<?> type = parameter.getParameter().getType();
        AtomicReference<Optional<Round>> running = running(context);
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, (_, method, arguments) -> {
            Object provided = PROVIDED.get(type)
                    .apply(running.get().orElseThrow(() -> new IllegalStateException("no compilation is running")));
            try {
                return method.invoke(provided, arguments);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Override
    public void interceptTestMethod(
            Invocation<Void> invocation, ReflectiveInvocationContext<Method> invoked, ExtensionContext context)
            throws Throwable {
        AtomicReference<Optional<Round>> running = running(context);
        AtomicReference<Optional<Throwable>> thrown = new AtomicReference<>(Optional.empty());
        Compiled compiled = Javac.plain()
                .with(new AbstractProcessor() {
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
                        if (running.get().isEmpty()) {
                            running.set(Optional.of(new Round(processingEnv, round)));
                            try {
                                invocation.proceed();
                            } catch (Throwable failure) {
                                thrown.set(Optional.of(failure));
                            }
                        }
                        return false;
                    }
                })
                .compile(sources(invoked.getExecutable()));
        if (thrown.get().isPresent()) {
            throw thrown.get().get();
        }
        if (running.get().isEmpty()) {
            invocation.skip();
        }
        compiled.orFail();
    }

    private static List<Source> sources(Method test) {
        List<Source> sources = AnnotationSupport.findAnnotation(test, Sources.class)
                .or(() -> AnnotationSupport.findAnnotation(test.getDeclaringClass(), Sources.class))
                .stream()
                .flatMap(named -> Stream.concat(
                        Stream.of(named.value()).map(Source::of),
                        Stream.of(named.libraries())
                                .flatMap(fixture ->
                                        Source.in(FIXTURES.resolve(fixture).resolve("lib")).stream())))
                .toList();
        return sources.isEmpty() ? List.of(Source.of(NOTHING)) : sources;
    }

    @SuppressWarnings("unchecked")
    private static AtomicReference<Optional<Round>> running(ExtensionContext context) {
        return context.getStore(NAMESPACE)
                .computeIfAbsent(Round.class, _ -> new AtomicReference<>(Optional.empty()), AtomicReference.class);
    }
}
