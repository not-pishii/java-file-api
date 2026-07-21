package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class CastConditionalClassLiteralCompileTest {

    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc CLASS = ClassDesc.of("java.lang", "Class");

    @Test
    void castConditionalAndClassLiteralCompile() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Describe"),
                cb -> cb.withMethod(
                        "kind",
                        Types.of(CLASS),
                        mb -> mb.withParam("value", Types.of(OBJECT))
                                .withBody(b -> b.localVar(
                                                "n", PrimitiveTypeRef.INT, b.cast(PrimitiveTypeRef.INT, b.literal(1.9)))
                                        .return_(b.cond(
                                                b.gt(b.field("n"), b.literal(0)),
                                                b.classLiteral(Types.of(OBJECT)),
                                                b.classLiteral(PrimitiveTypeRef.INT))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
