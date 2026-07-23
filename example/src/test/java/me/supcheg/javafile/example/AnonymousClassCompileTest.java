package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class AnonymousClassCompileTest {

    private static final ClassDesc RUNNABLE = ClassDesc.of("java.lang", "Runnable");

    @Test
    void anonymousRunnableSubclassCompiles() {
        MethodDecl runMethod = new MethodDecl(
                "run",
                Optional.empty(),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(),
                me.supcheg.javafile.code.CodeBody.EMPTY,
                List.of());

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Tasks"),
                cb -> cb.withMethod(
                        "task",
                        Types.of(RUNNABLE),
                        mb -> mb.withBody(b -> b.return_(b.newAnonymous(Types.of(RUNNABLE), List.of(runMethod))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
