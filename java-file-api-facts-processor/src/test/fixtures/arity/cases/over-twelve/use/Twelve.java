import gen.facts.p.Big_;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidMethodRef12;
import p.Big;

import static org.assertj.core.api.Assertions.assertThat;

/// The families of facts end at 12 parameters: the members that have that many are facts.
public final class Twelve {
    private Twelve() {}

    public static void aConstructorWithTwelveParametersHasAFact() {
        CtorRef12<Big, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int> constructor =
                Big_.new_int_int_int_int_int_int_int_int_int_int_int_int;

        assertThat(constructor.owner()).isSameAs(Big_.TOKEN);
    }

    public static void aMethodWithTwelveParametersHasAFact() {
        VoidMethodRef12<Big, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int, Prim.Int> twelve =
                Big_.twelve_int_int_int_int_int_int_int_int_int_int_int_int;

        assertThat(twelve.name()).isEqualTo("twelve");
    }
}
