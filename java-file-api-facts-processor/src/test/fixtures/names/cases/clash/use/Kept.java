import gen.facts.p.Cf_;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Cf;

import static org.assertj.core.api.Assertions.assertThat;

/// Of the members that would share a name, the field `x` keeps it; the methods have no fact.
public final class Kept {
    private Kept() {}

    public static void theFieldKeepsItsName() {
        MutableFieldRef<Cf, Prim.Int> x = Cf_.x;

        assertThat(x.name()).isEqualTo("x");
    }
}
