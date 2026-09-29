package me.supcheg.javafile.typed;

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
import static com.google.testing.compile.Compiler.javac;

/// A generated class compiled by javac with every lint enabled, together
/// with hand-written fixture classes, and loaded for running: an end-to-end
/// test asserts both that javac accepts the rendered code without a warning
/// and which member the running code actually reached.
///
/// @param source the rendered source of the generated class
/// @param loader loads the generated and the fixture classes
/// @param generated the generated class
record CompiledClasses(String source, ClassLoader loader, Class<?> generated) {

    /// Renders, compiles and loads `file`.
    ///
    /// @param file the generated class
    /// @param fixtures the fixture classes, as `{binary name, source}` pairs
    /// @return the loaded classes
    static CompiledClasses of(JavaFile file, String... fixtures) {
        String source = file.render();
        List<JavaFileObject> sources = new ArrayList<>();
        sources.add(JavaFileObjects.forSourceString(file.qualifiedName(), source));
        for (int i = 0; i < fixtures.length; i += 2) {
            sources.add(JavaFileObjects.forSourceString(fixtures[i], fixtures[i + 1]));
        }
        Compilation compilation = javac().withOptions("-Xlint:all").compile(sources);
        assertThat(compilation).succeededWithoutWarnings();
        ClassLoader loader = new GeneratedClassLoader(compilation);
        try {
            return new CompiledClasses(source, loader, loader.loadClass(file.qualifiedName()));
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
    Object invoke(String name, Object... args) throws Throwable {
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
    Object instantiate(String binaryName) throws ReflectiveOperationException {
        return loader.loadClass(binaryName).getConstructor().newInstance();
    }

    /// Defines the classes of a compilation, delegating everything else to
    /// the test's own loader.
    private static final class GeneratedClassLoader extends ClassLoader {
        private final Compilation compilation;

        GeneratedClassLoader(Compilation compilation) {
            super(CompiledClasses.class.getClassLoader());
            this.compilation = compilation;
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
