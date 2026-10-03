package me.supcheg.javafile.facts.processor.harness;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/// Writes the output of every run of the processor into a directory, so
/// that two runs of the tests — before a change of the processor and after
/// it — are compared with `diff -r`: the answer to "did the output of any
/// test change?", for the tests that keep no snapshot too.
///
/// ```
/// ./gradlew :java-file-api-facts-processor:test -Pfixtures.dump=build/dump/before
/// ```
///
/// A run is written into `<directory>/<Test>.<method>/<n>/`, as a
/// [Snapshot]: `<Test>.<method>` is the test that ran javac — the nearest
/// caller in a class named `…Test` — or `fixtures` for a case of a
/// fixture, whose test is made by the harness; `n` counts the runs of one
/// name from 1, so the names are the same in every run of the same tests.
///
/// @param directory where the runs are written
record Dump(Path directory) {

    /// The system property naming the directory, as `-Pfixtures.dump=…` of the build sets it.
    static final String PROPERTY = "fixtures.dump";

    private static final String TESTS = Dump.class.getPackageName().replaceFirst("\\.harness$", "");
    private static final Map<String, AtomicInteger> RUNS = new ConcurrentHashMap<>();

    /// The dump the system property asks for, if it does.
    static Optional<Dump> configured() {
        return Optional.ofNullable(System.getProperty(PROPERTY))
                .filter(directory -> !directory.isEmpty())
                .map(Path::of)
                .map(Dump::new);
    }

    /// Writes one run of the processor.
    void write(Snapshot snapshot) {
        String test = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames.filter(frame -> frame.getClassName().startsWith(TESTS + ".")
                                && frame.getDeclaringClass()
                                        .getNestHost()
                                        .getSimpleName()
                                        .endsWith("Test"))
                        .map(frame -> frame.getClassName().substring(TESTS.length() + 1) + "." + frame.getMethodName())
                        .findFirst())
                .orElse("fixtures");
        int run = RUNS.computeIfAbsent(test, _ -> new AtomicInteger()).incrementAndGet();
        snapshot.write(directory.resolve(test.replaceAll("[^\\w.$-]", "_")).resolve(String.valueOf(run)));
    }
}
