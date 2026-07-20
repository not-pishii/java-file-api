package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class StatementExprCompileTest {

    @Test
    void postIncrementAndMethodCallAsStatementsCompile() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Counter"),
                cb -> cb.withField("count", PrimitiveTypeRef.INT, fb -> fb.withInitializer(new IntLiteral(0)))
                        .withVoidMethod(
                                "tick", mb -> mb.withBody(b -> b.exprStatement(b.postIncrement(b.field("count")))))
                        .withVoidMethod(
                                "tickTwice",
                                mb -> mb.withBody(
                                        b -> b.exprStatement(b.call("tick")).exprStatement(b.call("tick")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
