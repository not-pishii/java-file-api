package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BitwiseOpsTest {

    @Test
    void bitwiseAndSymbolIsAmpersand() {
        assertThat(BinaryOp.BIT_AND.symbol()).isEqualTo("&");
    }

    @Test
    void unsignedShiftRightSymbol() {
        assertThat(BinaryOp.USHR.symbol()).isEqualTo(">>>");
    }

    @Test
    void bitwiseNotIsAUnaryOp() {
        UnaryExpr expr = new UnaryExpr(UnaryOp.BIT_NOT, new IntLiteral(1));
        assertThat(expr.op()).isEqualTo(UnaryOp.BIT_NOT);
    }
}
