package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.routine.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What the round has read is read again as it was ([Models#settled]): translated or not, and never
/// deferred.
class SettledTest {

    @Test
    void whatWasReadIsTranslatedOrIsNot() {
        assertThat(Models.settled(new Translation.Ok<>("model"))).isEqualTo(Either.right("model"));
        assertThat(Models.settled(new Translation.Unrepresentable<String>("no model")))
                .isEqualTo(Either.left("no model"));
    }

    @Test
    void whatWasReadIsNotDeferredAnyMore() {
        assertThatIllegalStateException()
                .isThrownBy(() -> Models.settled(new Translation.Deferred<String>("gen.Missing")))
                .withMessage("what was read mentions gen.Missing now");
    }
}
