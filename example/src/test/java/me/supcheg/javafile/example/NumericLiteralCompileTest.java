package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.div;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.neg;
import static me.supcheg.javafile.code.Exprs.sub;
import static org.assertj.core.api.Assertions.assertThat;

class NumericLiteralCompileTest {

    @Test
    void aNegativeNumberCastToAReferenceTypeIsInParentheses() throws Exception {
        // JLS 15.16: `(Object) -1` is the difference of a variable `Object` and 1
        JavaFile file = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "Negatives"),
                ib -> ib.withConstant("INT", Types.OBJECT, cast(Types.OBJECT, literal(-1)))
                        .withConstant("MIN_INT", Types.OBJECT, cast(Types.OBJECT, literal(Integer.MIN_VALUE)))
                        .withConstant("LONG", Types.OBJECT, cast(Types.OBJECT, literal(-1L)))
                        .withConstant("MIN_LONG", Types.OBJECT, cast(Types.OBJECT, literal(Long.MIN_VALUE)))
                        .withConstant("DOUBLE", Types.OBJECT, cast(Types.OBJECT, literal(-1.5)))
                        .withConstant("ZERO", Types.OBJECT, cast(Types.OBJECT, literal(-0.0)))
                        .withConstant("PRIMITIVE", Types.INT, cast(Types.INT, literal(-1L)))
                        .withConstant("NEGATED", Types.INT, neg(literal(-1)))
                        .withConstant("LESS", Types.INT, sub(literal(1), literal(-1))));

        String source = file.render();
        Class<?> negatives = CompiledClasses.of(file).generated();

        assertThat(source)
                .contains("Object INT = (Object) (-1);")
                .contains("Object MIN_INT = (Object) (-2147483648);")
                .contains("Object LONG = (Object) (-1L);")
                .contains("Object MIN_LONG = (Object) (-9223372036854775808L);")
                .contains("Object DOUBLE = (Object) (-1.5);")
                .contains("Object ZERO = (Object) (-0.0);")
                .contains("int PRIMITIVE = (int) -1L;")
                .contains("int NEGATED = -(-1);")
                .contains("int LESS = 1 - -1;");
        assertThat(negatives.getField("INT").get(null)).isEqualTo(-1);
        assertThat(negatives.getField("MIN_INT").get(null)).isEqualTo(Integer.MIN_VALUE);
        assertThat(negatives.getField("LONG").get(null)).isEqualTo(-1L);
        assertThat(negatives.getField("MIN_LONG").get(null)).isEqualTo(Long.MIN_VALUE);
        assertThat(negatives.getField("DOUBLE").get(null)).isEqualTo(-1.5);
        assertThat(negatives.getField("ZERO").get(null)).isEqualTo(-0.0);
        assertThat(negatives.getField("PRIMITIVE").get(null)).isEqualTo(-1);
        assertThat(negatives.getField("NEGATED").get(null)).isEqualTo(1);
        assertThat(negatives.getField("LESS").get(null)).isEqualTo(2);
    }

    @Test
    void aDoubleThatIsNoFiniteNumberIsAConstantExpressionOfItsValue() throws Exception {
        // JLS 3.10.2: no literal is NaN or an infinity, and `NaN` and `Infinity` are names of nothing
        JavaFile file = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "NonFinite"),
                ib -> ib.withConstant("NAN", Types.DOUBLE, literal(Double.NaN))
                        .withConstant("POSITIVE", Types.DOUBLE, literal(Double.POSITIVE_INFINITY))
                        .withConstant("NEGATIVE", Types.DOUBLE, literal(Double.NEGATIVE_INFINITY))
                        .withConstant("NEGATED", Types.DOUBLE, neg(literal(Double.NEGATIVE_INFINITY)))
                        .withConstant("BOXED", Types.OBJECT, cast(Types.OBJECT, literal(Double.NEGATIVE_INFINITY)))
                        .withConstant("DIVIDED", Types.DOUBLE, div(literal(1.0), literal(Double.POSITIVE_INFINITY)))
                        .withConstant("LESS", Types.DOUBLE, sub(literal(1.0), literal(Double.POSITIVE_INFINITY))));

        String source = file.render();
        Class<?> nonFinite = CompiledClasses.of(file).generated();

        assertThat(source)
                .contains("double NAN = 0.0 / 0.0;")
                .contains("double POSITIVE = 1.0 / 0.0;")
                .contains("double NEGATIVE = -1.0 / 0.0;")
                .contains("double NEGATED = -(-1.0 / 0.0);")
                .contains("Object BOXED = (Object) (-1.0 / 0.0);")
                .contains("double DIVIDED = 1.0 / (1.0 / 0.0);")
                .contains("double LESS = 1.0 - 1.0 / 0.0;");
        assertThat(nonFinite.getField("NAN").get(null)).isEqualTo(Double.NaN);
        assertThat(nonFinite.getField("POSITIVE").get(null)).isEqualTo(Double.POSITIVE_INFINITY);
        assertThat(nonFinite.getField("NEGATIVE").get(null)).isEqualTo(Double.NEGATIVE_INFINITY);
        assertThat(nonFinite.getField("NEGATED").get(null)).isEqualTo(Double.POSITIVE_INFINITY);
        assertThat(nonFinite.getField("BOXED").get(null)).isEqualTo(Double.NEGATIVE_INFINITY);
        assertThat(nonFinite.getField("DIVIDED").get(null)).isEqualTo(0.0);
        assertThat(nonFinite.getField("LESS").get(null)).isEqualTo(Double.NEGATIVE_INFINITY);
    }
}
