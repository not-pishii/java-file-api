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

class LambdaCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc FUNCTION = ClassDesc.of("java.util.function", "Function");
    private static final ClassDesc ILLEGAL_STATE = ClassDesc.of("java.lang", "IllegalStateException");

    @Test
    void lambdaWithSwitchExpressionBodyCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "ArgsResolver"),
                cb -> cb.withVoidMethod(
                        "resolve",
                        mb -> mb.withParam("x", Types.of(OBJECT))
                                .withBody(b -> b.localVar(
                                        "args",
                                        Types.parameterized(
                                                FUNCTION, Types.exact(Types.of(STRING)), Types.exact(Types.of(OBJECT))),
                                        b.lambda(
                                                List.of("name"),
                                                b.switchExpr(
                                                        b.field("name"),
                                                        sb -> sb.caseValue(b.literal("x"), b.field("x"))
                                                                .default_(body -> body.throw_(b.new_(
                                                                        Types.of(ILLEGAL_STATE),
                                                                        b.field("name"))))))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
