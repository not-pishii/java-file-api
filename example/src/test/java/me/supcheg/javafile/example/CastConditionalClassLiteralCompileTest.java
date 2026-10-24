package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.classLiteral;
import static me.supcheg.javafile.code.Exprs.cond;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.literal;

class CastConditionalClassLiteralCompileTest {

    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc CLASS = ClassDesc.of("java.lang", "Class");

    @Test
    void castConditionalAndClassLiteralCompile() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Describe"),
                cb -> cb.withMethod(
                        "kind",
                        Types.parameterized(CLASS, Types.unbounded()),
                        mb -> mb.withParam("value", Types.of(OBJECT))
                                .withBody(b -> b.localVar(
                                                "n", PrimitiveTypeRef.INT, cast(PrimitiveTypeRef.INT, literal(1.9)))
                                        .return_(cond(
                                                gt(field("n"), literal(0)),
                                                classLiteral(OBJECT),
                                                classLiteral(PrimitiveTypeRef.INT))))));

        Compilation compilation = RenderedJava.compile(file);

        assertThat(compilation).succeededWithoutWarnings();
    }
}
