package me.supcheg.javafile.testing;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

import static com.google.testing.compile.Compiler.javac;

/// The oracle of the tests: the Java a [JavaFile] renders, compiled by javac with every lint enabled, because the
/// library guarantees that what it renders compiles without a warning under `-Xlint:all`.
public final class RenderedJava {
    private RenderedJava() {}

    /// Compiles the rendered `file`, and `companions` that stand beside it, under every lint.
    ///
    /// @param file the rendered class
    /// @param companions the hand-written sources the rendered class refers to
    /// @return what javac said
    public static Compilation compile(JavaFile file, JavaFileObject... companions) {
        return compile(List.of(file), companions);
    }

    /// Compiles the rendered `files`, and `companions` that stand beside them, together under every lint.
    ///
    /// @param files the rendered classes
    /// @param companions the hand-written sources the rendered classes refer to
    /// @return what javac said
    public static Compilation compile(List<JavaFile> files, JavaFileObject... companions) {
        List<JavaFileObject> sources = new ArrayList<>();
        files.forEach(file -> sources.add(JavaFileObjects.forSourceString(file.qualifiedName(), file.render())));
        sources.addAll(List.of(companions));
        return javac().withOptions("-Xlint:all").compile(sources);
    }

    /// Asserts that javac accepts the rendered `file` under every lint and without a warning.
    ///
    /// @param file the rendered class
    /// @return what javac said
    public static Compilation assertCompilesCleanly(JavaFile file) {
        Compilation compilation = compile(file);
        CompilationSubject.assertThat(compilation).succeededWithoutWarnings();
        return compilation;
    }
}
