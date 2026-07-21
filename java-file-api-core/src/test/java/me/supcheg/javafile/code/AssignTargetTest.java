package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AssignTargetTest {

    @Test
    void fieldAccessIsAValidAssignTarget() {
        AssignTarget target = new FieldAccessExpr(Optional.empty(), "counter");
        AssignStmt stmt = new AssignStmt(target, AssignOp.ASSIGN, new IntLiteral(0));

        assertThat(stmt.target()).isEqualTo(target);
    }

    @Test
    void staticFieldAccessIsAValidAssignTarget() {
        ClassOrInterfaceTypeRef type = Types.of(ClassDesc.of("java.lang", "Integer"));
        AssignTarget target = new StaticFieldAccessExpr(type, "MAX_VALUE");
        AssignStmt stmt = new AssignStmt(target, AssignOp.ASSIGN, new IntLiteral(0));

        assertThat(stmt.target()).isEqualTo(target);
    }

    // намеренно НЕ компилируется и служит документацией сужения (не запускается, используется в code review):
    // new AssignStmt(new MethodCallExpr(Optional.empty(), "foo", List.of()), value); // MethodCallExpr does not
    // implement AssignTarget
}
