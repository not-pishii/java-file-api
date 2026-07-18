package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwitchCaseTest {

    @Test
    void labelsCannotBeEmptyByConstruction() {
        NonEmptyList<CaseLabel> labels = new NonEmptyList<>(new DefaultLabel(), java.util.List.of());
        SwitchCase switchCase = new SwitchCase(labels, new ExprCaseBody(new IntLiteral(1)));

        assertThat(switchCase.labels().toList()).containsExactly(new DefaultLabel());
    }
}
