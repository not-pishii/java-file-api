package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.MethodRefExpr;
import me.supcheg.javafile.code.TypeMethodRefTarget;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class MethodRefCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc INTEGER = ClassDesc.of("java.lang", "Integer");
    private static final ClassDesc FUNCTION = ClassDesc.of("java.util.function", "Function");

    @Test
    void staticMethodRefAssignedToFunctionalInterfaceFieldCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Parsers"),
                cb -> cb.withField(
                        "toInt",
                        new ParameterizedTypeRef(
                                FUNCTION,
                                List.of(new ExactTypeArg(Types.of(STRING)), new ExactTypeArg(Types.of(INTEGER)))),
                        fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                                .withInitializer(
                                        new MethodRefExpr(new TypeMethodRefTarget(Types.of(INTEGER)), "parseInt"))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
