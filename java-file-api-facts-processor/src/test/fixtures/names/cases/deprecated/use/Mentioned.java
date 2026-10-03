import gen.facts.p.Uses_;

import static org.assertj.core.api.Assertions.assertThat;

/// A deprecated type in a signature does not make the metamodel warn (the metamodels compile under `-Xlint:all
/// -Werror`, as does this code that refers to the facts of the signature).
public final class Mentioned {
    private Mentioned() {}

    public static void theFactsOfAMemberThatMentionsADeprecatedTypeAreThere() {
        assertThat(Uses_.old.name()).isEqualTo("old");
        assertThat(Uses_.take_Old.name()).isEqualTo("take");
    }
}
