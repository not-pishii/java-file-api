package me.supcheg.javafile.facts.processor.harness;

import com.google.testing.compile.Compiler;
import me.supcheg.javafile.facts.processor.FactsProcessor;
import org.opentest4j.AssertionFailedError;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/// A run of javac, described: what is on its classpath, its options and
/// its processors. A description is a value — every method returns another
/// one — and [#compile] runs it, each time anew; only a processor given to
/// [#with] is the same object in every run, though javac wants a new one.
///
/// ```java
/// Path lib = Javac.plain().compile(Source.in(sources)).orFail().writeTo(directory);
/// Compiled processed = Javac.facts().classpath(lib).options("-Ajavafile.facts.strict=true").compile(request);
/// processed.snapshot().verify(expected);
/// ```
///
/// @param classpath the entries before those of the tests themselves
/// @param isolated whether `classpath` is all there is, without the classpath of the tests
/// @param options the options of javac
/// @param processors the processors to run, before [FactsProcessor] if it runs
/// @param factsProcessor whether [FactsProcessor] runs
public record Javac(
        List<Path> classpath,
        boolean isolated,
        List<String> options,
        List<Processor> processors,
        boolean factsProcessor) {

    public Javac {
        classpath = List.copyOf(classpath);
        options = List.copyOf(options);
        processors = List.copyOf(processors);
    }

    /// javac without annotation processing.
    ///
    /// @return the description
    public static Javac plain() {
        return new Javac(List.of(), false, List.of(), List.of(), false);
    }

    /// javac with [FactsProcessor].
    ///
    /// @return the description
    public static Javac facts() {
        return new Javac(List.of(), false, List.of(), List.of(), true);
    }

    /// With more classpath entries: libraries, or what [Compiled#writeTo] wrote.
    ///
    /// @param entries directories of classes
    /// @return the description
    public Javac classpath(Path... entries) {
        return classpath(List.of(entries));
    }

    /// With more classpath entries.
    ///
    /// @param entries directories of classes
    /// @return the description
    public Javac classpath(List<Path> entries) {
        return new Javac(concat(classpath, entries), isolated, options, processors, factsProcessor);
    }

    /// Against the given classpath alone: the code compiled must need
    /// nothing of the tests, not even the facts.
    ///
    /// @return the description
    public Javac alone() {
        return new Javac(classpath, true, options, processors, factsProcessor);
    }

    /// With more options, such as `-Ajavafile.facts.package=…`.
    ///
    /// @param more the options
    /// @return the description
    public Javac options(String... more) {
        return options(List.of(more));
    }

    /// With more options.
    ///
    /// @param more the options
    /// @return the description
    public Javac options(List<String> more) {
        return new Javac(classpath, isolated, concat(options, more), processors, factsProcessor);
    }

    /// With every lint on and warnings as errors: javac must have nothing
    /// to say about the code.
    ///
    /// @return the description
    public Javac linted() {
        return options("-Xlint:all", "-Werror");
    }

    /// [#linted()], and with every check of doclint on what is `public` or
    /// `protected`: a link of a documentation comment that does not
    /// resolve, markup that is not well-formed, a declaration or a parameter
    /// without a comment — javac must have nothing to say about the comments
    /// either. What a generated metamodel must pass.
    ///
    /// @return the description
    public Javac documented() {
        return linted().options("-Xdoclint:all/protected");
    }

    /// With more processors, which run before [FactsProcessor].
    ///
    /// @param more the processors, each good for one run
    /// @return the description
    public Javac with(Processor... more) {
        return new Javac(classpath, isolated, options, concat(processors, List.of(more)), factsProcessor);
    }

    /// Runs javac on sources written in a test, see [Source#of].
    ///
    /// @param sources the sources
    /// @return what came of it
    public Compiled compile(String... sources) {
        return compile(Stream.of(sources).map(Source::of).toList());
    }

    /// Runs javac. If the processor runs and the system property
    /// [Dump#PROPERTY] names a directory, its output is written there.
    ///
    /// @param sources the sources
    /// @return what came of it
    public Compiled compile(List<Source> sources) {
        List<Processor> all = Stream.concat(
                        processors.stream(),
                        factsProcessor ? Stream.of(new FactsProcessor()) : Stream.<Processor>empty())
                .toList();
        Compiler compiler = Compiler.javac()
                .withClasspath(Stream.concat(classpath.stream().map(Path::toFile), isolated ? Stream.empty() : own())
                        .toList())
                .withOptions(all.isEmpty() ? concat(options, List.of("-proc:none")) : options);
        Compiled compiled = Compiled.of((all.isEmpty() ? compiler : compiler.withProcessors(all))
                .compile(sources.stream().map(Source::file).toList()));
        if (factsProcessor) {
            Dump.configured().ifPresent(dump -> dump.write(compiled.snapshot()));
        }
        return compiled;
    }

    /// Runs javac on a source of no interest and gives `action` what a
    /// processor of this description has in its first round: the types of
    /// this classpath under these options, through the environment. For
    /// what needs a compilation to run in, as a generator that is an
    /// annotation processor does — a target classpath above all.
    ///
    /// ```java
    /// String source = Javac.plain().alone().classpath(lib)
    ///         .inFirstRound(env -> render(TargetClasspaths.of(env)));
    /// ```
    ///
    /// @param action what to do in the round
    /// @param <T> what it gives
    /// @return what `action` returned; what it threw is thrown here, as it is
    public <T> T inFirstRound(Function<? super ProcessingEnvironment, ? extends T> action) {
        AtomicReference<Optional<Supplier<T>>> ran = new AtomicReference<>(Optional.empty());
        Compiled compiled = with(new AbstractProcessor() {
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
                        if (ran.get().isEmpty()) {
                            ran.set(Optional.of(attempted(() -> action.apply(processingEnv))));
                        }
                        return false;
                    }
                })
                .compile(List.of(new Source("probe.Probe", "package probe; class Probe {}")));
        return ran.get()
                .orElseThrow(
                        () -> new AssertionFailedError("javac did not get to its processors:\n" + compiled.rendered()))
                .get();
    }

    /// What an action came to, to be told once javac is done: its result,
    /// or what it threw.
    private static <T> Supplier<T> attempted(Supplier<? extends T> action) {
        try {
            T result = action.get();
            return () -> result;
        } catch (RuntimeException | Error thrown) {
            return () -> {
                throw thrown;
            };
        }
    }

    /// The classpath of the tests: the facts, the typed layer and the
    /// libraries of the tests; only what exists, or `-Xlint:path` warns.
    private static Stream<File> own() {
        return Pattern.compile(File.pathSeparator, Pattern.LITERAL)
                .splitAsStream(System.getProperty("java.class.path"))
                .map(File::new)
                .filter(File::exists);
    }

    private static <T> List<T> concat(List<? extends T> first, List<? extends T> second) {
        return Stream.<T>concat(first.stream(), second.stream()).toList();
    }
}
