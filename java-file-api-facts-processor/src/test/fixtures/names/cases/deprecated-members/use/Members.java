import gen.facts.p.Aged_;
import gen.facts.p.Phase_;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/// A deprecated member has no fact, whatever it is — a field, a constant, a constructor, a method, an enum
/// constant: code that names it is code javac warns of (`-Xlint:deprecation`, `removal`). The others have
/// theirs, named as if the deprecated ones were not there.
public final class Members {
    private Members() {}

    public static void aDeprecatedMemberHasNoFact() {
        assertThat(Arrays.stream(Aged_.class.getFields()).map(field -> field.getName()))
                .contains("is", "here", "new_")
                .doesNotContain("was", "OLD", "gone", "new_String");
        assertThat(Arrays.stream(Phase_.class.getFields()).map(field -> field.getName()))
                .contains("NEW")
                .doesNotContain("OLD");
    }
}
