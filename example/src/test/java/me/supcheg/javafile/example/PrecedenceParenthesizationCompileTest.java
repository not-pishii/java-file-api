package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.cond;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.neg;

/// End-to-end javac coverage for the receiver-position and reference-type-cast
/// parenthesization fixes in `ExprRenderer`/`Precedence`. These prove the
/// rendered source is not merely string-equal to the expected form, but is
/// itself valid, semantically-correct Java that javac accepts.
class PrecedenceParenthesizationCompileTest {

    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc INTEGER = ClassDesc.of("java.lang", "Integer");

    @Test
    void castOfAMethodCallReceiverParenthesizesTheCastSoTheCallTargetsTheCastResult() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "CastReceiver"),
                cb -> cb.withMethod(
                        "trimmed",
                        Types.of(STRING),
                        mb -> mb.withParam("o", Types.of(OBJECT))
                                .withBody(b -> b.return_(
                                        cast(Types.of(STRING), field("o")).call("trim")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void conditionalArrayPickThenFieldAccessParenthesizesTheConditionalSoLengthAppliesToThePickedArray() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "ConditionalArrayLength"),
                cb -> cb.withMethod(
                        "pickLength",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("cond", PrimitiveTypeRef.BOOLEAN)
                                .withParam("arr1", new ArrayTypeRef(PrimitiveTypeRef.INT))
                                .withParam("arr2", new ArrayTypeRef(PrimitiveTypeRef.INT))
                                .withBody(b -> b.return_(cond(field("cond"), field("arr1"), field("arr2"))
                                        .field("length")))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void referenceTypeCastOfUnaryMinusParenthesizesTheOperandSoTheCastIsAValidExpression() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "BoxedNegation"),
                cb -> cb.withMethod(
                        "negate",
                        Types.of(INTEGER),
                        mb -> mb.withParam("x", PrimitiveTypeRef.INT)
                                .withBody(b -> b.return_(cast(Types.of(INTEGER), neg(field("x")))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
