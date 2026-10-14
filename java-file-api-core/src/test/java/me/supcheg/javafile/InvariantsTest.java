package me.supcheg.javafile;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InvariantsTest {

    @Test
    void aValueThatIsThereIsReturned() {
        assertThat(Invariants.present(Optional.of("value"), "the %s is read after it is written", "value"))
                .isEqualTo("value");
        assertThatCode(() -> Invariants.require(true, "never said")).doesNotThrowAnyException();
    }

    @Test
    void aBrokenInvariantSaysWhichOneItIs() {
        assertThatThrownBy(
                        () -> Invariants.present(Optional.empty(), "the model of %s is read after round %d", "p.A", 2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("the model of p.A is read after round 2");
        assertThatThrownBy(() -> Invariants.require(false, "%s was rendered twice", "p.A"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("p.A was rendered twice");
    }
}
