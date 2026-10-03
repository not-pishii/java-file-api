import gen.facts.java.lang.String_;
import gen.facts.p.StrFn_;
import me.supcheg.javafile.facts.Sam1;
import p.StrFn;

import static org.assertj.core.api.Assertions.assertThat;

/// Nothing of `StrFn` declares `String`, but its `sam` does: the metamodel of `String` is there to refer to.
public final class InheritedSam {
    private InheritedSam() {}

    public static void theTypesOfAnInheritedSamAreInTheClosureOfTheInterface() {
        Sam1<StrFn, String, String> sam = StrFn_.sam;

        assertThat(sam.result()).isEqualTo(String_.TOKEN);
        assertThat(sam.param1()).isEqualTo(String_.TOKEN);
    }
}
