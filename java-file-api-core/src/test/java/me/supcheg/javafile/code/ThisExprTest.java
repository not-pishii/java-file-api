package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ThisExprTest {

    @Test
    void thisDotFieldNoLongerNeedsAStringHack() {
        CodeBuilder cb = new CodeBuilder();
        Expr expr = cb.field(cb.this_(), "bundle");

        assertThat(expr).isEqualTo(new FieldAccessExpr(Optional.of(new ThisExpr()), "bundle"));
    }

    @Test
    void superDotMethodCall() {
        CodeBuilder cb = new CodeBuilder();
        Expr expr = cb.call(cb.super_(), "toString");

        assertThat(expr).isEqualTo(new MethodCallExpr(Optional.of(new SuperExpr()), "toString", java.util.List.of()));
    }
}
