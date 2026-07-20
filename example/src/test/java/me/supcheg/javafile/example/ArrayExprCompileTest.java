package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class ArrayExprCompileTest {

    @Test
    void arrayCreationAccessAndInitializerCompile() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Arrays_"),
                cb -> cb.withMethod(
                        "firstOfThree",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withBody(b -> {
                            b.localVar(
                                    "values",
                                    new ArrayTypeRef(PrimitiveTypeRef.INT),
                                    b.newArrayOf(PrimitiveTypeRef.INT, b.literal(1), b.literal(2), b.literal(3)));
                            b.localVar(
                                    "scratch",
                                    new ArrayTypeRef(PrimitiveTypeRef.INT),
                                    b.newArray(PrimitiveTypeRef.INT, b.literal(3)));
                            b.assign(
                                    b.arrayAccess(b.field("scratch"), b.literal(0)),
                                    b.arrayAccess(b.field("values"), b.literal(0)));
                            b.return_(b.arrayAccess(b.field("scratch"), b.literal(0)));
                        })));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
