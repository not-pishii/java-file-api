package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThrowCaseBodyTest {

    @Test
    void exceptionReturnsTheThrownExpression() {
        Expr exception = new StringLiteral("boom");

        ThrowCaseBody body = new ThrowCaseBody(exception);

        assertThat(body.exception()).isEqualTo(exception);
        assertThat((CaseBody) body).isEqualTo(new ThrowCaseBody(exception));
    }
}
