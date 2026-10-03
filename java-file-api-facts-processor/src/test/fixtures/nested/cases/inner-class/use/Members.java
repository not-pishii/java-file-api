import gen.facts.p.Outer_NonStatic_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import p.Outer;

import static org.assertj.core.api.Assertions.assertThat;

/// An inner class has the facts of its methods, not of its constructors (see use-fails/).
public final class Members {
    private Members() {}

    public static void anInnerClassHasTheFactsOfItsMethods() {
        MethodRef0<Outer.NonStatic, Prim.Int> x = Outer_NonStatic_.x;

        assertThat(x.name()).isEqualTo("x");
    }
}
