package me.supcheg.javafile.render;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
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

import static org.assertj.core.api.Assertions.assertThat;

class PrecedenceTest {

    private final CodeBuilder cb = new CodeBuilder();

    @Test
    void primaryFormsAllRenderAtThePrimaryLevel() {
        Expr[] primaries = {
            cb.field("x"),
            cb.staticField(Types.of(ClassDesc.of("java.lang", "Integer")), "MAX_VALUE"),
            cb.call("use"),
            cb.callStatic(Types.of(ClassDesc.of("java.lang", "Math")), "abs", cb.literal(1)),
            cb.literal(1),
            cb.textBlock("x"),
            new NewExpr(new TypedNewTarget(Types.of(ClassDesc.of("java.lang", "Object"))), List.of(), Optional.empty()),
            new SwitchExpr(cb.field("x"), List.of()),
            new ThisExpr(),
            new SuperExpr(),
            cb.classLiteral(Types.of(ClassDesc.of("java.lang", "String"))),
            cb.methodRef(Types.of(ClassDesc.of("java.lang", "Integer")), "parseInt"),
            cb.constructorRef(Types.of(ClassDesc.of("java.lang", "String"))),
            cb.arrayAccess(cb.field("array"), cb.literal(0)),
            cb.newArray(PrimitiveTypeRef.INT, cb.literal(1)),
            cb.newArrayOf(PrimitiveTypeRef.INT, cb.literal(1))
        };

        for (Expr primary : primaries) {
            assertThat(Precedence.level(primary))
                    .as(primary.getClass().getSimpleName())
                    .isEqualTo(15);
        }
    }

    @Test
    void lambdaExprRendersAtTheLowestLevel() {
        Expr lambda = cb.lambda(List.of("x"), cb.field("x"));
        assertThat(Precedence.level(lambda)).isEqualTo(1);
    }

    @Test
    void conditionalExprRendersAtTheTernaryLevel() {
        Expr conditional = cb.cond(cb.literal(true), cb.literal(1), cb.literal(2));
        assertThat(Precedence.level(conditional)).isEqualTo(2);
    }

    @Test
    void logicalOrAndAndRenderAtTheirLevels() {
        assertThat(Precedence.level(cb.or(cb.literal(true), cb.literal(false)))).isEqualTo(3);
        assertThat(Precedence.level(cb.and(cb.literal(true), cb.literal(false))))
                .isEqualTo(4);
    }

    @Test
    void bitwiseOrXorAndRenderAtTheirLevels() {
        assertThat(Precedence.level(cb.bitOr(cb.literal(1), cb.literal(2)))).isEqualTo(5);
        assertThat(Precedence.level(cb.bitXor(cb.literal(1), cb.literal(2)))).isEqualTo(6);
        assertThat(Precedence.level(cb.bitAnd(cb.literal(1), cb.literal(2)))).isEqualTo(7);
    }

    @Test
    void equalityRendersAtLevelEightAndRelationalAndInstanceofAtLevelNine() {
        assertThat(Precedence.level(cb.eq(cb.literal(1), cb.literal(2)))).isEqualTo(8);
        assertThat(Precedence.level(cb.neq(cb.literal(1), cb.literal(2)))).isEqualTo(8);
        assertThat(Precedence.level(cb.lt(cb.literal(1), cb.literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(cb.le(cb.literal(1), cb.literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(cb.gt(cb.literal(1), cb.literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(cb.ge(cb.literal(1), cb.literal(2)))).isEqualTo(9);
        assertThat(Precedence.level(new InstanceOfExpr(
                        cb.field("obj"),
                        new TypePattern(Types.of(ClassDesc.of("java.lang", "String")), Optional.empty()))))
                .isEqualTo(9);
    }

    @Test
    void shiftAdditiveAndMultiplicativeRenderAtTheirLevels() {
        assertThat(Precedence.level(cb.shl(cb.literal(1), cb.literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(cb.shr(cb.literal(1), cb.literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(cb.ushr(cb.literal(1), cb.literal(2)))).isEqualTo(10);
        assertThat(Precedence.level(cb.add(cb.literal(1), cb.literal(2)))).isEqualTo(11);
        assertThat(Precedence.level(cb.sub(cb.literal(1), cb.literal(2)))).isEqualTo(11);
        assertThat(Precedence.level(cb.mul(cb.literal(1), cb.literal(2)))).isEqualTo(12);
        assertThat(Precedence.level(cb.div(cb.literal(1), cb.literal(2)))).isEqualTo(12);
        assertThat(Precedence.level(cb.mod(cb.literal(1), cb.literal(2)))).isEqualTo(12);
    }

    @Test
    void unaryAndCastRenderAtLevelThirteenIncDecAtFourteen() {
        assertThat(Precedence.level(cb.not(cb.literal(true)))).isEqualTo(13);
        assertThat(Precedence.level(cb.neg(cb.literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(cb.bitNot(cb.literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(cb.unaryPlus(cb.literal(1)))).isEqualTo(13);
        assertThat(Precedence.level(cb.cast(PrimitiveTypeRef.INT, cb.literal(1.5))))
                .isEqualTo(13);
        assertThat(Precedence.level(cb.preIncrement(cb.field("i")))).isEqualTo(14);
        assertThat(Precedence.level(cb.preDecrement(cb.field("i")))).isEqualTo(14);
        assertThat(Precedence.level(cb.postIncrement(cb.field("i")))).isEqualTo(14);
        assertThat(Precedence.level(cb.postDecrement(cb.field("i")))).isEqualTo(14);
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
