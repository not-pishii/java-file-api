package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class VarargsCompileTest {

    @Test
    void varargsParamAsLastParameterCompiles() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Summer"), cb -> cb.withMethod(
                "sum",
                PrimitiveTypeRef.INT,
                mb -> mb.withVarargsParam("values", PrimitiveTypeRef.INT)
                        .withBody(b -> b.return_(b.literal(0)))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
