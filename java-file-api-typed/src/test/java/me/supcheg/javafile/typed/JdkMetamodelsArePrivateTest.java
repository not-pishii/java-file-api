package me.supcheg.javafile.typed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The JDK metamodels the typed layer generates for itself are no public API of it: a consumer of the library
/// names none of them, the code of another package cannot, and no public signature of the module mentions one.
///
/// The consumer is compiled against the module's classes and the runtime classpath, as a published jar and its
/// dependencies would be, and runs from a class loader that has those alone.
class JdkMetamodelsArePrivateTest {

    /// The consumer of the typed API, which uses the metamodels the library keeps to itself through the public
    /// declarations that need them.
    private static final String USES_TYPED_API = """
            package consumer;

            import java.lang.constant.ClassDesc;

            import me.supcheg.javafile.JavaFile;
            import me.supcheg.javafile.facts.UnsafeFacts;
            import me.supcheg.javafile.typed.Expressions;
            import me.supcheg.javafile.typed.TypedClassBodies;
            import me.supcheg.javafile.typed.TypedClassBuilder;
            import me.supcheg.javafile.typed.TypedJavaFile;

            public final class UsesTypedApi {
                private UsesTypedApi() {}

                public static void main(String[] args) {
                    TypedJavaFile.typeParam("T");
                    Expressions.literal("x");
                    Expressions.narrowCheckedLongToInt(Expressions.literal(1L));
                    JavaFile file = TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(),
                            ClassDesc.of("consumer", "Generated"),
                            new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                    var constructor = cb.declareConstructor();
                                    var bodies = cb.bodies();
                                    bodies.define(constructor, (b, self) -> b.end());
                                    return bodies;
                                }
                            });
                    if (!file.render().contains("public final class Generated")) {
                        throw new AssertionError(file.render());
                    }
                }
            }
            """;

    @Test
    void aConsumerOfTheTypedApiCompilesAndRuns(@TempDir Path output) throws Exception {
        Files.createDirectories(output);
        Compiled compiled = compile(output, "consumer.UsesTypedApi", USES_TYPED_API);

        assertThat(compiled.errors()).isEmpty();
        assertThat(compiled.succeeded()).isTrue();
        run(compiled.output());
    }

    /// A consumer that names a generated metamodel of the library is refused by javac, whichever metamodel it
    /// names.
    @Test
    void aConsumerCannotNameAGeneratedMetamodel(@TempDir Path directory) throws Exception {
        for (String metamodel : List.of("Object_", "String_", "Math_")) {
            Path output = directory.resolve(metamodel);
            Files.createDirectories(output);
            String source = """
                    package consumer;

                    public final class Sees%s {
                        public static Object token() {
                            return me.supcheg.javafile.typed.%s.TOKEN;
                        }
                    }
                    """.formatted(metamodel, metamodel);

            Compiled compiled = compile(output, "consumer.Sees" + metamodel, source);

            assertThat(compiled.succeeded()).isFalse();
            assertThat(compiled.errors())
                    .anySatisfy(message -> assertThat(message)
                            .contains(metamodel + " is not public in me.supcheg.javafile.typed")
                            .contains("cannot be accessed from outside package"));
        }
    }

    /// Every metamodel the processor generated into the module is package-private, as its nested classes are
    /// reached only through it.
    @Test
    void everyGeneratedMetamodelIsPackagePrivate() throws Exception {
        Set<String> generated = generatedMetamodels();

        assertThat(generated)
                .contains(
                        "me.supcheg.javafile.typed.Math_",
                        "me.supcheg.javafile.typed.Object_",
                        "me.supcheg.javafile.typed.String_");
        for (String metamodel : generated) {
            Class<?> type = Class.forName(metamodel, false, getClass().getClassLoader());
            assertThat(Modifier.isPublic(type.getModifiers())).as(metamodel).isFalse();
        }
    }

    /// No public or protected signature of the module's classes mentions a generated metamodel.
    @Test
    void noPublicSignatureMentionsAGeneratedMetamodel() throws Exception {
        Set<String> generated = generatedMetamodels();
        List<String> mentions = new ArrayList<>();

        for (String name : moduleClasses()) {
            Class<?> type = Class.forName(name, false, getClass().getClassLoader());
            if (isExternallyVisible(type)) {
                scan(type, generated, mentions);
            }
        }

        assertThat(mentions).isEmpty();
    }

    /// Compiles one consumer source against the module's classes and the runtime classpath.
    private static Compiled compile(Path output, String binaryName, String source) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager files = compiler.getStandardFileManager(diagnostics, null, null)) {
            files.setLocationFromPaths(StandardLocation.CLASS_OUTPUT, List.of(output));
            Boolean succeeded = compiler.getTask(
                            null,
                            files,
                            diagnostics,
                            List.of("-proc:none", "-classpath", classpath()),
                            null,
                            List.of(new Source(binaryName, source)))
                    .call();
            List<String> errors = diagnostics.getDiagnostics().stream()
                    .filter(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR)
                    .map(diagnostic -> diagnostic.getMessage(null))
                    .toList();
            return new Compiled(Boolean.TRUE.equals(succeeded), errors, output);
        }
    }

    /// Runs the compiled consumer from a class loader that has the module and its dependencies alone.
    private static void run(Path output) throws Exception {
        List<URL> urls = Stream.concat(Stream.of(output), classpathEntries().stream())
                .map(Path::toUri)
                .map(uri -> {
                    try {
                        return uri.toURL();
                    } catch (IOException e) {
                        throw new AssertionError(e);
                    }
                })
                .toList();
        try (URLClassLoader loader =
                new URLClassLoader(urls.toArray(URL[]::new), ClassLoader.getPlatformClassLoader())) {
            Class<?> consumer = Class.forName("consumer.UsesTypedApi", true, loader);
            try {
                consumer.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
            } catch (InvocationTargetException e) {
                throw new AssertionError("the consumer of the typed API failed", e.getCause());
            }
        }
    }

    /// The entries a consumer of the typed layer needs: the module's classes and everything of the runtime
    /// classpath that is not a test output.
    private static String classpath() {
        return classpathEntries().stream().map(Path::toString).collect(Collectors.joining(File.pathSeparator));
    }

    private static List<Path> classpathEntries() {
        Path main = mainClasses();
        Set<Path> entries = new LinkedHashSet<>();
        entries.add(main);
        for (String entry :
                Pattern.compile(File.pathSeparator, Pattern.LITERAL).split(System.getProperty("java.class.path"))) {
            if (entry.isEmpty()) {
                continue;
            }
            Path path = Path.of(entry).toAbsolutePath().normalize();
            String name = path.toString().replace('\\', '/');
            if (name.equals(main.toAbsolutePath().normalize().toString().replace('\\', '/'))
                    || name.contains("/build/classes/java/test")
                    || name.contains("/build/classes/java/testFixtures")
                    || name.contains("/build/resources/test")
                    || name.contains("/build/generated/")) {
                continue;
            }
            entries.add(path);
        }
        return List.copyOf(entries);
    }

    private static Path mainClasses() {
        try {
            return Path.of(ClassDeclaration.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    /// The top-level classes the processor generated into the typed layer's package, by binary name.
    private static Set<String> generatedMetamodels() throws IOException {
        try (Stream<Path> files = Files.list(mainClasses().resolve("me/supcheg/javafile/typed"))) {
            return files.filter(file -> file.getFileName().toString().endsWith(".class"))
                    .map(file -> file.getFileName().toString())
                    .map(name -> {
                        int nested = name.indexOf('$');
                        return nested < 0
                                ? name.substring(0, name.length() - ".class".length())
                                : name.substring(0, nested);
                    })
                    .filter(name -> name.endsWith("_") || name.endsWith("_Heritage"))
                    .map(name -> "me.supcheg.javafile.typed." + name)
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    /// The classes of the module, by binary name, without its `package-info`.
    private static List<String> moduleClasses() throws IOException {
        Path main = mainClasses();
        try (Stream<Path> files = Files.walk(main)) {
            return files.filter(file -> file.toString().endsWith(".class"))
                    .map(main::relativize)
                    .map(Path::toString)
                    .map(name -> name.substring(0, name.length() - ".class".length()))
                    .map(name -> name.replace(File.separatorChar, '.'))
                    .filter(name -> !name.endsWith("package-info") && !name.endsWith("module-info"))
                    .toList();
        }
    }

    /// Whether the class, and every class that encloses it, is `public`.
    private static boolean isExternallyVisible(Class<?> type) {
        for (Class<?> current = type; current != null; current = current.getEnclosingClass()) {
            if (!Modifier.isPublic(current.getModifiers())) {
                return false;
            }
        }
        return true;
    }

    /// Adds to `mentions` every generated class a public or protected signature of `type` names.
    private static void scan(Class<?> type, Set<String> generated, List<String> mentions) {
        Set<Type> seen = Collections.newSetFromMap(new IdentityHashMap<>());
        mention(type.getGenericSuperclass(), generated, mentions, seen);
        for (Type implemented : type.getGenericInterfaces()) {
            mention(implemented, generated, mentions, seen);
        }
        for (TypeVariable<?> parameter : type.getTypeParameters()) {
            mentionBounds(parameter, generated, mentions, seen);
        }
        for (Field field : type.getDeclaredFields()) {
            if (exposed(field.getModifiers())) {
                mention(field.getGenericType(), generated, mentions, seen);
            }
        }
        for (Method method : type.getDeclaredMethods()) {
            if (exposed(method.getModifiers())) {
                mention(method.getGenericReturnType(), generated, mentions, seen);
                for (Type parameter : method.getGenericParameterTypes()) {
                    mention(parameter, generated, mentions, seen);
                }
                for (Type thrown : method.getGenericExceptionTypes()) {
                    mention(thrown, generated, mentions, seen);
                }
                for (TypeVariable<?> parameter : method.getTypeParameters()) {
                    mentionBounds(parameter, generated, mentions, seen);
                }
            }
        }
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (exposed(constructor.getModifiers())) {
                for (Type parameter : constructor.getGenericParameterTypes()) {
                    mention(parameter, generated, mentions, seen);
                }
                for (Type thrown : constructor.getGenericExceptionTypes()) {
                    mention(thrown, generated, mentions, seen);
                }
                for (TypeVariable<?> parameter : constructor.getTypeParameters()) {
                    mentionBounds(parameter, generated, mentions, seen);
                }
            }
        }
    }

    private static boolean exposed(int modifiers) {
        return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers);
    }

    private static void mentionBounds(
            TypeVariable<?> variable, Set<String> generated, List<String> mentions, Set<Type> seen) {
        for (Type bound : variable.getBounds()) {
            mention(bound, generated, mentions, seen);
        }
    }

    /// Adds to `mentions` the name of a generated class the type names, however deep in its type arguments,
    /// bounds and arrays it is. A type is read once: a type variable bounds itself through a parameterized type.
    private static void mention(Type type, Set<String> generated, List<String> mentions, Set<Type> seen) {
        if (type == null || !seen.add(type)) {
            return;
        }
        switch (type) {
            case Class<?> cls -> {
                if (cls.isArray()) {
                    mention(cls.getComponentType(), generated, mentions, seen);
                } else if (generated.stream()
                        .anyMatch(name ->
                                cls.getName().equals(name) || cls.getName().startsWith(name + "$"))) {
                    mentions.add(cls.getName());
                }
            }
            case ParameterizedType parameterized -> {
                mention(parameterized.getRawType(), generated, mentions, seen);
                for (Type argument : parameterized.getActualTypeArguments()) {
                    mention(argument, generated, mentions, seen);
                }
            }
            case GenericArrayType array -> mention(array.getGenericComponentType(), generated, mentions, seen);
            case TypeVariable<?> variable -> mentionBounds(variable, generated, mentions, seen);
            case WildcardType wildcard -> {
                for (Type bound : wildcard.getUpperBounds()) {
                    mention(bound, generated, mentions, seen);
                }
                for (Type bound : wildcard.getLowerBounds()) {
                    mention(bound, generated, mentions, seen);
                }
            }
            default -> {}
        }
    }

    /// A consumer source written in the test.
    private static final class Source extends SimpleJavaFileObject {
        private final String source;

        Source(String binaryName, String source) {
            super(URI.create("string:///" + binaryName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }
    }

    /// What a consumer compilation said.
    ///
    /// @param succeeded whether javac reported no error
    /// @param errors the messages of the errors, in order
    /// @param output the directory of the classes
    private record Compiled(boolean succeeded, List<String> errors, Path output) {}
}
