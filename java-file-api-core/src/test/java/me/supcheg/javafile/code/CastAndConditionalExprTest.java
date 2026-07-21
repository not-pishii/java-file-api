package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CastAndConditionalExprTest {

    @Test
    void castHoldsTargetTypeAndOperand() {
        CastExpr expr = new CastExpr(PrimitiveTypeRef.INT, new DoubleLiteral(1.5));
        assertThat(expr.type()).isEqualTo(PrimitiveTypeRef.INT);
    }

    @Test
    void conditionalHoldsAllThreeBranches() {
        ConditionalExpr expr = new ConditionalExpr(new BooleanLiteral(true), new IntLiteral(1), new IntLiteral(2));
        assertThat(expr.whenTrue()).isEqualTo(new IntLiteral(1));
        assertThat(expr.whenFalse()).isEqualTo(new IntLiteral(2));
    }

    @Test
    void classLiteralIsNotAConstantExpr() {
        ClassLiteralExpr expr = new ClassLiteralExpr(PrimitiveTypeRef.INT);
        assertThat(expr).isNotInstanceOf(ConstantExpr.class);
    }
}
