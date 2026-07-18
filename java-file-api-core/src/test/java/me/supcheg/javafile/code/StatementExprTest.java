package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StatementExprTest {

    @Test
    void methodCallIsAValidStatementExpr() {
        StatementExpr expr = new MethodCallExpr(Optional.empty(), "run", List.of());
        assertThat(new ExprStmt(expr).expr()).isEqualTo(expr);
    }

    @Test
    void postIncrementIsAValidStatementExpr() {
        IncDecExpr expr = new IncDecExpr(IncDecOp.POST_INC, new FieldAccessExpr(Optional.empty(), "counter"));
        assertThat(new ExprStmt(expr).expr()).isEqualTo(expr);
    }

    // намеренно НЕ компилируется и служит документацией сужения (не запускается, используется в code review):
    // new ExprStmt(new BinaryExpr(a, BinaryOp.ADD, b)); // BinaryExpr does not implement StatementExpr
}
