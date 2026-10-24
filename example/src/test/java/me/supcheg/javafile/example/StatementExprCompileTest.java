package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.postIncrement;

class StatementExprCompileTest {

    @Test
    void postIncrementAndMethodCallAsStatementsCompile() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Counter"),
                cb -> cb.withField("count", PrimitiveTypeRef.INT, fb -> fb.withInitializer(new IntLiteral(0)))
                        .withVoidMethod("tick", mb -> mb.withBody(b -> b.exprStatement(postIncrement(field("count")))))
                        .withVoidMethod(
                                "tickTwice",
                                mb -> mb.withBody(
                                        b -> b.exprStatement(call("tick")).exprStatement(call("tick")))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
