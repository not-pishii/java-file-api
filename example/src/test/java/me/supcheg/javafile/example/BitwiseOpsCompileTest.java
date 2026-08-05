package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.bitAnd;
import static me.supcheg.javafile.code.Exprs.bitNot;
import static me.supcheg.javafile.code.Exprs.bitOr;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.shl;

class BitwiseOpsCompileTest {

    @Test
    void bitwiseAndShiftOperatorsCompile() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Bits"),
                cb -> cb.withMethod(
                        "pack",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("high", PrimitiveTypeRef.INT)
                                .withParam("low", PrimitiveTypeRef.INT)
                                .withBody(b -> b.return_(bitOr(
                                        shl(field("high"), literal(16)), bitAnd(field("low"), bitNot(literal(0))))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
