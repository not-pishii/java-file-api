import gen.facts.out.OtherOut_;
import gen.facts.out.OtherSealed_;
import gen.facts.out.Out_;
import gen.facts.out.SealedClass_;
import gen.facts.out.SealedInterface_;
import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// A sealed type has facts of its own, and its shape tells the subtypes it permits.
public final class SealedFacts {
    private SealedFacts() {}

    public static void aSealedTypeTellsTheSubtypesItPermits() {
        assertThat(SealedClass_.TOKEN.shape().sealed()).isTrue();
        assertThat(SealedClass_.TOKEN.shape().permitted()).containsExactly(ClassDesc.of("out.Out"));
        assertThat(SealedInterface_.TOKEN.shape().sealed()).isTrue();
        assertThat(SealedInterface_.TOKEN.shape().permitted()).containsExactly(ClassDesc.of("out.Out"));
        assertThat(OtherSealed_.TOKEN.shape().sealed()).isTrue();
        assertThat(OtherSealed_.TOKEN.shape().permitted()).containsExactly(ClassDesc.of("out.OtherOut"));
        assertThat(OtherOut_.TOKEN.shape().sealed()).isFalse();
        assertThat(OtherOut_.TOKEN.shape().permitted()).isEmpty();
    }

    public static void aPermittedSubtypeIsNotSealed() {
        assertThat(Out_.TOKEN.shape().sealed()).isFalse();
        assertThat(Out_.TOKEN.shape().permitted()).isEmpty();
    }
}
