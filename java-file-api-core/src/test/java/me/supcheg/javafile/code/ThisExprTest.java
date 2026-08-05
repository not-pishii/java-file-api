package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ThisExprTest {

    @Test
    void thisDotFieldNoLongerNeedsAStringHack() {
        Expr expr = Exprs.this_().field("bundle");

        assertThat(expr).isEqualTo(new FieldAccessExpr(Optional.of(new ThisExpr()), "bundle"));
    }

    @Test
    void superDotMethodCall() {
        Expr expr = Exprs.super_().call("toString");

        assertThat(expr).isEqualTo(new MethodCallExpr(Optional.of(new SuperExpr()), "toString", java.util.List.of()));
    }
}
