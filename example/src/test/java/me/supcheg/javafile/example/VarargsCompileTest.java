package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.literal;

class VarargsCompileTest {

    @Test
    void varargsParamAsLastParameterCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Summer"),
                cb -> cb.withMethod(
                        "sum",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withVarargsParam("values", PrimitiveTypeRef.INT)
                                .withBody(b -> b.return_(literal(0)))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
