package me.supcheg.javafile.example;

import javax.tools.DiagnosticCollector;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/// Compiles a single Java source string in-memory and loads the resulting
/// class, so generated code can be executed rather than only checked for
/// compilability.
final class InMemoryCompiler {

    private InMemoryCompiler() {}

    static Class<?> compileAndLoad(String qualifiedName, String source) throws ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Map<String, byte[]> classBytes = new HashMap<>();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);

        ForwardingJavaFileManager<StandardJavaFileManager> fileManager =
                new ForwardingJavaFileManager<>(standardFileManager) {
                    @Override
                    public JavaFileObject getJavaFileForOutput(
                            Location location,
                            String className,
                            JavaFileObject.Kind kind,
                            javax.tools.FileObject sibling) {
                        return new SimpleJavaFileObject(URI.create("bytes:///" + className), kind) {
                            @Override
                            public OutputStream openOutputStream() {
                                return new ByteArrayOutputStream() {
                                    @Override
                                    public void close() {
                                        classBytes.put(className, toByteArray());
                                    }
                                };
                            }
                        };
                    }
                };

        JavaFileObject sourceFile =
                new SimpleJavaFileObject(
                        URI.create("string:///" + qualifiedName.replace('.', '/') + ".java"),
                        JavaFileObject.Kind.SOURCE) {
                    @Override
                    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                        return source;
                    }
                };

        boolean success = compiler.getTask(null, fileManager, diagnostics, null, null, List.of(sourceFile))
                .call();
        if (!success) {
            String diagnosticText =
                    diagnostics.getDiagnostics().stream().map(Object::toString).collect(Collectors.joining("\n"));
            throw new IllegalStateException("Compilation failed:\n" + diagnosticText + "\n\nSource:\n" + source);
        }

        ClassLoader classLoader = new ClassLoader(InMemoryCompiler.class.getClassLoader()) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] bytes = classBytes.get(name);
                if (bytes == null) {
                    return super.findClass(name);
                }
                return defineClass(name, bytes, 0, bytes.length);
            }
        };
        return Class.forName(qualifiedName, true, classLoader);
    }
}
