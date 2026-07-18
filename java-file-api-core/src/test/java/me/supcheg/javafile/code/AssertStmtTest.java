package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AssertStmtTest {

    @Test
    void holdsConditionWithoutMessage() {
        AssertStmt stmt = new AssertStmt(new BooleanLiteral(true), Optional.empty());
        assertThat(stmt.message()).isEmpty();
    }

    @Test
    void holdsConditionWithMessage() {
        AssertStmt stmt = new AssertStmt(new BooleanLiteral(true), Optional.of(new StringLiteral("must hold")));
        assertThat(stmt.message()).contains(new StringLiteral("must hold"));
    }
}
