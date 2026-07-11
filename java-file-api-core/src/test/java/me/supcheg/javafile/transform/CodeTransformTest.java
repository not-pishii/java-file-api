package me.supcheg.javafile.transform;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.Stmt;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CodeTransformTest {

    @Test
    void acceptCanPassAStatementThroughToTheBuilder() {
        CodeBuilder builder = new CodeBuilder();
        ExprStmt stmt = new ExprStmt(new IntLiteral(1));

        CodeTransform passThrough = (b, s) -> b.accept(s);
        passThrough.accept(builder, stmt);

        assertThat(builder.build().statements()).containsExactly(stmt);
    }

    @Test
    void andThenInvokesBothTransformsInOrderAgainstTheSameStatement() {
        List<String> callOrder = new ArrayList<>();
        CodeBuilder builder = new CodeBuilder();
        ExprStmt stmt = new ExprStmt(new IntLiteral(1));

        CodeTransform first = (b, s) -> {
            callOrder.add("first");
            b.accept(s);
        };
        CodeTransform second = (b, s) -> callOrder.add("second");

        CodeTransform combined = first.andThen(second);
        combined.accept(builder, stmt);

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(builder.build().statements()).containsExactly((Stmt) stmt);
    }
}
