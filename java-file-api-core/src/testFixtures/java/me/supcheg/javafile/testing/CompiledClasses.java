package me.supcheg.javafile.testing;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;

/// A generated class compiled by javac with every lint enabled, together
/// with hand-written fixture classes, and loaded for running: an end-to-end
/// test asserts both that javac accepts the rendered code without a warning
/// and which member the running code actually reached.
///
/// @param source the rendered source of the generated class
/// @param loader loads the generated and the fixture classes
/// @param generated the generated class
public record CompiledClasses(String source, ClassLoader loader, Class<?> generated) {

    /// Renders, compiles and loads `file`.
    ///
    /// @param file the generated class
    /// @param fixtures the fixture classes, as `{binary name, source}` pairs
    /// @return the loaded classes
    public static CompiledClasses of(JavaFile file, String... fixtures) {
        return ofAll(List.of(file), fixtures).getFirst();
    }

    /// Renders, compiles and loads `file` with assertions enabled or disabled for the classes of its loader.
    ///
    /// @param file the generated class
    /// @param assertionsEnabled whether the JVM checks the `assert` statements of the generated class
    /// @param fixtures the fixture classes, as `{binary name, source}` pairs
    /// @return the loaded classes
    public static CompiledClasses of(JavaFile file, boolean assertionsEnabled, String... fixtures) {
        return ofAll(List.of(file), assertionsEnabled, fixtures).getFirst();
    }

    /// Renders the `files` and compiles them together, in one run of javac, and loads each.
    ///
    /// @param files the generated classes
    /// @param fixtures the fixture classes, as `{binary name, source}` pairs
    /// @return the loaded classes, one for each of the `files`, in their order
    public static List<CompiledClasses> ofAll(List<JavaFile> files, String... fixtures) {
        return ofAll(files, false, fixtures);
    }

    private static List<CompiledClasses> ofAll(List<JavaFile> files, boolean assertionsEnabled, String... fixtures) {
        List<JavaFileObject> companions = new ArrayList<>();
        for (int i = 0; i < fixtures.length; i += 2) {
            companions.add(JavaFileObjects.forSourceString(fixtures[i], fixtures[i + 1]));
        }
        Compilation compilation = RenderedJava.compile(files, companions.toArray(JavaFileObject[]::new));
        assertThat(compilation).succeededWithoutWarnings();
        ClassLoader loader = new GeneratedClassLoader(compilation, assertionsEnabled);
        try {
            List<CompiledClasses> loaded = new ArrayList<>();
            for (JavaFile file : files) {
                loaded.add(new CompiledClasses(file.render(), loader, loader.loadClass(file.qualifiedName())));
            }
            return List.copyOf(loaded);
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    /// Invokes the method `name` of the generated class — static, or on a
    /// new instance — rethrowing what it throws.
    ///
    /// @param name the method name, unique in the class
    /// @param args the arguments
    /// @return the result
    public Object invoke(String name, Object... args) throws Throwable {
        Method method = Arrays.stream(generated.getMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("no method " + name + " in\n" + source));
        Object receiver = java.lang.reflect.Modifier.isStatic(method.getModifiers())
                ? null
                : generated.getConstructor().newInstance();
        try {
            return method.invoke(receiver, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    /// Instantiates a fixture class through its no-argument constructor.
    ///
    /// @param binaryName the class name
    /// @return the new instance
    public Object instantiate(String binaryName) throws ReflectiveOperationException {
        return loader.loadClass(binaryName).getConstructor().newInstance();
    }

    /// Defines the classes of a compilation, delegating everything else to
    /// the test's own loader.
    private static final class GeneratedClassLoader extends ClassLoader {
        private final Compilation compilation;

        GeneratedClassLoader(Compilation compilation, boolean assertionsEnabled) {
            super(CompiledClasses.class.getClassLoader());
            this.compilation = compilation;
            setDefaultAssertionStatus(assertionsEnabled);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            JavaFileObject file = compilation
                    .generatedFile(StandardLocation.CLASS_OUTPUT, name.replace('.', '/') + ".class")
                    .orElseThrow(() -> new ClassNotFoundException(name));
            try (InputStream in = file.openInputStream()) {
                byte[] bytes = in.readAllBytes();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        }
    }
}
