import gen.facts.p.Gen_St_;
import me.supcheg.javafile.facts.CtorRef0;
import p.Gen;

import static org.assertj.core.api.Assertions.assertThat;

/// A static class of a generic class is a metamodel: it cannot mention the type parameters.
public final class Static {
    private Static() {}

    public static void aStaticClassOfAGenericClassHasItsFacts() {
        CtorRef0<Gen.St> constructor = Gen_St_.new_;

        assertThat(constructor.owner()).isSameAs(Gen_St_.TOKEN);
    }
}
