package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.staticField;

class StaticMemberAccessCompileTest {

    @Test
    void staticFieldReadStaticMethodCallAndStaticFieldAssignmentCompile() {
        ClassDesc selfDesc = ClassDesc.of("me.supcheg.example", "Registry");
        ClassDesc integerType = ClassDesc.of("java.lang", "Integer");
        ClassDesc mathType = ClassDesc.of("java.lang", "Math");

        JavaFile file = JavaFile.class_(
                selfDesc,
                cb -> cb.withField(
                                "total",
                                PrimitiveTypeRef.INT,
                                fb -> fb.withModifiers(Modifier.STATIC).withInitializer(new IntLiteral(0)))
                        .withVoidMethod(
                                "update",
                                mb -> mb.withBody(b -> b.localVar(
                                                "max", PrimitiveTypeRef.INT, staticField(integerType, "MAX_VALUE"))
                                        .localVar(
                                                "bounded",
                                                PrimitiveTypeRef.INT,
                                                Exprs.staticCall(mathType, "max", field("max"), literal(1)))
                                        .assign(staticField(selfDesc, "total"), field("bounded")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
