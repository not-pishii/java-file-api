package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.Pattern;
import me.supcheg.javafile.code.RecordPattern;
import me.supcheg.javafile.code.TypePattern;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class RecordPatternCompileTest {

    private static final ClassDesc POINT = ClassDesc.of("me.supcheg.example", "Point");
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");

    @Test
    void recordDeconstructionInInstanceofCompiles() {
        JavaFile pointFile = JavaFile.record(
                POINT, rb -> rb.withComponent("x", PrimitiveTypeRef.INT).withComponent("y", PrimitiveTypeRef.INT));

        Pattern pattern = new RecordPattern(
                Types.of(POINT),
                List.of(
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("x")),
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("y"))));
        JavaFile useFile = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Describe"),
                cb -> cb.withMethod(
                        "sum",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("value", Types.of(OBJECT))
                                .withBody(b -> b.if_(
                                                b.instanceOfPattern(b.field("value"), pattern),
                                                ib -> ib.then(t -> t.return_(t.add(t.field("x"), t.field("y")))))
                                        .return_(b.literal(0)))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString(pointFile.qualifiedName(), pointFile.render()),
                        JavaFileObjects.forSourceString(useFile.qualifiedName(), useFile.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
