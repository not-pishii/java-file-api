package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AssignOpTest {

    @Test
    void plainAssignmentUsesAssignOp() {
        AssignStmt stmt =
                new AssignStmt(new FieldAccessExpr(Optional.empty(), "x"), AssignOp.ASSIGN, new IntLiteral(1));
        assertThat(stmt.op()).isEqualTo(AssignOp.ASSIGN);
    }

    @Test
    void addAssignSymbolIsPlusEquals() {
        assertThat(AssignOp.ADD_ASSIGN.symbol()).isEqualTo("+=");
    }

    @ParameterizedTest
    @MethodSource("symbols")
    void symbolMatchesOperator(AssignOp op, String expectedSymbol) {
        assertThat(op.symbol()).isEqualTo(expectedSymbol);
    }

    static Stream<Arguments> symbols() {
        return Stream.of(
                Arguments.of(AssignOp.ASSIGN, "="),
                Arguments.of(AssignOp.ADD_ASSIGN, "+="),
                Arguments.of(AssignOp.SUB_ASSIGN, "-="),
                Arguments.of(AssignOp.MUL_ASSIGN, "*="),
                Arguments.of(AssignOp.DIV_ASSIGN, "/="),
                Arguments.of(AssignOp.MOD_ASSIGN, "%="),
                Arguments.of(AssignOp.AND_ASSIGN, "&="),
                Arguments.of(AssignOp.OR_ASSIGN, "|="),
                Arguments.of(AssignOp.XOR_ASSIGN, "^="),
                Arguments.of(AssignOp.SHL_ASSIGN, "<<="),
                Arguments.of(AssignOp.SHR_ASSIGN, ">>="),
                Arguments.of(AssignOp.USHR_ASSIGN, ">>>="));
    }
}
