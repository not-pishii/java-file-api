package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.testing.RenderedJava;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.this_;

class SynchronizedCompileTest {

    @Test
    void synchronizedBlockOnThisCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Counter"),
                cb -> cb.withVoidMethod(
                        "increment",
                        mb -> mb.withBody(b -> b.synchronized_(this_(), sb -> sb.exprStatement(call("notifyAll"))))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
