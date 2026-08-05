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
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.newArray;
import static me.supcheg.javafile.code.Exprs.newArrayOf;

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
                                    newArrayOf(PrimitiveTypeRef.INT, literal(1), literal(2), literal(3)));
                            b.localVar(
                                    "scratch",
                                    new ArrayTypeRef(PrimitiveTypeRef.INT),
                                    newArray(PrimitiveTypeRef.INT, literal(3)));
                            b.assign(
                                    field("scratch").arrayAccess(literal(0)),
                                    field("values").arrayAccess(literal(0)));
                            b.return_(field("scratch").arrayAccess(literal(0)));
                        })));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
