package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;

import java.util.List;

import static com.google.testing.compile.Compiler.javac;

final class Compilations {
    private Compilations() {}

    static void assertCompiles(List<JavaFile> files) {
        Compilation compilation = javac().compile(files.stream()
                .map(f -> JavaFileObjects.forSourceString(f.qualifiedName(), f.render()))
                .toList());
        CompilationSubject.assertThat(compilation).succeededWithoutWarnings();
    }
}
