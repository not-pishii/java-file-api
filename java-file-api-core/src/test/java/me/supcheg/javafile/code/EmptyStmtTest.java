package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyStmtTest {

    @Test
    void isAValidStmt() {
        assertThat((Stmt) new EmptyStmt()).isInstanceOf(Stmt.class);
    }
}
