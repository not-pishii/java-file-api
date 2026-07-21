package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class BitwiseOpsCompileTest {

    @Test
    void bitwiseAndShiftOperatorsCompile() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Bits"),
                cb -> cb.withMethod(
                        "pack",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("high", PrimitiveTypeRef.INT)
                                .withParam("low", PrimitiveTypeRef.INT)
                                .withBody(b -> b.return_(b.bitOr(
                                        b.shl(b.field("high"), b.literal(16)),
                                        b.bitAnd(b.field("low"), b.bitNot(b.literal(0))))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
