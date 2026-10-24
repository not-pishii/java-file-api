package me.supcheg.javafile.code;

import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnonymousBodyTest {

    @Test
    void plainNewExprHasNoAnonymousBody() {
        NewExpr expr = new NewExpr(new TypedNewTarget(Types.of(ClassDesc.of("java.lang", "Object"))), List.of());
        assertThat(expr.anonymousBody()).isEmpty();
    }
}
