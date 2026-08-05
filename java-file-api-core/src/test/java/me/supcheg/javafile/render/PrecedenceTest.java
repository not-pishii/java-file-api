package me.supcheg.javafile.render;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.InstanceOfExpr;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.code.SuperExpr;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.TypePattern;
import me.supcheg.javafile.code.TypedNewTarget;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.and;
import static me.supcheg.javafile.code.Exprs.bitAnd;
import static me.supcheg.javafile.code.Exprs.bitNot;
import static me.supcheg.javafile.code.Exprs.bitOr;
import static me.supcheg.javafile.code.Exprs.bitXor;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.classLiteral;
import static me.supcheg.javafile.code.Exprs.cond;
import static me.supcheg.javafile.code.Exprs.constructorRef;
import static me.supcheg.javafile.code.Exprs.div;
import static me.supcheg.javafile.code.Exprs.eq;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.ge;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.lambda;
import static me.supcheg.javafile.code.Exprs.le;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.lt;
import static me.supcheg.javafile.code.Exprs.mod;
import static me.supcheg.javafile.code.Exprs.mul;
import static me.supcheg.javafile.code.Exprs.neg;
import static me.supcheg.javafile.code.Exprs.neq;
import static me.supcheg.javafile.code.Exprs.newArray;
import static me.supcheg.javafile.code.Exprs.newArrayOf;
import static me.supcheg.javafile.code.Exprs.not;
import static me.supcheg.javafile.code.Exprs.or;
import static me.supcheg.javafile.code.Exprs.postDecrement;
import static me.supcheg.javafile.code.Exprs.postIncrement;
import static me.supcheg.javafile.code.Exprs.preDecrement;
import static me.supcheg.javafile.code.Exprs.preIncrement;
import static me.supcheg.javafile.code.Exprs.shl;
import static me.supcheg.javafile.code.Exprs.shr;
import static me.supcheg.javafile.code.Exprs.staticField;
import static me.supcheg.javafile.code.Exprs.sub;
import static me.supcheg.javafile.code.Exprs.textBlock;
import static me.supcheg.javafile.code.Exprs.unaryPlus;
import static me.supcheg.javafile.code.Exprs.ushr;
import static org.assertj.core.api.Assertions.assertThat;

class PrecedenceTest {

    @Test
    void primaryFormsAllRenderAtThePrimaryLevel() {
        Expr[] primaries = {
            field("x"),
            staticField(Types.of(ClassDesc.of("java.lang", "Integer")), "MAX_VALUE"),
            call("use"),
            Exprs.staticCall(Types.of(ClassDesc.of("java.lang", "Math")), "abs", literal(1)),
            literal(1),
            textBlock("x"),
            new NewExpr(new TypedNewTarget(Types.of(ClassDesc.of("java.lang", "Object"))), List.of(), Optional.empty()),
            new SwitchExpr(field("x"), List.of()),
            new ThisExpr(),
            new SuperExpr(),
            classLiteral(Types.of(ClassDesc.of("java.lang", "String"))),
            Exprs.methodRef(Types.of(ClassDesc.of("java.lang", "Integer")), "parseInt"),
            constructorRef(Types.of(ClassDesc.of("java.lang", "String"))),
            field("array").arrayAccess(literal(0)),
            newArray(PrimitiveTypeRef.INT, literal(1)),
            newArrayOf(PrimitiveTypeRef.INT, literal(1))
        };

        for (Expr primary : primaries) {
            assertThat(Precedence.level(primary))
                    .as(primary.getClass().getSimpleName())
                    .isEqualTo(15);
        }
    }

    @Test
    void lambdaExprRendersAtTheLowestLevel() {
        Expr lambda = lambda(List.of("x"), field("x"));
        assertThat(Precedence.level(lambda)).isEqualTo(1);
    }

    @Test
    void conditionalExprRendersAtTheTernaryLevel() {
        Expr conditional = cond(literal(true), literal(1), literal(2));
        assertThat(Precedence.level(conditional)).isEqualTo(2);
    }

    @Test
    void logicalOrAndAndRenderAtTheirLevels() {
        assertThat(Precedence.level(or(literal(true), literal(false)))).isEqualTo(3);
        assertThat(Precedence.level(and(literal(true), literal(false)))).isEqualTo(4);
    }

    @Test
    void bitwiseOrXorAndRenderAtTheirLevels() {
        assertThat(Precedence.level(bitOr(literal(1), literal(2)))).isEqualTo(5);
        assertThat(Precedence.level(bitXor(literal(1), literal(2)))).isEqualTo(6);
        assertThat(Precedence.level(bitAnd(literal(1), literal(2)))).isEqualTo(7);
    }

    @Test
    void equalityRendersAtLevelEightAndRelationalAndInstanceofAtLevelNine() {
        assertThat(Precedence.level(eq(literal(1), literal(2)))).isEqualTo(8);
        assertThat(Precedence.level(neq(literal(1), literal(2)))).isEqualTo(8);
        assertThat(Precedence.level(lt(literal(1), literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(le(literal(1), literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(gt(literal(1), literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(ge(literal(1), literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(new InstanceOfExpr(
                        field("obj"),
                        new TypePattern(Types.of(ClassDesc.of("java.lang", "String")), Optional.empty()))))
                .isEqualTo(9);
    }

    @Test
    void shiftAdditiveAndMultiplicativeRenderAtTheirLevels() {
        assertThat(Precedence.level(shl(literal(1), literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(shr(literal(1), literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(ushr(literal(1), literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(add(literal(1), literal(2)))).isEqualTo(11);
        assertThat(Precedence.level(sub(literal(1), literal(2)))).isEqualTo(11);
        assertThat(Precedence.level(mul(literal(1), literal(2)))).isEqualTo(12);
        assertThat(Precedence.level(div(literal(1), literal(2)))).isEqualTo(12);
        assertThat(Precedence.level(mod(literal(1), literal(2)))).isEqualTo(12);
    }

    @Test
    void unaryAndCastRenderAtLevelThirteenIncDecAtFourteen() {
        assertThat(Precedence.level(not(literal(true)))).isEqualTo(13);
        assertThat(Precedence.level(neg(literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(bitNot(literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(unaryPlus(literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(cast(PrimitiveTypeRef.INT, literal(1.5)))).isEqualTo(13);
        assertThat(Precedence.level(preIncrement(field("i")))).isEqualTo(14);
        assertThat(Precedence.level(preDecrement(field("i")))).isEqualTo(14);
        assertThat(Precedence.level(postIncrement(field("i")))).isEqualTo(14);
        assertThat(Precedence.level(postDecrement(field("i")))).isEqualTo(14);
    }

    @Test
    void needsParensFlagsOnlyMatchingPlusOrMinusAdjacency() {
        assertThat(Precedence.needsParensAroundUnaryOperand("-", "-x")).isTrue();
        assertThat(Precedence.needsParensAroundUnaryOperand("-", "--x")).isTrue();
        assertThat(Precedence.needsParensAroundUnaryOperand("+", "+x")).isTrue();
        assertThat(Precedence.needsParensAroundUnaryOperand("++", "++x")).isTrue();
        assertThat(Precedence.needsParensAroundUnaryOperand("--", "--x")).isTrue();
        assertThat(Precedence.needsParensAroundUnaryOperand("-", "+x")).isFalse();
        assertThat(Precedence.needsParensAroundUnaryOperand("+", "-x")).isFalse();
        assertThat(Precedence.needsParensAroundUnaryOperand("++", "--x")).isFalse();
        assertThat(Precedence.needsParensAroundUnaryOperand("~", "~x")).isFalse();
        assertThat(Precedence.needsParensAroundUnaryOperand("!", "!x")).isFalse();
        assertThat(Precedence.needsParensAroundUnaryOperand("-", "")).isFalse();
    }
}
