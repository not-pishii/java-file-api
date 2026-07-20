package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

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
}
