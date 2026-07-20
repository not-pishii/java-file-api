package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StaticFieldAccessExprTest {

    private final ClassOrInterfaceTypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));

    @Test
    void exposesTargetAndName() {
        StaticFieldAccessExpr expr = new StaticFieldAccessExpr(integerType, "MAX_VALUE");

        assertThat(expr.target()).isEqualTo(integerType);
        assertThat(expr.name()).isEqualTo("MAX_VALUE");
    }

    @Test
    void rejectsAnInvalidFieldName() {
        assertThatThrownBy(() -> new StaticFieldAccessExpr(integerType, "1bad"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
