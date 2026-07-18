package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class SuperExprCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void subclassMethodCallingSuperMethodCompiles() {
        JavaFile shape = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Shape"),
                cb -> cb.withMethod(
                        "describe", Types.of(STRING), mb -> mb.withBody(b -> b.return_(b.literal("Shape")))));

        JavaFile circle =
                JavaFile.of(ClassDesc.of("me.supcheg.example", "Circle"), cb -> cb.withModifiers(Modifier.FINAL)
                        .withSuperclass(ClassDesc.of("me.supcheg.example", "Shape"))
                        .withMethod(
                                "describe",
                                Types.of(STRING),
                                mb -> mb.withBody(b ->
                                        b.return_(b.add(b.call(b.super_(), "describe"), b.literal(" -> Circle"))))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString(shape.qualifiedName(), shape.render()),
                        JavaFileObjects.forSourceString(circle.qualifiedName(), circle.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
