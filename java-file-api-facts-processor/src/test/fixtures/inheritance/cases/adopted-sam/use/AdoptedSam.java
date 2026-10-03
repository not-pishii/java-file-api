import gen.facts.p.Fn_;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Sam1;
import p.Fn;

import static org.assertj.core.api.Assertions.assertThat;

/// `Fn extends HiddenFn`, which declares the one abstract method.
public final class AdoptedSam {
    private AdoptedSam() {}

    public static void theSamOfAnInterfaceThatAdoptsItsMethodIsThatFact() {
        Sam1<Fn, String, String> sam = Fn_.sam;
        MethodRef1<Fn, String, String> apply = Fn_.apply_String;

        assertThat(sam.method()).isSameAs(apply);
    }
}
