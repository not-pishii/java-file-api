package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class DiamondNewCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc LIST = ClassDesc.of("java.util", "List");
    private static final ClassDesc ARRAY_LIST = ClassDesc.of("java.util", "ArrayList");

    @Test
    void diamondInstantiationCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Holder"),
                cb -> cb.withVoidMethod(
                        "init",
                        mb -> mb.withBody(b -> b.localVar(
                                "names",
                                Types.parameterized(LIST, Types.exact(Types.of(STRING))),
                                b.newDiamond(ARRAY_LIST)))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
