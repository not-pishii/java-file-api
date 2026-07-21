package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.Modifier;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class LocalTypeCompileTest {

    @Test
    void localClassDeclarationInsideMethodCompiles() {
        ClassDecl localCounter = new ClassDecl(
                ClassDesc.of("Counter"),
                List.of(),
                Set.of(Modifier.FINAL),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Runner"),
                cb -> cb.withVoidMethod("run", mb -> mb.withBody(b -> b.localType(localCounter))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
