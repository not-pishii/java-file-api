package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.this_;

class CanonicalConstructorCompileTest {

    @Test
    void explicitCanonicalConstructorWithValidationCompiles() {
        JavaFile file = JavaFile.record(
                ClassDesc.of("me.supcheg.example", "Range"),
                rb -> rb.withComponent("low", PrimitiveTypeRef.INT)
                        .withComponent("high", PrimitiveTypeRef.INT)
                        .withCanonicalConstructor(
                                List.of(
                                        new Param("low", PrimitiveTypeRef.INT),
                                        new Param("high", PrimitiveTypeRef.INT)),
                                b -> b.if_(
                                                gt(field("low"), field("high")),
                                                ib -> ib.then(t -> t.throw_(
                                                        new_(ClassDesc.of("java.lang", "IllegalArgumentException")))))
                                        .assign(this_().field("low"), field("low"))
                                        .assign(this_().field("high"), field("high"))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
