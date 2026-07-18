package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class SynchronizedCompileTest {

    @Test
    void synchronizedBlockOnThisCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Counter"),
                cb -> cb.withVoidMethod(
                        "increment",
                        mb -> mb.withBody(b -> b.synchronized_(b.this_(), sb -> sb.exprStatement(sb.call("notifyAll"))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
