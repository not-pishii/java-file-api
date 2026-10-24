package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;

class InitializerBlockCompileTest {

    @Test
    void staticAndInstanceInitializerBlocksCompile() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Config"),
                cb -> cb.withField(
                                "ready",
                                PrimitiveTypeRef.BOOLEAN,
                                fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC))
                        .withStaticInitializerBlock(b -> b.assign(field("ready"), literal(true)))
                        .withField("id", PrimitiveTypeRef.INT, fb -> fb.withModifiers(Modifier.PRIVATE))
                        .withInitializerBlock(b -> b.assign(field("id"), literal(1))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
