package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class NestedTypeCompileTest {

    @Test
    void staticNestedClassInsideClassCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Container"),
                cb -> cb.accept(new me.supcheg.javafile.model.ClassDecl(
                        ClassDesc.of("me.supcheg.example", "Item"),
                        java.util.List.of(),
                        java.util.Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        java.util.List.of(),
                        java.util.Optional.empty(),
                        java.util.List.of(),
                        java.util.List.of(),
                        java.util.List.of())));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
