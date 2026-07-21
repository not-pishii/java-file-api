package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class InitializerBlockCompileTest {

    @Test
    void staticAndInstanceInitializerBlocksCompile() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Config"), cb -> cb.withField(
                        "ready", PrimitiveTypeRef.BOOLEAN, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC))
                .withStaticInitializerBlock(b -> b.assign(b.field("ready"), b.literal(true)))
                .withField("id", PrimitiveTypeRef.INT, fb -> fb.withModifiers(Modifier.PRIVATE))
                .withInitializerBlock(b -> b.assign(b.field("id"), b.literal(1))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
