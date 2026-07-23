package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Path;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import static org.assertj.core.api.Assertions.assertThat;

/// compile-testing's in-memory file manager cannot resolve modular
/// compilation units (compiling a lone `module-info.java` through it fails
/// with `module name ... does not match expected name java.base`, a known
/// limitation unrelated to this library's rendered output). This test
/// instead drives the platform compiler directly against real files on disk,
/// which is how `module-info.java` compilation is actually meant to work.
class ModuleInfoCompileTest {

    @Test
    void moduleWithRequiresAndExportsCompiles(@TempDir Path sourceDir, @TempDir Path outputDir) throws IOException {
        ModuleFile moduleFile =
                ModuleFile.of("me.supcheg.example", mb -> mb.requires("java.base").exports("me.supcheg.example"));
        moduleFile.writeTo(sourceDir);

        // `exports` of a package with no types is a compile error, so the
        // module needs something real to export.
        JavaFile.of(ClassDesc.of("me.supcheg.example", "Marker"), cb -> {}).writeTo(sourceDir);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromPaths(List.of(
                    sourceDir.resolve("module-info.java"), sourceDir.resolve("me/supcheg/example/Marker.java")));

            boolean succeeded = compiler.getTask(
                            null,
                            fileManager,
                            diagnostics,
                            List.of("-d", outputDir.toString()),
                            null,
                            compilationUnits)
                    .call();

            List<Diagnostic<? extends JavaFileObject>> errors = diagnostics.getDiagnostics().stream()
                    .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                    .toList();
            assertThat(succeeded).as("javac errors: %s", errors).isTrue();
            assertThat(errors).isEmpty();
        }
    }
}
