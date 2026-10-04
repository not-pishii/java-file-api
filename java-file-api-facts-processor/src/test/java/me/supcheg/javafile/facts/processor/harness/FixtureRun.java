package me.supcheg.javafile.facts.processor.harness;

import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetReader;
import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.langmodel.mirror.TargetClasspaths;
import me.supcheg.javafile.typed.Expr;
import me.supcheg.javafile.typed.TypedClassBuilder;
import me.supcheg.javafile.typed.TypedJavaFile;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.constant.ClassDesc;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The tests of a [Fixture]: one for `expected-jdk/`, the metamodels of the
/// JDK the cases share, see [Snapshot#common], and of every case, in this order:
///
/// 1. the processor runs on `request/` against the library, and its output
///    — sources, index, diagnostics — is the [Snapshot] in `expected/`,
///    but for the files of the JDK that are those of `expected-jdk/`;
/// 2. the generated sources compile on their own under `-Xlint:all
///    -Xdoclint:all/protected -Werror`: every link of their comments
///    resolves, and nothing `public` is without one;
/// 3. `use/` compiles against the metamodels under `-Xlint:all -Werror`,
///    and every check of it runs: a `public static void` method of a
///    `public` class, without parameters or with one [Typed], that fails
///    by throwing; the typed layer of a [Typed] renders against the
///    library of the case as the target classpath, or against another
///    version of it, a directory of `targets/`;
/// 4. every file of `use-fails/` does not compile, with the errors its
///    comments tell, see [Rejection].
///
/// Steps 2–4 are there only if the processor did not fail: a case whose
/// processor fails has `expected/diagnostics.txt` and neither `use/` nor
/// `use-fails/`. The library is compiled once for all the cases, and each
/// case runs the processor once.
public final class FixtureRun {

    private final Fixture fixture;
    private final Supplier<Path> library;
    private final Map<String, CaseRun> cases;
    private final Supplier<Snapshot> common;

    private FixtureRun(Fixture fixture, Path work) {
        this.fixture = fixture;
        this.library = Memo.of(
                () -> Javac.plain().compile(Source.in(fixture.lib())).orFail().writeTo(work.resolve("lib")));
        this.cases = fixture.cases().stream()
                .collect(Collectors.toMap(
                        Fixture.Case::name,
                        each -> new CaseRun(each, work.resolve("cases").resolve(each.name()))));
        this.common = Memo.of(() -> Snapshot.common(fixture.cases().stream()
                .map(each -> cases.get(each.name()).processed.get().snapshot())
                .toList()));
    }

    /// What the cases share: the files of the JDK as the cases write them
    /// when the snapshots are accepted, and as `expected-jdk/` holds them
    /// when they are compared — a case is compared on its own.
    private Snapshot shared() {
        return Snapshot.updating() ? common.get() : Snapshot.read(fixture.shared());
    }

    /// The tests of the fixtures of a directory: a container per
    /// fixture, and in it one per case unless the fixture is its only case.
    ///
    /// @param fixtures the directory of the fixtures, `src/test/fixtures`
    /// @param work a directory for what the tests compile
    /// @param only the fixture, `plain`, or the case of one, `inheritance/hidden`, to leave; every one if empty
    /// @return the tests, made as they run
    public static Stream<DynamicNode> tests(Path fixtures, Path work, Optional<String> only) {
        return Fixture.all(fixtures).stream()
                .map(fixture -> new FixtureRun(fixture, work.resolve(fixture.name())))
                .flatMap(run -> run.container(only));
    }

    private Stream<DynamicNode> container(Optional<String> only) {
        List<Fixture.Case> wanted = fixture.cases().stream()
                .filter(each -> only.map(
                                name -> name.equals(fixture.name()) || name.equals(fixture.name() + "/" + each.name()))
                        .orElse(true))
                .toList();
        if (wanted.isEmpty()) {
            return Stream.empty();
        }
        return Stream.of(DynamicContainer.dynamicContainer(
                fixture.name(),
                fixture.directory().toUri(),
                Stream.concat(
                        Stream.of(DynamicTest.dynamicTest(
                                fixture.name() + ": the metamodels of the JDK are expected-jdk/",
                                fixture.shared().toUri(),
                                () -> common.get().verify(fixture.shared()))),
                        fixture.single()
                                ? wanted.stream()
                                        .flatMap(each -> cases.get(each.name()).tests())
                                : wanted.stream()
                                        .map(each -> DynamicContainer.dynamicContainer(
                                                each.name(),
                                                each.directory().toUri(),
                                                cases.get(each.name()).tests())))));
    }

    /// One case as it runs: every step is made once, by the first test that needs it.
    private final class CaseRun {
        private final Fixture.Case fixtureCase;
        private final Path work;
        private final Supplier<Path> caseLibrary;
        private final Supplier<Compiled> processed;
        private final Supplier<Path> metamodels;
        private final Supplier<ClassLoader> used;

        CaseRun(Fixture.Case fixtureCase, Path work) {
            this.fixtureCase = fixtureCase;
            this.work = work;
            this.caseLibrary = Memo.of(this::compileLibrary);
            this.processed = Memo.of(() -> Javac.facts()
                    .classpath(libraries())
                    .options(fixtureCase.options())
                    .compile(Source.in(fixtureCase.request())));
            this.metamodels = Memo.of(this::compileMetamodels);
            this.used = Memo.of(this::compileUse);
        }

        /// A test named after the case too, as a flat report — that of
        /// Gradle — shows the names without their containers.
        private DynamicNode test(String name, Path source, Executable executable) {
            return DynamicTest.dynamicTest(
                    (fixture.single() ? fixture.name() : fixture.name() + "/" + fixtureCase.name()) + ": " + name,
                    source.toUri(),
                    executable);
        }

        Stream<DynamicNode> tests() {
            return Stream.concat(
                    Stream.of(test(
                            "the output of the processor is expected/",
                            fixtureCase.expected(),
                            () -> processed.get().snapshot().verify(fixtureCase.expected(), shared()))),
                    after(() -> processed.get().succeeded() ? afterTheMetamodels() : afterTheFailure()));
        }

        private Stream<DynamicNode> afterTheMetamodels() {
            List<Source> use = Source.in(fixtureCase.use());
            return Stream.of(
                            Stream.of(test(
                                    "the metamodels compile under -Xlint:all -Xdoclint:all/protected -Werror",
                                    fixtureCase.expected(),
                                    metamodels::get)),
                            use.isEmpty()
                                    ? Stream.<DynamicNode>empty()
                                    : Stream.concat(
                                            Stream.of(test(
                                                    "use/ compiles under -Xlint:all -Werror",
                                                    fixtureCase.use(),
                                                    used::get)),
                                            after(() -> checks(use))),
                            Source.in(fixtureCase.useFails()).stream()
                                    .map(source -> test(
                                            "use-fails/" + source.path(),
                                            fixtureCase.useFails().resolve(source.path()),
                                            () -> Rejection.verify(
                                                    source,
                                                    Javac.plain()
                                                            .classpath(classpath())
                                                            .compile(List.of(source))))))
                    .flatMap(Function.identity());
        }

        private Stream<DynamicNode> afterTheFailure() {
            return Stream.of(fixtureCase.use(), fixtureCase.useFails())
                    .filter(Files::isDirectory)
                    .map(directory ->
                            test(directory.getFileName() + "/ is not there, as the processor fails", directory, () -> {
                                throw new AssertionFailedError("the processor fails, so there are no metamodels for "
                                        + directory.getFileName() + "/:\n"
                                        + processed.get().rendered());
                            }));
        }

        /// The checks of `use/`: one test per method, named `Class.method`.
        private Stream<DynamicNode> checks(List<Source> use) {
            ClassLoader loader = used.get();
            Typed typed = new ThroughTyped(caseLibrary, work, this::against, false);
            List<DynamicNode> checks = use.stream()
                    .flatMap(source -> {
                        Class<?> type = load(loader, source.name());
                        return Arrays.stream(type.getDeclaredMethods())
                                .filter(method -> isCheck(type, method))
                                .sorted(Comparator.comparing(Method::getName))
                                .map(method -> test(
                                        "use/" + source.simpleName() + "." + method.getName(),
                                        fixtureCase.use().resolve(source.path()),
                                        () -> run(method, typed)));
                    })
                    .toList();
            return checks.isEmpty()
                    ? Stream.of(test("use/ has checks", fixtureCase.use(), () -> {
                        throw new AssertionFailedError("no public class of use/ has a public static void method"
                                + " without parameters or with one " + Typed.class.getSimpleName());
                    }))
                    : checks.stream();
        }

        private static boolean isCheck(Class<?> type, Method method) {
            int modifiers = method.getModifiers();
            return Modifier.isPublic(type.getModifiers())
                    && Modifier.isPublic(modifiers)
                    && Modifier.isStatic(modifiers)
                    && !method.isSynthetic()
                    && method.getReturnType() == void.class
                    && (method.getParameterCount() == 0
                            || List.of(method.getParameterTypes()).equals(List.of(Typed.class)));
        }

        private static void run(Method check, Typed typed) throws Throwable {
            try {
                check.invoke(null, check.getParameterCount() == 0 ? new Object[0] : new Object[] {typed});
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }

        /// The library of the fixture, or, for a case with a `lib/` of
        /// its own, the library with those sources instead of its own.
        private Path compileLibrary() {
            List<Source> own = Source.in(fixtureCase.lib());
            if (own.isEmpty() || fixtureCase.directory().equals(fixture.directory())) {
                return library.get();
            }
            return Javac.plain()
                    .compile(Source.overlaid(fixtureCase.lib(), fixture.lib()))
                    .orFail()
                    .writeTo(work.resolve("lib"));
        }

        /// [Typed] against `targets/<version>/` of the case: the library
        /// with the files of the version instead of its own.
        private Typed against(String version) {
            Path target = fixtureCase.target(version);
            if (!Files.isDirectory(target)) {
                throw new IllegalStateException("case " + fixtureCase.name() + " of fixture " + fixture.name()
                        + " has no targets/" + version + "/");
            }
            Path directory = work.resolve("targets").resolve(version);
            return new ThroughTyped(
                    Memo.of(() -> Javac.plain()
                            .compile(Source.overlaid(target, fixtureCase.lib(), fixture.lib()))
                            .orFail()
                            .writeTo(directory.resolve("lib"))),
                    directory,
                    this::against,
                    true);
        }

        /// What the processor runs against: the library, and the output of
        /// the cases `request/classpath.txt` names.
        private List<Path> libraries() {
            return Stream.concat(
                            Stream.of(caseLibrary.get()),
                            fixtureCase.classpath().stream()
                                    .map(name -> Optional.ofNullable(cases.get(name))
                                            .orElseThrow(() -> new IllegalStateException("fixture " + fixture.name()
                                                    + " has no case " + name + ", which " + fixtureCase.name()
                                                    + "/request/classpath.txt names"))
                                            .metamodels
                                            .get()))
                    .toList();
        }

        /// What code that uses the metamodels compiles against.
        private List<Path> classpath() {
            return Stream.concat(Stream.of(metamodels.get()), libraries().stream())
                    .toList();
        }

        /// The class output of the case as the jar of its module would
        /// hold it — the index and the metamodels — with the metamodels
        /// compiled again, on their own, with every lint and every check of
        /// their documentation comments on.
        private Path compileMetamodels() {
            Compiled output = processed.get().orFail();
            Path directory = output.writeTo(work.resolve("metamodels"));
            return output.generated().isEmpty()
                    ? directory
                    : Javac.plain()
                            .documented()
                            .classpath(libraries())
                            .compile(output.generated())
                            .clean()
                            .writeTo(directory);
        }

        private ClassLoader compileUse() {
            Path directory = Javac.plain()
                    .linted()
                    .classpath(classpath())
                    .compile(Source.in(fixtureCase.use()))
                    .clean()
                    .writeTo(work.resolve("use"));
            URLClassLoader loader = new URLClassLoader(
                    Stream.concat(Stream.of(directory), classpath().stream())
                            .map(FixtureRun::url)
                            .toArray(URL[]::new),
                    FixtureRun.class.getClassLoader());
            loader.setDefaultAssertionStatus(true);
            return loader;
        }
    }

    /// [Typed] for the checks of a case.
    ///
    /// @param library the library the typed layer renders against, compiled
    /// @param work where the rendered classes are compiled into
    /// @param versions the typed layer against another version of the library, by its name
    /// @param another whether `library` is another version than the one the checks were compiled with
    private record ThroughTyped(Supplier<Path> library, Path work, Function<String, Typed> versions, boolean another)
            implements Typed {

        /// What came of rendering: the source, or why the typed layer
        /// rejected the facts, and what the target classpath was asked.
        private sealed interface Rendering {
            List<String> verified();

            record Rendered(String source, List<String> verified) implements Rendering {}

            record Rejected(RuntimeException reason, List<String> verified) implements Rendering {}
        }

        /// Renders in a compilation that has the library alone, against
        /// its target classpath.
        private <R, P> Rendering rendering(
                TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body) {
            return Javac.plain()
                    .alone()
                    .classpath(library.get())
                    .inFirstRound(processing -> render(processing, result, parameter, body));
        }

        private static <R, P> Rendering render(
                ProcessingEnvironment processing,
                TypeToken<R> result,
                TypeToken<P> parameter,
                Function<Expr<P>, Expr<R>> body) {
            List<String> verified = new CopyOnWriteArrayList<>();
            TargetReader reader = TargetClasspaths.reader(processing.getElementUtils(), processing.getTypeUtils());
            TargetClasspath target = UnsafeFacts.targetClasspath((shape, origin) -> {
                TargetType found = reader.read(shape, origin);
                verified.add(shape.desc().packageName() + "." + shape.desc().displayName() + ": "
                        + switch (found) {
                            case TargetType.Unchanged _ -> "unchanged";
                            case TargetType.Changed _ -> "changed";
                            case TargetType.Mismatched _ -> "mismatched";
                        });
                return found;
            });
            try {
                String source = TypedJavaFile.class_(
                                target, ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                                    @Override
                                    public <Self> void build(TypedClassBuilder<Self> cb) {
                                        cb.staticMethod("go", result, parameter, (b, p) -> b.return_(body.apply(p)));
                                    }
                                })
                        .render();
                return new Rendering.Rendered(source, List.copyOf(verified));
            } catch (RuntimeException rejected) {
                return new Rendering.Rejected(rejected, List.copyOf(verified));
            }
        }

        @Override
        public <R, P> String render(TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body) {
            return switch (rendering(result, parameter, body)) {
                case Rendering.Rendered(String source, List<String> _) -> source;
                case Rendering.Rejected(RuntimeException reason, List<String> _) -> throw reason;
            };
        }

        @Override
        public <R, P> List<String> verified(
                TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body) {
            return rendering(result, parameter, body).verified();
        }

        @Override
        public Typed against(String version) {
            return versions.apply(version);
        }

        @Override
        public <R, P> Object apply(
                TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body, P argument) {
            ClassLoader ofTheArgument = argument.getClass().getClassLoader();
            if (another && ofTheArgument != null && ofTheArgument != ClassLoader.getPlatformClassLoader()) {
                throw new IllegalArgumentException("an argument of another version of the library than "
                        + argument.getClass().getName() + " is needed: against a version, give an argument of the"
                        + " JDK and make what the body needs of the library in the body");
            }
            String source = render(result, parameter, body);
            Compiled compiled = Javac.plain()
                    .alone()
                    .linted()
                    .classpath(library.get())
                    .compile(List.of(new Source("out.Out", source)));
            if (!compiled.succeeded() || !compiled.diagnostics().isEmpty()) {
                throw new AssertionFailedError("javac has something to say about what the typed layer rendered:\n"
                        + compiled.rendered() + "\n" + source);
            }
            try {
                Files.createDirectories(work);
                Path directory = compiled.writeTo(Files.createTempDirectory(work, "typed"));
                // the library too: the parent of the loader of an argument of the JDK sees none of it
                try (URLClassLoader loader =
                        new URLClassLoader(new URL[] {url(directory), url(library.get())}, ofTheArgument)) {
                    return Arrays.stream(loader.loadClass("out.Out").getMethods())
                            .filter(method -> method.getName().equals("go"))
                            .findFirst()
                            .orElseThrow()
                            .invoke(null, argument);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (InvocationTargetException e) {
                throw new AssertionFailedError("what the typed layer rendered threw:\n" + source, e.getCause());
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /// The tests that can be told only after the test before them has
    /// run; none if what they need fails, as that test has failed with
    /// the same.
    private static Stream<DynamicNode> after(Supplier<Stream<DynamicNode>> tests) {
        return Stream.of(tests).flatMap(supplier -> {
            try {
                return supplier.get();
            } catch (RuntimeException | AssertionError e) {
                return Stream.empty();
            }
        });
    }

    private static Class<?> load(ClassLoader loader, String name) {
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static URL url(Path directory) {
        try {
            return directory.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
}
