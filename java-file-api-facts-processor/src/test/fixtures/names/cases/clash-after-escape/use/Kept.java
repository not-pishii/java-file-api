import gen.facts.p.Sh_;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Sh;

import static org.assertj.core.api.Assertions.assertThat;

/// `x()` is `x_` as a fact, as the field `x` is named `x`; so the field `x_` may not take that name: of the three
/// members only the field `x` keeps its fact.
public final class Kept {
    private Kept() {}

    public static void theFieldWithTheNameOfTheMemberKeepsIt() {
        MutableFieldRef<Sh, Prim.Int> x = Sh_.x;

        assertThat(x.name()).isEqualTo("x");
    }
}
