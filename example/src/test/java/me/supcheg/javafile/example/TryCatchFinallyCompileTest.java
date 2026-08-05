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
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.new_;

class TryCatchFinallyCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc SQL_EXCEPTION = ClassDesc.of("java.sql", "SQLException");
    private static final ClassDesc STRING_READER = ClassDesc.of("java.io", "StringReader");

    @Test
    void tryWithResourcesMultiCatchAndFinallyCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "ResourceReader"),
                cb -> cb.withVoidMethod(
                                "markUsed",
                                mb -> mb.withThrows(IO_EXCEPTION, SQL_EXCEPTION).withBody(b -> {}))
                        .withVoidMethod("cleanup", mb -> mb.withBody(b -> {}))
                        .withMethod(
                                "read",
                                Types.of(STRING),
                                mb -> mb.withBody(b -> b.try_(
                                        tryBody -> tryBody.exprStatement(call("markUsed"))
                                                .return_(literal("ok")),
                                        tb -> tb.resource_(
                                                        "reader",
                                                        Types.of(STRING_READER),
                                                        new_(STRING_READER, literal("x")))
                                                .catch_(
                                                        List.of(Types.of(IO_EXCEPTION), Types.of(SQL_EXCEPTION)),
                                                        "e",
                                                        catchBody -> catchBody.return_(literal("failed")))
                                                .finally_(
                                                        finallyBody -> finallyBody.exprStatement(call("cleanup")))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
