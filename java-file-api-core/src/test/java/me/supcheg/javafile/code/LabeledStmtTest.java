package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LabeledStmtTest {

    @Test
    void wrapsASingleStatementWithALabel() {
        LabeledStmt stmt = new LabeledStmt("outer", new BreakStmt(Optional.empty()));

        assertThat(stmt.label()).isEqualTo("outer");
        assertThat(stmt.statement()).isEqualTo(new BreakStmt(Optional.empty()));
    }

    @Test
    void breakAndContinueCarryAnOptionalLabel() {
        assertThat(new BreakStmt(Optional.of("outer")).label()).contains("outer");
        assertThat(new ContinueStmt(Optional.empty()).label()).isEmpty();
    }
}
