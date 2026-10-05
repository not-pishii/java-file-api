package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.and;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.divInt;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingDoubleToInt;
import static me.supcheg.javafile.typed.Expressions.not;
import static me.supcheg.javafile.typed.Expressions.or;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// Constant folding (JLS 15.29) reproduces javac's verdict on loop
/// conditions; each case was checked against javac's "unreachable statement".
class ConstantsTest {

    private static boolean constantTrue(Expr<Prim.Bool> condition) {
        return Constants.isConstant(condition.node(), true);
    }

    @Test
    void operatorsOverConstantsFold() {
        assertThat(constantTrue(not(literal(false)))).isTrue();
        assertThat(constantTrue(and(literal(true), or(literal(false), literal(true)))))
                .isTrue();
        assertThat(constantTrue(eqInt(addInt(literal(1), literal(2)), literal(3))))
                .isTrue();
        assertThat(constantTrue(eqInt(narrowTruncatingDoubleToInt(literal(2.9)), literal(2))))
                .isTrue();
        assertThat(constantTrue(cond(literal(true), literal(true), literal(false), PrimitiveToken.BOOLEAN)))
                .isTrue();
    }

    @Test
    void constantVariablesFold() {
        assertThat(constantTrue(gtInt(staticField(Integer_.MAX_VALUE), literal(0))))
                .isTrue();
    }

    @Test
    void stringConstantsCompareByContentAsTheyAreInterned() {
        assertThat(constantTrue(eqRef(literal("a"), literal("a")))).isTrue();
        assertThat(constantTrue(eqRef(concat(literal("a"), literal("1")), literal("a1"))))
                .isTrue();
        assertThat(Constants.isConstant(eqRef(literal("a"), literal("b")).node(), false))
                .isTrue();
    }

    @Test
    void anAbruptlyCompletingExpressionIsNotConstant() {
        // while (1 / 0 == 0) {} return 0;  compiles: 1 / 0 is not a constant expression
        Expr<Prim.Bool> condition = eqInt(divInt(literal(1), literal(0)), literal(0));

        assertThat(constantTrue(condition)).isFalse();
        assertThat(Constants.isConstant(condition.node(), false)).isFalse();
    }

    @Test
    void anExpressionWithAVariableIsNotConstant() {
        Var<Prim.Bool> flag = Var.param(PrimitiveToken.BOOLEAN, Body.root("root"));

        assertThat(constantTrue(or(literal(true), flag))).isFalse();
    }

    @Test
    void theCastsThatTypeAnExpressionFoldAsJavacFoldsThem() {
        // cond branches of its own type are not cast: still a constant
        assertThat(constantTrue(eqRef(cond(literal(true), literal("a"), literal("b"), String_.TOKEN), literal("a"))))
                .isTrue();
        // branches cast to CharSequence: a cast to a type other than String or a primitive is not
        // a constant expression (JLS 15.29)
        assertThat(constantTrue(
                        eqRef(cond(literal(true), literal("a"), literal("b"), CharSequence_.TOKEN), literal("a"))))
                .isFalse();
        // `(String) null` is not a constant
        assertThat(constantTrue(eqRef(literalNull(String_.TOKEN), literalNull(String_.TOKEN))))
                .isFalse();
        // a primitive cond stays constant; the casts lowering adds to arguments are never part of a
        // constant expression, since a call is not one
        assertThat(constantTrue(cond(literal(false), literal(false), literal(true), PrimitiveToken.BOOLEAN)))
                .isTrue();
    }
}
