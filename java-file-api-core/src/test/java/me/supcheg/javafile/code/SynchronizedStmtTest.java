package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SynchronizedStmtTest {

    @Test
    void holdsLockExpressionAndBody() {
        Expr lock = new FieldAccessExpr(Optional.empty(), "monitor");
        SynchronizedStmt stmt = new SynchronizedStmt(lock, CodeBody.EMPTY);

        assertThat(stmt.lock()).isEqualTo(lock);
        assertThat(stmt.body()).isEqualTo(CodeBody.EMPTY);
    }
}
