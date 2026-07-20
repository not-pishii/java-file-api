package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class UninitializedLocalCompileTest {

    @Test
    void typedLocalWithoutInitializerCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Loader"),
                cb -> cb.withMethod(
                        "load",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withBody(b -> {
                            b.localVar("result", PrimitiveTypeRef.INT);
                            b.assign(b.field("result"), b.literal(1));
                            b.return_(b.field("result"));
                        })));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
