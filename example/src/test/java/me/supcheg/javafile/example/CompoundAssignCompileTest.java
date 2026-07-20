package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class CompoundAssignCompileTest {

    @Test
    void addAssignCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Accumulator"),
                cb -> cb.withMethod(
                        "addAll",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("total", PrimitiveTypeRef.INT)
                                .withParam("delta", PrimitiveTypeRef.INT)
                                .withBody(b -> b.assign(b.field("total"), AssignOp.ADD_ASSIGN, b.field("delta"))
                                        .return_(b.field("total")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
