package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

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
                                                b.gt(b.field("low"), b.field("high")),
                                                ib -> ib.then(t -> t.throw_(t.new_(Types.of(
                                                        ClassDesc.of("java.lang", "IllegalArgumentException"))))))
                                        .assign(b.field(b.this_(), "low"), b.field("low"))
                                        .assign(b.field(b.this_(), "high"), b.field("high"))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
