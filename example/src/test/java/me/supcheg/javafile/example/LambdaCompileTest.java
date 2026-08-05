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
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.lambda;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.switchExpr;

class LambdaCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc FUNCTION = ClassDesc.of("java.util.function", "Function");
    private static final ClassDesc ILLEGAL_STATE = ClassDesc.of("java.lang", "IllegalStateException");

    @Test
    void lambdaWithSwitchExpressionBodyCompiles() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "ArgsResolver"),
                cb -> cb.withVoidMethod(
                        "resolve",
                        mb -> mb.withParam("x", Types.of(OBJECT))
                                .withBody(b -> b.localVar(
                                        "args",
                                        Types.parameterized(FUNCTION, Types.of(STRING), Types.of(OBJECT)),
                                        lambda(
                                                List.of("name"),
                                                switchExpr(
                                                        field("name"),
                                                        sb -> sb.caseValue(literal("x"), field("x"))
                                                                .default_(body -> body.throw_(
                                                                        new_(ILLEGAL_STATE, field("name"))))))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
