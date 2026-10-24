package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InferredLambdaParamsTest {

    @Test
    void acceptsValidNames() {
        assertThatCode(() -> new InferredLambdaParams(List.of("a", "b"))).doesNotThrowAnyException();
    }

    @Test
    void rejectsReservedKeywordAsName() {
        assertThatThrownBy(() -> new InferredLambdaParams(List.of("class")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsLeadingDigitInName() {
        assertThatThrownBy(() -> new InferredLambdaParams(List.of("1foo")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
