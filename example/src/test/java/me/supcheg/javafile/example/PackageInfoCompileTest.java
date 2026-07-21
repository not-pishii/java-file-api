package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class PackageInfoCompileTest {

    @Test
    void annotatedPackageInfoCompiles() {
        PackageInfoFile file = PackageInfoFile.of(
                "me.supcheg.example", new AnnotationUse(ClassDesc.of("java.lang", "Deprecated"), List.of()));

        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString("me.supcheg.example.package-info", file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
