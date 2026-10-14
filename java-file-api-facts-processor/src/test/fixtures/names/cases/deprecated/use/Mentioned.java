import gen.facts.p.Uses_;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/// A member whose signature mentions a deprecated type has no fact: code that names it is code javac warns of
/// (`-Xlint:deprecation`), and the facts describe what code may name without a warning. The metamodel compiles
/// under `-Xlint:all -Werror`, as does this code.
public final class Mentioned {
    private Mentioned() {}

    public static void theMembersThatMentionADeprecatedTypeHaveNoFact() {
        assertThat(Arrays.stream(Uses_.class.getFields()).map(field -> field.getName()))
                .doesNotContain("old", "take_Old");
    }
}
