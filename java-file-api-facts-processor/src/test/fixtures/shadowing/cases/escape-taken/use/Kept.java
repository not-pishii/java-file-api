import gen.facts.p.Other_;
import gen.facts.p.Tk_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Other;
import p.Tk;

import static org.assertj.core.api.Assertions.assertThat;

/// The members that can be named keep their facts.
public final class Kept {
    private Kept() {}

    public static void theOtherMembersKeepTheirFacts() {
        MutableFieldRef<Tk, Prim.Int> fine = Tk_.fine;
        MethodRef0<Tk, Other> other = Tk_.other;

        assertThat(fine.name()).isEqualTo("fine");
        assertThat(other.resultType().orElseThrow()).isEqualTo(Other_.TOKEN);
    }
}
