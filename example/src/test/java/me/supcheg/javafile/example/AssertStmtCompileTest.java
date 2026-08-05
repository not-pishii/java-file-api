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
import static me.supcheg.javafile.code.Exprs.ge;
import static me.supcheg.javafile.code.Exprs.literal;

class AssertStmtCompileTest {

    @Test
    void assertWithMessageCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Guard"),
                cb -> cb.withVoidMethod(
                        "check",
                        mb -> mb.withParam("value", PrimitiveTypeRef.INT)
                                .withBody(b -> b.assert_(
                                        ge(field("value"), literal(0)), literal("value must be non-negative")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
