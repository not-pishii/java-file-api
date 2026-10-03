package me.supcheg.javafile.facts.processor.harness;

import com.google.testing.compile.Compiler;
import me.supcheg.javafile.facts.processor.FactsProcessor;

import javax.annotation.processing.Processor;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
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
