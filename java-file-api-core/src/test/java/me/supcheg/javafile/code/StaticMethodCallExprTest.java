package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StaticMethodCallExprTest {

    private final ClassOrInterfaceTypeRef mathType = Types.of(ClassDesc.of("java.lang", "Math"));

    @Test
    void exposesTargetMethodAndArgs() {
        StaticMethodCallExpr expr =
                new StaticMethodCallExpr(mathType, "max", List.of(new IntLiteral(1), new IntLiteral(2)));

        assertThat(expr.target()).isEqualTo(mathType);
        assertThat(expr.method()).isEqualTo("max");
        assertThat(expr.args()).containsExactly(new IntLiteral(1), new IntLiteral(2));
    }

    @Test
    void copiesArgsDefensively() {
        List<Expr> mutableArgs = new java.util.ArrayList<>(List.of(new IntLiteral(1)));
        StaticMethodCallExpr expr = new StaticMethodCallExpr(mathType, "abs", mutableArgs);

        mutableArgs.add(new IntLiteral(2));

        assertThat(expr.args()).containsExactly(new IntLiteral(1));
    }

    @Test
    void rejectsAnInvalidMethodName() {
        assertThatThrownBy(() -> new StaticMethodCallExpr(mathType, "1bad", List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
