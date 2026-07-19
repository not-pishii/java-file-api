package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ConstantExprTest {

    @Test
    void literalIsAValidConstantExpr() {
        ConstantExpr value = new IntLiteral(1);
        assertThat(new ConstantLabel(value).value()).isEqualTo(value);
    }

    @Test
    void enumConstantNameIsAValidConstantExpr() {
        ConstantExpr value = new FieldAccessExpr(Optional.empty(), "RED");
        assertThat(new ConstantLabel(value).value()).isEqualTo(value);
    }

    @Test
    void staticFieldAccessIsAValidConstantExpr() {
        ClassOrInterfaceTypeRef type = Types.of(ClassDesc.of("java.lang", "Integer"));
        ConstantExpr value = new StaticFieldAccessExpr(type, "MAX_VALUE");
        assertThat(new ConstantLabel(value).value()).isEqualTo(value);
    }

    // намеренно НЕ компилируется и служит документацией сужения (не запускается, используется в code review):
    // new ConstantLabel(new MethodCallExpr(Optional.empty(), "compute", List.of())); // MethodCallExpr does not
    // implement ConstantExpr
}
