package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class TryCatchFinallyCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc SQL_EXCEPTION = ClassDesc.of("java.sql", "SQLException");
    private static final ClassDesc STRING_READER = ClassDesc.of("java.io", "StringReader");

    @Test
    void tryWithResourcesMultiCatchAndFinallyCompiles() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "ResourceReader"), cb -> cb.withVoidMethod(
                        "markUsed",
                        mb -> mb.withThrows(IO_EXCEPTION, SQL_EXCEPTION).withBody(b -> {}))
                .withVoidMethod("cleanup", mb -> mb.withBody(b -> {}))
                .withMethod(
                        "read",
                        Types.of(STRING),
                        mb -> mb.withBody(b -> b.try_(
                                tryBody -> tryBody.exprStatement(tryBody.call("markUsed"))
                                        .return_(tryBody.literal("ok")),
                                tb -> tb.resource_(
                                                "reader",
                                                Types.of(STRING_READER),
                                                b.new_(Types.of(STRING_READER), b.literal("x")))
                                        .catch_(
                                                List.of(Types.of(IO_EXCEPTION), Types.of(SQL_EXCEPTION)),
                                                "e",
                                                catchBody -> catchBody.return_(catchBody.literal("failed")))
                                        .finally_(finallyBody ->
                                                finallyBody.exprStatement(finallyBody.call("cleanup")))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
