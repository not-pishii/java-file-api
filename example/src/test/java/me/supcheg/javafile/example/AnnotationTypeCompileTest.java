package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.LiteralValue;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;

class AnnotationTypeCompileTest {

    @Test
    void annotationTypeWithDefaultElementCompiles() {
        JavaFile file = JavaFile.annotationType(
                ClassDesc.of("me.supcheg.example", "MaxLength"),
                ab -> ab.withElement("value", PrimitiveTypeRef.INT, new LiteralValue(new IntLiteral(255))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
