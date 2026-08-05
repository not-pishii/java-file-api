package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;

class UninitializedLocalCompileTest {

    @Test
    void typedLocalWithoutInitializerCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Loader"),
                cb -> cb.withMethod(
                        "load",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withBody(b -> {
                            b.localVar("result", PrimitiveTypeRef.INT);
                            b.assign(field("result"), literal(1));
                            b.return_(field("result"));
                        })));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
