package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// The literals whose value Java source writes otherwise than a literal: a negative number, which is the
/// unary minus of a literal and so is parenthesised under a cast, and what is not a number and the
/// infinities, which are the constant expressions that have them. Compiled and run, each is the value
/// it was given.
class LiteralsTest {

    @Test
    void aValueIsWhatItWasGivenWhereverItStands() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Literals"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var negative = cb.declareStaticMethod("negative", String_.TOKEN);
                        var lowest = cb.declareStaticMethod("lowest", String_.TOKEN);
                        var lowestLong = cb.declareStaticMethod("lowestLong", String_.TOKEN);
                        var notANumber = cb.declareStaticMethod("notANumber", String_.TOKEN);
                        var infinite = cb.declareStaticMethod("infinite", String_.TOKEN);
                        var negativeInfinite = cb.declareStaticMethod("negativeInfinite", String_.TOKEN);
                        var nan = cb.declareStaticMethod("nan", PrimitiveToken.DOUBLE);
                        var bottom = cb.declareStaticMethod("bottom", PrimitiveToken.DOUBLE);
                        var bodies = cb.bodies();
                        // each under the cast that pins valueOf(Object) among the overloads
                        bodies.define(negative, b -> b.return_(staticCall(String_.valueOf_Object, literal(-1))));
                        bodies.define(
                                lowest, b -> b.return_(staticCall(String_.valueOf_Object, literal(Integer.MIN_VALUE))));
                        bodies.define(
                                lowestLong,
                                b -> b.return_(staticCall(String_.valueOf_Object, literal(Long.MIN_VALUE))));
                        bodies.define(
                                notANumber, b -> b.return_(staticCall(String_.valueOf_Object, literal(Double.NaN))));
                        bodies.define(
                                infinite,
                                b -> b.return_(staticCall(String_.valueOf_Object, literal(Double.POSITIVE_INFINITY))));
                        bodies.define(
                                negativeInfinite,
                                b -> b.return_(staticCall(String_.valueOf_Object, literal(Double.NEGATIVE_INFINITY))));
                        bodies.define(nan, b -> b.return_(literal(Double.NaN)));
                        bodies.define(bottom, b -> b.return_(literal(-Double.MAX_VALUE)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("String.valueOf((Object) (-1))")
                .contains("String.valueOf((Object) (0.0 / 0.0))")
                .contains("String.valueOf((Object) (-1.0 / 0.0))");
        assertThat(compiled.invoke("negative")).isEqualTo("-1");
        assertThat(compiled.invoke("lowest")).isEqualTo("-2147483648");
        assertThat(compiled.invoke("lowestLong")).isEqualTo("-9223372036854775808");
        assertThat(compiled.invoke("notANumber")).isEqualTo("NaN");
        assertThat(compiled.invoke("infinite")).isEqualTo("Infinity");
        assertThat(compiled.invoke("negativeInfinite")).isEqualTo("-Infinity");
        assertThat((Double) compiled.invoke("nan")).isNaN();
        assertThat(compiled.invoke("bottom")).isEqualTo(-Double.MAX_VALUE);
    }
}
