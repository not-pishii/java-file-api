import gen.facts.java.lang.String_;
import gen.facts.p.PubNest_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.PubNest;

import static me.supcheg.javafile.typed.Expressions.call;
import static org.assertj.core.api.Assertions.assertThat;

/// `PubNest extends HNest`: `plain()` is adopted; `make()` gives `HNest.In`,
/// a `public` class nested in a type that is not, which a metamodel does not
/// name though `PubNest.In` would do — see `expected/diagnostics.txt`.
public final class NestedOfHidden {
    private NestedOfHidden() {}

    public static void aMemberThatMentionsNoNestedTypeIsAdopted(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, PubNest_.TOKEN, nest -> call(nest, PubNest_.plain), new PubNest()))
                .isEqualTo("plain");
    }
}
