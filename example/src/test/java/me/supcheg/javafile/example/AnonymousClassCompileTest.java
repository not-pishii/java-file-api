package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.newAnonymous;

class AnonymousClassCompileTest {

    private static final ClassDesc RUNNABLE = ClassDesc.of("java.lang", "Runnable");

    @Test
    void anonymousRunnableSubclassCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Tasks"),
                cb -> cb.withMethod(
                        "task",
                        Types.of(RUNNABLE),
                        mb -> mb.withBody(b -> b.return_(newAnonymous(
                                Types.of(RUNNABLE), List.of(), acb -> acb.withVoidMethod("run", rm -> {}))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
