package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class StaticMemberAccessCompileTest {

    @Test
    void staticFieldReadStaticMethodCallAndStaticFieldAssignmentCompile() {
        ClassDesc selfDesc = ClassDesc.of("me.supcheg.example", "Registry");
        ClassOrInterfaceTypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        ClassOrInterfaceTypeRef mathType = Types.of(ClassDesc.of("java.lang", "Math"));
        ClassOrInterfaceTypeRef selfType = Types.of(selfDesc);

        JavaFile file = JavaFile.of(
                selfDesc,
                cb -> cb.withField(
                                "total",
                                PrimitiveTypeRef.INT,
                                fb -> fb.withModifiers(Modifier.STATIC).withInitializer(new IntLiteral(0)))
                        .withVoidMethod(
                                "update",
                                mb -> mb.withBody(b -> b.localVar(
                                                "max", PrimitiveTypeRef.INT, b.staticField(integerType, "MAX_VALUE"))
                                        .localVar(
                                                "bounded",
                                                PrimitiveTypeRef.INT,
                                                b.callStatic(mathType, "max", b.field("max"), b.literal(1)))
                                        .assign(b.staticField(selfType, "total"), b.field("bounded")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
